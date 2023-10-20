package net.jukoz.me.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class FastPonceAtTargetGoal extends Goal {
    private final MobEntity mob;
    private LivingEntity target;
    private final float verticalVelocity;
    private final float horizontalVelocity;

    public FastPonceAtTargetGoal(MobEntity mob, float verticalVelocity, float horizontalVelocity) {
        this.mob = mob;
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.mob.hasPassengers()) {
            return false;
        }
        this.target = this.mob.getTarget();
        if (this.target == null) {
            return false;
        }
        double d = this.mob.squaredDistanceTo(this.target);
        if (d < 4.0 || d > 16.0) {
            return false;
        }
        if (!this.mob.isOnGround()) {
            return false;
        }
        return this.mob.getRandom().nextInt(PounceAtTargetGoal.toGoalTicks(5)) == 0;
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.isOnGround();
    }

    @Override
    public void start() {
        Vec3d vec3d = this.mob.getVelocity();
        Vec3d vec3d2 = new Vec3d(this.target.getX() - this.mob.getX(), 0.0, this.target.getZ() - this.mob.getZ());
        if (vec3d2.lengthSquared() > 1.0E-7) {
            vec3d2 = vec3d2.multiply(horizontalVelocity).add(vec3d.multiply(0.2));;
        }
        this.mob.setVelocity(vec3d2.x, this.verticalVelocity, vec3d2.z);
    }
}
