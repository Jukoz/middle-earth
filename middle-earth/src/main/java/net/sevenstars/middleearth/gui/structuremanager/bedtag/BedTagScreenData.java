package net.sevenstars.middleearth.gui.structuremanager.bedtag;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.beds.BedOccupant;

import java.util.List;

// The bed's current tag (an empty nest kind means nobody spawns in the bed) and every occupant each place can house.
// The place is the bed's own. If the bed has none yet it's the neighbouring beds' place, or empty
public record BedTagScreenData(BlockPos pos, String place, String nestKind, String job, String npcClass, List<BedOccupant> occupants) {
    public static final int MAXIMUM_LENGTH = 128;
    public static final PacketCodec<RegistryByteBuf, BedTagScreenData> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, BedTagScreenData::pos,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::place,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::nestKind,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::job,
            PacketCodecs.string(MAXIMUM_LENGTH), BedTagScreenData::npcClass,
            BedOccupant.PACKET_CODEC.collect(PacketCodecs.toList(BedOccupant.MAXIMUM_OCCUPANTS)), BedTagScreenData::occupants,
            BedTagScreenData::new
    );
}
