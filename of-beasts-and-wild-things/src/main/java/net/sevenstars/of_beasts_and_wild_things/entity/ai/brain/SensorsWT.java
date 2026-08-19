package net.sevenstars.of_beasts_and_wild_things.entity.ai.brain;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.ai.sensing.TemptingSensor;
import net.sevenstars.of_beasts_and_wild_things.OfBeastsAndWildThings;
import net.sevenstars.of_beasts_and_wild_things.entity.ai.brain.sensor.SwanAttackablesSensor;
import net.sevenstars.of_beasts_and_wild_things.entity.swan.SwanBrain;
import net.sevenstars.api.registries.RegistrationBridge;

import java.util.function.Supplier;

public class SensorsWT {

    public static final SensorType<SwanAttackablesSensor> SWAN_ATTACKABLES = register("swan_attackables", SwanAttackablesSensor::new);
    public static final SensorType<TemptingSensor> SWAN_TEMPTATIONS = register("swan_temptations", () -> new TemptingSensor(SwanBrain.getTemptItemPredicate()));

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return RegistrationBridge.register(BuiltInRegistries.SENSOR_TYPE, OfBeastsAndWildThings.of(id), new SensorType<>(factory));
    }

    public static void registerModSensors() {
        OfBeastsAndWildThings.LOGGER.logDebugMsg("Registering Mod Sensors for " + OfBeastsAndWildThings.MOD_ID);
    }
}
