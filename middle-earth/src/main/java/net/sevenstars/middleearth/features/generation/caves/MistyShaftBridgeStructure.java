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

    private static final int MAX_SCAN = 14; // blocks from the centre to look for the rim

    private MistyShaftBridgeStructure(Config config) {
        super(config);
    }

    @Override
    protected Optional<StructurePosition> getStructurePosition(Context context) {
        MistyCaves caves = new MistyCaves(context.seed());
        MistyCaves.Shaft shaft = caves.shaftCenteredIn(MiddleEarthMapRuntime.getInstance(), context.chunkPos().x, context.chunkPos().z);
        if (shaft == null) return Optional.empty();
        MistyCaves.Road road = caves.roadOf(shaft);
        if (road == null) return Optional.empty();
        CaveProbe probe = new CaveProbe(context.seed(), context.world());
        int hash = MistyCaves.mix(shaft.x, shaft.z, 4242);
        Direction positive = road.alongX ? Direction.EAST : Direction.SOUTH;
        Direction negative = positive.getOpposite();
        BlockPos centre = new BlockPos(shaft.x, road.deckY, shaft.z);
        int y = centre.getY();
        int toPositive = rim(probe, centre, positive);
        int toNegative = rim(probe, centre, negative);
        if (toPositive < 0 || toNegative < 0) return Optional.empty();
        int size = GoblinBridge.sizeFor(toPositive + toNegative - 1);
        if (size < 0) return Optional.empty();
        int shift = (toPositive - toNegative) / 2;
        int half = GoblinBridge.SPAN_LENGTH[size] / 2;
        if (Math.abs(shift + half - (toPositive - 1)) > GoblinBridge.SPAN_SLACK || Math.abs(half - shift - (toNegative - 1)) > GoblinBridge.SPAN_SLACK) return Optional.empty();

        boolean rotten = (MistyCaves.mix(hash, 1, 0) & 3) == 0;
        boolean lampA = (MistyCaves.mix(hash, 2, 0) & 3) == 0;
        boolean lampB = (MistyCaves.mix(hash, 3, 0) & 3) == 0;
        Optional<List<StructurePiece>> pieces = GoblinBridge.bridge(context.structureTemplateManager(), probe, size, rotten, positive, centre.offset(positive, shift), lampA, lampB, true, true, true);
        if (pieces.isEmpty()) return Optional.empty();
        StructurePiecesCollector collector = new StructurePiecesCollector();
        pieces.get().forEach(collector::addPiece);
        return Optional.of(new StructurePosition(centre, Either.right(collector)));
    }

    // distance from the centre to the first block of floor at deck level or just below, or -1 when there is none in reach
    private static int rim(CaveProbe probe, BlockPos centre, Direction direction) {
        for (int k = 1; k <= MAX_SCAN; k++) {
            int x = centre.getX() + direction.getOffsetX() * k;
            int z = centre.getZ() + direction.getOffsetZ() * k;
            if (probe.solid(x, centre.getY(), z) || probe.solid(x, centre.getY() - 1, z)) return k;
        }
        return -1;
    }

    @Override
    public StructureType<?> getType() {
        return Caves.MISTY_SHAFT_BRIDGE;
    }
}
