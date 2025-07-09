package net.sevenstars.of_beasts_and_wild_things.entity.swan;

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
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.EatCropTask;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.MoveTowardsBlockTask;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.SearchForHomeTask;

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
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(new MoveToTargetTask()));
    }

    private static void addIdleActivities(Brain<SwanEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new RandomTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(TagKey.of(RegistryKeys.BLOCK, Identifier.of(OfBeastsAndWildThings.MOD_ID, "swan_homes"))), 2),
                        Pair.of(StrollTask.create(1.0F), 1)
                )))
        ));
    }

    public static void updateActivities(SwanEntity swan) {
        swan.getBrain().resetPossibleActivities(ImmutableList.of(Activity.IDLE));
    }

    static {
        SENSORS = ImmutableList.of(SensorType.NEAREST_PLAYERS);
        MEMORY_MODULES = ImmutableList.of(MemoryModuleType.WALK_TARGET, MemoryModuleType.HOME);
    }

}
