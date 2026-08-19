package net.sevenstars.middleearth.network.contexts;

import net.minecraft.world.entity.player.Player;
import net.sevenstars.middleearth.network.connections.IConnectionToServer;

public record ClientPacketContext(Player player, IConnectionToServer connection) {

}