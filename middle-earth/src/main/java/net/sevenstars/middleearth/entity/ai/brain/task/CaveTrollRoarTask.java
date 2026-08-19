package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

import java.util.List;

public class CaveTrollRoarTask extends Behavior<CaveTrollEntity> {
    long startTime;
    public CaveTrollRoarTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModulesME.ROAR_COOLDOWN,
                        MemoryStatus.VALUE_ABSENT
                ),
                35
        );
    }

    @Override
    protected boolean canStillUse(ServerLevel world, CaveTrollEntity entity, long time) {
        return true;
    }

    @Override
    protected void start(ServerLevel world, CaveTrollEntity troll, long time) {
        this.startTime = time;
        troll.setRoaring(true);
        troll.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);

        List<LivingEntity> entities = world.getEntitiesOfClass(
                LivingEntity.class,
                troll.getBoundingBox().inflate(15, 15, 15),
                troll::isValidTarget
        );
        for(LivingEntity entity : entities) {
            entity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200), troll);
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100), troll);
        }
    }


    @Override
    protected void tick(ServerLevel world, CaveTrollEntity entity, long time) {
        if(time - startTime == 10) {
            entity.playRoarSound();
        }
    }

    @Override
    protected boolean timedOut(long time) {
        return super.timedOut(time);
    }

    @Override
    protected void stop(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setRoaring(false);
        entity.getBrain().setMemory(MemoryModulesME.ROAR_COOLDOWN, 600);
    }
}
