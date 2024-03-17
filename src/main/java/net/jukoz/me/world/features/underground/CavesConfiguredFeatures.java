package net.jukoz.me.world.features.underground;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.ModNatureBlocks;
import net.jukoz.me.block.OreRockSets;
import net.jukoz.me.block.StoneBlockSets;
import net.jukoz.me.world.features.ores.SurfaceOreFeatureConfig;
import net.jukoz.me.world.features.pillar.PillarFeatureConfig;
import net.jukoz.me.world.gen.ModFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerbedBlock;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.PlacementModifier;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.List;

public class CavesConfiguredFeatures {
    public static final RegistryKey<ConfiguredFeature<?, ?>> CITRINE_GEODE = registerKey("citrine_geode");
    public static final RegistryKey<ConfiguredFeature<?, ?>> GLOWSTONE_GEODE = registerKey("glowstone_geode");
    public static final RegistryKey<ConfiguredFeature<?, ?>> RED_AGATE_GEODE = registerKey("red_agate_geode");
    public static final RegistryKey<ConfiguredFeature<?, ?>> QUARTZ_GEODE = registerKey("quartz_geode");

    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_MUD = registerKey("ore_mud");
    public static final RegistryKey<ConfiguredFeature<?, ?>> POOL_MUD = registerKey("pool_mud");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_MAGMA = registerKey("ore_magma");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_OBSIDIAN = registerKey("ore_obsidian");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DISK_MYCELIUM = registerKey("disk_mycelium");
    public static final RegistryKey<ConfiguredFeature<?, ?>> POOL_MAGMA = registerKey("pool_magma");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_ASH = registerKey("ore_ash");
    public static final RegistryKey<ConfiguredFeature<?, ?>> ORE_ASHEN_DIRT = registerKey("ore_ashen_dirt");

    public static final RegistryKey<ConfiguredFeature<?, ?>> PILLAR_BASALT = registerKey("pillar_basalt");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PILLAR_BLACKSTONE = registerKey("pillar_blackstone");

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

    // region MUSHROOMS
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CAVE_AMANITA = registerKey("patch_cave_amanita");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_CAVE_AMANITA_TILLER = registerKey("patch_cave_amanita_tiller");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DEEP_FIRECAP = registerKey("patch_deep_firecap");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_DEEP_FIRECAP_TILLER = registerKey("patch_deep_firecap_tiller");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GHOSTSHROOM = registerKey("patch_ghostshroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_GHOSTSHROOM_TILLER = registerKey("patch_ghostshroom_tiller");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SKY_FIRECAP = registerKey("patch_sky_firecap");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_SKY_FIRECAP_TILLER = registerKey("patch_sky_firecap_tiller");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TUBESHROOMS = registerKey("patch_tubeshrooms");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TALL_TUBESHROOMS = registerKey("patch_tall_ubeshrooms");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TRUMPET_SHROOM = registerKey("patch_trumpet_shroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_TALL_TRUMPET_SHROOM = registerKey("patch_tall_trumpet_shroom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_VIOLET_CAPS = registerKey("patch_violet_caps");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_VIOLET_CAPS_TILLER = registerKey("patch_violet_caps_tiller");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_AMANITA = registerKey("patch_yellow_amanita");
    public static final RegistryKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_AMANITA_TILLER = registerKey("patch_yellow_amanita_tiller");
    // endregion

    public static final RegistryKey<ConfiguredFeature<?, ?>> SPRING_LAVA = registerKey("spring_lava");

    // region TESTS
    static TagMatchRuleTest baseStone = new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD);
    static TagMatchRuleTest stoneTest = new TagMatchRuleTest(BlockTags.STONE_ORE_REPLACEABLES);
    static BlockMatchRuleTest ashenStoneTest = new BlockMatchRuleTest(StoneBlockSets.ASHEN_STONE.base());
    static BlockMatchRuleTest frozenStoneTest = new BlockMatchRuleTest(StoneBlockSets.FROZEN_STONE.base());
    static BlockMatchRuleTest gonluinTest = new BlockMatchRuleTest(StoneBlockSets.GONLUIN.base());
    static BlockMatchRuleTest limestoneTest = new BlockMatchRuleTest(StoneBlockSets.LIMESTONE.base());
    static TagMatchRuleTest deepslateTest = new TagMatchRuleTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    static BlockMatchRuleTest diftominTest = new BlockMatchRuleTest(StoneBlockSets.DIFTOMIN.base());
    static BlockMatchRuleTest epmostoTest = new BlockMatchRuleTest(StoneBlockSets.EPMOSTO.base());
    // endregion

    // region LISTS
    static List<OreFeatureConfig.Target> mudList = List.of(
            OreFeatureConfig.createTarget(stoneTest, Blocks.MUD.getDefaultState()),
            OreFeatureConfig.createTarget(gonluinTest, Blocks.MUD.getDefaultState()),
            OreFeatureConfig.createTarget(baseStone, Blocks.MUD.getDefaultState()),
            OreFeatureConfig.createTarget(deepslateTest, Blocks.MUD.getDefaultState()));
    static List<OreFeatureConfig.Target> ashList = List.of(
            OreFeatureConfig.createTarget(ashenStoneTest, ModBlocks.ASH_BLOCK.getDefaultState()),
            OreFeatureConfig.createTarget(deepslateTest, ModBlocks.ASH_BLOCK.getDefaultState()),
            OreFeatureConfig.createTarget(diftominTest, ModBlocks.ASH_BLOCK.getDefaultState()),
            OreFeatureConfig.createTarget(epmostoTest, ModBlocks.ASH_BLOCK.getDefaultState()));
    static List<OreFeatureConfig.Target> ashenDirtList = List.of(
            OreFeatureConfig.createTarget(ashenStoneTest, ModBlocks.ASHEN_DIRT.getDefaultState()),
            OreFeatureConfig.createTarget(deepslateTest, ModBlocks.ASHEN_DIRT.getDefaultState()),
            OreFeatureConfig.createTarget(diftominTest, ModBlocks.ASHEN_DIRT.getDefaultState()));
    static List<OreFeatureConfig.Target> magmaList = List.of(
            OreFeatureConfig.createTarget(diftominTest, Blocks.MAGMA_BLOCK.getDefaultState()),
            OreFeatureConfig.createTarget(epmostoTest, Blocks.MAGMA_BLOCK.getDefaultState()));
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
    // endregion

    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);

        registerGeode(CITRINE_GEODE, featureRegisterable, ModBlocks.CITRINE_BLOCK, ModBlocks.BUDDING_CITRINE, ModBlocks.SMALL_CITRINE_BUD,
                ModBlocks.MEDIUM_CITRINE_BUD, ModBlocks.LARGE_CITRINE_BUD, ModBlocks.CITRINE_CLUSTER);
        registerGeode(GLOWSTONE_GEODE, featureRegisterable, ModBlocks.GLOWSTONE_BLOCK, ModBlocks.BUDDING_GLOWSTONE, ModBlocks.SMALL_GLOWSTONE_BUD,
                ModBlocks.MEDIUM_GLOWSTONE_BUD, ModBlocks.LARGE_GLOWSTONE_BUD, ModBlocks.GLOWSTONE_CLUSTER);
        registerGeode(QUARTZ_GEODE, featureRegisterable, ModBlocks.QUARTZ_BLOCK, ModBlocks.BUDDING_QUARTZ, ModBlocks.SMALL_QUARTZ_BUD,
                ModBlocks.MEDIUM_QUARTZ_BUD, ModBlocks.LARGE_QUARTZ_BUD, ModBlocks.QUARTZ_CLUSTER);
        registerGeode(RED_AGATE_GEODE, featureRegisterable, ModBlocks.RED_AGATE_BLOCK, ModBlocks.BUDDING_RED_AGATE, ModBlocks.SMALL_RED_AGATE_BUD,
                ModBlocks.MEDIUM_RED_AGATE_BUD, ModBlocks.LARGE_RED_AGATE_BUD, ModBlocks.RED_AGATE_CLUSTER);

        ConfiguredFeatures.register(featureRegisterable, ORE_MUD, Feature.ORE, new OreFeatureConfig(mudList, 41));
        ConfiguredFeatures.register(featureRegisterable, POOL_MUD, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchFeatureConfig(BlockTags.LUSH_GROUND_REPLACEABLE,
                BlockStateProvider.of(Blocks.MUD), PlacedFeatures.createEntry(registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.DRIPLEAF), new PlacementModifier[0]), VerticalSurfaceType.FLOOR,
                ConstantIntProvider.create(3), 0.8f, 5, 0.1f, UniformIntProvider.create(4, 7), 0.7f));
        ConfiguredFeatures.register(featureRegisterable, ORE_MAGMA, Feature.ORE, new OreFeatureConfig(magmaList, 31, 0.4f));
        ConfiguredFeatures.register(featureRegisterable, ORE_OBSIDIAN, Feature.ORE, new OreFeatureConfig(epmostoTest, Blocks.OBSIDIAN.getDefaultState(), 27));
        ConfiguredFeatures.register(featureRegisterable, DISK_MYCELIUM, ModFeatures.SURFACE_ORE, new SurfaceOreFeatureConfig(baseStone, ModBlocks.STONE_MYCELIUM.getDefaultState(), 56));
        ConfiguredFeatures.register(featureRegisterable, POOL_MAGMA, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchFeatureConfig(BlockTags.BASE_STONE_OVERWORLD,
                BlockStateProvider.of(Blocks.MAGMA_BLOCK), PlacedFeatures.createEntry(registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.DRIPLEAF), new PlacementModifier[0]), VerticalSurfaceType.FLOOR,
                ConstantIntProvider.create(3), 0.8f, 5, 0.1f, UniformIntProvider.create(4, 7), 0.7f));
        ConfiguredFeatures.register(featureRegisterable, ORE_ASH, Feature.ORE, new OreFeatureConfig(ashList, 48, 0.2f));
        ConfiguredFeatures.register(featureRegisterable, ORE_ASHEN_DIRT, Feature.ORE, new OreFeatureConfig(ashenDirtList, 33));

        ConfiguredFeatures.register(featureRegisterable, PILLAR_BASALT, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformIntProvider.create(3, 19),
                UniformFloatProvider.create(0.4f, 2.0f), 0.33f, UniformFloatProvider.create(0.3f, 0.9f), UniformFloatProvider.create(0.4f, 1.0f),
                UniformFloatProvider.create(0.0f, 0.3f), 4, 0.6f, Blocks.BASALT.getDefaultState()));
        ConfiguredFeatures.register(featureRegisterable, PILLAR_BLACKSTONE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformIntProvider.create(3, 19),
                UniformFloatProvider.create(0.4f, 2.0f), 0.33f, UniformFloatProvider.create(0.3f, 0.9f), UniformFloatProvider.create(0.4f, 1.0f),
                UniformFloatProvider.create(0.0f, 0.3f), 4, 0.6f, Blocks.BLACKSTONE.getDefaultState()));

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


        ConfiguredFeatures.register(featureRegisterable, PATCH_CAVE_AMANITA, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.CAVE_AMANITA))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_CAVE_AMANITA_TILLER, Feature.FLOWER, new RandomPatchFeatureConfig(48, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(getMushroomBuilder(ModNatureBlocks.CAVE_AMANITA_TILLER))))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_DEEP_FIRECAP, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.DEEP_FIRECAP))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_DEEP_FIRECAP_TILLER, Feature.FLOWER, new RandomPatchFeatureConfig(48, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(getMushroomBuilder(ModNatureBlocks.DEEP_FIRECAP_TILLER))))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_GHOSTSHROOM, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.GHOSTSHROOM))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_GHOSTSHROOM_TILLER, Feature.FLOWER, new RandomPatchFeatureConfig(48, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(getMushroomBuilder(ModNatureBlocks.GHOSTSHROOM_TILLER))))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_SKY_FIRECAP, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.SKY_FIRECAP))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_SKY_FIRECAP_TILLER, Feature.FLOWER, new RandomPatchFeatureConfig(48, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(getMushroomBuilder(ModNatureBlocks.SKY_FIRECAP_TILLER))))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TUBESHROOMS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TUBESHRROM))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_TALL_TUBESHROOMS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(Blocks.TALL_GRASS))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_TRUMPET_SHROOM, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TRUMPET_SHROOM))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_TALL_TRUMPET_SHROOM, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.TALL_TRUMPET_SHROOM))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_VIOLET_CAPS, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.VIOLET_CAPS))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_VIOLET_CAPS_TILLER, Feature.FLOWER, new RandomPatchFeatureConfig(48, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(getMushroomBuilder(ModNatureBlocks.VIOLET_CAPS_TILLER))))));

        ConfiguredFeatures.register(featureRegisterable, PATCH_YELLOW_AMANITA, Feature.RANDOM_PATCH,
                ConfiguredFeatures.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockFeatureConfig(BlockStateProvider.of(ModNatureBlocks.YELLOW_AMANITA))));
        ConfiguredFeatures.register(featureRegisterable, PATCH_YELLOW_AMANITA_TILLER, Feature.FLOWER, new RandomPatchFeatureConfig(48, 6, 2,
                PlacedFeatures.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(getMushroomBuilder(ModNatureBlocks.YELLOW_AMANITA_TILLER))))));


        ConfiguredFeatures.register(featureRegisterable, SPRING_LAVA, Feature.SPRING_FEATURE, new SpringFeatureConfig(Fluids.LAVA.getDefaultState(),
                true, 4, 1, RegistryEntryList.of(Block::getRegistryEntry, StoneBlockSets.DIFTOMIN.base(), StoneBlockSets.EPMOSTO.base())));
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

    private static DataPool.Builder<BlockState> getMushroomBuilder(Block tiller) {
        DataPool.Builder<BlockState> builder = DataPool.builder();
        for (int i = 1; i <= 4; ++i) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                builder.add((tiller.getDefaultState().with(FlowerbedBlock.FLOWER_AMOUNT, i)).with(FlowerbedBlock.FACING, direction), 1);
            }
        }
        return builder;
    }
}
