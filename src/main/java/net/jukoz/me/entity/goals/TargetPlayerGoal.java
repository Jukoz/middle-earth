package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.beasts.BeastEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;

public class TargetPlayerGoal extends ActiveTargetGoal<PlayerEntity> {
    BeastEntity mob;
    public TargetPlayerGoal(BeastEntity mob) {
        super((MobEntity)mob, PlayerEntity.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        if (mob.getWorld().getDifficulty() == Difficulty.PEACEFUL || mob.isTame()) {
            if(mob.getTarget() instanceof PlayerEntity) {
                mob.setTarget(null);
            }

            return false;
        }
        return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        if(!mob.isTame()) {
            return super.shouldContinue();
        }
        return false;
    }
}
