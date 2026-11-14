package net.sevenstars.middleearth.registries.content.npcjobs;

import net.minecraft.item.Items;
import net.sevenstars.middleearth.item.WeaponItemsME;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NpcJobs {
    private static final int BED_STATION_RANGE = 48;
    private static final Map<RegistryKey<NpcJob>, NpcJob> JOBS = new LinkedHashMap<>();
    // The last word of a nest's name says who lives there
    private static final Map<String, RegistryKey<NpcJob>> NEST_JOBS = new LinkedHashMap<>();

    // Stays by the beasts once they are brought in, and leaves the life of the place to those who live there
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
    // Fells nothing and sits at no log pile: the logs it cuts from the trees go on the wood pile
    public static final RegistryKey<NpcJob> WOODCUTTER = registerForNest("woodcutter", "lumber", NpcJob.named(RoutineData.create(Period.DAY).timid()
            .add(Activity.CHOP, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_AXE));
    public static final RegistryKey<NpcJob> FISHER = registerForNest("fisher", "fisher",
            NpcJob.named(RoutineData.create(Period.DAY).timid().add(Activity.FISH, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.FISHING_ROD));
    // Bow or crossbow and a knife for close quarters, and no armour: a hunter is no warrior, and does not run from a fight
    public static final RegistryKey<NpcJob> HUNTER = registerForNest("hunter", "hunter",
            NpcJob.named(RoutineData.create(Period.DAY).add(Activity.HUNT, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1))
                    .holding(Items.BOW).orHolding(Items.CROSSBOW).withSidearm(WeaponItemsME.IRON_DAGGER).withoutArmour());
    public static final RegistryKey<NpcJob> MINER = registerForNest("miner", "miner",
            NpcJob.named(RoutineData.create(Period.DAY).timid().add(Activity.MINE, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_PICKAXE));
    // Orcs and goblins who live in a place that has no trade for them. They work by shifts, like the guard
    public static final RegistryKey<NpcJob> LABOURER = registerForNest("labourer", "labourer",
            NpcJob.named(RoutineData.create(Period.SHIFTS).working(StationKind.FORGE, StationKind.HEARTH, StationKind.WOOD_PILE)
                    .add(Activity.WORK, 2).add(Activity.CHOP, 2).add(Activity.FORAGE, 1)).holding(Items.STONE_PICKAXE));
    // Only gives the name, the travelling is done by its party
    public static final RegistryKey<NpcJob> TRAVELLER = register("traveller", NpcJob.named());
    public static final RegistryKey<NpcJob> BEEKEEPER = registerForNest("beekeeper", "beekeeper", trade(StationKind.HIVE));
    public static final RegistryKey<NpcJob> INNKEEPER = registerForNest("innkeeper", "innkeeper", trade(StationKind.HEARTH, StationKind.OVEN));
    public static final RegistryKey<NpcJob> STABLES_MASTER = registerForNest("stables_master", "stables_master", trade());

    // [WARRIORS]
    // Keeps its own name: a Gondor Soldier on guard is still that. Only for those saved with it, nests hand out the duties below
    public static final RegistryKey<NpcJob> GUARD = register("guard", new NpcJob(Optional.of(NpcRoutines.GUARD), false, Optional.empty()));
    public static final RegistryKey<NpcJob> LORD = registerForNests(List.of("lord", "captain", "chief", "chieftain", "marshal"), "lord",
            new NpcJob(Optional.of(RoutineData.create(Period.DAY).add(Activity.COURT, 2).add(Activity.INSPECT, 1).add(Activity.TABLE, 1).add(Activity.VISIT, 1)), false, Optional.empty()));
    // For the orcs and goblins, whose lords keep the night
    public static final RegistryKey<NpcJob> NIGHT_LORD = register("night_lord",
            new NpcJob(Optional.of(RoutineData.create(Period.NIGHT).add(Activity.COURT, 2).add(Activity.INSPECT, 1).add(Activity.TABLE, 1).add(Activity.VISIT, 1)), false, Optional.empty()));
    // The duties of a guard nest, chosen when the guard is spawned
    public static final RegistryKey<NpcJob> GATE_GUARD = register("gate_guard", duty(RoutineData.create(Period.SHIFTS).add(Activity.POST, 1)));
    public static final RegistryKey<NpcJob> BODYGUARD = register("bodyguard", duty(RoutineData.create(Period.SHIFTS).add(Activity.FOLLOW, 1)));
    public static final RegistryKey<NpcJob> SENTRY = register("sentry", duty(RoutineData.create(Period.SHIFTS).add(Activity.POST, 1)));
    public static final RegistryKey<NpcJob> PATROL = register("patrol", duty(RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 3).add(Activity.ESCORT, 1)));
    // Locked in a cell, with no work and nowhere to go but the bed. A thief in a cell is still a thief
    public static final RegistryKey<NpcJob> PRISONER = registerForNests(List.of("prisoner", "prisoner_nest", "prisoner_cell", "small_prisoner_cell", "large_prisoner_cell"),
            "prisoner", duty(RoutineData.create(Period.DAY)));

    private static final List<String> GUARD_TYPES = List.of("guard", "orthanc_guard");
    private static final int BODYGUARDS = 2;

    public static void bootstrap(Registerable<NpcJob> context) {
        JOBS.forEach(context::register);
    }

    public static Iterable<RegistryKey<NpcJob>> all() {
        return JOBS.keySet();
    }

    public static @Nullable NpcJob get(Identifier id) {
        for(Map.Entry<RegistryKey<NpcJob>, NpcJob> job : JOBS.entrySet()) {
            if(job.getKey().getValue().equals(id))
                return job.getValue();
        }
        return null;
    }

    public static boolean isLord(@Nullable Identifier job) {
        return LORD.getValue().equals(job) || NIGHT_LORD.getValue().equals(job);
    }

    // Those who live in a place go through its steel doors. Prisoners, and npcs only passing through, do not
    public static boolean holdsKeys(NpcEntity npc) {
        return npc.getStructureManagerHostPos() != null && !PRISONER.getValue().equals(npc.getJobId());
    }

    public static boolean isGuardType(String type) {
        return GUARD_TYPES.contains(type);
    }

    // The first duty of the place that has nobody on it: a post at each entrance, bodyguards for the lord, a sentry at each
    // lookout, and the rounds for the rest. Counted over everybody living in the place, whatever nest they came from
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
        String path = nestId.getPath();
        @Nullable RegistryKey<NpcJob> job = NEST_JOBS.get(path.substring(path.lastIndexOf('.') + 1));
        return Optional.ofNullable(job).map(RegistryKey::getValue);
    }

    // The kinds of station a job works at. A farmer's is its fields, a woodcutter's the pile its logs go on
    public static List<StationKind> stationKinds(Identifier job) {
        if(FARMER.getValue().equals(job))
            return List.of(StationKind.FIELD);
        if(WOODCUTTER.getValue().equals(job))
            return List.of(StationKind.WOOD_PILE);
        NpcJob found = get(job);
        return found == null || found.routine().isEmpty() ? List.of() : found.routine().get().getStations();
    }

    // The markers that stand for a job's workplace, for the jobs that have no block
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

    // What a bed tagged with this job works at: the nearest station of its kinds, or else the nearest of its markers
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
        return key;
    }

    // Most of the day at the trade, with time left to sit down
    private static NpcJob trade(StationKind... stations) {
        return NpcJob.named(RoutineData.create(Period.DAY).timid().working(stations).add(Activity.WORK, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1));
    }

    // Keeps the name of the class, as a Gondor Knight on duty is still a knight
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
