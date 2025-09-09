package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

public class CaveTrollSmashTask extends MultiTickTask<CaveTrollEntity> {
    public CaveTrollSmashTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModulesME.SMASH_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT
                ),30
        );
    }

    @Override
    protected boolean shouldRun(ServerWorld world, CaveTrollEntity entity) {
        return entity.getTarget() != null && entity.squaredDistanceTo(entity.getTarget()) < 16;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        return true;
    }

    @Override
    protected void run(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.setSmashing(true);
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void finishRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.smashAttack(entity.getRandom().nextBetween(50, 100));
        entity.getBrain().remember(MemoryModulesME.SMASH_COOLDOWN, 200);
    }
}
