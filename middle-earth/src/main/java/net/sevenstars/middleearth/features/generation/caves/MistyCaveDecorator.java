package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;

public final class MistyCaveDecorator {
    private static final float ICE_MIN_WEIGHT = 0.5f;

    private static final float WORM_INNER = 0.8f; // squared share of the lake radius
    private static final float WORM_CLUSTER = 0.45f;
    private static final int WORM_CHANCE = 60; // out of 256, inside a cluster
    private static final float WORM_CLUSTER_SCALE = 11;
    private static final int WORM_HASH_SALT = 4801;
    private static final int WORM_MAX_LENGTH = 6;

    private static final float ICE_SHEET = 10; // noise scale, so ice comes in sheets
    private static final float ICE_SPREAD = 1.7f;
    private static final float BLUE_ICE = 0.55f;
    private static final int COLD_FULL = 18; // fully cold this deep, gone COLD_FADE further down
    private static final int COLD_FADE = 50;
    private static final int ENTRANCE_COLD = 60; // reach of the cold from a mouth
    private static final float SNOW_COLD = 0.55f; // colder floors are snow, the rest ice
    private static final float LAYER_COLD = 0.75f;
    private static final float SNOW_LAYER_SCALE = 5;
    private static final float ICICLE_CHANCE = 0.3f; // per unit of cold
    private static final int CHUNK = 16;

    private MistyCaveDecorator() {
    }

    // weights and surface heights are indexed local x * 16 + local z
    public static void decorate(Chunk chunk, BlockBuffer blocks, MistyCaves caves, Lake[] lakes, int[] surfaceY, float[] weights, Entrance[] entrances, RiverCaves.Near rivers) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (Lake lake : lakes) glowWorms(chunk, blocks, caves, lake, startX, startZ, pos);
        DepthBands.Edges[] edges = new DepthBands.Edges[CHUNK * CHUNK];
        for (int lx = 0; lx < CHUNK; lx++) {
            for (int lz = 0; lz < CHUNK; lz++) {
                int index = lx * CHUNK + lz;
                if (weights[index] < ICE_MIN_WEIGHT) continue;
                int x = startX + lx;
                int z = startZ + lz;
                edges[index] = DepthBands.Edges.of(surfaceY[index], DepthBands.deepFloor(MiddleEarthMapRuntime.getInstance(), x, z, caves.seed), MiddleEarthChunkGenerator.caveBlendNoise(x, z, caves.seed));
                ice(chunk, blocks, caves, x, z, lx, lz, surfaceY, edges[index].upperBottom(), pos, entrances, rivers);
            }
        }
        new MistyCaveSetPieces(chunk, blocks, caves, surfaceY, weights, edges).place();
    }

    private static void glowWorms(Chunk chunk, BlockBuffer blocks, MistyCaves caves, Lake lake, int startX, int startZ, BlockPos.Mutable pos) {
        BlockState worm = NatureBlockRegistryME.GLOWWORM_MAIN.getDefaultState();
        BlockState webbing = NatureBlockRegistryME.GLOWWORM_WEBBING.getDefaultState();
        int top = Math.min(lake.waterTop + (int) lake.ceilingMax() + 3, chunk.getTopYInclusive() - 1);
        for (int lx = 0; lx < CHUNK; lx++) {
            for (int lz = 0; lz < CHUNK; lz++) {
                int x = startX + lx;
                int z = startZ + lz;
                float nx = (x - lake.x) / lake.rx;
                float nz = (z - lake.z) / lake.rz;
                if (nx * nx + nz * nz > WORM_INNER) continue;
                float cluster = (float) SimplexNoise.noise((x + caves.offsetX) / WORM_CLUSTER_SCALE, (z + caves.offsetZ) / WORM_CLUSTER_SCALE);
                if (cluster < WORM_CLUSTER) continue;
                int h = MistyCaves.mix(x + caves.offsetX, z + caves.offsetZ, WORM_HASH_SALT);
                if ((h & 0xFF) >= WORM_CHANCE) continue;
                for (int y = lake.waterTop + 1; y <= top; y++) {
                    if (!blocks.get(x, y, z).isAir()) break;
                    BlockState above = blocks.get(x, y + 1, z);
                    pos.set(x, y + 1, z);
                    if (above.isAir() || !above.getFluidState().isEmpty()) continue;
                    if (!above.isFullCube(chunk, pos)) break;
                    int length = 1 + ((h >>> 8) & 0xFF) % WORM_MAX_LENGTH;
                    for (int i = 0; i < length; i++) {
                        pos.set(x, y - i, z);
                        if (y - i <= lake.waterTop || !blocks.get(x, y - i, z).isAir()) break;
                        blocks.put(x, y - i, z, i == length - 1 ? webbing : worm);
                    }
                    break;
                }
            }
        }
    }

    // rock an entrance dresses itself
    private static boolean entranceRock(Entrance[] entrances, int x, int y, int z) {
        for (Entrance entrance : entrances) {
            if (!entrance.box.contains(x, y, z)) continue;
            if (entrance.carves(x, y + 1, z) || entrance.carves(x + 1, y, z) || entrance.carves(x - 1, y, z)
                    || entrance.carves(x, y, z + 1) || entrance.carves(x, y, z - 1)) return true;
        }
        return false;
    }

    // cold from the surface and the entrances: sheets of ice and snow near them, bare rock deeper down
    private static void ice(Chunk chunk, BlockBuffer blocks, MistyCaves caves, int x, int z, int lx, int lz, int[] surfaceY, int upperBottom, BlockPos.Mutable pos, Entrance[] entrances, RiverCaves.Near rivers) {
        int surface = surfaceY[lx * CHUNK + lz];
        int bottom = Math.max(chunk.getBottomY() + 2, Math.min(upperBottom, Galleries.layerFloor(caves, x, z) - 2));
        int top = Math.min(surface - 2, chunk.getTopYInclusive() - 2);
        // the block above is read once and carried to the next step; it is replaced when a snow layer goes there
        BlockState carried = null;
        for (int y = bottom; y <= top; y++) {
            BlockState state = carried != null ? carried : blocks.get(x, y, z);
            carried = null;
            if (state.isAir() || !state.getFluidState().isEmpty() || !state.isFullCube(chunk, pos.set(x, y, z))) continue;
            BlockState above = blocks.get(x, y + 1, z);
            carried = above;
            // the galleries' own ice and snow
            if (state.isOf(Blocks.ICE) || state.isOf(Blocks.PACKED_ICE) || state.isOf(Blocks.BLUE_ICE) || state.isOf(Blocks.SNOW_BLOCK)) continue;
            boolean floor = above.isAir();
            boolean ceiling = blocks.get(x, y - 1, z).isAir();
            boolean wall = (lx > 0 && caveAir(blocks, surfaceY, x - 1, y, z, lx - 1, lz))
                    || (lx < 15 && caveAir(blocks, surfaceY, x + 1, y, z, lx + 1, lz))
                    || (lz > 0 && caveAir(blocks, surfaceY, x, y, z - 1, lx, lz - 1))
                    || (lz < 15 && caveAir(blocks, surfaceY, x, y, z + 1, lx, lz + 1));
            if (!floor && !ceiling && !wall) continue;
            float cold = cold(entrances, x, y, z, surface);
            if (cold <= 0 || entrances.length > 0 && entranceRock(entrances, x, y, z)) continue;
            // no ice by the river water
            if (rivers != null && rivers.quiet(x, y, z)) continue;
            float sheet = (float) SimplexNoise.noise((x + caves.offsetX) / ICE_SHEET, (y + caves.offsetY) / ICE_SHEET, (z + caves.offsetZ) / ICE_SHEET);
            if (sheet < 1 - ICE_SPREAD * cold) continue;
            if (floor) {
                boolean snow = cold > SNOW_COLD;
                blocks.put(x, y, z, snow ? Blocks.SNOW_BLOCK.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
                if (snow && cold > LAYER_COLD) {
                    float drift = (float) SimplexNoise.noise((x + caves.offsetZ) / SNOW_LAYER_SCALE, (z + caves.offsetX) / SNOW_LAYER_SCALE);
                    int layers = MathHelper.clamp(1 + (int) ((drift + 1) * 1.2f), 1, 3);
                    carried = Blocks.SNOW.getDefaultState().with(SnowBlock.LAYERS, layers);
                    blocks.put(x, y + 1, z, carried);
                }
                continue;
            }
            blocks.put(x, y, z, sheet > BLUE_ICE ? Blocks.BLUE_ICE.getDefaultState() : Blocks.PACKED_ICE.getDefaultState());
            if (ceiling && y - 2 >= bottom && blocks.get(x, y - 2, z).isAir()) {
                int h = MistyCaves.mix(x + caves.offsetX, y + caves.offsetY, z + caves.offsetZ);
                if ((h & 0xFF) < 256 * ICICLE_CHANCE * cold) blocks.put(x, y - 1, z, NatureBlockRegistryME.SHORT_ICICLES.getDefaultState());
            }
        }
    }

    // air below its column's ground; open sky beside a slope is no cave wall
    private static boolean caveAir(BlockBuffer blocks, int[] surfaceY, int x, int y, int z, int lx, int lz) {
        return y < surfaceY[lx * CHUNK + lz] - 1 && blocks.get(x, y, z).isAir();
    }

    private static float cold(Entrance[] entrances, int x, int y, int z, int surface) {
        float cold = MathHelper.clamp(1 - (surface - y - COLD_FULL) / (float) COLD_FADE, 0f, 1f);
        for (Entrance entrance : entrances) {
            if (!entrance.box.contains(x, y, z)) continue;
            float d = (float) Math.sqrt(sq(x + 0.5f - entrance.mx) + sq(y - entrance.floorY) + sq(z + 0.5f - entrance.mz));
            cold = Math.max(cold, 1 - d / ENTRANCE_COLD);
        }
        return cold;
    }

}
