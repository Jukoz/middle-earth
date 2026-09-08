package net.sevenstars.api;

import net.sevenstars.api.registries.brain.ActivitiesAPI;
import net.sevenstars.api.registries.brain.MemoryModulesAPI;
import net.sevenstars.api.registries.brain.SchedulesAPI;
import net.sevenstars.api.registries.brain.SensorsAPI;

public class SevenStarsApi extends AbstractModInitializer {
	public SevenStarsApi() {
		super("sevenstars-api", false);
	}

	@Override
	public void onInitialize() {
		registerAll();
	}

	private void registerAll() {
		SchedulesAPI.register();
		ActivitiesAPI.register();
		SensorsAPI.register();
		MemoryModulesAPI.register();
	}
}
