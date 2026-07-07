package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

import java.util.Optional;

public class CaveTrollSleepTask extends MultiTickTask<CaveTrollEntity> {
    public CaveTrollSleepTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModuleType.HURT_BY_ENTITY,
                        MemoryModuleState.REGISTERED,
                        MemoryModulesME.FOOD_EATEN_COUNT,
                        MemoryModuleState.VALUE_PRESENT
                ),1200, 3600
        );
    }

    @Override
    protected boolean shouldRun(ServerWorld world, CaveTrollEntity entity) {
        Optional<Integer> foodCount = entity.getBrain().getOptionalRegisteredMemory(MemoryModulesME.FOOD_EATEN_COUNT);

        return foodCount.isPresent() && foodCount.get() >= 3;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        return entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).isEmpty();
    }

    @Override
    protected void run(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.getBrain().remember(MemoryModulesME.FOOD_EATEN_COUNT, 0);
        entity.startSleeping();
    }

    @Override
    protected void finishRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.stopSleeping();
        entity.getBrain().remember(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN, 600);
        entity.getBrain().remember(MemoryModulesME.ACTION_TIMEOUT, 200);
    }
}
