package net.sevenstars.of_beasts_and_wild_things.entity.swan;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.block.ModBlocks;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModActivity;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModMemoryModules;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModSchedule;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.ModSensors;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.task.*;

import java.util.Optional;
import java.util.function.Predicate;

public class SwanBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super SwanEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(SwanEntity swanEntity, Dynamic<?> dynamic) {
        Brain.Profile<SwanEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<SwanEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        addRestActivities(brain);
        addBabyIdleActivities(brain);
        addBabyRestActivities(brain);
        addFightActivities(swanEntity, brain);
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
                UpdateAttackTargetTask.create((world, swan) -> swan.getHurtBy()),
                new TickCooldownTask(ModMemoryModules.EGG_COOLDOWN)
        ));
    }

    private static void addIdleActivities(Brain<SwanEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(1, new RandomTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(ModBlocks.BIRD_NEST), 2),
                        Pair.of(StrollTask.create(1.0F), 1)
                ))),
                Pair.of(2, new RandomTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_PRESENT), ImmutableList.of(
                        Pair.of(StrollAroundHomeTask.create(0.7f, 20, true), 1),
                        Pair.of(SeekWaterTask.create(10, 1.0f), 4),
                        Pair.of(new WaitTask(20, 100), 3)
                ))),
                Pair.of(3, UpdateAttackTargetTask.create(SwanBrain::getAttackTarget)),
                Pair.of(4, new TemptTask(swan -> 1.0f)),
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

    private static void addFightActivities(SwanEntity swan, Brain<SwanEntity> brain) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                Pair.of(0, UpdateAttackTargetTask.create(SwanBrain::getAttackTarget)),
                Pair.of(1, ForgetAttackTargetTask.create(((world, target) -> shouldForgetTarget(world, target, swan)))),
                Pair.of(2, RangedApproachTask.create(1.25F)),
                Pair.of(3, MeleeAttackTask.create(30))
        ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
                ));
    }

    private static void addBabyIdleActivities(Brain<SwanEntity> brain) {
        brain.setTaskList(ModActivity.BABY_IDLE, ImmutableList.of(
                Pair.of(0, new RandomTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(ModBlocks.BIRD_NEST), 2),
                        Pair.of(StrollTask.create(1.0F), 1)
                ))),
                Pair.of(1, new RandomTask(ImmutableMap.of(MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(StrollTask.create(1.0F), 1),
                        Pair.of(new WaitTask(20, 100), 1)
                ))),
                Pair.of(2, WalkTowardsEntityTask.createNearestVisibleAdult(UniformIntProvider.create(5, 16), 1f)),
                Pair.of(99, ScheduleActivityTask.create())
        ));
    }

    private static void addBabyRestActivities(Brain<SwanEntity> brain) {
        brain.setTaskList(ModActivity.BABY_REST, ImmutableList.of(
                Pair.of(0, new RandomTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_ABSENT), ImmutableList.of(
                        Pair.of(SearchForHomeTask.create(ModBlocks.BIRD_NEST), 2),
                        Pair.of(StrollTask.create(1.0F), 1)
                ))),
                Pair.of(1, MoveTowardsPosMemoryTask.create(MemoryModuleType.HOME, 1.0f, 2, 20, 300)),
                Pair.of(2, new SleepOnGroundTask()),
                Pair.of(99, ScheduleActivityTask.create())
        ));
    }

    public static void updateActivities(SwanEntity swan) {
        Optional<LivingEntity> optional = swan.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if(swan.isBaby()) {
            swan.getBrain().resetPossibleActivities(ImmutableList.of());
        }
        else if(optional != null && optional.isPresent()) {
            swan.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT));
        }
        else {
            swan.getBrain().resetPossibleActivities(ImmutableList.of());
        }
        swan.getBrain().refreshActivities(swan.getWorld().getTimeOfDay(), swan.getWorld().getTime());
    }

    private static boolean shouldForgetTarget(ServerWorld world, LivingEntity target, SwanEntity swan) {
        Optional<Boolean> defendingHome = swan.getBrain().getOptionalMemory(ModMemoryModules.DEFENDING_HOME);
        Optional<GlobalPos> home = swan.getBrain().getOptionalMemory(MemoryModuleType.HOME);

        if(home != null && home.isPresent() && defendingHome != null && defendingHome.isPresent()) {
            return target.getBlockPos().getSquaredDistance(home.get().pos()) > 36;
        }

        return false;
    }

    private static Optional<? extends LivingEntity> getAttackTarget(ServerWorld world, SwanEntity swan) {
        return swan.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    public static Predicate<ItemStack> getTemptItemPredicate() {
        return stack -> stack.isOf(Items.TADPOLE_BUCKET);
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.IS_IN_WATER,
                SensorType.NEAREST_ADULT,
                ModSensors.SWAN_ATTACKABLES,
                ModSensors.SWAN_TEMPTATIONS
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
                ModMemoryModules.DEFENDING_HOME,
                ModMemoryModules.EGG_COOLDOWN
        );
    }
}
