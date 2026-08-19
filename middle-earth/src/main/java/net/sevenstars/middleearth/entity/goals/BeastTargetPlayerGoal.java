package net.sevenstars.middleearth.entity.goals;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

public class BeastTargetPlayerGoal extends NearestAttackableTargetGoal<Player> {
    AbstractBeastEntity mob;
    DispositionType beastDispositionType;

    public BeastTargetPlayerGoal(AbstractBeastEntity mob, DispositionType beastDispositionType) {
        super(mob, Player.class, true);
        this.mob = mob;
        this.beastDispositionType = beastDispositionType;
    }

    @Override
    public boolean canUse() {
        return super.canUse() && canTargetMob();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && canTargetMob();
    }

    private boolean canTargetMob(){
        if(this.mob.level() instanceof ServerLevel serverWorld) {
            Player player = serverWorld.getNearestPlayer(this.targetConditions, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());;
            if(player == null || mob.level().getDifficulty() == Difficulty.PEACEFUL || mob.isTamed() || player == mob.getOwner()){
                return false;
            }
            if(beastDispositionType != null){
                return PlayerDataService.getPlayerDisposition(player, player.level()) != beastDispositionType;
            }
            return true;
        }
        return false;
    }
}
