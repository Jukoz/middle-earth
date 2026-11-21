package net.sevenstars.middleearth.features.npcs.jobs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.StringIdentifiable;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.classes.data.RoutineData;
import net.sevenstars.middleearth.features.npcs.classes.data.StationKind;

import java.util.List;
import java.util.Optional;

// Comes from the nest or party, on top of the class. A job with no routine only gives a name.
// A job named for its trade puts that trade's tool in hand, or nothing if the class's gear is another trade's.
public record NpcJob(Optional<RoutineData> routine, boolean replacesName, Optional<Item> tool, Optional<Item> otherTool, Optional<Item> sidearm,
                      boolean unarmoured, List<StationKind> stationKinds, List<MarkerKind> stationMarkers, Category category) {
    // Trades for common folk, duties for fighters
    public enum Category implements StringIdentifiable {
        TRADE("trade"),
        DUTY("duty");

        public static final Codec<Category> CODEC = StringIdentifiable.createCodec(Category::values);
        private final String name;

        Category(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return name;
        }
    }

    public static final Codec<NpcJob> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RoutineData.CODEC.optionalFieldOf("routine").forGetter(NpcJob::routine),
            Codec.BOOL.optionalFieldOf("replaces_name", false).forGetter(NpcJob::replacesName),
            Registries.ITEM.getCodec().optionalFieldOf("tool").forGetter(NpcJob::tool),
            Registries.ITEM.getCodec().optionalFieldOf("other_tool").forGetter(NpcJob::otherTool),
            Registries.ITEM.getCodec().optionalFieldOf("sidearm").forGetter(NpcJob::sidearm),
            Codec.BOOL.optionalFieldOf("unarmoured", false).forGetter(NpcJob::unarmoured),
            StationKind.CODEC.listOf().optionalFieldOf("station_kinds", List.of()).forGetter(NpcJob::stationKinds),
            MarkerKind.CODEC.listOf().optionalFieldOf("station_markers", List.of()).forGetter(NpcJob::stationMarkers),
            // Absent means a job that replaces the name is a trade
            Category.CODEC.optionalFieldOf("category").forGetter(job -> Optional.of(job.category))
    ).apply(instance, (routine, replacesName, tool, otherTool, sidearm, unarmoured, stationKinds, stationMarkers, category) ->
            new NpcJob(routine, replacesName, tool, otherTool, sidearm, unarmoured, stationKinds, stationMarkers, category.orElse(replacesName ? Category.TRADE : Category.DUTY))));

    public NpcJob(Optional<RoutineData> routine, boolean replacesName, Optional<Item> tool) {
        this(routine, replacesName, tool, Optional.empty(), Optional.empty(), false, List.of(), List.of(), replacesName ? Category.TRADE : Category.DUTY);
    }

    public static NpcJob named() {
        return new NpcJob(Optional.empty(), true, Optional.empty());
    }

    public static NpcJob named(RoutineData routine) {
        return new NpcJob(Optional.of(routine), true, Optional.empty());
    }

    public boolean isTrade() {
        return category == Category.TRADE;
    }

    // Own station kinds, else whatever the routine works at
    public List<StationKind> stations() {
        if(!stationKinds.isEmpty())
            return stationKinds;
        return routine.map(RoutineData::getStations).orElse(List.of());
    }

    public NpcJob holding(Item tool) {
        return new NpcJob(routine, replacesName, Optional.of(tool), otherTool, sidearm, unarmoured, stationKinds, stationMarkers, category);
    }

    public NpcJob orHolding(Item other) {
        return new NpcJob(routine, replacesName, tool, Optional.of(other), sidearm, unarmoured, stationKinds, stationMarkers, category);
    }

    public NpcJob withSidearm(Item weapon) {
        return new NpcJob(routine, replacesName, tool, otherTool, Optional.of(weapon), unarmoured, stationKinds, stationMarkers, category);
    }

    public NpcJob withoutArmour() {
        return new NpcJob(routine, replacesName, tool, otherTool, sidearm, true, stationKinds, stationMarkers, category);
    }

    public NpcJob atStations(StationKind... kinds) {
        return new NpcJob(routine, replacesName, tool, otherTool, sidearm, unarmoured, List.of(kinds), stationMarkers, category);
    }

    public NpcJob atMarkers(MarkerKind... kinds) {
        return new NpcJob(routine, replacesName, tool, otherTool, sidearm, unarmoured, stationKinds, List.of(kinds), category);
    }
}
