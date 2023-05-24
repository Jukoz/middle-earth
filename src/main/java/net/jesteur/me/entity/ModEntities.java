package net.jesteur.me.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.jesteur.me.MiddleEarth;
import net.jesteur.me.entity.dwarves.durin.DurinDwarfEntity;
import net.jesteur.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jesteur.me.entity.orcs.mordor.MordorOrcEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<DurinDwarfEntity> DURIN_FOLK = registerEntity("durin_folk", DurinDwarfEntity::new, SpawnGroup.CREATURE, 0.4f, 1.67f);
    public static final EntityType<GaladhrimElfEntity> GALADHRIM_ELF = registerEntity("galadhrim_elf", GaladhrimElfEntity::new, SpawnGroup.CREATURE, 0.4f, 1.9f);
    public static final EntityType<MordorOrcEntity> MORDOR_ORC = registerEntity("mordor_orc", MordorOrcEntity::new, SpawnGroup.CREATURE, 0.4f, 1.72f);

    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType.EntityFactory<T> entity, SpawnGroup spawnGroup,
                                                                  float width, float height) {
        return Registry.register(Registries.ENTITY_TYPE,
                new Identifier(MiddleEarth.MOD_ID, name),FabricEntityTypeBuilder.create(spawnGroup, entity).dimensions(EntityDimensions.fixed(width, height)).build());
    }

    public static void registerModEntities() {
        FabricDefaultAttributeRegistry.register(DURIN_FOLK, DurinDwarfEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(GALADHRIM_ELF, GaladhrimElfEntity.setAttributes());
        FabricDefaultAttributeRegistry.register(MORDOR_ORC, MordorOrcEntity.setAttributes());
        MiddleEarth.LOGGER.debug("Registering Mod Entities for " + MiddleEarth.MOD_ID);
    }
}
