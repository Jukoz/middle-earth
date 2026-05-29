package net.sevenstars.middleearth.resources.datas.combatarchetypes.utils;

import net.minecraft.nbt.NbtCompound;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.CombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.RangedCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;

public class CombatArchetypeDataUtil {
    public static CombatArchetypeData create(NbtCompound combatArchetypeData) {
        CombatArchetype combatArchetype = CombatArchetype.valueOf(combatArchetypeData.getString("type", CombatArchetype.MELEE.name()));

        NbtCompound data = combatArchetypeData.getCompoundOrEmpty("data");
        return switch (combatArchetype) {
            case MELEE -> new MeleeCombatArchetypeData(data);
            case RANGED -> new RangedCombatArchetypeData(data);
            default -> null;
        };
    }
}
