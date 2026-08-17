package net.sevenstars.middleearth.entity.wight;

import com.google.common.collect.ImmutableMap;
import net.minecraft.command.argument.EntityAnchorArgumentType;
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
import net.sevenstars.middleearth.statusEffects.ModStatusEffects;

public class BarrowWightIllusionTask extends MultiTickTask<BarrowWightEnchanterEntity> {
    private static final int SHOOT_CHARGING_EXPIRY = Math.round(15.0F);
    private static final int RECOVER_EXPIRY = Math.round(4.0F);
    private static final int SHOOT_COOLDOWN_EXPIRY = Math.round(30.0F);

    public BarrowWightIllusionTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModulesME.CAST_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT
                ),
                SHOOT_CHARGING_EXPIRY + 1 + RECOVER_EXPIRY
        );
    }

    protected boolean shouldRun(ServerWorld serverWorld, BarrowWightEnchanterEntity BarrowWightEnchanterEntity) {
        return (Boolean)BarrowWightEnchanterEntity.getBrain()
                .getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET)
                .map(target -> isTargetWithinRange(BarrowWightEnchanterEntity, target))
                .map(withinRange -> {
                    //if (!withinRange) {
                    //    BarrowWightEnchanterEntity.getBrain().forget(MemoryModuleType.BREEZE_SHOOT);
                    //}
                    return withinRange;
                })
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
            livingEntity.addStatusEffect(new StatusEffectInstance(ModStatusEffects.HALLUCINATION, 240, 0), barrowWightEnchanterEntity);
        }

        barrowWightEnchanterEntity.playSound(SoundEvents.ENTITY_BREEZE_INHALE, 1.0F, 1.0F);
    }

    @Override
    protected void finishRunning(ServerWorld world, BarrowWightEnchanterEntity entity, long time) {
        super.finishRunning(world, entity, time);
        entity.getBrain().remember(MemoryModulesME.CAST_COOLDOWN, SHOOT_COOLDOWN_EXPIRY);
    }

    @Override
    protected void keepRunning(ServerWorld serverWorld, BarrowWightEnchanterEntity BarrowWightEnchanterEntity, long l) {
        Brain<BarrowWightEntity> brain = BarrowWightEnchanterEntity.getBrain();
        LivingEntity livingEntity = (LivingEntity)brain.getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
        if (livingEntity != null) {
            BarrowWightEnchanterEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, livingEntity.getPos());
        }
    }

    private static boolean isTargetWithinRange(BarrowWightEnchanterEntity breeze, LivingEntity target) {
        double d = breeze.getPos().squaredDistanceTo(target.getPos());
        return d < 256.0;
    }
}
