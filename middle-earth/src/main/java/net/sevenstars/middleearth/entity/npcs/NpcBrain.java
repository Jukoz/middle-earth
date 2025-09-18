package net.sevenstars.middleearth.entity.npcs;

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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.GlobalPos;
import net.sevenstars.api.entity.ai.brain.MemoryModulesAPI;
import net.sevenstars.middleearth.entity.ai.brain.SensorsME;

import java.util.Optional;

public class NpcBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super NpcEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    public NpcBrain() {
    }

    protected static Brain<?> create(NpcEntity npcEntity, Dynamic<?> dynamic) {
        Brain.Profile<NpcEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<NpcEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(npcEntity, brain);

        addIdleActivities(npcEntity, brain);
        addFightActivities(npcEntity, brain);

        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.resetPossibleActivities();

        return brain;
    }

    public static void updateActivities(NpcEntity npc) {
        Optional<LivingEntity> optionalTarget = npc.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if(optionalTarget != null && optionalTarget.isPresent()) {
            npc.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT));
        }
        else {
            npc.getBrain().resetPossibleActivities(ImmutableList.of());
        }
        npc.getBrain().refreshActivities(npc.getWorld().getTimeOfDay(), npc.getWorld().getTime());
    }


    private static void addCoreActivities(NpcEntity npcEntity, Brain<NpcEntity> brain) {
        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetTask(),
                new UpdateLookControlTask(45, 90),
                UpdateAttackTargetTask.create((world, npc) -> npc.getHurtBy()),
                UpdateAttackTargetTask.create(NpcBrain::getAttackTarget))
        );
    }

    private static void addIdleActivities(NpcEntity npcEntity, Brain<NpcEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(1, new RandomTask(ImmutableMap.of(MemoryModuleType.HOME, MemoryModuleState.VALUE_PRESENT),
                    ImmutableList.of(
                        Pair.of(new WaitTask(20, 100), 3)
                ))),
                Pair.of(2, UpdateAttackTargetTask.create(NpcBrain::getAttackTarget)),
                Pair.of(99, ScheduleActivityTask.create())
        ));
    }

    private static void addFightActivities(NpcEntity npc, Brain<NpcEntity> brain) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                        Pair.of(0, UpdateAttackTargetTask.create(NpcBrain::getAttackTarget)),
                        Pair.of(1, ForgetAttackTargetTask.create(((world, target) -> shouldForgetTarget(world, target, npc)))),
                        Pair.of(2, RangedApproachTask.create(npc.getMovementSpeed())),
                        Pair.of(3, MeleeAttackTask.create((int)(npc.getAttackSpeed() * 5)))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
                ));
    }



    private static Optional<? extends LivingEntity> getAttackTarget(ServerWorld serverWorld, NpcEntity npc) {
        return npc.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static boolean shouldForgetTarget(ServerWorld world, LivingEntity target, NpcEntity npc) {
        Optional<Boolean> defendingHome = npc.getBrain().getOptionalMemory(MemoryModulesAPI.DEFENDING_HOME);
        Optional<GlobalPos> home = npc.getBrain().getOptionalMemory(MemoryModuleType.HOME);

        if(home != null && home.isPresent() && defendingHome != null && defendingHome.isPresent()) {
            return target.getBlockPos().getSquaredDistance(npc.getBlockPos()) > 36;
        }

        return false;
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_BED,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorsME.NPC_ATTACKABLES
        );
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.PATH,
                MemoryModuleType.HOME,
                MemoryModuleType.LAST_WOKEN,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_BED,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE
        );
    }
}
