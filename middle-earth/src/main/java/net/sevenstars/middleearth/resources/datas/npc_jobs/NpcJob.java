package net.sevenstars.middleearth.resources.datas.npc_jobs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;

import java.util.Optional;

// The job an npc gets from its nest or its party, on top of its class. A baker and a smith are the same peasant with
// different days. A job without a routine only gives the npc its name.
// A job named for its trade also puts the trade's tool in the npc's hand, or nothing, because what the class carries
// belongs to another trade, such as a fisherman's rod. A job can offer a second tool to give instead half the time, a
// side arm for the other hand, and can remove whatever armour the class wears
public record NpcJob(Optional<RoutineData> routine, boolean replacesName, Optional<Item> tool, Optional<Item> otherTool, Optional<Item> sidearm,
                      boolean unarmoured) {
    public static final Codec<NpcJob> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RoutineData.CODEC.optionalFieldOf("routine").forGetter(NpcJob::routine),
            Codec.BOOL.optionalFieldOf("replaces_name", false).forGetter(NpcJob::replacesName),
            Registries.ITEM.getCodec().optionalFieldOf("tool").forGetter(NpcJob::tool),
            Registries.ITEM.getCodec().optionalFieldOf("other_tool").forGetter(NpcJob::otherTool),
            Registries.ITEM.getCodec().optionalFieldOf("sidearm").forGetter(NpcJob::sidearm),
            Codec.BOOL.optionalFieldOf("unarmoured", false).forGetter(NpcJob::unarmoured)
    ).apply(instance, NpcJob::new));

    public NpcJob(Optional<RoutineData> routine, boolean replacesName, Optional<Item> tool) {
        this(routine, replacesName, tool, Optional.empty(), Optional.empty(), false);
    }

    // Named for its trade, so a Shire Baker where the class would give Shire Peasant
    public static NpcJob named() {
        return new NpcJob(Optional.empty(), true, Optional.empty());
    }

    public static NpcJob named(RoutineData routine) {
        return new NpcJob(Optional.of(routine), true, Optional.empty());
    }

    public NpcJob holding(Item tool) {
        return new NpcJob(routine, replacesName, Optional.of(tool), otherTool, sidearm, unarmoured);
    }

    public NpcJob orHolding(Item other) {
        return new NpcJob(routine, replacesName, tool, Optional.of(other), sidearm, unarmoured);
    }

    public NpcJob withSidearm(Item weapon) {
        return new NpcJob(routine, replacesName, tool, otherTool, Optional.of(weapon), unarmoured);
    }

    public NpcJob withoutArmour() {
        return new NpcJob(routine, replacesName, tool, otherTool, sidearm, true);
    }
}
