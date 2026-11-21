package net.sevenstars.middleearth.features.generation.biomes.caves;

import net.sevenstars.middleearth.features.generation.biomes.BiomeColorsDTO;
import net.sevenstars.middleearth.features.generation.biomes.BiomeAmbianceME;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.surface.BiomeData;
import net.sevenstars.middleearth.features.generation.worldfeatures.underground.CavesPlacedFeatureRegistryME;
import net.sevenstars.middleearth.features.generation.worldfeatures.vegetation.VegetationPlacedFeatureRegistryME;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CaveBiomesME {
    public static final int defaultWater = 4159204;
    public static final int defaultWaterFog = 329011;

    private static List<RegistryKey<PlacedFeature>> undergroundOres = new ArrayList<>();;

    public static CaveBiomesMap defaultCaves = new CaveBiomesMap();
    public static CaveBiomesMap ashCaves = new CaveBiomesMap();
    public static CaveBiomesMap elvenCaves = new CaveBiomesMap();
    public static CaveBiomesMap forodCaves = new CaveBiomesMap();
    public static CaveBiomesMap mountainCaves = new CaveBiomesMap();
    public static CaveBiomesMap whiteMountainCaves = new CaveBiomesMap();
    public static CaveBiomesMap mistyCaves = new CaveBiomesMap();
    public static CaveBiomesMap lonelyMountainCaves = new CaveBiomesMap();
    public static CaveBiomesMap haradCaves = new CaveBiomesMap();

    public static void init() {
        defaultCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LIMESTONE_CAVE, new Vec2f(-1.0f,1.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(0.0f,1.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DOLOMITE_CAVE, new Vec2f(1.0f,1.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.BASIC_CAVE, new Vec2f(0.0f,0.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LUSH_CAVE, new Vec2f(-1.0f,-1.1f)));
        defaultCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MUD_CAVE, new Vec2f(0f,-1.1f)));
        defaultCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.FUNGUS_CAVE, new Vec2f(1.0f,-1.1f)));

        elvenCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LIMESTONE_CAVE, new Vec2f(-1.0f,1.0f)));
        elvenCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.BASIC_CAVE, new Vec2f(0.0f,1.0f)));
        elvenCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,1.0f)));
        elvenCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.GALONN_CAVE, new Vec2f(0.0f,0.0f)));
        elvenCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LUSH_CAVE, new Vec2f(-1.0f,-1.1f)));
        elvenCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MUD_CAVE, new Vec2f(0f,-1.1f)));
        elvenCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.FUNGUS_CAVE, new Vec2f(1.0f,-1.1f)));

        lonelyMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.IZHERABAN_CAVE, new Vec2f(-1.0f,1.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(0.0f,1.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DOLOMITE_CAVE, new Vec2f(1.0f,1.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.GILDED_CAVE, new Vec2f(0.0f,0.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MOUNTAIN_CAVE, new Vec2f(1.0f,-1.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LUSH_CAVE, new Vec2f(-1.25f,-1.1f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.FUNGUS_CAVE, new Vec2f(0f,-1.1f)));

        mountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.IZHERABAN_CAVE, new Vec2f(-1.0f,1.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LIMESTONE_CAVE, new Vec2f(0.0f,1.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DOLOMITE_CAVE, new Vec2f(1.0f,1.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,0.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MOUNTAIN_CAVE, new Vec2f(0.0f,0.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.BASIC_CAVE, new Vec2f(0.0f,-1.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LUSH_CAVE, new Vec2f(-1.25f,-1.1f)));
        mountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.FUNGUS_CAVE, new Vec2f(0f,-1.1f)));

        // limestone country: plain karst with dripstone, no gems or gold outside Aglarond
        whiteMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LIMESTONE_CAVE, new Vec2f(-1.0f,1.0f)));
        whiteMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,1.0f)));
        whiteMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LIMESTONE_CAVE, new Vec2f(0.0f,0.0f)));
        whiteMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LIMESTONE_CAVE, new Vec2f(1.0f,-0.5f)));
        whiteMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MOUNTAIN_CAVE, new Vec2f(-1.0f,-0.5f)));
        whiteMountainCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.BASIC_CAVE, new Vec2f(0.0f,-1.1f)));

        // the Misty Mountains by depth band; the middle one is the mountain palette
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.ICE_CAVE, new Vec2f(-0.6f,0.3f), DepthBands.Band.UPPER));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(0.6f,0.3f), DepthBands.Band.UPPER));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MOUNTAIN_CAVE, new Vec2f(0.0f,-0.6f), DepthBands.Band.UPPER));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.IZHERABAN_CAVE, new Vec2f(-1.0f,1.0f), DepthBands.Band.MIDDLE));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LIMESTONE_CAVE, new Vec2f(0.0f,1.0f), DepthBands.Band.MIDDLE));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DOLOMITE_CAVE, new Vec2f(1.0f,1.0f), DepthBands.Band.MIDDLE));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,0.0f), DepthBands.Band.MIDDLE));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MOUNTAIN_CAVE, new Vec2f(0.0f,0.0f), DepthBands.Band.MIDDLE));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.BASIC_CAVE, new Vec2f(0.0f,-1.0f), DepthBands.Band.MIDDLE));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.LUSH_CAVE, new Vec2f(-1.25f,-1.1f), DepthBands.Band.MIDDLE));
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.FUNGUS_CAVE, new Vec2f(0f,-1.1f), DepthBands.Band.MIDDLE));
        // the deeps proper are picked by DeepThemes; a Misty column with the usual floor has no deeps to speak of
        mistyCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MITHRIL_CAVE, new Vec2f(0.0f,0.0f), DepthBands.Band.MITHRIL, DepthBands.Band.DEEPS));

        ashCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,0.5f)));
        ashCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.BASALT_CAVE, new Vec2f(-1.0f,0.5f)));
        ashCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MAGMA_CAVE, new Vec2f(0.0f,-1.0f)));

        haradCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,0f)));
        haradCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRY_CAVE, new Vec2f(0.0f,0f)));
        haradCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.BASIC_CAVE, new Vec2f(-1.0f,0f)));

        forodCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.ICE_CAVE, new Vec2f(-0.5f,0f)));
        forodCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,0f)));
    }

    public static RegistryKey<Biome> getBiome(Vec2f coordinates, BiomeData surfaceBiome, DepthBands.Band band) {
        if(surfaceBiome.getCaveType() != null)
            return switch (surfaceBiome.getCaveType()) {
                case ASHEN -> ashCaves.getClosestBiome(coordinates, band);
                case ELVEN -> elvenCaves.getClosestBiome(coordinates, band);
                case FOROD -> forodCaves.getClosestBiome(coordinates, band);
                case HARAD -> haradCaves.getClosestBiome(coordinates, band);
                case MISTIES -> mistyCaves.getClosestBiome(coordinates, band);
                case MOUNTAINS -> mountainCaves.getClosestBiome(coordinates, band);
                case WHITE_MOUNTAINS -> whiteMountainCaves.getClosestBiome(coordinates, band);
                case LONELY_MOUNTAIN -> lonelyMountainCaves.getClosestBiome(coordinates, band);
                default -> defaultCaves.getClosestBiome(coordinates, band);
            };
        return defaultCaves.getClosestBiome(coordinates, band);
    }

    public static void bootstrap(Registerable<Biome> context) {
        context.register(BiomeKeyRegistryME.BASIC_CAVE, createBasicCave(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.MOUNTAIN_CAVE, createMountainCave(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.LUSH_CAVE, createLushCave(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 8703593, 8703593)));
        context.register(BiomeKeyRegistryME.DRIPSTONE_CAVE, createDripstoneCave(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.DOLOMITE_CAVE, createDolomiteCave(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.GALONN_CAVE, createGalonnCave(context, new BiomeColorsDTO(BiomeAmbianceME.CLEAR, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.GILDED_CAVE, createGildedCave(context, new BiomeColorsDTO(BiomeAmbianceME.CLEAR, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.IZHERABAN_CAVE, createIzherAbanCave(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.LIMESTONE_CAVE, createLimestoneCave(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.MUD_CAVE, createMudCaves(context, new BiomeColorsDTO(BiomeAmbianceME.CLEAR, defaultWater, defaultWaterFog, 7435337, 7905386)));
        context.register(BiomeKeyRegistryME.FUNGUS_CAVE, createFungusCave(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 5869935, 6263141)));

        context.register(BiomeKeyRegistryME.MITHRIL_CAVE, createMithrilCave(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.NAMELESS_DEEPS, createNamelessDeeps(context, new BiomeColorsDTO(BiomeAmbianceME.NAMELESS_DEEPS, 0x0B1620, 0x01030A, 0x2A2F33, 0x2A2F33)));
        context.register(BiomeKeyRegistryME.GNAWED_TUNNELS, createGnawedTunnels(context, new BiomeColorsDTO(BiomeAmbianceME.GNAWED_TUNNELS, 0x6E6A60, 0x4A4740, 0x9A9384, 0x9A9384)));
        context.register(BiomeKeyRegistryME.GLOWWORM_GROTTO, createGlowwormGrotto(context, new BiomeColorsDTO(BiomeAmbianceME.GLOWWORM_GROTTO, 0x1E5C62, 0x04161A, 0x2F5E4F, 0x2F5E4F)));
        context.register(BiomeKeyRegistryME.GLITTERING_CAVES, createBareCave(context, new BiomeColorsDTO(BiomeAmbianceME.GLITTERING_CAVES, 0x2E6A7A, 0x07181E, 10338918, 10604137), 0.5f));
        context.register(BiomeKeyRegistryME.LAVA_TUBES, createBareCave(context, new BiomeColorsDTO(BiomeAmbianceME.LAVA_TUBES, 6450777, 1513734, 3550502, 2169880), 2.0f));

        context.register(BiomeKeyRegistryME.BASALT_CAVE, createBasaltCave(context, new BiomeColorsDTO(BiomeAmbianceME.ASH_MOUNTAINS.withoutParticles().withoutSounds(), 6450777, 1513734, 3550502, 2169880)));
        context.register(BiomeKeyRegistryME.MAGMA_CAVE, createMagmaCave(context, new BiomeColorsDTO(BiomeAmbianceME.ASH_MOUNTAINS.withoutParticles().withoutSounds(), 6450777, 1513734, 3550502, 2169880)));

        context.register(BiomeKeyRegistryME.DRY_CAVE, createDryCave(context, new BiomeColorsDTO(BiomeAmbianceME.CLEAR, defaultWater, defaultWaterFog, 10928742, 11259497)));
        context.register(BiomeKeyRegistryME.ICE_CAVE, createIceCaves(context, new BiomeColorsDTO(BiomeAmbianceME.MISTY_CAVE, defaultWater, defaultWaterFog, 11121530, 10990723)));
    }

    // for caves that dress themselves (Aglarond, the lava tubes); no features, so nothing grows over them
    public static Biome createBareCave(Registerable<Biome> context, BiomeColorsDTO biomeColors, float temperature) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));
        return createBiome(biomeColors, spawnSettings, generationSettings, temperature, false, false);
    }

    public static Biome createBasicCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createMountainCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_GABBRO);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_GNEISS);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_SCHIST);

        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createLushCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        CaveBiomeFeaturesME.addAxolotls(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true, false);
        DefaultBiomeFeatures.addPlainsTallGrass(generationSettings);

        undergroundOres.add(OrePlacedFeatures.ORE_CLAY);
        undergroundOres.add(UndergroundPlacedFeatures.UNDERWATER_MAGMA);
        undergroundOres.add(MiscPlacedFeatures.DISK_SAND);
        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);

        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatureRegistryME.AZALEA_GROWTH);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.LUSH_CAVES_CEILING_VEGETATION);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.CAVE_VINES);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.LUSH_CAVES_CLAY);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.LUSH_CAVES_VEGETATION);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.SPORE_BLOSSOM);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.CLASSIC_VINES_CAVE);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createDripstoneCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(UndergroundPlacedFeatures.LARGE_DRIPSTONE);
        undergroundOres.add(UndergroundPlacedFeatures.DRIPSTONE_CLUSTER);
        undergroundOres.add(UndergroundPlacedFeatures.POINTED_DRIPSTONE);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createDolomiteCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatureRegistryME.DOLOMITE_CLUSTER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.LARGE_DOLOMITE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.POINTED_DOLOMITE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_DOLOMITE_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_OLD_DOLOMITE);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createGalonnCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatureRegistryME.GALONN_CLUSTER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.LARGE_GALONN);
        undergroundOres.add(CavesPlacedFeatureRegistryME.POINTED_GALONN);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_GALONN);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_OLD_GALONN);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createGildedCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_GREEN_TUFF);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_GILDED_GREEN_TUFF);
        //generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, CavesPlacedFeatures.ORE_GOLD_GREEN_TUFF);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_GOLD_RARE);

        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createIzherAbanCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatureRegistryME.IZHER_ABAN_CLUSTER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.LARGE_IZHER_ABAN);
        undergroundOres.add(CavesPlacedFeatureRegistryME.POINTED_IZHER_ABAN);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_IZHER_ABAN);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_ZIGIL_ABAN);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_OLD_IZHER_ABAN);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createLimestoneCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatureRegistryME.LIMESTONE_CLUSTER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.LARGE_LIMESTONE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.POINTED_LIMESTONE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_LIMESTONE_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_OLD_LIMESTONE);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createMudCaves(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        CaveBiomeFeaturesME.addSnails(spawnSettings);
        CaveBiomeFeaturesME.addFrogs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_DIRT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_MUD);
        undergroundOres.add(CavesPlacedFeatureRegistryME.POOL_MUD);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createFungusCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        CaveBiomeFeaturesME.addSnails(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true, false);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_DIRT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.DISK_MYCELIUM);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);

        // TODO bring back the fungus trees without crashing
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_BROWN_BOLETTE);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_CAVE_AMANITA);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_CAVE_AMANITA_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_CAVE_AMANITA);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_DEEP_FIRECAP);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_DEEP_FIRECAP_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_DEEP_FIRECAP);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_GHOSTSHROOM);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_GHOSTSHROOM_TILLER);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_SKY_FIRECAP);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_SKY_FIRECAP_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_SKY_FIRECAP);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_TUBESHROOMS);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_TALL_TUBESHROOMS);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_TRUMPET_SHROOM);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_TALL_TRUMPET_SHROOM);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_VIOLET_CAPS);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_VIOLET_CAPS_TILLER);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_YELLOW_AMANITA);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.PATCH_YELLOW_AMANITA_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_YELLOW_AMANITA);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.GLOWWORM_WEBBING);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    // The Dwarves' deepest delvings: plain and noble ores in the deep rock, and no fire, lava or geodes
    public static Biome createMithrilCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDeepOres();
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_JADE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_EMERALD);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_RUBY);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_SAPPHIRE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_ADAMANT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_MITHRIL);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    // The three deeps share nothing with the other caves but the plain ores: no lava, magma, basalt, geodes or moss
    private static void addDeepOres() {
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_IRON);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_SILVER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_GOLD);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_LEAD);
    }

    public static Biome createNamelessDeeps(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        CaveBiomeFeaturesME.addBats(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDeepOres();
        undergroundOres.add(CavesPlacedFeatureRegistryME.DEEPS_MUD);
        undergroundOres.add(CavesPlacedFeatureRegistryME.DEEPS_CLAY);
        undergroundOres.add(CavesPlacedFeatureRegistryME.DEEPS_POINTED_DRIPSTONE);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true, false);
    }

    public static Biome createGnawedTunnels(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        spawnSettings.spawn(SpawnGroup.MONSTER, 2, new SpawnSettings.SpawnEntry(EntityType.CAVE_SPIDER, 1, 2));
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDeepOres();
        undergroundOres.add(CavesPlacedFeatureRegistryME.DEEPS_TUFF);
        undergroundOres.add(CavesPlacedFeatureRegistryME.DEEPS_CALCITE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.DEEPS_GRAVEL);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, false, false);
    }

    public static Biome createGlowwormGrotto(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        CaveBiomeFeaturesME.addBats(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addDeepOres();
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.DEEPS_GLOW_LICHEN);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.DEEPS_GLOWWORM);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatureRegistryME.DEEPS_AMANITA);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true, false);
    }

    public static Biome createBasaltCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, false);
        generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, CavesPlacedFeatureRegistryME.DELTA);
        generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, NetherPlacedFeatures.SMALL_BASALT_COLUMNS);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_ASH);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_ASHEN_DIRT);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createMagmaCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, false);
        generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, NetherPlacedFeatures.DELTA);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_MAGMA_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_ASHEN_DIRT);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createDryCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_DRY_DIRT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_SAND);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_SANDSTONE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_TERRACOTTA);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_LAPIS_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.PILLAR_SMOOTH_SANDSTONE);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, false);
    }

    public static Biome createIceCaves(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_BLUE_ICE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_PACKED_ICE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_SNOW);
        undergroundOres.add(CavesPlacedFeatureRegistryME.PILLAR_PACKED_ICE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.DROOPING_ICICLES);
        undergroundOres.add(CavesPlacedFeatureRegistryME.SHORT_ICICLES);
        undergroundOres.add(CavesPlacedFeatureRegistryME.STICKY_ICE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.STICKY_SNOW);
        addBasicFeatures(generationSettings, true, false);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, false);
    }

    private static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings, boolean vanillaRocks) {
        addBasicFeatures(generationSettings, vanillaRocks, true);
    }

    // hot leaves out the lava springs, magma, obsidian, basalt and blackstone, for the caves of ice and of living things
    private static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings, boolean vanillaRocks, boolean hot) {
        CaveBiomeFeaturesME.addAmethystGeode(generationSettings);
        CaveBiomeFeaturesME.addCitrineGeode(generationSettings);
        CaveBiomeFeaturesME.addGlowstoneGeode(generationSettings);
        CaveBiomeFeaturesME.addRedAgateGeode(generationSettings);
        CaveBiomeFeaturesME.addQuartzGeode(generationSettings);

        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_COAL);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_COAL_UPPER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_COPPER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_COPPER_UPPER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_TIN);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_LAPIS);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_LEAD);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_IRON);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_SILVER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_JADE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_GOLD);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_ADAMANT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_EMERALD);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_RUBY);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_SAPPHIRE);
        undergroundOres.add(MiscPlacedFeatures.SPRING_WATER);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_TUFF);
        if(hot) {
            undergroundOres.add(CavesPlacedFeatureRegistryME.SPRING_LAVA);
            undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_MAGMA);
            undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_OBSIDIAN);
            undergroundOres.add(CavesPlacedFeatureRegistryME.PILLAR_BASALT);
            undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_BASALT);
            undergroundOres.add(CavesPlacedFeatureRegistryME.PILLAR_BLACKSTONE);
            undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_BLACKSTONE);
        }

        undergroundOres.add(OrePlacedFeatures.ORE_GRAVEL);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_DOLOMITE);

        if(vanillaRocks) {
            undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_QUARTZITE);
            undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_LIMESTONE);
            undergroundOres.add(OrePlacedFeatures.ORE_DIRT);
            undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_UPPER);
            undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_LOWER);
            undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_UPPER);
            undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_LOWER);
            undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_UPPER);
            undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_LOWER);
        }
        undergroundOres.add(OrePlacedFeatures.ORE_TUFF);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.GLOW_LICHEN);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }

    public static Biome createBiome(BiomeColorsDTO biomeColors, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, float temperature, boolean precipitation) {
        return createBiome(biomeColors, spawnSettings, generationSettings, temperature, precipitation, true);
    }

    private static Biome createBiome(BiomeColorsDTO biomeColors, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, float temperature, boolean precipitation, boolean usualMobs) {
        if(usualMobs) {
            CaveBiomeFeaturesME.addBats(spawnSettings);
            CaveBiomeFeaturesME.addSparseNpc(spawnSettings);
            CaveBiomeFeaturesME.addCaveTroll(spawnSettings);
            CaveBiomeFeaturesME.addSpiders(spawnSettings);
        }

        undergroundOres = undergroundOres.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList();
        for (RegistryKey<PlacedFeature> feature: undergroundOres) {
            generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, feature);
        }

        Biome biome = biomeColors.createBiomeBuilder()
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(0.5F)
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
        undergroundOres = new ArrayList<>();
        return biome;
    }
}
