package net.sevenstars.middleearth.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.GateBehavior;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.ai.brain.SensorsME;
import net.sevenstars.middleearth.entity.ai.brain.task.*;

import java.util.Optional;

public class CaveTrollBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super CaveTrollEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(CaveTrollEntity troll, Dynamic<?> dynamic) {
        Brain.Provider<CaveTrollEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
        Brain<CaveTrollEntity> brain = profile.makeBrain(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        addTamedActivities(brain);
        addFightActivities(brain, troll);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.useDefaultActivity();
        return brain;
    }

    private static void addCoreActivities(Brain<CaveTrollEntity> brain) {
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetSink(),
                new LookAtTargetSink(45, 90),
                new CountDownCooldownTicks(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN),
                new CountDownCooldownTicks(MemoryModulesME.ROAR_COOLDOWN),
                new CountDownCooldownTicks(MemoryModulesME.SMASH_COOLDOWN),
                new CountDownCooldownTicks(MemoryModulesME.ACTION_TIMEOUT)
        ));
    }

    private static void addIdleActivities(Brain<CaveTrollEntity> brain) {
        brain.addActivityWithConditions(Activity.IDLE, ImmutableList.of(
                Pair.of(0, StartAttacking.create(CaveTrollBrain::getAttackTarget)),
                Pair.of(0, StartAttacking.create(CaveTrollBrain::getHurtBy)),
                Pair.of(1, new RunOne<>(ImmutableList.of(
                        Pair.of(RandomStroll.stroll(1.0F), 5),
                        Pair.of(new GateBehavior<>(ImmutableMap.of(MemoryModulesME.ACTION_TIMEOUT, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(),
                                GateBehavior.OrderPolicy.ORDERED,
                                GateBehavior.RunningPolicy.TRY_ALL,
                                ImmutableList.of(
                                        Pair.of(new CaveTrollDigForFoodTask(), 1),
                                        Pair.of(new CaveTrollEatFoodTask(), 1),
                                        Pair.of(new CaveTrollSleepTask(), 1)
                                )), 1)
                )))
        ),
                ImmutableSet.of(
                        Pair.of(MemoryModulesME.TAME, MemoryStatus.VALUE_ABSENT)
                ));
    }

    private static void addTamedActivities(Brain<CaveTrollEntity> brain) {
        brain.addActivityWithConditions(ActivitiesME.TAMED, ImmutableList.of(
                Pair.of(0, StartAttacking.create(CaveTrollBrain::getAttackTarget)),
                Pair.of(1, new GateBehavior<>(ImmutableMap.of(MemoryModulesME.SITTING, MemoryStatus.VALUE_ABSENT), ImmutableSet.of(),
                        GateBehavior.OrderPolicy.SHUFFLED,
                        GateBehavior.RunningPolicy.RUN_ONE,
                        ImmutableList.of(
                                Pair.of(RandomStroll.stroll(1.0f), 1)
                        )))
        ),
                ImmutableSet.of(
                        Pair.of(MemoryModulesME.TAME, MemoryStatus.VALUE_PRESENT)
                )
        );
    }

    private static void addFightActivities(Brain<CaveTrollEntity> brain, CaveTrollEntity troll) {
        brain.addActivityWithConditions(Activity.FIGHT, ImmutableList.of(
                        Pair.of(0, StopAttackingIfTargetInvalid.create()),
                        Pair.of(2, new RunOne<>(ImmutableList.of(
                                Pair.of(MeleeAttack.create(30), 4),
                                Pair.of(SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(2.5F), 3),
                                Pair.of(new BeastChargeTask(troll.chargeDuration(), troll.maxChargeCooldown()), 1),
                                Pair.of(new CaveTrollRoarTask(), 2),
                                Pair.of(new CaveTrollSmashTask(), 2)
                        )))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
                )
        );
    }

    private static Optional<? extends LivingEntity> getAttackTarget(CaveTrollEntity troll) {
        return (troll.isSleeping() || troll.isSitting()) ? troll.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY) : troll.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static Optional<? extends LivingEntity> getHurtBy(CaveTrollEntity troll) {
        return troll.getBrain().getMemory(MemoryModuleType.HURT_BY_ENTITY);
    }

    public static void updateActivities(CaveTrollEntity troll) {
        troll.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT, ActivitiesME.TAMED, Activity.IDLE));
        troll.getBrain().updateActivityFromSchedule(troll.level().getDayTime(), troll.level().getGameTime());
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
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.NEAREST_PLAYERS,
                MemoryModulesME.DIG_FOR_FOOD_COOLDOWN,
                MemoryModulesME.FOOD_EATEN_COUNT,
                MemoryModulesME.TAME,
                MemoryModulesME.SITTING,
                MemoryModulesME.ROAR_COOLDOWN,
                MemoryModulesME.SMASH_COOLDOWN,
                MemoryModulesME.ACTION_TIMEOUT
        );
    }
}
