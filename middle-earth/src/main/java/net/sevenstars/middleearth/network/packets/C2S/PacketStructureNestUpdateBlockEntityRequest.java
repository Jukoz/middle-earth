package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.nest.StructureNestBlockEntity;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

import java.util.Optional;

public class PacketStructureNestUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureNestUpdateBlockEntityRequest>
{
    public static final Id<PacketStructureNestUpdateBlockEntityRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_nest_update_block_entity_request"));

    public static final PacketCodec<RegistryByteBuf, PacketStructureNestUpdateBlockEntityRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketCodecs.optional(Identifier.PACKET_CODEC), p -> p.getStructureManagerId(),
            PacketCodecs.optional(Identifier.PACKET_CODEC), p -> p.getStructureNestId(),
            PacketStructureNestUpdateBlockEntityRequest::new
    );

    private Optional<Identifier> getStructureManagerId() {
        return Optional.ofNullable(structureManagerId);
    }

    private Optional<Identifier> getStructureNestId() {
        return Optional.ofNullable(structureNestId);
    }

    private final BlockPos pos;
    private final Identifier structureManagerId;
    private final Identifier structureNestId;

    public PacketStructureNestUpdateBlockEntityRequest(BlockPos pos, Optional<Identifier> structureManagerId, Optional<Identifier> structureNestId) {
        this.pos = pos;
        if(structureManagerId.isPresent())
            this.structureManagerId = structureManagerId.get();
        else
            this.structureManagerId = null;

        if(structureNestId.isPresent())
            this.structureNestId = structureNestId.get();
        else
            this.structureNestId = null;
    }

    @Override
    public Id<PacketStructureNestUpdateBlockEntityRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureNestUpdateBlockEntityRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            MinecraftServer server = context.player().getServer();
            server.execute(() -> {
                if(context.player().getWorld().getBlockEntity(pos) instanceof StructureNestBlockEntity blockEntity){
                    blockEntity.setStructureManagerId(structureManagerId);
                    blockEntity.setStructureNestId(structureNestId);
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureNestUpdateBlockEntityRequest::Tried to update the block entity.", e);
        }
    }
}