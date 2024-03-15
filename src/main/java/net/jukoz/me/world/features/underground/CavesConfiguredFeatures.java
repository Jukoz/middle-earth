package net.jukoz.me.world.features.underground;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.OreRockSets;
import net.jukoz.me.block.StoneBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import org.apache.http.params.CoreConnectionPNames;

import java.util.List;

public class CavesConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> GLOWSTONE_GEODE = registerKey("glowstone_geode");
    public static final RegistryKey<ConfiguredFeature<?, ?>> RED_AGATE_GEODE = registerKey("red_agate_geode");
    public static final RegistryKey<ConfiguredFeature<?, ?>> QUARTZ_GEODE = registerKey("quartz_geode");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_MAGMA = registerKey("ore_magma");

    // region Material Ores
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_COAL = registerKey("ore_coal");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_COPPER = registerKey("ore_copper");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_TIN = registerKey("ore_tin");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_LAPIS = registerKey("ore_lapis");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_LEAD = registerKey("ore_lead");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_IRON = registerKey("ore_iron");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_SILVER = registerKey("ore_silver");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_JADE = registerKey("ore_jade");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_GOLD = registerKey("ore_gold");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_MITHRIL = registerKey("ore_mithril");
    // endregion

    static TagMatchRuleTest stoneTest = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
    static BlockMatchRuleTest ashenStoneTest = new BlockMatchRuleTest(StoneBlockSets.ASHEN_STONE.base());
    static BlockMatchRuleTest frozenStoneTest = new BlockMatchRuleTest(StoneBlockSets.FROZEN_STONE.base());
    static BlockMatchRuleTest gonluinTest = new BlockMatchRuleTest(StoneBlockSets.GONLUIN.base());
    static BlockMatchRuleTest limestoneTest = new BlockMatchRuleTest(StoneBlockSets.LIMESTONE.base());
    static TagMatchRuleTest deepslateTest = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    static BlockMatchRuleTest diftominTest = new BlockMatchRuleTest(StoneBlockSets.DIFTOMIN.base());
    static BlockMatchRuleTest epmostoTest = new BlockMatchRuleTest(StoneBlockSets.EPMOSTO.base());

    static List<OreFeatureConfig.Target> coalList = List.of(
            OreFeatureConfig.createTarget(stoneTest, Blocks.COAL_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(ashenStoneTest, OreRockSets.ASHEN.coal_ore().getDefaultState()),
            OreFeatureConfig.createTarget(frozenStoneTest, OreRockSets.FROZEN.coal_ore().getDefaultState()),
            OreFeatureConfig.createTarget(gonluinTest, OreRockSets.GONLUIN.coal_ore().getDefaultState()),
            OreFeatureConfig.createTarget(limestoneTest, OreRockSets.LIMESTONE.coal_ore().getDefaultState()),
            OreFeatureConfig.createTarget(deepslateTest, Blocks.DEEPSLATE_COAL_ORE.getDefaultState()));
    static List<OreFeatureConfig.Target> copperList = List.of(
            OreFeatureConfig.createTarget(stoneTest, Blocks.COPPER_ORE.getDefaultState()),
            OreFeatureConfig.createTarget(ashenStoneTest, OreRockSets.ASHEN.copper_ore().getDefaultState()),
            OreFeatureConfig.createTarget(frozenStoneTest, OreRockSets.FROZEN.copper_ore().getDefaultState()),
            OreFeatureConfig.createTarget(gonluinTest, OreRockSets.GONLUIN.copper_ore().getDefaultState()),
            OreFeatureConfig.createTarget(limestoneTest, OreRockSets.LIMESTONE.copper_ore().getDefaultState()),
            OreFeatureConfig.createTarget(deepslateTest, Blocks.DEEPSLATE_COPPER_ORE.getDefaultState()));
    static List<OreFeatureConfig.Target> tinList = List.of(
            OreFeatureConfig.createTarget(stoneTest, OreRockSets.STONE.tin_ore().getDefaultState()),
            OreFeatureConfig.createTarget(ashenStoneTest, OreRockSets.ASHEN.coal_ore().getDefaultState()),
            OreFeatureConfig.createTarget(frozenStoneTest, OreRockSets.FROZEN.coal_ore().getDefaultState()),
            OreFeatureConfig.createTarget(gonluinTest, OreRockSets.GONLUIN.coal_ore().getDefaultState()),
            OreFeatureConfig.createTarget(limestoneTest, OreRockSets.LIMESTONE.coal_ore().getDefaultState()),
            OreFeatureConfig.createTarget(deepslateTest, OreRockSets.DEEPSLATE.tin_ore().getDefaultState()),
            OreFeatureConfig.createTarget(diftominTest, OreRockSets.DIFTOMIN.tin_ore().getDefaultState()));
    static List<OreFeatureConfig.Target> lapisList = List.of(OreFeatureConfig.createTarget(deepslateTest, Blocks.DEEPSLATE_LAPIS_ORE.getDefaultState()));
    static List<OreFeatureConfig.Target> leadList = List.of(
            OreFeatureConfig.createTarget(deepslateTest, OreRockSets.DEEPSLATE.lead_ore().getDefaultState()),
            OreFeatureConfig.createTarget(diftominTest, OreRockSets.DIFTOMIN.lead_ore().getDefaultState()),
            OreFeatureConfig.createTarget(epmostoTest, OreRockSets.EPMOSTO.lead_ore().getDefaultState()));
    static List<OreFeatureConfig.Target> ironList = List.of(
            OreFeatureConfig.createTarget(diftominTest, OreRockSets.DIFTOMIN.iron_ore().getDefaultState()),
            OreFeatureConfig.createTarget(epmostoTest, OreRockSets.EPMOSTO.iron_ore().getDefaultState()));
    static List<OreFeatureConfig.Target> silverList = List.of(
            OreFeatureConfig.createTarget(diftominTest, OreRockSets.DIFTOMIN.silver_ore().getDefaultState()),
            OreFeatureConfig.createTarget(epmostoTest, OreRockSets.EPMOSTO.silver_ore().getDefaultState()));
    static List<OreFeatureConfig.Target> jadeList = List.of(
            OreFeatureConfig.createTarget(diftominTest, StoneBlockSets.JADEITE.base().getDefaultState()),
            OreFeatureConfig.createTarget(epmostoTest, StoneBlockSets.JADEITE.base().getDefaultState()));
    static List<OreFeatureConfig.Target> goldList = List.of(
            OreFeatureConfig.createTarget(diftominTest, OreRockSets.DIFTOMIN.gold_ore().getDefaultState()),
            OreFeatureConfig.createTarget(epmostoTest, OreRockSets.EPMOSTO.gold_ore().getDefaultState()));
    static List<OreFeatureConfig.Target> mithrilList = List.of(OreFeatureConfig.createTarget(epmostoTest, OreRockSets.EPMOSTO.mithril_ore().getDefaultState()));

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        registerGeode(GLOWSTONE_GEODE, featureRegisterable, ModBlocks.GLOWSTONE_BLOCK, ModBlocks.BUDDING_GLOWSTONE, ModBlocks.SMALL_GLOWSTONE_BUD,
                ModBlocks.MEDIUM_GLOWSTONE_BUD, ModBlocks.LARGE_GLOWSTONE_BUD, ModBlocks.GLOWSTONE_CLUSTER);
        registerGeode(QUARTZ_GEODE, featureRegisterable, ModBlocks.QUARTZ_BLOCK, ModBlocks.BUDDING_QUARTZ, ModBlocks.SMALL_QUARTZ_BUD,
                ModBlocks.MEDIUM_QUARTZ_BUD, ModBlocks.LARGE_QUARTZ_BUD, ModBlocks.QUARTZ_CLUSTER);
        registerGeode(RED_AGATE_GEODE, featureRegisterable, ModBlocks.RED_AGATE_BLOCK, ModBlocks.BUDDING_RED_AGATE, ModBlocks.SMALL_RED_AGATE_BUD,
                ModBlocks.MEDIUM_RED_AGATE_BUD, ModBlocks.LARGE_RED_AGATE_BUD, ModBlocks.RED_AGATE_CLUSTER);


        ConfiguredFeatures.register(featureRegisterable, ORE_MAGMA, Feature.ORE, new OreFeatureConfig(epmostoTest, Blocks.MAGMA_BLOCK.getDefaultState(), 31));
        ConfiguredFeatures.register(featureRegisterable, ORE_COAL, Feature.ORE, new OreFeatureConfig(coalList, 17, 0.25f));
        ConfiguredFeatures.register(featureRegisterable, ORE_COPPER, Feature.ORE, new OreFeatureConfig(copperList, 15, 0.25f));
        ConfiguredFeatures.register(featureRegisterable, ORE_TIN, Feature.ORE, new OreFeatureConfig(tinList, 12, 0.25f));
        ConfiguredFeatures.register(featureRegisterable, ORE_LAPIS, Feature.ORE, new OreFeatureConfig(lapisList, 5, 0.5f));
        ConfiguredFeatures.register(featureRegisterable, ORE_LEAD, Feature.ORE, new OreFeatureConfig(leadList, 10, 0.3f));
        ConfiguredFeatures.register(featureRegisterable, ORE_IRON, Feature.ORE, new OreFeatureConfig(ironList, 9, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, ORE_SILVER, Feature.ORE, new OreFeatureConfig(silverList, 7, 0.6f));
        ConfiguredFeatures.register(featureRegisterable, ORE_JADE, Feature.ORE, new OreFeatureConfig(jadeList, 18, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, ORE_GOLD, Feature.ORE, new OreFeatureConfig(goldList, 5, 0.7f));
        ConfiguredFeatures.register(featureRegisterable, ORE_MITHRIL, Feature.ORE, new OreFeatureConfig(mithrilList, 1, 0.85f));
    }

    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
    }

    private static void registerGeode(RegistryKey<ConfiguredFeature<?, ?>> registryKey, Registerable<ConfiguredFeature<?, ?>> featureRegisterable, Block geodeBlock, Block budding, Block smallBud, Block mediumBud, Block largeBud, Block cluster) {
        ConfiguredFeatures.register(featureRegisterable, registryKey, Feature.GEODE, new GeodeFeatureConfig(
                new GeodeLayerConfig(BlockStateProvider.of(Blocks.AIR), BlockStateProvider.of(geodeBlock), BlockStateProvider.of(budding),
                        BlockStateProvider.of(Blocks.CALCITE), BlockStateProvider.of(Blocks.SMOOTH_BASALT), List.of(smallBud.getDefaultState(),
                        mediumBud.getDefaultState(), largeBud.getDefaultState(), cluster.getDefaultState()),
                        BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                new GeodeLayerThicknessConfig(1.7, 2.2, 3.2, 4.2),
                new GeodeCrackConfig(0.95, 2.0, 2), 0.35, 0.083,
                true, UniformIntProvider.create(2, 3), UniformIntProvider.create(1, 2), UniformIntProvider.create(1, 2),
                -16, 16, 0.05, 1));
    }
}
