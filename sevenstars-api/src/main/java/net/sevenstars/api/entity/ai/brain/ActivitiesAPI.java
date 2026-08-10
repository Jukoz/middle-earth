package net.sevenstars.api.entity.ai.brain;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.api.SevenStarsApi;

public class ActivitiesAPI {
    public static final Activity BABY_IDLE = register("baby_idle");
    public static final Activity BABY_REST = register("baby_rest");

    private static Activity register(String id) {
        return Registry.register(Registries.ACTIVITY, id, new Activity(id));
    }

    public static void registerModActivities() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering ModActivities for " + SevenStarsApi.MOD_ID);
    }
}
