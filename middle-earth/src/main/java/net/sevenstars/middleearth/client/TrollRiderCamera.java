package net.sevenstars.middleearth.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

// Over-the-shoulder third person camera for cave troll riders.
public class TrollRiderCamera {
    private static final double BACK = 4.0;
    private static final double SIDE = 1.6;
    private static final double UP = 0.6;

    private static final double SWAY_FOLLOW = 6.0;
    private static final double HEIGHT_FOLLOW = 10.0;
    private static final double WALL_RELEASE = 4.0;

    private static Perspective perspectiveBeforeMounting;

    private static long lastFrameNanos;
    private static Entity lastVehicle;
    private static Vec3d smoothedRiderOffset;
    private static double smoothedY;
    private static double clipDistance;
    private static Vec3d cameraPos;

    public record View(Vec3d pos, float yaw, float pitch) {}

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            boolean riding = isRidingTroll(client.player);
            if(riding && perspectiveBeforeMounting == null) {
                perspectiveBeforeMounting = client.options.getPerspective();
                client.options.setPerspective(Perspective.THIRD_PERSON_BACK);
            }
            else if(!riding && perspectiveBeforeMounting != null) {
                client.options.setPerspective(perspectiveBeforeMounting);
                perspectiveBeforeMounting = null;
            }
        });
    }

    public static boolean isRidingTroll(Entity entity) {
        return entity != null && entity.getVehicle() instanceof CaveTrollEntity;
    }

    public static boolean isActive(MinecraftClient client) {
        return isRidingTroll(client.getCameraEntity()) && client.options.getPerspective() == Perspective.THIRD_PERSON_BACK;
    }

    public static View compute(Entity rider, float tickProgress) {
        World world = rider.getWorld();
        Entity vehicle = rider.getVehicle();
        long now = System.nanoTime();
        double dt = MathHelper.clamp((now - lastFrameNanos) / 1.0E9, 0.0, 0.1);
        boolean reset = vehicle != lastVehicle || now - lastFrameNanos > 500_000_000L;
        lastFrameNanos = now;
        lastVehicle = vehicle;

        float yaw = rider.getYaw(tickProgress);
        float pitch = rider.getPitch(tickProgress);
        Vec3d look = Vec3d.fromPolar(pitch, yaw);
        Vec3d eye = rider.getCameraPosVec(tickProgress);
        Vec3d vehiclePos = vehicle.getLerpedPos(tickProgress);

        Vec3d riderOffset = eye.subtract(vehiclePos);
        if(reset) {
            smoothedRiderOffset = riderOffset;
            smoothedY = eye.y;
        }
        smoothedRiderOffset = smoothedRiderOffset.add(riderOffset.subtract(smoothedRiderOffset).multiply(follow(SWAY_FOLLOW, dt)));
        Vec3d steadyEye = vehiclePos.add(smoothedRiderOffset);
        smoothedY += (steadyEye.y - smoothedY) * follow(HEIGHT_FOLLOW, dt);
        steadyEye = new Vec3d(steadyEye.x, smoothedY, steadyEye.z);

        Vec3d forward = Vec3d.fromPolar(0.0f, yaw);
        Vec3d right = new Vec3d(-forward.z, 0.0, forward.x);
        Vec3d offset = right.multiply(SIDE).add(0.0, UP, 0.0).subtract(look.multiply(BACK));
        double wantedDistance = offset.length();
        Vec3d direction = offset.multiply(1.0 / wantedDistance);

        double freeDistance = clipToSpace(world, rider, steadyEye, direction, wantedDistance);
        if(reset || freeDistance < clipDistance) {
            clipDistance = freeDistance;
        }
        else {
            clipDistance += (freeDistance - clipDistance) * follow(WALL_RELEASE, dt);
        }

        cameraPos = steadyEye.add(direction.multiply(clipDistance));
        return new View(cameraPos, yaw, pitch);
    }

    // Aiming fix, aim the character at the crosshair instead of the camera position, so the player can hit what they see
    public static HitResult findCrosshairTarget(Entity rider, double blockRange, double entityRange, float tickProgress) {
        if(cameraPos == null) {
            return null;
        }
        World world = rider.getWorld();
        Vec3d eye = rider.getCameraPosVec(tickProgress);
        Vec3d look = rider.getRotationVec(tickProgress);

        Vec3d start = cameraPos.add(look.multiply(Math.max(0.0, eye.subtract(cameraPos).dotProduct(look))));
        Vec3d end = start.add(look.multiply(Math.max(blockRange, entityRange) + start.distanceTo(eye)));

        BlockHitResult blockHit = world.raycast(new RaycastContext(start, end, RaycastContext.ShapeType.OUTLINE, RaycastContext.FluidHandling.NONE, rider));
        Vec3d rayEnd = blockHit.getType() == HitResult.Type.MISS ? end : blockHit.getPos();

        EntityHitResult entityHit = ProjectileUtil.raycast(rider, start, rayEnd, new Box(start, rayEnd).expand(1.0),
                entity -> EntityPredicates.CAN_HIT.test(entity) && entity.getRootVehicle() != rider.getRootVehicle(),
                start.squaredDistanceTo(rayEnd));
        if(entityHit != null) {
            return entityHit.getPos().isInRange(eye, entityRange) ? entityHit : missed(entityHit.getPos(), look);
        }
        if(blockHit.getType() != HitResult.Type.MISS && blockHit.getPos().isInRange(eye, blockRange)) {
            return blockHit;
        }
        return missed(rayEnd, look);
    }

    private static HitResult missed(Vec3d pos, Vec3d look) {
        return BlockHitResult.createMissed(pos, Direction.getFacing(look.x, look.y, look.z), BlockPos.ofFloored(pos));
    }

    private static double follow(double rate, double dt) {
        return 1.0 - Math.exp(-rate * dt);
    }

    private static double clipToSpace(World world, Entity rider, Vec3d from, Vec3d direction, double distance) {
        double allowed = distance;
        for(int i = 0; i < 8; i++) {
            Vec3d corner = from.add(((i & 1) * 2 - 1) * 0.1, ((i >> 1 & 1) * 2 - 1) * 0.1, ((i >> 2 & 1) * 2 - 1) * 0.1);
            HitResult hit = world.raycast(new RaycastContext(corner, corner.add(direction.multiply(distance)),
                    RaycastContext.ShapeType.VISUAL, RaycastContext.FluidHandling.NONE, rider));
            if(hit.getType() != HitResult.Type.MISS) {
                allowed = Math.min(allowed, hit.getPos().distanceTo(from));
            }
        }
        return allowed;
    }
}
