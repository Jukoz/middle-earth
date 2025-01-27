package net.sevenstars.middleearth.entity.goals;

import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;

public class TargetPlayerGoal extends ActiveTargetGoal<PlayerEntity> {
    AbstractBeastEntity mob;
    public TargetPlayerGoal(AbstractBeastEntity mob) {
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
