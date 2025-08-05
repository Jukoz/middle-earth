package net.sevenstars.middleearth.world.spawners;

import net.sevenstars.middleearth.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;

public class ModSpawnSettingsBuilder {
    public static void addRiverAnimals(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.WATER_AMBIENT,12,  new SpawnSettings.SpawnEntry(EntityType.SALMON, 1, 5));
    }
    public static void addOceanAnimals(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.WATER_AMBIENT, 10, new SpawnSettings.SpawnEntry(EntityType.COD, 1, 5));
        builder.spawn(SpawnGroup.WATER_CREATURE,6, new SpawnSettings.SpawnEntry(EntityType.SQUID, 1, 4));
        builder.spawn(SpawnGroup.WATER_CREATURE,4,  new SpawnSettings.SpawnEntry(EntityType.TURTLE, 1, 2));
        builder.spawn(SpawnGroup.WATER_CREATURE, 3, new SpawnSettings.SpawnEntry(EntityType.DOLPHIN, 1, 2));
    }

    public static void addColdWaterAnimals(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.WATER_AMBIENT,5, new SpawnSettings.SpawnEntry(EntityType.COD,  1, 5));
    }

    public static void addFarmAnimals(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 12, new SpawnSettings.SpawnEntry(EntityType.SHEEP, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.PIG, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntityType.CHICKEN, 4, 4));
        builder.spawn(SpawnGroup.CREATURE, 8, new SpawnSettings.SpawnEntry(EntityType.COW, 4, 4));
    }

    public static void addRareWarg(SpawnSettings.Builder builder){
        builder.spawn(SpawnGroup.CREATURE, 3,  new SpawnSettings.SpawnEntry(ModEntities.WARG, 1, 3));
    }
    public static void addUncommonWarg(SpawnSettings.Builder builder){
        builder.spawn(SpawnGroup.CREATURE, 6,new SpawnSettings.SpawnEntry(ModEntities.WARG,  1, 3));
    }

    public static void addRareStoneTroll(SpawnSettings.Builder builder){
        builder.spawn(SpawnGroup.CREATURE,3,  new SpawnSettings.SpawnEntry(ModEntities.STONE_TROLL, 1, 2));
    }
    public static void addMirkwoodSpider(SpawnSettings.Builder builder){
        builder.spawn(SpawnGroup.CREATURE, 9, new SpawnSettings.SpawnEntry(ModEntities.MIRKWOOD_SPIDER, 2, 4));
    }
    public static void addRareMirkwoodSpider(SpawnSettings.Builder builder){
        builder.spawn(SpawnGroup.CREATURE, 5, new SpawnSettings.SpawnEntry(ModEntities.MIRKWOOD_SPIDER, 1, 2));
    }

    public static void addUncommonBats(SpawnSettings.Builder builder){
    builder.spawn(SpawnGroup.CREATURE, 8, new SpawnSettings.SpawnEntry(EntityType.BAT, 2, 4));
    }

    public static void addPlainsMobs(SpawnSettings.Builder builder) {
        addFarmAnimals(builder);
        builder.spawn(SpawnGroup.CREATURE, 5, new SpawnSettings.SpawnEntry(EntityType.HORSE, 2, 6));
        builder.spawn(SpawnGroup.CREATURE, 1, new SpawnSettings.SpawnEntry(EntityType.DONKEY, 1, 3));
    }

    public static void addRabbits(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE,3,  new SpawnSettings.SpawnEntry(EntityType.RABBIT, 1, 5));
    }

    public static void addCats(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 5, new SpawnSettings.SpawnEntry(EntityType.CAT, 1, 3));
    }
    public static void addWolves(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 3, new SpawnSettings.SpawnEntry(EntityType.WOLF, 1, 3));
    }
    public static void addCommonWolves(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE,8,  new SpawnSettings.SpawnEntry(EntityType.WOLF, 1, 3));
    }
    public static void addRareWolves(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 1, new SpawnSettings.SpawnEntry(EntityType.WOLF, 1, 2));
    }

    public static void addMountainsMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 4, new SpawnSettings.SpawnEntry(EntityType.GOAT, 1, 3));
    }
    public static void addBroadhoofGoats(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 4, new SpawnSettings.SpawnEntry(ModEntities.BROADHOOF_GOAT, 1, 3));
    }

    public static void addNordicMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE,4, new SpawnSettings.SpawnEntry(EntityType.RABBIT, 2, 4));
        builder.spawn(SpawnGroup.CREATURE, 2, new SpawnSettings.SpawnEntry(EntityType.FOX,  1, 3));
    }

    public static void addForochelMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 2, new SpawnSettings.SpawnEntry(EntityType.FOX, 1, 3));
        builder.spawn(SpawnGroup.CREATURE, 1, new SpawnSettings.SpawnEntry(EntityType.POLAR_BEAR, 1, 2));
    }

    public static void addSwampMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE,8, new SpawnSettings.SpawnEntry(EntityType.FROG,  1, 4));
        builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntitiesWT.SNAIL, 1, 4));
    }

    public static void addRareSnails(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 2,new SpawnSettings.SpawnEntry(EntitiesWT.SNAIL,  1, 3));
    }

    public static void addEriadorMobs(SpawnSettings.Builder builder) {
        addPlainsMobs(builder);
        addPheasant(builder);
    }

    public static void addArmadillo(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 3, new SpawnSettings.SpawnEntry(EntityType.ARMADILLO, 1, 3));
    }
    public static void addHaradMobs(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE,3,  new SpawnSettings.SpawnEntry(EntityType.RABBIT, 1, 4));
    }

    public static void addCamel(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE,1, new SpawnSettings.SpawnEntry(EntityType.CAMEL, 1, 2));
    }

    public static void addLlama(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 2, new SpawnSettings.SpawnEntry(EntityType.LLAMA, 1, 3));
    }

    public static void addPheasant(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 6,new SpawnSettings.SpawnEntry(EntitiesWT.PHEASANT,  1, 2));
    }

    public static void addSwan(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 7, new SpawnSettings.SpawnEntry(EntitiesWT.SWAN, 1, 3));
    }

    public static void addDeer(SpawnSettings.Builder builder) {
        builder.spawn(SpawnGroup.CREATURE, 10, new SpawnSettings.SpawnEntry(EntitiesWT.DEER, 1, 4));
    }
}
