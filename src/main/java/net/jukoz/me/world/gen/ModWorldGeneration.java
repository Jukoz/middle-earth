package net.jukoz.me.world.gen;

import net.jukoz.me.world.biomes.caves.ModCaveBiomes;

public class ModWorldGeneration {
    public static void generateModWorldGen() {
        ModCaveBiomes.init();
        ModTreeGeneration.generateTrees();
    }
}
