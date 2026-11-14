package net.sevenstars.api.registries.brain;

import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.world.attribute.EnvironmentAttribute;
import net.minecraft.world.attribute.EnvironmentAttributeTypes;
import net.sevenstars.api.SevenStarsApi;

public class SchedulesAPI {
    public static final EnvironmentAttribute<Activity> DEFAULT_SLEEP = register("default_sleep", Activity.IDLE);
    public static final EnvironmentAttribute<Activity> DEFAULT_BABY  = register("default_baby", ActivitiesAPI.BABY_IDLE);
    public static final EnvironmentAttribute<Activity> EMPTY = register("empty", Activity.IDLE);

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
        SevenStarsApi.logRegistryMsg("Schedules");
    }
}
