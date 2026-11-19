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
import net.sevenstars.middleearth.block.special.structureManager.features.NestLookup;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;

// Beds in a build being edited, or placed by a player, house nobody
public final class BedSpawning {
    // In ticks
    private static final int CHECK_INTERVAL = 20;
    // Whether the bed is in a generated structure, found once per loaded block entity
    private static final Map<BlockEntity, Boolean> IN_STRUCTURE = new WeakHashMap<>();

    private BedSpawning() {}

    public static void tick(World world, BlockPos pos, BlockState state, BlockEntity blockEntity) {
        if(!(world instanceof ServerWorld serverWorld) || Math.floorMod(world.getTime() + pos.hashCode(), CHECK_INTERVAL) != 0)
            return;
        if(state.get(BedBlock.PART) != BedPart.HEAD || !(blockEntity instanceof BedTagHolder holder) || !(blockEntity instanceof NestHost host))
            return;
        BedTag tag = holder.getBedTag();
        SpawnNestNodeData data = tag == null ? null : NestLookup.nestFor(serverWorld, tag, Optional.empty());
        if(data == null || !inStructure(serverWorld, pos, blockEntity)) {
            if(host.getNest() != null) {
                host.setNest(null);
                StructureManagerService.unregisterHost(world, pos);
                blockEntity.markDirty();
            }
            return;
        }
        SpawnNestManager nest = host.getNest();
        // Tagged again since, so it's someone else's now
        if(nest == null || !nest.getId().equals(data.getId()) || !nest.getHouseholdJob().equals(tag.job())) {
            SpawnNestManager previous = nest;
            nest = new SpawnNestManager(data, bedOf(serverWorld, pos, tag));
            if(previous != null)
                nest.adopt(previous.getEntityUuids());
            host.setNest(nest);
            blockEntity.markDirty();
        }
        StructureManagerService.registerHost(world, pos);
        nest.tick(serverWorld.getTime(), serverWorld, pos);
    }

    private static boolean inStructure(ServerWorld world, BlockPos pos, BlockEntity blockEntity) {
        return IN_STRUCTURE.computeIfAbsent(blockEntity, key -> GeneratedStructures.contains(world, pos));
    }

    // From the build's generated data, including store and links. A bed it doesn't know, like one tagged
    // in an older world, only has its tag
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
