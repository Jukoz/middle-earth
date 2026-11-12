package net.sevenstars.middleearth.client.renderer.light;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.registration.DecorativeBlockRegistryME;
import net.sevenstars.middleearth.light.HeldLight;
import net.sevenstars.middleearth.light.HeldLights;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * Flames on torches and candles carried in the off hand: little flames and a wisp of smoke at the tip, where the
 * held model shows it (in your own view and on everyone else), for every client.
 * <p>
 * The tip is found from the matrix the hand item is drawn with, through the same display transform as its held
 * model (models/item/held, made by the art script; keep the numbers here in step with it).
 */
@Environment(EnvType.CLIENT)
public final class HeldFlames {
    /** How far a held-up light is turned on the arm (degrees on x), as its held model's display has it. */
    private static final float RAISED_ROT = 16;

    /**
     * How a kind of flame is held, as its held model's display has it: the grip (model pixels, where the fist
     * closes), the third-person scale, the first-person translation (pixels) and scale; and where its flames are
     * (model pixels).
     */
    private record Kind(Vector3f grip, float thirdScale, Vector3f firstTranslate, float firstScale, List<Vector3f> flames,
                        ParticleEffect particle, float turn, int tint) {
        Kind(float gx, float gy, float gz, float thirdScale, float firstY, float firstScale, float fx, float fy, float fz, ParticleEffect particle) {
            this(new Vector3f(gx, gy, gz), thirdScale, new Vector3f(0, firstY, 0), firstScale, List.of(new Vector3f(fx, fy, fz)), particle, 0, -1);
        }

        /** Turned about the upright (degrees, as the held display's y rotation), e.g. to face forward. */
        Kind turned(float degrees) {
            return new Kind(grip, thirdScale, firstTranslate, firstScale, flames, particle, degrees, tint);
        }

        /** Its flame in another colour (RGB), on a white particle. */
        Kind tinted(int rgb) {
            return new Kind(grip, thirdScale, firstTranslate, firstScale, flames, particle, turn, rgb);
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

    private static final Kind TORCH = new Kind(8, 3, 8, 0.85F, 3, 0.9F, 8, 11.5F, 8, ParticleTypes.SMALL_FLAME);
    private static final Kind SOUL_TORCH = new Kind(8, 3, 8, 0.85F, 3, 0.9F, 8, 11.5F, 8, ParticleTypes.SOUL_FIRE_FLAME);
    private static final Kind CANDLE = new Kind(8, 0.5F, 8, 0.9F, 5, 1.0F, 8, 7.7F, 8, ParticleTypes.SMALL_FLAME);
    private static final Kind CANDLESTICK = new Kind(8, 4, 8, 0.6F, 1, 0.6F, 8, 16.7F, 8, ParticleTypes.SMALL_FLAME);
    // Held by its loop (turned 45 degrees on the model, at the north-west corner), the loop turned to the hand.
    private static final Kind CANDLE_HOLDER = new Kind(4.59F, 2.5F, 4.59F, 0.6F, 1, 0.6F, 8, 11.7F, 8, ParticleTypes.SMALL_FLAME).turned(135);
    private static final Kind SKULL_CANDLE = new Kind(10, 0, 7, 0.5F, 1, 0.55F, 8, 15.7F, 8, ParticleTypes.SMALL_FLAME).turned(-67.5F);
    private static final Kind CERAMIC_LAMP = new Kind(8, 2.5F, 12.5F, 0.6F, 1, 0.6F, 8, 6, 3, ParticleTypes.SMALL_FLAME);
    private static final Map<Item, Kind> KINDS = new IdentityHashMap<>();

    static {
        KINDS.put(Items.TORCH, TORCH);
        KINDS.put(Items.SOUL_TORCH, SOUL_TORCH);
        KINDS.put(DecorativeBlockRegistryME.CANDLESTICK.asItem(), CANDLESTICK);
        KINDS.put(DecorativeBlockRegistryME.CANDLE_HOLDER.asItem(), CANDLE_HOLDER);
        KINDS.put(DecorativeBlockRegistryME.SKULL_CANDLE.asItem(), SKULL_CANDLE);
        KINDS.put(DecorativeBlockRegistryME.CERAMIC_LAMP.asItem(), CERAMIC_LAMP);
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
    /**
     * Helmets with candles on them, and their wicks in the head's frame (pixels), as their models have the flames:
     * the Dwarven Miner Helmet's two before the brow, the Goblin Town Tunneler's two stubs on its crown.
     */
    private static final Map<Item, List<Vector3f>> WORN_WICKS = Map.of(
        net.sevenstars.middleearth.item.EquipmentItemsME.DWARVEN_MINER_HELMET,
        List.of(new Vector3f(-5.5F, -10.5F, -6.5F), new Vector3f(5.5F, -10.5F, -6.5F)),
        net.sevenstars.middleearth.item.EquipmentItemsME.GOBLIN_TOWN_TUNNELER_HELMET,
        List.of(new Vector3f(1.4F, -12.5F, 1.5F), new Vector3f(-1.6F, -13.5F, -0.6F)));
    private static final Kind WORN_CANDLE = new Kind(8, 8, 8, 1, 0, 1, 8, 8, 8, ParticleTypes.SMALL_FLAME);

    /** Whether a worn stack has candles that burn on it. */
    public static boolean hasWornFlames(ItemStack stack) {
        return WORN_WICKS.containsKey(stack.getItem());
    }

    private record Seen(List<Vec3d> at, long time) {
    }

    private HeldFlames() {
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(HeldFlames::tick);
        // Lanterns and lamps say whether they are lit, and how to shade them (the focused lamps say so themselves).
        net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> {
            if (HeldLights.of(stack) instanceof HeldLight.Glow glow && glow.toggled()) {
                lines.add(net.minecraft.text.Text.translatable(HeldLights.isOn(stack) ? "item.middle-earth.beam_lamp.lit"
                    : "item.middle-earth.beam_lamp.shaded").formatted(net.minecraft.util.Formatting.GRAY));
                lines.add(net.minecraft.text.Text.translatable("item.middle-earth.beam_lamp.hint").formatted(net.minecraft.util.Formatting.DARK_GRAY));
            }
        });
    }

    private static @Nullable Kind kind(ItemStack stack) {
        if (!(HeldLights.active(stack) instanceof HeldLight.Glow glow) || !glow.flame()) {
            return null;
        }
        Kind kind = KINDS.get(stack.getItem());
        return kind != null ? kind : stack.isIn(ItemTags.CANDLES) ? CANDLE : null;
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
        List<Vector3f> wicks = stack == null ? null : WORN_WICKS.get(stack.getItem());
        if (wicks == null) {
            return;
        }
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
            if (!hasWornFlames(head)) {
                continue;
            }
            Seen seen = WORN.get(head);
            if (seen != null && now - seen.time() <= FRESH_MS) {
                for (Vec3d at : seen.at()) {
                    flicker(world, at, WORN_CANDLE);
                }
            }
        }
    }

    /** A placed torch's flame and smoke, a little at a time. */
    private static void flicker(ClientWorld world, Vec3d at, Kind kind) {
        if (RANDOM.nextInt(3) == 0) {
            if (kind.tint() < 0) {
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
            world.addParticleClient(ParticleTypes.SMOKE, at.x, at.y + 0.05, at.z, 0, 0.01, 0);
        }
    }
}
