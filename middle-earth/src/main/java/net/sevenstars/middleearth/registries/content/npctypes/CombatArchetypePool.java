package net.sevenstars.middleearth.registries.content.npctypes;

import net.sevenstars.middleearth.resources.datas.combatarchetypes.CombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.RangedCombatArchetypeData;

public class CombatArchetypePool {
    public final static CombatArchetypeData DEFAULT =  new MeleeCombatArchetypeData(
            1f,
            1f,
            1f,
            5f,
            0.3f);

    public final static CombatArchetypeData ARCHER = new RangedCombatArchetypeData(
            1.15f,
            1f,
            10f,
            18f,
            0.8f,
            5,
            10,
            100,
            50);

    /// WOODLAND REALMS
    public final static CombatArchetypeData WOODLAND_REALMS_RANGER = new RangedCombatArchetypeData(
            1.2f,
            1f,
            10f,
            18f,
            0.8f,
            5,
            10,
            100,
            50);
}
