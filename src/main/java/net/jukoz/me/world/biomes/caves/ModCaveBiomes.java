package net.jukoz.me.world.biomes.caves;

import net.jukoz.me.world.biomes.BiomeColorsDTO;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.surface.CustomBiome;
import net.jukoz.me.world.features.underground.CavesPlacedFeatures;
import net.jukoz.me.world.features.vegetation.ModVegetationPlacedFeatures;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ModCaveBiomes {
    public static final int defaultSky = 7907327;
    public static final int defaultFog = 12638463;
    public static final int defaultWater = 4159204;
    public static final int defaultWaterFog = 329011;

    private static List<RegistryKey<PlacedFeature>> undergroundOres = new ArrayList<>();;

    public static CaveBiomesMap defaultCaves = new CaveBiomesMap();
    public static CaveBiomesMap ashCaves = new CaveBiomesMap();
    public static CaveBiomesMap forodCaves = new CaveBiomesMap();
    public static CaveBiomesMap haradCaves = new CaveBiomesMap();

    public static void init() {
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.LUSH_CAVE, new Vec2f(-1.0f,0f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2f(1.0f,0f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.MUD_CAVE, new Vec2f(1.0f,1.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.FUNGUS_CAVE, new Vec2f(0f,-1.0f)));
        defaultCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.BASIC_CAVE, new Vec2f(0.0f,0.8f)));

        ashCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2f(1.0f,0.5f)));
        ashCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.BASALT_CAVE, new Vec2f(-1.0f,0.5f)));
        ashCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.MAGMA_CAVE, new Vec2f(0.0f,-1.0f)));

        haradCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2f(1.0f,0f)));
        haradCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRY_CAVE, new Vec2f(0.0f,0f)));
        haradCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.BASIC_CAVE, new Vec2f(-1.0f,0f)));

        forodCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.ICE_CAVE, new Vec2f(-0.5f,0f)));
        forodCaves.addCave(new CaveBiomeDTO(MEBiomeKeys.DRIPSTONE_CAVE, new Vec2f(1.0f,0f)));
    }

    public static RegistryKey<Biome> getBiome(Vec2f coordinates, CustomBiome surfaceBiome) {
        if(surfaceBiome.getCaveType() != null)
            return switch (surfaceBiome.getCaveType()) {
                case ASHEN -> ashCaves.getClosestBiome(coordinates);
                case HARAD -> haradCaves.getClosestBiome(coordinates);
                case FOROD -> forodCaves.getClosestBiome(coordinates);
                default -> defaultCaves.getClosestBiome(coordinates);
            };
        return defaultCaves.getClosestBiome(coordinates);
    }

    public static void bootstrap(Registerable<Biome> context) {
        context.register(MEBiomeKeys.BASIC_CAVE, createBasicCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 10338918, 10604137)));
        context.register(MEBiomeKeys.LUSH_CAVE, createLushCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 8703593)));
        context.register(MEBiomeKeys.DRIPSTONE_CAVE, createDripstoneCave(context, new BiomeColorsDTO(
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

    public static Biome createBasicCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createLushCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModCaveBiomeFeatures.addAxolotls(spawnSettings);

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        DefaultBiomeFeatures.addPlainsTallGrass(generationSettings);

        undergroundOres.add(OrePlacedFeatures.ORE_CLAY);
        undergroundOres.add(UndergroundPlacedFeatures.UNDERWATER_MAGMA);
        undergroundOres.add(MiscPlacedFeatures.DISK_SAND);
        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);

        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, ModVegetationPlacedFeatures.AZALEA_GROWTH);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.LUSH_CAVES_CEILING_VEGETATION);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.CAVE_VINES);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.LUSH_CAVES_CLAY);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.LUSH_CAVES_VEGETATION);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.SPORE_BLOSSOM);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.CLASSIC_VINES_CAVE);

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

    public static Biome createMudCaves(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModCaveBiomeFeatures.addSnails(spawnSettings);
        ModCaveBiomeFeatures.addFrogs(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        undergroundOres.add(CavesPlacedFeatures.ORE_DIRT);
        undergroundOres.add(CavesPlacedFeatures.ORE_MUD);
        undergroundOres.add(CavesPlacedFeatures.POOL_MUD);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createFungusCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        ModCaveBiomeFeatures.addSnails(spawnSettings);
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        undergroundOres.add(CavesPlacedFeatures.ORE_DIRT);
        undergroundOres.add(CavesPlacedFeatures.DISK_MYCELIUM);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);

        // TODO bring back the fungus trees without crashing
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_BROWN_BOLETTE);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_CAVE_AMANITA);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_CAVE_AMANITA_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_CAVE_AMANITA);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_DEEP_FIRECAP);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_DEEP_FIRECAP_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_DEEP_FIRECAP);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_GHOSTSHROOM);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_GHOSTSHROOM_TILLER);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_SKY_FIRECAP);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_SKY_FIRECAP_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_SKY_FIRECAP);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_TUBESHROOMS);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_TALL_TUBESHROOMS);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_TRUMPET_SHROOM);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_TALL_TRUMPET_SHROOM);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_VIOLET_CAPS);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_VIOLET_CAPS_TILLER);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_YELLOW_AMANITA);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.PATCH_YELLOW_AMANITA_TILLER);
        //generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.TREE_YELLOW_AMANITA);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, CavesPlacedFeatures.GLOWWORM_WEBBING);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createMithrilCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, true);
        undergroundOres.add(CavesPlacedFeatures.ORE_MITHRIL);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createBasaltCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, false);
        generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, CavesPlacedFeatures.DELTA);
        generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, NetherPlacedFeatures.SMALL_BASALT_COLUMNS);
        undergroundOres.add(CavesPlacedFeatures.ORE_ASH);
        undergroundOres.add(CavesPlacedFeatures.ORE_ASHEN_DIRT);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createMagmaCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings, false);
        generationSettings.feature(GenerationStep.Feature.SURFACE_STRUCTURES, NetherPlacedFeatures.DELTA);
        undergroundOres.add(CavesPlacedFeatures.ORE_MAGMA_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatures.ORE_ASHEN_DIRT);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createDryCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.ORE_DRY_DIRT);
        undergroundOres.add(CavesPlacedFeatures.ORE_SAND);
        undergroundOres.add(CavesPlacedFeatures.ORE_SANDSTONE);
        undergroundOres.add(CavesPlacedFeatures.ORE_TERRACOTTA);
        undergroundOres.add(CavesPlacedFeatures.ORE_LAPIS_ABUNDANT);
        undergroundOres.add(CavesPlacedFeatures.PILLAR_SMOOTH_SANDSTONE);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, false);
    }

    public static Biome createIceCaves(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        undergroundOres.add(CavesPlacedFeatures.ORE_BLUE_ICE);
        undergroundOres.add(CavesPlacedFeatures.ORE_PACKED_ICE);
        undergroundOres.add(CavesPlacedFeatures.ORE_SNOW);
        undergroundOres.add(CavesPlacedFeatures.PILLAR_PACKED_ICE);
        addBasicFeatures(generationSettings, true);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, false);
    }

    private static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings, boolean vanillaRocks) {
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
        undergroundOres.add(CavesPlacedFeatures.ORE_EMERALD);
        undergroundOres.add(CavesPlacedFeatures.SPRING_LAVA);
        undergroundOres.add(MiscPlacedFeatures.SPRING_WATER);

        undergroundOres.add(CavesPlacedFeatures.ORE_MAGMA);
        undergroundOres.add(CavesPlacedFeatures.ORE_OBSIDIAN);
        undergroundOres.add(CavesPlacedFeatures.PILLAR_BASALT);
        undergroundOres.add(CavesPlacedFeatures.ORE_BASALT);
        undergroundOres.add(CavesPlacedFeatures.ORE_TUFF);
        undergroundOres.add(CavesPlacedFeatures.PILLAR_BLACKSTONE);
        undergroundOres.add(CavesPlacedFeatures.ORE_BLACKSTONE);

        undergroundOres.add(OrePlacedFeatures.ORE_GRAVEL);
        undergroundOres.add(CavesPlacedFeatures.ORE_DOLOMITE);

        if(vanillaRocks) {
            undergroundOres.add(CavesPlacedFeatures.ORE_QUARTZITE);
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
        ModCaveBiomeFeatures.addBats(spawnSettings);

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
