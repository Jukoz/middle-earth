package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.dev.NpcDebug;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.routine.NpcRoutine.Phase;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;

class RoutineDebug {
    private static final int DEBUG_NOTE_INTERVAL = 600;

    // Weak, so dropped sites don't pile up
    private static final Set<RoutineSite> DEBUG_LOGGED = Collections.newSetFromMap(new WeakHashMap<>());

    private final NpcRoutine routine;
    private final NpcEntity npc;

    private long debugNoteAt = 0;

    RoutineDebug(NpcRoutine routine) {
        this.routine = routine;
        this.npc = routine.npc;
    }

    String debugLabel() {
        debugLogRing();
        RoutineActivity handler = routine.activityFor(routine.activity);
        String what = handler.label(routine.activity);
        String load = routine.carried() == null ? "" : " [carrying " + routine.carried().getName().getString() + "]";
        return switch (routine.phase()) {
            case GOING -> what + " (walking there)";
            case WORKING -> what + handler.workingNote() + load;
            case HUNTING -> "hunting (chasing prey)";
            case RETURNING -> (routine.isToBed() ? "going to bed" : routine.isHomeward() ? "going home" : what + " (heading home)") + load;
            case SLEEPING -> "sleeping";
            case NONE -> "idle";
        };
    }

    private void debugLogRing() {
        if(NpcDebug.ENABLED && routine.site != null && routine.activity == RoutineData.Activity.PATROL && routine.phase() != Phase.NONE && DEBUG_LOGGED.add(routine.site))
            MiddleEarth.LOGGER.logInfoMsg("RINGDEBUG center " + routine.site.getCenterX() + "," + routine.site.getCenterZ() + " extent " + routine.site.getExtent() + " ring " + routine.site.getRing().stream().map(p -> p.getX() + "," + p.getZ()).toList());
    }

    void debugWhy(String what, ServerWorld world, @Nullable BlockPos target, @Nullable Vec3d destination) {
        if(!NpcDebug.ENABLED)
            return;
        Path path = routine.walker.mover().getNavigation().getCurrentPath();
        String route = "none";
        if(path != null) {
            PathNode end = path.getEnd();
            PathNode next = path.isFinished() ? null : path.getCurrentNode();
            route = "len " + path.getLength() + " at " + path.getCurrentNodeIndex() + " reaches " + path.reachesTarget() + " end " + (end == null ? null : end.x + "," + end.y + "," + end.z)
                    + " next " + (next == null ? null : next.x + "," + next.y + "," + next.z + " " + world.getBlockState(new BlockPos(next.x, next.y, next.z)).getBlock().getName().getString());
        }
        MiddleEarth.LOGGER.logInfoMsg("ROUTEDIAG " + what + " " + (npc.getCustomName() == null ? "" : npc.getCustomName().getString()) + " phase " + routine.phase() + " toBed " + routine.isToBed()
                + " at " + String.format("%.1f,%.1f,%.1f", npc.getX(), npc.getY(), npc.getZ()) + " target " + (target == null ? null : target.toShortString())
                + " leg " + (destination == null ? null : String.format("%.0f,%.0f,%.0f", destination.x, destination.y, destination.z)) + " legs " + routine.walker.legs + " failures " + routine.walker.failures + " path " + route);
    }

    void debugWhyNot(ServerWorld world, String why) {
        if(!NpcDebug.ENABLED || world.getTime() < debugNoteAt) return;
        debugNoteAt = world.getTime() + DEBUG_NOTE_INTERVAL;
        MiddleEarth.LOGGER.logInfoMsg("WHYNOT " + (npc.getCustomName() == null ? "" : npc.getCustomName().getString()) + ": " + why);
    }
}
