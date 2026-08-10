package net.sevenstars.api.entity.ai.brain;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Schedule;
import net.minecraft.entity.ai.brain.ScheduleBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.api.SevenStarsApi;

public class SchedulesAPI {
    public static final Schedule DEFAULT_SLEEP = register("default_sleep")
            .withActivity(10, Activity.IDLE)
            .withActivity(12000, Activity.REST)
            .build();

    public static final Schedule DEFAULT_BABY = register("default_baby")
            .withActivity(10, ActivitiesAPI.BABY_IDLE)
            .withActivity(12000, ActivitiesAPI.BABY_REST)
            .build();

    protected static ScheduleBuilder register(String id) {
        Schedule schedule = Registry.register(Registries.SCHEDULE, id, new Schedule());
        return new ScheduleBuilder(schedule);
    }

    public static void registerModSchedules() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering Mod Schedules for " + SevenStarsApi.MOD_ID);
    }
}
