package net.jukoz.me.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.barrow_wights.BarrowWightEntity;
import net.jukoz.me.entity.beasts.trolls.petrified.PetrifiedTrollEntity;
import net.jukoz.me.entity.beasts.trolls.stone.StoneTrollEntity;
import net.jukoz.me.entity.crab.CrabEntity;
import net.jukoz.me.entity.deer.DeerEntity;
import net.jukoz.me.entity.duck.DuckEntity;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goose.GooseEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.misties.MistyGoblinEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.pheasant.PheasantEntity;
import net.jukoz.me.entity.projectile.boulder.BoulderEntity;
import net.jukoz.me.entity.projectile.pinecone.LitPineconeEntity;
import net.jukoz.me.entity.projectile.pebble.PebbleEntity;
import net.jukoz.me.entity.projectile.pinecone.PineconeEntity;
import net.jukoz.me.entity.projectile.spear.SpearEntity;
import net.jukoz.me.entity.seat.SeatEntity;
import net.jukoz.me.entity.snail.SnailEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.swan.SwanEntity;
import net.jukoz.me.entity.beasts.trolls.cave.CaveTrollEntity;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollEntity;
import net.jukoz.me.entity.uruks.misties.MistyHobgoblinEntity;
import net.jukoz.me.entity.uruks.mordor.MordorBlackUrukEntity;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    //TODO Npc weird animation/item holding -> model fucked

    // Barrow Wights
    public static final EntityType<BarrowWightEntity> BARROW_WIGHT = registerEntity("barrow_wight", BarrowWightEntity::new, SpawnGroup.CREATURE, 0.9f, 2.1f);

    // Hobbits
    public static final EntityType<ShireHobbitEntity> HOBBIT_CIVILIAN = registerEntity("hobbit_civilian", ShireHobbitEntity::new, SpawnGroup.CREATURE, 0.6f, 1.9f);
    public static final EntityType<ShireHobbitEntity> HOBBIT_BOUNDER = registerEntity("hobbit_bounder", ShireHobbitEntity::new, SpawnGroup.CREATURE, 0.6f, 1.9f);
    public static final EntityType<ShireHobbitEntity> HOBBIT_SHIRRIFF = registerEntity("hobbit_shirriff", ShireHobbitEntity::new, SpawnGroup.CREATURE, 0.6f, 1.9f);

    //HUMANS
    //Gondor
    public static final EntityType<GondorHumanEntity> GONDORIAN_MILITIA = registerEntity("gondorian_militia", GondorHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<GondorHumanEntity> GONDORIAN_SOLDIER = registerEntity("gondorian_soldier", GondorHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<GondorHumanEntity> GONDORIAN_KNIGHT = registerEntity("gondorian_knight", GondorHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<GondorHumanEntity> GONDORIAN_VETERAN = registerEntity("gondorian_veteran", GondorHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<GondorHumanEntity> GONDORIAN_LEADER = registerEntity("gondorian_leader", GondorHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);

    //Rohan
    public static final EntityType<RohanHumanEntity> ROHIRRIM_MILITIA = registerEntity("rohirrim_militia", RohanHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<RohanHumanEntity> ROHIRRIM_SOLDIER = registerEntity("rohirrim_soldier", RohanHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<RohanHumanEntity> ROHIRRIM_KNIGHT = registerEntity("rohirrim_knight", RohanHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<RohanHumanEntity> ROHIRRIM_VETERAN = registerEntity("rohirrim_veteran", RohanHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<RohanHumanEntity> ROHIRRIM_LEADER = registerEntity("rohirrim_leader", RohanHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);

    //Bandits
    public static final EntityType<BanditHumanEntity> BANDIT_MILITIA = registerEntity("bandit_militia", BanditHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<BanditHumanEntity> BANDIT_SOLDIER = registerEntity("bandit_soldier", BanditHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);
    public static final EntityType<BanditHumanEntity> BANDIT_CHIEFTAIN = registerEntity("bandit_chieftain", BanditHumanEntity::new, SpawnGroup.CREATURE, 0.7F, 1.9F);

    // Dwarfs
    public static final EntityType<LongbeardDwarfEntity> LONGBEARD_MILITIA = registerEntity("longbeard_militia", LongbeardDwarfEntity::new, SpawnGroup.CREATURE, 0.8f, 1.9f);
    public static final EntityType<LongbeardDwarfEntity> LONGBEARD_SOLDIER = registerEntity("longbeard_soldier", LongbeardDwarfEntity::new, SpawnGroup.CREATURE, 0.8f, 1.9f);
    public static final EntityType<LongbeardDwarfEntity> LONGBEARD_ELITE = registerEntity("longbeard_elite", LongbeardDwarfEntity::new, SpawnGroup.CREATURE, 0.8f, 1.9f);
    public static final EntityType<LongbeardDwarfEntity> LONGBEARD_VETERAN = registerEntity("longbeard_veteran", LongbeardDwarfEntity::new, SpawnGroup.CREATURE, 0.8f, 1.9f);
    public static final EntityType<LongbeardDwarfEntity> LONGBEARD_LEADER = registerEntity("longbeard_leader", LongbeardDwarfEntity::new, SpawnGroup.CREATURE, 0.8f, 1.9f);

    // Elves
    public static final EntityType<GaladhrimElfEntity> LORIEN_MILITIA = registerEntity("lorien_militia", GaladhrimElfEntity::new, SpawnGroup.CREATURE, 0.75f, 1.9f);
    public static final EntityType<GaladhrimElfEntity> LORIEN_SOLDIER = registerEntity("lorien_soldier", GaladhrimElfEntity::new, SpawnGroup.CREATURE, 0.75f, 1.9f);
    public static final EntityType<GaladhrimElfEntity> LORIEN_KNIGHT = registerEntity("lorien_knight", GaladhrimElfEntity::new, SpawnGroup.CREATURE, 0.75f, 1.9f);
    public static final EntityType<GaladhrimElfEntity> LORIEN_VETERAN = registerEntity("lorien_veteran", GaladhrimElfEntity::new, SpawnGroup.CREATURE, 0.75f, 1.9f);
    public static final EntityType<GaladhrimElfEntity> LORIEN_LEADER = registerEntity("lorien_leader", GaladhrimElfEntity::new, SpawnGroup.CREATURE, 0.75f, 1.9f);

    //ORCS
    //Mordor
    public static final EntityType<MordorOrcEntity> MORDOR_ORC_SNAGA = registerEntity("mordor_orc_snaga", MordorOrcEntity::new, SpawnGroup.CREATURE, 0.7f, 1.9f);
    public static final EntityType<MordorOrcEntity> MORDOR_ORC_SOLDIER = registerEntity("mordor_orc_soldier", MordorOrcEntity::new, SpawnGroup.CREATURE, 0.7f, 1.9f);
    //Misties
    public static final EntityType<MistyGoblinEntity> MISTY_GOBLIN_SNAGA = registerEntity("misty_goblin_snaga", MistyGoblinEntity::new, SpawnGroup.CREATURE, 0.7f, 1.9f);
    public static final EntityType<MistyGoblinEntity> MISTY_GOBLIN_WARRIOR = registerEntity("misty_goblin_warrior", MistyGoblinEntity::new, SpawnGroup.CREATURE, 0.7f, 1.9f);

    //URUKS
    //Mordor
    public static final EntityType<MordorBlackUrukEntity> MORDOR_BLACK_URUK_SOLDIER = registerEntity("mordor_black_uruk_soldier", MordorBlackUrukEntity::new, SpawnGroup.CREATURE, 0.85f, 1.9f);
    public static final EntityType<MordorBlackUrukEntity> MORDOR_BLACK_URUK_VETERAN = registerEntity("mordor_black_uruk_veteran", MordorBlackUrukEntity::new, SpawnGroup.CREATURE, 0.85f, 1.9f);
    public static final EntityType<MordorBlackUrukEntity> MORDOR_BLACK_URUK_LEADER = registerEntity("mordor_black_uruk_leader", MordorBlackUrukEntity::new, SpawnGroup.CREATURE, 0.85f, 1.9f);
    //Misties
    public static final EntityType<MistyHobgoblinEntity> MISTY_HOBGOBLIN_SOLDIER = registerEntity("misty_hobgoblin_soldier", MistyHobgoblinEntity::new, SpawnGroup.CREATURE, 0.85f, 1.9f);
    public static final EntityType<MistyHobgoblinEntity> MISTY_HOBGOBLIN_VETERAN = registerEntity("misty_hobgoblin_veteran", MistyHobgoblinEntity::new, SpawnGroup.CREATURE, 0.85f, 1.9f);
    public static final EntityType<MistyHobgoblinEntity> MISTY_HOBGOBLIN_LEADER = registerEntity("misty_hobgoblin_leader", MistyHobgoblinEntity::new, SpawnGroup.CREATURE, 0.85f, 1.9f);

    // Nazgul
    public static final EntityType<NazgulEntity> NAZGUL = registerEntity("nazgul", NazgulEntity::new, SpawnGroup.CREATURE, 0.8f, 1.9f);

    // Spiders
    public static final EntityType<MirkwoodSpiderEntity> MIRKWOOD_SPIDER = registerEntity("mirkwood_spider", MirkwoodSpiderEntity::new, SpawnGroup.CREATURE, 1.15f, 0.9f);

    // Trolls
    public static final EntityType<CaveTrollEntity> CAVE_TROLL = registerEntity("cave_troll", CaveTrollEntity::new, SpawnGroup.CREATURE, 1.4f, 3.4f);
    public static final EntityType<SnowTrollEntity> SNOW_TROLL = registerEntity("snow_troll", SnowTrollEntity::new, SpawnGroup.CREATURE, 2.2f, 2.5f);
    public static final EntityType<StoneTrollEntity> STONE_TROLL = registerEntity("stone_troll", StoneTrollEntity::new, SpawnGroup.CREATURE, 1.4f, 3.4f);
    public static final EntityType<PetrifiedTrollEntity> PETRIFIED_TROLL = registerEntity("petrified_troll", PetrifiedTrollEntity::new, SpawnGroup.CREATURE, 1.4f, 3.4f);

    ///* Weapons *///
    public static final EntityType<PebbleEntity> PEBBLE = registerEntity("pebble", PebbleEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<PineconeEntity> PINECONE = registerEntity("pinecone", PineconeEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<LitPineconeEntity> LIT_PINECONE = registerEntity("lit_pinecone", LitPineconeEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
    public static final EntityType<BoulderEntity> BOULDER = registerEntity("boulder", BoulderEntity::new, SpawnGroup.MISC, 1f, 1f);
    public static final EntityType<SpearEntity> SPEAR = registerEntity("spear", SpearEntity::new, SpawnGroup.MISC, 0.5F, 0.5F);

    ///* Animals *///
    public static final EntityType<CrabEntity> CRAB = registerEntity("crab", CrabEntity::new, SpawnGroup.CREATURE, 0.4f, 0.4f);
    public static final EntityType<GooseEntity> GOOSE = registerEntity("goose", GooseEntity::new, SpawnGroup.CREATURE, 0.6f, 1.25f);
    public static final EntityType<DuckEntity> DUCK = registerEntity("duck", DuckEntity::new, SpawnGroup.CREATURE, 0.6f, 0.8f);
    public static final EntityType<SwanEntity> SWAN = registerEntity("swan", SwanEntity::new, SpawnGroup.CREATURE, 0.6f, 0.9f);
    public static final EntityType<PheasantEntity> PHEASANT = registerEntity("pheasant", PheasantEntity::new, SpawnGroup.CREATURE, 0.6f, 0.8f);
    public static final EntityType<SnailEntity> SNAIL = registerEntity("snail", SnailEntity::new, SpawnGroup.CREATURE, 0.3f, 0.3f);
    public static final EntityType<DeerEntity> DEER = registerEntity("deer", DeerEntity::new, SpawnGroup.CREATURE, 1.3f, 1.8f);

    // Seat
    public static final EntityType<SeatEntity> SEAT_ENTITY = registerEntity("seat_entity", SeatEntity::new, SpawnGroup.MISC, 0.1F, 0.1F);

    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> entity, SpawnGroup spawnGroup,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE,
                new Identifier(MiddleEarth.MOD_ID, name),FabricEntityTypeBuilder.create(spawnGroup, entity).dimensions(EntityDimensions.fixed(width, height)).build());
    }

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(BARROW_WIGHT, BarrowWightEntity.setAttributes());
        //FabricDefaultAttributeRegistry.register(BALROG, BalrogEntity.createBalrogAttributes());
        FabricDefaultAttributeRegistry.register(CAVE_TROLL, CaveTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(STONE_TROLL, StoneTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(PETRIFIED_TROLL, PetrifiedTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SNOW_TROLL, SnowTrollEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(HOBBIT_CIVILIAN, ShireHobbitEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(HOBBIT_BOUNDER, ShireHobbitEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(HOBBIT_SHIRRIFF, ShireHobbitEntity.setKnightAttributes());
        FabricDefaultAttributeRegistry.register(GONDORIAN_MILITIA, GondorHumanEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(GONDORIAN_SOLDIER, GondorHumanEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(GONDORIAN_KNIGHT, GondorHumanEntity.setKnightAttributes());
        FabricDefaultAttributeRegistry.register(GONDORIAN_VETERAN, GondorHumanEntity.setVeteranAttributes());
        FabricDefaultAttributeRegistry.register(GONDORIAN_LEADER, GondorHumanEntity.setLeaderAttributes());
        
        FabricDefaultAttributeRegistry.register(ROHIRRIM_MILITIA, RohanHumanEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(ROHIRRIM_SOLDIER, RohanHumanEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(ROHIRRIM_KNIGHT, RohanHumanEntity.setKnightAttributes());
        FabricDefaultAttributeRegistry.register(ROHIRRIM_VETERAN, RohanHumanEntity.setVeteranAttributes());
        FabricDefaultAttributeRegistry.register(ROHIRRIM_LEADER, RohanHumanEntity.setLeaderAttributes());

        FabricDefaultAttributeRegistry.register(LONGBEARD_MILITIA, LongbeardDwarfEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(LONGBEARD_SOLDIER, LongbeardDwarfEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(LONGBEARD_ELITE, LongbeardDwarfEntity.setKnightAttributes());
        FabricDefaultAttributeRegistry.register(LONGBEARD_VETERAN, LongbeardDwarfEntity.setVeteranAttributes());
        FabricDefaultAttributeRegistry.register(LONGBEARD_LEADER, LongbeardDwarfEntity.setLeaderAttributes());

        FabricDefaultAttributeRegistry.register(LORIEN_MILITIA, GaladhrimElfEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(LORIEN_SOLDIER, GaladhrimElfEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(LORIEN_KNIGHT, GaladhrimElfEntity.setKnightAttributes());
        FabricDefaultAttributeRegistry.register(LORIEN_VETERAN, GaladhrimElfEntity.setVeteranAttributes());
        FabricDefaultAttributeRegistry.register(LORIEN_LEADER, GaladhrimElfEntity.setLeaderAttributes());
        
        FabricDefaultAttributeRegistry.register(MORDOR_ORC_SNAGA, MordorOrcEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(MORDOR_ORC_SOLDIER, MordorOrcEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(MORDOR_BLACK_URUK_SOLDIER, MordorBlackUrukEntity.setKnightAttributes());
        FabricDefaultAttributeRegistry.register(MORDOR_BLACK_URUK_VETERAN, MordorBlackUrukEntity.setVeteranAttributes());
        FabricDefaultAttributeRegistry.register(MORDOR_BLACK_URUK_LEADER, MordorBlackUrukEntity.setLeaderAttributes());

        FabricDefaultAttributeRegistry.register(MISTY_GOBLIN_SNAGA, MistyGoblinEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(MISTY_GOBLIN_WARRIOR, MistyGoblinEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(MISTY_HOBGOBLIN_SOLDIER, MordorBlackUrukEntity.setKnightAttributes());
        FabricDefaultAttributeRegistry.register(MISTY_HOBGOBLIN_VETERAN, MordorBlackUrukEntity.setVeteranAttributes());
        FabricDefaultAttributeRegistry.register(MISTY_HOBGOBLIN_LEADER, MordorBlackUrukEntity.setLeaderAttributes());

        FabricDefaultAttributeRegistry.register(NAZGUL, NazgulEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(MIRKWOOD_SPIDER, MirkwoodSpiderEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(STONE_TROLL, StoneTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(PETRIFIED_TROLL, PetrifiedTrollEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(SNOW_TROLL, SnowTrollEntity.setAttributes());

        FabricDefaultAttributeRegistry.register(BANDIT_MILITIA, BanditHumanEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(BANDIT_SOLDIER, BanditHumanEntity.setSoldierAttributes());
        FabricDefaultAttributeRegistry.register(BANDIT_CHIEFTAIN, BanditHumanEntity.setKnightAttributes());
        // Animals
        FabricDefaultAttributeRegistry.register(CRAB, CrabEntity.createCrabAttributes());
        FabricDefaultAttributeRegistry.register(GOOSE, GooseEntity.createGooseAttributes());
        FabricDefaultAttributeRegistry.register(DUCK, DuckEntity.createDuckAttributes());
        FabricDefaultAttributeRegistry.register(SWAN, SwanEntity.createSwanAttributes());
        FabricDefaultAttributeRegistry.register(PHEASANT, PheasantEntity.createPheasantAttributes());
        FabricDefaultAttributeRegistry.register(SNAIL, SnailEntity.createSnailAttributes());
        FabricDefaultAttributeRegistry.register(DEER, DeerEntity.createDeerAttributes());

        LoggerUtil.getInstance().logDebugMsg("Registering Mod Entities for " + MiddleEarth.MOD_ID);
    }
}
