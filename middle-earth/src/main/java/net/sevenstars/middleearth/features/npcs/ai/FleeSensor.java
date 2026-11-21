package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.server.world.ServerWorld;
import net.sevenstars.middleearth.entity.ai.brain.MemoryModulesME;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.relations.NpcAlarm;
import net.sevenstars.middleearth.features.npcs.relations.NpcRelations;

import java.util.Set;

// Common folk run home and call the guards instead of fighting. FleeBehaviour does the running
final class FleeSensor extends Sensor<NpcEntity> {
    private static final int THREAT_RANGE = 16;

    FleeSensor() {
        // Every tick, so the think tick check below sets the pace
        super(1);
    }

    @Override
    public Set<MemoryModuleType<?>> getOutputMemoryModules() {
        return Set.of(MemoryModulesME.FLEEING_FROM);
    }

    @Override
    protected void sense(ServerWorld world, NpcEntity npc) {
        if(!BehaviourTask.isThinkTick(npc) || npc.getBrain().hasMemoryModule(MemoryModulesME.FLEEING_FROM))
            return;
        LivingEntity danger = npc.getTarget() != null ? npc.getTarget() : npc.getAttacker();
        if(danger == null || !danger.isAlive() || danger == npc.getHuntTarget() || !npc.isTimid())
            return;
        if(NpcRelations.of(npc, danger).isFriendly())
            return;
        if(npc.squaredDistanceTo(danger) > THREAT_RANGE * THREAT_RANGE || npc.isAtHome())
            return;
        NpcAlarm alarm = npc.getAlarm();
        alarm.raiseAlarm(danger);
        if(!alarm.decidesToFlee(danger))
            return;
        npc.getBrain().remember(MemoryModulesME.FLEEING_FROM, danger);
        // Starts fleeing this tick, since the activity's tasks start after the sensors
        NpcBrain.updateActivities(npc);
    }
}
