package net.sevenstars.middleearth.resources.datas.structure_beds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Optional;

// The nests of one structure template and the beds that go with each, worked out from the template when data is generated.
// Positions are those of the template, before it is turned and placed
public record StructureBedData(List<Nest> nests) {
    public record Nest(BlockPos pos, Identifier id, List<BlockPos> beds, Optional<BlockPos> store) {
        public static final Codec<Nest> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.fieldOf("pos").forGetter(Nest::pos),
                Identifier.CODEC.fieldOf("id").forGetter(Nest::id),
                Codec.list(BlockPos.CODEC).fieldOf("beds").forGetter(Nest::beds),
                BlockPos.CODEC.optionalFieldOf("store").forGetter(Nest::store)
        ).apply(instance, Nest::new));
    }

    public static final Codec<StructureBedData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.list(Nest.CODEC).fieldOf("nests").forGetter(StructureBedData::nests)
    ).apply(instance, StructureBedData::new));
}
