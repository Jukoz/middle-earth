package net.jukoz.me.world.features.underground;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.jukoz.me.world.features.tree.MushroomTreeConfiguredFeatures;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.math.intprovider.ClampedNormalIntProvider;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.blockpredicate.BlockPredicate;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class CavesPlacedFeatures {
    public static final int MAX_MITHRIL_HEIGHT = -55;

    // region GEODES
    public static final RegistryKey<PlacedFeature> AMETHYST_GEODE = registerKey("amethyst_geode");
    public static final RegistryKey<PlacedFeature> CITRINE_GEODE = registerKey("citrine_geode");
    public static final RegistryKey<PlacedFeature> GLOWSTONE_GEODE = registerKey("glowstone_geode");
    public static final RegistryKey<PlacedFeature> RED_AGATE_GEODE = registerKey("red_agate_geode");
    public static final RegistryKey<PlacedFeature> QUARTZ_GEODE = registerKey("quartz_geode");
    // endregion

    // region ORES
    public static final RegistryKey<PlacedFeature> ORE_ASH = registerKey("ore_ash");
    public static final RegistryKey<PlacedFeature> ORE_ASHEN_DIRT = registerKey("ore_ashen_dirt");
    public static final RegistryKey<PlacedFeature> ORE_BASALT = registerKey("ore_basalt");
    public static final RegistryKey<PlacedFeature> ORE_BLACKSTONE = registerKey("ore_black_stone");
    public static final RegistryKey<PlacedFeature> ORE_BLUE_ICE = registerKey("ore_blue_ice");
    public static final RegistryKey<PlacedFeature> ORE_PACKED_ICE = registerKey("ore_packed_ice");
    public static final RegistryKey<PlacedFeature> ORE_DIRT = registerKey("ore_dirt");
    public static final RegistryKey<PlacedFeature> ORE_DOLOMITE = registerKey("ore_dolomite");
    public static final RegistryKey<PlacedFeature> ORE_OLD_DOLOMITE = registerKey("ore_old_dolomite");
    public static final RegistryKey<PlacedFeature> ORE_DOLOMITE_ABUNDANT = registerKey("ore_dolomite_abundant");
    public static final RegistryKey<PlacedFeature> ORE_DRY_DIRT = registerKey("ore_dry_dirt");
    public static final RegistryKey<PlacedFeature> ORE_IZHER_ABAN = registerKey("ore_izher_aban");
    public static final RegistryKey<PlacedFeature> ORE_OLD_IZHER_ABAN = registerKey("ore_old_izher_aban");
    public static final RegistryKey<PlacedFeature> ORE_IZHER_ABAN_ABUNDANT = registerKey("ore_izher_aban_abundant");
    public static final RegistryKey<PlacedFeature> ORE_LIMESTONE = registerKey("ore_limestone");
    public static final RegistryKey<PlacedFeature> ORE_OLD_LIMESTONE = registerKey("ore_old_limestone");
    public static final RegistryKey<PlacedFeature> ORE_LIMESTONE_ABUNDANT = registerKey("ore_limestone_abundant");
    public static final RegistryKey<PlacedFeature> ORE_MAGMA = registerKey("ore_magma");
    public static final RegistryKey<PlacedFeature> ORE_MAGMA_ABUNDANT = registerKey("ore_magma_abundant");
    public static final RegistryKey<PlacedFeature> ORE_MUD = registerKey("ore_mud");
    public static final RegistryKey<PlacedFeature> ORE_OBSIDIAN = registerKey("ore_obsidian");
    public static final RegistryKey<PlacedFeature> ORE_SAND = registerKey("ore_sand");
    public static final RegistryKey<PlacedFeature> ORE_SANDSTONE = registerKey("ore_sandstone");
    public static final RegistryKey<PlacedFeature> ORE_SNOW = registerKey("ore_snow");
    public static final RegistryKey<PlacedFeature> ORE_TERRACOTTA = registerKey("ore_terracotta");
    public static final RegistryKey<PlacedFeature> ORE_TUFF = registerKey("ore_tuff");
    // endregion

    public static final RegistryKey<PlacedFeature> DOLOMITE_CLUSTER = registerKey("dolomite_cluster");
    public static final RegistryKey<PlacedFeature> LARGE_DOLOMITE = registerKey("large_dolomite");
    public static final RegistryKey<PlacedFeature> POINTED_DOLOMITE = registerKey("pointed_dolomite");

    public static final RegistryKey<PlacedFeature> IZHER_ABAN_CLUSTER = registerKey("izher_aban_cluster");
    public static final RegistryKey<PlacedFeature> LARGE_IZHER_ABAN = registerKey("large_izher_aban");
    public static final RegistryKey<PlacedFeature> POINTED_IZHER_ABAN = registerKey("pointed_izher_aban");

    public static final RegistryKey<PlacedFeature> LIMESTONE_CLUSTER = registerKey("limestone_cluster");
    public static final RegistryKey<PlacedFeature> LARGE_LIMESTONE = registerKey("large_limestone");
    public static final RegistryKey<PlacedFeature> POINTED_LIMESTONE = registerKey("pointed_limestone");

    public static final RegistryKey<PlacedFeature> POOL_MUD = registerKey("pool_mud");
    public static final RegistryKey<PlacedFeature> DISK_MYCELIUM = registerKey("disk_mycelium");

    public static final RegistryKey<PlacedFeature> DELTA = registerKey("delta");

    public static final RegistryKey<PlacedFeature> PILLAR_BASALT = registerKey("pillar_polished_basalt");
    public static final RegistryKey<PlacedFeature> PILLAR_BLACKSTONE = registerKey("pillar_blackstone");
    public static final RegistryKey<PlacedFeature> PILLAR_PACKED_ICE = registerKey("pillar_packed_ice");
    public static final RegistryKey<PlacedFeature> PILLAR_SMOOTH_SANDSTONE = registerKey("pillar_smooth_sandstone");

    // region MATERIALS
    public static final RegistryKey<PlacedFeature> ORE_COAL = registerKey("ore_coal");
    public static final RegistryKey<PlacedFeature> ORE_COAL_UPPER = registerKey("ore_coal_upper");
    public static final RegistryKey<PlacedFeature> ORE_COPPER = registerKey("ore_copper");
    public static final RegistryKey<PlacedFeature> ORE_COPPER_UPPER = registerKey("ore_copper_upper");
    public static final RegistryKey<PlacedFeature> ORE_TIN = registerKey("ore_tin");
    public static final RegistryKey<PlacedFeature> ORE_LAPIS = registerKey("ore_lapis");
    public static final RegistryKey<PlacedFeature> ORE_LAPIS_ABUNDANT = registerKey("ore_lapis_abundant");
    public static final RegistryKey<PlacedFeature> ORE_LEAD = registerKey("ore_lead");
    public static final RegistryKey<PlacedFeature> ORE_QUARTZITE = registerKey("ore_quartzite");
    public static final RegistryKey<PlacedFeature> ORE_IRON = registerKey("ore_iron");
    public static final RegistryKey<PlacedFeature> ORE_SILVER = registerKey("ore_silver");
    public static final RegistryKey<PlacedFeature> ORE_JADE = registerKey("ore_jade");
    public static final RegistryKey<PlacedFeature> ORE_GOLD = registerKey("ore_gold");
    public static final RegistryKey<PlacedFeature> ORE_EMERALD = registerKey("ore_emerald");
    public static final RegistryKey<PlacedFeature> ORE_MITHRIL = registerKey("ore_mithril");
    // endregion

    // region VEGETATION
    public static final RegistryKey<PlacedFeature> TREE_BROWN_BOLETTE = registerKey("brown_bolette_tree");
    public static final RegistryKey<PlacedFeature> PATCH_CAVE_AMANITA = registerKey("patch_cave_amanita");
    public static final RegistryKey<PlacedFeature> PATCH_CAVE_AMANITA_TILLER = registerKey("patch_cave_amanita_tiller");
    public static final RegistryKey<PlacedFeature> TREE_CAVE_AMANITA = registerKey("cave_amanita_tree");
    public static final RegistryKey<PlacedFeature> PATCH_DEEP_FIRECAP = registerKey("patch_deep_firecap");
    public static final RegistryKey<PlacedFeature> PATCH_DEEP_FIRECAP_TILLER = registerKey("patch_deep_firecap_tiller");
    public static final RegistryKey<PlacedFeature> TREE_DEEP_FIRECAP = registerKey("deep_firecap_tree");
    public static final RegistryKey<PlacedFeature> PATCH_GHOSTSHROOM = registerKey("patch_ghostshroom");
    public static final RegistryKey<PlacedFeature> PATCH_GHOSTSHROOM_TILLER = registerKey("patch_ghostshroom_tiller");
    public static final RegistryKey<PlacedFeature> PATCH_SKY_FIRECAP = registerKey("patch_sky_firecap");
    public static final RegistryKey<PlacedFeature> PATCH_SKY_FIRECAP_TILLER = registerKey("patch_sky_firecap_tiller");
    public static final RegistryKey<PlacedFeature> TREE_SKY_FIRECAP = registerKey("tree_sky_firecap");
    public static final RegistryKey<PlacedFeature> PATCH_TUBESHROOMS = registerKey("patch_tubeshrooms");
    public static final RegistryKey<PlacedFeature> PATCH_TALL_TUBESHROOMS = registerKey("patch_tall_ubeshrooms");
    public static final RegistryKey<PlacedFeature> PATCH_TRUMPET_SHROOM = registerKey("patch_trumpet_shroom");
    public static final RegistryKey<PlacedFeature> PATCH_TALL_TRUMPET_SHROOM = registerKey("patch_tall_trumpet_shroom");
    public static final RegistryKey<PlacedFeature> PATCH_VIOLET_CAPS = registerKey("patch_violet_caps");
    public static final RegistryKey<PlacedFeature> PATCH_VIOLET_CAPS_TILLER = registerKey("patch_violet_caps_tiller");
    public static final RegistryKey<PlacedFeature> PATCH_YELLOW_AMANITA = registerKey("patch_yellow_amanita");
    public static final RegistryKey<PlacedFeature> PATCH_YELLOW_AMANITA_TILLER = registerKey("patch_yellow_amanita_tiller");
    public static final RegistryKey<PlacedFeature> TREE_YELLOW_AMANITA = registerKey("tree_yellow_amanita");
    public static final RegistryKey<PlacedFeature> GLOWWORM_WEBBING = registerKey("glowworm_webbing");
    // endregion
    public static final RegistryKey<PlacedFeature> DROOPING_ICICLES = registerKey("drooping_icicles");
    public static final RegistryKey<PlacedFeature> SHORT_ICICLES = registerKey("short_icicles");
    public static final RegistryKey<PlacedFeature> STICKY_ICE = registerKey("sticky_ice");
    public static final RegistryKey<PlacedFeature> STICKY_SNOW = registerKey("sticky_snow");

    // region LUSH
    public static final RegistryKey<PlacedFeature> LUSH_CAVES_CEILING_VEGETATION = registerKey("lush_caves_ceiling_vegetation");
    public static final RegistryKey<PlacedFeature> CAVE_VINES = registerKey("cave_vines");
    public static final RegistryKey<PlacedFeature> LUSH_CAVES_CLAY = registerKey("lush_caves_clay");
    public static final RegistryKey<PlacedFeature> LUSH_CAVES_VEGETATION = registerKey("lush_caves_vegetation");
    public static final RegistryKey<PlacedFeature> ROOTED_AZALEA_TREE = registerKey("rooted_azalea_tree");
    public static final RegistryKey<PlacedFeature> SPORE_BLOSSOM = registerKey("spore_blossom");
    public static final RegistryKey<PlacedFeature> CLASSIC_VINES_CAVE = registerKey("classic_vines_cave");
    // endregion
    public static final RegistryKey<PlacedFeature> SPRING_LAVA = registerKey("spring_lava");

    static final HeightRangePlacementModifier mushroomsRange = HeightRangePlacementModifier.trapezoid(YOffset.fixed(-40), YOffset.fixed(32));
    static final HeightRangePlacementModifier lushRange = HeightRangePlacementModifier.trapezoid(YOffset.fixed(-4), YOffset.fixed(80));
    static final HeightRangePlacementModifier nurgonRange = HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.MEDGON_LEVEL), YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL));
    static final HeightRangePlacementModifier medgonRange = HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(MiddleEarthChunkGenerator.MEDGON_LEVEL));

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> amethystGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.AMETHYST_GEODE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> citrineGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.CITRINE_GEODE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> glowstoneGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.GLOWSTONE_GEODE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> redAgateGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.RED_AGATE_GEODE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> quartzGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.QUARTZ_GEODE);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreDolomite = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_DOLOMITE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreOldDolomite = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_OLD_DOLOMITE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreMagma = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_MAGMA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreObsidian = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_OBSIDIAN);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreDirt = registryEntryLookup.getOrThrow(OreConfiguredFeatures.ORE_DIRT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreMud = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_MUD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreAsh = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_ASH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreAshenDirt = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_ASHEN_DIRT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreDryDirt = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_DRY_DIRT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreBlueIce = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_BLUE_ICE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> orePackedIce = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_PACKED_ICE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreLimestone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_LIMESTONE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreOldLimestone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_OLD_LIMESTONE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreSand = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SAND);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreSandstone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SANDSTONE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreSnow = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SNOW);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreTerracotta = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_TERRACOTTA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreTuff = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_TUFF);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreBasalt = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_BASALT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreBlackstone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_BLACKSTONE);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> dolomiteCluster = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.DOLOMITE_CLUSTER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> largeDolomite = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LARGE_DOLOMITE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pointedDolomite = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.POINTED_DOLOMITE);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> izherAbanCluster = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.IZHER_ABAN_CLUSTER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> largeIzherAban = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LARGE_IZHER_ABAN);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pointedIzherAban = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.POINTED_IZHER_ABAN);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> limestoneCluster = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LIMESTONE_CLUSTER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> largeLimestone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LARGE_LIMESTONE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pointedLimestone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.POINTED_LIMESTONE);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> poolMud = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.POOL_MUD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> diskMycelium = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.DISK_MYCELIUM);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> delta = registryEntryLookup.getOrThrow(NetherConfiguredFeatures.DELTA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pillarBasalt = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PILLAR_BASALT);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pillarBlackstone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PILLAR_BLACKSTONE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pillarPackedIce = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PILLAR_PACKED_ICE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> pillarSmoothSandstone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PILLAR_SMOOTH_SANDSTONE);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> coalOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_COAL);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> copperOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_COPPER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tinOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_TIN);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> lapisOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_LAPIS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> leadOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_LEAD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> quartziteOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_QUARTZITE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ironOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_IRON);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> silverOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SILVER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> jadeOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_JADE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> goldOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_GOLD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> emeraldOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_EMERALD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mithrilOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_MITHRIL);

        // region MUSHROOMS
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> brownBoletteTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.BROWN_BOLETTE_TREE_KEY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> caveAmanita = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_CAVE_AMANITA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> caveAmanitaTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_CAVE_AMANITA_TILLER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> caveAmanitaTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.CAVE_AMANITA_TREE_KEY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> deepFirecap = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_DEEP_FIRECAP);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> deepFirecapTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_DEEP_FIRECAP_TILLER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> deepFirecapTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.DEEP_FIRECAP_TREE_KEY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ghostshrooms = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_GHOSTSHROOM);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ghostshroomsTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_GHOSTSHROOM_TILLER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> skyFirecap = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_SKY_FIRECAP);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> skyFirecapTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_SKY_FIRECAP_TILLER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> skyFirecapTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.SKY_FIRECAP_TREE_KEY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tubeshroom = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_TUBESHROOMS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tallTubeshroom = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_TALL_TUBESHROOMS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> trumpetShroom = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_TRUMPET_SHROOM);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tallTrumpetShroom = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_TALL_TRUMPET_SHROOM);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> violetCaps = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_VIOLET_CAPS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> violetCapsTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_VIOLET_CAPS_TILLER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> yellowAmanita = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_YELLOW_AMANITA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> yellowAmanitaTiller = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.PATCH_YELLOW_AMANITA_TILLER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> yellowAmanitaTree = registryEntryLookup.getOrThrow(MushroomTreeConfiguredFeatures.YELLOW_AMANITA_TREE_KEY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> glowwormWebbing = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.GLOWWORM_WEBBING);
        // endregion
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> droopingIcicles = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.DROOPING_ICICLES);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> shortIcicles = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.SHORT_ICICLES);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> stickyIce = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.STICKY_ICE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> stickySnow = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.STICKY_SNOW);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> caveVine = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.CAVE_VINE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mossPatch = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.MOSS_PATCH);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> lushCavesClay = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.LUSH_CAVES_CLAY);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mossPatchCeiling = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.MOSS_PATCH_CEILING);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> sporeBlossom = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.SPORE_BLOSSOM);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> vines = registryEntryLookup.getOrThrow(VegetationConfiguredFeatures.VINES);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> springLava = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.SPRING_LAVA);

        PlacedFeatures.register(featureRegisterable, AMETHYST_GEODE, amethystGeode, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.fixed(30)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, CITRINE_GEODE, citrineGeode, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(-16), YOffset.fixed(24)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, GLOWSTONE_GEODE, glowstoneGeode, RarityFilterPlacementModifier.of(24), SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(-16), YOffset.fixed(24)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, RED_AGATE_GEODE, redAgateGeode, RarityFilterPlacementModifier.of(48), SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(-32), YOffset.fixed(0)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, QUARTZ_GEODE, quartzGeode, RarityFilterPlacementModifier.of(20), SquarePlacementModifier.of(),
                HeightRangePlacementModifier.uniform(YOffset.aboveBottom(6), YOffset.fixed(30)), BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, ORE_DOLOMITE, oreDolomite, modifiersWithCount(4, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(320))));
        PlacedFeatures.register(featureRegisterable, ORE_OLD_DOLOMITE, oreOldDolomite, modifiersWithCount(24, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(320))));
        PlacedFeatures.register(featureRegisterable, ORE_DOLOMITE_ABUNDANT, oreDolomite, modifiersWithCount(25, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(320))));
        PlacedFeatures.register(featureRegisterable, ORE_MAGMA, oreMagma, modifiersWithCount(11, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-128), YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_MAGMA_ABUNDANT, oreMagma, modifiersWithCount(14, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-128), YOffset.fixed(120))));
        PlacedFeatures.register(featureRegisterable, ORE_OBSIDIAN, oreObsidian, modifiersWithCount(7, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-92), YOffset.fixed(MiddleEarthChunkGenerator.MEDGON_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_DIRT, oreDirt, modifiersWithCount(6, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(120))));
        PlacedFeatures.register(featureRegisterable, ORE_MUD, oreMud, modifiersWithCount(15, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(120))));
        PlacedFeatures.register(featureRegisterable, ORE_ASH, oreAsh, modifiersWithCount(21, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-48), YOffset.fixed(250))));
        PlacedFeatures.register(featureRegisterable, ORE_ASHEN_DIRT, oreAshenDirt, modifiersWithCount(7, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(160))));
        PlacedFeatures.register(featureRegisterable, ORE_DRY_DIRT, oreDryDirt, modifiersWithCount(12, HeightRangePlacementModifier.uniform(YOffset.fixed(8), YOffset.fixed(100))));
        PlacedFeatures.register(featureRegisterable, ORE_BLUE_ICE, oreBlueIce, modifiersWithCount(7, HeightRangePlacementModifier.uniform(YOffset.fixed(8), YOffset.fixed(100))));
        PlacedFeatures.register(featureRegisterable, ORE_PACKED_ICE, orePackedIce, modifiersWithCount(13, HeightRangePlacementModifier.uniform(YOffset.fixed(8), YOffset.fixed(100))));
        PlacedFeatures.register(featureRegisterable, ORE_LIMESTONE, oreLimestone, modifiersWithCount(4, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(320))));
        PlacedFeatures.register(featureRegisterable, ORE_OLD_LIMESTONE, oreOldLimestone, modifiersWithCount(24, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(320))));
        PlacedFeatures.register(featureRegisterable, ORE_LIMESTONE_ABUNDANT, oreLimestone, modifiersWithCount(23, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(320))));
        PlacedFeatures.register(featureRegisterable, ORE_SAND, oreSand, modifiersWithCount(7, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(100))));
        PlacedFeatures.register(featureRegisterable, ORE_SANDSTONE, oreSandstone, modifiersWithCount(14, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(100))));
        PlacedFeatures.register(featureRegisterable, ORE_SNOW, oreSnow, modifiersWithCount(18, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(100))));
        PlacedFeatures.register(featureRegisterable, ORE_TERRACOTTA, oreTerracotta, modifiersWithCount(17, HeightRangePlacementModifier.uniform(YOffset.fixed(0), YOffset.fixed(100))));
        PlacedFeatures.register(featureRegisterable, ORE_TUFF, oreTuff, modifiersWithCount(7, HeightRangePlacementModifier.uniform(YOffset.fixed(-32), YOffset.fixed(0))));
        PlacedFeatures.register(featureRegisterable, ORE_BASALT, oreBasalt, modifiersWithCount(7, HeightRangePlacementModifier.uniform(YOffset.fixed(-32), YOffset.fixed(0))));
        PlacedFeatures.register(featureRegisterable, ORE_BLACKSTONE, oreBlackstone, modifiersWithCount(7, HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(-32))));

        PlacedFeatures.register(featureRegisterable, DOLOMITE_CLUSTER, dolomiteCluster, CountPlacementModifier.of(UniformIntProvider.create(48, 96)), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LARGE_DOLOMITE, largeDolomite, CountPlacementModifier.of(UniformIntProvider.create(10, 48)), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, POINTED_DOLOMITE, pointedDolomite, CountPlacementModifier.of(UniformIntProvider.create(192, 256)), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE,
                CountPlacementModifier.of(UniformIntProvider.create(1, 5)), RandomOffsetPlacementModifier.of(ClampedNormalIntProvider.of(0.0F, 3.0F, -10, 10), ClampedNormalIntProvider.of(0.0F, 0.6F, -2, 2)), BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, IZHER_ABAN_CLUSTER, izherAbanCluster, CountPlacementModifier.of(UniformIntProvider.create(48, 96)), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LARGE_IZHER_ABAN, largeIzherAban, CountPlacementModifier.of(UniformIntProvider.create(10, 48)), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, POINTED_IZHER_ABAN, pointedIzherAban, CountPlacementModifier.of(UniformIntProvider.create(192, 256)), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE,
                CountPlacementModifier.of(UniformIntProvider.create(1, 5)), RandomOffsetPlacementModifier.of(ClampedNormalIntProvider.of(0.0F, 3.0F, -10, 10), ClampedNormalIntProvider.of(0.0F, 0.6F, -2, 2)), BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, LIMESTONE_CLUSTER, limestoneCluster, CountPlacementModifier.of(UniformIntProvider.create(48, 96)), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LARGE_LIMESTONE, largeLimestone, CountPlacementModifier.of(UniformIntProvider.create(10, 48)), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE, BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, POINTED_LIMESTONE, pointedLimestone, CountPlacementModifier.of(UniformIntProvider.create(192, 256)), SquarePlacementModifier.of(), PlacedFeatures.BOTTOM_TO_120_RANGE,
                CountPlacementModifier.of(UniformIntProvider.create(1, 5)), RandomOffsetPlacementModifier.of(ClampedNormalIntProvider.of(0.0F, 3.0F, -10, 10), ClampedNormalIntProvider.of(0.0F, 0.6F, -2, 2)), BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, POOL_MUD, poolMud, modifiersWithCount(9, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(120))));
        PlacedFeatures.register(featureRegisterable, DISK_MYCELIUM, diskMycelium, modifiersWithCount(40, mushroomsRange));

        PlacedFeatures.register(featureRegisterable, DELTA, delta, modifiersWithCount(5, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-128), YOffset.fixed(MiddleEarthChunkGenerator.DEEPSLATE_LEVEL))));
        PlacedFeatures.register(featureRegisterable, PILLAR_BASALT, pillarBasalt, modifiersWithCount(2, nurgonRange));
        PlacedFeatures.register(featureRegisterable, PILLAR_BLACKSTONE, pillarBlackstone, modifiersWithCount(2, medgonRange));
        PlacedFeatures.register(featureRegisterable, PILLAR_PACKED_ICE, pillarPackedIce, modifiersWithCount(3,  HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(64))));
        PlacedFeatures.register(featureRegisterable, PILLAR_SMOOTH_SANDSTONE, pillarSmoothSandstone, modifiersWithCount(3, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(64))));

        PlacedFeatures.register(featureRegisterable, ORE_COAL_UPPER, coalOre, modifiersWithCount(14, HeightRangePlacementModifier.trapezoid(YOffset.fixed(60), YOffset.fixed(300))));
        PlacedFeatures.register(featureRegisterable, ORE_COAL, coalOre, modifiersWithCount(16, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(100))));
        PlacedFeatures.register(featureRegisterable, ORE_COPPER_UPPER, copperOre, modifiersWithCount(10, HeightRangePlacementModifier.trapezoid(YOffset.fixed(80), YOffset.fixed(300))));
        PlacedFeatures.register(featureRegisterable, ORE_COPPER, copperOre, modifiersWithCount(12, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(120))));
        PlacedFeatures.register(featureRegisterable, ORE_TIN, tinOre, modifiersWithCount(10, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-20), YOffset.fixed(80))));
        PlacedFeatures.register(featureRegisterable, ORE_LAPIS, lapisOre, modifiersWithCount(1, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(MiddleEarthChunkGenerator.DEEPSLATE_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_LAPIS_ABUNDANT, lapisOre, modifiersWithCount(2, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(MiddleEarthChunkGenerator.DEEPSLATE_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_LEAD, leadOre, modifiersWithCount(9, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-48), YOffset.fixed(16))));
        PlacedFeatures.register(featureRegisterable, ORE_QUARTZITE, quartziteOre, modifiersWithCount(2, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(60))));
        PlacedFeatures.register(featureRegisterable, ORE_IRON, ironOre, modifiersWithCount(12, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-128), YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL + 12))));
        PlacedFeatures.register(featureRegisterable, ORE_SILVER, silverOre, modifiersWithCount(3, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-64), YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_JADE, jadeOre, modifiersWithCount(1, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-40), YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_GOLD, goldOre, List.of(PlacedFeatures.createCountExtraModifier(1, 0.5f, 1), SquarePlacementModifier.of(),
                HeightRangePlacementModifier.trapezoid(YOffset.fixed(-48), YOffset.fixed(-16)), BiomePlacementModifier.of()));
        PlacedFeatures.register(featureRegisterable, ORE_EMERALD, emeraldOre, modifiersWithRarity(1, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL), YOffset.fixed(MiddleEarthChunkGenerator.DEEPSLATE_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_MITHRIL, mithrilOre, modifiersWithRarity(3, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-61), YOffset.fixed(MAX_MITHRIL_HEIGHT))));

        // region MUSHROOMS
        PlacedFeatures.register(featureRegisterable, TREE_BROWN_BOLETTE, brownBoletteTree, List.of(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(),
                CountMultilayerPlacementModifier.of(2), BiomePlacementModifier.of()));

        PlacedFeatures.register(featureRegisterable, PATCH_CAVE_AMANITA, caveAmanita, modifiersWithCount(1, mushroomsRange));
        PlacedFeatures.register(featureRegisterable, PATCH_CAVE_AMANITA_TILLER, caveAmanitaTiller, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, TREE_CAVE_AMANITA, caveAmanitaTree, List.of(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(),
                CountMultilayerPlacementModifier.of(2), BiomePlacementModifier.of()));

        PlacedFeatures.register(featureRegisterable, PATCH_DEEP_FIRECAP, deepFirecap, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, PATCH_DEEP_FIRECAP_TILLER, deepFirecapTiller, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, TREE_DEEP_FIRECAP, deepFirecapTree, List.of(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(),
                CountMultilayerPlacementModifier.of(1), BiomePlacementModifier.of()));

        PlacedFeatures.register(featureRegisterable, PATCH_GHOSTSHROOM, ghostshrooms, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, PATCH_GHOSTSHROOM_TILLER, ghostshroomsTiller, modifiersWithCount(1, mushroomsRange ));

        PlacedFeatures.register(featureRegisterable, PATCH_SKY_FIRECAP, skyFirecap, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, PATCH_SKY_FIRECAP_TILLER, skyFirecapTiller, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, TREE_SKY_FIRECAP, skyFirecapTree, List.of(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(),
                CountMultilayerPlacementModifier.of(1), BiomePlacementModifier.of()));

        PlacedFeatures.register(featureRegisterable, PATCH_TUBESHROOMS, tubeshroom, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, PATCH_TALL_TUBESHROOMS, tallTubeshroom, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, PATCH_TRUMPET_SHROOM, trumpetShroom, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, PATCH_TALL_TRUMPET_SHROOM, tallTrumpetShroom, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, PATCH_VIOLET_CAPS, violetCaps, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, PATCH_VIOLET_CAPS_TILLER, violetCapsTiller, modifiersWithCount(1, mushroomsRange ));

        PlacedFeatures.register(featureRegisterable, PATCH_YELLOW_AMANITA, yellowAmanita, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, PATCH_YELLOW_AMANITA_TILLER, yellowAmanitaTiller, modifiersWithCount(1, mushroomsRange ));
        PlacedFeatures.register(featureRegisterable, TREE_YELLOW_AMANITA, yellowAmanitaTree, List.of(RarityFilterPlacementModifier.of(1), SquarePlacementModifier.of(),
                CountMultilayerPlacementModifier.of(2), BiomePlacementModifier.of()));

        PlacedFeatures.register(featureRegisterable, GLOWWORM_WEBBING, glowwormWebbing, CountPlacementModifier.of(47), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-48), YOffset.fixed(48)),
                EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
                BiomePlacementModifier.of());
        // endregion

        PlacedFeatures.register(featureRegisterable, DROOPING_ICICLES, droopingIcicles, CountPlacementModifier.of(37), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-48), YOffset.fixed(64)),
                EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
                BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SHORT_ICICLES, shortIcicles, CountPlacementModifier.of(49), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.fixed(-48), YOffset.fixed(64)),
                EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)),
                BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, STICKY_ICE, stickyIce, CountPlacementModifier.of(UniformIntProvider.create(204, 250)), PlacedFeatures.BOTTOM_TO_120_RANGE,
                SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, STICKY_SNOW, stickySnow, CountPlacementModifier.of(UniformIntProvider.create(204, 250)), PlacedFeatures.BOTTOM_TO_120_RANGE,
                SquarePlacementModifier.of(), SurfaceThresholdFilterPlacementModifier.of(Heightmap.Type.OCEAN_FLOOR_WG, Integer.MIN_VALUE, -13), BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, CAVE_VINES, caveVine, CountPlacementModifier.of(188), SquarePlacementModifier.of(), lushRange, EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.hasSturdyFace(Direction.DOWN), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LUSH_CAVES_VEGETATION, mossPatch, CountPlacementModifier.of(125), SquarePlacementModifier.of(), lushRange, EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LUSH_CAVES_CLAY, lushCavesClay, CountPlacementModifier.of(62), SquarePlacementModifier.of(), lushRange, EnvironmentScanPlacementModifier.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(1)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, LUSH_CAVES_CEILING_VEGETATION, mossPatchCeiling, CountPlacementModifier.of(125), SquarePlacementModifier.of(), lushRange, EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, SPORE_BLOSSOM, sporeBlossom, CountPlacementModifier.of(25), SquarePlacementModifier.of(), lushRange, EnvironmentScanPlacementModifier.of(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR, 12), RandomOffsetPlacementModifier.vertically(ConstantIntProvider.create(-1)), BiomePlacementModifier.of());
        PlacedFeatures.register(featureRegisterable, CLASSIC_VINES_CAVE, vines, CountPlacementModifier.of(256), SquarePlacementModifier.of(), lushRange, BiomePlacementModifier.of());


        PlacedFeatures.register(featureRegisterable, SPRING_LAVA, springLava, modifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.getBottom(), YOffset.fixed(MiddleEarthChunkGenerator.NURGON_LEVEL)) ));
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, Identifier.of(MiddleEarth.MOD_ID, name));
    }

    private static List<PlacementModifier> modifiers(PlacementModifier countModifier, PlacementModifier heightModifier) {
        return List.of(countModifier, SquarePlacementModifier.of(), heightModifier, BiomePlacementModifier.of());
    }

    private static List<PlacementModifier> modifiersWithCount(int count, PlacementModifier heightModifier) {
        return modifiers(CountPlacementModifier.of(count), heightModifier);
    }

    private static List<PlacementModifier> modifiersWithRarity(int chance, PlacementModifier heightModifier) {
        return modifiers(RarityFilterPlacementModifier.of(chance), heightModifier);
    }
}
