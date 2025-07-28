package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class PacketStructureManagerUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureManagerUpdateBlockEntityRequest>
{
    public static final Id<PacketStructureManagerUpdateBlockEntityRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_manager_update_block_entity_request"));

    public static final PacketCodec<RegistryByteBuf, PacketStructureManagerUpdateBlockEntityRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            Identifier.PACKET_CODEC, p -> p.structureManagerId,
            PacketCodecs.BOOLEAN, p -> p.isActive,
            PacketStructureManagerUpdateBlockEntityRequest::new
    );
    private final BlockPos pos;
    private final Identifier structureManagerId;
    private final boolean isActive;

    public PacketStructureManagerUpdateBlockEntityRequest(BlockPos pos, Identifier structureManagerId, boolean isActive) {
        this.pos = pos;
        this.structureManagerId = structureManagerId;
        this.isActive = isActive;
    }

    @Override
    public Id<PacketStructureManagerUpdateBlockEntityRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureManagerUpdateBlockEntityRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            MinecraftServer server = context.player().getServer();
            server.execute(() -> {
                if(context.player().getWorld().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                    blockEntity.setStructureManagerId(structureManagerId);
                    blockEntity.toggle(isActive);
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerUpdateBlockEntityRequest::Tried to update the block entity.", e);
        }

    }
}