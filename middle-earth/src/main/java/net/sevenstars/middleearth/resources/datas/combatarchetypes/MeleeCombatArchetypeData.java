package net.sevenstars.middleearth.resources.datas.combatarchetypes;

import net.minecraft.nbt.NbtCompound;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

public class MeleeCombatArchetypeData extends CombatArchetypeData {
    private final float critialChance; // 1 being always, 0 being never

    public MeleeCombatArchetypeData(float critialChance) {
        super();
        this.critialChance = critialChance;
    }

    public MeleeCombatArchetypeData(NbtCompound data) {
        super();
        critialChance = data.getFloat("critical_chance", 0.2f);
    }

    @Override
    protected CombatArchetype getCombatArchetype(){
        return CombatArchetype.MELEE;
    };

    @Override
    protected NbtCompound getDataNbt() {
        NbtCompound nbt = new NbtCompound();

        nbt.putFloat("critical_chance", this.critialChance);

        return nbt;
    }
}
