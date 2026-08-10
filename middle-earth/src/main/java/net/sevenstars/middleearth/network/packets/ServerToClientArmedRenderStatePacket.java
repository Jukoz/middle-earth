package net.sevenstars.middleearth.network.packets;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.sevenstars.middleearth.network.contexts.ClientPacketContext;
import net.sevenstars.middleearth.network.contexts.RenderStatePacketContext;
import net.sevenstars.middleearth.network.packets.S2C.PacketLivingEntityData;

public abstract class ServerToClientArmedRenderStatePacket<T extends ServerToClientArmedRenderStatePacket<T>> implements CustomPayload {
    @Override
    public abstract Id<T> getId();
    public abstract PacketCodec<RegistryByteBuf, T> streamCodec();
    public abstract void process(RenderStatePacketContext context);
}