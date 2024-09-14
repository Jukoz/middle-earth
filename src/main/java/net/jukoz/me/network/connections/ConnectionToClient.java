package net.jukoz.me.network.connections;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.network.packets.ServerToClientPacket;
import net.minecraft.server.network.ServerPlayerEntity;

public class ConnectionToClient implements IConnectionToClient{
    @Override
    public <T extends ServerToClientPacket<T>> void sendPacketToClient(T packet, ServerPlayerEntity player) {
        ServerPlayNetworking.send(player, packet);
    }
}
