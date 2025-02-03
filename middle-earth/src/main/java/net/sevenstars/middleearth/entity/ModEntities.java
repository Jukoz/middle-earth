package net.sevenstars.middleearth.entity;

import com.google.common.collect.ImmutableSet;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityType;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.datafixer.TypeReferences;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.CamelEntity;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.*;
import net.minecraft.resource.featuretoggle.FeatureFlag;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.fire_of_orthanc.FireOfOrthancEntity;
import net.sevenstars.middleearth.entity.barrow_wights.BarrowWightEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.petrified.PetrifiedTrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.stone.StoneTrollEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.deer.DeerEntity;
import net.sevenstars.middleearth.entity.pheasant.PheasantEntity;
import net.sevenstars.middleearth.entity.projectile.boulder.BoulderEntity;
import net.sevenstars.middleearth.entity.projectile.pebble.PebbleEntity;
import net.sevenstars.middleearth.entity.projectile.pinecone.LitPineconeEntity;
import net.sevenstars.middleearth.entity.projectile.pinecone.PineconeEntity;
import net.sevenstars.middleearth.entity.projectile.spear.SpearEntity;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.entity.snail.SnailEntity;
import net.sevenstars.middleearth.entity.spider.MirkwoodSpiderEntity;
import net.sevenstars.middleearth.entity.swan.SwanEntity;

import java.util.Optional;

public class ModEntities {

    //TODO Npc weird animation/item holding -> model fucked

    // Barrow Wights
    public static final EntityType<BarrowWightEntity> BARROW_WIGHT = register("barrow_wight", EntityType.Builder.create(BarrowWightEntity::new, SpawnGroup.CREATURE).dimensions(0.9f, 2.1f));

    // Wargs
    public static final EntityType<WargEntity> WARG = register("warg", EntityType.Builder.create(WargEntity::new, SpawnGroup.CREATURE).dimensions(1.4f, 1.4f));

    // Goat
    public static final EntityType<BroadhoofGoatEntity> BROADHOOF_GOAT = register("broadhoof_goat", EntityType.Builder.create(BroadhoofGoatEntity::new, SpawnGroup.CREATURE).dimensions(1.4f, 1.4f));

    // Spiders
    public static final EntityType<MirkwoodSpiderEntity> MIRKWOOD_SPIDER = register("mirkwood_spider", EntityType.Builder.create(MirkwoodSpiderEntity::new, SpawnGroup.CREATURE).dimensions(1.15f, 0.9f));

    // Trolls
    public static final EntityType<SnowTrollEntity> SNOW_TROLL = register("snow_troll", EntityType.Builder.create(SnowTrollEntity::new, SpawnGroup.CREATURE).dimensions(2.2f, 2.5f));
    public static final EntityType<StoneTrollEntity> STONE_TROLL = register("stone_troll", EntityType.Builder.create(StoneTrollEntity::new, SpawnGroup.CREATURE).dimensions(1.4f, 3.4f));
    public static final EntityType<PetrifiedTrollEntity> PETRIFIED_TROLL = register("petrified_troll", EntityType.Builder.create(PetrifiedTrollEntity::new, SpawnGroup.CREATURE).dimensions(1.4f, 3.4f));

    ///* Weapons *///
    public static final EntityType<FireOfOrthancEntity> FIRE_OF_ORTHANC = registerEntity("fire_of_orthanc", FireOfOrthancEntity::new, SpawnGroup.MISC, 0.65F, 0.65F);
    public static final EntityType<PebbleEntity> PEBBLE = registerEntity("pebble", PebbleEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<PineconeEntity> PINECONE = registerEntity("pinecone", PineconeEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<LitPineconeEntity> LIT_PINECONE = registerEntity("lit_pinecone", LitPineconeEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<BoulderEntity> BOULDER = registerEntity("boulder", BoulderEntity::new, SpawnGroup.MISC, 1f, 1f);

    public static final EntityType<SpearEntity> SPEAR = registerEntity("spear", SpearEntity::new, SpawnGroup.MISC, 1f, 1f);

    ///* Animals *///
    public static final EntityType<SwanEntity> SWAN = register("swan", EntityType.Builder.create(SwanEntity::new, SpawnGroup.CREATURE).dimensions(0.6f, 0.9f));
    public static final EntityType<PheasantEntity> PHEASANT = register("pheasant", EntityType.Builder.create(PheasantEntity::new, SpawnGroup.CREATURE).dimensions(0.6f, 0.8f));
    public static final EntityType<SnailEntity> SNAIL = register("snail", EntityType.Builder.create(SnailEntity::new, SpawnGroup.CREATURE).dimensions(0.3f, 0.3f));
    public static final EntityType<DeerEntity> DEER = register("deer", EntityType.Builder.create(DeerEntity::new, SpawnGroup.CREATURE).dimensions(1.3f, 1.8f));

    // Seat
    public static final EntityType<SeatEntity> SEAT_ENTITY = register("seat_entity", EntityType.Builder.create(SeatEntity::new, SpawnGroup.MISC).dimensions(0.1F, 0.1F));

    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> entity, SpawnGroup spawnGroup,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE,
                Identifier.of(MiddleEarth.MOD_ID, name),
                EntityType.Builder.create(entity, spawnGroup).dimensions(width, height).build(keyOf("name")));
    }
    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        return (EntityType)Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
    }

    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }


    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(BARROW_WIGHT, BarrowWightEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(STONE_TROLL, StoneTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(PETRIFIED_TROLL, PetrifiedTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SNOW_TROLL, SnowTrollEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(BROADHOOF_GOAT, BroadhoofGoatEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(WARG, WargEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(MIRKWOOD_SPIDER, MirkwoodSpiderEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(STONE_TROLL, StoneTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(PETRIFIED_TROLL, PetrifiedTrollEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(SNOW_TROLL, SnowTrollEntity.setAttributes());


        // Animals
        FabricDefaultAttributeRegistry.register(SWAN, SwanEntity.createSwanAttributes());
        FabricDefaultAttributeRegistry.register(PHEASANT, PheasantEntity.createPheasantAttributes());
        FabricDefaultAttributeRegistry.register(SNAIL, SnailEntity.createSnailAttributes());
        FabricDefaultAttributeRegistry.register(DEER, DeerEntity.createDeerAttributes());

        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Entities for " + MiddleEarth.MOD_ID);
    }
}
