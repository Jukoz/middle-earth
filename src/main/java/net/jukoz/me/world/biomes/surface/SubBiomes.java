package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;

public class SubBiomes {
    public static HashMap<RegistryKey<Biome>, SubBiome> subBiomesMap;

    public static void loadSubBiomes() {
        subBiomesMap = new HashMap<>();

        subBiomesMap.put(MEBiomeKeys.ANDUIN_VALES, new SubBiome()
                .addSubBiomeData(-1.0f, -0.1f, MEBiomeKeys.ANDUIN_VALES_FOREST));
    }
}
