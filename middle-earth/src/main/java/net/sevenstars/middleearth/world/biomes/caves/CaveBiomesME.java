package net.sevenstars.middleearth.world.biomes.caves;

import net.sevenstars.middleearth.world.biomes.BiomeColorsDTO;
import net.sevenstars.middleearth.world.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.world.biomes.surface.BiomeData;
import net.sevenstars.middleearth.world.features.underground.CavesPlacedFeatureRegistryME;
import net.sevenstars.middleearth.world.features.vegetation.VegetationPlacedFeatureRegistryME;
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
    public static final int defaultSky = 7907327;
    public static final int defaultFog = 12638463;
    public static final int defaultWater = 4159204;
    public static final int defaultWaterFog = 329011;

    private static List<RegistryKey<PlacedFeature>> undergroundOres = new ArrayList<>();;

    public static CaveBiomesMap defaultCaves = new CaveBiomesMap();
    public static CaveBiomesMap ashCaves = new CaveBiomesMap();
    public static CaveBiomesMap elvenCaves = new CaveBiomesMap();
    public static CaveBiomesMap forodCaves = new CaveBiomesMap();
    public static CaveBiomesMap mountainCaves = new CaveBiomesMap();
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

        ashCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,0.5f)));
        ashCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.BASALT_CAVE, new Vec2f(-1.0f,0.5f)));
        ashCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.MAGMA_CAVE, new Vec2f(0.0f,-1.0f)));

        haradCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,0f)));
        haradCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRY_CAVE, new Vec2f(0.0f,0f)));
        haradCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.BASIC_CAVE, new Vec2f(-1.0f,0f)));

        forodCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.ICE_CAVE, new Vec2f(-0.5f,0f)));
        forodCaves.addCave(new CaveBiomeDTO(BiomeKeyRegistryME.DRIPSTONE_CAVE, new Vec2f(1.0f,0f)));
    }

    public static RegistryKey<Biome> getBiome(Vec2f coordinates, BiomeData surfaceBiome) {
        if(surfaceBiome.getCaveType() != null)
            return switch (surfaceBiome.getCaveType()) {
                case ASHEN -> ashCaves.getClosestBiome(coordinates);
                case ELVEN -> elvenCaves.getClosestBiome(coordinates);
                case FOROD -> forodCaves.getClosestBiome(coordinates);
                case HARAD -> haradCaves.getClosestBiome(coordinates);
                case MISTIES, MOUNTAINS -> mountainCaves.getClosestBiome(coordinates);
                case LONELY_MOUNTAIN -> lonelyMountainCaves.getClosestBiome(coordinates);
                default -> defaultCaves.getClosestBiome(coordinates);
            };
        return defaultCaves.getClosestBiome(coordinates);
    }

    public static void bootstrap(Registerable<Biome> context) {
        context.register(BiomeKeyRegistryME.BASIC_CAVE, createBasicCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.MOUNTAIN_CAVE, createMountainCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.LUSH_CAVE, createLushCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 8703593)));
        context.register(BiomeKeyRegistryME.DRIPSTONE_CAVE, createDripstoneCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.DOLOMITE_CAVE, createDolomiteCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.GALONN_CAVE, createGalonnCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.GILDED_CAVE, createGildedCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.IZHERABAN_CAVE, createIzherAbanCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.LIMESTONE_CAVE, createLimestoneCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(BiomeKeyRegistryME.MUD_CAVE, createMudCaves(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 7435337, 7905386)));
        context.register(BiomeKeyRegistryME.FUNGUS_CAVE, createFungusCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 5869935, 6263141)));

        context.register(BiomeKeyRegistryME.MITHRIL_CAVE, createMithrilCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));

        context.register(BiomeKeyRegistryME.BASALT_CAVE, createBasaltCave(context, new BiomeColorsDTO(
                4142646, 3090215, 6450777, 1513734, 3550502, 2169880)));
        context.register(BiomeKeyRegistryME.MAGMA_CAVE, createMagmaCave(context, new BiomeColorsDTO(
                4142646, 3090215, 6450777, 1513734, 3550502, 2169880)));

        context.register(BiomeKeyRegistryME.DRY_CAVE, createDryCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10928742, 11259497)));
        context.register(BiomeKeyRegistryME.ICE_CAVE, createIceCaves(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 11121530, 10990723)));
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

        addBasicFeatures(generationSettings, true);
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

        addBasicFeatures(generationSettings, true);
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

    public static Biome createMithrilCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_MITHRIL);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
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
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, false);
    }

    private static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings, boolean vanillaRocks) {
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
        undergroundOres.add(CavesPlacedFeatureRegistryME.SPRING_LAVA);
        undergroundOres.add(MiscPlacedFeatures.SPRING_WATER);

        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_MAGMA);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_OBSIDIAN);
        undergroundOres.add(CavesPlacedFeatureRegistryME.PILLAR_BASALT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_BASALT);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_TUFF);
        undergroundOres.add(CavesPlacedFeatureRegistryME.PILLAR_BLACKSTONE);
        undergroundOres.add(CavesPlacedFeatureRegistryME.ORE_BLACKSTONE);

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
        CaveBiomeFeaturesME.addBats(spawnSettings);

        CaveBiomeFeaturesME.addSparseNpc(spawnSettings);
        CaveBiomeFeaturesME.addCaveTroll(spawnSettings);
        CaveBiomeFeaturesME.addSpiders(spawnSettings);

        undergroundOres = undergroundOres.stream().sorted(Comparator.comparing(a -> a.getValue().toString())).toList();
        for (RegistryKey<PlacedFeature> feature: undergroundOres) {
            generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, feature);
        }

        Biome biome = (new Biome.Builder())
                .precipitation(precipitation)
                .temperature(temperature)
                .downfall(0.5F)
                .effects((new BiomeEffects.Builder())
                        .skyColor(biomeColors.skyColor)
                        .fogColor(biomeColors.fogColor)
                        .waterColor(biomeColors.waterColor)
                        .waterFogColor(biomeColors.waterFogColor)
                        .grassColor(biomeColors.grassColor)
                        .foliageColor(biomeColors.foliageColor)
                        .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
        undergroundOres = new ArrayList<>();
        return biome;
    }
}
