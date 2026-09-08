package net.sevenstars.ofhallsandheralds;

import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import net.sevenstars.api.AbstractModInitializer;
import net.sevenstars.api.enums.LangCategory;
import net.sevenstars.api.network.connections.ConnectionToClient;
import net.sevenstars.api.registries.brain.ActivitiesAPI;
import net.sevenstars.api.registries.brain.MemoryModulesAPI;
import net.sevenstars.api.registries.brain.SchedulesAPI;
import net.sevenstars.api.registries.brain.SensorsAPI;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.api.utils.LoggerUtil;
import net.sevenstars.ofhallsandheralds.network.ServerNetworkHandlerHH;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;
import net.sevenstars.ofhallsandheralds.registries.RegistriesHH;
import net.sevenstars.ofhallsandheralds.registries.custom.EventRegistryHH;

public class OfHallsAndHeralds extends AbstractModInitializer {
	protected OfHallsAndHeralds(String id, boolean shouldBeDebug) {
		super("ofhallsandheralds", shouldBeDebug);
	}

	@Override
	public void onInitialize() {
		registerAll();
	}

	private void registerAll() {
		ServerNetworkHandlerHH.register(new ConnectionToClient());
		EventRegistryHH.register();
		RegistriesHH.register();
		DynamicRegistriesHH.register();
	}
}
