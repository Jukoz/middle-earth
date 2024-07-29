package net.jukoz.me.network;

import com.mojang.authlib.GameProfile;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.joml.Vector3i;

import java.util.Collections;

import static net.jukoz.me.world.dimension.ModDimensions.WORLD_KEY;


public record TeleportRequest(int coordinateX, int coordinateZ) implements CustomPayload
{
    public static final CustomPayload.Id<TeleportRequest> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "teleport_request"));
    public static final PacketCodec<RegistryByteBuf, TeleportRequest> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, TeleportRequest::coordinateX,
            PacketCodecs.INTEGER, TeleportRequest::coordinateZ,
            TeleportRequest::new
    );

    public static void apply(TeleportRequest packet, ServerPlayNetworking.Context context) {
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