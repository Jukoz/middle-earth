package net.sevenstars.middleearth.features.generation.chunkgen;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.PaletteProvider;
import net.minecraft.world.chunk.PalettedContainer;
import net.minecraft.world.chunk.ProtoChunk;
import net.minecraft.world.chunk.ReadableContainer;
import net.minecraft.util.collection.PackedIntegerArray;
import net.sevenstars.middleearth.features.generation.caves.BlockBuffer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.LongStream;

// Writes the column blocks into the chunk sections. ProtoChunk.setBlockState does the same plus light and
// heightmap bookkeeping; light is not tracked before the INITIALIZE_LIGHT status, and the heightmaps are rebuilt
// in finish() the way vanilla does after its noise fill. Other chunk types keep the regular path.
// Each section the fill touches is first collected as a plain array of block states and turned into a section in one go,
// which spares the palette from growing block by block; the blocks are the same, only the palette order can differ.
final class ColumnWriter implements BlockBuffer {
    private static final PaletteProvider<BlockState> BLOCK_PALETTE = PaletteProvider.forBlockStates(Block.STATE_IDS);
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
    private static final int SECTION_BLOCKS = 16 * 16 * 16;
    private static final int MAX_PALETTE_SIZE = 256; // beyond it the section needs the global palette
    // the first builds are compared with their source, and a mismatch turns the shortcut off for good
    private static final int CHECKED_BUILDS = 64;
    private static final AtomicInteger builds = new AtomicInteger();
    private static volatile boolean shortcut = true;

    private static final class Pool {
        boolean busy;
        BlockState[][] arrays = new BlockState[0][];
        final int[] indices = new int[SECTION_BLOCKS];
    }

    private static final ThreadLocal<Pool> POOL = ThreadLocal.withInitial(Pool::new);

    private final Chunk chunk;
    private final boolean direct;
    private final ChunkSection[] sections;
    private final int startX;
    private final int startZ;
    private final BlockPos.Mutable pos = new BlockPos.Mutable();
    private final Pool pool;
    private final BlockState[][] active; // the section arrays in use, by section index
    private final BlockState[] fills; // what each active array started as
    private final boolean[] bedrockSections;
    private boolean buffered;

    ColumnWriter(Chunk chunk) {
        this.chunk = chunk;
        this.direct = chunk.getClass() == ProtoChunk.class && !chunk.getStatus().isAtLeast(ChunkStatus.INITIALIZE_LIGHT);
        this.sections = new ChunkSection[chunk.countVerticalSections()];
        this.startX = chunk.getPos().getStartX();
        this.startZ = chunk.getPos().getStartZ();
        Pool pool = direct && shortcut ? POOL.get() : null;
        if (pool != null && !pool.busy) {
            pool.busy = true;
            if (pool.arrays.length < sections.length) pool.arrays = Arrays.copyOf(pool.arrays, sections.length);
            this.pool = pool;
            this.buffered = true;
            this.active = new BlockState[sections.length][];
            this.fills = new BlockState[sections.length];
            this.bedrockSections = new boolean[sections.length];
        } else {
            this.pool = null;
            this.active = null;
            this.fills = null;
            this.bedrockSections = null;
        }
    }

    boolean outOfHeightLimit(int y) {
        return chunk.isOutOfHeightLimit(y);
    }

    void set(int x, int y, int z, BlockState state) {
        if (chunk.isOutOfHeightLimit(y)) return;
        if (!direct) {
            chunk.setBlockState(pos.set(startX + x, y, startZ + z), state);
            return;
        }
        int index = chunk.getSectionIndex(y);
        if (buffered) {
            BlockState[] array = active[index];
            if (array == null) array = open(index);
            if (array != null) {
                array[(y & 15) << 8 | z << 4 | x] = state;
                return;
            }
        }
        ChunkSection section = sections[index];
        if (section == null) section = sections[index] = chunk.getSection(index);
        if (section.isEmpty() && state.isOf(Blocks.AIR)) return;
        section.setBlockState(x, y & 15, z, state, false);
    }

    @Override
    public BlockState get(int x, int y, int z) {
        if (chunk.isOutOfHeightLimit(y)) return Blocks.VOID_AIR.getDefaultState();
        if (!direct) return chunk.getBlockState(pos.set(x, y, z));
        int index = chunk.getSectionIndex(y);
        if (buffered) {
            BlockState[] array = active[index];
            if (array != null) return array[(y & 15) << 8 | (z - startZ) << 4 | (x - startX)];
        }
        ChunkSection section = sections[index] != null ? sections[index] : chunk.getSection(index);
        return section.getBlockState(x - startX, y & 15, z - startZ);
    }

    @Override
    public void put(int x, int y, int z, BlockState state) {
        set(x - startX, y, z - startZ, state);
    }

    // null for a section that did not start out empty or bedrock, which takes the block by block path
    private BlockState[] open(int index) {
        ChunkSection section = sections[index] != null ? sections[index] : chunk.getSection(index);
        BlockState fill;
        if (bedrockSections[index]) fill = BEDROCK;
        else if (section.isEmpty() && !section.hasAny(state -> state != AIR)) fill = AIR;
        else {
            sections[index] = section;
            return null;
        }
        BlockState[] array = pool.arrays[index];
        if (array == null) array = pool.arrays[index] = new BlockState[SECTION_BLOCKS];
        Arrays.fill(array, fill);
        active[index] = array;
        fills[index] = fill;
        return array;
    }

    // Every section below topY becomes one bedrock block, so each keeps a single palette entry
    void fillBedrock(int topY) {
        for (int index = 0; chunk.getBottomY() + (index + 1) * 16 <= topY; index++) {
            if (!direct) {
                for (int y = chunk.getBottomY() + index * 16; y < chunk.getBottomY() + (index + 1) * 16; y++) {
                    for (int x = 0; x < 16; x++) {
                        for (int z = 0; z < 16; z++) chunk.setBlockState(pos.set(startX + x, y, startZ + z), BEDROCK);
                    }
                }
                continue;
            }
            ChunkSection section = new ChunkSection(new PalettedContainer<>(BEDROCK, BLOCK_PALETTE), chunk.getSection(index).getBiomeContainer());
            chunk.getSectionArray()[index] = section;
            sections[index] = section;
            if (bedrockSections != null) bedrockSections[index] = true;
        }
    }

    // everything buffered goes into the chunk, and the rest is written block by block
    void sync() {
        if (!buffered) return;
        flush();
        buffered = false;
    }

    private void flush() {
        for (int index = 0; index < active.length; index++) {
            BlockState[] array = active[index];
            if (array == null) continue;
            active[index] = null;
            ChunkSection old = sections[index] != null ? sections[index] : chunk.getSection(index);
            PalettedContainer<BlockState> container = build(array);
            if (container != null) {
                ChunkSection section = new ChunkSection(container, old.getBiomeContainer());
                chunk.getSectionArray()[index] = section;
                sections[index] = section;
            } else {
                sections[index] = old;
                for (int i = 0; i < SECTION_BLOCKS; i++) {
                    if (array[i] != fills[index]) old.setBlockState(i & 15, i >> 8, i >> 4 & 15, array[i], false);
                }
            }
        }
    }

    // null when the shortcut cannot be trusted
    private PalettedContainer<BlockState> build(BlockState[] array) {
        if (!shortcut) return null;
        List<BlockState> palette = new ArrayList<>();
        int[] indices = pool.indices;
        BlockState last = null;
        int lastIndex = 0;
        for (int i = 0; i < SECTION_BLOCKS; i++) {
            BlockState state = array[i];
            if (state != last) {
                int found = palette.indexOf(state);
                if (found < 0) {
                    found = palette.size();
                    palette.add(state);
                }
                last = state;
                lastIndex = found;
            }
            indices[i] = lastIndex;
        }
        if (palette.size() > MAX_PALETTE_SIZE) return null;
        ReadableContainer.Serialized<BlockState> serialized;
        if (palette.size() == 1) {
            serialized = new ReadableContainer.Serialized<>(palette, Optional.empty());
        } else {
            int bits = Math.max(4, 32 - Integer.numberOfLeadingZeros(palette.size() - 1));
            long[] data = new PackedIntegerArray(bits, SECTION_BLOCKS, indices).getData();
            serialized = new ReadableContainer.Serialized<>(palette, Optional.of(LongStream.of(data)), bits);
        }
        PalettedContainer<BlockState> container = PalettedContainer.read(BLOCK_PALETTE, serialized).result().orElse(null);
        if (container == null) {
            shortcut = false;
            return null;
        }
        if (builds.getAndIncrement() < CHECKED_BUILDS) {
            for (int i = 0; i < SECTION_BLOCKS; i++) {
                if (container.get(i & 15, i >> 8, i >> 4 & 15) != array[i]) {
                    shortcut = false;
                    return null;
                }
            }
        }
        return container;
    }

    void finish() {
        if (direct) {
            if (buffered) flush();
            Heightmap.populateHeightmaps(chunk, chunk.getStatus().getHeightmapTypes());
        }
    }

    void release() {
        if (pool != null) pool.busy = false;
    }
}
