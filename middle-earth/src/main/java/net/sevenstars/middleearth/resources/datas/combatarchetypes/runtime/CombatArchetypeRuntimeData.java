package net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.CombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;
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

    public NbtCompound getNbt() {
        NbtCompound nbt = new NbtCompound();

        nbt.put("data", combatArchetypeData.getNbt());

        return nbt;
    }

    public CombatArchetype getArchetype() {
        return this.combatArchetypeData.getArchetype();
    }

    public boolean needToGetCloser(){
        return true;
    }

    public boolean needToFlee(){
        return false;
    }

    public int getOptimalDistance(){
        return 1;
    }

    public void attack(){
    }

    public void hurt(){
    }
}
