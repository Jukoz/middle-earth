package net.sevenstars.middleearth.registries.content.structurefeatures;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.CampfireBlock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.jetbrains.annotations.Nullable;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

// Reads every structure template of the mod when data is generated, for its beds and tags, stations, seats, doors and markers
public class StructureFeaturesRegistry {
    private static final String TEMPLATES = "data/" + MiddleEarth.MOD_ID + "/structure";
    private static final String MANAGER_BLOCK = MiddleEarth.MOD_ID + ":structure_manager";
    private static final String MANAGER_ID = "structure_manager.Identifier";
    private static final String MARKER_BLOCK = MiddleEarth.MOD_ID + ":structure_marker";
    private static final String MARKER_ID = "structure_marker.MarkerId";
    private static final String MARKER_VALUE = "structure_marker.Value";
    private static final String MARKER_COUNT = "structure_marker.Count";
    private static final String MARKER_RESPAWNS = "structure_marker.Respawns";

    // What the scan found in one template, besides the data itself
    // Managers are those the template has blocks for, which say which structure manager data its beds are born by
    public record Template(Identifier id, StructureFeatureData data, int beds, int stores, Set<Identifier> pools, Set<String> unresolved,
                           Set<Identifier> managers) {
        // Nothing in it worth a file
        public boolean isEmpty() {
            return data.beds().isEmpty() && data.tables().isEmpty() && data.stations().isEmpty() && data.seats().isEmpty()
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
        for(Path root : FabricLoader.getInstance().getModContainer(MiddleEarth.MOD_ID).orElseThrow().getRootPaths()) {
            Path folder = root.resolve(TEMPLATES);
            if(!Files.isDirectory(folder))
                continue;
            try(Stream<Path> files = Files.walk(folder)) {
                for(Path file : files.filter(path -> path.toString().endsWith(".nbt")).sorted().toList()) {
                    String path = folder.relativize(file).toString().replace('\\', '/');
                    templates.add(read(MiddleEarth.id(path.substring(0, path.length() - ".nbt".length())), file));
                }
            } catch (IOException e) {
                throw new IllegalStateException("Could not read the structure templates", e);
            }
        }
        scanned = templates;
        return scanned;
    }

    private static Template read(Identifier templateId, Path file) throws IOException {
        NbtCompound root = NbtIo.readCompressed(file, NbtSizeTracker.ofUnlimitedBytes());
        NbtList palette = root.contains("palette") ? root.getListOrEmpty("palette") : root.getListOrEmpty("palettes").getListOrEmpty(0);
        NbtList blocks = root.getListOrEmpty("blocks");

        Set<Identifier> managerIds = new TreeSet<>();
        List<BlockPos> beds = new ArrayList<>();
        List<Optional<BedTag>> bedTags = new ArrayList<>();
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
            if(name.equals(MANAGER_BLOCK)) {
                Identifier id = Identifier.tryParse(block.getCompoundOrEmpty("nbt").getString(MANAGER_ID, ""));
                if(id != null)
                    managerIds.add(id);
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
                if(properties.getString("part", "").equals("head")) {
                    beds.add(pos);
                    bedTags.add(tagOf(block.getCompoundOrEmpty("nbt").getCompoundOrEmpty(BedTagHolder.TAG_KEY)));
                }
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

        List<StructureFeatureData.Bed> bedData = new ArrayList<>();
        // A barrel or a chest where the building has one: what is brought home to a fire goes on it, harvest and all.
        // Only a camp with nothing else keeps its things by the fire
        boolean anyBox = stores.size() > fires.size();
        for(int i = 0; i < beds.size(); i++) {
            BlockPos bed = beds.get(i);
            BlockPos store = null;
            for(BlockPos candidate : stores) {
                if(anyBox && fires.contains(candidate))
                    continue;
                if(store == null || candidate.getSquaredDistance(bed) < store.getSquaredDistance(bed))
                    store = candidate;
            }
            Optional<BedTag> tag = bedTags.get(i);
            List<BlockPos> links = tag.map(found -> found.links().stream().map(bed::add).toList()).orElse(List.of());
            bedData.add(new StructureFeatureData.Bed(bed, tag.map(BedTag::withoutLinks), Optional.ofNullable(store), links, tag.map(BedTag::manual).orElse(false)));
        }
        return new Template(templateId, new StructureFeatureData(bedData, tables, stations, seats, doors, ladders, markers), beds.size(), stores.size(), pools, unresolved, managerIds);
    }

    private static Optional<BedTag> tagOf(NbtCompound tag) {
        return BedTag.CODEC.parse(NbtOps.INSTANCE, tag).result().filter(found -> !found.type().isEmpty());
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
