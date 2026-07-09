package net.sevenstars.middleearth.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.*;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.fire_of_orthanc.FireOfOrthancEntity;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.entity.barrel.BarrelEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.TrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.petrified.PetrifiedTrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.snow.SnowTrollEntity;
import net.sevenstars.middleearth.entity.beasts.trolls.stone.StoneTrollEntity;
import net.sevenstars.middleearth.entity.beasts.warg.WargEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.projectile.WebbedEntity;
import net.sevenstars.middleearth.entity.projectile.boulder.BoulderEntity;
import net.sevenstars.middleearth.entity.projectile.pebble.PebbleEntity;
import net.sevenstars.middleearth.entity.projectile.pinecone.LitPineconeEntity;
import net.sevenstars.middleearth.entity.projectile.pinecone.PineconeEntity;
import net.sevenstars.middleearth.entity.projectile.smoke.SmokeRingProjectileEntity;
import net.sevenstars.middleearth.entity.projectile.spear.SpearEntity;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.entity.spider.larva.ShelobiteLarvaEntity;
import net.sevenstars.middleearth.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.sevenstars.middleearth.entity.spider.spawn.SpawnOfShelobEntity;
import net.sevenstars.middleearth.item.ResourceItemsME;
import net.sevenstars.middleearth.registries.RegistryAliasesME;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;

import java.util.function.Supplier;
public class EntitiesME {
    // Npc
    public static final EntityType<NpcEntity> NPC = register("npc", EntityType.Builder.create(NpcEntity::new, SpawnGroup.MONSTER).dimensions(0.8f, 1.8f));

    // Mounts
    public static final EntityType<WargEntity> WARG = register("warg", EntityType.Builder.create(WargEntity::new, SpawnGroup.CREATURE).dimensions(1.4f, 1.4f));
    public static final EntityType<BroadhoofGoatEntity> BROADHOOF_GOAT = register("broadhoof_goat", EntityType.Builder.create(BroadhoofGoatEntity::new, SpawnGroup.CREATURE).dimensions(1.4f, 1.4f));
    public static final EntityType<GreatHornEntity> GREAT_HORN = register("great_horn", EntityType.Builder.create(GreatHornEntity::new, SpawnGroup.CREATURE).dimensions(1.3f, 1.8f));

    public static final EntityType<BarrelEntity> REINFORCED_BARREL;

    // Spiders
    public static final EntityType<ShelobiteLarvaEntity> SHELOBITE_LARVA = register("shelobite_larva",
            EntityType.Builder.create(ShelobiteLarvaEntity::new, SpawnGroup.MONSTER).dimensions(0.4f, 0.3f));
    public static final EntityType<ShelobiteScuttlerEntity> SHELOBITE_SCUTTLER = register("shelobite_scuttler",
            EntityType.Builder.create(ShelobiteScuttlerEntity::new, SpawnGroup.MONSTER).dimensions(1.15f, 0.9f));
    public static final EntityType<SpawnOfShelobEntity> SPAWN_OF_SHELOB = register("spawn_of_shelob",
            EntityType.Builder.create(SpawnOfShelobEntity::new, SpawnGroup.MONSTER).dimensions(1.75f, 1.4f));

    // Trolls
    public static final EntityType<SnowTrollEntity> SNOW_TROLL = register("snow_troll",
            EntityType.Builder.create(SnowTrollEntity::new, SpawnGroup.MONSTER).dimensions(2.2f, 2.5f));
    public static final EntityType<CaveTrollEntity> CAVE_TROLL = register("cave_troll",
            EntityType.Builder.create(CaveTrollEntity::new, SpawnGroup.MONSTER)
                    .dimensions(2.2f, 3.5f)
                    .passengerAttachments(new Vec3d(0, 3.825, -0.65), new Vec3d(-0.8, 3.4, -1.2), new Vec3d(0.8, 3.4, -1.2)));
    public static final EntityType<StoneTrollEntity> STONE_TROLL = register("stone_troll",
            EntityType.Builder.create(StoneTrollEntity::new, SpawnGroup.MONSTER).dimensions(1.4f, 3.4f));
    public static final EntityType<PetrifiedTrollEntity> PETRIFIED_TROLL = register("petrified_troll",
            EntityType.Builder.create(PetrifiedTrollEntity::new, SpawnGroup.MONSTER).dimensions(1.4f, 3.4f));

    ///* Weapons *///
    public static final EntityType<FireOfOrthancEntity> FIRE_OF_ORTHANC = registerEntity("fire_of_orthanc", FireOfOrthancEntity::new, SpawnGroup.MISC, 0.65F, 0.65F);
    public static final EntityType<PebbleEntity> PEBBLE = registerEntity("pebble", PebbleEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<PineconeEntity> PINECONE = registerEntity("pinecone", PineconeEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<LitPineconeEntity> LIT_PINECONE = registerEntity("lit_pinecone", LitPineconeEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<BoulderEntity> BOULDER = registerEntity("boulder", BoulderEntity::new, SpawnGroup.MISC, 1f, 1f);
    public static final EntityType<WebbedEntity> WEB = registerEntity("web", WebbedEntity::new, SpawnGroup.MISC, 0.5F, 0.5F);

    /// * Projectiles *///
    public static final EntityType<SmokeRingProjectileEntity> SMOKE_RING_PROJECTILE = registerEntity("smoke_ring_projectile", SmokeRingProjectileEntity::new, SpawnGroup.MISC, 0.5F, 0.5F);

    public static final EntityType<SpearEntity> SPEAR = registerEntity("spear", SpearEntity::new, SpawnGroup.MISC, 1f, 1f);

    // Seat
    public static final EntityType<SeatEntity> SEAT_ENTITY = register("seat_entity", EntityType.Builder.create(SeatEntity::new, SpawnGroup.MISC).dimensions(0.1F, 0.1F));


    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> entity, SpawnGroup spawnGroup,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE, MiddleEarth.of(name), EntityType.Builder.create(entity, spawnGroup).dimensions(width, height).build(keyOf("name")));
    }

    private static <T extends Entity> EntityType<T> register(RegistryKey<EntityType<?>> key, EntityType.Builder<T> type) {
        EntityType<T> entityType = Registry.register(Registries.ENTITY_TYPE, key, type.build(key));
        TranslationEntries.entityEntries.add(entityType);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ENTITY_TYPE, entityType.getUntranslatedName()));
        return entityType;
    }

    private static EntityType.EntityFactory<BarrelEntity> getBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new BarrelEntity(type, world, itemSupplier);
    }

    private static RegistryKey<EntityType<?>> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(MiddleEarth.MOD_ID, id));
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        return register(keyOf(id), type);
    }


    public static void registerModEntities() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Entities for " + MiddleEarth.MOD_ID);
        SpawnRestriction.register(SHELOBITE_LARVA, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(SHELOBITE_SCUTTLER, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
        SpawnRestriction.register(SPAWN_OF_SHELOB, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

        SpawnRestriction.register(CAVE_TROLL, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CaveTrollEntity::canSpawn);

        FabricDefaultAttributeRegistry.register(STONE_TROLL, StoneTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(PETRIFIED_TROLL, PetrifiedTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SNOW_TROLL, SnowTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(CAVE_TROLL, CaveTrollEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(BROADHOOF_GOAT, BroadhoofGoatEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(GREAT_HORN, GreatHornEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(WARG, WargEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(SHELOBITE_LARVA, ShelobiteLarvaEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SHELOBITE_SCUTTLER, ShelobiteScuttlerEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SPAWN_OF_SHELOB, SpawnOfShelobEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(STONE_TROLL, StoneTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(PETRIFIED_TROLL, PetrifiedTrollEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(SNOW_TROLL, SnowTrollEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(NPC, NpcEntity.setAttributes());
        SpawnRestriction.register(NPC, SpawnLocationTypes.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, NpcEntity::canSpawn);
    }

    static {
        REINFORCED_BARREL = register("reinforced_barrel", EntityType.Builder.create(getBoatFactory(() -> {
            return ResourceItemsME.REINFORCED_BARREL;
        }), SpawnGroup.MISC).dropsNothing().dimensions(1.2F, 1.2F).eyeHeight(0.625F).maxTrackingRange(10));

    }
}
