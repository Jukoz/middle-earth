package net.sevenstars.api.network.contexts;

import net.sevenstars.api.network.connections.IConnectionToServer;
import net.minecraft.entity.player.PlayerEntity;

public record ClientPacketContext(PlayerEntity player, IConnectionToServer connection) {

}