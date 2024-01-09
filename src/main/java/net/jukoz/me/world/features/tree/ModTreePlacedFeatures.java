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
import net.minecraft.world.gen.placementmodifier.RarityFilterPlacementModifier;

import java.util.List;

public class ModTreePlacedFeatures {
    public static final RegistryKey<PlacedFeature> BIRCH_PLACED_TREE_KEY = registerKey("birch_tree");
    public static final RegistryKey<PlacedFeature> MEGA_BIRCH_PLACED_TREE_KEY = registerKey("mega_birch_tree");
    public static final RegistryKey<PlacedFeature> LARCH_PLACED_TREE_KEY = registerKey("larch_tree");
    public static final RegistryKey<PlacedFeature> BLACK_LEBETHRON_PLACED_TREE_KEY = registerKey("black_lebethron_tree");
    public static final RegistryKey<PlacedFeature> WHITE_LEBETHRON_PLACED_TREE_KEY = registerKey("white_lebethron_tree");
    public static final RegistryKey<PlacedFeature> MALLORN_PLACED_TREE_KEY = registerKey("mallorn_tree");
    public static final RegistryKey<PlacedFeature> MEGA_MALLORN_PLACED_TREE_KEY = registerKey("mega_mallorn_tree");
    public static final RegistryKey<PlacedFeature> MIRKWOOD_PLACED_TREE_KEY = registerKey("mirkwood_tree");
    public static final RegistryKey<PlacedFeature> SPARSE_MIRKWOOD_PLACED_TREE_KEY = registerKey("sparse_mirkwood_tree");
    public static final RegistryKey<PlacedFeature> MEGA_MIRKWOOD_PLACED_TREE_KEY = registerKey("mega_mirkwood_tree");
    public static final RegistryKey<PlacedFeature> MAPLE_PLACED_TREE_KEY = registerKey("maple_tree");
    public static final RegistryKey<PlacedFeature> MEGA_OAK_PLACED_TREE_KEY = registerKey("mega_oak_tree");
    public static final RegistryKey<PlacedFeature> PINE_PLACED_TREE_KEY = registerKey("pine_tree");
    public static final RegistryKey<PlacedFeature> SPRUCE_PLACED_TREE_KEY = registerKey("spruce_tree");
    public static final RegistryKey<PlacedFeature> WILLOW_PLACED_TREE_KEY = registerKey("willow_tree");

    static PlacementModifier placementModifier = PlacedFeatures.createCountExtraModifier(1, 0.2f, 1);
    static PlacementModifier rareTree = PlacedFeatures.createCountExtraModifier(0, 0.2f, 1);
    static PlacementModifier veryRareTree = PlacedFeatures.createCountExtraModifier(0, 0.05f, 1);

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(context, BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.25f, 1),
                        Blocks.BIRCH_SAPLING));
        register(context, MEGA_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1f, 1),
                        Blocks.BIRCH_SAPLING));

        register(context, LARCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.LARCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        ModNatureBlocks.LARCH_SAPLING));

        register(context, BLACK_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.5f, 1),
                        ModNatureBlocks.LEBETHRON_SAPLING));
        register(context, WHITE_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WHITE_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(veryRareTree,
                        ModNatureBlocks.LEBETHRON_SAPLING));

        register(context, MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MALLORN_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(13, 0.2f, 2),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, MEGA_MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_MALLORN_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(8, 0.2f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));

        register(context, MEGA_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(18, 0.2f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, SPARSE_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.1f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(21, 0.2f, 2),
                        ModNatureBlocks.MIRKWOOD_SAPLING));

        register(context, MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MAPLE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(placementModifier,
                        ModNatureBlocks.MAPLE_SAPLING));

        register(context, MEGA_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.MEGA_OAK_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(rareTree,
                        Blocks.OAK_SAPLING));

        register(context, PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.2f, 1),
                        ModNatureBlocks.PINE_SAPLING));

        register(context, SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.2f, 1),
                        Blocks.SPRUCE_SAPLING));

        register(context, WILLOW_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModTreeConfiguredFeatures.WILLOW_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.2f, 1),
                        ModNatureBlocks.WILLOW_SAPLING));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
