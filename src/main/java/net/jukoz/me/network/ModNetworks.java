package net.jukoz.me.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.network.packets.C2S.*;
import net.jukoz.me.network.packets.S2C.OnboardingDetailParsedPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.AffiliationData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.util.math.BlockPos;

public class ModNetworks {
    /**
     * Client side
     * Register Server to Client packets
     */
    public static void registerS2CPackets() {
        PayloadTypeRegistry.playS2C().register(OnboardingDetailParsedPacket.ID, OnboardingDetailParsedPacket.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(OnboardingDetailParsedPacket.ID, ModNetworks::onOnboardingDetailsRequestPacketReceived);

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
        ServerPlayNetworking.registerGlobalReceiver(TeleportRequestPacket.ID, ModNetworks::onTeleportRequestPacketReceived);

        PayloadTypeRegistry.playC2S().register(TeleportToMeSpawnRequestPacket.ID, TeleportToMeSpawnRequestPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(TeleportToMeSpawnRequestPacket.ID, ModNetworks::onTeleportToMeSpawnRequestPacket);

        PayloadTypeRegistry.playC2S().register(AffiliationPacket.ID, AffiliationPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(AffiliationPacket.ID, ModNetworks::onAffiliationPacketReceived);

        PayloadTypeRegistry.playC2S().register(SpawnDataPacket.ID, SpawnDataPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SpawnDataPacket.ID, ModNetworks::onSpawnDataPacketReceived);

        PayloadTypeRegistry.playC2S().register(OnboardingDetailFetchingPacket.ID, OnboardingDetailFetchingPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(OnboardingDetailFetchingPacket.ID, ModNetworks::onOnboardingDetailsRequestPacketReceived);
    }


    private static void onTeleportToMeSpawnRequestPacket(TeleportToMeSpawnRequestPacket teleportToMeSpawnRequestPacket, ServerPlayNetworking.Context context) {
        TeleportToMeSpawnRequestPacket.apply(teleportToMeSpawnRequestPacket, context);
    }

    // region Server to Client requests

    private static void onOnboardingDetailsRequestPacketReceived(OnboardingDetailParsedPacket onboardingDetailFetchingPacket, ClientPlayNetworking.Context context) {
        OnboardingDetailParsedPacket.apply(onboardingDetailFetchingPacket, context);
    }
    // endregion

    // region Client to Server requests
    private static void onPlayerJoin(ServerPlayNetworkHandler serverPlayNetworkHandler, PacketSender packetSender, MinecraftServer minecraftServer) {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PlayerData playerState = StateSaverAndLoader.getPlayerState(handler.getPlayer());
            if(playerState.hasAffilition()){
                // Affiliation data
                AffiliationData affiliationData = playerState.getAffiliationData();
                if(affiliationData != null){
                    int alignment = affiliationData.alignment;
                    int faction = affiliationData.faction;
                    int subfaction = affiliationData.subfaction;
                    AffiliationPacket affiliationPacket = new AffiliationPacket(alignment, faction, subfaction);
                    server.execute(() -> {
                        ServerPlayNetworking.send(handler.getPlayer(), affiliationPacket);
                    });
                }

                // Spawn coordinates
                BlockPos overworldSpawnBlockPos = playerState.getOverworldSpawnCoordinates();
                BlockPos middleEarthSpawnBlockPos = playerState.getMiddleEarthSpawnCoordinates();
                if(overworldSpawnBlockPos != null && middleEarthSpawnBlockPos != null){
                    SpawnDataPacket spawnDataPacket = new SpawnDataPacket(
                            overworldSpawnBlockPos.getX(), overworldSpawnBlockPos.getY(), overworldSpawnBlockPos.getZ(),
                            middleEarthSpawnBlockPos.getX(), middleEarthSpawnBlockPos.getY(), middleEarthSpawnBlockPos.getZ()
                    );

                    server.execute(() -> {
                        ServerPlayNetworking.send(handler.getPlayer(), spawnDataPacket);
                    });
                }
            }
        });
    }

    private static void onTeleportRequestPacketReceived(TeleportRequestPacket teleportRequestPacket, ServerPlayNetworking.Context context) {
        TeleportRequestPacket.apply(teleportRequestPacket, context);
    }

    private static void onAffiliationPacketReceived(AffiliationPacket affiliationPacket, ServerPlayNetworking.Context context) {
        AffiliationPacket.apply(affiliationPacket, context);
    }

    private static void onSpawnDataPacketReceived(SpawnDataPacket spawnDataPacket, ServerPlayNetworking.Context context) {
        SpawnDataPacket.apply(spawnDataPacket, context);
    }

    private static void onOnboardingDetailsRequestPacketReceived(OnboardingDetailFetchingPacket onboardingDetailFetchingPacket, ServerPlayNetworking.Context context) {
        OnboardingDetailFetchingPacket.apply(onboardingDetailFetchingPacket, context);
    }
    // endregion
}
