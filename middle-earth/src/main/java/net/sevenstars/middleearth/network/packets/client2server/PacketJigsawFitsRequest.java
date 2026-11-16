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
import net.sevenstars.middleearth.commands.custom.JigsawFits;
import net.sevenstars.middleearth.network.packets.server2client.PacketJigsawFits;

// Asks which plugs fit the clicked jigsaw block: those in the builder's loaded structure, or the nearby ones when the
// block is in no loaded piece. Only the server knows the pools
public class PacketJigsawFitsRequest extends ClientToServerPacket<PacketJigsawFitsRequest> {
    public static final Id<PacketJigsawFitsRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "jigsaw_fits_request"));

    public static final PacketCodec<RegistryByteBuf, PacketJigsawFitsRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            PacketJigsawFitsRequest::new
    );

    private final BlockPos pos;

    public PacketJigsawFitsRequest(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Id<PacketJigsawFitsRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketJigsawFitsRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getServer().execute(() -> {
            if(!StructureEditing.mayEdit(player, pos) || !(player.getWorld() instanceof ServerWorld world))
                return;
            ServerPlayNetworking.send(player, new PacketJigsawFits(pos, JigsawFits.of(world, player, pos)));
        });
    }
}
