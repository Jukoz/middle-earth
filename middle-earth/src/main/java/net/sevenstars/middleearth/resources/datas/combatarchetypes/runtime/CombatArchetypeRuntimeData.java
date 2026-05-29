package net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime;

import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.CombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.MovementBehavior;

public abstract class CombatArchetypeRuntimeData {
    protected final CombatArchetypeData combatArchetypeData;

    public CombatArchetypeRuntimeData(CombatArchetypeData combatArchetypeData){
        this.combatArchetypeData = combatArchetypeData;
    }

    public void tick(NpcEntity npcEntity, World world) {

    }

    public float getMovementSpeedModifier(MovementBehavior movementBehavior) {
        return switch (movementBehavior) {
            case IDLE -> 1.0f;
            case FLEE -> combatArchetypeData.getFleeSpeedModifier();
            case SEEK -> combatArchetypeData.getSeekSpeedModifier();
        };
    }

    public CombatArchetypeData getCombatArchetypeData() {
        return combatArchetypeData;
    }
}
