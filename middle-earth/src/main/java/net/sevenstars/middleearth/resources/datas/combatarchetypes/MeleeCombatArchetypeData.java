package net.sevenstars.middleearth.resources.datas.combatarchetypes;

import net.minecraft.nbt.NbtCompound;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

public class MeleeCombatArchetypeData extends CombatArchetypeData {
    private final float critialChance; // 1 being always, 0 being never

    public MeleeCombatArchetypeData(float fleeMovementSpeedModifier,  float seekTargetMovementSpeedModifier, float optimalBlockRangeMinimum, float optimalBlockRangeMaximum, float critialChance) {
        super(fleeMovementSpeedModifier, seekTargetMovementSpeedModifier, optimalBlockRangeMinimum, optimalBlockRangeMaximum);
        this.critialChance = critialChance;
    }

    public MeleeCombatArchetypeData(NbtCompound data) {
        super(data);
        critialChance = data.getFloat("critical_chance", 0.2f);
    }

    @Override
    protected CombatArchetype getCombatArchetype(){
        return CombatArchetype.MELEE;
    };

    @Override
    protected NbtCompound getDataNbt() {
        NbtCompound nbt = super.getDataNbt();
        nbt.putFloat("critical_chance", this.critialChance);

        return nbt;
    }
}
