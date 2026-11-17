package net.sevenstars.middleearth.entity.npcs.ai.task;

import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.NpcBrain;
import net.sevenstars.middleearth.entity.npcs.ai.task.target.EnemyBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.target.RevengeBehaviour;
import net.sevenstars.middleearth.entity.npcs.ai.task.target.TargetBehaviour;
import org.jetbrains.annotations.Nullable;

// Picks who the npc fights, one behaviour at a time. Each update the one that is running stops if it should, then the
// behaviours in order start if they can take over, which is how a grudge replaces a stranger and not the other way
// around. It is done before anything else, so the fight starts on the tick the target is found
public class NpcTargetTask extends NpcTask {
    private final RevengeBehaviour revenge = new RevengeBehaviour();
    private final EnemyBehaviour enemy = new EnemyBehaviour();
    // In the order they get a turn to start
    private final TargetBehaviour[] behaviours = {revenge, enemy};
    private @Nullable TargetBehaviour running = null;

    public NpcTargetTask() {
        super(null);
    }

    @Override
    protected boolean canStart(NpcEntity npc) {
        return true;
    }

    @Override
    protected boolean shouldContinue(NpcEntity npc) {
        return false;
    }

    @Override
    protected void start(NpcEntity npc) {
        boolean hadTarget = npc.getTarget() != null;
        if(running != null && !running.shouldContinue(npc))
            end(npc);
        for(TargetBehaviour behaviour : behaviours) {
            if(behaviour == running || running != null && behaviour.priority() >= running.priority() || !behaviour.canStart(npc))
                continue;
            if(running != null)
                end(npc);
            running = behaviour;
            behaviour.start(npc);
        }
        // So the old activity stops and the new one starts on this tick, as the tasks of the activity come after this one
        if(hadTarget != (npc.getTarget() != null))
            NpcBrain.updateActivities(npc);
    }

    private void end(NpcEntity npc) {
        TargetBehaviour ended = running;
        running = null;
        ended.stop(npc);
    }
}
