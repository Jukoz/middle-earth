package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.NoPenaltyTargeting;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.entity.spider.Pouncer;

import java.util.EnumSet;

public class PounceRetreatGoal extends Goal {
    private final HostileEntity spider;
    private LivingEntity target;
    private final float verticalVelocity;
    private final float horizontalVelocity;
    protected final EntityNavigation fleeingEntityNavigation;
    private boolean leaping;
    private Path path;
    private float healthPercentage;
    private int timer;

    public PounceRetreatGoal(HostileEntity mob, float verticalVelocity, float horizontalVelocity,
                             float healthPercentage) {
        this.spider = mob;
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
        this.healthPercentage = healthPercentage;
        this.fleeingEntityNavigation = mob.getNavigation();
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        leaping = false;
    }

    @Override
    public boolean canStart() {
        if (this.spider.hasPassengers()) {
            return false;
        }
        this.target = this.spider.getTarget();
        if (this.target == null) {
            return false;
        }

        float percentageHealthLeft = this.spider.getHealth() / this.spider.getMaxHealth();
        if(percentageHealthLeft > this.healthPercentage) {
            return false;
        }

        return this.spider.isOnGround();
    }

    @Override
    public void tick() {
        super.tick();
        if(leaping) {
            if(this.spider.isOnGround()) {
                leaping = false;
            }
        } else {
            Vec3d vec3d = NoPenaltyTargeting.findFrom(this.spider, 28, 12, this.target.getPos());
            if (vec3d != null) {
                this.path = this.fleeingEntityNavigation.findPathTo(vec3d.x, vec3d.y, vec3d.z, 0);
                if(this.path != null) {
                    this.fleeingEntityNavigation.startMovingAlong(this.path, 1.05f);
                }
            }
        }
        timer = Math.max(0, timer - 1);
        this.spider.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
    }

    @Override
    public boolean shouldContinue() {
        double distance = this.spider.distanceTo(this.target);
        return timer > 0 && distance > 4.5f;
    }

    @Override
    public void start() {
        this.spider.getNavigation().stop();
        this.spider.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        this.spider.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200, 1));

        Vec3d backPounce = new Vec3d(1, 1, 1).multiply(-horizontalVelocity);
        this.spider.setVelocity(backPounce.x, this.verticalVelocity, backPounce.z);
        leaping = true;
        timer = 100;
    }
}
