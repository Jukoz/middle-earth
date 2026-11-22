package net.sevenstars.middleearth.features.generation.caves;

import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.JigsawBlock;
import net.minecraft.structure.StructurePlacementData;
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
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

// jigsaw assembly for the mines: vanilla placement rules, but a candidate only avoids the boxes of placed pieces
// (shrunk by one block, so walls can touch) and passes the caller's own test. No free-space shapes
final class JigsawAssembler {
    private static final List<Block> FLOOR_BLOCKS = List.of(Blocks.COBBLESTONE, Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
    private static final int ROTATIONS = BlockRotation.values().length;

    // floor columns by template manager (one per server, so a reload starts clean). Chunks generate in parallel
    private static final Map<StructureTemplateManager, Map<FloorKey, int[]>> FLOORS = Collections.synchronizedMap(new WeakHashMap<>());

    private final StructureTemplateManager manager;
    private final Registry<StructurePool> pools;
    private final Random random;
    private final Fit accept;
    private final Fit acceptEnd;
    private final List<BlockBox> boxes;
    private final Map<StructurePoolElement, Variant[]> variants = new IdentityHashMap<>();

    // box, origin and the floor blocks of the template as x, y, z offsets from the origin
    interface Fit {
        boolean test(BlockBox box, BlockPos origin, int[] floor);
    }

    private record Variant(List<StructureTemplate.JigsawBlockInfo> jigsaws, BlockBox box, int[] floor) {
    }

    private record FloorKey(StructurePoolElement element, BlockRotation rotation) {
    }

    private record Queued(PoolStructurePiece piece, int depth) {
    }

    private JigsawAssembler(Structure.Context context, Random random, Fit accept, Fit acceptEnd, List<BlockBox> taken) {
        this.manager = context.structureTemplateManager();
        this.pools = context.dynamicRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL);
        this.random = random;
        this.accept = accept;
        this.acceptEnd = acceptEnd;
        this.boxes = new ArrayList<>(taken);
    }

    // first piece uses the named jigsaw (facing the given way) on pos, or its corner when no name. At most limit pieces
    // fallback-pool pieces are dead ends someone may stand in, so they pass acceptEnd. Empty when the first doesn't fit
    static List<StructurePiece> assemble(Structure.Context context, RegistryEntry<StructurePool> pool, Optional<Identifier> startJigsaw, Optional<Direction> startFacing,
                                         BlockPos pos, int size, int limit, List<BlockBox> taken, Fit accept, Fit acceptEnd, Random random) {
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
            if (!fits(box, origin, variant.floor(), accept)) continue;
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
                    if (!fits(box, origin, variant.floor(), end ? acceptEnd : accept)) continue;
                    PoolStructurePiece piece = piece(element, origin, rotation, box);
                    boxes.add(box);
                    return piece;
                }
            }
        }
        return null;
    }

    private boolean fits(BlockBox box, BlockPos origin, int[] floor, Fit test) {
        // the box shrunk by one block must not touch another. Plain comparisons, as a thin piece shrinks to nothing
        for (BlockBox other : boxes) {
            if (box.getMinX() + 1 <= other.getMaxX() && box.getMaxX() - 1 >= other.getMinX()
                    && box.getMinY() + 1 <= other.getMaxY() && box.getMaxY() - 1 >= other.getMinY()
                    && box.getMinZ() + 1 <= other.getMaxZ() && box.getMaxZ() - 1 >= other.getMinZ()
                    && box.getMinX() + 1 <= box.getMaxX() - 1 && box.getMinY() + 1 <= box.getMaxY() - 1 && box.getMinZ() + 1 <= box.getMaxZ() - 1) return false;
        }
        return test.test(box, origin, floor);
    }

    private Variant variant(StructurePoolElement element, BlockRotation rotation) {
        Variant[] all = variants.computeIfAbsent(element, key -> new Variant[ROTATIONS]);
        Variant variant = all[rotation.ordinal()];
        if (variant == null) {
            variant = new Variant(element.getStructureBlockInfos(manager, BlockPos.ORIGIN, rotation, random), element.getBoundingBox(manager, BlockPos.ORIGIN, rotation), floorColumns(element, rotation));
            all[rotation.ordinal()] = variant;
        }
        return variant;
    }

    // floor blocks of the template (shell blocks with nothing of the template under them), as offsets from the origin
    private int[] floorColumns(StructurePoolElement element, BlockRotation rotation) {
        return FLOORS.computeIfAbsent(manager, key -> new ConcurrentHashMap<>()).computeIfAbsent(new FloorKey(element, rotation), key -> findFloorColumns(element, rotation));
    }

    private int[] findFloorColumns(StructurePoolElement element, BlockRotation rotation) {
        JsonElement json = StructurePoolElement.CODEC.encodeStart(JsonOps.INSTANCE, element).result().orElse(null);
        Identifier id = json != null && json.isJsonObject() && json.getAsJsonObject().has("location") ? Identifier.tryParse(json.getAsJsonObject().get("location").getAsString()) : null;
        if (id == null) return new int[0];
        StructureTemplate template = manager.getTemplateOrBlank(id);
        StructurePlacementData placement = new StructurePlacementData().setRotation(rotation);
        Set<Long> air = new HashSet<>();
        for (StructureTemplate.StructureBlockInfo info : template.getInfosForBlock(BlockPos.ORIGIN, placement, Blocks.AIR)) air.add(info.pos().asLong());
        Set<Long> shell = new HashSet<>();
        for (Block block : FLOOR_BLOCKS) {
            for (StructureTemplate.StructureBlockInfo info : template.getInfosForBlock(BlockPos.ORIGIN, placement, block)) shell.add(info.pos().asLong());
        }
        List<Integer> floor = new ArrayList<>();
        for (long packed : shell) {
            BlockPos pos = BlockPos.fromLong(packed);
            if (shell.contains(pos.down().asLong()) || air.contains(pos.down().asLong())) continue;
            floor.add(pos.getX());
            floor.add(pos.getY());
            floor.add(pos.getZ());
        }
        return floor.stream().mapToInt(Integer::intValue).toArray();
    }

    private PoolStructurePiece piece(StructurePoolElement element, BlockPos origin, BlockRotation rotation, BlockBox box) {
        return new PoolStructurePiece(manager, element, origin, element.getGroundLevelDelta(), rotation, box, StructureLiquidSettings.APPLY_WATERLOGGING);
    }
}
