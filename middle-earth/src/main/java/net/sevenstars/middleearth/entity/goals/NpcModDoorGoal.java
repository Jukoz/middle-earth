package net.sevenstars.middleearth.entity.goals;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

// Opens the doors of the mod on the way through, and shuts them behind. Any block that is an NpcDoor, where the game's own goal knows only its own doors
public class NpcModDoorGoal extends Goal {
    private static final double REACH = 2.5;
    private static final int NODES_AHEAD = 2;
    private static final int DOOR_HEIGHT = 2;
    // In ticks
    private static final int PASSING_TIME = 30;
    private static final int MAXIMUM_HOLD_TIME = 20 * 8;
    private static final double DOORWAY_REACH = 2;

    private final NpcEntity npc;
    // The part of the door that stays where it is while the rest swings
    private @Nullable BlockPos origin = null;
    private @Nullable BlockPos doorway = null;
    private @Nullable BlockPos leftOpen = null;
    private @Nullable BlockPos leftOpenDoorway = null;
    private int heldTicks;

    public NpcModDoorGoal(NpcEntity npc) {
        this.npc = npc;
    }

    @Override
    public boolean canStart() {
        // A door that could not be shut with somebody in the way is shut once they have gone
        if(leftOpen != null && !isBusy(leftOpenDoorway)) {
            origin = leftOpen;
            set(false);
            origin = null;
            leftOpen = null;
        }
        Path path = npc.getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return false;
        World world = npc.getWorld();
        BlockPos.Mutable probe = new BlockPos.Mutable();
        int last = Math.min(path.getCurrentNodeIndex() + NODES_AHEAD, path.getLength() - 1);
        for(int i = path.getCurrentNodeIndex(); i <= last; i++) {
            PathNode node = path.getNode(i);
            for(int up = 0; up < DOOR_HEIGHT; up++) {
                BlockState state = world.getBlockState(probe.set(node.x, node.y + up, node.z));
                if(!(state.getBlock() instanceof NpcDoor door) || door.isOpen(state) || !NpcDoor.opensFor(state, npc))
                    continue;
                if(npc.squaredDistanceTo(node.x + 0.5, npc.getY(), node.z + 0.5) > REACH * REACH)
                    return false;
                origin = door.getAnchor(probe, state);
                doorway = probe.toImmutable();
                return true;
            }
        }
        return false;
    }

    @Override
    public void start() {
        heldTicks = 0;
        set(true);
    }

    @Override
    public boolean shouldContinue() {
        if(doorway == null || heldTicks >= MAXIMUM_HOLD_TIME)
            return false;
        return heldTicks < PASSING_TIME || isBusy(doorway);
    }

    // Anybody at all in the doorway, a player as much as an npc
    private boolean isBusy(BlockPos at) {
        Box around = new Box(at).expand(DOORWAY_REACH, 1, DOORWAY_REACH);
        return !npc.getWorld().getEntitiesByClass(LivingEntity.class, around, LivingEntity::isAlive).isEmpty();
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        heldTicks++;
    }

    @Override
    public void stop() {
        if(doorway != null && isBusy(doorway)) {
            leftOpen = origin;
            leftOpenDoorway = doorway;
        } else {
            set(false);
        }
        origin = null;
        doorway = null;
    }

    private void set(boolean open) {
        if(origin == null)
            return;
        BlockState state = npc.getWorld().getBlockState(origin);
        if(state.getBlock() instanceof NpcDoor door)
            door.setOpen(npc.getWorld(), origin, state, open, null);
    }
}
