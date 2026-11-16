package net.sevenstars.middleearth.registries.content.npcjobs;

import net.minecraft.item.Items;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.registries.content.npcclasses.NpcRoutines;
import net.sevenstars.middleearth.resources.datas.npc_jobs.NpcJob;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData.Activity;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData.Period;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NpcJobs {
    // In blocks
    private static final int BED_STATION_RANGE = 48;
    private static final int BODYGUARDS = 2;
    private static final List<String> GUARD_NESTS = List.of("guard", "orthanc_guard");
    private static final List<String> LORD_NESTS = List.of("lord", "captain", "chief", "chieftain", "marshal");
    // The knights and veterans who rank above a place's soldiers
    private static final List<String> ELITE_NESTS = List.of("elite");
    private static final List<String> PRISONER_NESTS = List.of("prisoner", "prisoner_nest", "prisoner_cell", "small_prisoner_cell", "large_prisoner_cell");
    private static final Map<RegistryKey<NpcJob>, NpcJob> JOBS = new LinkedHashMap<>();
    private static final Map<Identifier, NpcJob> JOBS_BY_ID = new HashMap<>();
    // The last word of a nest's name says who lives there
    private static final Map<String, RegistryKey<NpcJob>> NEST_JOBS = new LinkedHashMap<>();

    // Stays with the animals once they're brought in and leaves the rest of the place's life to its residents
    private static final RoutineData HERDING = RoutineData.create(Period.DAY).timid();

    // [HERDERS]
    public static final RegistryKey<NpcJob> SWINEHERD = register("swineherd", NpcJob.named(HERDING));
    public static final RegistryKey<NpcJob> HERDSMAN = register("herdsman", NpcJob.named(HERDING));
    public static final RegistryKey<NpcJob> DROVER = register("drover", NpcJob.named(HERDING));

    // [TRADES]
    public static final RegistryKey<NpcJob> FARMER = registerForNest("farmer", "farmer",
            NpcJob.named(RoutineData.create(Period.DAY).timid().add(Activity.FARM, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_HOE));
    public static final RegistryKey<NpcJob> BAKER = registerForNest("baker", "baker", trade(StationKind.OVEN));
    public static final RegistryKey<NpcJob> SMITH = registerForNest("smith", "smith", trade(StationKind.FORGE, StationKind.GRINDSTONE).holding(ToolItemsME.SMITHING_HAMMER));
    public static final RegistryKey<NpcJob> COOK = registerForNest("cook", "cook", trade(StationKind.HEARTH, StationKind.OVEN));
    public static final RegistryKey<NpcJob> POTTER = registerForNest("potter", "potter", trade(StationKind.HEARTH));
    // Doesn't fell trees or sit at a log pile. The logs it cuts from trees go on the wood pile
    public static final RegistryKey<NpcJob> WOODCUTTER = registerForNest("woodcutter", "lumber", NpcJob.named(RoutineData.create(Period.DAY).timid()
            .add(Activity.CHOP, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_AXE));
    public static final RegistryKey<NpcJob> FISHER = registerForNest("fisher", "fisher",
            NpcJob.named(RoutineData.create(Period.DAY).timid().add(Activity.FISH, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.FISHING_ROD));
    // Bow or crossbow and a knife for close quarters, and no armour. A hunter isn't a warrior but doesn't run from a fight
    public static final RegistryKey<NpcJob> HUNTER = registerForNest("hunter", "hunter",
            NpcJob.named(RoutineData.create(Period.DAY).add(Activity.HUNT, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1))
                    .holding(Items.BOW).orHolding(Items.CROSSBOW).withSidearm(WeaponItemsME.IRON_DAGGER).withoutArmour());
    public static final RegistryKey<NpcJob> MINER = registerForNest("miner", "miner",
            NpcJob.named(RoutineData.create(Period.DAY).timid().add(Activity.MINE, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_PICKAXE));
    // Orcs and goblins who live in a place with no trade for them. They work in shifts, like the guard
    public static final RegistryKey<NpcJob> LABOURER = registerForNest("labourer", "labourer",
            NpcJob.named(RoutineData.create(Period.SHIFTS).working(StationKind.FORGE, StationKind.HEARTH, StationKind.WOOD_PILE)
                    .add(Activity.WORK, 2).add(Activity.CHOP, 2).add(Activity.FORAGE, 1)).holding(Items.STONE_PICKAXE));
    // Only gives the name. The travelling is done by its party
    public static final RegistryKey<NpcJob> TRAVELLER = register("traveller", NpcJob.named());
    public static final RegistryKey<NpcJob> BEEKEEPER = registerForNest("beekeeper", "beekeeper", trade(StationKind.HIVE));
    public static final RegistryKey<NpcJob> INNKEEPER = registerForNest("innkeeper", "innkeeper", trade(StationKind.HEARTH, StationKind.OVEN));
    public static final RegistryKey<NpcJob> STABLES_MASTER = registerForNest("stables_master", "stables_master", trade());

    // [WARRIORS]
    // Keeps the class name, so a Gondor Soldier on guard is still a Gondor Soldier. Only for npcs saved with it, nests assign the duties below
    public static final RegistryKey<NpcJob> GUARD = register("guard", duty(NpcRoutines.GUARD));
    public static final RegistryKey<NpcJob> LORD = registerForNests(LORD_NESTS, "lord", duty(lordRoutine(Period.DAY)));
    // For orcs and goblins, whose lords keep to the night
    public static final RegistryKey<NpcJob> NIGHT_LORD = register("night_lord", duty(lordRoutine(Period.NIGHT)));
    // The duties of a guard nest, picked when the guard is spawned
    public static final RegistryKey<NpcJob> GATE_GUARD = register("gate_guard", duty(NpcRoutines.GUARD));
    public static final RegistryKey<NpcJob> BODYGUARD = register("bodyguard", duty(RoutineData.create(Period.SHIFTS).add(Activity.FOLLOW, 1)));
    public static final RegistryKey<NpcJob> SENTRY = register("sentry", duty(NpcRoutines.GUARD));
    public static final RegistryKey<NpcJob> PATROL = register("patrol", duty(NpcRoutines.WATCH));
    // Locked in a cell with no work and nowhere to go except the bed. A thief in a cell is still a thief
    public static final RegistryKey<NpcJob> PRISONER = registerForNests(PRISONER_NESTS, "prisoner", duty(RoutineData.create(Period.DAY)));

    public static void bootstrap(Registerable<NpcJob> context) {
        JOBS.forEach(context::register);
    }

    public static Iterable<RegistryKey<NpcJob>> all() {
        return JOBS.keySet();
    }

    public static @Nullable NpcJob get(Identifier id) {
        return JOBS_BY_ID.get(id);
    }

    public static boolean isLord(@Nullable Identifier job) {
        return LORD.getValue().equals(job) || NIGHT_LORD.getValue().equals(job);
    }

    public static boolean isGuardNest(String nestKind) {
        return GUARD_NESTS.contains(nestKind);
    }

    public static boolean isNobleNest(String nestKind) {
        return LORD_NESTS.contains(nestKind) || ELITE_NESTS.contains(nestKind);
    }

    // Picks the first duty that still has an opening: a guard at each entrance or guard post, bodyguards for the lord,
    // a sentry at each lookout, then patrol for everyone else. Counts everybody living in the place, whatever nest
    // they came from
    public static Identifier guardDuty(RoutineSite site, Iterable<? extends NpcEntity> residents, boolean lordExpected) {
        int gates = 0;
        int bodyguards = 0;
        int sentries = 0;
        boolean lord = false;
        for(NpcEntity resident : residents) {
            Identifier job = resident.getJobId();
            if(job == null || !resident.isAlive())
                continue;
            if(job.equals(GATE_GUARD.getValue())) gates++;
            else if(job.equals(BODYGUARD.getValue())) bodyguards++;
            else if(job.equals(SENTRY.getValue())) sentries++;
            else if(isLord(job)) lord = true;
        }
        int entrances = site.getMarkers(MarkerKind.ENTRANCE).size() + site.getMarkers(MarkerKind.GUARD_POST).size();
        if(gates < entrances)
            return GATE_GUARD.getValue();
        if((lord || lordExpected) && bodyguards < BODYGUARDS)
            return BODYGUARD.getValue();
        if(sentries < site.getMarkers(MarkerKind.LOOKOUT).size())
            return SENTRY.getValue();
        return PATROL.getValue();
    }

    public static Optional<Identifier> forNest(Identifier nestId) {
        return forNestKind(BedTag.nestKindOf(nestId));
    }

    public static Optional<Identifier> forNestKind(String nestKind) {
        @Nullable RegistryKey<NpcJob> job = NEST_JOBS.get(nestKind);
        return Optional.ofNullable(job).map(RegistryKey::getValue);
    }

    // Trades are for common folk and duties for fighters. Prisoners get neither. The job a nest of that kind gives always fits
    public static boolean fits(Identifier job, BedTag.Folk folk, String nestKind) {
        if(forNestKind(nestKind).filter(job::equals).isPresent())
            return true;
        NpcJob found = get(job);
        if(found == null || folk == BedTag.Folk.PRISONERS || PRISONER.getValue().equals(job))
            return false;
        return folk == BedTag.Folk.MIXED || found.replacesName() == (folk == BedTag.Folk.COMMON);
    }

    // The kinds of station a job works at. A farmer works at fields and a woodcutter at the wood pile
    public static List<StationKind> stationKinds(Identifier job) {
        if(FARMER.getValue().equals(job))
            return List.of(StationKind.FIELD);
        if(WOODCUTTER.getValue().equals(job))
            return List.of(StationKind.WOOD_PILE);
        NpcJob found = get(job);
        return found == null || found.routine().isEmpty() ? List.of() : found.routine().get().getStations();
    }

    // The markers that mark a job's workplace, for jobs that have no block
    public static List<MarkerKind> stationMarkers(Identifier job) {
        if(STABLES_MASTER.getValue().equals(job))
            return List.of(MarkerKind.SPAWN_ANIMAL);
        if(MINER.getValue().equals(job))
            return List.of(MarkerKind.MINE_FACE);
        if(GATE_GUARD.getValue().equals(job))
            return List.of(MarkerKind.GUARD_POST, MarkerKind.ENTRANCE);
        if(SENTRY.getValue().equals(job))
            return List.of(MarkerKind.LOOKOUT);
        if(isLord(job))
            return List.of(MarkerKind.LORD_SEAT);
        return List.of();
    }

    // Where a bed tagged with this job works: the nearest station of its kinds, or else the nearest of its markers
    public static @Nullable BlockPos stationFor(Identifier job, BlockPos bed, List<StructureFeatureData.Station> stations, List<StructureFeatureData.Marker> markers) {
        List<StationKind> kinds = stationKinds(job);
        List<MarkerKind> markerKinds = stationMarkers(job);
        BlockPos best = null;
        for(StructureFeatureData.Station station : stations) {
            if(kinds.contains(station.kind()) && station.pos().isWithinDistance(bed, BED_STATION_RANGE)
                    && (best == null || station.pos().getSquaredDistance(bed) < best.getSquaredDistance(bed)))
                best = station.pos();
        }
        if(best != null || markerKinds.isEmpty())
            return best;
        for(StructureFeatureData.Marker marker : markers) {
            if(markerKinds.contains(marker.kind()) && marker.pos().isWithinDistance(bed, BED_STATION_RANGE)
                    && (best == null || marker.pos().getSquaredDistance(bed) < best.getSquaredDistance(bed)))
                best = marker.pos();
        }
        return best;
    }

    private static RegistryKey<NpcJob> register(String name, NpcJob job) {
        RegistryKey<NpcJob> key = RegistryKey.of(DynamicRegistriesME.NPC_JOB, MiddleEarth.id(name));
        JOBS.put(key, job);
        JOBS_BY_ID.put(key.getValue(), job);
        return key;
    }

    // Mostly working at the trade, with some time left to sit at a table
    private static NpcJob trade(StationKind... stations) {
        return NpcJob.named(RoutineData.create(Period.DAY).timid().working(stations).add(Activity.WORK, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1));
    }

    private static RoutineData lordRoutine(Period period) {
        return RoutineData.create(period).add(Activity.COURT, 2).add(Activity.INSPECT, 1).add(Activity.TABLE, 1).add(Activity.VISIT, 1);
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
