package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.ai.brain.SensorsME;
import net.sevenstars.middleearth.entity.ai.brain.task.CaveTrollDigForFoodTask;
import net.sevenstars.middleearth.entity.ai.brain.task.CaveTrollEatFoodTask;
import net.sevenstars.middleearth.entity.ai.brain.task.CaveTrollSleepTask;

public class CaveTrollBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super CaveTrollEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(CaveTrollEntity troll, Dynamic<?> dynamic) {
        Brain.Profile<CaveTrollEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<CaveTrollEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(troll, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.remember(MemoryModulesME.FOOD_EATEN_COUNT, 0);

        brain.refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
        return brain;
    }

    private static void addCoreActivities(Brain<CaveTrollEntity> brain) {
        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetTask(),
                new UpdateLookControlTask(45, 90),
                new TickCooldownTask(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN)

        ));
    }

    private static void addIdleActivities(Brain<CaveTrollEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, UpdateAttackTargetTask.create((world, troll) -> troll.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE))),
                Pair.of(1, new RandomTask<>(ImmutableList.of(
                        Pair.of(StrollTask.create(1.0F), 5),
                        Pair.of(new CompositeTask<>(ImmutableMap.of(), ImmutableSet.of(),
                                CompositeTask.Order.ORDERED,
                                CompositeTask.RunMode.TRY_ALL,
                                ImmutableList.of(
                                        Pair.of(new CaveTrollDigForFoodTask(), 1),
                                        Pair.of(new CaveTrollEatFoodTask(), 1),
                                        Pair.of(new CaveTrollSleepTask(), 1)
                                )), 1)
                )))
        ));
    }

    private static void addFightActivities(CaveTrollEntity troll, Brain<CaveTrollEntity> brain) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                        Pair.of(0, ForgetAttackTargetTask.create()),
                        Pair.of(1, RangedApproachTask.create(2.5F)),
                        Pair.of(2, MeleeAttackTask.create(30))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
                ));
    }

    public static void updateActivities(CaveTrollEntity troll) {
        troll.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, ActivitiesME.LOOK_FOR_FOOD, Activity.IDLE));
        troll.getBrain().refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.IS_IN_WATER,
                SensorsME.CAVE_TROLL_ATTACKABLES
        );
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.HOME,
                MemoryModuleType.LAST_WOKEN,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.NEAREST_PLAYERS,
                MemoryModulesME.DIG_FOR_FOOD_COOLDOWN,
                MemoryModulesME.FOOD_EATEN_COUNT
        );
    }
}
