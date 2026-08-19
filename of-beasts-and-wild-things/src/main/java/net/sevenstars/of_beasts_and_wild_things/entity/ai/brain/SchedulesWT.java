package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.api.registries.RegistrationBridge;

public class SchedulesWT {

    protected static ScheduleBuilder register(String id) {
        Schedule schedule = RegistrationBridge.register(BuiltInRegistries.SCHEDULE, OfBeastsAndWildThings.of(id), new Schedule());
        return new ScheduleBuilder(schedule);
    }

    public static void registerModSchedules() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Schedules for " + OfBeastsAndWildThings.MOD_ID);
    }
}
