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
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;

import java.util.Optional;

public class SleepOnGroundTask extends MultiTickTask<LivingEntity> {
    public SleepOnGroundTask() {
        super(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_PRESENT, MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT));
    }

    @Override
    protected boolean shouldRun(ServerWorld world, LivingEntity entity) {
        if (entity.hasVehicle()) {
            return false;
        } else {
            Brain<?> brain = entity.getBrain();
            GlobalPos globalPos = (GlobalPos)brain.getOptionalRegisteredMemory(MemoryModuleType.HOME).get();
            if (world.getRegistryKey() != globalPos.dimension()) {
                return false;
            } else {
                return globalPos.pos().isWithinDistance(entity.getPos(), 4.0) && !entity.isTouchingWater();
            }
        }
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, LivingEntity entity, long time) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HOME);
        if (optional.isEmpty()) {
            return false;
        } else {
            BlockPos blockPos = ((GlobalPos)optional.get()).pos();
            return entity.getBrain().hasActivity(Activity.REST) && blockPos.isWithinDistance(entity.getPos(), 4.0) && !entity.isTouchingWater();
        }
    }

    @Override
    protected void run(ServerWorld world, LivingEntity entity, long time) {
        if(entity instanceof SwanEntity) {
            ((SwanEntity) entity).startSleeping();
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, LivingEntity entity, long time) {
        if(entity instanceof SwanEntity) {
            ((SwanEntity) entity).stopSleeping();
        }
    }

    @Override
    protected boolean isTimeLimitExceeded(long time) {
        return false;
    }
}
