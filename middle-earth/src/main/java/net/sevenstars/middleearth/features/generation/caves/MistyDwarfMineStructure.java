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
import java.util.Comparator;
import net.minecraft.world.gen.structure.DimensionPadding;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.chunkgen.MiddleEarthChunkGenerator;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

public final class MistyDwarfMineStructure extends Structure {
    public static final MapCodec<MistyDwarfMineStructure> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    configCodecBuilder(instance),
                    StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
                    StructurePool.REGISTRY_CODEC.fieldOf("corridor_pool").forGetter(structure -> structure.corridorPool),
                    StructurePool.REGISTRY_CODEC.fieldOf("ends_pool").forGetter(structure -> structure.endsPool),
                    StructurePool.REGISTRY_CODEC.fieldOf("plugs_pool").forGetter(structure -> structure.plugsPool),
                    Codec.intRange(0, 20).fieldOf("size").forGetter(structure -> structure.size),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistance)
            ).apply(instance, MistyDwarfMineStructure::new));

    private static final float MIN_WEIGHT = 0.8f;
    // out of 256, per start chunk of the placement
    // out of 3, the starts in the upper half of the mithril band; the rest are in the lower half of the middle one
    private static final int DELVED_DEEP = 1;
    private static final int START_TRIES = 5;
    private static final int PEAKS_CHANCE = 160;
    private static final int ELSEWHERE_CHANCE = 64;

    private static final int MIN_PIECES = 20;
    private static final int TARGET_MIN = 200;
    private static final int TARGET_MAX = 400; // the growth stops here; ends, plugs and bridges come on top
    private static final int MAX_WAVES = 30;
    private static final int WAVE_STARTS_MIN = 8;
    private static final int WAVE_STARTS_SPREAD = 9;
    private static final int WAVE_SIZE_MIN = 12;
    private static final int WAVE_SIZE_SPREAD = 9;
    private static final int WAVE_MIN_PIECES = 3;
    private static final int WAVE_TRIES = 12;
    private static final int WAVE_JITTER = 32; // blocks of noise on the farthest-first order
    // a structure's pieces are only placed in chunks within 8 chunks of its start chunk
    private static final int WINDOW_CHUNKS = 8;
    private static final int LOW_BAND = 2; // exits this close to the lowest one of their piece are bottom exits
    private static final int VERTICAL_RISE = 6; // an exit this far above the lowest one of its piece is a vertical exit
    private static final int SEAL_ROUNDS = 8;
    private static final int CAP_ROUNDS = 24;
    private static final float CAP_FLOOR_SHARE = 0.7f;
    private static final int ROCK_CELLS = 6; // of the 9 cells beyond an opening
    private static final int TOP_MARGIN = 10; // of the middle band, kept clear above the mine
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
    private final RegistryEntry<StructurePool> endsPool;
    private final RegistryEntry<StructurePool> plugsPool;
    private final int size;
    private final int maxDistance;

    private MistyDwarfMineStructure(Config config, RegistryEntry<StructurePool> startPool, RegistryEntry<StructurePool> corridorPool, RegistryEntry<StructurePool> endsPool, RegistryEntry<StructurePool> plugsPool, int size, int maxDistance) {
        super(config);
        this.startPool = startPool;
        this.corridorPool = corridorPool;
        this.endsPool = endsPool;
        this.plugsPool = plugsPool;
        this.size = size;
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
        DepthBands.Edges edges = new MistyCaves(context.seed()).edgesAt(map, x, z);
        MiddleEarthChunkGenerator.CaveProbe probe = new MiddleEarthChunkGenerator.CaveProbe(context.seed(), context.world());
        // the band is rolled once; a start the rock refuses is tried again higher or lower in the same band, so the share holds
        boolean delved = context.random().nextInt(3) < DELVED_DEEP;
        for (int attempt = 0; attempt < START_TRIES; attempt++) {
            Optional<StructurePosition> position = tryStart(context, probe, new BlockPos(chunkPos.getStartX(), startY(edges, context.random(), delved), chunkPos.getStartZ()));
            if (position.isPresent()) return position;
        }
        return Optional.empty();
    }

    private Optional<StructurePosition> tryStart(Context context, MiddleEarthChunkGenerator.CaveProbe probe, BlockPos pos) {
        if (airShare(probe, pos.getX() - 2, pos.getX() + 2, pos.getY(), pos.getY() + 2, pos.getZ() - 2, pos.getZ() + 2, 1) > START_MAX_AIR) return Optional.empty();
        Session session = new Session(context, probe, new MistyCaves(context.seed()));
        List<StructurePiece> all = JigsawAssembler.assemble(context, startPool, Optional.empty(), Optional.empty(), pos, size, TARGET_MAX, List.of(), session::fits, session::capFits, random(context, pos, 4100));
        if (all.size() < MIN_PIECES) return Optional.empty();
        grow(session, all, pos);
        Set<BlockPos> bridged = new HashSet<>();
        continueAcrossDrops(session, all, bridged);
        sealLevels(session, all);
        capOpenings(session, all, bridged);
        BlockBox first = all.get(0).getBoundingBox();
        StructurePiecesCollector collector = new StructurePiecesCollector();
        all.forEach(collector::addPiece);
        return Optional.of(new StructurePosition(new BlockPos(first.getCenter().getX(), pos.getY(), first.getCenter().getZ()), Either.right(collector)));
    }

    // what one mine start needs while it grows: the rock probe, the depth bands of the columns it meets and the chunks it may fill
    private static final class Session {
        final Context context;
        final MiddleEarthChunkGenerator.CaveProbe probe;
        final MistyCaves caves;
        final BlockBox window;
        private final Map<Long, DepthBands.Edges> edges = new HashMap<>();

        Session(Context context, MiddleEarthChunkGenerator.CaveProbe probe, MistyCaves caves) {
            this.context = context;
            this.probe = probe;
            this.caves = caves;
            ChunkPos chunk = context.chunkPos();
            this.window = new BlockBox((chunk.x - WINDOW_CHUNKS) << 4, context.world().getBottomY(), (chunk.z - WINDOW_CHUNKS) << 4,
                    ((chunk.x + WINDOW_CHUNKS) << 4) + 15, context.world().getTopYInclusive(), ((chunk.z + WINDOW_CHUNKS) << 4) + 15);
        }

        // most of the row under the box is rock
        boolean floored(BlockBox box) {
            int solid = 0;
            int total = 0;
            for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
                for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                    total++;
                    if (probe.solid(x, box.getMinY() - 1, z)) solid++;
                }
            }
            return solid >= total * CAP_FLOOR_SHARE;
        }

        // a cap or a piece started to seal an exit: inside the limits and with rock under it
        boolean capFits(BlockBox box) {
            return fits(box) && floored(box);
        }

        boolean inBand(BlockBox box) {
            return !outOfBounds(box);
        }

        // the piece has to lie inside the window and between the top of the middle band and the bottom of the mithril band
        boolean outOfBounds(BlockBox box) {
            if (box.getMinX() < window.getMinX() || box.getMaxX() > window.getMaxX() || box.getMinZ() < window.getMinZ() || box.getMaxZ() > window.getMaxZ()) return true;
            int x = (box.getMinX() + box.getMaxX()) / 2;
            int z = (box.getMinZ() + box.getMaxZ()) / 2;
            DepthBands.Edges column = edges.computeIfAbsent(ChunkPos.toLong(x >> 4, z >> 4), key -> caves.edgesAt(MiddleEarthMapRuntime.getInstance(), x, z));
            return box.getMaxY() > column.top(DepthBands.Band.MIDDLE) - TOP_MARGIN || box.getMinY() < column.bottom(DepthBands.Band.MITHRIL);
        }

        // inside the limits and on rock: samples the row under the piece, its floor row and its middle row
        boolean fits(BlockBox box) {
            if (outOfBounds(box)) return false;
            int middle = (box.getMinY() + box.getMaxY()) / 2;
            int columns = ((box.getMaxX() - box.getMinX()) / 2 + 1) * ((box.getMaxZ() - box.getMinZ()) / 2 + 1);
            float allowed = 3 * columns * PIECE_MAX_AIR;
            int air = 0;
            // the same share as counting everything first, but a piece over a cave is refused after a few samples
            for (int y : new int[]{box.getMinY() - 1, box.getMinY(), middle}) {
                for (int x = box.getMinX(); x <= box.getMaxX(); x += 2) {
                    for (int z = box.getMinZ(); z <= box.getMaxZ(); z += 2) {
                        if (!probe.solid(x, y, z) && ++air > allowed) return false;
                    }
                }
            }
            return true;
        }
    }

    // Waves: new layouts started from the open ends of the mine, the farthest first, so it stretches outwards.
    // Every layout is pruned like the first one and may not touch anything already there.
    private void grow(Session session, List<StructurePiece> all, BlockPos origin) {
        ChunkPos chunk = session.context.chunkPos();
        int target = TARGET_MIN + (MistyCaves.mix(chunk.x, chunk.z, 6100) & 0xFFFF) % (TARGET_MAX - TARGET_MIN + 1);
        List<BlockBox> taken = new ArrayList<>();
        all.forEach(piece -> taken.add(piece.getBoundingBox()));
        int empty = 0;
        for (int wave = 0; wave < MAX_WAVES && all.size() < target && empty < 3; wave++) {
            List<BlockPos> positions = new ArrayList<>();
            List<Direction> facings = new ArrayList<>();
            collectJigsaws(session.context, all, positions, facings);
            List<Integer> open = new ArrayList<>();
            for (int i = 0; i < positions.size(); i++) {
                Direction out = facings.get(i);
                if (out.getAxis() == Direction.Axis.Y || connected(positions, facings, i)) continue;
                BlockPos front = positions.get(i).offset(out);
                // an opening over a drop is left to the bridges
                if (session.probe.solid(front.getX(), front.getY() - 1, front.getZ())) open.add(i);
            }
            final int waveIndex = wave;
            open.sort(Comparator.comparingLong(i -> {
                long dx = positions.get(i).getX() - origin.getX();
                long dz = positions.get(i).getZ() - origin.getZ();
                return -(long) Math.sqrt(dx * dx + dz * dz) + (MistyCaves.mix(positions.get(i).getX(), positions.get(i).getZ(), 6200 + waveIndex) & (WAVE_JITTER - 1));
            }));
            int starts = WAVE_STARTS_MIN + (MistyCaves.mix(chunk.x, chunk.z, 6300 + wave) & 0xFF) % WAVE_STARTS_SPREAD;
            int added = 0;
            for (int n = 0; n < Math.min(starts, open.size()) && all.size() + added < target; n++) {
                int index = open.get(n);
                Optional<List<StructurePiece>> layout = layoutFrom(session, positions.get(index), facings.get(index), wave, target - all.size() - added, taken, session::fits);
                if (layout.isEmpty()) continue;
                layout.get().forEach(piece -> taken.add(piece.getBoundingBox()));
                all.addAll(layout.get());
                added += layout.get().size();
            }
            empty = added == 0 ? empty + 1 : 0;
        }
    }

    // Level connectors lead somewhere or go. A level piece (two exits at different heights) needs both of them connected; a hub or
    // crossing with an exit well above its floor gets something attached there when it can, and keeps the exit when it cannot.
    private void sealLevels(Session session, List<StructurePiece> all) {
        for (int round = 0; round < SEAL_ROUNDS; round++) {
            List<BlockPos> positions = new ArrayList<>();
            List<Direction> facings = new ArrayList<>();
            List<Integer> owners = new ArrayList<>();
            for (int i = 0; i < all.size(); i++) {
                int before = positions.size();
                collectJigsaws(session.context, List.of(all.get(i)), positions, facings);
                for (int n = before; n < positions.size(); n++) owners.add(i);
            }
            List<BlockBox> taken = new ArrayList<>();
            all.forEach(piece -> taken.add(piece.getBoundingBox()));
            boolean changed = false;
            for (int piece = 1; piece < all.size() && !changed; piece++) {
                List<Integer> own = new ArrayList<>();
                int lowest = Integer.MAX_VALUE;
                int highest = Integer.MIN_VALUE;
                for (int j = 0; j < positions.size(); j++) {
                    if (owners.get(j) != piece || facings.get(j).getAxis() == Direction.Axis.Y) continue;
                    own.add(j);
                    lowest = Math.min(lowest, positions.get(j).getY());
                    highest = Math.max(highest, positions.get(j).getY());
                }
                if (highest - lowest < VERTICAL_RISE) continue;
                // the bottom exit of a pit and both exits of a level piece are where a ladder or stair leads: they have to connect
                List<Integer> low = new ArrayList<>();
                List<Integer> high = new ArrayList<>();
                for (int j : own) {
                    if (positions.get(j).getY() <= lowest + LOW_BAND) low.add(j);
                    if (positions.get(j).getY() >= lowest + VERTICAL_RISE) high.add(j);
                }
                boolean required = low.size() == 1;
                boolean sealed = true;
                for (int j : high) {
                    if (connected(positions, facings, j)) continue;
                    if (attachSomething(session, positions.get(j), facings.get(j), taken, round, all)) {
                        changed = true;
                    } else if (required && high.size() == 1) {
                        sealed = false;
                    }
                }
                for (int j : low) {
                    if (!required || connected(positions, facings, j)) continue;
                    if (attachSomething(session, positions.get(j), facings.get(j), taken, round, all)) {
                        changed = true;
                    } else {
                        sealed = false;
                    }
                }
                if (!sealed) {
                    removeWithOrphans(session, all, piece);
                    changed = true;
                }
            }
            if (!changed) return;
        }
    }

    // An opening that faces rock ends in a dead end piece, or in a plug of rubble that is placed even where it overlaps rock; both need rock under them.
    // One that leads into cave air stays open, as a natural break, and so do bridged and vertical ones. When neither fits inside
    // the depth bands, the piece with that opening goes, with whatever hangs off it, and the pieces beside it are checked again.
    private void capOpenings(Session session, List<StructurePiece> all, Set<BlockPos> bridged) {
        for (int round = 0; round < CAP_ROUNDS; round++) {
            List<BlockPos> positions = new ArrayList<>();
            List<Direction> facings = new ArrayList<>();
            List<Integer> owners = new ArrayList<>();
            for (int i = 0; i < all.size(); i++) {
                int before = positions.size();
                collectJigsaws(session.context, List.of(all.get(i)), positions, facings);
                for (int n = before; n < positions.size(); n++) owners.add(i);
            }
            List<BlockBox> taken = new ArrayList<>();
            all.forEach(piece -> taken.add(piece.getBoundingBox()));
            List<StructurePiece> added = new ArrayList<>();
            List<StructurePiece> doomed = new ArrayList<>();
            for (int i = 0; i < positions.size(); i++) {
                Direction out = facings.get(i);
                BlockPos opening = positions.get(i);
                if (out.getAxis() == Direction.Axis.Y || bridged.contains(opening) || connected(positions, facings, i) || !facesRock(session, opening, out, taken)) continue;
                List<StructurePiece> cap = JigsawAssembler.assemble(session.context, endsPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), opening.offset(out), 1, Integer.MAX_VALUE, taken, session::capFits, session::capFits, random(session.context, opening, 9500));
                if (cap.isEmpty()) cap = JigsawAssembler.assemble(session.context, plugsPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), opening.offset(out), 1, Integer.MAX_VALUE, taken, box -> session.inBand(box) && session.floored(box) && !overlaps(box, taken), session::capFits, random(session.context, opening, 9600));
                if (cap.isEmpty() && owners.get(i) > 0) doomed.add(all.get(owners.get(i)));
                cap.forEach(piece -> taken.add(piece.getBoundingBox()));
                added.addAll(cap);
            }
            all.addAll(added);
            if (doomed.isEmpty()) return;
            for (StructurePiece piece : doomed) {
                int index = all.indexOf(piece);
                if (index > 0) removeWithOrphans(session, all, index);
            }
        }
    }

    // mostly terrain rock in the 3 by 3 cells just beyond the opening; one that faces another piece is left as the blocked alcove it is
    private static boolean facesRock(Session session, BlockPos opening, Direction out, List<BlockBox> taken) {
        BlockPos front = opening.offset(out);
        int sideX = out.getOffsetZ();
        int sideZ = out.getOffsetX();
        int solid = 0;
        int piece = 0;
        for (int side = -1; side <= 1; side++) {
            for (int up = 0; up <= 2; up++) {
                int x = front.getX() + sideX * side;
                int y = front.getY() + up;
                int z = front.getZ() + sideZ * side;
                boolean inside = false;
                for (BlockBox box : taken) inside |= box.contains(new BlockPos(x, y, z));
                if (inside) {
                    piece++;
                } else if (session.probe.solid(x, y, z)) {
                    solid++;
                }
            }
        }
        return piece < ROCK_CELLS && solid >= ROCK_CELLS;
    }

    private static boolean overlaps(BlockBox box, List<BlockBox> taken) {
        for (BlockBox other : taken) {
            if (box.intersects(other)) return true;
        }
        return false;
    }

    // a growth layout from the opening, or else a single dead end
    private boolean attachSomething(Session session, BlockPos opening, Direction out, List<BlockBox> taken, int round, List<StructurePiece> all) {
        Optional<List<StructurePiece>> layout = layoutFrom(session, opening, out, 100 + round, Integer.MAX_VALUE, taken, session::capFits);
        if (layout.isEmpty()) layout = endFrom(session, opening, out, round, taken);
        if (layout.isEmpty()) return false;
        layout.get().forEach(piece -> taken.add(piece.getBoundingBox()));
        all.addAll(layout.get());
        return true;
    }

    private Optional<List<StructurePiece>> endFrom(Session session, BlockPos opening, Direction out, int round, List<BlockBox> taken) {
        List<StructurePiece> pieces = JigsawAssembler.assemble(session.context, endsPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), opening.offset(out), 1, Integer.MAX_VALUE, taken, session::capFits, session::capFits, random(session.context, opening, 9000 + round));
        return pieces.isEmpty() ? Optional.empty() : Optional.of(pieces);
    }

    // drops the piece and whatever was only reachable from the start through it
    private static void removeWithOrphans(Session session, List<StructurePiece> all, int index) {
        List<StructurePiece> before = reachable(session.context, all);
        all.remove(index);
        List<StructurePiece> after = reachable(session.context, all);
        for (StructurePiece piece : before) {
            if (!after.contains(piece)) all.remove(piece);
        }
    }

    // pieces linked to the first one through jigsaws
    private static List<StructurePiece> reachable(Context context, List<StructurePiece> pieces) {
        List<BlockPos> positions = new ArrayList<>();
        List<Direction> facings = new ArrayList<>();
        List<Integer> owners = new ArrayList<>();
        Map<BlockPos, Integer> at = new HashMap<>();
        for (int i = 0; i < pieces.size(); i++) {
            int before = positions.size();
            collectJigsaws(context, List.of(pieces.get(i)), positions, facings);
            for (int n = before; n < positions.size(); n++) {
                owners.add(i);
                at.put(positions.get(n), i);
            }
        }
        boolean[] seen = new boolean[pieces.size()];
        List<StructurePiece> result = new ArrayList<>();
        if (pieces.isEmpty()) return result;
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        seen[0] = true;
        while (!queue.isEmpty()) {
            int index = queue.poll();
            result.add(pieces.get(index));
            for (int j = 0; j < positions.size(); j++) {
                if (owners.get(j) != index) continue;
                Integer other = at.get(positions.get(j).offset(facings.get(j)));
                if (other == null || seen[other]) continue;
                seen[other] = true;
                queue.add(other);
            }
        }
        return result;
    }

    private Optional<List<StructurePiece>> layoutFrom(Session session, BlockPos opening, Direction out, int wave, int limit, List<BlockBox> taken, Predicate<BlockBox> accept) {
        int size = WAVE_SIZE_MIN + (MistyCaves.mix(opening.getX(), opening.getZ(), 6400 + wave) & 0xFF) % WAVE_SIZE_SPREAD;
        List<StructurePiece> pieces = JigsawAssembler.assemble(session.context, corridorPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), opening.offset(out), size, limit, taken, accept, session::capFits, random(session.context, opening, 7000 + wave));
        return pieces.size() >= WAVE_MIN_PIECES ? Optional.of(pieces) : Optional.empty();
    }

    private static void collectJigsaws(Context context, List<StructurePiece> pieces, List<BlockPos> positions, List<Direction> facings) {
        for (StructurePiece piece : pieces) {
            if (!(piece instanceof PoolStructurePiece pool)) continue;
            for (StructureTemplate.JigsawBlockInfo jigsaw : pool.getPoolElement().getStructureBlockInfos(context.structureTemplateManager(), pool.getPos(), pool.getRotation(), Random.create(0))) {
                positions.add(jigsaw.info().pos());
                facings.add(JigsawBlock.getFacing(jigsaw.info().state()));
            }
        }
    }

    // the mines delve deep: a third start in the upper half of the mithril band, the rest in the lower half of the middle band
    private static int startY(DepthBands.Edges edges, Random random, boolean delved) {
        float share = random.nextFloat() * 0.5f;
        if (delved) return edges.at(DepthBands.Band.MITHRIL, 0.5f + share);
        return edges.at(DepthBands.Band.MIDDLE, share);
    }

    // where an opening faces a drop, a goblin bridge to rock on the far side and a mine continuing from there
    private void continueAcrossDrops(Session session, List<StructurePiece> all, Set<BlockPos> bridged) {
        List<BlockPos> positions = new ArrayList<>();
        List<Direction> facings = new ArrayList<>();
        List<BlockBox> taken = new ArrayList<>();
        all.forEach(piece -> taken.add(piece.getBoundingBox()));
        collectJigsaws(session.context, all, positions, facings);
        int landing = GoblinBridge.landingLength(session.context.structureTemplateManager(), false);
        if (landing < 0) return;
        int count = 0;
        for (int i = 0; i < positions.size() && count < MAX_CONTINUATIONS; i++) {
            if (facings.get(i).getAxis() == Direction.Axis.Y || connected(positions, facings, i)) continue;
            Optional<List<StructurePiece>> added = continueFrom(session, positions.get(i), facings.get(i), landing, taken, count);
            if (added.isEmpty()) continue;
            added.get().forEach(piece -> taken.add(piece.getBoundingBox()));
            all.addAll(added.get());
            bridged.add(positions.get(i));
            count++;
        }
    }

    // true when another jigsaw sits on the cell in front of this opening
    private static boolean connected(List<BlockPos> positions, List<Direction> facings, int index) {
        BlockPos front = positions.get(index).offset(facings.get(index));
        int other = positions.indexOf(front);
        return other >= 0 && facings.get(other) == facings.get(index).getOpposite();
    }

    private Optional<List<StructurePiece>> continueFrom(Session session, BlockPos opening, Direction out, int landing, List<BlockBox> taken, int count) {
        Context context = session.context;
        MiddleEarthChunkGenerator.CaveProbe probe = session.probe;
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
        List<StructurePiece> pieces = JigsawAssembler.assemble(context, corridorPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), start, CONTINUATION_SIZE, Integer.MAX_VALUE, blocked, session::capFits, session::capFits, random(context, opening, 5000));
        if (pieces.size() < CONTINUATION_MIN_PIECES) return Optional.empty();
        List<StructurePiece> result = new ArrayList<>(bridge.get());
        result.addAll(pieces);
        return Optional.of(result);
    }

    private static ChunkRandom random(Context context, BlockPos opening, int attempt) {
        ChunkRandom random = new ChunkRandom(new CheckedRandom(0L));
        random.setRegionSeed(context.seed(), opening.getX(), opening.getZ(), 5000 + attempt);
        return random;
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
