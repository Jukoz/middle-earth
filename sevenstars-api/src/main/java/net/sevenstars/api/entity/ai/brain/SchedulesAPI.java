package net.sevenstars.api.entity.ai.brain;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.schedule.Schedule;
import net.minecraft.world.entity.schedule.ScheduleBuilder;
import net.sevenstars.api.SevenStarsApi;
import net.sevenstars.api.registries.RegistrationBridge;

public class SchedulesAPI {
    public static final Schedule DEFAULT_SLEEP = register("default_sleep")
            .changeActivityAt(10, Activity.IDLE)
            .changeActivityAt(12000, Activity.REST)
            .build();

    public static final Schedule DEFAULT_BABY = register("default_baby")
            .changeActivityAt(10, ActivitiesAPI.BABY_IDLE)
            .changeActivityAt(12000, ActivitiesAPI.BABY_REST)
            .build();

    protected static ScheduleBuilder register(String id) {
        Schedule schedule = RegistrationBridge.register(
                BuiltInRegistries.SCHEDULE,
                ResourceLocation.fromNamespaceAndPath(SevenStarsApi.MOD_ID, id),
                new Schedule()
        );
        return new ScheduleBuilder(schedule);
    }

    public static void registerModSchedules() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering Mod Schedules for " + SevenStarsApi.MOD_ID);
    }
}
