package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.features.boulder.BoulderPlacedFeatures;
import net.jukoz.me.world.features.misc.ModMiscPlacedFeatures;
import net.jukoz.me.world.features.ores.OrePlacedFeatures;
import net.jukoz.me.world.features.tree.ModTreePlacedFeatures;
import net.jukoz.me.world.features.vegetation.ModVegetationPlacedFeatures;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.ArrayList;
import java.util.List;

public class ModBiomeFeatures {

    public static void addDisks(ArrayList<RegistryKey<PlacedFeature>> ores) {
        ores.add(MiscPlacedFeatures.DISK_SAND);
        ores.add(MiscPlacedFeatures.DISK_CLAY);
        ores.add(MiscPlacedFeatures.DISK_GRAVEL);
    }

    public static void addRiverSand(ArrayList<RegistryKey<PlacedFeature>> ores) {
        ores.add(OrePlacedFeatures.RIVER_SAND);
    }
    public static void addRiverDisks(ArrayList<RegistryKey<PlacedFeature>> ores) {
        ores.add(OrePlacedFeatures.RIVER_SAND);
        ores.add(OrePlacedFeatures.DISK_SAND);
        ores.add(MiscPlacedFeatures.DISK_CLAY);
        ores.add(MiscPlacedFeatures.DISK_GRAVEL);
    }

    // region TREES
    public static void addAcaciaTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ACACIA_PLACED_TREE_KEY);
    }
    public static void addCommonBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_BEECH_PLACED_TREE_KEY);
    }
    public static void addBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BEECH_PLACED_TREE_KEY);
    }
    public static void addRareBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_BEECH_PLACED_TREE_KEY);
    }
    public static void addVeryRareBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_BEECH_PLACED_TREE_KEY);
    }
    public static void addBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BIRCH_PLACED_TREE_KEY);
    }
    public static void addBirchAndOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BIRCH_AND_OAK_PLACED_TREE_KEY);
    }
    public static void addSparseBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addRareBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addVeryRareBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addCherryBlossomTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_CHERRY_BLOSSOM_PLACED_TREE_KEY);
    }
    public static void addChestnutTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.CHESTNUT_PLACED_TREE_KEY);
    }
    public static void addMegaBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_BIRCH_PLACED_TREE_KEY);
    }
    public static void addMegaBirchCommonTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_BIRCH_PLACED_COMMON_TREE_KEY);
    }
    public static void addDarkOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addCommonDarkOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaDarkOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addRareMegaDarkOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_MEGA_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaDarkOakCommonTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_DARK_OAK_PLACED_COMMON_TREE_KEY);
    }
    public static void addHollyTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.HOLLY_PLACED_TREE_KEY);
    }
    public static void addCommonLarchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_LARCH_PLACED_TREE_KEY);
    }
    public static void addLarchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.LARCH_PLACED_TREE_KEY);
    }
    public static void addSparseLarchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_LARCH_PLACED_TREE_KEY);
    }
    public static void addRareLarchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_LARCH_PLACED_TREE_KEY);
    }

    public static void addLebethronTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addCommonLebethronTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.COMMON_WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addRareLebethronTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.RARE_WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addVeryRareLebethronTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_BLACK_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addMallornTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MALLORN_PLACED_TREE_KEY);
    }
    public static void addSmallMallornTress(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SMALL_MALLORN_PLACED_TREE_KEY);
    }
    public static void addMegaMallornTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_MALLORN_PLACED_TREE_KEY);
    }
    public static void addMallornBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MALLORN_BUSH_PLACED_TREE_KEY);
    }
    public static void addMallornFloweringBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MALLORN_FLOWERING_BUSH_PLACED_TREE_KEY);
    }
    public static void addMapleTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addMapleTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_MAPLE_PLACED_TREE_KEY);
    }
    public static void addYellowMapleTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
    }
    public static void addOrangeMapleTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
    }
    public static void addRedMapleTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addScarceMapleTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addSmallMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SMALL_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addMegaMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_MIRKWOOD_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SPARSE_MIRKWOOD_PLACED_TREE_KEY);
    }

    public static void addCommonOakBush(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_BUSH_COMMON_PLACED_TREE_KEY);
    }
    public static void addOakBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_BUSH_PLACED_TREE_KEY);
    }
    public static void addCommonOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_OAK_PLACED_TREE_KEY);
    }
    public static void addFrequentOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ABUNDANT_OAK_PLACED_TREE_KEY);
    }
    public static void addRareOakBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_BUSH_RARE_PLACED_TREE_KEY);
    }
    public static void addOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_PLACED_TREE_KEY);
    }
    public static void addRareSmallSwampOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_SMALL_SWAMP_OAK_PLACED_TREE_KEY);
    }
    public static void addRareOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_OAK_PLACED_TREE_KEY);
    }
    public static void addOakVinesTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_VINES_PLACED_TREE_KEY);
    }
    public static void addMegaOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaOakCommonTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_OAK_COMMON_PLACED_TREE_KEY);
    }
    public static void addRareMegaOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_MEGA_OAK_PLACED_TREE_KEY);
    }
    public static void addVeryRareMegaOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_MEGA_OAK_PLACED_TREE_KEY);
    }

    public static void addPalmTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.PALM_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.WHITE_PALM_PLACED_TREE_KEY);
    }
    public static void addWhitePalmTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.UNCOMMON_WHITE_PALM_PLACED_TREE_KEY);
    }

    public static void addAbundantPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ABUNDANT_PINE_PLACED_TREE_KEY);
    }
    public static void addCommonPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_PINE_PLACED_TREE_KEY);
    }
    public static void addPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.PINE_PLACED_TREE_KEY);
    }
    public static void addSparsePineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_PINE_PLACED_TREE_KEY);
    }
    public static void addDeadPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DEAD_PINE_PLACED_TREE_KEY);
    }
    public static void addDryPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DRY_PINE_PLACED_TREE_KEY);
    }
    public static void addDryPineBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DRY_PINE_BUSH_PLACED_TREE_KEY);
    }

    public static void addCommonBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addScarceBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addDeadBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DEAD_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addCommonDeadBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_DEAD_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addAbundantDeadBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ABUNDANT_DEAD_BLACK_PINE_PLACED_TREE_KEY);
    }

    public static void addScorchedTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCORCHED_TREE_PLACED_TREE_KEY);
    }
    public static void addAbundantScorchedTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ABUNDANT_SCORCHED_TREE_PLACED_TREE_KEY);
    }

    public static void addAbundantSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.FOOTHILLS_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addFrequentSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.FREQUENT_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPRUCE_PLACED_TREE_KEY);
    }
    public static void addScarceSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addRareSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonSpruceBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addSpruceBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addVeryRareSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addWillowTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.WILLOW_PLACED_TREE_KEY);
    }
    public static void addCommonWillowTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_WILLOW_PLACED_TREE_KEY);
    }
    // endregion TREES

    // region BOULDERS

    public static void addMirkwoodRoots(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.MIRKWOOD_ROOTS_BOULDER);
    }
    public static void addAndesiteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_ANDESITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_ANDESITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_ANDESITE);
    }
    public static void addAshenStoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_ASHEN_STONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_ASHEN_STONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_ASHEN_STONE);
    }
    public static void addBasaltBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_BASALT);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_BASALT);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_BASALT);
    }
    public static void addBlueTuffBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_BLUE_TUFF);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_BLUE_TUFF);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_BLUE_TUFF);
    }
    public static void addCalciteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_CALCITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_CALCITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_CALCITE);
    }
    public static void addDioriteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_DIORITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_DIORITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_DIORITE);
    }
    public static void addDolomiteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_DOLOMITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_DOLOMITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_DOLOMITE);
    }
    public static void addSmoothDolomiteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_SMOOTH_DOLOMITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_SMOOTH_DOLOMITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_SMOOTH_DOLOMITE);
    }
    public static void addGalonnBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_GALONN);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_GALONN);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_GALONN);
    }
    public static void addGneissBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_GNEISS);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_GNEISS);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_GNEISS);
    }
    public static void addGraniteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_GRANITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_GRANITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_GRANITE);
    }
    public static void addHematiteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_HEMATITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_HEMATITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_HEMATITE);
    }
    public static void addIronStoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_IRONSTONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_IRONSTONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_IRONSTONE);
    }
    public static void addLimestoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_LIMESTONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_LIMESTONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_LIMESTONE);
    }
    public static void addMossyBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_MOSSY_STONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_MOSSY_STONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_MOSSY_STONE);
    }
    public static void addSandStoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_SANDSTONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_SANDSTONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_SANDSTONE);
    }
    public static void addStoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_STONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_STONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_STONE);
    }

    // endregion

    // region FIELDS
    public static void addHeatherField(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FIELD_HEATHER);
    // endregion
    }
    public static void addMirkwoodVines(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.MIRKWOOD_VINES);
    }

    // region GROWTH
    public static void addAzaleaGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.AZALEA_GROWTH);
    }
    public static void addIvyGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.IVY_GROWTH);
    }
    public static void addFrozenGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FROZEN_GROWTH);
    }
    public static void addLilacFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.LILAC_FLOWER_GROWTH);
    }
    public static void addRedFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.RED_FLOWER_GROWTH);
    }
    public static void addYellowFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.YELLOW_FLOWER_GROWTH);
    }
    public static void addPinkFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PINK_FLOWER_GROWTH);
    }
    public static void addWhiteFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.WHITE_FLOWER_GROWTH);
    }

    // endregion

    // region FOLIAGE
    public static void addWaterDelta(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.WATER_DELTA);
    }
    public static void addAbundantWaterDelta(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.ABUNDANT_WATER_DELTA);
    }

    public static void addAlliumFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_ALLIUM);
    }
    public static void addAzureBluetFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_AZURE_BLUET);
    }
    public static void addRareAzureBluetFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_AZURE_BLUET_RARE);
    }
    public static void addBlueOrchidFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_BLUE_ORCHID);
    }
    public static void addCornflower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_CORNFLOWER);
    }
    public static void addCornflowerCommon(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_CORNFLOWER_COMMON);
    }
    public static void addFlowerGreenJewel(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_GREEN_JEWEL);
    }
    public static void addLilacFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_LILAC);
    }
    public static void addFlowerDorwinion(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_DORWINION);
    }
    public static void addLebenninFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_LEBENNIN);
    }
    public static void addLossarnachFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_LOSSARNACH);
    }
    public static void addLossarnachFlowersCommon(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_LOSSARNACH_COMMON);
    }
    public static void addFlowerMeadow(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_MEADOW);
    }
    public static void addMallos(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_MALLOS);
    }
    public static void addPoppyFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_POPPY);
    }
    public static void addElanor(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_ELANOR);
    }
    public static void addRoseBush(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_ROSE_BUSH);
    }

    public static void addLightBlueFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_LIGHT_BLUE);
    }
    public static void addMagentaFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_MAGENTA);
    }
    public static void addOrangeFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_ORANGE);
    }
    public static void addPinkFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_PINK);
    }
    public static void addPurpleFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_PURPLE);
    }
    public static void addRedFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_RED);
    }
    public static void addWhiteFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_WHITE);
    }
    public static void addYellowFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_YELLOW);
    }

    public static void addBasaltPile(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BASALT);
    }
    public static void addBasaltPileRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BASALT_RARE);
    }
    public static void addBlackStonePile(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BLACKSTONE);
    }
    public static void addBracken(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BRACKEN);
    }
    public static void addPumicePileRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_PUMICE);
    }
    public static void addPumicePileSparse(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_PUMICE_SPARSE);
    }
    public static void addPumiceColumn(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PUMICE_COLUMN);
    }
    public static void addPumiceColumnRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PUMICE_COLUMN_RARE);
    }
    public static void addPumiceColumnLarge(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PUMICE_COLUMN_LARGE);
    }
    public static void addBeachGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BEACH_GRASS);
    }
    public static void addHaradFoliage(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BROWN_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GREEN_SHRUB);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TAN_SHRUB);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_YELLOW_FLOWER);
    }
    public static void addBulrushAndCattail(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SHORT_BULRUSH);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TALL_BULRUSH);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SHORT_CATTAIL);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TALL_CATTAIL);
    }
    public static void addCoastalFoliage(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COASTAL_PANIC_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_HOROKAKA);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GIANT_HOROKAKA);
    }
    public static void addCobwebs(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COBWEB);
    }
    public static void addCorruptedMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_CORRUPTED_MOSS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_CORRUPTED_MOSS_CARPET);
        vegetation.add(OrePlacedFeatures.CORRUPTED_MOSS_DISK);
    }
    public static void addDeadRushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DEAD_RUSHES);
    }
    public static void addDryGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DRY_GRASS);
    }
    public static void addDuckweed(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DUCKWEED);
    }
    public static void addDyingGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DYING_GRASS);
    }
    public static void addFallenLeaves(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FALLEN_LEAVES);
    }
    public static void addFallenMallornLeaves(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FALLEN_MALLORN_LEAVES);
    }
    public static void addFallenMirkwoodLeaves(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FALLEN_MIRKWOOD_LEAVES);
    }
    public static void addFalseOatgrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FALSE_OATGRASS);
    }
    public static void addFloatingIce(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FLOATING_ICE);
    }
    public static void addForestMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FOREST_MOSS);
    }
    public static void addForestBlockMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FOREST_MOSS_CARPET);
        vegetation.add(OrePlacedFeatures.FOREST_MOSS_DISK);
    }
    public static void addFrozenGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FROZEN_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FROZEN_TUFT);
    }
    public static void addFrozenShrub(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FROZEN_SHRUB);
    }
    public static void addRareForestMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_FOREST_MOSS);
    }
    public static void addGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GRASS);
    }
    public static void addGreenShrub(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GREEN_SHRUB);
    }
    public static void addGrimGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GRIM_GRASS);
    }
    public static void addCommonHeath(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_HEATH);
    }
    public static void addHeath(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_HEATH);
    }
    public static void addHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_HEATHER);
    }
    public static void addCommonHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_HEATHER);
    }
    public static void addRareHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_HEATHER);
    }
    public static void addDeadHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DEAD_HEATHER);
    }
    public static void addHorokaka(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_HOROKAKA);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GIANT_HOROKAKA);
    }
    public static void addRedHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RED_HEATHER);
    }
    public static void addScorchedGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SCORCHED_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SCORCHED_TUFT);
    }
    public static void addScorchedShrub(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SCORCHED_SHRUB);
    }
    public static void addCommonScorchedGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_SCORCHED_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_SCORCHED_TUFT);
    }
    public static void addCommonScorchedShrub(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_SCORCHED_SHRUB);
    }
    public static void addSedum(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SEDUM);
    }
    public static void addSedumYellow(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SEDUM_YELLOW);
    }
    public static void addShriveledShrubs(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SHRIVELED_SHRUB);
    }
    public static void addStrawberries(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_STRAWBERRY_BUSH);
    }
    public static void addTallGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TALL_GRASS);
    }
    public static void addCommonTallGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_TALL_GRASS);
    }
    public static void addToughBerries(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TOUGH_BERRY_BUSH);
    }
    public static void addCommonToughBerries(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_TOUGH_BERRY_BUSH);
    }
    public static void addToughBerriesRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TOUGH_BERRY_BUSH_RARE);
    }
    public static void addTuftGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TUFT_GRASS);
    }
    public static void addLilyPads(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_LILY_PADS);
    }
    public static void addSmallLilyPads(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SMALL_LILY_PADS);
    }
    public static void addSmallFloweringLilyPads(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SMALL_FLOWERING_LILY_PADS);
    }
    public static void addMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MOSS);
    }
    public static void addMossCarpet(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MOSS_CARPET);
    }
    public static void addReedsFoliage(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_REEDS);
    }
    public static void addStickySnow(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_STICKY_SNOW);
    }
    public static void addSpiderEggs(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SPIDER_EGGS);
    }
    public static void addWheatGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WHEAT_GRASS);
    }
    public static void addSparseWheatGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SPARSE_WHEAT_GRASS);
    }
    public static void addCommonWheatGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_WHEAT_GRASS);
    }
    public static void addWildGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_GRASS);
    }
    public static void addWilderGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILDER_GRASS);
    }
    public static void addRareWilderGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_WILDER_GRASS);
    }
    // endregion

    // region MUSHROOMS
    public static void addBrownBolete(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BROWN_BOLETE);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BROWN_BOLETE_TILLER);
    }
    public static void addMorsel(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MORSEL);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MORSEL_TILLER);
    }
    public static void addWhiteMushroom(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WHITE_MUSHROOM);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WHITE_MUSHROOM_TILLER);
    }
    public static void addRareMorsel(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_MORSEL);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_MORSEL_TILLER);
    }
    public static void addRareWhiteMushroom(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_WHITE_MUSHROOM);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_WHITE_MUSHROOM_TILLER);
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

    // region ORES
    public static void addAndesiteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ANDESITE_ORE);
    }
    public static void addAshBlockOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASH_BLOCK_ORE);
    }
    public static void addAshenDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_DIRT_ORE);
    }
    public static void addAshenStoneDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_STONE_DIRT_ORE);
    }
    public static void addAshenStoneDirtCommonOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_STONE_DIRT_COMMON_ORE);
    }
    public static void addAshenGravelOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_GRAVEL);
    }
    public static void addAshenSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_SAND);
    }
    public static void addAshenGravelDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_GRAVEL_DIRT);
    }
    public static void addAshenGravelSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_GRAVEL_SAND);
    }
    public static void addAshenStoneGravelOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_STONE_GRAVEL);
    }
    public static void addAshenStoneSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_STONE_SAND);
    }
    public static void addBasaltOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.BASALT_ORE);
    }
    public static void addSmoothBasaltOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SMOOTH_BASALT_ORE);
    }
    public static void addBlackSand(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.BLACK_SAND_ORE);
    }
    public static void addBlueTuff(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.BLUE_TUFF_ORE);
    }
    public static void addCalciteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.CALCITE_ORE);
    }
    public static void addRareCalciteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.RARE_CALCITE_ORE);
    }
    public static void addCoarseDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COARSE_DIRT_ORE);
    }
    public static void addDioriteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DIORITE_ORE);
    }
    public static void addDirtToGrassOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DIRT_TO_GRASS_ORE);
    }
    public static void addDirtyRootsOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DIRTY_ROOTS_ORE);
    }
    public static void addDolomiteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DOLOMITE_ORE);
    }
    public static void addDripstoneOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DRIPSTONE_ORE);
    }
    public static void addDryDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DRY_DIRT_ORE);
    }
    public static void addFrozenStone(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.FROZEN_STONE_ORE);
    }
    public static void addGraniteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRANITE_ORE);
    }
    public static void addGravelOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRAVEL_ORE);
    }
    public static void addStoneGrassOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.STONE_GRASS_ORE);
    }
    public static void addStoneGrassAbundantOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.STONE_GRASS_ABUNDANT_ORE);
    }
    public static void addLimestoneOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.LIMESTONE_ORE);
    }
    public static void addLorienPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.LORIEN_PODZOL_ORE);
    }
    public static void addMireOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.MIRE_ORE);
    }
    public static void addAbundantMudOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ABUNDANT_MUD_ORE);
    }
    public static void addMudOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.MUD_ORE);
    }
    public static void addPackedMudOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.PACKED_MUD_ORE);
    }
    public static void addPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.PODZOL_ORE);
    }
    public static void addAbundantPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ABUNDANT_PODZOL_ORE);
    }
    public static void addPowderSnowOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.POWDER_SNOW_ORE);
    }
    public static void addOldPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.OLD_PODZOL_ORE);
    }
    public static void addStoneOldPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.STONE_OLD_PODZOL_ORE);
    }
    public static void addSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SAND_ORE);
    }
    public static void addSnowOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SNOW_ORE);
    }
    public static void addSoulSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SOUL_SAND_ORE);
    }
    public static void addCalciteStoneOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.CALCITE_STONE_ORE);
    }
    public static void addGrassToStoneOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRASS_TO_STONE_ORE);
    }
    public static void addGrassToGraniteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRASS_TO_GRANITE_ORE);
    }
    public static void addTerracottaOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.TERRACOTTA_ORE);
    }
    public static void addTuffOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.TUFF_ORE);
    }
    public static void addAbundantTuffOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ABUNDANT_TUFF_ORE);
    }
    public static void addTurfOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.TURF_ORE);
    }
    public static void addCommonTurfOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COMMON_TURF_ORE);
    }
    public static void addWhiteSand(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.WHITE_SAND_ORE);
    }
    // endregion

    // region MISC
    public static void addLavaMagmaLake(GenerationSettings.LookupBackedBuilder generationSettings) {
        generationSettings.feature(GenerationStep.Feature.LAKES, ModMiscPlacedFeatures.LAVA_MAGMA_POOL);
    }
    // endregion
}
