package net.sevenstars.middleearth.features.generation;

import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedBiomePool;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBiomeData;
import net.sevenstars.middleearth.features.generation.caves.Caves;
import net.sevenstars.middleearth.features.generation.dimension.DimensionRegistryME;
import net.sevenstars.middleearth.features.generation.gen.CustomWorldGeneration;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapGeneration;
import net.sevenstars.middleearth.features.generation.spawners.EntitySpawningME;

public class Generation {
    public static void register() {
        StartInMiddleEarth.register();
        Caves.register();
        EntitySpawningME.addSpawns();

        DimensionRegistryME.register();
        MapBiomeData.loadBiomes();
        BiomeKeyRegistryME.registerModBiomes();
        MapBasedBiomePool.loadBiomes();

        CustomWorldGeneration.generateModWorldGen();

        try {
            new MiddleEarthMapGeneration();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
