package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// Who holds which post and bed
class SiteClaims {
    private final SiteLayout layout;
    private final Map<BlockPos, UUID> postClaims = new HashMap<>();
    // Separate: a traveller keeps its bed when posts are released
    private final Map<BlockPos, UUID> bedClaims = new HashMap<>();

    SiteClaims(SiteLayout layout) {
        this.layout = layout;
    }

    // A marker the npc already holds counts as free
    @Nullable StructureFeatureData.Marker claimPost(ServerWorld world, UUID npc, BlockPos near, MarkerKind... kinds) {
        StructureFeatureData.Marker best = null;
        for(MarkerKind kind : kinds) {
            for(StructureFeatureData.Marker marker : layout.getMarkers(kind)) {
                if(isFree(world, postClaims.get(marker.pos()), npc) && (best == null || marker.pos().getSquaredDistance(near) < best.pos().getSquaredDistance(near)))
                    best = marker;
            }
        }
        if(best != null) {
            postClaims.values().removeIf(npc::equals);
            postClaims.put(best.pos(), npc);
        }
        return best;
    }

    // Spawns nobody, empty, floor beside it. Its own bed counts as free
    @Nullable BlockPos claimFreeBed(ServerWorld world, UUID npc, BlockPos near) {
        List<StructureFeatureData.Bed> candidates = new ArrayList<>();
        for(StructureFeatureData.Bed bed : layout.getBeds()) {
            if(bed.tag().isEmpty())
                candidates.add(bed);
        }
        candidates.sort((a, b) -> Double.compare(a.pos().getSquaredDistance(near), b.pos().getSquaredDistance(near)));
        for(StructureFeatureData.Bed bed : candidates) {
            BlockPos pos = bed.pos();
            if(!world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4))
                continue;
            BlockState state = world.getBlockState(pos);
            if(!isFree(world, bedClaims.get(pos), npc) || !(state.getBlock() instanceof BedBlock) || state.get(BedBlock.OCCUPIED) || NestSurvey.standFor(world, pos) == null)
                continue;
            bedClaims.values().removeIf(npc::equals);
            bedClaims.put(pos, npc);
            return pos;
        }
        return null;
    }

    private static boolean isFree(ServerWorld world, @Nullable UUID holder, UUID npc) {
        return holder == null || holder.equals(npc) || !(world.getEntity(holder) instanceof NpcEntity holding && holding.isAlive());
    }

    void releasePost(UUID npc) {
        postClaims.values().removeIf(npc::equals);
    }

    void releaseBed(UUID npc) {
        bedClaims.values().removeIf(npc::equals);
    }
}
