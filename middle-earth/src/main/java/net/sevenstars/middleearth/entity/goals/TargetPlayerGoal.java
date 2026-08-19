package net.sevenstars.middleearth.entity.goals;

import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class TargetPlayerGoal extends NearestAttackableTargetGoal<Player> {
    AbstractBeastEntity mob;
    public TargetPlayerGoal(AbstractBeastEntity mob) {
        super((Mob)mob, Player.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (mob.level().getDifficulty() == Difficulty.PEACEFUL || mob.isTamed()) {
            if(mob.getTarget() instanceof Player) {
                mob.setTarget(null);
            }

            return false;
        }
        return super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        if(!mob.isTamed()) {
            return super.canContinueToUse();
        }
        return false;
    }
}
