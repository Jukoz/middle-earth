package net.jesteur.me.entity.goals;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.EscapeSunlightGoal;
import net.minecraft.entity.mob.PathAwareEntity;

public class PanicFireGoal extends EscapeSunlightGoal {
    public PanicFireGoal(PathAwareEntity mob, double speed) {
        super(mob, speed);
    }

    @Override
    public boolean canStart() {
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (!this.mob.isOnFire()) {
            return false;
        }
        return this.targetShadedPos();
    }
}
