package net.jukoz.me.entity.uruks;

import net.jukoz.me.entity.NpcEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class UrukNpcEntity extends NpcEntity {
    protected UrukNpcEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }
}
