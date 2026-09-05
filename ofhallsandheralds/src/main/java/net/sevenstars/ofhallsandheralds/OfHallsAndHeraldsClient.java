package net.sevenstars.ofhallsandheralds;

import net.fabricmc.api.ClientModInitializer;
import net.sevenstars.api.network.connections.ConnectionToServer;
import net.sevenstars.ofhallsandheralds.network.ClientNetworkHandlerHH;

public class OfHallsAndHeraldsClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ClientNetworkHandlerHH.register(new ConnectionToServer());
	}
}
