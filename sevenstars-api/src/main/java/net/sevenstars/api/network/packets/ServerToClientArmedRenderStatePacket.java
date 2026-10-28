package net.sevenstars.api.network.packets;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.sevenstars.api.network.contexts.RenderStatePacketContext;

public abstract class ServerToClientArmedRenderStatePacket<T extends ServerToClientArmedRenderStatePacket<T>> implements CustomPayload {
    @Override
    public abstract Id<T> getId();
    public abstract PacketCodec<RegistryByteBuf, T> streamCodec();
    public abstract void process(RenderStatePacketContext context);
}