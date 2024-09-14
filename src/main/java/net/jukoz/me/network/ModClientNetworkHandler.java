package net.jukoz.me.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.network.connections.IConnectionToServer;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.S2C.PacketOnboardingResult;
import net.jukoz.me.network.packets.ServerToClientPacket;

import java.util.function.BiConsumer;

public class ModClientNetworkHandler {
    public static void register(IConnectionToServer connection) {
        // Application [CLIENT SIDE]
        ClientPlayNetworking.registerGlobalReceiver(PacketOnboardingResult.ID, wrapClientHandler(connection, PacketOnboardingResult::process));
    }

    private static <T extends ServerToClientPacket<T>> ClientPlayNetworking.PlayPayloadHandler<T> wrapClientHandler(IConnectionToServer connection, BiConsumer<T, ClientPacketContext> consumer) {
        return (t, payloadContext) -> {
            var clientPacketContext = new ClientPacketContext(payloadContext.player(), connection);
            consumer.accept(t, clientPacketContext);
        };
    }
}
