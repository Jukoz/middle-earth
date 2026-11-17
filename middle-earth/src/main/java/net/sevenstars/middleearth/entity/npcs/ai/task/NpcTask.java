package net.sevenstars.middleearth.entity.npcs.ai.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

// Starts, checks whether to go on and ticks only every other tick, which is what the delays and counts in the npc code are
// in, unless it says it runs every tick. It ends as soon as its activity is no longer the active one. A task without an
// activity always runs
public abstract class NpcTask extends MultiTickTask<NpcEntity> {
    private final @Nullable Activity activity;

    protected NpcTask(@Nullable Activity activity) {
        // The default time limit would end a task after a few seconds, and these end themselves
        super(ImmutableMap.of(), Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.activity = activity;
    }

    public static boolean isGoalTick(NpcEntity npc) {
        return (npc.age + npc.getId()) % 2 == 0 || npc.age <= 1;
    }

    public @Nullable Activity getActivity() {
        return activity;
    }

    protected abstract boolean canStart(NpcEntity npc);

    protected abstract boolean shouldContinue(NpcEntity npc);

    protected void start(NpcEntity npc) {
    }

    protected void tick(NpcEntity npc) {
    }

    protected void stop(NpcEntity npc) {
    }

    protected boolean runsEveryTick() {
        return false;
    }

    @Override
    protected final boolean shouldRun(ServerWorld world, NpcEntity npc) {
        return isGoalTick(npc) && canStart(npc);
    }

    @Override
    protected final boolean shouldKeepRunning(ServerWorld world, NpcEntity npc, long time) {
        if(activity != null && !npc.getBrain().hasActivity(activity))
            return false;
        return !isGoalTick(npc) || shouldContinue(npc);
    }

    @Override
    protected final void run(ServerWorld world, NpcEntity npc, long time) {
        start(npc);
    }

    @Override
    protected final void keepRunning(ServerWorld world, NpcEntity npc, long time) {
        if(runsEveryTick() || isGoalTick(npc))
            tick(npc);
    }

    @Override
    protected final void finishRunning(ServerWorld world, NpcEntity npc, long time) {
        stop(npc);
    }
}
