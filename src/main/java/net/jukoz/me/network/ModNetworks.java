package net.jukoz.me.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModNetworks {
    public static final Identifier ITEM_SYNC = Identifier.of(MiddleEarth.MOD_ID, "item_sync");

    /**
     * Register Server to Client packets
     */
    public static void registerS2CPackets() {
        //PayloadTypeRegistry.playC2S().register(TeleportRequest.ID, TeleportRequest.CODEC);

        // ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive); // TODO fixme & ItemStackSyncS2CPacket::receive
    }
    /**
     * Register Client to Server packets
     */
    public static void registerC2SPackets() {
        PayloadTypeRegistry.playC2S().register(TeleportRequest.ID, TeleportRequest.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(TeleportRequest.ID, ModNetworks::onTeleportRequestReceived);
    }

    private static void onTeleportRequestReceived(TeleportRequest teleportRequest, ServerPlayNetworking.Context context) {
        TeleportRequest.apply(teleportRequest, context);
    }
}
