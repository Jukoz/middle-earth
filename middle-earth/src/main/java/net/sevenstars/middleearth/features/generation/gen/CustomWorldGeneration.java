package net.sevenstars.middleearth.features.generation.gen;

import net.sevenstars.middleearth.features.generation.biomes.caves.CaveBiomesME;

public class CustomWorldGeneration {

    public static void generateModWorldGen() {
        CaveBiomesME.init();
        FeatureRegistryME.init();
        TreeGenerationME.generateTrees();
    }

}
