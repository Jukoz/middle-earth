package net.sevenstars.of_beasts_and_wild_things.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.datageneration.content.TranslationEntries;
import net.sevenstars.of_beasts_and_wild_things.entity.deer.DeerEntity;
import net.sevenstars.of_beasts_and_wild_things.entity.pheasant.PheasantEntity;
import net.sevenstars.of_beasts_and_wild_things.entity.projectile.thrown.SwanEggEntity;
import net.sevenstars.of_beasts_and_wild_things.entity.snail.SnailEntity;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;

public class EntitiesWT {

    public static final EntityType<SnailEntity> SNAIL = register("snail", EntityType.Builder.of(SnailEntity::new, MobCategory.CREATURE).sized(0.3f, 0.3f));
    public static final EntityType<PheasantEntity> PHEASANT = register("pheasant", EntityType.Builder.of(PheasantEntity::new, MobCategory.CREATURE).sized(0.6f, 0.6f));
    public static final EntityType<SwanEntity> SWAN = register("swan", EntityType.Builder.of(SwanEntity::new, MobCategory.CREATURE).sized(0.8f, 0.8f));
    public static final EntityType<DeerEntity> DEER = register("deer", EntityType.Builder.of(DeerEntity::new, MobCategory.CREATURE).sized(0.8f, 1.5f));

    // Projectiles
    public static final EntityType<SwanEggEntity> SWAN_EGG = register("swan_egg", EntityType.Builder.<SwanEggEntity>of(SwanEggEntity::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(10));

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        EntityType<T> entityType = RegistrationBridge.register(
                BuiltInRegistries.ENTITY_TYPE, OfBeastsAndWildThings.of(id), type.build(id));
        TranslationEntries.entityEntries.add(entityType);
        return entityType;
    }

    public static void registerModEntities() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Entities for " + OfBeastsAndWildThings.MOD_ID);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(SNAIL, SnailEntity.createSnailAttributes().build());
        event.put(PHEASANT, PheasantEntity.createPheasantAttributes().build());
        event.put(SWAN, SwanEntity.createSwanAttributes().build());
        event.put(DEER, DeerEntity.createDeerAttributes().build());
    }
}
