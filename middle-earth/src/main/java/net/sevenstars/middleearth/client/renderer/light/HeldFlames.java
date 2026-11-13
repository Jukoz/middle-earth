package net.sevenstars.middleearth.client.renderer.light;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.light.HeldLight;
import net.sevenstars.middleearth.light.HeldLights;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Flames on torches and candles carried in the off hand: little flames and a wisp of smoke at the tip, where the
 * held model shows it (in your own view and on everyone else), for every client.
 * <p>
 * The tip is found from the matrix the hand item is drawn with, through the same display transform as its held
 * model, as its {@code held_light} entry gives it ({@link HeldLightData}: its grip, display and flames).
 */
@Environment(EnvType.CLIENT)
public final class HeldFlames {
    /** How far a held-up light is turned on the arm (degrees on x), as its held model's display has it. */
    private static final float RAISED_ROT = 16;

    /**
     * How a kind of flame is held, as its held model's display has it: the grip (model pixels, where the fist
     * closes), the third-person scale, the first-person translation (pixels) and scale; and where its flames are
     * (model pixels), what they burn with, and whether they are a candle's (smaller).
     */
    private record Kind(Vector3f grip, float thirdScale, Vector3f firstTranslate, float firstScale, List<Vector3f> flames,
                        ParticleEffect particle, float turn, int tint, boolean small) {
        /** The flames of a held light's entry, or null if it burns with none. */
        static @Nullable Kind of(@Nullable HeldLightData data) {
            if (data == null || data.flames().isEmpty()) {
                return null;
            }
            HeldLightData.Flames flames = data.flames().get();
            HeldLightData.Display display = data.display();
            ParticleEffect particle = Registries.PARTICLE_TYPE.get(flames.particle()) instanceof SimpleParticleType simple
                ? simple : ParticleTypes.SMALL_FLAME;
            return new Kind(new Vector3f(data.grip()), display.thirdScale(), new Vector3f(0, display.firstY(), 0),
                display.firstScale(), flames.points(), particle, display.turn(), flames.tint().orElse(-1), flames.small());
        }

        /** A flame's place in the hand item's frame, in blocks. */
        Vector3f flame(Vector3f at, boolean firstPerson) {
            if (firstPerson) {
                Vector3f point = new Vector3f(at).sub(8, 8, 8).mul(firstScale);
                new Quaternionf().rotationY(turn * MathHelper.RADIANS_PER_DEGREE).transform(point);
                return point.add(firstTranslate).div(16);
            }
            // The display brings the grip to the fist: the flame sits where it is from the grip, turned and scaled.
            Vector3f point = new Vector3f(at).sub(grip).mul(thirdScale);
            new Quaternionf().rotationXYZ(RAISED_ROT * MathHelper.RADIANS_PER_DEGREE, turn * MathHelper.RADIANS_PER_DEGREE, 0).transform(point);
            return point.div(16);
        }
    }

    /** How long a noted flame stays good, in ms (it is noted every frame its holder is drawn). */
    private static final long FRESH_MS = 200;
    private static final Random RANDOM = Random.create();

    /** Your own held flame, in the hand's view space; and everyone's (yours too, seen from outside), in the world. */
    private static final List<Vector3f> firstPerson = new ArrayList<>();
    private static long firstPersonNoted;
    private static final Map<ItemStack, Seen> THIRD_PERSON = new WeakHashMap<>();
    /** Worn lights' flames (the Dwarven Miner Helmet's candles), in the world, by the worn stack. */
    private static final Map<ItemStack, Seen> WORN = new WeakHashMap<>();
    /** Candles' flames and smoke are a little smaller than a torch's. */
    private static final float CANDLE_PARTICLE_SCALE = 0.75F;

    /** Whether a worn stack has candles that burn on it (a worn {@code held_light} entry with flames). */
    public static boolean hasWornFlames(ItemStack stack) {
        HeldLightData data = HeldLights.wornData(true, stack);
        return data != null && data.flames().isPresent();
    }

    private record Seen(List<Vec3d> at, long time) {
    }

    private HeldFlames() {
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(HeldFlames::tick);
        // The client's held lights: loaded as it joins a world (its tags load then too), forgotten as it leaves.
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> HeldLights.load(handler.getRegistryManager(), true));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> HeldLights.clear(true));
        // Lanterns and lamps say whether they are lit, and how to shade them.
        net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (HeldLights.of(true, stack) instanceof HeldLight.Glow glow && glow.toggled()) {
                lines.add(net.minecraft.text.Text.translatable(HeldLights.isOn(stack) ? "tooltip.middle-earth.held_light.lit"
                    : "tooltip.middle-earth.held_light.shaded").formatted(net.minecraft.util.Formatting.GRAY));
                lines.add(net.minecraft.text.Text.translatable("tooltip.middle-earth.held_light.hint").formatted(net.minecraft.util.Formatting.DARK_GRAY));
            }
        });
    }

    private static @Nullable Kind kind(ItemStack stack) {
        if (!(HeldLights.active(true, stack) instanceof HeldLight.Glow glow) || !glow.flame()) {
            return null;
        }
        return Kind.of(HeldLights.data(true, stack));
    }

    /** As your own hand items are drawn (first person). */
    public static void noteFirstPerson(ItemStack stack, boolean leftHand, Matrix4f matrix) {
        Kind kind = kind(stack);
        if (kind != null) {
            firstPerson.clear();
            for (Vector3f flame : kind.flames()) {
                firstPerson.add(LampBeams.handView(matrix, kind.flame(flame, true)));
            }
            firstPersonNoted = Util.getMeasuringTimeMs();
        }
    }

    /** As a player's off-hand item is drawn on them (third person); the matrix is camera-relative, world-turned. */
    public static void noteThirdPerson(ItemStack stack, Matrix4f matrix) {
        Kind kind = kind(stack);
        if (kind != null) {
            Vec3d camera = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
            List<Vec3d> flames = new ArrayList<>();
            for (Vector3f flame : kind.flames()) {
                Vector3f at = matrix.transformPosition(kind.flame(flame, false));
                flames.add(camera.add(at.x, at.y, at.z));
            }
            THIRD_PERSON.put(stack, new Seen(flames, Util.getMeasuringTimeMs()));
        }
    }

    /** As a candle helmet is drawn on its wearer; the matrix is its head's, camera-relative, world-turned. */
    public static void noteWorn(ItemStack stack, Matrix4f matrix) {
        HeldLightData data = HeldLights.wornData(true, stack);
        if (data == null || data.flames().isEmpty()) {
            return;
        }
        List<Vector3f> wicks = data.flames().get().points();
        Vec3d camera = MinecraftClient.getInstance().gameRenderer.getCamera().getPos();
        List<Vec3d> flames = new ArrayList<>();
        for (Vector3f wick : wicks) {
            Vector3f at = matrix.transformPosition(new Vector3f(wick).div(16));
            flames.add(camera.add(at.x, at.y, at.z));
        }
        WORN.put(stack, new Seen(flames, Util.getMeasuringTimeMs()));
    }

    private static void tick(MinecraftClient client) {
        ClientWorld world = client.world;
        if (world == null || client.isPaused()) {
            return;
        }
        long now = Util.getMeasuringTimeMs();
        for (AbstractClientPlayerEntity player : world.getPlayers()) {
            ItemStack stack = player.getOffHandStack();
            Kind kind = kind(stack);
            if (kind == null || player.isSpectator() || player.isInvisible()) {
                continue;
            }
            if (client.getCameraEntity() == player && client.options.getPerspective().isFirstPerson()) {
                if (now - firstPersonNoted <= FRESH_MS) {
                    for (Vector3f flame : firstPerson) {
                        flicker(world, LampBeams.handToWorld(client, flame, 1.0F), kind);
                    }
                }
            } else {
                Seen seen = THIRD_PERSON.get(stack);
                if (seen != null && now - seen.time() <= FRESH_MS) {
                    for (Vec3d at : seen.at()) {
                        flicker(world, at, kind);
                    }
                }
            }
        }
        // Worn candles burn on whoever wears them, as they are drawn (not on your own head in first person).
        for (net.minecraft.entity.Entity entity : world.getEntities()) {
            if (!(entity instanceof net.minecraft.entity.LivingEntity living) || living.isInvisible() || living.isSpectator()) {
                continue;
            }
            ItemStack head = living.getEquippedStack(net.minecraft.entity.EquipmentSlot.HEAD);
            Kind worn = Kind.of(HeldLights.wornData(true, head));
            if (worn == null) {
                continue;
            }
            Seen seen = WORN.get(head);
            if (seen != null && now - seen.time() <= FRESH_MS) {
                for (Vec3d at : seen.at()) {
                    flicker(world, at, worn);
                }
            }
        }
    }

    /** A placed torch's flame and smoke, a little at a time. */
    private static void flicker(ClientWorld world, Vec3d at, Kind kind) {
        boolean candle = kind.small();
        net.minecraft.client.particle.ParticleManager particles = MinecraftClient.getInstance().particleManager;
        if (RANDOM.nextInt(3) == 0) {
            if (candle) {
                net.minecraft.client.particle.Particle flame = particles.addParticle(kind.particle(), at.x, at.y, at.z, 0, 0.004, 0);
                if (flame != null) {
                    flame.scale(CANDLE_PARTICLE_SCALE);
                }
            } else if (kind.tint() < 0) {
                world.addParticleClient(kind.particle(), at.x, at.y, at.z, 0, 0.004, 0);
            } else {
                net.minecraft.client.particle.Particle flame = MinecraftClient.getInstance().particleManager
                    .addParticle(kind.particle(), at.x, at.y, at.z, 0, 0.012, 0);
                if (flame != null) {
                    int rgb = kind.tint();
                    flame.setColor(((rgb >> 16) & 0xFF) / 255F, ((rgb >> 8) & 0xFF) / 255F, (rgb & 0xFF) / 255F);
                    flame.scale(0.6F);
                }
            }
        }
        if (RANDOM.nextInt(12) == 0) {
            if (candle) {
                net.minecraft.client.particle.Particle smoke = particles.addParticle(ParticleTypes.SMOKE, at.x, at.y + 0.05, at.z, 0, 0.01, 0);
                if (smoke != null) {
                    smoke.scale(CANDLE_PARTICLE_SCALE);
                }
            } else {
                world.addParticleClient(ParticleTypes.SMOKE, at.x, at.y + 0.05, at.z, 0, 0.01, 0);
            }
        }
    }
}
