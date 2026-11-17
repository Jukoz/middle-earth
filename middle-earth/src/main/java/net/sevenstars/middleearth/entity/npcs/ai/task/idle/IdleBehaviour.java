package net.sevenstars.middleearth.entity.npcs.ai.task.idle;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// What an npc does with itself while nothing else needs it. NpcIdleTask runs one behaviour at a time for each of moving and
// looking, and a behaviour with a lower priority number takes over from one with a higher number
public abstract class IdleBehaviour {
    private final int priority;
    private final boolean moves;
    private final boolean looks;
    private boolean running = false;

    protected IdleBehaviour(int priority, boolean moves, boolean looks) {
        this.priority = priority;
        this.moves = moves;
        this.looks = looks;
    }

    public int priority() {
        return priority;
    }

    public boolean moves() {
        return moves;
    }

    public boolean looks() {
        return looks;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    // Also ticks on the ticks in between
    public boolean runsEveryTick() {
        return false;
    }

    public abstract boolean canStart(NpcEntity npc);

    public abstract boolean shouldContinue(NpcEntity npc);

    public void start(NpcEntity npc) {
    }

    public void tick(NpcEntity npc) {
    }

    public void stop(NpcEntity npc) {
    }
}
