package net.sevenstars.middleearth.gui.structuremanager.structuremarker;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;

public record StructureMarkerScreenData(BlockPos pos, MarkerKind kind, String value, int count, boolean respawns) {
    public static final PacketCodec<RegistryByteBuf, StructureMarkerScreenData> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, StructureMarkerScreenData::pos,
            PacketCodecs.indexed(index -> MarkerKind.values()[index], MarkerKind::ordinal), StructureMarkerScreenData::kind,
            PacketCodecs.string(StructureMarkerBlockEntity.MAXIMUM_VALUE_LENGTH), StructureMarkerScreenData::value,
            PacketCodecs.VAR_INT, StructureMarkerScreenData::count,
            PacketCodecs.BOOLEAN, StructureMarkerScreenData::respawns,
            StructureMarkerScreenData::new
    );
}
