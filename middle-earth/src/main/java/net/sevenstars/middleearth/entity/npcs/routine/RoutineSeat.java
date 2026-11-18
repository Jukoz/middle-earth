package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import org.jetbrains.annotations.Nullable;

class RoutineSeat {
    private static final double SAT_FROM_RANGE = 5;

    private final NpcRoutine routine;
    private final NpcEntity npc;

    private @Nullable Vec3d satFrom = null;

    RoutineSeat(NpcRoutine routine) {
        this.routine = routine;
        this.npc = routine.npc;
    }

    // The floor side between this seat and the next is shut in, so getting up goes back where it sat from
    boolean sitDown(ServerWorld world) {
        Vec3d from = npc.getEntityPos();
        if(routine.meetSeat == null || !routine.canSee(world, routine.meetSeat) || !SeatBlock.seat(world, routine.meetSeat, npc))
            return false;
        satFrom = from;
        return true;
    }

    void standUp() {
        if(!(npc.getVehicle() instanceof SeatEntity seat))
            return;
        BlockPos seatPos = seat.getBlockPos();
        npc.stopRiding();
        // Getting up leaves it standing on the seat, and no path starts off furniture. Put it on the floor it sat from
        Vec3d back = satFrom;
        satFrom = null;
        if(back != null && back.isInRange(Vec3d.ofBottomCenter(seatPos), SAT_FROM_RANGE)) {
            npc.refreshPositionAndAngles(back.x, back.y, back.z, npc.getYaw(), npc.getPitch());
            return;
        }
        BlockPos floor = npc.getEntityWorld() instanceof ServerWorld world ? routine.standAt(world, seatPos) : null;
        if(floor != null && !floor.equals(seatPos))
            npc.refreshPositionAndAngles(floor.getX() + 0.5, floor.getY(), floor.getZ() + 0.5, npc.getYaw(), npc.getPitch());
    }
}
