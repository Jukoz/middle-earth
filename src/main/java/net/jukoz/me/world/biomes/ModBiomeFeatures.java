package net.jukoz.me.world.biomes;

import net.jukoz.me.world.features.tree.ModTreePlacedFeatures;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModBiomeFeatures {

    public static void addBirchTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.BIRCH_PLACED_TREE_KEY);
    }
    public static void addMegaBirchTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_BIRCH_PLACED_TREE_KEY);
    }
    public static void addLarchTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.LARCH_PLACED_TREE_KEY);
    }
    public static void addLebethronTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.BLACK_LEBETHRON_PLACED_TREE_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addMapleTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MAPLE_PLACED_TREE_KEY);
    }

    public static void addMirkwoodTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MIRKWOOD_PLACED_TREE_KEY);
    }

    public static void addMegaMirkwoodTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_MIRKWOOD_PLACED_TREE_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.SPARSE_MIRKWOOD_PLACED_TREE_KEY);
    }
}
