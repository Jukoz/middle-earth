package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.EnumSet;

public class BeastSitGoal extends Goal {
    private final AbstractBeastEntity mob;

    public BeastSitGoal(AbstractBeastEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
    }

    @Override
    public boolean shouldContinue() {
        return this.mob.isSitting();
    }

    @Override
    public boolean canStart() {
        if (!this.mob.isTame()) {
            return false;
        }
        if (this.mob.isInsideWaterOrBubbleColumn()) {
            return false;
        }
        if (!this.mob.isOnGround()) {
            return false;
        }
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity == null) {
            return true;
        }
        if (this.mob.squaredDistanceTo(livingEntity) < 144.0 && livingEntity.getAttacker() != null) {
            return false;
        }
        return this.mob.isSitting();
    }

    @Override
    public void start() {
        this.mob.getNavigation().stop();
    }

    @Override
    public void stop() {
    }
}
