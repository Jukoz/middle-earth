package net.sevenstars.middleearth.gui.structuremanager.bedtag;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.beds.BedOccupant;

import java.util.List;

// What the bed is tagged with now, an empty nest kind for a bed nobody is born in, and everybody the place it is in can
// house. Those are worked out on the server, which knows the place and its nests
public record BedTagScreenData(BlockPos pos, String nestKind, String job, String npcClass, List<BedOccupant> occupants) {
    public static final int MAXIMUM_LENGTH = 128;
    public static final PacketCodec<RegistryByteBuf, BedTagScreenData> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, BedTagScreenData::pos,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::nestKind,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::job,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::npcClass,
            BedOccupant.PACKET_CODEC.collect(PacketCodecs.toList(BedOccupant.MAXIMUM_OCCUPANTS)), BedTagScreenData::occupants,
            BedTagScreenData::new
    );
}
