package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class SubBiomes {
    public static HashMap<RegistryKey<Biome>, SubBiome> subBiomesMap;

    public static void loadSubBiomes() {
        subBiomesMap = new HashMap<>();

        subBiomesMap.put(MEBiomeKeys.ANDUIN_VALES, new SubBiome()
                .addSubBiomeData(-1.0f, -0.1f, MEBiomeKeys.ANDUIN_VALES_FOREST));
        subBiomesMap.put(MEBiomeKeys.OLD_ANGMAR, new SubBiome()
                .addSubBiomeData(-1.0f, -0.2f, MEBiomeKeys.OLD_ANGMAR_FOREST)
                .addSubBiomeData(0.22f, 0.27f, MEBiomeKeys.OLD_ANGMAR_COLD_HILL, true)
                .addSubBiomeData(0.27f, 1.01f, MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL, true));
        subBiomesMap.put(MEBiomeKeys.OLD_ARTHEDAIN, new SubBiome()
                .addSubBiomeData(-1.0f, -0.28f, MEBiomeKeys.OLD_ARTHEDAIN_FOREST)
                .addSubBiomeData(0.25f, 1.01f, MEBiomeKeys.OLD_ARTHEDAIN_MEADOW));
    }

    public static boolean isSubBiome(RegistryKey<Biome> biomeRegistryKey) {
        AtomicBoolean containsBiome = new AtomicBoolean(false);
        subBiomesMap.forEach((key, value) -> {
            if(value.containsSubBiome(biomeRegistryKey)) {
                containsBiome.set(true);
            }
        });
        return containsBiome.get();
    }

    public static SubBiome getSubBiome(RegistryKey<Biome> biomeRegistryKey) {
        return subBiomesMap.get(biomeRegistryKey);
    }

    public static SubBiome getSubBiomeFromChild(RegistryKey<Biome> biomeRegistryKey) {
        for(Map.Entry<RegistryKey<Biome>, SubBiome> entry : subBiomesMap.entrySet()) {
            if(entry.getValue().containsSubBiome(biomeRegistryKey)) {
                return entry.getValue();
            }
        }
        return null;
    }
}
