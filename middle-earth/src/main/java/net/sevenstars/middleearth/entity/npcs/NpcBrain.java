package net.sevenstars.middleearth.entity.npcs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.LookAtTargetSink;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.MoveToTargetSink;
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
import net.sevenstars.api.entity.ai.brain.MemoryModulesAPI;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.ai.brain.SensorsME;
import net.sevenstars.middleearth.entity.ai.brain.task.npc.NpcMountedApproachTask;
import net.sevenstars.middleearth.entity.ai.brain.task.npc.NpcOnGroundApproachTask;
import net.sevenstars.middleearth.entity.ai.brain.task.npc.NpcRangedApproachTask;
import net.sevenstars.middleearth.entity.ai.brain.task.npc.NpcRangedAttackTask;
import net.sevenstars.middleearth.entity.beasts.cave_troll.CaveTrollEntity;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.data.CombatArchetype;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.MeleeCombatArchetypeRuntimeData;
import net.sevenstars.middleearth.resources.datas.combatarchetypes.runtime.RangedCombatArchetypeRuntimeData;

import java.util.Optional;

public class NpcBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super NpcEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    public NpcBrain() {
    }

    protected static Brain<?> create(Dynamic<?> dynamic) {
        Brain.Provider<NpcEntity> profile = Brain.provider(MEMORY_MODULES, SENSORS);
        Brain<NpcEntity> brain = profile.makeBrain(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.useDefaultActivity();
        return brain;
    }

    public static void setMeleeActivities(Brain<NpcEntity> brain, NpcEntity npcEntity, MeleeCombatArchetypeRuntimeData runtimeData){
        addCoreActivities(brain);

        addIdleActivities(brain);
        addFightActivities(npcEntity, brain);
    }

    public static void setRangedActivities(Brain<NpcEntity> brain, NpcEntity npcEntity, RangedCombatArchetypeRuntimeData runtimeData){
        addCoreActivities(brain);

        addIdleActivities(brain);
        // FIGHT
        float movementSpeed = npcEntity.getFightingMovementSpeed();
        int attackSpeed = npcEntity.getTickAttackSpeedCooldown();

        brain.addActivityWithConditions(Activity.FIGHT, ImmutableList.of(
                        Pair.of(0, StartAttacking.create(NpcBrain::getAttackTarget)),
                        Pair.of(1, StopAttackingIfTargetInvalid.create(target -> shouldForgetTarget(target, npcEntity))),
                        Pair.of(2, NpcRangedAttackTask.create(attackSpeed)),
                        //Pair.of(2, new NpcMountedApproachTask()),
                        Pair.of(3, NpcRangedApproachTask.create(movementSpeed, 10))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
                ));
    }

    public static void updateActivities(NpcEntity npc) {
        Optional<LivingEntity> optionalTarget = npc.getBrain().getMemoryInternal(MemoryModuleType.ATTACK_TARGET);
        if(optionalTarget != null && optionalTarget.isPresent()) {
            npc.getBrain().setActiveActivityToFirstValid(ImmutableList.of(Activity.FIGHT));
        }
        else {
            npc.getBrain().setActiveActivityToFirstValid(ImmutableList.of());
        }
        npc.getBrain().updateActivityFromSchedule(npc.level().getDayTime(), npc.level().getGameTime());
    }


    private static void addCoreActivities(Brain<NpcEntity> brain) {
        brain.eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);

        brain.addActivity(Activity.CORE, 0, ImmutableList.of(
                new MoveToTargetSink(),
                new LookAtTargetSink(45, 90),
                //UpdateAttackTargetTask.create((world, npc) -> npc.getHurtBy()),
                StartAttacking.create(NpcBrain::getAttackTarget))
        );
    }

    private static void addIdleActivities(Brain<NpcEntity> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(
                Pair.of(1, new RunOne(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT),
                    ImmutableList.of(
                        Pair.of(new DoNothing(20, 100), 3)
                ))),
                Pair.of(2, StartAttacking.create(NpcBrain::getAttackTarget)),
                Pair.of(99, UpdateActivityFromSchedule.create())
        ));
    }

    private static void addFightActivities(NpcEntity npc, Brain<NpcEntity> brain) {
        float movementSpeed = npc.getFightingMovementSpeed();
        int attackSpeed = npc.getTickAttackSpeedCooldown();

        brain.addActivityWithConditions(Activity.FIGHT, ImmutableList.of(
                        Pair.of(0, StartAttacking.create(NpcBrain::getAttackTarget)),
                        Pair.of(1, StopAttackingIfTargetInvalid.create(target -> shouldForgetTarget(target, npc))),
                        Pair.of(2, new NpcMountedApproachTask()),
                        Pair.of(3, new NpcOnGroundApproachTask()),
                        Pair.of(4, SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(movementSpeed)),
                        Pair.of(5, MeleeAttack.create(30))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
                ));
    }

    private static Optional<? extends LivingEntity> getAttackTarget(NpcEntity npc) {
        return npc.getBrain().getMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static boolean shouldForgetTarget(LivingEntity target, NpcEntity npc) {
        Optional<Boolean> defendingHome = npc.getBrain().getMemoryInternal(MemoryModulesAPI.DEFENDING_HOME);
        Optional<GlobalPos> home = npc.getBrain().getMemoryInternal(MemoryModuleType.HOME);

        if(home != null && home.isPresent() && defendingHome != null && defendingHome.isPresent()) {
            return target.blockPosition().distSqr(npc.blockPosition()) > 36;
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
                // Generic
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.PATH,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,

                // NPC specific
                MemoryModulesME.STRUCTURE_MANAGER_HOST_POS,
                MemoryModulesME.ASSIGNED_BED_POS,

                // Fight specific
                MemoryModuleType.HOME,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY
        );
    }
}
