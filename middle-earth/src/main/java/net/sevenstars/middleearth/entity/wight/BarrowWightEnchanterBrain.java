package net.sevenstars.middleearth.entity.wight;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;

import java.util.Optional;

public class BarrowWightEnchanterBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super BarrowWightEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    public BarrowWightEnchanterBrain() {
    }

    protected static Brain<?> create(BarrowWightEnchanterEntity barrowWightEntity, Dynamic<?> dynamic) {
        Brain.Profile<BarrowWightEnchanterEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<BarrowWightEnchanterEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        addEnchanterFightActivities(barrowWightEntity, brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    protected static void addCoreActivities(Brain<BarrowWightEnchanterEntity> brain) {
        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);

        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetTask(),
                new UpdateLookControlTask(45, 90),
                new TickCooldownTask(MemoryModulesME.CAST_COOLDOWN),
                //UpdateAttackTargetTask.create((world, npc) -> npc.getHurtBy()),
                UpdateAttackTargetTask.create(BarrowWightBrain::getAttackTarget))
        );
    }

    protected static void addIdleActivities(Brain<BarrowWightEnchanterEntity> brain) {
        brain.setTaskList(
                Activity.IDLE,
                10,
                ImmutableList.of(
                        UpdateAttackTargetTask.<BarrowWightEnchanterEntity>create(BarrowWightBrain::getTarget),
                        makeRandomWanderTask(),
                        FindInteractionTargetTask.create(EntityType.PLAYER, 4)
                )
        );
    }

    private static void addEnchanterFightActivities(BarrowWightEnchanterEntity barrowWightEntity, Brain<BarrowWightEnchanterEntity> brain) {
        brain.setTaskList(
                Activity.FIGHT,
                10,
                ImmutableList.of(
                        UpdateAttackTargetTask.create(BarrowWightEnchanterBrain::getAttackTarget),
                        new BarrowWightIllusionTask(),
                        ForgetAttackTargetTask.create((world, target) -> !isTarget(world, barrowWightEntity, target))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT), Pair.of(MemoryModuleType.WALK_TARGET, MemoryModuleState.VALUE_ABSENT)
                )
        );
    }

    protected static RandomTask<BarrowWightEntity> makeRandomWanderTask() {
        return new RandomTask<>(
                ImmutableList.of(
                        Pair.of(StrollTask.create(0.6F), 2),
                        Pair.of(FindEntityTask.create(EntitiesME.BARROW_WIGHT, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
                        Pair.of(GoToLookTargetTask.create(0.6F, 3), 2),
                        Pair.of(new WaitTask(30, 60), 1)
                )
        );
    }

    protected static Optional<? extends LivingEntity> getAttackTarget(ServerWorld serverWorld, BarrowWightEntity npc) {
        return npc.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    protected static boolean isTarget(ServerWorld world, BarrowWightEntity barrowWightEntity, LivingEntity target) {
        return getTarget(world, barrowWightEntity).filter(targetx -> targetx == target).isPresent();
    }

    protected static Optional<? extends LivingEntity> getTarget(ServerWorld world, BarrowWightEntity barrowWightEntity) {
        Optional<LivingEntity> optional = TargetUtil.getEntity(barrowWightEntity, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(world, barrowWightEntity, (LivingEntity)optional.get())) {
            return optional;
        } else {
            Optional<? extends LivingEntity> optional2 = barrowWightEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
            return optional2.isPresent() ? optional2 : barrowWightEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
        }
    }


    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES
        );
        MEMORY_MODULES = ImmutableList.of(
                // Generic
                MemoryModuleType.MOBS,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.NEAREST_VISIBLE_PLAYER,
                MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
                MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,

                // Fight specific
                MemoryModuleType.ANGRY_AT,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModulesME.CAST_COOLDOWN
        );
    }
}
