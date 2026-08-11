package net.sevenstars.middleearth.entity.stone_troll;

import com.google.common.collect.ImmutableList;
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

public class StoneTrollBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super StoneTrollEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(StoneTrollEntity troll, Dynamic<?> dynamic) {
        Brain.Profile<StoneTrollEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<StoneTrollEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        addFightActivities(brain, troll);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<StoneTrollEntity> brain) {
        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetTask(),
                new UpdateLookControlTask(45, 90)
        ));
    }

    private static void addIdleActivities(Brain<StoneTrollEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                    Pair.of(0, StrollTask.create(1.0F, false)),
                    Pair.of(0, LookAtMobTask.create(5))
                ),
                ImmutableSet.of(

                ));
    }

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
        troll.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));
        troll.getBrain().refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
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
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.NEAREST_PLAYERS
        );
    }
}
