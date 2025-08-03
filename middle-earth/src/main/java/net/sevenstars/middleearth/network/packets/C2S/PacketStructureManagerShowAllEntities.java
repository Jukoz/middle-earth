package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class PacketStructureManagerShowAllEntities extends ClientToServerPacket<PacketStructureManagerShowAllEntities> {
    public static final CustomPayload.Id<PacketStructureManagerShowAllEntities> ID = new CustomPayload.Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_manager_show_all_entities"));

    public static final PacketCodec<RegistryByteBuf, PacketStructureManagerShowAllEntities> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketStructureManagerShowAllEntities::new
    );

    private final BlockPos pos;

    public PacketStructureManagerShowAllEntities(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Id<PacketStructureManagerShowAllEntities> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureManagerShowAllEntities> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            MinecraftServer server = context.player().getServer();
            server.execute(() -> {
                if(context.player().getWorld().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                    blockEntity.showAllEntities();
                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerUpdateBlockEntityRequest::Tried to update the block entity.", e);
        }
    }
}
