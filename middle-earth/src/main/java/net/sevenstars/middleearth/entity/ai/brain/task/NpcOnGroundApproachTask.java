package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class NpcOnGroundApproachTask extends MultiTickTask<NpcEntity> {
    public NpcOnGroundApproachTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModulesME.SMASH_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT
                )
        );
    }

    @Override
    protected boolean shouldRun(ServerWorld world, NpcEntity entity) {
        return entity.getTarget() != null && !entity.hasVehicle() && entity.squaredDistanceTo(entity.getTarget()) < 16;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, NpcEntity entity, long time) {
        return !entity.hasVehicle();
    }

    @Override
    protected void run(ServerWorld world, NpcEntity entity, long time) {
        LivingEntity target = entity.getTarget();
        if(target == null || entity.hasVehicle()){
            entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
            return;
        }

        float distance = target.distanceTo(entity);
        entity.setBlocking(true);

        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void finishRunning(ServerWorld world, NpcEntity entity, long time) {
        entity.setBlocking(false);
    }
}

