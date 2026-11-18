package net.sevenstars.middleearth.registries.content.structurefeatures;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.routine.PrisonCell;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.registries.content.structurefeatures.StructureFeaturesRegistry.Template;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataPools;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

// Builders' to-do list of what each structure still lacks. Written during datagen
public class StructureFeaturesReport {
    private static final String DATA = "data/" + MiddleEarth.MOD_ID + "/worldgen/";

    private final Map<Identifier, Template> templates = new LinkedHashMap<>();
    private final Map<String, Set<Identifier>> structures = new TreeMap<>();
    private final Map<String, Warnings> warnings = new LinkedHashMap<>();

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

    private static void collectLocations(JsonElement json, List<Identifier> into) {
        if(json.isJsonObject()) {
            JsonObject object = json.getAsJsonObject();
            if(object.has("location") && object.get("location").isJsonPrimitive()) {
                Identifier location = Identifier.tryParse(object.get("location").getAsString());
                if(location != null)
                    into.add(location);
            }
            object.entrySet().forEach(entry -> collectLocations(entry.getValue(), into));
        } else if(json.isJsonArray()) {
            for(JsonElement element : (JsonArray) json)
                collectLocations(element, into);
        }
    }

    private record Warnings(List<String> noNestKind, List<String> noStore, List<String> noStation, List<String> noGuardMarkers,
                            List<String> badSpawns, List<String> badCells, List<String> badLinks) {
        Warnings() {
            this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
        }

        int total() {
            return noNestKind.size() + noStore.size() + noStation.size() + noGuardMarkers.size() + badSpawns.size() + badCells.size() + badLinks.size();
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
        Set<String> nestKinds = nestKindsOf(structure);
        Set<Identifier> managers = managerIdsOf(structure);
        for(Identifier id : structure) {
            Template template = templates.get(id);
            if(template == null)
                continue;
            Set<BlockPos> known = recognised(template.data());
            for(StructureFeatureData.Bed bed : template.data().beds()) {
                if(bed.tag().isEmpty())
                    continue;
                BedTag tag = bed.tag().get();
                String where = "`%s` at %s, bed tagged `%s`".formatted(id.getPath(), at(bed.pos()), tag.nestKind());
                if(bed.manual()) {
                    for(BlockPos link : bed.links()) {
                        if(!known.contains(link))
                            warnings.badLinks.add(where + " is linked by hand to %s, which is no station, marker, store or seat".formatted(at(link)));
                    }
                }
                if(tag.place().isPresent() && !BedTag.placeHas(tag.place().get(), tag.nestKind()))
                    warnings.noNestKind.add(where + " of `%s`, which has no nest of that kind".formatted(tag.place().get().getPath()));
                else if(tag.place().isEmpty() && !nestKinds.contains(tag.nestKind()))
                    warnings.noNestKind.add(where + (managers.isEmpty() ? " in a structure with no nest data to say who is born" : " has no nest of that kind in the nests of the structure"));
                if(bed.store().isEmpty())
                    warnings.noStore.add(where);
                if(tag.job().isPresent()) {
                    List<StationKind> needed = NpcJobs.stationKinds(tag.job().get());
                    List<MarkerKind> neededMarkers = NpcJobs.stationMarkers(tag.job().get());
                    if(!needed.isEmpty() && needed.stream().noneMatch(stations::contains))
                        warnings.noStation.add(where + ", job `%s` needs one of %s".formatted(tag.job().get().getPath(), needed.stream().map(StationKind::asString).toList()));
                    else if(needed.isEmpty() && !neededMarkers.isEmpty() && neededMarkers.stream().noneMatch(markers::contains))
                        warnings.noStation.add(where + ", job `%s` needs a marker of %s".formatted(tag.job().get().getPath(), neededMarkers.stream().map(MarkerKind::asString).toList()));
                } else {
                    if(NpcJobs.isGuardNest(tag.nestKind()) && !markers.contains(MarkerKind.ENTRANCE) && !markers.contains(MarkerKind.GUARD_POST))
                        warnings.noGuardMarkers.add(where);
                }
            }
            List<BlockPos> corners = new ArrayList<>();
            for(StructureFeatureData.Marker marker : template.data().markers()) {
                String where = "`%s` at %s, %s".formatted(id.getPath(), at(marker.pos()), marker.kind().asString());
                if(marker.kind() == MarkerKind.SPAWN_NPC) {
                    if(marker.value().isEmpty())
                        warnings.badSpawns.add(where + " has no value");
                    else if(marker.value().indexOf(Identifier.NAMESPACE_SEPARATOR) >= 0 ? Identifier.tryParse(marker.value()) == null || !BedTag.peopleNests().containsKey(Identifier.tryParse(marker.value()))
                            : !nestKinds.contains(marker.value()))
                        warnings.badSpawns.add(where + " has the unknown nest `%s`".formatted(marker.value()));
                } else if(marker.kind() == MarkerKind.SPAWN_ANIMAL) {
                    Identifier type = Identifier.tryParse(marker.value());
                    if(marker.value().isEmpty())
                        warnings.badSpawns.add(where + " has no value");
                    else if(type == null || !Registries.ENTITY_TYPE.containsId(type))
                        warnings.badSpawns.add(where + " has the unknown entity type `%s`".formatted(marker.value()));
                } else if(marker.kind() == MarkerKind.CELL_CORNER) {
                    corners.add(marker.pos());
                }
            }
            for(List<BlockPos> cell : PrisonCell.group(corners)) {
                if(cell.size() != PrisonCell.CORNERS)
                    warnings.badCells.add("`%s` at %s, %d cell_corner markers together".formatted(id.getPath(), at(cell.get(0)), cell.size()));
            }
        }
        return warnings;
    }

    private static Set<BlockPos> recognised(StructureFeatureData data) {
        Set<BlockPos> known = new HashSet<>();
        data.stations().forEach(station -> known.add(station.pos()));
        data.markers().forEach(marker -> known.add(marker.pos()));
        data.beds().forEach(bed -> bed.store().ifPresent(known::add));
        known.addAll(data.seats());
        known.addAll(data.doors());
        known.addAll(data.ladders());
        return known;
    }

    private Set<Identifier> managerIdsOf(Set<Identifier> structure) {
        Set<Identifier> managerIds = new TreeSet<>();
        for(Identifier id : structure) {
            Template template = templates.get(id);
            if(template != null)
                managerIds.addAll(template.managers());
        }
        return managerIds;
    }

    private Set<String> nestKindsOf(Set<Identifier> structure) {
        Set<Identifier> managerIds = managerIdsOf(structure);
        Set<String> nestKinds = new TreeSet<>();
        for(StructureManagerData manager : StructureManagerDataPools.all()) {
            if(!managerIds.contains(manager.getId()))
                continue;
            for(SpawnNestNodeData nest : manager.getNpcSpawnNest()) {
                if(nest.isPeople())
                    nestKinds.add(BedTag.nestKindOf(nest.getId()));
            }
        }
        return nestKinds;
    }

    private static String at(BlockPos pos) {
        return "(%d, %d, %d)".formatted(pos.getX(), pos.getY(), pos.getZ());
    }

    private String render() {
        StringBuilder out = new StringBuilder();
        out.append("# Structure features\n\n");
        out.append("Written by the data generator from the structure templates. What a structure lacks is listed under its name.\n");
        out.append("Kinds are defined in `BuildFeatures`. Add what is missing to the template and generate again. Markers (`middle-earth:structure_marker`) say what blocks cannot.\n\n");

        Map<String, Totals> totals = new LinkedHashMap<>();
        structures.forEach((name, reached) -> {
            warnings.put(name, warningsOf(reached));
            totals.put(name, totalsOf(reached));
        });

        out.append("## Overview\n\n");
        out.append("| structure | templates | tagged beds | beds | stores | seats | tables | fields | doors | markers | warnings |\n");
        out.append("|---|---|---|---|---|---|---|---|---|---|---|\n");
        structures.forEach((name, reached) -> {
            Totals sum = totals.get(name);
            out.append("| %s | %d | %d | %d | %d | %d | %d | %d | %d | %d | %d |\n".formatted(name, sum.templates, sum.taggedBeds, sum.beds, sum.stores, sum.seats, sum.tables, sum.fields, sum.doors, sum.markers, warnings.get(name).total()));
        });

        for(Map.Entry<String, Set<Identifier>> structure : structures.entrySet()) {
            Warnings found = warnings.get(structure.getKey());
            out.append("\n## ").append(structure.getKey()).append("\n\n");
            Totals sum = totals.get(structure.getKey());
            out.append("%d templates with features, %d tagged beds, %d beds, %d stores, %d seats, %d tables, %d fields, %d doors, %d markers".formatted(sum.templates, sum.taggedBeds, sum.beds, sum.stores, sum.seats, sum.tables, sum.fields, sum.doors, sum.markers));
            Map<MarkerKind, Integer> byKind = new EnumMap<>(MarkerKind.class);
            for(Identifier id : structure.getValue()) {
                if(templates.containsKey(id))
                    templates.get(id).data().markers().forEach(marker -> byKind.merge(marker.kind(), 1, Integer::sum));
            }
            out.append(byKind.isEmpty() ? "" : " (" + byKind.entrySet().stream().map(entry -> entry.getKey().asString() + " " + entry.getValue()).toList() + ")");
            out.append("\n\n");
            section(out, "Tagged beds whose nest kind has no nest pool in the structure", found.noNestKind);
            section(out, "Tagged beds without a store", found.noStore);
            section(out, "Tagged beds whose job has none of its stations or markers in the structure", found.noStation);
            section(out, "Guards in a structure without an entrance or guard_post marker", found.noGuardMarkers);
            section(out, "Spawn markers with no value or an unknown one", found.badSpawns);
            section(out, "Cell corners that do not make up groups of four", found.badCells);
            section(out, "Beds linked by hand to something that is not recognised", found.badLinks);
            if(found.total() == 0)
                out.append("Nothing to add.\n");
        }

        Set<Identifier> placed = new TreeSet<>();
        structures.values().forEach(placed::addAll);
        List<String> loose = new ArrayList<>();
        for(Template template : templates.values()) {
            if(!placed.contains(template.id()) && !template.isEmpty())
                loose.add("`%s` (%d beds)".formatted(template.id().getPath(), template.data().beds().size()));
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

    private static class Totals {
        int templates, taggedBeds, beds, stores, seats, tables, fields, doors, markers;
    }

    private Totals totalsOf(Set<Identifier> structure) {
        Totals sum = new Totals();
        for(Identifier id : structure) {
            Template template = templates.get(id);
            if(template == null || template.isEmpty())
                continue;
            StructureFeatureData data = template.data();
            sum.templates++;
            sum.taggedBeds += (int) data.beds().stream().filter(bed -> bed.tag().isPresent()).count();
            sum.beds += data.beds().size();
            sum.stores += template.stores();
            sum.seats += data.seats().size();
            sum.tables += data.tables().size();
            sum.fields += (int) data.stations().stream().filter(station -> station.kind() == StationKind.FIELD).count();
            sum.doors += data.doors().size();
            sum.markers += data.markers().size();
        }
        return sum;
    }

    private String summary() {
        int tagged = 0, noStore = 0, beds = 0, stores = 0, seats = 0, doors = 0, fields = 0, markers = 0, files = 0, warned = 0;
        for(Template template : templates.values()) {
            if(!template.isEmpty())
                files++;
            StructureFeatureData data = template.data();
            for(StructureFeatureData.Bed bed : data.beds()) {
                tagged += bed.tag().isPresent() ? 1 : 0;
                noStore += bed.tag().isPresent() && bed.store().isEmpty() ? 1 : 0;
            }
            beds += data.beds().size();
            stores += template.stores();
            seats += data.seats().size();
            doors += data.doors().size();
            markers += data.markers().size();
            fields += (int) data.stations().stream().filter(station -> station.kind() == StationKind.FIELD).count();
        }
        for(Warnings found : warnings.values())
            warned += found.total();
        return "Structure features: %d templates with features, %d tagged beds (%d without a store), %d beds, %d stores, %d seats, %d doors, %d field blocks, %d markers, %d warnings in %d structures"
                .formatted(files, tagged, noStore, beds, stores, seats, doors, fields, markers, warned, structures.size());
    }
}
