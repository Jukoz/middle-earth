package net.sevenstars.middleearth.features.npcs.ai;

import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;

public class NpcSensors {
    static final SensorType<FleeSensor> FLEE = Registry.register(Registries.SENSOR_TYPE, Identifier.of(MiddleEarth.MOD_ID, "npc_flee"), new SensorType<>(FleeSensor::new));

    // Has to run during mod initialisation, while the registry is open
    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering npc sensors for " + MiddleEarth.MOD_ID);
    }
}
