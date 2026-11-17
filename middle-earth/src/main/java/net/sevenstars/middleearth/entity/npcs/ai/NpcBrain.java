package net.sevenstars.middleearth.entity.npcs.ai;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

import java.util.List;
import java.util.Optional;

// Everything an npc does. The brain is built before the npc's own fields are, so nothing here may read them. The behaviours
// do, when they run
public class NpcBrain {
    // As a multiple of the npc's walking speed
    public static final double WALK_SPEED = 0.8;
    static final double BOW_ATTACK_SPEED = 1.0;

    private static final float SWIM_CHANCE = 0.8F;
    // Where the tasks of an activity start, after the core ones
    private static final int FIRST_ACTIVITY_PRIORITY = 10;

    // The first activity that can be done is the one. Fleeing can only be done while the npc is afraid of someone
    private static final List<Activity> PANIC_OR_IDLE = List.of(Activity.PANIC, Activity.IDLE);
    private static final List<Activity> PANIC_OR_CONFINED = List.of(Activity.PANIC, ActivitiesME.CONFINED);

    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModulesME.FLEEING_FROM, MemoryModuleType.ATTACK_TARGET);
    private static final ImmutableList<SensorType<? extends Sensor<? super NpcEntity>>> SENSORS = ImmutableList.of();

    public static Brain.Profile<NpcEntity> createProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    // The tasks are only for npcs on a server, the only ones whose brain runs
    public static Brain<NpcEntity> create(Brain<NpcEntity> brain, World world) {
        if(world.isClient())
            return brain;
        // Who to fight and how to get through doors, each before anything else
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new BehaviourTask(null, new RevengeBehaviour(), new EnemyBehaviour(), new DoorBehaviour(), new ModDoorBehaviour(), new FleeCheckBehaviour()),
                new StayAboveWaterTask<>(SWIM_CHANCE)
        ));
        brain.setTaskList(Activity.PANIC, FIRST_ACTIVITY_PRIORITY, ImmutableList.of(new BehaviourTask(Activity.PANIC, new FleeBehaviour(), new LookAtPlayerBehaviour())),
                ImmutableSet.of(Pair.of(MemoryModulesME.FLEEING_FROM, MemoryModuleState.VALUE_PRESENT)));
        brain.setTaskList(ActivitiesME.CONFINED, FIRST_ACTIVITY_PRIORITY, ImmutableList.of(new BehaviourTask(ActivitiesME.CONFINED, new PrisonerBehaviour(), new LookAtPlayerBehaviour())));
        // The attacks come last, which is where they get to take over from the rest
        brain.setTaskList(Activity.IDLE, FIRST_ACTIVITY_PRIORITY, ImmutableList.of(new BehaviourTask(Activity.IDLE,
                new RoutineBehaviour(), new WandererBedBehaviour(), new TravelBehaviour(), new LookAtPlayerBehaviour(), new LookAroundBehaviour(),
                new MeleeAttackBehaviour(), new BowAttackBehaviour(), new CrossbowAttackBehaviour())));
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void updateActivities(NpcEntity npc) {
        Brain<NpcEntity> brain = npc.getBrain();
        Optional<Activity> before = brain.getFirstPossibleNonCoreActivity();
        brain.resetPossibleActivities(npc.isConfined() && !npc.isSleeping() ? PANIC_OR_CONFINED : PANIC_OR_IDLE);
        if(before.equals(brain.getFirstPossibleNonCoreActivity()) || !(npc.getEntityWorld() instanceof ServerWorld world))
            return;
        // A task of an activity that has ended stops now, before the tasks of the new one start
        for(Task<? super NpcEntity> task : brain.getRunningTasks())
            if(task instanceof BehaviourTask behaviours && behaviours.getActivity() != null && !brain.hasActivity(behaviours.getActivity()))
                behaviours.stop(world, npc, world.getTime());
    }

    // Ends the attack that is going on, for when the weapon in hand no longer fits it
    public static void stopAttack(NpcEntity npc) {
        for(Task<? super NpcEntity> task : npc.getBrain().getRunningTasks())
            if(task instanceof BehaviourTask behaviours)
                behaviours.stopAll(npc, AttackBehaviour.class);
    }

    public static boolean isFleeing(NpcEntity npc) {
        return isRunning(npc, FleeBehaviour.class);
    }

    public static boolean isRoutineRunning(NpcEntity npc) {
        return isRunning(npc, RoutineBehaviour.class);
    }

    public static boolean isTravelling(NpcEntity npc) {
        return isRunning(npc, TravelBehaviour.class);
    }

    private static boolean isRunning(NpcEntity npc, Class<? extends Behaviour> type) {
        for(Task<? super NpcEntity> task : npc.getBrain().getRunningTasks())
            if(task instanceof BehaviourTask behaviours && behaviours.isRunning(type))
                return true;
        return false;
    }
}
