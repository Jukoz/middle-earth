package net.sevenstars.middleearth.features.npcs.ai;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

final class BehaviourTask extends MultiTickTask<NpcEntity> {
    private final @Nullable Activity activity;
    private final BehaviourSelector selector;

    BehaviourTask(@Nullable Activity activity, Behaviour... behaviours) {
        // The default time limit would end tasks after a few seconds
        super(ImmutableMap.of(), Integer.MAX_VALUE, Integer.MAX_VALUE);
        this.activity = activity;
        this.selector = new BehaviourSelector(behaviours);
    }

    // Every other tick is a think tick, which the delays and counts in the npc code are in. Ids spread
    // npcs over the two kinds
    static boolean isThinkTick(NpcEntity npc) {
        return (npc.age + npc.getId()) % 2 == 0 || npc.age <= 1;
    }

    @Nullable Activity getActivity() {
        return activity;
    }

    boolean isRunning(Class<? extends Behaviour> type) {
        return selector.isRunning(type);
    }

    void stopAll(NpcEntity npc, Class<? extends Behaviour> type) {
        selector.stopAll(npc, type);
    }

    @Override
    protected boolean shouldRun(ServerWorld world, NpcEntity npc) {
        return isThinkTick(npc);
    }

    @Override
    protected boolean shouldKeepRunning(ServerWorld world, NpcEntity npc, long time) {
        return activity == null || npc.getBrain().hasActivity(activity);
    }

    @Override
    protected void keepRunning(ServerWorld world, NpcEntity npc, long time) {
        selector.update(npc, isThinkTick(npc));
    }

    @Override
    protected void finishRunning(ServerWorld world, NpcEntity npc, long time) {
        selector.stopAll(npc);
    }
}
