package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.ScheduleBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public class ModSchedule {
    public static final Schedule SWAN_DEFAULT = register("swan_default")
            .withActivity(10, Activity.IDLE)
            .withActivity(12000, Activity.REST)
            .build();

    public static final Schedule BABY = register("baby")
            .withActivity(10, ModActivity.BABY_IDLE)
            .withActivity(12000, ModActivity.BABY_REST)
            .build();

    protected static ScheduleBuilder register(String id) {
        Schedule schedule = Registry.register(Registries.SCHEDULE, id, new Schedule());
        return new ScheduleBuilder(schedule);
    }

    public static void registerModSchedules() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Schedules for " + OfBeastsAndWildThings.MOD_ID);
    }
}
