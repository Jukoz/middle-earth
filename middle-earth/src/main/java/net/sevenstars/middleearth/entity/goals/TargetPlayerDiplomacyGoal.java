package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;
import net.sevenstars.middleearth.utils.PlayerUtil;

import java.util.concurrent.atomic.AtomicBoolean;

public class TargetPlayerDiplomacyGoal extends NearestAttackableTargetGoal<Player> {
    private static final AtomicBoolean DIPLOMACY_ERROR_LOGGED = new AtomicBoolean();
    NpcEntity mob;
    public TargetPlayerDiplomacyGoal(NpcEntity mob) {
        super(mob, Player.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (mob.level().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        } else {
            if(mob.getTarget() instanceof Player playerEntity && !playerEntity.isCreative() && !playerEntity.isSpectator()) {
                try {
                    Faction currentFaction = FactionLookup.getFactionById(mob.level(), mob.getFactionIdentifier());
                    if(currentFaction == null)
                        return true;
                    Faction playerFaction = PlayerUtil.fetchFaction(playerEntity);
                    if(playerFaction != null && !currentFaction.isHostileToward(playerFaction.getId())) return false;
                } catch (Exception e) {
                    if (DIPLOMACY_ERROR_LOGGED.compareAndSet(false, true)) {
                        MiddleEarth.LOGGER.logError("TargetPlayerDiplomacyGoal::Failed to evaluate diplomacy", e);
                    }
                }
            }
            return super.canUse();
        }
    }
}
