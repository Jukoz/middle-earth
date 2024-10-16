package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.Disposition;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;

public class NpcTargetPlayerGoal extends ActiveTargetGoal<PlayerEntity> {
    NpcEntity mob;
    Disposition ownDisposition;

    public NpcTargetPlayerGoal(NpcEntity mob, Disposition ownDisposition) {
        super(mob, PlayerEntity.class, true);
        this.mob = mob;
        this.ownDisposition = ownDisposition;
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
        if(ownDisposition != null){
            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            Disposition playerDisposition = data.getCurrentDisposition();
            if(playerDisposition == null)
                return true;
            if(playerDisposition == ownDisposition){
                return false;
            }
        }
        return true;
    }
}
