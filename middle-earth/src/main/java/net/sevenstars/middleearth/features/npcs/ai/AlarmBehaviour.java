package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.LivingEntity;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.relations.NpcRelations;

// Common folk who are hit raise the alarm before RevengeBehaviour decides whether to fight back
final class AlarmBehaviour extends Behaviour {
    AlarmBehaviour() {
        super(REVENGE_PRIORITY, NONE, TARGET);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        LivingEntity attacker = npc.getAttacker();
        return npc.isTimid() && attacker != null && !NpcRelations.of(npc, attacker).isFriendly();
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        return false;
    }

    @Override
    void start(NpcEntity npc) {
        LivingEntity attacker = npc.getAttacker();
        if(attacker == null)
            return;
        // At home there's nowhere left to run, so call the guards and fight
        if(npc.isAtHome())
            npc.callGuardsOnce(attacker);
        else
            npc.getAlarm().raiseAlarm(attacker);
    }
}
