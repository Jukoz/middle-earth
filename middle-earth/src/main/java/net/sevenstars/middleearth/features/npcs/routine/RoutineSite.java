package net.sevenstars.middleearth.features.npcs.routine;

import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

public class RoutineSite {
    // Wider gaps aren't walked, the round becomes an arc
    private static final double ARC_GAP = SiteSurvey.RING_SPACING * 2.5;
    private static final int MINIMUM_ROUND_POINTS = 3;
    private static final int MINIMUM_MARKED_ROUND_POINTS = 2;
    private static final int GAME_INTERVAL = 20 * 60 * 2;
    // Matches how far a hunter roams from open ground
    private static final int GAME_MARGIN = 34;
    private static final int GAME_HEIGHT = 24;

    private record Game(AnimalEntity animal, BlockPos pos) {}

    private final SiteSurvey survey;
    private final int ringRadius;
    private final SiteLayout layout = new SiteLayout();
    private final SiteClaims claims = new SiteClaims(layout);
    private final SiteHives hives = new SiteHives();
    private final SiteStock stock = new SiteStock(this);
    private @Nullable Identifier faction = null;
    // Faction path ids the structure is tagged with. Empty if unassigned
    private Set<String> owners = Set.of();
    private @Nullable List<BlockPos> patrolRoute = null;
    private boolean patrolArc = false;
    private List<Game> game = List.of();
    // -GAME_INTERVAL so the first lookup runs at once, without Long.MIN_VALUE overflowing
    private long gameSeenAt = -GAME_INTERVAL;
    private long lastUsed;
    private boolean dropped = false;

    RoutineSite(ServerWorld world, int centerX, int centerZ, boolean enclosed, int ringRadius, int extent, @Nullable List<BlockBox> pieces) {
        this.survey = new SiteSurvey(world, centerX, centerZ, enclosed, ringRadius, extent, pieces, layout::getDoors, this::forgetPatrolRoute);
        this.ringRadius = ringRadius;
    }

    private void forgetPatrolRoute() {
        patrolRoute = null;
    }

    public static boolean isEnclosed(ServerWorld world, int x, int z, int y) {
        return SiteSurvey.isEnclosed(world, x, z, y);
    }

    // Enclosed only if all of it is. Decided from the structure, not whoever asks first
    public static boolean isEnclosed(ServerWorld world, BlockBox box) {
        return SiteSurvey.isEnclosed(world, box);
    }

    void drop() {
        dropped = true;
    }

    boolean isDropped() {
        return dropped;
    }

    public void touch(long time) {
        lastUsed = time;
    }

    long getLastUsed() {
        return lastUsed;
    }

    public boolean isReady() {
        return survey.isReady();
    }

    // SURVEY

    boolean scanStep(ServerWorld world) {
        return survey.scanStep(world);
    }

    public boolean hasMissed() {
        return survey.hasMissed();
    }

    void retryMissed(ServerWorld world) {
        survey.retryMissed(world);
    }

    public List<BlockPos> getShoreStands() {
        return survey.getShoreStands();
    }

    public BlockPos getShoreWater(int index) {
        return survey.getShoreWater(index);
    }

    public List<BlockPos> getGrounds() {
        return survey.getGrounds();
    }

    public List<BlockPos> getTrees() {
        return survey.getTrees();
    }

    public List<BlockPos> getRing() {
        return survey.getRing();
    }

    public int getSurfaceY() {
        return survey.getSurfaceY();
    }

    public int getExtent() {
        return survey.getExtent();
    }

    public int getCenterX() {
        return survey.getCenterX();
    }

    public int getCenterZ() {
        return survey.getCenterZ();
    }

    public boolean isBuilding(int x, int y, int z) {
        return survey.isBuilding(x, y, z);
    }

    boolean hasPatrolRound() {
        return getPatrolRoute().size() >= MINIMUM_ROUND_POINTS || hasMarkedRoute();
    }

    boolean hasRingRound() {
        return getRing().size() >= MINIMUM_ROUND_POINTS;
    }

    // It moves, so it's refreshed regularly
    public List<BlockPos> getGame(ServerWorld world, long time, Predicate<AnimalEntity> prey) {
        if(time - gameSeenAt >= GAME_INTERVAL) {
            gameSeenAt = time;
            int range = survey.reach() + GAME_MARGIN;
            int centerX = survey.getCenterX();
            int centerZ = survey.getCenterZ();
            int surfaceY = survey.getSurfaceY();
            Box around = new Box(centerX - range, surfaceY - GAME_HEIGHT, centerZ - range, centerX + range, surfaceY + GAME_HEIGHT, centerZ + range);
            game = world.getEntitiesByClass(AnimalEntity.class, around, animal -> true).stream().map(animal -> new Game(animal, animal.getBlockPos())).toList();
        }
        return game.stream().filter(seen -> prey.test(seen.animal())).map(Game::pos).toList();
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

    public SiteStock getStock() {
        return stock;
    }

    // LAYOUT

    public List<BlockPos> getStores() {
        return layout.getStores();
    }

    void setStores(List<BlockPos> stores) {
        layout.setStores(stores);
    }

    public List<StructureFeatureData.Bed> getBeds() {
        return layout.getBeds();
    }

    void setBeds(List<StructureFeatureData.Bed> beds) {
        layout.setBeds(beds);
    }

    void setHouseholds(List<BlockPos> households) {
        layout.setHouseholds(households);
    }

    boolean hasHouseholdsToVisit() {
        return layout.hasHouseholdsToVisit();
    }

    boolean hasTables() {
        return layout.hasTables();
    }

    boolean hasWorkStation() {
        return layout.hasWorkStation();
    }

    boolean hasFields() {
        return layout.hasFields();
    }

    public @Nullable BlockPos pickHousehold(ServerWorld world, BlockPos home, Random random) {
        return layout.pickHousehold(world, home, random);
    }

    public @Nullable BlockPos pickSeatNear(ServerWorld world, BlockPos place, int range) {
        return layout.pickSeatNear(world, place, range);
    }

    public @Nullable BlockPos pickSeatNear(ServerWorld world, BlockPos place, int range, @Nullable BlockPos besides) {
        return layout.pickSeatNear(world, place, range, besides);
    }

    void setStations(List<StructureFeatureData.Station> stations) {
        layout.setStations(stations);
    }

    void setSeats(List<BlockPos> seats) {
        layout.setSeats(seats);
    }

    void setDoors(List<BlockPos> doors) {
        layout.setDoors(doors);
    }

    void setMarkers(List<StructureFeatureData.Marker> markers) {
        layout.setMarkers(markers);
        patrolRoute = null;
    }

    void setTables(List<List<BlockPos>> tables) {
        layout.setTables(tables);
    }

    public List<BlockPos> getSeats() {
        return layout.getSeats();
    }

    public List<BlockPos> getDoors() {
        return layout.getDoors();
    }

    public List<StructureFeatureData.Marker> getMarkers(MarkerKind kind) {
        return layout.getMarkers(kind);
    }

    public @Nullable PrisonCell findCell(BlockPos pos) {
        return layout.findCell(pos);
    }

    public List<BlockPos> getFields(BlockPos near, int range) {
        return layout.getFields(near, range);
    }

    public List<BlockPos> stationsOf(StationKind kind) {
        return layout.stationsOf(kind);
    }

    public List<BlockPos> stationsNear(List<StationKind> kinds, BlockPos near, int range) {
        return layout.stationsNear(kinds, near, range);
    }

    public @Nullable StationKind stationKindAt(BlockPos pos) {
        return layout.stationKindAt(pos);
    }

    public @Nullable BlockPos pickStation(List<StationKind> kinds, BlockPos near, int range) {
        return layout.pickStation(kinds, near, range);
    }

    // Same floor first, else up to twice as far on any floor
    public @Nullable BlockPos pickSeat(ServerWorld world, Random random, BlockPos near, int range) {
        return layout.pickSeat(world, random, near, range);
    }

    public List<BlockPos> getPatrolRoute() {
        if(patrolRoute == null) {
            List<BlockPos> ring = getRing();
            List<BlockPos> points = new ArrayList<>();
            for(StructureFeatureData.Marker marker : getMarkers(MarkerKind.PATROL_POINT))
                points.add(marker.pos());
            if(points.size() < MINIMUM_MARKED_ROUND_POINTS) {
                patrolRoute = new ArrayList<>(ring);
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
                int centerX = getCenterX();
                int centerZ = getCenterZ();
                points.sort((a, b) -> Double.compare(Math.atan2(a.getZ() - centerZ, a.getX() - centerX), Math.atan2(b.getZ() - centerZ, b.getX() - centerX)));
                patrolRoute = points;
            }
        }
        return patrolRoute;
    }

    public boolean hasMarkedRoute() {
        return getMarkers(MarkerKind.PATROL_POINT).size() >= MINIMUM_MARKED_ROUND_POINTS;
    }

    public boolean isPatrolArc() {
        getPatrolRoute();
        return patrolArc;
    }

    // CLAIMS

    public @Nullable StructureFeatureData.Marker claimPost(ServerWorld world, UUID npc, BlockPos near, MarkerKind... kinds) {
        return claims.claimPost(world, npc, near, kinds);
    }

    public @Nullable BlockPos claimFreeBed(ServerWorld world, UUID npc, BlockPos near) {
        return claims.claimFreeBed(world, npc, near);
    }

    public void releasePost(UUID npc) {
        claims.releasePost(npc);
    }

    public void releaseBed(UUID npc) {
        claims.releaseBed(npc);
    }

    // HIVES

    public long hiveEmptiedAt(BlockPos hive, long time, int levelFound, long filledFor) {
        return hives.emptiedAt(hive, time, levelFound, filledFor);
    }

    public void noteHiveLevel(BlockPos hive, int level) {
        hives.noteLevel(hive, level);
    }

    public void emptyHive(BlockPos hive, long time) {
        hives.empty(hive, time);
    }
}
