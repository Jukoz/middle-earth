package net.sevenstars.middleearth.entity.ai.brain.task.npc;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class NpcMountedApproachTask extends Behavior<NpcEntity> {
    public NpcMountedApproachTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModuleType.ATTACK_COOLING_DOWN,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModuleType.WALK_TARGET,
                        MemoryStatus.VALUE_PRESENT
                ),35
        );
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, NpcEntity entity) {
        return entity.getTarget() != null && entity.isPassenger() && entity.distanceToSqr(entity.getTarget()) < 16;
    }

    @Override
    protected boolean canStillUse(ServerLevel world, NpcEntity entity, long time) {
        return entity.isPassenger();
    }

    @Override
    protected void start(ServerLevel world, NpcEntity entity, long time) {
        LivingEntity target = entity.getTarget();
        if(target == null || !entity.isPassenger()){
            entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
            return;
        }
        float distance = target.distanceTo(entity);

        entity.setBlocking(true);

        if(entity.getVehicle() instanceof AbstractBeastEntity beastEntity){
            if(distance > 5 && distance < 16){
                beastEntity.chargeAttack();
            }
        }

        entity.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void stop(ServerLevel world, NpcEntity entity, long time) {
        entity.getBrain().setMemory(MemoryModulesME.SMASH_COOLDOWN, 200);
        entity.setBlocking(false);
    }
}

