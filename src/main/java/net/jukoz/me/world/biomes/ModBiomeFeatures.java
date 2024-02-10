package net.jukoz.me.world.biomes;

import net.jukoz.me.world.features.boulder.BoulderPlacedFeatures;
import net.jukoz.me.world.features.tree.ModTreePlacedFeatures;
import net.jukoz.me.world.features.vegetation.ModVegetationPlacedFeatures;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.List;

public class ModBiomeFeatures {

    // region TREES
    public static void addBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BIRCH_PLACED_TREE_KEY);
    }
    public static void addSparseBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addRareBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addMegaBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_BIRCH_PLACED_TREE_KEY);
    }
    public static void addMegaBirchCommonTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_BIRCH_PLACED_COMMON_TREE_KEY);
    }
    public static void addBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BEECH_PLACED_TREE_KEY);
    }
    public static void addRareBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_BEECH_PLACED_TREE_KEY);
    }
    public static void addMegaDarkOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaDarkOakCommonTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_DARK_OAK_PLACED_COMMON_TREE_KEY);
    }
    public static void addLarchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.LARCH_PLACED_TREE_KEY);
    }
    public static void addLebethronTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addMallornTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MALLORN_PLACED_TREE_KEY);
    }
    public static void addMegaMallornTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_MALLORN_PLACED_TREE_KEY);
    }
    public static void addMapleTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MAPLE_PLACED_TREE_KEY);
    }

    public static void addMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MIRKWOOD_PLACED_TREE_KEY);
    }

    public static void addMegaOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaOakCommonTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_OAK_PLACED_TREE_COMMON_KEY);
    }
    public static void addRareMegaOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_MEGA_OAK_PLACED_TREE_KEY);
    }

    public static void addMegaMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_MIRKWOOD_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SPARSE_MIRKWOOD_PLACED_TREE_KEY);
    }

    public static void addPalmTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.PALM_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.WHITE_PALM_PLACED_TREE_KEY);
    }

    public static void addPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.PINE_PLACED_TREE_KEY);
    }
    public static void addDeadPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DEAD_PINE_PLACED_TREE_KEY);
    }

    public static void addAcaciaTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ACACIA_PLACED_TREE_KEY);
    }

    public static void addSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPRUCE_PLACED_TREE_KEY);
    }
    public static void addSparsePineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_PINE_PLACED_TREE_KEY);
    }
    public static void addScarceSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addSparseSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addWillowTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.WILLOW_PLACED_TREE_KEY);
    }
    // endregion TREES

    // region BOULDERS
    public static void addAndesiteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.ANDESITE_BOULDER);
    }
    public static void addDioriteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.DIORITE_BOULDER);
    }
    public static void addGraniteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.GRANITE_BOULDER);
    }
    public static void addStoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.STONE_BOULDER);
    }
    public static void addMossyBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.MOSSY_BOULDER);
    }
    // endregion

    // region FOLIAGE
    public static void addMallos(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MALLOS);
    }
    public static void addStrawberries(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_STRAWBERRY_BUSH);
    }
    public static void addToughBerries(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TOUGH_BERRY_BUSH);
    }
    public static void addToughBerriesRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TOUGH_BERRY_BUSH_RARE);
    }
    public static void addMirkwoodFoliage(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MIRKWOOD);
    }
    public static void addReedsFoliage(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_REEDS);
    }
    // endregion

    // region WILD CROPS
    public static void addWildBeetroot(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_BEETROOT);
    }
    public static void addWildBellPepper(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_BELL_PEPPER);
    }
    public static void addWildCarrot(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_CARROT);
    }
    public static void addWildCucumber(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_CUCUMBER);
    }
    public static void addWildFlax(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_FLAX);
    }
    public static void addWildGarlic(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_GARLIC);
    }
    public static void addWildLeek(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_LEEK);
    }
    public static void addWildLettuce(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_LETTUCE);
    }
    public static void addWildOnion(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_ONION);
    }
    public static void addWildPipeweed(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_PIPEWEED);
    }
    public static void addWildPotato(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_POTATO);
    }
    public static void addWildTomato(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_TOMATO);
    }
    // endregion
}
