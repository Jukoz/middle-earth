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
import net.sevenstars.middleearth.entity.ai.brain.ActivitiesME;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcAttackTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcDoorTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcFleeCheckTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcFleeTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcIdleTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcLookAtPlayerTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcModDoorTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcPrisonerTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcTargetTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcTask;

import org.jetbrains.annotations.Nullable;

import java.util.List;

// Everything an npc does. The brain is built before the npc's own fields are, so nothing here may read them. The tasks do,
// when they run
public class NpcBrain {
    // Speeds, as multiples of the npc's walking speed
    public static final double WALK_SPEED = 0.8;
    public static final double FLEE_SPEED = 1.15;
    public static final double PRISONER_SPEED = 0.6;
    public static final double BOW_ATTACK_SPEED = 1.0;
    public static final double MELEE_ATTACK_SPEED = 1.2;
    public static final float LOOK_RANGE = 8.0F;

    private static final float SWIM_CHANCE = 0.8F;
    // The tasks of an activity start after the core ones, which is where fleeing is noticed
    private static final int ACTIVITY_TASKS = 10;

    // The first activity that can be done is the one. Fleeing can only be done while the npc is afraid of someone
    private static final List<Activity> PANIC_OR_IDLE = List.of(Activity.PANIC, Activity.IDLE);
    private static final List<Activity> PANIC_OR_CONFINED = List.of(Activity.PANIC, ActivitiesME.CONFINED);
    private static final List<Activity> PANIC_OR_FIGHT = List.of(Activity.PANIC, Activity.FIGHT);

    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModulesME.FLEEING_FROM, MemoryModuleType.ATTACK_TARGET);
    private static final ImmutableList<SensorType<? extends Sensor<? super NpcEntity>>> SENSORS = ImmutableList.of();

    public static Brain.Profile<NpcEntity> createProfile() {
        return Brain.createProfile(MEMORY_MODULES, SENSORS);
    }

    public static Brain<NpcEntity> create(Brain<NpcEntity> brain) {
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                new NpcTargetTask(),
                new StayAboveWaterTask<>(SWIM_CHANCE),
                new NpcDoorTask(),
                new NpcModDoorTask(),
                new NpcFleeCheckTask()
        ));
        brain.setTaskList(Activity.PANIC, ACTIVITY_TASKS, ImmutableList.of(new NpcFleeTask(), new NpcLookAtPlayerTask(Activity.PANIC)),
                ImmutableSet.of(Pair.of(MemoryModulesME.FLEEING_FROM, MemoryModuleState.VALUE_PRESENT)));
        brain.setTaskList(ActivitiesME.CONFINED, ACTIVITY_TASKS, ImmutableList.of(new NpcPrisonerTask(), new NpcLookAtPlayerTask(ActivitiesME.CONFINED)));
        brain.setTaskList(Activity.FIGHT, ACTIVITY_TASKS, ImmutableList.of(new NpcAttackTask()),
                ImmutableSet.of(Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)));
        brain.setTaskList(Activity.IDLE, ACTIVITY_TASKS, ImmutableList.of(new NpcIdleTask()));
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.resetPossibleActivities();
        return brain;
    }

    public static void updateActivities(NpcEntity npc) {
        Brain<NpcEntity> brain = npc.getBrain();
        List<Activity> wanted = PANIC_OR_IDLE;
        if(npc.isConfined() && !npc.isSleeping())
            wanted = PANIC_OR_CONFINED;
        else if(npc.getTarget() != null)
            wanted = PANIC_OR_FIGHT;
        Activity before = current(brain);
        brain.resetPossibleActivities(wanted);
        if(before == current(brain) || !(npc.getEntityWorld() instanceof ServerWorld world))
            return;
        // A task of an activity that has ended stops now, before the tasks of the new one start
        for(Task<? super NpcEntity> task : brain.getRunningTasks())
            if(task instanceof NpcTask npcTask && npcTask.getActivity() != null && !brain.hasActivity(npcTask.getActivity()))
                npcTask.stop(world, npc, world.getTime());
    }

    // Ends the attack that is going on, for when the weapon in hand no longer fits it
    public static void stopAttack(NpcEntity npc) {
        for(Task<? super NpcEntity> task : npc.getBrain().getRunningTasks())
            if(task instanceof NpcAttackTask attack)
                attack.end(npc);
    }

    private static @Nullable Activity current(Brain<NpcEntity> brain) {
        for(Activity activity : brain.getPossibleActivities())
            if(activity != Activity.CORE)
                return activity;
        return null;
    }
}
