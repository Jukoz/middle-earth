package net.sevenstars.middleearth.entity.npcs.ai.task;

import net.minecraft.entity.ai.brain.Activity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.task.idle.LookAtPlayerBehaviour;

// A glance at a passing player, for the activities that hold the npc's feet but not its eyes
public class NpcLookAtPlayerTask extends NpcTask {
    private final LookAtPlayerBehaviour look = new LookAtPlayerBehaviour();

    public NpcLookAtPlayerTask(Activity activity) {
        super(activity);
    }

    @Override
    protected boolean canStart(NpcEntity npc) {
        return look.canStart(npc);
    }

    @Override
    protected boolean shouldContinue(NpcEntity npc) {
        return look.shouldContinue(npc);
    }

    @Override
    protected void start(NpcEntity npc) {
        look.start(npc);
    }

    @Override
    protected void tick(NpcEntity npc) {
        look.tick(npc);
    }

    @Override
    protected void stop(NpcEntity npc) {
        look.stop(npc);
    }
}
