package net.sevenstars.middleearth.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.item.Item;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;

import java.util.Optional;

public class CaveTrollEatFoodTask extends Behavior<CaveTrollEntity> {
    private long startTime;
    public static TagKey<Item> TROLL_FOOD = TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "troll_food"));

    public CaveTrollEatFoodTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModulesME.FOOD_EATEN_COUNT,
                        MemoryStatus.REGISTERED
                ),
                200
        );
    }

    @Override
    protected boolean checkExtraStartConditions(ServerLevel world, CaveTrollEntity entity) {
        return !entity.getMainHandItem().isEmpty() && entity.getMainHandItem().is(TROLL_FOOD);
    }

    @Override
    protected boolean canStillUse(ServerLevel world, CaveTrollEntity entity, long time) {
        return hasRequiredMemories(entity) && !entity.getMainHandItem().isEmpty();
    }

    @Override
    protected void start(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setSitting(true);
        this.startTime = time;
    }

    @Override
    protected void tick(ServerLevel world, CaveTrollEntity entity, long time) {
        if((time - startTime) > 60 && !entity.getMainHandItem().isEmpty()) {
            ItemParticleOption particles = new ItemParticleOption(ParticleTypes.ITEM, entity.getMainHandItem());
            Vec3 position = new Vec3(entity.getX() - Math.sin(Math.toRadians(entity.getVisualRotationYInDegrees())), entity.getEyeY(), entity.getZ() + Math.cos(Math.toRadians(entity.getVisualRotationYInDegrees())));
            world.sendParticles(particles, position.x(), position.y() - 1.0, position.z(),7, 0.0, 0.0, 0.0, 0.1);
        }
    }

    @Override
    protected void stop(ServerLevel world, CaveTrollEntity entity, long time) {
        Optional<Integer> foodCount = entity.getBrain().getMemory(MemoryModulesME.FOOD_EATEN_COUNT);
        foodCount.ifPresentOrElse(
                count -> entity.getBrain().setMemory(MemoryModulesME.FOOD_EATEN_COUNT, count + 1), // If present
                () -> entity.getBrain().setMemory(MemoryModulesME.FOOD_EATEN_COUNT, 1)); // If absent

        entity.getBrain().setMemory(MemoryModulesME.ACTION_TIMEOUT, 200);
        entity.setSitting(false);

        entity.getMainHandItem().shrink(1);
    }
}
