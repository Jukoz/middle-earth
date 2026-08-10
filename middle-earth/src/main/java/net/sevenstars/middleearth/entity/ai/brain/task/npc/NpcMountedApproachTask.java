package net.sevenstars.middleearth.entity.ai.brain.task.npc;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.AbstractBeastEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

public class NpcMountedApproachTask extends MultiTickTask<NpcEntity> {
    public NpcMountedApproachTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModuleType.ATTACK_COOLING_DOWN,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModuleType.WALK_TARGET,
                        MemoryModuleState.VALUE_PRESENT
                ),35
        );
    }

    @Override
    protected boolean shouldRun(ServerWorld world, NpcEntity entity) {
        return entity.getTarget() != null && entity.hasVehicle() && entity.squaredDistanceTo(entity.getTarget()) < 16;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, NpcEntity entity, long time) {
        return entity.hasVehicle();
    }

    @Override
    protected void run(ServerWorld world, NpcEntity entity, long time) {
        LivingEntity target = entity.getTarget();
        if(target == null || !entity.hasVehicle()){
            entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
            return;
        }
        float distance = target.distanceTo(entity);

        entity.setBlocking(true);

        if(entity.getVehicle() instanceof AbstractBeastEntity beastEntity){
            if(distance > 5 && distance < 16){
                beastEntity.chargeAttack();
            }
        }

        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void finishRunning(ServerWorld world, NpcEntity entity, long time) {
        entity.getBrain().remember(MemoryModulesME.SMASH_COOLDOWN, 200);
        entity.setBlocking(false);
    }
}

