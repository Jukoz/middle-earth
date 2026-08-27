package net.sevenstars.middleearth.entity.stone_troll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.util.math.GlobalPos;
import net.sevenstars.api.entity.ai.brain.SchedulesAPI;
import net.sevenstars.api.entity.ai.brain.task.ForgetMemorizedPosTask;
import net.sevenstars.api.entity.ai.brain.task.MoveTowardsPosMemoryTask;
import net.sevenstars.api.entity.ai.brain.task.PlaceBlockNearbyTask;
import net.sevenstars.api.entity.ai.brain.task.RememberBlockLocationTask;
import net.sevenstars.api.entity.ai.brain.task.SleepOnGroundTask;

import java.util.Optional;

// TODO WALK TO CAMPFIRE AT NIGHT
// TODO SIT AROUND CAMPFIRE AT NIGHT
// TODO BUILD CAMPFIRE IF NONE AROUND
// TODO FIND SHADE DURING DAY
// TODO IDEA: Split schedule during the night into hunting and idling.
// TODO IDEA: Troll will want food during the hunting phase and treasures during the idling phase
public class StoneTrollBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super StoneTrollEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(StoneTrollEntity troll, Dynamic<?> dynamic) {
        Brain.Profile<StoneTrollEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<StoneTrollEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain, troll);
        addRestActivities(brain, troll);
        addFightActivities(brain, troll);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.setSchedule(SchedulesAPI.NOCTURNAL);

        brain.refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
        return brain;
    }

    private static void addCoreActivities(Brain<StoneTrollEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetTask(),
                new UpdateLookControlTask(45, 90)
        ));
    }

    // Plays at night
    private static void addIdleActivities(Brain<StoneTrollEntity> brain, StoneTrollEntity troll) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                    Pair.of(0, LookAtMobTask.create(5)),
                    Pair.of(1, new CompositeTask<>(ImmutableMap.of(MemoryModuleType.MEETING_POINT, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(), // Find or build new campfire
                            CompositeTask.Order.ORDERED, CompositeTask.RunMode.TRY_ALL,
                            ImmutableList.of(
                                    Pair.of(RememberBlockLocationTask.create(Blocks.CAMPFIRE, MemoryModuleType.MEETING_POINT), 1),
                                    Pair.of(PlaceBlockNearbyTask.create(() -> true, MemoryModuleType.MEETING_POINT, Blocks.CAMPFIRE), 1),
                                    Pair.of(StrollTask.create(1.0f), 1)
                            ))),
                    Pair.of(2, ForgetMemorizedPosTask.create(() -> shouldForgetMeetingPoint(troll), MemoryModuleType.MEETING_POINT)),
                    Pair.of(3, MoveTowardsPosMemoryTask.create(MemoryModuleType.MEETING_POINT, 1.0f, 5, 30, 300)),
                    // Sit down
                    Pair.of(99, ScheduleActivityTask.create())
        ));
    }

    // Plays during the day
    private static void addRestActivities(Brain<StoneTrollEntity> brain, StoneTrollEntity troll) {
        brain.setTaskList(Activity.REST, ImmutableList.of(
                // If in daylight, start panicking
                // Go home
                //Pair.of(1, ForgetMemorizedPosTask.create(() -> shouldForgetHome(troll), MemoryModuleType.HOME)),
                // If no home present, find new home
                Pair.of(2, new SleepOnGroundTask()),
                Pair.of(99, ScheduleActivityTask.create())
        ));
    }

    // Do not fight in daylight
    private static void addFightActivities(Brain<StoneTrollEntity> brain, StoneTrollEntity troll) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                        Pair.of(0, ForgetAttackTargetTask.create()),
                        Pair.of(2, new RandomTask<>(ImmutableList.of(
                                Pair.of(MeleeAttackTask.create(30), 4),
                                Pair.of(RangedApproachTask.create(2.5F), 3)
                        )))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
                )
        );
    }

    public static void updateActivities(StoneTrollEntity troll) {
        Optional<LivingEntity> optional = troll.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);

        if(optional != null && optional.isPresent()) {
            troll.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT));
        }
        else {
            troll.getBrain().resetPossibleActivities(ImmutableList.of());
        }

        troll.getBrain().refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
    }

    public static boolean shouldForgetHome(StoneTrollEntity troll) {
        Optional<GlobalPos> optional = troll.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HOME);

        if(optional != null && optional.isPresent()) {
            return troll.getWorld().isSkyVisible(optional.get().pos());
        }

        return false;
    }

    public static boolean shouldForgetMeetingPoint(StoneTrollEntity troll) {
        Optional<GlobalPos> optional = troll.getBrain().getOptionalRegisteredMemory(MemoryModuleType.MEETING_POINT);

        if(optional != null && optional.isPresent()) {
            return !troll.getWorld().getBlockState(optional.get().pos()).isOf(Blocks.CAMPFIRE);
        }

        return false;
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES
        );
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.IS_IN_WATER,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.NEAREST_PLAYERS,
                MemoryModuleType.HOME,
                MemoryModuleType.MEETING_POINT
        );
    }
}
