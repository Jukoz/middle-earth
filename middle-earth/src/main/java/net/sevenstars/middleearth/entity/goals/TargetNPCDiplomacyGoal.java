package net.sevenstars.middleearth.entity.goals;

import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;

import java.util.concurrent.atomic.AtomicBoolean;

public class TargetNPCDiplomacyGoal extends NearestAttackableTargetGoal<NpcEntity> {
    private static final AtomicBoolean DIPLOMACY_ERROR_LOGGED = new AtomicBoolean();
    NpcEntity mob;
    public TargetNPCDiplomacyGoal(NpcEntity mob) {
        super(mob, NpcEntity.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canUse() {
        if (mob.level().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        } else {
            if(mob.getTarget() instanceof NpcEntity npcEntity) {
                try {
                    Faction currentFaction = FactionLookup.getFactionById(mob.level(), mob.getFactionIdentifier());
                    if(currentFaction != null && !currentFaction.isHostileToward(npcEntity.getFactionIdentifier())) return false;
                } catch (Exception e) {
                    if (DIPLOMACY_ERROR_LOGGED.compareAndSet(false, true)) {
                        MiddleEarth.LOGGER.logError("TargetNPCDiplomacyGoal::Failed to evaluate diplomacy", e);
                    }
                }
            }
            return super.canUse();
        }
    }
}
