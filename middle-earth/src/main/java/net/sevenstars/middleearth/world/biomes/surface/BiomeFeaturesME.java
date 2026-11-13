package net.sevenstars.middleearth.world.biomes.surface;

import net.sevenstars.middleearth.world.features.boulder.BoulderPlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.misc.MiscPlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.ores.OrePlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.tree.TreePlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.vegetation.VegetationPlacedFeatureRegistryME;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.MiscPlacedFeatures;
import net.minecraft.world.gen.feature.PlacedFeature;

import java.util.ArrayList;
import java.util.List;

public class BiomeFeaturesME {

    public static void addDisks(ArrayList<RegistryKey<PlacedFeature>> ores) {
        ores.add(MiscPlacedFeatures.DISK_SAND);
        ores.add(MiscPlacedFeatures.DISK_CLAY);
        ores.add(MiscPlacedFeatures.DISK_GRAVEL);
    }

    public static void addRiverSand(ArrayList<RegistryKey<PlacedFeature>> ores) {
        ores.add(OrePlacedFeatureRegistryME.RIVER_SAND);
    }
    public static void addRiverDisks(ArrayList<RegistryKey<PlacedFeature>> ores) {
        ores.add(OrePlacedFeatureRegistryME.RIVER_SAND);
        ores.add(OrePlacedFeatureRegistryME.DISK_SAND);
        ores.add(MiscPlacedFeatures.DISK_CLAY);
        ores.add(MiscPlacedFeatures.DISK_GRAVEL);
    }

    // region TREES
    public static void addAcaciaTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.ACACIA_PLACED_TREE_KEY);
    }
    public static void addCommonAcaciaTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_ACACIA_PLACED_TREE_KEY);
    }
    public static void addRareAcaciaTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_ACACIA_PLACED_TREE_KEY);
    }
    public static void addCommonBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_BEECH_PLACED_TREE_KEY);
    }
    public static void addBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.BEECH_PLACED_TREE_KEY);
    }
    public static void addRareBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_BEECH_PLACED_TREE_KEY);
    }
    public static void addVeryRareBeechTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_BEECH_PLACED_TREE_KEY);
    }

    public static void addCommonBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_BIRCH_PLACED_TREE_KEY);
    }
    public static void addBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.BIRCH_PLACED_TREE_KEY);
    }
    public static void addBirchAndOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.BIRCH_AND_OAK_PLACED_TREE_KEY);
    }
    public static void addSparseBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SPARSE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addRareBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addVeryRareBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_BIRCH_PLACED_TREE_KEY);
    }

    public static void addCommonAspenTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_ASPEN_PLACED_TREE_KEY);
    }
    public static void addAspenTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.ASPEN_PLACED_TREE_KEY);
    }
    public static void addSparseAspenTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SPARSE_ASPEN_PLACED_TREE_KEY);
    }
    public static void addRareAspenTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_ASPEN_PLACED_TREE_KEY);
    }
    public static void addVeryRareAspenTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_ASPEN_PLACED_TREE_KEY);
    }
    public static void addCherryBlossomTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_CHERRY_BLOSSOM_PLACED_TREE_KEY);
    }
    public static void addChestnutTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.CHESTNUT_PLACED_TREE_KEY);
    }
    public static void addMegaBirchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MEGA_BIRCH_PLACED_TREE_KEY);
    }
    public static void addMegaBirchCommonTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MEGA_BIRCH_PLACED_COMMON_TREE_KEY);
    }
    public static void addDarkOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addCommonDarkOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaDarkOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MEGA_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addRareMegaDarkOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_MEGA_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaDarkOakCommonTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MEGA_DARK_OAK_PLACED_COMMON_TREE_KEY);
    }
    public static void addDeadwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.DEADWOOD_TREE_KEY);
    }
    public static void addCommonFirTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_FIR_PLACED_TREE_KEY);
    }
    public static void addFirTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.FIR_PLACED_TREE_KEY);
    }
    public static void addAbundantFirTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.ABUNDANT_FIR_PLACED_TREE_KEY);
    }
    public static void addRareFirTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_FIR_PLACED_TREE_KEY);
    }
    public static void addVeryRareFirTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_FIR_PLACED_TREE_KEY);
    }
    public static void addHollyTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.HOLLY_PLACED_TREE_KEY);
    }
    public static void addCommonHollyTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_HOLLY_PLACED_TREE_KEY);
    }
    public static void addCommonLarchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_LARCH_PLACED_TREE_KEY);
    }
    public static void addLarchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.LARCH_PLACED_TREE_KEY);
    }
    public static void addSparseLarchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SPARSE_LARCH_PLACED_TREE_KEY);
    }
    public static void addRareLarchTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_LARCH_PLACED_TREE_KEY);
    }

    public static void addLebethronTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addCommonLebethronTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addRareLebethronTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.RARE_WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addVeryRareLebethronTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_BLACK_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addMallornTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MALLORN_PLACED_TREE_KEY);
    }
    public static void addSmallMallornTress(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SMALL_MALLORN_PLACED_TREE_KEY);
    }
    public static void addMegaMallornTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MEGA_MALLORN_PLACED_TREE_KEY);
    }
    public static void addMallornBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MALLORN_BUSH_PLACED_TREE_KEY);
    }
    public static void addMallornFloweringBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MALLORN_FLOWERING_BUSH_PLACED_TREE_KEY);
    }
    public static void addMapleTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addCommonMapleTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addMapleTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SILVER_MAPLE_PLACED_TREE_KEY);
    }
    public static void addGreenMapleTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SILVER_MAPLE_PLACED_TREE_KEY);
    }
    public static void addYellowMapleTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
    }
    public static void addOrangeMapleTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
    }
    public static void addRedMapleTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SILVER_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addScarceMapleTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addSmallMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SMALL_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addSparseMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SPARSE_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addDeadMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.DEAD_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addUncommonMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.UNCOMMON_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addMegaMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MEGA_MIRKWOOD_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.SPARSE_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addRareMegaMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_MEGA_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addVeryRareMegaMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_MEGA_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addDeadMegaMirkwoodTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.DEAD_MEGA_MIRKWOOD_PLACED_TREE_KEY);
    }

    public static void addCommonOakBush(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.OAK_BUSH_COMMON_PLACED_TREE_KEY);
    }
    public static void addOakBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.OAK_BUSH_PLACED_TREE_KEY);
    }
    public static void addCommonOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_OAK_PLACED_TREE_KEY);
    }
    public static void addFrequentOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.ABUNDANT_OAK_PLACED_TREE_KEY);
    }
    public static void addRareOakBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.OAK_BUSH_RARE_PLACED_TREE_KEY);
    }
    public static void addOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.OAK_PLACED_TREE_KEY);
    }
    public static void addBeesOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.BEES_OAK_PLACED_TREE_KEY);
    }
    public static void addRareSmallSwampOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_SMALL_SWAMP_OAK_PLACED_TREE_KEY);
    }
    public static void addRareOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_OAK_PLACED_TREE_KEY);
    }
    public static void addOakVinesTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.OAK_VINES_PLACED_TREE_KEY);
    }
    public static void addMegaOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MEGA_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaOakCommonTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.MEGA_OAK_COMMON_PLACED_TREE_KEY);
    }
    public static void addRareMegaOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_MEGA_OAK_PLACED_TREE_KEY);
    }
    public static void addVeryRareMegaOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_MEGA_OAK_PLACED_TREE_KEY);
    }

    public static void addPalmTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.PALM_PLACED_TREE_KEY);
        vegetation.add(TreePlacedFeatureRegistryME.WHITE_PALM_PLACED_TREE_KEY);
    }
    public static void addWhitePalmTree(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.UNCOMMON_WHITE_PALM_PLACED_TREE_KEY);
    }

    public static void addAbundantPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.ABUNDANT_PINE_PLACED_TREE_KEY);
    }
    public static void addCommonPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_PINE_PLACED_TREE_KEY);
    }
    public static void addPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.PINE_PLACED_TREE_KEY);
    }
    public static void addSparsePineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SPARSE_PINE_PLACED_TREE_KEY);
    }
    public static void addDeadPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.DEAD_PINE_PLACED_TREE_KEY);
    }
    public static void addDryPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.DRY_PINE_PLACED_TREE_KEY);
    }
    public static void addDryPineBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.DRY_PINE_BUSH_PLACED_TREE_KEY);
    }

    public static void addCommonBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addScarceBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addDeadBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.DEAD_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addCommonDeadBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_DEAD_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addAbundantDeadBlackPineTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.ABUNDANT_DEAD_BLACK_PINE_PLACED_TREE_KEY);
    }

    public static void addRottenTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.ROTTEN_TREE_KEY);
    }

    public static void addScorchedTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCORCHED_TREE_PLACED_TREE_KEY);
    }
    public static void addCommonScorchedTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_SCORCHED_TREE_PLACED_TREE_KEY);
    }
    public static void addAbundantScorchedTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.ABUNDANT_SCORCHED_TREE_PLACED_TREE_KEY);
    }

    public static void addAbundantSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.FOOTHILLS_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addFrequentSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.FREQUENT_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SPRUCE_PLACED_TREE_KEY);
    }
    public static void addScarceSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addRareSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonSpruceBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addSpruceBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addVeryRareSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addExtremelyRareSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_SPRUCE_PLACED_TREE_KEY);
    }

    public static void addAbundantWhiteSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.FOOTHILLS_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addFrequentWhiteSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.FREQUENT_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonWhiteSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addWhiteSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addScarceWhiteSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.SCARCE_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addRareWhiteSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.RARE_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonWhiteSpruceBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_WHITE_SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addWhiteSpruceBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.WHITE_SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addVeryRareWhiteSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addExtremelyRareWhiteSpruceTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.VERY_RARE_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    
    public static void addWillowTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.WILLOW_PLACED_TREE_KEY);
    }
    public static void addCommonWillowTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.COMMON_WILLOW_PLACED_TREE_KEY);
    }

    public static void addPaleOakTrees(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(TreePlacedFeatureRegistryME.PALE_OAK_PLACED_TREE_KEY);
    }
    
    // endregion TREES

    // region BOULDERS

    public static void addMirkwoodRoots(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.MIRKWOOD_ROOTS_BOULDER);
    }
    public static void addAndesiteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_ANDESITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_ANDESITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_ANDESITE);
    }
    public static void addAshenStoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_ASHEN_STONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_ASHEN_STONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_ASHEN_STONE);
    }
    public static void addBasaltBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_BASALT);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_BASALT);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_BASALT);
    }
    public static void addBlueTuffBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_BLUE_TUFF);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_BLUE_TUFF);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_BLUE_TUFF);
    }
    public static void addCalciteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_CALCITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_CALCITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_CALCITE);
    }
    public static void addDioriteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_DIORITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_DIORITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_DIORITE);
    }
    public static void addDolomiteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_DOLOMITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_DOLOMITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_DOLOMITE);
    }
    public static void addSmoothDolomiteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_SMOOTH_DOLOMITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_SMOOTH_DOLOMITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_SMOOTH_DOLOMITE);
    }
    public static void addGalonnBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_GALONN);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_GALONN);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_GALONN);
    }
    public static void addGneissBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_GNEISS);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_GNEISS);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_GNEISS);
    }
    public static void addGraniteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_GRANITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_GRANITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_GRANITE);
    }
    public static void addHematiteBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_HEMATITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_HEMATITE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_HEMATITE);
    }
    public static void addIronStoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_IRONSTONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_IRONSTONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_IRONSTONE);
    }
    public static void addLimestoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_LIMESTONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_LIMESTONE);
    }
    public static void addMossyBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_MOSSY_STONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_MOSSY_STONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_MOSSY_STONE);
    }
    public static void addSandStoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_SANDSTONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_SANDSTONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_SANDSTONE);
    }
    public static void addStoneBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_STONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_STONE);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_STONE);
    }
    public static void addSlateBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_SLATE);
    }
    public static void addGabbroBoulder(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatureRegistryME.SMALL_BOULDER_GABBRO);
        vegetation.add(BoulderPlacedFeatureRegistryME.MEDIUM_BOULDER_GABBRO);
        vegetation.add(BoulderPlacedFeatureRegistryME.BIG_BOULDER_GABBRO);
    }

    // endregion

    // region FIELDS
    public static void addHeatherField(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FIELD_HEATHER);
    }
    public static void addDryHeatherField(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FIELD_DRY_HEATHER);
    }
    public static void addLavenderField(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FIELD_LAVENDER);
    }
    public static void addWildWheatField(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FIELD_WILD_WHEAT);
    }


    // endregion
    public static void addMirkwoodVines(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.MIRKWOOD_VINES);
    }
    public static void addWillowVines(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.WILLOW_VINES);
    }

    // region GROWTH
    public static void addAzaleaGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.AZALEA_GROWTH);
    }
    public static void addDryGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.DRY_GROWTH);
    }
    public static void addFrozenGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FROZEN_GROWTH);
    }
    public static void addIvyGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.IVY_GROWTH);
    }
    public static void addGreenGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.GREEN_GROWTH);
    }
    public static void addThornyGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.THORNY_GROWTH);
    }
    public static void addLilacFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.LILAC_FLOWER_GROWTH);
    }
    public static void addRedFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.RED_FLOWER_GROWTH);
    }
    public static void addYellowFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.YELLOW_FLOWER_GROWTH);
    }
    public static void addPinkFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PINK_FLOWER_GROWTH);
    }
    public static void addWhiteFlowerGrowth(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.WHITE_FLOWER_GROWTH);
    }

    // endregion

    // region FOLIAGE
    public static void addWaterDelta(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.WATER_DELTA);
    }
    public static void addAbundantWaterDelta(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.ABUNDANT_WATER_DELTA);
    }

    public static void addAthelas(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_ATHELAS);
    }
    public static void addAlliumFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_ALLIUM);
    }
    public static void addAzureBluetFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_AZURE_BLUET);
    }
    public static void addRareAzureBluetFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_AZURE_BLUET_RARE);
    }
    public static void addBlueGentianFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_BLUE_GENTIAN);
    }
    public static void addBlueOrchidFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_BLUE_ORCHID);
    }
    public static void addCornflower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_CORNFLOWER);
    }
    public static void addCornflowerCommon(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_CORNFLOWER_COMMON);
    }
    public static void addFlowerGreenJewel(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_GREEN_JEWEL);
    }
    public static void addLilacFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_LILAC);
    }
    public static void addFlowerDorwinion(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_DORWINION);
    }
    public static void addLebenninFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_LEBENNIN);
    }
    public static void addLossarnachFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_LOSSARNACH);
    }
    public static void addLossarnachFlowersCommon(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_LOSSARNACH_COMMON);
    }
    public static void addFlowerMeadow(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_MEADOW);
    }
    public static void addMallos(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_MALLOS);
    }
    public static void addNoblewhite(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_NOBLEWHITE);
    }
    public static void addPoppyFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_POPPY);
    }
    public static void addElanor(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_ELANOR);
    }
    public static void addNiphredil(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_NIPHREDIL);
    }
    public static void addSimbelmyne(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_SIMBELMYNE);
    }
    public static void addRoseBush(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_ROSE_BUSH);
    }
    public static void addYellowFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWER_YELLOW);
    }
    public static void addRareYellowFlower(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.RARE_FLOWER_YELLOW);
    }
    public static void addWildFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_FLOWERS);
    }

    public static void addLightBlueFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWERS_LIGHT_BLUE);
    }
    public static void addRareLightBlueFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.RARE_FLOWERS_LIGHT_BLUE);
    }
    public static void addMagentaFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWERS_MAGENTA);
    }
    public static void addOrangeFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWERS_ORANGE);
    }
    public static void addPinkFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWERS_PINK);
    }
    public static void addPurpleFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWERS_PURPLE);
    }
    public static void addRedFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWERS_RED);
    }
    public static void addWhiteFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWERS_WHITE);
    }
    public static void addYellowFlowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FLOWERS_YELLOW);
    }

    public static void addBasaltPile(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BASALT);
    }
    public static void addBasaltPileRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BASALT_RARE);
    }
    public static void addBlackStonePile(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BLACKSTONE);
    }
    public static void addBracken(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BRACKEN);
    }
    public static void addGiantButterbur(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_GIANT_BUTTERBUR);
    }
    public static void addFieldBlueFescue(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FIELD_BLUE_FESCUE);
    }
    public static void addPumicePileRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_PUMICE);
    }
    public static void addPumicePileSparse(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_PUMICE_SPARSE);
    }
    public static void addPumiceColumn(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PUMICE_COLUMN);
    }
    public static void addPumiceColumnRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PUMICE_COLUMN_RARE);
    }
    public static void addPumiceColumnLarge(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PUMICE_COLUMN_LARGE);
    }
    public static void addBamboo(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BAMBOO);
    }
    public static void addBeachGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BEACH_GRASS);
    }
    public static void addCommonBeachGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COMMON_BEACH_GRASS);
    }
    public static void addBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BUSHES);
    }
    public static void addClovers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_CLOVERS);
    }
    public static void addHaradFoliage(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BROWN_GRASS);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_GRIM_GRASS);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_TAN_SHRUB);
    }
    public static void addBulrushAndCattail(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SHORT_BULRUSH);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_TALL_BULRUSH);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SHORT_CATTAIL);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_TALL_CATTAIL);
    }
    public static void addCoastalFoliage(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COASTAL_PANIC_GRASS);
    }
    public static void addCobwebs(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COBWEB);
    }
    public static void addCorruptedMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_CORRUPTED_MOSS);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_CORRUPTED_MOSS_CARPET);
        vegetation.add(OrePlacedFeatureRegistryME.CORRUPTED_MOSS_DISK);
    }
    public static void addDeadRushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_DEAD_RUSHES);
    }
    public static void addDryGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_DRY_GRASS);
    }
    public static void addVeryRareDryGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_VERY_RARE_DRY_GRASS);
    }
    public static void addSmallDryShrub(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SMALL_DRY_SHRUB);
    }
    public static void addDuckweed(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_DUCKWEED);
    }
    public static void addDyingGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_DYING_GRASS);
    }
    public static void addFallenLeaves(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FALLEN_LEAVES);
    }
    public static void addFallenMallornLeaves(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FALLEN_MALLORN_LEAVES);
    }
    public static void addFallenMirkwoodLeaves(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FALLEN_MIRKWOOD_LEAVES);
    }
    public static void addFalseOatgrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FALSE_OATGRASS);
    }
    public static void addFloatingIce(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FLOATING_ICE);
    }
    public static void addForestMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FOREST_MOSS);
    }
    public static void addForestBlockMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FOREST_MOSS_CARPET);
        vegetation.add(OrePlacedFeatureRegistryME.FOREST_MOSS_DISK);
    }
    public static void addFrozenGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FROZEN_GRASS);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FROZEN_TUFT);
    }
    public static void addFrozenShrub(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FROZEN_SHRUB);
    }
    public static void addRareForestMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_FOREST_MOSS);
    }
    public static void addGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_GRASS);
    }
    public static void addGreenShrub(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_GREEN_SHRUB);
    }
    public static void addGrimGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_GRIM_GRASS);
    }
    public static void addCommonHeath(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COMMON_HEATH);
    }
    public static void addSparseHeath(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SPARSE_HEATH);
    }
    public static void addHeath(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_HEATH);
    }
    public static void addHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_HEATHER);
    }
    public static void addCommonHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COMMON_HEATHER);
    }
    public static void addFieldDeadNormalHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FIELD_DEAD_NORMAL_HEATHER);
    }
    public static void addSparseFieldDeadNormalHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.FIELD_SPARSE_DEAD_NORMAL_HEATHER);
    }
    public static void addUncommonBlueLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BLUE_LAVENDER);
    }
    public static void addSparseBlueLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SPARSE_BLUE_LAVENDER);
    }
    public static void addRareBlueLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_BLUE_LAVENDER);
    }
    public static void addVeryRareBlueLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_VERY_RARE_BLUE_LAVENDER);
    }
    public static void addUncommonLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_LAVENDER);
    }
    public static void addSparseLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SPARSE_LAVENDER);
    }
    public static void addRareLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_LAVENDER);
    }
    public static void addVeryRareLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_VERY_RARE_LAVENDER);
    }
    public static void addUncommonWhiteLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WHITE_LAVENDER);
    }
    public static void addSparseWhiteLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SCARCE_WHITE_LAVENDER);
    }
    public static void addRareWhiteLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_WHITE_LAVENDER);
    }
    public static void addVeryRareWhiteLavender(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_VERY_RARE_WHITE_LAVENDER);
    }
    public static void addRareHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_HEATHER);
    }
    public static void addDeadHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_DEAD_HEATHER);
    }
    public static void addDryHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_DRY_HEATHER);
    }
    public static void addRedHeather(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RED_HEATHER);
    }
    public static void addScorchedGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SCORCHED_GRASS);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SCORCHED_TUFT);
    }
    public static void addScorchedShrub(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SCORCHED_SHRUB);
    }
    public static void addCommonScorchedGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COMMON_SCORCHED_GRASS);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COMMON_SCORCHED_TUFT);
    }
    public static void addCommonScorchedShrub(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COMMON_SCORCHED_SHRUB);
    }
    public static void addSedum(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SEDUM);
    }
    public static void addSedumOrange(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SEDUM_ORANGE);
    }
    public static void addSedumRed(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SEDUM_RED);
    }
    public static void addSedumYellow(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SEDUM_YELLOW);
    }
    public static void addSedums(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SEDUMS);
    }
    public static void addShriveledShrubs(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SHRIVELED_SHRUB);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_LARGE_SHRIVELED_SHRUB);
    }
    public static void addRushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RUSHES);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SHORT_RUSHES);
    }
    public static void addStrawberries(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_STRAWBERRY_BUSH);
    }
    public static void addTallGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_TALL_GRASS);
    }
    public static void addYellowTrolliusPatch(List<RegistryKey<PlacedFeature>> vegetation){
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_YELLOW_TROLLIUS);
    }
    public static void addCommonTallGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COMMON_TALL_GRASS);
    }
    public static void addTemperateGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_TEMPERATE_GRASS);
    }
    public static void addSweetBerriesRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SWEET_BERRY_BUSH_RARE);
    }
    public static void addToughBerries(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_TOUGH_BERRY_BUSH);
    }
    public static void addCommonToughBerries(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COMMON_TOUGH_BERRY_BUSH);
    }
    public static void addToughBerriesRare(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_TOUGH_BERRY_BUSH_RARE);
    }
    public static void addTuftGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_TUFT_GRASS);
    }
    public static void addSparseGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SPARSE_GRASS);
    }
    public static void addLargeLilyPad(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_LARGE_LILY_PAD);
    }
    public static void addLargeFloweringLilyPad(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_LARGE_FLOWERING_LILY_PAD);
    }
    public static void addLilyPads(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_LILY_PADS);
    }
    public static void addFloweringLilyPads(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FLOWERING_LILY_PADS);
    }
    public static void addSmallLilyPads(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SMALL_LILY_PADS);
    }
    public static void addSmallFloweringLilyPads(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SMALL_FLOWERING_LILY_PADS);
    }
    public static void addMistweed(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_MISTWEED);
    }
    public static void addMoss(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_MOSS);
    }
    public static void addMossCarpet(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_MOSS_CARPET);
    }
    public static void addHogweeds(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_HOGWEED);
    }
    public static void addHobbitSunflowers(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_HOBBIT_SUNFLOWERS);
    }
    public static void addBigleafHydrangeas(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BLUE_BIGLEAF_HYDRANGEA);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_PINK_BIGLEAF_HYDRANGEA);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WHITE_BIGLEAF_HYDRANGEA);
    }
    public static void addCampion(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_CAMPION);
    }
    public static void addReedsFoliage(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_REEDS);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SHORT_REEDS);
    }
    public static void addFireflyBushes(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FIREFLY_BUSH_SWAMP);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_FIREFLY_BUSH_SWAMP_NEAR_WATER);
    }
    public static void addStickySnow(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_STICKY_SNOW);
    }
    public static void addSpiderEggs(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SPIDER_EGGS);
    }
    public static void addWheatGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WHEAT_GRASS);
    }
    public static void addMeadowGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_MEADOW_GRASS);
    }
    public static void addSparseWheatGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_SPARSE_WHEAT_GRASS);
    }
    public static void addCommonWheatGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_COMMON_WHEAT_GRASS);
    }

    public static void addMixedWildWheatPatch(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_MIXED_WILD_WHEAT);
    }

    public static void addWildGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_GRASS);
    }
    public static void addOccasionalWildGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_OCCASIONAL_WILD_GRASS);
    }
    public static void addWilderGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILDER_GRASS);
    }
    public static void addRareWilderGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_WILDER_GRASS);
    }

    public static void addNettles(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_NETTLES);
    }
    public static void addThistle(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_THISTLE);
    }
    public static void addMordorBrambles(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_MORDOR_BRAMBLES);
    }
    // endregion

    // region MUSHROOMS
    public static void addBrownBolete(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BROWN_BOLETE);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_BROWN_BOLETE_TILLER);
    }
    public static void addMorsel(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_MORSEL);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_MORSEL_TILLER);
    }
    public static void addWhiteMushroom(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WHITE_MUSHROOM);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WHITE_MUSHROOM_TILLER);
    }
    public static void addRareMorsel(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_MORSEL);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_MORSEL_TILLER);
    }
    public static void addRareWhiteMushroom(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_WHITE_MUSHROOM);
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_RARE_WHITE_MUSHROOM_TILLER);
    }
    // endregion

    // region WILD CROPS
    public static void addWildBeetroot(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_BEETROOT);
    }
    public static void addWildBellPepper(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_BELL_PEPPER);
    }
    public static void addWildCarrot(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_CARROT);
    }
    public static void addWildCucumber(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_CUCUMBER);
    }
    public static void addWildFlax(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_FLAX);
    }
    public static void addWildGarlic(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_GARLIC);
    }
    public static void addWildLeek(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_LEEK);
    }
    public static void addWildLettuce(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_LETTUCE);
    }
    public static void addWildOnion(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_ONION);
    }
    public static void addWildPipeweed(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_PIPEWEED);
    }
    public static void addWildPotato(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_POTATO);
    }
    public static void addWildTomato(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(VegetationPlacedFeatureRegistryME.PATCH_WILD_TOMATO);
    }
    // endregion

    // region ORES
    public static void addAndesiteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ANDESITE_ORE);
    }
    public static void addAshBlockOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASH_BLOCK_ORE);
    }
    public static void addAshenDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASHEN_DIRT_ORE);
    }
    public static void addAshenStoneDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASHEN_STONE_DIRT_ORE);
    }
    public static void addAshenStoneDirtCommonOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASHEN_STONE_DIRT_COMMON_ORE);
    }
    public static void addAshenGravelOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASHEN_GRAVEL);
    }
    public static void addAshenSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASHEN_SAND);
    }
    public static void addAshenGravelDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASHEN_GRAVEL_DIRT);
    }
    public static void addAshenGravelSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASHEN_GRAVEL_SAND);
    }
    public static void addAshenStoneGravelOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASHEN_STONE_GRAVEL);
    }
    public static void addAshenStoneSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ASHEN_STONE_SAND);
    }
    public static void addBasaltOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.BASALT_ORE);
    }
    public static void addSmoothBasaltOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.SMOOTH_BASALT_ORE);
    }
    public static void addBlackSand(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.BLACK_SAND_ORE);
    }
    public static void addBlueTuff(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.BLUE_TUFF_ORE);
    }
    public static void addCalciteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.CALCITE_ORE);
    }
    public static void addRareCalciteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.RARE_CALCITE_ORE);
    }
    public static void addCoarseDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.COARSE_DIRT_ORE);
    }
    public static void addCoarseChalksoilOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.COARSE_CHALKSOIL_ORE);
    }
    public static void addCoarseLoamOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.COARSE_LOAM_ORE);
    }
    public static void addCoarsePeatOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.COARSE_PEAT_ORE);
    }
    public static void addCoarseSiltOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.COARSE_SILT_ORE);
    }

    public static void addDioriteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.DIORITE_ORE);
    }
    public static void addRootedDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ROOTED_DIRT_ORE);
    }
    public static void addGrassyDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRASSY_DIRT_ORE);
    }
    public static void addGrassyChalksoilOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRASSY_CHALKSOIL_ORE);
    }
    public static void addGrassyLoamOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRASSY_LOAM_ORE);
    }
    public static void addGrassyPeatOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRASSY_PEAT_ORE);
    }
    public static void addGrassySiltOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRASSY_SILT_ORE);
    }
    public static void addDirtToGrassOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.DIRT_TO_GRASS_ORE);
    }
    public static void addDirtyRootsOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.DIRTY_ROOTS_ORE);
    }
    public static void addDolomiteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.DOLOMITE_ORE);
    }
    public static void addDripstoneOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.DRIPSTONE_ORE);
    }
    public static void addDryDirtOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.DRY_DIRT_ORE);
    }
    public static void addGraniteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRANITE_ORE);
    }
    public static void addGravelOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRAVEL_ORE);
    }
    public static void addGravelToSiltOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRAVEL_TO_SILT_ORE);
        vegetation.add(OrePlacedFeatureRegistryME.SILT_TO_GRASSY_ORE);
        vegetation.add(OrePlacedFeatureRegistryME.SILT_TO_COARSE_ORE);
    }
    public static void addCoarseLoamToGrassy(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.COARSE_LOAM_TO_GRASSY);
        vegetation.add(OrePlacedFeatureRegistryME.GRASSY_LOAM_TO_GRASS);
    }
    public static void addCoarseLoamToFoulDirt(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.COARSE_LOAM_TO_FOUL_DIRT);
        vegetation.add(OrePlacedFeatureRegistryME.FOUL_DIRT_TO_WASTE_PILE);
    }
    public static void addSnowyDirt(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.SNOWY_DIRT_ORE);
    }
    public static void addSnowyGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.SNOWY_GRASS_ORE);
    }
    public static void addStoneGrassOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.STONE_GRASS_ORE);
    }
    public static void addSandToGrass(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.SAND_TO_GRASS_ORE);
    }
    public static void addStoneGrassAbundantOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.STONE_GRASS_ABUNDANT_ORE);
    }
    public static void addLimestoneOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.LIMESTONE_ORE);
    }
    public static void addLorienPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.LORIEN_PODZOL_ORE);
    }
    public static void addMireOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.MIRE_ORE);
    }
    public static void addAbundantMudOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ABUNDANT_MUD_ORE);
    }
    public static void addMudOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.MUD_ORE);
    }
    public static void addPackedMudOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.PACKED_MUD_ORE);
    }
    public static void addPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.PODZOL_ORE);
    }
    public static void addAbundantPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ABUNDANT_PODZOL_ORE);
    }
    public static void addPowderSnowOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.POWDER_SNOW_ORE);
    }
    public static void addOldPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.OLD_PODZOL_ORE);
    }
    public static void addStoneOldPodzolOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.STONE_OLD_PODZOL_ORE);
    }
    public static void addSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.SAND_ORE);
    }
    public static void addSnowOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.SNOW_ORE);
    }
    public static void addSoulSandOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.SOUL_SAND_ORE);
    }
    public static void addCalciteStoneOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.CALCITE_STONE_ORE);
    }
    public static void addGrassToStoneOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRASS_TO_STONE_ORE);
    }
    public static void addGrassToGraniteOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.GRASS_TO_GRANITE_ORE);
    }
    public static void addTerracottaOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.TERRACOTTA_ORE);
    }
    public static void addTuffOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.TUFF_ORE);
    }
    public static void addAbundantTuffOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.ABUNDANT_TUFF_ORE);
    }
    public static void addTurfOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.TURF_ORE);
    }
    public static void addCommonTurfOre(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.COMMON_TURF_ORE);
    }
    public static void addWhiteSand(List<RegistryKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatureRegistryME.WHITE_SAND_ORE);
    }
    // endregion

    // region MISC
    public static void addLavaMagmaLake(GenerationSettings.LookupBackedBuilder generationSettings) {
        generationSettings.feature(GenerationStep.Feature.LAKES, MiscPlacedFeatureRegistryME.LAVA_MAGMA_POOL);
    }
    // endregion
}
