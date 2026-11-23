package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.special.pointedBlocks.PointedStoneBlock;
import net.sevenstars.middleearth.features.building.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;

// the inside of a lava tube: glazed basalt, ropy floor, rust-red roof, frozen drips, rubble and ash under skylights,
// and near the Mountain a live lava channel. Every choice comes from position or hash, so chunks agree
final class LavaTubeDressing {
    private static final BlockState BASALT = Blocks.BASALT.getDefaultState();
    private static final BlockState SMOOTH_BASALT = Blocks.SMOOTH_BASALT.getDefaultState();
    private static final BlockState BLACKSTONE = Blocks.BLACKSTONE.getDefaultState();
    private static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
    private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState HEMATITE = StoneBlockSetRegistryME.HEMATITE_SET.baseBlocks.base().getDefaultState();
    private static final BlockState ASHEN_COBBLE = StoneBlockSetRegistryME.ASHENSTONE_SET.cobblestoneBlocks.base().getDefaultState();
    private static final BlockState ASHEN_GRAVEL = BlockRegistryME.ASHEN_GRAVEL.getDefaultState();
    private static final BlockState ASH = BlockRegistryME.ASH_BLOCK.getDefaultState();
    private static final BlockState ASH_LAYER = BlockRegistryME.ASHEN_SAND_LAYER.getDefaultState();
    private static final PointedStoneBlock POINTED_BASALT = (PointedStoneBlock) BlockRegistryME.POINTED_BASALT;

    private static final int ISLAND = 12;
    private static final int DRIPS = 14; // out of 256 roof blocks
    private static final float CHANNEL = 0.95f; // half width of the lava channel
    private static final float HOT_BAND = 2.6f; // magma either side of the channel

    private LavaTubeDressing() {
    }

    // the stretch of tube a column lies in
    private record Column(LavaTubes.Tube tube, int floor, int roof, float along, float side, float heat) {
    }

    private record Context(LavaTubes tubes, BlockBuffer blocks, CarveMask air, Column[] columns, int startX, int startZ) {
        Column column(int x, int z) {
            return columns[(x - startX) * 16 + (z - startZ)];
        }

        boolean inside(int x, int z) {
            return x >= startX && x < startX + 16 && z >= startZ && z < startZ + 16;
        }

        boolean solid(int x, int y, int z) {
            BlockState state = blocks.get(x, y, z);
            return !state.isAir() && state.getFluidState().isEmpty();
        }

        double noise(double x, double z, double scale) {
            return SimplexNoise.noise((x + tubes.noiseX) / scale, (z + tubes.noiseZ) / scale);
        }

        double noise(double x, double y, double z, double scale) {
            return SimplexNoise.noise((x + tubes.noiseZ) / scale, y / scale, (z + tubes.noiseX) / scale);
        }
    }

    static void dress(LavaTubes.Near near, Chunk chunk, BlockBuffer blocks) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        int lowY = near.minY - 3;
        int highY = near.maxY + 2;
        CarveMask air = new CarveMask(near::carves, startX - 3, startX + 18, lowY - 3, highY + 3, startZ - 3, startZ + 18);
        air.clearIslands(ISLAND);
        Column[] columns = new Column[256];
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) columns[x * 16 + z] = column(near, air, startX + x, startZ + z);
        }
        Context at = new Context(near.tubes, blocks, air, columns, startX, startZ);
        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                for (int y = lowY; y <= highY; y++) rock(at, x, y, z);
            }
        }
        for (LavaTubes.Skylight light : near.skylights) rubble(at, light);
        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                Column c = at.column(x, z);
                if (c == null) continue;
                drips(at, c, x, z);
                if (c.heat > 0) channel(at, c, x, z);
            }
        }
        for (LavaTubes.Segment s : near.segments) {
            if (s.tube.active() && s.index == 0) sump(at, s.a);
        }
        for (LavaTubes.Skylight light : near.skylights) ash(at, light);
    }

    private static Column column(LavaTubes.Near near, CarveMask air, int x, int z) {
        float px = x + 0.5f;
        float pz = z + 0.5f;
        LavaTubes.Segment best = null;
        float bestT = 0;
        float bestD = Float.MAX_VALUE;
        for (LavaTubes.Segment s : near.segments) {
            float t = s.project(px, pz);
            float d = s.lateral2(px, pz, t);
            if (d < bestD && d < sq(s.width(t) * LavaTubes.SWELL + 2)) {
                bestD = d;
                best = s;
                bestT = t;
            }
        }
        if (best == null) return null;
        int floor = best.floorY(bestT);
        int roof = floor + (int) Math.ceil(best.height(bestT) * LavaTubes.SWELL);
        // where two stretches of different floor overlap, the lower one is the floor
        for (int y = floor - 2; y <= floor + 2; y++) {
            if (air.at(x, y, z) && !air.at(x, y - 1, z)) {
                floor = y;
                break;
            }
        }
        float along = best.along(bestT);
        LavaTubes.Tube tube = best.tube;
        float heat = tube.active() && along < tube.hotLength ? 1 - along / tube.hotLength : 0;
        return new Column(tube, floor, roof, along, best.side(px, pz), heat);
    }

    private static float distance(int x, int z, float cx, float cz) {
        return (float) Math.sqrt(sq(x + 0.5f - cx) + sq(z + 0.5f - cz));
    }

    // THE ROCK: a basalt rind over the ash-stone of the plain

    private static void rock(Context at, int x, int y, int z) {
        if (!at.solid(x, y, z)) return;
        CarveMask air = at.air;
        if (air.at(x, y, z)) {
            at.blocks.put(x, y, z, AIR);
            return;
        }
        Column c = at.column(x, z);
        // the skylight shaft and the deep rock keep the plain's own stone
        if (c == null || y > c.roof + 1 || y < c.floor - 4) return;
        boolean floor = air.at(x, y + 1, z);
        boolean ceiling = air.at(x, y - 1, z);
        boolean wall = air.at(x + 1, y, z) || air.at(x - 1, y, z) || air.at(x, y, z + 1) || air.at(x, y, z - 1);
        if (!floor && !ceiling && !wall) {
            if (air.nearAir(x, y, z)) at.blocks.put(x, y, z, BASALT);
            return;
        }
        if (floor) {
            at.blocks.put(x, y, z, floorStone(at, x, y, z, c));
        } else if (ceiling) {
            at.blocks.put(x, y, z, ceilingStone(at, x, y, z));
        } else {
            at.blocks.put(x, y, z, wallStone(at, x, y, z, c));
        }
    }

    // ropy flow surface in broad patches, glassy where the last lava pooled
    private static BlockState floorStone(Context at, int x, int y, int z, Column c) {
        if (c.heat > 0 && at.noise(x, y, z, 5) > 0.8 - 0.5 * c.heat) return MAGMA;
        double n = at.noise(x, z, 14) + 0.15 * at.noise(x + 300, z, 4);
        if (n > 0.4) return SMOOTH_BASALT;
        if (n > -0.3) return BASALT;
        return BLACKSTONE;
    }

    // smooth basalt glaze with rough streaks, magma low down where fire still runs
    private static BlockState wallStone(Context at, int x, int y, int z, Column c) {
        if (c.heat > 0 && y <= c.floor + 1 && at.noise(x, y, z, 4) > 0.65 - 0.5 * c.heat) return MAGMA;
        double streak = SimplexNoise.noise((x + at.tubes.noiseX) / 5.0, y / 16.0, (z + at.tubes.noiseZ) / 5.0);
        if (streak > 0.3) return BASALT;
        if (streak < -0.45) return BLACKSTONE;
        return SMOOTH_BASALT;
    }

    // the roof is blackened, and rusted red in patches
    private static BlockState ceilingStone(Context at, int x, int y, int z) {
        if (at.noise(x, y, z, 11) > 0.58) return HEMATITE;
        return at.noise(x + 500, y, z, 7) > 0 ? BLACKSTONE : SMOOTH_BASALT;
    }

    // THE ROOF: lava that dripped and froze as the tube drained, in clusters, a stub under some of them

    private static void drips(Context at, Column c, int x, int z) {
        if ((mix(x, z, at.tubes.noiseX) & 255) >= DRIPS || at.noise(x, z, 6) < 0.1) return;
        int y = c.floor;
        while (y <= c.roof && at.air.at(x, y + 1, z)) y++;
        if (y > c.roof || y - c.floor < 4 || !at.solid(x, y + 1, z) || !at.blocks.get(x, y, z).isAir()) return;
        int h = mix(x, z, at.tubes.noiseZ);
        int run = (h & 1) == 0 || !at.blocks.get(x, y - 1, z).isAir() ? 1 : 2;
        for (int k = 0; k < run; k++) at.blocks.put(x, y - k, z, POINTED_BASALT.pointing(Direction.DOWN, run - 1 - k, run));
        if (c.heat > 0 || (h & 6) != 0 || !at.blocks.get(x, c.floor, z).isAir() || !at.solid(x, c.floor - 1, z)) return;
        at.blocks.put(x, c.floor, z, POINTED_BASALT.pointing(Direction.UP, 0, 1));
    }

    // THE SKYLIGHTS: a low cone of fallen roof, with ash blown in over it and round it

    private static void rubble(Context at, LavaTubes.Skylight light) {
        float reach = light.mouth(2);
        float peak = Math.min(light.node.h - 3.5f, 0.6f + light.radius * 0.45f);
        if (peak < 1) return;
        for (int x = (int) Math.floor(light.x - reach); x <= (int) Math.floor(light.x + reach); x++) {
            for (int z = (int) Math.floor(light.z - reach); z <= (int) Math.floor(light.z + reach); z++) {
                if (!at.inside(x, z)) continue;
                Column c = at.column(x, z);
                if (c == null) continue;
                float d = distance(x, z, light.x, light.z);
                float height = peak * (1 - d / reach) * (0.85f + 0.3f * (float) at.noise(x, z, 3));
                // the top is level from the skylight's floor, so a step in the floor makes no cliff
                int count = Math.round(light.floor + height) - c.floor;
                for (int k = 0; k < count; k++) {
                    int y = c.floor + k;
                    // 3 blocks of headroom, so it can be climbed
                    if (!at.air.at(x, y + 3, z) || !at.air.at(x, y, z) || !at.blocks.get(x, y, z).isAir() || !at.solid(x, y - 1, z)) break;
                    boolean top = k == count - 1 && d < light.radius;
                    at.blocks.put(x, y, z, top ? ASH : rubbleStone(at, x, y, z));
                }
            }
        }
    }

    private static BlockState rubbleStone(Context at, int x, int y, int z) {
        double n = at.noise(x, y, z, 2.2);
        if (n > 0.35) return ASHEN_COBBLE;
        if (n > -0.1) return BASALT;
        if (n > -0.5) return BLACKSTONE;
        return ASHEN_GRAVEL;
    }

    private static void ash(Context at, LavaTubes.Skylight light) {
        float reach = light.mouth(6);
        for (int x = (int) Math.floor(light.x - reach); x <= (int) Math.floor(light.x + reach); x++) {
            for (int z = (int) Math.floor(light.z - reach); z <= (int) Math.floor(light.z + reach); z++) {
                if (!at.inside(x, z)) continue;
                Column c = at.column(x, z);
                if (c == null || c.heat > 0) continue;
                float d = distance(x, z, light.x, light.z);
                if (at.noise(x, z, 4) < d / reach * 1.4f - 0.3f) continue;
                int y = c.floor;
                while (y < c.floor + 4 && !at.blocks.get(x, y, z).isAir()) y++;
                if (!at.air.at(x, y, z) || !at.blocks.get(x, y, z).isAir() || !at.solid(x, y - 1, z)) continue;
                if (at.blocks.get(x, y - 1, z).isOf(BlockRegistryME.ASH_BLOCK)) continue;
                at.blocks.put(x, y, z, ASH_LAYER);
            }
        }
    }

    // THE FIRE: a river of lava winding down a channel in the floor, with hot rock either side

    private static void channel(Context at, Column c, int x, int z) {
        int y = c.floor - 1;
        if (!at.air.at(x, y + 1, z) || at.air.at(x, y, z)) return;
        float phase = (c.tube.hash & 0xFFFF) / 1000f;
        float offset = 0.8f * MathHelper.sin(c.along * 0.19f + phase);
        float d = Math.abs(c.side - offset);
        if (d < CHANNEL * (0.6f + 0.4f * c.heat + 0.25f) && sealed(at, x, y, z)) {
            at.blocks.put(x, y, z, LAVA);
        } else if (d < HOT_BAND && at.noise(x, z, 3) > 0.35 - 0.4 * c.heat) {
            at.blocks.put(x, y, z, MAGMA);
        }
    }

    // fire welling up at the head of a live tube, from under the Mountain
    private static void sump(Context at, LavaTubes.Node head) {
        float reach = head.w * 0.8f;
        int floor = head.floorY();
        for (int x = (int) Math.floor(head.x - reach); x <= (int) Math.floor(head.x + reach); x++) {
            for (int z = (int) Math.floor(head.z - reach); z <= (int) Math.floor(head.z + reach); z++) {
                if (!at.inside(x, z)) continue;
                float d = distance(x, z, head.x, head.z);
                float wobble = 1 + 0.2f * (float) at.noise(x, z, 2.5);
                if (d >= reach * wobble) continue;
                for (int y = floor - 1; y >= floor - 2; y--) {
                    if (at.air.at(x, y, z) || !at.air.at(x, floor, z) || !sealed(at, x, y, z)) break;
                    at.blocks.put(x, y, z, LAVA);
                }
            }
        }
    }

    // lava stays put only where no open air lies beside or under it
    private static boolean sealed(Context at, int x, int y, int z) {
        return !at.air.at(x + 1, y, z) && !at.air.at(x - 1, y, z) && !at.air.at(x, y, z + 1) && !at.air.at(x, y, z - 1) && !at.air.at(x, y - 1, z);
    }
}
