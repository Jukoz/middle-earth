package net.sevenstars.middleearth.entity.goals;

import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.ai.goal.DoorInteractGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.Box;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;

// Opens a door on the way through and shuts it afterwards, but not while someone else is still going through
public class NpcDoorInteractGoal extends DoorInteractGoal {
    // In ticks
    private static final int PASSING_TIME = 20;
    private static final int MAXIMUM_HOLD_TIME = 20 * 10;
    private static final int DOORWAY_CHECK_INTERVAL = 5;

    private static final int NODES_AHEAD = 2;
    private static final double DOORWAY_REACH = 1.25;
    private static final double OWN_REACH = 2;
    private static final double DOOR_REACH = 1.5;

    private final boolean delayedClose;
    private int ticksLeft;
    private int heldTicks;
    private boolean doorwayBusy;

    public NpcDoorInteractGoal(MobEntity mob, boolean delayedClose) {
        super(mob);
        this.delayedClose = delayedClose;
    }

    // The game measures to a corner of the door, which an npc a little wider than most, pressed against a shutter beside
    // the doorway, is never near enough to. This measures to the middle of the door instead
    @Override
    public boolean canStart() {
        if(!mob.horizontalCollision || !(mob.getNavigation() instanceof MobNavigation navigation))
            return false;
        Path path = navigation.getCurrentPath();
        if(path == null || path.isFinished())
            return false;
        for(int i = 0; i < Math.min(path.getCurrentNodeIndex() + NODES_AHEAD, path.getLength()); i++) {
            PathNode node = path.getNode(i);
            BlockPos pos = new BlockPos(node.x, node.y + 1, node.z);
            if(mob.squaredDistanceTo(pos.getX() + 0.5, mob.getY(), pos.getZ() + 0.5) <= DOOR_REACH * DOOR_REACH && DoorBlock.canOpenByHand(mob.getEntityWorld(), pos)) {
                doorPos = pos;
                doorValid = true;
                return true;
            }
        }
        doorPos = mob.getBlockPos().up();
        doorValid = DoorBlock.canOpenByHand(mob.getEntityWorld(), doorPos);
        return doorValid;
    }

    @Override
    public boolean shouldContinue() {
        if(!delayedClose || heldTicks >= MAXIMUM_HOLD_TIME)
            return false;
        if(ticksLeft > 0 && super.shouldContinue())
            return true;
        // The npc is still squeezing through, or others are in the doorway
        boolean stillPassing = super.shouldContinue() && mob.squaredDistanceTo(doorPos.getX() + 0.5, mob.getY(), doorPos.getZ() + 0.5) < OWN_REACH * OWN_REACH;
        return stillPassing || doorwayBusy;
    }

    @Override
    public void start() {
        this.ticksLeft = PASSING_TIME;
        this.heldTicks = 0;
        this.doorwayBusy = false;
        this.setDoorOpen(true);
        if(mob instanceof NpcEntity npc)
            npc.setPassingDoor(true);
    }

    @Override
    public void stop() {
        this.setDoorOpen(false);
        if(mob instanceof NpcEntity npc)
            npc.setPassingDoor(false);
    }

    @Override
    public void tick() {
        ticksLeft--;
        heldTicks++;
        if(heldTicks % DOORWAY_CHECK_INTERVAL == 0) {
            Box doorway = new Box(doorPos).expand(DOORWAY_REACH, 0, DOORWAY_REACH);
            doorwayBusy = !mob.getEntityWorld().getEntitiesByClass(NpcEntity.class, doorway, other -> other != mob && other.isAlive()).isEmpty();
            // Someone else may have shut it in the meantime
            if(!isDoorOpen())
                setDoorOpen(true);
        }
        super.tick();
    }

    @Override
    protected void setDoorOpen(boolean open) {
        BlockState blockState = this.mob.getEntityWorld().getBlockState(this.doorPos);
        if(!NpcDoor.opensFor(this.mob.getEntityWorld(), this.doorPos, blockState, mob))
            return;
        super.setDoorOpen(open);
    }
}
