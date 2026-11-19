package net.sevenstars.middleearth.features.npcs.ai;

import net.sevenstars.middleearth.features.npcs.NpcEntity;

final class BehaviourSelector {
    private final Behaviour[] behaviours;
    private final boolean[] running;

    BehaviourSelector(Behaviour... behaviours) {
        this.behaviours = behaviours;
        this.running = new boolean[behaviours.length];
    }

    // Think ticks start and stop behaviours; other ticks only tick ones that run every tick
    void update(NpcEntity npc, boolean thinkTick) {
        if(!thinkTick) {
            for(int i = 0; i < behaviours.length; i++)
                if(running[i] && behaviours[i].runsEveryTick())
                    behaviours[i].tick(npc);
            return;
        }
        for(int i = 0; i < behaviours.length; i++)
            if(running[i] && !behaviours[i].shouldContinue(npc))
                stop(i, npc);
        for(int i = 0; i < behaviours.length; i++)
            if(!running[i] && isFree(i) && behaviours[i].canStart(npc))
                start(i, npc);
        for(int i = 0; i < behaviours.length; i++)
            if(running[i])
                behaviours[i].tick(npc);
    }

    void stopAll(NpcEntity npc) {
        for(int i = 0; i < behaviours.length; i++)
            if(running[i])
                stop(i, npc);
    }

    void stopAll(NpcEntity npc, Class<? extends Behaviour> type) {
        for(int i = 0; i < behaviours.length; i++)
            if(running[i] && type.isInstance(behaviours[i]))
                stop(i, npc);
    }

    boolean isRunning(Class<? extends Behaviour> type) {
        for(int i = 0; i < behaviours.length; i++)
            if(running[i] && type.isInstance(behaviours[i]))
                return true;
        return false;
    }

    private boolean isFree(int wanted) {
        for(int i = 0; i < behaviours.length; i++)
            if(running[i] && (behaviours[i].controls() & behaviours[wanted].needs()) != 0 && behaviours[wanted].priority() >= behaviours[i].priority())
                return false;
        return true;
    }

    private void start(int started, NpcEntity npc) {
        for(int control = Behaviour.MOVE; control <= Behaviour.TARGET; control <<= 1)
            if((behaviours[started].controls() & control) != 0)
                for(int i = 0; i < behaviours.length; i++)
                    if(running[i] && (behaviours[i].controls() & control) != 0)
                        stop(i, npc);
        running[started] = true;
        behaviours[started].start(npc);
    }

    private void stop(int stopped, NpcEntity npc) {
        running[stopped] = false;
        behaviours[stopped].stop(npc);
    }
}
