package net.sevenstars.api.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.api.network.connections.IConnectionToServer;
import net.sevenstars.api.network.contexts.ClientPacketContext;
import net.sevenstars.api.network.packets.ServerToClientPacket;

import java.util.function.BiConsumer;

public abstract class ClientNetworkHandlerAPI {
    protected static <T extends ServerToClientPacket<T>> ClientPlayNetworking.PlayPayloadHandler<T> wrapClientHandler(IConnectionToServer connection, BiConsumer<T, ClientPacketContext> consumer) {
        return (t, payloadContext) -> {
            var clientPacketContext = new ClientPacketContext(payloadContext.player(), connection);
            consumer.accept(t, clientPacketContext);
        };
    }
}
