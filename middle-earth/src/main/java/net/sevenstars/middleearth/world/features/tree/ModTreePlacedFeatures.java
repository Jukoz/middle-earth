package net.sevenstars.middleearth.world.features.tree;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.TreeFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightmapPlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import java.util.List;

public class ModTreePlacedFeatures {
    public static final ResourceKey<PlacedFeature> ACACIA_PLACED_TREE_KEY = registerKey("acacia_tree");
    public static final ResourceKey<PlacedFeature> COMMON_ACACIA_PLACED_TREE_KEY = registerKey("common_acacia_tree");
    public static final ResourceKey<PlacedFeature> RARE_ACACIA_PLACED_TREE_KEY = registerKey("rare_acacia_tree");
    public static final ResourceKey<PlacedFeature> COMMON_BEECH_PLACED_TREE_KEY = registerKey("common_beech_tree");
    public static final ResourceKey<PlacedFeature> BEECH_PLACED_TREE_KEY = registerKey("beech_tree");
    public static final ResourceKey<PlacedFeature> RARE_BEECH_PLACED_TREE_KEY = registerKey("rare_beech_tree");
    public static final ResourceKey<PlacedFeature> VERY_RARE_BEECH_PLACED_TREE_KEY = registerKey("very_rare_beech_tree");

    public static final ResourceKey<PlacedFeature> COMMON_BIRCH_PLACED_TREE_KEY = registerKey("common_birch_tree");
    public static final ResourceKey<PlacedFeature> BIRCH_PLACED_TREE_KEY = registerKey("birch_tree");
    public static final ResourceKey<PlacedFeature> BIRCH_AND_OAK_PLACED_TREE_KEY = registerKey("birch_and_oak_tree");
    public static final ResourceKey<PlacedFeature> FALLEN_BIRCH_PLACED_TREE_KEY = registerKey("fallen_birch_tree");
    public static final ResourceKey<PlacedFeature> FALLEN_OAK_PLACED_TREE_KEY = registerKey("fallen_oak_tree");
    public static final ResourceKey<PlacedFeature> OAK_BEES_0002_LEAF_LITTER_PLACED_TREE_KEY = registerKey("oak_bees_0002_leaf_litter");
    public static final ResourceKey<PlacedFeature> BIRCH_BEES_0002_LEAF_LITTER_PLACED_TREE_KEY = registerKey("birch_bees_0002_leaf_litter");
    public static final ResourceKey<PlacedFeature> FANCY_OAK_BEES_0002_LEAF_LITTER_PLACED_TREE_KEY = registerKey("fancy_oak_bees_0002_leaf_litter");
    public static final ResourceKey<PlacedFeature> SPARSE_BIRCH_PLACED_TREE_KEY = registerKey("sparse_birch_tree");
    public static final ResourceKey<PlacedFeature> RARE_BIRCH_PLACED_TREE_KEY = registerKey("rare_birch_tree");
    public static final ResourceKey<PlacedFeature> VERY_RARE_BIRCH_PLACED_TREE_KEY = registerKey("super_rare_birch_tree");
    public static final ResourceKey<PlacedFeature> MEGA_BIRCH_PLACED_COMMON_TREE_KEY = registerKey("mega_birch_common_tree");
    public static final ResourceKey<PlacedFeature> MEGA_BIRCH_PLACED_TREE_KEY = registerKey("mega_birch_tree");

    public static final ResourceKey<PlacedFeature> COMMON_ASPEN_PLACED_TREE_KEY = registerKey("common_aspen_tree");
    public static final ResourceKey<PlacedFeature> ASPEN_PLACED_TREE_KEY = registerKey("aspen_tree");
    public static final ResourceKey<PlacedFeature> SPARSE_ASPEN_PLACED_TREE_KEY = registerKey("sparse_aspen_tree");
    public static final ResourceKey<PlacedFeature> RARE_ASPEN_PLACED_TREE_KEY = registerKey("rare_aspen_tree");
    public static final ResourceKey<PlacedFeature> VERY_RARE_ASPEN_PLACED_TREE_KEY = registerKey("super_rare_aspen_tree");
    
    public static final ResourceKey<PlacedFeature> RARE_CHERRY_BLOSSOM_PLACED_TREE_KEY = registerKey("rare_cherry_blossom_tree");
    public static final ResourceKey<PlacedFeature> CHESTNUT_PLACED_TREE_KEY = registerKey("chestnut_tree");
    public static final ResourceKey<PlacedFeature> DARK_OAK_PLACED_TREE_KEY = registerKey("dark_oak_tree");
    public static final ResourceKey<PlacedFeature> COMMON_DARK_OAK_PLACED_TREE_KEY = registerKey("common_dark_oak_tree");
    public static final ResourceKey<PlacedFeature> RARE_MEGA_DARK_OAK_PLACED_TREE_KEY = registerKey("rare_mega_dark_oak_tree");
    public static final ResourceKey<PlacedFeature> MEGA_DARK_OAK_PLACED_TREE_KEY = registerKey("mega_dark_oak_tree");
    public static final ResourceKey<PlacedFeature> MEGA_DARK_OAK_PLACED_COMMON_TREE_KEY = registerKey("mega_dark_oak_common_tree");
    public static final ResourceKey<PlacedFeature> DEADWOOD_TREE_KEY = registerKey("deadwood_tree");
    public static final ResourceKey<PlacedFeature> COMMON_FIR_PLACED_TREE_KEY = registerKey("common_fir_tree");
    public static final ResourceKey<PlacedFeature> ABUNDANT_FIR_PLACED_TREE_KEY = registerKey("abundant_common_fir_tree");
    public static final ResourceKey<PlacedFeature> FIR_PLACED_TREE_KEY = registerKey("fir_tree");
    public static final ResourceKey<PlacedFeature> RARE_FIR_PLACED_TREE_KEY = registerKey("rare_fir_tree");
    public static final ResourceKey<PlacedFeature> VERY_RARE_FIR_PLACED_TREE_KEY = registerKey("very_rare_fir_tree");
    public static final ResourceKey<PlacedFeature> HOLLY_PLACED_TREE_KEY = registerKey("holly_tree");
    public static final ResourceKey<PlacedFeature> COMMON_HOLLY_PLACED_TREE_KEY = registerKey("common_holly_tree");
    public static final ResourceKey<PlacedFeature> COMMON_LARCH_PLACED_TREE_KEY = registerKey("abundant_larch_tree");
    public static final ResourceKey<PlacedFeature> LARCH_PLACED_TREE_KEY = registerKey("larch_tree");
    public static final ResourceKey<PlacedFeature> SPARSE_LARCH_PLACED_TREE_KEY = registerKey("sparse_larch_tree");
    public static final ResourceKey<PlacedFeature> RARE_LARCH_PLACED_TREE_KEY = registerKey("rare_larch_tree");
    public static final ResourceKey<PlacedFeature> BLACK_LEBETHRON_PLACED_TREE_KEY = registerKey("black_lebethron_tree");
    public static final ResourceKey<PlacedFeature> WHITE_LEBETHRON_PLACED_TREE_KEY = registerKey("white_lebethron_tree");
    public static final ResourceKey<PlacedFeature> COMMON_BLACK_LEBETHRON_PLACED_TREE_KEY = registerKey("common_black_lebethron_tree");
    public static final ResourceKey<PlacedFeature> COMMON_WHITE_LEBETHRON_PLACED_TREE_KEY = registerKey("common_white_lebethron_tree");
    public static final ResourceKey<PlacedFeature> RARE_BLACK_LEBETHRON_PLACED_TREE_KEY = registerKey("rare_black_lebethron_tree");
    public static final ResourceKey<PlacedFeature> RARE_WHITE_LEBETHRON_PLACED_TREE_KEY = registerKey("rare_white_lebethron_tree");
    public static final ResourceKey<PlacedFeature> VERY_RARE_BLACK_LEBETHRON_PLACED_TREE_KEY = registerKey("very_rare_black_lebethron_tree");
    public static final ResourceKey<PlacedFeature> MALLORN_PLACED_TREE_KEY = registerKey("mallorn_tree");
    public static final ResourceKey<PlacedFeature> SMALL_MALLORN_PLACED_TREE_KEY = registerKey("small_mallorn_tree");
    public static final ResourceKey<PlacedFeature> MALLORN_BUSH_PLACED_TREE_KEY = registerKey("mallorn_bush");
    public static final ResourceKey<PlacedFeature> MALLORN_FLOWERING_BUSH_PLACED_TREE_KEY = registerKey("mallorn_flowering_bush");
    public static final ResourceKey<PlacedFeature> MEGA_MALLORN_PLACED_TREE_KEY = registerKey("mega_mallorn_tree");
    public static final ResourceKey<PlacedFeature> MEGA_MALLORN_STRUCTURE_PLACED_TREE_KEY = registerKey("mega_mallorn_structure_tree");
    public static final ResourceKey<PlacedFeature> SMALL_MIRKWOOD_PLACED_TREE_KEY = registerKey("small_mirkwood_tree");
    public static final ResourceKey<PlacedFeature> MIRKWOOD_PLACED_TREE_KEY = registerKey("mirkwood_tree");
    public static final ResourceKey<PlacedFeature> SPARSE_MIRKWOOD_PLACED_TREE_KEY = registerKey("sparse_mirkwood_tree");
    public static final ResourceKey<PlacedFeature> DEAD_MIRKWOOD_PLACED_TREE_KEY = registerKey("dead_mirkwood_tree");
    public static final ResourceKey<PlacedFeature> UNCOMMON_MIRKWOOD_PLACED_TREE_KEY = registerKey("uncommon_mirkwood_tree");
    public static final ResourceKey<PlacedFeature> MEGA_MIRKWOOD_PLACED_TREE_KEY = registerKey("mega_mirkwood_tree");
    public static final ResourceKey<PlacedFeature> RARE_MEGA_MIRKWOOD_PLACED_TREE_KEY = registerKey("rare_mega_mirkwood_tree");
    public static final ResourceKey<PlacedFeature> VERY_RARE_MEGA_MIRKWOOD_PLACED_TREE_KEY = registerKey("very_rare_mega_mirkwood_tree");
    public static final ResourceKey<PlacedFeature> DEAD_MEGA_MIRKWOOD_PLACED_TREE_KEY = registerKey("dead_mega_mirkwood_tree");
    public static final ResourceKey<PlacedFeature> MAPLE_PLACED_TREE_KEY = registerKey("maple_tree");
    public static final ResourceKey<PlacedFeature> YELLOW_MAPLE_PLACED_TREE_KEY = registerKey("yellow_maple_tree");
    public static final ResourceKey<PlacedFeature> ORANGE_MAPLE_PLACED_TREE_KEY = registerKey("orange_maple_tree");
    public static final ResourceKey<PlacedFeature> RED_MAPLE_PLACED_TREE_KEY = registerKey("red_maple_tree");
    public static final ResourceKey<PlacedFeature> SILVER_MAPLE_PLACED_TREE_KEY = registerKey("silver_maple_tree");
    public static final ResourceKey<PlacedFeature> SILVER_YELLOW_MAPLE_PLACED_TREE_KEY = registerKey("silver_yellow_maple_tree");
    public static final ResourceKey<PlacedFeature> SILVER_ORANGE_MAPLE_PLACED_TREE_KEY = registerKey("silver_orange_maple_tree");
    public static final ResourceKey<PlacedFeature> SILVER_RED_MAPLE_PLACED_TREE_KEY = registerKey("silver_red_maple_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_MAPLE_PLACED_TREE_KEY = registerKey("scarce_maple_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_YELLOW_MAPLE_PLACED_TREE_KEY = registerKey("scarce_yellow_maple_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_ORANGE_MAPLE_PLACED_TREE_KEY = registerKey("scarce_orange_maple_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_RED_MAPLE_PLACED_TREE_KEY = registerKey("scarce_red_maple_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_SILVER_MAPLE_PLACED_TREE_KEY = registerKey("scarce_silver_maple_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY = registerKey("scarce_silver_yellow_maple_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY = registerKey("scarce_silver_orange_maple_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_SILVER_RED_MAPLE_PLACED_TREE_KEY = registerKey("scarce_silver_red_maple_tree");
    public static final ResourceKey<PlacedFeature> COMMON_MAPLE_PLACED_TREE_KEY = registerKey("common_maple_tree");
    public static final ResourceKey<PlacedFeature> COMMON_YELLOW_MAPLE_PLACED_TREE_KEY = registerKey("common_yellow_maple_tree");
    public static final ResourceKey<PlacedFeature> COMMON_ORANGE_MAPLE_PLACED_TREE_KEY = registerKey("common_orange_maple_tree");
    public static final ResourceKey<PlacedFeature> COMMON_RED_MAPLE_PLACED_TREE_KEY = registerKey("common_red_maple_tree");
    public static final ResourceKey<PlacedFeature> COMMON_SILVER_MAPLE_PLACED_TREE_KEY = registerKey("common_silver_maple_tree");
    public static final ResourceKey<PlacedFeature> COMMON_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY = registerKey("common_silver_yellow_maple_tree");
    public static final ResourceKey<PlacedFeature> COMMON_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY = registerKey("common_silver_orange_maple_tree");
    public static final ResourceKey<PlacedFeature> COMMON_SILVER_RED_MAPLE_PLACED_TREE_KEY = registerKey("common_silver_red_maple_tree");
    public static final ResourceKey<PlacedFeature> OAK_BUSH_PLACED_TREE_KEY = registerKey("oak_bush_tree");
    public static final ResourceKey<PlacedFeature> OAK_BUSH_COMMON_PLACED_TREE_KEY = registerKey("oak_bush_common_tree");
    public static final ResourceKey<PlacedFeature> OAK_BUSH_RARE_PLACED_TREE_KEY = registerKey("oak_bush_rare_tree");
    public static final ResourceKey<PlacedFeature> COMMON_OAK_PLACED_TREE_KEY = registerKey("common_oak_tree");
    public static final ResourceKey<PlacedFeature> ABUNDANT_OAK_PLACED_TREE_KEY = registerKey("abundant_oak_tree");
    public static final ResourceKey<PlacedFeature> OAK_PLACED_TREE_KEY = registerKey("oak_tree");
    public static final ResourceKey<PlacedFeature> BEES_OAK_PLACED_TREE_KEY = registerKey("bees_oak_tree");
    public static final ResourceKey<PlacedFeature> RARE_SMALL_SWAMP_OAK_PLACED_TREE_KEY = registerKey("rare_small_swamp_oak_tree");
    public static final ResourceKey<PlacedFeature> RARE_OAK_PLACED_TREE_KEY = registerKey("rare_oak_tree");
    public static final ResourceKey<PlacedFeature> OAK_VINES_PLACED_TREE_KEY = registerKey("oak_vines_tree");
    public static final ResourceKey<PlacedFeature> MEGA_OAK_PLACED_TREE_KEY = registerKey("mega_oak_tree");
    public static final ResourceKey<PlacedFeature> RARE_MEGA_OAK_PLACED_TREE_KEY = registerKey("rare_mega_oak_tree");
    public static final ResourceKey<PlacedFeature> VERY_RARE_MEGA_OAK_PLACED_TREE_KEY = registerKey("very_rare_mega_oak_tree");
    public static final ResourceKey<PlacedFeature> MEGA_OAK_COMMON_PLACED_TREE_KEY = registerKey("mega_oak_common_tree");
    public static final ResourceKey<PlacedFeature> PALM_PLACED_TREE_KEY = registerKey("palm_tree");
    public static final ResourceKey<PlacedFeature> WHITE_PALM_PLACED_TREE_KEY = registerKey("white_palm_tree");
    public static final ResourceKey<PlacedFeature> UNCOMMON_WHITE_PALM_PLACED_TREE_KEY = registerKey("uncommon_white_palm_tree");
    public static final ResourceKey<PlacedFeature> ABUNDANT_PINE_PLACED_TREE_KEY = registerKey("abundant_pine_tree");
    public static final ResourceKey<PlacedFeature> COMMON_PINE_PLACED_TREE_KEY = registerKey("common_pine_tree");
    public static final ResourceKey<PlacedFeature> PINE_PLACED_TREE_KEY = registerKey("pine_tree");
    public static final ResourceKey<PlacedFeature> DEAD_PINE_PLACED_TREE_KEY = registerKey("dead_pine_tree");
    public static final ResourceKey<PlacedFeature> DRY_PINE_PLACED_TREE_KEY = registerKey("dry_pine_tree");
    public static final ResourceKey<PlacedFeature> SPARSE_PINE_PLACED_TREE_KEY = registerKey("sparse_pine_tree");
    public static final ResourceKey<PlacedFeature> DRY_PINE_BUSH_PLACED_TREE_KEY = registerKey("sparse_pine_brush_tree");
    public static final ResourceKey<PlacedFeature> COMMON_BLACK_PINE_PLACED_TREE_KEY = registerKey("common_black_pine_tree");
    public static final ResourceKey<PlacedFeature> BLACK_PINE_PLACED_TREE_KEY = registerKey("black_pine_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_BLACK_PINE_PLACED_TREE_KEY = registerKey("scarce_black_pine_tree");
    public static final ResourceKey<PlacedFeature> DEAD_BLACK_PINE_PLACED_TREE_KEY = registerKey("dead_black_pine_tree");
    public static final ResourceKey<PlacedFeature> COMMON_DEAD_BLACK_PINE_PLACED_TREE_KEY = registerKey("common_dead_black_pine_tree");
    public static final ResourceKey<PlacedFeature> ABUNDANT_DEAD_BLACK_PINE_PLACED_TREE_KEY = registerKey("abundant_dead_black_pine_tree");
    public static final ResourceKey<PlacedFeature> ROTTEN_TREE_KEY = registerKey("rotten_tree");
    public static final ResourceKey<PlacedFeature> SCORCHED_TREE_PLACED_TREE_KEY = registerKey("scorched_tree");
    public static final ResourceKey<PlacedFeature> COMMON_SCORCHED_TREE_PLACED_TREE_KEY = registerKey("common_scorched_tree");
    public static final ResourceKey<PlacedFeature> ABUNDANT_SCORCHED_TREE_PLACED_TREE_KEY = registerKey("abundant_scorched_tree");
    
    public static final ResourceKey<PlacedFeature> FOOTHILLS_SPRUCE_PLACED_TREE_KEY = registerKey("foothills_spruce_tree");
    public static final ResourceKey<PlacedFeature> FREQUENT_SPRUCE_PLACED_TREE_KEY = registerKey("frequent_spruce_tree");
    public static final ResourceKey<PlacedFeature> COMMON_SPRUCE_PLACED_TREE_KEY = registerKey("common_spruce_tree");
    public static final ResourceKey<PlacedFeature> SPRUCE_PLACED_TREE_KEY = registerKey("spruce_tree");
    public static final ResourceKey<PlacedFeature> COMMON_SPRUCE_BUSH_PLACED_TREE_KEY = registerKey("common_spruce_bush_tree");
    public static final ResourceKey<PlacedFeature> SPRUCE_BUSH_PLACED_TREE_KEY = registerKey("spruce_bush_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_SPRUCE_PLACED_TREE_KEY = registerKey("scarce_spruce_tree");
    public static final ResourceKey<PlacedFeature> RARE_SPRUCE_PLACED_TREE_KEY = registerKey("rare_spruce_tree");
    public static final ResourceKey<PlacedFeature> VERY_RARE_SPRUCE_PLACED_TREE_KEY = registerKey("very_rare_spruce_tree");
    public static final ResourceKey<PlacedFeature> EXTREMELY_RARE_SPRUCE_PLACED_TREE_KEY = registerKey("extremely_rare_spruce_tree");

    public static final ResourceKey<PlacedFeature> FOOTHILLS_WHITE_SPRUCE_PLACED_TREE_KEY = registerKey("foothills_white_spruce_tree");
    public static final ResourceKey<PlacedFeature> FREQUENT_WHITE_SPRUCE_PLACED_TREE_KEY = registerKey("frequent_white_spruce_tree");
    public static final ResourceKey<PlacedFeature> COMMON_WHITE_SPRUCE_PLACED_TREE_KEY = registerKey("common_white_spruce_tree");
    public static final ResourceKey<PlacedFeature> WHITE_SPRUCE_PLACED_TREE_KEY = registerKey("white_spruce_tree");
    public static final ResourceKey<PlacedFeature> WHITE_SPRUCE_BUSH_PLACED_TREE_KEY = registerKey("white_spruce_bush_tree");
    public static final ResourceKey<PlacedFeature> COMMON_WHITE_SPRUCE_BUSH_PLACED_TREE_KEY = registerKey("common_white_spruce_bush_tree");
    public static final ResourceKey<PlacedFeature> SCARCE_WHITE_SPRUCE_PLACED_TREE_KEY = registerKey("scarce_white_spruce_tree");
    public static final ResourceKey<PlacedFeature> RARE_WHITE_SPRUCE_PLACED_TREE_KEY = registerKey("rare_white_spruce_tree");
    public static final ResourceKey<PlacedFeature> VERY_RARE_WHITE_SPRUCE_PLACED_TREE_KEY = registerKey("very_rare_white_spruce_tree");
    public static final ResourceKey<PlacedFeature> EXTREMELY_RARE_WHITE_SPRUCE_PLACED_TREE_KEY = registerKey("extremely_rare_white_spruce_tree");

    public static final ResourceKey<PlacedFeature> WILLOW_PLACED_TREE_KEY = registerKey("willow_tree");
    public static final ResourceKey<PlacedFeature> COMMON_WILLOW_PLACED_TREE_KEY = registerKey("common_willow_tree");

    public static final ResourceKey<PlacedFeature> PALE_OAK_PLACED_TREE_KEY = registerKey("pale_oak_tree");
    public static final ResourceKey<PlacedFeature> PALE_MOSS_PATCH_PLACED_KEY = registerKey("pale_moss_patch");


    static PlacementModifier foothillsTree = PlacementUtils.countExtra(5, 0.5f, 1);
    static PlacementModifier abundantTree = PlacementUtils.countExtra(3, 0.5f, 1);
    static PlacementModifier frequentTree = PlacementUtils.countExtra(1, 0.5f, 1);
    static PlacementModifier commonTree = PlacementUtils.countExtra(1, 0.1f, 1);
    static PlacementModifier uncommonTree = PlacementUtils.countExtra(0, 0.5f, 1);
    static PlacementModifier scarceTree = PlacementUtils.countExtra(0, 0.25f, 1);
    static PlacementModifier rareTree = PlacementUtils.countExtra(0, 0.125f, 1);
    static PlacementModifier megaTree = PlacementUtils.countExtra(0, 0.1f, 1);
    static PlacementModifier veryRareTree = PlacementUtils.countExtra(0, 0.05f, 1);
    static PlacementModifier megaRareTree = PlacementUtils.countExtra(0, 0.025f, 1);
    static PlacementModifier specialTree = PlacementUtils.countExtra(0, 0.01f, 1);
    static PlacementModifier superRareTree = PlacementUtils.countExtra(0, 0.0025f, 1);

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.lookup(Registries.CONFIGURED_FEATURE);
        register(context, ACACIA_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(TreeFeatures.ACACIA),
                VegetationPlacements.treePlacement(scarceTree,
                        Blocks.ACACIA_SAPLING));
        register(context, COMMON_ACACIA_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(TreeFeatures.ACACIA),
                VegetationPlacements.treePlacement(frequentTree,
                        Blocks.ACACIA_SAPLING));
        register(context, RARE_ACACIA_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(TreeFeatures.ACACIA),
                VegetationPlacements.treePlacement(veryRareTree,
                        Blocks.ACACIA_SAPLING));

        register(context, BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        Blocks.BIRCH_SAPLING));
        register(context, FALLEN_BIRCH_PLACED_TREE_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.FALLEN_BIRCH_TREE_KEY),
                List.of(PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING), BiomeFilter.biome()));
        register(context, FALLEN_OAK_PLACED_TREE_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.FALLEN_OAK_TREE_KEY),
                List.of(PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)));
        register(context, OAK_BEES_0002_LEAF_LITTER_PLACED_TREE_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_BEES_0002_LEAF_LITTER_KEY),
                List.of(PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)));
        register(context, BIRCH_BEES_0002_LEAF_LITTER_PLACED_TREE_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_BEES_0002_LEAF_LITTER_KEY),
                List.of(PlacementUtils.filteredByBlockSurvival(Blocks.BIRCH_SAPLING)));
        register(context, FANCY_OAK_BEES_0002_LEAF_LITTER_PLACED_TREE_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.FANCY_OAK_BEES_0002_LEAF_LITTER_KEY),
                List.of(PlacementUtils.filteredByBlockSurvival(Blocks.OAK_SAPLING)));
        register(context, BIRCH_AND_OAK_PLACED_TREE_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.TREES_BIRCH_AND_OAK_LEAF_LITTER_KEY),
                VegetationPlacements.treePlacement(commonTree,
                        Blocks.BIRCH_SAPLING));
        register(context, SPARSE_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, RARE_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, VERY_RARE_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacements.treePlacement(megaRareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, MEGA_BIRCH_PLACED_COMMON_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        Blocks.BIRCH_SAPLING));
        register(context, MEGA_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_BIRCH_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        Blocks.BIRCH_SAPLING));
        register(context, COMMON_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        Blocks.BIRCH_SAPLING));

        register(context, ASPEN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ASPEN_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        ModNatureBlocks.ASPEN_SAPLING));
        register(context, SPARSE_ASPEN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ASPEN_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        ModNatureBlocks.ASPEN_SAPLING));
        register(context, RARE_ASPEN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ASPEN_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        ModNatureBlocks.ASPEN_SAPLING));
        register(context, VERY_RARE_ASPEN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ASPEN_TREE_KEY),
                VegetationPlacements.treePlacement(megaRareTree,
                        ModNatureBlocks.ASPEN_SAPLING));
        register(context, COMMON_ASPEN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ASPEN_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        ModNatureBlocks.ASPEN_SAPLING));

        register(context, RARE_CHERRY_BLOSSOM_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(TreeFeatures.CHERRY_BEES_005),
                VegetationPlacements.treePlacement(rareTree,
                        Blocks.CHERRY_SAPLING));

        register(context, CHESTNUT_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.CHESTNUT_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        ModNatureBlocks.CHESTNUT_SAPLING));

        register(context, COMMON_BEECH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BEECH_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        Blocks.BIRCH_SAPLING));
        register(context, BEECH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BEECH_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, RARE_BEECH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BEECH_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, VERY_RARE_BEECH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BEECH_TREE_KEY),
                VegetationPlacements.treePlacement(megaRareTree,
                        Blocks.BIRCH_SAPLING));

        register(context, DARK_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DARK_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        Blocks.DARK_OAK_SAPLING));
        register(context, COMMON_DARK_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DARK_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        Blocks.DARK_OAK_SAPLING));
        register(context, RARE_MEGA_DARK_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_DARK_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        Blocks.DARK_OAK_SAPLING));
        register(context, MEGA_DARK_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_DARK_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        Blocks.DARK_OAK_SAPLING));
        register(context, MEGA_DARK_OAK_PLACED_COMMON_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_DARK_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(abundantTree,
                        Blocks.DARK_OAK_SAPLING));

        register(context, DEADWOOD_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DEADWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.FIR_SAPLING));

        register(context, COMMON_FIR_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.FIR_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        ModNatureBlocks.FIR_SAPLING));
        register(context, ABUNDANT_FIR_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.FIR_TREE_KEY),
                VegetationPlacements.treePlacement(abundantTree,
                        ModNatureBlocks.FIR_SAPLING));
        register(context, FIR_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.FIR_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        ModNatureBlocks.FIR_SAPLING));
        register(context, RARE_FIR_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.FIR_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        ModNatureBlocks.FIR_SAPLING));
        register(context, VERY_RARE_FIR_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.FIR_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        ModNatureBlocks.FIR_SAPLING));

        register(context, HOLLY_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.HOLLY_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        ModNatureBlocks.HOLLY_SAPLING));
        register(context, COMMON_HOLLY_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.HOLLY_TREE_KEY),
                VegetationPlacements.treePlacement(commonTree,
                        ModNatureBlocks.HOLLY_SAPLING));

        register(context, COMMON_LARCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.LARCH_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        ModNatureBlocks.LARCH_SAPLING));
        register(context, LARCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.LARCH_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        ModNatureBlocks.LARCH_SAPLING));
        register(context, SPARSE_LARCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.LARCH_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        ModNatureBlocks.LARCH_SAPLING));
        register(context, RARE_LARCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.LARCH_TREE_KEY),
                VegetationPlacements.treePlacement(megaRareTree,
                        ModNatureBlocks.LARCH_SAPLING));

        register(context, BLACK_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, WHITE_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY),
                VegetationPlacements.treePlacement(specialTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, COMMON_BLACK_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacements.treePlacement(abundantTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, COMMON_WHITE_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY),
                VegetationPlacements.treePlacement(megaRareTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, RARE_BLACK_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, RARE_WHITE_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.005f, 1),
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, VERY_RARE_BLACK_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));

        register(context, MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MALLORN_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(13, 0.2f, 2),
                        ModNatureBlocks.MALLORN_SAPLING));
        register(context, SMALL_MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SMALL_MALLORN_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(4, 0.25f, 1),
                        ModNatureBlocks.MALLORN_SAPLING));
        register(context, MEGA_MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_MALLORN_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(7, 0.2f, 1),
                        ModNatureBlocks.MALLORN_SAPLING));
        register(context, MEGA_MALLORN_STRUCTURE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_MALLORN_STRUCTURE_TREE_KEY),
                List.of());
        register(context, MALLORN_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MALLORN_BUSH_KEY),
                VegetationPlacements.treePlacement(commonTree, ModNatureBlocks.MALLORN_SAPLING));
        register(context, MALLORN_FLOWERING_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MALLORN_FLOWERING_BUSH_KEY),
                VegetationPlacements.treePlacement(uncommonTree, ModNatureBlocks.MALLORN_SAPLING));

        register(context, SMALL_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SMALL_MIRKWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(15, 0.25f, 2),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, SPARSE_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MIRKWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, DEAD_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DEAD_MIRKWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.5f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, UNCOMMON_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MIRKWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.5f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MIRKWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(21, 0.2f, 4),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, MEGA_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_MIRKWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(18, 0.2f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, RARE_MEGA_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_MIRKWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(2, 0.5f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, VERY_RARE_MEGA_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_MIRKWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.05f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, DEAD_MEGA_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DEAD_MEGA_MIRKWOOD_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 0.125f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));

        register(context, MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, YELLOW_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.YELLOW_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, ORANGE_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ORANGE_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, RED_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.RED_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, SILVER_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SILVER_YELLOW_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_YELLOW_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SILVER_ORANGE_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_ORANGE_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SILVER_RED_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_RED_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SCARCE_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(superRareTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, SCARCE_YELLOW_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.YELLOW_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(superRareTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, SCARCE_ORANGE_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ORANGE_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(superRareTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, SCARCE_RED_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.RED_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(superRareTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, SCARCE_SILVER_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(superRareTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SCARCE_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_YELLOW_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(superRareTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SCARCE_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_ORANGE_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(superRareTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SCARCE_SILVER_RED_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_RED_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(superRareTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));

        register(context, COMMON_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, COMMON_YELLOW_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.YELLOW_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, COMMON_ORANGE_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ORANGE_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, COMMON_RED_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.RED_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, COMMON_SILVER_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, COMMON_SILVER_YELLOW_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_YELLOW_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, COMMON_SILVER_ORANGE_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_ORANGE_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, COMMON_SILVER_RED_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_RED_MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));

        register(context, OAK_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_BUSH_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        Blocks.OAK_SAPLING));
        register(context, OAK_BUSH_COMMON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_BUSH_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        Blocks.OAK_SAPLING));
        register(context, OAK_BUSH_RARE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_BUSH_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        Blocks.OAK_SAPLING));
        register(context, COMMON_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        Blocks.OAK_SAPLING));
        register(context, ABUNDANT_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_TREE_KEY),
                VegetationPlacements.treePlacement(abundantTree,
                        Blocks.OAK_SAPLING));
        register(context, OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        Blocks.OAK_SAPLING));
        register(context, BEES_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BEES_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        Blocks.OAK_SAPLING));
        register(context, RARE_SMALL_SWAMP_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_SMALL_TREE_VINES_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        Blocks.OAK_SAPLING));
        register(context, RARE_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        Blocks.OAK_SAPLING));
        register(context, OAK_VINES_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_TREE_VINES_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        Blocks.OAK_SAPLING));
        register(context, MEGA_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(megaTree,
                        Blocks.OAK_SAPLING));
        register(context, MEGA_OAK_COMMON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(abundantTree,
                        Blocks.OAK_SAPLING));
        register(context, RARE_MEGA_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        Blocks.OAK_SAPLING));
        register(context, VERY_RARE_MEGA_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(specialTree,
                        Blocks.OAK_SAPLING));

        register(context, PALM_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PALM_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        Blocks.OAK_SAPLING));
        register(context, WHITE_PALM_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_PALM_TREE_KEY),
                VegetationPlacements.treePlacement(specialTree,
                        Blocks.OAK_SAPLING));
        register(context, UNCOMMON_WHITE_PALM_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_PALM_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree));

        register(context, ABUNDANT_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacements.treePlacement(foothillsTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, COMMON_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, DEAD_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DEAD_PINE_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, DRY_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DRY_PINE_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, SPARSE_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, DRY_PINE_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DRY_PINE_BUSH_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        ModNatureBlocks.PINE_SAPLING));

        register(context, COMMON_BLACK_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_PINE_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, BLACK_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_PINE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, SCARCE_BLACK_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_PINE_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, DEAD_BLACK_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DEAD_BLACK_PINE_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, COMMON_DEAD_BLACK_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DEAD_BLACK_PINE_TREE_KEY),
                VegetationPlacements.treePlacement(commonTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, ABUNDANT_DEAD_BLACK_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DEAD_BLACK_PINE_TREE_KEY),
                VegetationPlacements.treePlacement(abundantTree,
                        ModNatureBlocks.PINE_SAPLING));

        register(context, ROTTEN_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ROTTEN_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.PINE_SAPLING));

        register(context, SCORCHED_TREE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SCORCHED_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, COMMON_SCORCHED_TREE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SCORCHED_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, ABUNDANT_SCORCHED_TREE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SCORCHED_TREE_KEY),
                VegetationPlacements.treePlacement(abundantTree,
                        ModNatureBlocks.PINE_SAPLING));

        register(context, FOOTHILLS_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(foothillsTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, FREQUENT_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, COMMON_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(commonTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, SCARCE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, RARE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, VERY_RARE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, EXTREMELY_RARE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(specialTree,
                        Blocks.SPRUCE_SAPLING));

        register(context, FOOTHILLS_WHITE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(foothillsTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));
        register(context, FREQUENT_WHITE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));
        register(context, COMMON_WHITE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(commonTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));
        register(context, WHITE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));
        register(context, SCARCE_WHITE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(scarceTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));
        register(context, RARE_WHITE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(rareTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));
        register(context, VERY_RARE_WHITE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(veryRareTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));
        register(context, EXTREMELY_RARE_WHITE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_TREE_KEY),
                VegetationPlacements.treePlacement(specialTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));

        register(context, COMMON_SPRUCE_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_BUSH_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, SPRUCE_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_BUSH_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        Blocks.SPRUCE_SAPLING));

        register(context, COMMON_WHITE_SPRUCE_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_BUSH_TREE_KEY),
                VegetationPlacements.treePlacement(frequentTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));
        register(context, WHITE_SPRUCE_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_SPRUCE_BUSH_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.WHITE_SPRUCE_SAPLING));

        register(context, WILLOW_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WILLOW_TREE_KEY),
                VegetationPlacements.treePlacement(uncommonTree,
                        ModNatureBlocks.WILLOW_SAPLING));
        register(context, COMMON_WILLOW_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WILLOW_TREE_KEY),
                VegetationPlacements.treePlacement(commonTree,
                        ModNatureBlocks.WILLOW_SAPLING));

        register(context, PALE_OAK_PLACED_TREE_KEY,
                configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PALE_OAK_TREE_KEY),
                VegetationPlacements.treePlacement(megaRareTree, ModNatureBlocks.PALE_OAK_SAPLING));
        register(context, PALE_MOSS_PATCH_PLACED_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PALE_MOSS_PATCH_KEY),
                List.of(
                        CountPlacement.of(1),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                        BiomeFilter.biome()));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }

    private static void register(BootstrapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
