package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.Disposition;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;

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
