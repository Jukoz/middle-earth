package net.sevenstars.middleearth.registries.content.npcroles;

import net.minecraft.item.Items;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.item.ToolItemsME;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.races.RaceRegistry;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRoutines;
import net.sevenstars.middleearth.resources.datas.npc_roles.NpcRole;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Activity;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Period;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

public class NpcRoles {
    private static final int HOUSEHOLD_RANGE = 16;
    private static final int HOUSEHOLD_SHORE_RANGE = 24;
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
    public static final RegistryKey<NpcRole> WOODCUTTER = registerForNest("woodcutter", "lumber", NpcRole.named(RoutineData.create(Period.DAY).timid().working(StationKind.WOOD_PILE)
            .add(Activity.CHOP, 3).add(Activity.WORK, 2).add(Activity.TABLE, 1)).holding(Items.IRON_AXE));
    public static final RegistryKey<NpcRole> FISHER = registerForNest("fisher", "fisher",
            NpcRole.named(RoutineData.create(Period.DAY).timid().add(Activity.FISH, 4).add(Activity.TABLE, 1).add(Activity.VISIT, 1)).holding(Items.FISHING_ROD));
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
    public static final RegistryKey<NpcRole> STABLES_MASTER = registerForNest("stables_master", "stables_master", trade(StationKind.HAY));

    // [WARRIORS]
    // Keeps its own name: a Gondor Soldier on guard is still that
    public static final RegistryKey<NpcRole> GUARD = registerForNest("guard", "guard", new NpcRole(Optional.of(NpcRoutines.GUARD), false, Optional.empty()));

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

    public static Optional<Identifier> forNest(Identifier nestId) {
        String path = nestId.getPath();
        @Nullable RegistryKey<NpcRole> role = NEST_ROLES.get(path.substring(path.lastIndexOf('.') + 1));
        return Optional.ofNullable(role).map(RegistryKey::getValue);
    }

    // What a household has the means for, which is the work of whoever lives in it. One of a trade to a household, and the
    // orcs and goblins that find nothing to do are labourers, of whom a place has as many as it likes
    public static Optional<Identifier> forHousehold(RoutineSite site, BlockPos nest, NpcType type, Predicate<Identifier> taken) {
        List<RegistryKey<NpcRole>> offered = new ArrayList<>();
        if(site.pickStation(List.of(StationKind.FIELD), nest, HOUSEHOLD_RANGE) != null)
            offered.add(FARMER);
        if(site.pickStation(List.of(StationKind.WOOD_PILE), nest, HOUSEHOLD_RANGE) != null)
            offered.add(WOODCUTTER);
        if(site.pickStation(List.of(StationKind.FORGE, StationKind.GRINDSTONE), nest, HOUSEHOLD_RANGE) != null)
            offered.add(SMITH);
        if(site.pickStation(List.of(StationKind.OVEN), nest, HOUSEHOLD_RANGE) != null)
            offered.add(BAKER);
        if(site.pickStation(List.of(StationKind.HIVE), nest, HOUSEHOLD_RANGE) != null)
            offered.add(BEEKEEPER);
        if(site.pickStation(List.of(StationKind.HAY), nest, HOUSEHOLD_RANGE) != null)
            offered.add(STABLES_MASTER);
        // The shores are only known once the place has been looked over
        if(site.isReady() && site.getShoreStands().stream().anyMatch(stand -> stand.isWithinDistance(nest, HOUSEHOLD_SHORE_RANGE)))
            offered.add(FISHER);
        for(RegistryKey<NpcRole> role : offered) {
            if(!taken.test(role.getValue()))
                return Optional.of(role.getValue());
        }
        Identifier race = type.getRace();
        boolean orcish = race.equals(RaceRegistry.ORC.getValue()) || race.equals(RaceRegistry.URUK.getValue())
                || race.equals(RaceRegistry.GOBLIN.getValue()) || race.equals(RaceRegistry.SNAGA.getValue());
        return orcish ? Optional.of(LABOURER.getValue()) : Optional.empty();
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

    private static RegistryKey<NpcRole> registerForNest(String name, String nestName, NpcRole role) {
        RegistryKey<NpcRole> key = register(name, role);
        NEST_ROLES.put(nestName, key);
        return key;
    }
}
