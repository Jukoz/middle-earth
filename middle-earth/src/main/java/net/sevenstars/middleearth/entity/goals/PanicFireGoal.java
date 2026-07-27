package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;

public class PanicFireGoal extends FleeSunGoal {
    public PanicFireGoal(PathfinderMob mob, double speed) {
        super(mob, speed);
    }

    @Override
    public boolean canUse() {
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (!this.mob.isOnFire()) {
            return false;
        }
        return this.setWantedPos();
    }
}
