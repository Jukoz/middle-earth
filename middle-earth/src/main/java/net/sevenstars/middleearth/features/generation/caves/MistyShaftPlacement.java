package net.sevenstars.middleearth.features.generation.caves;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;
import net.minecraft.world.gen.chunk.placement.StructurePlacement;
import net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator;
import net.minecraft.world.gen.chunk.placement.StructurePlacementType;
import net.minecraft.util.math.Vec3i;

import java.util.Optional;

public class MistyShaftPlacement extends StructurePlacement {
    public static final MapCodec<MistyShaftPlacement> CODEC = RecordCodecBuilder.mapCodec(
            instance -> buildCodec(instance).apply(instance, MistyShaftPlacement::new));

    private MistyShaftPlacement(Vec3i locateOffset, FrequencyReductionMethod frequencyReductionMethod, float frequency, int salt, Optional<ExclusionZone> exclusionZone) {
        super(locateOffset, frequencyReductionMethod, frequency, salt, exclusionZone);
    }

    @Override
    protected boolean isStartChunk(StructurePlacementCalculator calculator, int chunkX, int chunkZ) {
        // the structure seed is the world seed, the same one buildSurface hands to MistyCaves
        return new MistyCaves(calculator.getStructureSeed()).roadCrossingIn(MiddleEarthMapRuntime.getInstance(), chunkX, chunkZ) != null;
    }

    @Override
    public StructurePlacementType<?> getType() {
        return Caves.MISTY_SHAFT_PLACEMENT;
    }
}
