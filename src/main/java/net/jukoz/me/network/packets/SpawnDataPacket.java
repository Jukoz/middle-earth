package net.jukoz.me.network.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

public record SpawnDataPacket(int overworldX, int overworldY, int overworldZ, int middleEarthX, int middleEarthY, int middleEarthZ) implements CustomPayload
{
    public static final CustomPayload.Id<SpawnDataPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "spawn_data_packet"));

    public static final PacketCodec<RegistryByteBuf, SpawnDataPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, SpawnDataPacket::overworldX,
            PacketCodecs.INTEGER, SpawnDataPacket::overworldY,
            PacketCodecs.INTEGER, SpawnDataPacket::overworldZ,
            PacketCodecs.INTEGER, SpawnDataPacket::middleEarthX,
            PacketCodecs.INTEGER, SpawnDataPacket::middleEarthY,
            PacketCodecs.INTEGER, SpawnDataPacket::middleEarthZ,
            SpawnDataPacket::new
    );

    public static void apply(SpawnDataPacket packet, ServerPlayNetworking.Context context) {
        context.player().getServer().execute(() -> {
            LoggerUtil.logDebugMsg("SpawnDataPacket::" + context.player());

            MinecraftServer server = context.server();
            ServerPlayerEntity player = server.getPlayerManager().getPlayer(context.player().getUuid());

            PlayerData playerState = StateSaverAndLoader.getPlayerState(player);

            BlockPos overworldSpawnBlockpos = new BlockPos(packet.overworldX, packet.overworldY, packet.overworldZ);
            BlockPos middleEarthSpawnBlockpos = new BlockPos(packet.middleEarthX, packet.middleEarthY, packet.middleEarthZ);
            playerState.setOverworldSpawn(overworldSpawnBlockpos);
            playerState.setMiddleEarthSpawn(middleEarthSpawnBlockpos);

            server.execute(() -> {
                ServerPlayNetworking.send(player, packet);
            });
        });
    }
    public static void apply(SpawnDataPacket packet, ClientPlayNetworking.Context context) {
        context.client().execute(() -> {
            LoggerUtil.logDebugMsg("SpawnDataPacket::ReceiveClientside::" + packet.toString());
        });
    }

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId()
    {
        return ID;
    }

    @Override
    public String toString() {
        Vector3i overworld = new Vector3i(overworldX, overworldY, overworldZ);
        Vector3i middleEarth = new Vector3i(middleEarthX, middleEarthY, middleEarthZ);
        return "Middle_Earth="+middleEarth.x+","+middleEarth.y+","+middleEarth.z+";Overworld="+overworld.x+","+overworld.y+","+overworld.z+";";
    }
}