package net.sevenstars.middleearth.dev;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.text.Text;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcFleeTask;
import net.sevenstars.middleearth.entity.npcs.ai.task.NpcIdleTask;
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
        for(Task<? super NpcEntity> task : npc.getBrain().getRunningTasks()) {
            if(task instanceof NpcFleeTask) flee = true;
            if(task instanceof NpcIdleTask idle) {
                routine = idle.isRoutineRunning();
                travel = idle.isTravelRunning();
            }
        }
        LivingEntity target = npc.getTarget();
        if(npc.isSleeping())
            return "sleeping";
        if(flee)
            return "fleeing home";
        if(npc.getHuntTarget() != null)
            return "hunting " + npc.getHuntTarget().getType().getName().getString();
        if(target != null)
            return "fighting " + target.getType().getName().getString();
        if(routine)
            return npc.getDayRoutine().debugLabel();
        if(travel)
            return npc.isEscorting() ? "escorting" : npc.getPartyLeader() != null ? "following party leader"
                    : npc.getTravelDestination() != null || npc.getTravelHeading() != null ? "travelling" : "wandering";
        if(npc.isRestTime())
            return npc.getAssignedBed() == null ? "night, no bed" : "resting at home";
        return "idle";
    }
}
