package net.jukoz.me.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.balrog.BalrogEntity;
import net.jukoz.me.entity.barrow_wights.BarrowWightEntity;
import net.jukoz.me.entity.beasts.trolls.hill.HillTrollEntity;
import net.jukoz.me.entity.crab.CrabEntity;
import net.jukoz.me.entity.deer.DeerEntity;
import net.jukoz.me.entity.deer.DeerModel;
import net.jukoz.me.entity.duck.DuckEntity;
import net.jukoz.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goose.GooseEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.nazguls.NazgulEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.jukoz.me.entity.pheasant.PheasantEntity;
import net.jukoz.me.entity.projectile.boulder.BoulderEntity;
import net.jukoz.me.entity.projectile.pebble.PebbleEntity;
import net.jukoz.me.entity.projectile.spear.SpearEntity;
import net.jukoz.me.entity.snail.SnailEntity;
import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.jukoz.me.entity.swan.SwanEntity;
import net.jukoz.me.entity.beasts.trolls.cave.CaveTrollEntity;
import net.jukoz.me.entity.beasts.trolls.snow.SnowTrollEntity;
import net.jukoz.me.entity.uruks.mordor.MordorUrukEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    // Barrow Wights
    public static final EntityType<BarrowWightEntity> BARROW_WIGHT = registerEntity("barrow_wight", BarrowWightEntity::new, SpawnGroup.CREATURE, 0.9f, 2.1f);

    // Humans
    public static final EntityType<GondorHumanEntity> GONDOR_MAN = registerEntity("gondor_man", GondorHumanEntity::new, SpawnGroup.CREATURE, 0.4f, 1.9f);

    // Dwarfs
    public static final EntityType<DurinDwarfEntity> DURIN_FOLK = registerEntity("durin_folk", DurinDwarfEntity::new, SpawnGroup.CREATURE, 0.4f, 1.9f);

    // Elves
    public static final EntityType<GaladhrimElfEntity> GALADHRIM_ELF = registerEntity("galadhrim_elf", GaladhrimElfEntity::new, SpawnGroup.CREATURE, 0.4f, 1.9f);

    // Hobbits
    public static final EntityType<ShireHobbitEntity> HOBBIT = registerEntity("hobbit", ShireHobbitEntity::new, SpawnGroup.CREATURE, 0.5f, 1.9f);

    // Nazgul
    public static final EntityType<NazgulEntity> NAZGUL = registerEntity("nazgul", NazgulEntity::new, SpawnGroup.CREATURE, 0.55f, 1.9f);

    // Orcs
    public static final EntityType<MordorOrcEntity> MORDOR_ORC = registerEntity("mordor_orc", MordorOrcEntity::new, SpawnGroup.CREATURE, 0.4f, 1.9f);

    // Uruks
    public static final EntityType<MordorUrukEntity> MORDOR_URUK = registerEntity("mordor_uruk", MordorUrukEntity::new, SpawnGroup.CREATURE, 0.4f, 1.9f);

    // Spiders
    public static final EntityType<MirkwoodSpiderEntity> MIRKWOOD_SPIDER = registerEntity("mirkwood_spider", MirkwoodSpiderEntity::new, SpawnGroup.CREATURE, 1.15f, 0.9f);

    // Trolls
    public static final EntityType<CaveTrollEntity> CAVE_TROLL = registerEntity("cave_troll", CaveTrollEntity::new, SpawnGroup.CREATURE, 1.4f, 3.4f);
    public static final EntityType<SnowTrollEntity> SNOW_TROLL = registerEntity("snow_troll", SnowTrollEntity::new, SpawnGroup.CREATURE, 1.4f, 3.4f);
    public static final EntityType<HillTrollEntity> HILL_TROLL = registerEntity("hill_troll", HillTrollEntity::new, SpawnGroup.CREATURE, 1.4f, 3.4f);

    ///* Weapons *///
    public static final EntityType<PebbleEntity> PEBBLE = registerEntity("pebble", PebbleEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);
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


    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> entity, SpawnGroup spawnGroup,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE,
                new Identifier(MiddleEarth.MOD_ID, name),FabricEntityTypeBuilder.create(spawnGroup, entity).dimensions(EntityDimensions.fixed(width, height)).build());
    }

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(BARROW_WIGHT, BarrowWightEntity.setAttributes());
        //FabricDefaultAttributeRegistry.register(BALROG, BalrogEntity.createBalrogAttributes());
        FabricDefaultAttributeRegistry.register(CAVE_TROLL, CaveTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(GONDOR_MAN, GondorHumanEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(HILL_TROLL, HillTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SNOW_TROLL, SnowTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(DURIN_FOLK, DurinDwarfEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(HOBBIT, ShireHobbitEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(GALADHRIM_ELF, GaladhrimElfEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(NAZGUL, NazgulEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(MORDOR_ORC, MordorOrcEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(MORDOR_URUK, MordorUrukEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(MIRKWOOD_SPIDER, MirkwoodSpiderEntity.setAttributes());
        // Animals
        FabricDefaultAttributeRegistry.register(CRAB, CrabEntity.createCrabAttributes());
        FabricDefaultAttributeRegistry.register(GOOSE, GooseEntity.createGooseAttributes());
        FabricDefaultAttributeRegistry.register(DUCK, DuckEntity.createDuckAttributes());
        FabricDefaultAttributeRegistry.register(SWAN, SwanEntity.createSwanAttributes());
        FabricDefaultAttributeRegistry.register(PHEASANT, PheasantEntity.createPheasantAttributes());
        FabricDefaultAttributeRegistry.register(SNAIL, SnailEntity.createSnailAttributes());
        FabricDefaultAttributeRegistry.register(DEER, DeerEntity.createDeerAttributes());

        MiddleEarth.LOGGER.debug("Registering Mod Entities for " + MiddleEarth.MOD_ID);
    }
}
