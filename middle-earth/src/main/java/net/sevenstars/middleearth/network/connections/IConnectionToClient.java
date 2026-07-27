package net.sevenstars.middleearth.network.connections;

import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public interface IConnectionToClient {
    <T extends ServerToClientPacket<T>> void sendPacketToClient(T packet, ServerPlayer player);
}