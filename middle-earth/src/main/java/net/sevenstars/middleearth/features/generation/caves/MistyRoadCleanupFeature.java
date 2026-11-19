package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.registration.OreStoneSetRegistryME;
import net.sevenstars.middleearth.features.building.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.CaveProbe;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// cave features run after the structures and grow back into the goblin roads; this clears them again.
// It also turns mithril ore that a mine has left touching air back into stone: the ore feature discards ore beside air,
// but a mine in the next chunk can be placed after the ore beside it.
public class MistyRoadCleanupFeature extends Feature<DefaultFeatureConfig> {
    private static final Set<Identifier> KEEP = Stream.of(
            "deadwood_planks", "deadwood_log", "deadwood_fence", "rotten_planks", "larch_planks", "larch_log", "larch_fence", "larch_ladder",
            "rope", "crude_lantern", "dwarven_lantern", "wall_sconce", "chiseled_stone_bricks", "chiseled_stone_bricks_wall"
    ).map(MiddleEarth::id).collect(Collectors.toUnmodifiableSet());

    private static final int MARGIN = 8; // into the neighbouring chunks, whichever decorates last clears the shared border

    public MistyRoadCleanupFeature() {
        super(DefaultFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        int startX = context.getOrigin().getX() & ~15;
        int startZ = context.getOrigin().getZ() & ~15;
        MiddleEarthMapRuntime map = MiddleEarthMapRuntime.getInstance();
        MistyCaves caves = new MistyCaves(world.getSeed());
        MistyCaves.Road[] roads = caves.roadsNear(map, startX - MARGIN, startZ - MARGIN, 16 + 2 * MARGIN);
        boolean covered = coverMithril(world, startX, startZ);
        if (roads.length == 0) return covered;

        CaveProbe probe = null;
        List<BlockBox> mine = null;
        BlockPos.Mutable pos = new BlockPos.Mutable();
        boolean cleared = false;
        for (MistyCaves.Road road : roads) {
            for (int x = startX - MARGIN; x < startX + 16 + MARGIN; x++) {
                for (int z = startZ - MARGIN; z < startZ + 16 + MARGIN; z++) {
                    int first = caves.roadFirstRow(road, x, z);
                    if (first == Integer.MIN_VALUE) continue;
                    for (int y = first; y < first + 4; y++) {
                        if (!caves.roadOpen(road, x, y, z)) continue;
                        pos.set(x, y, z);
                        BlockState state = world.getBlockState(pos);
                        if (state.isAir() || !state.getFluidState().isEmpty() || KEEP.contains(Registries.BLOCK.getId(state.getBlock()))) continue;
                        if (probe == null) probe = new CaveProbe(world.getSeed(), world);
                        if (probe.solid(x, y, z) || MistyCaves.weight(map, x, z) <= 0) continue;
                        if (mine == null) mine = mineBoxes(world, startX >> 4, startZ >> 4);
                        if (inside(mine, x, y, z)) continue;
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                        cleared = true;
                    }
                }
            }
        }
        return cleared || covered;
    }

    private static boolean coverMithril(StructureWorldAccess world, int startX, int startZ) {
        List<BlockBox> mine = mineBoxes(world, startX >> 4, startZ >> 4);
        if (mine.isEmpty()) return false;
        Block medgonOre = OreStoneSetRegistryME.MEDGON.mithril_ore();
        Block nurgonOre = OreStoneSetRegistryME.NURGON.mithril_ore();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        BlockPos.Mutable beside = new BlockPos.Mutable();
        boolean covered = false;
        for (BlockBox box : mine) {
            for (int x = Math.max(box.getMinX() - 1, startX - 1); x <= Math.min(box.getMaxX() + 1, startX + 16); x++) {
                for (int z = Math.max(box.getMinZ() - 1, startZ - 1); z <= Math.min(box.getMaxZ() + 1, startZ + 16); z++) {
                    for (int y = Math.max(box.getMinY() - 1, world.getBottomY()); y <= Math.min(box.getMaxY() + 1, world.getTopYInclusive()); y++) {
                        BlockState state = world.getBlockState(pos.set(x, y, z));
                        boolean medgon = state.isOf(medgonOre);
                        if (!medgon && !state.isOf(nurgonOre)) continue;
                        boolean exposed = false;
                        for (Direction direction : Direction.values()) {
                            exposed |= world.getBlockState(beside.set(x, y, z).move(direction)).isAir();
                        }
                        if (!exposed) continue;
                        Block host = medgon ? StoneBlockSetRegistryME.MEDGON_SET.baseBlocks.base() : StoneBlockSetRegistryME.NURGON_SET.baseBlocks.base();
                        world.setBlockState(pos, host.getDefaultState(), 2);
                        covered = true;
                    }
                }
            }
        }
        return covered;
    }

    private static boolean inside(List<BlockBox> boxes, int x, int y, int z) {
        for (BlockBox box : boxes) {
            if (box.contains(new BlockPos(x, y, z))) return true;
        }
        return false;
    }

    private static List<BlockBox> mineBoxes(StructureWorldAccess world, int chunkX, int chunkZ) {
        List<BlockBox> boxes = new ArrayList<>();
        Structure mine = world.getRegistryManager().getOrThrow(RegistryKeys.STRUCTURE).getOptionalValue(MiddleEarth.id("misty_dwarf_mine")).orElse(null);
        if (mine == null) return boxes;
        Set<Long> starts = new HashSet<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                Chunk chunk = reachable(world, chunkX + dx, chunkZ + dz, ChunkStatus.STRUCTURE_REFERENCES);
                if (chunk == null) continue;
                for (long packed : chunk.getStructureReferences(mine)) starts.add(packed);
            }
        }
        for (long packed : starts) {
            // a start can be up to 8 chunks away, where only the structure starts of a chunk are available
            Chunk startChunk = reachable(world, ChunkPos.getPackedX(packed), ChunkPos.getPackedZ(packed), ChunkStatus.STRUCTURE_STARTS);
            if (startChunk == null) continue;
            StructureStart start = startChunk.getStructureStart(mine);
            if (start == null) continue;
            for (StructurePiece piece : start.getChildren()) boxes.add(piece.getBoundingBox());
        }
        return boxes;
    }

    // null when the generation region does not reach that chunk at that status; it throws instead of answering
    private static Chunk reachable(StructureWorldAccess world, int chunkX, int chunkZ, ChunkStatus status) {
        if (!world.isChunkLoaded(chunkX, chunkZ)) return null;
        try {
            return world.getChunk(chunkX, chunkZ, status, false);
        } catch (RuntimeException exception) {
            return null;
        }
    }
}
