package net.sevenstars.middleearth.entity.goals;

import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.PathNodeType;

import java.util.EnumSet;

public class BeastFollowOwnerGoal extends Goal {
    private final AbstractBeastEntity mob;
    private LivingEntity owner;
    private final double speed;
    private final EntityNavigation navigation;
    private int updateCountdownTicks;
    private final float maxDistance;
    private final float minDistance;
    private float oldWaterPathfindingPenalty;

    public BeastFollowOwnerGoal(AbstractBeastEntity mob, double speed, float minDistance, float maxDistance) {
        this.mob = mob;
        this.speed = speed;
        this.navigation = mob.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
        if (!(mob.getNavigation() instanceof MobNavigation) && !(mob.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for BeastFollowOwnerGoal");
        }
    }

    @Override
    public boolean canStart() {
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity == null) {
            return false;
        }
        if (this.mob.cannotFollowOwner()) {
            return false;
        }
        if (this.mob.squaredDistanceTo(livingEntity) < (double)(this.minDistance * this.minDistance)) {
            return false;
        }
        this.owner = livingEntity;
        return true;
    }

    @Override
    public boolean shouldContinue() {
        if (this.navigation.isIdle()) {
            return false;
        }
        if (this.mob.cannotFollowOwner()) {
            return false;
        }
        return !(this.mob.squaredDistanceTo(this.owner) <= (double)(this.maxDistance * this.maxDistance));
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathfindingPenalty = this.mob.getPathfindingPenalty(PathNodeType.WATER);
        this.mob.setPathfindingPenalty(PathNodeType.WATER, 0.0f);
        this.mob.setRunning(true);
    }

    @Override
    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.mob.setPathfindingPenalty(PathNodeType.WATER, this.oldWaterPathfindingPenalty);
        this.mob.setRunning(false);
    }

    @Override
    public void tick() {
        this.mob.getLookControl().lookAt(this.owner, 10.0f, this.mob.getMaxLookPitchChange());

        if (--this.updateCountdownTicks > 0) {
            return;
        }
        this.updateCountdownTicks = this.getTickCount(10);
        this.navigation.startMovingTo(this.owner, this.speed);
    }
}
