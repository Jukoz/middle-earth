package net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime;

import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;

public class MeleeCombatArchetypeRuntimeData extends CombatArchetypeRuntimeData {
    public int lastCriticalDamageTick;

    public MeleeCombatArchetypeRuntimeData(MeleeCombatArchetypeData meleeCombatArchetypeData) {
        super(meleeCombatArchetypeData);
        lastCriticalDamageTick = 0;
    }

    @Override
    public void tick(NpcEntity npcEntity, World world){
        super.tick(npcEntity, world);
    }

    @Override
    public MeleeCombatArchetypeData getCombatArchetypeData() {
        return (MeleeCombatArchetypeData) combatArchetypeData;
    }
}
