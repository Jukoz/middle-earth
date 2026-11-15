package net.sevenstars.middleearth.block.special.beds;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.structureManager.GeneratedStructures;
import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.block.special.structureManager.features.SpawnNestManager;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;

import java.util.List;
import java.util.Optional;

// A tagged bed of a generated structure is born in by itself: it spawns whoever its tag says, and again when they are
// gone and the wait is over. Beds in a build being edited, or put down by a player, house nobody
public final class BedSpawning {
    // In ticks, between the looks a bed takes at itself
    private static final int CHECK_INTERVAL = 20;

    private BedSpawning() {}

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(!(world instanceof ServerWorld serverWorld) || Math.floorMod(world.getTime() + pos.hashCode(), CHECK_INTERVAL) != 0)
            return;
        if(state.get(BedBlock.PART) != BedPart.HEAD || !(blockEntity instanceof BedTagHolder holder) || !(blockEntity instanceof NestHost host))
            return;
        BedTag tag = holder.getBedTag();
        SpawnNestNodeData data = tag == null ? null : StructureManagerService.nestFor(serverWorld, tag, Optional.empty());
        if(data == null || !GeneratedStructures.contains(serverWorld, pos)) {
            if(host.getNest() != null) {
                host.setNest(null);
                StructureManagerService.unregisterHost(world, pos);
                blockEntity.markDirty();
            }
            return;
        }
        SpawnNestManager nest = host.getNest();
        // Tagged anew since, the bed is somebody else's from now on
        if(nest == null || !nest.getId().equals(data.getId()) || !nest.getBedJob().equals(tag.job())) {
            nest = new SpawnNestManager(data, bedOf(serverWorld, pos, tag));
            host.setNest(nest);
            blockEntity.markDirty();
        }
        StructureManagerService.registerHost(world, pos);
        nest.tick(serverWorld.getTime(), serverWorld, pos);
    }

    // As the data of its build has it, with its store and what it was linked to. A bed the data does not know, as one
    // tagged in a world that was generated before, has only its tag
    private static StructureFeatureData.Bed bedOf(ServerWorld world, BlockPos pos, BedTag tag) {
        RoutineSite site = RoutineSites.forManager(world, pos);
        if(site != null) {
            for(StructureFeatureData.Bed bed : site.getBeds()) {
                if(bed.pos().equals(pos) && bed.tag().isPresent())
                    return bed;
            }
        }
        List<BlockPos> links = tag.manual() ? tag.links().stream().map(pos::add).toList() : List.of();
        return new StructureFeatureData.Bed(pos, Optional.of(tag), tag.store().map(pos::add), links, tag.manual(), Optional.empty());
    }
}
