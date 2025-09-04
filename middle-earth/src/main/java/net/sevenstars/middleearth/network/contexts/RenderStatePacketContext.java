package net.sevenstars.middleearth.network.contexts;

import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sevenstars.middleearth.network.connections.IConnectionToServer;

public record RenderStatePacketContext(ArmedEntityRenderState renderState, ServerPlayerEntity playerEntity, IConnectionToServer connection) {

}