package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.api.entity.ai.brain.ActivitiesAPI;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanEntity;

import java.util.Optional;

public class SleepOnGroundTask extends Behavior<LivingEntity> {
    public SleepOnGroundTask() {
        super(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT, MemoryModuleType.IS_IN_WATER, MemoryStatus.VALUE_ABSENT, MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT));
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, LivingEntity entity) {
        if (entity.isPassenger()) {
            return false;
        } else {
            Brain<?> brain = entity.getBrain();
            GlobalPos globalPos = (GlobalPos)brain.getMemory(MemoryModuleType.HOME).get();
            if (world.dimension() != globalPos.dimension()) {
                return false;
            } else {
                return globalPos.pos().closerToCenterThan(entity.position(), 4.0) && !entity.isInWater();
            }
        }
    }

    @Override
    protected boolean canStillUse(ServerLevel world, LivingEntity entity, long time) {
        Optional<GlobalPos> optional = entity.getBrain().getMemory(MemoryModuleType.HOME);
        if (optional.isEmpty()) {
            return false;
        } else {
            BlockPos blockPos = ((GlobalPos)optional.get()).pos();
            return (entity.getBrain().isActive(Activity.REST) || entity.getBrain().isActive(ActivitiesAPI.BABY_REST)) && blockPos.closerToCenterThan(entity.position(), 4.0) && !entity.isInWater();
        }
    }

    @Override
    protected void start(ServerLevel world, LivingEntity entity, long time) {
        if(entity instanceof SwanEntity) {
            ((SwanEntity) entity).startSleeping();
        }
    }

    @Override
    protected void stop(ServerLevel world, LivingEntity entity, long time) {
        if(entity instanceof SwanEntity) {
            ((SwanEntity) entity).stopSleeping();
        }
    }

    @Override
    protected boolean timedOut(long time) {
        return false;
    }
}
