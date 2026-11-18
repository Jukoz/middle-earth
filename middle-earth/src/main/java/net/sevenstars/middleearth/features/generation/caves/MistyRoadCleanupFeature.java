package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.CaveProbe;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// cave features run after the structures and grow back into the goblin roads; this clears them again
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
        if (roads.length == 0) return false;

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
        return cleared;
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
                for (long packed : world.getChunk(chunkX + dx, chunkZ + dz).getStructureReferences(mine)) starts.add(packed);
            }
        }
        for (long packed : starts) {
            Chunk startChunk = world.getChunk(ChunkPos.getPackedX(packed), ChunkPos.getPackedZ(packed));
            StructureStart start = startChunk.getStructureStart(mine);
            if (start == null) continue;
            for (StructurePiece piece : start.getChildren()) boxes.add(piece.getBoundingBox());
        }
        return boxes;
    }
}
