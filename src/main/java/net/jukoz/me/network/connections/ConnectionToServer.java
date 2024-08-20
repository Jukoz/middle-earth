package net.jukoz.me.network.connections;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.network.packets.c2s.TeleportRequestPacket;

public class ConnectionToServer implements IConnectionToServer{
    @Override
    public boolean isOnServer() {
        return ClientPlayNetworking.canSend(TeleportRequestPacket.ID);
    }

    @Override
    public <T extends ClientToServerPacket<T>> void sendPacketToServer(T packet) {
        if (isOnServer()) {
            ClientPlayNetworking.send(packet);
        }
    }
}
