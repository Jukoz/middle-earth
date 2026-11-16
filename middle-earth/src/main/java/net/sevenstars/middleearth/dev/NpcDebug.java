package net.sevenstars.middleearth.dev;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.entity.goals.NpcFleeHomeGoal;
import net.sevenstars.middleearth.entity.goals.NpcRoutineGoal;
import net.sevenstars.middleearth.entity.goals.NpcTravelGoal;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;

// For working on npc routines: shows what each npc is doing over its head, draws its walks in the world, shows why it does
// nothing, records each task it gives up along with the blocks around it, and adds /npcpath. Off unless the game is
// started with -Dme.npcDebug=true
public class NpcDebug {
    public static final boolean ENABLED = Boolean.getBoolean("me.npcDebug");
    // In ticks
    private static final int LABEL_INTERVAL = 5;

    // What the npc is doing, as its name. The name is saved with the npc like any other
    public static void label(NpcEntity npc) {
        if(npc.age % LABEL_INTERVAL != 0)
            return;
        String label = who(npc) + ": " + doing(npc);
        if(npc.getCustomName() == null || !npc.getCustomName().getString().equals(label)) {
            npc.setCustomName(Text.literal(label));
            npc.setCustomNameVisible(true);
        }
    }

    private static String who(NpcEntity npc) {
        NpcClass npcClass = npc.getNpcClass();
        String who = npcClass == null ? "npc" : npcClass.getId().getPath();
        return npc.getJobId() == null ? who : who + " [" + npc.getJobId().getPath() + "]";
    }

    private static String doing(NpcEntity npc) {
        boolean flee = false, routine = false, travel = false;
        for(PrioritizedGoal goal : npc.goalSelector.getGoals()) {
            if(!goal.isRunning())
                continue;
            if(goal.getGoal() instanceof NpcFleeHomeGoal) flee = true;
            if(goal.getGoal() instanceof NpcRoutineGoal) routine = true;
            if(goal.getGoal() instanceof NpcTravelGoal) travel = true;
        }
        LivingEntity target = npc.getTarget();
        NpcRoutineGoal routineGoal = npc.getRoutineGoal();
        if(npc.isSleeping())
            return "sleeping";
        if(flee)
            return "fleeing home";
        if(npc.getHuntTarget() != null)
            return "hunting " + npc.getHuntTarget().getType().getName().getString();
        if(target != null)
            return "fighting " + target.getType().getName().getString();
        if(routine && routineGoal != null)
            return routineGoal.debugLabel();
        if(travel)
            return npc.isEscorting() ? "escorting" : npc.getPartyLeader() != null ? "following party leader"
                    : npc.getTravelDestination() != null || npc.getTravelHeading() != null ? "travelling" : "wandering";
        if(npc.isRestTime())
            return npc.getAssignedBed() == null ? "night, no bed" : "resting at home";
        return "idle";
    }
}
