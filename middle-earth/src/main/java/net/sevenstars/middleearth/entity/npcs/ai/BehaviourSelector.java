package net.sevenstars.middleearth.entity.npcs.ai;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// Runs the behaviours it is given, which are tried in the order given
final class BehaviourSelector {
    private final Behaviour[] behaviours;
    private final boolean[] running;

    BehaviourSelector(Behaviour... behaviours) {
        this.behaviours = behaviours;
        this.running = new boolean[behaviours.length];
    }

    // A think tick stops the behaviours that are done, starts those that can, and then ticks the running ones. The ticks in
    // between only tick those that run every tick
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
            if(running[i] && shares(i, wanted) && behaviours[wanted].priority() >= behaviours[i].priority())
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

    private boolean shares(int first, int second) {
        return (behaviours[first].controls() & behaviours[second].controls()) != 0;
    }
}
