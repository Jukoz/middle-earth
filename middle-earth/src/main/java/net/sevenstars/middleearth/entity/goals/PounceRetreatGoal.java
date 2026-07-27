package net.sevenstars.middleearth.entity.goals;

import java.util.EnumSet;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

public class PounceRetreatGoal extends Goal {
    private final Monster spider;
    private LivingEntity target;
    private final float verticalVelocity;
    private final float horizontalVelocity;
    protected final PathNavigation fleeingEntityNavigation;
    private boolean leaping;
    private Path path;
    private float healthPercentage;
    private int timer;

    public PounceRetreatGoal(Monster mob, float verticalVelocity, float horizontalVelocity,
                             float healthPercentage) {
        this.spider = mob;
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
        this.healthPercentage = healthPercentage;
        this.fleeingEntityNavigation = mob.getNavigation();
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        leaping = false;
    }

    @Override
    public boolean canUse() {
        if (this.spider.isVehicle()) {
            return false;
        }
        this.target = this.spider.getTarget();
        if (this.target == null) {
            return false;
        }

        if(this.spider.hasEffect(MobEffects.REGENERATION)) {
            return false;
        }

        float percentageHealthLeft = this.spider.getHealth() / this.spider.getMaxHealth();
        if(percentageHealthLeft > this.healthPercentage) {
            return false;
        }

        return this.spider.onGround();
    }

    @Override
    public void tick() {
        super.tick();
        if(leaping) {
            if(this.spider.onGround()) {
                leaping = false;
            }
        } else {
            Vec3 vec3d = DefaultRandomPos.getPosAway(this.spider, 28, 12, this.target.position());
            if (vec3d != null) {
                this.path = this.fleeingEntityNavigation.createPath(vec3d.x, vec3d.y, vec3d.z, 0);
                if(this.path != null) {
                    this.fleeingEntityNavigation.moveTo(this.path, 1.05f);
                }
            }
        }
        timer = Math.max(0, timer - 1);
        this.spider.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
    }

    @Override
    public boolean canContinueToUse() {
        double distance = this.spider.distanceTo(this.target);
        return timer > 0 && distance > 4.5f;
    }

    @Override
    public void start() {
        this.spider.getNavigation().stop();
        this.spider.getLookControl().setLookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        this.spider.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1));

        Vec3 backPounce = new Vec3(1, 1, 1).scale(-horizontalVelocity);
        this.spider.setDeltaMovement(backPounce.x, this.verticalVelocity, backPounce.z);
        leaping = true;
        timer = 100;
    }
}
