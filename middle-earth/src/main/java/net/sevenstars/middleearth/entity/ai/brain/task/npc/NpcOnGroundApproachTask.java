package net.sevenstars.middleearth.entity.ai.brain.task.npc;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class NpcOnGroundApproachTask extends Behavior<NpcEntity> {
    public NpcOnGroundApproachTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModulesME.SMASH_COOLDOWN,
                        MemoryStatus.VALUE_ABSENT
                )
        );
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, NpcEntity entity) {
        return entity.getTarget() != null && !entity.isPassenger() && entity.distanceToSqr(entity.getTarget()) < 16;
    }

    @Override
    protected boolean canStillUse(ServerLevel world, NpcEntity entity, long time) {
        return !entity.isPassenger();
    }

    @Override
    protected void start(ServerLevel world, NpcEntity entity, long time) {
        LivingEntity target = entity.getTarget();
        if(target == null || entity.isPassenger()){
            entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            return;
        }

        float distance = target.distanceTo(entity);
        entity.setBlocking(true);

        entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void stop(ServerLevel world, NpcEntity entity, long time) {
        entity.setBlocking(false);
    }
}

