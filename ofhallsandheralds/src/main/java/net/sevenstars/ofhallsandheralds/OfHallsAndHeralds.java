package net.sevenstars.ofhallsandheralds;

import net.sevenstars.api.AbstractModInitializer;
import net.sevenstars.api.network.connections.ConnectionToClient;
import net.sevenstars.ofhallsandheralds.network.ServerNetworkHandlerHH;
import net.sevenstars.ofhallsandheralds.registries.DynamicRegistriesHH;
import net.sevenstars.ofhallsandheralds.registries.RegistriesHH;
import net.sevenstars.ofhallsandheralds.registries.custom.EventRegistryHH;

public class OfHallsAndHeralds extends AbstractModInitializer {
	public OfHallsAndHeralds() {
		initialize("ofhallsandheralds", false);
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
