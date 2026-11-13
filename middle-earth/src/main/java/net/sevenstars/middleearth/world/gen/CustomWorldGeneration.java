package net.sevenstars.middleearth.world.gen;

import net.sevenstars.middleearth.world.biomes.caves.CaveBiomesME;

public class CustomWorldGeneration {

    public static void generateModWorldGen() {
        CaveBiomesME.init();
        FeatureRegistryME.init();
        TreeGenerationME.generateTrees();
    }

}
