package net.sevenstars.middleearth.entity.goals;

import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.PathType;

public class FollowDifferentMobGoal<T extends Mob> extends Goal {
    private final Mob mob;
    private final Predicate<Mob> targetPredicate;
    @Nullable
    private Mob target;
    private final double speed;
    private final PathNavigation navigation;
    private int updateCountdownTicks;
    private final float minDistance;
    private float oldWaterPathFindingPenalty;
    private final float maxDistance;
    private final Class<T> followedClass;

    public FollowDifferentMobGoal(Mob follower, Class<T> followed, double speed, float minDistance, float maxDistance) {
        this.mob = follower;
        this.followedClass = followed;
        this.targetPredicate = target -> target != null && followed != target.getClass();
        this.speed = speed;
        this.navigation = follower.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(follower.getNavigation() instanceof GroundPathNavigation) && !(follower.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported follower type for FollowMobGoal");
        }
    }

    @Override
    public boolean canUse() {
        List<T> list = this.mob.level().getEntitiesOfClass(this.followedClass, this.mob.getBoundingBox().inflate(this.maxDistance), EntitySelector.ENTITY_STILL_ALIVE);
        if (!list.isEmpty()) {
            for (Mob mobEntity : list) {
                if (!mobEntity.isInvisible()) {
                    this.target = mobEntity;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        return this.target != null && !this.navigation.isDone() && this.mob.distanceToSqr(this.target) > (this.minDistance/2) * (this.minDistance/2);
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathFindingPenalty = this.mob.getPathfindingMalus(PathType.WATER);
        this.mob.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.target = null;
        this.navigation.stop();
        this.mob.setPathfindingMalus(PathType.WATER, this.oldWaterPathFindingPenalty);
    }

    @Override
    public void tick() {
        if (this.target != null && !this.mob.isLeashed()) {
            this.mob.getLookControl().setLookAt(this.target, 10.0F, this.mob.getMaxHeadXRot());
            if (--this.updateCountdownTicks <= 0) {
                this.updateCountdownTicks = this.adjustedTickDelay(10);
                double d = this.mob.getX() - this.target.getX();
                double e = this.mob.getY() - this.target.getY();
                double f = this.mob.getZ() - this.target.getZ();
                double g = d * d + e * e + f * f;
                if (!(g <= this.minDistance * this.minDistance)) {
                    this.navigation.moveTo(this.target, this.speed);
                } else {
                    this.navigation.stop();
                    LookControl lookControl = this.target.getLookControl();
                    if (g <= this.minDistance
                            || lookControl.getWantedX() == this.mob.getX() && lookControl.getWantedY() == this.mob.getY() && lookControl.getWantedZ() == this.mob.getZ()) {
                        double h = this.target.getX() - this.mob.getX();
                        double i = this.target.getZ() - this.mob.getZ();
                        this.navigation.moveTo(this.mob.getX() - h, this.mob.getY(), this.mob.getZ() - i, this.speed);
                    }
                }
            }
        }
    }
}
