package net.sevenstars.middleearth.entity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.sevenstars.api.registries.RegistrationBridge;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementType;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.fire_of_orthanc.FireOfOrthancEntity;
import net.sevenstars.middleearth.datageneration.content.TranslationEntries;
import net.sevenstars.middleearth.entity.barrel.BarrelEntity;
import net.sevenstars.middleearth.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.entity.beasts.great_horn.GreatHornEntity;
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
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;

import java.util.function.Supplier;
public class EntitiesME {
    // Npc
    public static final EntityType<NpcEntity> NPC = register("npc", EntityType.Builder.of(NpcEntity::new, MobCategory.MONSTER).sized(0.8f, 1.8f));

    // Mounts
    public static final EntityType<WargEntity> WARG = register("warg", EntityType.Builder.of(WargEntity::new, MobCategory.CREATURE).sized(1.4f, 1.4f));
    public static final EntityType<BroadhoofGoatEntity> BROADHOOF_GOAT = register("broadhoof_goat", EntityType.Builder.of(BroadhoofGoatEntity::new, MobCategory.CREATURE).sized(1.4f, 1.4f));
    public static final EntityType<GreatHornEntity> GREAT_HORN = register("great_horn", EntityType.Builder.of(GreatHornEntity::new, MobCategory.CREATURE).sized(1.3f, 1.8f));

    public static final EntityType<BarrelEntity> REINFORCED_BARREL;

    // Spiders
    public static final EntityType<ShelobiteLarvaEntity> SHELOBITE_LARVA = register("shelobite_larva",
            EntityType.Builder.of(ShelobiteLarvaEntity::new, MobCategory.MONSTER).sized(0.4f, 0.3f));
    public static final EntityType<ShelobiteScuttlerEntity> SHELOBITE_SCUTTLER = register("shelobite_scuttler",
            EntityType.Builder.of(ShelobiteScuttlerEntity::new, MobCategory.MONSTER).sized(1.15f, 0.9f));
    public static final EntityType<SpawnOfShelobEntity> SPAWN_OF_SHELOB = register("spawn_of_shelob",
            EntityType.Builder.of(SpawnOfShelobEntity::new, MobCategory.MONSTER).sized(1.75f, 1.4f));

    // Trolls
    public static final EntityType<SnowTrollEntity> SNOW_TROLL = register("snow_troll",
            EntityType.Builder.of(SnowTrollEntity::new, MobCategory.CREATURE).sized(2.2f, 2.5f));
    public static final EntityType<CaveTrollEntity> CAVE_TROLL = register("cave_troll", EntityType.Builder.of(CaveTrollEntity::new, MobCategory.MONSTER).sized(2.2f, 3.5f)
                    .passengerAttachments(new Vec3(0, 3.825, -0.65), new Vec3(-0.8, 3.4, -1.2), new Vec3(0.8, 3.4, -1.2)));

    public static final EntityType<StoneTrollEntity> STONE_TROLL = register("stone_troll",
            EntityType.Builder.of(StoneTrollEntity::new, MobCategory.MONSTER).sized(1.4f, 3.4f));
    public static final EntityType<PetrifiedTrollEntity> PETRIFIED_TROLL = register("petrified_troll",
            EntityType.Builder.of(PetrifiedTrollEntity::new, MobCategory.CREATURE).sized(1.4f, 3.4f));

    ///* Weapons *///
    public static final EntityType<FireOfOrthancEntity> FIRE_OF_ORTHANC = registerEntity("fire_of_orthanc", FireOfOrthancEntity::new, MobCategory.MISC, 0.65F, 0.65F);
    public static final EntityType<PebbleEntity> PEBBLE = registerEntity("pebble", PebbleEntity::new, MobCategory.MISC, 0.25F, 0.25F);
    public static final EntityType<PineconeEntity> PINECONE = registerEntity("pinecone", PineconeEntity::new, MobCategory.MISC, 0.25F, 0.25F);
    public static final EntityType<LitPineconeEntity> LIT_PINECONE = registerEntity("lit_pinecone", LitPineconeEntity::new, MobCategory.MISC, 0.25F, 0.25F);
    public static final EntityType<BoulderEntity> BOULDER = registerEntity("boulder", BoulderEntity::new, MobCategory.MISC, 1f, 1f);
    public static final EntityType<WebbedEntity> WEB = registerEntity("web", WebbedEntity::new, MobCategory.MISC, 0.5F, 0.5F);

    /// * Projectiles *///
    public static final EntityType<SmokeRingProjectileEntity> SMOKE_RING_PROJECTILE = registerEntity("smoke_ring_projectile", SmokeRingProjectileEntity::new, MobCategory.MISC, 0.5F, 0.5F);

    public static final EntityType<SpearEntity> SPEAR = registerEntity("spear", SpearEntity::new, MobCategory.MISC, 1f, 1f);

    // Seat
    public static final EntityType<SeatEntity> SEAT_ENTITY = register("seat_entity", EntityType.Builder.of(SeatEntity::new, MobCategory.MISC).sized(0.1F, 0.1F));


    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> entity, MobCategory spawnGroup,
                                                                  float width, float height) {
        return register(name, EntityType.Builder.of(entity, spawnGroup).sized(width, height));
    }

    private static <T extends Entity> EntityType<T> register(String id, EntityType.Builder<T> type) {
        EntityType<T> entityType = RegistrationBridge.register(
                BuiltInRegistries.ENTITY_TYPE, MiddleEarth.of(id), type.build(id));
        TranslationEntries.entityEntries.add(entityType);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(BuiltInRegistries.ENTITY_TYPE, id));
        return entityType;
    }

    private static EntityType.EntityFactory<BarrelEntity> getBoatFactory(Supplier<Item> itemSupplier) {
        return (type, world) -> new BarrelEntity(type, world, itemSupplier);
    }

    public static void registerModEntities() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Entities for " + MiddleEarth.MOD_ID);
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(STONE_TROLL, StoneTrollEntity.setAttributes().build());
        event.put(PETRIFIED_TROLL, PetrifiedTrollEntity.setAttributes().build());
        event.put(SNOW_TROLL, SnowTrollEntity.setAttributes().build());
        event.put(CAVE_TROLL, CaveTrollEntity.setAttributes().build());
        event.put(BROADHOOF_GOAT, BroadhoofGoatEntity.setAttributes().build());
        event.put(GREAT_HORN, GreatHornEntity.setAttributes().build());
        event.put(WARG, WargEntity.setAttributes().build());
        event.put(SHELOBITE_LARVA, ShelobiteLarvaEntity.setAttributes().build());
        event.put(SHELOBITE_SCUTTLER, ShelobiteScuttlerEntity.setAttributes().build());
        event.put(SPAWN_OF_SHELOB, SpawnOfShelobEntity.setAttributes().build());
        event.put(NPC, NpcEntity.setAttributes().build());
    }

    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        SpawnPlacementType onGround = SpawnPlacementTypes.ON_GROUND;
        Heightmap.Types heightmap = Heightmap.Types.MOTION_BLOCKING_NO_LEAVES;
        event.register(WARG, onGround, heightmap, Animal::checkAnimalSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(BROADHOOF_GOAT, onGround, heightmap, Animal::checkAnimalSpawnRules,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(GREAT_HORN, onGround, heightmap, GreatHornEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(STONE_TROLL, onGround, heightmap, StoneTrollEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(SHELOBITE_LARVA, onGround, heightmap, ShelobiteLarvaEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(SHELOBITE_SCUTTLER, onGround, heightmap, ShelobiteScuttlerEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(SPAWN_OF_SHELOB, onGround, heightmap, SpawnOfShelobEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(CAVE_TROLL, onGround, heightmap, CaveTrollEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);
        event.register(NPC, onGround, heightmap, NpcEntity::canSpawn,
                RegisterSpawnPlacementsEvent.Operation.REPLACE);

        if (shouldRegisterVanillaCamelSpawnPlacement()) {
            event.register(
                    EntityType.CAMEL,
                    SpawnPlacementTypes.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    Animal::checkAnimalSpawnRules,
                    RegisterSpawnPlacementsEvent.Operation.REPLACE
            );
        } else {
            MiddleEarth.LOGGER.logDebugMsg(
                    "Skipping vanilla camel spawn placement because a compatibility provider is loaded."
            );
        }
    }

    private static boolean shouldRegisterVanillaCamelSpawnPlacement() {
        return !ModList.get().isLoaded("vanillabackport")
                && !ModList.get().isLoaded("platform");
    }

    static {
        REINFORCED_BARREL = register("reinforced_barrel", EntityType.Builder.of(getBoatFactory(() -> {
            return ResourceItemsME.REINFORCED_BARREL;
        }), MobCategory.MISC).sized(1.2F, 1.2F).eyeHeight(0.625F).clientTrackingRange(10));

    }
}
