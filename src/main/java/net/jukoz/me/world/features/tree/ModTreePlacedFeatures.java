package net.jukoz.me.world.features.tree;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModNatureBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;

import java.util.List;

public class ModTreePlacedFeatures {
    public static final RegistryKey<PlacedFeature> ACACIA_PLACED_TREE_KEY = registerKey("acacia_tree");
    public static final RegistryKey<PlacedFeature> COMMON_BEECH_PLACED_TREE_KEY = registerKey("common_beech_tree");
    public static final RegistryKey<PlacedFeature> BEECH_PLACED_TREE_KEY = registerKey("beech_tree");
    public static final RegistryKey<PlacedFeature> RARE_BEECH_PLACED_TREE_KEY = registerKey("rare_beech_tree");
    public static final RegistryKey<PlacedFeature> VERY_RARE_BEECH_PLACED_TREE_KEY = registerKey("very_rare_beech_tree");
    public static final RegistryKey<PlacedFeature> BIRCH_PLACED_TREE_KEY = registerKey("birch_tree");
    public static final RegistryKey<PlacedFeature> SPARSE_BIRCH_PLACED_TREE_KEY = registerKey("sparse_birch_tree");
    public static final RegistryKey<PlacedFeature> RARE_BIRCH_PLACED_TREE_KEY = registerKey("rare_birch_tree");
    public static final RegistryKey<PlacedFeature> VERY_RARE_BIRCH_PLACED_TREE_KEY = registerKey("super_rare_birch_tree");
    public static final RegistryKey<PlacedFeature> MEGA_BIRCH_PLACED_COMMON_TREE_KEY = registerKey("mega_birch_common_tree");
    public static final RegistryKey<PlacedFeature> MEGA_BIRCH_PLACED_TREE_KEY = registerKey("mega_birch_tree");
    public static final RegistryKey<PlacedFeature> MEGA_DARK_OAK_PLACED_TREE_KEY = registerKey("mega_dark_oak_tree");
    public static final RegistryKey<PlacedFeature> MEGA_DARK_OAK_PLACED_COMMON_TREE_KEY = registerKey("mega_dark_oak_common_tree");
    public static final RegistryKey<PlacedFeature> COMMON_LARCH_PLACED_TREE_KEY = registerKey("abundant_larch_tree");
    public static final RegistryKey<PlacedFeature> LARCH_PLACED_TREE_KEY = registerKey("larch_tree");
    public static final RegistryKey<PlacedFeature> SPARSE_LARCH_PLACED_TREE_KEY = registerKey("sparse_larch_tree");
    public static final RegistryKey<PlacedFeature> BLACK_LEBETHRON_PLACED_TREE_KEY = registerKey("black_lebethron_tree");
    public static final RegistryKey<PlacedFeature> WHITE_LEBETHRON_PLACED_TREE_KEY = registerKey("white_lebethron_tree");
    public static final RegistryKey<PlacedFeature> COMMON_BLACK_LEBETHRON_PLACED_TREE_KEY = registerKey("common_black_lebethron_tree");
    public static final RegistryKey<PlacedFeature> COMMON_WHITE_LEBETHRON_PLACED_TREE_KEY = registerKey("common_white_lebethron_tree");
    public static final RegistryKey<PlacedFeature> RARE_BLACK_LEBETHRON_PLACED_TREE_KEY = registerKey("rare_black_lebethron_tree");
    public static final RegistryKey<PlacedFeature> RARE_WHITE_LEBETHRON_PLACED_TREE_KEY = registerKey("rare_white_lebethron_tree");
    public static final RegistryKey<PlacedFeature> MALLORN_PLACED_TREE_KEY = registerKey("mallorn_tree");
    public static final RegistryKey<PlacedFeature> SMALL_MALLORN_PLACED_TREE_KEY = registerKey("small_mallorn_tree");
    public static final RegistryKey<PlacedFeature> MALLORN_BUSH_PLACED_TREE_KEY = registerKey("mallorn_bush");
    public static final RegistryKey<PlacedFeature> MEGA_MALLORN_PLACED_TREE_KEY = registerKey("mega_mallorn_tree");
    public static final RegistryKey<PlacedFeature> SMALL_MIRKWOOD_PLACED_TREE_KEY = registerKey("small_mirkwood_tree");
    public static final RegistryKey<PlacedFeature> MIRKWOOD_PLACED_TREE_KEY = registerKey("mirkwood_tree");
    public static final RegistryKey<PlacedFeature> SPARSE_MIRKWOOD_PLACED_TREE_KEY = registerKey("sparse_mirkwood_tree");
    public static final RegistryKey<PlacedFeature> MEGA_MIRKWOOD_PLACED_TREE_KEY = registerKey("mega_mirkwood_tree");
    public static final RegistryKey<PlacedFeature> MAPLE_PLACED_TREE_KEY = registerKey("maple_tree");
    public static final RegistryKey<PlacedFeature> YELLOW_MAPLE_PLACED_TREE_KEY = registerKey("yellow_maple_tree");
    public static final RegistryKey<PlacedFeature> ORANGE_MAPLE_PLACED_TREE_KEY = registerKey("orange_maple_tree");
    public static final RegistryKey<PlacedFeature> RED_MAPLE_PLACED_TREE_KEY = registerKey("red_maple_tree");
    public static final RegistryKey<PlacedFeature> SILVER_MAPLE_PLACED_TREE_KEY = registerKey("silver_maple_tree");
    public static final RegistryKey<PlacedFeature> SILVER_YELLOW_MAPLE_PLACED_TREE_KEY = registerKey("silver_yellow_maple_tree");
    public static final RegistryKey<PlacedFeature> SILVER_ORANGE_MAPLE_PLACED_TREE_KEY = registerKey("silver_orange_maple_tree");
    public static final RegistryKey<PlacedFeature> SILVER_RED_MAPLE_PLACED_TREE_KEY = registerKey("silver_red_maple_tree");
    public static final RegistryKey<PlacedFeature> OAK_BUSH_PLACED_TREE_KEY = registerKey("oak_bush_tree");
    public static final RegistryKey<PlacedFeature> OAK_BUSH_COMMON_PLACED_TREE_KEY = registerKey("oak_bush_common_tree");
    public static final RegistryKey<PlacedFeature> COMMON_OAK_PLACED_TREE_KEY = registerKey("common_oak_tree");
    public static final RegistryKey<PlacedFeature> OAK_PLACED_TREE_KEY = registerKey("oak_tree");
    public static final RegistryKey<PlacedFeature> RARE_SMALL_SWAMP_OAK_PLACED_TREE_KEY = registerKey("rare_small_swamp_oak_tree");
    public static final RegistryKey<PlacedFeature> RARE_OAK_PLACED_TREE_KEY = registerKey("rare_oak_tree");
    public static final RegistryKey<PlacedFeature> OAK_VINES_PLACED_TREE_KEY = registerKey("oak_vines_tree");
    public static final RegistryKey<PlacedFeature> MEGA_OAK_PLACED_TREE_KEY = registerKey("mega_oak_tree");
    public static final RegistryKey<PlacedFeature> RARE_MEGA_OAK_PLACED_TREE_KEY = registerKey("rare_mega_oak_tree");
    public static final RegistryKey<PlacedFeature> VERY_RARE_MEGA_OAK_PLACED_TREE_KEY = registerKey("very_rare_mega_oak_tree");
    public static final RegistryKey<PlacedFeature> MEGA_OAK_COMMON_PLACED_TREE_KEY = registerKey("mega_oak_common_tree");
    public static final RegistryKey<PlacedFeature> PALM_PLACED_TREE_KEY = registerKey("palm_tree");
    public static final RegistryKey<PlacedFeature> WHITE_PALM_PLACED_TREE_KEY = registerKey("white_palm_tree");
    public static final RegistryKey<PlacedFeature> ABUNDANT_PINE_PLACED_TREE_KEY = registerKey("abundant_pine_tree");
    public static final RegistryKey<PlacedFeature> COMMON_PINE_PLACED_TREE_KEY = registerKey("common_pine_tree");
    public static final RegistryKey<PlacedFeature> PINE_PLACED_TREE_KEY = registerKey("pine_tree");
    public static final RegistryKey<PlacedFeature> DEAD_PINE_PLACED_TREE_KEY = registerKey("dead_pine_tree");
    public static final RegistryKey<PlacedFeature> SPARSE_PINE_PLACED_TREE_KEY = registerKey("sparse_pine_tree");
    public static final RegistryKey<PlacedFeature> FOOTHILLS_SPRUCE_PLACED_TREE_KEY = registerKey("foothills_spruce_tree");
    public static final RegistryKey<PlacedFeature> COMMON_SPRUCE_PLACED_TREE_KEY = registerKey("common_spruce_tree");
    public static final RegistryKey<PlacedFeature> SPRUCE_PLACED_TREE_KEY = registerKey("spruce_tree");
    public static final RegistryKey<PlacedFeature> COMMON_SPRUCE_BUSH_PLACED_TREE_KEY = registerKey("common_spruce_bush_tree");
    public static final RegistryKey<PlacedFeature> SPRUCE_BUSH_PLACED_TREE_KEY = registerKey("spruce_bush_tree");
    public static final RegistryKey<PlacedFeature> SCARCE_SPRUCE_PLACED_TREE_KEY = registerKey("scarce_spruce_tree");
    public static final RegistryKey<PlacedFeature> RARE_SPRUCE_PLACED_TREE_KEY = registerKey("rare_spruce_tree");
    public static final RegistryKey<PlacedFeature> VERY_RARE_SPRUCE_PLACED_TREE_KEY = registerKey("very_rare_spruce_tree");
    public static final RegistryKey<PlacedFeature> WILLOW_PLACED_TREE_KEY = registerKey("willow_tree");

    static PlacementModifier foothillsTree = PlacedFeatures.createCountExtraModifier(5, 0.5f, 1);
    static PlacementModifier abundantTree = PlacedFeatures.createCountExtraModifier(3, 0.5f, 1);
    static PlacementModifier frequentTree = PlacedFeatures.createCountExtraModifier(1, 0.5f, 1);
    static PlacementModifier commonTree = PlacedFeatures.createCountExtraModifier(1, 0.1f, 1);
    static PlacementModifier uncommonTree = PlacedFeatures.createCountExtraModifier(0, 0.5f, 1);
    static PlacementModifier scarceTree = PlacedFeatures.createCountExtraModifier(0, 0.25f, 1);
    static PlacementModifier rareTree = PlacedFeatures.createCountExtraModifier(0, 0.125f, 1);
    static PlacementModifier megaTree = PlacedFeatures.createCountExtraModifier(0, 0.1f, 1);
    static PlacementModifier veryRareTree = PlacedFeatures.createCountExtraModifier(0, 0.05f, 1);
    static PlacementModifier megaRareTree = PlacedFeatures.createCountExtraModifier(0, 0.025f, 1);
    static PlacementModifier specialTree = PlacedFeatures.createCountExtraModifier(0, 0.01f, 1);
    static PlacementModifier superRareTree = PlacedFeatures.createCountExtraModifier(0, 0.005f, 1);

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(context, ACACIA_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(TreeConfiguredFeatures.ACACIA),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(scarceTree,
                        Blocks.ACACIA_SAPLING));

        register(context, BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(scarceTree,
                        Blocks.BIRCH_SAPLING));
        register(context, SPARSE_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, RARE_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(veryRareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, VERY_RARE_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(megaRareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, MEGA_BIRCH_PLACED_COMMON_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(uncommonTree,
                        Blocks.BIRCH_SAPLING));
        register(context, MEGA_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(megaTree,
                        Blocks.BIRCH_SAPLING));

        register(context, COMMON_BEECH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BEECH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(uncommonTree,
                        Blocks.BIRCH_SAPLING));
        register(context, BEECH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BEECH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, RARE_BEECH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BEECH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(veryRareTree,
                        Blocks.BIRCH_SAPLING));
        register(context, VERY_RARE_BEECH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BEECH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(megaRareTree,
                        Blocks.BIRCH_SAPLING));

        register(context, MEGA_DARK_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_DARK_OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(frequentTree,
                        Blocks.DARK_OAK_SAPLING));
        register(context, MEGA_DARK_OAK_PLACED_COMMON_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_DARK_OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(abundantTree,
                        Blocks.DARK_OAK_SAPLING));

        register(context, COMMON_LARCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.LARCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(frequentTree,
                        ModNatureBlocks.LARCH_SAPLING));
        register(context, LARCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.LARCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        ModNatureBlocks.LARCH_SAPLING));
        register(context, SPARSE_LARCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.LARCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(veryRareTree,
                        ModNatureBlocks.LARCH_SAPLING));

        register(context, BLACK_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(frequentTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, WHITE_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(specialTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, COMMON_BLACK_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(abundantTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, COMMON_WHITE_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(megaRareTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, RARE_BLACK_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, RARE_WHITE_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.005f, 1),
                        ModNatureBlocks.LEBETHRON_SAPLING));

        register(context, MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MALLORN_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(13, 0.2f, 2),
                        ModNatureBlocks.MALLORN_SAPLING));
        register(context, SMALL_MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SMALL_MALLORN_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(4, 0.25f, 1),
                        ModNatureBlocks.MALLORN_SAPLING));
        register(context, MEGA_MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_MALLORN_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(8, 0.2f, 1),
                        ModNatureBlocks.MALLORN_SAPLING));
        register(context, MALLORN_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MALLORN_BUSH_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(commonTree, ModNatureBlocks.MALLORN_SAPLING));

        register(context, SMALL_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SMALL_MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(15, 0.25f, 2),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, SPARSE_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.1f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(21, 0.2f, 2),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, MEGA_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(18, 0.2f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));

        register(context, MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MAPLE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(superRareTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, YELLOW_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.YELLOW_MAPLE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(superRareTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, ORANGE_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.ORANGE_MAPLE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(superRareTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, RED_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.RED_MAPLE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(superRareTree,
                        ModNatureBlocks.MAPLE_SAPLING));
        register(context, SILVER_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_MAPLE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(superRareTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SILVER_YELLOW_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_YELLOW_MAPLE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(superRareTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SILVER_ORANGE_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_ORANGE_MAPLE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(superRareTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));
        register(context, SILVER_RED_MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SILVER_RED_MAPLE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(superRareTree,
                        ModNatureBlocks.SILVER_MAPLE_SAPLING));

        register(context, OAK_BUSH_COMMON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_BUSH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(frequentTree,
                        Blocks.OAK_SAPLING));
        register(context, OAK_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_BUSH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(uncommonTree,
                        Blocks.OAK_SAPLING));
        register(context, COMMON_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(uncommonTree,
                        Blocks.OAK_SAPLING));
        register(context, OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        Blocks.OAK_SAPLING));
        register(context, RARE_SMALL_SWAMP_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(TreeConfiguredFeatures.SWAMP_OAK),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        Blocks.OAK_SAPLING));
        register(context, RARE_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(veryRareTree,
                        Blocks.OAK_SAPLING));
        register(context, OAK_VINES_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.OAK_TREE_VINES_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(frequentTree,
                        Blocks.OAK_SAPLING));
        register(context, MEGA_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(megaTree,
                        Blocks.OAK_SAPLING));
        register(context, MEGA_OAK_COMMON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(abundantTree,
                        Blocks.OAK_SAPLING));
        register(context, RARE_MEGA_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(veryRareTree,
                        Blocks.OAK_SAPLING));
        register(context, VERY_RARE_MEGA_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(specialTree,
                        Blocks.OAK_SAPLING));

        register(context, PALM_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PALM_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        Blocks.OAK_SAPLING));
        register(context, WHITE_PALM_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_PALM_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(specialTree,
                        Blocks.OAK_SAPLING));

        register(context, ABUNDANT_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(foothillsTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, COMMON_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(frequentTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(uncommonTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, DEAD_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.DEAD_PINE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(scarceTree,
                        ModNatureBlocks.PINE_SAPLING));
        register(context, SPARSE_PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        ModNatureBlocks.PINE_SAPLING));

        register(context, FOOTHILLS_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(foothillsTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, COMMON_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(frequentTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(uncommonTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, SCARCE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(scarceTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, RARE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, VERY_RARE_SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(veryRareTree,
                        Blocks.SPRUCE_SAPLING));

        register(context, COMMON_SPRUCE_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_BUSH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(frequentTree,
                        Blocks.SPRUCE_SAPLING));
        register(context, SPRUCE_BUSH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_BUSH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(uncommonTree,
                        Blocks.SPRUCE_SAPLING));

        register(context, WILLOW_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WILLOW_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(uncommonTree,
                        ModNatureBlocks.WILLOW_SAPLING));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
