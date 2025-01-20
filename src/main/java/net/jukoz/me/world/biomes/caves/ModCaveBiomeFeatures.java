package net.jukoz.me.world.biomes.caves;

import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.world.features.underground.CavesPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;

public class ModCaveBiomeFeatures {

    public static void addAmethystGeode(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.AMETHYST_GEODE);
    }
    public static void addCitrineGeode(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.CITRINE_GEODE);
    }
    public static void addGlowstoneGeode(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.GLOWSTONE_GEODE);
    }
    public static void addRedAgateGeode(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.RED_AGATE_GEODE);
    }
    public static void addQuartzGeode(GenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.QUARTZ_GEODE);
    }

    public static void addAxolotls(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.AXOLOTLS, new SpawnSettings.SpawnEntry(EntityType.AXOLOTL, 12, 2, 5));
    }
    public static void addBats(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.AMBIENT, new SpawnSettings.SpawnEntry(EntityType.BAT, 3, 1, 2));
    }
    public static void addFrogs(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(EntityType.FROG, 4, 1, 4));
    }
    public static void addSnails(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.SNAIL, 5, 1, 3));
    }
    public static void addWildGoblins(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.WILD_GOBLIN, 2, 1, 2));
    }
    public static void addSpiders(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CAVE_SPIDER, 4, 1, 2));
    }

    public static void addSnowTrolls(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(ModEntities.SNOW_TROLL, 4, 1, 2));
    }

}
