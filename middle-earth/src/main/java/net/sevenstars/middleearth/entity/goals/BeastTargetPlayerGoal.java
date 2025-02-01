package net.sevenstars.middleearth.entity.goals;

import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.resources.StateSaverAndLoader;
import net.sevenstars.middleearth.resources.datas.Disposition;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerData;
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
        if(this.mob.getWorld() instanceof ServerWorld serverWorld) {
            PlayerEntity player = serverWorld.getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());;
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
        return false;
    }
}
