package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

import java.util.Optional;

public class CaveTrollEatFoodTask extends MultiTickTask<CaveTrollEntity> {
    private long startTime;
    public CaveTrollEatFoodTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModulesME.FOOD_EATEN_COUNT,
                        MemoryModuleState.VALUE_PRESENT
                ),
                200
        );
    }

    @Override
    protected boolean shouldRun(ServerWorld world, CaveTrollEntity entity) {
        return !entity.getMainHandStack().isEmpty();
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        return hasRequiredMemoryState(entity);
    }

    @Override
    protected void run(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.setSitting(true);
        this.startTime = time;
    }

    @Override
    protected void keepRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        if((time - startTime) > 60) {
            ItemStackParticleEffect particles = new ItemStackParticleEffect(ParticleTypes.ITEM, entity.getMainHandStack());
            Vec3d position = new Vec3d(entity.getX() - Math.sin(Math.toRadians(entity.getBodyYaw())), entity.getEyeY(), entity.getZ() + Math.cos(Math.toRadians(entity.getBodyYaw())));
            world.spawnParticles(particles, position.getX(), position.getY() - 1.0, position.getZ(),7, 0.0, 0.0, 0.0, 0.1);
        }
    }

    @Override
    protected void finishRunning(ServerWorld world, CaveTrollEntity entity, long time) {
        entity.getMainHandStack().decrement(1);

        Optional<Integer> foodCount = entity.getBrain().getOptionalRegisteredMemory(MemoryModulesME.FOOD_EATEN_COUNT);
        foodCount.ifPresent(count -> entity.getBrain().remember(MemoryModulesME.FOOD_EATEN_COUNT, count + 1));

        entity.setSitting(false);
    }
}
