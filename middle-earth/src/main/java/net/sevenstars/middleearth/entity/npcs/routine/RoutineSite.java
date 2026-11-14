package net.sevenstars.middleearth.entity.npcs.routine;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.utils.BlockTagsME;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

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
    // Up or down this far is still the same floor
    private static final int FLOOR_HEIGHT = 3;
    // A household further up or down than this is not called on
    private static final int VISIT_HEIGHT = 16;
    // A gap this wide between two points of the ring is no stretch of the round
    private static final double ARC_GAP = RING_SPACING * 2.5;
    // How far outside the outermost buildings the watch walks
    private static final int RING_MARGIN = 6;
    // Outwards first, a point moved inwards ends up between the houses
    private static final int[] RING_ADJUSTMENTS = {0, 4, 8, -4};
    private static final int MAXIMUM_SHORES = 8;
    private static final int SHORE_SPACING = 6;
    private static final int MAXIMUM_GROUNDS = 12;
    private static final int GROUND_SPACING = 10;
    private static final int GROUND_MINIMUM_RADIUS = 20;
    private static final int MAXIMUM_TREES = 24;
    private static final int TREE_SPACING = 3;
    // Around each sample, which on its own would hit a trunk now and then
    private static final int TREE_SEARCH = 2;
    private static final int MAXIMUM_TRUNK_HEIGHT = 24;
    private static final int MAXIMUM_HEIGHT_DIFFERENCE = 14;
    private static final int MAXIMUM_SHORE_STEP = 2;
    // A site this far under the surface is a hall or a cave
    private static final int ENCLOSED_DEPTH = 16;
    private static final int BUILDING_HEIGHT = 4;
    // Past the farthest spot, for the flood to find the way round to it
    private static final int FLOOD_MARGIN = 8;
    // As far down as a step off a bank or a wall is taken, which has to be walked back up some other way
    private static final int FLOOD_DROP = 3;
    private static final int GAME_INTERVAL = 20 * 60 * 2;
    // Past the farthest open ground, as far as a hunter roams from it
    private static final int GAME_MARGIN = 34;
    private static final int GAME_HEIGHT = 24;

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
    // The foot of each trunk that carries a crown, the trees to work at
    private final List<BlockPos> trees = new ArrayList<>();
    private final List<BlockPos> ring = new ArrayList<>();
    // Where each point of the ring should be, and the way that leads out of the place from there: x, z, outX, outZ
    private final List<double[]> ringPlan = new ArrayList<>();
    private List<StructureFeatureData.Bed> beds = List.of();
    // The beds somebody is born in, each the middle of a household
    private List<BlockPos> households = List.of();
    private List<BlockPos> stores = List.of();
    // The seats around each table of the settlement, from the generated data of its templates
    private List<List<BlockPos>> tables = List.of();
    private List<StructureFeatureData.Station> stations = List.of();
    // Every seat, doors and ladders of the settlement, and the markers its builders put down
    private List<BlockPos> seats = List.of();
    private List<BlockPos> doors = List.of();
    private List<BlockPos> ladders = List.of();
    private List<StructureFeatureData.Marker> markers = List.of();
    private @Nullable List<PrisonCell> cells = null;
    private @Nullable Identifier faction = null;
    // Who stands at which marker, so that two guards never take the same post
    private final Map<BlockPos, UUID> claims = new HashMap<>();
    private @Nullable List<BlockPos> patrolRoute = null;

    private final BlockPos.Mutable probe = new BlockPos.Mutable();
    private int probeY;
    private int cursor = 0;
    private boolean ready;
    // Where each ring point was planned, so that one found later goes in its place
    private final List<Integer> ringPlanIndex = new ArrayList<>();
    // Ring points and samples that fell on ground not loaded at the time, looked at again once it is
    private final List<Integer> missedRing = new ArrayList<>();
    private final List<Integer> missedSamples = new ArrayList<>();
    private boolean patrolArc = false;
    private final Map<BlockPos, Long> hivesEmptied = new HashMap<>();
    private List<BlockPos> game = List.of();
    // Looked for at once the first time, without the gap from the smallest long overflowing
    private long gameSeenAt = -GAME_INTERVAL;
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

    // Far enough under the ground at that spot to be shut in, in a cave or a delving
    public static boolean isEnclosed(ServerWorld world, int x, int z, int y) {
        return world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z) - y > ENCLOSED_DEPTH;
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
            case PATROL -> getPatrolRoute().size() >= 3 || !getMarkers(MarkerKind.PATROL_POINT).isEmpty() && getPatrolRoute().size() >= 2;
            case COURT -> !getMarkers(MarkerKind.LORD_SEAT).isEmpty() || !tables.isEmpty();
            case INSPECT -> !getMarkers(MarkerKind.GUARD_POST).isEmpty() || !getMarkers(MarkerKind.ENTRANCE).isEmpty() || ring.size() >= 3;
            case VISIT -> households.size() >= 2;
            case TABLE -> !tables.isEmpty();
            case WORK -> stations.stream().anyMatch(station -> station.kind() != StationKind.FIELD) || !getMarkers(MarkerKind.SPAWN_ANIMAL).isEmpty();
            case FARM -> stations.stream().anyMatch(station -> station.kind() == StationKind.FIELD);
            case CHOP -> !trees.isEmpty();
            case MINE -> markers.stream().anyMatch(marker -> marker.kind() == MarkerKind.MINE_FACE);
            case POST, ESCORT, FOLLOW -> true;
        };
    }

    public List<BlockPos> getShoreStands() {
        return shoreStands;
    }

    public BlockPos getShoreWater(int index) {
        return shoreWaters.get(index);
    }

    // When the honey was last taken from a hive. One not seen before is reckoned from the honey already in it
    public long hiveEmptiedAt(BlockPos hive, long time, long filledFor) {
        return hivesEmptied.computeIfAbsent(hive, pos -> time - filledFor);
    }

    public void emptyHive(BlockPos hive, long time) {
        hivesEmptied.put(hive, time);
    }

    public List<BlockPos> getGrounds() {
        return grounds;
    }

    // Where game was last seen around the place. It wanders, so it is looked for again now and then
    public List<BlockPos> getGame(ServerWorld world, long time, Predicate<AnimalEntity> prey) {
        if(time - gameSeenAt < GAME_INTERVAL)
            return game;
        gameSeenAt = time;
        int range = (int) (RADII[RADII.length - 1] * sampleScale) + GAME_MARGIN;
        Box around = new Box(centerX - range, surfaceY - GAME_HEIGHT, centerZ - range, centerX + range, surfaceY + GAME_HEIGHT, centerZ + range);
        game = world.getEntitiesByClass(AnimalEntity.class, around, prey).stream().map(Entity::getBlockPos).toList();
        return game;
    }

    public List<BlockPos> getTrees() {
        return trees;
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

    // The barrels and chests the households keep their things in
    public List<BlockPos> getStores() {
        return stores;
    }

    void setStores(List<BlockPos> stores) {
        this.stores = stores;
    }

    // Every bed of the place, those somebody is born in and those that are free
    public List<StructureFeatureData.Bed> getBeds() {
        return beds;
    }

    void setBeds(List<StructureFeatureData.Bed> beds) {
        this.beds = beds;
    }

    void setHouseholds(List<BlockPos> households) {
        this.households = households;
    }

    // Somebody else's home, not the one next to the npc's own bed
    // Another household to call on, among those down in the halls for one living there and up in the open for one living
    // there: the way between is up the stairs of the whole place
    public @Nullable BlockPos pickHousehold(ServerWorld world, BlockPos home, Random random) {
        boolean underground = isEnclosed(world, home.getX(), home.getZ(), home.getY());
        int offset = households.isEmpty() ? 0 : random.nextInt(households.size());
        for(int i = 0; i < households.size(); i++) {
            BlockPos household = households.get((i + offset) % households.size());
            if(household.isWithinDistance(home, OWN_HOUSEHOLD) || Math.abs(household.getY() - home.getY()) > VISIT_HEIGHT
                    || isEnclosed(world, household.getX(), household.getZ(), household.getY()) != underground)
                continue;
            return household;
        }
        return null;
    }

    // A free seat in the house that is being called on
    public @Nullable BlockPos pickSeatNear(ServerWorld world, BlockPos place, int range) {
        return pickSeatNear(world, place, range, null);
    }

    // Another than the one given, for the host to sit with its visitor
    public @Nullable BlockPos pickSeatNear(ServerWorld world, BlockPos place, int range, @Nullable BlockPos besides) {
        BlockPos best = null;
        for(List<BlockPos> table : tables) {
            for(BlockPos seat : table) {
                if(seat.equals(besides) || !seat.isWithinDistance(place, range) || !world.isChunkLoaded(seat.getX() >> 4, seat.getZ() >> 4) || !SeatBlock.isFree(world, seat))
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
        this.patrolRoute = null;
        this.cells = null;
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

    public @Nullable PrisonCell findCell(BlockPos pos) {
        if(cells == null)
            cells = PrisonCell.cells(getMarkers(MarkerKind.CELL_CORNER).stream().map(StructureFeatureData.Marker::pos).toList());
        for(PrisonCell cell : cells) {
            if(cell.contains(pos))
                return cell;
        }
        return null;
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

    // The stations of these kinds within range, nearest first
    public List<BlockPos> stationsNear(List<StationKind> kinds, BlockPos near, int range) {
        List<BlockPos> found = new ArrayList<>();
        for(StructureFeatureData.Station station : stations) {
            if(kinds.contains(station.kind()) && station.pos().isWithinDistance(near, range))
                found.add(station.pos());
        }
        found.sort((a, b) -> Double.compare(a.getSquaredDistance(near), b.getSquaredDistance(near)));
        return found;
    }

    public @Nullable StationKind stationKindAt(BlockPos pos) {
        for(StructureFeatureData.Station station : stations) {
            if(station.pos().equals(pos))
                return station.kind();
        }
        return null;
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
    // A table on the same floor within range of the given place, or else one twice as far on any floor. In a stronghold a
    // table two floors up is a long way round by its stairs, and the far tables are further still down its halls
    public @Nullable BlockPos pickSeat(ServerWorld world, Random random, BlockPos near, int range) {
        BlockPos seat = pickSeat(world, random, near, range, true);
        return seat != null ? seat : pickSeat(world, random, near, range * 2, false);
    }

    private @Nullable BlockPos pickSeat(ServerWorld world, Random random, BlockPos near, int range, boolean sameFloor) {
        BlockPos alone = null;
        int offset = tables.isEmpty() ? 0 : random.nextInt(tables.size());
        for(int i = 0; i < tables.size(); i++) {
            List<BlockPos> table = tables.get((i + offset) % tables.size());
            if(table.isEmpty() || !table.getFirst().isWithinDistance(near, range) || sameFloor && Math.abs(table.getFirst().getY() - near.getY()) > FLOOR_HEIGHT)
                continue;
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

    // The patrol points the builders marked, walked round in order of their bearing from the middle, or else the outline ring
    public List<BlockPos> getPatrolRoute() {
        if(patrolRoute == null) {
            List<BlockPos> points = new ArrayList<>();
            for(StructureFeatureData.Marker marker : getMarkers(MarkerKind.PATROL_POINT))
                points.add(marker.pos());
            if(points.size() < 2) {
                patrolRoute = ring;
                patrolArc = false;
                int widest = -1;
                double widestGap = ARC_GAP * ARC_GAP;
                for(int i = 0; i < ring.size(); i++) {
                    double gap = ring.get(i).getSquaredDistance(ring.get((i + 1) % ring.size()));
                    if(gap > widestGap) {
                        widestGap = gap;
                        widest = i;
                    }
                }
                if(widest >= 0 && ring.size() >= 2) {
                    List<BlockPos> arc = new ArrayList<>(ring.size());
                    for(int i = 1; i <= ring.size(); i++)
                        arc.add(ring.get((widest + i) % ring.size()));
                    patrolRoute = arc;
                    patrolArc = true;
                }
            } else {
                points.sort((a, b) -> Double.compare(Math.atan2(a.getZ() - centerZ, a.getX() - centerX), Math.atan2(b.getZ() - centerZ, b.getX() - centerX)));
                patrolRoute = points;
            }
        }
        return patrolRoute;
    }

    // The nearest free marker of these kinds to the place given, taken for the npc. One it holds already is its own
    public @Nullable StructureFeatureData.Marker claimPost(ServerWorld world, UUID npc, BlockPos near, MarkerKind... kinds) {
        StructureFeatureData.Marker best = null;
        for(MarkerKind kind : kinds) {
            for(StructureFeatureData.Marker marker : getMarkers(kind)) {
                UUID holder = claims.get(marker.pos());
                boolean free = holder == null || holder.equals(npc) || !(world.getEntity(holder) instanceof NpcEntity holding && holding.isAlive());
                if(free && (best == null || marker.pos().getSquaredDistance(near) < best.pos().getSquaredDistance(near)))
                    best = marker;
            }
        }
        if(best != null) {
            claims.values().removeIf(npc::equals);
            claims.put(best.pos(), npc);
        }
        return best;
    }

    // The nearest bed nobody is born in, empty and with floor to get into it from, taken for the traveller. One it holds already is its own
    public @Nullable BlockPos claimFreeBed(ServerWorld world, UUID npc, BlockPos near) {
        List<StructureFeatureData.Bed> candidates = new ArrayList<>(beds);
        candidates.sort((a, b) -> Double.compare(a.pos().getSquaredDistance(near), b.pos().getSquaredDistance(near)));
        for(StructureFeatureData.Bed bed : candidates) {
            BlockPos pos = bed.pos();
            if(bed.tag().isPresent() || !world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4))
                continue;
            BlockState state = world.getBlockState(pos);
            UUID holder = claims.get(pos);
            boolean free = holder == null || holder.equals(npc) || !(world.getEntity(holder) instanceof NpcEntity holding && holding.isAlive());
            if(!free || !(state.getBlock() instanceof BedBlock) || state.get(BedBlock.OCCUPIED) || NestSurvey.standFor(world, pos) == null)
                continue;
            claims.values().removeIf(npc::equals);
            claims.put(pos, npc);
            return pos;
        }
        return null;
    }

    public void release(UUID npc) {
        claims.values().removeIf(npc::equals);
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
                keepReachable(world);
                ready = true;
                return true;
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

    // A log column with leaves on top of it is a tree. Logs of a building or a fence post carry none
    private void scanTree(ServerWorld world, int x, int z) {
        if(trees.size() >= MAXIMUM_TREES || !world.isChunkLoaded(x >> 4, z >> 4))
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
        // A bush is a log in leaves on every side, with no room to stand and swing at it
        BlockPos foot = new BlockPos(x, base, z);
        if(NestSurvey.standNextTo(world, foot, foot) == null)
            return;
        trees.add(foot);
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

    public boolean hasMissed() {
        return ready && (!missedRing.isEmpty() || !missedSamples.isEmpty());
    }

    // A place first looked over while part of it was not loaded is filled in once that part is
    void retryMissed(ServerWorld world) {
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
            keepReachable(world);
    }

    // A round with a long gap in it, where the place runs along a lake or a cliff, is walked there and back
    public boolean isPatrolArc() {
        getPatrolRoute();
        return patrolArc;
    }

    // Spots of the ring, the grounds, the trees and the shores that nobody can walk to from the place, across a river or up a
    // cliff, are left out. The ground is flooded out from the doors of the place a column at a time: a block up at most, a few
    // down, never into water or onto a roof. With no doors known, every spot is kept
    private void keepReachable(ServerWorld world) {
        if(doors.isEmpty())
            return;
        int bound = 0;
        for(List<BlockPos> spots : List.of(ring, grounds, trees, shoreStands)) {
            for(BlockPos spot : spots)
                bound = Math.max(bound, Math.max(Math.abs(spot.getX() - centerX), Math.abs(spot.getZ() - centerZ)) + FLOOD_MARGIN);
        }
        Long2IntOpenHashMap reached = new Long2IntOpenHashMap();
        reached.defaultReturnValue(Integer.MIN_VALUE);
        LongArrayFIFOQueue open = new LongArrayFIFOQueue();
        for(BlockPos door : doors) {
            for(int side = 0; side < 4; side++) {
                int x = door.getX() + (side == 0 ? 1 : side == 1 ? -1 : 0);
                int z = door.getZ() + (side == 2 ? 1 : side == 3 ? -1 : 0);
                if(classify(world, x, z) == LAND && Math.abs(probeY - door.getY()) <= 1 && !isBuilding(x, probeY, z) && reached.putIfAbsent(column(x, z), probeY) == reached.defaultReturnValue())
                    open.enqueue(column(x, z));
            }
        }
        if(open.isEmpty())
            return;
        while(!open.isEmpty()) {
            long at = open.dequeueLong();
            int x = (int) (at >> 32);
            int z = (int) at;
            int height = reached.get(at);
            for(int side = 0; side < 4; side++) {
                int nextX = x + (side == 0 ? 1 : side == 1 ? -1 : 0);
                int nextZ = z + (side == 2 ? 1 : side == 3 ? -1 : 0);
                long next = column(nextX, nextZ);
                if(Math.abs(nextX - centerX) > bound || Math.abs(nextZ - centerZ) > bound || reached.containsKey(next))
                    continue;
                if(classify(world, nextX, nextZ) != LAND || probeY - height > 1 || height - probeY > FLOOD_DROP || isBuilding(nextX, probeY, nextZ))
                    continue;
                reached.put(next, probeY);
                open.enqueue(next);
            }
        }
        for(int i = ring.size() - 1; i >= 0; i--) {
            if(!reached.containsKey(column(ring.get(i).getX(), ring.get(i).getZ()))) {
                ring.remove(i);
                ringPlanIndex.remove(i);
            }
        }
        grounds.removeIf(spot -> !reached.containsKey(column(spot.getX(), spot.getZ())));
        // A trunk stands in its own column, and is got to from beside it
        trees.removeIf(tree -> !reached.containsKey(column(tree.getX() + 1, tree.getZ())) && !reached.containsKey(column(tree.getX() - 1, tree.getZ()))
                && !reached.containsKey(column(tree.getX(), tree.getZ() + 1)) && !reached.containsKey(column(tree.getX(), tree.getZ() - 1)));
        for(int i = shoreStands.size() - 1; i >= 0; i--) {
            if(!reached.containsKey(column(shoreStands.get(i).getX(), shoreStands.get(i).getZ()))) {
                shoreStands.remove(i);
                shoreWaters.remove(i);
            }
        }
        patrolRoute = null;
    }

    private static long column(int x, int z) {
        return ((long) x << 32) | (z & 0xFFFFFFFFL);
    }

    // Crops are no obstacle to the heightmap, so a point of the round or a patch to forage can fall in a field. The fields
    // are for those who work them. Asks of the ground classify last looked at
    private boolean isField(ServerWorld world) {
        BlockState ground = world.getBlockState(probe);
        return ground.getBlock() instanceof FarmlandBlock || ground.isIn(BlockTagsME.FARMLANDS);
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
