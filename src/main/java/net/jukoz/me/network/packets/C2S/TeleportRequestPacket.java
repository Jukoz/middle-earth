package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import org.joml.Vector3i;


public record TeleportRequestPacket(int coordinateX, int coordinateZ) implements CustomPayload
{
    public static final CustomPayload.Id<TeleportRequestPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "teleport_request_packet"));
    public static final PacketCodec<RegistryByteBuf, TeleportRequestPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, TeleportRequestPacket::coordinateX,
            PacketCodecs.INTEGER, TeleportRequestPacket::coordinateZ,
            TeleportRequestPacket::new
    );

    public static void apply(TeleportRequestPacket packet, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            ModDimensions.teleportPlayerToMe(packet, context);
        });
    }

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId()
    {
        return ID;
    }

    public Vector3i getCoordinates()
    {
        return ModDimensions.getDimensionHeight(coordinateX, coordinateZ);
    }
}