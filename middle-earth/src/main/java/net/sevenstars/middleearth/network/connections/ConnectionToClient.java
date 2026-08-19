package net.sevenstars.middleearth.network.connections;

import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.sevenstars.middleearth.network.packets.ServerToClientPacket;

public class ConnectionToClient implements IConnectionToClient{
    @Override
    public <T extends ServerToClientPacket<T>> void sendPacketToClient(T packet, ServerPlayer player) {
        send(player, packet);
    }

    public static <T extends ServerToClientPacket<T>> void send(ServerPlayer player, T packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }
}
