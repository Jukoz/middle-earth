package net.sevenstars.middleearth.resources.datas.structure_features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.npc_types.data.StationKind;

import java.util.List;
import java.util.Optional;

// What one structure template holds that people make use of, worked out from the template when data is generated.
// Positions are those of the template, before it is turned and placed
// Tables are the seats around one table each, places to sit down together. Seats is every seat there is
// Stations are the places of work: a forge, an oven, a loom, a plot of field
// Doors are the lower halves
public record StructureFeatureData(List<Nest> nests, List<List<BlockPos>> tables, List<Station> stations, List<BlockPos> seats,
                                   List<BlockPos> doors, List<BlockPos> ladders, List<Marker> markers) {
    public record Station(StationKind kind, BlockPos pos) {
        public static final Codec<Station> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                StationKind.CODEC.fieldOf("kind").forGetter(Station::kind),
                BlockPos.CODEC.fieldOf("pos").forGetter(Station::pos)
        ).apply(instance, Station::new));
    }

    // The value says what to spawn, a nest or an entity type, and the count how many. Animals come back over time only where it respawns
    public record Marker(MarkerKind kind, BlockPos pos, Direction facing, String value, int count, boolean respawns) {
        public static final Codec<Marker> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                MarkerKind.CODEC.fieldOf("kind").forGetter(Marker::kind),
                BlockPos.CODEC.fieldOf("pos").forGetter(Marker::pos),
                Direction.CODEC.optionalFieldOf("facing", Direction.NORTH).forGetter(Marker::facing),
                Codec.STRING.optionalFieldOf("value", "").forGetter(Marker::value),
                Codec.INT.optionalFieldOf("count", 1).forGetter(Marker::count),
                Codec.BOOL.optionalFieldOf("respawns", false).forGetter(Marker::respawns)
        ).apply(instance, Marker::new));

        public Marker(MarkerKind kind, BlockPos pos, Direction facing) {
            this(kind, pos, facing, "", 1, false);
        }
    }

    public record Nest(BlockPos pos, Identifier id, List<BlockPos> beds, Optional<BlockPos> store) {
        public static final Codec<Nest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.fieldOf("pos").forGetter(Nest::pos),
                Identifier.CODEC.fieldOf("id").forGetter(Nest::id),
                Codec.list(BlockPos.CODEC).fieldOf("beds").forGetter(Nest::beds),
                BlockPos.CODEC.optionalFieldOf("store").forGetter(Nest::store)
        ).apply(instance, Nest::new));
    }

    public static final Codec<StructureFeatureData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Nest.CODEC).optionalFieldOf("nests", List.of()).forGetter(StructureFeatureData::nests),
            Codec.list(Codec.list(BlockPos.CODEC)).optionalFieldOf("tables", List.of()).forGetter(StructureFeatureData::tables),
            Codec.list(Station.CODEC).optionalFieldOf("stations", List.of()).forGetter(StructureFeatureData::stations),
            Codec.list(BlockPos.CODEC).optionalFieldOf("seats", List.of()).forGetter(StructureFeatureData::seats),
            Codec.list(BlockPos.CODEC).optionalFieldOf("doors", List.of()).forGetter(StructureFeatureData::doors),
            Codec.list(BlockPos.CODEC).optionalFieldOf("ladders", List.of()).forGetter(StructureFeatureData::ladders),
            Codec.list(Marker.CODEC).optionalFieldOf("markers", List.of()).forGetter(StructureFeatureData::markers)
    ).apply(instance, StructureFeatureData::new));
}
