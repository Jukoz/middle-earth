package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.utils.PlayerUtil;

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
                    Faction currentFaction = FactionLookup.getFactionById(mob.getWorld(), mob.getFactionIdentifier());
                    Faction playerFaction = PlayerUtil.fetchFaction(playerEntity);
                    if(playerFaction != null && !currentFaction.isHostileToward(playerFaction.getId())) return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.canStart();
        }
    }
}
