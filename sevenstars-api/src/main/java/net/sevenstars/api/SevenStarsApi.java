package net.sevenstars.api;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import net.sevenstars.api.registries.brain.ActivitiesAPI;
import net.sevenstars.api.registries.brain.MemoryModulesAPI;
import net.sevenstars.api.registries.brain.SchedulesAPI;
import net.sevenstars.api.registries.brain.SensorsAPI;
import net.sevenstars.api.utils.ModLogger;

public class SevenStarsApi implements ModInitializer {
	private static final String MOD_ID = "sevenstars-api";
	public static final String MOD_VERSION = "1.0.0-1.21.8-beta";
	public static final boolean IS_DEBUG = true;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);
	@Override
	public void onInitialize() {
		SchedulesAPI.register();
		ActivitiesAPI.register();
		SensorsAPI.register();
		MemoryModulesAPI.register();
	}

	public static Identifier id(String path){
		return Identifier.of(MOD_ID, path);
	}

	public static void logRegistryMsg(String registry) {
		LOGGER.logDebugMsg("Registering Mod " +  registry + " for " + MOD_ID);
	}
}
