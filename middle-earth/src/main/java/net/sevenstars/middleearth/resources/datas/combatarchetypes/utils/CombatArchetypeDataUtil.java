package net.sevenstars.middleearth.resources.datas.combatarchetypes.utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.CombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.RangedCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

public class CombatArchetypeDataUtil {
    public static CombatArchetypeData create(CompoundTag combatArchetypeData) {
        if(!combatArchetypeData.contains("type", Tag.TAG_STRING))
            return null;
        CombatArchetype combatArchetype = CombatArchetype.valueOf(combatArchetypeData.getString("type"));

        CompoundTag data = combatArchetypeData.getCompound("data");
        return switch (combatArchetype) {
            case MELEE -> new MeleeCombatArchetypeData(data);
            case RANGED -> new RangedCombatArchetypeData(data);
            default -> null;
        };
    }
}
