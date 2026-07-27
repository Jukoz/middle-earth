package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.gui.structuremanager.StructureManagerScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.ServerPacketGuards;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;

public class PacketStructureManagerRespawnEntities extends ClientToServerPacket<PacketStructureManagerRespawnEntities> {
    public static final Type<PacketStructureManagerRespawnEntities> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "structure_manager_respawn_entities"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketStructureManagerRespawnEntities> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, p -> p.pos,
            PacketStructureManagerRespawnEntities::new
    );

    private final BlockPos pos;

    public PacketStructureManagerRespawnEntities(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Type<PacketStructureManagerRespawnEntities> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketStructureManagerRespawnEntities> streamCodec() {
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
                    || !ServerPacketGuards.tryAcquire(player, ID.id(), 40)) {
                return;
            }
            if(player.level().getBlockEntity(pos) instanceof StructureManagerBlockEntity blockEntity){
                blockEntity.respawnAllEntities();
            }
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureManagerRespawnEntities::Tried to reset all entities.", e);
        }
    }
}
