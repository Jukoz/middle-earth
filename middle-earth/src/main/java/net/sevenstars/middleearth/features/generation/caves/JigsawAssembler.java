package net.sevenstars.middleearth.features.generation.caves;

import net.minecraft.block.JigsawBlock;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.pool.EmptyPoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.gen.structure.Structure;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

// Jigsaw assembly for the mines: the placement rules of the vanilla generator, but a candidate only has to avoid the boxes of
// the pieces already there (shrunk by one block, so walls can touch) and pass the caller's own test, with none of the free-space shapes
final class JigsawAssembler {
    private final StructureTemplateManager manager;
    private final Registry<StructurePool> pools;
    private final Random random;
    private final Predicate<BlockBox> accept;
    private final Predicate<BlockBox> acceptEnd;
    private final List<BlockBox> boxes;
    private final Map<StructurePoolElement, Variant[]> variants = new IdentityHashMap<>();

    private record Variant(List<StructureTemplate.JigsawBlockInfo> jigsaws, BlockBox box) {
    }

    private record Queued(PoolStructurePiece piece, int depth) {
    }

    private JigsawAssembler(Structure.Context context, Random random, Predicate<BlockBox> accept, Predicate<BlockBox> acceptEnd, List<BlockBox> taken) {
        this.manager = context.structureTemplateManager();
        this.pools = context.dynamicRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL);
        this.random = random;
        this.accept = accept;
        this.acceptEnd = acceptEnd;
        this.boxes = new ArrayList<>(taken);
    }

    // The pieces of a layout of the given size, the first one placed with the jigsaw of that name (facing the given way)
    // on pos, or with its corner on pos when there is no name, and at most limit pieces. Pieces from a fallback pool are dead ends
    // that someone may stand in, so they pass acceptEnd instead of accept. Empty when the first piece does not fit.
    static List<StructurePiece> assemble(Structure.Context context, RegistryEntry<StructurePool> pool, Optional<Identifier> startJigsaw, Optional<Direction> startFacing,
                                         BlockPos pos, int size, int limit, List<BlockBox> taken, Predicate<BlockBox> accept, Predicate<BlockBox> acceptEnd, Random random) {
        JigsawAssembler assembler = new JigsawAssembler(context, random, accept, acceptEnd, taken);
        return assembler.run(pool, startJigsaw, startFacing, pos, size, limit);
    }

    private List<StructurePiece> run(RegistryEntry<StructurePool> pool, Optional<Identifier> startJigsaw, Optional<Direction> startFacing, BlockPos pos, int size, int limit) {
        List<StructurePiece> placed = new ArrayList<>();
        StructurePoolElement element = pool.value().getRandomElement(random);
        if (element == EmptyPoolElement.INSTANCE) return placed;
        PoolStructurePiece first = null;
        for (BlockRotation rotation : BlockRotation.randomRotationOrder(random)) {
            Variant variant = variant(element, rotation);
            BlockPos origin = pos;
            if (startJigsaw.isPresent()) {
                origin = null;
                for (StructureTemplate.JigsawBlockInfo jigsaw : variant.jigsaws()) {
                    if (!startJigsaw.get().equals(jigsaw.name())) continue;
                    if (startFacing.isPresent() && JigsawBlock.getFacing(jigsaw.info().state()) != startFacing.get()) continue;
                    origin = pos.subtract(jigsaw.info().pos());
                    break;
                }
                if (origin == null) continue;
            }
            BlockBox box = variant.box().offset(origin.getX(), origin.getY(), origin.getZ());
            if (!fits(box, accept)) continue;
            first = piece(element, origin, rotation, box);
            break;
        }
        if (first == null) return placed;
        placed.add(first);
        boxes.add(first.getBoundingBox());
        Deque<Queued> queue = new ArrayDeque<>();
        queue.add(new Queued(first, 0));
        while (!queue.isEmpty()) {
            Queued parent = queue.poll();
            for (StructureTemplate.JigsawBlockInfo jigsaw : parent.piece().getPoolElement().getStructureBlockInfos(manager, parent.piece().getPos(), parent.piece().getRotation(), random)) {
                if (placed.size() >= limit) return placed;
                PoolStructurePiece child = attach(parent, jigsaw, size);
                if (child == null) continue;
                placed.add(child);
                if (parent.depth() + 1 <= size) queue.add(new Queued(child, parent.depth() + 1));
            }
        }
        return placed;
    }

    private PoolStructurePiece attach(Queued parent, StructureTemplate.JigsawBlockInfo jigsaw, int size) {
        Optional<? extends RegistryEntry<StructurePool>> target = pools.getOptional(jigsaw.pool());
        if (target.isEmpty()) return null;
        RegistryEntry<StructurePool> fallback = target.get().value().getFallback();
        List<StructurePoolElement> candidates = new ArrayList<>();
        if (parent.depth() != size) candidates.addAll(target.get().value().getElementIndicesInRandomOrder(random));
        int fromFallback = candidates.size();
        candidates.addAll(fallback.value().getElementIndicesInRandomOrder(random));
        BlockPos front = jigsaw.info().pos().offset(JigsawBlock.getFacing(jigsaw.info().state()));
        for (int c = 0; c < candidates.size(); c++) {
            StructurePoolElement element = candidates.get(c);
            boolean end = c >= fromFallback;
            if (element == EmptyPoolElement.INSTANCE) break;
            for (BlockRotation rotation : BlockRotation.randomRotationOrder(random)) {
                Variant variant = variant(element, rotation);
                for (StructureTemplate.JigsawBlockInfo other : variant.jigsaws()) {
                    if (!JigsawBlock.attachmentMatches(jigsaw, other)) continue;
                    BlockPos origin = front.subtract(other.info().pos());
                    BlockBox box = variant.box().offset(origin.getX(), origin.getY(), origin.getZ());
                    if (!fits(box, end ? acceptEnd : accept)) continue;
                    PoolStructurePiece piece = piece(element, origin, rotation, box);
                    boxes.add(box);
                    return piece;
                }
            }
        }
        return null;
    }

    private boolean fits(BlockBox box, Predicate<BlockBox> test) {
        // the box shrunk by one block must not touch another; plain comparisons, as a thin piece shrinks to nothing
        for (BlockBox other : boxes) {
            if (box.getMinX() + 1 <= other.getMaxX() && box.getMaxX() - 1 >= other.getMinX()
                    && box.getMinY() + 1 <= other.getMaxY() && box.getMaxY() - 1 >= other.getMinY()
                    && box.getMinZ() + 1 <= other.getMaxZ() && box.getMaxZ() - 1 >= other.getMinZ()
                    && box.getMinX() + 1 <= box.getMaxX() - 1 && box.getMinY() + 1 <= box.getMaxY() - 1 && box.getMinZ() + 1 <= box.getMaxZ() - 1) return false;
        }
        return test.test(box);
    }

    private Variant variant(StructurePoolElement element, BlockRotation rotation) {
        Variant[] all = variants.computeIfAbsent(element, key -> new Variant[4]);
        Variant variant = all[rotation.ordinal()];
        if (variant == null) {
            variant = new Variant(element.getStructureBlockInfos(manager, BlockPos.ORIGIN, rotation, random), element.getBoundingBox(manager, BlockPos.ORIGIN, rotation));
            all[rotation.ordinal()] = variant;
        }
        return variant;
    }

    private PoolStructurePiece piece(StructurePoolElement element, BlockPos origin, BlockRotation rotation, BlockBox box) {
        return new PoolStructurePiece(manager, element, origin, element.getGroundLevelDelta(), rotation, box, StructureLiquidSettings.APPLY_WATERLOGGING);
    }
}
