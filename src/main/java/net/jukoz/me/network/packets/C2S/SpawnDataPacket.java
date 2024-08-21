package net.jukoz.me.network.packets.c2s;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;

public class  SpawnDataPacket extends ClientToServerPacket<SpawnDataPacket>
{
    public static final CustomPayload.Id<SpawnDataPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "spawn_data_packet"));

    public static final PacketCodec<RegistryByteBuf, SpawnDataPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.overworldX,
            PacketCodecs.INTEGER, p -> p.overworldY,
            PacketCodecs.INTEGER, p -> p.overworldZ,
            PacketCodecs.INTEGER, p -> p.middleEarthX,
            PacketCodecs.INTEGER, p -> p.middleEarthY,
            PacketCodecs.INTEGER, p -> p.middleEarthZ,
            SpawnDataPacket::new
    );

    private final int overworldX;
    private final int overworldY;
    private final int overworldZ;

    private final int middleEarthX;
    private final int middleEarthY;
    private final int middleEarthZ;

    public SpawnDataPacket(int overworldX, int overworldY, int overworldZ, int middleEarthX, int middleEarthY, int middleEarthZ){
        this.overworldX = overworldX;
        this.overworldY = overworldY;
        this.overworldZ = overworldZ;

        this.middleEarthX = middleEarthX;
        this.middleEarthY = middleEarthY;
        this.middleEarthZ = middleEarthZ;
    }

    @Override
    public Id<SpawnDataPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, SpawnDataPacket> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {

                MinecraftServer server = context.player().server;
                ServerPlayerEntity player = server.getPlayerManager().getPlayer(context.player().getUuid());

                PlayerData playerState = StateSaverAndLoader.getPlayerState(player);

                BlockPos overworldSpawnBlockpos = new BlockPos(overworldX, overworldY, overworldZ);
                BlockPos middleEarthSpawnBlockpos = new BlockPos(middleEarthX, middleEarthY, middleEarthZ);
                playerState.setOverworldSpawn(overworldSpawnBlockpos);
                playerState.setMiddleEarthSpawn(middleEarthSpawnBlockpos);
            });
        } catch (Exception e){
            LoggerUtil.logError("SpawnDataPacket::Apply - Tried applying the spawn data packet",e);
        }
    }

    @Override
    public String toString() {
        Vector3i overworld = new Vector3i(overworldX, overworldY, overworldZ);
        Vector3i middleEarth = new Vector3i(middleEarthX, middleEarthY, middleEarthZ);
        return "Middle_Earth="+middleEarth.x+","+middleEarth.y+","+middleEarth.z+";Overworld="+overworld.x+","+overworld.y+","+overworld.z+";";
    }
}