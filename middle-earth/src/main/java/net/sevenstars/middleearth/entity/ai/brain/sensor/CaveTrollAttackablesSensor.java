package net.sevenstars.middleearth.entity.ai.brain.sensor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

public class CaveTrollAttackablesSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean matches(ServerWorld world, LivingEntity entity, LivingEntity target) {
        return CaveTrollEntity.shouldTarget(target) && !((CaveTrollEntity)entity).isTame();
    }

    @Override
    protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}
