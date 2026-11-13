package net.sevenstars.api.network.connections;

import net.sevenstars.api.network.packets.ClientToServerPacket;

public interface IConnectionToServer {
    boolean isOnServer();

    <T extends ClientToServerPacket<T>> void sendPacketToServer(T packet);
}