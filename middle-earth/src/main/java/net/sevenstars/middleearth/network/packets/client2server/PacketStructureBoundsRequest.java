package net.sevenstars.middleearth.network.packets.client2server;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.BuildSections;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.commands.custom.BuildSectionEditing;

// Box between two corners becomes the build's saved part; with a name, a new section instead
public class PacketStructureBoundsRequest extends ClientToServerPacket<PacketStructureBoundsRequest> {
    public static final Id<PacketStructureBoundsRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "structure_bounds_request"));


    public static final PacketCodec<RegistryByteBuf, PacketStructureBoundsRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.anchor,
            BlockPos.PACKET_CODEC, p -> p.first,
            BlockPos.PACKET_CODEC, p -> p.second,
            PacketCodecs.string(BuildSections.MAXIMUM_NAME_LENGTH), p -> p.name,
            PacketStructureBoundsRequest::new
    );

    private final BlockPos anchor;
    private final BlockPos first;
    private final BlockPos second;
    private final String name;

    public PacketStructureBoundsRequest(BlockPos anchor, BlockPos first, BlockPos second, String name) {
        this.name = name;
        this.anchor = anchor;
        this.first = first;
        this.second = second;
    }

    @Override
    public Id<PacketStructureBoundsRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketStructureBoundsRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getEntityWorld().getServer().execute(() -> {
            if(!StructureEditing.mayEdit(player, second) || !(player.getEntityWorld() instanceof ServerWorld world))
                return;
            BuildSectionEditing.setBounds(world, player, anchor, first, second, name);
        });
    }
}
