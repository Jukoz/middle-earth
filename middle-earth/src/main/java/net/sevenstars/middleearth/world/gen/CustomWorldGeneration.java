package net.sevenstars.middleearth.world.gen;

import net.sevenstars.middleearth.world.biomes.caves.CaveBiomesME;
import net.sevenstars.middleearth.world.gen.structure.RulePoolElement;

public class CustomWorldGeneration {

    public static void generateModWorldGen() {
        CaveBiomesME.init();
        FeatureRegistryME.init();
        RulePoolElement.register();
        TreeGenerationME.generateTrees();
    }

}
