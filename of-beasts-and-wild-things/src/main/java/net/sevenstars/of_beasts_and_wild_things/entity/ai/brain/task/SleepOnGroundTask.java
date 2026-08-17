package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.sevenstars.api.entity.SleepingEntity;
import net.sevenstars.api.entity.ai.brain.ActivitiesAPI;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;

import java.util.Optional;

public class SleepOnGroundTask extends MultiTickTask<LivingEntity> {
    private final double rangeFromHome;

    public SleepOnGroundTask() {
        super(ImmutableMap.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));

        this.rangeFromHome = 0.0;
    }

    public SleepOnGroundTask(double rangeFromHome) {
        super(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));

        this.rangeFromHome = rangeFromHome;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        if (entity.hasVehicle()) {
            return false;
        } else {
            if(rangeFromHome != 0.0) {
                Brain<?> brain = entity.getBrain();
                GlobalPos globalPos = (GlobalPos)brain.getOptionalRegisteredMemory(MemoryModuleType.HOME).get();
                if (world.getRegistryKey() != globalPos.dimension()) {
                    return false;
                } else {
                    return globalPos.pos().isWithinDistance(entity.getPos(), rangeFromHome) && !entity.isTouchingWater();
                }
            }
        }

        return true;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
        if(rangeFromHome != 0.0) {
            Optional<GlobalPos> optional = entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HOME);
            if (optional.isEmpty()) {
                return false;
            } else {
                BlockPos blockPos = ((GlobalPos)optional.get()).pos();
                return (entity.getBrain().hasActivity(Activity.REST) || entity.getBrain().hasActivity(ActivitiesAPI.BABY_REST)) && blockPos.isWithinDistance(entity.getPos(), rangeFromHome) && !entity.isTouchingWater();
            }
        }

        return (entity.getBrain().hasActivity(Activity.REST) || entity.getBrain().hasActivity(ActivitiesAPI.BABY_REST)) && !entity.isTouchingWater();
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        if(entity instanceof SleepingEntity sleepingEntity) {
            sleepingEntity.startSleeping();
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, LivingEntity entity, long time) {
        if(entity instanceof SleepingEntity sleepingEntity) {
            sleepingEntity.stopSleeping();
        }
    }

    @Override
    protected boolean isTimeLimitExceeded(long time) {
        return false;
    }
}
