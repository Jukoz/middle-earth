package net.sevenstars.middleearth.entity.npcs.ai;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

final class ModDoorBehaviour extends Behaviour {
    private static final double REACH = 2.5;
    private static final int NODES_AHEAD = 2;
    private static final int DOOR_HEIGHT = 2;
    // In ticks
    private static final int PASSING_TIME = 30;
    private static final int MAXIMUM_HOLD_TIME = 20 * 8;

    private static final double DOORWAY_REACH = 2;

    private @Nullable BlockPos origin = null;
    private @Nullable BlockPos doorway = null;
    private @Nullable BlockPos leftOpen = null;
    private @Nullable BlockPos leftOpenDoorway = null;
    private int heldTicks;

    ModDoorBehaviour() {
        super(NONE);
    }

    @Override
    boolean canStart(NpcEntity npc) {
        // A door that couldn't shut because someone was in the way shuts once they've gone
        if(leftOpen != null && (npc.age + npc.getId()) % 10 == 0 && !isBusy(npc, leftOpenDoorway)) {
            origin = leftOpen;
            set(npc, false);
            origin = null;
            leftOpen = null;
        }
        Path path = npc.getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return false;
        World world = npc.getEntityWorld();
        BlockPos.Mutable probe = new BlockPos.Mutable();
        int last = Math.min(path.getCurrentNodeIndex() + NODES_AHEAD, path.getLength() - 1);
        for(int i = path.getCurrentNodeIndex(); i <= last; i++) {
            PathNode node = path.getNode(i);
            for(int up = 0; up < DOOR_HEIGHT; up++) {
                BlockState state = world.getBlockState(probe.set(node.x, node.y + up, node.z));
                if(!(state.getBlock() instanceof NpcDoor door) || door.isOpen(state) || !NpcDoor.opensFor(world, probe, state, npc))
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
    void start(NpcEntity npc) {
        heldTicks = 0;
        set(npc, true);
    }

    @Override
    boolean shouldContinue(NpcEntity npc) {
        if(doorway == null || heldTicks >= MAXIMUM_HOLD_TIME)
            return false;
        return heldTicks < PASSING_TIME || isBusy(npc, doorway);
    }

    private boolean isBusy(NpcEntity npc, BlockPos at) {
        Box around = new Box(at).expand(DOORWAY_REACH, 1, DOORWAY_REACH);
        return !npc.getEntityWorld().getEntitiesByClass(LivingEntity.class, around, LivingEntity::isAlive).isEmpty();
    }

    @Override
    boolean runsEveryTick() {
        return true;
    }

    @Override
    void tick(NpcEntity npc) {
        heldTicks++;
    }

    @Override
    void stop(NpcEntity npc) {
        if(doorway != null && isBusy(npc, doorway)) {
            leftOpen = origin;
            leftOpenDoorway = doorway;
        } else {
            set(npc, false);
        }
        origin = null;
        doorway = null;
    }

    private void set(NpcEntity npc, boolean open) {
        if(origin == null)
            return;
        BlockState state = npc.getEntityWorld().getBlockState(origin);
        if(state.getBlock() instanceof NpcDoor door)
            door.setOpen(npc.getEntityWorld(), origin, state, open, null);
    }
}
