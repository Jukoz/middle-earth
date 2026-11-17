package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.dev.NpcDebug;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.npcs.routine.NpcRoutine.Phase;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

class RoutineDebug {
    private static final int DEBUG_NOTE_INTERVAL = 600;

    private static final Set<RoutineSite> DEBUG_LOGGED = new HashSet<>();

    private final NpcRoutine routine;
    private final NpcEntity npc;

    private long debugNoteAt = 0;

    RoutineDebug(NpcRoutine routine) {
        this.routine = routine;
        this.npc = routine.npc;
    }

    String debugLabel() {
        debugLogRing();
        String what = switch (routine.activity) {
            case FISH -> "fishing";
            case HUNT -> "hunting";
            case FORAGE -> "foraging";
            case PATROL -> "patrol";
            case VISIT -> npc.getVehicle() instanceof SeatEntity ? "visiting, seated" : "visiting";
            case WORK -> "at work";
            case POST -> "on guard";
            case ESCORT -> "escort";
            case TABLE -> npc.getVehicle() instanceof SeatEntity ? "sitting at a table" : "table";
            case FARM -> routine.farming.fieldCell == null ? "farming" : "farming at " + routine.farming.fieldCell.toShortString();
            case CHOP -> "chopping";
            case MINE -> "mining";
            case COURT -> npc.getVehicle() instanceof SeatEntity ? "holding court, seated" : "holding court";
            case INSPECT -> "inspecting the posts";
            case FOLLOW -> "following its lord";
        };
        String load = routine.carried == null ? "" : " [carrying " + routine.carried.getName().getString() + "]";
        return switch (routine.phase) {
            case GOING -> what + " (walking there)";
            case WORKING -> what + (routine.activity == RoutineData.Activity.PATROL ? " (on watch)" : routine.activity == RoutineData.Activity.HUNT ? " (stalking)" : "") + load;
            case HUNTING -> "hunting (chasing prey)";
            case RETURNING -> (routine.toBed ? "going to bed" : routine.homeward ? "going home" : what + " (heading home)") + load;
            case SLEEPING -> "sleeping";
            case NONE -> "idle";
        };
    }

    private void debugLogRing() {
        if(NpcDebug.ENABLED && routine.site != null && routine.activity == RoutineData.Activity.PATROL && routine.phase != Phase.NONE && DEBUG_LOGGED.add(routine.site))
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
        MiddleEarth.LOGGER.logInfoMsg("ROUTEDIAG " + what + " " + (npc.getCustomName() == null ? "" : npc.getCustomName().getString()) + " phase " + routine.phase + " toBed " + routine.toBed
                + " at " + String.format("%.1f,%.1f,%.1f", npc.getX(), npc.getY(), npc.getZ()) + " target " + (target == null ? null : target.toShortString())
                + " leg " + (destination == null ? null : String.format("%.0f,%.0f,%.0f", destination.x, destination.y, destination.z)) + " legs " + routine.walker.legs + " failures " + routine.walker.failures + " path " + route);
    }

    void debugWhyNot(ServerWorld world, String why) {
        if(!NpcDebug.ENABLED || world.getTime() < debugNoteAt) return;
        debugNoteAt = world.getTime() + DEBUG_NOTE_INTERVAL;
        MiddleEarth.LOGGER.logInfoMsg("WHYNOT " + (npc.getCustomName() == null ? "" : npc.getCustomName().getString()) + ": " + why);
    }
}
