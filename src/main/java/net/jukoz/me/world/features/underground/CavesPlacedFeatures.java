package net.jukoz.me.world.features.underground;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.heightprovider.VeryBiasedToBottomHeightProvider;
import net.minecraft.world.gen.placementmodifier.*;

import java.util.List;

public class CavesPlacedFeatures {
    public static final RegistryKey<PlacedFeature> AMETHYST_GEODE = registerKey("amethyst_geode");
    public static final RegistryKey<PlacedFeature> CITRINE_GEODE = registerKey("citrine_geode");
    public static final RegistryKey<PlacedFeature> GLOWSTONE_GEODE = registerKey("glowstone_geode");
    public static final RegistryKey<PlacedFeature> RED_AGATE_GEODE = registerKey("red_agate_geode");
    public static final RegistryKey<PlacedFeature> QUARTZ_GEODE = registerKey("quartz_geode");

    public static final RegistryKey<PlacedFeature> ORE_MAGMA = registerKey("ore_magma");
    public static final RegistryKey<PlacedFeature> ORE_OBSIDIAN = registerKey("ore_obsidian");
    public static final RegistryKey<PlacedFeature> LARGE_BLACKSTONE = registerKey("large_blackstone");
    public static final RegistryKey<PlacedFeature> LARGE_POLISHED_BASALT = registerKey("large_polished_basalt");
    //public static final RegistryKey<PlacedFeature> DRIPSTONE_CLUSTER = registerKey("dripstone_cluster");
    //public static final RegistryKey<PlacedFeature> POINTED_DRIPSTONE = registerKey("pointed_dripstone");

    public static final RegistryKey<PlacedFeature> ORE_COAL = registerKey("ore_coal");
    public static final RegistryKey<PlacedFeature> ORE_COAL_UPPER = registerKey("ore_coal_upper");
    public static final RegistryKey<PlacedFeature> ORE_COPPER = registerKey("ore_copper");
    public static final RegistryKey<PlacedFeature> ORE_TIN = registerKey("ore_tin");
    public static final RegistryKey<PlacedFeature> ORE_LAPIS = registerKey("ore_lapis");
    public static final RegistryKey<PlacedFeature> ORE_LEAD = registerKey("ore_lead");
    public static final RegistryKey<PlacedFeature> ORE_IRON = registerKey("ore_iron");
    public static final RegistryKey<PlacedFeature> ORE_SILVER = registerKey("ore_silver");
    public static final RegistryKey<PlacedFeature> ORE_JADE = registerKey("ore_jade");
    public static final RegistryKey<PlacedFeature> ORE_GOLD = registerKey("ore_gold");
    public static final RegistryKey<PlacedFeature> ORE_MITHRIL = registerKey("ore_mithril");

    public static final RegistryKey<PlacedFeature> SPRING_LAVA = registerKey("spring_lava");

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> amethystGeode = registryEntryLookup.getOrThrow(UndergroundConfiguredFeatures.AMETHYST_GEODE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> citrineGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.CITRINE_GEODE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> glowstoneGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.GLOWSTONE_GEODE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> redAgateGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.RED_AGATE_GEODE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> quartzGeode = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.QUARTZ_GEODE);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreMagma = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_MAGMA);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> oreObsidian = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_OBSIDIAN);
        //RegistryEntry.Reference<ConfiguredFeature<?, ?>> largeBlackstone = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LARGE_DRIPSTONE);
        //RegistryEntry.Reference<ConfiguredFeature<?, ?>> largePolishedBasalt = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.LARGE_DRIPSTONE);

        RegistryEntry.Reference<ConfiguredFeature<?, ?>> coalOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_COAL);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> copperOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_COPPER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> tinOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_TIN);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> lapisOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_LAPIS);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> leadOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_LEAD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> ironOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_IRON);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> silverOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_SILVER);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> jadeOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_JADE);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> goldOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_GOLD);
        RegistryEntry.Reference<ConfiguredFeature<?, ?>> mithrilOre = registryEntryLookup.getOrThrow(CavesConfiguredFeatures.ORE_MITHRIL);

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

        PlacedFeatures.register(featureRegisterable, ORE_MAGMA, oreMagma, modifiersWithCount(11, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-128), YOffset.fixed(MiddleEarthChunkGenerator.DIFTOMIN_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_OBSIDIAN, oreObsidian, modifiersWithCount(7, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-92), YOffset.fixed(MiddleEarthChunkGenerator.EPMOSTO_LEVEL))));
        //PlacedFeatures.register(featureRegisterable, LARGE_BLACKSTONE, largeDripstone, CountPlacementModifier.of(UniformIntProvider.create(10, 48)), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(256)), BiomePlacementModifier.of());
        //PlacedFeatures.register(featureRegisterable, LARGE_POLISHED_BASALT, largeDripstone, CountPlacementModifier.of(UniformIntProvider.create(10, 48)), SquarePlacementModifier.of(), HeightRangePlacementModifier.uniform(YOffset.fixed(-64), YOffset.fixed(256)), BiomePlacementModifier.of());

        PlacedFeatures.register(featureRegisterable, ORE_COAL_UPPER, coalOre, modifiersWithCount(16, HeightRangePlacementModifier.trapezoid(YOffset.fixed(60), YOffset.fixed(300))));
        PlacedFeatures.register(featureRegisterable, ORE_COAL, coalOre, modifiersWithCount(20, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.DIFTOMIN_LEVEL), YOffset.fixed(100))));
        PlacedFeatures.register(featureRegisterable, ORE_COPPER, copperOre, modifiersWithCount(16, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.DIFTOMIN_LEVEL), YOffset.fixed(120))));
        PlacedFeatures.register(featureRegisterable, ORE_TIN, tinOre, modifiersWithCount(16, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-8), YOffset.fixed(40))));
        PlacedFeatures.register(featureRegisterable, ORE_LAPIS, lapisOre, modifiersWithCount(4, HeightRangePlacementModifier.trapezoid(YOffset.fixed(MiddleEarthChunkGenerator.DIFTOMIN_LEVEL), YOffset.fixed(MiddleEarthChunkGenerator.DEEPSLATE_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_LEAD, leadOre, modifiersWithCount(9, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-48), YOffset.fixed(16))));
        PlacedFeatures.register(featureRegisterable, ORE_IRON, ironOre, modifiersWithCount(14, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-128), YOffset.fixed(MiddleEarthChunkGenerator.DIFTOMIN_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_SILVER, silverOre, modifiersWithCount(7, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-40), YOffset.fixed(MiddleEarthChunkGenerator.DIFTOMIN_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_JADE, jadeOre, modifiersWithCount(1, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-40), YOffset.fixed(MiddleEarthChunkGenerator.DIFTOMIN_LEVEL))));
        PlacedFeatures.register(featureRegisterable, ORE_GOLD, goldOre, modifiersWithCount(2, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-44), YOffset.fixed(-20))));
        PlacedFeatures.register(featureRegisterable, ORE_MITHRIL, mithrilOre, modifiersWithRarity(4, HeightRangePlacementModifier.trapezoid(YOffset.fixed(-61), YOffset.fixed(-55))));

        PlacedFeatures.register(featureRegisterable, SPRING_LAVA, springLava, CountPlacementModifier.of(20), SquarePlacementModifier.of(), HeightRangePlacementModifier.trapezoid(YOffset.getBottom(), YOffset.fixed(MiddleEarthChunkGenerator.DIFTOMIN_LEVEL)), BiomePlacementModifier.of());
    }

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier(MiddleEarth.MOD_ID, name));
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
