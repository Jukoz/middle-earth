package net.jukoz.me.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.network.packets.AffiliationPacket;
import net.jukoz.me.network.packets.TeleportRequestPacket;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;

public class ModNetworks {
    /**
     * Client side
     * Register Server to Client packets
     */
    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(AffiliationPacket.ID, AffiliationPacket.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(AffiliationPacket.ID, ModNetworks::onIdentityPacketReceived);

        // TODO fixme & ItemStackSyncS2CPacket::receive
        // ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
    /**
     * Server side
     * Register Client to Server packets
     */
    public static void registerC2SPackets() {
        ServerPlayConnectionEvents.JOIN.register(ModNetworks::onPlayerJoin);
        ServerPlayConnectionEvents.DISCONNECT.register(ModNetworks::onPlayerDisconnect);

        PayloadTypeRegistry.playC2S().register(TeleportRequestPacket.ID, TeleportRequestPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(AffiliationPacket.ID, AffiliationPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(TeleportRequestPacket.ID, ModNetworks::onTeleportRequestPacketReceived);
        ServerPlayNetworking.registerGlobalReceiver(AffiliationPacket.ID, ModNetworks::onIdentityPacketReceived);
    }

    // region Server to Client requests
    private static void onIdentityPacketReceived(AffiliationPacket affiliationPacket, ClientPlayNetworking.Context context) {
        AffiliationPacket.apply(affiliationPacket, context);
    }
    // endregion

    // region Client to Server requests
    private static void onPlayerJoin(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer minecraftServer) {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerData playerState = StateSaverAndLoader.getPlayerState(handler.getPlayer());
            if(playerState.hasAffilition()){
                AffiliationData affiliationData = playerState.getAffiliationData();
                int alignment = affiliationData.alignment;
                int faction = affiliationData.faction;
                int subfaction = affiliationData.subfaction;

                AffiliationPacket affiliationPacket = new AffiliationPacket(alignment, faction, subfaction);
                server.execute(() -> {
                    ServerPlayNetworking.send(handler.getPlayer(), affiliationPacket);
                });
            }
        });
    }

    private static void onPlayerDisconnect(ServerPlayNetworkHandler serverPlayNetworkHandler, MinecraftServer minecraftServer) {
        PlayerData playerState = StateSaverAndLoader.getPlayerState(serverPlayNetworkHandler.getPlayer());

        playerState.toString();

        AffiliationData affiliationData = playerState.getAffiliationData();
        int alignment = affiliationData.alignment;
        int faction = affiliationData.faction;
        int subfaction = affiliationData.subfaction;

        AffiliationPacket affiliationPacket = new AffiliationPacket(alignment, faction, subfaction);

        minecraftServer.execute(() -> {
            ServerPlayNetworking.send(serverPlayNetworkHandler.getPlayer(), affiliationPacket);
        });
    }

    private static void onTeleportRequestPacketReceived(TeleportRequestPacket teleportRequestPacket, ServerPlayNetworking.Context context) {
        TeleportRequestPacket.apply(teleportRequestPacket, context);
    }

    private static void onIdentityPacketReceived(AffiliationPacket affiliationPacket, ServerPlayNetworking.Context context) {
        AffiliationPacket.apply(affiliationPacket, context);
    }
    // endregion
}
