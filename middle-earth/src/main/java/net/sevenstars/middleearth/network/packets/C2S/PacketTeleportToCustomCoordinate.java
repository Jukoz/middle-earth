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

public class PacketTeleportToCustomCoordinate extends ClientToServerPacket<PacketTeleportToCustomCoordinate> {
    public static final Id<PacketTeleportToCustomCoordinate> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_teleport_custom_spawn"));
    public static final PacketCodec<RegistryByteBuf, PacketTeleportToCustomCoordinate> CODEC = PacketCodec.tuple(
            PacketCodecs.DOUBLE, p -> p.xCoordinate,
            PacketCodecs.DOUBLE, p -> p.yCoordinate,
            PacketCodecs.DOUBLE, p -> p.zCoordinate,
            PacketCodecs.BOOLEAN, p -> p.welcomeNeeded,
            PacketTeleportToCustomCoordinate::new
    );
    private final double xCoordinate;
    private final double yCoordinate;
    private final double zCoordinate;
    private final boolean welcomeNeeded;

    public PacketTeleportToCustomCoordinate(Double xCoordinate, Double yCoordinate, Double zCoordinate, boolean welcomeNeeded){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.welcomeNeeded = welcomeNeeded;
    }
    @Override
    public Id<PacketTeleportToCustomCoordinate> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketTeleportToCustomCoordinate> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        context.player().getServer().execute(() -> {
            Vec3d coordinates = new Vec3d(xCoordinate, yCoordinate, zCoordinate);
            ModDimensions.teleportPlayerToMe(context.player(), coordinates, true, welcomeNeeded);
        });
    }
}
