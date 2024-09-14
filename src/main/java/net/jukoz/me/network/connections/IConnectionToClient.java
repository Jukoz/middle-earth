package net.jukoz.me.network.connections;

import net.jukoz.me.network.packets.ServerToClientPacket;
import net.minecraft.server.network.ServerPlayerEntity;

public interface IConnectionToClient {
    <T extends ServerToClientPacket<T>> void sendPacketToClient(T packet, ServerPlayerEntity player);
}