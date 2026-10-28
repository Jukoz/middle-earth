package net.sevenstars.api.network;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sevenstars.api.network.connections.IConnectionToClient;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;

import java.util.function.BiConsumer;

public abstract class ServerNetworkHandlerAPI {
    protected static <T extends ClientToServerPacket<T>> ServerPlayNetworking.PlayPayloadHandler<T> wrapServerHandler(
            IConnectionToClient connection,
            BiConsumer<T, ServerPacketContext> consumer
    ) {
        return (t, payloadContext) -> {
            ServerPlayerEntity player = payloadContext.player();
            var serverPacketContext = new ServerPacketContext(player, connection);
            consumer.accept(t, serverPacketContext);
        };
    }
}
