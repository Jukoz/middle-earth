package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;

public class BeastChargeTask extends MultiTickTask<AbstractBeastEntity> {
    private final int MAX_COOLDOWN;
    public BeastChargeTask(int runtime, int maxCooldown) {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT
                ),
                runtime
        );

        this.MAX_COOLDOWN = maxCooldown;
    }

    @Override
    protected boolean shouldRun(ServerWorld world, AbstractBeastEntity entity) {
        return entity.getTarget() != null &&
                entity.getChargeTimeout() == 0  &&
                entity.canSee(entity.getTarget()) &&
                entity.canCharge() &&
                entity.squaredDistanceTo(entity.getTarget()) > 20;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, AbstractBeastEntity entity, long time) {
        return entity.isCharging();
    }

    @Override
    protected void run(ServerWorld world, AbstractBeastEntity entity, long time) {
        entity.setCharging(true);
        entity.setChargeTimeout(MAX_COOLDOWN);
    }

    @Override
    protected void finishRunning(ServerWorld world, AbstractBeastEntity entity, long time) {
        entity.setCharging(false);
    }
}
