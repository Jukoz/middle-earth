package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

import java.util.Optional;

public class CaveTrollSleepTask extends Behavior<CaveTrollEntity> {
    public CaveTrollSleepTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.HURT_BY_ENTITY,
                        MemoryStatus.REGISTERED,
                        MemoryModulesME.FOOD_EATEN_COUNT,
                        MemoryStatus.VALUE_PRESENT
                ),1200, 3600
        );
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, CaveTrollEntity entity) {
        Optional<Integer> foodCount = entity.getBrain().getMemory(MemoryModulesME.FOOD_EATEN_COUNT);

        return foodCount.isPresent() && foodCount.get() >= 3;
    }

    @Override
    protected boolean canStillUse(ServerLevel world, CaveTrollEntity entity, long time) {
        return entity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).isEmpty();
    }

    @Override
    protected void start(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.getBrain().setMemory(MemoryModulesME.FOOD_EATEN_COUNT, 0);
        entity.startSleeping();
    }

    @Override
    protected void stop(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.stopSleeping();
        entity.getBrain().setMemory(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN, 600);
        entity.getBrain().setMemory(MemoryModulesME.ACTION_TIMEOUT, 200);
    }
}
