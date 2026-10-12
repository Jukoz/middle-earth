package net.sevenstars.middleearth.loot;

import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.sevenstars.middleearth.MiddleEarth;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LootTablesME {
    private static final Set<RegistryKey<LootTable>> LOOT_TABLES = new HashSet();
    private static final Set<RegistryKey<LootTable>> LOOT_TABLES_READ_ONLY = Collections.unmodifiableSet(LOOT_TABLES);

    public static final RegistryKey<LootTable> STONE_TROLL_BARTERING_GAMEPLAY = register("gameplay/stone_troll_bartering");


    private static Map<DyeColor, RegistryKey<LootTable>> registerAllDyeColors(String prefix) {
        return Util.mapEnum(DyeColor.class, color -> register(prefix + "/" + color.getId()));
    }

    private static RegistryKey<LootTable> register(String id) {
        return registerLootTable(RegistryKey.of(RegistryKeys.LOOT_TABLE, MiddleEarth.of(id)));
    }

    private static RegistryKey<LootTable> registerLootTable(RegistryKey<LootTable> key) {
        if (LOOT_TABLES.add(key)) {
            return key;
        } else {
            throw new IllegalArgumentException(key.getValue() + " is already a registered built-in loot table");
        }
    }

    public static Set<RegistryKey<LootTable>> getAll() {
        return LOOT_TABLES_READ_ONLY;
    }

    public static void registerModLootTables() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod LootTables for " + MiddleEarth.MOD_ID);
    }
}
