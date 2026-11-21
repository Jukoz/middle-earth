package net.sevenstars.middleearth.network.packets.client2server;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.function.ValueLists;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.BuildSections;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.commands.custom.BuildSectionEditing;

import java.util.function.IntFunction;

public class PacketSectionEditRequest extends ClientToServerPacket<PacketSectionEditRequest> {
    public static final Id<PacketSectionEditRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "section_edit_request"));

    public enum Edit {
        RENAME,
        REMOVE;

        private static final IntFunction<Edit> BY_INDEX = ValueLists.createIndexToValueFunction(Edit::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);
        public static final PacketCodec<ByteBuf, Edit> PACKET_CODEC = PacketCodecs.indexed(BY_INDEX, Edit::ordinal);
    }

    public static final PacketCodec<RegistryByteBuf, PacketSectionEditRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            Edit.PACKET_CODEC, p -> p.what,
            PacketCodecs.string(BuildSections.MAXIMUM_NAME_LENGTH), p -> p.name,
            PacketSectionEditRequest::new
    );

    private final BlockPos pos;
    private final Edit what;
    private final String name;

    public PacketSectionEditRequest(BlockPos pos, Edit what, String name) {
        this.pos = pos;
        this.what = what;
        this.name = name;
    }

    @Override
    public Id<PacketSectionEditRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketSectionEditRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getEntityWorld().getServer().execute(() -> {
            if(!StructureEditing.mayEdit(player, pos) || !(player.getEntityWorld() instanceof ServerWorld world))
                return;
            BuildSectionEditing.edit(world, player, pos, what == Edit.REMOVE, name);
        });
    }
}
