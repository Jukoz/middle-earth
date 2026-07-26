package net.sevenstars.api;

import net.fabricmc.api.ModInitializer;
import net.sevenstars.api.entity.ai.brain.ActivitiesAPI;
import net.sevenstars.api.entity.ai.brain.MemoryModulesAPI;
import net.sevenstars.api.entity.ai.brain.SchedulesAPI;
import net.sevenstars.api.entity.ai.brain.SensorsAPI;
import net.sevenstars.api.utils.ModLogger;

public class SevenStarsApi implements ModInitializer {
	public static final String MOD_ID = "sevenstars-api";
	public static final String MOD_VERSION = "1.0.0-1.21.8-beta";
	public static final boolean IS_DEBUG = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);
	@Override
	public void onInitialize() {
		SchedulesAPI.registerModSchedules();
		ActivitiesAPI.registerModActivities();
		SensorsAPI.registerModSensors();
		MemoryModulesAPI.registerModMemoryModules();
	}
}
