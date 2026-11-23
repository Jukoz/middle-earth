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
import net.minecraft.particle.ParticlesMode;
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
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

/** Flame and smoke particles at the tip of torches and candles carried in the off hand or worn. */
@Environment(EnvType.CLIENT)
public final class HeldFlames {
    private static final float RAISED_ROT = 16;

    private record Kind(Vector3f grip, float thirdScale, Vector3f firstTranslate, float firstScale, List<Vector3f> flames,
                        ParticleEffect particle, float turn, int tint, boolean small) {
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

        Vector3f flame(Vector3f at, boolean firstPerson) {
            if (firstPerson) {
                Vector3f point = new Vector3f(at).sub(8, 8, 8).mul(firstScale);
                new Quaternionf().rotationY(turn * MathHelper.RADIANS_PER_DEGREE).transform(point);
                return point.add(firstTranslate).div(16);
            }
            Vector3f point = new Vector3f(at).sub(grip).mul(thirdScale);
            new Quaternionf().rotationXYZ(RAISED_ROT * MathHelper.RADIANS_PER_DEGREE, turn * MathHelper.RADIANS_PER_DEGREE, 0).transform(point);
            return point.div(16);
        }
    }

    private static final long FRESH_MS = 200;
    private static final Random RANDOM = Random.create();

    private static final List<Vector3f> firstPerson = new ArrayList<>();
    private static long firstPersonNoted;
    private static final Map<ItemStack, Seen> THIRD_PERSON = new WeakHashMap<>();
    private static final Map<ItemStack, Seen> WORN = new WeakHashMap<>();
    private static final float CANDLE_PARTICLE_SCALE = 0.75F;

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
        ClientPlayConnectionEvents.JOIN.register((handler, sender, client) -> HeldLights.load(handler.getRegistryManager(), true));
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> HeldLights.clear(true));
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (HeldLights.of(true, stack) instanceof HeldLight light && light.toggled()) {
                lines.add(Text.translatable(HeldLights.isOn(stack) ? "tooltip.middle-earth.held_light.lit"
                    : "tooltip.middle-earth.held_light.shaded").formatted(Formatting.GRAY));
                lines.add(Text.translatable(Screen.hasShiftDown()
                    ? "tooltip.middle-earth.held_light.hint" : "tooltip.middle-earth.held_light.hold_shift")
                    .formatted(Formatting.DARK_GRAY));
            }
        });
    }

    private static @Nullable Kind kind(ItemStack stack) {
        if (!(HeldLights.active(true, stack) instanceof HeldLight.Glow glow) || !glow.flame()) {
            return null;
        }
        return Kind.of(HeldLights.data(true, stack));
    }

    /** Flame tips from the hand matrix through the held model's display transform. */
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
        for (Entity entity : world.getEntities()) {
            if (!(entity instanceof LivingEntity living) || living.isInvisible() || living.isSpectator()) {
                continue;
            }
            ItemStack head = living.getEquippedStack(EquipmentSlot.HEAD);
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

    private static void flicker(ClientWorld world, Vec3d at, Kind kind) {
        if (MinecraftClient.getInstance().options.getParticles().getValue() == ParticlesMode.MINIMAL) {
            return;
        }
        boolean candle = kind.small();
        ParticleManager particles = MinecraftClient.getInstance().particleManager;
        if (RANDOM.nextInt(3) == 0) {
            if (candle) {
                Particle flame = particles.addParticle(kind.particle(), at.x, at.y, at.z, 0, 0.004, 0);
                if (flame != null) {
                    flame.scale(CANDLE_PARTICLE_SCALE);
                }
            } else if (kind.tint() < 0) {
                world.addParticleClient(kind.particle(), at.x, at.y, at.z, 0, 0.004, 0);
            } else {
                Particle flame = MinecraftClient.getInstance().particleManager
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
                Particle smoke = particles.addParticle(ParticleTypes.SMOKE, at.x, at.y + 0.05, at.z, 0, 0.01, 0);
                if (smoke != null) {
                    smoke.scale(CANDLE_PARTICLE_SCALE);
                }
            } else {
                world.addParticleClient(ParticleTypes.SMOKE, at.x, at.y + 0.05, at.z, 0, 0.01, 0);
            }
        }
    }
}
