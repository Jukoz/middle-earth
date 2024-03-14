package net.jukoz.me.world.biomes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
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

    public static void bootstrap(Registerable<Biome> context) {
        context.register(MEBiomeKeys.LUSH_CAVE, createLushCave(context, new BiomeColorsDTO(
                defaultSky, defaultFog, defaultWater, defaultWaterFog, 8703593, 8703593)));

    }

    public static Biome createLushCave(Registerable<Biome> context, BiomeColorsDTO biomeColors) {
        SpawnSettings.Builder spawnSettings = new SpawnSettings.Builder();
        spawnSettings.spawn(SpawnGroup.AXOLOTLS, new SpawnSettings.SpawnEntry(EntityType.AXOLOTL, 10, 4, 6));

        GenerationSettings.LookupBackedBuilder generationSettings = new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE), context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        addBasicFeatures(generationSettings);
        DefaultBiomeFeatures.addPlainsTallGrass(generationSettings);

        undergroundOres.add(OrePlacedFeatures.ORE_CLAY);

        undergroundOres.add(OrePlacedFeatures.ORE_COAL_UPPER);
        undergroundOres.add(OrePlacedFeatures.ORE_COAL_LOWER);
        undergroundOres.add(OrePlacedFeatures.ORE_IRON_UPPER);
        undergroundOres.add(OrePlacedFeatures.ORE_IRON_MIDDLE);
        undergroundOres.add(OrePlacedFeatures.ORE_IRON_SMALL);
        undergroundOres.add(OrePlacedFeatures.ORE_GOLD);
        undergroundOres.add(OrePlacedFeatures.ORE_GOLD_LOWER);
        //undergroundOres.add(OrePlacedFeatures.ORE_LAPIS);
        //undergroundOres.add(OrePlacedFeatures.ORE_LAPIS_BURIED);
        undergroundOres.add(OrePlacedFeatures.ORE_COPPER);
        undergroundOres.add(UndergroundPlacedFeatures.UNDERWATER_MAGMA);
        undergroundOres.add(MiscPlacedFeatures.DISK_SAND);
        undergroundOres.add(MiscPlacedFeatures.DISK_CLAY);
        undergroundOres.add(MiscPlacedFeatures.DISK_GRAVEL);

        DefaultBiomeFeatures.addLushCavesDecoration(generationSettings);

        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    private static void addBasicFeatures(GenerationSettings.LookupBackedBuilder generationSettings) {
        DefaultBiomeFeatures.addAmethystGeodes(generationSettings);
        ModCaveBiomeFeatures.addRedAgateGeode(generationSettings);

        undergroundOres.add(OrePlacedFeatures.ORE_DIRT);
        undergroundOres.add(OrePlacedFeatures.ORE_GRAVEL);
        undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_UPPER);
        undergroundOres.add(OrePlacedFeatures.ORE_GRANITE_LOWER);
        undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_UPPER);
        undergroundOres.add(OrePlacedFeatures.ORE_DIORITE_LOWER);
        undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_UPPER);
        undergroundOres.add(OrePlacedFeatures.ORE_ANDESITE_LOWER);
        undergroundOres.add(OrePlacedFeatures.ORE_TUFF);
        generationSettings.feature(GenerationStep.Feature.VEGETAL_DECORATION, UndergroundPlacedFeatures.GLOW_LICHEN);
        DefaultBiomeFeatures.addSprings(generationSettings);
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings);
    }

    public static Biome createBiome(BiomeColorsDTO biomeColors, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings) {
        return createBiome(biomeColors, spawnSettings, generationSettings, 0.5f, true);
    }

    public static Biome createBiome(BiomeColorsDTO biomeColors, SpawnSettings.Builder spawnSettings, GenerationSettings.LookupBackedBuilder generationSettings, float temperature, boolean precipitation) {
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
