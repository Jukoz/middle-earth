package net.sevenstars.middleearth.network.contexts;

import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.network.connections.IConnectionToClient;

public record ServerPacketContext(ServerPlayer player, IConnectionToClient connection) {
}
