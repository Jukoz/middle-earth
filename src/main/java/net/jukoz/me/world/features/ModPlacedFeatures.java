package net.jukoz.me.world.features;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
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

public class ModPlacedFeatures {
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

    public static void bootstrap(Registerable<PlacedFeature> context) {
        var configuredFeatureRegistryEntryLookup = context.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        register(context, BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.25f, 1),
                        Blocks.BIRCH_SAPLING));
        register(context, MEGA_BIRCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MEGA_BIRCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1f, 1),
                        Blocks.BIRCH_SAPLING));

        register(context, LARCH_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.LARCH_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(9, 0.2f, 2),
                        Blocks.BIRCH_SAPLING));

        register(context, BLACK_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(9, 0.2f, 2),
                        Blocks.BIRCH_SAPLING));
        register(context, WHITE_LEBETHRON_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.BLACK_LEBETHRON_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(0, 0.1f, 1),
                        Blocks.BIRCH_SAPLING));

        register(context, MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MALLORN_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(13, 0.2f, 2),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, MEGA_MALLORN_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MEGA_MALLORN_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(8, 0.2f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));

        register(context, MEGA_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MEGA_MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(18, 0.2f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, SPARSE_MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(1, 0.1f, 1),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, MIRKWOOD_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MIRKWOOD_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(21, 0.2f, 2),
                        ModNatureBlocks.MIRKWOOD_SAPLING));

        register(context, MAPLE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MALLORN_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(13, 0.2f, 2),
                        ModNatureBlocks.MIRKWOOD_SAPLING));
        register(context, MEGA_OAK_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.MALLORN_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(9, 0.2f, 2),
                        Blocks.OAK_SAPLING));

        register(context, PINE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.PINE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(7, 0.2f, 2),
                        Blocks.SPRUCE_SAPLING));

        register(context, SPRUCE_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.SPRUCE_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(9, 0.2f, 2),
                        Blocks.SPRUCE_SAPLING));

        register(context, WILLOW_PLACED_TREE_KEY, configuredFeatureRegistryEntryLookup.getOrThrow(ModConfiguredFeatures.WILLOW_TREE_KEY),
                VegetationPlacedFeatures.treeModifiersWithWouldSurvive(PlacedFeatures.createCountExtraModifier(3, 0.2f, 1),
                        Blocks.OAK_SAPLING));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }

    private static void register(Registerable<PlacedFeature> context, RegistryKey<PlacedFeature> key, RegistryEntry<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}