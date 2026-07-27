package net.sevenstars.middleearth.world.features.underground;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.world.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.world.features.tree.MushroomTreeConfiguredFeatures;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.NetherFeatures;
import net.minecraft.data.worldgen.features.OreFeatures;
import net.minecraft.data.worldgen.features.VegetationFeatures;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ClampedNormalInt;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.BiomeFilter;
import net.minecraft.world.level.levelgen.placement.CountOnEveryLayerPlacement;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import net.minecraft.world.level.levelgen.placement.SurfaceRelativeThresholdFilter;
import java.util.List;

public class CavesPlacedFeatures {
    public static final int MAX_MITHRIL_HEIGHT = -54;

    // region GEODES
    public static final ResourceKey<PlacedFeature> AMETHYST_GEODE = registerKey("amethyst_geode");
    public static final ResourceKey<PlacedFeature> CITRINE_GEODE = registerKey("citrine_geode");
    public static final ResourceKey<PlacedFeature> GLOWSTONE_GEODE = registerKey("glowstone_geode");
    public static final ResourceKey<PlacedFeature> RED_AGATE_GEODE = registerKey("red_agate_geode");
    public static final ResourceKey<PlacedFeature> QUARTZ_GEODE = registerKey("quartz_geode");
    // endregion

    // region ORES
    public static final ResourceKey<PlacedFeature> ORE_ASH = registerKey("ore_ash");
    public static final ResourceKey<PlacedFeature> ORE_ASHEN_DIRT = registerKey("ore_ashen_dirt");
    public static final ResourceKey<PlacedFeature> ORE_BASALT = registerKey("ore_basalt");
    public static final ResourceKey<PlacedFeature> ORE_BLACKSTONE = registerKey("ore_black_stone");
    public static final ResourceKey<PlacedFeature> ORE_BLUE_ICE = registerKey("ore_blue_ice");
    public static final ResourceKey<PlacedFeature> ORE_PACKED_ICE = registerKey("ore_packed_ice");
    public static final ResourceKey<PlacedFeature> ORE_DIRT = registerKey("ore_dirt");
    public static final ResourceKey<PlacedFeature> ORE_DOLOMITE = registerKey("ore_dolomite");
    public static final ResourceKey<PlacedFeature> ORE_OLD_DOLOMITE = registerKey("ore_old_dolomite");
    public static final ResourceKey<PlacedFeature> ORE_DOLOMITE_ABUNDANT = registerKey("ore_dolomite_abundant");
    public static final ResourceKey<PlacedFeature> ORE_DRY_DIRT = registerKey("ore_dry_dirt");
    public static final ResourceKey<PlacedFeature> ORE_GABBRO = registerKey("ore_gabbro");
    public static final ResourceKey<PlacedFeature> ORE_GALONN = registerKey("ore_galonn");
    public static final ResourceKey<PlacedFeature> ORE_OLD_GALONN = registerKey("ore_old_galonn");
    public static final ResourceKey<PlacedFeature> ORE_GNEISS = registerKey("ore_gneiss");
    public static final ResourceKey<PlacedFeature> ORE_GREEN_TUFF = registerKey("ore_green_tuff");
    public static final ResourceKey<PlacedFeature> ORE_GILDED_GREEN_TUFF = registerKey("ore_gilded_green_tuff");
    public static final ResourceKey<PlacedFeature> ORE_GOLD_GREEN_TUFF = registerKey("ore_gold_green_tuff");
    public static final ResourceKey<PlacedFeature> ORE_IZHER_ABAN = registerKey("ore_izher_aban");
    public static final ResourceKey<PlacedFeature> ORE_ZIGIL_ABAN = registerKey("ore_zigil_aban");
    public static final ResourceKey<PlacedFeature> ORE_OLD_IZHER_ABAN = registerKey("ore_old_izher_aban");
    public static final ResourceKey<PlacedFeature> ORE_LIMESTONE = registerKey("ore_limestone");
    public static final ResourceKey<PlacedFeature> ORE_OLD_LIMESTONE = registerKey("ore_old_limestone");
    public static final ResourceKey<PlacedFeature> ORE_LIMESTONE_ABUNDANT = registerKey("ore_limestone_abundant");
    public static final ResourceKey<PlacedFeature> ORE_MAGMA = registerKey("ore_magma");
    public static final ResourceKey<PlacedFeature> ORE_MAGMA_ABUNDANT = registerKey("ore_magma_abundant");
    public static final ResourceKey<PlacedFeature> ORE_MUD = registerKey("ore_mud");
    public static final ResourceKey<PlacedFeature> ORE_OBSIDIAN = registerKey("ore_obsidian");
    public static final ResourceKey<PlacedFeature> ORE_SAND = registerKey("ore_sand");
    public static final ResourceKey<PlacedFeature> ORE_SANDSTONE = registerKey("ore_sandstone");
    public static final ResourceKey<PlacedFeature> ORE_SCHIST = registerKey("ore_schist");
    public static final ResourceKey<PlacedFeature> ORE_SNOW = registerKey("ore_snow");
    public static final ResourceKey<PlacedFeature> ORE_TERRACOTTA = registerKey("ore_terracotta");
    public static final ResourceKey<PlacedFeature> ORE_TUFF = registerKey("ore_tuff");
    // endregion

    public static final ResourceKey<PlacedFeature> DOLOMITE_CLUSTER = registerKey("dolomite_cluster");
    public static final ResourceKey<PlacedFeature> LARGE_DOLOMITE = registerKey("large_dolomite");
    public static final ResourceKey<PlacedFeature> POINTED_DOLOMITE = registerKey("pointed_dolomite");

    public static final ResourceKey<PlacedFeature> GALONN_CLUSTER = registerKey("galonn_cluster");
    public static final ResourceKey<PlacedFeature> LARGE_GALONN = registerKey("large_galonn");
    public static final ResourceKey<PlacedFeature> POINTED_GALONN = registerKey("pointed_galonn");

    public static final ResourceKey<PlacedFeature> IZHER_ABAN_CLUSTER = registerKey("izher_aban_cluster");
    public static final ResourceKey<PlacedFeature> LARGE_IZHER_ABAN = registerKey("large_izher_aban");
    public static final ResourceKey<PlacedFeature> POINTED_IZHER_ABAN = registerKey("pointed_izher_aban");

    public static final ResourceKey<PlacedFeature> LIMESTONE_CLUSTER = registerKey("limestone_cluster");
    public static final ResourceKey<PlacedFeature> LARGE_LIMESTONE = registerKey("large_limestone");
    public static final ResourceKey<PlacedFeature> POINTED_LIMESTONE = registerKey("pointed_limestone");

    public static final ResourceKey<PlacedFeature> POOL_MUD = registerKey("pool_mud");
    public static final ResourceKey<PlacedFeature> DISK_MYCELIUM = registerKey("disk_mycelium");

    public static final ResourceKey<PlacedFeature> DELTA = registerKey("delta");

    public static final ResourceKey<PlacedFeature> PILLAR_BASALT = registerKey("pillar_polished_basalt");
    public static final ResourceKey<PlacedFeature> PILLAR_BLACKSTONE = registerKey("pillar_blackstone");
    public static final ResourceKey<PlacedFeature> PILLAR_PACKED_ICE = registerKey("pillar_packed_ice");
    public static final ResourceKey<PlacedFeature> PILLAR_SMOOTH_SANDSTONE = registerKey("pillar_smooth_sandstone");

    // region MATERIALS
    public static final ResourceKey<PlacedFeature> ORE_COAL = registerKey("ore_coal");
    public static final ResourceKey<PlacedFeature> ORE_COAL_UPPER = registerKey("ore_coal_upper");
    public static final ResourceKey<PlacedFeature> ORE_COPPER = registerKey("ore_copper");
    public static final ResourceKey<PlacedFeature> ORE_COPPER_UPPER = registerKey("ore_copper_upper");
    public static final ResourceKey<PlacedFeature> ORE_TIN = registerKey("ore_tin");
    public static final ResourceKey<PlacedFeature> ORE_LAPIS = registerKey("ore_lapis");
    public static final ResourceKey<PlacedFeature> ORE_LAPIS_ABUNDANT = registerKey("ore_lapis_abundant");
    public static final ResourceKey<PlacedFeature> ORE_LEAD = registerKey("ore_lead");
    public static final ResourceKey<PlacedFeature> ORE_QUARTZITE = registerKey("ore_quartzite");
    public static final ResourceKey<PlacedFeature> ORE_IRON = registerKey("ore_iron");
    public static final ResourceKey<PlacedFeature> ORE_SILVER = registerKey("ore_silver");
    public static final ResourceKey<PlacedFeature> ORE_JADE = registerKey("ore_jade");
    public static final ResourceKey<PlacedFeature> ORE_GOLD = registerKey("ore_gold");
    public static final ResourceKey<PlacedFeature> ORE_GOLD_RARE = registerKey("ore_gold_rare");
    public static final ResourceKey<PlacedFeature> ORE_MITHRIL = registerKey("ore_mithril");
    public static final ResourceKey<PlacedFeature> ORE_ADAMANT = registerKey("ore_adamant");
    public static final ResourceKey<PlacedFeature> ORE_EMERALD = registerKey("ore_emerald");
    public static final ResourceKey<PlacedFeature> ORE_RUBY = registerKey("ore_ruby");
    public static final ResourceKey<PlacedFeature> ORE_SAPPHIRE = registerKey("ore_sapphire");
    // endregion

    // region VEGETATION
    public static final ResourceKey<PlacedFeature> TREE_BROWN_BOLETTE = registerKey("brown_bolette_tree");
    public static final ResourceKey<PlacedFeature> PATCH_CAVE_AMANITA = registerKey("patch_cave_amanita");
    public static final ResourceKey<PlacedFeature> PATCH_CAVE_AMANITA_TILLER = registerKey("patch_cave_amanita_tiller");
    public static final ResourceKey<PlacedFeature> TREE_CAVE_AMANITA = registerKey("cave_amanita_tree");
    public static final ResourceKey<PlacedFeature> PATCH_DEEP_FIRECAP = registerKey("patch_deep_firecap");
    public static final ResourceKey<PlacedFeature> PATCH_DEEP_FIRECAP_TILLER = registerKey("patch_deep_firecap_tiller");
    public static final ResourceKey<PlacedFeature> TREE_DEEP_FIRECAP = registerKey("deep_firecap_tree");
    public static final ResourceKey<PlacedFeature> PATCH_GHOSTSHROOM = registerKey("patch_ghostshroom");
    public static final ResourceKey<PlacedFeature> PATCH_GHOSTSHROOM_TILLER = registerKey("patch_ghostshroom_tiller");
    public static final ResourceKey<PlacedFeature> PATCH_SKY_FIRECAP = registerKey("patch_sky_firecap");
    public static final ResourceKey<PlacedFeature> PATCH_SKY_FIRECAP_TILLER = registerKey("patch_sky_firecap_tiller");
    public static final ResourceKey<PlacedFeature> TREE_SKY_FIRECAP = registerKey("tree_sky_firecap");
    public static final ResourceKey<PlacedFeature> PATCH_TUBESHROOMS = registerKey("patch_tubeshrooms");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_TUBESHROOMS = registerKey("patch_tall_ubeshrooms");
    public static final ResourceKey<PlacedFeature> PATCH_TRUMPET_SHROOM = registerKey("patch_trumpet_shroom");
    public static final ResourceKey<PlacedFeature> PATCH_TALL_TRUMPET_SHROOM = registerKey("patch_tall_trumpet_shroom");
    public static final ResourceKey<PlacedFeature> PATCH_VIOLET_CAPS = registerKey("patch_violet_caps");
    public static final ResourceKey<PlacedFeature> PATCH_VIOLET_CAPS_TILLER = registerKey("patch_violet_caps_tiller");
    public static final ResourceKey<PlacedFeature> PATCH_YELLOW_AMANITA = registerKey("patch_yellow_amanita");
    public static final ResourceKey<PlacedFeature> PATCH_YELLOW_AMANITA_TILLER = registerKey("patch_yellow_amanita_tiller");
    public static final ResourceKey<PlacedFeature> TREE_YELLOW_AMANITA = registerKey("tree_yellow_amanita");
    public static final ResourceKey<PlacedFeature> GLOWWORM_WEBBING = registerKey("glowworm_webbing");
    // endregion
    public static final ResourceKey<PlacedFeature> DROOPING_ICICLES = registerKey("drooping_icicles");
    public static final ResourceKey<PlacedFeature> SHORT_ICICLES = registerKey("short_icicles");
    public static final ResourceKey<PlacedFeature> STICKY_ICE = registerKey("sticky_ice");
    public static final ResourceKey<PlacedFeature> STICKY_SNOW = registerKey("sticky_snow");

    // region LUSH
    public static final ResourceKey<PlacedFeature> LUSH_CAVES_CEILING_VEGETATION = registerKey("lush_caves_ceiling_vegetation");
    public static final ResourceKey<PlacedFeature> CAVE_VINES = registerKey("cave_vines");
    public static final ResourceKey<PlacedFeature> LUSH_CAVES_CLAY = registerKey("lush_caves_clay");
    public static final ResourceKey<PlacedFeature> LUSH_CAVES_VEGETATION = registerKey("lush_caves_vegetation");
    public static final ResourceKey<PlacedFeature> SPORE_BLOSSOM = registerKey("spore_blossom");
    public static final ResourceKey<PlacedFeature> CLASSIC_VINES_CAVE = registerKey("classic_vines_cave");
    // endregion
    public static final ResourceKey<PlacedFeature> SPRING_LAVA = registerKey("spring_lava");

    static final HeightRangePlacement mushroomsRange = HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(32));
    static final HeightRangePlacement lushRange = HeightRangePlacement.triangle(VerticalAnchor.absolute(-4), VerticalAnchor.absolute(80));
    static final HeightRangePlacement nurgonRange = HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.MEDGON_LEVEL), VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL));
    static final HeightRangePlacement medgonRange = HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(MiddleEarthChunkGenerator.MEDGON_LEVEL));

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.lookup(Registries.CONFIGURED_FEATURE);
        Holder.Reference<ConfiguredFeature<?, ?>> amethystGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.AMETHYST_GEODE);
        Holder.Reference<ConfiguredFeature<?, ?>> citrineGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.CITRINE_GEODE);
        Holder.Reference<ConfiguredFeature<?, ?>> glowstoneGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.GLOWSTONE_GEODE);
        Holder.Reference<ConfiguredFeature<?, ?>> redAgateGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.RED_AGATE_GEODE);
        Holder.Reference<ConfiguredFeature<?, ?>> quartzGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.QUARTZ_GEODE);

        Holder.Reference<ConfiguredFeature<?, ?>> oreDolomite = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_DOLOMITE);
        Holder.Reference<ConfiguredFeature<?, ?>> oreOldDolomite = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_OLD_DOLOMITE);
        Holder.Reference<ConfiguredFeature<?, ?>> oreMagma = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_MAGMA);
        Holder.Reference<ConfiguredFeature<?, ?>> oreObsidian = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_OBSIDIAN);
        Holder.Reference<ConfiguredFeature<?, ?>> oreDirt = registryEntryLookup.getOrThrow(OreFeatures.ORE_DIRT);
        Holder.Reference<ConfiguredFeature<?, ?>> oreMud = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_MUD);
        Holder.Reference<ConfiguredFeature<?, ?>> oreAsh = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_ASH);
        Holder.Reference<ConfiguredFeature<?, ?>> oreAshenDirt = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_ASHEN_DIRT);
        Holder.Reference<ConfiguredFeature<?, ?>> oreDryDirt = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_DRY_DIRT);
        Holder.Reference<ConfiguredFeature<?, ?>> oreBlueIce = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_BLUE_ICE);
        Holder.Reference<ConfiguredFeature<?, ?>> orePackedIce = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_PACKED_ICE);
        Holder.Reference<ConfiguredFeature<?, ?>> oreGabbro = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_GABBRO);
        Holder.Reference<ConfiguredFeature<?, ?>> oreGalonn = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_GALONN);
        Holder.Reference<ConfiguredFeature<?, ?>> oreOldGalonn = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_OLD_GALONN);
        Holder.Reference<ConfiguredFeature<?, ?>> oreGneiss = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_GNEISS);
        Holder.Reference<ConfiguredFeature<?, ?>> oreGreenTuff = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_GREEN_TUFF);
        Holder.Reference<ConfiguredFeature<?, ?>> oreGildedGreenTuff = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_GILDED_GREEN_TUFF);
        Holder.Reference<ConfiguredFeature<?, ?>> oreGoldGreenTuff = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_GOLD_GREEN_TUFF);
        Holder.Reference<ConfiguredFeature<?, ?>> oreIzherAban = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_IZHER_ABAN);
        Holder.Reference<ConfiguredFeature<?, ?>> oreZigilAban = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_ZIGIL_ABAN);
        Holder.Reference<ConfiguredFeature<?, ?>> oreOldIzherAban = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_OLD_IZHER_ABAN);
        Holder.Reference<ConfiguredFeature<?, ?>> oreLimestone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_LIMESTONE);
        Holder.Reference<ConfiguredFeature<?, ?>> oreOldLimestone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_OLD_LIMESTONE);
        Holder.Reference<ConfiguredFeature<?, ?>> oreSand = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SAND);
        Holder.Reference<ConfiguredFeature<?, ?>> oreSandstone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SANDSTONE);
        Holder.Reference<ConfiguredFeature<?, ?>> oreSchist = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SCHIST);
        Holder.Reference<ConfiguredFeature<?, ?>> oreSnow = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SNOW);
        Holder.Reference<ConfiguredFeature<?, ?>> oreTerracotta = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_TERRACOTTA);
        Holder.Reference<ConfiguredFeature<?, ?>> oreTuff = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_TUFF);
        Holder.Reference<ConfiguredFeature<?, ?>> oreBasalt = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_BASALT);
        Holder.Reference<ConfiguredFeature<?, ?>> oreBlackstone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_BLACKSTONE);

        Holder.Reference<ConfiguredFeature<?, ?>> dolomiteCluster = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.DOLOMITE_CLUSTER);
        Holder.Reference<ConfiguredFeature<?, ?>> largeDolomite = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LARGE_DOLOMITE);
        Holder.Reference<ConfiguredFeature<?, ?>> pointedDolomite = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.POINTED_DOLOMITE);

        Holder.Reference<ConfiguredFeature<?, ?>> galonnCluster = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.GALONN_CLUSTER);
        Holder.Reference<ConfiguredFeature<?, ?>> largeGalonn = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LARGE_GALONN);
        Holder.Reference<ConfiguredFeature<?, ?>> pointedGalonn = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.POINTED_GALONN);

        Holder.Reference<ConfiguredFeature<?, ?>> izherAbanCluster = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.IZHER_ABAN_CLUSTER);
        Holder.Reference<ConfiguredFeature<?, ?>> largeIzherAban = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LARGE_IZHER_ABAN);
        Holder.Reference<ConfiguredFeature<?, ?>> pointedIzherAban = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.POINTED_IZHER_ABAN);

        Holder.Reference<ConfiguredFeature<?, ?>> limestoneCluster = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LIMESTONE_CLUSTER);
        Holder.Reference<ConfiguredFeature<?, ?>> largeLimestone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LARGE_LIMESTONE);
        Holder.Reference<ConfiguredFeature<?, ?>> pointedLimestone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.POINTED_LIMESTONE);

        Holder.Reference<ConfiguredFeature<?, ?>> poolMud = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.POOL_MUD);
        Holder.Reference<ConfiguredFeature<?, ?>> diskMycelium = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.DISK_MYCELIUM);

        Holder.Reference<ConfiguredFeature<?, ?>> delta = registryEntryLookup.getOrThrow(NetherFeatures.DELTA);
        Holder.Reference<ConfiguredFeature<?, ?>> pillarBasalt = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PILLAR_BASALT);
        Holder.Reference<ConfiguredFeature<?, ?>> pillarBlackstone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PILLAR_BLACKSTONE);
        Holder.Reference<ConfiguredFeature<?, ?>> pillarPackedIce = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PILLAR_PACKED_ICE);
        Holder.Reference<ConfiguredFeature<?, ?>> pillarSmoothSandstone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PILLAR_SMOOTH_SANDSTONE);

        Holder.Reference<ConfiguredFeature<?, ?>> coalOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_COAL);
        Holder.Reference<ConfiguredFeature<?, ?>> copperOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_COPPER);
        Holder.Reference<ConfiguredFeature<?, ?>> tinOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_TIN);
        Holder.Reference<ConfiguredFeature<?, ?>> lapisOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_LAPIS);
        Holder.Reference<ConfiguredFeature<?, ?>> leadOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_LEAD);
        Holder.Reference<ConfiguredFeature<?, ?>> quartziteOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_QUARTZITE);
        Holder.Reference<ConfiguredFeature<?, ?>> ironOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_IRON);
        Holder.Reference<ConfiguredFeature<?, ?>> silverOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SILVER);
        Holder.Reference<ConfiguredFeature<?, ?>> jadeOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_JADE);
        Holder.Reference<ConfiguredFeature<?, ?>> goldOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_GOLD);
        Holder.Reference<ConfiguredFeature<?, ?>> emeraldOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_EMERALD);
        Holder.Reference<ConfiguredFeature<?, ?>> rubyOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_RUBY);
        Holder.Reference<ConfiguredFeature<?, ?>> sapphireOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SAPPHIRE);
        Holder.Reference<ConfiguredFeature<?, ?>> adamantOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_ADAMANT);
        Holder.Reference<ConfiguredFeature<?, ?>> mithrilOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_MITHRIL);

        // region MUSHROOMS
        Holder.Reference<ConfiguredFeature<?, ?>> brownBoletteTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.BROWN_BOLETTE_TREE_KEY);
        Holder.Reference<ConfiguredFeature<?, ?>> caveAmanita = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_CAVE_AMANITA);
        Holder.Reference<ConfiguredFeature<?, ?>> caveAmanitaTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_CAVE_AMANITA_TILLER);
        Holder.Reference<ConfiguredFeature<?, ?>> caveAmanitaTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.CAVE_AMANITA_TREE_KEY);
        Holder.Reference<ConfiguredFeature<?, ?>> deepFirecap = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_DEEP_FIRECAP);
        Holder.Reference<ConfiguredFeature<?, ?>> deepFirecapTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_DEEP_FIRECAP_TILLER);
        Holder.Reference<ConfiguredFeature<?, ?>> deepFirecapTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.DEEP_FIRECAP_TREE_KEY);
        Holder.Reference<ConfiguredFeature<?, ?>> ghostshrooms = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_GHOSTSHROOM);
        Holder.Reference<ConfiguredFeature<?, ?>> ghostshroomsTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_GHOSTSHROOM_TILLER);
        Holder.Reference<ConfiguredFeature<?, ?>> skyFirecap = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_SKY_FIRECAP);
        Holder.Reference<ConfiguredFeature<?, ?>> skyFirecapTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_SKY_FIRECAP_TILLER);
        Holder.Reference<ConfiguredFeature<?, ?>> skyFirecapTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.SKY_FIRECAP_TREE_KEY);
        Holder.Reference<ConfiguredFeature<?, ?>> tubeshroom = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_TUBESHROOMS);
        Holder.Reference<ConfiguredFeature<?, ?>> tallTubeshroom = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_TALL_TUBESHROOMS);
        Holder.Reference<ConfiguredFeature<?, ?>> trumpetShroom = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_TRUMPET_SHROOM);
        Holder.Reference<ConfiguredFeature<?, ?>> tallTrumpetShroom = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_TALL_TRUMPET_SHROOM);
        Holder.Reference<ConfiguredFeature<?, ?>> violetCaps = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_VIOLET_CAPS);
        Holder.Reference<ConfiguredFeature<?, ?>> violetCapsTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_VIOLET_CAPS_TILLER);
        Holder.Reference<ConfiguredFeature<?, ?>> yellowAmanita = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_YELLOW_AMANITA);
        Holder.Reference<ConfiguredFeature<?, ?>> yellowAmanitaTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_YELLOW_AMANITA_TILLER);
        Holder.Reference<ConfiguredFeature<?, ?>> yellowAmanitaTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.YELLOW_AMANITA_TREE_KEY);
        Holder.Reference<ConfiguredFeature<?, ?>> glowwormWebbing = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.GLOWWORM_WEBBING);
        // endregion
        Holder.Reference<ConfiguredFeature<?, ?>> droopingIcicles = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.DROOPING_ICICLES);
        Holder.Reference<ConfiguredFeature<?, ?>> shortIcicles = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.SHORT_ICICLES);
        Holder.Reference<ConfiguredFeature<?, ?>> stickyIce = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.STICKY_ICE);
        Holder.Reference<ConfiguredFeature<?, ?>> stickySnow = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.STICKY_SNOW);

        Holder.Reference<ConfiguredFeature<?, ?>> caveVine = registryEntryLookup.getOrThrow(CaveFeatures.CAVE_VINE);
        Holder.Reference<ConfiguredFeature<?, ?>> mossPatch = registryEntryLookup.getOrThrow(CaveFeatures.MOSS_PATCH);
        Holder.Reference<ConfiguredFeature<?, ?>> lushCavesClay = registryEntryLookup.getOrThrow(CaveFeatures.LUSH_CAVES_CLAY);
        Holder.Reference<ConfiguredFeature<?, ?>> mossPatchCeiling = registryEntryLookup.getOrThrow(CaveFeatures.MOSS_PATCH_CEILING);
        Holder.Reference<ConfiguredFeature<?, ?>> sporeBlossom = registryEntryLookup.getOrThrow(CaveFeatures.SPORE_BLOSSOM);
        Holder.Reference<ConfiguredFeature<?, ?>> vines = registryEntryLookup.getOrThrow(VegetationFeatures.VINES);

        Holder.Reference<ConfiguredFeature<?, ?>> springLava = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.SPRING_LAVA);

        PlacementUtils.register(featureRegisterable, AMETHYST_GEODE, amethystGeode, RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(30)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, CITRINE_GEODE, citrineGeode, RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-16), VerticalAnchor.absolute(24)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, GLOWSTONE_GEODE, glowstoneGeode, RarityFilter.onAverageOnceEvery(24), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-16), VerticalAnchor.absolute(24)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, RED_AGATE_GEODE, redAgateGeode, RarityFilter.onAverageOnceEvery(48), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(-32), VerticalAnchor.absolute(0)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, QUARTZ_GEODE, quartzGeode, RarityFilter.onAverageOnceEvery(20), InSquarePlacement.spread(),
                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(6), VerticalAnchor.absolute(30)), BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, ORE_DOLOMITE, oreDolomite, modifiersWithCount(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_OLD_DOLOMITE, oreOldDolomite, modifiersWithCount(24, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_DOLOMITE_ABUNDANT, oreDolomite, modifiersWithCount(25, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_MAGMA, oreMagma, modifiersWithCount(11, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL))));
        PlacementUtils.register(featureRegisterable, ORE_MAGMA_ABUNDANT, oreMagma, modifiersWithCount(14, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(120))));
        PlacementUtils.register(featureRegisterable, ORE_OBSIDIAN, oreObsidian, modifiersWithCount(7, HeightRangePlacement.triangle(VerticalAnchor.absolute(-92), VerticalAnchor.absolute(MiddleEarthChunkGenerator.MEDGON_LEVEL))));
        PlacementUtils.register(featureRegisterable, ORE_DIRT, oreDirt, modifiersWithCount(6, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(120))));
        PlacementUtils.register(featureRegisterable, ORE_MUD, oreMud, modifiersWithCount(15, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(120))));
        PlacementUtils.register(featureRegisterable, ORE_ASH, oreAsh, modifiersWithCount(21, HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(250))));
        PlacementUtils.register(featureRegisterable, ORE_ASHEN_DIRT, oreAshenDirt, modifiersWithCount(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(160))));
        PlacementUtils.register(featureRegisterable, ORE_DRY_DIRT, oreDryDirt, modifiersWithCount(12, HeightRangePlacement.uniform(VerticalAnchor.absolute(8), VerticalAnchor.absolute(100))));
        PlacementUtils.register(featureRegisterable, ORE_BLUE_ICE, oreBlueIce, modifiersWithCount(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(8), VerticalAnchor.absolute(100))));
        PlacementUtils.register(featureRegisterable, ORE_PACKED_ICE, orePackedIce, modifiersWithCount(13, HeightRangePlacement.uniform(VerticalAnchor.absolute(8), VerticalAnchor.absolute(100))));
        PlacementUtils.register(featureRegisterable, ORE_GABBRO, oreGabbro, modifiersWithCount(23, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_GALONN, oreGalonn, modifiersWithCount(21, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_OLD_GALONN, oreOldGalonn, modifiersWithCount(24, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_GNEISS, oreGneiss, modifiersWithCount(23, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_GREEN_TUFF, oreGreenTuff, modifiersWithCount(45, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_GILDED_GREEN_TUFF, oreGildedGreenTuff, modifiersWithCount(18, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_GOLD_GREEN_TUFF, oreGoldGreenTuff, modifiersWithCount(34, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_IZHER_ABAN, oreIzherAban, modifiersWithCount(21, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_ZIGIL_ABAN, oreZigilAban, modifiersWithCount(37, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_OLD_IZHER_ABAN, oreOldIzherAban, modifiersWithCount(24, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_LIMESTONE, oreLimestone, modifiersWithCount(4, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_OLD_LIMESTONE, oreOldLimestone, modifiersWithCount(24, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_LIMESTONE_ABUNDANT, oreLimestone, modifiersWithCount(23, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_SAND, oreSand, modifiersWithCount(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))));
        PlacementUtils.register(featureRegisterable, ORE_SANDSTONE, oreSandstone, modifiersWithCount(14, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))));
        PlacementUtils.register(featureRegisterable, ORE_SCHIST, oreSchist, modifiersWithCount(23, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_SNOW, oreSnow, modifiersWithCount(18, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))));
        PlacementUtils.register(featureRegisterable, ORE_TERRACOTTA, oreTerracotta, modifiersWithCount(17, HeightRangePlacement.uniform(VerticalAnchor.absolute(0), VerticalAnchor.absolute(100))));
        PlacementUtils.register(featureRegisterable, ORE_TUFF, oreTuff, modifiersWithCount(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(0))));
        PlacementUtils.register(featureRegisterable, ORE_BASALT, oreBasalt, modifiersWithCount(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(-32), VerticalAnchor.absolute(0))));
        PlacementUtils.register(featureRegisterable, ORE_BLACKSTONE, oreBlackstone, modifiersWithCount(7, HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(-32))));

        PlacementUtils.register(featureRegisterable, DOLOMITE_CLUSTER, dolomiteCluster, CountPlacement.of(UniformInt.of(48, 96)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LARGE_DOLOMITE, largeDolomite, CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, POINTED_DOLOMITE, pointedDolomite, CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                CountPlacement.of(UniformInt.of(1, 5)), RandomOffsetPlacement.of(ClampedNormalInt.of(0.0F, 3.0F, -10, 10), ClampedNormalInt.of(0.0F, 0.6F, -2, 2)), BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, GALONN_CLUSTER, galonnCluster, CountPlacement.of(UniformInt.of(48, 96)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LARGE_GALONN, largeGalonn, CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, POINTED_GALONN, pointedGalonn, CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                CountPlacement.of(UniformInt.of(1, 5)), RandomOffsetPlacement.of(ClampedNormalInt.of(0.0F, 3.0F, -10, 10), ClampedNormalInt.of(0.0F, 0.6F, -2, 2)), BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, IZHER_ABAN_CLUSTER, izherAbanCluster, CountPlacement.of(UniformInt.of(48, 96)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LARGE_IZHER_ABAN, largeIzherAban, CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, POINTED_IZHER_ABAN, pointedIzherAban, CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                CountPlacement.of(UniformInt.of(1, 5)), RandomOffsetPlacement.of(ClampedNormalInt.of(0.0F, 3.0F, -10, 10), ClampedNormalInt.of(0.0F, 0.6F, -2, 2)), BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, LIMESTONE_CLUSTER, limestoneCluster, CountPlacement.of(UniformInt.of(48, 96)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LARGE_LIMESTONE, largeLimestone, CountPlacement.of(UniformInt.of(10, 48)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT, BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, POINTED_LIMESTONE, pointedLimestone, CountPlacement.of(UniformInt.of(192, 256)), InSquarePlacement.spread(), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                CountPlacement.of(UniformInt.of(1, 5)), RandomOffsetPlacement.of(ClampedNormalInt.of(0.0F, 3.0F, -10, 10), ClampedNormalInt.of(0.0F, 0.6F, -2, 2)), BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, POOL_MUD, poolMud, modifiersWithCount(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(120))));
        PlacementUtils.register(featureRegisterable, DISK_MYCELIUM, diskMycelium, modifiersWithCount(40, mushroomsRange));

        PlacementUtils.register(featureRegisterable, DELTA, delta, modifiersWithCount(5, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(MiddleEarthChunkGenerator.DEEPSLATE_LEVEL))));
        PlacementUtils.register(featureRegisterable, PILLAR_BASALT, pillarBasalt, modifiersWithCount(2, nurgonRange));
        PlacementUtils.register(featureRegisterable, PILLAR_BLACKSTONE, pillarBlackstone, modifiersWithCount(2, medgonRange));
        PlacementUtils.register(featureRegisterable, PILLAR_PACKED_ICE, pillarPackedIce, modifiersWithCount(3,  HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(64))));
        PlacementUtils.register(featureRegisterable, PILLAR_SMOOTH_SANDSTONE, pillarSmoothSandstone, modifiersWithCount(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(64))));

        PlacementUtils.register(featureRegisterable, ORE_COAL_UPPER, coalOre, modifiersWithCount(14, HeightRangePlacement.triangle(VerticalAnchor.absolute(60), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_COAL, coalOre, modifiersWithCount(16, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(100))));
        PlacementUtils.register(featureRegisterable, ORE_COPPER_UPPER, copperOre, modifiersWithCount(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(80), VerticalAnchor.absolute(300))));
        PlacementUtils.register(featureRegisterable, ORE_COPPER, copperOre, modifiersWithCount(12, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(120))));
        PlacementUtils.register(featureRegisterable, ORE_TIN, tinOre, modifiersWithCount(10, HeightRangePlacement.triangle(VerticalAnchor.absolute(-20), VerticalAnchor.absolute(80))));
        PlacementUtils.register(featureRegisterable, ORE_LAPIS, lapisOre, modifiersWithCount(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(MiddleEarthChunkGenerator.DEEPSLATE_LEVEL))));
        PlacementUtils.register(featureRegisterable, ORE_LAPIS_ABUNDANT, lapisOre, modifiersWithCount(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(MiddleEarthChunkGenerator.DEEPSLATE_LEVEL))));
        PlacementUtils.register(featureRegisterable, ORE_LEAD, leadOre, modifiersWithCount(9, HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(16))));
        PlacementUtils.register(featureRegisterable, ORE_QUARTZITE, quartziteOre, modifiersWithCount(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL), VerticalAnchor.absolute(60))));
        PlacementUtils.register(featureRegisterable, ORE_IRON, ironOre, modifiersWithCount(12, HeightRangePlacement.triangle(VerticalAnchor.absolute(-128), VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL + 12))));
        PlacementUtils.register(featureRegisterable, ORE_SILVER, silverOre, modifiersWithCount(3, HeightRangePlacement.triangle(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL))));
        PlacementUtils.register(featureRegisterable, ORE_JADE, jadeOre, modifiersWithCount(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(-40), VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL))));
        PlacementUtils.register(featureRegisterable, ORE_GOLD, goldOre, List.of(PlacementUtils.countExtra(2, 0.5f, 1), InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(-16)), BiomeFilter.biome()));
        PlacementUtils.register(featureRegisterable, ORE_GOLD_RARE, goldOre, List.of(PlacementUtils.countExtra(1, 0.1f, 1), InSquarePlacement.spread(),
                HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(-16)), BiomeFilter.biome()));
        PlacementUtils.register(featureRegisterable, ORE_EMERALD, emeraldOre, modifiersWithCount(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-61), VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL))));
        PlacementUtils.register(featureRegisterable, ORE_RUBY, rubyOre, modifiersWithCount(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-61), VerticalAnchor.absolute(MiddleEarthChunkGenerator.MEDGON_LEVEL))));
        PlacementUtils.register(featureRegisterable, ORE_SAPPHIRE, sapphireOre, modifiersWithCount(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-49), VerticalAnchor.absolute(-11))));
        PlacementUtils.register(featureRegisterable, ORE_ADAMANT, adamantOre, modifiersWithCount(2, HeightRangePlacement.triangle(VerticalAnchor.absolute(-61), VerticalAnchor.absolute(-42))));
        PlacementUtils.register(featureRegisterable, ORE_MITHRIL, mithrilOre, modifiersWithRarity(1, HeightRangePlacement.triangle(VerticalAnchor.absolute(-62), VerticalAnchor.absolute(MAX_MITHRIL_HEIGHT))));

        // region MUSHROOMS
        PlacementUtils.register(featureRegisterable, TREE_BROWN_BOLETTE, brownBoletteTree, List.of(RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(),
                CountOnEveryLayerPlacement.of(2), BiomeFilter.biome()));

        PlacementUtils.register(featureRegisterable, PATCH_CAVE_AMANITA, caveAmanita, modifiersWithCount(1, mushroomsRange));
        PlacementUtils.register(featureRegisterable, PATCH_CAVE_AMANITA_TILLER, caveAmanitaTiller, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, TREE_CAVE_AMANITA, caveAmanitaTree, List.of(RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(),
                CountOnEveryLayerPlacement.of(2), BiomeFilter.biome()));

        PlacementUtils.register(featureRegisterable, PATCH_DEEP_FIRECAP, deepFirecap, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, PATCH_DEEP_FIRECAP_TILLER, deepFirecapTiller, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, TREE_DEEP_FIRECAP, deepFirecapTree, List.of(RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(),
                CountOnEveryLayerPlacement.of(1), BiomeFilter.biome()));

        PlacementUtils.register(featureRegisterable, PATCH_GHOSTSHROOM, ghostshrooms, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, PATCH_GHOSTSHROOM_TILLER, ghostshroomsTiller, modifiersWithCount(1, mushroomsRange ));

        PlacementUtils.register(featureRegisterable, PATCH_SKY_FIRECAP, skyFirecap, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, PATCH_SKY_FIRECAP_TILLER, skyFirecapTiller, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, TREE_SKY_FIRECAP, skyFirecapTree, List.of(RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(),
                CountOnEveryLayerPlacement.of(1), BiomeFilter.biome()));

        PlacementUtils.register(featureRegisterable, PATCH_TUBESHROOMS, tubeshroom, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, PATCH_TALL_TUBESHROOMS, tallTubeshroom, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, PATCH_TRUMPET_SHROOM, trumpetShroom, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, PATCH_TALL_TRUMPET_SHROOM, tallTrumpetShroom, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, PATCH_VIOLET_CAPS, violetCaps, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, PATCH_VIOLET_CAPS_TILLER, violetCapsTiller, modifiersWithCount(1, mushroomsRange ));

        PlacementUtils.register(featureRegisterable, PATCH_YELLOW_AMANITA, yellowAmanita, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, PATCH_YELLOW_AMANITA_TILLER, yellowAmanitaTiller, modifiersWithCount(1, mushroomsRange ));
        PlacementUtils.register(featureRegisterable, TREE_YELLOW_AMANITA, yellowAmanitaTree, List.of(RarityFilter.onAverageOnceEvery(1), InSquarePlacement.spread(),
                CountOnEveryLayerPlacement.of(2), BiomeFilter.biome()));

        PlacementUtils.register(featureRegisterable, GLOWWORM_WEBBING, glowwormWebbing, CountPlacement.of(47), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(48)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome());
        // endregion

        PlacementUtils.register(featureRegisterable, DROOPING_ICICLES, droopingIcicles, CountPlacement.of(37), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(64)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SHORT_ICICLES, shortIcicles, CountPlacement.of(49), InSquarePlacement.spread(), HeightRangePlacement.triangle(VerticalAnchor.absolute(-48), VerticalAnchor.absolute(64)),
                EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)),
                BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, STICKY_ICE, stickyIce, CountPlacement.of(UniformInt.of(204, 250)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, STICKY_SNOW, stickySnow, CountPlacement.of(UniformInt.of(204, 250)), PlacementUtils.RANGE_BOTTOM_TO_MAX_TERRAIN_HEIGHT,
                InSquarePlacement.spread(), SurfaceRelativeThresholdFilter.of(Heightmap.Types.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomeFilter.biome());

        PlacementUtils.register(featureRegisterable, CAVE_VINES, caveVine, CountPlacement.of(188), InSquarePlacement.spread(), lushRange, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LUSH_CAVES_VEGETATION, mossPatch, CountPlacement.of(125), InSquarePlacement.spread(), lushRange, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LUSH_CAVES_CLAY, lushCavesClay, CountPlacement.of(62), InSquarePlacement.spread(), lushRange, EnvironmentScanPlacement.scanningFor(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(1)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, LUSH_CAVES_CEILING_VEGETATION, mossPatchCeiling, CountPlacement.of(125), InSquarePlacement.spread(), lushRange, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, SPORE_BLOSSOM, sporeBlossom, CountPlacement.of(25), InSquarePlacement.spread(), lushRange, EnvironmentScanPlacement.scanningFor(Direction.UP, BlockPredicate.solid(), BlockPredicate.ONLY_IN_AIR_PREDICATE, 12), RandomOffsetPlacement.vertical(ConstantInt.of(-1)), BiomeFilter.biome());
        PlacementUtils.register(featureRegisterable, CLASSIC_VINES_CAVE, vines, CountPlacement.of(256), InSquarePlacement.spread(), lushRange, BiomeFilter.biome());


        PlacementUtils.register(featureRegisterable, SPRING_LAVA, springLava, modifiersWithCount(20, HeightRangePlacement.triangle(VerticalAnchor.bottom(), VerticalAnchor.absolute(MiddleEarthChunkGenerator.NURGON_LEVEL)) ));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, name));
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, InSquarePlacement.spread(), heightModifier, BiomeFilter.biome());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacement.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilter.onAverageOnceEvery(chance), heightModifier);
    }
}
