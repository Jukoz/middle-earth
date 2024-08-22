package net.jukoz.me.network.packets.C2S;

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
import net.minecraft.util.math.Vec3d;


public class PacketTeleportToCurrentSpawn extends ClientToServerPacket<PacketTeleportToCurrentSpawn> {
    public static final CustomPayload.Id<PacketTeleportToCurrentSpawn> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_teleport_current_spawn"));
    public static final PacketCodec<RegistryByteBuf, PacketTeleportToCurrentSpawn> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, p -> p.value,
            PacketTeleportToCurrentSpawn::new
    );
    private Boolean value;

    public PacketTeleportToCurrentSpawn(boolean newValue){
        this.value = newValue;
    }
    @Override
    public Id<PacketTeleportToCurrentSpawn> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketTeleportToCurrentSpawn> streamCodec() {
        return CODEC;
    }



    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                PlayerData data = StateSaverAndLoader.getPlayerState(context.player());
                if(data != null){
                    if(data.hasAffilition()){
                        Vec3d spawnCoordinates = data.getAffiliationData().getMiddleEarthSpawnCoordinate();
                        if(spawnCoordinates != null)
                            ModDimensions.teleportPlayerToMe(context.player(), new Vec3d(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z));
                    }
                }
            });
        } catch (Exception e){
            LoggerUtil.logError("TeleportToMeSpawnRequestPacket::Apply - Tried applying the teleport to me request packet",e);
        }
    }
}