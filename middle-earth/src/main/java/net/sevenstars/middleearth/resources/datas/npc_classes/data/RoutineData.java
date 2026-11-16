package net.sevenstars.middleearth.resources.datas.npc_classes.data;

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
        // Visits another household in the place
        VISIT("visit"),
        // Sits at a table with anyone already there
        TABLE("table"),
        // Works at the forge, the oven or whatever else the npc's trade uses
        WORK("work"),
        // Tills the place's fields, sows them and harvests what's ripe
        FARM("farm"),
        // Stands guard at the nearest free entrance or guard post marker (lookout for a sentry), or at its nest
        POST("post"),
        // Goes out with a resident who has work beyond the walls
        ESCORT("escort"),
        // Works at a tree without felling it
        CHOP("chop"),
        // Works the rock at a face the builders marked
        MINE("mine"),
        // A lord sits at its seat where visitors can see it
        COURT("court"),
        // A lord goes round the posts of its guards
        INSPECT("inspect"),
        // A bodyguard stays by its lord
        FOLLOW("follow");

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
        // Half of them work the day, the other half the night
        SHIFTS("shifts"),
        ALWAYS("always");

        public static final Codec<Period> CODEC = StringIdentifiable.createCodec(Period::values);
        // In ticks
        private static final int DAY_LENGTH = 24000;
        // Gaps at dusk and dawn so nobody sets out as the light changes
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
            long time = timeOfDay % DAY_LENGTH;
            return switch (this) {
                case DAY -> time < DAY_END;
                case NIGHT -> time >= NIGHT_START && time < NIGHT_END;
                case SHIFTS -> (nightShift ? NIGHT : DAY).isActive(timeOfDay, nightShift);
                case ALWAYS -> true;
            };
        }

        // The hours spent in bed. They leave some time between work and sleep
        public boolean isResting(long timeOfDay, boolean nightShift) {
            long time = timeOfDay % DAY_LENGTH;
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
    private final boolean timid;
    private final boolean bounds;
    private final List<StationKind> stations;

    private RoutineData(Period period, List<Entry> entries, List<Item> forage, boolean timid, boolean bounds, List<StationKind> stations) {
        this.period = period;
        this.entries = List.copyOf(entries);
        this.forage = List.copyOf(forage);
        this.timid = timid;
        this.bounds = bounds;
        this.stations = List.copyOf(stations);
    }

    public static RoutineData create(Period period) {
        return new RoutineData(period, List.of(), List.of(), false, false, List.of());
    }

    // Each of these returns a new routine, because the ones in NpcRoutines are shared by many classes
    public RoutineData add(Activity activity, int weight) {
        return new RoutineData(period, appended(entries, List.of(new Entry(activity, weight))), forage, timid, bounds, stations);
    }

    // Items carried home from foraging. If left out, what grows in the surrounding land is used
    public RoutineData foraging(Item... items) {
        return new RoutineData(period, entries, appended(forage, List.of(items)), timid, bounds, stations);
    }

    // Runs home from a fight and leaves it to the guards
    public RoutineData timid() {
        return new RoutineData(period, entries, forage, true, bounds, stations);
    }

    // Moves on for good within the land it was born in and doesn't seek out a settlement
    public RoutineData keepingBounds() {
        return new RoutineData(period, entries, forage, timid, true, stations);
    }

    // The stations where this routine's work is done
    public RoutineData working(StationKind... kinds) {
        return new RoutineData(period, entries, forage, timid, bounds, appended(stations, List.of(kinds)));
    }

    private static <T> List<T> appended(List<T> list, List<T> more) {
        List<T> all = new ArrayList<>(list);
        all.addAll(more);
        return all;
    }

    public Period getPeriod() {
        return period;
    }

    public List<Entry> getEntries() {
        return entries;
    }

    public List<Item> getForage() {
        return forage;
    }

    public boolean isTimid() {
        return timid;
    }

    public boolean keepsBounds() {
        return bounds;
    }

    public List<StationKind> getStations() {
        return stations;
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
