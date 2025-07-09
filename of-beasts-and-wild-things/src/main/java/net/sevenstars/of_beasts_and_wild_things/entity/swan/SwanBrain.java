package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.RandomTask;

public class SwanBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super SwanEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    public SwanBrain() {
    }

    protected static Brain<?> create(SwanEntity swanEntity, Dynamic<?> dynamic) {
        Brain.Profile<SwanEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<SwanEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<SwanEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of());
    }

    private static void addIdleActivities(Brain<SwanEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RandomTask(ImmutableMap.of(), ImmutableList.of(

                ))),
                Pair.of(1, new RandomTask(ImmutableMap.of(), ImmutableList.of(

                )))
        ));
    }

    public static void updateActivities(SwanEntity swan) {
        swan.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    static {
        SENSORS = ImmutableList.of();
        MEMORY_MODULES = ImmutableList.of();
    }

}
