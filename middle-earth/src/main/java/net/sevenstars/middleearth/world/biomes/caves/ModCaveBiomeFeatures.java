package net.sevenstars.middleearth.world.biomes.caves;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.world.features.underground.CavesPlacedFeatures;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;

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
        spawnSettings.spawn(SpawnGroup.AXOLOTLS,12, new SpawnSettings.SpawnEntry(EntityType.AXOLOTL, 2, 5));
    }
    public static void addBats(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.AMBIENT, 1, new SpawnSettings.SpawnEntry(EntityType.BAT, 1, 2));
    }
    public static void addFrogs(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.CREATURE, 4, new SpawnSettings.SpawnEntry(EntityType.FROG, 1, 4));
    }
    public static void addSnails(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.CREATURE, 5, new SpawnSettings.SpawnEntry(EntitiesWT.SNAIL, 1, 3));
    }

    public static void addParseWildGoblins(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, 2, new SpawnSettings.SpawnEntry(EntitiesME.NPC, 1, 3))
                .spawnCost(EntitiesME.NPC, 0.7, 0.3);
    }

    public static void addGroupWildGoblins(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, 2, new SpawnSettings.SpawnEntry(EntitiesME.NPC, 3, 5))
                .spawnCost(EntitiesME.NPC, 0.7, 0.3);
    }

    public static void addSpiders(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, 2, new SpawnSettings.SpawnEntry(EntitiesME.SHELOBITE_SCUTTLER, 1, 2))
                .spawnCost(EntitiesME.SHELOBITE_SCUTTLER, 0.7, 0.1);
    }

    public static void addCaveTroll(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, 1, new SpawnSettings.SpawnEntry(EntitiesME.CAVE_TROLL, 1, 1))
                .spawnCost(EntitiesME.CAVE_TROLL, 0.7, 1);
    }
    public static void addSpiderLarvas(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, 2, new SpawnSettings.SpawnEntry(EntitiesME.SHELOBITE_LARVA, 2, 4))
                .spawnCost(EntitiesME.SHELOBITE_LARVA, 0.7, 0.3);
    }

    public static void addSnowTrolls(SpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, 4, new SpawnSettings.SpawnEntry(EntitiesME.SNOW_TROLL, 1, 2));
    }

}
