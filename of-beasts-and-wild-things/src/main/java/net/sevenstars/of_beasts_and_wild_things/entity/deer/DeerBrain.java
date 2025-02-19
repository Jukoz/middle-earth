package net.sevenstars.of_beasts_and_wild_things.entity.deer;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;

public class DeerBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super DeerEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    public DeerBrain() {
    }

    protected static Brain<?> create(DeerEntity deerEntity, Dynamic<?> dynamic) {
        Brain.Profile<DeerEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<DeerEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<DeerEntity> brain) {
        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new MoveToTargetTask()));
    }

    private static void addIdleActivities(Brain<DeerEntity> brain) {
    }

    public static void updateActivities(DeerEntity deer) {
        deer.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.LONG_JUMP));
    }

    static {
        SENSORS = ImmutableList.of();
        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.LONG_JUMP_COOLING_DOWN);
    }
}
