package net.sevenstars.middleearth.resources.datas.combatarchetypes;

import net.minecraft.nbt.NbtCompound;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

public abstract class CombatArchetypeData {
    protected CombatArchetype combatArchetype;

    private final float fleeMovementSpeedModifier;
    private final float seekTargetMovementSpeedModifier;

    public CombatArchetypeData(float fleeMovementSpeedModifier,  float seekTargetMovementSpeedModifier) {
        setArchetype(getCombatArchetype());
        this.fleeMovementSpeedModifier = fleeMovementSpeedModifier;
        this.seekTargetMovementSpeedModifier = seekTargetMovementSpeedModifier;
    }

    public CombatArchetypeData(NbtCompound data) {
        setArchetype(getCombatArchetype());

        this.fleeMovementSpeedModifier = data.getFloat("flee_movement_speed_modifier", 1f);
        this.seekTargetMovementSpeedModifier = data.getFloat("seek_target_movement_speed_modifier", 1f);
    }

    public NbtCompound getNbt(){
        NbtCompound nbt = new NbtCompound();
        if(this.combatArchetype == null)
            this.combatArchetype = CombatArchetype.MELEE;

        nbt.putString("type", combatArchetype.name());


        NbtCompound dataNbt =  getDataNbt();

        nbt.put("data", dataNbt);

        return nbt;
    }

    protected NbtCompound getDataNbt() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putFloat("flee_movement_speed_modifier", fleeMovementSpeedModifier);
        nbtCompound.putFloat("seek_target_movement_speed_modifier", seekTargetMovementSpeedModifier);

        return nbtCompound;
    }

    protected CombatArchetype getCombatArchetype(){
        return CombatArchetype.MELEE;
    };

    public CombatArchetype getArchetype() {
        return this.combatArchetype;
    }
    protected void setArchetype(CombatArchetype combatArchetype) {
        this.combatArchetype = combatArchetype;
    }

    public float getFleeSpeedModifier() {
        return this.fleeMovementSpeedModifier;
    }

    public float getSeekSpeedModifier() {
        return this.seekTargetMovementSpeedModifier;
    }
}
