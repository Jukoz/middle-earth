package net.sevenstars.middleearth.gui.structuremanager.bedtag;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;

import java.util.List;

// An empty nest kind is a bed nobody is born in, an empty job is the routine of the class, an empty class is one from the nest. The nest kinds are sent because the client does not know the nests
public record BedTagScreenData(BlockPos pos, String nestKind, String job, String npcClass, List<String> nestKinds) {
    public static final int MAXIMUM_LENGTH = 128;
    public static final PacketCodec<RegistryByteBuf, BedTagScreenData> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, BedTagScreenData::pos,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::nestKind,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::job,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::npcClass,
            PacketCodecs.string(MAXIMUM_LENGTH).collect(PacketCodecs.toList()), BedTagScreenData::nestKinds,
            BedTagScreenData::new
    );
}
