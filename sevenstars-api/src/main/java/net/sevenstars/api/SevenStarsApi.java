package net.sevenstars.api;

import net.sevenstars.api.entity.ai.brain.ActivitiesAPI;
import net.sevenstars.api.entity.ai.brain.MemoryModulesAPI;
import net.sevenstars.api.entity.ai.brain.SchedulesAPI;
import net.sevenstars.api.entity.ai.brain.SensorsAPI;
import net.sevenstars.api.registries.RegistrationBridge;
import net.sevenstars.api.utils.ModLogger;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;

@Mod(SevenStarsApi.MOD_ID)
public final class SevenStarsApi {
	public static final String MOD_ID = "sevenstars_api";
	public static final String MOD_VERSION = "1.0.1-1.21.1-beta";
	public static final boolean IS_DEBUG = false;
	public static final ModLogger LOGGER = new ModLogger(MOD_ID, IS_DEBUG);

	public SevenStarsApi(IEventBus modEventBus) {
		RegistrationBridge.attach(modEventBus);
		onInitialize();
		if (FMLEnvironment.dist == Dist.CLIENT) {
			SevenStarsApiClient.onInitializeClient();
		}
	}

	public void onInitialize() {
		ActivitiesAPI.registerModActivities();
		MemoryModulesAPI.registerModMemoryModules();
		SensorsAPI.registerModSensors();
		SchedulesAPI.registerModSchedules();
	}
}
