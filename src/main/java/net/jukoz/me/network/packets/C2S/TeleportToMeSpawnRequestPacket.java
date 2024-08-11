package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;


public record TeleportToMeSpawnRequestPacket(boolean temp) implements CustomPayload
{
    public static final Id<TeleportToMeSpawnRequestPacket> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "teleport_to_me_spawn_request_packet"));
    public static final PacketCodec<RegistryByteBuf, TeleportToMeSpawnRequestPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, TeleportToMeSpawnRequestPacket::temp,
            TeleportToMeSpawnRequestPacket::new
    );

    public static void apply(TeleportToMeSpawnRequestPacket packet, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            PlayerData data = StateSaverAndLoader.getPlayerState(context.player());
            if(data != null){
                BlockPos coordinates = data.getMiddleEarthSpawnCoordinates();
                ModDimensions.teleportPlayerToMe(new TeleportRequestPacket(coordinates.getX(), coordinates.getZ()), context);
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId()
    {
        return ID;
    }
}