package net.sevenstars.api.registries.brain;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.world.attribute.timeline.Timeline;
import net.sevenstars.api.SevenStarsApi;

public class TimelinesAPI {
    /**
     *             .withActivity(10, Activity.IDLE) <Br>
     *             .withActivity(12000, Activity.REST)
     */
    public static final RegistryKey<Timeline> DEFAULT_SLEEP = of("default_sleep");
    /**
     *            .withActivity(10, ActivitiesAPI.BABY_IDLE)<Br>
     *             .withActivity(12000, ActivitiesAPI.BABY_REST)
     */
    public static final RegistryKey<Timeline> DEFAULT_BABY= of("default_baby");

    private static RegistryKey<Timeline> of(String idPath) {
        return RegistryKey.of(RegistryKeys.TIMELINE, SevenStarsApi.id(idPath));
    }

    public static void register() {
        SevenStarsApi.logRegistryMessage("Timelines");
    }
}
