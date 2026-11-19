package net.sevenstars.middleearth.features.generation.caves;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.MapCodec;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator.CaveProbe;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.List;
import java.util.Optional;

public final class MistyShaftBridgeStructure extends Structure {
    public static final MapCodec<MistyShaftBridgeStructure> CODEC = createCodec(MistyShaftBridgeStructure::new);

    private MistyShaftBridgeStructure(Config config) {
        super(config);
    }

    @Override
    protected Optional<StructurePosition> getStructurePosition(Context context) {
        MistyCaves caves = new MistyCaves(context.seed());
        MistyCaves.Road road = caves.roadCrossingIn(MiddleEarthMapRuntime.getInstance(), context.chunkPos().x, context.chunkPos().z);
        if (road == null) return Optional.empty();
        MistyCaves.BridgeFit fit = caves.bridgeFit(MiddleEarthMapRuntime.getInstance(), road);
        if (fit == null) return Optional.empty();
        int hash = MistyCaves.mix(road.cx, road.cz, 4242);
        Direction positive = road.alongX ? Direction.EAST : Direction.SOUTH;
        BlockPos centre = new BlockPos(road.cx, road.deckY, road.cz);
        int size = fit.size();
        int shift = fit.shift();

        boolean rotten = (MistyCaves.mix(hash, 1, 0) & 3) == 0;
        boolean lampA = (MistyCaves.mix(hash, 2, 0) & 3) == 0;
        boolean lampB = (MistyCaves.mix(hash, 3, 0) & 3) == 0;
        Optional<List<StructurePiece>> pieces = GoblinBridge.bridge(context.structureTemplateManager(), null, size, rotten, positive, centre.offset(positive, shift), lampA, lampB, false, false, true);
        if (pieces.isEmpty()) return Optional.empty();
        StructurePiecesCollector collector = new StructurePiecesCollector();
        pieces.get().forEach(collector::addPiece);
        return Optional.of(new StructurePosition(centre, Either.right(collector)));
    }

    // the road only exists where the bridge fits, and the biome at that depth can be any cave biome
    @Override
    public Optional<StructurePosition> getValidStructurePosition(Context context) {
        return getStructurePosition(context);
    }

    @Override
    public StructureType<?> getType() {
        return Caves.MISTY_SHAFT_BRIDGE;
    }
}
