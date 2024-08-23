package net.jukoz.me.network.packets.C2S;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class PacketTeleportToDynamicCoordinate extends ClientToServerPacket<PacketTeleportToDynamicCoordinate> {
    public static final CustomPayload.Id<PacketTeleportToDynamicCoordinate> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_teleport_dynamic_spawn"));
    public static final PacketCodec<RegistryByteBuf, PacketTeleportToDynamicCoordinate> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.xCoordinate,
            PacketCodecs.INTEGER, p -> p.zCoordinate,
            PacketTeleportToDynamicCoordinate::new
    );
    private final int xCoordinate;
    private final int zCoordinate;

    public PacketTeleportToDynamicCoordinate(int xCoordinate, int zCoordinate){
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
    }
    @Override
    public Id<PacketTeleportToDynamicCoordinate> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketTeleportToDynamicCoordinate> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        context.player().getServer().execute(() -> {
            Vec3d coordinates = new Vec3d(xCoordinate, ModDimensions.getDimensionHeight(xCoordinate, zCoordinate).y, zCoordinate);
            ModDimensions.teleportPlayerToMe(context.player(), coordinates);
        });
    }
}
