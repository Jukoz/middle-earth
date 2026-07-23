package net.sevenstars.middleearth.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.middleearth.network.connections.IConnectionToServer;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.packets.S2C.*;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

import java.util.function.BiConsumer;

public class ModClientNetworkHandler {
    public static void register(IConnectionToServer connection) {
        // Application [CLIENT SIDE]
        ClientPlayNetworking.registerGlobalReceiver(PacketForceOnboardingScreen.ID, wrapClientHandler(connection, PacketForceOnboardingScreen::process));
        ClientPlayNetworking.registerGlobalReceiver(PacketOnboardingResult.ID, wrapClientHandler(connection, PacketOnboardingResult::process));
        ClientPlayNetworking.registerGlobalReceiver(PacketLivingEntityData.ID, wrapClientHandler(connection, PacketLivingEntityData::process));
        ClientPlayNetworking.registerGlobalReceiver(InscriptionEnchantInfoPacket.ID, wrapClientHandler(connection, InscriptionEnchantInfoPacket::process));
        ClientPlayNetworking.registerGlobalReceiver(ShapingAnvilRecipePacket.ID, wrapClientHandler(connection, ShapingAnvilRecipePacket::process));
        ClientPlayNetworking.registerGlobalReceiver(ArtisanRecipePacket.ID, wrapClientHandler(connection, ArtisanRecipePacket::process));
        ClientPlayNetworking.registerGlobalReceiver(PacketOpenMapScreen.ID, wrapClientHandler(connection, PacketOpenMapScreen::process));
    }

    private static <T extends ServerToClientPacket<T>> ClientPlayNetworking.PlayPayloadHandler<T> wrapClientHandler(IConnectionToServer connection, BiConsumer<T, ClientPacketContext> consumer) {
        return (t, payloadContext) -> {
            var clientPacketContext = new ClientPacketContext(payloadContext.player(), connection);
            consumer.accept(t, clientPacketContext);
        };
    }
}
