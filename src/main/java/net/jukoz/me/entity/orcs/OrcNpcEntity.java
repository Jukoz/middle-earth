package net.jukoz.me.entity.orcs;

import net.jukoz.me.entity.NpcEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.world.World;

public class OrcNpcEntity extends NpcEntity {
    protected OrcNpcEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        super.initGoals();
        int index = 4;
        index = initEvilTargetSelector(index);
    }
}
