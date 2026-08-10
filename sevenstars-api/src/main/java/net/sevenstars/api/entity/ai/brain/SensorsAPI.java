package net.sevenstars.api.entity.ai.brain;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.api.SevenStarsApi;

import java.util.function.Supplier;

public class SensorsAPI {

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, Identifier.of(SevenStarsApi.MOD_ID, id), new SensorType<>(factory));
    }

    public static void registerModSensors() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering Mod Sensors for " + SevenStarsApi.MOD_ID);
    }
}
