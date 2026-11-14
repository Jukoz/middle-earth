package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeTypes;
import net.sevenstars.api.SevenStarsApi;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;

public class SchedulesWT {
    private static EnvironmentAttribute<Activity> register(String id, Activity defaultValue) {
        return Registry.register(
                Registries.ENVIRONMENTAL_ATTRIBUTE,
                SevenStarsApi.id(id),
                EnvironmentAttribute.builder(EnvironmentAttributeTypes.ACTIVITY)
                        .defaultValue(defaultValue)
                        .global()
                        .build()
        );
    }

    public static void register() {
        OfBeastsAndWildThings.logRegistryMsg("Schedules");
    }
}
