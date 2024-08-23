package net.jukoz.me.network.connections;

import net.jukoz.me.network.packets.ClientToServerPacket;

public interface IConnectionToServer {
    boolean isOnServer();

    <T extends ClientToServerPacket<T>> void sendPacketToServer(T packet);
}