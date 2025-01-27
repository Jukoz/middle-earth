package net.sevenstars.middleearth.entity.goals;

import net.sevenstars.middleearth.entity.NpcEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;

public class NpcTargetPlayerGoal extends ActiveTargetGoal<PlayerEntity> {
    NpcEntity mob;

    public NpcTargetPlayerGoal(NpcEntity mob) {
        super(mob, PlayerEntity.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && canContinue();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && canContinue();
    }

    private boolean canContinue(){
        if(this.mob.getTarget() != null && this.mob.getTarget().isAlive()){
            if(this.mob.getTarget() instanceof PlayerEntity player){
                if(player.isCreative() || player.distanceTo(this.mob) > 50)
                    return false;
                return true;
            }
        }
        return false;
    }
}
