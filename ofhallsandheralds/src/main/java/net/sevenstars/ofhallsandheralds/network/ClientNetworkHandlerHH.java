package net.sevenstars.ofhallsandheralds.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.api.network.ClientNetworkHandlerAPI;
import net.sevenstars.api.network.connections.IConnectionToServer;
import net.sevenstars.ofhallsandheralds.network.packet.PlayerPersistentReputationDataPacket;

public abstract class ClientNetworkHandlerHH extends ClientNetworkHandlerAPI {
    public static void register(IConnectionToServer connection) {
        // Application [CLIENT SIDE]
        ClientPlayNetworking.registerGlobalReceiver(PlayerPersistentReputationDataPacket.ID, wrapClientHandler(connection, PlayerPersistentReputationDataPacket::process));
    }
}
