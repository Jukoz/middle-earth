package net.sevenstars.of_beasts_and_wild_things.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.Heightmap;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.utils.BiomeTagsWT;

public class EntitySpawnsWT {
    public static void addSpawns() {
        // Snail
        BiomeModifications.addSpawn(BiomeSelectors.tag(BiomeTagsWT.SNAIL_SPAWNS),
                SpawnGroup.CREATURE,
                EntitiesWT.SNAIL,
                40, 2, 5);
        SpawnRestriction.register(EntitiesWT.SNAIL, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::isValidNaturalSpawn);
    }
}
