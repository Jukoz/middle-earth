package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.Alignment;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;

import java.util.Objects;

public class NpcTargetPlayerGoal extends ActiveTargetGoal<PlayerEntity> {
    NpcEntity mob;
    Alignment ownAlignment;

    public NpcTargetPlayerGoal(NpcEntity mob, Alignment ownAlignment) {
        super(mob, PlayerEntity.class, true);
        this.mob = mob;
        this.ownAlignment = ownAlignment;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && canTargetMob();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && canTargetMob();
    }

    private boolean canTargetMob(){
        LivingEntity entity = mob.getTarget();
        if(entity == null)
            return true;
        PlayerEntity player = (PlayerEntity) entity;
        if(mob.getWorld().getDifficulty() == Difficulty.PEACEFUL){
            return false;
        }
        if(ownAlignment != null){
            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            Alignment playerAlignment = data.getCurrentAlignment();
            if(playerAlignment == null)
                return true;
            if(playerAlignment == ownAlignment){
                return false;
            }
        }
        return true;
    }
}
