package net.jukoz.me.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.network.connections.IConnectionToClient;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.network.packets.c2s.*;
import net.jukoz.me.network.packets.s2c.OnboardingDetailParsedPacket;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.function.BiConsumer;

public class ModServerNetworkHandler {
    public static void register(IConnectionToClient connection) {
        // REGISTRY : Server to client
        PayloadTypeRegistry.playS2C().register(OnboardingDetailParsedPacket.ID, OnboardingDetailParsedPacket.CODEC);

        // REGISTRY : Client to server
        PayloadTypeRegistry.playC2S().register(AffiliationPacket.ID, AffiliationPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(TeleportRequestPacket.ID, TeleportRequestPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(TeleportToMeSpawnRequestPacket.ID, TeleportToMeSpawnRequestPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(SpawnDataPacket.ID, SpawnDataPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(OnboardingDetailFetchingPacket.ID, OnboardingDetailFetchingPacket.CODEC);

        // Application [SERVER SIDE]
        ServerPlayNetworking.registerGlobalReceiver(AffiliationPacket.ID, wrapServerHandler(connection, AffiliationPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(TeleportRequestPacket.ID, wrapServerHandler(connection, TeleportRequestPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(TeleportToMeSpawnRequestPacket.ID, wrapServerHandler(connection, TeleportToMeSpawnRequestPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(SpawnDataPacket.ID, wrapServerHandler(connection, SpawnDataPacket::process));
        ServerPlayNetworking.registerGlobalReceiver(OnboardingDetailFetchingPacket.ID, wrapServerHandler(connection, OnboardingDetailFetchingPacket::process));
    }

    private static <T extends ClientToServerPacket<T>> ServerPlayNetworking.PlayPayloadHandler<T> wrapServerHandler(
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
