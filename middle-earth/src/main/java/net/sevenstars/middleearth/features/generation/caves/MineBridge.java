package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.JigsawBlock;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.generation.chunkgen.CaveProbe;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// span and landing pieces placed by hand, aligned through their jigsaw blocks
final class MineBridge {
    static final int[] SPAN_LENGTH = {13, 17, 21};
    static final int SPAN_SLACK = 2;
    private static final int FOOTING_REACH = 2; // rock this close under a footing's bottom layer holds it
    private static final int FOOTING_VOID = 4; // no column may leave more air than this under the footing

    private static final String[] SPAN_SIZES = {"short", "medium", "long"};
    private static final Identifier ANCHOR = MiddleEarth.id("bridge_anchor");
    private static final Identifier CENTER = MiddleEarth.id("bridge_center");

    private MineBridge() {
    }

    // cells a landing occupies along the span axis, or -1 when its template is missing
    static int landingLength(StructureTemplateManager manager, boolean lamp) {
        SinglePoolElement element = landing(lamp);
        List<StructureTemplate.JigsawBlockInfo> jigsaws = anchors(manager, element, BlockRotation.NONE);
        if (jigsaws.isEmpty()) return -1;
        BlockBox box = element.getBoundingBox(manager, BlockPos.ORIGIN, BlockRotation.NONE);
        return JigsawBlock.getFacing(jigsaws.get(0).info().state()).getAxis() == Direction.Axis.X ? box.getBlockCountX() : box.getBlockCountZ();
    }

    // span centred on the given block (the deck block), with a landing at each end; empty when a template is missing, or when a
    // landing that was asked to have support would hang over a void
    // the landing brings its own footing, so it needs only something under it: four in five of the columns under the footing have rock
    // within two blocks below its bottom layer, and none leaves more than four blocks of air, over the whole walkway width and length
    static Optional<List<StructurePiece>> bridge(StructureTemplateManager manager, CaveProbe probe, int size, boolean rotten, Direction positive,
                                                 BlockPos centre, boolean lampPositive, boolean lampNegative, boolean supportPositive, boolean supportNegative, boolean landNegative) {
        Direction.Axis axis = positive.getAxis();
        SinglePoolElement spanElement = span(size, rotten);
        BlockRotation rotation = axis == Direction.Axis.X ? BlockRotation.NONE : BlockRotation.CLOCKWISE_90;
        List<StructureTemplate.JigsawBlockInfo> all = spanElement.getStructureBlockInfos(manager, BlockPos.ORIGIN, rotation, Random.create(0));
        BlockPos middle = null;
        for (StructureTemplate.JigsawBlockInfo jigsaw : all) {
            if (CENTER.equals(jigsaw.name())) middle = jigsaw.info().pos();
        }
        List<StructureTemplate.JigsawBlockInfo> ends = anchors(manager, spanElement, rotation);
        if (middle == null || ends.size() != 2) return Optional.empty();
        BlockPos origin = centre.subtract(middle);
        List<StructurePiece> pieces = new ArrayList<>();
        pieces.add(piece(manager, spanElement, origin, rotation));
        for (int i = 0; i < 2; i++) {
            StructureTemplate.JigsawBlockInfo end = ends.get(i);
            Direction outward = JigsawBlock.getFacing(end.info().state());
            boolean forward = outward == positive;
            if (!forward && !landNegative) continue;
            BlockPos anchor = end.info().pos().add(origin);
            if ((forward ? supportPositive : supportNegative) && !footed(manager, probe, anchor.offset(outward), outward, anchor.getY(), forward ? lampPositive : lampNegative)) return Optional.empty();
            PoolStructurePiece landing = landing(manager, forward ? lampPositive : lampNegative, anchor, outward);
            if (landing == null) return Optional.empty();
            pieces.add(landing);
        }
        return Optional.of(pieces);
    }

    private static boolean footed(StructureTemplateManager manager, CaveProbe probe, BlockPos first, Direction outward, int deckY, boolean lamp) {
        SinglePoolElement element = landing(lamp);
        List<StructureTemplate.JigsawBlockInfo> jigsaws = anchors(manager, element, BlockRotation.NONE);
        if (jigsaws.isEmpty()) return false;
        // the jigsaw sits on the deck row of the landing, so its height above the template floor is the footing and post depth
        int bottom = deckY - jigsaws.get(0).info().pos().getY();
        int length = landingLength(manager, lamp);
        int sideX = outward.getOffsetZ();
        int sideZ = outward.getOffsetX();
        int supported = 0;
        int total = 0;
        for (int k = 0; k < length; k++) {
            for (int side = -2; side <= 2; side++) {
                total++;
                int x = first.getX() + outward.getOffsetX() * k + sideX * side;
                int z = first.getZ() + outward.getOffsetZ() * k + sideZ * side;
                int air = 0;
                while (air <= FOOTING_VOID && !probe.solid(x, bottom - 1 - air, z)) air++;
                if (air > FOOTING_VOID) return false;
                if (air < FOOTING_REACH) supported++;
            }
        }
        return supported * 5 >= total * 4;
    }

    // a landing whose jigsaw sits next to the given anchor cell and faces back at it
    static PoolStructurePiece landing(StructureTemplateManager manager, boolean lamp, BlockPos anchor, Direction outward) {
        SinglePoolElement element = landing(lamp);
        for (BlockRotation rotation : BlockRotation.values()) {
            List<StructureTemplate.JigsawBlockInfo> jigsaws = anchors(manager, element, rotation);
            if (jigsaws.isEmpty()) return null;
            StructureTemplate.JigsawBlockInfo jigsaw = jigsaws.get(0);
            if (JigsawBlock.getFacing(jigsaw.info().state()) != outward.getOpposite()) continue;
            return piece(manager, element, anchor.offset(outward).subtract(jigsaw.info().pos()), rotation);
        }
        return null;
    }

    private static List<StructureTemplate.JigsawBlockInfo> anchors(StructureTemplateManager manager, StructurePoolElement element, BlockRotation rotation) {
        return element.getStructureBlockInfos(manager, BlockPos.ORIGIN, rotation, Random.create(0)).stream()
                .filter(jigsaw -> ANCHOR.equals(jigsaw.name())).toList();
    }

    private static PoolStructurePiece piece(StructureTemplateManager manager, StructurePoolElement element, BlockPos pos, BlockRotation rotation) {
        return new PoolStructurePiece(manager, element, pos, element.getGroundLevelDelta(), rotation,
                element.getBoundingBox(manager, pos, rotation), StructureLiquidSettings.IGNORE_WATERLOGGING);
    }

    private static SinglePoolElement span(int size, boolean rotten) {
        return StructurePoolElement.ofSingle("middle-earth:misty/dwarf_mine/bridge_span_" + SPAN_SIZES[size] + (rotten ? "_rotten" : "")).apply(StructurePool.Projection.RIGID);
    }

    private static SinglePoolElement landing(boolean lamp) {
        return StructurePoolElement.ofSingle("middle-earth:misty/dwarf_mine/bridge_landing" + (lamp ? "_lamp" : "")).apply(StructurePool.Projection.RIGID);
    }
}
