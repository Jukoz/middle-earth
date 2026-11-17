package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.sevenstars.middleearth.entity.seat.SeatEntity;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

public class RoutineSeat {
    private static final double SAT_FROM_RANGE = 5;

    private final NpcRoutine engine;
    private final NpcEntity npc;

    // Where the npc stood when it sat down, so getting up puts it back there
    private @Nullable Vec3d satFrom = null;

    public RoutineSeat(NpcRoutine engine) {
        this.engine = engine;
        this.npc = engine.npc;
    }

    // Of the floor around a seat, the side between it and the next seat is shut in, and getting up goes back to where
    // it sat down from
    public boolean sitDown(ServerWorld world) {
        Vec3d from = npc.getEntityPos();
        if(engine.meetSeat == null || !engine.canSee(world, engine.meetSeat) || !SeatBlock.seat(world, engine.meetSeat, npc))
            return false;
        satFrom = from;
        return true;
    }

    public void standUp() {
        if(!(npc.getVehicle() instanceof SeatEntity seat))
            return;
        BlockPos seatPos = seat.getBlockPos();
        npc.stopRiding();
        // Getting up leaves the npc standing on the seat, and there's no path off a piece of furniture. It's put on the
        // floor it sat down from
        Vec3d back = satFrom;
        satFrom = null;
        if(back != null && back.isInRange(Vec3d.ofBottomCenter(seatPos), SAT_FROM_RANGE)) {
            npc.refreshPositionAndAngles(back.x, back.y, back.z, npc.getYaw(), npc.getPitch());
            return;
        }
        BlockPos floor = npc.getEntityWorld() instanceof ServerWorld world ? engine.standAt(world, seatPos) : null;
        if(floor != null && !floor.equals(seatPos))
            npc.refreshPositionAndAngles(floor.getX() + 0.5, floor.getY(), floor.getZ() + 0.5, npc.getYaw(), npc.getPitch());
    }
}
