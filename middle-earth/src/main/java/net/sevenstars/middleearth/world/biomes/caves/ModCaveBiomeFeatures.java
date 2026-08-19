package net.sevenstars.middleearth.world.biomes.caves;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.world.features.underground.CavesPlacedFeatures;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;

import static net.sevenstars.middleearth.world.spawners.ModSpawnSettingsBuilder.addSpawn;

public class ModCaveBiomeFeatures {

    public static void addAmethystGeode(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavesPlacedFeatures.AMETHYST_GEODE);
    }
    public static void addCitrineGeode(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavesPlacedFeatures.CITRINE_GEODE);
    }
    public static void addGlowstoneGeode(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavesPlacedFeatures.GLOWSTONE_GEODE);
    }
    public static void addRedAgateGeode(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavesPlacedFeatures.RED_AGATE_GEODE);
    }
    public static void addQuartzGeode(BiomeGenerationSettings.Builder builder) {
        builder.addFeature(GenerationStep.Decoration.LOCAL_MODIFICATIONS, CavesPlacedFeatures.QUARTZ_GEODE);
    }

    public static void addAxolotls(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntityType.AXOLOTL, 12, 2, 5);
    }
    public static void addBats(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntityType.BAT, 1, 1, 2);
    }
    public static void addFrogs(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntityType.FROG, 4, 1, 4);
    }
    public static void addSnails(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntitiesWT.SNAIL, 5, 1, 3);
    }

    // Monsters
    public static void addSparseNpc(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntitiesME.NPC, 4, 1, 1);
                //.spawnCost(EntitiesME.NPC, 0.7, 0.5);
    }
    public static void addCaveTroll(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntitiesME.CAVE_TROLL, 5, 1, 2);
                //.spawnCost(EntitiesME.CAVE_TROLL, 20, 0.1);
    }
    public static void addSpiders(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntitiesME.SHELOBITE_SCUTTLER, 3, 1, 5);
        addSpawn(spawnSettings, EntitiesME.SPAWN_OF_SHELOB, 5, 1, 1);
    }
    public static void addSpiderLarvas(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntitiesME.SHELOBITE_LARVA, 2, 2, 4)
                .addMobCharge(EntitiesME.SHELOBITE_LARVA, 0.7, 0.3);
    }
    public static void addSnowTrolls(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntitiesME.SNOW_TROLL, 4, 1, 2);
    }

}
