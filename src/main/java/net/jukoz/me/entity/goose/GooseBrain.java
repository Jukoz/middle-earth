package net.jukoz.me.entity.goose;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.jukoz.me.entity.ModEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.FrogBrain;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;

import java.util.function.Predicate;

public class GooseBrain {
    private static final UniformIntProvider WALK_TOWARD_ADULT_RANGE = UniformIntProvider.create(5, 16);


    public GooseBrain() {
    }

    protected static void initialize(GooseEntity goose, Random random) {
    }

    protected static Brain<?> create(Brain<GooseEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<GooseEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new StayAboveWaterTask(0.8F),
                new LookAroundTask(45, 90),
                new WanderAroundTask(),
                new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new TemptationCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)));
    }

    private static void addIdleActivities(Brain<GooseEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, LookAtMobWithIntervalTask.follow(EntityType.PLAYER, 6.0F, UniformIntProvider.create(30, 60))),
                Pair.of(0, new BreedTask(ModEntities.GOOSE, 1.0F)),
                Pair.of(1, new TemptTask((goose) -> {return 1.25F;})),
                Pair.of(2, WalkTowardsLandTask.create(6, 1.0F)),
                Pair.of(3, new RandomTask(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(StrollTask.create(1.0F), 1),
                        Pair.of(GoTowardsLookTargetTask.create(1.0F, 3), 1),
                        Pair.of(TaskTriggerer.predicate(Entity::isOnGround), 2))))), ImmutableSet.of(
                                Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleState.VALUE_ABSENT),
                                Pair.of(MemoryModuleType.IS_IN_WATER, MemoryModuleState.VALUE_ABSENT)));
    }

    public static void updateActivities(GooseEntity goose) {
        goose.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    public static Ingredient getTemptItems() {
        return GooseEntity.BREEDING_INGREDIENT;
    }
}
