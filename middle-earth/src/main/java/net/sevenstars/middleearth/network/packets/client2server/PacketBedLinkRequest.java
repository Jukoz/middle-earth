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
import net.sevenstars.middleearth.commands.custom.BedTagEditing;

import java.util.List;

// Until the first manual link, its links are auto-found ones only the drawing client knows
public class PacketBedLinkRequest extends ClientToServerPacket<PacketBedLinkRequest> {
    public static final Id<PacketBedLinkRequest> ID = new Id<>(Identifier.of(MiddleEarth.MOD_ID, "bed_link_request"));

    public static final PacketCodec<RegistryByteBuf, PacketBedLinkRequest> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.bed,
            BlockPos.PACKET_CODEC, p -> p.target,
            BlockPos.PACKET_CODEC.collect(PacketCodecs.toList(BedTagEditing.MAXIMUM_LINKS)), p -> p.automatic,
            PacketBedLinkRequest::new
    );

    private final BlockPos bed;
    private final BlockPos target;
    private final List<BlockPos> automatic;

    public PacketBedLinkRequest(BlockPos bed, BlockPos target, List<BlockPos> automatic) {
        this.bed = bed;
        this.target = target;
        this.automatic = automatic;
    }

    @Override
    public Id<PacketBedLinkRequest> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketBedLinkRequest> streamCodec() {
        return CODEC;
    }

    @Override
    public void process(ServerPacketContext context) {
        ServerPlayerEntity player = context.player();
        player.getEntityWorld().getServer().execute(() -> BedTagEditing.link(player, bed, target, automatic));
    }
}
