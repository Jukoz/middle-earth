package net.jukoz.me.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.network.packets.SpawnDataPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.network.packets.AffiliationPacket;
import net.jukoz.me.network.packets.TeleportRequestPacket;
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
        PayloadTypeRegistry.playS2C().register(AffiliationPacket.ID, AffiliationPacket.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(AffiliationPacket.ID, ModNetworks::onAffiliationPacketReceived);

        PayloadTypeRegistry.playS2C().register(SpawnDataPacket.ID, SpawnDataPacket.CODEC);
        ClientPlayNetworking.registerGlobalReceiver(SpawnDataPacket.ID, ModNetworks::onSpawnDataPacketReceived);

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
        PayloadTypeRegistry.playC2S().register(AffiliationPacket.ID, AffiliationPacket.CODEC);
        PayloadTypeRegistry.playC2S().register(SpawnDataPacket.ID, SpawnDataPacket.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(TeleportRequestPacket.ID, ModNetworks::onTeleportRequestPacketReceived);
        ServerPlayNetworking.registerGlobalReceiver(AffiliationPacket.ID, ModNetworks::onAffiliationPacketReceived);
        ServerPlayNetworking.registerGlobalReceiver(SpawnDataPacket.ID, ModNetworks::onSpawnDataPacketReceived);
    }

    // region Server to Client requests
    private static void onAffiliationPacketReceived(AffiliationPacket affiliationPacket, ClientPlayNetworking.Context context) {
        AffiliationPacket.apply(affiliationPacket, context);
    }

    private static void onSpawnDataPacketReceived(SpawnDataPacket spawnDataPacket, ClientPlayNetworking.Context context) {
        SpawnDataPacket.apply(spawnDataPacket, context);
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
                BlockPos overworldSpawnBlockPos = playerState.getOverworldSpawnBlockpos();
                BlockPos middleEarthSpawnBlockPos = playerState.getMiddleEarthSpawnBlockpos();
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
    // endregion
}
