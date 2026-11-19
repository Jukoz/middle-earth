package net.sevenstars.middleearth.dev;

import net.minecraft.entity.LivingEntity;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.ai.NpcBrain;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;

// Dev tooling for npc routines. Off unless the game starts with -Dme.npcDebug=true
public class NpcDebug {
    public static final boolean ENABLED = Boolean.getBoolean("me.npcDebug");

    public static String text(NpcEntity npc) {
        return who(npc) + ": " + doing(npc);
    }

    private static String who(NpcEntity npc) {
        NpcClass npcClass = npc.getNpcClass();
        String who = npcClass == null ? "npc" : npcClass.getId().getPath();
        return npc.getJobId() == null ? who : who + " [" + npc.getJobId().getPath() + "]";
    }

    private static String doing(NpcEntity npc) {
        LivingEntity target = npc.getTarget();
        if(npc.isSleeping())
            return "sleeping";
        if(NpcBrain.isFleeing(npc))
            return "fleeing home";
        if(npc.getHuntTarget() != null)
            return "hunting " + npc.getHuntTarget().getType().getName().getString();
        if(target != null)
            return "fighting " + target.getType().getName().getString();
        if(NpcBrain.isRoutineRunning(npc))
            return npc.getDayRoutine().debugLabel();
        if(NpcBrain.isTravelling(npc))
            return npc.isEscorting() ? "escorting" : npc.getPartyLeader() != null ? "following party leader"
                    : npc.getTravelDestination() != null || npc.getTravelHeading() != null ? "travelling" : "wandering";
        if(npc.isRestTime())
            return npc.getAssignedBed() == null ? "night, no bed" : "resting at home";
        return "idle";
    }
}
