package net.sevenstars.middleearth.entity.ai.brain;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.api.SevenStarsApi;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.api.registries.RegistrationBridge;

public class ActivitiesME {
    public static final Activity TAMED = register("tamed");

    private static Activity register(String id) {
        return RegistrationBridge.register(BuiltInRegistries.ACTIVITY, MiddleEarth.of(id), new Activity(id));
    }

    public static void registerModActivities() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering ModActivities for " + MiddleEarth.MOD_ID);
    }
}
