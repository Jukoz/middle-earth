package net.sevenstars.middleearth.entity.wight.brain;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.StrayEntity;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.event.GameEvent;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.wight.BarrowWightEnchanterEntity;
import net.sevenstars.middleearth.entity.wight.BarrowWightEntity;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.statusEffects.StatusEffectRegistryME;

public class BarrowWightSummonTask extends MultiTickTask<BarrowWightEnchanterEntity> {
    private static final int SHOOT_CHARGING_EXPIRY = Math.round(15.0F);
    private static final int RECOVER_EXPIRY = Math.round(4.0F);
    public static final int SHOOT_COOLDOWN_EXPIRY = 600;

    public BarrowWightSummonTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModulesME.SUMMON_CAST_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT
                ),
                SHOOT_CHARGING_EXPIRY + 1 + RECOVER_EXPIRY
        );
    }

    protected boolean shouldRun(ServerWorld serverWorld, BarrowWightEnchanterEntity BarrowWightEnchanterEntity) {
        if(BarrowWightEnchanterEntity.getBrain().hasMemoryModule(MemoryModulesME.SUMMON_CAST_COOLDOWN)) return false;
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
            summonSkeletons(serverWorld, barrowWightEnchanterEntity);
        }

        if(!brain.hasMemoryModule(MemoryModulesME.SUMMON_CAST_COOLDOWN)) {
            barrowWightEnchanterEntity.tryCastingAnimation();
            brain.remember(MemoryModulesME.SUMMON_CAST_COOLDOWN, SHOOT_COOLDOWN_EXPIRY);
        }
        barrowWightEnchanterEntity.playSound(SoundEvents.ENTITY_EVOKER_PREPARE_SUMMON, 2.0F, 0.8F);
    }

    protected void summonSkeletons(ServerWorld serverWorld, BarrowWightEnchanterEntity enchanterEntity) {
        Team team = enchanterEntity.getScoreboardTeam();
        Random random = enchanterEntity.getRandom();

        for(int i = 0; i < 3; ++i) {
            BlockPos blockPos = enchanterEntity.getBlockPos().add(
                    -2 + random.nextInt(5), 1, -2 + random.nextInt(5));
            StrayEntity strayEntity = (StrayEntity) EntityType.STRAY.create(serverWorld, SpawnReason.MOB_SUMMONED);
            if (strayEntity != null) {
                strayEntity.refreshPositionAndAngles(blockPos, 0.0F, 0.0F);
                strayEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(blockPos), SpawnReason.MOB_SUMMONED, (EntityData)null);
                BarrowWightEntity.addEntityToBarrowWightsTeam(serverWorld, strayEntity.getNameForScoreboard());

                float randomChance = random.nextFloat();
                if(randomChance < 0.225f) {
                    strayEntity.equipStack(EquipmentSlot.MAINHAND, WeaponItemsME.BARROW_BLADE.getDefaultStack());
                } else if(randomChance < 0.5f) {
                    strayEntity.equipStack(EquipmentSlot.MAINHAND, WeaponItemsME.IRON_DAGGER.getDefaultStack());
                } else if(randomChance < 0.7f) {
                    strayEntity.equipStack(EquipmentSlot.MAINHAND, WeaponItemsME.GOLDEN_DAGGER.getDefaultStack());
                } else {
                    strayEntity.equipStack(EquipmentSlot.MAINHAND, WeaponItemsME.CRUDE_DAGGER.getDefaultStack());
                }

                if (team != null) {
                    serverWorld.getScoreboard().addScoreHolderToTeam(strayEntity.getNameForScoreboard(), team);
                }

                serverWorld.spawnEntityAndPassengers(strayEntity);
                serverWorld.addParticleClient(ParticleTypes.CAMPFIRE_COSY_SMOKE, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 0.0, 0.7, 0.0);
                serverWorld.emitGameEvent(GameEvent.ENTITY_PLACE, blockPos, GameEvent.Emitter.of(enchanterEntity));
            }
        }
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
