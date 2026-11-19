package net.sevenstars.middleearth.registries.content.structurefeatures;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.CampfireBlock;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtString;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.NpcDoor;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.beds.BedTagHolder;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class StructureFeaturesRegistry {
    private static final String TEMPLATES = "data/" + MiddleEarth.MOD_ID + "/structure";
    private static final String MANAGER_BLOCK = MiddleEarth.MOD_ID + ":structure_manager";
    private static final String ORC_MANAGER_BLOCK = MiddleEarth.MOD_ID + ":orc_structure_manager";
    private static final String MANAGER_ID = "structure_manager.Identifier";
    private static final String MARKER_BLOCK = MiddleEarth.MOD_ID + ":structure_marker";
    private static final String JIGSAW_BLOCK = "minecraft:jigsaw";
    private static final int MINIMUM_TABLE_SEATS = 2;

    private static final String PALETTE_KEY = "palette";
    private static final String PALETTES_KEY = "palettes";
    private static final String BLOCKS_KEY = "blocks";
    private static final String STATE_KEY = "state";
    private static final String POS_KEY = "pos";
    private static final String NAME_KEY = "Name";
    private static final String PROPERTIES_KEY = "Properties";
    private static final String NBT_KEY = "nbt";
    private static final String POOL_KEY = "pool";
    private static final String PART_PROPERTY = "part";
    private static final String HALF_PROPERTY = "half";
    private static final String FACING_PROPERTY = "facing";
    private static final String HEAD_PART = "head";
    private static final String UPPER_HALF = "upper";

    private static List<Template> scanned;
    private static final Map<Identifier, Optional<StructureFeatureData>> LIVE = new ConcurrentHashMap<>();
    private static final Set<Identifier> EDITED = ConcurrentHashMap.newKeySet();

    public record Template(Identifier id, StructureFeatureData data, int stores, Set<Identifier> pools, Set<String> unresolved,
                           Set<Identifier> managers) {
        public boolean isEmpty() {
            return data.beds().isEmpty() && data.tables().isEmpty() && data.stations().isEmpty() && data.seats().isEmpty()
                    && data.doors().isEmpty() && data.ladders().isEmpty() && data.markers().isEmpty() && data.managers().isEmpty();
        }
    }

    public static void bootstrap(Registerable<StructureFeatureData> context) {
        for(Template template : scan()) {
            if(!template.isEmpty())
                context.register(RegistryKey.of(DynamicRegistriesME.STRUCTURE_FEATURES, template.id()), template.data());
        }
    }

    // The generator builds data more than once per run, so read templates once
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

    // A template saved since launch no longer matches the generated data
    public static void edited(Identifier template) {
        EDITED.add(template);
        LIVE.remove(template);
    }

    public static void clearLive() {
        LIVE.clear();
        EDITED.clear();
    }

    public static boolean isEdited(Identifier template) {
        return EDITED.contains(template);
    }

    public static @Nullable StructureFeatureData live(StructureTemplateManager manager, Identifier template) {
        return live(manager, null, template);
    }

    public static @Nullable StructureFeatureData live(StructureTemplateManager manager, @Nullable DynamicRegistryManager registries, Identifier template) {
        return LIVE.computeIfAbsent(template, id -> manager.getTemplate(id).map(found -> read(id, registries, found.writeNbt(new NbtCompound())).data())).orElse(null);
    }

    private static Template read(Identifier templateId, Path file) throws IOException {
        return read(templateId, null, NbtIo.readCompressed(file, NbtSizeTracker.ofUnlimitedBytes()));
    }

    // What a template's blocks held, before off-limits filtering and grouping
    private static class Scan {
        final Set<Identifier> managerIds = new TreeSet<>();
        final List<StructureFeatureData.Manager> managers = new ArrayList<>();
        final List<BlockPos> beds = new ArrayList<>();
        final List<Optional<BedTag>> bedTags = new ArrayList<>();
        final List<BlockPos> stores = new ArrayList<>();
        final List<BlockPos> fires = new ArrayList<>();
        final List<BlockPos> seats = new ArrayList<>();
        final List<BlockPos> doors = new ArrayList<>();
        final List<BlockPos> ladders = new ArrayList<>();
        final Set<BlockPos> tableBlocks = new HashSet<>();
        final List<StructureFeatureData.Station> stations = new ArrayList<>();
        final List<StructureFeatureData.Marker> markers = new ArrayList<>();
        final Set<String> unresolved = new TreeSet<>();
        final Set<Identifier> pools = new TreeSet<>();
    }

    // No registries is the generated data, which uses the built-in jobs
    private static Template read(Identifier templateId, @Nullable DynamicRegistryManager registries, NbtCompound root) {
        NbtList palette = root.contains(PALETTE_KEY) ? root.getListOrEmpty(PALETTE_KEY) : root.getListOrEmpty(PALETTES_KEY).getListOrEmpty(0);
        NbtList blocks = root.getListOrEmpty(BLOCKS_KEY);

        Scan scan = new Scan();
        for(int i = 0; i < blocks.size(); i++)
            classify(scan, blocks.getCompoundOrEmpty(i), palette);
        applyOffLimits(scan);
        List<List<BlockPos>> tables = tables(scan.tableBlocks, scan.seats);
        List<StructureFeatureData.Bed> bedData = bedData(scan, registries);
        return new Template(templateId, new StructureFeatureData(bedData, tables, scan.stations, scan.seats, scan.doors, scan.ladders, scan.markers, scan.managers),
                scan.stores.size(), scan.pools, scan.unresolved, scan.managerIds);
    }

    private static void classify(Scan scan, NbtCompound block, NbtList palette) {
        NbtCompound state = palette.getCompoundOrEmpty(block.getInt(STATE_KEY, 0));
        String name = state.getString(NAME_KEY, "");
        NbtCompound properties = state.getCompoundOrEmpty(PROPERTIES_KEY);
        NbtList at = block.getListOrEmpty(POS_KEY);
        BlockPos pos = new BlockPos(at.getInt(0, 0), at.getInt(1, 0), at.getInt(2, 0));
        NbtCompound data = block.getCompoundOrEmpty(NBT_KEY);
        if(name.equals(MANAGER_BLOCK) || name.equals(ORC_MANAGER_BLOCK)) {
            Identifier id = Identifier.tryParse(data.getString(MANAGER_ID, ""));
            if(id != null)
                scan.managerIds.add(id);
            scan.managers.add(new StructureFeatureData.Manager(pos, Optional.ofNullable(id)));
            return;
        }
        if(name.equals(MARKER_BLOCK)) {
            MarkerKind kind = markerKind(data.getString(StructureMarkerBlockEntity.MARKER_ID, ""));
            if(kind != null)
                scan.markers.add(new StructureFeatureData.Marker(kind, pos, facingOf(properties), data.getString(StructureMarkerBlockEntity.VALUE, ""),
                        data.getInt(StructureMarkerBlockEntity.COUNT, 1), data.getBoolean(StructureMarkerBlockEntity.RESPAWNS, false)));
            return;
        }
        if(name.equals(JIGSAW_BLOCK)) {
            Identifier pool = Identifier.tryParse(data.getString(POOL_KEY, ""));
            if(pool != null)
                scan.pools.add(pool);
            return;
        }
        Block resolved = BuildFeatures.resolve(name);
        if(resolved == null) {
            scan.unresolved.add(name);
            return;
        }
        classifyFeature(scan, resolved, pos, properties, data);
    }

    private static void classifyFeature(Scan scan, Block resolved, BlockPos pos, NbtCompound properties, NbtCompound data) {
        if(BuildFeatures.BED.test(resolved)) {
            if(properties.getString(PART_PROPERTY, "").equals(HEAD_PART)) {
                scan.beds.add(pos);
                scan.bedTags.add(tagOf(data.getCompoundOrEmpty(BedTagHolder.TAG_KEY)));
            }
        }
        if(BuildFeatures.STORE.test(resolved)) {
            scan.stores.add(pos);
            if(resolved instanceof CampfireBlock)
                scan.fires.add(pos);
        }
        if(BuildFeatures.SEAT.test(resolved))
            scan.seats.add(pos);
        if(BuildFeatures.TABLE.test(resolved))
            scan.tableBlocks.add(pos);
        if(BuildFeatures.LADDER.test(resolved))
            scan.ladders.add(pos);
        if(BuildFeatures.DOOR.test(resolved) && isAnchor(resolved, properties))
            scan.doors.add(pos);
        StationKind kind = BuildFeatures.stationOf(resolved);
        if(kind != null)
            scan.stations.add(new StructureFeatureData.Station(kind, pos));
    }

    private static void applyOffLimits(Scan scan) {
        Set<BlockPos> offLimits = new HashSet<>();
        for(StructureFeatureData.Marker marker : scan.markers) {
            if(marker.kind() == MarkerKind.OFF_LIMITS)
                offLimits.add(marker.pos().offset(marker.facing()));
        }
        scan.stores.removeIf(offLimits::contains);
        scan.fires.removeIf(offLimits::contains);
        scan.seats.removeIf(offLimits::contains);
        scan.tableBlocks.removeIf(offLimits::contains);
        scan.ladders.removeIf(offLimits::contains);
        scan.stations.removeIf(station -> offLimits.contains(station.pos()));
        scan.doors.removeIf(door -> offLimits.contains(door) || offLimits.contains(door.up()));
    }

    private static List<StructureFeatureData.Bed> bedData(Scan scan, @Nullable DynamicRegistryManager registries) {
        List<StructureFeatureData.Bed> bedData = new ArrayList<>();
        // Prefer a barrel or chest: things brought to a campfire go on the fire, harvest included
        boolean anyBox = scan.stores.size() > scan.fires.size();
        for(int i = 0; i < scan.beds.size(); i++) {
            BlockPos bed = scan.beds.get(i);
            Optional<BedTag> tag = scan.bedTags.get(i);
            // Things go near the work: the bed's link, or where its job will find one; else by the bed
            BlockPos work = tag.filter(BedTag::manual).filter(found -> !found.links().isEmpty()).map(found -> bed.add(found.links().getFirst()))
                    .or(() -> tag.flatMap(BedTag::job).map(job -> registries == null ? NpcJobs.stationFor(job, bed, scan.stations, scan.markers) : NpcJobs.stationFor(registries, job, bed, scan.stations, scan.markers))).orElse(bed);
            BlockPos store = null;
            for(BlockPos candidate : scan.stores) {
                if(anyBox && scan.fires.contains(candidate))
                    continue;
                if(store == null || candidate.getSquaredDistance(work) < store.getSquaredDistance(work))
                    store = candidate;
            }
            BlockPos picked = tag.flatMap(BedTag::store).map(bed::add).orElse(null);
            if(picked != null && scan.stores.contains(picked))
                store = picked;
            List<BlockPos> links = tag.map(found -> found.links().stream().map(bed::add).toList()).orElse(List.of());
            bedData.add(new StructureFeatureData.Bed(bed, tag.map(BedTag::withoutLinks), Optional.ofNullable(store), links, tag.map(BedTag::manual).orElse(false),
                    tag.flatMap(BedTag::manager).map(bed::add)));
        }
        return bedData;
    }

    private static Optional<BedTag> tagOf(NbtCompound tag) {
        return BedTag.CODEC.parse(NbtOps.INSTANCE, tag).result().filter(found -> !found.nestKind().isEmpty());
    }

    private static @Nullable MarkerKind markerKind(String name) {
        return MarkerKind.CODEC.parse(NbtOps.INSTANCE, NbtString.of(name)).result().orElse(null);
    }

    private static Direction facingOf(NbtCompound properties) {
        String facing = properties.getString(FACING_PROPERTY, "");
        for(Direction side : Direction.Type.HORIZONTAL) {
            if(side.asString().equals(facing))
                return side;
        }
        return Direction.NORTH;
    }

    private static boolean isAnchor(Block door, NbtCompound properties) {
        if(properties.getString(HALF_PROPERTY, "").equals(UPPER_HALF))
            return false;
        return !(door instanceof NpcDoor) || properties.getString(PART_PROPERTY, "0").equals("0");
    }

    // Touching table blocks form one table; one seat isn't a place to meet
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
            if(places.size() >= MINIMUM_TABLE_SEATS)
                tables.add(places);
        }
        return tables;
    }
}
