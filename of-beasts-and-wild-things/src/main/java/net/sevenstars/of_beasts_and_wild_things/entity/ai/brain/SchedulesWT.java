package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.ScheduleBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public class SchedulesWT {

    protected static ScheduleBuilder register(String id) {
        Schedule schedule = Registry.register(Registries.SCHEDULE, id, new Schedule());
        return new ScheduleBuilder(schedule);
    }

    public static void registerModSchedules() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Schedules for " + OfBeastsAndWildThings.MOD_ID);
    }
}
