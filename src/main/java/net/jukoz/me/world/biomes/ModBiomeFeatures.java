package net.jukoz.me.world.biomes;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.features.boulder.BoulderPlacedFeatures;
import net.jukoz.me.world.features.tree.ModTreePlacedFeatures;
import net.jukoz.me.world.features.vegetation.ModVegetationPlacedFeatures;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class ModBiomeFeatures {

    // region TREES
    public static void addBeechTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.BEECH_PLACED_TREE_KEY);
    }
    public static void addBirchTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.BIRCH_PLACED_TREE_KEY);
    }
    public static void addBirchSparseTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.SPARSE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addMegaBirchTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_BIRCH_PLACED_TREE_KEY);
    }
    public static void addMegaBirchCommonTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_BIRCH_PLACED_COMMON_TREE_KEY);
    }
    public static void addMegaDarkOakTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaDarkOakCommonTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_DARK_OAK_PLACED_COMMON_TREE_KEY);
    }
    public static void addLarchTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.LARCH_PLACED_TREE_KEY);
    }
    public static void addLebethronTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.BLACK_LEBETHRON_PLACED_TREE_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addMallornTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MALLORN_PLACED_TREE_KEY);
    }
    public static void addMegaMallornTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_MALLORN_PLACED_TREE_KEY);
    }
    public static void addMapleTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MAPLE_PLACED_TREE_KEY);
    }

    public static void addMirkwoodTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MIRKWOOD_PLACED_TREE_KEY);
    }

    public static void addMegaOakTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaOakCommonTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_OAK_PLACED_TREE_COMMON_KEY);
    }

    public static void addMegaMirkwoodTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.MEGA_MIRKWOOD_PLACED_TREE_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.SPARSE_MIRKWOOD_PLACED_TREE_KEY);
    }

    public static void addPalmTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.PALM_PLACED_TREE_KEY);
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.WHITE_PALM_PLACED_TREE_KEY);
    }

    public static void addPineTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.PINE_PLACED_TREE_KEY);
    }
    public static void addDeadPineTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.DEAD_PINE_PLACED_TREE_KEY);
    }

    public static void addAcaciaTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.ACACIA_PLACED_TREE_KEY);
    }

    public static void addSpruceTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.SPRUCE_PLACED_TREE_KEY);
    }
    public static void addSparsePineTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.SPARSE_PINE_PLACED_TREE_KEY);
    }
    public static void addScarceSpruceTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.SCARCE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addSparseSpruceTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.SPARSE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addWillowTrees(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModTreePlacedFeatures.WILLOW_PLACED_TREE_KEY);
    }
    // endregion TREES

    // region BOULDERS
    public static void addAndesiteBoulder(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BoulderPlacedFeatures.ANDESITE_BOULDER);
    }
    public static void addDioriteBoulder(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BoulderPlacedFeatures.DIORITE_BOULDER);
    }
    public static void addGraniteBoulder(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BoulderPlacedFeatures.GRANITE_BOULDER);
    }
    public static void addStoneBoulder(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BoulderPlacedFeatures.STONE_BOULDER);
    }
    public static void addMossyBoulder(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, BoulderPlacedFeatures.MOSSY_BOULDER);
    }
    // endregion

    // region FOLIAGE
    public static void addMallos(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_MALLOS);
    }
    public static void addToughBerries(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_TOUGH_BERRY_BUSH);
    }
    public static void addToughBerriesRare(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_TOUGH_BERRY_BUSH_RARE);
    }
    public static void addMirkwoodFoliage(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_MIRKWOOD);
    }
    public static void addReedsFoliage(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.PATCH_REEDS);
    }
    // enregion


}
