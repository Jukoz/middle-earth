package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.WardenAngerManager;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.AxolotlBrain;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.poi.PointOfInterestTypes;
import net.sevenstars.of_beasts_and_wild_things.block.ModBlocks;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModMemoryModules;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModSchedule;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModSensors;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.*;

import java.util.List;
import java.util.Optional;

public class SwanBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super SwanEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(SwanEntity swanEntity, Dynamic<?> dynamic) {
        Brain.Profile<SwanEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<SwanEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        addRestActivities(brain);
        addFightActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.setSchedule(ModSchedule.SWAN_DEFAULT);

        brain.refreshActivities(swanEntity.getWorld().getTimeOfDay(), swanEntity.getWorld().getTime());
        return brain;
    }

    private static void addCoreActivities(Brain<SwanEntity> brain) {
        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetTask(),
                new UpdateLookControlTask(45, 90),
                DefendHomeTask.create(5),
                UpdateAttackTargetTask.create((world, swan) -> swan.getHurtBy())
        ));
    }

    private static void addIdleActivities(Brain<SwanEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(1, new RandomTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(ModBlocks.BIRD_NEST), 2),
                        Pair.of(StrollTask.create(1.0F), 1)
                ))),
                Pair.of(2, new RandomTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_PRESENT), ImmutableList.of(
                        Pair.of(StrollAroundHomeTask.create(1.0f, 20, true), 2),
                        Pair.of(new WaitTask(20, 100), 1)
                ))),
                Pair.of(3, UpdateAttackTargetTask.create(SwanBrain::getAttackTarget)),
                Pair.of(99, ScheduleActivityTask.create())
        ));
    }

    private static void addRestActivities(Brain<SwanEntity> brain) {
        brain.setTaskList(Activity.REST, ImmutableList.of(
                Pair.of(0, new RandomTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(ModBlocks.BIRD_NEST), 2),
                        Pair.of(StrollTask.create(1.0F), 1)
                ))),
                Pair.of(1, MoveTowardsPosMemoryTask.create(MemoryModuleType.HOME, 1.0f, 2, 20, 300)),
                Pair.of(2, new SleepOnGroundTask()),
                Pair.of(99, ScheduleActivityTask.create())
        ));
    }

    private static void addFightActivities(Brain<SwanEntity> brain) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                Pair.of(0, UpdateAttackTargetTask.create(SwanBrain::getAttackTarget)),
                Pair.of(1, ForgetAttackTargetTask.create()),
                Pair.of(2, RangedApproachTask.create(1.25F)),
                Pair.of(3, MeleeAttackTask.create(30))
        ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
                ));
    }

    public static void updateActivities(SwanEntity swan) {
        swan.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT));
    }

    private static Optional<? extends LivingEntity> getAttackTarget(ServerWorld world, SwanEntity swan) {
        return swan.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.IS_IN_WATER,
                ModSensors.SWAN_ATTACKABLES
        );
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.HOME,
                MemoryModuleType.LAST_WOKEN,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.IS_IN_WATER
        );
    }
}
