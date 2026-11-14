package net.sevenstars.middleearth.gui.structuremanager.structuremarker;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.block.special.structureManager.marker.StructureMarkerBlockEntity;

import java.util.List;
import java.util.TreeSet;

// The lists are sent because the client does not know the nests
public record StructureMarkerScreenData(BlockPos pos, boolean isNew, MarkerKind kind, String value, int count, boolean respawns,
                                        Direction facing, List<String> types, List<String> animals) {
    private static final List<EntityType<?>> EXTRA_ANIMALS = List.of(EntityType.HORSE, EntityType.DONKEY, EntityType.MULE, EntityType.LLAMA, EntityType.GOAT);
    private static final int MAXIMUM_LIST_SIZE = 1024;
    private static List<String> animalIds;

    public static final PacketCodec<RegistryByteBuf, StructureMarkerScreenData> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC, StructureMarkerScreenData::pos,
            PacketCodecs.BOOLEAN, StructureMarkerScreenData::isNew,
            PacketCodecs.indexed(index -> MarkerKind.values()[index], MarkerKind::ordinal), StructureMarkerScreenData::kind,
            PacketCodecs.string(StructureMarkerBlockEntity.MAXIMUM_VALUE_LENGTH), StructureMarkerScreenData::value,
            PacketCodecs.VAR_INT, StructureMarkerScreenData::count,
            PacketCodecs.BOOLEAN, StructureMarkerScreenData::respawns,
            PacketCodecs.indexed(Direction::byIndex, Direction::getIndex), StructureMarkerScreenData::facing,
            PacketCodecs.string(StructureMarkerBlockEntity.MAXIMUM_VALUE_LENGTH).collect(PacketCodecs.toList(MAXIMUM_LIST_SIZE)), StructureMarkerScreenData::types,
            PacketCodecs.string(StructureMarkerBlockEntity.MAXIMUM_VALUE_LENGTH).collect(PacketCodecs.toList(MAXIMUM_LIST_SIZE)), StructureMarkerScreenData::animals,
            StructureMarkerScreenData::new
    );

    public static StructureMarkerScreenData create(BlockPos pos, boolean isNew, MarkerKind kind, String value, int count, boolean respawns, Direction facing) {
        return new StructureMarkerScreenData(pos, isNew, kind, value, count, respawns, facing, BedTag.types(), allAnimals());
    }

    // The animals that live in the world, which are the creatures plus the rideable ones that are not
    public static synchronized List<String> allAnimals() {
        if(animalIds == null) {
            TreeSet<String> found = new TreeSet<>();
            for(EntityType<?> type : Registries.ENTITY_TYPE) {
                if(type.getSpawnGroup() == SpawnGroup.CREATURE)
                    found.add(Registries.ENTITY_TYPE.getId(type).toString());
            }
            for(EntityType<?> type : EXTRA_ANIMALS)
                found.add(Registries.ENTITY_TYPE.getId(type).toString());
            animalIds = List.copyOf(found);
        }
        return animalIds;
    }
}
