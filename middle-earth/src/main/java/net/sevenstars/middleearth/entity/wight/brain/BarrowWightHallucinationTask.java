package net.sevenstars.middleearth.entity.wight.brain;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.wight.BarrowWightEnchanterEntity;
import net.sevenstars.middleearth.entity.wight.BarrowWightEntity;
import net.sevenstars.middleearth.statusEffects.StatusEffectRegistryME;

public class BarrowWightHallucinationTask extends MultiTickTask<BarrowWightEnchanterEntity> {
    private static final int SHOOT_CHARGING_EXPIRY = Math.round(15.0F);
    private static final int RECOVER_EXPIRY = Math.round(4.0F);
    public static final int SHOOT_COOLDOWN_EXPIRY = BarrowWightEnchanterEntity.HALLUCINATION_CAST_COOLDOWN * 4;

    public BarrowWightHallucinationTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModulesME.HALLUCINATION_CAST_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModulesME.SUMMON_CAST_COOLDOWN,
                        MemoryModuleState.VALUE_PRESENT
                ),
                SHOOT_CHARGING_EXPIRY + 1 + RECOVER_EXPIRY
        );
    }

    protected boolean shouldRun(ServerWorld serverWorld, BarrowWightEnchanterEntity BarrowWightEnchanterEntity) {
        Brain<BarrowWightEntity> brain = BarrowWightEnchanterEntity.getBrain();

        if(!brain.hasMemoryModule(MemoryModulesME.SUMMON_CAST_COOLDOWN)) return false;
        int cooldown = brain.getOptionalMemory(MemoryModulesME.SUMMON_CAST_COOLDOWN).orElse(0);
        if(cooldown <= BarrowWightSummonTask.SHOOT_COOLDOWN_EXPIRY * 0.85f) return false;

        if(brain.hasMemoryModule(MemoryModulesME.HALLUCINATION_CAST_COOLDOWN)) return false;
        return (Boolean)BarrowWightEnchanterEntity.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
                .map(target -> isTargetWithinRange(BarrowWightEnchanterEntity, target))
                .orElse(false);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, BarrowWightEnchanterEntity BarrowWightEnchanterEntity, long l) {
        return BarrowWightEnchanterEntity.getBrain().hasMemoryModule(MemoryModuleType.ATTACK_TARGET);
    }

    @Override
    protected void run(ServerWorld serverWorld, BarrowWightEnchanterEntity barrowWightEnchanterEntity, long l) {
        barrowWightEnchanterEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
                .ifPresent(target -> barrowWightEnchanterEntity.setPose(EntityPose.SHOOTING));
        Brain<BarrowWightEntity> brain = barrowWightEnchanterEntity.getBrain();
        LivingEntity livingEntity = (LivingEntity)brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (livingEntity != null) {
            livingEntity.addStatusEffect(
                    new StatusEffectInstance(StatusEffectRegistryME.HALLUCINATION, 80, 0),
                    barrowWightEnchanterEntity
            );
        }

        if(!brain.hasMemoryModule(MemoryModulesME.HALLUCINATION_CAST_COOLDOWN)) {
            barrowWightEnchanterEntity.tryCastingAnimation();
            brain.remember(MemoryModulesME.HALLUCINATION_CAST_COOLDOWN, SHOOT_COOLDOWN_EXPIRY);
        }
        barrowWightEnchanterEntity.playSound(SoundEvents.ENTITY_BREEZE_INHALE, 1.0F, 1.0F);
    }

    @Override
    protected void finishRunning(ServerWorld world, BarrowWightEnchanterEntity entity, long time) {
        super.finishRunning(world, entity, time);
    }

    private static boolean isTargetWithinRange(BarrowWightEnchanterEntity breeze, LivingEntity target) {
        double d = breeze.getPos().squaredDistanceTo(target.getPos());
        return d < 256.0;
    }
}
