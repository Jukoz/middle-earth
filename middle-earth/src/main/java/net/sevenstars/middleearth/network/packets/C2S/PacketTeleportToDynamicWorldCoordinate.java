package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class PacketTeleportToDynamicWorldCoordinate extends ClientToServerPacket<PacketTeleportToDynamicWorldCoordinate> {
    public static final Id<PacketTeleportToDynamicWorldCoordinate> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_teleport_dynamic_world_coordinate"));
    public static final PacketCodec<RegistryByteBuf, PacketTeleportToDynamicWorldCoordinate> CODEC = PacketCodec.tuple(
            PacketCodecs.DOUBLE, p -> p.xCoordinate,
            PacketCodecs.DOUBLE, p -> p.zCoordinate,
            PacketTeleportToDynamicWorldCoordinate::new
    );
    private final double xCoordinate;
    private final double zCoordinate;

    public PacketTeleportToDynamicWorldCoordinate(double xCoordinate, double zCoordinate){
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
    }
    @Override
    public Id<PacketTeleportToDynamicWorldCoordinate> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketTeleportToDynamicWorldCoordinate> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        context.player().getServer().execute(() -> {
            Vec3d coordinates = new Vec3d(xCoordinate, ModDimensions.getDimensionHeight((int)xCoordinate, (int)zCoordinate).y, zCoordinate);
            ModDimensions.teleportPlayerToMe(context.player(), coordinates, false, false);
        });
    }
}
