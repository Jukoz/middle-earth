package net.jesteur.me.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.crab.CrabEntity;
import net.jesteur.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jesteur.me.entity.hobbits.HobbitEntity;
import net.jesteur.me.entity.orcs.mordor.MordorOrcEntity;
import net.jesteur.me.entity.pebble.PebbleEntity;
import net.jesteur.me.entity.trolls.cave.CaveTrollEntity;
import net.jesteur.me.entity.trolls.snow.SnowTrollEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    // Dwarfs
    public static final EntityType<DurinDwarfEntity> DURIN_FOLK = registerEntity("durin_folk", DurinDwarfEntity::new, SpawnGroup.CREATURE, 0.4f, 1.9f);

    // Elves
    public static final EntityType<GaladhrimElfEntity> GALADHRIM_ELF = registerEntity("galadhrim_elf", GaladhrimElfEntity::new, SpawnGroup.CREATURE, 0.4f, 1.9f);

    // Hobbits
    public static final EntityType<HobbitEntity> HOBBIT = registerEntity("hobbit", HobbitEntity::new, SpawnGroup.CREATURE, 0.5f, 1.9f);

    // Orcs
    public static final EntityType<MordorOrcEntity> MORDOR_ORC = registerEntity("mordor_orc", MordorOrcEntity::new, SpawnGroup.CREATURE, 0.4f, 1.9f);

    // Trolls
    public static final EntityType<CaveTrollEntity> CAVE_TROLL = registerEntity("cave_troll", CaveTrollEntity::new, SpawnGroup.CREATURE, 1.4f, 3.4f);
    public static final EntityType<SnowTrollEntity> SNOW_TROLL = registerEntity("snow_troll", SnowTrollEntity::new, SpawnGroup.CREATURE, 1.4f, 3.4f);


    ///* Weapons *///
    public static final EntityType<PebbleEntity> PEBBLE = registerEntity("pebble", PebbleEntity::new, SpawnGroup.MISC, 0.25F, 0.25F);

    ///* Animals *///
    public static final EntityType<CrabEntity> CRAB = registerEntity("crab", CrabEntity::new, SpawnGroup.CREATURE, 0.4f, 0.4f);


    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> entity, SpawnGroup spawnGroup,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE,
                new Identifier(MiddleEarth.MOD_ID, name),FabricEntityTypeBuilder.create(spawnGroup, entity).dimensions(EntityDimensions.fixed(width, height)).build());
    }

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(CAVE_TROLL, CaveTrollEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(DURIN_FOLK, DurinDwarfEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(HOBBIT, HobbitEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(GALADHRIM_ELF, GaladhrimElfEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(MORDOR_ORC, MordorOrcEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(SNOW_TROLL, SnowTrollEntity.setAttributes());
        // Animals
        FabricDefaultAttributeRegistry.register(CRAB, CrabEntity.createCrabAttributes());

        MiddleEarth.LOGGER.debug("Registering Mod Entities for " + MiddleEarth.MOD_ID);
    }
}
