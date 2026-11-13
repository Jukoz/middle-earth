package net.sevenstars.api.network.packets;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.sevenstars.api.network.contexts.ServerPacketContext;

public abstract class ClientToServerPacket<T extends ClientToServerPacket<T>> implements CustomPayload {
    @Override
    public abstract Id<T> getId();


    public abstract PacketCodec<RegistryByteBuf, T> streamCodec();
    public abstract void process(ServerPacketContext context);
}