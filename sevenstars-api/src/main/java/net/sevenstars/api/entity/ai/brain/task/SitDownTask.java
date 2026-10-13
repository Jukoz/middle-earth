package net.sevenstars.api.entity.ai.brain.task;

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
import net.sevenstars.api.entity.SittingEntity;
import net.sevenstars.api.entity.SleepingEntity;
import net.sevenstars.api.entity.ai.brain.ActivitiesAPI;

import java.util.Map;
import java.util.Optional;

public class SitDownTask extends MultiTickTask<LivingEntity> {
    private final double rangeFromPos;
    private final MemoryModuleType<GlobalPos> sittingPosMemory;

    public SitDownTask() {
        super(ImmutableMap.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));

        sittingPosMemory = null;
        rangeFromPos = 0.0d;
    }

    public SitDownTask(MemoryModuleType<GlobalPos> sittingPosMemory, double rangeFromPos) {
        super(ImmutableMap.of(sittingPosMemory, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));

        this.sittingPosMemory = sittingPosMemory;
        this.rangeFromPos = rangeFromPos;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        if (entity.hasVehicle() || !(entity instanceof SittingEntity)) {
            return false;
        }
        else if(sittingPosMemory != null) {
            Brain<?> brain = entity.getBrain();
            GlobalPos globalPos = (GlobalPos)brain.getOptionalRegisteredMemory(sittingPosMemory).get();
            if (world.getRegistryKey() != globalPos.dimension()) {
                return false;
            } else {
                return globalPos.pos().isWithinDistance(entity.getPos(), rangeFromPos) && !entity.isTouchingWater();
            }
        }

        return true;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
        if(sittingPosMemory != null) {
            Optional<GlobalPos> optional = entity.getBrain().getOptionalRegisteredMemory(sittingPosMemory);
            if (optional.isEmpty()) {
                return false;
            } else {
                BlockPos blockPos = ((GlobalPos)optional.get()).pos();
                return (entity.getBrain().hasActivity(Activity.IDLE) || entity.getBrain().hasActivity(ActivitiesAPI.BABY_IDLE)) && blockPos.isWithinDistance(entity.getPos(), rangeFromPos) && !entity.isTouchingWater();
            }
        }

        return (entity.getBrain().hasActivity(Activity.IDLE) || entity.getBrain().hasActivity(ActivitiesAPI.BABY_IDLE)) && !entity.isTouchingWater();
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        if(entity instanceof SittingEntity sittingEntity) {
            sittingEntity.startSitting();
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, LivingEntity entity, long time) {
        if(entity instanceof SittingEntity sittingEntity) {
            sittingEntity.stopSitting();
        }
    }

    @Override
    protected boolean isTimeLimitExceeded(long time) {
        return false;
    }
}
