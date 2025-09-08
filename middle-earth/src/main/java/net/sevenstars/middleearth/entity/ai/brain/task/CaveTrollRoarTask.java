package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

import java.util.List;

public class CaveTrollRoarTask extends MultiTickTask<CaveTrollEntity> {
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
        troll.setRoaring(true);
        troll.getBrain().forget(MemoryModuleType.WALK_TARGET);

        List<Entity> entities = world.getOtherEntities(troll, troll.getBoundingBox().expand(15, 15, 15));
        for(Entity entity : entities) {
            if(entity instanceof LivingEntity livingEntity) {
                if(troll.getPassengerList().contains(livingEntity) || (troll.getOwner() != null && troll.getOwner().equals(livingEntity))) {
                    continue;
                }

                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 200), troll);
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100), troll);
            }
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.setRoaring(false);
        entity.getBrain().remember(MemoryModulesME.ROAR_COOLDOWN, 600);
    }
}
