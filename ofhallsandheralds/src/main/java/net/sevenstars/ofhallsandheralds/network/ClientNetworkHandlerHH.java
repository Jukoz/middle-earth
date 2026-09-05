package net.sevenstars.ofhallsandheralds.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.api.network.ClientNetworkHandlerAPI;
import net.sevenstars.api.network.connections.IConnectionToServer;
import net.sevenstars.ofhallsandheralds.network.payload.PlayerPersistentDataPacket;

public abstract class ClientNetworkHandlerHH extends ClientNetworkHandlerAPI {
    public static void register(IConnectionToServer connection) {
        // Application [CLIENT SIDE]
        ClientPlayNetworking.registerGlobalReceiver(PlayerPersistentDataPacket.ID, wrapClientHandler(connection, PlayerPersistentDataPacket::process));
    }
}
