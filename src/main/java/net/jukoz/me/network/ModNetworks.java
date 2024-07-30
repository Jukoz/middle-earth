package net.jukoz.me.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.StateSaverAndLoader;
import net.jukoz.me.network.packets.IdentityPacket;
import net.jukoz.me.network.packets.TeleportRequestPacket;
import net.jukoz.me.resource.PlayerData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.Identifier;

public class ModNetworks {
    /**
     * Client side
     * Register Server to Client packets
     */
    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(IdentityPacket.ID, IdentityPacket.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(IdentityPacket.ID, ModNetworks::onIdentityPacketReceived);

        // TODO fixme & ItemStackSyncS2CPacket::receive
        // ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
    /**
     * Server side
     * Register Client to Server packets
     */
    public static void registerC2SPackets() {
        ServerPlayConnectionEvents.JOIN.register(ModNetworks::onPlayerJoin);

        PayloadTypeRegistry.playC2S().register(TeleportRequestPacket.ID, TeleportRequestPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(IdentityPacket.ID, IdentityPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(TeleportRequestPacket.ID, ModNetworks::onTeleportRequestPacketReceived);
        ServerPlayNetworking.registerGlobalReceiver(IdentityPacket.ID, ModNetworks::onIdentityPacketReceived);
    }

    // region Server to Client requests
    private static void onIdentityPacketReceived(IdentityPacket identityPacket, ClientPlayNetworking.Context context) {
        IdentityPacket.apply(identityPacket, context);
    }
    // endregion

    // region Client to Server requests
    private static void onPlayerJoin(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer minecraftServer) {
        PlayerData playerState = StateSaverAndLoader.getPlayerState(serverPlayNetworkHandler.getPlayer());
        IdentityPacket packet = new IdentityPacket(playerState.alignment, playerState.faction, playerState.subfaction);

        minecraftServer.execute(() -> {
            ServerPlayNetworking.send(serverPlayNetworkHandler.getPlayer(), packet);
        });
    }
    private static void onTeleportRequestPacketReceived(TeleportRequestPacket teleportRequestPacket, ServerPlayNetworking.Context context) {
        TeleportRequestPacket.apply(teleportRequestPacket, context);
    }

    private static void onIdentityPacketReceived(IdentityPacket identityPacket, ServerPlayNetworking.Context context) {
        IdentityPacket.apply(identityPacket, context);
    }
    // endregion
}
