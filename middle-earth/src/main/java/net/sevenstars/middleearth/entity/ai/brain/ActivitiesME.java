package net.sevenstars.middleearth.entity.ai.brain;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.api.SevenStarsApi;
import net.sevenstars.middleearth.MiddleEarth;

public class ActivitiesME {
    public static final Activity TAMED = register("tamed");

    private static Activity register(String id) {
        return Registry.register(Registries.ACTIVITY, id, new Activity(id));
    }

    public static void registerModActivities() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering ModActivities for " + MiddleEarth.MOD_ID);
    }
}
