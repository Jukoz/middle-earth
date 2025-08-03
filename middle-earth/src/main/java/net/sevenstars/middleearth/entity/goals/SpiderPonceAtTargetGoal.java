package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.spider.Pouncer;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;

import java.util.EnumSet;

public class SpiderPonceAtTargetGoal extends Goal {
    private final HostileEntity spider;
    private LivingEntity target;
    private final float verticalVelocity;
    private final float horizontalVelocity;
    private boolean startPrePounce;
    private int preparationPounceTimer;
    private Path path;
    private Pouncer pouncer;

    public SpiderPonceAtTargetGoal(HostileEntity mob, Pouncer pouncer, float verticalVelocity, float horizontalVelocity) {
        this.spider = mob;
        this.verticalVelocity = verticalVelocity;
        this.horizontalVelocity = horizontalVelocity;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
        this.pouncer = pouncer;
        startPrePounce = false;
        preparationPounceTimer = 18;
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
        double d = this.spider.distanceTo(this.target);
        if (d < 3.0 || d > 12.0) {
            return false;
        }
        if (!this.spider.isOnGround()) {
            return false;
        }
        this.path = this.spider.getNavigation().findPathTo(target, 0);
        if(path == null) return false;

        int randomInt = this.spider.getRandom().nextInt(PounceAtTargetGoal.toGoalTicks(5));
        return randomInt == 0;
    }

    @Override
    public void tick() {
        super.tick();
        this.path = this.spider.getNavigation().findPathTo(target, 0);
        if(this.path != null) {
            this.spider.getNavigation().startMovingTo(target, 0.8f);
        }
        this.spider.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());

        if(startPrePounce) {
            preparationPounceTimer = Math.max(preparationPounceTimer - 1, 0);
            if(preparationPounceTimer == 0) {
                this.spider.getNavigation().stop();
                Vec3d vec3d = this.spider.getVelocity();
                Vec3d vec3d2 = new Vec3d(this.target.getX() - this.spider.getX(), 0.0, this.target.getZ() - this.spider.getZ());
                if (vec3d2.lengthSquared() > 1.0E-7) {
                    vec3d2 = vec3d2.multiply(horizontalVelocity).add(vec3d.multiply(0.2));;
                }
                this.spider.setVelocity(vec3d2.x, this.verticalVelocity, vec3d2.z);
                startPrePounce = false;
                stop();
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return startPrePounce;
    }

    @Override
    public void start() {
        preparationPounceTimer = 18;
        startPrePounce = true;
        this.spider.getNavigation().startMovingAlong(this.path, 0.8f);
        this.pouncer.startPounceAnimation();
    }

    @Override
    public void stop() {
        super.stop();
        this.pouncer.stopPounceAnimation();
    }
}
