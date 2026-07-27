package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

public class CaveTrollSmashTask extends Behavior<CaveTrollEntity> {
    public CaveTrollSmashTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModulesME.SMASH_COOLDOWN,
                        MemoryStatus.VALUE_ABSENT
                ),30
        );
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, CaveTrollEntity entity) {
        return entity.getTarget() != null && entity.distanceToSqr(entity.getTarget()) < 16;
    }

    @Override
    protected boolean canStillUse(ServerLevel world, CaveTrollEntity entity, long time) {
        return true;
    }

    @Override
    protected void start(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setSmashing(true);
        entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void stop(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.smashAttack(entity.getRandom().nextIntBetweenInclusive(75, 125));
        entity.getBrain().setMemory(MemoryModulesME.SMASH_COOLDOWN, 200);
    }
}
