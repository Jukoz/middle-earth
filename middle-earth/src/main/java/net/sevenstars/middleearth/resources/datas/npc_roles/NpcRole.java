package net.sevenstars.middleearth.resources.datas.npc_roles;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.sevenstars.middleearth.resources.datas.npc_types.data.RoutineData;

import java.util.Optional;

// The job an npc is given by its nest or its party, on top of what its type is. A baker and a smith are the same peasant
// with different days. Without a routine of its own the role only gives the npc its name.
// A role named for its trade also puts the trade's tool in the npc's hand, or nothing: what the type carries belongs to
// another trade, such as a fisherman's rod. A role may offer a second tool to be given instead, half the time, a side arm
// for the other hand, and take off what armour the type wears
public record NpcRole(Optional<RoutineData> routine, boolean replacesName, Optional<Item> tool, Optional<Item> otherTool, Optional<Item> sidearm,
                      boolean unarmoured) {
    public static final Codec<NpcRole> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RoutineData.CODEC.optionalFieldOf("routine").forGetter(NpcRole::routine),
            Codec.BOOL.optionalFieldOf("replaces_name", false).forGetter(NpcRole::replacesName),
            Registries.ITEM.getCodec().optionalFieldOf("tool").forGetter(NpcRole::tool),
            Registries.ITEM.getCodec().optionalFieldOf("other_tool").forGetter(NpcRole::otherTool),
            Registries.ITEM.getCodec().optionalFieldOf("sidearm").forGetter(NpcRole::sidearm),
            Codec.BOOL.optionalFieldOf("unarmoured", false).forGetter(NpcRole::unarmoured)
    ).apply(instance, NpcRole::new));

    public NpcRole(Optional<RoutineData> routine, boolean replacesName, Optional<Item> tool) {
        this(routine, replacesName, tool, Optional.empty(), Optional.empty(), false);
    }

    // Named for its trade: a Shire Baker, where the type would say Shire Peasant
    public static NpcRole named() {
        return new NpcRole(Optional.empty(), true, Optional.empty());
    }

    public static NpcRole named(RoutineData routine) {
        return new NpcRole(Optional.of(routine), true, Optional.empty());
    }

    public NpcRole holding(Item tool) {
        return new NpcRole(routine, replacesName, Optional.of(tool), otherTool, sidearm, unarmoured);
    }

    public NpcRole orHolding(Item other) {
        return new NpcRole(routine, replacesName, tool, Optional.of(other), sidearm, unarmoured);
    }

    public NpcRole withSidearm(Item weapon) {
        return new NpcRole(routine, replacesName, tool, otherTool, Optional.of(weapon), unarmoured);
    }

    public NpcRole withoutArmour() {
        return new NpcRole(routine, replacesName, tool, otherTool, sidearm, true);
    }
}
