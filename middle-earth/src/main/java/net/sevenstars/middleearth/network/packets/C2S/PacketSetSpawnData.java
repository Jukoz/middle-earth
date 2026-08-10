package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.world.dimension.DimensionTypes;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;

public class PacketSetSpawnData extends ClientToServerPacket<PacketSetSpawnData>
{
    public static final Id<PacketSetSpawnData> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_spawn_data"));

    public static final PacketCodec<RegistryByteBuf, PacketSetSpawnData> CODEC = PacketCodec.tuple(
            PacketCodecs.INTEGER, p -> p.overworldX,
            PacketCodecs.INTEGER, p -> p.overworldY,
            PacketCodecs.INTEGER, p -> p.overworldZ,
            PacketSetSpawnData::new
    );

    private final int overworldX;
    private final int overworldY;
    private final int overworldZ;
    public PacketSetSpawnData(int overworldX, int overworldY, int overworldZ){
        this.overworldX = overworldX;
        this.overworldY = overworldY;
        this.overworldZ = overworldZ;
    }

    @Override
    public Id<PacketSetSpawnData> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketSetSpawnData> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {

                MinecraftServer server = context.player().getServer();
                ServerPlayerEntity player = server.getPlayerManager().getPlayer(context.player().getUuid());


                BlockPos overworldSpawnBlockpos = new BlockPos(overworldX, overworldY, overworldZ);
                PlayerDataService.setOrigin(player, player.getWorld(), DimensionTypes.OVERWORLD_ID, overworldSpawnBlockpos);
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("SpawnDataPacket::Apply - Tried applying the spawn data packet",e);
        }
    }

    @Override
    public String toString() {
        return "Overworld="+overworldX+","+overworldY+","+overworldZ+";";
    }
}