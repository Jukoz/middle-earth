package net.sevenstars.middleearth.network.packets.C2S;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.nest.StructureNestBlockEntity;
import net.sevenstars.middleearth.gui.structuremanager.structurenest.StructureNestScreenHandler;
import net.sevenstars.middleearth.network.contexts.ServerPacketContext;
import net.sevenstars.middleearth.network.handlers.ServerPacketGuards;
import net.sevenstars.middleearth.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerDataLookup;

import java.util.Objects;
import java.util.Optional;

public class PacketStructureNestUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureNestUpdateBlockEntityRequest>
{
    public static final Type<PacketStructureNestUpdateBlockEntityRequest> ID = new Type<>(ResourceLocation.fromNamespaceAndPath(MiddleEarth.MOD_ID, "structure_nest_update_block_entity_request"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PacketStructureNestUpdateBlockEntityRequest> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, p -> p.pos,
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), p -> p.getStructureManagerId(),
            ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), p -> p.getStructureNestId(),
            ByteBufCodecs.INT, p -> p.spawnRadius,
            ByteBufCodecs.BOOL, p -> p.isEnabled,
            PacketStructureNestUpdateBlockEntityRequest::new
    );

    private Optional<ResourceLocation> getStructureManagerId() {
        return Optional.ofNullable(structureManagerId);
    }

    private Optional<ResourceLocation> getStructureNestId() {
        return Optional.ofNullable(structureNestId);
    }

    private final BlockPos pos;
    private final ResourceLocation structureManagerId;
    private final ResourceLocation structureNestId;
    private final int spawnRadius;
    private final boolean isEnabled;

    public PacketStructureNestUpdateBlockEntityRequest(BlockPos pos, Optional<ResourceLocation> structureManagerId, Optional<ResourceLocation> structureNestId, int spawnRadius, boolean isEnabled) {
        this.pos = pos;
        this.spawnRadius = spawnRadius;
        this.isEnabled = isEnabled;

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
    public Type<PacketStructureNestUpdateBlockEntityRequest> type() {
        return ID;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, PacketStructureNestUpdateBlockEntityRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            ServerPlayer player = context.player();
            if (!player.isCreative()
                    || !player.canUseGameMasterBlocks()
                    || !(player.containerMenu instanceof StructureNestScreenHandler menu)
                    || !menu.getPos().equals(pos)
                    || !menu.stillValid(player)
                    || !ServerPacketGuards.isLoadedAndNearby(player, pos)) {
                return;
            }

            if (structureManagerId == null) {
                if (structureNestId != null) {
                    return;
                }
            } else {
                var managerData = StructureManagerDataLookup
                        .getStructureManagerData(player.level(), structureManagerId)
                        .orElse(null);
                if (managerData == null
                        || (structureNestId != null && managerData.getNpcSpawnNest().stream()
                        .noneMatch(nest -> Objects.equals(nest.getId(), structureNestId)))) {
                    return;
                }
            }
            if (!ServerPacketGuards.tryAcquire(player, ID.id(), 2)) {
                return;
            }

            if(player.level().getBlockEntity(pos) instanceof StructureNestBlockEntity blockEntity){
                blockEntity.applySettings(
                        structureManagerId,
                        structureNestId,
                        Mth.clamp(spawnRadius, 0, 128),
                        isEnabled
                );
            }
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketStructureNestUpdateBlockEntityRequest::Tried to update the block entity.", e);
        }
    }
}
