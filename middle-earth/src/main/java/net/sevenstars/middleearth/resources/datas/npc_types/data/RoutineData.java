package net.sevenstars.middleearth.resources.datas.npc_types.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.random.Random;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RoutineData {
    public static class Fields {
        public static final String PERIOD = "period";
        public static final String ACTIVITIES = "activities";
        public static final String ACTIVITY = "activity";
        public static final String WEIGHT = "weight";
        public static final String FORAGE = "forage";
        public static final String TIMID = "timid";
        public static final String BOUNDS = "bounds";
        public static final String STATIONS = "stations";
    }

    public enum Activity implements StringIdentifiable {
        FISH("fish"),
        HUNT("hunt"),
        FORAGE("forage"),
        PATROL("patrol"),
        // Calling on another household of the place
        VISIT("visit"),
        // Sitting down at a table, with whoever else is there
        TABLE("table"),
        // At the forge, the oven or whatever else the npc's trade is plied at
        WORK("work"),
        // Standing guard at the door the npc lives by
        POST("post"),
        // Going out with one of the place's own who has work beyond its walls
        ESCORT("escort");

        public static final Codec<Activity> CODEC = StringIdentifiable.createCodec(Activity::values);
        private final String name;

        Activity(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return name;
        }
    }

    public enum Period implements StringIdentifiable {
        DAY("day"),
        NIGHT("night"),
        // Half of them keep the day, the other half the night
        SHIFTS("shifts"),
        ALWAYS("always");

        public static final Codec<Period> CODEC = StringIdentifiable.createCodec(Period::values);
        // Dusk and dawn, so that nobody sets out as the light changes
        private static final int DAY_END = 11500;
        private static final int NIGHT_START = 13000;
        private static final int NIGHT_END = 22500;
        private static final int BED_TIME = 12500;
        private static final int RISING_TIME = 23500;
        private static final int DAY_REST_START = 500;
        private final String name;

        Period(String name) {
            this.name = name;
        }

        public boolean isActive(long timeOfDay, boolean nightShift) {
            long time = timeOfDay % 24000;
            return switch (this) {
                case DAY -> time < DAY_END;
                case NIGHT -> time >= NIGHT_START && time < NIGHT_END;
                case SHIFTS -> (nightShift ? NIGHT : DAY).isActive(timeOfDay, nightShift);
                case ALWAYS -> true;
            };
        }

        // The hours spent in bed, which leave some time between work and sleep
        public boolean isResting(long timeOfDay, boolean nightShift) {
            long time = timeOfDay % 24000;
            return switch (this) {
                case DAY -> time >= BED_TIME && time < RISING_TIME;
                case NIGHT -> time >= DAY_REST_START && time < DAY_END;
                case SHIFTS -> (nightShift ? NIGHT : DAY).isResting(timeOfDay, nightShift);
                case ALWAYS -> false;
            };
        }

        @Override
        public String asString() {
            return name;
        }
    }

    public record Entry(Activity activity, int weight) {
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Activity.CODEC.fieldOf(Fields.ACTIVITY).forGetter(Entry::activity),
                Codec.INT.fieldOf(Fields.WEIGHT).forGetter(Entry::weight)
        ).apply(instance, Entry::new));
    }

    public static final Codec<RoutineData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Period.CODEC.fieldOf(Fields.PERIOD).forGetter(RoutineData::getPeriod),
            Codec.list(Entry.CODEC).fieldOf(Fields.ACTIVITIES).forGetter(RoutineData::getEntries),
            Codec.list(Registries.ITEM.getCodec()).optionalFieldOf(Fields.FORAGE, List.of()).forGetter(RoutineData::getForage),
            Codec.BOOL.optionalFieldOf(Fields.TIMID, false).forGetter(RoutineData::isTimid),
            Codec.BOOL.optionalFieldOf(Fields.BOUNDS, false).forGetter(RoutineData::keepsBounds),
            Codec.list(StationKind.CODEC).optionalFieldOf(Fields.STATIONS, List.of()).forGetter(RoutineData::getStations)
    ).apply(instance, RoutineData::new));

    private final Period period;
    private final List<Entry> entries;
    private final List<Item> forage;
    private boolean timid;
    private boolean bounds;
    private final List<StationKind> stations;

    private RoutineData(Period period, List<Entry> entries, List<Item> forage, boolean timid, boolean bounds, List<StationKind> stations) {
        this.stations = new ArrayList<>(stations);
        this.timid = timid;
        this.bounds = bounds;
        this.period = period;
        this.entries = new ArrayList<>(entries);
        this.forage = new ArrayList<>(forage);
    }

    public static RoutineData create(Period period) {
        return new RoutineData(period, List.of(), List.of(), false, false, List.of());
    }

    // What is carried home from foraging, mushrooms and berries when left out
    public RoutineData foraging(Item... items) {
        this.forage.addAll(List.of(items));
        return this;
    }

    // Runs home from a fight and leaves it to the guards
    public RoutineData timid() {
        this.timid = true;
        return this;
    }

    // Travels on for good inside the land it was born in, and seeks no settlement out
    public RoutineData keepingBounds() {
        this.bounds = true;
        return this;
    }

    public boolean keepsBounds() {
        return bounds;
    }

    // Where the work of this routine is done
    public RoutineData working(StationKind... kinds) {
        this.stations.addAll(List.of(kinds));
        return this;
    }

    public List<StationKind> getStations() {
        return stations;
    }

    public boolean isTimid() {
        return timid;
    }

    public List<Item> getForage() {
        return forage;
    }

    public RoutineData add(Activity activity, int weight) {
        this.entries.add(new Entry(activity, weight));
        return this;
    }

    public Period getPeriod() {
        return period;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public @Nullable Activity pick(Random random, Predicate<Activity> possible) {
        int total = 0;
        for(Entry entry : entries) {
            if(possible.test(entry.activity()))
                total += entry.weight();
        }
        if(total <= 0)
            return null;
        int roll = random.nextInt(total);
        for(Entry entry : entries) {
            if(!possible.test(entry.activity()))
                continue;
            roll -= entry.weight();
            if(roll < 0)
                return entry.activity();
        }
        return null;
    }
}
