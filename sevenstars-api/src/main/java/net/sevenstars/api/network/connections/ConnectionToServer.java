package net.sevenstars.api.network.connections;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.sevenstars.api.network.packets.ClientToServerPacket;

public class ConnectionToServer implements IConnectionToServer{

    @Override
    public boolean isOnServer() {
        MinecraftClient client = MinecraftClient.getInstance();
        return client != null && (client.getServer() != null || client.getCurrentServerEntry() != null);
    }

    @Override
    public <T extends ClientToServerPacket<T>> void sendPacketToServer(T packet) {
        if(isOnServer()){
            ClientPlayNetworking.send(packet);
        }
    }
}
