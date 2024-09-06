package net.jukoz.me.network.packets.C2S;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.CommandColors;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.world.dimension.ModDimensions;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2d;
import org.joml.Vector2i;

public class PacketTeleportToDynamicCoordinate extends ClientToServerPacket<PacketTeleportToDynamicCoordinate> {
    public static final CustomPayload.Id<PacketTeleportToDynamicCoordinate> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_teleport_dynamic_spawn"));
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

            Vec3d coordinates = new Vec3d(worldCoordinate.x, ModDimensions.getDimensionHeight((int)worldCoordinate.x, (int)worldCoordinate.y).y, worldCoordinate.y);
            ModDimensions.teleportPlayerToMe(context.player(), coordinates, welcomeNeeded);
        });
    }
}
