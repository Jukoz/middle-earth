package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.trolls.TrollEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.TameableEntity;

import java.util.EnumSet;

public class BeastSitGoal extends Goal {
    private final TrollEntity mob;

    public BeastSitGoal(TrollEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.JUMP, Goal.Control.MOVE));
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
