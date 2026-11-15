package net.sevenstars.middleearth.network.packets.server2client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.api.network.contexts.ClientPacketContext;
import net.sevenstars.api.network.packets.ServerToClientPacket;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.client.renderer.BuildersToolSelection;
import net.sevenstars.middleearth.commands.custom.JigsawFits;

import java.util.List;

public class PacketJigsawFits extends ServerToClientPacket<PacketJigsawFits> {
    public static final Id<PacketJigsawFits> ID = new Id<>(MiddleEarth.id("jigsaw_fits"));

    public static final PacketCodec<RegistryByteBuf, PacketJigsawFits> CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, p -> p.pos,
            BlockPos.PACKET_CODEC.collect(PacketCodecs.toList(JigsawFits.MAXIMUM_FITS)), p -> p.fits,
            PacketJigsawFits::new
    );

    private final BlockPos pos;
    private final List<BlockPos> fits;

    public PacketJigsawFits(BlockPos pos, List<BlockPos> fits) {
        this.pos = pos;
        this.fits = fits;
    }

    @Override
    public Id<PacketJigsawFits> getId() {
        return ID;
    }

    @Override
    public PacketCodec<RegistryByteBuf, PacketJigsawFits> streamCodec() {
        return CODEC;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        BuildersToolSelection.showJigsawFits(pos, fits);
    }
}
