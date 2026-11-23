package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.AmethystClusterBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.block.registration.BlockRegistryME;
import net.sevenstars.middleearth.block.special.pointedBlocks.PointedStoneBlock;
import net.sevenstars.middleearth.block.registration.OreStoneSetRegistryME;
import net.sevenstars.middleearth.features.building.StoneBlockSetRegistryME;
import net.sevenstars.middleearth.features.generation.noise.SimplexNoise;

import java.util.ArrayList;
import java.util.List;

import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.mix;
import static net.sevenstars.middleearth.features.generation.caves.MistyCaves.sq;

// Gimli's caves: "columns of white and saffron and dawn-rose, fluted and twisted ... from many-coloured floors to meet
// the glistening pendants of the roof: wings, ropes, curtains fine as frozen clouds", "still lakes", and "gems and
// crystals and veins of precious ore" in "polished walls". Every choice comes from a position or a hash, and only
// this chunk's blocks are written, so chunks agree
final class AglarondDressing {
    private static final BlockState CALCITE = Blocks.CALCITE.getDefaultState();
    private static final BlockState LIMESTONE = StoneBlockSetRegistryME.LIMESTONE_SET.baseBlocks.base().getDefaultState();
    private static final BlockState TRAVERTINE = StoneBlockSetRegistryME.TRAVERTINE_SET.baseBlocks.base().getDefaultState();
    private static final BlockState QUARTZITE = StoneBlockSetRegistryME.QUARTZITE_SET.baseBlocks.base().getDefaultState();
    private static final BlockState CHALK = StoneBlockSetRegistryME.CHALK_SET.baseBlocks.base().getDefaultState();
    private static final BlockState GOLD = OreStoneSetRegistryME.CALCITE.gold_ore().getDefaultState();
    private static final BlockState SILVER = OreStoneSetRegistryME.CALCITE.silver_ore().getDefaultState();
    private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
    private static final BlockState AIR = Blocks.AIR.getDefaultState();
    private static final BlockState WATER = Blocks.WATER.getDefaultState();
    // white, saffron and dawn-rose, white most of all
    private static final BlockState[] COLUMN_COLOURS = {CALCITE, CALCITE, TRAVERTINE, QUARTZITE, CALCITE, CHALK};

    private static final float FORMATIONS = 0.03f; // drip points per square block of floor, at full richness
    private static final float STRAWS = 0.04f;
    private static final float HANG = 0.6f; // stalactites hang this share of the drop that set them
    private static final int SPIKE = 2; // every stalactite ends in at least this much pointed calcite
    private static final float THIN = 1; // narrower than this a formation is one block wide, a thread of pointed calcite
    private static final PointedStoneBlock POINTED_CALCITE = (PointedStoneBlock) BlockRegistryME.POINTED_CALCITE;
    private static final float CORRIDOR = 2.5f; // formations keep this clear beside the ways through a hall
    private static final float CURTAIN_LINE = 0.07f;
    private static final float CURTAIN_SCALE = 7;
    private static final int CURTAIN_MAX = 4;
    private static final float VEIN = 0.03f;
    private static final int ISLAND = 12; // lumps of rock left by the wobble up to this size are cleared

    private AglarondDressing() {
    }

    static void dress(Aglarond caves, Chunk chunk, BlockBuffer blocks) {
        int startX = chunk.getPos().getStartX();
        int startZ = chunk.getPos().getStartZ();
        Box box = caves.box;
        int fromX = Math.max(box.minX() - 2, startX);
        int toX = Math.min(box.maxX() + 2, startX + 15);
        int fromZ = Math.max(box.minZ() - 2, startZ);
        int toZ = Math.min(box.maxZ() + 2, startZ + 15);
        if (fromX > toX || fromZ > toZ) return;
        int lowY = box.minY() - 3;
        int highY = box.maxY() + 2;
        CarveMask air = new CarveMask(caves::carves, fromX - 3, toX + 3, lowY - 3, highY + 3, fromZ - 3, toZ + 3);
        air.clearIslands(ISLAND);
        Context at = new Context(caves, chunk, blocks, air, startX, startZ);
        for (int x = fromX; x <= toX; x++) {
            for (int z = fromZ; z <= toZ; z++) {
                for (int y = lowY; y <= highY; y++) rock(at, x, y, z);
            }
        }
        for (Aglarond.Chamber c : caves.chambers) {
            if (!c.box.touchesChunk(startX, startZ)) continue;
            List<Pool> pools = pools(at, c);
            curtains(at, c);
            formations(at, c, pools);
            straws(at, c);
            if (c.kind != Aglarond.Chamber.Kind.OUTER) {
                rimstone(at, c, pools);
                vugs(at, c);
            }
        }
        for (Aglarond.Avenue way : caves.stream) {
            if (way.box.touchesChunk(startX, startZ)) stream(at, way);
        }
    }

    private record Context(Aglarond caves, Chunk chunk, BlockBuffer blocks, CarveMask air, int startX, int startZ) {
        boolean inside(int x, int z) {
            return x >= startX && x < startX + 16 && z >= startZ && z < startZ + 16;
        }

        boolean solid(int x, int y, int z) {
            BlockState state = blocks.get(x, y, z);
            return !state.isAir() && state.getFluidState().isEmpty();
        }

        int ox() {
            return caves.rock.noiseX;
        }

        int oz() {
            return caves.rock.noiseZ;
        }
    }

    // THE ROCK: a pale skin over whatever the mountain is made of

    private static void rock(Context at, int x, int y, int z) {
        CarveMask air = at.air;
        BlockState state = at.blocks.get(x, y, z);
        if (state.isAir() || !state.getFluidState().isEmpty()) return;
        if (air.at(x, y, z)) {
            at.blocks.put(x, y, z, at.caves.water(x, y, z) ? WATER : AIR);
            return;
        }
        boolean floor = air.at(x, y + 1, z);
        boolean ceiling = air.at(x, y - 1, z);
        boolean wall = air.at(x + 1, y, z) || air.at(x - 1, y, z) || air.at(x, y, z + 1) || air.at(x, y, z - 1);
        boolean underWater = at.caves.water(x, y + 1, z);
        if (!floor && !ceiling && !wall && !underWater) {
            // a second layer of limestone, so a broken face shows the same rock
            if (air.nearAir(x, y, z)) at.blocks.put(x, y, z, LIMESTONE);
            return;
        }
        float richness = richness(at.caves, x, z);
        // a ledge in a stepped wall is wall, not floor
        if (underWater || floor && air.at(x + 2, y + 1, z) && air.at(x - 2, y + 1, z) && air.at(x, y + 1, z + 2) && air.at(x, y + 1, z - 2)) {
            at.blocks.put(x, y, z, floorStone(at, x, z, richness));
            return;
        }
        if (richness > 0.3f && vein(at, x, y, z)) {
            float kind = (float) SimplexNoise.noise((x + at.oz()) / 30.0, y / 30.0, (z + at.ox()) / 30.0);
            at.blocks.put(x, y, z, kind > -0.2f ? GOLD : SILVER);
            return;
        }
        at.blocks.put(x, y, z, ceiling ? ceilingStone(at, x, y, z) : wallStone(at, x, y, z, richness));
    }


    // many-coloured: broad patches of rose, saffron, white and grey, the outer caves plain, with grit underfoot
    private static BlockState floorStone(Context at, int x, int z, float richness) {
        float n = (float) (SimplexNoise.noise((x + at.ox()) / 22.0, (z + at.oz()) / 22.0) + 0.12 * SimplexNoise.noise((x + at.oz()) / 6.0, (z + at.ox()) / 6.0));
        if (richness < 0.3f) return n > 0.45f ? GRAVEL : LIMESTONE;
        if (n > 0.5f) return QUARTZITE;
        if (n > 0.2f) return TRAVERTINE;
        if (n > -0.45f) return CALCITE;
        return LIMESTONE;
    }

    // flowstone runs down the walls in streaks, and the richer halls are banded like marble
    private static BlockState wallStone(Context at, int x, int y, int z, float richness) {
        float streak = (float) SimplexNoise.noise((x + at.ox()) / 6.0, y / 24.0, (z + at.oz()) / 6.0);
        if (richness < 0.3f) return streak > -0.15f ? CALCITE : LIMESTONE;
        if (richness > 0.5f && SimplexNoise.noise((x + at.oz()) / 28.0, y / 7.0, (z + at.ox()) / 28.0) > 0.62) return QUARTZITE;
        if (streak > -0.15f) return CALCITE;
        if (streak < -0.6f) return TRAVERTINE;
        return LIMESTONE;
    }

    private static BlockState ceilingStone(Context at, int x, int y, int z) {
        return SimplexNoise.noise((x + at.ox()) / 9.0, y / 9.0, (z + at.oz()) / 9.0) > 0.45 ? CHALK : CALCITE;
    }

    // thin winding veins, only where a broad patch of the mountain carries them
    private static boolean vein(Context at, int x, int y, int z) {
        if (SimplexNoise.noise((x + at.ox()) / 45.0, y / 45.0, (z + at.oz()) / 45.0) < 0.15) return false;
        return Math.abs(SimplexNoise.noise((x + at.oz()) / 10.0, y / 10.0, (z + at.ox()) / 10.0)) < VEIN;
    }

    private static float richness(Aglarond caves, int x, int z) {
        float best = Float.MAX_VALUE;
        float richness = 0.5f;
        for (Aglarond.Chamber c : caves.chambers) {
            float q = c.spread(x + 0.5f, z + 0.5f);
            if (q < best) {
                best = q;
                richness = c.richness;
            }
        }
        return richness;
    }

    // THE FORMATIONS: every stalagmite stands under the stalactite that fed it, and where the two meet, a column

    private static void formations(Context at, Aglarond.Chamber c, List<Pool> pools) {
        int count = Math.round(c.rx * c.rz * FORMATIONS * c.richness);
        for (int i = 0; i < count; i++) {
            int h = mix(c.hash, 100 + i, 0);
            float[] p = pointIn(c, h, 0.85f);
            // nothing stands in a pool
            if (p == null || corridor(at.caves, p[0], p[1], CORRIDOR + 2) || nearPool(pools, p[0], p[1], 3.5f)) continue;
            int px = (int) Math.floor(p[0]);
            int pz = (int) Math.floor(p[1]);
            int top = ceiling(at.caves, c, px, pz);
            int bed = bed(at.caves, px, c.floor, pz);
            int gap = top - c.floor + 1;
            if (gap < 4) continue;
            float u1 = (mix(h, 1, 1) & 0xFF) / 255f;
            float u2 = (mix(h, 2, 2) & 0xFF) / 255f;
            float u3 = (mix(h, 3, 3) & 0xFF) / 255f;
            float hang = gap * (0.12f + 0.5f * u1);
            float stand = gap * (0.08f + 0.4f * u2);
            Shape shape = new Shape(p[0], p[1], h, c.richness);
            if (hang + stand >= gap - 1 || u3 < 0.25f * c.richness) {
                float radius = MathHelper.clamp(0.9f + (1.8f + 1.2f * u3) * gap / 24f, 0.9f, 3.6f);
                shape.column(at, bed, top, radius);
            } else {
                shape.stalactite(at, top, hang * HANG, MathHelper.clamp(0.55f + 0.09f * hang * HANG, 0.55f, 2.4f));
                shape.stalagmite(at, bed, c.floor, stand, MathHelper.clamp(0.65f + 0.1f * stand, 0.65f, 2.8f));
            }
        }
    }

    // a formation's place, its banding and its flutes
    private static final class Shape {
        final float x;
        final float z;
        final int flutes;
        final float twist;
        final int bandShift;
        final int bandHeight;
        final BlockState[] palette = new BlockState[3];

        Shape(float x, float z, int h, float richness) {
            this.x = x;
            this.z = z;
            this.flutes = 4 + Math.floorMod(mix(h, 4, 4), 4);
            this.twist = ((mix(h, 5, 5) & 0xFF) / 255f - 0.5f) * 0.25f;
            this.bandShift = Math.floorMod(mix(h, 6, 6), 7);
            this.bandHeight = 2 + Math.floorMod(mix(h, 7, 7), 3);
            for (int i = 0; i < palette.length; i++) {
                int pick = Math.floorMod(mix(h, 8 + i, 8), COLUMN_COLOURS.length);
                palette[i] = richness < 0.3f ? (i == 0 ? LIMESTONE : CALCITE) : COLUMN_COLOURS[pick];
            }
        }

        BlockState band(int y) {
            return palette[Math.floorMod(Math.floorDiv(y + bandShift, bandHeight), palette.length)];
        }

        // fluted columns flare at foot and head and twist a little as they rise
        void column(Context at, int bed, int top, float radius) {
            int span = Math.max(1, top - bed);
            for (int y = bed; y <= top; y++) {
                float t = (y - bed) / (float) span;
                float flare = 1 + 0.55f * sq(2 * t - 1) * Math.abs(2 * t - 1);
                fill(at, y, radius * flare, true, null);
            }
        }

        void stalactite(Context at, int top, float length, float radius) {
            int last = (int) Math.ceil(length) - 1;
            int thin = 0;
            for (int d = 0; d <= last; d++) {
                if (hanging(d, last, length, radius) < THIN) thin++;
            }
            boolean calcite = thin > last || band(top - (last - thin)).isOf(Blocks.CALCITE);
            for (int d = 0; d <= last; d++) {
                fill(at, top - d, hanging(d, last, length, radius), false, calcite ? POINTED_CALCITE.pointing(Direction.DOWN, last - d, thin) : null);
            }
        }

        private static float hanging(int d, int last, float length, float radius) {
            if (d > last - SPIKE) return 0;
            return Math.max(radius * (float) Math.pow(1 - d / length, 0.8), 0.3f);
        }

        void stalagmite(Context at, int bed, int floor, float length, float radius) {
            int under = floor - bed; // through the water of a lake to stand on its bed
            int last = (int) Math.ceil(length + under) - 1;
            int thin = 0;
            for (int d = 0; d <= last; d++) {
                if (standing(d, last, under, length, radius) < THIN) thin++;
            }
            boolean calcite = thin > last || band(bed + last - thin).isOf(Blocks.CALCITE);
            for (int d = 0; d <= last; d++) {
                fill(at, bed + d, standing(d, last, under, length, radius), false, calcite ? POINTED_CALCITE.pointing(Direction.UP, last - d, thin) : null);
            }
        }

        // the top is always a point
        private static float standing(int d, int last, int under, float length, float radius) {
            if (d == last && d >= under) return 0;
            return Math.max(radius * (float) Math.pow(1 - Math.max(0, d - under) / length, 0.9), 0.3f);
        }

        // where it is one block wide it is the point, if it has one, out of the water
        private void fill(Context at, int y, float radius, boolean fluted, BlockState point) {
            if (point != null && radius < THIN) {
                int bx = (int) Math.floor(x);
                int bz = (int) Math.floor(z);
                if (!at.inside(bx, bz)) return;
                BlockState here = at.blocks.get(bx, y, bz);
                if (here.isAir()) at.blocks.put(bx, y, bz, point);
                else if (here.isOf(Blocks.WATER)) at.blocks.put(bx, y, bz, band(y));
                return;
            }
            int reach = (int) Math.ceil(radius * 1.15f) + 1;
            int cx = (int) Math.floor(x);
            int cz = (int) Math.floor(z);
            for (int bx = cx - reach; bx <= cx + reach; bx++) {
                for (int bz = cz - reach; bz <= cz + reach; bz++) {
                    if (!at.inside(bx, bz)) continue;
                    float px = bx + 0.5f - x;
                    float pz = bz + 0.5f - z;
                    float r = radius;
                    if (fluted && radius >= 1.4f) r *= 1 + 0.12f * MathHelper.cos(flutes * (float) Math.atan2(pz, px) + twist * y);
                    boolean core = bx == cx && bz == cz;
                    if (px * px + pz * pz >= r * r && !core) continue;
                    BlockState here = at.blocks.get(bx, y, bz);
                    if (!here.isAir() && !here.isOf(Blocks.WATER)) continue;
                    at.blocks.put(bx, y, bz, band(y));
                }
            }
        }
    }

    // soda straws: thin threads hanging in clusters
    private static void straws(Context at, Aglarond.Chamber c) {
        int count = Math.round(c.rx * c.rz * STRAWS * c.richness);
        for (int i = 0; i < count; i++) {
            int h = mix(c.hash, 500 + i, 0);
            float[] p = pointIn(c, h, 0.8f);
            if (p == null) continue;
            int px = (int) Math.floor(p[0]);
            int pz = (int) Math.floor(p[1]);
            if (!at.inside(px, pz)) continue;
            int top = ceiling(at.air, c, px, pz);
            int length = 1 + Math.floorMod(mix(h, 1, 1), 4);
            int run = 0;
            while (run < length && top - run > c.floor + 2 && at.blocks.get(px, top - run, pz).isAir()) run++;
            for (int k = 0; k < run; k++) at.blocks.put(px, top - k, pz, POINTED_CALCITE.pointing(Direction.DOWN, run - 1 - k, run));
        }
    }

    // curtains hang along a sloping roof, in folds of white and rose
    private static void curtains(Context at, Aglarond.Chamber c) {
        if (c.richness < 0.4f) return;
        for (int x = Math.max(c.box.minX(), at.startX); x <= Math.min(c.box.maxX(), at.startX + 15); x++) {
            for (int z = Math.max(c.box.minZ(), at.startZ); z <= Math.min(c.box.maxZ(), at.startZ + 15); z++) {
                if (c.spread(x + 0.5f, z + 0.5f) > 1 || corridor(at.caves, x + 0.5f, z + 0.5f, CORRIDOR)) continue;
                float line = (float) SimplexNoise.noise((x + at.ox()) / CURTAIN_SCALE, (z + at.oz()) / CURTAIN_SCALE);
                if (Math.abs(line) > CURTAIN_LINE) continue;
                int top = ceiling(at.air, c, x, z);
                if (top <= c.floor + 3) continue;
                int slope = Math.max(Math.abs(ceiling(at.air, c, x + 2, z) - ceiling(at.air, c, x - 2, z)),
                        Math.abs(ceiling(at.air, c, x, z + 2) - ceiling(at.air, c, x, z - 2)));
                if (slope < 2) continue;
                float edge = (float) SimplexNoise.noise((x + at.oz()) / 2.5, (z + at.ox()) / 2.5);
                int length = Math.min(CURTAIN_MAX, Math.round((1 + slope * 0.7f) * c.richness * (1 + 0.4f * edge)));
                boolean rose = SimplexNoise.noise((x + at.ox()) / 1.5, (z + at.oz()) / 1.5) > 0.3;
                int run = 0;
                while (run < length && top - run > c.floor + 2 && at.blocks.get(x, top - run, z).isAir()) run++;
                // a white fold ends in a point of calcite
                for (int k = 0; k < run; k++) {
                    int fromTip = run - 1 - k;
                    at.blocks.put(x, top - k, z, rose ? QUARTZITE : fromTip < SPIKE ? POINTED_CALCITE.pointing(Direction.DOWN, fromTip, Math.min(run, SPIKE)) : CALCITE);
                }
            }
        }
    }

    // rimstone: shallow still pools held in by little dams of flowstone
    private record Pool(float x, float z, float radius) {
    }

    private static List<Pool> pools(Context at, Aglarond.Chamber c) {
        List<Pool> pools = new ArrayList<>();
        if (c.kind == Aglarond.Chamber.Kind.OUTER) return pools;
        int count = 1 + Math.round(3 * c.richness) - (c.lake > 0 ? 1 : 0);
        for (int i = 0; i < count; i++) {
            int h = mix(c.hash, 700 + i, 0);
            float[] p = pointIn(c, h, 0.6f);
            float radius = 2 + 2.2f * (mix(h, 1, 1) & 0xFF) / 255f;
            if (p == null || corridor(at.caves, p[0], p[1], CORRIDOR + radius)) continue;
            pools.add(new Pool(p[0], p[1], radius));
        }
        return pools;
    }

    // inside a pool's water, a function of position only, so neighbouring chunks agree on the dams
    private static boolean pooled(Context at, List<Pool> pools, int x, int z) {
        float wobble = 1 + 0.25f * (float) SimplexNoise.noise((x + at.ox()) / 2.0, (z + at.oz()) / 2.0);
        for (Pool p : pools) {
            if (sq(x + 0.5f - p.x) + sq(z + 0.5f - p.z) < sq(p.radius * wobble - 0.9f)) return true;
        }
        return false;
    }

    private static boolean nearPool(List<Pool> pools, float x, float z, float margin) {
        for (Pool p : pools) {
            if (sq(x - p.x) + sq(z - p.z) < sq(p.radius * 1.25f + margin)) return true;
        }
        return false;
    }

    // water only on solid ground, and every open side dammed, so no pool can spill
    private static void rimstone(Context at, Aglarond.Chamber c, List<Pool> pools) {
        int y = c.floor;
        for (Pool p : pools) {
            int reach = (int) Math.ceil(p.radius * 1.25f) + 2;
            for (int x = (int) p.x - reach; x <= (int) p.x + reach; x++) {
                for (int z = (int) p.z - reach; z <= (int) p.z + reach; z++) {
                    if (!at.inside(x, z) || !at.blocks.get(x, y, z).isAir()) continue;
                    if (pooled(at, pools, x, z)) {
                        at.blocks.put(x, y, z, at.solid(x, y - 1, z) ? WATER : TRAVERTINE);
                    } else if (pooled(at, pools, x + 1, z) || pooled(at, pools, x - 1, z) || pooled(at, pools, x, z + 1) || pooled(at, pools, x, z - 1)) {
                        at.blocks.put(x, y, z, TRAVERTINE);
                    }
                }
            }
        }
    }

    // pockets of crystal in the walls
    private static void vugs(Context at, Aglarond.Chamber c) {
        int count = 1 + Math.round(3 * c.richness);
        for (int i = 0; i < count; i++) {
            int h = mix(c.hash, 900 + i, 0);
            float angle = (float) (Math.PI * 2 * (h & 0xFFFF) / 65536f);
            int y = c.floor + 2 + Math.round((mix(h, 1, 1) & 0xFF) / 255f * c.height * 0.4f);
            float dx = MathHelper.cos(angle);
            float dz = MathHelper.sin(angle);
            float step = 0;
            while (step < 60 && at.caves.carves((int) Math.floor(c.x + dx * step), y, (int) Math.floor(c.z + dz * step))) step += 0.5f;
            if (step >= 60) continue;
            Vug vug = new Vug(c.x + dx * (step + 1.4f), y + 0.5f, c.z + dz * (step + 1.4f));
            for (int x = (int) vug.x - 3; x <= (int) vug.x + 3; x++) {
                for (int z = (int) vug.z - 3; z <= (int) vug.z + 3; z++) {
                    if (!at.inside(x, z)) continue;
                    for (int by = y - 3; by <= y + 3; by++) {
                        boolean hollow = vug.hollow(x, by, z);
                        if (hollow) {
                            if (!at.air.at(x, by, z)) at.blocks.put(x, by, z, AIR);
                        } else if (vug.lining(at.air, x, by, z)) {
                            at.blocks.put(x, by, z, Blocks.AMETHYST_BLOCK.getDefaultState());
                        } else {
                            continue;
                        }
                        if (!hollow || (mix(x, by, z ^ h) & 3) == 0) continue;
                        // crystals grow inward off the lining
                        for (Direction toward : Direction.values()) {
                            if (!vug.lining(at.air, x - toward.getOffsetX(), by - toward.getOffsetY(), z - toward.getOffsetZ())) continue;
                            BlockState crystal = (mix(x, by, z ^ h) & 4) == 0 ? Blocks.AMETHYST_CLUSTER.getDefaultState() : Blocks.MEDIUM_AMETHYST_BUD.getDefaultState();
                            at.blocks.put(x, by, z, crystal.with(AmethystClusterBlock.FACING, toward));
                            break;
                        }
                    }
                }
            }
        }
    }

    // decided from the shape and the carving alone, so neighbouring chunks agree
    private record Vug(float x, float y, float z) {
        float distance(int bx, int by, int bz) {
            return (float) Math.sqrt(sq(bx + 0.5f - x) + sq(by + 0.5f - y) + sq(bz + 0.5f - z));
        }

        boolean hollow(int bx, int by, int bz) {
            return distance(bx, by, bz) < 1.4f;
        }

        boolean lining(CarveMask air, int bx, int by, int bz) {
            float d = distance(bx, by, bz);
            return d >= 1.4f && d < 2.4f && !air.at(bx, by, bz);
        }
    }

    // the Deeping-stream, in a groove down the middle of each way from the great hall to the mouth
    private static void stream(Context at, Aglarond.Avenue way) {
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int x = Math.max(way.box.minX(), at.startX); x <= Math.min(way.box.maxX(), at.startX + 15); x++) {
            for (int z = Math.max(way.box.minZ(), at.startZ); z <= Math.min(way.box.maxZ(), at.startZ + 15); z++) {
                float px = x + 0.5f - way.ax;
                float pz = z + 0.5f - way.az;
                float t = px * way.dx + pz * way.dz;
                if (t < 0 || t > way.length) continue;
                float f = t / way.length;
                float meander = 0.9f * MathHelper.sin(t * 0.21f + way.phase);
                if (Math.abs(pz * way.dx - px * way.dz - way.centre(f) - meander) >= 0.85f) continue;
                int y = way.floorAt(f) - 1;
                if (!at.air.at(x, y + 1, z) || !at.solid(x, y, z)) continue;
                at.blocks.put(x, y, z, WATER);
                at.chunk.markBlockForPostProcessing(pos.set(x, y, z));
            }
        }
    }

    // a point spread evenly over a hall's floor, within a share of its radius
    private static float[] pointIn(Aglarond.Chamber c, int h, float share) {
        float angle = (float) (Math.PI * 2 * (h & 0xFFFF) / 65536f);
        float out = share * (float) Math.sqrt(((h >>> 16) & 0xFFFF) / 65536f);
        float u = MathHelper.cos(angle) * c.rx * out;
        float v = MathHelper.sin(angle) * c.rz * out;
        return new float[]{c.x + u * c.cos - v * c.sin, c.z + u * c.sin + v * c.cos};
    }

    // the highest air over a floor block, and the lake bed under it
    private static int ceiling(Walk.Carving air, Aglarond.Chamber c, int x, int z) {
        int y = c.floor;
        int limit = c.box.maxY() + 1;
        while (y < limit && air.at(x, y + 1, z)) y++;
        return y;
    }

    private static int ceiling(Aglarond caves, Aglarond.Chamber c, int x, int z) {
        return ceiling(caves::carves, c, x, z);
    }

    private static int bed(Aglarond caves, int x, int floor, int z) {
        int y = floor;
        while (y > floor - 10 && caves.water(x, y - 1, z)) y--;
        return y;
    }

    private static boolean corridor(Aglarond caves, float x, float z, float margin) {
        for (Aglarond.Avenue way : caves.avenues) {
            float px = x - way.ax;
            float pz = z - way.az;
            float t = px * way.dx + pz * way.dz;
            if (t < 0 || t > way.length) continue;
            if (Math.abs(pz * way.dx - px * way.dz - way.centre(t / way.length)) < way.halfWidth + margin) return true;
        }
        return false;
    }
}
