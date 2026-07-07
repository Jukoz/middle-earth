package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.factions.Faction;
import net.sevenstars.middleearth.resources.datas.factions.FactionLookup;

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
                    Faction currentFaction = FactionLookup.getFactionById(mob.getWorld(), mob.getFactionIdentifier());
                    if(!currentFaction.isHostileToward(npcEntity.getFactionIdentifier())) return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.canStart();
        }
    }
}
