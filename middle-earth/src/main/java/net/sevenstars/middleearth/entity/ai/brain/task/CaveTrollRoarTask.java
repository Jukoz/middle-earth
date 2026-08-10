package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

import java.util.List;

public class CaveTrollRoarTask extends MultiTickTask<CaveTrollEntity> {
    long startTime;
    public CaveTrollRoarTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModulesME.ROAR_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT
                ),
                35
        );
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        return true;
    }

    @Override
    protected void run(ServerWorld world, CaveTrollEntity troll, long time) {
        this.startTime = time;
        troll.setRoaring(true);
        troll.getBrain().forget(MemoryModuleType.WALK_TARGET);

        List<Entity> entities = world.getOtherEntities(troll, troll.getBoundingBox().expand(15, 15, 15)); // Get nearby entities
        for(Entity entity : entities) { // Run through all entities
            if(troll.isValidTarget(entity)) { // Apply bad effects if entity is valid target
                ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200), troll);
                ((LivingEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100), troll);
            }
        }
    }


    @Override
    protected void keepRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        if(time - startTime == 10) {
            entity.playRoarSound();
        }
    }

    @Override
    protected boolean isTimeLimitExceeded(long time) {
        return super.isTimeLimitExceeded(time);
    }

    @Override
    protected void finishRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.setRoaring(false);
        entity.getBrain().remember(MemoryModulesME.ROAR_COOLDOWN, 600);
    }
}
