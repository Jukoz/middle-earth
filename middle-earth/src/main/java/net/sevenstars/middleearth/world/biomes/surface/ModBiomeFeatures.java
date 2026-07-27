package net.sevenstars.middleearth.world.biomes.surface;

import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.sevenstars.middleearth.world.features.boulder.BoulderPlacedFeatures;
import net.sevenstars.middleearth.world.features.misc.ModMiscPlacedFeatures;
import net.sevenstars.middleearth.world.features.ores.OrePlacedFeatures;
import net.sevenstars.middleearth.world.features.tree.ModTreePlacedFeatures;
import net.sevenstars.middleearth.world.features.vegetation.ModVegetationPlacedFeatures;
import java.util.ArrayList;
import java.util.List;

public class ModBiomeFeatures {

    public static void addDisks(ArrayList<ResourceKey<PlacedFeature>> ores) {
        ores.add(MiscOverworldPlacements.DISK_SAND);
        ores.add(MiscOverworldPlacements.DISK_CLAY);
        ores.add(MiscOverworldPlacements.DISK_GRAVEL);
    }

    public static void addRiverSand(ArrayList<ResourceKey<PlacedFeature>> ores) {
        ores.add(OrePlacedFeatures.RIVER_SAND);
    }
    public static void addRiverDisks(ArrayList<ResourceKey<PlacedFeature>> ores) {
        ores.add(OrePlacedFeatures.RIVER_SAND);
        ores.add(OrePlacedFeatures.DISK_SAND);
        ores.add(MiscOverworldPlacements.DISK_CLAY);
        ores.add(MiscOverworldPlacements.DISK_GRAVEL);
    }

    // region TREES
    public static void addAcaciaTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ACACIA_PLACED_TREE_KEY);
    }
    public static void addCommonAcaciaTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_ACACIA_PLACED_TREE_KEY);
    }
    public static void addRareAcaciaTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_ACACIA_PLACED_TREE_KEY);
    }
    public static void addCommonBeechTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_BEECH_PLACED_TREE_KEY);
    }
    public static void addBeechTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BEECH_PLACED_TREE_KEY);
    }
    public static void addRareBeechTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_BEECH_PLACED_TREE_KEY);
    }
    public static void addVeryRareBeechTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_BEECH_PLACED_TREE_KEY);
    }

    public static void addCommonBirchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_BIRCH_PLACED_TREE_KEY);
    }
    public static void addBirchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BIRCH_PLACED_TREE_KEY);
    }
    public static void addBirchAndOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BIRCH_AND_OAK_PLACED_TREE_KEY);
    }
    public static void addSparseBirchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addRareBirchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_BIRCH_PLACED_TREE_KEY);
    }
    public static void addVeryRareBirchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_BIRCH_PLACED_TREE_KEY);
    }

    public static void addCommonAspenTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_ASPEN_PLACED_TREE_KEY);
    }
    public static void addAspenTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ASPEN_PLACED_TREE_KEY);
    }
    public static void addSparseAspenTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_ASPEN_PLACED_TREE_KEY);
    }
    public static void addRareAspenTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_ASPEN_PLACED_TREE_KEY);
    }
    public static void addVeryRareAspenTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_ASPEN_PLACED_TREE_KEY);
    }
    public static void addCherryBlossomTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_CHERRY_BLOSSOM_PLACED_TREE_KEY);
    }
    public static void addChestnutTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.CHESTNUT_PLACED_TREE_KEY);
    }
    public static void addMegaBirchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_BIRCH_PLACED_TREE_KEY);
    }
    public static void addMegaBirchCommonTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_BIRCH_PLACED_COMMON_TREE_KEY);
    }
    public static void addDarkOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addCommonDarkOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaDarkOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addRareMegaDarkOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_MEGA_DARK_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaDarkOakCommonTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_DARK_OAK_PLACED_COMMON_TREE_KEY);
    }
    public static void addDeadwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DEADWOOD_TREE_KEY);
    }
    public static void addCommonFirTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_FIR_PLACED_TREE_KEY);
    }
    public static void addFirTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.FIR_PLACED_TREE_KEY);
    }
    public static void addAbundantFirTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ABUNDANT_FIR_PLACED_TREE_KEY);
    }
    public static void addRareFirTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_FIR_PLACED_TREE_KEY);
    }
    public static void addVeryRareFirTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_FIR_PLACED_TREE_KEY);
    }
    public static void addHollyTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.HOLLY_PLACED_TREE_KEY);
    }
    public static void addCommonHollyTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_HOLLY_PLACED_TREE_KEY);
    }
    public static void addCommonLarchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_LARCH_PLACED_TREE_KEY);
    }
    public static void addLarchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.LARCH_PLACED_TREE_KEY);
    }
    public static void addSparseLarchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_LARCH_PLACED_TREE_KEY);
    }
    public static void addRareLarchTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_LARCH_PLACED_TREE_KEY);
    }

    public static void addLebethronTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addCommonLebethronTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.COMMON_WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addRareLebethronTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_BLACK_LEBETHRON_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.RARE_WHITE_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addVeryRareLebethronTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_BLACK_LEBETHRON_PLACED_TREE_KEY);
    }
    public static void addMallornTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MALLORN_PLACED_TREE_KEY);
    }
    public static void addSmallMallornTress(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SMALL_MALLORN_PLACED_TREE_KEY);
    }
    public static void addMegaMallornTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_MALLORN_PLACED_TREE_KEY);
    }
    public static void addMallornBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MALLORN_BUSH_PLACED_TREE_KEY);
    }
    public static void addMallornFloweringBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MALLORN_FLOWERING_BUSH_PLACED_TREE_KEY);
    }
    public static void addMapleTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addCommonMapleTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.COMMON_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.COMMON_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.COMMON_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.COMMON_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.COMMON_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.COMMON_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.COMMON_SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addMapleTree(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_MAPLE_PLACED_TREE_KEY);
    }
    public static void addGreenMapleTree(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_MAPLE_PLACED_TREE_KEY);
    }
    public static void addYellowMapleTree(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
    }
    public static void addOrangeMapleTree(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
    }
    public static void addRedMapleTree(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addScarceMapleTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_RED_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SCARCE_SILVER_RED_MAPLE_PLACED_TREE_KEY);
    }
    public static void addSmallMirkwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SMALL_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addMirkwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addSparseMirkwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addDeadMirkwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DEAD_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addUncommonMirkwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.UNCOMMON_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addMegaMirkwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_MIRKWOOD_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.SPARSE_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addRareMegaMirkwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_MEGA_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addVeryRareMegaMirkwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_MEGA_MIRKWOOD_PLACED_TREE_KEY);
    }
    public static void addDeadMegaMirkwoodTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DEAD_MEGA_MIRKWOOD_PLACED_TREE_KEY);
    }

    public static void addCommonOakBush(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_BUSH_COMMON_PLACED_TREE_KEY);
    }
    public static void addOakBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_BUSH_PLACED_TREE_KEY);
    }
    public static void addCommonOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_OAK_PLACED_TREE_KEY);
    }
    public static void addFrequentOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ABUNDANT_OAK_PLACED_TREE_KEY);
    }
    public static void addRareOakBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_BUSH_RARE_PLACED_TREE_KEY);
    }
    public static void addOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_PLACED_TREE_KEY);
    }
    public static void addBeesOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BEES_OAK_PLACED_TREE_KEY);
    }
    public static void addRareSmallSwampOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_SMALL_SWAMP_OAK_PLACED_TREE_KEY);
    }
    public static void addRareOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_OAK_PLACED_TREE_KEY);
    }
    public static void addOakVinesTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.OAK_VINES_PLACED_TREE_KEY);
    }
    public static void addMegaOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_OAK_PLACED_TREE_KEY);
    }
    public static void addMegaOakCommonTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.MEGA_OAK_COMMON_PLACED_TREE_KEY);
    }
    public static void addRareMegaOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_MEGA_OAK_PLACED_TREE_KEY);
    }
    public static void addVeryRareMegaOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_MEGA_OAK_PLACED_TREE_KEY);
    }

    public static void addPalmTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.PALM_PLACED_TREE_KEY);
        vegetation.add(ModTreePlacedFeatures.WHITE_PALM_PLACED_TREE_KEY);
    }
    public static void addWhitePalmTree(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.UNCOMMON_WHITE_PALM_PLACED_TREE_KEY);
    }

    public static void addAbundantPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ABUNDANT_PINE_PLACED_TREE_KEY);
    }
    public static void addCommonPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_PINE_PLACED_TREE_KEY);
    }
    public static void addPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.PINE_PLACED_TREE_KEY);
    }
    public static void addSparsePineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPARSE_PINE_PLACED_TREE_KEY);
    }
    public static void addDeadPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DEAD_PINE_PLACED_TREE_KEY);
    }
    public static void addDryPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DRY_PINE_PLACED_TREE_KEY);
    }
    public static void addDryPineBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DRY_PINE_BUSH_PLACED_TREE_KEY);
    }

    public static void addCommonBlackPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addBlackPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addScarceBlackPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addDeadBlackPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.DEAD_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addCommonDeadBlackPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_DEAD_BLACK_PINE_PLACED_TREE_KEY);
    }
    public static void addAbundantDeadBlackPineTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ABUNDANT_DEAD_BLACK_PINE_PLACED_TREE_KEY);
    }

    public static void addRottenTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ROTTEN_TREE_KEY);
    }

    public static void addScorchedTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCORCHED_TREE_PLACED_TREE_KEY);
    }
    public static void addCommonScorchedTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_SCORCHED_TREE_PLACED_TREE_KEY);
    }
    public static void addAbundantScorchedTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.ABUNDANT_SCORCHED_TREE_PLACED_TREE_KEY);
    }

    public static void addAbundantSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.FOOTHILLS_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addFrequentSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.FREQUENT_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPRUCE_PLACED_TREE_KEY);
    }
    public static void addScarceSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addRareSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonSpruceBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addSpruceBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addVeryRareSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addExtremelyRareSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_SPRUCE_PLACED_TREE_KEY);
    }

    public static void addAbundantWhiteSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.FOOTHILLS_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addFrequentWhiteSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.FREQUENT_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonWhiteSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addWhiteSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addScarceWhiteSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.SCARCE_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addRareWhiteSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.RARE_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addCommonWhiteSpruceBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_WHITE_SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addWhiteSpruceBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.WHITE_SPRUCE_BUSH_PLACED_TREE_KEY);
    }
    public static void addVeryRareWhiteSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    public static void addExtremelyRareWhiteSpruceTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.VERY_RARE_WHITE_SPRUCE_PLACED_TREE_KEY);
    }
    
    public static void addWillowTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.WILLOW_PLACED_TREE_KEY);
    }
    public static void addCommonWillowTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.COMMON_WILLOW_PLACED_TREE_KEY);
    }

    public static void addPaleOakTrees(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModTreePlacedFeatures.PALE_OAK_PLACED_TREE_KEY);
    }
    
    // endregion TREES

    // region BOULDERS

    public static void addMirkwoodRoots(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.MIRKWOOD_ROOTS_BOULDER);
    }
    public static void addAndesiteBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_ANDESITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_ANDESITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_ANDESITE);
    }
    public static void addAshenStoneBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_ASHEN_STONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_ASHEN_STONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_ASHEN_STONE);
    }
    public static void addBasaltBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_BASALT);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_BASALT);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_BASALT);
    }
    public static void addBlueTuffBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_BLUE_TUFF);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_BLUE_TUFF);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_BLUE_TUFF);
    }
    public static void addCalciteBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_CALCITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_CALCITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_CALCITE);
    }
    public static void addDioriteBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_DIORITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_DIORITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_DIORITE);
    }
    public static void addDolomiteBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_DOLOMITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_DOLOMITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_DOLOMITE);
    }
    public static void addSmoothDolomiteBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_SMOOTH_DOLOMITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_SMOOTH_DOLOMITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_SMOOTH_DOLOMITE);
    }
    public static void addGalonnBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_GALONN);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_GALONN);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_GALONN);
    }
    public static void addGneissBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_GNEISS);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_GNEISS);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_GNEISS);
    }
    public static void addGraniteBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_GRANITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_GRANITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_GRANITE);
    }
    public static void addHematiteBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_HEMATITE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_HEMATITE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_HEMATITE);
    }
    public static void addIronStoneBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_IRONSTONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_IRONSTONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_IRONSTONE);
    }
    public static void addLimestoneBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_LIMESTONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_LIMESTONE);
    }
    public static void addMossyBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_MOSSY_STONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_MOSSY_STONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_MOSSY_STONE);
    }
    public static void addSandStoneBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_SANDSTONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_SANDSTONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_SANDSTONE);
    }
    public static void addStoneBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_STONE);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_STONE);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_STONE);
    }
    public static void addSlateBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_SLATE);
    }
    public static void addGabbroBoulder(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(BoulderPlacedFeatures.SMALL_BOULDER_GABBRO);
        vegetation.add(BoulderPlacedFeatures.MEDIUM_BOULDER_GABBRO);
        vegetation.add(BoulderPlacedFeatures.BIG_BOULDER_GABBRO);
    }

    // endregion

    // region FIELDS
    public static void addHeatherField(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FIELD_HEATHER);
    }
    public static void addDryHeatherField(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FIELD_DRY_HEATHER);
    }
    public static void addLavenderField(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FIELD_LAVENDER);
    }
    public static void addWildWheatField(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FIELD_WILD_WHEAT);
    }


    // endregion
    public static void addMirkwoodVines(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.MIRKWOOD_VINES);
    }
    public static void addWillowVines(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.WILLOW_VINES);
    }

    // region GROWTH
    public static void addAzaleaGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.AZALEA_GROWTH);
    }
    public static void addDryGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.DRY_GROWTH);
    }
    public static void addFrozenGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FROZEN_GROWTH);
    }
    public static void addIvyGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.IVY_GROWTH);
    }
    public static void addGreenGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.GREEN_GROWTH);
    }
    public static void addThornyGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.THORNY_GROWTH);
    }
    public static void addLilacFlowerGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.LILAC_FLOWER_GROWTH);
    }
    public static void addRedFlowerGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.RED_FLOWER_GROWTH);
    }
    public static void addYellowFlowerGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.YELLOW_FLOWER_GROWTH);
    }
    public static void addPinkFlowerGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PINK_FLOWER_GROWTH);
    }
    public static void addWhiteFlowerGrowth(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.WHITE_FLOWER_GROWTH);
    }

    // endregion

    // region FOLIAGE
    public static void addWaterDelta(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.WATER_DELTA);
    }
    public static void addAbundantWaterDelta(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.ABUNDANT_WATER_DELTA);
    }

    public static void addAthelas(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_ATHELAS);
    }
    public static void addAlliumFlower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_ALLIUM);
    }
    public static void addAzureBluetFlower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_AZURE_BLUET);
    }
    public static void addRareAzureBluetFlower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_AZURE_BLUET_RARE);
    }
    public static void addBlueGentianFlower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_BLUE_GENTIAN);
    }
    public static void addBlueOrchidFlower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_BLUE_ORCHID);
    }
    public static void addCornflower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_CORNFLOWER);
    }
    public static void addCornflowerCommon(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_CORNFLOWER_COMMON);
    }
    public static void addFlowerGreenJewel(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_GREEN_JEWEL);
    }
    public static void addLilacFlower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_LILAC);
    }
    public static void addFlowerDorwinion(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_DORWINION);
    }
    public static void addLebenninFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_LEBENNIN);
    }
    public static void addLossarnachFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_LOSSARNACH);
    }
    public static void addLossarnachFlowersCommon(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_LOSSARNACH_COMMON);
    }
    public static void addFlowerMeadow(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_MEADOW);
    }
    public static void addMallos(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_MALLOS);
    }
    public static void addNoblewhite(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_NOBLEWHITE);
    }
    public static void addPoppyFlower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_POPPY);
    }
    public static void addElanor(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_ELANOR);
    }
    public static void addNiphredil(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_NIPHREDIL);
    }
    public static void addSimbelmyne(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_SIMBELMYNE);
    }
    public static void addRoseBush(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_ROSE_BUSH);
    }
    public static void addYellowFlower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWER_YELLOW);
    }
    public static void addRareYellowFlower(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.RARE_FLOWER_YELLOW);
    }
    public static void addWildFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_FLOWERS);
    }

    public static void addLightBlueFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_LIGHT_BLUE);
    }
    public static void addRareLightBlueFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.RARE_FLOWERS_LIGHT_BLUE);
    }
    public static void addMagentaFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_MAGENTA);
    }
    public static void addOrangeFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_ORANGE);
    }
    public static void addPinkFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_PINK);
    }
    public static void addPurpleFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_PURPLE);
    }
    public static void addRedFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_RED);
    }
    public static void addWhiteFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_WHITE);
    }
    public static void addYellowFlowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FLOWERS_YELLOW);
    }

    public static void addBasaltPile(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BASALT);
    }
    public static void addBasaltPileRare(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BASALT_RARE);
    }
    public static void addBlackStonePile(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BLACKSTONE);
    }
    public static void addBracken(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BRACKEN);
    }
    public static void addGiantButterbur(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GIANT_BUTTERBUR);
    }
    public static void addFieldBlueFescue(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FIELD_BLUE_FESCUE);
    }
    public static void addPumicePileRare(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_PUMICE);
    }
    public static void addPumicePileSparse(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_PUMICE_SPARSE);
    }
    public static void addPumiceColumn(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PUMICE_COLUMN);
    }
    public static void addPumiceColumnRare(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PUMICE_COLUMN_RARE);
    }
    public static void addPumiceColumnLarge(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PUMICE_COLUMN_LARGE);
    }
    public static void addBamboo(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BAMBOO);
    }
    public static void addBeachGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BEACH_GRASS);
    }
    public static void addCommonBeachGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_BEACH_GRASS);
    }
    public static void addBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BUSHES);
    }
    public static void addClovers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_CLOVERS);
    }
    public static void addHaradFoliage(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BROWN_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GRIM_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TAN_SHRUB);
    }
    public static void addBulrushAndCattail(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SHORT_BULRUSH);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TALL_BULRUSH);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SHORT_CATTAIL);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TALL_CATTAIL);
    }
    public static void addCoastalFoliage(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COASTAL_PANIC_GRASS);
    }
    public static void addCobwebs(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COBWEB);
    }
    public static void addCorruptedMoss(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_CORRUPTED_MOSS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_CORRUPTED_MOSS_CARPET);
        vegetation.add(OrePlacedFeatures.CORRUPTED_MOSS_DISK);
    }
    public static void addDeadRushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DEAD_RUSHES);
    }
    public static void addDryGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DRY_GRASS);
    }
    public static void addVeryRareDryGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_VERY_RARE_DRY_GRASS);
    }
    public static void addSmallDryShrub(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SMALL_DRY_SHRUB);
    }
    public static void addDuckweed(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DUCKWEED);
    }
    public static void addDyingGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DYING_GRASS);
    }
    public static void addFallenLeaves(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FALLEN_LEAVES);
    }
    public static void addFallenMallornLeaves(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FALLEN_MALLORN_LEAVES);
    }
    public static void addFallenMirkwoodLeaves(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FALLEN_MIRKWOOD_LEAVES);
    }
    public static void addFalseOatgrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FALSE_OATGRASS);
    }
    public static void addFloatingIce(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FLOATING_ICE);
    }
    public static void addForestMoss(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FOREST_MOSS);
    }
    public static void addForestBlockMoss(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FOREST_MOSS_CARPET);
        vegetation.add(OrePlacedFeatures.FOREST_MOSS_DISK);
    }
    public static void addFrozenGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FROZEN_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FROZEN_TUFT);
    }
    public static void addFrozenShrub(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FROZEN_SHRUB);
    }
    public static void addRareForestMoss(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_FOREST_MOSS);
    }
    public static void addGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GRASS);
    }
    public static void addGreenShrub(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GREEN_SHRUB);
    }
    public static void addGrimGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_GRIM_GRASS);
    }
    public static void addCommonHeath(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_HEATH);
    }
    public static void addSparseHeath(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SPARSE_HEATH);
    }
    public static void addHeath(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_HEATH);
    }
    public static void addHeather(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_HEATHER);
    }
    public static void addCommonHeather(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_HEATHER);
    }
    public static void addFieldDeadNormalHeather(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FIELD_DEAD_NORMAL_HEATHER);
    }
    public static void addSparseFieldDeadNormalHeather(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.FIELD_SPARSE_DEAD_NORMAL_HEATHER);
    }
    public static void addUncommonBlueLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BLUE_LAVENDER);
    }
    public static void addSparseBlueLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SPARSE_BLUE_LAVENDER);
    }
    public static void addRareBlueLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_BLUE_LAVENDER);
    }
    public static void addVeryRareBlueLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_VERY_RARE_BLUE_LAVENDER);
    }
    public static void addUncommonLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_LAVENDER);
    }
    public static void addSparseLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SPARSE_LAVENDER);
    }
    public static void addRareLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_LAVENDER);
    }
    public static void addVeryRareLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_VERY_RARE_LAVENDER);
    }
    public static void addUncommonWhiteLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WHITE_LAVENDER);
    }
    public static void addSparseWhiteLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SCARCE_WHITE_LAVENDER);
    }
    public static void addRareWhiteLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_WHITE_LAVENDER);
    }
    public static void addVeryRareWhiteLavender(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_VERY_RARE_WHITE_LAVENDER);
    }
    public static void addRareHeather(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_HEATHER);
    }
    public static void addDeadHeather(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DEAD_HEATHER);
    }
    public static void addDryHeather(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_DRY_HEATHER);
    }
    public static void addRedHeather(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RED_HEATHER);
    }
    public static void addScorchedGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SCORCHED_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SCORCHED_TUFT);
    }
    public static void addScorchedShrub(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SCORCHED_SHRUB);
    }
    public static void addCommonScorchedGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_SCORCHED_GRASS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_SCORCHED_TUFT);
    }
    public static void addCommonScorchedShrub(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_SCORCHED_SHRUB);
    }
    public static void addSedum(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SEDUM);
    }
    public static void addSedumOrange(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SEDUM_ORANGE);
    }
    public static void addSedumRed(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SEDUM_RED);
    }
    public static void addSedumYellow(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SEDUM_YELLOW);
    }
    public static void addSedums(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SEDUMS);
    }
    public static void addShriveledShrubs(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SHRIVELED_SHRUB);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_LARGE_SHRIVELED_SHRUB);
    }
    public static void addRushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RUSHES);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SHORT_RUSHES);
    }
    public static void addStrawberries(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_STRAWBERRY_BUSH);
    }
    public static void addTallGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TALL_GRASS);
    }
    public static void addYellowTrolliusPatch(List<ResourceKey<PlacedFeature>> vegetation){
        vegetation.add(ModVegetationPlacedFeatures.PATCH_YELLOW_TROLLIUS);
    }
    public static void addCommonTallGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_TALL_GRASS);
    }
    public static void addTemperateGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TEMPERATE_GRASS);
    }
    public static void addSweetBerriesRare(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SWEET_BERRY_BUSH_RARE);
    }
    public static void addToughBerries(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TOUGH_BERRY_BUSH);
    }
    public static void addCommonToughBerries(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_TOUGH_BERRY_BUSH);
    }
    public static void addToughBerriesRare(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TOUGH_BERRY_BUSH_RARE);
    }
    public static void addTuftGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_TUFT_GRASS);
    }
    public static void addSparseGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SPARSE_GRASS);
    }
    public static void addLargeLilyPad(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_LARGE_LILY_PAD);
    }
    public static void addLargeFloweringLilyPad(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_LARGE_FLOWERING_LILY_PAD);
    }
    public static void addLilyPads(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_LILY_PADS);
    }
    public static void addFloweringLilyPads(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FLOWERING_LILY_PADS);
    }
    public static void addSmallLilyPads(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SMALL_LILY_PADS);
    }
    public static void addSmallFloweringLilyPads(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SMALL_FLOWERING_LILY_PADS);
    }
    public static void addMistweed(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MISTWEED);
    }
    public static void addMoss(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MOSS);
    }
    public static void addMossCarpet(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MOSS_CARPET);
    }
    public static void addHogweeds(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_HOGWEED);
    }
    public static void addHobbitSunflowers(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_HOBBIT_SUNFLOWERS);
    }
    public static void addBigleafHydrangeas(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BLUE_BIGLEAF_HYDRANGEA);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_PINK_BIGLEAF_HYDRANGEA);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WHITE_BIGLEAF_HYDRANGEA);
    }
    public static void addCampion(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_CAMPION);
    }
    public static void addReedsFoliage(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_REEDS);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SHORT_REEDS);
    }
    public static void addFireflyBushes(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FIREFLY_BUSH_SWAMP);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_FIREFLY_BUSH_SWAMP_NEAR_WATER);
    }
    public static void addStickySnow(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_STICKY_SNOW);
    }
    public static void addSpiderEggs(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SPIDER_EGGS);
    }
    public static void addWheatGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WHEAT_GRASS);
    }
    public static void addMeadowGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MEADOW_GRASS);
    }
    public static void addSparseWheatGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_SPARSE_WHEAT_GRASS);
    }
    public static void addCommonWheatGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_COMMON_WHEAT_GRASS);
    }

    public static void addMixedWildWheatPatch(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MIXED_WILD_WHEAT);
    }

    public static void addWildGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_GRASS);
    }
    public static void addOccasionalWildGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_OCCASIONAL_WILD_GRASS);
    }
    public static void addWilderGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILDER_GRASS);
    }
    public static void addRareWilderGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_WILDER_GRASS);
    }

    public static void addNettles(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_NETTLES);
    }
    public static void addThistle(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_THISTLE);
    }
    public static void addMordorBrambles(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MORDOR_BRAMBLES);
    }
    // endregion

    // region MUSHROOMS
    public static void addBrownBolete(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BROWN_BOLETE);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_BROWN_BOLETE_TILLER);
    }
    public static void addMorsel(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MORSEL);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_MORSEL_TILLER);
    }
    public static void addWhiteMushroom(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WHITE_MUSHROOM);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WHITE_MUSHROOM_TILLER);
    }
    public static void addRareMorsel(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_MORSEL);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_MORSEL_TILLER);
    }
    public static void addRareWhiteMushroom(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_WHITE_MUSHROOM);
        vegetation.add(ModVegetationPlacedFeatures.PATCH_RARE_WHITE_MUSHROOM_TILLER);
    }
    // endregion

    // region WILD CROPS
    public static void addWildBeetroot(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_BEETROOT);
    }
    public static void addWildBellPepper(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_BELL_PEPPER);
    }
    public static void addWildCarrot(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_CARROT);
    }
    public static void addWildCucumber(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_CUCUMBER);
    }
    public static void addWildFlax(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_FLAX);
    }
    public static void addWildGarlic(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_GARLIC);
    }
    public static void addWildLeek(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_LEEK);
    }
    public static void addWildLettuce(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_LETTUCE);
    }
    public static void addWildOnion(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_ONION);
    }
    public static void addWildPipeweed(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_PIPEWEED);
    }
    public static void addWildPotato(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_POTATO);
    }
    public static void addWildTomato(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(ModVegetationPlacedFeatures.PATCH_WILD_TOMATO);
    }
    // endregion

    // region ORES
    public static void addAndesiteOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ANDESITE_ORE);
    }
    public static void addAshBlockOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASH_BLOCK_ORE);
    }
    public static void addAshenDirtOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_DIRT_ORE);
    }
    public static void addAshenStoneDirtOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_STONE_DIRT_ORE);
    }
    public static void addAshenStoneDirtCommonOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_STONE_DIRT_COMMON_ORE);
    }
    public static void addAshenGravelOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_GRAVEL);
    }
    public static void addAshenSandOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_SAND);
    }
    public static void addAshenGravelDirtOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_GRAVEL_DIRT);
    }
    public static void addAshenGravelSandOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_GRAVEL_SAND);
    }
    public static void addAshenStoneGravelOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_STONE_GRAVEL);
    }
    public static void addAshenStoneSandOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ASHEN_STONE_SAND);
    }
    public static void addBasaltOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.BASALT_ORE);
    }
    public static void addSmoothBasaltOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SMOOTH_BASALT_ORE);
    }
    public static void addBlackSand(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.BLACK_SAND_ORE);
    }
    public static void addBlueTuff(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.BLUE_TUFF_ORE);
    }
    public static void addCalciteOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.CALCITE_ORE);
    }
    public static void addRareCalciteOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.RARE_CALCITE_ORE);
    }
    public static void addCoarseDirtOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COARSE_DIRT_ORE);
    }
    public static void addCoarseChalksoilOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COARSE_CHALKSOIL_ORE);
    }
    public static void addCoarseLoamOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COARSE_LOAM_ORE);
    }
    public static void addCoarsePeatOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COARSE_PEAT_ORE);
    }
    public static void addCoarseSiltOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COARSE_SILT_ORE);
    }

    public static void addDioriteOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DIORITE_ORE);
    }
    public static void addRootedDirtOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ROOTED_DIRT_ORE);
    }
    public static void addGrassyDirtOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRASSY_DIRT_ORE);
    }
    public static void addGrassyChalksoilOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRASSY_CHALKSOIL_ORE);
    }
    public static void addGrassyLoamOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRASSY_LOAM_ORE);
    }
    public static void addGrassyPeatOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRASSY_PEAT_ORE);
    }
    public static void addGrassySiltOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRASSY_SILT_ORE);
    }
    public static void addDirtToGrassOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DIRT_TO_GRASS_ORE);
    }
    public static void addDirtyRootsOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DIRTY_ROOTS_ORE);
    }
    public static void addDolomiteOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DOLOMITE_ORE);
    }
    public static void addDripstoneOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DRIPSTONE_ORE);
    }
    public static void addDryDirtOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.DRY_DIRT_ORE);
    }
    public static void addGraniteOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRANITE_ORE);
    }
    public static void addGravelOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRAVEL_ORE);
    }
    public static void addGravelToSiltOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRAVEL_TO_SILT_ORE);
        vegetation.add(OrePlacedFeatures.SILT_TO_GRASSY_ORE);
        vegetation.add(OrePlacedFeatures.SILT_TO_COARSE_ORE);
    }
    public static void addCoarseLoamToGrassy(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COARSE_LOAM_TO_GRASSY);
        vegetation.add(OrePlacedFeatures.GRASSY_LOAM_TO_GRASS);
    }
    public static void addCoarseLoamToFoulDirt(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COARSE_LOAM_TO_FOUL_DIRT);
        vegetation.add(OrePlacedFeatures.FOUL_DIRT_TO_WASTE_PILE);
    }
    public static void addSnowyDirt(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SNOWY_DIRT_ORE);
    }
    public static void addSnowyGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SNOWY_GRASS_ORE);
    }
    public static void addStoneGrassOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.STONE_GRASS_ORE);
    }
    public static void addSandToGrass(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SAND_TO_GRASS_ORE);
    }
    public static void addStoneGrassAbundantOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.STONE_GRASS_ABUNDANT_ORE);
    }
    public static void addLimestoneOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.LIMESTONE_ORE);
    }
    public static void addLorienPodzolOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.LORIEN_PODZOL_ORE);
    }
    public static void addMireOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.MIRE_ORE);
    }
    public static void addAbundantMudOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ABUNDANT_MUD_ORE);
    }
    public static void addMudOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.MUD_ORE);
    }
    public static void addPackedMudOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.PACKED_MUD_ORE);
    }
    public static void addPodzolOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.PODZOL_ORE);
    }
    public static void addAbundantPodzolOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ABUNDANT_PODZOL_ORE);
    }
    public static void addPowderSnowOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.POWDER_SNOW_ORE);
    }
    public static void addOldPodzolOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.OLD_PODZOL_ORE);
    }
    public static void addStoneOldPodzolOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.STONE_OLD_PODZOL_ORE);
    }
    public static void addSandOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SAND_ORE);
    }
    public static void addSnowOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SNOW_ORE);
    }
    public static void addSoulSandOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.SOUL_SAND_ORE);
    }
    public static void addCalciteStoneOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.CALCITE_STONE_ORE);
    }
    public static void addGrassToStoneOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRASS_TO_STONE_ORE);
    }
    public static void addGrassToGraniteOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.GRASS_TO_GRANITE_ORE);
    }
    public static void addTerracottaOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.TERRACOTTA_ORE);
    }
    public static void addTuffOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.TUFF_ORE);
    }
    public static void addAbundantTuffOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.ABUNDANT_TUFF_ORE);
    }
    public static void addTurfOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.TURF_ORE);
    }
    public static void addCommonTurfOre(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.COMMON_TURF_ORE);
    }
    public static void addWhiteSand(List<ResourceKey<PlacedFeature>> vegetation) {
        vegetation.add(OrePlacedFeatures.WHITE_SAND_ORE);
    }
    // endregion

    // region MISC
    public static void addLavaMagmaLake(BiomeGenerationSettings.Builder generationSettings) {
        generationSettings.addFeature(GenerationStep.Decoration.LAKES, ModMiscPlacedFeatures.LAVA_MAGMA_POOL);
    }
    // endregion
}
