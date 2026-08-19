package net.sevenstars.middleearth.entity.ai.brain.sensor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class NpcAttackablesSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean isMatchingEntity(LivingEntity entity, LivingEntity target) {
        return NpcEntity.shouldTarget((NpcEntity) entity, target);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}
