package net.sevenstars.middleearth.registries.content.npcroles;

import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_roles.NpcRole;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Activity;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData.Period;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class NpcRoles {
    private static final Map<RegistryKey<NpcRole>, NpcRole> ROLES = new LinkedHashMap<>();
    // The last word of a nest's name says who lives there
    private static final Map<String, RegistryKey<NpcRole>> NEST_ROLES = new LinkedHashMap<>();

    // Stands about the square with the beasts once they are brought in
    private static final RoutineData HERDING = RoutineData.create(Period.DAY).timid().add(Activity.MEET, 1);

    // [HERDERS]
    public static final RegistryKey<NpcRole> SWINEHERD = register("swineherd", NpcRole.named(HERDING));
    public static final RegistryKey<NpcRole> HERDSMAN = register("herdsman", NpcRole.named(HERDING));
    public static final RegistryKey<NpcRole> DROVER = register("drover", NpcRole.named(HERDING));

    // [TRADES]
    public static final RegistryKey<NpcRole> FARMER = registerForNest("farmer", "farmer");
    public static final RegistryKey<NpcRole> BAKER = registerForNest("baker", "baker");
    public static final RegistryKey<NpcRole> SMITH = registerForNest("smith", "smith");
    public static final RegistryKey<NpcRole> COOK = registerForNest("cook", "cook");
    public static final RegistryKey<NpcRole> POTTER = registerForNest("potter", "potter");
    public static final RegistryKey<NpcRole> WOODCUTTER = registerForNest("woodcutter", "lumber");
    public static final RegistryKey<NpcRole> BEEKEEPER = registerForNest("beekeeper", "beekeeper");
    public static final RegistryKey<NpcRole> INNKEEPER = registerForNest("innkeeper", "innkeeper");
    public static final RegistryKey<NpcRole> STABLES_MASTER = registerForNest("stables_master", "stables_master");

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

    private static RegistryKey<NpcRole> registerForNest(String name, String nestName) {
        RegistryKey<NpcRole> key = register(name, NpcRole.named());
        NEST_ROLES.put(nestName, key);
        return key;
    }
}
