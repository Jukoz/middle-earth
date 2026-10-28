package net.sevenstars.of_beasts_and_wild_things.world.gen;

import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public class WorldGenerationWT {
    public static void register() {
        OfBeastsAndWildThings.logRegistryMsg("World Generation");

        EntitySpawnsWT.addSpawns();
    }
}
