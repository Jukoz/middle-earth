package net.sevenstars.ofhallsandheralds.network;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.codec.PacketCodecs;
import net.sevenstars.api.network.ServerNetworkHandlerAPI;
import net.sevenstars.api.network.connections.ConnectionToClient;
import net.sevenstars.ofhallsandheralds.network.payload.PlayerPersistentDataPacket;

public abstract class ServerNetworkHandlerHH extends ServerNetworkHandlerAPI {
    public static void register(ConnectionToClient connection) {
        // REGISTRY : Server to client
        PayloadTypeRegistry.playS2C().register(PlayerPersistentDataPacket.ID, PlayerPersistentDataPacket.PACKET_CODEC);

        // REGISTRY : Client to server
        PayloadTypeRegistry.playC2S().register(PlayerPersistentDataPacket.ID, PlayerPersistentDataPacket.PACKET_CODEC);
    }
}
