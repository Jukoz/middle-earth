package net.sevenstars.middleearth.registries.content.npcroles;

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
import net.sevenstars.middleearth.registries.content.npctypes.NpcRoutines;
import net.sevenstars.middleearth.resources.datas.npc_roles.NpcRole;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Activity;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Period;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class NpcRoles {
    private static final int BED_STATION_RANGE = 48;
    private static final Map<RegistryKey<NpcRole>, NpcRole> ROLES = new LinkedHashMap<>();
    // The last word of a nest's name says who lives there
    private static final Map<String, RegistryKey<NpcRole>> NEST_ROLES = new LinkedHashMap<>();

    // Stays by the beasts once they are brought in, and leaves the life of the place to those who live there
    private static final RoutineData HERDING = RoutineData.create(Period.DAY).timid();

    // [HERDERS]
    public static final RegistryKey<NpcRole> SWINEHERD = register("swineherd", NpcRole.named(HERDING));
    public static final RegistryKey<NpcRole> HERDSMAN = register("herdsman", NpcRole.named(HERDING));
    public static final RegistryKey<NpcRole> DROVER = register("drover", NpcRole.named(HERDING));

    // [TRADES]
    public static final RegistryKey<NpcRole> FARMER = registerForNest("farmer", "farmer",
            NpcRole.named(RoutineData.create(Period.DAY).timid().add(Activity.FARM, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_HOE));
    public static final RegistryKey<NpcRole> BAKER = registerForNest("baker", "baker", trade(StationKind.OVEN));
    public static final RegistryKey<NpcRole> SMITH = registerForNest("smith", "smith", trade(StationKind.FORGE, StationKind.GRINDSTONE).holding(ToolItemsME.SMITHING_HAMMER));
    public static final RegistryKey<NpcRole> COOK = registerForNest("cook", "cook", trade(StationKind.HEARTH, StationKind.OVEN));
    public static final RegistryKey<NpcRole> POTTER = registerForNest("potter", "potter", trade(StationKind.HEARTH));
    // Fells nothing and sits at no log pile: the logs it cuts from the trees go on the wood pile
    public static final RegistryKey<NpcRole> WOODCUTTER = registerForNest("woodcutter", "lumber", NpcRole.named(RoutineData.create(Period.DAY).timid()
            .add(Activity.CHOP, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_AXE));
    public static final RegistryKey<NpcRole> FISHER = registerForNest("fisher", "fisher",
            NpcRole.named(RoutineData.create(Period.DAY).timid().add(Activity.FISH, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.FISHING_ROD));
    // Bow or crossbow and a knife for close quarters, and no armour: a hunter is no warrior, and does not run from a fight
    public static final RegistryKey<NpcRole> HUNTER = registerForNest("hunter", "hunter",
            NpcRole.named(RoutineData.create(Period.DAY).add(Activity.HUNT, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1))
                    .holding(Items.BOW).orHolding(Items.CROSSBOW).withSidearm(WeaponItemsME.IRON_DAGGER).withoutArmour());
    public static final RegistryKey<NpcRole> MINER = registerForNest("miner", "miner",
            NpcRole.named(RoutineData.create(Period.DAY).timid().add(Activity.MINE, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.IRON_PICKAXE));
    // Orcs and goblins who live in a place that has no trade for them. They work by shifts, like the guard
    public static final RegistryKey<NpcRole> LABOURER = registerForNest("labourer", "labourer",
            NpcRole.named(RoutineData.create(Period.SHIFTS).working(StationKind.FORGE, StationKind.HEARTH, StationKind.WOOD_PILE)
                    .add(Activity.WORK, 2).add(Activity.CHOP, 2).add(Activity.FORAGE, 1)).holding(Items.STONE_PICKAXE));
    // Only gives the name, the travelling is done by its party
    public static final RegistryKey<NpcRole> TRAVELLER = register("traveller", NpcRole.named());
    public static final RegistryKey<NpcRole> BEEKEEPER = registerForNest("beekeeper", "beekeeper", trade(StationKind.HIVE));
    public static final RegistryKey<NpcRole> INNKEEPER = registerForNest("innkeeper", "innkeeper", trade(StationKind.HEARTH, StationKind.OVEN));
    public static final RegistryKey<NpcRole> STABLES_MASTER = registerForNest("stables_master", "stables_master", trade());

    // [WARRIORS]
    // Keeps its own name: a Gondor Soldier on guard is still that. Only for those saved with it, nests hand out the duties below
    public static final RegistryKey<NpcRole> GUARD = register("guard", new NpcRole(Optional.of(NpcRoutines.GUARD), false, Optional.empty()));
    public static final RegistryKey<NpcRole> LORD = registerForNests(List.of("lord", "captain", "chief", "chieftain", "marshal"), "lord",
            new NpcRole(Optional.of(RoutineData.create(Period.DAY).add(Activity.COURT, 2).add(Activity.INSPECT, 1).add(Activity.TABLE, 1).add(Activity.VISIT, 1)), false, Optional.empty()));
    // For the orcs and goblins, whose lords keep the night
    public static final RegistryKey<NpcRole> NIGHT_LORD = register("night_lord",
            new NpcRole(Optional.of(RoutineData.create(Period.NIGHT).add(Activity.COURT, 2).add(Activity.INSPECT, 1).add(Activity.TABLE, 1).add(Activity.VISIT, 1)), false, Optional.empty()));
    // The duties of a guard nest, chosen when the guard is spawned
    public static final RegistryKey<NpcRole> GATE_GUARD = register("gate_guard", duty(RoutineData.create(Period.SHIFTS).add(Activity.POST, 1)));
    public static final RegistryKey<NpcRole> BODYGUARD = register("bodyguard", duty(RoutineData.create(Period.SHIFTS).add(Activity.FOLLOW, 1)));
    public static final RegistryKey<NpcRole> SENTRY = register("sentry", duty(RoutineData.create(Period.SHIFTS).add(Activity.POST, 1)));
    public static final RegistryKey<NpcRole> PATROL = register("patrol", duty(RoutineData.create(Period.SHIFTS).add(Activity.PATROL, 3).add(Activity.ESCORT, 1)));
    // Locked in a cell, with no work and nowhere to go but the bed. A thief in a cell is still a thief
    public static final RegistryKey<NpcRole> PRISONER = registerForNests(List.of("prisoner", "prisoner_nest", "prisoner_cell", "small_prisoner_cell", "large_prisoner_cell"),
            "prisoner", duty(RoutineData.create(Period.DAY)));

    private static final List<String> GUARD_TYPES = List.of("guard", "orthanc_guard");
    private static final int BODYGUARDS = 2;

    public static void bootstrap(Registerable<NpcRole> context) {
        ROLES.forEach(context::register);
    }

    public static Iterable<RegistryKey<NpcRole>> all() {
        return ROLES.keySet();
    }

    public static @Nullable NpcRole get(Identifier id) {
        for(Map.Entry<RegistryKey<NpcRole>, NpcRole> role : ROLES.entrySet()) {
            if(role.getKey().getValue().equals(id))
                return role.getValue();
        }
        return null;
    }

    public static boolean isLord(@Nullable Identifier role) {
        return LORD.getValue().equals(role) || NIGHT_LORD.getValue().equals(role);
    }

    // Those who live in a place go through its steel doors. Prisoners, and npcs only passing through, do not
    public static boolean holdsKeys(NpcEntity npc) {
        return npc.getStructureManagerHostPos() != null && !PRISONER.getValue().equals(npc.getRoleId());
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
            Identifier role = resident.getRoleId();
            if(role == null || !resident.isAlive())
                continue;
            if(role.equals(GATE_GUARD.getValue())) gates++;
            else if(role.equals(BODYGUARD.getValue())) bodyguards++;
            else if(role.equals(SENTRY.getValue())) sentries++;
            else if(isLord(role)) lord = true;
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
        @Nullable RegistryKey<NpcRole> role = NEST_ROLES.get(path.substring(path.lastIndexOf('.') + 1));
        return Optional.ofNullable(role).map(RegistryKey::getValue);
    }

    // The kinds of station a role works at. A farmer's is its fields, a woodcutter's the pile its logs go on
    public static List<StationKind> stationKinds(Identifier role) {
        if(FARMER.getValue().equals(role))
            return List.of(StationKind.FIELD);
        if(WOODCUTTER.getValue().equals(role))
            return List.of(StationKind.WOOD_PILE);
        NpcRole found = get(role);
        return found == null || found.routine().isEmpty() ? List.of() : found.routine().get().getStations();
    }

    // The markers that stand for a role's workplace, for the roles that have no block
    public static List<MarkerKind> stationMarkers(Identifier role) {
        if(STABLES_MASTER.getValue().equals(role))
            return List.of(MarkerKind.SPAWN_ANIMAL);
        if(MINER.getValue().equals(role))
            return List.of(MarkerKind.MINE_FACE);
        if(GATE_GUARD.getValue().equals(role))
            return List.of(MarkerKind.GUARD_POST, MarkerKind.ENTRANCE);
        if(SENTRY.getValue().equals(role))
            return List.of(MarkerKind.LOOKOUT);
        if(isLord(role))
            return List.of(MarkerKind.LORD_SEAT);
        return List.of();
    }

    // What a bed tagged with this role works at: the nearest station of its kinds, or else the nearest of its markers
    public static @Nullable BlockPos stationFor(Identifier role, BlockPos bed, List<StructureFeatureData.Station> stations, List<StructureFeatureData.Marker> markers) {
        List<StationKind> kinds = stationKinds(role);
        List<MarkerKind> markerKinds = stationMarkers(role);
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

    private static RegistryKey<NpcRole> register(String name, NpcRole role) {
        RegistryKey<NpcRole> key = RegistryKey.of(DynamicRegistriesME.NPC_ROLE, MiddleEarth.id(name));
        ROLES.put(key, role);
        return key;
    }

    // Most of the day at the trade, with time left to sit down
    private static NpcRole trade(StationKind... stations) {
        return NpcRole.named(RoutineData.create(Period.DAY).timid().working(stations).add(Activity.WORK, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1));
    }

    // Keeps the name of the type, as a Gondor Knight on duty is still a knight
    private static NpcRole duty(RoutineData routine) {
        return new NpcRole(Optional.of(routine), false, Optional.empty());
    }

    private static RegistryKey<NpcRole> registerForNests(List<String> nestNames, String name, NpcRole role) {
        RegistryKey<NpcRole> key = register(name, role);
        nestNames.forEach(nestName -> NEST_ROLES.put(nestName, key));
        return key;
    }

    private static RegistryKey<NpcRole> registerForNest(String name, String nestName, NpcRole role) {
        RegistryKey<NpcRole> key = register(name, role);
        NEST_ROLES.put(nestName, key);
        return key;
    }
}
