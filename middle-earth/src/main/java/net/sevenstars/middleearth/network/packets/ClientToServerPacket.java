package net.sevenstars.middleearth.network.packets;

import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public abstract class ClientToServerPacket<T extends ClientToServerPacket<T>> implements CustomPayload {
    @Override
    public abstract Id<T> getId();


    public abstract PacketCodec<RegistryByteBuf, T> streamCodec();
    public abstract void process(ServerPacketContext context);
}