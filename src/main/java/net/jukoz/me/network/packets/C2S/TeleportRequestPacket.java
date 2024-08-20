package net.jukoz.me.network.packets.c2s;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;


public class  TeleportRequestPacket extends ClientToServerPacket<TeleportRequestPacket>
{
    public static final CustomPayload.Id<TeleportRequestPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "teleport_request_packet"));
    public static final PacketCodec<RegistryByteBuf, TeleportRequestPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT, p -> p.xCoordinate,
            PacketCodecs.VAR_INT, p -> p.zCoordinate,
            TeleportRequestPacket::new
    );

    private final int xCoordinate;
    private final int zCoordinate;

    public TeleportRequestPacket(int xCoordinate, int zCoordinate){
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
    }

    @Override
    public Id<TeleportRequestPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, TeleportRequestPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                ModDimensions.teleportPlayerToMe(context.player(), ModDimensions.getDimensionHeight(xCoordinate, zCoordinate));
            });
        } catch (Exception e){
            LoggerUtil.logError("TeleportRequestPacket::Apply - Tried applying the teleport request packet",e);
        }
    }
}