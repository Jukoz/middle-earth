package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.permissions.PermissionsME;
import net.sevenstars.middleearth.world.dimension.ModDimensions;

public class PacketTeleportToDynamicWorldCoordinate extends ClientToServerPacket<PacketTeleportToDynamicWorldCoordinate> {
    public static final Type<PacketTeleportToDynamicWorldCoordinate> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "packet_teleport_dynamic_world_coordinate"));
    public static final StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToDynamicWorldCoordinate> CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE, p -> p.xCoordinate,
            ByteBufCodecs.DOUBLE, p -> p.zCoordinate,
            PacketTeleportToDynamicWorldCoordinate::new
    );
    private final double xCoordinate;
    private final double zCoordinate;

    public PacketTeleportToDynamicWorldCoordinate(double xCoordinate, double zCoordinate){
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
    }
    @Override
    public Type<PacketTeleportToDynamicWorldCoordinate> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketTeleportToDynamicWorldCoordinate> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        if(!PermissionsME.checkMapTeleport(context.player()))
            return;
        Vec3 coordinates = new Vec3(xCoordinate, 0, zCoordinate);
        ModDimensions.teleportPlayerToMe(context.player(), coordinates, false, false);
    }
}
