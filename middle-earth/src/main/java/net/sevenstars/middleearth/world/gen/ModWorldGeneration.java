package net.sevenstars.middleearth.world.gen;

import net.sevenstars.middleearth.world.biomes.caves.ModCaveBiomes;

public class ModWorldGeneration {

    public static void generateModWorldGen() {
        ModCaveBiomes.init();
        ModFeatures.init();
        ModTreeGeneration.generateTrees();
    }

}
