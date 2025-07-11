package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.sensor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.of_beasts_and_wild_things.entity.ModEntities;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;

public class SwanAttackablesSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean matches(ServerWorld world, LivingEntity entity, LivingEntity target) {
        return SwanEntity.isValidSwanFood(target);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}
