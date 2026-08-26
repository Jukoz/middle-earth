package net.sevenstars.middleearth.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.api.network.ClientNetworkHandlerAPI;
import net.sevenstars.api.network.connections.IConnectionToServer;
import net.sevenstars.middleearth.network.packets.server2client.*;

public class ClientNetworkHandlerME extends ClientNetworkHandlerAPI {
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
}
