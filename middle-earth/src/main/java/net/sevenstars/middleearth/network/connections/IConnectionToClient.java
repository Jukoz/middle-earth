package net.sevenstars.middleearth.network.connections;

import net.sevenstars.middleearth.network.packets.ServerToClientPacket;
import net.minecraft.server.network.ServerPlayerEntity;

public interface IConnectionToClient {
    <T extends ServerToClientPacket<T>> void sendPacketToClient(T packet, ServerPlayerEntity player);
}