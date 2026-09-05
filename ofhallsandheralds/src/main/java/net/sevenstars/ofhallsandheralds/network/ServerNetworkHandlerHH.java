package net.sevenstars.ofhallsandheralds.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.sevenstars.api.network.ServerNetworkHandlerAPI;
import net.sevenstars.api.network.connections.ConnectionToClient;
import net.sevenstars.ofhallsandheralds.network.packet.PlayerPersistentReputationDataPacket;

public abstract class ServerNetworkHandlerHH extends ServerNetworkHandlerAPI {
    public static void register(ConnectionToClient connection) {
        // REGISTRY : Server to client
        PayloadTypeRegistry.playS2C().register(PlayerPersistentReputationDataPacket.ID, PlayerPersistentReputationDataPacket.PACKET_CODEC);

        // REGISTRY : Client to server
        PayloadTypeRegistry.playC2S().register(PlayerPersistentReputationDataPacket.ID, PlayerPersistentReputationDataPacket.PACKET_CODEC);
    }
}
