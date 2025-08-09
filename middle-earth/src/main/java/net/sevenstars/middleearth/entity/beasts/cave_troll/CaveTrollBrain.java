package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.MoveToTargetTask;
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.ai.brain.task.UpdateLookControlTask;

import java.util.Optional;

public class CaveTrollBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super CaveTrollEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(CaveTrollEntity troll, Dynamic<?> dynamic) {
        Brain.Profile<CaveTrollEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<CaveTrollEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        addRestActivities(brain);
        addFightActivities(troll, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
        return brain;
    }

    private static void addCoreActivities(Brain<CaveTrollEntity> brain) {
        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetTask(),
                new UpdateLookControlTask(45, 90)
        ));
    }

    private static void addIdleActivities(Brain<CaveTrollEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, StrollTask.create(1.0F))
        ));
    }

    private static void addRestActivities(Brain<CaveTrollEntity> brain) {
        brain.setTaskList(Activity.REST, ImmutableList.of(

        ));
    }

    private static void addFightActivities(CaveTrollEntity troll, Brain<CaveTrollEntity> brain) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(

                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
                ));
    }

    public static void updateActivities(CaveTrollEntity troll) {
        Optional<LivingEntity> optional = troll.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if(optional != null && optional.isPresent()) {
            troll.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT));
        }
        else {
            troll.getBrain().resetPossibleActivities(ImmutableList.of());
        }
        troll.getBrain().refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.IS_IN_WATER
        );
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.HOME,
                MemoryModuleType.LAST_WOKEN,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE
        );
    }
}
