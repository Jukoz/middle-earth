package net.sevenstars.middleearth.features.npcs.jobs;

import net.minecraft.item.Items;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.features.smithing.SmithingItems;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.features.npcs.classes.NpcRoutines;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData.Activity;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData.Period;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJob;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NpcJobs {
    // In blocks
    private static final int BED_STATION_RANGE = 48;

    private static final List<String> GUARD_NESTS = List.of("guard", "orthanc_guard");
    private static final List<String> LORD_NESTS = List.of("lord", "captain", "chief", "chieftain", "marshal");
    // Knights and veterans who rank above a place's soldiers
    private static final List<String> ELITE_NESTS = List.of("elite");
    private static final List<String> PRISONER_NESTS = List.of("prisoner", "prisoner_nest", "prisoner_cell", "small_prisoner_cell", "large_prisoner_cell");
    private static final Map<RegistryKey<NpcJob>, NpcJob> JOBS = new LinkedHashMap<>();
    private static final Map<Identifier, NpcJob> JOBS_BY_ID = new HashMap<>();
    // The last word of a nest's name says who lives there
    private static final Map<String, RegistryKey<NpcJob>> NEST_JOBS = new LinkedHashMap<>();

    private static final RoutineData HERDING = RoutineData.create(Period.DAY).timid();

    // [HERDERS]
    public static final RegistryKey<NpcJob> SWINEHERD = register("swineherd", NpcJob.named(HERDING));
    public static final RegistryKey<NpcJob> HERDSMAN = register("herdsman", NpcJob.named(HERDING));
    public static final RegistryKey<NpcJob> DROVER = register("drover", NpcJob.named(HERDING));

    // [TRADES]
    public static final RegistryKey<NpcJob> FARMER = registerForNest("farmer", "farmer",
            NpcJob.named(RoutineData.create(Period.DAY).timid().add(Activity.FARM, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_HOE).atStations(StationKind.FIELD));
    public static final RegistryKey<NpcJob> BAKER = registerForNest("baker", "baker", trade(StationKind.OVEN));
    public static final RegistryKey<NpcJob> SMITH = registerForNest("smith", "smith", trade(StationKind.FORGE, StationKind.GRINDSTONE).holding(SmithingItems.SMITHING_HAMMER));
    public static final RegistryKey<NpcJob> COOK = registerForNest("cook", "cook", trade(StationKind.HEARTH, StationKind.OVEN));
    public static final RegistryKey<NpcJob> POTTER = registerForNest("potter", "potter", trade(StationKind.HEARTH));
    // Doesn't fell trees or work a log pile. Logs it cuts go on the wood pile
    public static final RegistryKey<NpcJob> WOODCUTTER = registerForNest("woodcutter", "lumber", NpcJob.named(RoutineData.create(Period.DAY).timid()
            .add(Activity.CHOP, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_AXE).atStations(StationKind.WOOD_PILE));
    public static final RegistryKey<NpcJob> FISHER = registerForNest("fisher", "fisher",
            NpcJob.named(RoutineData.create(Period.DAY).timid().add(Activity.FISH, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.FISHING_ROD));
    // Bow or crossbow plus a knife, no armour. Doesn't run from a fight
    public static final RegistryKey<NpcJob> HUNTER = registerForNest("hunter", "hunter",
            NpcJob.named(RoutineData.create(Period.DAY).add(Activity.HUNT, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1))
                    .holding(Items.BOW).orHolding(Items.CROSSBOW).withSidearm(WeaponItemsME.IRON_DAGGER).withoutArmour());
    public static final RegistryKey<NpcJob> MINER = registerForNest("miner", "miner",
            NpcJob.named(RoutineData.create(Period.DAY).timid().add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_PICKAXE));
    // Orcs and goblins in a place with no trade for them. They work shifts, like the guard
    public static final RegistryKey<NpcJob> LABOURER = registerForNest("labourer", "labourer",
            NpcJob.named(RoutineData.create(Period.SHIFTS).working(StationKind.FORGE, StationKind.HEARTH, StationKind.WOOD_PILE)
                    .add(Activity.WORK, 2).add(Activity.CHOP, 2).add(Activity.FORAGE, 1)).holding(Items.STONE_PICKAXE));
    // Only gives the name. Its party does the travelling
    public static final RegistryKey<NpcJob> TRAVELLER = register("traveller", NpcJob.named());
    public static final RegistryKey<NpcJob> BEEKEEPER = registerForNest("beekeeper", "beekeeper", trade(StationKind.HIVE));
    public static final RegistryKey<NpcJob> INNKEEPER = registerForNest("innkeeper", "innkeeper", trade(StationKind.HEARTH, StationKind.OVEN));
    public static final RegistryKey<NpcJob> STABLES_MASTER = registerForNest("stables_master", "stables_master", trade().atMarkers(MarkerKind.SPAWN_ANIMAL));

    // [WARRIORS]
    // Only for npcs saved with it. Nests assign the duties below
    public static final RegistryKey<NpcJob> GUARD = register("guard", duty(NpcRoutines.GUARD));
    // Keeps its class's routine, so orc chiefs still keep to the night
    public static final RegistryKey<NpcJob> LORD = registerForNests(LORD_NESTS, "lord", new NpcJob(Optional.empty(), false, Optional.empty()));
    // Guard nest duties, picked when the guard spawns
    public static final RegistryKey<NpcJob> GATE_GUARD = register("gate_guard", duty(NpcRoutines.GUARD).atMarkers(MarkerKind.GUARD_POST, MarkerKind.ENTRANCE));
    public static final RegistryKey<NpcJob> PATROL = register("patrol", duty(NpcRoutines.WATCH));
    // Nowhere to go but the bed. A thief in a cell is still a thief
    public static final RegistryKey<NpcJob> PRISONER = registerForNests(PRISONER_NESTS, "prisoner", duty(RoutineData.create(Period.DAY)));

    private static final Map<Identifier, Identifier> RETIRED = Map.of(
            MiddleEarth.id("sentry"), PATROL.getValue(),
            MiddleEarth.id("bodyguard"), PATROL.getValue(),
            MiddleEarth.id("night_lord"), LORD.getValue());

    public static void bootstrap(Registerable<NpcJob> context) {
        JOBS.forEach(context::register);
    }

    public static Iterable<RegistryKey<NpcJob>> all() {
        return JOBS.keySet();
    }

    // Built-in definitions only, not datapack overrides
    public static @Nullable NpcJob get(Identifier id) {
        return JOBS_BY_ID.get(id);
    }

    public static boolean isGuardNest(String nestKind) {
        return GUARD_NESTS.contains(nestKind);
    }

    public static boolean isNobleNest(String nestKind) {
        return LORD_NESTS.contains(nestKind) || ELITE_NESTS.contains(nestKind);
    }

    // Gates first while any are unmanned, then patrol
    public static Identifier guardDuty(RoutineSite site, Iterable<? extends NpcEntity> residents) {
        int gates = 0;
        for(NpcEntity resident : residents) {
            if(resident.isAlive() && GATE_GUARD.getValue().equals(resident.getJobId()))
                gates++;
        }
        int entrances = site.getMarkers(MarkerKind.ENTRANCE).size() + site.getMarkers(MarkerKind.GUARD_POST).size();
        return gates < entrances ? GATE_GUARD.getValue() : PATROL.getValue();
    }

    // Jobs that were folded into others. Old saves still name them
    public static @Nullable Identifier current(@Nullable Identifier job) {
        return job == null ? null : RETIRED.getOrDefault(job, job);
    }

    public static Optional<Identifier> forNest(Identifier nestId) {
        return forNestKind(BedTag.nestKindOf(nestId));
    }

    public static Optional<Identifier> forNestKind(String nestKind) {
        @Nullable RegistryKey<NpcJob> job = NEST_JOBS.get(nestKind);
        return Optional.ofNullable(job).map(RegistryKey::getValue);
    }

    public static @Nullable NpcJob get(DynamicRegistryManager registries, Identifier id) {
        return registries.getOptional(DynamicRegistriesME.NPC_JOB).map(jobs -> jobs.get(id)).orElse(null);
    }

    // Trades for common folk, duties for fighters, prisoners neither. A nest's own job always fits
    public static boolean fits(Identifier job, BedTag.Folk folk, String nestKind) {
        return fits(get(job), job, folk, nestKind);
    }

    public static boolean fits(DynamicRegistryManager registries, Identifier job, BedTag.Folk folk, String nestKind) {
        return fits(get(registries, job), job, folk, nestKind);
    }

    private static boolean fits(@Nullable NpcJob found, Identifier job, BedTag.Folk folk, String nestKind) {
        if(forNestKind(nestKind).filter(job::equals).isPresent())
            return true;
        if(found == null || folk == BedTag.Folk.PRISONERS || PRISONER.getValue().equals(job))
            return false;
        return folk == BedTag.Folk.MIXED || found.isTrade() == (folk == BedTag.Folk.COMMON);
    }

    public static List<StationKind> stationKinds(Identifier job) {
        return stationKinds(get(job));
    }

    public static List<StationKind> stationKinds(DynamicRegistryManager registries, Identifier job) {
        return stationKinds(get(registries, job));
    }

    private static List<StationKind> stationKinds(@Nullable NpcJob found) {
        return found == null ? List.of() : found.stations();
    }

    public static List<MarkerKind> stationMarkers(Identifier job) {
        NpcJob found = get(job);
        return found == null ? List.of() : found.stationMarkers();
    }

    public static List<MarkerKind> stationMarkers(DynamicRegistryManager registries, Identifier job) {
        NpcJob found = get(registries, job);
        return found == null ? List.of() : found.stationMarkers();
    }

    public static @Nullable BlockPos stationFor(Identifier job, BlockPos bed, List<StructureFeatureData.Station> stations, List<StructureFeatureData.Marker> markers) {
        return stationFor(get(job), bed, stations, markers);
    }

    public static @Nullable BlockPos stationFor(DynamicRegistryManager registries, Identifier job, BlockPos bed, List<StructureFeatureData.Station> stations, List<StructureFeatureData.Marker> markers) {
        return stationFor(get(registries, job), bed, stations, markers);
    }

    private static @Nullable BlockPos stationFor(@Nullable NpcJob found, BlockPos bed, List<StructureFeatureData.Station> stations, List<StructureFeatureData.Marker> markers) {
        List<StationKind> kinds = stationKinds(found);
        List<MarkerKind> markerKinds = found == null ? List.of() : found.stationMarkers();
        BlockPos best = null;
        for(StructureFeatureData.Station station : stations) {
            if(kinds.contains(station.kind()))
                best = nearer(best, station.pos(), bed);
        }
        if(best != null || markerKinds.isEmpty())
            return best;
        for(StructureFeatureData.Marker marker : markers) {
            if(markerKinds.contains(marker.kind()))
                best = nearer(best, marker.pos(), bed);
        }
        return best;
    }

    private static @Nullable BlockPos nearer(@Nullable BlockPos best, BlockPos candidate, BlockPos bed) {
        if(candidate.isWithinDistance(bed, BED_STATION_RANGE) && (best == null || candidate.getSquaredDistance(bed) < best.getSquaredDistance(bed)))
            return candidate;
        return best;
    }

    private static RegistryKey<NpcJob> register(String name, NpcJob job) {
        RegistryKey<NpcJob> key = RegistryKey.of(DynamicRegistriesME.NPC_JOB, MiddleEarth.id(name));
        JOBS.put(key, job);
        JOBS_BY_ID.put(key.getValue(), job);
        return key;
    }

    private static NpcJob trade(StationKind... stations) {
        return NpcJob.named(RoutineData.create(Period.DAY).timid().working(stations).add(Activity.WORK, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1));
    }

    // Keeps the class name, so a Gondor Knight on duty is still a knight
    private static NpcJob duty(RoutineData routine) {
        return new NpcJob(Optional.of(routine), false, Optional.empty());
    }

    private static RegistryKey<NpcJob> registerForNests(List<String> nestNames, String name, NpcJob job) {
        RegistryKey<NpcJob> key = register(name, job);
        nestNames.forEach(nestName -> NEST_JOBS.put(nestName, key));
        return key;
    }

    private static RegistryKey<NpcJob> registerForNest(String name, String nestName, NpcJob job) {
        RegistryKey<NpcJob> key = register(name, job);
        NEST_JOBS.put(nestName, key);
        return key;
    }
}
