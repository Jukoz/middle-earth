package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.sensor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;

public class SwanAttackablesSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean isMatchingEntity(LivingEntity entity, LivingEntity target) {
        return SwanEntity.isValidSwanFood(target);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getMemory() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}
