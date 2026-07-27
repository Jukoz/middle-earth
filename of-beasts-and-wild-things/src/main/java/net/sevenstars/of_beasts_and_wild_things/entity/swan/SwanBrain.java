package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.AnimalMakeLove;
import net.minecraft.world.entity.ai.behavior.BabyFollowAdult;
import net.minecraft.world.entity.ai.behavior.CountDownCooldownTicks;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.FollowTemptation;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.UpdateActivityFromSchedule;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.ItemStack;
import net.sevenstars.api.entity.ai.brain.ActivitiesAPI;
import net.sevenstars.api.entity.ai.brain.MemoryModulesAPI;
import net.sevenstars.api.entity.ai.brain.SchedulesAPI;
import net.sevenstars.api.entity.ai.brain.task.DefendHomeTask;
import net.sevenstars.api.entity.ai.brain.task.MoveTowardsPosMemoryTask;
import net.sevenstars.api.entity.ai.brain.task.StrollAroundHomeTask;
import net.sevenstars.api.entity.ai.brain.task.StrollInWaterTask;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.block.BlocksWT;
import net.sevenstars.of_beasts_and_wild_things.entity.EntitiesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.MemoryModulesWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.SensorsWT;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.*;

import java.util.Optional;
import java.util.function.Predicate;

public class SwanBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super SwanEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(SwanEntity swanEntity, Dynamic<?> dynamic) {
        Brain.Provider<SwanEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
        Brain<SwanEntity> brain = profile.makeBrain(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        addRestActivities(brain);
        addBabyIdleActivities(brain);
        addBabyRestActivities(brain);
        addFightActivities(swanEntity, brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.setSchedule(SchedulesAPI.DEFAULT_SLEEP);

        brain.updateActivityFromSchedule(swanEntity.level().getDayTime(), swanEntity.level().getGameTime());
        return brain;
    }

    private static void addCoreActivities(Brain<SwanEntity> brain) {
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetSink(),
                new LookAtTargetSink(45, 90),
                DefendHomeTask.create(5),
                StartAttacking.create(SwanEntity::getHurtBy),
                new CountDownCooldownTicks(MemoryModulesWT.EGG_COOLDOWN)
        ));
    }

    private static void addIdleActivities(Brain<SwanEntity> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(0, new AnimalMakeLove(EntitiesWT.SWAN)),
                Pair.of(1, new RunOne(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(BlocksWT.BIRD_NEST), 2),
                        Pair.of(RandomStroll.stroll(1.0F), 1)
                ))),
                Pair.of(2, new RunOne(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT), ImmutableList.of(
                        Pair.of(StrollAroundHomeTask.create(0.7f, 20, true), 1),
                        Pair.of(StrollInWaterTask.create(10, 0.7f), 5),
                        Pair.of(new DoNothing(20, 100), 3)
                ))),
                Pair.of(3, StartAttacking.create(SwanBrain::getAttackTarget)),
                Pair.of(4, new FollowTemptation(swan -> 1.0f)),
                Pair.of(99, UpdateActivityFromSchedule.create())
        ));
    }

    private static void addRestActivities(Brain<SwanEntity> brain) {
        brain.addActivity(Activity.REST, ImmutableList.of(
                Pair.of(0, new RunOne(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(BlocksWT.BIRD_NEST), 2),
                        Pair.of(RandomStroll.stroll(1.0F), 1)
                ))),
                Pair.of(1, MoveTowardsPosMemoryTask.create(MemoryModuleType.HOME, 1.0f, 2, 20, 300)),
                Pair.of(2, new SleepOnGroundTask()),
                Pair.of(99, UpdateActivityFromSchedule.create())
        ));
    }

    private static void addFightActivities(SwanEntity swan, Brain<SwanEntity> brain) {
        brain.addActivityWithConditions(Activity.FIGHT, ImmutableList.of(
                Pair.of(0, StartAttacking.create(SwanBrain::getAttackTarget)),
                Pair.of(1, StopAttackingIfTargetInvalid.create(target -> shouldForgetTarget(target, swan))),
                Pair.of(2, SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.25F)),
                Pair.of(3, MeleeAttack.create(30))
        ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
                ));
    }

    private static void addBabyIdleActivities(Brain<SwanEntity> brain) {
        brain.addActivity(ActivitiesAPI.BABY_IDLE, ImmutableList.of(
                Pair.of(0, new RunOne(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(BlocksWT.BIRD_NEST), 2),
                        Pair.of(RandomStroll.stroll(1.0F), 1)
                ))),
                Pair.of(1, new RunOne(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(RandomStroll.stroll(1.0F), 1),
                        Pair.of(new DoNothing(20, 100), 1)
                ))),
                Pair.of(2, BabyFollowAdult.create(UniformInt.of(5, 16), 1f)),
                Pair.of(99, UpdateActivityFromSchedule.create())
        ));
    }

    private static void addBabyRestActivities(Brain<SwanEntity> brain) {
        brain.addActivity(ActivitiesAPI.BABY_REST, ImmutableList.of(
                Pair.of(0, new RunOne(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(BlocksWT.BIRD_NEST), 2),
                        Pair.of(RandomStroll.stroll(1.0F), 1)
                ))),
                Pair.of(1, MoveTowardsPosMemoryTask.create(MemoryModuleType.HOME, 1.0f, 2, 20, 300)),
                Pair.of(2, new SleepOnGroundTask()),
                Pair.of(99, UpdateActivityFromSchedule.create())
        ));
    }

    public static void updateActivities(SwanEntity swan) {
        Optional<LivingEntity> optional = swan.getBrain().getMemoryInternal(MemoryModuleType.ATTACK_TARGET);
        if(swan.isBaby()) {
            swan.getBrain().setActiveActivityToFirstValid(ImmutableList.of());
        }
        else if(optional != null && optional.isPresent()) {
            swan.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT));
        }
        else {
            swan.getBrain().setActiveActivityToFirstValid(ImmutableList.of());
        }
        swan.getBrain().updateActivityFromSchedule(swan.level().getDayTime(), swan.level().getGameTime());
    }

    private static boolean shouldForgetTarget(LivingEntity target, SwanEntity swan) {
        Optional<Boolean> defendingHome = swan.getBrain().getMemoryInternal(MemoryModulesAPI.DEFENDING_HOME);
        Optional<GlobalPos> home = swan.getBrain().getMemoryInternal(MemoryModuleType.HOME);

        if(home != null && home.isPresent() && defendingHome != null && defendingHome.isPresent()) {
            return target.blockPosition().distSqr(home.get().pos()) > 36;
        }

        return false;
    }

    private static Optional<? extends LivingEntity> getAttackTarget(SwanEntity swan) {
        return swan.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    public static Predicate<ItemStack> getTemptItemPredicate() {
        return stack -> stack.is(TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(OfBeastsAndWildThings.MOD_ID, "swan_food")));
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.IS_IN_WATER,
                SensorType.NEAREST_ADULT,
                SensorsWT.SWAN_ATTACKABLES,
                SensorsWT.SWAN_TEMPTATIONS
        );
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.HOME,
                MemoryModuleType.LAST_WOKEN,
                MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.IS_IN_WATER,
                MemoryModuleType.NEAREST_VISIBLE_ADULT,
                MemoryModuleType.IS_TEMPTED,
                MemoryModuleType.TEMPTING_PLAYER,
                MemoryModuleType.TEMPTATION_COOLDOWN_TICKS,
                MemoryModuleType.BREED_TARGET,
                MemoryModuleType.IS_PANICKING,
                MemoryModulesAPI.DEFENDING_HOME,
                MemoryModulesWT.EGG_COOLDOWN
        );
    }
}
