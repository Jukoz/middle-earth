package net.sevenstars.middleearth.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.network.packets.S2C.PacketStructureManagerUpdateClient;

public class PacketStructureManagerUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureManagerUpdateBlockEntityRequest>
{
    public static final Id<PacketStructureManagerUpdateBlockEntityRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_manager_update_block_entity_request"));

    public static final PacketCodec<RegistryByteBuf, PacketStructureManagerUpdateBlockEntityRequest> CODEC = PacketCodec.tuple(
            Identifier.PACKET_CODEC, p -> p.structureManagerId,
            PacketCodecs.BOOLEAN, p -> p.isActive,
            PacketStructureManagerUpdateBlockEntityRequest::new
    );
    private final Identifier structureManagerId;
    private final boolean isActive;

    public PacketStructureManagerUpdateBlockEntityRequest(Identifier structureManagerId, boolean isActive) {
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
                if(context.player().currentScreenHandler instanceof StructureManagerScreenHandler handler){
                    BlockPos pos = handler.getPos();
                    if(context.player().getWorld().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                        blockEntity.setDataId(structureManagerId);
                        PacketStructureManagerUpdateClient newPacket = new PacketStructureManagerUpdateClient(
                                blockEntity.getDataId(),
                                blockEntity.getIsActive()
                        );
                        ServerPlayNetworking.send(context.player(), newPacket);
                    }
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerUpdateBlockEntityRequest::Tried to update the block entity.", e);
        }

    }
}