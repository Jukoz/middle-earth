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
        PlayerEntity player = this.mob.getWorld().getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());;
        if(player == null || mob.getWorld().getDifficulty() == Difficulty.PEACEFUL || mob.isTame() || player == mob.getOwner()){
            return false;
        }
        if(beastDisposition != null){
            PlayerData data = StateSaverAndLoader.getPlayerState(player);

            if(data == null)
                return false;
            
            Disposition playerDisposition = data.getCurrentDisposition();
            return playerDisposition != beastDisposition;
        }
        return true;
    }
}
