package net.jukoz.me.network.contexts;

import net.jukoz.me.network.connections.IConnectionToClient;
import net.minecraft.server.network.ServerPlayerEntity;

public record ServerPacketContext(ServerPlayerEntity player, IConnectionToClient connection) {
}
