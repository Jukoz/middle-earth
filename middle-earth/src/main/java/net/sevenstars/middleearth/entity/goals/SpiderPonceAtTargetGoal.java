package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.entity.spider.MirkwoodSpiderEntity;

import java.util.EnumSet;

public class SpiderPonceAtTargetGoal extends Goal {
    private final MirkwoodSpiderEntity spider;
    private LivingEntity target;
    private final float verticalVelocity;
    private final float horizontalVelocity;
    private boolean startPrePounce;
    private int preparationPounceTimer;

    public SpiderPonceAtTargetGoal(MirkwoodSpiderEntity mob, float verticalVelocity, float horizontalVelocity) {
        this.spider = mob;
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        startPrePounce = false;
        preparationPounceTimer = 1;
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
        double d = this.spider.squaredDistanceTo(this.target);
        if (d < 4.0 || d > 16.0) {
            return false;
        }
        if (!this.spider.isOnGround()) {
            return false;
        }
        return this.spider.getRandom().nextInt(PounceAtTargetGoal.toGoalTicks(5)) == 0;
    }

    @Override
    public void tick() {
        super.tick();
        if(startPrePounce) {
            preparationPounceTimer = Math.max(preparationPounceTimer - 1, 0);
            if(preparationPounceTimer == 0) {
                Vec3d vec3d = this.spider.getVelocity();
                Vec3d vec3d2 = new Vec3d(this.target.getX() - this.spider.getX(), 0.0, this.target.getZ() - this.spider.getZ());
                if (vec3d2.lengthSquared() > 1.0E-7) {
                    vec3d2 = vec3d2.multiply(horizontalVelocity).add(vec3d.multiply(0.2));;
                }
                this.spider.setVelocity(vec3d2.x, this.verticalVelocity, vec3d2.z);
                startPrePounce = false;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.spider.isOnGround();
    }

    @Override
    public void start() {
        startPrePounce = true;
        this.spider.startPrePounce();
    }
}
