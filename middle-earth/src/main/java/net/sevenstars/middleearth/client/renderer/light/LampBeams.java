package net.sevenstars.middleearth.client.renderer.light;

import java.util.Iterator;
import java.util.Map;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.Arm;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.light.BeamLights;
import net.sevenstars.middleearth.light.BeamLitBlock;
import net.sevenstars.middleearth.light.HeldLight;
import net.sevenstars.middleearth.light.HeldLights;
import net.sevenstars.middleearth.mixin.client.GameRendererFovInvoker;
import net.sevenstars.middleearth.resources.datas.held_lights.HeldLightData;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

/**
 * The visible side of beam lights, for every player: the soft beam from each lit one (held in someone's off hand
 * or set down) to where it lands, and any {@link BeamLitBlock} answering where a beam
 * sweeps over it.
 */
@Environment(EnvType.CLIENT)
public final class LampBeams {
    /** A beam this frame: from the lens to where it lands. */
    public record Beam(Vec3d from, Vec3d to, int color) {
    }

    private record Seen(Beam beam, long time) {
    }

    private static final int SIDES = 8;
    /** Rings along a beam, so its fade can curve. */
    private static final int SEGMENTS = 12;
    /** How far along its reach a beam is seen before it has faded away (its light still goes the whole way). */
    private static final double FADE_SHARE = 0.75;
    /** A held lamp's beam is seen only near its holder: gone this many blocks from the lens. */
    private static final double HELD_FADE = 5;
    /** How far a beam is seen past the face it lands on (through a thin wall, leaves and the like). */
    private static final double SHINE_THROUGH = 1;
    /** Where your own off-hand lamp's lens sits in view, until the hand has been drawn: ahead, to the side, and up. */
    private static final double FIRST_PERSON_AHEAD = 0.8;
    private static final double FIRST_PERSON_SIDE = 0.56;
    private static final double FIRST_PERSON_DOWN = -0.58;
    /**
     * A third-person holder at full size (all of it scales with them): the shoulder's height (standing, sneaking)
     * and how far out from the middle; along the raised arm to the fist; up from the arm to where the item hangs.
     */
    private static final double SHOULDER = 1.375;
    private static final double SHOULDER_SNEAKING = 1.08;
    private static final double SHOULDER_OUT = 0.33;
    private static final double ARM_TO_FIST = 0.625;
    private static final double ARM_TO_ITEM = 0.125;
    /** The raised lamp as its lit item model shows it: hung 4 px below the fist. */
    private static final double RAISED_DROP = 4 / 16.0;
    /** How long a noted first-person lens stays good, in ms (it is noted every frame the hand is drawn). */
    private static final long FIRST_PERSON_FRESH_MS = 200;
    /** The beam's radius at the lens, and how much it widens per block it travels. */
    private static final float START_RADIUS = 0.09F;
    private static final float SPREAD = 0.1F;
    private static final int START_ALPHA = 26;
    /** Beam-lit block checks per beam per tick: random points in the beam's cone. */
    private static final int GLEAM_SAMPLES = 6;

    /** Placed beam lights drawn recently (their renderer notes them), for their beams and what they light. */
    private static final Long2ObjectOpenHashMap<Seen> LAMPS = new Long2ObjectOpenHashMap<>();
    private static final Random RANDOM = Random.create();
    /** Your own off-hand lamp's lens, in the hand's view space, as last drawn. */
    private static final Vector3f firstPersonLens = new Vector3f();
    private static long firstPersonNoted;

    private LampBeams() {
    }

    public static void register() {
        WorldRenderEvents.AFTER_ENTITIES.register(LampBeams::renderHeld);
        ClientTickEvents.END_CLIENT_TICK.register(LampBeams::tick);
    }

    public static void notePlaced(BlockPos pos, Beam beam, long time) {
        LAMPS.put(pos.asLong(), new Seen(beam, time));
    }

    /**
     * Called as your own hand items are drawn: where a lit beam light's lens is, in the hand's view space.
     * The matrix is the hand's, turned to the world's axes (the game undoes the camera's turn for the hand), so
     * the camera's turn is put back to get view space.
     */
    public static void noteFirstPerson(ItemStack stack, boolean leftHand, Matrix4f matrix) {
        HeldLightData data = HeldLights.data(true, stack);
        if (data == null || !(HeldLights.active(true, stack) instanceof HeldLight.Beam)) {
            return;
        }
        // The lens on the held model, centred as an item is drawn, then shown as its first-person display has it.
        HeldLightData.Display display = data.display();
        Vector3f at = data.lens();
        float x = display.firstScale() * (at.x() / 16 - 0.5F);
        float y = display.firstY() / 16 + display.firstScale() * (at.y() / 16 - 0.5F);
        float z = display.firstScale() * (at.z() / 16 - 0.5F);
        Vector3f lens = matrix.transformPosition(new Vector3f(x, y, z));
        Quaternionf toView = new Quaternionf(MinecraftClient.getInstance().gameRenderer.getCamera().getRotation()).conjugate();
        firstPersonLens.set(toView.transform(lens));
        firstPersonNoted = Util.getMeasuringTimeMs();
    }

    /**
     * The beam of a lamp in a player's off hand: from the lamp's lens to where they look.
     * <p>
     * Seen through your own eyes, the beam starts at the lens of the lamp as it was just drawn in your hand. The
     * hand is drawn with its own field of view, so the point is moved to where it shows in the world's. Anyone
     * else holds the lamp up before them, the arm along their gaze, so the beam starts there.
     */
    public static Beam held(PlayerEntity player, HeldLight.Beam light, float tickProgress) {
        Vec3d look = player.getRotationVec(tickProgress);
        double side = player.getMainArm() == Arm.RIGHT ? -1 : 1;
        // Square to the gaze, from the yaw alone, so it holds when looking straight up or down.
        float yaw = player.getYaw(tickProgress) * MathHelper.RADIANS_PER_DEGREE;
        Vec3d right = new Vec3d(-MathHelper.cos(yaw), 0, -MathHelper.sin(yaw));
        Vec3d up = right.crossProduct(look).normalize();
        Vec3d from;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.getCameraEntity() == player && client.options.getPerspective().isFirstPerson()) {
            from = firstPersonFrom(client, tickProgress);
            if (from == null) {
                from = player.getCameraPosVec(tickProgress).add(look.multiply(FIRST_PERSON_AHEAD))
                    .add(right.multiply(side * FIRST_PERSON_SIDE)).add(up.multiply(FIRST_PERSON_DOWN));
            }
        } else {
            // Races differ in size, and the whole model scales with them.
            double size = player.getScale();
            float bodyYaw = MathHelper.lerpAngleDegrees(tickProgress, player.lastBodyYaw, player.bodyYaw)
                * MathHelper.RADIANS_PER_DEGREE;
            Vec3d bodyRight = new Vec3d(-MathHelper.cos(bodyYaw), 0, -MathHelper.sin(bodyYaw));
            Vec3d shoulder = player.getLerpedPos(tickProgress)
                .add(0, size * (player.isInSneakingPose() ? SHOULDER_SNEAKING : SHOULDER), 0)
                .add(bodyRight.multiply(side * size * SHOULDER_OUT));
            // The lamp hangs upright from the raised fist by its handle, its front (north, on the model) ahead.
            double lensUp = ARM_TO_ITEM - RAISED_DROP;
            double lensAhead = ARM_TO_FIST;
            double lensSide = 0;
            HeldLightData data = HeldLights.data(true, player.getOffHandStack());
            if (data != null) {
                double scale = data.display().thirdScale();
                lensUp += scale * (data.lens().y() / 16 - 0.5);
                lensAhead += scale * (0.5 - data.lens().z() / 16);
                lensSide = scale * (data.lens().x() / 16 - 0.5);
            }
            from = shoulder.add(look.multiply(size * lensAhead)).add(up.multiply(size * lensUp))
                .add(right.multiply(size * lensSide));
        }
        Vec3d to = BeamLights.beamEnd(player.getWorld(), from, look, light.range());
        return new Beam(from, to, light.color());
    }

    /** Your own lamp's lens in the world, from where it was last drawn in your hand; null if it has not been. */
    private static @Nullable Vec3d firstPersonFrom(MinecraftClient client, float tickProgress) {
        if (Util.getMeasuringTimeMs() - firstPersonNoted > FIRST_PERSON_FRESH_MS) {
            return null;
        }
        return handToWorld(client, firstPersonLens, tickProgress);
    }

    /**
     * Where a point of your own hand items (in the hand's view space) shows in the world: the hand is drawn with
     * its own field of view, so the point is moved to where it shows in the world's, then turned with the camera.
     */
    public static Vec3d handToWorld(MinecraftClient client, Vector3f handView, float tickProgress) {
        Camera camera = client.gameRenderer.getCamera();
        GameRendererFovInvoker fov = (GameRendererFovInvoker) client.gameRenderer;
        double world = Math.tan(fov.middleearth$getFov(camera, tickProgress, true) * MathHelper.RADIANS_PER_DEGREE / 2);
        double hand = Math.tan(fov.middleearth$getFov(camera, tickProgress, false) * MathHelper.RADIANS_PER_DEGREE / 2);
        float scale = (float) (world / hand);
        Vector3f view = new Vector3f(handView.x * scale, handView.y * scale, handView.z);
        Vector3f offset = new Quaternionf(camera.getRotation()).transform(view);
        return camera.getPos().add(offset.x, offset.y, offset.z);
    }

    /** A point of the hand's matrix (world-turned, as the game draws the hand) into the hand's view space. */
    public static Vector3f handView(Matrix4f matrix, Vector3f local) {
        Vector3f lens = matrix.transformPosition(new Vector3f(local));
        Quaternionf toView = new Quaternionf(MinecraftClient.getInstance().gameRenderer.getCamera().getRotation()).conjugate();
        return toView.transform(lens);
    }

    private static void renderHeld(WorldRenderContext context) {
        ClientWorld world = context.world();
        MatrixStack matrices = context.matrixStack();
        VertexConsumerProvider consumers = context.consumers();
        if (world == null || matrices == null || consumers == null) {
            return;
        }
        float tickProgress = context.tickCounter().getTickProgress(true);
        Vec3d camera = context.camera().getPos();
        for (AbstractClientPlayerEntity player : world.getPlayers()) {
            if (HeldLights.held(player) instanceof HeldLight.Beam light) {
                Beam beam = held(player, light, tickProgress);
                draw(matrices.peek().getPositionMatrix(), consumers.getBuffer(layer()),
                    beam.from().subtract(camera), beam.to().subtract(camera), beam.color(), HELD_FADE);
            }
        }
    }

    /**
     * What beams are drawn with: added onto what is behind them, as light, and leaving no depth behind, so water,
     * particles and anything else see-through drawn after them still shows over and behind them. (Triangles.)
     */
    public static RenderLayer layer() {
        return RenderLayer.getDragonRays();
    }

    /** How far a beam is seen: it fades to nothing well inside the reach of its light, so no end shows. */
    public static double fadeLength(int range) {
        return range * FADE_SHARE;
    }

    /**
     * A soft beam: one faint cone, widening at a fixed angle and fading with distance, to nothing at the fade
     * length (it stops there, wherever it would land). Drawn additively, so it reads as light rather than a solid.
     */
    public static void draw(Matrix4f matrix, VertexConsumer buffer, Vec3d from, Vec3d to, int rgb, double fadeLength) {
        Vec3d axis = to.subtract(from);
        // Its whole fading length, but no more than a block past what it lands on: the light still goes where it
        // lands; the beam is not seen beyond the face it hits but for the skin of it.
        double length = Math.min(fadeLength, axis.length() + SHINE_THROUGH);
        if (length < 1.0E-2) {
            return;
        }
        Vec3d dir = axis.normalize();
        for (int i = 0; i < SEGMENTS; i++) {
            double d1 = length * i / SEGMENTS;
            double d2 = length * (i + 1) / SEGMENTS;
            cone(matrix, buffer, from.add(dir.multiply(d1)), from.add(dir.multiply(d2)), rgb,
                radius(d1), radius(d2), alpha(d1, fadeLength), alpha(d2, fadeLength));
        }
    }

    private static float radius(double distance) {
        return START_RADIUS + SPREAD * (float) distance;
    }

    /** Bright at the lens, falling off quickly and then slowly, gone at the fade length. */
    private static int alpha(double distance, double fadeLength) {
        double left = MathHelper.clamp(1 - distance / fadeLength, 0, 1);
        return (int) Math.round(START_ALPHA * left * left * left);
    }

    private static void cone(Matrix4f matrix, VertexConsumer buffer, Vec3d from, Vec3d to, int rgb,
                             float startRadius, float endRadius, int startAlpha, int endAlpha) {
        Vec3d axis = to.subtract(from);
        if (axis.lengthSquared() < 1.0E-4) {
            return;
        }
        Vec3d dir = axis.normalize();
        Vec3d up = Math.abs(dir.y) > 0.95 ? new Vec3d(1, 0, 0) : new Vec3d(0, 1, 0);
        Vec3d u = dir.crossProduct(up).normalize();
        Vec3d v = dir.crossProduct(u).normalize();
        int r = (rgb >> 16) & 0xFF;
        int g = (rgb >> 8) & 0xFF;
        int b = rgb & 0xFF;
        for (int i = 0; i < SIDES; i++) {
            double a1 = MathHelper.TAU * i / SIDES;
            double a2 = MathHelper.TAU * (i + 1) / SIDES;
            Vec3d o1 = u.multiply(Math.cos(a1)).add(v.multiply(Math.sin(a1)));
            Vec3d o2 = u.multiply(Math.cos(a2)).add(v.multiply(Math.sin(a2)));
            Vec3d s1 = from.add(o1.multiply(startRadius));
            Vec3d s2 = from.add(o2.multiply(startRadius));
            Vec3d e1 = to.add(o1.multiply(endRadius));
            Vec3d e2 = to.add(o2.multiply(endRadius));
            // Two triangles a side, in both windings, so the beam shows from inside and out.
            vertex(matrix, buffer, s1, r, g, b, startAlpha);
            vertex(matrix, buffer, s2, r, g, b, startAlpha);
            vertex(matrix, buffer, e2, r, g, b, endAlpha);
            vertex(matrix, buffer, s1, r, g, b, startAlpha);
            vertex(matrix, buffer, e2, r, g, b, endAlpha);
            vertex(matrix, buffer, e1, r, g, b, endAlpha);
            vertex(matrix, buffer, e2, r, g, b, endAlpha);
            vertex(matrix, buffer, s2, r, g, b, startAlpha);
            vertex(matrix, buffer, s1, r, g, b, startAlpha);
            vertex(matrix, buffer, e1, r, g, b, endAlpha);
            vertex(matrix, buffer, e2, r, g, b, endAlpha);
            vertex(matrix, buffer, s1, r, g, b, startAlpha);
        }
    }

    private static void vertex(Matrix4f matrix, VertexConsumer buffer, Vec3d at, int r, int g, int b, int a) {
        buffer.vertex(matrix, (float) at.x, (float) at.y, (float) at.z).color(r, g, b, a);
    }

    /** Every tick: beam-lit blocks in any beam's cone may answer. */
    private static void tick(MinecraftClient client) {
        ClientWorld world = client.world;
        if (world == null || client.isPaused()) {
            return;
        }
        for (AbstractClientPlayerEntity player : world.getPlayers()) {
            if (HeldLights.held(player) instanceof HeldLight.Beam light) {
                gleam(world, held(player, light, 1.0F));
            }
        }
        long now = world.getTime();
        Iterator<Map.Entry<Long, Seen>> it = LAMPS.entrySet().iterator();
        while (it.hasNext()) {
            Seen seen = it.next().getValue();
            if (now - seen.time() > 5 || now < seen.time()) {
                it.remove();
            } else {
                gleam(world, seen.beam());
            }
        }
    }

    private static void gleam(ClientWorld world, Beam beam) {
        Vec3d axis = beam.to().subtract(beam.from());
        double length = axis.length();
        if (length < 0.5) {
            return;
        }
        Vec3d dir = axis.multiply(1 / length);
        Vec3d up = Math.abs(dir.y) > 0.95 ? new Vec3d(1, 0, 0) : new Vec3d(0, 1, 0);
        Vec3d u = dir.crossProduct(up).normalize();
        Vec3d v = dir.crossProduct(u).normalize();
        for (int i = 0; i <= GLEAM_SAMPLES; i++) {
            Vec3d at;
            if (i == GLEAM_SAMPLES) {
                // The block the beam lands on.
                at = beam.to().add(dir.multiply(0.3));
            } else {
                double t = 0.3 + 0.7 * RANDOM.nextDouble();
                double radius = (0.4 + 1.6 * t) * Math.sqrt(RANDOM.nextDouble());
                double angle = RANDOM.nextDouble() * MathHelper.TAU;
                at = beam.from().add(axis.multiply(t))
                    .add(u.multiply(Math.cos(angle) * radius)).add(v.multiply(Math.sin(angle) * radius));
            }
            BlockPos pos = BlockPos.ofFloored(at);
            BlockState state = world.getBlockState(pos);
            if (state.getBlock() instanceof BeamLitBlock lit) {
                lit.onBeamLit(world, pos, state, RANDOM);
            }
        }
    }
}
