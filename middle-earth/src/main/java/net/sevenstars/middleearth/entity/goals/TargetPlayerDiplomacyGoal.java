package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.factions.FactionOld;
import net.sevenstars.middleearth.utils.PlayerUtil;
import net.sevenstars.ofhallsandheralds.registries.services.FactionService;

public class TargetPlayerDiplomacyGoal extends ActiveTargetGoal<PlayerEntity> {
    NpcEntity mob;
    public TargetPlayerDiplomacyGoal(NpcEntity mob) {
        super(mob, PlayerEntity.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        if (mob.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        } else {
            if(mob.getTarget() instanceof PlayerEntity playerEntity && !playerEntity.isCreative() && !playerEntity.isSpectator()) {
                try {
                    /*
                    FactionOld currentFaction = FactionService.getFactionById(mob.getWorld(), mob.getFactionKey());
                    if(currentFaction == null)
                        return true;
                    FactionOld playerFaction = PlayerUtil.fetchFaction(playerEntity);
                    // [TODO] handle hostility
                    //if(playerFaction != null && !currentFaction.isHostileToward(playerFaction.getId())) return false;

                     */
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.canStart();
        }
    }
}
