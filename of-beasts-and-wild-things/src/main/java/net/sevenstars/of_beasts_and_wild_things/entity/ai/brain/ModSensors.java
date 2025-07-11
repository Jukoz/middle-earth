package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.sensor.SwanAttackablesSensor;

import java.util.function.Supplier;

public class ModSensors {

    public static final SensorType<SwanAttackablesSensor> SWAN_ATTACKABLES = register("swan_attackables", SwanAttackablesSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, Identifier.of(OfBeastsAndWildThings.MOD_ID, id), new SensorType<>(factory));
    }

    public static void registerModSensors() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Sensors for " + OfBeastsAndWildThings.MOD_ID);
    }
}
