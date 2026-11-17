package net.sevenstars.middleearth.entity.npcs.ai.task;

import net.minecraft.entity.ai.brain.Activity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.task.idle.IdleBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.idle.LookAroundBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.idle.LookAtPlayerBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.idle.RoutineBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.idle.TravelBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.idle.WandererBedBehaviour;
import org.jetbrains.annotations.Nullable;

// Picks what an idle npc does, one behaviour at a time for moving and for looking. Each update the behaviours that are done
// stop, then the behaviours in order start if nothing of higher priority is using what they need, taking over from
// whatever has a lower one, and then the running ones tick. Separate brain tasks can't be taken over from, and stop only
// after the others have started
public class NpcIdleTask extends NpcTask {
    private final RoutineBehaviour routine = new RoutineBehaviour();
    private final TravelBehaviour travel = new TravelBehaviour();
    // In the order they get a turn to start
    private final IdleBehaviour[] behaviours = {routine, new WandererBedBehaviour(), travel, new LookAtPlayerBehaviour(), new LookAroundBehaviour()};
    private @Nullable IdleBehaviour mover = null;
    private @Nullable IdleBehaviour looker = null;

    public NpcIdleTask() {
        super(Activity.IDLE);
    }

    public boolean isRoutineRunning() {
        return routine.isRunning();
    }

    public boolean isTravelRunning() {
        return travel.isRunning();
    }

    @Override
    protected boolean canStart(NpcEntity npc) {
        return true;
    }

    @Override
    protected boolean shouldContinue(NpcEntity npc) {
        return true;
    }

    @Override
    protected boolean runsEveryTick() {
        return true;
    }

    @Override
    protected void tick(NpcEntity npc) {
        if(!isGoalTick(npc)) {
            for(IdleBehaviour behaviour : behaviours)
                if(behaviour.isRunning() && behaviour.runsEveryTick())
                    behaviour.tick(npc);
            return;
        }
        for(IdleBehaviour behaviour : behaviours)
            if(behaviour.isRunning() && !behaviour.shouldContinue(npc))
                end(behaviour, npc);
        for(IdleBehaviour behaviour : behaviours)
            if(!behaviour.isRunning() && canTakeOver(behaviour) && behaviour.canStart(npc))
                begin(behaviour, npc);
        for(IdleBehaviour behaviour : behaviours)
            if(behaviour.isRunning())
                behaviour.tick(npc);
    }

    @Override
    protected void stop(NpcEntity npc) {
        for(IdleBehaviour behaviour : behaviours)
            end(behaviour, npc);
    }

    private boolean canTakeOver(IdleBehaviour behaviour) {
        return (!behaviour.moves() || mover == null || behaviour.priority() < mover.priority())
                && (!behaviour.looks() || looker == null || behaviour.priority() < looker.priority());
    }

    private void begin(IdleBehaviour behaviour, NpcEntity npc) {
        if(behaviour.moves() && mover != null)
            end(mover, npc);
        if(behaviour.looks() && looker != null)
            end(looker, npc);
        if(behaviour.moves())
            mover = behaviour;
        if(behaviour.looks())
            looker = behaviour;
        behaviour.setRunning(true);
        behaviour.start(npc);
    }

    private void end(IdleBehaviour behaviour, NpcEntity npc) {
        if(!behaviour.isRunning())
            return;
        behaviour.setRunning(false);
        behaviour.stop(npc);
        if(mover == behaviour)
            mover = null;
        if(looker == behaviour)
            looker = null;
    }
}
