package net.jukoz.me.network.packets.c2s;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import org.joml.Vector3i;


public class TeleportToMeSpawnRequestPacket extends ClientToServerPacket<TeleportToMeSpawnRequestPacket> {
    public static final CustomPayload.Id<TeleportToMeSpawnRequestPacket> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "teleport_to_me_spawn_request_packet"));
    public static final PacketCodec<RegistryByteBuf, TeleportToMeSpawnRequestPacket> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, p -> p.value,
            TeleportToMeSpawnRequestPacket::new
    );
    private Boolean value;

    public TeleportToMeSpawnRequestPacket(boolean newValue){
        this.value = newValue;
    }
    @Override
    public Id<TeleportToMeSpawnRequestPacket> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, TeleportToMeSpawnRequestPacket> streamCodec() {
        return CODEC;
    }



    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                PlayerData data = StateSaverAndLoader.getPlayerState(context.player());
                if(data != null){
                    BlockPos coordinates = data.getMiddleEarthSpawnCoordinates();
                    ModDimensions.teleportPlayerToMe(context.player(), new Vector3i(coordinates.getX(), coordinates.getY(), coordinates.getZ()));
                }
            });
        } catch (Exception e){
            LoggerUtil.logError("TeleportToMeSpawnRequestPacket::Apply - Tried applying the teleport to me request packet",e);
        }
    }
}