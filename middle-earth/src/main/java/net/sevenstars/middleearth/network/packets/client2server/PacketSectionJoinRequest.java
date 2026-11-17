package net.sevenstars.middleearth.network.packets.client2server;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.api.network.contexts.ServerPacketContext;
import net.sevenstars.api.network.packets.ClientToServerPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.commands.custom.BuildSectionEditing;
import net.sevenstars.middleearth.commands.custom.JigsawFits;
import net.sevenstars.middleearth.network.packets.server2client.PacketJigsawFits;

// Makes the piece of one block target the piece of another where they meet. Either two plugs are linked, or two blocks
// side by side in two sections become a pair of plugs
public class PacketSectionJoinRequest extends ClientToServerPacket<PacketSectionJoinRequest> {
    public static final Id<PacketSectionJoinRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "section_join_request"));

    public static final PacketCodec<RegistryByteBuf, PacketSectionJoinRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.asker,
            BlockPos.PACKET_CODEC, p -> p.answerer,
            PacketSectionJoinRequest::new
    );

    private final BlockPos asker;
    private final BlockPos answerer;

    public PacketSectionJoinRequest(BlockPos asker, BlockPos answerer) {
        this.asker = asker;
        this.answerer = answerer;
    }

    @Override
    public Id<PacketSectionJoinRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketSectionJoinRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getEntityWorld().getServer().execute(() -> {
            // The last plug clicked is the one the builder is at
            if(!StructureEditing.mayEdit(player, answerer) || !(player.getEntityWorld() instanceof ServerWorld world))
                return;
            if(BuildSectionEditing.join(world, player, asker, answerer))
                ServerPlayNetworking.send(player, new PacketJigsawFits(asker, JigsawFits.of(world, player, asker)));
        });
    }
}
