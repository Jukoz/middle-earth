package net.jukoz.me.network.contexts;

import net.jukoz.me.network.connections.IConnectionToServer;
import net.minecraft.entity.player.PlayerEntity;

public record ClientPacketContext(PlayerEntity player, IConnectionToServer connection) {

}