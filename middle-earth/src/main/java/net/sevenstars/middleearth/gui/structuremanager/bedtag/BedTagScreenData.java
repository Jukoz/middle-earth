package net.sevenstars.middleearth.gui.structuremanager.bedtag;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;

import java.util.List;

// An empty type is a bed nobody is born in, an empty job is the routine of the type. The types are sent because the client does not know the nests
public record BedTagScreenData(BlockPos pos, String type, String job, List<String> types) {
    public static final int MAXIMUM_LENGTH = 128;
    public static final PacketCodec<RegistryByteBuf, BedTagScreenData> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, BedTagScreenData::pos,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::type,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::job,
            PacketCodecs.string(MAXIMUM_LENGTH).collect(PacketCodecs.toList()), BedTagScreenData::types,
            BedTagScreenData::new
    );
}
