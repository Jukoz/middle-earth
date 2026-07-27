package net.sevenstars.middleearth.world.spawners;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;

public class ModSpawnSettingsBuilder {
    public static MobSpawnSettings.Builder addSpawn(
            MobSpawnSettings.Builder builder,
            EntityType<?> entityType,
            int weight,
            int minCount,
            int maxCount
    ) {
        return builder.addSpawn(
                entityType.getCategory(),
                new MobSpawnSettings.SpawnerData(entityType, weight, minCount, maxCount)
        );
    }

    public static void addRiverAnimals(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.SALMON, 12, 1, 5);
    }
    public static void addOceanAnimals(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.COD, 10, 1, 5);
        addSpawn(builder, EntityType.SQUID, 6, 1, 4);
        addSpawn(builder, EntityType.TURTLE, 4, 1, 2);
        addSpawn(builder, EntityType.DOLPHIN, 3, 1, 2);
    }

    public static void addColdWaterAnimals(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.COD, 5, 1, 5);
    }

    public static void addFarmAnimals(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.SHEEP, 12, 4, 4);
        addSpawn(builder, EntityType.PIG, 10, 4, 4);
        addSpawn(builder, EntityType.CHICKEN, 10, 4, 4);
        addSpawn(builder, EntityType.COW, 8, 4, 4);
    }

    public static void addRareWarg(MobSpawnSettings.Builder builder){
        addSpawn(builder, EntitiesME.WARG, 3, 1, 3);
    }
    public static void addUncommonWarg(MobSpawnSettings.Builder builder){
        addSpawn(builder, EntitiesME.WARG, 6, 1, 3);
    }

    public static void addRareStoneTroll(MobSpawnSettings.Builder builder){
        addSpawn(builder, EntitiesME.STONE_TROLL, 3, 1, 2);
    }
    public static void addRareCaveTroll(MobSpawnSettings.Builder builder){
        addSpawn(builder, EntitiesME.CAVE_TROLL, 3, 1, 1);
    }
    public static void addMirkwoodSpider(MobSpawnSettings.Builder builder){
        addSpawn(builder, EntitiesME.SHELOBITE_SCUTTLER, 9, 2, 4);
    }
    public static void addRareMirkwoodSpider(MobSpawnSettings.Builder builder){
        addSpawn(builder, EntitiesME.SHELOBITE_SCUTTLER, 5, 1, 2);
    }

    public static void addUncommonBats(MobSpawnSettings.Builder builder){
        addSpawn(builder, EntityType.BAT, 8, 2, 4);
    }

    public static void addPlainsMobs(MobSpawnSettings.Builder builder) {
        addFarmAnimals(builder);
        addSpawn(builder, EntityType.HORSE, 5, 2, 6);
        addSpawn(builder, EntityType.DONKEY, 1, 1, 3);
    }

    public static void addRabbits(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.RABBIT, 3, 1, 5);
    }

    public static void addCats(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.CAT, 5, 1, 3);
    }
    public static void addWolves(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.WOLF, 3, 1, 3);
    }
    public static void addCommonWolves(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.WOLF, 8, 1, 3);
    }
    public static void addRareWolves(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.WOLF, 1, 1, 2);
    }

    public static void addMountainsMobs(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.GOAT, 4, 1, 3);
    }
    public static void addBroadhoofGoats(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntitiesME.BROADHOOF_GOAT, 4, 1, 3);
    }

    public static void addNordicMobs(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.RABBIT, 4, 2, 4);
        addSpawn(builder, EntityType.FOX, 2, 1, 3);
    }

    public static void addForochelMobs(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.FOX, 2, 1, 3);
        addSpawn(builder, EntityType.POLAR_BEAR, 1, 1, 2);
    }

    public static void addSwampMobs(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.FROG, 8, 1, 4);
        addSpawn(builder, EntitiesWT.SNAIL, 10, 1, 4);
    }

    public static void addRareSnails(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntitiesWT.SNAIL, 2, 1, 3);
    }

    public static void addEriadorMobs(MobSpawnSettings.Builder builder) {
        addPlainsMobs(builder);
        addPheasant(builder);
    }

    public static void addArmadillo(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.ARMADILLO, 3, 1, 3);
    }
    public static void addHaradMobs(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.RABBIT, 3, 1, 4);
    }

    public static void addCamel(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.CAMEL, 1, 1, 2);
    }

    public static void addLlama(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntityType.LLAMA, 2, 1, 3);
    }

    public static void addPheasant(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntitiesWT.PHEASANT, 6, 1, 2);
    }

    public static void addSwan(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntitiesWT.SWAN, 7, 1, 3);
    }

    public static void addDeer(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntitiesWT.DEER, 10, 1, 4);
    }

    public static void addNpcs(MobSpawnSettings.Builder spawnSettings) {
        addSpawn(spawnSettings, EntitiesME.NPC, 3, 1, 1);
    }

    public static void addGreatHorn(MobSpawnSettings.Builder builder) {
        addSpawn(builder, EntitiesME.GREAT_HORN, 5, 1, 2);
    }
}
