package net.sevenstars.of_beasts_and_wild_things.entity.snail;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.mob.WardenEntity;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.EatCropTask;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.WalkTowardsCropTask;

public class SnailBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super SnailEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    public SnailBrain() {
    }

    protected static Brain<?> create(SnailEntity snailEntity, Dynamic<?> dynamic) {
        Brain.Profile<SnailEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<SnailEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<SnailEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new MoveToTargetTask(), new TickCooldownTask(MemoryModuleType.LONG_JUMP_COOLING_DOWN)));
    }

    private static void addIdleActivities(Brain<SnailEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RandomTask(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(Pair.of(WalkTowardsCropTask.create(1.0F), 1), Pair.of(new EatCropTask(), 2), Pair.of(TaskTriggerer.predicate(SnailEntity::isOnGround), 3))))
        ));
    }

    public static void updateActivities(SnailEntity snail) {
        snail.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.LONG_JUMP));
    }

    static {
        SENSORS = ImmutableList.of();
        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.LONG_JUMP_COOLING_DOWN);
    }
}
