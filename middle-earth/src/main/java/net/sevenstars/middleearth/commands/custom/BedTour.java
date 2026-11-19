package net.sevenstars.middleearth.commands.custom;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.sevenstars.middleearth.block.special.structureManager.BuildSections;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// Walks a builder from bed to bed through what is loaded
class BedTour {
    private static final float BED_VIEW_PITCH = 35;
    private static final Map<UUID, BlockPos> VISITED = new HashMap<>();

    record Stop(BlockPos pos, String build) {}

    static void forget() {
        VISITED.clear();
    }

    static List<Stop> bedsOf(ServerWorld world, ServerPlayerEntity player) {
        Map<BlockBox, String> builds = new LinkedHashMap<>();
        StructureEditSession session = StructureEditSession.claim(player.getUuid());
        if(session != null && session.getDimension().equals(world.getRegistryKey())) {
            for(StructureEditSession.Piece piece : session.getPieces())
                builds.put(piece.box(), piece.template().getPath());
        } else {
            for(BuildSections.Section section : BuildSections.buildAround(world, player.getBlockPos()))
                builds.put(section.box(), section.name());
        }
        List<Stop> beds = new ArrayList<>();
        for(Map.Entry<BlockBox, String> build : builds.entrySet()) {
            for(BlockPos pos : bedHeadsIn(world, build.getKey()))
                beds.add(new Stop(pos, build.getValue()));
        }
        return beds;
    }

    private static List<BlockPos> bedHeadsIn(ServerWorld world, BlockBox box) {
        List<BlockPos> found = new ArrayList<>();
        for(int x = box.getMinX() >> 4; x <= box.getMaxX() >> 4; x++) {
            for(int z = box.getMinZ() >> 4; z <= box.getMaxZ() >> 4; z++) {
                if(!world.isChunkLoaded(x, z))
                    continue;
                for(BlockPos pos : world.getChunk(x, z).getBlockEntityPositions()) {
                    BlockState state = world.getBlockState(pos);
                    if(box.contains(pos) && state.getBlock() instanceof BedBlock && state.get(BedBlock.PART) == BedPart.HEAD)
                        found.add(pos.toImmutable());
                }
            }
        }
        found.sort(Comparator.naturalOrder());
        return found;
    }

    // 0 for the first stop, else a step from the last bed this builder was sent to
    static int indexAfter(UUID builder, List<Stop> beds, int step) {
        if(step == 0)
            return 0;
        BlockPos visited = VISITED.get(builder);
        int last = -1;
        for(int i = 0; i < beds.size(); i++) {
            if(beds.get(i).pos().equals(visited))
                last = i;
        }
        return last < 0 ? 0 : Math.floorMod(last + step, beds.size());
    }

    static void visit(ServerWorld world, ServerPlayerEntity player, BlockPos bed) {
        VISITED.put(player.getUuid(), bed);
        BlockPos stand = NestSurvey.standFor(world, bed);
        if(stand == null)
            stand = bed.up();
        Vec3d from = Vec3d.ofBottomCenter(stand);
        Vec3d to = Vec3d.ofCenter(bed);
        float yaw = stand.getX() == bed.getX() && stand.getZ() == bed.getZ() ? player.getYaw()
                : (float) Math.toDegrees(Math.atan2(to.z - from.z, to.x - from.x)) - 90;
        player.teleport(world, from.x, from.y, from.z, Set.of(), yaw, BED_VIEW_PITCH, false);
    }
}
