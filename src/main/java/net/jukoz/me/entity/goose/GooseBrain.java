package net.jukoz.me.entity.goose;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.jukoz.me.entity.ModEntities;
import net.jukoz.me.entity.duck.DuckEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.math.random.Random;


public class GooseBrain {
    private static final UniformIntProvider WALK_TOWARD_ADULT_RANGE = UniformIntProvider.create(5, 16);
    private static final UniformIntProvider WALKING_SPEED = UniformIntProvider.create(5, 16);


    public GooseBrain() {
    }

    protected static void initialize(GooseEntity goose, Random random) {
    }

    protected static Brain<?> create(GooseEntity goose, Brain<GooseEntity> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(goose, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<GooseEntity> brain) {

        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new StayAboveWaterTask(1.0F),
                new LookAroundTask(45, 90),
                //new WanderAroundTask(), //TODO renamed need to finc replacement, if we ever even use brains (we should)
                new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new TemptationCooldownTask(MemoryModuleType.GAZE_COOLDOWN_TICKS)));
    }

    private static void addIdleActivities(Brain<GooseEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, LookAtMobWithIntervalTask.follow(EntityType.PLAYER, 6.0F, UniformIntProvider.create(30, 60))),
                Pair.of(0, new BreedTask(ModEntities.GOOSE, 1.0F, 5)),
                Pair.of(1, new TemptTask((goose) -> {return 1.25F;})),
                Pair.of(2, WalkTowardClosestAdultTask.create(WALKING_SPEED, 1.25F)),
                Pair.of(3, WalkTowardsWaterTask.create(6, 1.0F)),
                Pair.of(4, WalkTowardsLandTask.create(8, 1.0F)),
                Pair.of(5, new RandomTask(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(StrollTask.create(1.0F), 1),
                        Pair.of(GoTowardsLookTargetTask.create(1.0F, 3), 1))))));
    }

    private static void addFightActivities(GooseEntity goose, Brain<GooseEntity> brain) {
        brain.setTaskList(Activity.FIGHT,10, ImmutableList.of(
                ForgetAttackTargetTask.create((target) -> true),
                LookAtMobTask.create((entity) -> {
                    if(!goose.isBaby()){
                        return isTargeting(goose, entity);
                    }
                    return false;
                }, (float)goose.getAttributeValue(EntityAttributes.GENERIC_FOLLOW_RANGE)),
                MeleeAttackTask.create(18)),
                MemoryModuleType.ATTACK_TARGET);
    }

    public static boolean isTargeting(GooseEntity goose, LivingEntity entity) {
        return goose.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).filter((entityx) -> {
            return entityx == entity;
        }).isPresent();
    }

    public static void updateActivities(GooseEntity goose) {
        goose.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.CORE, Activity.FIGHT));
    }

    public static Ingredient getTemptItems() {
        return DuckEntity.BREEDING_INGREDIENT;
    }
}
