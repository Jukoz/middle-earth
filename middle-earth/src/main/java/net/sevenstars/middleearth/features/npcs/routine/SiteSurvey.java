package net.sevenstars.middleearth.features.npcs.routine;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.features.npcs.pathing.NpcPathNodeMaker;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

// What the ground around a site offers: a ring to walk, shores, hunting grounds and trees
class SiteSurvey {
    private static final int[] RADII = {8, 14, 20, 26, 32, 38, 44};
    private static final int SAMPLE_SPACING = 6;
    private static final int SAMPLE_MARGIN = 36;
    private static final int MINIMUM_SAMPLES_PER_CIRCLE = 8;
    // Radians per block of radius
    private static final double SAMPLE_TWIST = 0.37;
    // x, z pairs
    private static final int[] SAMPLES = samples();

    private static int[] samples() {
        List<Integer> samples = new ArrayList<>();
        for(int radius : RADII) {
            int amount = Math.max(MINIMUM_SAMPLES_PER_CIRCLE, Math.round(MathHelper.TAU * radius / SAMPLE_SPACING));
            for(int i = 0; i < amount; i++) {
                double angle = MathHelper.TAU * i / amount + radius * SAMPLE_TWIST;
                samples.add(MathHelper.floor(Math.cos(angle) * radius));
                samples.add(MathHelper.floor(Math.sin(angle) * radius));
            }
        }
        return samples.stream().mapToInt(Integer::intValue).toArray();
    }

    private static final int MINIMUM_RING_POINTS = 8;
    private static final int MAXIMUM_RING_POINTS = 40;
    // Short enough to path between ring points
    static final int RING_SPACING = 14;
    private static final int OUTLINE_OFFSET = 6;
    // Outwards first, inwards lands between houses
    private static final int[] RING_ADJUSTMENTS = {0, 4, 8, -4};
    private static final int MINIMUM_OUTLINE_CORNERS = 3;
    private static final int MAXIMUM_SHORES = 8;
    private static final int MINIMUM_WATER_WIDTH = 2;
    private static final int SHORE_SEARCH = 3;
    private static final int SHORE_SPACING = 6;
    private static final int MAXIMUM_GROUNDS = 12;
    private static final int GROUND_SPACING = 10;
    private static final int GROUND_MINIMUM_RADIUS = 20;
    private static final int MAXIMUM_TREES = 24;
    private static final int TREE_SPACING = 3;
    // One sample rarely hits a trunk
    private static final int TREE_SEARCH = 2;
    private static final int MAXIMUM_TRUNK_HEIGHT = 24;
    private static final int MAXIMUM_HEIGHT_DIFFERENCE = 14;
    private static final int MAXIMUM_SHORE_STEP = 2;
    private static final int ENCLOSED_DEPTH = 16;
    private static final int BUILDING_HEIGHT = 4;
    private static final int FLOOD_MARGIN = 8;
    // Max drop the flood steps down, it can't climb back
    private static final int FLOOD_DROP = 3;
    // Columns flooded per scan step
    private static final int FLOOD_COLUMNS = 128;
    private static final int UNLOADED = 0;
    private static final int WATER = 1;
    private static final int LAND = 2;
    private static final int BLOCKED = 3;

    private final int centerX;
    private final int centerZ;
    private final int surfaceY;
    private final int ringRadius;
    // Half the structure's width, 0 in the wild
    private final int extent;
    private final float sampleScale;
    // Buildings by the chunks they cover
    private final Long2ObjectOpenHashMap<List<BlockBox>> buildings = new Long2ObjectOpenHashMap<>();
    private final Supplier<List<BlockPos>> doors;
    private final Runnable ringChanged;

    private final List<BlockPos> shoreStands = new ArrayList<>();
    private final List<BlockPos> shoreWaters = new ArrayList<>();
    private final List<BlockPos> grounds = new ArrayList<>();
    private final List<BlockPos> trees = new ArrayList<>();
    private final List<BlockPos> ring = new ArrayList<>();
    // Ring points as x, z, outX, outZ (out = away from the place)
    private final List<double[]> ringPlan = new ArrayList<>();

    private final BlockPos.Mutable probe = new BlockPos.Mutable();
    private int probeY;
    private int cursor = 0;
    private boolean ready;
    // Slot of each ring point, so a late one goes in the right place
    private final List<Integer> ringPlanIndex = new ArrayList<>();
    // Unloaded ground, rechecked once it loads
    private final List<Integer> missedRing = new ArrayList<>();
    private final List<Integer> missedSamples = new ArrayList<>();

    // Set while a flood runs, which spreads over scan steps
    @Nullable private Long2IntOpenHashMap reached;
    // Column to kind and height, so a column is classified once per flood
    private final Long2IntOpenHashMap floodClasses = new Long2IntOpenHashMap();
    private final LongArrayFIFOQueue open = new LongArrayFIFOQueue();
    private int floodBound;

    SiteSurvey(ServerWorld world, int centerX, int centerZ, boolean enclosed, int ringRadius, boolean ring, int extent, @Nullable List<BlockBox> pieces,
               Supplier<List<BlockPos>> doors, Runnable ringChanged) {
        this.extent = extent;
        this.sampleScale = Math.max(1f, (extent + SAMPLE_MARGIN) / (float) RADII[RADII.length - 1]);
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.surfaceY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, centerX, centerZ);
        this.ringRadius = ringRadius;
        this.doors = doors;
        this.ringChanged = ringChanged;
        if(pieces != null) {
            // Roads and squares can be walked on, so they aren't buildings
            for(BlockBox piece : pieces) {
                if(piece.getBlockCountY() < BUILDING_HEIGHT)
                    continue;
                for(int chunkX = piece.getMinX() >> 4; chunkX <= piece.getMaxX() >> 4; chunkX++) {
                    for(int chunkZ = piece.getMinZ() >> 4; chunkZ <= piece.getMaxZ() >> 4; chunkZ++)
                        buildings.computeIfAbsent(ChunkPos.toLong(chunkX, chunkZ), key -> new ArrayList<>()).add(piece);
                }
            }
        }
        if(ringRadius > 0 && ring)
            planRing(pieces);
        this.ready = enclosed;
    }

    static boolean isEnclosed(ServerWorld world, int x, int z, int y) {
        return groundY(world, x, z) - y > ENCLOSED_DEPTH;
    }

    // Enclosed only if all of it is. Decided from the structure, not whoever asks first
    static boolean isEnclosed(ServerWorld world, BlockBox box) {
        BlockPos center = box.getCenter();
        int[][] spots = {{center.getX(), center.getZ()}, {box.getMinX(), box.getMinZ()}, {box.getMinX(), box.getMaxZ()},
                {box.getMaxX(), box.getMinZ()}, {box.getMaxX(), box.getMaxZ()}};
        for(int[] spot : spots) {
            if(world.isChunkLoaded(spot[0] >> 4, spot[1] >> 4) && !isEnclosed(world, spot[0], spot[1], box.getMaxY()))
                return false;
        }
        return true;
    }

    // Below water and leaves, so a lake's depth isn't underground
    private static int groundY(ServerWorld world, int x, int z) {
        return Math.min(world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z), world.getTopY(Heightmap.Type.OCEAN_FLOOR, x, z));
    }

    boolean isReady() {
        return ready;
    }

    int getSurfaceY() {
        return surfaceY;
    }

    int getExtent() {
        return extent;
    }

    int getCenterX() {
        return centerX;
    }

    int getCenterZ() {
        return centerZ;
    }

    // How far the survey samples out from the centre
    int reach() {
        return (int) (RADII[RADII.length - 1] * sampleScale);
    }

    List<BlockPos> getShoreStands() {
        return shoreStands;
    }

    BlockPos getShoreWater(int index) {
        return shoreWaters.get(index);
    }

    List<BlockPos> getGrounds() {
        return grounds;
    }

    List<BlockPos> getTrees() {
        return trees;
    }

    List<BlockPos> getRing() {
        return ring;
    }

    // One sample per call, true when done
    boolean scanStep(ServerWorld world) {
        if(reached != null)
            return stepFlood(world);
        if(ready)
            return true;

        int ringSteps = ringPlan.size();
        if(cursor < ringSteps) {
            scanRingPoint(world, cursor);
        } else {
            int sample = (cursor - ringSteps) * 2;
            if(sample >= SAMPLES.length) {
                startFlood(world);
                if(reached == null) {
                    ready = true;
                    return true;
                }
                return false;
            }
            int x = centerX + Math.round(SAMPLES[sample] * sampleScale);
            int z = centerZ + Math.round(SAMPLES[sample + 1] * sampleScale);
            if(world.isChunkLoaded(x >> 4, z >> 4))
                scanSample(world, x, z);
            else
                missedSamples.add(sample);
        }
        cursor++;
        return false;
    }

    // Follows the outline, a circle cuts through places that aren't round
    private void planRing(@Nullable List<BlockBox> pieces) {
        List<double[]> outline = pieces == null ? List.of() : outlineOf(pieces);
        if(outline.size() < MINIMUM_OUTLINE_CORNERS) {
            planCircle();
            return;
        }
        planOutline(outline);
        if(ringPlan.size() < MINIMUM_RING_POINTS) {
            ringPlan.clear();
            planCircle();
        }
    }

    private void planCircle() {
        int points = MathHelper.clamp(Math.round(MathHelper.TAU * ringRadius / RING_SPACING), MINIMUM_RING_POINTS, MAXIMUM_RING_POINTS);
        for(int i = 0; i < points; i++) {
            double angle = MathHelper.TAU * i / points;
            ringPlan.add(new double[]{centerX + Math.cos(angle) * ringRadius, centerZ + Math.sin(angle) * ringRadius, Math.cos(angle), Math.sin(angle)});
        }
    }

    private void planOutline(List<double[]> outline) {
        double perimeter = 0;
        for(int i = 0; i < outline.size(); i++) {
            double[] from = outline.get(i);
            double[] to = outline.get((i + 1) % outline.size());
            perimeter += Math.hypot(to[0] - from[0], to[1] - from[1]);
        }
        double spacing = Math.max(RING_SPACING, perimeter / MAXIMUM_RING_POINTS);
        double untilNext = 0;
        for(int i = 0; i < outline.size(); i++) {
            double[] from = outline.get(i);
            double[] to = outline.get((i + 1) % outline.size());
            double length = Math.hypot(to[0] - from[0], to[1] - from[1]);
            if(length == 0)
                continue;
            double alongX = (to[0] - from[0]) / length;
            double alongZ = (to[1] - from[1]) / length;
            // The outline runs counterclockwise, so this direction points outwards
            double outX = alongZ;
            double outZ = -alongX;
            double walked = untilNext;
            for(; walked < length; walked += spacing)
                ringPlan.add(new double[]{from[0] + alongX * walked + outX * OUTLINE_OFFSET, from[1] + alongZ * walked + outZ * OUTLINE_OFFSET, outX, outZ});
            untilNext = walked - length;
        }
    }

    private static List<double[]> outlineOf(List<BlockBox> pieces) {
        List<double[]> corners = new ArrayList<>();
        for(BlockBox piece : pieces) {
            corners.add(new double[]{piece.getMinX(), piece.getMinZ()});
            corners.add(new double[]{piece.getMaxX() + 1, piece.getMinZ()});
            corners.add(new double[]{piece.getMinX(), piece.getMaxZ() + 1});
            corners.add(new double[]{piece.getMaxX() + 1, piece.getMaxZ() + 1});
        }
        if(corners.isEmpty())
            return List.of();
        corners.sort((a, b) -> a[0] != b[0] ? Double.compare(a[0], b[0]) : Double.compare(a[1], b[1]));
        List<double[]> hull = new ArrayList<>();
        for(int pass = 0; pass < 2; pass++) {
            int start = hull.size();
            for(int i = 0; i < corners.size(); i++) {
                double[] corner = corners.get(pass == 0 ? i : corners.size() - 1 - i);
                while(hull.size() >= start + 2 && turn(hull.get(hull.size() - 2), hull.get(hull.size() - 1), corner) <= 0)
                    hull.remove(hull.size() - 1);
                hull.add(corner);
            }
            hull.remove(hull.size() - 1);
        }
        return hull;
    }

    private static double turn(double[] a, double[] b, double[] c) {
        return (b[0] - a[0]) * (c[1] - a[1]) - (b[1] - a[1]) * (c[0] - a[0]);
    }

    private void scanRingPoint(ServerWorld world, int index) {
        double[] planned = ringPlan.get(index);
        for(int adjustment : RING_ADJUSTMENTS) {
            int x = MathHelper.floor(planned[0] + planned[2] * adjustment);
            int z = MathHelper.floor(planned[1] + planned[3] * adjustment);
            int kind = classify(world, x, z);
            if(kind == UNLOADED) {
                missedRing.add(index);
                return;
            }
            if(kind == LAND && !isBuilding(x, probeY, z) && !isField(world)) {
                int at = 0;
                while(at < ringPlanIndex.size() && ringPlanIndex.get(at) < index)
                    at++;
                ring.add(at, new BlockPos(x, probeY, z));
                ringPlanIndex.add(at, index);
                ringChanged.run();
                return;
            }
        }
    }

    private void scanSample(ServerWorld world, int x, int z) {
        if(trees.size() < MAXIMUM_TREES) {
            for(int offsetX = -TREE_SEARCH; offsetX <= TREE_SEARCH; offsetX++) {
                for(int offsetZ = -TREE_SEARCH; offsetZ <= TREE_SEARCH; offsetZ++)
                    scanTree(world, x + offsetX, z + offsetZ);
            }
        }
        int kind = classify(world, x, z);
        if(kind == LAND) {
            int distanceX = x - centerX;
            int distanceZ = z - centerZ;
            int minimumRadius = Math.max(GROUND_MINIMUM_RADIUS, extent);
            boolean farEnough = distanceX * distanceX + distanceZ * distanceZ >= minimumRadius * minimumRadius;
            if(farEnough && grounds.size() < MAXIMUM_GROUNDS && !isBuilding(x, probeY, z) && !isField(world) && isSpaced(grounds, x, z, GROUND_SPACING))
                grounds.add(new BlockPos(x, probeY, z));
        } else if(kind == WATER && shoreStands.size() < MAXIMUM_SHORES) {
            scanShore(world, x, z, probeY);
        }
    }

    // Logs with leaves on top are a tree; building logs and fence posts have none
    private void scanTree(ServerWorld world, int x, int z) {
        if(trees.size() >= MAXIMUM_TREES || !isAroundLoaded(world, x, z))
            return;
        int top = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) - 1;
        BlockPos.Mutable at = new BlockPos.Mutable(x, top, z);
        if(!world.getBlockState(at).isIn(BlockTags.LOGS) || isBuilding(x, top, z) || Math.hypot(x - centerX, z - centerZ) > RADII[RADII.length - 1] * sampleScale)
            return;
        boolean crowned = world.getBlockState(at.set(x, top + 1, z)).isIn(BlockTags.LEAVES);
        for(int side = 0; side < 4 && !crowned; side++)
            crowned = world.getBlockState(at.set(x + (side == 0 ? 1 : side == 1 ? -1 : 0), top, z + (side == 2 ? 1 : side == 3 ? -1 : 0))).isIn(BlockTags.LEAVES);
        if(!crowned)
            return;
        int base = top;
        while(top - base < MAXIMUM_TRUNK_HEIGHT && world.getBlockState(at.set(x, base - 1, z)).isIn(BlockTags.LOGS))
            base--;
        at.set(x, base - 1, z);
        BlockState ground = world.getBlockState(at);
        if(!ground.isSolidBlock(world, at) || !world.getFluidState(at.up()).isEmpty() || !isSpaced(trees, x, z, TREE_SPACING))
            return;
        // Bush: a log with leaves all round and no room to stand and swing at it
        BlockPos foot = new BlockPos(x, base, z);
        if(NestSurvey.standNextTo(world, foot, foot) == null)
            return;
        trees.add(foot);
    }

    // Reads neighbouring columns, which could load a chunk at the edge of the loaded ones
    private static boolean isAroundLoaded(ServerWorld world, int x, int z) {
        return world.isChunkLoaded((x - 1) >> 4, (z - 1) >> 4) && world.isChunkLoaded((x + 1) >> 4, (z - 1) >> 4)
                && world.isChunkLoaded((x - 1) >> 4, (z + 1) >> 4) && world.isChunkLoaded((x + 1) >> 4, (z + 1) >> 4);
    }

    private void scanShore(ServerWorld world, int waterX, int waterZ, int waterY) {
        int towardsX = Integer.signum(centerX - waterX);
        int towardsZ = Integer.signum(centerZ - waterZ);
        if(towardsX == 0 && towardsZ == 0)
            return;
        if(classify(world, waterX - towardsX * MINIMUM_WATER_WIDTH, waterZ - towardsZ * MINIMUM_WATER_WIDTH) != WATER)
            return;

        for(int step = 1; step <= SHORE_SEARCH; step++) {
            int x = waterX + towardsX * step;
            int z = waterZ + towardsZ * step;
            int kind = classify(world, x, z);
            if(kind == WATER)
                continue;
            if(kind != LAND || Math.abs(probeY - waterY) > MAXIMUM_SHORE_STEP)
                return;
            if(isBuilding(x, probeY, z) || !isSpaced(shoreStands, x, z, SHORE_SPACING))
                return;
            shoreStands.add(new BlockPos(x, probeY, z));
            shoreWaters.add(new BlockPos(x - towardsX, waterY - 1, z - towardsZ));
            return;
        }
    }

    // Sets probeY to the first free block's height
    private int classify(ServerWorld world, int x, int z) {
        if(!world.isChunkLoaded(x >> 4, z >> 4))
            return UNLOADED;
        probeY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
        probe.set(x, probeY - 1, z);
        BlockState below = world.getBlockState(probe);
        FluidState fluid = below.getFluidState();
        if(!fluid.isEmpty())
            return fluid.isIn(FluidTags.WATER) ? WATER : BLOCKED;
        // Paths and farmland aren't full blocks but still count as ground
        boolean ground = below.isSolidBlock(world, probe) || below.isOf(Blocks.DIRT_PATH) || below.isOf(Blocks.FARMLAND);
        if(!ground || below.isIn(BlockTags.LOGS))
            return BLOCKED;
        if(Math.abs(probeY - surfaceY) > MAXIMUM_HEIGHT_DIFFERENCE)
            return BLOCKED;
        return LAND;
    }

    boolean hasMissed() {
        return ready && reached == null && (!missedRing.isEmpty() || !missedSamples.isEmpty());
    }

    // True when it started a flood, which needs scan steps to finish
    boolean retryMissed(ServerWorld world) {
        if(reached != null)
            return false;
        List<Integer> ringLeft = new ArrayList<>(missedRing);
        List<Integer> samplesLeft = new ArrayList<>(missedSamples);
        missedRing.clear();
        missedSamples.clear();
        for(int index : ringLeft)
            scanRingPoint(world, index);
        for(int sample : samplesLeft) {
            int x = centerX + Math.round(SAMPLES[sample] * sampleScale);
            int z = centerZ + Math.round(SAMPLES[sample + 1] * sampleScale);
            if(world.isChunkLoaded(x >> 4, z >> 4))
                scanSample(world, x, z);
            else
                missedSamples.add(sample);
        }
        if(missedRing.size() < ringLeft.size() || missedSamples.size() < samplesLeft.size())
            startFlood(world);
        return reached != null;
    }

    // Flood from the doors: up 1 block, down a few, never water or roofs
    private void startFlood(ServerWorld world) {
        ringChanged.run();
        List<BlockPos> doorList = doors.get();
        if(doorList.isEmpty())
            return;
        int bound = 0;
        for(List<BlockPos> spots : List.of(ring, grounds, trees, shoreStands)) {
            for(BlockPos spot : spots)
                bound = Math.max(bound, Math.max(Math.abs(spot.getX() - centerX), Math.abs(spot.getZ() - centerZ)) + FLOOD_MARGIN);
        }
        Long2IntOpenHashMap found = new Long2IntOpenHashMap();
        found.defaultReturnValue(Integer.MIN_VALUE);
        floodClasses.clear();
        floodClasses.defaultReturnValue(Integer.MIN_VALUE);
        open.clear();
        reached = found;
        for(BlockPos door : doorList) {
            for(int side = 0; side < 4; side++) {
                int x = door.getX() + (side == 0 ? 1 : side == 1 ? -1 : 0);
                int z = door.getZ() + (side == 2 ? 1 : side == 3 ? -1 : 0);
                if(floodClassify(world, x, z) == LAND && Math.abs(probeY - door.getY()) <= 1 && !isBuilding(x, probeY, z) && found.putIfAbsent(column(x, z), probeY) == found.defaultReturnValue())
                    open.enqueue(column(x, z));
            }
        }
        if(open.isEmpty()) {
            reached = null;
            return;
        }
        floodBound = bound;
    }

    // True when the flood is done and the spots are pruned
    private boolean stepFlood(ServerWorld world) {
        Long2IntOpenHashMap found = reached;
        for(int columns = 0; columns < FLOOD_COLUMNS && !open.isEmpty(); columns++) {
            long at = open.dequeueLong();
            int x = (int) (at >> 32);
            int z = (int) at;
            int height = found.get(at);
            for(int side = 0; side < 4; side++) {
                int nextX = x + (side == 0 ? 1 : side == 1 ? -1 : 0);
                int nextZ = z + (side == 2 ? 1 : side == 3 ? -1 : 0);
                long next = column(nextX, nextZ);
                if(Math.abs(nextX - centerX) > floodBound || Math.abs(nextZ - centerZ) > floodBound || found.containsKey(next))
                    continue;
                if(floodClassify(world, nextX, nextZ) != LAND || probeY - height > 1 || height - probeY > FLOOD_DROP || isBuilding(nextX, probeY, nextZ))
                    continue;
                found.put(next, probeY);
                open.enqueue(next);
            }
        }
        if(!open.isEmpty())
            return false;
        pruneUnreached(found);
        reached = null;
        floodClasses.clear();
        ready = true;
        return true;
    }

    private int floodClassify(ServerWorld world, int x, int z) {
        long key = column(x, z);
        int known = floodClasses.get(key);
        if(known != Integer.MIN_VALUE) {
            probeY = known >> 2;
            return known & 3;
        }
        int kind = classify(world, x, z);
        if(kind != UNLOADED)
            floodClasses.put(key, (probeY << 2) | kind);
        return kind;
    }

    private void pruneUnreached(Long2IntOpenHashMap found) {
        for(int i = ring.size() - 1; i >= 0; i--) {
            if(!found.containsKey(column(ring.get(i).getX(), ring.get(i).getZ()))) {
                ring.remove(i);
                ringPlanIndex.remove(i);
                ringChanged.run();
            }
        }
        grounds.removeIf(spot -> !found.containsKey(column(spot.getX(), spot.getZ())));
        // A trunk fills its own column, so it's reached from a neighbouring one
        trees.removeIf(tree -> !found.containsKey(column(tree.getX() + 1, tree.getZ())) && !found.containsKey(column(tree.getX() - 1, tree.getZ()))
                && !found.containsKey(column(tree.getX(), tree.getZ() + 1)) && !found.containsKey(column(tree.getX(), tree.getZ() - 1)));
        for(int i = shoreStands.size() - 1; i >= 0; i--) {
            if(!found.containsKey(column(shoreStands.get(i).getX(), shoreStands.get(i).getZ()))) {
                shoreStands.remove(i);
                shoreWaters.remove(i);
            }
        }
    }

    private static long column(int x, int z) {
        return ((long) x << 32) | (z & 0xFFFFFFFFL);
    }

    // Crops don't show in the heightmap, so a field looks like ground
    private boolean isField(ServerWorld world) {
        BlockState ground = world.getBlockState(probe);
        return NpcPathNodeMaker.isFarmland(ground);
    }

    boolean isBuilding(int x, int y, int z) {
        List<BlockBox> boxes = buildings.get(ChunkPos.toLong(x >> 4, z >> 4));
        if(boxes == null)
            return false;
        for(int i = 0; i < boxes.size(); i++) {
            BlockBox box = boxes.get(i);
            if(x >= box.getMinX() && x <= box.getMaxX() && z >= box.getMinZ() && z <= box.getMaxZ() && y - box.getMinY() >= BUILDING_HEIGHT)
                return true;
        }
        return false;
    }

    private static boolean isSpaced(List<BlockPos> others, int x, int z, int spacing) {
        for(BlockPos other : others) {
            int distanceX = other.getX() - x;
            int distanceZ = other.getZ() - z;
            if(distanceX * distanceX + distanceZ * distanceZ < spacing * spacing)
                return false;
        }
        return true;
    }
}
