package net.sevenstars.middleearth.network.contexts;

import net.sevenstars.middleearth.network.connections.IConnectionToClient;
import net.minecraft.server.network.ServerPlayerEntity;

public record ServerPacketContext(ServerPlayerEntity player, IConnectionToClient connection) {
}
