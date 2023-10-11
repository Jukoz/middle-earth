package net.jesteur.me.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jesteur.me.MiddleEarth;
import net.minecraft.util.Identifier;

public class ModNetworks {
    public static final Identifier ITEM_SYNC = new Identifier(MiddleEarth.MOD_ID, "item_sync");

    public static void registerS2CPackets() { // Server to Client packets
        ClientPlayNetworking.registerGlobalReceiver(ITEM_SYNC, ItemStackSyncS2CPacket::receive);
    }
}
