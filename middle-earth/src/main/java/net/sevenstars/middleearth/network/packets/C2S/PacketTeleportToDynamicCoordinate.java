package net.sevenstars.middleearth.network.packets.C2S;

import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.sevenstars.middleearth.world.map.MiddleEarthMapUtils;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;

public class PacketTeleportToDynamicCoordinate extends ClientToServerPacket<PacketTeleportToDynamicCoordinate> {
    public static final Id<PacketTeleportToDynamicCoordinate> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_teleport_dynamic_spawn"));
    public static final PacketCodec<RegistryByteBuf, PacketTeleportToDynamicCoordinate> CODEC = PacketCodec.tuple(
            PacketCodecs.DOUBLE, p -> p.xCoordinate,
            PacketCodecs.DOUBLE, p -> p.zCoordinate,
            PacketCodecs.BOOL, p -> p.welcomeNeeded,
            PacketTeleportToDynamicCoordinate::new
    );
    private final double xCoordinate;
    private final double zCoordinate;
    private final boolean welcomeNeeded;

    public PacketTeleportToDynamicCoordinate(double xCoordinate, double zCoordinate, boolean welcomeNeeded){
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
        this.welcomeNeeded = welcomeNeeded;
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
            Vector2d worldCoordinate = MiddleEarthMapUtils.getInstance().getWorldCoordinateFromInitialMap(xCoordinate, zCoordinate);
            MinecraftServer server = context.player().getServer();
            Vec3d coordinates = new Vec3d(worldCoordinate.x, ModDimensions.getDimensionHeight((int)worldCoordinate.x, (int)worldCoordinate.y).y, worldCoordinate.y);
            ModDimensions.teleportPlayerToMe(context.player(), coordinates, true, welcomeNeeded);
        });
    }
}
