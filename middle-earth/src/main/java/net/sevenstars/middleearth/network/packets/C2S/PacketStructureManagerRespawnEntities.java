package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class PacketStructureManagerRespawnEntities extends ClientToServerPacket<PacketStructureManagerRespawnEntities> {
    public static final Id<PacketStructureManagerRespawnEntities> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_manager_respawn_entities"));

    public static final PacketCodec<RegistryByteBuf, PacketStructureManagerRespawnEntities> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketStructureManagerRespawnEntities::new
    );

    private final BlockPos pos;

    public PacketStructureManagerRespawnEntities(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Id<PacketStructureManagerRespawnEntities> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureManagerRespawnEntities> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            MinecraftServer server = context.player().getServer();
            server.execute(() -> {
                if(context.player().getWorld().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                    blockEntity.respawnAllEntities();
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerRespawnEntities::Tried to reset all entities.", e);
        }
    }
}
