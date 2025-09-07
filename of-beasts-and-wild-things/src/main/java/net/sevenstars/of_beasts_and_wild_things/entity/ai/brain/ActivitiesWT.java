package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public class ActivitiesWT {


    private static Activity register(String id) {
        return Registry.register(Registries.ACTIVITY, id, new Activity(id));
    }

    public static void registerModActivities() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering ModActivities for " + OfBeastsAndWildThings.MOD_ID);
    }
}
