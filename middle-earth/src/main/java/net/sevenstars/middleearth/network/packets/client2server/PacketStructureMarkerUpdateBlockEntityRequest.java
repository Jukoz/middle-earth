package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;

public class PacketStructureMarkerUpdateBlockEntityRequest extends ClientToServerPacket<PacketStructureMarkerUpdateBlockEntityRequest> {
    public static final Id<PacketStructureMarkerUpdateBlockEntityRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_marker_update_block_entity_request"));

    public static final PacketCodec<RegistryByteBuf, PacketStructureMarkerUpdateBlockEntityRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketCodecs.indexed(index -> MarkerKind.values()[index], MarkerKind::ordinal), p -> p.kind,
            PacketStructureMarkerUpdateBlockEntityRequest::new
    );

    private final BlockPos pos;
    private final MarkerKind kind;

    public PacketStructureMarkerUpdateBlockEntityRequest(BlockPos pos, MarkerKind kind) {
        this.pos = pos;
        this.kind = kind;
    }

    @Override
    public Id<PacketStructureMarkerUpdateBlockEntityRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureMarkerUpdateBlockEntityRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getServer().execute(() -> {
            if(!player.isCreative() || !player.isCreativeLevelTwoOp() || !player.getBlockPos().isWithinDistance(pos, 8))
                return;
            if(player.getWorld().getBlockEntity(pos) instanceof StructureMarkerBlockEntity marker)
                marker.setKind(kind);
        });
    }
}
