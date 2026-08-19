package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.ServerPacketGuards;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;

public class PacketStructureManagerUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureManagerUpdateBlockEntityRequest>
{
    public static final Type<PacketStructureManagerUpdateBlockEntityRequest> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "structure_manager_update_block_entity_request"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketStructureManagerUpdateBlockEntityRequest> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, p -> p.pos,
            ResourceLocation.STREAM_CODEC, p -> p.structureManagerId,
            ByteBufCodecs.BOOL, p -> p.toInitialize,
            ByteBufCodecs.BOOL, p -> p.isActive,
            PacketStructureManagerUpdateBlockEntityRequest::new
    );
    private final BlockPos pos;
    private final ResourceLocation structureManagerId;
    private final boolean toInitialize;
    private final boolean isActive;

    public PacketStructureManagerUpdateBlockEntityRequest(BlockPos pos, ResourceLocation structureManagerId, boolean toInitialize, boolean isActive) {
        this.pos = pos;
        this.structureManagerId = structureManagerId;
        this.toInitialize = toInitialize;
        this.isActive = isActive;
    }

    @Override
    public Type<PacketStructureManagerUpdateBlockEntityRequest> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketStructureManagerUpdateBlockEntityRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            ServerPlayer player = context.player();
            if (!player.isCreative()
                    || !player.canUseGameMasterBlocks()
                    || !(player.containerMenu instanceof StructureManagerScreenHandler menu)
                    || !menu.getPos().equals(pos)
                    || !menu.stillValid(player)
                    || !ServerPacketGuards.isLoadedAndNearby(player, pos)
                    || StructureManagerDataLookup.getStructureManagerData(player.level(), structureManagerId).isEmpty()
                    || !ServerPacketGuards.tryAcquire(player, ID.id(), 2)) {
                return;
            }
            if(player.level().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                blockEntity.updateData(structureManagerId, isActive, toInitialize);
            }
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerUpdateBlockEntityRequest::Tried to update the block entity.", e);
        }
    }
}
