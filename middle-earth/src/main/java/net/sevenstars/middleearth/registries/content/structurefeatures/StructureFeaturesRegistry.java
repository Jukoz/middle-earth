package net.sevenstars.middleearth.registries.content.structurefeatures;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.CampfireBlock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataPools;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.jetbrains.annotations.Nullable;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

// Reads every structure template of the mod when data is generated, and pairs its beds with its nests
public class StructureFeaturesRegistry {
    private static final String TEMPLATES = "data/" + MiddleEarth.MOD_ID + "/structure";
    private static final String NEST_BLOCK = MiddleEarth.MOD_ID + ":structure_nest";
    private static final String NEST_ID = "structure_nest.NestId";
    private static final String MARKER_BLOCK = MiddleEarth.MOD_ID + ":structure_marker";
    private static final String MARKER_ID = "structure_marker.MarkerId";
    private static final String MARKER_VALUE = "structure_marker.Value";
    private static final String MARKER_COUNT = "structure_marker.Count";
    private static final String MARKER_RESPAWNS = "structure_marker.Respawns";

    // What the scan found in one template, besides the data itself
    public record Template(Identifier id, StructureFeatureData data, int beds, int stores, Set<Identifier> pools, Set<String> unresolved) {
        // Nothing in it worth a file
        public boolean isEmpty() {
            return data.nests().isEmpty() && data.tables().isEmpty() && data.stations().isEmpty() && data.seats().isEmpty()
                    && data.doors().isEmpty() && data.ladders().isEmpty() && data.markers().isEmpty();
        }
    }

    private static List<Template> scanned;

    public static void bootstrap(Registerable<StructureFeatureData> context) {
        for(Template template : scan()) {
            if(!template.isEmpty())
                context.register(RegistryKey.of(DynamicRegistriesME.STRUCTURE_FEATURES, template.id()), template.data());
        }
    }

    // Data is built more than once in a run of the generator, the templates are read once
    public static synchronized List<Template> scan() {
        if(scanned != null)
            return scanned;
        List<Template> templates = new ArrayList<>();
        Map<Identifier, Integer> bedRadii = bedRadii();
        for(Path root : FabricLoader.getInstance().getModContainer(MiddleEarth.MOD_ID).orElseThrow().getRootPaths()) {
            Path folder = root.resolve(TEMPLATES);
            if(!Files.isDirectory(folder))
                continue;
            try(Stream<Path> files = Files.walk(folder)) {
                for(Path file : files.filter(path -> path.toString().endsWith(".nbt")).sorted().toList()) {
                    String path = folder.relativize(file).toString().replace('\\', '/');
                    templates.add(read(MiddleEarth.id(path.substring(0, path.length() - ".nbt".length())), file, bedRadii));
                }
            } catch (IOException e) {
                throw new IllegalStateException("Could not read the structure templates", e);
            }
        }
        scanned = templates;
        return scanned;
    }

    // Only nests of npcs take beds. Bees and horses have nests as well
    private static Map<Identifier, Integer> bedRadii() {
        Map<Identifier, Integer> radii = new HashMap<>();
        for(StructureManagerData manager : StructureManagerDataPools.all()) {
            for(SpawnNestNodeData nest : manager.getNpcSpawnNest()) {
                boolean people = nest.getNpcSpawnNestPool().stream().allMatch(pool -> pool.getEntityType() == EntitiesME.NPC);
                if(people)
                    radii.put(nest.getId(), nest.getBedRadius());
            }
        }
        return radii;
    }

    private static Template read(Identifier templateId, Path file, Map<Identifier, Integer> bedRadii) throws IOException {
        NbtCompound root = NbtIo.readCompressed(file, NbtSizeTracker.ofUnlimitedBytes());
        NbtList palette = root.contains("palette") ? root.getListOrEmpty("palette") : root.getListOrEmpty("palettes").getListOrEmpty(0);
        NbtList blocks = root.getListOrEmpty("blocks");

        List<BlockPos> nestPositions = new ArrayList<>();
        List<Identifier> nestIds = new ArrayList<>();
        List<BlockPos> beds = new ArrayList<>();
        List<BlockPos> stores = new ArrayList<>();
        List<BlockPos> fires = new ArrayList<>();
        List<BlockPos> seats = new ArrayList<>();
        List<BlockPos> doors = new ArrayList<>();
        List<BlockPos> ladders = new ArrayList<>();
        Set<BlockPos> tableBlocks = new HashSet<>();
        List<StructureFeatureData.Station> stations = new ArrayList<>();
        List<StructureFeatureData.Marker> markers = new ArrayList<>();
        Set<String> unresolved = new TreeSet<>();
        Set<Identifier> pools = new TreeSet<>();
        for(int i = 0; i < blocks.size(); i++) {
            NbtCompound block = blocks.getCompoundOrEmpty(i);
            NbtCompound state = palette.getCompoundOrEmpty(block.getInt("state", 0));
            String name = state.getString("Name", "");
            NbtCompound properties = state.getCompoundOrEmpty("Properties");
            NbtList at = block.getListOrEmpty("pos");
            BlockPos pos = new BlockPos(at.getInt(0, 0), at.getInt(1, 0), at.getInt(2, 0));
            if(name.equals(NEST_BLOCK)) {
                Identifier id = Identifier.tryParse(block.getCompoundOrEmpty("nbt").getString(NEST_ID, ""));
                if(id != null && bedRadii.containsKey(id)) {
                    nestPositions.add(pos);
                    nestIds.add(id);
                }
                continue;
            }
            if(name.equals(MARKER_BLOCK)) {
                NbtCompound markerData = block.getCompoundOrEmpty("nbt");
                MarkerKind kind = markerKind(markerData.getString(MARKER_ID, ""));
                if(kind != null)
                    markers.add(new StructureFeatureData.Marker(kind, pos, facingOf(properties), markerData.getString(MARKER_VALUE, ""),
                            markerData.getInt(MARKER_COUNT, 1), markerData.getBoolean(MARKER_RESPAWNS, false)));
                continue;
            }
            if(name.equals("minecraft:jigsaw")) {
                Identifier pool = Identifier.tryParse(block.getCompoundOrEmpty("nbt").getString("pool", ""));
                if(pool != null)
                    pools.add(pool);
                continue;
            }
            Block resolved = BuildFeatures.resolve(name);
            if(resolved == null) {
                unresolved.add(name);
                continue;
            }
            if(BuildFeatures.BED.test(resolved)) {
                if(properties.getString("part", "").equals("head"))
                    beds.add(pos);
            }
            if(BuildFeatures.STORE.test(resolved)) {
                stores.add(pos);
                if(resolved instanceof CampfireBlock)
                    fires.add(pos);
            }
            if(BuildFeatures.SEAT.test(resolved))
                seats.add(pos);
            if(BuildFeatures.TABLE.test(resolved))
                tableBlocks.add(pos);
            if(BuildFeatures.LADDER.test(resolved))
                ladders.add(pos);
            if(BuildFeatures.DOOR.test(resolved) && isAnchor(resolved, properties))
                doors.add(pos);
            StationKind kind = BuildFeatures.stationOf(resolved);
            if(kind != null)
                stations.add(new StructureFeatureData.Station(kind, pos));
        }
        List<List<BlockPos>> tables = tables(tableBlocks, seats);

        // Builders put a nest by each bed, so every nest takes the closest bed still free before any nest takes a second
        List<List<BlockPos>> bedsOf = new ArrayList<>();
        for(int i = 0; i < nestPositions.size(); i++)
            bedsOf.add(new ArrayList<>());
        List<BlockPos> left = new ArrayList<>(beds);
        while(true) {
            int bestNest = -1;
            BlockPos bestBed = null;
            for(int i = 0; i < nestPositions.size(); i++) {
                if(!bedsOf.get(i).isEmpty())
                    continue;
                int radius = bedRadii.get(nestIds.get(i));
                for(BlockPos bed : left) {
                    double distance = nestPositions.get(i).getSquaredDistance(bed);
                    if(distance <= radius * radius && (bestBed == null || distance < nestPositions.get(bestNest).getSquaredDistance(bestBed))) {
                        bestNest = i;
                        bestBed = bed;
                    }
                }
            }
            if(bestBed == null)
                break;
            bedsOf.get(bestNest).add(bestBed);
            left.remove(bestBed);
        }
        // Beds left over are spare room in the nearest household
        for(BlockPos bed : left) {
            int best = -1;
            for(int i = 0; i < nestPositions.size(); i++) {
                int radius = bedRadii.get(nestIds.get(i));
                double distance = nestPositions.get(i).getSquaredDistance(bed);
                if(distance <= radius * radius && (best < 0 || distance < nestPositions.get(best).getSquaredDistance(bed)))
                    best = i;
            }
            if(best >= 0)
                bedsOf.get(best).add(bed);
        }

        List<StructureFeatureData.Nest> nests = new ArrayList<>();
        for(int i = 0; i < nestPositions.size(); i++) {
            BlockPos nest = nestPositions.get(i);
            bedsOf.get(i).sort((a, b) -> Double.compare(a.getSquaredDistance(nest), b.getSquaredDistance(nest)));
            // A barrel or a chest where the building has one: what is brought home to a fire goes on it, harvest and all.
            // Only a camp with nothing else keeps its things by the fire
            BlockPos store = null;
            boolean anyBox = stores.size() > fires.size();
            for(BlockPos candidate : stores) {
                if(anyBox && fires.contains(candidate))
                    continue;
                if(store == null || candidate.getSquaredDistance(nest) < store.getSquaredDistance(nest))
                    store = candidate;
            }
            nests.add(new StructureFeatureData.Nest(nest, nestIds.get(i), bedsOf.get(i), Optional.ofNullable(store)));
        }
        return new Template(templateId, new StructureFeatureData(nests, tables, stations, seats, doors, ladders, markers), beds.size(), stores.size(), pools, unresolved);
    }

    private static @Nullable MarkerKind markerKind(String name) {
        for(MarkerKind kind : MarkerKind.values()) {
            if(kind.asString().equals(name))
                return kind;
        }
        return null;
    }

    private static Direction facingOf(NbtCompound properties) {
        String facing = properties.getString("facing", "");
        for(Direction side : Direction.Type.HORIZONTAL) {
            if(side.asString().equals(facing))
                return side;
        }
        return Direction.NORTH;
    }

    // Of a door of several blocks, the one the rest are counted from
    private static boolean isAnchor(Block door, NbtCompound properties) {
        if(properties.getString("half", "").equals("upper"))
            return false;
        return !(door instanceof NpcDoor) || properties.getString("part", "0").equals("0");
    }

    // Table blocks that touch are one table, and the seats beside it are its places. A table for one is no place to meet
    private static List<List<BlockPos>> tables(Set<BlockPos> tableBlocks, List<BlockPos> seats) {
        List<List<BlockPos>> tables = new ArrayList<>();
        Set<BlockPos> seen = new HashSet<>();
        Set<BlockPos> taken = new HashSet<>();
        List<BlockPos> ordered = new ArrayList<>(tableBlocks);
        ordered.sort(BlockPos::compareTo);
        for(BlockPos first : ordered) {
            if(!seen.add(first))
                continue;
            List<BlockPos> table = new ArrayList<>(List.of(first));
            for(int i = 0; i < table.size(); i++) {
                for(Direction side : Direction.Type.HORIZONTAL) {
                    BlockPos next = table.get(i).offset(side);
                    if(tableBlocks.contains(next) && seen.add(next))
                        table.add(next);
                }
            }
            List<BlockPos> places = new ArrayList<>();
            for(BlockPos seat : seats) {
                boolean beside = false;
                for(Direction side : Direction.Type.HORIZONTAL)
                    beside |= table.contains(seat.offset(side));
                if(beside && taken.add(seat))
                    places.add(seat);
            }
            if(places.size() >= 2)
                tables.add(places);
        }
        return tables;
    }
}
