package net.sevenstars.middleearth.entity.ai.brain.sensor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.server.world.ServerWorld;

public class AttackablesSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean matches(ServerWorld world, LivingEntity entity, LivingEntity target) {
        return entity.canTarget(target);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}
