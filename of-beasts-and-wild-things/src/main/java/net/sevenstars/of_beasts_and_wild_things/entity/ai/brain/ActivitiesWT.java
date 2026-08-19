package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.schedule.Activity;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.api.registries.RegistrationBridge;

public class ActivitiesWT {


    private static Activity register(String id) {
        return RegistrationBridge.register(BuiltInRegistries.ACTIVITY, OfBeastsAndWildThings.of(id), new Activity(id));
    }

    public static void registerModActivities() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering ModActivities for " + OfBeastsAndWildThings.MOD_ID);
    }
}
