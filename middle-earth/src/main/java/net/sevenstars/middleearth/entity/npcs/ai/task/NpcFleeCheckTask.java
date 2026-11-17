package net.sevenstars.middleearth.entity.npcs.ai.task;

import net.minecraft.entity.LivingEntity;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.NpcBrain;
import net.sevenstars.middleearth.entity.npcs.relations.NpcRelations;
import org.jetbrains.annotations.Nullable;

// Common folk run home from a fight and call the guards instead of fighting back. This notices the danger and starts the
// fleeing activity, which NpcFleeTask carries out
public class NpcFleeCheckTask extends NpcTask {
    private static final int THREAT_RANGE = 16;

    private @Nullable LivingEntity threat = null;

    public NpcFleeCheckTask() {
        super(null);
    }

    @Override
    protected boolean canStart(NpcEntity npc) {
        if(npc.getBrain().hasMemoryModule(MemoryModulesME.FLEEING_FROM))
            return false;
        LivingEntity danger = npc.getTarget() != null ? npc.getTarget() : npc.getAttacker();
        // Game animals aren't a danger
        if(danger == null || !danger.isAlive() || danger == npc.getHuntTarget() || !npc.isTimid())
            return false;
        // Don't run from a friend's stray hit
        if(NpcRelations.of(npc, danger).isFriendly())
            return false;
        if(npc.squaredDistanceTo(danger) > THREAT_RANGE * THREAT_RANGE || npc.isAtHome())
            return false;
        if(!npc.fleesFrom(danger))
            return false;
        threat = danger;
        return true;
    }

    @Override
    protected boolean shouldContinue(NpcEntity npc) {
        return false;
    }

    @Override
    protected void start(NpcEntity npc) {
        npc.getBrain().remember(MemoryModulesME.FLEEING_FROM, threat);
        threat = null;
        // So the fleeing starts on this tick, as the tasks of the activity come after this one
        NpcBrain.updateActivities(npc);
    }
}
