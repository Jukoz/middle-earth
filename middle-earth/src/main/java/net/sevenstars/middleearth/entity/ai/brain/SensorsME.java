package net.sevenstars.middleearth.entity.ai.brain;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.ai.brain.sensor.CaveTrollAttackablesSensor;
import net.sevenstars.middleearth.entity.ai.brain.sensor.NpcAttackablesSensor;

import java.util.function.Supplier;

public class SensorsME {
    public static final SensorType<CaveTrollAttackablesSensor> CAVE_TROLL_ATTACKABLES = register("cave_troll_attackables", CaveTrollAttackablesSensor::new);
    public static final SensorType<NpcAttackablesSensor> NPC_ATTACKABLES = register("npc_attackables", NpcAttackablesSensor::new);

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, Identifier.of(MiddleEarth.MOD_ID, id), new SensorType<>(factory));
    }

    public static void registerModSensors() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Sensors for " + MiddleEarth.MOD_ID);
    }
}
