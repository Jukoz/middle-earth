package net.sevenstars.middleearth.resources.datas.npc_jobs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.RoutineData;

import java.util.Optional;

// Comes from the nest or party, on top of the class. A job with no routine only gives a name.
// A job named for its trade puts that trade's tool in hand, or nothing if the class's gear is another trade's.
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
