package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.SeatBlock;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

// What the structure places: beds, stores, tables, stations, markers and doors
class SiteLayout {
    private static final int OWN_HOUSEHOLD = 8;
    private static final int FLOOR_HEIGHT = 3;
    private static final int VISIT_HEIGHT = 16;
    private static final int MINIMUM_HOUSEHOLDS_TO_VISIT = 2;
    private static final int FAR_TABLE_FACTOR = 2;
    private static final int MINIMUM_FREE_SEATS_ALONE = 2;

    private List<StructureFeatureData.Bed> beds = List.of();
    // Birth beds, each marks the middle of a household
    private List<BlockPos> households = List.of();
    private List<BlockPos> stores = List.of();
    private List<List<BlockPos>> tables = List.of();
    private List<StructureFeatureData.Station> stations = List.of();
    private List<BlockPos> seats = List.of();
    private List<BlockPos> doors = List.of();
    private List<StructureFeatureData.Marker> markers = List.of();
    private Map<MarkerKind, List<StructureFeatureData.Marker>> markersByKind = Map.of();

    List<BlockPos> getStores() {
        return stores;
    }

    void setStores(List<BlockPos> stores) {
        this.stores = stores;
    }

    List<StructureFeatureData.Bed> getBeds() {
        return beds;
    }

    void setBeds(List<StructureFeatureData.Bed> beds) {
        this.beds = beds;
    }

    void setHouseholds(List<BlockPos> households) {
        this.households = households;
    }

    boolean hasHouseholdsToVisit() {
        return households.size() >= MINIMUM_HOUSEHOLDS_TO_VISIT;
    }

    boolean hasTables() {
        return !tables.isEmpty();
    }

    boolean hasWorkStation() {
        return stations.stream().anyMatch(station -> station.kind() != StationKind.FIELD) || !getMarkers(MarkerKind.SPAWN_ANIMAL).isEmpty();
    }

    boolean hasFields() {
        return stations.stream().anyMatch(station -> station.kind() == StationKind.FIELD);
    }

    // Same level only, halls and open ground are a long walk apart
    @Nullable BlockPos pickHousehold(ServerWorld world, BlockPos home, Random random) {
        boolean underground = SiteSurvey.isEnclosed(world, home.getX(), home.getZ(), home.getY());
        int offset = households.isEmpty() ? 0 : random.nextInt(households.size());
        for(int i = 0; i < households.size(); i++) {
            BlockPos household = households.get((i + offset) % households.size());
            if(household.isWithinDistance(home, OWN_HOUSEHOLD) || Math.abs(household.getY() - home.getY()) > VISIT_HEIGHT
                    || SiteSurvey.isEnclosed(world, household.getX(), household.getZ(), household.getY()) != underground)
                continue;
            return household;
        }
        return null;
    }

    @Nullable BlockPos pickSeatNear(ServerWorld world, BlockPos place, int range) {
        return pickSeatNear(world, place, range, null);
    }

    @Nullable BlockPos pickSeatNear(ServerWorld world, BlockPos place, int range, @Nullable BlockPos besides) {
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
    }

    List<BlockPos> getSeats() {
        return seats;
    }

    List<BlockPos> getDoors() {
        return doors;
    }

    List<StructureFeatureData.Marker> getMarkers(MarkerKind kind) {
        return markersByKind.getOrDefault(kind, List.of());
    }

    List<BlockPos> getFields(BlockPos near, int range) {
        return stationsNear(List.of(StationKind.FIELD), near, range);
    }

    List<BlockPos> stationsOf(StationKind kind) {
        List<BlockPos> found = new ArrayList<>();
        for(StructureFeatureData.Station station : stations) {
            if(station.kind() == kind)
                found.add(station.pos());
        }
        return found;
    }

    List<BlockPos> stationsNear(List<StationKind> kinds, BlockPos near, int range) {
        List<BlockPos> found = new ArrayList<>();
        for(StructureFeatureData.Station station : stations) {
            if(kinds.contains(station.kind()) && station.pos().isWithinDistance(near, range))
                found.add(station.pos());
        }
        found.sort((a, b) -> Double.compare(a.getSquaredDistance(near), b.getSquaredDistance(near)));
        return found;
    }

    @Nullable StationKind stationKindAt(BlockPos pos) {
        for(StructureFeatureData.Station station : stations) {
            if(station.pos().equals(pos))
                return station.kind();
        }
        return null;
    }

    @Nullable BlockPos pickStation(List<StationKind> kinds, BlockPos near, int range) {
        List<BlockPos> found = stationsNear(kinds, near, range);
        return found.isEmpty() ? null : found.getFirst();
    }

    void setTables(List<List<BlockPos>> tables) {
        this.tables = tables;
    }

    // Same floor first, else up to twice as far on any floor
    @Nullable BlockPos pickSeat(ServerWorld world, Random random, BlockPos near, int range) {
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
}
