package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.factions.FactionOld;

public class TargetNPCDiplomacyGoal extends ActiveTargetGoal<NpcEntity> {
    NpcEntity mob;
    public TargetNPCDiplomacyGoal(NpcEntity mob) {
        super(mob, NpcEntity.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        if (mob.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        } else {
            if(mob.getTarget() instanceof NpcEntity npcEntity) {
                try {
                    //FactionOld currentFaction = FactionLookup.getFactionById(mob.getWorld(), mob.getFactionKey());
                    // [TODO] handle hostility
                    //if(!currentFaction.isHostileToward(npcEntity.getFactionIdentifier())) return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.canStart();
        }
    }
}
