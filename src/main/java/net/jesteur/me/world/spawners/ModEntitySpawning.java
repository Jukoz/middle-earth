package net.jesteur.me.world.spawners;

import net.jesteur.me.entity.ModEntities;
import net.jesteur.me.entity.hobbits.HobbitEntity;
import net.jesteur.me.world.biomes.MEBiomeKeys;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ModEntitySpawning {
    private static HashMap<RegistryKey<Biome>, List<EntitySpawningSettings>> spawns = new HashMap<>();

    public static void addSpawns() {
        spawns.put(MEBiomeKeys.OLD_RHUDAUR, List.of(new EntitySpawningSettings(ModEntities.CAVE_TROLL, 1, 1)));
        spawns.put(MEBiomeKeys.SHIRE, List.of(new EntitySpawningSettings(ModEntities.HOBBIT, 2, 6)));
        spawns.put(MEBiomeKeys.RIVENDELL, List.of(new EntitySpawningSettings(ModEntities.GALADHRIM_ELF, 2, 5)));
        spawns.put(MEBiomeKeys.LINDON, List.of(new EntitySpawningSettings(ModEntities.GALADHRIM_ELF, 2, 5)));
        spawns.put(MEBiomeKeys.LOTHLORIEN, List.of(new EntitySpawningSettings(ModEntities.GALADHRIM_ELF, 2, 5)));
        spawns.put(MEBiomeKeys.BLUE_MOUNTAINS, List.of(new EntitySpawningSettings(ModEntities.DURIN_FOLK, 2, 5)));
        spawns.put(MEBiomeKeys.IRON_HILLS, List.of(new EntitySpawningSettings(ModEntities.DURIN_FOLK, 2, 5)));
        spawns.put(MEBiomeKeys.GREY_PLAINS, List.of(new EntitySpawningSettings(ModEntities.DURIN_FOLK, 2, 3)));
        spawns.put(MEBiomeKeys.MISTY_MOUNTAINS, List.of(new EntitySpawningSettings(ModEntities.DURIN_FOLK, 2, 5)));
        spawns.put(MEBiomeKeys.MORDOR, List.of(new EntitySpawningSettings(ModEntities.MORDOR_ORC, 2, 5)));
        spawns.put(MEBiomeKeys.NURN, List.of(new EntitySpawningSettings(ModEntities.MORDOR_ORC, 1, 3)));
        spawns.put(MEBiomeKeys.BEACH, List.of(new EntitySpawningSettings(ModEntities.CRAB, 3, 6)));
    }

    public static List<EntitySpawningSettings> getSpawnsAt(RegistryKey<Biome> biomeRegistryKey) {
        if(spawns.containsKey(biomeRegistryKey)) return spawns.get(biomeRegistryKey);
        return null;
    }
}
