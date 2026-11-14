package net.sevenstars.middleearth.resources.datas.npc_roles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;

import java.util.Optional;

// The job an npc is given by its nest or its party, on top of what its type is. A baker and a smith are the same peasant
// with different days. Without a routine of its own the role only gives the npc its name
public record NpcRole(Optional<RoutineData> routine, boolean replacesName) {
    public static final Codec<NpcRole> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RoutineData.CODEC.optionalFieldOf("routine").forGetter(NpcRole::routine),
            Codec.BOOL.optionalFieldOf("replaces_name", false).forGetter(NpcRole::replacesName)
    ).apply(instance, NpcRole::new));

    // Named for its trade: a Shire Baker, where the type would say Shire Peasant
    public static NpcRole named() {
        return new NpcRole(Optional.empty(), true);
    }

    public static NpcRole named(RoutineData routine) {
        return new NpcRole(Optional.of(routine), true);
    }
}
