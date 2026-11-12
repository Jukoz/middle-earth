package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

// The places around one structure, or one patch of wilderness, shared by every npc living there
public class RoutineSite {
    private static final int[] RADII = {8, 14, 20, 26, 32, 38, 44};
    private static final int SAMPLE_SPACING = 6;
    private static final int SAMPLE_MARGIN = 36;
    // x and z pairs
    private static final int[] SAMPLES;

    private static final int RING_POINTS = 8;
    private static final int[] RING_ADJUSTMENTS = {0, 4, -4};
    private static final int[][] MEETING_OFFSETS = {{0, 0}, {4, 0}, {-4, 0}, {0, 4}, {0, -4}, {7, 7}, {-7, 7}, {7, -7}, {-7, -7}};
    private static final int MAXIMUM_SHORES = 8;
    private static final int SHORE_SPACING = 6;
    private static final int MAXIMUM_GROUNDS = 12;
    private static final int GROUND_SPACING = 10;
    private static final int GROUND_MINIMUM_RADIUS = 20;
    private static final int MAXIMUM_HEIGHT_DIFFERENCE = 14;
    private static final int MAXIMUM_SHORE_STEP = 2;
    // A site this far under the surface is a hall or a cave
    private static final int ENCLOSED_DEPTH = 16;
    private static final int BUILDING_HEIGHT = 4;

    private static final int UNLOADED = 0;
    private static final int WATER = 1;
    private static final int LAND = 2;
    private static final int BLOCKED = 3;

    private final int centerX;
    private final int centerZ;
    private final int surfaceY;
    private final int ringRadius;
    // Half the width of the structure, 0 in the wild
    private final int extent;
    // Stretches the samples of a large structure out past its edge
    private final float sampleScale;
    private final boolean enclosed;
    private final List<BlockBox> buildings = new ArrayList<>();

    private final List<BlockPos> shoreStands = new ArrayList<>();
    private final List<BlockPos> shoreWaters = new ArrayList<>();
    private final List<BlockPos> grounds = new ArrayList<>();
    private final List<BlockPos> ring = new ArrayList<>();
    private @Nullable BlockPos meetingPoint = null;
    private @Nullable Identifier faction = null;

    private final BlockPos.Mutable probe = new BlockPos.Mutable();
    private int probeY;
    private int cursor = 0;
    private boolean ready;
    long lastUsed;

    // ringRadius 0 leaves the site without a patrol ring
    RoutineSite(ServerWorld world, int centerX, int centerZ, @Nullable Integer referenceY, int ringRadius, int extent, @Nullable List<BlockBox> pieces) {
        this.extent = extent;
        this.sampleScale = Math.max(1f, (extent + SAMPLE_MARGIN) / (float) RADII[RADII.length - 1]);
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.surfaceY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, centerX, centerZ);
        this.ringRadius = ringRadius;
        if(pieces != null) {
            // Roads and squares are pieces too, but those can be walked on
            for(BlockBox piece : pieces) {
                if(piece.getBlockCountY() >= BUILDING_HEIGHT)
                    buildings.add(piece);
            }
        }
        this.enclosed = referenceY != null && surfaceY - referenceY > ENCLOSED_DEPTH;
        this.ready = enclosed;
    }

    public void touch(long time) {
        lastUsed = time;
    }

    public boolean isReady() {
        return ready;
    }

    public boolean supports(RoutineData.Activity activity) {
        return switch (activity) {
            case FISH -> !shoreStands.isEmpty();
            case HUNT, FORAGE -> !grounds.isEmpty();
            case PATROL -> ring.size() >= 3;
            case MEET -> meetingPoint != null;
        };
    }

    public List<BlockPos> getShoreStands() {
        return shoreStands;
    }

    public BlockPos getShoreWater(int index) {
        return shoreWaters.get(index);
    }

    public List<BlockPos> getGrounds() {
        return grounds;
    }

    public @Nullable Identifier getFaction() {
        return faction;
    }

    // The first of its people to live here names the place
    public void claim(@Nullable Identifier faction) {
        if(this.faction == null && ringRadius > 0)
            this.faction = faction;
    }

    public int getSurfaceY() {
        return surfaceY;
    }

    public @Nullable BlockPos getMeetingPoint() {
        return meetingPoint;
    }

    public List<BlockPos> getRing() {
        return ring;
    }

    public int getExtent() {
        return extent;
    }

    public int getCenterX() {
        return centerX;
    }

    public int getCenterZ() {
        return centerZ;
    }

    // One sample a call, true once there is nothing left to look at
    boolean scanStep(ServerWorld world) {
        if(ready)
            return true;

        int ringSteps = ringRadius > 0 ? RING_POINTS : 0;
        // Only settlements have a middle to meet in
        if(cursor == 0 && ringRadius > 0)
            scanMeetingPoint(world);
        if(cursor < ringSteps) {
            scanRingPoint(world, cursor);
        } else {
            int sample = (cursor - ringSteps) * 2;
            if(sample >= SAMPLES.length) {
                ready = true;
                return true;
            }
            scanSample(world, centerX + Math.round(SAMPLES[sample] * sampleScale), centerZ + Math.round(SAMPLES[sample + 1] * sampleScale));
        }
        cursor++;
        return false;
    }

    private void scanMeetingPoint(ServerWorld world) {
        for(int[] offset : MEETING_OFFSETS) {
            int x = centerX + offset[0];
            int z = centerZ + offset[1];
            if(classify(world, x, z) == LAND && !isBuilding(x, probeY, z)) {
                meetingPoint = new BlockPos(x, probeY, z);
                return;
            }
        }
    }

    private void scanRingPoint(ServerWorld world, int index) {
        double angle = MathHelper.TAU * index / RING_POINTS;
        for(int adjustment : RING_ADJUSTMENTS) {
            int radius = ringRadius + adjustment;
            int x = centerX + MathHelper.floor(Math.cos(angle) * radius);
            int z = centerZ + MathHelper.floor(Math.sin(angle) * radius);
            if(classify(world, x, z) == LAND && !isBuilding(x, probeY, z)) {
                ring.add(new BlockPos(x, probeY, z));
                return;
            }
        }
    }

    private void scanSample(ServerWorld world, int x, int z) {
        int kind = classify(world, x, z);
        if(kind == LAND) {
            int distanceX = x - centerX;
            int distanceZ = z - centerZ;
            int minimumRadius = Math.max(GROUND_MINIMUM_RADIUS, extent);
            boolean farEnough = distanceX * distanceX + distanceZ * distanceZ >= minimumRadius * minimumRadius;
            if(farEnough && grounds.size() < MAXIMUM_GROUNDS && !isBuilding(x, probeY, z) && isSpaced(grounds, x, z, GROUND_SPACING))
                grounds.add(new BlockPos(x, probeY, z));
        } else if(kind == WATER && shoreStands.size() < MAXIMUM_SHORES) {
            scanShore(world, x, z, probeY);
        }
    }

    private void scanShore(ServerWorld world, int waterX, int waterZ, int waterY) {
        int towardsX = Integer.signum(centerX - waterX);
        int towardsZ = Integer.signum(centerZ - waterZ);
        if(towardsX == 0 && towardsZ == 0)
            return;
        // A puddle is not worth the walk
        if(classify(world, waterX - towardsX * 2, waterZ - towardsZ * 2) != WATER)
            return;

        for(int step = 1; step <= 3; step++) {
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

    // Leaves the height of the first free block in probeY
    private int classify(ServerWorld world, int x, int z) {
        if(!world.isChunkLoaded(x >> 4, z >> 4))
            return UNLOADED;
        probeY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
        probe.set(x, probeY - 1, z);
        BlockState below = world.getBlockState(probe);
        FluidState fluid = below.getFluidState();
        if(!fluid.isEmpty())
            return fluid.isIn(FluidTags.WATER) ? WATER : BLOCKED;
        // Paths and farmland are not full blocks, but they are ground all the same
        boolean ground = below.isSolidBlock(world, probe) || below.isOf(Blocks.DIRT_PATH) || below.isOf(Blocks.FARMLAND);
        if(!ground || below.isIn(BlockTags.LOGS))
            return BLOCKED;
        if(Math.abs(probeY - surfaceY) > MAXIMUM_HEIGHT_DIFFERENCE)
            return BLOCKED;
        return LAND;
    }

    // The heightmap puts such a spot on a roof, the yard around the house stays free
    public boolean isBuilding(int x, int y, int z) {
        for(BlockBox box : buildings) {
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

    static {
        List<Integer> samples = new ArrayList<>();
        for(int radius : RADII) {
            int amount = Math.max(8, Math.round(MathHelper.TAU * radius / SAMPLE_SPACING));
            for(int i = 0; i < amount; i++) {
                // Offset each circle, so that the samples do not line up into spokes
                double angle = MathHelper.TAU * i / amount + radius * 0.37;
                samples.add(MathHelper.floor(Math.cos(angle) * radius));
                samples.add(MathHelper.floor(Math.sin(angle) * radius));
            }
        }
        SAMPLES = samples.stream().mapToInt(Integer::intValue).toArray();
    }
}
