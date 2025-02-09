package net.jukoz.me.network.packets;

import net.jukoz.me.network.contexts.ClientPacketContext;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public abstract class ServerToClientPacket<T extends ServerToClientPacket<T>> implements CustomPayload {
    @Override
    public abstract Id<T> getId();
    public abstract PacketCodec<RegistryByteBuf, T> streamCodec();
    public abstract void process(ClientPacketContext context);
}