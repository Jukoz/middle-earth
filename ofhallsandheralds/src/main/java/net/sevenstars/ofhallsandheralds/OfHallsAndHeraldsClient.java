package net.sevenstars.ofhallsandheralds;

import net.fabricmc.api.ClientModInitializer;
import net.sevenstars.api.network.connections.ConnectionToServer;
import net.sevenstars.ofhallsandheralds.network.ClientNetworkHandlerHH;
import net.sevenstars.ofhallsandheralds.registries.custom.AtlasRegistryHH;

public class OfHallsAndHeraldsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		AtlasRegistryHH.registerAtlas();
		ClientNetworkHandlerHH.register(new ConnectionToServer());
	}
}
