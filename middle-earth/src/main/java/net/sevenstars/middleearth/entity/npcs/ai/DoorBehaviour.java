package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

final class DoorBehaviour extends Behaviour {
    // In ticks
    private static final int PASSING_TIME = 20;
    private static final int MAXIMUM_HOLD_TIME = 20 * 10;
    private static final int DOORWAY_CHECK_INTERVAL = 5;

    private static final int NODES_AHEAD = 2;
    private static final double DOORWAY_REACH = 1.25;
    private static final double OWN_REACH = 2;
    private static final double DOOR_REACH = 1.5;

    private BlockPos doorPos = BlockPos.ORIGIN;
    private boolean doorValid;
    private int ticksLeft;
    private int heldTicks;
    private boolean doorwayBusy;

    DoorBehaviour() {
        super(NONE);
    }

    // Measure to the door's middle: a wide npc against a shutter is never near a corner
    @Override
    boolean canStart(NpcEntity npc) {
        if(!npc.horizontalCollision || !(npc.getNavigation() instanceof MobNavigation navigation))
            return false;
        Path path = navigation.getCurrentPath();
        if(path == null || path.isFinished())
            return false;
        for(int i = 0; i < Math.min(path.getCurrentNodeIndex() + NODES_AHEAD, path.getLength()); i++) {
            PathNode node = path.getNode(i);
            BlockPos pos = new BlockPos(node.x, node.y + 1, node.z);
            if(npc.squaredDistanceTo(pos.getX() + 0.5, npc.getY(), pos.getZ() + 0.5) <= DOOR_REACH * DOOR_REACH && DoorBlock.canOpenByHand(npc.getEntityWorld(), pos)) {
                doorPos = pos;
                doorValid = true;
                return true;
            }
        }
        doorPos = npc.getBlockPos().up();
        doorValid = DoorBlock.canOpenByHand(npc.getEntityWorld(), doorPos);
        return doorValid;
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        if(heldTicks >= MAXIMUM_HOLD_TIME)
            return false;
        if(ticksLeft > 0)
            return true;
        boolean stillPassing = npc.squaredDistanceTo(doorPos.getX() + 0.5, npc.getY(), doorPos.getZ() + 0.5) < OWN_REACH * OWN_REACH;
        return stillPassing || doorwayBusy;
    }

    @Override
    void start(NpcEntity npc) {
        ticksLeft = PASSING_TIME;
        heldTicks = 0;
        doorwayBusy = false;
        setDoorOpen(npc, true);
        npc.setPassingDoor(true);
    }

    @Override
    void stop(NpcEntity npc) {
        setDoorOpen(npc, false);
        npc.setPassingDoor(false);
    }

    @Override
    void tick(NpcEntity npc) {
        ticksLeft--;
        heldTicks++;
        if(heldTicks % DOORWAY_CHECK_INTERVAL == 0) {
            Box doorway = new Box(doorPos).expand(DOORWAY_REACH, 0, DOORWAY_REACH);
            doorwayBusy = !npc.getEntityWorld().getEntitiesByClass(NpcEntity.class, doorway, other -> other != npc && other.isAlive()).isEmpty();
            if(!isDoorOpen(npc))
                setDoorOpen(npc, true);
        }
    }

    @Override
    boolean runsEveryTick() {
        return true;
    }

    private boolean isDoorOpen(NpcEntity npc) {
        if(!doorValid)
            return false;
        BlockState state = npc.getEntityWorld().getBlockState(doorPos);
        if(!(state.getBlock() instanceof DoorBlock)) {
            doorValid = false;
            return false;
        }
        return state.get(DoorBlock.OPEN);
    }

    private void setDoorOpen(NpcEntity npc, boolean open) {
        BlockState state = npc.getEntityWorld().getBlockState(doorPos);
        if(!NpcDoor.opensFor(npc.getEntityWorld(), doorPos, state, npc) || !doorValid)
            return;
        if(state.getBlock() instanceof DoorBlock door)
            door.setOpen(npc, npc.getEntityWorld(), state, doorPos, open);
    }
}
