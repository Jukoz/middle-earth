package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.Disposition;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;

public class BeastTargetPlayerGoal extends ActiveTargetGoal<PlayerEntity> {
    AbstractBeastEntity mob;
    Disposition beastDisposition;

    public BeastTargetPlayerGoal(AbstractBeastEntity mob, Disposition beastDisposition) {
        super(mob, PlayerEntity.class, true);
        this.mob = mob;
        this.beastDisposition = beastDisposition;
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
        if(mob.getWorld().getDifficulty() == Difficulty.PEACEFUL || mob.isTame()){
            return false;
        }
        if(beastDisposition != null){
            PlayerData data = StateSaverAndLoader.getPlayerState(player);
            Disposition playerDisposition = data.getCurrentDisposition();
            if(playerDisposition == null)
                return true;
            if(playerDisposition == beastDisposition){
                return false;
            }
        }
        return true;
    }
}
