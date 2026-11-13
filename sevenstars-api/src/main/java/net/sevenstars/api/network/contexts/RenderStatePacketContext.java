package net.sevenstars.api.network.contexts;

import net.minecraft.client.render.entity.state.ArmedEntityRenderState;
import net.minecraft.server.network.ServerPlayerEntity;
import net.sevenstars.api.network.connections.IConnectionToServer;

public record RenderStatePacketContext(ArmedEntityRenderState renderState, ServerPlayerEntity playerEntity, IConnectionToServer connection) {

}