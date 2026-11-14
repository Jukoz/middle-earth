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
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
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

    private static final int MINIMUM_RING_POINTS = 8;
    private static final int MAXIMUM_RING_POINTS = 40;
    // Short enough for one path from a point of the ring to the next
    private static final int OWN_HOUSEHOLD = 8;
    private static final int RING_SPACING = 14;
    // How far outside the outermost buildings the watch walks
    private static final int RING_MARGIN = 6;
    // Outwards first, a point moved inwards ends up between the houses
    private static final int[] RING_ADJUSTMENTS = {0, 4, 8, -4};
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
    // Where each point of the ring should be, and the way that leads out of the place from there: x, z, outX, outZ
    private final List<double[]> ringPlan = new ArrayList<>();
    // The nests people live at, each the middle of a household
    private List<BlockPos> households = List.of();
    // The seats around each table of the settlement, from the generated data of its templates
    private List<List<BlockPos>> tables = List.of();
    private List<StructureFeatureData.Station> stations = List.of();
    // Every seat, doors and ladders of the settlement, and the markers its builders put down
    private List<BlockPos> seats = List.of();
    private List<BlockPos> doors = List.of();
    private List<BlockPos> ladders = List.of();
    private List<StructureFeatureData.Marker> markers = List.of();
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
        if(ringRadius > 0)
            planRing(pieces);
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
            case VISIT -> households.size() >= 2;
            case TABLE -> !tables.isEmpty();
            case WORK -> stations.stream().anyMatch(station -> station.kind() != StationKind.FIELD);
            case FARM -> stations.stream().anyMatch(station -> station.kind() == StationKind.FIELD);
            case POST, ESCORT -> true;
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

    void setHouseholds(List<BlockPos> households) {
        this.households = households;
    }

    // Somebody else's home, not the one next to the npc's own bed
    public @Nullable BlockPos pickHousehold(BlockPos home, Random random) {
        int offset = households.isEmpty() ? 0 : random.nextInt(households.size());
        for(int i = 0; i < households.size(); i++) {
            BlockPos household = households.get((i + offset) % households.size());
            if(!household.isWithinDistance(home, OWN_HOUSEHOLD))
                return household;
        }
        return null;
    }

    // A free seat in the house that is being called on
    public @Nullable BlockPos pickSeatNear(ServerWorld world, BlockPos place, int range) {
        BlockPos best = null;
        for(List<BlockPos> table : tables) {
            for(BlockPos seat : table) {
                if(!seat.isWithinDistance(place, range) || !world.isChunkLoaded(seat.getX() >> 4, seat.getZ() >> 4) || !SeatBlock.isFree(world, seat))
                    continue;
                if(best == null || seat.getSquaredDistance(place) < best.getSquaredDistance(place))
                    best = seat;
            }
        }
        return best;
    }

    void setStations(List<StructureFeatureData.Station> stations) {
        this.stations = stations;
    }

    void setSeats(List<BlockPos> seats) {
        this.seats = seats;
    }

    void setDoors(List<BlockPos> doors) {
        this.doors = doors;
    }

    void setLadders(List<BlockPos> ladders) {
        this.ladders = ladders;
    }

    void setMarkers(List<StructureFeatureData.Marker> markers) {
        this.markers = markers;
    }

    public List<BlockPos> getSeats() {
        return seats;
    }

    public List<BlockPos> getDoors() {
        return doors;
    }

    public List<BlockPos> getLadders() {
        return ladders;
    }

    // Where they are and which way they face
    public List<StructureFeatureData.Marker> getMarkers(MarkerKind kind) {
        List<StructureFeatureData.Marker> found = new ArrayList<>();
        for(StructureFeatureData.Marker marker : markers) {
            if(marker.kind() == kind)
                found.add(marker);
        }
        return found;
    }

    // The station of one of these kinds closest to the given place, which is the npc's home
    public List<BlockPos> getFields(BlockPos near, int range) {
        List<BlockPos> fields = new ArrayList<>();
        for(StructureFeatureData.Station station : stations) {
            if(station.kind() == StationKind.FIELD && station.pos().isWithinDistance(near, range))
                fields.add(station.pos());
        }
        return fields;
    }

    public @Nullable BlockPos pickStation(List<StationKind> kinds, BlockPos near, int range) {
        BlockPos best = null;
        for(StructureFeatureData.Station station : stations) {
            if(!kinds.contains(station.kind()) || !station.pos().isWithinDistance(near, range))
                continue;
            if(best == null || station.pos().getSquaredDistance(near) < best.getSquaredDistance(near))
                best = station.pos();
        }
        return best;
    }

    void setTables(List<List<BlockPos>> tables) {
        this.tables = tables;
    }

    // A free seat at a table where somebody already sits, or else at any table with room for two
    public @Nullable BlockPos pickSeat(ServerWorld world, Random random) {
        BlockPos alone = null;
        int offset = tables.isEmpty() ? 0 : random.nextInt(tables.size());
        for(int i = 0; i < tables.size(); i++) {
            List<BlockPos> table = tables.get((i + offset) % tables.size());
            BlockPos free = null;
            int freeSeats = 0;
            int loaded = 0;
            for(BlockPos seat : table) {
                if(!world.isChunkLoaded(seat.getX() >> 4, seat.getZ() >> 4))
                    continue;
                loaded++;
                if(SeatBlock.isFree(world, seat)) {
                    freeSeats++;
                    if(free == null || random.nextBoolean())
                        free = seat;
                }
            }
            if(free == null)
                continue;
            if(freeSeats < loaded)
                return free;
            if(alone == null && freeSeats >= 2)
                alone = free;
        }
        return alone;
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

        int ringSteps = ringPlan.size();
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

    // The ring follows the outline of the settlement. A circle cuts through any place that is not round
    private void planRing(@Nullable List<BlockBox> pieces) {
        List<double[]> outline = pieces == null ? List.of() : outlineOf(pieces);
        if(outline.size() < 3) {
            planCircle();
            return;
        }
        planOutline(outline);
        // Too small a place to walk around the outside of
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
            // The outline runs counter clockwise, which puts the outside on this hand
            double outX = alongZ;
            double outZ = -alongX;
            double walked = untilNext;
            for(; walked < length; walked += spacing)
                ringPlan.add(new double[]{from[0] + alongX * walked + outX * RING_MARGIN, from[1] + alongZ * walked + outZ * RING_MARGIN, outX, outZ});
            untilNext = walked - length;
        }
    }

    // The convex hull around every piece
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
