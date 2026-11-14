package net.sevenstars.middleearth.registries.content.structurebeds;

import net.fabricmc.loader.api.FabricLoader;
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
import net.sevenstars.middleearth.resources.datas.structure_beds.StructureBedData;
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
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

// Reads every structure template of the mod when data is generated, and pairs its beds with its nests
public class StructureBedsRegistry {
    private static final String TEMPLATES = "data/" + MiddleEarth.MOD_ID + "/structure";
    private static final String NEST_BLOCK = MiddleEarth.MOD_ID + ":structure_nest";
    private static final String NEST_ID = "structure_nest.NestId";
    private static final List<String> STORES = List.of("minecraft:barrel", "minecraft:chest", "minecraft:campfire");
    private static final List<String> SEATS = List.of("_chair", "_stool", "_bench");
    // Tables to work at, not to sit at
    private static final List<String> WORK_TABLES = List.of("crafting_table", "cartography_table", "smithing_table", "fletching_table", "enchanting_table", "artisan_table");

    public static void bootstrap(Registerable<StructureBedData> context) {
        Map<Identifier, Integer> bedRadii = bedRadii();
        for(Path root : FabricLoader.getInstance().getModContainer(MiddleEarth.MOD_ID).orElseThrow().getRootPaths()) {
            Path templates = root.resolve(TEMPLATES);
            if(!Files.isDirectory(templates))
                continue;
            try(Stream<Path> files = Files.walk(templates)) {
                for(Path file : files.filter(path -> path.toString().endsWith(".nbt")).sorted().toList()) {
                    StructureBedData data = read(file, bedRadii);
                    if(data == null)
                        continue;
                    String path = templates.relativize(file).toString().replace('\\', '/');
                    Identifier id = MiddleEarth.id(path.substring(0, path.length() - ".nbt".length()));
                    context.register(RegistryKey.of(DynamicRegistriesME.STRUCTURE_BEDS, id), data);
                }
            } catch (IOException e) {
                throw new IllegalStateException("Could not read the structure templates", e);
            }
        }
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

    private static StructureBedData read(Path file, Map<Identifier, Integer> bedRadii) throws IOException {
        NbtCompound root = NbtIo.readCompressed(file, NbtSizeTracker.ofUnlimitedBytes());
        NbtList palette = root.contains("palette") ? root.getListOrEmpty("palette") : root.getListOrEmpty("palettes").getListOrEmpty(0);
        NbtList blocks = root.getListOrEmpty("blocks");

        List<BlockPos> nestPositions = new ArrayList<>();
        List<Identifier> nestIds = new ArrayList<>();
        List<BlockPos> beds = new ArrayList<>();
        List<BlockPos> stores = new ArrayList<>();
        List<BlockPos> seats = new ArrayList<>();
        Set<BlockPos> tableBlocks = new HashSet<>();
        List<StructureBedData.Station> stations = new ArrayList<>();
        for(int i = 0; i < blocks.size(); i++) {
            NbtCompound block = blocks.getCompoundOrEmpty(i);
            NbtCompound state = palette.getCompoundOrEmpty(block.getInt("state", 0));
            String name = state.getString("Name", "");
            NbtList at = block.getListOrEmpty("pos");
            BlockPos pos = new BlockPos(at.getInt(0, 0), at.getInt(1, 0), at.getInt(2, 0));
            if(name.equals(NEST_BLOCK)) {
                Identifier id = Identifier.tryParse(block.getCompoundOrEmpty("nbt").getString(NEST_ID, ""));
                if(id != null && bedRadii.containsKey(id)) {
                    nestPositions.add(pos);
                    nestIds.add(id);
                }
            } else if(name.endsWith("_bed") && state.getCompoundOrEmpty("Properties").getString("part", "").equals("head")) {
                beds.add(pos);
            } else if(STORES.contains(name)) {
                stores.add(pos);
                // The fire a household keeps its food by is also where its cooking is done
                if(name.endsWith("campfire"))
                    stations.add(new StructureBedData.Station(StationKind.HEARTH, pos));
            } else if(StationKind.of(name.substring(name.indexOf(':') + 1)) != null) {
                stations.add(new StructureBedData.Station(StationKind.of(name.substring(name.indexOf(':') + 1)), pos));
            } else if(SEATS.stream().anyMatch(name::endsWith)) {
                seats.add(pos);
            } else if(name.endsWith("_table") && WORK_TABLES.stream().noneMatch(name::endsWith)) {
                tableBlocks.add(pos);
            }
        }
        List<List<BlockPos>> tables = tables(tableBlocks, seats);
        if(nestPositions.isEmpty() && tables.isEmpty() && stations.isEmpty())
            return null;

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

        List<StructureBedData.Nest> nests = new ArrayList<>();
        for(int i = 0; i < nestPositions.size(); i++) {
            BlockPos nest = nestPositions.get(i);
            bedsOf.get(i).sort((a, b) -> Double.compare(a.getSquaredDistance(nest), b.getSquaredDistance(nest)));
            BlockPos store = null;
            for(BlockPos candidate : stores) {
                if(store == null || candidate.getSquaredDistance(nest) < store.getSquaredDistance(nest))
                    store = candidate;
            }
            nests.add(new StructureBedData.Nest(nest, nestIds.get(i), bedsOf.get(i), Optional.ofNullable(store)));
        }
        return new StructureBedData(nests, tables, stations);
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
