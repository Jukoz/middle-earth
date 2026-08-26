package net.sevenstars.api.network.connections;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.sevenstars.middleearth.network.packets.client2server.PacketTeleportToDynamicCoordinate;
import net.sevenstars.api.network.packets.ClientToServerPacket;

public class ConnectionToServer implements IConnectionToServer{
    @Override
    public boolean isOnServer() {
        return ClientPlayNetworking.canSend(PacketTeleportToDynamicCoordinate.ID);
    }

    @Override
    public <T extends ClientToServerPacket<T>> void sendPacketToServer(T packet) {
        if (isOnServer()) {
            ClientPlayNetworking.send(packet);
        }
    }
}
