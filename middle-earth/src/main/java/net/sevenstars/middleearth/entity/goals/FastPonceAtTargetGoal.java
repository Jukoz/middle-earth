package net.sevenstars.middleearth.entity.goals;

import java.util.EnumSet;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.phys.Vec3;

public class FastPonceAtTargetGoal extends Goal {
    protected final Mob mob;
    protected LivingEntity target;
    protected final float verticalVelocity;
    protected final float horizontalVelocity;

    public FastPonceAtTargetGoal(Mob mob, float verticalVelocity, float horizontalVelocity) {
        this.mob = mob;
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
        this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        if (this.mob.isVehicle()) {
            return false;
        }
        this.target = this.mob.getTarget();
        if (this.target == null) {
            return false;
        }
        double d = this.mob.distanceToSqr(this.target);
        if (d < 4.0 || d > 16.0) {
            return false;
        }
        if (!this.mob.onGround()) {
            return false;
        }
        return this.mob.getRandom().nextInt(LeapAtTargetGoal.reducedTickDelay(5)) == 0;
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.onGround();
    }

    @Override
    public void start() {
        Vec3 vec3d = this.mob.getDeltaMovement();
        Vec3 vec3d2 = new Vec3(this.target.getX() - this.mob.getX(), 0.0, this.target.getZ() - this.mob.getZ());
        if (vec3d2.lengthSqr() > 1.0E-7) {
            vec3d2 = vec3d2.scale(horizontalVelocity).add(vec3d.scale(0.2));;
        }
        this.mob.setDeltaMovement(vec3d2.x, this.verticalVelocity, vec3d2.z);
    }
}
