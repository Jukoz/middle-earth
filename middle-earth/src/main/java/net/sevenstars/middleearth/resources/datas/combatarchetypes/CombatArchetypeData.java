package net.sevenstars.middleearth.resources.datas.combatarchetypes;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

public class CombatArchetypeData {
    protected CombatArchetype combatArchetype;

    public CombatArchetypeData() {
        setArchetype(getCombatArchetype());
    }

    public CombatArchetypeData(NbtElement data) {
        setArchetype(getCombatArchetype());
    }

    public NbtCompound getNbt(){
        NbtCompound nbt = new NbtCompound();
        if(this.combatArchetype == null)
            this.combatArchetype = CombatArchetype.MELEE;

        nbt.putString("type", combatArchetype.name());


        nbt.put("data", getDataNbt());

        return nbt;
    }

    protected NbtCompound getDataNbt() {
        return new NbtCompound();
    }

    protected CombatArchetype getCombatArchetype(){
        return CombatArchetype.MELEE;
    };


    protected void setArchetype(CombatArchetype combatArchetype) {
        this.combatArchetype = combatArchetype;
    }

}
