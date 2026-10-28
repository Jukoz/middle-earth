package net.sevenstars.api.registries.brain;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.api.SevenStarsApi;

import java.util.function.Supplier;

public class SensorsAPI {

    private static <U extends Sensor<?>> SensorType<U> register(String idPath, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, SevenStarsApi.id(idPath), new SensorType<>(factory));
    }

    public static void register() {
        SevenStarsApi.logRegistryMsg("Sensors");
    }
}
