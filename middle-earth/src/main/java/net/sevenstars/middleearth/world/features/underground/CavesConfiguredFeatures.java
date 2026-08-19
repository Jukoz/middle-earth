package net.sevenstars.middleearth.world.features.underground;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.OreRockSets;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.sevenstars.middleearth.block.special.DroopingIciclesBlock;
import net.sevenstars.middleearth.world.features.columns.ClusterFeatureConfig;
import net.sevenstars.middleearth.world.features.columns.SmallPointedStoneFeatureConfig;
import net.sevenstars.middleearth.world.features.ores.ModOreFeatureConfig;
import net.sevenstars.middleearth.world.features.ores.SurfaceOreFeatureConfig;
import net.sevenstars.middleearth.world.features.pillar.PillarFeatureConfig;
import net.sevenstars.middleearth.world.gen.ModFeatures;
import net.minecraft.world.level.block.*;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.world.level.material.Fluids;
import java.util.List;

public class CavesConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMETHYST_GEODE = registerKey("amethyst_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CITRINE_GEODE = registerKey("citrine_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSTONE_GEODE = registerKey("glowstone_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_AGATE_GEODE = registerKey("red_agate_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_GEODE = registerKey("quartz_geode");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DOLOMITE = registerKey("ore_dolomite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OLD_DOLOMITE = registerKey("ore_old_dolomite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MUD = registerKey("ore_mud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POOL_MUD = registerKey("pool_mud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MAGMA = registerKey("ore_magma");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OBSIDIAN = registerKey("ore_obsidian");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_MYCELIUM = registerKey("disk_mycelium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POOL_MAGMA = registerKey("pool_magma");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ASH = registerKey("ore_ash");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ASHEN_DIRT = registerKey("ore_ashen_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DRY_DIRT = registerKey("ore_dry_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PACKED_ICE = registerKey("ore_packed_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLUE_ICE = registerKey("ore_blue_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GABBRO = registerKey("ore_gabbro");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GALONN = registerKey("ore_galonn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OLD_GALONN = registerKey("ore_old_galonn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GNEISS = registerKey("ore_gneiss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREEN_TUFF = registerKey("ore_green_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GILDED_GREEN_TUFF = registerKey("ore_gilded_green_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLD_GREEN_TUFF = registerKey("ore_gold_green_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLDEN_GREEN_TUFF = registerKey("ore_golden_green_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IZHER_ABAN = registerKey("ore_izher_aban");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ZIGIL_ABAN = registerKey("ore_zigil_aban");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OLD_IZHER_ABAN = registerKey("ore_old_izher_aban");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LIMESTONE = registerKey("ore_limestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OLD_LIMESTONE = registerKey("ore_old_limestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SAND = registerKey("ore_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SANDSTONE = registerKey("ore_sandstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SCHIST = registerKey("ore_schist");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SNOW = registerKey("ore_snow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TERRACOTTA = registerKey("ore_terracotta");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TUFF = registerKey("ore_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BASALT = registerKey("ore_basalt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLACKSTONE = registerKey("ore_blackstone");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DOLOMITE_CLUSTER = registerKey("dolomite_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_DOLOMITE = registerKey("large_dolomite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POINTED_DOLOMITE = registerKey("pointed_dolomite");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GALONN_CLUSTER = registerKey("galonn_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GALONN = registerKey("large_galonn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POINTED_GALONN = registerKey("pointed_galonn");

    public static final ResourceKey<ConfiguredFeature<?, ?>> IZHER_ABAN_CLUSTER = registerKey("izher_aban_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_IZHER_ABAN = registerKey("large_izher_aban");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POINTED_IZHER_ABAN = registerKey("pointed_izher_aban");

    public static final ResourceKey<ConfiguredFeature<?, ?>> LIMESTONE_CLUSTER = registerKey("limestone_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_LIMESTONE = registerKey("large_limestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POINTED_LIMESTONE = registerKey("pointed_limestone");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PILLAR_BASALT = registerKey("pillar_basalt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PILLAR_BLACKSTONE = registerKey("pillar_blackstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PILLAR_PACKED_ICE = registerKey("pillar_packed_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PILLAR_SMOOTH_SANDSTONE = registerKey("pillar_smooth_sandstone");

    // region Material Ores
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COAL = registerKey("ore_coal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COPPER = registerKey("ore_copper");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TIN = registerKey("ore_tin");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LAPIS = registerKey("ore_lapis");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LEAD = registerKey("ore_lead");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_QUARTZITE = registerKey("ore_quartzite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IRON = registerKey("ore_iron");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER = registerKey("ore_silver");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_JADE = registerKey("ore_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLD = registerKey("ore_gold");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MITHRIL = registerKey("ore_mithril");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ADAMANT = registerKey("ore_adamant");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_EMERALD = registerKey("ore_emerald");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_RUBY = registerKey("ore_ruby");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SAPPHIRE = registerKey("ore_sapphire");
    // endregion

    // region MUSHROOMS
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CAVE_AMANITA = registerKey("patch_cave_amanita");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CAVE_AMANITA_TILLER = registerKey("patch_cave_amanita_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEEP_FIRECAP = registerKey("patch_deep_firecap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEEP_FIRECAP_TILLER = registerKey("patch_deep_firecap_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GHOSTSHROOM = registerKey("patch_ghostshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GHOSTSHROOM_TILLER = registerKey("patch_ghostshroom_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SKY_FIRECAP = registerKey("patch_sky_firecap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SKY_FIRECAP_TILLER = registerKey("patch_sky_firecap_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TUBESHROOMS = registerKey("patch_tubeshrooms");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_TUBESHROOMS = registerKey("patch_tall_tubeshrooms");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TRUMPET_SHROOM = registerKey("patch_trumpet_shroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_TRUMPET_SHROOM = registerKey("patch_tall_trumpet_shroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_VIOLET_CAPS = registerKey("patch_violet_caps");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_VIOLET_CAPS_TILLER = registerKey("patch_violet_caps_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_AMANITA = registerKey("patch_yellow_amanita");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_AMANITA_TILLER = registerKey("patch_yellow_amanita_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWWORM_WEBBING = registerKey("glowworm_webbing");
    // endregion

    public static final ResourceKey<ConfiguredFeature<?, ?>> DROOPING_ICICLES = registerKey("drooping_icicles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_ICICLES = registerKey("short_icicles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STICKY_ICE = registerKey("sticky_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STICKY_SNOW = registerKey("sticky_snow");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SPRING_LAVA = registerKey("spring_lava");

    // region TESTS
    static TagMatchTest baseStone = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
    static TagMatchTest stoneTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    static BlockMatchTest ashenStoneTest = new BlockMatchTest(StoneBlockSets.ASHENSTONE_SET.baseBlocks.base());
    static BlockMatchTest blueTuffTest = new BlockMatchTest(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base());
    static BlockMatchTest dolomiteTest = new BlockMatchTest(StoneBlockSets.DOLOMITE_SET.baseBlocks.base());
    static BlockMatchTest hematiteTest = new BlockMatchTest(StoneBlockSets.HEMATITE_SET.baseBlocks.base());
    static BlockMatchTest greenTuffTest = new BlockMatchTest(StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base());
    static BlockMatchTest galonnTest = new BlockMatchTest(StoneBlockSets.GALONN_SET.baseBlocks.base());
    static BlockMatchTest izherAbanTest = new BlockMatchTest(StoneBlockSets.IZHERABAN_SET.baseBlocks.base());
    static BlockMatchTest calciteTest = new BlockMatchTest(Blocks.CALCITE);
    static BlockMatchTest dioriteTest = new BlockMatchTest(Blocks.DIORITE);
    static BlockMatchTest ironStoneTest = new BlockMatchTest(StoneBlockSets.IRONSTONE_SET.baseBlocks.base());
    static BlockMatchTest gonluinTest = new BlockMatchTest(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base());
    static BlockMatchTest limestoneTest = new BlockMatchTest(StoneBlockSets.LIMESTONE_SET.baseBlocks.base());
    static BlockMatchTest slateTest = new BlockMatchTest(StoneBlockSets.SLATE_SET.baseBlocks.base());
    static TagMatchTest deepslateTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    static BlockMatchTest nurgonTest = new BlockMatchTest(StoneBlockSets.NURGON_SET.baseBlocks.base());
    static BlockMatchTest medgonTest = new BlockMatchTest(StoneBlockSets.MEDGON_SET.baseBlocks.base());
    // endregion

    // region LISTS
    static List<OreConfiguration.TargetBlockState> dolomiteReplaceTest = List.of(
            OreConfiguration.target(baseStone, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> oldDolomiteReplaceTest = List.of(
            OreConfiguration.target(baseStone, StoneBlockSets.DOLOMITE_SET.oldBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.DOLOMITE_SET.oldBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> mudList = List.of(
            OreConfiguration.target(gonluinTest, Blocks.MUD.defaultBlockState()),
            OreConfiguration.target(baseStone, Blocks.MUD.defaultBlockState()),
            OreConfiguration.target(deepslateTest, Blocks.MUD.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> ashList = List.of(
            OreConfiguration.target(ashenStoneTest, ModBlocks.ASH_BLOCK.defaultBlockState()),
            OreConfiguration.target(deepslateTest, ModBlocks.ASH_BLOCK.defaultBlockState()),
            OreConfiguration.target(nurgonTest, ModBlocks.ASH_BLOCK.defaultBlockState()),
            OreConfiguration.target(medgonTest, ModBlocks.ASH_BLOCK.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> ashenDirtList = List.of(
            OreConfiguration.target(ashenStoneTest, ModBlocks.ASHEN_DIRT.defaultBlockState()),
            OreConfiguration.target(deepslateTest, ModBlocks.ASHEN_DIRT.defaultBlockState()),
            OreConfiguration.target(nurgonTest, ModBlocks.ASHEN_DIRT.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> dryDirtList = List.of(
            OreConfiguration.target(stoneTest, ModBlocks.DRY_DIRT.defaultBlockState()),
            OreConfiguration.target(deepslateTest, ModBlocks.DRY_DIRT.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> gabbroList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.GABBRO_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.GABBRO_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> galonnList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.GALONN_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(limestoneTest, StoneBlockSets.GALONN_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.GALONN_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> oldGalonnList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.GALONN_SET.oldBlocks.base().defaultBlockState()),
            OreConfiguration.target(limestoneTest, StoneBlockSets.GALONN_SET.oldBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.GALONN_SET.oldBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> gneissList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.GNEISS_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.GNEISS_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> greenTuffList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> gildedGreenTuffList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base().defaultBlockState()));
    static List<ModOreFeatureConfig.Target> goldenGreenTuffList = List.of(
            ModOreFeatureConfig.createTarget(stoneTest, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base().defaultBlockState()),
            ModOreFeatureConfig.createTarget(deepslateTest, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> izherAbanList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> zigilAbanList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.ZIGILABAN_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.ZIGILABAN_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> oldIzherAbanList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.IZHERABAN_SET.oldBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.IZHERABAN_SET.oldBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> limestoneList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> oldLimestoneList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.LIMESTONE_SET.oldBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.LIMESTONE_SET.oldBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> schistList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.SCHIST_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.SCHIST_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> sandList = List.of(
            OreConfiguration.target(stoneTest, Blocks.SAND.defaultBlockState()),
            OreConfiguration.target(deepslateTest, Blocks.SAND.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> sandStoneList = List.of(
            OreConfiguration.target(stoneTest, Blocks.SMOOTH_SANDSTONE.defaultBlockState()),
            OreConfiguration.target(deepslateTest, Blocks.SMOOTH_SANDSTONE.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> snowList = List.of(
            OreConfiguration.target(stoneTest, Blocks.SNOW_BLOCK.defaultBlockState()),
            OreConfiguration.target(deepslateTest, Blocks.SNOW_BLOCK.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> packedIceList = List.of(
            OreConfiguration.target(stoneTest, Blocks.PACKED_ICE.defaultBlockState()),
            OreConfiguration.target(deepslateTest, Blocks.PACKED_ICE.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> blueIceList = List.of(
            OreConfiguration.target(stoneTest, Blocks.BLUE_ICE.defaultBlockState()),
            OreConfiguration.target(deepslateTest, Blocks.BLUE_ICE.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> terracottaList = List.of(
            OreConfiguration.target(stoneTest, Blocks.TERRACOTTA.defaultBlockState()),
            OreConfiguration.target(deepslateTest, Blocks.TERRACOTTA.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> tuffList = List.of(
            OreConfiguration.target(nurgonTest, Blocks.TUFF.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> basaltList = List.of(
            OreConfiguration.target(nurgonTest, Blocks.SMOOTH_BASALT.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> blackstoneList = List.of(
            OreConfiguration.target(medgonTest, Blocks.BLACKSTONE.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> magmaList = List.of(
            OreConfiguration.target(nurgonTest, Blocks.MAGMA_BLOCK.defaultBlockState()),
            OreConfiguration.target(medgonTest, Blocks.MAGMA_BLOCK.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> coalList = List.of(
            OreConfiguration.target(stoneTest, Blocks.COAL_ORE.defaultBlockState()),
            OreConfiguration.target(blueTuffTest, OreRockSets.KHAGALABAN.coal_ore().defaultBlockState()),
            OreConfiguration.target(dolomiteTest, Blocks.COAL_ORE.defaultBlockState()),
            OreConfiguration.target(hematiteTest, OreRockSets.IRONSTONE.coal_ore().defaultBlockState()),
            OreConfiguration.target(galonnTest, OreRockSets.LIMESTONE.coal_ore().defaultBlockState()),
            OreConfiguration.target(greenTuffTest, Blocks.COAL_ORE.defaultBlockState()),
            OreConfiguration.target(izherAbanTest, OreRockSets.LIMESTONE.coal_ore().defaultBlockState()),
            OreConfiguration.target(ashenStoneTest, OreRockSets.ASHEN.coal_ore().defaultBlockState()),
            OreConfiguration.target(calciteTest, OreRockSets.CALCITE.coal_ore().defaultBlockState()),
            OreConfiguration.target(dioriteTest, OreRockSets.CALCITE.coal_ore().defaultBlockState()),
            OreConfiguration.target(ironStoneTest, OreRockSets.IRONSTONE.coal_ore().defaultBlockState()),
            OreConfiguration.target(gonluinTest, OreRockSets.KHAGALABAN.coal_ore().defaultBlockState()),
            OreConfiguration.target(limestoneTest, OreRockSets.LIMESTONE.coal_ore().defaultBlockState()),
            OreConfiguration.target(slateTest, OreRockSets.SLATE.coal_ore().defaultBlockState()),
            OreConfiguration.target(deepslateTest, Blocks.DEEPSLATE_COAL_ORE.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> copperList = List.of(
            OreConfiguration.target(stoneTest, Blocks.COPPER_ORE.defaultBlockState()),
            OreConfiguration.target(blueTuffTest, OreRockSets.KHAGALABAN.copper_ore().defaultBlockState()),
            OreConfiguration.target(dolomiteTest, Blocks.COPPER_ORE.defaultBlockState()),
            OreConfiguration.target(hematiteTest, OreRockSets.IRONSTONE.copper_ore().defaultBlockState()),
            OreConfiguration.target(galonnTest, OreRockSets.LIMESTONE.copper_ore().defaultBlockState()),
            OreConfiguration.target(greenTuffTest, Blocks.COPPER_ORE.defaultBlockState()),
            OreConfiguration.target(izherAbanTest, OreRockSets.LIMESTONE.copper_ore().defaultBlockState()),
            OreConfiguration.target(ashenStoneTest, OreRockSets.ASHEN.copper_ore().defaultBlockState()),
            OreConfiguration.target(calciteTest, OreRockSets.CALCITE.copper_ore().defaultBlockState()),
            OreConfiguration.target(dioriteTest, OreRockSets.CALCITE.copper_ore().defaultBlockState()),
            OreConfiguration.target(ironStoneTest, OreRockSets.IRONSTONE.copper_ore().defaultBlockState()),
            OreConfiguration.target(gonluinTest, OreRockSets.KHAGALABAN.copper_ore().defaultBlockState()),
            OreConfiguration.target(limestoneTest, OreRockSets.LIMESTONE.copper_ore().defaultBlockState()),
            OreConfiguration.target(slateTest, OreRockSets.SLATE.copper_ore().defaultBlockState()),
            OreConfiguration.target(deepslateTest, Blocks.DEEPSLATE_COPPER_ORE.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> tinList = List.of(
            OreConfiguration.target(stoneTest, OreRockSets.STONE.tin_ore().defaultBlockState()),
            OreConfiguration.target(blueTuffTest, OreRockSets.KHAGALABAN.tin_ore().defaultBlockState()),
            OreConfiguration.target(dolomiteTest, OreRockSets.STONE.tin_ore().defaultBlockState()),
            OreConfiguration.target(hematiteTest, OreRockSets.IRONSTONE.tin_ore().defaultBlockState()),
            OreConfiguration.target(galonnTest, OreRockSets.LIMESTONE.tin_ore().defaultBlockState()),
            OreConfiguration.target(greenTuffTest, OreRockSets.STONE.tin_ore().defaultBlockState()),
            OreConfiguration.target(izherAbanTest, OreRockSets.LIMESTONE.tin_ore().defaultBlockState()),
            OreConfiguration.target(ashenStoneTest, OreRockSets.ASHEN.tin_ore().defaultBlockState()),
            OreConfiguration.target(calciteTest, OreRockSets.CALCITE.tin_ore().defaultBlockState()),
            OreConfiguration.target(dioriteTest, OreRockSets.CALCITE.tin_ore().defaultBlockState()),
            OreConfiguration.target(ironStoneTest, OreRockSets.IRONSTONE.tin_ore().defaultBlockState()),
            OreConfiguration.target(gonluinTest, OreRockSets.KHAGALABAN.tin_ore().defaultBlockState()),
            OreConfiguration.target(limestoneTest, OreRockSets.LIMESTONE.tin_ore().defaultBlockState()),
            OreConfiguration.target(deepslateTest, OreRockSets.DEEPSLATE.tin_ore().defaultBlockState()),
            OreConfiguration.target(slateTest, OreRockSets.SLATE.tin_ore().defaultBlockState()),
            OreConfiguration.target(nurgonTest, OreRockSets.NURGON.tin_ore().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> lapisList = List.of(OreConfiguration.target(deepslateTest, Blocks.DEEPSLATE_LAPIS_ORE.defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> leadList = List.of(
            OreConfiguration.target(deepslateTest, OreRockSets.DEEPSLATE.lead_ore().defaultBlockState()),
            OreConfiguration.target(nurgonTest, OreRockSets.NURGON.lead_ore().defaultBlockState()),
            OreConfiguration.target(medgonTest, OreRockSets.MEDGON.lead_ore().defaultBlockState()));

    static List<OreConfiguration.TargetBlockState> quartziteList = List.of(
            OreConfiguration.target(stoneTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(ashenStoneTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(gonluinTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(limestoneTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(deepslateTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> ironList = List.of(
            OreConfiguration.target(nurgonTest, OreRockSets.NURGON.iron_ore().defaultBlockState()),
            OreConfiguration.target(medgonTest, OreRockSets.MEDGON.iron_ore().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> silverList = List.of(
            OreConfiguration.target(nurgonTest, OreRockSets.NURGON.silver_ore().defaultBlockState()),
            OreConfiguration.target(medgonTest, OreRockSets.MEDGON.silver_ore().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> jadeList = List.of(
            OreConfiguration.target(nurgonTest, StoneBlockSets.JADEITE_SET.baseBlocks.base().defaultBlockState()),
            OreConfiguration.target(medgonTest, StoneBlockSets.JADEITE_SET.baseBlocks.base().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> goldList = List.of(
            OreConfiguration.target(nurgonTest, OreRockSets.NURGON.gold_ore().defaultBlockState()),
            OreConfiguration.target(medgonTest, OreRockSets.MEDGON.gold_ore().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> emeraldList = List.of(
            OreConfiguration.target(nurgonTest, OreRockSets.NURGON.emerald_ore().defaultBlockState()),
            OreConfiguration.target(medgonTest, OreRockSets.MEDGON.emerald_ore().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> rubyList = List.of(
            OreConfiguration.target(nurgonTest, OreRockSets.NURGON.ruby_ore().defaultBlockState()),
            OreConfiguration.target(medgonTest, OreRockSets.MEDGON.ruby_ore().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> sapphireList = List.of(
            OreConfiguration.target(nurgonTest, OreRockSets.NURGON.sapphire_ore().defaultBlockState()),
            OreConfiguration.target(medgonTest, OreRockSets.MEDGON.sapphire_ore().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> adamentList = List.of(OreConfiguration.target(medgonTest, OreRockSets.MEDGON.adamant_ore().defaultBlockState()));
    static List<OreConfiguration.TargetBlockState> mithrilList = List.of(OreConfiguration.target(medgonTest, OreRockSets.MEDGON.mithril_ore().defaultBlockState()));
    // endregion

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.lookup(Registries.CONFIGURED_FEATURE);

        registerGeode(AMETHYST_GEODE, featureRegisterable, Blocks.AMETHYST_BLOCK, Blocks.BUDDING_AMETHYST, Blocks.SMALL_AMETHYST_BUD,
                Blocks.MEDIUM_AMETHYST_BUD, Blocks.LARGE_AMETHYST_BUD, Blocks.AMETHYST_CLUSTER, Blocks.CALCITE);
        registerGeode(CITRINE_GEODE, featureRegisterable, ModBlocks.CITRINE_BLOCK, ModBlocks.BUDDING_CITRINE, ModBlocks.SMALL_CITRINE_BUD,
                ModBlocks.MEDIUM_CITRINE_BUD, ModBlocks.LARGE_CITRINE_BUD, ModBlocks.CITRINE_CLUSTER, Blocks.CALCITE);
        registerGeode(GLOWSTONE_GEODE, featureRegisterable, ModBlocks.GLOWSTONE_BLOCK, ModBlocks.BUDDING_GLOWSTONE, ModBlocks.SMALL_GLOWSTONE_BUD,
                ModBlocks.MEDIUM_GLOWSTONE_BUD, ModBlocks.LARGE_GLOWSTONE_BUD, ModBlocks.GLOWSTONE_CLUSTER, Blocks.CALCITE);
        registerGeode(QUARTZ_GEODE, featureRegisterable, ModBlocks.QUARTZ_BLOCK, ModBlocks.BUDDING_QUARTZ, ModBlocks.SMALL_QUARTZ_BUD,
                ModBlocks.MEDIUM_QUARTZ_BUD, ModBlocks.LARGE_QUARTZ_BUD, ModBlocks.QUARTZ_CLUSTER, StoneBlockSets.QUARTZITE_SET.baseBlocks.base());
        registerGeode(RED_AGATE_GEODE, featureRegisterable, ModBlocks.RED_AGATE_BLOCK, ModBlocks.BUDDING_RED_AGATE, ModBlocks.SMALL_RED_AGATE_BUD,
                ModBlocks.MEDIUM_RED_AGATE_BUD, ModBlocks.LARGE_RED_AGATE_BUD, ModBlocks.RED_AGATE_CLUSTER, Blocks.CALCITE);

        FeatureUtils.register(featureRegisterable, ORE_DOLOMITE, Feature.ORE, new OreConfiguration(dolomiteReplaceTest, 64));
        FeatureUtils.register(featureRegisterable, ORE_OLD_DOLOMITE, Feature.ORE, new OreConfiguration(oldDolomiteReplaceTest, 42));
        FeatureUtils.register(featureRegisterable, ORE_MUD, Feature.ORE, new OreConfiguration(mudList, 41));
        FeatureUtils.register(featureRegisterable, POOL_MUD, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE,
                BlockStateProvider.simple(Blocks.MUD), PlacementUtils.inlinePlaced(registryEntryLookup.getOrThrow(CaveFeatures.DRIPLEAF), new PlacementModifier[0]), CaveSurface.FLOOR,
                ConstantInt.of(3), 0.8f, 5, 0.1f, UniformInt.of(4, 7), 0.7f));
        FeatureUtils.register(featureRegisterable, ORE_MAGMA, Feature.ORE, new OreConfiguration(magmaList, 31, 0.4f));
        FeatureUtils.register(featureRegisterable, ORE_OBSIDIAN, Feature.ORE, new OreConfiguration(medgonTest, Blocks.OBSIDIAN.defaultBlockState(), 27));
        FeatureUtils.register(featureRegisterable, DISK_MYCELIUM, ModFeatures.SURFACE_ORE, new SurfaceOreFeatureConfig(baseStone, ModBlocks.STONE_MYCELIUM.defaultBlockState(), 56));
        FeatureUtils.register(featureRegisterable, POOL_MAGMA, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.BASE_STONE_OVERWORLD,
                BlockStateProvider.simple(Blocks.MAGMA_BLOCK), PlacementUtils.inlinePlaced(registryEntryLookup.getOrThrow(CaveFeatures.DRIPLEAF), new PlacementModifier[0]), CaveSurface.FLOOR,
                ConstantInt.of(3), 0.8f, 5, 0.1f, UniformInt.of(4, 7), 0.7f));
        FeatureUtils.register(featureRegisterable, ORE_ASH, Feature.ORE, new OreConfiguration(ashList, 48, 0.2f));
        FeatureUtils.register(featureRegisterable, ORE_ASHEN_DIRT, Feature.ORE, new OreConfiguration(ashenDirtList, 33));
        FeatureUtils.register(featureRegisterable, ORE_DRY_DIRT, Feature.ORE, new OreConfiguration(dryDirtList, 37));
        FeatureUtils.register(featureRegisterable, ORE_PACKED_ICE, Feature.ORE, new OreConfiguration(packedIceList, 33));
        FeatureUtils.register(featureRegisterable, ORE_BLUE_ICE, Feature.ORE, new OreConfiguration(blueIceList, 33));
        FeatureUtils.register(featureRegisterable, ORE_GABBRO, Feature.ORE, new OreConfiguration(gabbroList, 64));
        FeatureUtils.register(featureRegisterable, ORE_GALONN, Feature.ORE, new OreConfiguration(galonnList, 64));
        FeatureUtils.register(featureRegisterable, ORE_OLD_GALONN, Feature.ORE, new OreConfiguration(oldGalonnList, 42));
        FeatureUtils.register(featureRegisterable, ORE_GNEISS, Feature.ORE, new OreConfiguration(gneissList, 64));
        FeatureUtils.register(featureRegisterable, ORE_GREEN_TUFF, Feature.ORE, new OreConfiguration(greenTuffList, 64));
        FeatureUtils.register(featureRegisterable, ORE_GILDED_GREEN_TUFF, Feature.ORE, new OreConfiguration(gildedGreenTuffList, 40));
        FeatureUtils.register(featureRegisterable, ORE_GOLDEN_GREEN_TUFF, ModFeatures.ORE, new ModOreFeatureConfig(goldenGreenTuffList, Blocks.RAW_GOLD_BLOCK.defaultBlockState(), 42, 0.0f));
        FeatureUtils.register(featureRegisterable, ORE_IZHER_ABAN, Feature.ORE, new OreConfiguration(izherAbanList, 64));
        FeatureUtils.register(featureRegisterable, ORE_ZIGIL_ABAN, Feature.ORE, new OreConfiguration(zigilAbanList, 64));
        FeatureUtils.register(featureRegisterable, ORE_OLD_IZHER_ABAN, Feature.ORE, new OreConfiguration(oldIzherAbanList, 42));
        FeatureUtils.register(featureRegisterable, ORE_LIMESTONE, Feature.ORE, new OreConfiguration(limestoneList, 64));
        FeatureUtils.register(featureRegisterable, ORE_OLD_LIMESTONE, Feature.ORE, new OreConfiguration(oldLimestoneList, 42));
        FeatureUtils.register(featureRegisterable, ORE_SAND, Feature.ORE, new OreConfiguration(sandList, 48));
        FeatureUtils.register(featureRegisterable, ORE_SANDSTONE, Feature.ORE, new OreConfiguration(sandStoneList, 37));
        FeatureUtils.register(featureRegisterable, ORE_SCHIST, Feature.ORE, new OreConfiguration(schistList, 48));
        FeatureUtils.register(featureRegisterable, ORE_SNOW, Feature.ORE, new OreConfiguration(snowList, 48));
        FeatureUtils.register(featureRegisterable, ORE_TERRACOTTA, Feature.ORE, new OreConfiguration(terracottaList, 42));
        FeatureUtils.register(featureRegisterable, ORE_TUFF, Feature.ORE, new OreConfiguration(tuffList, 42));
        FeatureUtils.register(featureRegisterable, ORE_BASALT, Feature.ORE, new OreConfiguration(basaltList, 42));
        FeatureUtils.register(featureRegisterable, ORE_BLACKSTONE, Feature.ORE, new OreConfiguration(blackstoneList, 42));

        FeatureUtils.register(featureRegisterable, DOLOMITE_CLUSTER, ModFeatures.CLUSTER, new ClusterFeatureConfig(12,
                StoneBlockSets.DOLOMITE_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_DOLOMITE.defaultBlockState(), UniformInt.of(3, 6),
                UniformInt.of(2, 8), 1, 3, UniformInt.of(2, 4), UniformFloat.of(0.3F, 0.7F),
                ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));
        FeatureUtils.register(featureRegisterable, LARGE_DOLOMITE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.of(3, 19),
                UniformFloat.of(0.4f, 2.0f), 0.33f, UniformFloat.of(0.3f, 0.9f), UniformFloat.of(0.4f, 1.0f),
                UniformFloat.of(0.0f, 0.3f), 4, 0.6f, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().defaultBlockState()));
        FeatureUtils.register(featureRegisterable, POINTED_DOLOMITE, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_DOLOMITE.defaultBlockState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1))), PlacementUtils.inlinePlaced(
                                ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_DOLOMITE.defaultBlockState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(-1))))));

        FeatureUtils.register(featureRegisterable, GALONN_CLUSTER, ModFeatures.CLUSTER, new ClusterFeatureConfig(12,
                StoneBlockSets.GALONN_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_GALONN.defaultBlockState(), UniformInt.of(3, 6),
                UniformInt.of(2, 8), 1, 3, UniformInt.of(2, 4), UniformFloat.of(0.3F, 0.7F),
                ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));
        FeatureUtils.register(featureRegisterable, LARGE_GALONN, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.of(3, 19),
                UniformFloat.of(0.4f, 2.0f), 0.33f, UniformFloat.of(0.3f, 0.9f), UniformFloat.of(0.4f, 1.0f),
                UniformFloat.of(0.0f, 0.3f), 4, 0.6f, StoneBlockSets.GALONN_SET.baseBlocks.base().defaultBlockState()));
        FeatureUtils.register(featureRegisterable, POINTED_GALONN, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.GALONN_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_GALONN.defaultBlockState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1))), PlacementUtils.inlinePlaced(
                                ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.GALONN_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_GALONN.defaultBlockState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(-1))))));

        FeatureUtils.register(featureRegisterable, IZHER_ABAN_CLUSTER, ModFeatures.CLUSTER, new ClusterFeatureConfig(12,
                StoneBlockSets.IZHERABAN_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_IZHERABAN.defaultBlockState(), UniformInt.of(3, 6),
                UniformInt.of(2, 8), 1, 3, UniformInt.of(2, 4), UniformFloat.of(0.3F, 0.7F),
                ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));
        FeatureUtils.register(featureRegisterable, LARGE_IZHER_ABAN, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.of(3, 19),
                UniformFloat.of(0.4f, 2.0f), 0.33f, UniformFloat.of(0.3f, 0.9f), UniformFloat.of(0.4f, 1.0f),
                UniformFloat.of(0.0f, 0.3f), 4, 0.6f, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().defaultBlockState()));
        FeatureUtils.register(featureRegisterable, POINTED_IZHER_ABAN, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_IZHERABAN.defaultBlockState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1))), PlacementUtils.inlinePlaced(
                        ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_IZHERABAN.defaultBlockState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(-1))))));

        FeatureUtils.register(featureRegisterable, LIMESTONE_CLUSTER, ModFeatures.CLUSTER, new ClusterFeatureConfig(12,
                StoneBlockSets.LIMESTONE_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_LIMESTONE.defaultBlockState(), UniformInt.of(3, 6),
                UniformInt.of(2, 8), 1, 3, UniformInt.of(2, 4), UniformFloat.of(0.3F, 0.7F),
                ClampedNormalFloat.of(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));
        FeatureUtils.register(featureRegisterable, LARGE_LIMESTONE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.of(3, 19),
                UniformFloat.of(0.4f, 2.0f), 0.33f, UniformFloat.of(0.3f, 0.9f), UniformFloat.of(0.4f, 1.0f),
                UniformFloat.of(0.0f, 0.3f), 4, 0.6f, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().defaultBlockState()));
        FeatureUtils.register(featureRegisterable, POINTED_LIMESTONE, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.direct(
                PlacementUtils.inlinePlaced(ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_LIMESTONE.defaultBlockState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(1))), PlacementUtils.inlinePlaced(
                                ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().defaultBlockState(), ModBlocks.POINTED_LIMESTONE.defaultBlockState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, 12),
                        RandomOffsetPlacement.vertical(ConstantInt.of(-1))))));

        FeatureUtils.register(featureRegisterable, PILLAR_BASALT, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.of(3, 19),
                UniformFloat.of(0.4f, 2.0f), 0.33f, UniformFloat.of(0.3f, 0.9f), UniformFloat.of(0.4f, 1.0f),
                UniformFloat.of(0.0f, 0.3f), 4, 0.6f, Blocks.BASALT.defaultBlockState()));
        FeatureUtils.register(featureRegisterable, PILLAR_BLACKSTONE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.of(3, 19),
                UniformFloat.of(0.4f, 2.0f), 0.33f, UniformFloat.of(0.3f, 0.9f), UniformFloat.of(0.4f, 1.0f),
                UniformFloat.of(0.0f, 0.3f), 4, 0.6f, Blocks.BLACKSTONE.defaultBlockState()));
        FeatureUtils.register(featureRegisterable, PILLAR_PACKED_ICE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.of(3, 19),
                UniformFloat.of(0.4f, 2.0f), 0.33f, UniformFloat.of(0.3f, 0.9f), UniformFloat.of(0.4f, 1.0f),
                UniformFloat.of(0.0f, 0.3f), 4, 0.6f, Blocks.PACKED_ICE.defaultBlockState()));
        FeatureUtils.register(featureRegisterable, PILLAR_SMOOTH_SANDSTONE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.of(3, 19),
                UniformFloat.of(0.4f, 2.0f), 0.33f, UniformFloat.of(0.3f, 0.9f), UniformFloat.of(0.4f, 1.0f),
                UniformFloat.of(0.0f, 0.3f), 4, 0.6f, Blocks.SMOOTH_SANDSTONE.defaultBlockState()));

        FeatureUtils.register(featureRegisterable, ORE_COAL, Feature.ORE, new OreConfiguration(coalList, 17, 0.25f));
        FeatureUtils.register(featureRegisterable, ORE_COPPER, Feature.ORE, new OreConfiguration(copperList, 15, 0.25f));
        FeatureUtils.register(featureRegisterable, ORE_TIN, Feature.ORE, new OreConfiguration(tinList, 12, 0.25f));
        FeatureUtils.register(featureRegisterable, ORE_LAPIS, Feature.ORE, new OreConfiguration(lapisList, 6, 0.3f));
        FeatureUtils.register(featureRegisterable, ORE_LEAD, Feature.ORE, new OreConfiguration(leadList, 12, 0.3f));
        FeatureUtils.register(featureRegisterable, ORE_QUARTZITE, Feature.ORE, new OreConfiguration(quartziteList, 21, 0.4f));
        FeatureUtils.register(featureRegisterable, ORE_IRON, Feature.ORE, new OreConfiguration(ironList, 10, 0.3f));
        FeatureUtils.register(featureRegisterable, ORE_SILVER, Feature.ORE, new OreConfiguration(silverList, 7, 0.5f));
        FeatureUtils.register(featureRegisterable, ORE_JADE, Feature.ORE, new OreConfiguration(jadeList, 16, 0.5f));
        FeatureUtils.register(featureRegisterable, ORE_GOLD, Feature.ORE, new OreConfiguration(goldList, 7, 0.42f));
        FeatureUtils.register(featureRegisterable, ORE_GOLD_GREEN_TUFF, Feature.SCATTERED_ORE, new OreConfiguration(greenTuffTest, Blocks.RAW_GOLD_BLOCK.defaultBlockState(), 1, 1.0f));
        FeatureUtils.register(featureRegisterable, ORE_EMERALD, Feature.SCATTERED_ORE, new OreConfiguration(emeraldList, 5, 0.68f));
        FeatureUtils.register(featureRegisterable, ORE_RUBY, Feature.SCATTERED_ORE, new OreConfiguration(rubyList, 5, 0.68f));
        FeatureUtils.register(featureRegisterable, ORE_SAPPHIRE, Feature.SCATTERED_ORE, new OreConfiguration(sapphireList, 5, 0.68f));
        FeatureUtils.register(featureRegisterable, ORE_ADAMANT, Feature.SCATTERED_ORE, new OreConfiguration(adamentList, 3, 0.72f));
        FeatureUtils.register(featureRegisterable, ORE_MITHRIL, Feature.SCATTERED_ORE, new OreConfiguration(mithrilList, 1, 1.0f));

        FeatureUtils.register(featureRegisterable, PATCH_CAVE_AMANITA, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.CAVE_AMANITA))));
        FeatureUtils.register(featureRegisterable, PATCH_CAVE_AMANITA_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.CAVE_AMANITA_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_DEEP_FIRECAP, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.DEEP_FIRECAP))));
        FeatureUtils.register(featureRegisterable, PATCH_DEEP_FIRECAP_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.DEEP_FIRECAP_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_GHOSTSHROOM, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.GHOSTSHROOM))));
        FeatureUtils.register(featureRegisterable, PATCH_GHOSTSHROOM_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.GHOSTSHROOM_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_SKY_FIRECAP, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.SKY_FIRECAP))));
        FeatureUtils.register(featureRegisterable, PATCH_SKY_FIRECAP_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.SKY_FIRECAP_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_TUBESHROOMS, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.TUBESHRROM))));
        FeatureUtils.register(featureRegisterable, PATCH_TALL_TUBESHROOMS, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.TALL_TUBESHROOM))));

        FeatureUtils.register(featureRegisterable, PATCH_TRUMPET_SHROOM, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.TRUMPET_SHROOM))));
        FeatureUtils.register(featureRegisterable, PATCH_TALL_TRUMPET_SHROOM, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.TALL_TRUMPET_SHROOM))));

        FeatureUtils.register(featureRegisterable, PATCH_VIOLET_CAPS, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.VIOLET_CAPS))));
        FeatureUtils.register(featureRegisterable, PATCH_VIOLET_CAPS_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.VIOLET_CAPS_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_YELLOW_AMANITA, Feature.RANDOM_PATCH,
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.simple(ModNatureBlocks.YELLOW_AMANITA))));
        FeatureUtils.register(featureRegisterable, PATCH_YELLOW_AMANITA_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.YELLOW_AMANITA_TILLER))))));

        FeatureUtils.register(featureRegisterable, GLOWWORM_WEBBING, Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(List.of(
                        BlockColumnConfiguration.layer(UniformInt.of(2, 8), BlockStateProvider.simple(ModNatureBlocks.GLOWWORM_MAIN)),
                        BlockColumnConfiguration.layer(ConstantInt.of(1), BlockStateProvider.simple(ModNatureBlocks.GLOWWORM_WEBBING))),
                        Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));

        FeatureUtils.register(featureRegisterable, DROOPING_ICICLES, Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(List.of(
                        BlockColumnConfiguration.layer(UniformInt.of(1, 5), BlockStateProvider.simple(ModNatureBlocks.DROOPING_ICICLES.defaultBlockState().setValue(DroopingIciclesBlock.HALF, DoubleBlockHalf.UPPER))),
                        BlockColumnConfiguration.layer(ConstantInt.of(1), BlockStateProvider.simple(ModNatureBlocks.DROOPING_ICICLES.defaultBlockState().setValue(DroopingIciclesBlock.HALF, DoubleBlockHalf.LOWER)))),
                        Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));
        FeatureUtils.register(featureRegisterable, SHORT_ICICLES, Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(List.of(
                        BlockColumnConfiguration.layer(ConstantInt.of(1), BlockStateProvider.simple(ModNatureBlocks.SHORT_ICICLES))),
                        Direction.DOWN, BlockPredicate.ONLY_IN_AIR_PREDICATE, true));

        HolderSet<Block> stickyBlocks = HolderSet.direct(Block::builtInRegistryHolder, Blocks.STONE, Blocks.SNOW, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE,
                Blocks.TUFF, Blocks.DEEPSLATE, Blocks.ICE, Blocks.BLUE_ICE);
        FeatureUtils.register(featureRegisterable, STICKY_ICE, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.STICKY_ICE, 20, false, true, true, 0.5F, stickyBlocks));
        FeatureUtils.register(featureRegisterable, STICKY_SNOW, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.STICKY_SNOW, 20, false, true, true, 0.5F, stickyBlocks));


        FeatureUtils.register(featureRegisterable, SPRING_LAVA, Feature.SPRING, new SpringConfiguration(Fluids.LAVA.defaultFluidState(),
                true, 4, 1, HolderSet.direct(Block::builtInRegistryHolder, StoneBlockSets.NURGON_SET.baseBlocks.base(), StoneBlockSets.MEDGON_SET.baseBlocks.base())));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }

    private static void registerGeode(ResourceKey<ConfiguredFeature<?, ?>> registryKey, BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable, Block geodeBlock, Block budding, Block smallBud, Block mediumBud, Block largeBud, Block cluster, Block inner) {
        FeatureUtils.register(featureRegisterable, registryKey, Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(BlockStateProvider.simple(Blocks.AIR), BlockStateProvider.simple(geodeBlock), BlockStateProvider.simple(budding),
                        BlockStateProvider.simple(inner), BlockStateProvider.simple(Blocks.SMOOTH_BASALT), List.of(smallBud.defaultBlockState(),
                        mediumBud.defaultBlockState(), largeBud.defaultBlockState(), cluster.defaultBlockState()),
                        BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                new GeodeCrackSettings(0.95, 2.0, 2), 0.35, 0.083,
                true, UniformInt.of(3, 4), UniformInt.of(2, 3), UniformInt.of(1, 2),
                -16, 16, 0.05, 1));
    }

    public static SimpleWeightedRandomList.Builder<BlockState> get4StagesBlockBuilder(Block tiller) {
        SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; ++i) {
            for (Direction direction : Direction.Plane.HORIZONTAL) {
                builder.add((tiller.defaultBlockState().setValue(PinkPetalsBlock.AMOUNT, i)).setValue(PinkPetalsBlock.FACING, direction), 1);
            }
        }
        return builder;
    }
}
