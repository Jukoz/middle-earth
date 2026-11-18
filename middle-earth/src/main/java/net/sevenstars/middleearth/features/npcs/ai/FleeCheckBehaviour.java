package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.relations.NpcRelations;
import org.jetbrains.annotations.Nullable;

// Common folk run home and call the guards instead of fighting. FleeBehaviour does the running
final class FleeCheckBehaviour extends Behaviour {
    private static final int THREAT_RANGE = 16;

    private @Nullable LivingEntity threat = null;

    FleeCheckBehaviour() {
        super(NONE);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        if(npc.getBrain().hasMemoryModule(MemoryModulesME.FLEEING_FROM))
            return false;
        LivingEntity danger = npc.getTarget() != null ? npc.getTarget() : npc.getAttacker();
        if(danger == null || !danger.isAlive() || danger == npc.getHuntTarget() || !npc.isTimid())
            return false;
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
    boolean shouldContinue(NpcEntity npc) {
        return false;
    }

    @Override
    void start(NpcEntity npc) {
        npc.getBrain().remember(MemoryModulesME.FLEEING_FROM, threat);
        threat = null;
        // Starts fleeing this tick, since the activity's tasks run after the core ones
        NpcBrain.updateActivities(npc);
    }
}
