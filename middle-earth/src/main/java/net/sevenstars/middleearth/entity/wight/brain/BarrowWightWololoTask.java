package net.sevenstars.middleearth.entity.wight.brain;

import com.google.common.collect.ImmutableMap;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.wight.BarrowWightEnchanterEntity;
import net.sevenstars.middleearth.entity.wight.BarrowWightEntity;
import net.sevenstars.middleearth.statusEffects.StatusEffectRegistryME;

import java.util.List;

public class BarrowWightWololoTask extends MultiTickTask<BarrowWightEnchanterEntity> {
    private static final int SHOOT_CHARGING_EXPIRY = BarrowWightEnchanterEntity.BASE_CAST_COOLDOWN;
    private static final int RECOVER_EXPIRY = Math.round(4.0F);
    public static final int SHOOT_COOLDOWN_EXPIRY = BarrowWightEnchanterEntity.BASE_CAST_COOLDOWN * 10;
    public int preparationTicks = 0;

    public BarrowWightWololoTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModulesME.HALLUCINATION_CAST_COOLDOWN,
                        MemoryModuleState.VALUE_ABSENT
                ),
                SHOOT_CHARGING_EXPIRY + 1 + RECOVER_EXPIRY
        );
    }

    protected boolean shouldRun(ServerWorld serverWorld, BarrowWightEnchanterEntity enchanterEntity) {
        Brain<BarrowWightEntity> brain = enchanterEntity.getBrain();
        if(brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET)) return false;

        List<? extends SheepEntity> sheeps = getNearbySheeps(serverWorld, enchanterEntity);
        boolean hasSheeps = !sheeps.isEmpty();
        if(hasSheeps) {
            enchanterEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, sheeps.getFirst().getPos());
        }
        return hasSheeps;
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld serverWorld, BarrowWightEnchanterEntity enchanterEntity, long l) {
        if(preparationTicks >= 0) {
            Brain<BarrowWightEntity> brain = enchanterEntity.getBrain();
            enchanterEntity.tryCastingAnimation();
            preparationTicks = Math.max(-1, preparationTicks - 1);
            if(preparationTicks == 0 && brain.hasMemoryModule(MemoryModulesME.HALLUCINATION_CAST_COOLDOWN)) {
                List<? extends SheepEntity> sheeps = getNearbySheeps(serverWorld, enchanterEntity);
                if(sheeps.isEmpty()) return false;

                SheepEntity sheepEntity = sheeps.getFirst();
                enchanterEntity.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, sheepEntity.getPos());
                sheepEntity.setColor(DyeColor.RED);

                Random random = serverWorld.getRandom();
                serverWorld.spawnParticles(ParticleTypes.SOUL, true, true, sheepEntity.getX() + random.nextFloat() - 0.5f,
                        sheepEntity.getY() + random.nextFloat(), sheepEntity.getZ() + random.nextFloat() - 0.5f, 13,
                        random.nextFloat() - 0.5f, 0.5f + random.nextFloat(), random.nextFloat() - 0.5f, 0.07f);

                enchanterEntity.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, 1.1F, 0.8F);
            }
            return true;
        }
        return false;
    }

    protected static List<? extends SheepEntity> getNearbySheeps(ServerWorld serverWorld, BarrowWightEnchanterEntity enchanterEntity) {
        return serverWorld.getEntitiesByClass(SheepEntity.class,
                enchanterEntity.getBoundingBox().expand(20), (entity) -> {
                    boolean valid = entity.isAlive() && entity instanceof SheepEntity;
                    boolean isWoolRed = entity.getColor().equals(DyeColor.RED);
                    return valid && !isWoolRed;
                });
    }

    @Override
    protected void run(ServerWorld serverWorld, BarrowWightEnchanterEntity barrowWightEnchanterEntity, long l) {
        Brain<BarrowWightEntity> brain = barrowWightEnchanterEntity.getBrain();
        preparationTicks = (int)(BarrowWightEnchanterEntity.BASE_CAST_COOLDOWN * 0.7f);
        brain.remember(MemoryModulesME.HALLUCINATION_CAST_COOLDOWN, SHOOT_COOLDOWN_EXPIRY);
    }

    @Override
    protected void finishRunning(ServerWorld world, BarrowWightEnchanterEntity entity, long time) {
        super.finishRunning(world, entity, time);
    }
}
