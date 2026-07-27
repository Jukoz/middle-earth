package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

public class BirdFlightGoal extends Goal {
    private final Mob mob;
    private LivingEntity target;

    public BirdFlightGoal(Mob mob) {
        this.mob = mob;
    }


    @Override
    public boolean canUse() {
        this.target = this.mob.getTarget();

        if(!this.mob.onGround()){
            return false;
        } else if(this.target == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return !this.mob.onGround();
    }

    @Override
    public void start() {
        this.mob.setDeltaMovement(this.mob.getDeltaMovement().x * 4, 0.8D, this.mob.getDeltaMovement().z * 4);
    }
}
