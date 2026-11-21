package net.sevenstars.middleearth.features.npcs;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.features.npcs.pathing.NpcPathNodeMaker;

import java.util.HashSet;
import java.util.Set;

// Opens hatches on the path ahead and shuts them behind
final class HatchOpener {
    // In ticks
    private static final int CHECK_INTERVAL = 4;

    // In blocks
    private static final double OPEN_DISTANCE = 3;
    private static final double CLOSE_DISTANCE = 3.5;

    private final NpcEntity npc;
    private final Set<BlockPos> opened = new HashSet<>();

    HatchOpener(NpcEntity npc) {
        this.npc = npc;
    }

    void tick(ServerWorld world) {
        if(npc.age % CHECK_INTERVAL != 0)
            return;
        opened.removeIf(hatch -> {
            if(hatch.isWithinDistance(npc.getEntityPos(), CLOSE_DISTANCE))
                return false;
            close(world, hatch);
            return true;
        });
        Path path = npc.getNavigation().getCurrentPath();
        if(path == null || path.isFinished())
            return;
        for(int i = path.getCurrentNodeIndex(); i < Math.min(path.getCurrentNodeIndex() + 2, path.getLength()); i++) {
            BlockPos node = path.getNodePos(i);
            for(BlockPos pos : new BlockPos[]{node, node.up()}) {
                if(!pos.isWithinDistance(npc.getEntityPos(), OPEN_DISTANCE))
                    continue;
                BlockState state = world.getBlockState(pos);
                if(NpcPathNodeMaker.isHatch(state) && !state.get(Properties.OPEN) && NpcDoor.opensFor(world, pos, state, npc)) {
                    world.setBlockState(pos, state.with(Properties.OPEN, true));
                    opened.add(pos);
                }
            }
        }
    }

    void closeAll(ServerWorld world) {
        for(BlockPos hatch : opened)
            close(world, hatch);
        opened.clear();
    }

    private void close(ServerWorld world, BlockPos hatch) {
        if(!world.isChunkLoaded(hatch.getX() >> 4, hatch.getZ() >> 4))
            return;
        BlockState state = world.getBlockState(hatch);
        if(NpcPathNodeMaker.isHatch(state) && state.get(Properties.OPEN))
            world.setBlockState(hatch, state.with(Properties.OPEN, false));
    }
}
