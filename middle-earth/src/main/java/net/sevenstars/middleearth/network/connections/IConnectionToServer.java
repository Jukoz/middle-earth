package net.sevenstars.middleearth.network.connections;

import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public interface IConnectionToServer {
    boolean isOnServer();

    <T extends ClientToServerPacket<T>> void sendPacketToServer(T packet);
}