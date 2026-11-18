package net.sevenstars.middleearth.features.generation.caves;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.JigsawBlock;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.sevenstars.middleearth.MiddleEarth;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.alias.StructurePoolAliasLookup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.structure.DimensionPadding;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class MistyDwarfMineStructure extends Structure {
    public static final MapCodec<MistyDwarfMineStructure> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    configCodecBuilder(instance),
                    StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    StructurePool.REGISTRY_CODEC.fieldOf("corridor_pool").forGetter(structure -> structure.corridorPool),
                    Codec.intRange(0, 20).fieldOf("size").forGetter(structure -> structure.size),
                    HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistance)
            ).apply(instance, MistyDwarfMineStructure::new));

    private static final float MIN_WEIGHT = 0.8f;
    // out of 256, per start chunk of the placement
    private static final int PEAKS_CHANCE = 115;
    private static final int ELSEWHERE_CHANCE = 38;

    private static final int MIN_PIECES = 8;
    private static final float START_MAX_AIR = 0.2f;
    private static final float PIECE_MAX_AIR = 0.4f;

    private static final int MAX_CONTINUATIONS = 3;
    private static final int CONTINUATION_SIZE = 4;
    private static final int CONTINUATION_MIN_PIECES = 2;
    private static final int CONTINUATION_TRIES = 16;
    private static final int MAX_SHIFT = 3;
    private static final int MIN_GAP = 11;
    private static final int MAX_GAP = 23; // blocks of drop looked at beyond a mine opening
    private static final Identifier MINE_CORRIDOR = MiddleEarth.id("mine_corridor");
    private static final int DROP_DEPTH = 6;
    private static final float DROP_SHARE = 0.7f;

    private static final Set<RegistryKey<Biome>> PEAKS = Set.of(
            BiomeKeyRegistryME.CARADHRAS_BASE, BiomeKeyRegistryME.CARADHRAS, BiomeKeyRegistryME.CARADHRAS_PEAKS,
            BiomeKeyRegistryME.CELEBDIL_BASE, BiomeKeyRegistryME.CELEBDIL, BiomeKeyRegistryME.CELEBDIL_PEAKS,
            BiomeKeyRegistryME.FANUIDHOL_BASE, BiomeKeyRegistryME.FANUIDHOL, BiomeKeyRegistryME.FANUIDHOL_PEAKS);

    private final RegistryEntry<StructurePool> startPool;
    private final RegistryEntry<StructurePool> corridorPool;
    private final int size;
    private final HeightProvider startHeight;
    private final int maxDistance;

    private MistyDwarfMineStructure(Config config, RegistryEntry<StructurePool> startPool, RegistryEntry<StructurePool> corridorPool, int size, HeightProvider startHeight, int maxDistance) {
        super(config);
        this.startPool = startPool;
        this.corridorPool = corridorPool;
        this.size = size;
        this.startHeight = startHeight;
        this.maxDistance = maxDistance;
    }

    @Override
    protected Optional<StructurePosition> getStructurePosition(Context context) {
        ChunkPos chunkPos = context.chunkPos();
        MiddleEarthMapRuntime map = MiddleEarthMapRuntime.getInstance();
        int x = chunkPos.getCenterX();
        int z = chunkPos.getCenterZ();
        if (MistyCaves.weight(map, x, z) < MIN_WEIGHT) return Optional.empty();
        MapBasedCustomBiome biome = map.getBiome(x, z);
        boolean peaks = biome != null && PEAKS.contains(biome.getBiomeKey());
        if (context.random().nextInt(256) >= (peaks ? PEAKS_CHANCE : ELSEWHERE_CHANCE)) return Optional.empty();
        int y = startHeight.get(context.random(), new HeightContext(context.chunkGenerator(), context.world()));
        BlockPos pos = new BlockPos(chunkPos.getStartX(), y, chunkPos.getStartZ());
        MiddleEarthChunkGenerator.CaveProbe probe = new MiddleEarthChunkGenerator.CaveProbe(context.seed(), context.world());
        if (airShare(probe, pos.getX() - 2, pos.getX() + 2, pos.getY(), pos.getY() + 2, pos.getZ() - 2, pos.getZ() + 2, 1) > START_MAX_AIR) return Optional.empty();
        Optional<StructurePosition> generated = StructurePoolBasedGenerator.generate(
                context, startPool, Optional.empty(), size, pos, false, Optional.empty(),
                new JigsawStructure.MaxDistanceFromCenter(maxDistance), StructurePoolAliasLookup.EMPTY,
                DimensionPadding.NONE, StructureLiquidSettings.APPLY_WATERLOGGING);
        if (generated.isEmpty()) return generated;
        List<StructurePiece> pieces = generated.get().generate().toList().pieces();
        List<StructurePiece> kept = connectedSolidPieces(context, probe, pieces, List.of());
        if (kept.size() < MIN_PIECES) return Optional.empty();
        List<StructurePiece> all = new ArrayList<>(kept);
        continueAcrossDrops(context, probe, all);
        StructurePiecesCollector collector = new StructurePiecesCollector();
        all.forEach(collector::addPiece);
        return Optional.of(new StructurePosition(generated.get().position(), Either.right(collector)));
    }

    // pieces reachable from the start piece through jigsaw connections without passing a piece that hangs over air
    private static List<StructurePiece> connectedSolidPieces(Context context, MiddleEarthChunkGenerator.CaveProbe probe, List<StructurePiece> pieces, List<BlockBox> taken) {
        List<StructurePiece> kept = new ArrayList<>();
        if (pieces.isEmpty() || hangs(probe, pieces.get(0), taken)) return kept;
        Map<BlockPos, Integer> jigsawOwner = new HashMap<>();
        List<List<StructureTemplate.JigsawBlockInfo>> jigsaws = new ArrayList<>();
        for (int i = 0; i < pieces.size(); i++) {
            List<StructureTemplate.JigsawBlockInfo> list = List.of();
            if (pieces.get(i) instanceof PoolStructurePiece piece) {
                list = piece.getPoolElement().getStructureBlockInfos(context.structureTemplateManager(), piece.getPos(), piece.getRotation(), Random.create(0));
            }
            jigsaws.add(list);
            for (StructureTemplate.JigsawBlockInfo jigsaw : list) jigsawOwner.put(jigsaw.info().pos(), i);
        }
        boolean[] seen = new boolean[pieces.size()];
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        seen[0] = true;
        while (!queue.isEmpty()) {
            int index = queue.poll();
            kept.add(pieces.get(index));
            for (StructureTemplate.JigsawBlockInfo jigsaw : jigsaws.get(index)) {
                Integer other = jigsawOwner.get(jigsaw.info().pos().offset(JigsawBlock.getFacing(jigsaw.info().state())));
                if (other == null || seen[other]) continue;
                seen[other] = true;
                if (!hangs(probe, pieces.get(other), taken)) queue.add(other);
            }
        }
        return kept;
    }

    // where an opening faces a drop, a goblin bridge to rock on the far side and a mine continuing from there
    private void continueAcrossDrops(Context context, MiddleEarthChunkGenerator.CaveProbe probe, List<StructurePiece> all) {
        List<BlockPos> positions = new ArrayList<>();
        List<Direction> facings = new ArrayList<>();
        List<BlockBox> taken = new ArrayList<>();
        for (StructurePiece piece : all) {
            taken.add(piece.getBoundingBox());
            if (!(piece instanceof PoolStructurePiece pool)) continue;
            for (StructureTemplate.JigsawBlockInfo jigsaw : pool.getPoolElement().getStructureBlockInfos(context.structureTemplateManager(), pool.getPos(), pool.getRotation(), Random.create(0))) {
                positions.add(jigsaw.info().pos());
                facings.add(JigsawBlock.getFacing(jigsaw.info().state()));
            }
        }
        int landing = GoblinBridge.landingLength(context.structureTemplateManager(), false);
        if (landing < 0) return;
        int count = 0;
        for (int i = 0; i < positions.size() && count < MAX_CONTINUATIONS; i++) {
            if (facings.get(i).getAxis() == Direction.Axis.Y || connected(positions, facings, i)) continue;
            Optional<List<StructurePiece>> added = continueFrom(context, probe, positions.get(i), facings.get(i), landing, taken, count);
            if (added.isEmpty()) continue;
            added.get().forEach(piece -> taken.add(piece.getBoundingBox()));
            all.addAll(added.get());
            count++;
        }
    }

    // true when another jigsaw sits on the cell in front of this opening
    private static boolean connected(List<BlockPos> positions, List<Direction> facings, int index) {
        BlockPos front = positions.get(index).offset(facings.get(index));
        int other = positions.indexOf(front);
        return other >= 0 && facings.get(other) == facings.get(index).getOpposite();
    }

    private Optional<List<StructurePiece>> continueFrom(Context context, MiddleEarthChunkGenerator.CaveProbe probe, BlockPos opening, Direction out,
                                                         int landing, List<BlockBox> taken, int count) {
        int deckY = opening.getY() - 1;
        // the mine floor is the near landing: the span starts on the last cell in front of the opening that still has floor
        int floor = 0;
        while (floor < MAX_SHIFT) {
            BlockPos cell = opening.offset(out, floor + 1);
            if (!probe.solid(cell.getX(), deckY, cell.getZ()) || probe.solid(cell.getX(), deckY + 1, cell.getZ()) || probe.solid(cell.getX(), deckY + 2, cell.getZ())) break;
            floor++;
        }
        int gap = 0;
        int dropped = 0;
        while (gap < MAX_GAP) {
            BlockPos cell = opening.offset(out, floor + gap + 1);
            if (probe.solid(cell.getX(), deckY, cell.getZ()) || probe.solid(cell.getX(), deckY + 1, cell.getZ())) break;
            boolean drop = true;
            for (int down = 1; down <= DROP_DEPTH && drop; down++) drop = !probe.solid(cell.getX(), deckY - down, cell.getZ());
            if (drop) dropped++;
            gap++;
        }
        if (gap < MIN_GAP || gap >= MAX_GAP || dropped < gap * DROP_SHARE) return Optional.empty();

        int span = -1;
        for (int size = 0; size < GoblinBridge.SPAN_LENGTH.length && span < 0; size++) {
            if (Math.abs(GoblinBridge.SPAN_LENGTH[size] - 1 - gap) <= GoblinBridge.SPAN_SLACK) span = size;
        }
        if (span < 0) return Optional.empty();
        int length = GoblinBridge.SPAN_LENGTH[span];
        int hash = MistyCaves.mix(opening.getX(), opening.getZ(), 4243 + count);
        Optional<List<StructurePiece>> bridge = GoblinBridge.bridge(context.structureTemplateManager(), probe, span, (MistyCaves.mix(hash, 1, 0) & 3) == 0, out,
                opening.offset(out, floor + (length - 1) / 2).withY(deckY),
                (MistyCaves.mix(hash, 2, 0) & 3) == 0, (MistyCaves.mix(hash, 3, 0) & 3) == 0, true, false, false);
        if (bridge.isEmpty()) return Optional.empty();

        // the continuation starts right behind the far landing, with a corridor end facing back at it
        BlockPos start = opening.offset(out, floor + length + landing).withY(deckY + 1);
        List<BlockBox> blocked = new ArrayList<>(taken);
        bridge.get().forEach(piece -> blocked.add(piece.getBoundingBox()));
        for (int attempt = 0; attempt < CONTINUATION_TRIES; attempt++) {
            if (!startsFacing(context, start, out.getOpposite(), random(context, opening, attempt))) continue;
            Context seeded = new Context(context.dynamicRegistryManager(), context.chunkGenerator(), context.biomeSource(), context.noiseConfig(),
                    context.structureTemplateManager(), random(context, opening, attempt), context.seed(), context.chunkPos(), context.world(), context.biomePredicate());
            Optional<StructurePosition> generated = StructurePoolBasedGenerator.generate(
                    seeded, corridorPool, Optional.of(MINE_CORRIDOR), CONTINUATION_SIZE, start, false, Optional.empty(),
                    new JigsawStructure.MaxDistanceFromCenter(maxDistance), StructurePoolAliasLookup.EMPTY,
                    DimensionPadding.NONE, StructureLiquidSettings.APPLY_WATERLOGGING);
            if (generated.isEmpty()) continue;
            List<StructurePiece> pieces = connectedSolidPieces(context, probe, generated.get().generate().toList().pieces(), blocked);
            if (pieces.size() < CONTINUATION_MIN_PIECES) return Optional.empty();
            List<StructurePiece> result = new ArrayList<>(bridge.get());
            result.addAll(pieces);
            return Optional.of(result);
        }
        return Optional.empty();
    }

    private static ChunkRandom random(Context context, BlockPos opening, int attempt) {
        ChunkRandom random = new ChunkRandom(new CheckedRandom(0L));
        random.setRegionSeed(context.seed(), opening.getX(), opening.getZ(), 5000 + attempt);
        return random;
    }

    // replays the start of generate (rotation, element, then the jigsaw it aligns) to see whether that jigsaw faces the way asked
    private boolean startsFacing(Context context, BlockPos start, Direction facing, ChunkRandom random) {
        BlockRotation rotation = BlockRotation.random(random);
        StructurePoolElement element = corridorPool.value().getRandomElement(random);
        for (StructureTemplate.JigsawBlockInfo jigsaw : element.getStructureBlockInfos(context.structureTemplateManager(), start, rotation, random)) {
            if (MINE_CORRIDOR.equals(jigsaw.name())) return JigsawBlock.getFacing(jigsaw.info().state()) == facing;
        }
        return false;
    }

    // samples the row under the piece, its floor row and its middle row; a piece on top of taken space counts as bad too
    private static boolean hangs(MiddleEarthChunkGenerator.CaveProbe probe, StructurePiece piece, List<BlockBox> taken) {
        BlockBox box = piece.getBoundingBox();
        for (BlockBox other : taken) {
            if (box.intersects(other)) return true;
        }
        int middle = (box.getMinY() + box.getMaxY()) / 2;
        int air = 0;
        int total = 0;
        for (int y : new int[]{box.getMinY() - 1, box.getMinY(), middle}) {
            for (int x = box.getMinX(); x <= box.getMaxX(); x += 2) {
                for (int z = box.getMinZ(); z <= box.getMaxZ(); z += 2) {
                    total++;
                    if (!probe.solid(x, y, z)) air++;
                }
            }
        }
        return air > total * PIECE_MAX_AIR;
    }

    private static float airShare(MiddleEarthChunkGenerator.CaveProbe probe, int minX, int maxX, int minY, int maxY, int minZ, int maxZ, int step) {
        int air = 0;
        int total = 0;
        for (int x = minX; x <= maxX; x += step) {
            for (int y = minY; y <= maxY; y += 2) {
                for (int z = minZ; z <= maxZ; z += step) {
                    total++;
                    if (!probe.solid(x, y, z)) air++;
                }
            }
        }
        return (float) air / total;
    }

    @Override
    public StructureType<?> getType() {
        return Caves.MISTY_DWARF_MINE;
    }
}
