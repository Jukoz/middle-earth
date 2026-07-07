package net.sevenstars.middleearth.network.contexts;

import net.sevenstars.middleearth.network.connections.IConnectionToServer;
import net.minecraft.entity.player.PlayerEntity;

public record ClientPacketContext(PlayerEntity player, IConnectionToServer connection) {

}