package net.sevenstars.api.network.contexts;

import net.sevenstars.api.network.connections.IConnectionToClient;
import net.minecraft.server.network.ServerPlayerEntity;

public record ServerPacketContext(ServerPlayerEntity player, IConnectionToClient connection) {
}
