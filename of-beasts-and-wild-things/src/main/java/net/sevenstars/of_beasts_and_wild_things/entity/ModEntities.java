package net.sevenstars.of_beasts_and_wild_things.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntity;

public class ModEntities {

    public static final EntityType<SnailEntity> SNAIL = register("snail", EntityType.Builder.create(SnailEntity::new, SpawnGroup.CREATURE).dimensions(0.3f, 0.3f));



    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return (EntityType) Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }

    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(OfBeastsAndWildThings.MOD_ID, id));
    }

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(SNAIL, SnailEntity.createSnailAttributes());

        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Entities for " + OfBeastsAndWildThings.MOD_ID);
    }
}
