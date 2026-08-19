package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;

public class BeastChargeTask extends Behavior<AbstractBeastEntity> {
    private final int MAX_COOLDOWN;
    public BeastChargeTask(int runtime, int maxCooldown) {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT
                ),
                runtime
        );

        this.MAX_COOLDOWN = maxCooldown;
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, AbstractBeastEntity entity) {
        return entity.getTarget() != null &&
                entity.getChargeTimeout() == 0  &&
                entity.hasLineOfSight(entity.getTarget()) &&
                entity.canCharge() &&
                entity.distanceToSqr(entity.getTarget()) > 20;
    }

    @Override
    protected boolean canStillUse(ServerLevel world, AbstractBeastEntity entity, long time) {
        return entity.isCharging();
    }

    @Override
    protected void start(ServerLevel world, AbstractBeastEntity entity, long time) {
        entity.setCharging(true);
        entity.setChargeTimeout(MAX_COOLDOWN);
    }

    @Override
    protected void stop(ServerLevel world, AbstractBeastEntity entity, long time) {
        entity.setCharging(false);
    }
}
