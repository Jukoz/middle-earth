package net.sevenstars.middleearth.entity.orcs;

import net.sevenstars.middleearth.entity.NpcEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class OrcNpcEntity extends NpcEntity {
    protected OrcNpcEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
