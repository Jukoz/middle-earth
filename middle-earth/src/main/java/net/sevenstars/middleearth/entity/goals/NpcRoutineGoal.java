package net.sevenstars.middleearth.entity.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine;

import java.util.EnumSet;

public class NpcRoutineGoal extends Goal {
    private final NpcRoutine routine;

    public NpcRoutineGoal(NpcEntity npc, double speed) {
        this.routine = new NpcRoutine(npc, speed);
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK));
    }

    public NpcRoutine getRoutine() {
        return routine;
    }

    @Override
    public boolean canStart() {
        return routine.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return routine.shouldContinue();
    }

    @Override
    public void start() {
        routine.start();
    }

    @Override
    public void stop() {
        routine.stop();
    }

    @Override
    public void tick() {
        routine.tick();
    }
}
