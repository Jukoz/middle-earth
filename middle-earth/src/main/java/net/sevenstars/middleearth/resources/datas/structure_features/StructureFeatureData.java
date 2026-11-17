package net.sevenstars.middleearth.resources.datas.structure_features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.resources.datas.npc_classes.data.StationKind;

import java.util.List;
import java.util.Optional;

// What one structure template holds that npcs make use of, worked out from the template when data is generated.
// Beds are every bed head. Positions are those of the template, before it is rotated and placed.
// Tables are the seats around each table, places to sit down together.
// Stations are the places of work: a forge, an oven, a loom, a plot of field.
// Doors are the lower halves.
// Managers are the structure manager blocks, with the data each one spawns from. That way whose bed a bed is can be
// worked out without loading its manager
public record StructureFeatureData(List<Bed> beds, List<List<BlockPos>> tables, List<Station> stations, List<BlockPos> seats,
                                   List<BlockPos> doors, List<BlockPos> ladders, List<Marker> markers, List<Manager> managers) {
    // The data is the place a manager was given before beds stored a place of their own. Beds tagged since then don't
    // need it
    public record Manager(BlockPos pos, Optional<Identifier> data) {
        public static final Codec<Manager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.fieldOf("pos").forGetter(Manager::pos),
                Identifier.CODEC.optionalFieldOf("data").forGetter(Manager::data)
        ).apply(instance, Manager::new));
    }

    public record Station(StationKind kind, BlockPos pos) {
        public static final Codec<Station> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                StationKind.CODEC.fieldOf("kind").forGetter(Station::kind),
                BlockPos.CODEC.fieldOf("pos").forGetter(Station::pos)
        ).apply(instance, Station::new));
    }

    // The value says what to spawn, a nest or an entity type, and the count says how many. Animals come back over time only if respawns is set
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

    // The tag says who spawns from the bed, and a bed with none is free for travellers to sleep in. The store is where
    // what its npc brings home is put away: the one the builder picked, or else the one nearest its place of work.
    // Links are the places the builder tied the bed to, stored as positions like the rest, and are the ones to use
    // when manual. The manager is the position of the manager block the builder once tied the bed to, if any
    public record Bed(BlockPos pos, Optional<BedTag> tag, Optional<BlockPos> store, List<BlockPos> links, boolean manual, Optional<BlockPos> manager) {
        public static final Codec<Bed> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.fieldOf("pos").forGetter(Bed::pos),
                BedTag.CODEC.optionalFieldOf("tag").forGetter(Bed::tag),
                BlockPos.CODEC.optionalFieldOf("store").forGetter(Bed::store),
                BlockPos.CODEC.listOf().optionalFieldOf("links", List.of()).forGetter(Bed::links),
                Codec.BOOL.optionalFieldOf("manual", false).forGetter(Bed::manual),
                BlockPos.CODEC.optionalFieldOf("manager").forGetter(Bed::manager)
        ).apply(instance, Bed::new));
    }

    public static final Codec<StructureFeatureData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Bed.CODEC).optionalFieldOf("beds", List.of()).forGetter(StructureFeatureData::beds),
            Codec.list(Codec.list(BlockPos.CODEC)).optionalFieldOf("tables", List.of()).forGetter(StructureFeatureData::tables),
            Codec.list(Station.CODEC).optionalFieldOf("stations", List.of()).forGetter(StructureFeatureData::stations),
            Codec.list(BlockPos.CODEC).optionalFieldOf("seats", List.of()).forGetter(StructureFeatureData::seats),
            Codec.list(BlockPos.CODEC).optionalFieldOf("doors", List.of()).forGetter(StructureFeatureData::doors),
            Codec.list(BlockPos.CODEC).optionalFieldOf("ladders", List.of()).forGetter(StructureFeatureData::ladders),
            Codec.list(Marker.CODEC).optionalFieldOf("markers", List.of()).forGetter(StructureFeatureData::markers),
            Codec.list(Manager.CODEC).optionalFieldOf("managers", List.of()).forGetter(StructureFeatureData::managers)
    ).apply(instance, StructureFeatureData::new));
}
