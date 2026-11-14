package net.sevenstars.middleearth.registries.content.structurefeatures;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.registries.content.npcroles.NpcRoles;
import net.sevenstars.middleearth.registries.content.structurefeatures.StructureFeaturesRegistry.Template;
import net.sevenstars.middleearth.resources.datas.npc_roles.NpcRole;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

// The to-do list of the builders: what each structure lacks for the people in it to live their days, written when data is generated
public class StructureFeaturesReport {
    private static final String DATA = "data/" + MiddleEarth.MOD_ID + "/worldgen/";
    private static final List<String> GUARDS = List.of("guard", "orthanc_guard");
    private static final List<String> LORDS = List.of("lord", "captain", "chief", "chieftain", "marshal");

    private final Map<Identifier, Template> templates = new LinkedHashMap<>();
    private final Map<String, Set<Identifier>> structures = new TreeMap<>();

    private StructureFeaturesReport(List<Template> scanned) {
        for(Template template : scanned)
            templates.put(template.id(), template);
    }

    public static String write(List<Template> scanned, Path file) throws IOException {
        StructureFeaturesReport report = new StructureFeaturesReport(scanned);
        report.readStructures();
        String text = report.render();
        Files.createDirectories(file.getParent());
        Files.writeString(file, text);
        return report.summary();
    }

    // Which templates each structure is made of: its start pool, and every pool a template of it points to
    private void readStructures() throws IOException {
        Map<Identifier, List<Identifier>> pools = new HashMap<>();
        Map<String, Identifier> starts = new TreeMap<>();
        for(Path root : FabricLoader.getInstance().getModContainer(MiddleEarth.MOD_ID).orElseThrow().getRootPaths()) {
            Path structureFolder = root.resolve(DATA + "structure");
            if(Files.isDirectory(structureFolder)) {
                try(Stream<Path> files = Files.walk(structureFolder)) {
                    for(Path file : files.filter(path -> path.toString().endsWith(".json")).sorted().toList()) {
                        JsonObject json = read(file);
                        if(json.has("start_pool"))
                            starts.put(name(structureFolder, file), Identifier.tryParse(json.get("start_pool").getAsString()));
                    }
                }
            }
            Path poolFolder = root.resolve(DATA + "template_pool");
            if(Files.isDirectory(poolFolder)) {
                try(Stream<Path> files = Files.walk(poolFolder)) {
                    for(Path file : files.filter(path -> path.toString().endsWith(".json")).sorted().toList()) {
                        List<Identifier> locations = new ArrayList<>();
                        collectLocations(read(file), locations);
                        pools.put(MiddleEarth.id(name(poolFolder, file)), locations);
                    }
                }
            }
        }
        for(Map.Entry<String, Identifier> start : starts.entrySet()) {
            Set<Identifier> reached = new LinkedHashSet<>();
            Set<Identifier> seen = new TreeSet<>();
            Deque<Identifier> queue = new ArrayDeque<>();
            queue.add(start.getValue());
            while(!queue.isEmpty()) {
                Identifier pool = queue.poll();
                if(pool == null || !seen.add(pool))
                    continue;
                for(Identifier location : pools.getOrDefault(pool, List.of())) {
                    reached.add(location);
                    Template template = templates.get(location);
                    if(template != null)
                        queue.addAll(template.pools());
                }
            }
            structures.put(start.getKey(), reached);
        }
    }

    private static JsonObject read(Path file) throws IOException {
        try(Reader reader = Files.newBufferedReader(file)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private static String name(Path folder, Path file) {
        String path = folder.relativize(file).toString().replace('\\', '/');
        return path.substring(0, path.length() - ".json".length());
    }

    // Single elements, and the lists of elements that hold more of them
    private static void collectLocations(JsonElement json, List<Identifier> into) {
        if(json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            if(object.has("location") && object.get("location").isJsonPrimitive())
                into.add(Identifier.tryParse(object.get("location").getAsString()));
            object.entrySet().forEach(entry -> collectLocations(entry.getValue(), into));
        } else if(json.isJsonArray()) {
            for(JsonElement element : (JsonArray) json)
                collectLocations(element, into);
        }
    }

    private record Warnings(List<String> noBeds, List<String> noStore, List<String> noStation, List<String> noFields, List<String> noGuardMarkers, List<String> noLordSeat) {
        Warnings() {
            this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }

        int total() {
            return noBeds.size() + noStore.size() + noStation.size() + noFields.size() + noGuardMarkers.size() + noLordSeat.size();
        }
    }

    private Warnings warningsOf(Set<Identifier> structure) {
        Set<StationKind> stations = new TreeSet<>();
        Set<MarkerKind> markers = new TreeSet<>();
        for(Identifier id : structure) {
            Template template = templates.get(id);
            if(template == null)
                continue;
            template.data().stations().forEach(station -> stations.add(station.kind()));
            template.data().markers().forEach(marker -> markers.add(marker.kind()));
        }
        Warnings warnings = new Warnings();
        for(Identifier id : structure) {
            Template template = templates.get(id);
            if(template == null)
                continue;
            for(StructureFeatureData.Nest nest : template.data().nests()) {
                String where = "`%s` at %s, nest `%s`".formatted(id.getPath(), at(nest.pos()), nest.id().getPath());
                String last = nest.id().getPath().substring(nest.id().getPath().lastIndexOf('.') + 1);
                if(nest.beds().isEmpty())
                    warnings.noBeds.add(where);
                if(nest.store().isEmpty())
                    warnings.noStore.add(where);
                NpcRole role = NpcRoles.forNest(nest.id()).map(NpcRoles::get).orElse(null);
                if(role != null && role.routine().isPresent()) {
                    List<StationKind> needed = role.routine().get().getStations();
                    if(!needed.isEmpty() && needed.stream().noneMatch(stations::contains))
                        warnings.noStation.add(where + " needs one of " + needed.stream().map(StationKind::asString).toList());
                }
                if(last.equals("farmer") && !stations.contains(StationKind.FIELD))
                    warnings.noFields.add(where);
                if(GUARDS.contains(last) && !markers.contains(MarkerKind.ENTRANCE) && !markers.contains(MarkerKind.GUARD_POST))
                    warnings.noGuardMarkers.add(where);
                if(LORDS.contains(last) && !markers.contains(MarkerKind.LORD_SEAT))
                    warnings.noLordSeat.add(where);
            }
        }
        return warnings;
    }

    private static String at(BlockPos pos) {
        return "(%d, %d, %d)".formatted(pos.getX(), pos.getY(), pos.getZ());
    }

    private String render() {
        StringBuilder out = new StringBuilder();
        out.append("# Structure features\n\n");
        out.append("Written by the data generator from the structure templates. What a structure lacks is listed under its name.\n");
        out.append("Kinds are defined in `BuildFeatures`. Add what is missing to the template and generate again. Markers (`middle-earth:structure_marker`) say what blocks cannot.\n\n");

        Map<String, Warnings> warnings = new LinkedHashMap<>();
        structures.forEach((name, reached) -> warnings.put(name, warningsOf(reached)));

        out.append("## Overview\n\n");
        out.append("| structure | templates | nests | beds | stores | seats | tables | fields | doors | markers | warnings |\n");
        out.append("|---|---|---|---|---|---|---|---|---|---|---|\n");
        structures.forEach((name, reached) -> {
            int[] sum = totals(reached);
            out.append("| %s | %d | %d | %d | %d | %d | %d | %d | %d | %d | %d |\n".formatted(name, sum[0], sum[1], sum[2], sum[3], sum[4], sum[5], sum[6], sum[7], sum[8], warnings.get(name).total()));
        });

        for(Map.Entry<String, Set<Identifier>> structure : structures.entrySet()) {
            Warnings found = warnings.get(structure.getKey());
            out.append("\n## ").append(structure.getKey()).append("\n\n");
            int[] sum = totals(structure.getValue());
            out.append("%d templates with features, %d nests, %d beds, %d stores, %d seats, %d tables, %d fields, %d doors, %d markers".formatted(sum[0], sum[1], sum[2], sum[3], sum[4], sum[5], sum[6], sum[7], sum[8]));
            Map<MarkerKind, Integer> byKind = new EnumMap<>(MarkerKind.class);
            for(Identifier id : structure.getValue()) {
                if(templates.containsKey(id))
                    templates.get(id).data().markers().forEach(marker -> byKind.merge(marker.kind(), 1, Integer::sum));
            }
            out.append(byKind.isEmpty() ? "" : " (" + byKind.entrySet().stream().map(entry -> entry.getKey().asString() + " " + entry.getValue()).toList() + ")");
            out.append("\n\n");
            section(out, "Nests without a bed", found.noBeds);
            section(out, "Nests without a store", found.noStore);
            section(out, "Nests whose role has none of its stations in the structure", found.noStation);
            section(out, "Farmers in a structure without fields", found.noFields);
            section(out, "Guards in a structure without an entrance or guard_post marker", found.noGuardMarkers);
            section(out, "Lords in a structure without a lord_seat marker", found.noLordSeat);
            if(found.total() == 0)
                out.append("Nothing to add.\n");
        }

        Set<Identifier> placed = new TreeSet<>();
        structures.values().forEach(placed::addAll);
        List<String> loose = new ArrayList<>();
        for(Template template : templates.values()) {
            if(!placed.contains(template.id()) && !template.isEmpty())
                loose.add("`%s` (%d nests)".formatted(template.id().getPath(), template.data().nests().size()));
        }
        out.append("\n## Templates no structure reaches\n\n");
        out.append(loose.isEmpty() ? "None.\n" : String.join("\n", loose.stream().map(line -> "- " + line).toList()) + "\n");

        Map<String, Integer> unresolved = new TreeMap<>();
        templates.values().forEach(template -> template.unresolved().forEach(name -> unresolved.merge(name, 1, Integer::sum)));
        out.append("\n## Blocks that no longer exist\n\n");
        out.append(unresolved.isEmpty() ? "None.\n" : String.join("\n", unresolved.entrySet().stream().map(entry -> "- `%s` in %d templates".formatted(entry.getKey(), entry.getValue())).toList()) + "\n");
        return out.toString();
    }

    private static void section(StringBuilder out, String title, List<String> lines) {
        if(lines.isEmpty())
            return;
        out.append("### ").append(title).append(" (").append(lines.size()).append(")\n\n");
        lines.forEach(line -> out.append("- ").append(line).append('\n'));
        out.append('\n');
    }

    // Templates with something in them, nests, beds, stores, seats, tables, fields, doors, markers
    private int[] totals(Set<Identifier> structure) {
        int[] sum = new int[9];
        for(Identifier id : structure) {
            Template template = templates.get(id);
            if(template == null || template.isEmpty())
                continue;
            StructureFeatureData data = template.data();
            sum[0]++;
            sum[1] += data.nests().size();
            sum[2] += template.beds();
            sum[3] += template.stores();
            sum[4] += data.seats().size();
            sum[5] += data.tables().size();
            sum[6] += (int) data.stations().stream().filter(station -> station.kind() == StationKind.FIELD).count();
            sum[7] += data.doors().size();
            sum[8] += data.markers().size();
        }
        return sum;
    }

    private String summary() {
        int nests = 0, noBeds = 0, noStore = 0, beds = 0, stores = 0, seats = 0, doors = 0, fields = 0, markers = 0, files = 0, warned = 0;
        for(Template template : templates.values()) {
            if(!template.isEmpty())
                files++;
            StructureFeatureData data = template.data();
            nests += data.nests().size();
            for(StructureFeatureData.Nest nest : data.nests()) {
                noBeds += nest.beds().isEmpty() ? 1 : 0;
                noStore += nest.store().isEmpty() ? 1 : 0;
            }
            beds += template.beds();
            stores += template.stores();
            seats += data.seats().size();
            doors += data.doors().size();
            markers += data.markers().size();
            fields += (int) data.stations().stream().filter(station -> station.kind() == StationKind.FIELD).count();
        }
        for(Set<Identifier> structure : structures.values())
            warned += warningsOf(structure).total();
        return "Structure features: %d templates with features, %d nests (%d without beds, %d without a store), %d beds, %d stores, %d seats, %d doors, %d field blocks, %d markers, %d warnings in %d structures"
                .formatted(files, nests, noBeds, noStore, beds, stores, seats, doors, fields, markers, warned, structures.size());
    }
}
