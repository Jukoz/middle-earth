package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.resources.datas.factions.data.SpawnData;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import net.sevenstars.middleearth.world.dimension.ModDimensions;


public class PacketTeleportToCurrentSpawn extends ClientToServerPacket<PacketTeleportToCurrentSpawn> {
    public static final Id<PacketTeleportToCurrentSpawn> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "packet_teleport_current_spawn"));
    public static final PacketCodec<RegistryByteBuf, PacketTeleportToCurrentSpawn> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOLEAN, p -> p.welcomeNeeded,
            PacketTeleportToCurrentSpawn::new
    );
    private Boolean welcomeNeeded;

    public PacketTeleportToCurrentSpawn(boolean welcomeNeeded){
        this.welcomeNeeded = welcomeNeeded;
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
                if(PlayerDataService.playerPassedOnboarding(context.player())){
                    SpawnData spawnData = PlayerDataService.getPlayerSpawnData(context.player(), context.player().getWorld());
                    if(spawnData == null)
                        return;
                    BlockPos spawnCoordinates = spawnData.getBlockPos();
                    if(spawnCoordinates != null)
                        ModDimensions.teleportPlayerToMe(context.player(), spawnCoordinates.toCenterPos(), true, welcomeNeeded);

                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("TeleportToMeSpawnRequestPacket::Apply - Tried applying the teleport to me request packet",e);
        }
    }
}