package net.sevenstars.middleearth.entity.npcs.routine;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.features.NestSurvey;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// Keeps the sites of each world, and rations the work routines may do in one tick
public class RoutineSites {
    // All per world, per tick
    private static final int SCAN_STEPS = 8;
    private static final int ROUTINE_STARTS = 1;
    private static final int PATH_REQUESTS = 3;
    private static final int PREY_SEARCHES = 1;

    private static final int WILD_CELL_SIZE = 48;
    private static final int DESTINATION_RANGE = 320;
    private static final int DESTINATION_MINIMUM = 40;
    private static final int STRUCTURE_SEARCH_CHUNKS = 2;
    private static final int DEFAULT_RING_RADIUS = 14;
    private static final int MINIMUM_RING_RADIUS = 10;
    private static final int MAXIMUM_RING_RADIUS = 40;
    private static final int RING_RADIUS_PADDING = 6;
    private static final String FACTION_TAG_PREFIX = "faction/";
    private static final int CLEANUP_INTERVAL = 20 * 60;
    private static final int UNUSED_LIFETIME = 20 * 60 * 10;

    // Stand in for the height in the keys of sites that are not tied to a block
    private static final int STRUCTURE_KEY_Y = 2047;
    private static final int WILD_KEY_Y = 2046;
    private static final int UNDERGROUND_KEY_Y = 2045;
    // Ticks between looks at the parts of places that were not loaded when they were looked over
    private static final int RETRY_INTERVAL = 20 * 30;

    private static final Map<RegistryKey<World>, WorldSites> WORLDS = new HashMap<>();

    private static class WorldSites {
        final Long2ObjectOpenHashMap<RoutineSite> sites = new Long2ObjectOpenHashMap<>();
        final ArrayDeque<RoutineSite> scanning = new ArrayDeque<>();
        long budgetTick = -1;
        int starts;
        int paths;
        int searches;

        void refresh(long time) {
            if(budgetTick == time)
                return;
            budgetTick = time;
            starts = ROUTINE_STARTS;
            paths = PATH_REQUESTS;
            searches = PREY_SEARCHES;
        }
    }

    public static void register() {
        ServerTickEvents.END_WORLD_TICK.register(RoutineSites::tick);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> WORLDS.clear());
        // Game goes home with the hunter, it is not left on the ground
        LootTableEvents.MODIFY_DROPS.register((entry, context, drops) -> {
            if(context.get(LootContextParameters.ATTACKING_ENTITY) instanceof NpcEntity npc
                    && npc.getHuntTarget() != null && npc.getHuntTarget() == context.get(LootContextParameters.THIS_ENTITY))
                drops.clear();
        });
    }

    private static void tick(ServerWorld world) {
        WorldSites sites = WORLDS.get(world.getRegistryKey());
        if(sites == null)
            return;

        for(int step = 0; step < SCAN_STEPS && !sites.scanning.isEmpty(); step++) {
            if(sites.scanning.peek().scanStep(world))
                sites.scanning.poll();
        }

        long time = world.getTime();
        if(time % RETRY_INTERVAL == 0) {
            // A structure's site is kept under the key of each of its managers as well as its own
            for(RoutineSite site : new ReferenceOpenHashSet<>(sites.sites.values())) {
                if(site.hasMissed())
                    site.retryMissed(world);
            }
        }
        if(time % CLEANUP_INTERVAL == 0)
            sites.sites.long2ObjectEntrySet().removeIf(entry -> entry.getValue().isReady() && time - entry.getValue().lastUsed > UNUSED_LIFETIME);
    }

    public static boolean takeStart(ServerWorld world) {
        WorldSites sites = of(world);
        if(sites.starts <= 0)
            return false;
        sites.starts--;
        return true;
    }

    public static boolean takePath(ServerWorld world) {
        WorldSites sites = of(world);
        if(sites.paths <= 0)
            return false;
        sites.paths--;
        return true;
    }

    // Shared by the searches for prey, for company at a table and for somebody to escort
    public static boolean takeEntitySearch(ServerWorld world) {
        WorldSites sites = of(world);
        if(sites.searches <= 0)
            return false;
        sites.searches--;
        return true;
    }

    private static WorldSites of(ServerWorld world) {
        WorldSites sites = WORLDS.computeIfAbsent(world.getRegistryKey(), key -> new WorldSites());
        sites.refresh(world.getTime());
        return sites;
    }

    // Where a travelling party heads: a settlement of its own people, or by night one to raid for the servants of the Enemy.
    // Only places somebody has lately been near are known, looking for others would mean searching the map
    public static @Nullable BlockPos findDestination(ServerWorld world, NpcEntity leader) {
        WorldSites sites = WORLDS.get(world.getRegistryKey());
        Identifier own = leader.getFactionIdentifier();
        if(sites == null || own == null)
            return null;
        boolean raiding = leader.isEvil() && world.isNight();
        RoutineSite best = null;
        double bestDistance = DESTINATION_RANGE * DESTINATION_RANGE;
        for(RoutineSite site : sites.sites.values()) {
            Identifier faction = site.getFaction();
            if(faction == null)
                continue;
            boolean wanted = raiding ? leader.isHostileToFaction(faction) : faction.equals(own);
            double distance = leader.squaredDistanceTo(site.getCenterX(), leader.getY(), site.getCenterZ());
            // A place out where the world stands still is never reached, and the journey would end frozen at the edge
            if(wanted && distance > DESTINATION_MINIMUM * DESTINATION_MINIMUM && distance < bestDistance
                    && world.shouldTickEntityAt(new BlockPos(site.getCenterX(), site.getSurfaceY(), site.getCenterZ()))) {
                best = site;
                bestDistance = distance;
            }
        }
        return best == null ? null : new BlockPos(best.getCenterX(), best.getSurfaceY(), best.getCenterZ());
    }

    // The settlements known around a place, with their free beds. Only places somebody has lately been near are known
    public static List<RoutineSite> near(ServerWorld world, BlockPos pos, int range) {
        WorldSites sites = WORLDS.get(world.getRegistryKey());
        List<RoutineSite> found = new ArrayList<>();
        if(sites == null)
            return found;
        for(RoutineSite site : sites.sites.values()) {
            int reach = range + site.getExtent();
            long distanceX = site.getCenterX() - pos.getX();
            long distanceZ = site.getCenterZ() - pos.getZ();
            if(!site.getBeds().isEmpty() && distanceX * distanceX + distanceZ * distanceZ <= (long) reach * reach && !found.contains(site))
                found.add(site);
        }
        return found;
    }

    // Null while the place is not loaded
    public static @Nullable RoutineSite forManager(ServerWorld world, BlockPos managerPos) {
        if(!world.isChunkLoaded(managerPos.getX() >> 4, managerPos.getZ() >> 4))
            return null;
        WorldSites sites = of(world);
        long key = managerPos.asLong();
        RoutineSite site = sites.sites.get(key);
        if(site == null) {
            StructureStart start = world.getStructureAccessor().getStructureContaining(managerPos, entry -> true);
            if(start != null && start.hasChildren())
                site = forStructure(world, sites, start, managerPos.getY());
            else
                site = create(world, sites, managerPos.getX(), managerPos.getZ(), managerPos.getY(), DEFAULT_RING_RADIUS, 0, null);
            if(site == null)
                return null;
            sites.sites.put(key, site);
        }
        site.lastUsed = world.getTime();
        return site;
    }

    // Wild npcs take the nearby structure of their people, or share the wilderness around them
    public static @Nullable RoutineSite forWild(ServerWorld world, BlockPos home, @Nullable Identifier factionId) {
        if(!world.isChunkLoaded(home.getX() >> 4, home.getZ() >> 4))
            return null;
        WorldSites sites = of(world);

        StructureStart start = factionId == null ? null : findFactionStructure(world, home, factionId);
        if(start != null) {
            RoutineSite site = forStructure(world, sites, start, home.getY());
            if(site != null)
                site.lastUsed = world.getTime();
            return site;
        }

        int cellX = Math.floorDiv(home.getX(), WILD_CELL_SIZE);
        int cellZ = Math.floorDiv(home.getZ(), WILD_CELL_SIZE);
        int centerX = cellX * WILD_CELL_SIZE + WILD_CELL_SIZE / 2;
        int centerZ = cellZ * WILD_CELL_SIZE + WILD_CELL_SIZE / 2;
        if(!world.isChunkLoaded(centerX >> 4, centerZ >> 4))
            return null;
        // Those living in a cave below share none of the ground up top
        boolean underground = RoutineSite.isEnclosed(world, centerX, centerZ, home.getY());
        long wildKey = BlockPos.asLong(cellX, underground ? UNDERGROUND_KEY_Y : WILD_KEY_Y, cellZ);
        RoutineSite site = sites.sites.get(wildKey);
        if(site != null) {
            site.lastUsed = world.getTime();
            return site;
        }

        site = create(world, sites, centerX, centerZ, home.getY(), 0, 0, null);
        sites.sites.put(wildKey, site);
        site.lastUsed = world.getTime();
        return site;
    }

    private static @Nullable RoutineSite forStructure(ServerWorld world, WorldSites sites, StructureStart start, @Nullable Integer referenceY) {
        BlockBox box = start.getBoundingBox();
        BlockPos center = box.getCenter();
        long key = BlockPos.asLong(center.getX(), STRUCTURE_KEY_Y, center.getZ());
        RoutineSite site = sites.sites.get(key);
        if(site != null)
            return site;

        if(!world.isChunkLoaded(center.getX() >> 4, center.getZ() >> 4))
            return null;

        List<BlockBox> pieces = new ArrayList<>();
        for(StructurePiece piece : start.getChildren())
            pieces.add(piece.getBoundingBox());
        int extent = Math.max(box.getBlockCountX(), box.getBlockCountZ()) / 2;
        int radius = MathHelper.clamp(extent + RING_RADIUS_PADDING, MINIMUM_RING_RADIUS, MAXIMUM_RING_RADIUS);
        site = create(world, sites, center.getX(), center.getZ(), referenceY, radius, extent, pieces);
        placesOf(world, start, site);
        sites.sites.put(key, site);
        return site;
    }

    // What the generated data knows of the pieces this settlement is made of
    private static void placesOf(ServerWorld world, StructureStart start, RoutineSite site) {
        List<List<BlockPos>> tables = new ArrayList<>();
        List<StructureFeatureData.Station> stations = new ArrayList<>();
        List<StructureFeatureData.Bed> beds = new ArrayList<>();
        List<BlockPos> households = new ArrayList<>();
        List<BlockPos> stores = new ArrayList<>();
        List<BlockPos> seats = new ArrayList<>();
        List<BlockPos> doors = new ArrayList<>();
        List<StructureFeatureData.Marker> markers = new ArrayList<>();
        Optional<Registry<StructureFeatureData>> registry = world.getRegistryManager().getOptional(DynamicRegistriesME.STRUCTURE_FEATURES);
        if(registry.isEmpty())
            return;
        for(StructurePiece piece : start.getChildren()) {
            if(!(piece instanceof PoolStructurePiece placed))
                continue;
            StructureFeatureData data = NestSurvey.dataOf(registry.get(), placed);
            if(data == null)
                continue;
            for(List<BlockPos> table : data.tables()) {
                List<BlockPos> places = new ArrayList<>();
                for(BlockPos seat : table)
                    places.add(NestSurvey.placedAt(placed, seat));
                tables.add(places);
            }
            for(StructureFeatureData.Bed bed : data.beds()) {
                BlockPos at = NestSurvey.placedAt(placed, bed.pos());
                beds.add(new StructureFeatureData.Bed(at, bed.tag(), bed.store().map(store -> NestSurvey.placedAt(placed, store)),
                        bed.links().stream().map(link -> NestSurvey.placedAt(placed, link)).toList(), bed.manual()));
                if(bed.tag().isPresent())
                    households.add(at);
                bed.store().ifPresent(store -> stores.add(NestSurvey.placedAt(placed, store)));
            }
            for(StructureFeatureData.Station station : data.stations())
                stations.add(new StructureFeatureData.Station(station.kind(), NestSurvey.placedAt(placed, station.pos())));
            for(BlockPos seat : data.seats())
                seats.add(NestSurvey.placedAt(placed, seat));
            for(BlockPos door : data.doors())
                doors.add(NestSurvey.placedAt(placed, door));
            for(StructureFeatureData.Marker marker : data.markers())
                markers.add(new StructureFeatureData.Marker(marker.kind(), NestSurvey.placedAt(placed, marker.pos()), placed.getRotation().rotate(marker.facing()),
                        marker.value(), marker.count(), marker.respawns()));
        }
        site.setTables(tables);
        site.setStations(stations);
        site.setBeds(beds);
        site.setHouseholds(households);
        site.setStores(stores.stream().distinct().toList());
        site.setSeats(seats);
        site.setDoors(doors);
        site.setMarkers(markers);
    }

    private static RoutineSite create(ServerWorld world, WorldSites sites, int centerX, int centerZ, @Nullable Integer referenceY, int ringRadius, int extent, @Nullable List<BlockBox> pieces) {
        RoutineSite site = new RoutineSite(world, centerX, centerZ, referenceY, ringRadius, extent, pieces);
        if(!site.isReady())
            sites.scanning.add(site);
        return site;
    }

    private static @Nullable StructureStart findFactionStructure(ServerWorld world, BlockPos pos, Identifier factionId) {
        Registry<Structure> structures = world.getRegistryManager().getOrThrow(RegistryKeys.STRUCTURE);
        TagKey<Structure> tag = factionTag(factionId.getPath());
        // longbeards.erebor also lives in the structures of the longbeards
        int split = factionId.getPath().indexOf('.');
        TagKey<Structure> parentTag = split < 0 ? null : factionTag(factionId.getPath().substring(0, split));

        // A chunk only knows the structures that reach into it, so the ones around are asked too
        ChunkPos center = new ChunkPos(pos);
        for(int x = -STRUCTURE_SEARCH_CHUNKS; x <= STRUCTURE_SEARCH_CHUNKS; x++) {
            for(int z = -STRUCTURE_SEARCH_CHUNKS; z <= STRUCTURE_SEARCH_CHUNKS; z++) {
                if(!world.isChunkLoaded(center.x + x, center.z + z))
                    continue;
                List<StructureStart> starts = world.getStructureAccessor().getStructureStarts(new ChunkPos(center.x + x, center.z + z), structure -> {
                    var entry = structures.getEntry(structure);
                    return entry.isIn(tag) || (parentTag != null && entry.isIn(parentTag));
                });
                if(!starts.isEmpty())
                    return starts.getFirst();
            }
        }
        return null;
    }

    private static TagKey<Structure> factionTag(String faction) {
        return TagKey.of(RegistryKeys.STRUCTURE, Identifier.of(MiddleEarth.MOD_ID, FACTION_TAG_PREFIX + faction));
    }
}
