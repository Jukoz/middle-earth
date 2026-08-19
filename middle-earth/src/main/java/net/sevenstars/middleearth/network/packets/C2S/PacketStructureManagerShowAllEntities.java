package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.ServerPacketGuards;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class PacketStructureManagerShowAllEntities extends ClientToServerPacket<PacketStructureManagerShowAllEntities> {
    public static final CustomPacketPayload.Type<PacketStructureManagerShowAllEntities> ID = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "structure_manager_show_all_entities"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketStructureManagerShowAllEntities> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, p -> p.pos,
            PacketStructureManagerShowAllEntities::new
    );

    private final BlockPos pos;

    public PacketStructureManagerShowAllEntities(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Type<PacketStructureManagerShowAllEntities> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketStructureManagerShowAllEntities> streamCodec() {
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
                    || !ServerPacketGuards.tryAcquire(player, ID.id(), 20)) {
                return;
            }
            if(player.level().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                blockEntity.showAllEntities();
            }
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerShowAllEntities::Tried to show all entities.", e);
        }
    }
}
