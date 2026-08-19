package net.sevenstars.middleearth.world.biomes.caves;

import net.sevenstars.middleearth.world.biomes.BiomeColorsDTO;
import net.sevenstars.middleearth.world.biomes.MEBiomeKeys;
import net.sevenstars.middleearth.world.biomes.surface.BiomeData;
import net.sevenstars.middleearth.world.features.underground.CavesPlacedFeatures;
import net.sevenstars.middleearth.world.features.vegetation.ModVegetationPlacedFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.CavePlacements;
import net.minecraft.data.worldgen.placement.MiscOverworldPlacements;
import net.minecraft.data.worldgen.placement.NetherPlacements;
import net.minecraft.data.worldgen.placement.OrePlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.*;
import net.minecraft.world.level.levelgen.feature.configurations.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.phys.Vec2;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModCaveBiomes {
    public static final int defaultSky = 7907327;
    public static final int defaultFog = 12638463;
    public static final int defaultWater = 4159204;
    public static final int defaultWaterFog = 329011;

    private static List<ResourceKey<PlacedFeature>> undergroundOres = new ArrayList<>();;

    public static CaveBiomesMap defaultCaves = new CaveBiomesMap();
    public static CaveBiomesMap ashCaves = new CaveBiomesMap();
    public static CaveBiomesMap elvenCaves = new CaveBiomesMap();
    public static CaveBiomesMap forodCaves = new CaveBiomesMap();
    public static CaveBiomesMap mountainCaves = new CaveBiomesMap();
    public static CaveBiomesMap lonelyMountainCaves = new CaveBiomesMap();
    public static CaveBiomesMap haradCaves = new CaveBiomesMap();

    public static void init() {
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.LIMESTONE_CAVE, new Vec2(-1.0f,1.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2(0.0f,1.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DOLOMITE_CAVE, new Vec2(1.0f,1.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.BASIC_CAVE, new Vec2(0.0f,0.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.LUSH_CAVE, new Vec2(-1.0f,-1.1f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.MUD_CAVE, new Vec2(0f,-1.1f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.FUNGUS_CAVE, new Vec2(1.0f,-1.1f)));

        elvenCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.LIMESTONE_CAVE, new Vec2(-1.0f,1.0f)));
        elvenCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.BASIC_CAVE, new Vec2(0.0f,1.0f)));
        elvenCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2(1.0f,1.0f)));
        elvenCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.GALONN_CAVE, new Vec2(0.0f,0.0f)));
        elvenCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.LUSH_CAVE, new Vec2(-1.0f,-1.1f)));
        elvenCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.MUD_CAVE, new Vec2(0f,-1.1f)));
        elvenCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.FUNGUS_CAVE, new Vec2(1.0f,-1.1f)));

        lonelyMountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.IZHERABAN_CAVE, new Vec2(-1.0f,1.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2(0.0f,1.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DOLOMITE_CAVE, new Vec2(1.0f,1.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.GILDED_CAVE, new Vec2(0.0f,0.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.MOUNTAIN_CAVE, new Vec2(1.0f,-1.0f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.LUSH_CAVE, new Vec2(-1.25f,-1.1f)));
        lonelyMountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.FUNGUS_CAVE, new Vec2(0f,-1.1f)));

        mountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.IZHERABAN_CAVE, new Vec2(-1.0f,1.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.LIMESTONE_CAVE, new Vec2(0.0f,1.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DOLOMITE_CAVE, new Vec2(1.0f,1.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2(1.0f,0.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.MOUNTAIN_CAVE, new Vec2(0.0f,0.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.BASIC_CAVE, new Vec2(0.0f,-1.0f)));
        mountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.LUSH_CAVE, new Vec2(-1.25f,-1.1f)));
        mountainCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.FUNGUS_CAVE, new Vec2(0f,-1.1f)));

        ashCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2(1.0f,0.5f)));
        ashCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.BASALT_CAVE, new Vec2(-1.0f,0.5f)));
        ashCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.MAGMA_CAVE, new Vec2(0.0f,-1.0f)));

        haradCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2(1.0f,0f)));
        haradCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRY_CAVE, new Vec2(0.0f,0f)));
        haradCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.BASIC_CAVE, new Vec2(-1.0f,0f)));

        forodCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.ICE_CAVE, new Vec2(-0.5f,0f)));
        forodCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2(1.0f,0f)));
    }

    public static ResourceKey<Biome> getBiome(Vec2 coordinates, BiomeData surfaceBiome) {
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

    public static void bootstrap(BootstrapContext<Biome> context) {
        context.register(MEBiomeKeys.BASIC_CAVE, createBasicCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(MEBiomeKeys.MOUNTAIN_CAVE, createMountainCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(MEBiomeKeys.LUSH_CAVE, createLushCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 8703593)));
        context.register(MEBiomeKeys.DRIPSTONE_CAVE, createDripstoneCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(MEBiomeKeys.DOLOMITE_CAVE, createDolomiteCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(MEBiomeKeys.GALONN_CAVE, createGalonnCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(MEBiomeKeys.GILDED_CAVE, createGildedCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(MEBiomeKeys.IZHERABAN_CAVE, createIzherAbanCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(MEBiomeKeys.LIMESTONE_CAVE, createLimestoneCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(MEBiomeKeys.MUD_CAVE, createMudCaves(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 7435337, 7905386)));
        context.register(MEBiomeKeys.FUNGUS_CAVE, createFungusCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 5869935, 6263141)));

        context.register(MEBiomeKeys.MITHRIL_CAVE, createMithrilCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));

        context.register(MEBiomeKeys.BASALT_CAVE, createBasaltCave(context, new BiomeColorsDTO(
                4142646, 3090215, 6450777, 1513734, 3550502, 2169880)));
        context.register(MEBiomeKeys.MAGMA_CAVE, createMagmaCave(context, new BiomeColorsDTO(
                4142646, 3090215, 6450777, 1513734, 3550502, 2169880)));

        context.register(MEBiomeKeys.DRY_CAVE, createDryCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10928742, 11259497)));
        context.register(MEBiomeKeys.ICE_CAVE, createIceCaves(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 11121530, 10990723)));
    }

    public static Biome createBasicCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createMountainCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.ORE_GABBRO);
        undergroundOres.add(CavesPlacedFeatures.ORE_GNEISS);
        undergroundOres.add(CavesPlacedFeatures.ORE_SCHIST);

        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createLushCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        ModCaveBiomeFeatures.addAxolotls(spawnSettings);

        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        BiomeDefaultFeatures.addPlainGrass(generationSettings);

        undergroundOres.add(OrePlacements.ORE_CLAY);
        undergroundOres.add(CavePlacements.UNDERWATER_MAGMA);
        undergroundOres.add(MiscOverworldPlacements.DISK_SAND);
        undergroundOres.add(MiscOverworldPlacements.DISK_CLAY);
        undergroundOres.add(MiscOverworldPlacements.DISK_GRAVEL);

        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, ModVegetationPlacedFeatures.AZALEA_GROWTH);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.LUSH_CAVES_CEILING_VEGETATION);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.CAVE_VINES);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.LUSH_CAVES_CLAY);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.LUSH_CAVES_VEGETATION);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.SPORE_BLOSSOM);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.CLASSIC_VINES_CAVE);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createDripstoneCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        undergroundOres.add(CavePlacements.LARGE_DRIPSTONE);
        undergroundOres.add(CavePlacements.DRIPSTONE_CLUSTER);
        undergroundOres.add(CavePlacements.POINTED_DRIPSTONE);
        undergroundOres.add(MiscOverworldPlacements.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createDolomiteCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.DOLOMITE_CLUSTER);
        undergroundOres.add(CavesPlacedFeatures.LARGE_DOLOMITE);
        undergroundOres.add(CavesPlacedFeatures.POINTED_DOLOMITE);
        undergroundOres.add(CavesPlacedFeatures.ORE_DOLOMITE_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatures.ORE_OLD_DOLOMITE);
        undergroundOres.add(MiscOverworldPlacements.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createGalonnCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.GALONN_CLUSTER);
        undergroundOres.add(CavesPlacedFeatures.LARGE_GALONN);
        undergroundOres.add(CavesPlacedFeatures.POINTED_GALONN);
        undergroundOres.add(CavesPlacedFeatures.ORE_GALONN);
        undergroundOres.add(CavesPlacedFeatures.ORE_OLD_GALONN);
        undergroundOres.add(MiscOverworldPlacements.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createGildedCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.ORE_GREEN_TUFF);
        undergroundOres.add(CavesPlacedFeatures.ORE_GILDED_GREEN_TUFF);
        //generationSettings.feature(GenerationStep.Feature.UNDERGROUND_ORES, CavesPlacedFeatures.ORE_GOLD_GREEN_TUFF);
        undergroundOres.add(CavesPlacedFeatures.ORE_GOLD_RARE);

        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createIzherAbanCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.IZHER_ABAN_CLUSTER);
        undergroundOres.add(CavesPlacedFeatures.LARGE_IZHER_ABAN);
        undergroundOres.add(CavesPlacedFeatures.POINTED_IZHER_ABAN);
        undergroundOres.add(CavesPlacedFeatures.ORE_IZHER_ABAN);
        undergroundOres.add(CavesPlacedFeatures.ORE_ZIGIL_ABAN);
        undergroundOres.add(CavesPlacedFeatures.ORE_OLD_IZHER_ABAN);
        undergroundOres.add(MiscOverworldPlacements.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createLimestoneCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.LIMESTONE_CLUSTER);
        undergroundOres.add(CavesPlacedFeatures.LARGE_LIMESTONE);
        undergroundOres.add(CavesPlacedFeatures.POINTED_LIMESTONE);
        undergroundOres.add(CavesPlacedFeatures.ORE_LIMESTONE_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatures.ORE_OLD_LIMESTONE);
        undergroundOres.add(MiscOverworldPlacements.DISK_GRAVEL);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createMudCaves(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        ModCaveBiomeFeatures.addSnails(spawnSettings);
        ModCaveBiomeFeatures.addFrogs(spawnSettings);
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        undergroundOres.add(CavesPlacedFeatures.ORE_DIRT);
        undergroundOres.add(CavesPlacedFeatures.ORE_MUD);
        undergroundOres.add(CavesPlacedFeatures.POOL_MUD);
        undergroundOres.add(MiscOverworldPlacements.DISK_GRAVEL);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createFungusCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        ModCaveBiomeFeatures.addSnails(spawnSettings);
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        undergroundOres.add(CavesPlacedFeatures.ORE_DIRT);
        undergroundOres.add(CavesPlacedFeatures.DISK_MYCELIUM);
        undergroundOres.add(MiscOverworldPlacements.DISK_GRAVEL);

        // TODO bring back the fungus trees without crashing
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_BROWN_BOLETTE);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_CAVE_AMANITA);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_CAVE_AMANITA_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_CAVE_AMANITA);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_DEEP_FIRECAP);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_DEEP_FIRECAP_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_DEEP_FIRECAP);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_GHOSTSHROOM);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_GHOSTSHROOM_TILLER);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_SKY_FIRECAP);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_SKY_FIRECAP_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_SKY_FIRECAP);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_TUBESHROOMS);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_TALL_TUBESHROOMS);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_TRUMPET_SHROOM);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_TALL_TRUMPET_SHROOM);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_VIOLET_CAPS);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_VIOLET_CAPS_TILLER);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_YELLOW_AMANITA);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_YELLOW_AMANITA_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_YELLOW_AMANITA);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavesPlacedFeatures.GLOWWORM_WEBBING);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createMithrilCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        undergroundOres.add(CavesPlacedFeatures.ORE_MITHRIL);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createBasaltCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, false);
        generationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, CavesPlacedFeatures.DELTA);
        generationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.SMALL_BASALT_COLUMNS);
        undergroundOres.add(CavesPlacedFeatures.ORE_ASH);
        undergroundOres.add(CavesPlacedFeatures.ORE_ASHEN_DIRT);
        undergroundOres.add(MiscOverworldPlacements.DISK_GRAVEL);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createMagmaCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, false);
        generationSettings.addFeature(GenerationStep.Decoration.SURFACE_STRUCTURES, NetherPlacements.DELTA);
        undergroundOres.add(CavesPlacedFeatures.ORE_MAGMA_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatures.ORE_ASHEN_DIRT);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createDryCave(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.ORE_DRY_DIRT);
        undergroundOres.add(CavesPlacedFeatures.ORE_SAND);
        undergroundOres.add(CavesPlacedFeatures.ORE_SANDSTONE);
        undergroundOres.add(CavesPlacedFeatures.ORE_TERRACOTTA);
        undergroundOres.add(CavesPlacedFeatures.ORE_LAPIS_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatures.PILLAR_SMOOTH_SANDSTONE);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, false);
    }

    public static Biome createIceCaves(BootstrapContext<Biome> context, BiomeColorsDTO biomeColors) {
        MobSpawnSettings.Builder spawnSettings = new MobSpawnSettings.Builder();
        BiomeGenerationSettings.Builder generationSettings = new BiomeGenerationSettings.Builder(context.lookup(Registries.PLACED_FEATURE), context.lookup(Registries.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.ORE_BLUE_ICE);
        undergroundOres.add(CavesPlacedFeatures.ORE_PACKED_ICE);
        undergroundOres.add(CavesPlacedFeatures.ORE_SNOW);
        undergroundOres.add(CavesPlacedFeatures.PILLAR_PACKED_ICE);
        undergroundOres.add(CavesPlacedFeatures.DROOPING_ICICLES);
        undergroundOres.add(CavesPlacedFeatures.SHORT_ICICLES);
        undergroundOres.add(CavesPlacedFeatures.STICKY_ICE);
        undergroundOres.add(CavesPlacedFeatures.STICKY_SNOW);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, false);
    }

    private static void addBasicFeatures(BiomeGenerationSettings.Builder generationSettings, boolean vanillaRocks) {
        ModCaveBiomeFeatures.addAmethystGeode(generationSettings);
        ModCaveBiomeFeatures.addCitrineGeode(generationSettings);
        ModCaveBiomeFeatures.addGlowstoneGeode(generationSettings);
        ModCaveBiomeFeatures.addRedAgateGeode(generationSettings);
        ModCaveBiomeFeatures.addQuartzGeode(generationSettings);

        undergroundOres.add(CavesPlacedFeatures.ORE_COAL);
        undergroundOres.add(CavesPlacedFeatures.ORE_COAL_UPPER);
        undergroundOres.add(CavesPlacedFeatures.ORE_COPPER);
        undergroundOres.add(CavesPlacedFeatures.ORE_COPPER_UPPER);
        undergroundOres.add(CavesPlacedFeatures.ORE_TIN);
        undergroundOres.add(CavesPlacedFeatures.ORE_LAPIS);
        undergroundOres.add(CavesPlacedFeatures.ORE_LEAD);
        undergroundOres.add(CavesPlacedFeatures.ORE_IRON);
        undergroundOres.add(CavesPlacedFeatures.ORE_SILVER);
        undergroundOres.add(CavesPlacedFeatures.ORE_JADE);
        undergroundOres.add(CavesPlacedFeatures.ORE_GOLD);
        undergroundOres.add(CavesPlacedFeatures.ORE_ADAMANT);
        undergroundOres.add(CavesPlacedFeatures.ORE_EMERALD);
        undergroundOres.add(CavesPlacedFeatures.ORE_RUBY);
        undergroundOres.add(CavesPlacedFeatures.ORE_SAPPHIRE);
        undergroundOres.add(CavesPlacedFeatures.SPRING_LAVA);
        undergroundOres.add(MiscOverworldPlacements.SPRING_WATER);

        undergroundOres.add(CavesPlacedFeatures.ORE_MAGMA);
        undergroundOres.add(CavesPlacedFeatures.ORE_OBSIDIAN);
        undergroundOres.add(CavesPlacedFeatures.PILLAR_BASALT);
        undergroundOres.add(CavesPlacedFeatures.ORE_BASALT);
        undergroundOres.add(CavesPlacedFeatures.ORE_TUFF);
        undergroundOres.add(CavesPlacedFeatures.PILLAR_BLACKSTONE);
        undergroundOres.add(CavesPlacedFeatures.ORE_BLACKSTONE);

        undergroundOres.add(OrePlacements.ORE_GRAVEL);
        undergroundOres.add(CavesPlacedFeatures.ORE_DOLOMITE);

        if(vanillaRocks) {
            undergroundOres.add(CavesPlacedFeatures.ORE_QUARTZITE);
            undergroundOres.add(CavesPlacedFeatures.ORE_LIMESTONE);
            undergroundOres.add(OrePlacements.ORE_DIRT);
            undergroundOres.add(OrePlacements.ORE_GRANITE_UPPER);
            undergroundOres.add(OrePlacements.ORE_GRANITE_LOWER);
            undergroundOres.add(OrePlacements.ORE_DIORITE_UPPER);
            undergroundOres.add(OrePlacements.ORE_DIORITE_LOWER);
            undergroundOres.add(OrePlacements.ORE_ANDESITE_UPPER);
            undergroundOres.add(OrePlacements.ORE_ANDESITE_LOWER);
        }
        undergroundOres.add(OrePlacements.ORE_TUFF);
        generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, CavePlacements.GLOW_LICHEN);
        BiomeDefaultFeatures.addSurfaceFreezing(generationSettings);
    }

    public static Biome createBiome(BiomeColorsDTO biomeColors, MobSpawnSettings.Builder spawnSettings, BiomeGenerationSettings.Builder generationSettings, float temperature, boolean precipitation) {
        ModCaveBiomeFeatures.addBats(spawnSettings);

        ModCaveBiomeFeatures.addSparseNpc(spawnSettings);
        ModCaveBiomeFeatures.addCaveTroll(spawnSettings);
        ModCaveBiomeFeatures.addSpiders(spawnSettings);

        undergroundOres = undergroundOres.stream().sorted(Comparator.comparing(a -> a.location().toString())).toList();
        for (ResourceKey<PlacedFeature> feature: undergroundOres) {
            generationSettings.addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, feature);
        }

        Biome biome = (new Biome.BiomeBuilder())
                .hasPrecipitation(precipitation)
                .temperature(temperature)
                .downfall(0.5F)
                .specialEffects((new BiomeSpecialEffects.Builder())
                        .skyColor(biomeColors.skyColor)
                        .fogColor(biomeColors.fogColor)
                        .waterColor(biomeColors.waterColor)
                        .waterFogColor(biomeColors.waterFogColor)
                        .grassColorOverride(biomeColors.grassColor)
                        .foliageColorOverride(biomeColors.foliageColor)
                        .build())
                .mobSpawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build();
        undergroundOres = new ArrayList<>();
        return biome;
    }
}
