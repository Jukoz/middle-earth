package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.sensor.SwanAttackablesSensor;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanBrain;

import java.util.function.Supplier;

public class SensorsWT {

    public static final SensorType<SwanAttackablesSensor> SWAN_ATTACKABLES = register("swan_attackables", SwanAttackablesSensor::new);
    public static final SensorType<TemptationsSensor> SWAN_TEMPTATIONS = register("swan_temptations", () -> new TemptationsSensor(SwanBrain.getTemptItemPredicate()));

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, Identifier.of(OfBeastsAndWildThings.MOD_ID, id), new SensorType<>(factory));
    }

    public static void registerModSensors() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Sensors for " + OfBeastsAndWildThings.MOD_ID);
    }
}
