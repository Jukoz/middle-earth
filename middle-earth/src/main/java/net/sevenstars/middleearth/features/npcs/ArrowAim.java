package net.sevenstars.middleearth.features.npcs;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

final class ArrowAim {
    private static final double ARROW_AIR_DRAG = 0.99;
    private static final double MAX_ARROW_FLIGHT_TICKS = 200;
    // AIM_STEP is ticks of flight per arc tried; AIM_BISECTIONS halves the interval
    private static final double AIM_STEP = 0.5;
    private static final int AIM_BISECTIONS = 30;

    private ArrowAim() {}

    // Steps the arrow like vanilla. Picks the flattest arc that reaches, else the furthest
    static Vec3d solve(Vec3d origin, LivingEntity target, double speed, double gravity) {
        Vec3d targetStart = new Vec3d(target.getX(), target.getBodyY(0.5), target.getZ());
        Vec3d targetMovement = movementOf(target);

        double bestTicks = -1;
        double previousTicks = 0;
        double closestTicks = 1;
        double closestSpeedGap = Double.MAX_VALUE;
        for (double ticks = AIM_STEP; ticks <= MAX_ARROW_FLIGHT_TICKS; ticks += AIM_STEP) {
            double speedGap = requiredLaunchVelocity(origin, targetStart, targetMovement, gravity, ticks).length() - speed;
            if (speedGap <= 0) {
                // Needed speed falls then rises, so the first crossing is the flat shot
                double low = previousTicks, high = ticks;
                for (int i = 0; i < AIM_BISECTIONS; i++) {
                    double mid = (low + high) / 2;
                    if (requiredLaunchVelocity(origin, targetStart, targetMovement, gravity, mid).length() <= speed) high = mid;
                    else low = mid;
                }
                bestTicks = high;
                break;
            }
            if (speedGap < closestSpeedGap) {
                closestSpeedGap = speedGap;
                closestTicks = ticks;
            }
            previousTicks = ticks;
        }

        double ticks = bestTicks > 0 ? bestTicks : closestTicks;
        return requiredLaunchVelocity(origin, targetStart, targetMovement, gravity, ticks);
    }

    // Position change over the last tick, not velocity: friction has halved it and a mob on
    // the ground still has downward velocity, which makes shots land short
    private static Vec3d movementOf(LivingEntity target) {
        if(target instanceof PlayerEntity)
            return target.getMovement();
        return new Vec3d(target.getX() - target.lastX, target.isOnGround() ? 0 : target.getY() - target.lastY, target.getZ() - target.lastZ);
    }

    private static Vec3d requiredLaunchVelocity(Vec3d origin, Vec3d targetStart, Vec3d targetMovement, double gravity, double ticks) {
        // After n ticks: offset = v0 * k(n) - gravity / (1 - drag) * (n - k(n)) on y, where k(n) = (1 - drag^n) / (1 - drag).
        double travel = (1 - Math.pow(ARROW_AIR_DRAG, ticks)) / (1 - ARROW_AIR_DRAG);
        double fall = gravity / (1 - ARROW_AIR_DRAG) * (ticks - travel);
        Vec3d offset = targetStart.add(targetMovement.multiply(ticks)).subtract(origin);
        return new Vec3d(offset.x / travel, (offset.y + fall) / travel, offset.z / travel);
    }
}
