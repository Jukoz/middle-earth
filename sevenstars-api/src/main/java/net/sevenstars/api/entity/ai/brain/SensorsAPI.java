package net.sevenstars.api.entity.ai.brain;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.sevenstars.api.SevenStarsApi;
import net.sevenstars.api.registries.RegistrationBridge;

import java.util.function.Supplier;

public class SensorsAPI {

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return RegistrationBridge.register(
                BuiltInRegistries.SENSOR_TYPE,
                ResourceLocation.fromNamespaceAndPath(SevenStarsApi.MOD_ID, id),
                new SensorType<>(factory)
        );
    }

    public static void registerModSensors() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering Mod Sensors for " + SevenStarsApi.MOD_ID);
    }
}
