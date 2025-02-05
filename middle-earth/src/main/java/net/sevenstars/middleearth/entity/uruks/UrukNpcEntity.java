package net.sevenstars.middleearth.entity.uruks;

import net.sevenstars.middleearth.entity.NpcEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class UrukNpcEntity extends NpcEntity {
    protected UrukNpcEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int index = 4;
        index = initEvilTargetSelector(index);
    }
}
