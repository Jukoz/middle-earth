package net.sevenstars.middleearth.features.generation.chunkgen;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.features.generation.biomes.caves.DeepThemes;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.caves.BlockBuffer;
import net.sevenstars.middleearth.features.generation.caves.MistyCaveDecorator;
import net.sevenstars.middleearth.features.generation.caves.MistyCaves;
import net.sevenstars.middleearth.features.generation.chunkgen.map.MiddleEarthHeightMap;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.DIRT_HEIGHT;
import static net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.WATER_HEIGHT;
import static net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.caveBlendNoise;

// Cave noise sampled every 2 blocks and blended in between, like vanilla's noise cells.
// Corners sit on fixed world positions, so neighbouring chunks share their edges.
final class CaveSampler {
    private static final int CHUNK_SIZE = 16;
    private static final int CAVE_STRETCH_H = 60;
    private static final int SPAGHETTI_CAVE_STRETCH_H = 90;
    private static final int CAVE_STRETCH_V = 50;
    private static final int CELL = 2;
    private static final int CORNERS = CHUNK_SIZE / CELL + 1;
    private static final int VALUES = 6;
    // the base cave noise is sampled every 4 blocks of height, twice as far apart as in x and z; the Misty channels keep CELL
    private static final int BASE_CELL_Y = 4;
    private static final int PAGE_LAYERS = 16;
    private static final int LAYER_SIZE = CORNERS * CORNERS * VALUES;
    private static final int MISTY_LAYER_SIZE = CORNERS * CORNERS * MistyCaves.VALUES;
    // only for the probe: single corners evaluated on demand in place of whole layer pages, dropped when they get too many
    private static final int MAX_CORNERS = 1024;
    private static final int MARGIN = 2; // blocks beyond the chunk the opening has to see
    private static final int MARGIN_SPAN = CHUNK_SIZE + 2 * MARGIN;
    private static final int MARGIN_CORNERS = CORNERS + 2;
    private static final int MISTY_LATTICE = 8;
    private static final float CULL_MARGIN = 1e-4f;
    private static final int ISLAND_BLOCKS = 12;
    private static final int ISLAND_REACH = 12; // blocks beyond the width of a ravine that are looked at
    private static final int ISLAND_SURFACE_GAP = 12;
    private static final int ISLAND_AIR = 1;
    private static final int ISLAND_ROCK = 2;
    private static final int ISLAND_FLUID = 3;
    private static final int ISLAND_VISITING = 4;
    private static final int ISLAND_BIG = 8;
    private static final byte SOLID_CELL = 1;
    private static final byte CARVING_CELL = 2;
    // the thinnest blob space kept, in blocks of depth below its surface, and the same in noise units where the field is flat
    private static final float THIN_DEPTH = 1.2f;
    private static final float THIN_FLOOR = 0.003f;
    private static final float SPAGHETTI_SCALE = 0.53f;
    private static final float SPAGHETTI_THRESHOLD = 0.09f;
    // per blob channel: the value from which it opens air
    private static final float WATER_BLOB_THRESHOLD = 0.4f; // only below the water height
    private static final float BLOB_THRESHOLD_1 = 0.75f;
    private static final float BLOB_THRESHOLD_2 = 0.8f;
    static final int AIR = 0;
    static final int STONE = 1;
    static final int WATER = 2;

    private final int startX;
    private final int startZ;
    private final int minCellY;
    private final int minBaseCellY;
    private final int baseLayerCount;
    private final int layerCount;
    private final float[][] layerPages;
    private final PagePool pool;
    private final boolean[] layerDone;
    // per cell, 0 not decided yet, else the verdict of cellVerdict; null for the probe, which keeps its own small map
    private final byte[] cellVerdict;
    private final float[] cellValues = new float[8 * VALUES];
    private boolean[] thin; // blob air that openBlobs turned to rock, by column and height
    private int thinMin;
    private int thinHeight;
    // verdicts of cells off the chunk's own grid and of the probe's, by cell (-1 to 8 each way) and layer, 0 until known
    private byte[] looseVerdicts;
    // base corners one cell beyond the chunk, 11 x 11 around the 9 x 9, for the opening of blob air; by layer, filled as needed
    private float[][] marginPages;
    private boolean[] marginDone;
    private Map<Long, Boolean> probeBlob;
    private final MistyCaves misty;
    private final float[] mistyWeight; // null when no corner of the chunk is Misty
    private final float[] riftScale;
    private final float[] streamGates; // the stream's 2D noise per corner, NaN until needed
    private final float[][] mistyPages;
    private final boolean[] mistyDone;
    private final MistyCaves.Shaft[] shafts;
    private final float[][] shaftColumns; // per ravine and corner: distance across, half width
    private final MistyCaves.Lake[] lakes;
    private final float[][] lakeWobble;
    private final int[] surfaceY;
    private final DepthBands.Edges[] cornerEdges; // null where the weight is 0
    private final DeepThemes.Theme[] cornerThemes;
    private final boolean[] cornerBorders;
    private MistyCaves.Worm[] worms; // only when a corner of the chunk lies in the gnawed deeps
    private int riftMinY = Integer.MAX_VALUE;
    private int chamberMinY = Integer.MAX_VALUE;
    private int chamberMaxY = Integer.MIN_VALUE;
    private int streamMinY = Integer.MAX_VALUE;
    private int streamMaxY = Integer.MIN_VALUE;
    private int shaftMinY;
    private int shaftMaxY;
    private int lakeMinY;

    // the blend weights and corner blends of the column being sampled; a stamp marks a slot as filled for that column
    private int columnX = -1;
    private int columnZ = -1;
    private int cellX;
    private int cellZ;
    private float fx;
    private float fz;
    private int stamp;
    private int weightStamp;
    private float columnWeight;
    private final int[] baseStamp;
    private final float[] baseBlend;
    private int[] mistyStamp;
    private float[] mistyBlend;
    private final Map<Long, float[]> baseCorners;
    private final Map<Long, float[]> mistyCorners;

    CaveSampler(int startX, int startZ, int bottomY, int topY, long seed, boolean point) {
        this.cellVerdict = point ? null : new byte[(CORNERS - 1) * (CORNERS - 1) * (Math.floorDiv(topY, BASE_CELL_Y) - Math.floorDiv(bottomY, BASE_CELL_Y) + 2)];
        this.baseCorners = point ? new HashMap<>() : null;
        this.mistyCorners = point ? new HashMap<>() : null;
        this.startX = startX;
        this.startZ = startZ;
        this.minCellY = Math.floorDiv(bottomY, CELL);
        this.layerCount = Math.floorDiv(topY, CELL) - minCellY + 2;
        this.minBaseCellY = Math.floorDiv(bottomY, BASE_CELL_Y);
        this.baseLayerCount = Math.floorDiv(topY, BASE_CELL_Y) - minBaseCellY + 2;
        int mistyPageCount = (layerCount + PAGE_LAYERS - 1) / PAGE_LAYERS;
        int pageCount = (baseLayerCount + PAGE_LAYERS - 1) / PAGE_LAYERS;
        // a page is only read for layers marked done since this sampler began, so pages can be reused as they are
        PagePool pool = point ? null : PAGE_POOL.get();
        if (pool != null && !pool.busy) {
            pool.busy = true;
            this.pool = pool;
            if (pool.base.length < pageCount) pool.base = Arrays.copyOf(pool.base, pageCount);
            if (pool.misty.length < mistyPageCount) pool.misty = Arrays.copyOf(pool.misty, mistyPageCount);
            this.layerPages = pool.base;
        } else {
            this.pool = null;
            this.layerPages = new float[pageCount][];
        }
        this.layerDone = new boolean[baseLayerCount];
        this.baseStamp = new int[baseLayerCount * VALUES];
        this.baseBlend = new float[baseLayerCount * VALUES];

        // weights sit on a world-fixed 8 block lattice and are interpolated to the corners
        int lattice = CHUNK_SIZE / MISTY_LATTICE + 1;
        MiddleEarthMapRuntime map = MiddleEarthMapRuntime.getInstance();
        float[] latticeWeights = new float[lattice * lattice];
        boolean any = false;
        for (int i = 0; i < lattice; i++) {
            for (int j = 0; j < lattice; j++) {
                float w = MistyCaves.weight(map, startX + i * MISTY_LATTICE, startZ + j * MISTY_LATTICE);
                latticeWeights[i * lattice + j] = w;
                any |= w > 0;
            }
        }
        // a lake tunnel runs out of the Misty region, and the chunks it passes through still carry it
        MistyCaves candidate = new MistyCaves(seed);
        MistyCaves.Lake[] tunnels = any ? null : candidate.lakesNear(map, startX, startZ);
        // and the gnawed deeps are bored by worms wherever the floor is deep, Misty or not
        boolean deepHere = false;
        for (int i = 0; i < lattice && !deepHere; i++) {
            for (int j = 0; j < lattice && !deepHere; j++) {
                deepHere = DepthBands.deepFloor(map, startX + i * MISTY_LATTICE, startZ + j * MISTY_LATTICE, seed) < DepthBands.SHALLOW_FLOOR;
            }
        }
        if (!any && !deepHere && tunnels.length == 0) {
            this.misty = null;
            this.mistyWeight = null;
            this.riftScale = null;
            this.streamGates = null;
            this.mistyPages = null;
            this.mistyDone = null;
            this.shafts = null;
            this.shaftColumns = null;
            this.lakes = null;
            this.lakeWobble = null;
            this.surfaceY = null;
            this.cornerEdges = null;
            this.cornerThemes = null;
            this.cornerBorders = null;
            return;
        }
        this.misty = candidate;
        this.mistyWeight = new float[CORNERS * CORNERS];
        this.riftScale = new float[CORNERS * CORNERS];
        this.streamGates = new float[CORNERS * CORNERS];
        Arrays.fill(streamGates, Float.NaN);
        this.mistyPages = pool != null ? pool.misty : new float[mistyPageCount][];
        this.mistyDone = new boolean[layerCount];
        this.mistyStamp = new int[layerCount * MistyCaves.VALUES];
        this.mistyBlend = new float[layerCount * MistyCaves.VALUES];
        this.shafts = misty.shaftsNear(map, startX, startZ);
        this.shaftColumns = new float[shafts.length][CORNERS * CORNERS * 2];
        this.lakes = any ? misty.lakesNear(map, startX, startZ) : tunnels;
        this.lakeWobble = new float[lakes.length][CHUNK_SIZE * CHUNK_SIZE * 2];
        for (int i = 0; i < lakes.length; i++) {
            for (int x = 0; x < CHUNK_SIZE; x++) {
                for (int z = 0; z < CHUNK_SIZE; z++) misty.lakeColumn(lakes[i], startX + x, startZ + z, lakeWobble[i], (x * CHUNK_SIZE + z) * 2);
            }
        }
        this.surfaceY = new int[CHUNK_SIZE * CHUNK_SIZE];
        this.cornerEdges = new DepthBands.Edges[CORNERS * CORNERS];
        this.cornerThemes = new DeepThemes.Theme[CORNERS * CORNERS];
        this.cornerBorders = new boolean[CORNERS * CORNERS];
        int ratio = MISTY_LATTICE / CELL;
        for (int cornerX = 0; cornerX < CORNERS; cornerX++) {
            for (int cornerZ = 0; cornerZ < CORNERS; cornerZ++) {
                int i = Math.min(cornerX / ratio, lattice - 2);
                int j = Math.min(cornerZ / ratio, lattice - 2);
                float fx = (float) (cornerX - i * ratio) / ratio;
                float fz = (float) (cornerZ - j * ratio) / ratio;
                float c00 = latticeWeights[i * lattice + j];
                float c10 = latticeWeights[(i + 1) * lattice + j];
                float c01 = latticeWeights[i * lattice + j + 1];
                float c11 = latticeWeights[(i + 1) * lattice + j + 1];
                float top = c00 + (c10 - c00) * fx;
                float bottom = c01 + (c11 - c01) * fx;
                float w = top + (bottom - top) * fz;
                int corner = cornerX * CORNERS + cornerZ;
                mistyWeight[corner] = w;
                int cornerWorldX = startX + cornerX * CELL;
                int cornerWorldZ = startZ + cornerZ * CELL;
                int cornerFloor = DepthBands.deepFloor(map, cornerWorldX, cornerWorldZ, seed);
                boolean deep = cornerFloor < DepthBands.SHALLOW_FLOOR;
                if (w <= 0 && !deep) continue;
                DepthBands.Edges edges = DepthBands.Edges.of((int) (DIRT_HEIGHT + MiddleEarthHeightMap.getCachedHeight(cornerWorldX, cornerWorldZ)),
                        cornerFloor, caveBlendNoise(cornerWorldX, cornerWorldZ, seed));
                cornerEdges[corner] = edges;
                cornerThemes[corner] = deep ? DeepThemes.at(cornerWorldX, cornerWorldZ, seed) : DeepThemes.Theme.WET;
                cornerBorders[corner] = deep && DeepThemes.border(cornerWorldX, cornerWorldZ, seed);
                if (cornerThemes[corner] == DeepThemes.Theme.GNAWED) {
                    if (worms == null) worms = misty.wormsNear(startX, startZ);
                    chamberMinY = Math.min(chamberMinY, MistyCaves.chamberBottom(edges));
                    chamberMaxY = Math.max(chamberMaxY, edges.top(DepthBands.Band.DEEPS));
                }
                if (w <= 0) continue;
                riftScale[corner] = w * misty.riftCluster(cornerWorldX, cornerWorldZ);
                if (riftScale[corner] > 0) riftMinY = Math.min(riftMinY, MistyCaves.riftBottom(edges));
                chamberMinY = Math.min(chamberMinY, MistyCaves.chamberBottom(edges));
                chamberMaxY = Math.max(chamberMaxY, edges.top(DepthBands.Band.DEEPS));
                if (MistyCaves.streamBand(edges)) {
                    streamMinY = Math.min(streamMinY, edges.middleBottom() - 2);
                    streamMaxY = Math.max(streamMaxY, edges.top(DepthBands.Band.MIDDLE) + 2);
                }
            }
        }
        if (shafts.length > 0) {
            for (int i = 0; i < shafts.length; i++) {
                for (int corner = 0; corner < CORNERS * CORNERS; corner++) {
                    MistyCaves.shaftColumn(shafts[i], startX + corner / CORNERS * CELL, startZ + corner % CORNERS * CELL, shaftColumns[i], corner * 2);
                }
            }
            int[] range = MistyCaves.shaftRange(shafts);
            shaftMinY = range[0];
            shaftMaxY = range[1];
        }
        if (lakes.length > 0) lakeMinY = MistyCaves.lakeMinY(lakes);
    }

    void release() {
        if (pool != null) pool.busy = false;
    }

    private static final class PagePool {
        boolean busy;
        float[][] base = new float[0][];
        float[][] misty = new float[0][];
    }

    private static final ThreadLocal<PagePool> PAGE_POOL = ThreadLocal.withInitial(PagePool::new);

    private void selectColumn(int localX, int localZ) {
        if (localX == columnX && localZ == columnZ) return;
        columnX = localX;
        columnZ = localZ;
        cellX = localX / CELL;
        cellZ = localZ / CELL;
        fx = (float) (localX % CELL) / CELL;
        fz = (float) (localZ % CELL) / CELL;
        stamp++;
    }

    private float weight() {
        if (weightStamp != stamp) {
            columnWeight = bilerp2(mistyWeight, cellX, cellZ, fx, fz);
            weightStamp = stamp;
        }
        return columnWeight;
    }

    int sample(int x, int y, int z) {
        selectColumn(x - startX, z - startZ);
        if (misty == null) return baseSolid(x, y, z) ? STONE : AIR;
        if (lakes.length > 0 && y >= lakeMinY && y <= MistyCaves.lakeMaxY()) {
            int column = ((x - startX) * CHUNK_SIZE + (z - startZ)) * 2;
            for (int i = 0; i < lakes.length; i++) {
                int state = MistyCaves.lakeState(lakes[i], lakeWobble[i][column], lakeWobble[i][column + 1], x, y, z);
                if (state == MistyCaves.LAKE_STONE) return STONE;
                if (state == MistyCaves.LAKE_WATER) return WATER;
                if (state == MistyCaves.LAKE_AIR) return AIR;
            }
            for (MistyCaves.Lake lake : lakes) {
                if (MistyCaves.inLakeTunnel(lake, x, y, z)) return AIR;
            }
        }
        return (gnawedRock(y) || baseSolid(x, y, z)) && mistySolid(x, y, z) ? STONE : AIR;
    }

    // Small lumps of rock left loose beside a ravine or abyss (fewer than ISLAND_BLOCKS blocks, every neighbour of them air)
    // are removed. Only a lump lying wholly inside the chunk, away from its outer columns, is judged, so the result does not
    // depend on which chunk is generated: a lump that reaches a border is left by both chunks.
    void removeIslands(ColumnWriter out) {
        if (shafts == null || shafts.length == 0) return;
        int yMin = Math.max(shaftMinY - 2, minCellY * CELL + 2);
        int[] top = new int[CHUNK_SIZE * CHUNK_SIZE];
        int yMax = Integer.MIN_VALUE;
        boolean any = false;
        for (int lx = 1; lx < 15; lx++) {
            for (int lz = 1; lz < 15; lz++) {
                int column = lx * CHUNK_SIZE + lz;
                top[column] = Integer.MIN_VALUE;
                int corner = ((lx + 1) / CELL) * CORNERS + (lz + 1) / CELL;
                boolean near = false;
                for (int i = 0; i < shafts.length && !near; i++) {
                    float halfWidth = shaftColumns[i][corner * 2 + 1];
                    near = halfWidth > 0 && shaftColumns[i][corner * 2] <= halfWidth + ISLAND_REACH;
                }
                if (!near) continue;
                top[column] = surfaceY[column] - ISLAND_SURFACE_GAP;
                if (top[column] >= yMin) {
                    yMax = Math.max(yMax, top[column]);
                    any = true;
                }
            }
        }
        if (!any) return;
        int height = yMax - yMin + 1;
        // 0 outside what is looked at, then air, rock or fluid, plus the flags below
        byte[] kind = new byte[CHUNK_SIZE * CHUNK_SIZE * height];
        for (int lx = 1; lx < 15; lx++) {
            for (int lz = 1; lz < 15; lz++) {
                int column = lx * CHUNK_SIZE + lz;
                for (int y = yMin; y <= top[column]; y++) {
                    BlockState state = out.get(startX + lx, y, startZ + lz);
                    kind[column * height + y - yMin] = (byte) (state.isAir() ? ISLAND_AIR : state.getFluidState().isEmpty() ? ISLAND_ROCK : ISLAND_FLUID);
                }
            }
        }
        int[] queue = new int[ISLAND_BLOCKS];
        int[] steps = {height * CHUNK_SIZE, -height * CHUNK_SIZE, height, -height, 1, -1};
        for (int column = 0; column < CHUNK_SIZE * CHUNK_SIZE; column++) {
            int lx = column >> 4;
            int lz = column & 15;
            if (lx < 1 || lx > 14 || lz < 1 || lz > 14) continue;
            for (int level = 0; level < height; level++) {
                int start = column * height + level;
                if (kind[start] != ISLAND_ROCK) continue;
                // only rock that touches air can be part of a loose lump
                boolean touches = false;
                for (int step : steps) {
                    int neighbour = start + step;
                    if (step == 1 && level + 1 >= height || step == -1 && level == 0) continue;
                    if (neighbour >= 0 && neighbour < kind.length && (kind[neighbour] & 3) == ISLAND_AIR) {
                        touches = true;
                        break;
                    }
                }
                if (!touches) continue;
                int count = 0;
                queue[count++] = start;
                kind[start] |= ISLAND_VISITING;
                boolean loose = true;
                for (int head = 0; head < count && loose; head++) {
                    int at = queue[head];
                    int atLevel = at % height;
                    int atColumn = at / height;
                    if ((atColumn >> 4) < 1 || (atColumn >> 4) > 14 || (atColumn & 15) < 1 || (atColumn & 15) > 14) {
                        loose = false;
                        break;
                    }
                    for (int s = 0; s < 6 && loose; s++) {
                        if (s == 4 && atLevel + 1 >= height || s == 5 && atLevel == 0) {
                            loose = false;
                            break;
                        }
                        int neighbour = at + steps[s];
                        if (neighbour < 0 || neighbour >= kind.length) {
                            loose = false;
                            break;
                        }
                        int flags = kind[neighbour];
                        int type = flags & 3;
                        if (type == ISLAND_AIR) continue;
                        if (type != ISLAND_ROCK || (flags & ISLAND_BIG) != 0) {
                            loose = false;
                        } else if ((flags & ISLAND_VISITING) == 0) {
                            if (count == ISLAND_BLOCKS - 1) {
                                loose = false;
                            } else {
                                queue[count++] = neighbour;
                                kind[neighbour] |= ISLAND_VISITING;
                            }
                        }
                    }
                }
                for (int i = 0; i < count; i++) {
                    int at = queue[i];
                    kind[at] &= ~ISLAND_VISITING;
                    if (loose) {
                        kind[at] = ISLAND_AIR;
                        out.set((at / height) >> 4, yMin + at % height, (at / height) & 15, Blocks.AIR.getDefaultState());
                    } else {
                        kind[at] |= ISLAND_BIG;
                    }
                }
            }
        }
    }

    void setSurface(int x, int z, int y) {
        if (surfaceY != null) surfaceY[(x - startX) * CHUNK_SIZE + (z - startZ)] = y;
    }

    void decorate(Chunk chunk, BlockBuffer blocks) {
        if (misty == null) return;
        float[] weights = new float[CHUNK_SIZE * CHUNK_SIZE];
        for (int x = 0; x < CHUNK_SIZE; x++) {
            for (int z = 0; z < CHUNK_SIZE; z++) {
                weights[x * CHUNK_SIZE + z] = bilerp2(mistyWeight, x / CELL, z / CELL, (float) (x % CELL) / CELL, (float) (z % CELL) / CELL);
            }
        }
        MistyCaveDecorator.decorate(chunk, blocks, misty, lakes, surfaceY, weights);
    }

    private boolean mistySolid(int x, int y, int z) {
        boolean rift = y >= riftMinY;
        boolean chamber = y >= chamberMinY && y <= chamberMaxY;
        boolean shaft = shafts.length > 0 && y >= shaftMinY && y <= shaftMaxY;
        boolean stream = y >= streamMinY && y <= streamMaxY;
        if (!rift && !chamber && !shaft && !stream) return true;
        if (weight() <= 0 && !gnawedRock(y)) return true;
        int cellY = Math.floorDiv(y, CELL) - minCellY;
        float fy = (float) Math.floorMod(y, CELL) / CELL;
        if (rift && lerp3m(cellY, MistyCaves.RIFT, fy) > 0) return false;
        if (chamber && lerp3m(cellY, MistyCaves.CHAMBER, fy) > 0) return false;
        if (shaft && lerp3m(cellY, MistyCaves.SHAFT, fy) > 0) return false;
        if (stream && lerp3m(cellY, MistyCaves.STREAM, fy) > 0) return false;
        return true;
    }

    // true inside a ravine or abyss, which no wall between regions may cross
    boolean shaftOpen(int x, int y, int z) {
        if (misty == null || shafts.length == 0 || y < shaftMinY || y > shaftMaxY) return false;
        selectColumn(x - startX, z - startZ);
        if (weight() <= 0) return false;
        return lerp3m(Math.floorDiv(y, CELL) - minCellY, MistyCaves.SHAFT, (float) Math.floorMod(y, CELL) / CELL) > 0;
    }

    // true where a rift breaks through the surface skin
    boolean mouthOpen(int x, int z, int surfaceY) {
        if (misty == null) return false;
        int localX = x - startX;
        int localZ = z - startZ;
        float w = bilerp2(mistyWeight, localX / CELL, localZ / CELL, (float) (localX % CELL) / CELL, (float) (localZ % CELL) / CELL);
        return misty.mouthOpen(x, z, w, surfaceY);
    }

    private static float bilerp2(float[] grid, int cellX, int cellZ, float fx, float fz) {
        float c00 = grid[cellX * CORNERS + cellZ];
        float c10 = grid[(cellX + 1) * CORNERS + cellZ];
        float c01 = grid[cellX * CORNERS + cellZ + 1];
        float c11 = grid[(cellX + 1) * CORNERS + cellZ + 1];
        float top = c00 + (c10 - c00) * fx;
        float bottom = c01 + (c11 - c01) * fx;
        return top + (bottom - top) * fz;
    }

    private float lerp3m(int cellY, int v, float fy) {
        float b = mistyBlend(cellY, v);
        float a = mistyBlend(cellY + 1, v);
        return b + (a - b) * fy;
    }

    private float mistyBlend(int layer, int v) {
        int slot = layer * MistyCaves.VALUES + v;
        if (mistyStamp[slot] == stamp) return mistyBlend[slot];
        float c00;
        float c10;
        float c01;
        float c11;
        if (mistyCorners == null) {
            float[] page = mistyLayer(layer);
            int base = (layer % PAGE_LAYERS) * MISTY_LAYER_SIZE;
            c00 = page[base + (cellX * CORNERS + cellZ) * MistyCaves.VALUES + v];
            c10 = page[base + ((cellX + 1) * CORNERS + cellZ) * MistyCaves.VALUES + v];
            c01 = page[base + (cellX * CORNERS + cellZ + 1) * MistyCaves.VALUES + v];
            c11 = page[base + ((cellX + 1) * CORNERS + cellZ + 1) * MistyCaves.VALUES + v];
        } else {
            c00 = mistyCorner(layer, cellX, cellZ)[v];
            c10 = mistyCorner(layer, cellX + 1, cellZ)[v];
            c01 = mistyCorner(layer, cellX, cellZ + 1)[v];
            c11 = mistyCorner(layer, cellX + 1, cellZ + 1)[v];
        }
        float top = c00 + (c10 - c00) * fx;
        float bottom = c01 + (c11 - c01) * fx;
        float blend = top + (bottom - top) * fz;
        mistyBlend[slot] = blend;
        mistyStamp[slot] = stamp;
        return blend;
    }

    private float[] mistyLayer(int cellY) {
        int pageIndex = cellY / PAGE_LAYERS;
        float[] page = mistyPages[pageIndex];
        if (page == null) page = mistyPages[pageIndex] = new float[PAGE_LAYERS * MISTY_LAYER_SIZE];
        if (!mistyDone[cellY]) {
            float[] layer = page;
            int layerBase = (cellY % PAGE_LAYERS) * MISTY_LAYER_SIZE;
            int y = (cellY + minCellY) * CELL;
            boolean rift = y >= riftMinY;
            boolean chamber = y >= chamberMinY && y <= chamberMaxY;
            boolean shaft = shafts.length > 0 && y >= shaftMinY && y <= shaftMaxY;
            boolean stream = y >= streamMinY && y <= streamMaxY;
            float[] wobbles = shaftWobbles(y, shaft);
            for (int cornerX = 0; cornerX < CORNERS; cornerX++) {
                for (int cornerZ = 0; cornerZ < CORNERS; cornerZ++) {
                    int corner = cornerX * CORNERS + cornerZ;
                    mistyCorner(corner, y, rift, chamber, shaft, stream, wobbles, layer, layerBase + corner * MistyCaves.VALUES);
                }
            }
            mistyDone[cellY] = true;
        }
        return page;
    }

    private float[] shaftWobbles(int y, boolean shaft) {
        if (!shaft) return null;
        float[] wobbles = new float[shafts.length];
        for (int i = 0; i < shafts.length; i++) wobbles[i] = MistyCaves.shaftWobble(shafts[i], y);
        return wobbles;
    }

    // the Misty channel margins of one corner at one height, written to out
    private void mistyCorner(int corner, int y, boolean rift, boolean chamber, boolean shaft, boolean stream, float[] wobbles, float[] out, int offset) {
        int x = startX + corner / CORNERS * CELL;
        int z = startZ + corner % CORNERS * CELL;
        float w = mistyWeight[corner];
        out[offset + MistyCaves.RIFT] = rift && riftScale[corner] > 0 ? misty.riftMargin(x, y, z, riftScale[corner], cornerEdges[corner]) : -1;
        out[offset + MistyCaves.CHAMBER] = chamber && (w > 0 || cornerThemes[corner] == DeepThemes.Theme.GNAWED) ? chamberMargin(corner, x, y, z, w) : -1;
        float shaftMargin = -1;
        if (shaft && w > 0) {
            for (int i = 0; i < shafts.length; i++) {
                float halfWidth = shaftColumns[i][corner * 2 + 1];
                if (halfWidth <= 0) continue;
                float scale = MistyCaves.shaftScale(shafts[i], y, cornerEdges[corner], wobbles[i]);
                if (scale <= 0) continue;
                shaftMargin = Math.max(shaftMargin, MistyCaves.shaftMargin(scale, shaftColumns[i][corner * 2], halfWidth, w));
            }
        }
        out[offset + MistyCaves.SHAFT] = shaftMargin;
        if (stream && w > 0 && Float.isNaN(streamGates[corner])) streamGates[corner] = misty.streamGate(x, z);
        out[offset + MistyCaves.STREAM] = stream && w > 0 ? misty.streamMargin(x, y, z, w, cornerEdges[corner], streamGates[corner]) : -1;
    }

    // probe only: the same corner, evaluated alone
    private float[] mistyCorner(int layer, int cornerX, int cornerZ) {
        long key = (long) layer * 256 + cornerX * 16 + cornerZ;
        float[] values = mistyCorners.get(key);
        if (values != null) return values;
        if (mistyCorners.size() >= MAX_CORNERS) mistyCorners.clear();
        int y = (layer + minCellY) * CELL;
        boolean rift = y >= riftMinY;
        boolean chamber = y >= chamberMinY && y <= chamberMaxY;
        boolean shaft = shafts.length > 0 && y >= shaftMinY && y <= shaftMaxY;
        boolean stream = y >= streamMinY && y <= streamMaxY;
        values = new float[MistyCaves.VALUES];
        mistyCorner(cornerX * CORNERS + cornerZ, y, rift, chamber, shaft, stream, shaftWobbles(y, shaft), values, 0);
        mistyCorners.put(key, values);
        return values;
    }

    // the base caves leave the gnawed deeps to the worms
    private boolean gnawedRock(int y) {
        int corner = (cellX + (fx >= 0.5f ? 1 : 0)) * CORNERS + cellZ + (fz >= 0.5f ? 1 : 0);
        return cornerThemes[corner] == DeepThemes.Theme.GNAWED && y < cornerEdges[corner].top(DepthBands.Band.DEEPS);
    }

    // the deep chambers of a region, its worm-bored tunnels, or nothing on a border
    private float chamberMargin(int corner, int x, int y, int z, float weight) {
        if (cornerBorders[corner]) return -1;
        return switch (cornerThemes[corner]) {
            case GNAWED -> MistyCaves.wormMargin(worms, x, y, z, 1f, cornerEdges[corner]); // whatever the Misty weight
            case GROTTO -> misty.chamberMargin(x, y, z, weight, cornerEdges[corner], true);
            default -> misty.chamberMargin(x, y, z, weight, cornerEdges[corner], false);
        };
    }

    private boolean baseSolid(int x, int y, int z) {
        int cellY = Math.floorDiv(y, BASE_CELL_Y) - minBaseCellY;
        int slot = (cellX * (CORNERS - 1) + cellZ) * baseLayerCount + cellY;
        byte verdict;
        if (cellVerdict != null) {
            verdict = cellVerdict[slot];
            if (verdict == 0) cellVerdict[slot] = verdict = cellVerdict(cellY);
        } else {
            verdict = verdictAt(cellX, cellZ, cellY);
        }
        if (verdict == SOLID_CELL) return true;
        int carving = verdict - CARVING_CELL;
        float fy = (float) Math.floorMod(y, BASE_CELL_Y) / BASE_CELL_Y;

        if (carving != 0 && blobCarves(carving, cellY, y, fy) && !thinAt(x - startX, y, z - startZ)) return false;
        // the spaghetti noises are blended signed and only then made absolute, which keeps the thin tunnels
        float spaghetti = Math.abs(lerp3(cellY, 3, fy))
                + Math.abs(lerp3(cellY, 4, fy))
                + Math.abs(lerp3(cellY, 5, fy));
        return spaghetti / 3 > SPAGHETTI_THRESHOLD;
    }

    private boolean blobCarves(int carving, int cellY, int y, float fy) {
        return ((carving & 1) != 0 && y < WATER_HEIGHT && lerp3(cellY, 0, fy) >= WATER_BLOB_THRESHOLD)
                || ((carving & 2) != 0 && lerp3(cellY, 1, fy) >= BLOB_THRESHOLD_1)
                || ((carving & 4) != 0 && lerp3(cellY, 2, fy) >= BLOB_THRESHOLD_2);
    }

    // x and z are local to the chunk
    private boolean thinAt(int x, int y, int z) {
        if (thin != null) return y >= thinMin && y < thinMin + thinHeight && thin[(x * CHUNK_SIZE + z) * thinHeight + y - thinMin];
        return baseCorners != null && carvedThin(x, y, z);
    }

    // the value of one channel at a corner of the world-fixed grid, cx and cz from -1 to 9 along the chunk
    private float cornerValue(int layer, int cx, int cz, int v) {
        if (baseCorners != null) return baseCorner(layer, cx, cz)[v];
        if (cx >= 0 && cx < CORNERS && cz >= 0 && cz < CORNERS) return layer(layer)[(layer % PAGE_LAYERS) * LAYER_SIZE + index(cx, cz, v)];
        if (marginPages == null) {
            marginPages = new float[baseLayerCount][];
            marginDone = new boolean[baseLayerCount * MARGIN_CORNERS * MARGIN_CORNERS];
        }
        float[] page = marginPages[layer];
        if (page == null) page = marginPages[layer] = new float[MARGIN_CORNERS * MARGIN_CORNERS * VALUES];
        int corner = (cx + 1) * MARGIN_CORNERS + cz + 1;
        if (!marginDone[layer * MARGIN_CORNERS * MARGIN_CORNERS + corner]) {
            baseCorner(LayerY.of((layer + minBaseCellY) * BASE_CELL_Y), startX + cx * CELL, startZ + cz * CELL, page, corner * VALUES);
            marginDone[layer * MARGIN_CORNERS * MARGIN_CORNERS + corner] = true;
        }
        return page[corner * VALUES + v];
    }

    private void fillValues(int cx, int cz, int cellY) {
        for (int layer = 0; layer < 2; layer++) {
            for (int dz = 0; dz < 2; dz++) {
                for (int dx = 0; dx < 2; dx++) {
                    for (int v = 0; v < VALUES; v++) {
                        cellValues[((layer * 2 + dz) * 2 + dx) * VALUES + v] = cornerValue(cellY + layer, cx + dx, cz + dz, v);
                    }
                }
            }
        }
    }

    // the verdict of any cell from -1 to 8 along the chunk
    private byte verdictAt(int cx, int cz, int cellY) {
        if (looseVerdicts == null) looseVerdicts = new byte[(CORNERS + 1) * (CORNERS + 1) * baseLayerCount];
        int slot = ((cx + 1) * (CORNERS + 1) + cz + 1) * baseLayerCount + cellY;
        byte verdict = looseVerdicts[slot];
        if (verdict == 0) {
            fillValues(cx, cz, cellY);
            looseVerdicts[slot] = verdict = verdictFromValues(cellY);
        }
        return verdict;
    }

    // blob air at a block of the world-fixed grid, x and z local to the chunk and from -2 to 17: the same verdict and the
    // same blend as the chunk's own columns use
    private boolean blobAt(int x, int y, int z) {
        int cx = Math.floorDiv(x, CELL);
        int cz = Math.floorDiv(z, CELL);
        int cellY = Math.floorDiv(y, BASE_CELL_Y) - minBaseCellY;
        byte verdict = verdictAt(cx, cz, cellY);
        int carving = verdict - CARVING_CELL;
        if (verdict == SOLID_CELL || carving == 0) return false;
        float fx = (float) (x - cx * CELL) / CELL;
        float fz = (float) (z - cz * CELL) / CELL;
        float fy = (float) Math.floorMod(y, BASE_CELL_Y) / BASE_CELL_Y;
        return ((carving & 1) != 0 && y < WATER_HEIGHT && pointBlend(cellY, 0, cx, cz, fx, fz, fy) >= WATER_BLOB_THRESHOLD)
                || ((carving & 2) != 0 && pointBlend(cellY, 1, cx, cz, fx, fz, fy) >= BLOB_THRESHOLD_1)
                || ((carving & 4) != 0 && pointBlend(cellY, 2, cx, cz, fx, fz, fy) >= BLOB_THRESHOLD_2);
    }

    // the arithmetic of baseBlend and lerp3
    private float pointBlend(int layer, int v, int cx, int cz, float fx, float fz, float fy) {
        float b = bilerpCorners(layer, v, cx, cz, fx, fz);
        float a = bilerpCorners(layer + 1, v, cx, cz, fx, fz);
        return b + (a - b) * fy;
    }

    private float bilerpCorners(int layer, int v, int cx, int cz, float fx, float fz) {
        float c00 = cornerValue(layer, cx, cz, v);
        float c10 = cornerValue(layer, cx + 1, cz, v);
        float c01 = cornerValue(layer, cx, cz + 1, v);
        float c11 = cornerValue(layer, cx + 1, cz + 1, v);
        float top = c00 + (c10 - c00) * fx;
        float bottom = c01 + (c11 - c01) * fx;
        return top + (bottom - top) * fz;
    }

    // the probe: a block of blob air is thin when no 3x3x3 cube of blob air covers it, as in openBlobs, but with the
    // neighbourhood read straight off the grid, which needs no margin
    private boolean carvedThin(int x, int y, int z) {
        int top = (minBaseCellY + baseLayerCount - 1) * BASE_CELL_Y;
        int bottom = minBaseCellY * BASE_CELL_Y;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int cy = y + dy;
                    if (cy < bottom || cy >= top) continue;
                    if (probeCore(x + dx, cy, z + dz, bottom, top)) return false;
                }
            }
        }
        return true;
    }

    private boolean probeCore(int x, int y, int z, int bottom, int top) {
        if (!probeBlobAt(x, y, z)) return false;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int cy = y + dy;
                    if (cy >= bottom && cy < top && !probeBlobAt(x + dx, cy, z + dz)) return false;
                }
            }
        }
        return true;
    }

    private boolean probeBlobAt(int x, int y, int z) {
        if (probeBlob == null) probeBlob = new HashMap<>();
        long key = ((long) y * 32 + (x + 8)) * 32 + (z + 8);
        Boolean known = probeBlob.get(key);
        if (known == null) {
            if (probeBlob.size() >= 1 << 16) probeBlob.clear();
            probeBlob.put(key, known = blobAt(x, y, z));
        }
        return known;
    }

    // Blob air (channels 0 to 2) that no 3x3x3 cube of blob air covers is a crack or a lens, and becomes rock; the spaghetti
    // tunnels and the Misty channels do not take part. The rule is a morphological opening. Each block is decided from the
    // blob air within two blocks of it (a cube needs one, a cube's own blocks another), which is read off the world-fixed
    // grid, past the chunk's edges and past the top and bottom of what it fills, so the answer does not depend on which
    // chunk asks. Only the world's own top and bottom count as air.
    void openBlobs(int yTop) {
        if (cellVerdict == null) return;
        int yMin = minBaseCellY * BASE_CELL_Y;
        int worldEnd = (minBaseCellY + baseLayerCount - 1) * BASE_CELL_Y;
        int yEnd = Math.min(yTop + 1 + 2 * MARGIN - 1, worldEnd);
        int decideEnd = Math.min(yTop + 1, yEnd);
        int height = yEnd - yMin;
        if (height < 6) return;
        boolean[] blob = new boolean[MARGIN_SPAN * MARGIN_SPAN * height];
        byte[] cellHas = new byte[CHUNK_SIZE * CHUNK_SIZE * baseLayerCount]; // inner blob air by column and cell
        if (!markChunkBlobs(blob, cellHas, yMin, yEnd, height)) return;
        markRingBlobs(blob, cellHas, yMin, yEnd, height);
        boolean[] core = findCores(blob, height);
        thin = findUncovered(blob, core, yMin, decideEnd, height);
        thinMin = yMin;
        thinHeight = height;
    }

    private static int blobIndex(int x, int z, int height) {
        return ((x + MARGIN) * MARGIN_SPAN + z + MARGIN) * height;
    }

    // blob air of the chunk's own columns; false when there is none
    private boolean markChunkBlobs(boolean[] blob, byte[] cellHas, int yMin, int yEnd, int height) {
        boolean any = false;
        for (int lx = 0; lx < CHUNK_SIZE; lx++) {
            for (int lz = 0; lz < CHUNK_SIZE; lz++) {
                selectColumn(lx, lz);
                for (int cellY = 0; (cellY + minBaseCellY) * BASE_CELL_Y < yEnd; cellY++) {
                    int slot = (cellX * (CORNERS - 1) + cellZ) * baseLayerCount + cellY;
                    byte verdict = cellVerdict[slot];
                    if (verdict == 0) cellVerdict[slot] = verdict = cellVerdict(cellY);
                    int carving = verdict - CARVING_CELL;
                    if (verdict == SOLID_CELL || carving == 0) continue;
                    for (int dy = 0; dy < BASE_CELL_Y; dy++) {
                        int y = (cellY + minBaseCellY) * BASE_CELL_Y + dy;
                        if (y >= yEnd) break;
                        if (blobCarves(carving, cellY, y, (float) dy / BASE_CELL_Y)) {
                            blob[blobIndex(lx, lz, height) + y - yMin] = true;
                            cellHas[(lx * CHUNK_SIZE + lz) * baseLayerCount + cellY] = 1;
                            any = true;
                        }
                    }
                }
            }
        }
        return any;
    }

    // the ring around the chunk, only where blob air lies within two blocks of it
    private void markRingBlobs(boolean[] blob, byte[] cellHas, int yMin, int yEnd, int height) {
        for (int x = -MARGIN; x < CHUNK_SIZE + MARGIN; x++) {
            for (int z = -MARGIN; z < CHUNK_SIZE + MARGIN; z++) {
                if (x >= 0 && x < CHUNK_SIZE && z >= 0 && z < CHUNK_SIZE) continue;
                int fromX = Math.max(0, x - MARGIN);
                int toX = Math.min(CHUNK_SIZE - 1, x + MARGIN);
                int fromZ = Math.max(0, z - MARGIN);
                int toZ = Math.min(CHUNK_SIZE - 1, z + MARGIN);
                for (int cellY = 0; (cellY + minBaseCellY) * BASE_CELL_Y < yEnd; cellY++) {
                    boolean near = false;
                    for (int ix = fromX; ix <= toX && !near; ix++) {
                        for (int iz = fromZ; iz <= toZ && !near; iz++) {
                            for (int c = Math.max(0, cellY - 1); c <= Math.min(baseLayerCount - 1, cellY + 1); c++) {
                                if (cellHas[(ix * CHUNK_SIZE + iz) * baseLayerCount + c] != 0) {
                                    near = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (!near) continue;
                    for (int dy = 0; dy < BASE_CELL_Y; dy++) {
                        int y = (cellY + minBaseCellY) * BASE_CELL_Y + dy;
                        if (y >= yEnd) break;
                        if (blobAt(x, y, z)) blob[blobIndex(x, z, height) + y - yMin] = true;
                    }
                }
            }
        }
    }

    // a core is a block of blob air with blob air all round it; what lies beyond the world's top and bottom counts as air
    private static boolean[] findCores(boolean[] blob, int height) {
        boolean[] core = new boolean[blob.length];
        for (int x = -1; x < CHUNK_SIZE + 1; x++) {
            for (int z = -1; z < CHUNK_SIZE + 1; z++) {
                for (int level = 0; level < height; level++) {
                    if (!blob[blobIndex(x, z, height) + level]) continue;
                    boolean inside = true;
                    for (int dx = -1; dx <= 1 && inside; dx++) {
                        for (int dz = -1; dz <= 1 && inside; dz++) {
                            for (int dy = -1; dy <= 1; dy++) {
                                int l = level + dy;
                                if (l >= 0 && l < height && !blob[blobIndex(x + dx, z + dz, height) + l]) {
                                    inside = false;
                                    break;
                                }
                            }
                        }
                    }
                    core[blobIndex(x, z, height) + level] = inside;
                }
            }
        }
        return core;
    }

    // the chunk's blob air that no core covers, by column and height; null when there is none
    private static boolean[] findUncovered(boolean[] blob, boolean[] core, int yMin, int decideEnd, int height) {
        boolean[] result = null;
        for (int lx = 0; lx < CHUNK_SIZE; lx++) {
            for (int lz = 0; lz < CHUNK_SIZE; lz++) {
                for (int level = 0; level < decideEnd - yMin; level++) {
                    if (!blob[blobIndex(lx, lz, height) + level]) continue;
                    boolean covered = false;
                    for (int dx = -1; dx <= 1 && !covered; dx++) {
                        for (int dz = -1; dz <= 1 && !covered; dz++) {
                            for (int dy = -1; dy <= 1; dy++) {
                                int l = level + dy;
                                if (l >= 0 && l < height && core[blobIndex(lx + dx, lz + dz, height) + l]) {
                                    covered = true;
                                    break;
                                }
                            }
                        }
                    }
                    if (covered) continue;
                    if (result == null) result = new boolean[CHUNK_SIZE * CHUNK_SIZE * height];
                    result[(lx * CHUNK_SIZE + lz) * height + level] = true;
                }
            }
        }
        return result;
    }

    // SOLID_CELL: nothing in the cell can be air. Otherwise CARVING_CELL plus a bit for each blob channel (0 to 2) that may
    // open air in it.
    // A blob channel only counts in a cell where some corner clears its threshold by the depth of the thinnest space we
    // keep, in blocks of the channel's own gradient (plus a floor for the flat tops of the field, where the gradient is
    // nothing). Without such a corner the air of that channel in the cell is a lens or a crack thinner than that, and it
    // stays rock. Everything is read off the corners of the one cell, which sit on world-fixed positions, so the answer
    // is the same from whichever chunk asks.
    // The blend of a cell stays between the minimum and maximum of its 8 corners (up to float rounding, far below
    // CULL_MARGIN), so the spaghetti lower bound below is safe too.
    private byte cellVerdict(int cellY) {
        float[] values = cellValues;
        if (baseCorners == null) {
            float[] low = layer(cellY);
            float[] high = layer(cellY + 1);
            int lowBase = (cellY % PAGE_LAYERS) * LAYER_SIZE;
            int highBase = ((cellY + 1) % PAGE_LAYERS) * LAYER_SIZE;
            for (int dz = 0; dz < 2; dz++) {
                for (int dx = 0; dx < 2; dx++) {
                    int corner = index(cellX + dx, cellZ + dz, 0);
                    System.arraycopy(low, lowBase + corner, values, (dz * 2 + dx) * VALUES, VALUES);
                    System.arraycopy(high, highBase + corner, values, (4 + dz * 2 + dx) * VALUES, VALUES);
                }
            }
        } else {
            fillValues(cellX, cellZ, cellY);
        }
        return verdictFromValues(cellY);
    }

    private byte verdictFromValues(int cellY) {
        float[] values = cellValues;
        boolean water = (cellY + minBaseCellY) * BASE_CELL_Y < WATER_HEIGHT;
        int carving = 0;
        float lowerBound = 0;
        for (int v = 0; v < VALUES; v++) {
            float min = values[v];
            float max = min;
            for (int k = 1; k < 8; k++) {
                float value = values[k * VALUES + v];
                if (value < min) min = value;
                if (value > max) max = value;
            }
            if (v < 3) {
                if (v == 0 && !water) continue;
                float threshold = v == 0 ? WATER_BLOB_THRESHOLD : v == 1 ? BLOB_THRESHOLD_1 : BLOB_THRESHOLD_2;
                if (max < threshold) continue;
                // corner k: layer k / 4, then dz, then dx
                float gx = 0;
                float gz = 0;
                float gy = 0;
                for (int k = 0; k < 4; k++) {
                    int dz = k >> 1;
                    int dx = k & 1;
                    for (int layer = 0; layer < 2; layer++) {
                        if (dx == 0) gx += values[(layer * 4 + dz * 2 + 1) * VALUES + v] - values[(layer * 4 + dz * 2) * VALUES + v];
                        if (dz == 0) gz += values[(layer * 4 + 2 + dx) * VALUES + v] - values[(layer * 4 + dx) * VALUES + v];
                    }
                    gy += values[(4 + k) * VALUES + v] - values[k * VALUES + v];
                }
                gx = gx / 4 / CELL;
                gz = gz / 4 / CELL;
                gy = gy / 4 / BASE_CELL_Y;
                float margin = THIN_DEPTH * (float) Math.sqrt(gx * gx + gy * gy + gz * gz) + THIN_FLOOR;
                if (max >= threshold + margin) carving |= 1 << v;
            } else if (min > 0) {
                lowerBound += min;
            } else if (max < 0) {
                lowerBound -= max;
            }
        }
        if (carving == 0 && lowerBound / 3 > SPAGHETTI_THRESHOLD + CULL_MARGIN) return SOLID_CELL;
        return (byte) (CARVING_CELL + carving);
    }

    private float lerp3(int cellY, int v, float fy) {
        float b = baseBlend(cellY, v);
        float a = baseBlend(cellY + 1, v);
        return b + (a - b) * fy;
    }

    private float baseBlend(int layer, int v) {
        int slot = layer * VALUES + v;
        if (baseStamp[slot] == stamp) return baseBlend[slot];
        float c00;
        float c10;
        float c01;
        float c11;
        if (baseCorners == null) {
            float[] page = layer(layer);
            int base = (layer % PAGE_LAYERS) * LAYER_SIZE;
            c00 = page[base + index(cellX, cellZ, v)];
            c10 = page[base + index(cellX + 1, cellZ, v)];
            c01 = page[base + index(cellX, cellZ + 1, v)];
            c11 = page[base + index(cellX + 1, cellZ + 1, v)];
        } else {
            c00 = baseCorner(layer, cellX, cellZ)[v];
            c10 = baseCorner(layer, cellX + 1, cellZ)[v];
            c01 = baseCorner(layer, cellX, cellZ + 1)[v];
            c11 = baseCorner(layer, cellX + 1, cellZ + 1)[v];
        }
        float top = c00 + (c10 - c00) * fx;
        float bottom = c01 + (c11 - c01) * fx;
        float blend = top + (bottom - top) * fz;
        baseBlend[slot] = blend;
        baseStamp[slot] = stamp;
        return blend;
    }

    private static int index(int cornerX, int cornerZ, int v) {
        return (cornerX * CORNERS + cornerZ) * VALUES + v;
    }

    // tan blows up at y -78; below the old floor it carries on in a straight line from where it stood at -64
    private static double caveTan(int y) {
        if (y >= DepthBands.SHALLOW_FLOOR) return Math.tan((float) y / CAVE_STRETCH_V);
        double tan = Math.tan((float) DepthBands.SHALLOW_FLOOR / CAVE_STRETCH_V);
        return tan + (1 + tan * tan) * (y - DepthBands.SHALLOW_FLOOR) / CAVE_STRETCH_V;
    }

    private static final class LayerY {
        final double tanY;
        final float tanYf;
        final float y1;
        final float y2;
        final float y3;
        final float y4;

        // everything in a LayerY depends on y alone, so one per height is shared by all chunks
        private static final int TABLE_OFFSET = 2048;
        private static final LayerY[] TABLE = new LayerY[4096];

        static LayerY of(int y) {
            int slot = y + TABLE_OFFSET;
            if (slot < 0 || slot >= TABLE.length) return new LayerY(y);
            LayerY layerY = TABLE[slot];
            if (layerY == null) TABLE[slot] = layerY = new LayerY(y);
            return layerY;
        }

        private LayerY(int y) {
            this.tanY = caveTan(y);
            this.tanYf = (float) tanY;
            this.y1 = (float) y / (CAVE_STRETCH_V * 0.5f);
            this.y2 = (float) y / 60;
            this.y3 = (float) y / 30;
            this.y4 = (float) y / CAVE_STRETCH_V;
        }
    }

    // the six base noise values of one corner
    private static void baseCorner(LayerY layerY, int x, int z, float[] out, int offset) {
        float noise = (float) SimplexNoise.noise(
                (float) x / CAVE_STRETCH_H, layerY.tanY, (float) z / CAVE_STRETCH_H);
        noise += 0.5f * (float) SimplexNoise.noise(
                (float) x / (CAVE_STRETCH_H * 0.5f), layerY.y1, (float) z / (CAVE_STRETCH_H * 0.5f));
        noise = noise / (1 + 0.5f);

        float noise3 = (float) SimplexNoise.noise((float) x / 90, layerY.y2, (float) z / 90);
        float miniNoise = (float) SimplexNoise.noise((float) x / 40, layerY.y3, (float) z / 40);

        out[offset] = noise;
        out[offset + 1] = noise3;
        out[offset + 2] = miniNoise;
        // 3D simplex, each with its own shift so the three stay unrelated to each other and to the noises above. A 3D
        // noise swings wider than the slice of a 4D one, so the scale keeps the same share of the rock inside the thin tunnels.
        out[offset + 3] = SPAGHETTI_SCALE * (float) SimplexNoise.noise(
                (float) x / (SPAGHETTI_CAVE_STRETCH_H * 1.5f) + 5714.2f, layerY.tanYf + 214.7f, (float) z / (SPAGHETTI_CAVE_STRETCH_H * 1.5f) + 321.9f);
        out[offset + 4] = SPAGHETTI_SCALE * (float) SimplexNoise.noise(
                (float) (98153 + z) / SPAGHETTI_CAVE_STRETCH_H, layerY.y4 + 77.7f, (float) x / SPAGHETTI_CAVE_STRETCH_H);
        out[offset + 5] = SPAGHETTI_SCALE * (float) SimplexNoise.noise(
                (float) (1243624 + z) / (SPAGHETTI_CAVE_STRETCH_H * 0.5f), layerY.y4 + 193.3f, (float) x / (SPAGHETTI_CAVE_STRETCH_H * 0.5f));
    }

    // probe only: the same corner, evaluated alone
    private float[] baseCorner(int layer, int cornerX, int cornerZ) {
        long key = (long) layer * 256 + cornerX * 16 + cornerZ;
        float[] values = baseCorners.get(key);
        if (values != null) return values;
        if (baseCorners.size() >= MAX_CORNERS) baseCorners.clear();
        values = new float[VALUES];
        baseCorner(LayerY.of((layer + minBaseCellY) * BASE_CELL_Y), startX + cornerX * CELL, startZ + cornerZ * CELL, values, 0);
        baseCorners.put(key, values);
        return values;
    }

    private float[] layer(int cellY) {
        int pageIndex = cellY / PAGE_LAYERS;
        float[] page = layerPages[pageIndex];
        if (page == null) page = layerPages[pageIndex] = new float[PAGE_LAYERS * LAYER_SIZE];
        if (!layerDone[cellY]) {
            int layerBase = (cellY % PAGE_LAYERS) * LAYER_SIZE;
            int y = (cellY + minBaseCellY) * BASE_CELL_Y;
            // everything that depends on y alone is evaluated once per layer
            LayerY layerY = LayerY.of(y);
            for (int cornerX = 0; cornerX < CORNERS; cornerX++) {
                for (int cornerZ = 0; cornerZ < CORNERS; cornerZ++) {
                    baseCorner(layerY, startX + cornerX * CELL, startZ + cornerZ * CELL, page, layerBase + index(cornerX, cornerZ, 0));
                }
            }
            layerDone[cellY] = true;
        }
        return page;
    }
}
