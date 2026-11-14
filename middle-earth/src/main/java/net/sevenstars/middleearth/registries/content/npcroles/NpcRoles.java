package net.sevenstars.middleearth.registries.content.npcroles;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRoutines;
import net.sevenstars.middleearth.resources.datas.npc_roles.NpcRole;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Activity;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Period;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class NpcRoles {
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
    public static final RegistryKey<NpcRole> FARMER = registerForNest("farmer", "farmer", NpcRole.named());
    public static final RegistryKey<NpcRole> BAKER = registerForNest("baker", "baker", trade(StationKind.OVEN));
    public static final RegistryKey<NpcRole> SMITH = registerForNest("smith", "smith", trade(StationKind.FORGE, StationKind.GRINDSTONE));
    public static final RegistryKey<NpcRole> COOK = registerForNest("cook", "cook", trade(StationKind.HEARTH, StationKind.OVEN));
    public static final RegistryKey<NpcRole> POTTER = registerForNest("potter", "potter", trade(StationKind.HEARTH));
    public static final RegistryKey<NpcRole> WOODCUTTER = registerForNest("woodcutter", "lumber", trade(StationKind.WOOD_PILE));
    public static final RegistryKey<NpcRole> BEEKEEPER = registerForNest("beekeeper", "beekeeper", trade(StationKind.HIVE));
    public static final RegistryKey<NpcRole> INNKEEPER = registerForNest("innkeeper", "innkeeper", trade(StationKind.HEARTH, StationKind.OVEN));
    public static final RegistryKey<NpcRole> STABLES_MASTER = registerForNest("stables_master", "stables_master", trade(StationKind.HAY));

    // [WARRIORS]
    // Keeps its own name: a Gondor Soldier on guard is still that
    public static final RegistryKey<NpcRole> GUARD = registerForNest("guard", "guard", new NpcRole(Optional.of(NpcRoutines.GUARD), false));

    public static void bootstrap(Registerable<NpcRole> context) {
        ROLES.forEach(context::register);
    }

    public static Iterable<RegistryKey<NpcRole>> all() {
        return ROLES.keySet();
    }

    public static Optional<Identifier> forNest(Identifier nestId) {
        String path = nestId.getPath();
        @Nullable RegistryKey<NpcRole> role = NEST_ROLES.get(path.substring(path.lastIndexOf('.') + 1));
        return Optional.ofNullable(role).map(RegistryKey::getValue);
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
