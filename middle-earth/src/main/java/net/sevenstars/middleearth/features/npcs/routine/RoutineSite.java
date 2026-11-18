package net.sevenstars.middleearth.features.npcs.routine;

import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayFIFOQueue;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.sevenstars.middleearth.features.npcs.pathing.NpcPathNodeMaker;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class RoutineSite {
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
    private static final int OWN_HOUSEHOLD = 8;
    // Short enough to path between ring points
    private static final int RING_SPACING = 14;
    private static final int FLOOR_HEIGHT = 3;
    private static final int VISIT_HEIGHT = 16;
    // Wider gaps aren't walked, the round becomes an arc
    private static final double ARC_GAP = RING_SPACING * 2.5;
    private static final int OUTLINE_OFFSET = 6;
    // Outwards first, inwards lands between houses
    private static final int[] RING_ADJUSTMENTS = {0, 4, 8, -4};
    private static final int MINIMUM_OUTLINE_CORNERS = 3;
    private static final int MINIMUM_ROUND_POINTS = 3;
    private static final int MINIMUM_MARKED_ROUND_POINTS = 2;
    private static final int MINIMUM_HOUSEHOLDS_TO_VISIT = 2;
    private static final int FAR_TABLE_FACTOR = 2;
    private static final int MINIMUM_FREE_SEATS_ALONE = 2;
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
    private static final int GAME_INTERVAL = 20 * 60 * 2;
    // Matches how far a hunter roams from open ground
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
    // Half the structure's width, 0 in the wild
    private final int extent;
    private final float sampleScale;
    private final List<BlockBox> buildings = new ArrayList<>();

    private final List<BlockPos> shoreStands = new ArrayList<>();
    private final List<BlockPos> shoreWaters = new ArrayList<>();
    private final List<BlockPos> grounds = new ArrayList<>();
    private final List<BlockPos> trees = new ArrayList<>();
    private final List<BlockPos> ring = new ArrayList<>();
    // Ring points as x, z, outX, outZ (out = away from the place)
    private final List<double[]> ringPlan = new ArrayList<>();
    private List<StructureFeatureData.Bed> beds = List.of();
    // Birth beds, each marks the middle of a household
    private List<BlockPos> households = List.of();
    private List<BlockPos> stores = List.of();
    private final SiteStock stock = new SiteStock(this);
    private List<List<BlockPos>> tables = List.of();
    private List<StructureFeatureData.Station> stations = List.of();
    private List<BlockPos> seats = List.of();
    private List<BlockPos> doors = List.of();
    private List<StructureFeatureData.Marker> markers = List.of();
    private Map<MarkerKind, List<StructureFeatureData.Marker>> markersByKind = Map.of();
    private @Nullable List<PrisonCell> cells = null;
    private @Nullable Identifier faction = null;
    // Faction path ids the structure is tagged with. Empty if unassigned
    private Set<String> owners = Set.of();
    private final Map<BlockPos, UUID> postClaims = new HashMap<>();
    // Separate: a traveller keeps its bed when posts are released
    private final Map<BlockPos, UUID> bedClaims = new HashMap<>();
    private @Nullable List<BlockPos> patrolRoute = null;

    private final BlockPos.Mutable probe = new BlockPos.Mutable();
    private int probeY;
    private int cursor = 0;
    private boolean ready;
    // Slot of each ring point, so a late one goes in the right place
    private final List<Integer> ringPlanIndex = new ArrayList<>();
    // Unloaded ground, rechecked once it loads
    private final List<Integer> missedRing = new ArrayList<>();
    private final List<Integer> missedSamples = new ArrayList<>();
    private boolean patrolArc = false;
    private final Map<BlockPos, Long> hivesEmptied = new HashMap<>();
    private final Map<BlockPos, Integer> hiveLevels = new HashMap<>();
    private List<BlockPos> game = List.of();
    // -GAME_INTERVAL so the first lookup runs at once, without Long.MIN_VALUE overflowing
    private long gameSeenAt = -GAME_INTERVAL;
    private long lastUsed;

    RoutineSite(ServerWorld world, int centerX, int centerZ, boolean enclosed, int ringRadius, int extent, @Nullable List<BlockBox> pieces) {
        this.extent = extent;
        this.sampleScale = Math.max(1f, (extent + SAMPLE_MARGIN) / (float) RADII[RADII.length - 1]);
        this.centerX = centerX;
        this.centerZ = centerZ;
        this.surfaceY = world.getTopY(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, centerX, centerZ);
        this.ringRadius = ringRadius;
        if(pieces != null) {
            // Roads and squares can be walked on, so they aren't buildings
            for(BlockBox piece : pieces) {
                if(piece.getBlockCountY() >= BUILDING_HEIGHT)
                    buildings.add(piece);
            }
        }
        if(ringRadius > 0)
            planRing(pieces);
        this.ready = enclosed;
    }

    public static boolean isEnclosed(ServerWorld world, int x, int z, int y) {
        return groundY(world, x, z) - y > ENCLOSED_DEPTH;
    }

    // Enclosed only if all of it is. Decided from the structure, not whoever asks first
    public static boolean isEnclosed(ServerWorld world, BlockBox box) {
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

    public void touch(long time) {
        lastUsed = time;
    }

    long getLastUsed() {
        return lastUsed;
    }

    public boolean isReady() {
        return ready;
    }

    public boolean supports(RoutineData.Activity activity) {
        return switch (activity) {
            case FISH -> !shoreStands.isEmpty();
            case HUNT, FORAGE -> !grounds.isEmpty();
            case PATROL -> getPatrolRoute().size() >= MINIMUM_ROUND_POINTS || hasMarkedRoute();
            case COURT -> !getMarkers(MarkerKind.LORD_SEAT).isEmpty() || !tables.isEmpty();
            case INSPECT -> !getMarkers(MarkerKind.GUARD_POST).isEmpty() || !getMarkers(MarkerKind.ENTRANCE).isEmpty() || ring.size() >= MINIMUM_ROUND_POINTS;
            case VISIT -> households.size() >= MINIMUM_HOUSEHOLDS_TO_VISIT;
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

    // For an unseen hive, guessed from the honey in it
    public long hiveEmptiedAt(BlockPos hive, long time, int levelFound, long filledFor) {
        Integer left = hiveLevels.get(hive);
        if(left == null || left != levelFound)
            hivesEmptied.put(hive, time - filledFor);
        return hivesEmptied.get(hive);
    }

    public void noteHiveLevel(BlockPos hive, int level) {
        hiveLevels.put(hive, level);
    }

    public void emptyHive(BlockPos hive, long time) {
        hivesEmptied.put(hive, time);
        hiveLevels.put(hive, 0);
    }

    public List<BlockPos> getGrounds() {
        return grounds;
    }

    // It moves, so it's refreshed regularly
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

    // First npc claims it for its faction. A held prisoner doesn't
    public void claim(@Nullable Identifier faction) {
        if(this.faction != null || faction == null || ringRadius <= 0)
            return;
        String path = faction.getPath();
        String parent = RoutineSites.parentFaction(path);
        if(owners.isEmpty() || owners.contains(path) || parent != null && owners.contains(parent))
            this.faction = faction;
    }

    void setOwners(Set<String> owners) {
        this.owners = owners;
    }

    public int getSurfaceY() {
        return surfaceY;
    }

    public SiteStock getStock() {
        return stock;
    }

    public List<BlockPos> getStores() {
        return stores;
    }

    void setStores(List<BlockPos> stores) {
        this.stores = stores;
    }

    public List<StructureFeatureData.Bed> getBeds() {
        return beds;
    }

    void setBeds(List<StructureFeatureData.Bed> beds) {
        this.beds = beds;
    }

    void setHouseholds(List<BlockPos> households) {
        this.households = households;
    }

    // Same level only, halls and open ground are a long walk apart
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

    public @Nullable BlockPos pickSeatNear(ServerWorld world, BlockPos place, int range) {
        return pickSeatNear(world, place, range, null);
    }

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

    void setMarkers(List<StructureFeatureData.Marker> markers) {
        this.markers = markers;
        Map<MarkerKind, List<StructureFeatureData.Marker>> grouped = new EnumMap<>(MarkerKind.class);
        for(StructureFeatureData.Marker marker : markers)
            grouped.computeIfAbsent(marker.kind(), kind -> new ArrayList<>()).add(marker);
        this.markersByKind = grouped;
        this.patrolRoute = null;
        this.cells = null;
    }

    public List<BlockPos> getSeats() {
        return seats;
    }

    public List<BlockPos> getDoors() {
        return doors;
    }

    public List<StructureFeatureData.Marker> getMarkers(MarkerKind kind) {
        return markersByKind.getOrDefault(kind, List.of());
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

    public List<BlockPos> getFields(BlockPos near, int range) {
        return stationsNear(List.of(StationKind.FIELD), near, range);
    }

    public List<BlockPos> stationsOf(StationKind kind) {
        List<BlockPos> found = new ArrayList<>();
        for(StructureFeatureData.Station station : stations) {
            if(station.kind() == kind)
                found.add(station.pos());
        }
        return found;
    }

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
        List<BlockPos> found = stationsNear(kinds, near, range);
        return found.isEmpty() ? null : found.getFirst();
    }

    void setTables(List<List<BlockPos>> tables) {
        this.tables = tables;
    }

    // Same floor first, else up to twice as far on any floor
    public @Nullable BlockPos pickSeat(ServerWorld world, Random random, BlockPos near, int range) {
        BlockPos seat = pickSeat(world, random, near, range, true);
        return seat != null ? seat : pickSeat(world, random, near, range * FAR_TABLE_FACTOR, false);
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
            if(alone == null && freeSeats >= MINIMUM_FREE_SEATS_ALONE)
                alone = free;
        }
        return alone;
    }

    public List<BlockPos> getRing() {
        return ring;
    }

    public List<BlockPos> getPatrolRoute() {
        if(patrolRoute == null) {
            List<BlockPos> points = new ArrayList<>();
            for(StructureFeatureData.Marker marker : getMarkers(MarkerKind.PATROL_POINT))
                points.add(marker.pos());
            if(points.size() < MINIMUM_MARKED_ROUND_POINTS) {
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

    // A marker the npc already holds counts as free
    public @Nullable StructureFeatureData.Marker claimPost(ServerWorld world, UUID npc, BlockPos near, MarkerKind... kinds) {
        StructureFeatureData.Marker best = null;
        for(MarkerKind kind : kinds) {
            for(StructureFeatureData.Marker marker : getMarkers(kind)) {
                if(isFree(world, postClaims.get(marker.pos()), npc) && (best == null || marker.pos().getSquaredDistance(near) < best.pos().getSquaredDistance(near)))
                    best = marker;
            }
        }
        if(best != null) {
            postClaims.values().removeIf(npc::equals);
            postClaims.put(best.pos(), npc);
        }
        return best;
    }

    // Spawns nobody, empty, floor beside it. Its own bed counts as free
    public @Nullable BlockPos claimFreeBed(ServerWorld world, UUID npc, BlockPos near) {
        List<StructureFeatureData.Bed> candidates = new ArrayList<>();
        for(StructureFeatureData.Bed bed : beds) {
            if(bed.tag().isEmpty())
                candidates.add(bed);
        }
        candidates.sort((a, b) -> Double.compare(a.pos().getSquaredDistance(near), b.pos().getSquaredDistance(near)));
        for(StructureFeatureData.Bed bed : candidates) {
            BlockPos pos = bed.pos();
            if(!world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4))
                continue;
            BlockState state = world.getBlockState(pos);
            if(!isFree(world, bedClaims.get(pos), npc) || !(state.getBlock() instanceof BedBlock) || state.get(BedBlock.OCCUPIED) || NestSurvey.standFor(world, pos) == null)
                continue;
            bedClaims.values().removeIf(npc::equals);
            bedClaims.put(pos, npc);
            return pos;
        }
        return null;
    }

    private static boolean isFree(ServerWorld world, @Nullable UUID holder, UUID npc) {
        return holder == null || holder.equals(npc) || !(world.getEntity(holder) instanceof NpcEntity holding && holding.isAlive());
    }

    public void releasePost(UUID npc) {
        postClaims.values().removeIf(npc::equals);
    }

    public void releaseBed(UUID npc) {
        bedClaims.values().removeIf(npc::equals);
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

    // One sample per call, true when done
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

    public boolean hasMissed() {
        return ready && (!missedRing.isEmpty() || !missedSamples.isEmpty());
    }

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

    public boolean hasMarkedRoute() {
        return getMarkers(MarkerKind.PATROL_POINT).size() >= MINIMUM_MARKED_ROUND_POINTS;
    }

    public boolean isPatrolArc() {
        getPatrolRoute();
        return patrolArc;
    }

    // Flood from the doors: up 1 block, down a few, never water or roofs
    private void keepReachable(ServerWorld world) {
        patrolRoute = null;
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
        // A trunk fills its own column, so it's reached from a neighbouring one
        trees.removeIf(tree -> !reached.containsKey(column(tree.getX() + 1, tree.getZ())) && !reached.containsKey(column(tree.getX() - 1, tree.getZ()))
                && !reached.containsKey(column(tree.getX(), tree.getZ() + 1)) && !reached.containsKey(column(tree.getX(), tree.getZ() - 1)));
        for(int i = shoreStands.size() - 1; i >= 0; i--) {
            if(!reached.containsKey(column(shoreStands.get(i).getX(), shoreStands.get(i).getZ()))) {
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
}
