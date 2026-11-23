package net.sevenstars.middleearth.features.generation.caves;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesCollector;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.features.generation.biomes.BiomeKeyRegistryME;
import net.sevenstars.middleearth.features.generation.biomes.caves.DepthBands;
import net.sevenstars.middleearth.features.generation.biomes.surface.MapBasedCustomBiome;
import net.sevenstars.middleearth.features.generation.chunkgen.CaveProbe;
import net.sevenstars.middleearth.features.generation.map.MiddleEarthMapRuntime;

import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
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
                    StructurePool.REGISTRY_CODEC.fieldOf("ends_pool").forGetter(structure -> structure.endsPool),
                    StructurePool.REGISTRY_CODEC.fieldOf("plugs_pool").forGetter(structure -> structure.plugsPool),
                    Codec.intRange(0, 20).fieldOf("size").forGetter(structure -> structure.size),
                    Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistance)
            ).apply(instance, MistyDwarfMineStructure::new));


    private static final float MIN_WEIGHT = 0.8f;
    private static final int PLACEMENT_ROLL = 256; // the chances below are out of this, per start chunk of the placement
    private static final int PEAKS_CHANCE = 160;
    private static final int ELSEWHERE_CHANCE = 64;
    private static final int BAND_ROLL = 3; // 1 in 3 starts are delved (upper half of the mithril band), the rest in the lower half of the middle band
    private static final int DELVED_DEEP = 1;
    private static final int START_TRIES = 5;
    private static final float START_MAX_AIR = 0.2f;
    private static final int START_AIR_RADIUS = 2;
    private static final int START_AIR_HEIGHT = 2;

    private static final int MIN_PIECES = 20;
    private static final int TARGET_MIN = 200;
    private static final int TARGET_MAX = 400; // the growth stops here. Ends, plugs and bridges come on top
    private static final int MAX_WAVES = 30;
    private static final int MAX_EMPTY_WAVES = 3;
    private static final int WAVE_STARTS_MIN = 8;
    private static final int WAVE_STARTS_SPREAD = 9;
    private static final int WAVE_SIZE_MIN = 12;
    private static final int WAVE_SIZE_SPREAD = 9;
    private static final int WAVE_MIN_PIECES = 3;
    private static final int WAVE_JITTER = 32; // blocks of noise on the farthest-first order
    private static final int LOW_BAND = 2; // exits this close to the lowest one of their piece are bottom exits
    private static final int VERTICAL_RISE = 6; // an exit this far above the lowest one of its piece is a vertical exit
    private static final int SEAL_ROUNDS = 8;
    private static final int CAP_ROUNDS = 24;
    private static final int ROCK_CELLS = 6; // of the 9 cells beyond an opening

    private static final int MAX_CONTINUATIONS = 4;
    private static final int WAVE_BRIDGES = 2; // openings over a drop tried per growth wave
    private static final int CONTINUATION_SIZE = 4;
    private static final int CONTINUATION_MIN_PIECES = 2;
    private static final int MAX_LEAD = 24; // blocks of floor walked from an opening to the edge of a chasm
    private static final int JOIN_MAX = 4; // jigsaw cells apart, so up to three blocks of rock between two ends
    private static final int MIN_GAP = 7;
    private static final int MAX_GAP = 23; // blocks of drop looked at beyond a mine opening
    private static final int DROP_DEPTH = 4;
    private static final float DROP_SHARE = 0.6f;
    private static final int SPAN_SHORT_BY = 1; // a span may end this far short of the far rim, or reach up to SPAN_LONG_BY into the rock
    private static final int SPAN_LONG_BY = 5;
    private static final int TUNNEL_HEIGHT = 3;
    private static final int RANDOM_SALT = 5000;

    // salts of the mine's random sources, each one is added to or mixed with a position
    private static final int START_SALT = 4100;
    private static final int TARGET_SALT = 6100;
    private static final int WAVE_ORDER_SALT = 6200;
    private static final int WAVE_STARTS_SALT = 6300;
    private static final int WAVE_SIZE_SALT = 6400;
    private static final int WAVE_LAYOUT_SALT = 7000;
    private static final int ATTACH_WAVE_BASE = 100; // wave number of the layouts that seal a level
    private static final int END_SALT = 9000;
    private static final int CAP_END_SALT = 9500;
    private static final int CAP_PLUG_SALT = 9600;
    private static final int BRIDGE_SALT = 4243;
    private static final int CONTINUATION_SALT = 5000;
    private static final int MASK_16 = 0xFFFF;
    private static final int MASK_8 = 0xFF;
    private static final int MASK_2 = 3;

    private static final Identifier MINE_CORRIDOR = MiddleEarth.id("mine_corridor");

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
        if (context.random().nextInt(PLACEMENT_ROLL) >= (peaks ? PEAKS_CHANCE : ELSEWHERE_CHANCE)) return Optional.empty();
        DepthBands.Edges edges = new MistyCaves(context.seed()).edgesAt(map, x, z);
        CaveProbe probe = new CaveProbe(context.seed(), context.world());
        // band rolled once. A start the rock refuses is retried higher or lower in the same band, so the share holds
        boolean delved = context.random().nextInt(BAND_ROLL) < DELVED_DEEP;
        for (int attempt = 0; attempt < START_TRIES; attempt++) {
            Optional<StructurePosition> position = tryStart(context, probe, new BlockPos(chunkPos.getStartX(), startY(edges, context.random(), delved), chunkPos.getStartZ()));
            if (position.isPresent()) return position;
        }
        return Optional.empty();
    }

    private Optional<StructurePosition> tryStart(Context context, CaveProbe probe, BlockPos pos) {
        if (airShare(probe, pos) > START_MAX_AIR) return Optional.empty();
        MineSession session = new MineSession(context, probe, new MistyCaves(context.seed()));
        List<StructurePiece> all = JigsawAssembler.assemble(context, startPool, Optional.empty(), Optional.empty(), pos, size, TARGET_MAX, List.of(), session::fits, session::capFits, random(context, pos, START_SALT));
        if (all.size() < MIN_PIECES) return Optional.empty();
        Set<BlockPos> bridged = new HashSet<>();
        Set<BlockPos> joined = new HashSet<>();
        grow(session, all, pos, bridged);
        joinNearbyEnds(session, all, joined);
        continueAcrossDrops(session, all, bridged);
        sealLevels(session, all, joined);
        capOpenings(session, all, bridged, joined);
        BlockBox first = all.get(0).getBoundingBox();
        StructurePiecesCollector collector = new StructurePiecesCollector();
        all.forEach(collector::addPiece);
        return Optional.of(new StructurePosition(new BlockPos(first.getCenter().getX(), pos.getY(), first.getCenter().getZ()), Either.right(collector)));
    }

    private static int startY(DepthBands.Edges edges, Random random, boolean delved) {
        float share = random.nextFloat() * 0.5f;
        if (delved) return edges.at(DepthBands.Band.MITHRIL, 0.5f + share);
        return edges.at(DepthBands.Band.MIDDLE, share);
    }

    private static float airShare(CaveProbe probe, BlockPos pos) {
        int air = 0;
        int total = 0;
        for (int x = pos.getX() - START_AIR_RADIUS; x <= pos.getX() + START_AIR_RADIUS; x++) {
            for (int y = pos.getY(); y <= pos.getY() + START_AIR_HEIGHT; y += 2) {
                for (int z = pos.getZ() - START_AIR_RADIUS; z <= pos.getZ() + START_AIR_RADIUS; z++) {
                    total++;
                    if (!probe.solid(x, y, z)) air++;
                }
            }
        }
        return (float) air / total;
    }

    private static ChunkRandom random(Context context, BlockPos opening, int attempt) {
        ChunkRandom random = new ChunkRandom(new CheckedRandom(0L));
        random.setRegionSeed(context.seed(), opening.getX(), opening.getZ(), RANDOM_SALT + attempt);
        return random;
    }

    // waves: new layouts from the open ends of the mine, the farthest first, so it stretches outwards
    // every layout is pruned like the first one and may not touch anything already there
    private void grow(MineSession session, List<StructurePiece> all, BlockPos origin, Set<BlockPos> bridged) {
        ChunkPos chunk = session.context.chunkPos();
        int target = TARGET_MIN + (MistyCaves.mix(chunk.x, chunk.z, TARGET_SALT) & MASK_16) % (TARGET_MAX - TARGET_MIN + 1);
        List<BlockBox> taken = boxesOf(all);
        int empty = 0;
        for (int wave = 0; wave < MAX_WAVES && all.size() < target && empty < MAX_EMPTY_WAVES; wave++) {
            MineOpenings openings = new MineOpenings(session, all);
            List<Integer> open = new ArrayList<>();
            List<Integer> drops = new ArrayList<>();
            sortOpenings(session, openings, bridged, open, drops);
            sortFarthestFirst(open, openings, origin, wave);
            int starts = WAVE_STARTS_MIN + (MistyCaves.mix(chunk.x, chunk.z, WAVE_STARTS_SALT + wave) & MASK_8) % WAVE_STARTS_SPREAD;
            int added = 0;
            for (int n = 0; n < Math.min(starts, open.size()) && all.size() + added < target; n++) {
                int index = open.get(n);
                BlockPos opening = openings.positions.get(index);
                Direction out = openings.facings.get(index);
                Optional<List<StructurePiece>> layout = layoutFrom(session, opening, out, wave, target - all.size() - added, taken, session::fits);
                if (layout.isEmpty()) {
                    // nothing fits there, perhaps because a cave lies in the way: try to bridge it
                    added += bridgeFrom(session, all, opening, out, bridged, taken);
                    continue;
                }
                addAll(all, taken, layout.get());
                added += layout.get().size();
            }
            for (int n = 0; n < Math.min(WAVE_BRIDGES, drops.size()) && bridged.size() < MAX_CONTINUATIONS; n++) {
                int index = drops.get(n);
                added += bridgeFrom(session, all, openings.positions.get(index), openings.facings.get(index), bridged, taken);
            }
            empty = added == 0 ? empty + 1 : 0;
        }
    }

    // open ends going sideways. One over a drop is tried with a bridge instead
    private static void sortOpenings(MineSession session, MineOpenings openings, Set<BlockPos> bridged, List<Integer> open, List<Integer> drops) {
        for (int i = 0; i < openings.size(); i++) {
            Direction out = openings.facings.get(i);
            if (out.getAxis() == Direction.Axis.Y || openings.connected(i)) continue;
            BlockPos front = openings.positions.get(i).offset(out);
            if (session.probe.solid(front.getX(), front.getY() - 1, front.getZ())) {
                open.add(i);
            } else if (!bridged.contains(openings.positions.get(i))) {
                drops.add(i);
            }
        }
    }

    private static void sortFarthestFirst(List<Integer> open, MineOpenings openings, BlockPos origin, int wave) {
        open.sort(Comparator.comparingLong(i -> {
            BlockPos pos = openings.positions.get(i);
            long dx = pos.getX() - origin.getX();
            long dz = pos.getZ() - origin.getZ();
            return -(long) Math.sqrt(dx * dx + dz * dz) + (MistyCaves.mix(pos.getX(), pos.getZ(), WAVE_ORDER_SALT + wave) & (WAVE_JITTER - 1));
        }));
    }

    // pieces added by a bridge from the opening, 0 when there is none
    private int bridgeFrom(MineSession session, List<StructurePiece> all, BlockPos opening, Direction out, Set<BlockPos> bridged, List<BlockBox> taken) {
        int before = all.size();
        if (!tryBridge(session, all, opening, out, bridged)) return 0;
        all.subList(before, all.size()).forEach(piece -> taken.add(piece.getBoundingBox()));
        return all.size() - before;
    }

    private static List<BlockBox> boxesOf(List<StructurePiece> pieces) {
        List<BlockBox> boxes = new ArrayList<>(pieces.size());
        pieces.forEach(piece -> boxes.add(piece.getBoundingBox()));
        return boxes;
    }

    private static void addAll(List<StructurePiece> all, List<BlockBox> taken, List<StructurePiece> pieces) {
        pieces.forEach(piece -> taken.add(piece.getBoundingBox()));
        all.addAll(pieces);
    }

    // level connectors lead somewhere or go. A level piece (exits at two heights) needs both connected, a hub or
    // crossing with an exit well above its floor gets something attached there when it can, else keeps the exit
    private void sealLevels(MineSession session, List<StructurePiece> all, Set<BlockPos> joined) {
        for (int round = 0; round < SEAL_ROUNDS; round++) {
            MineOpenings openings = new MineOpenings(session, all);
            List<BlockBox> taken = boxesOf(all);
            boolean changed = false;
            for (int piece = 1; piece < all.size() && !changed; piece++) {
                changed = sealPiece(session, all, joined, openings, taken, piece, round);
            }
            if (!changed) return;
        }
    }

    // true when something was attached or the piece was removed
    private boolean sealPiece(MineSession session, List<StructurePiece> all, Set<BlockPos> joined, MineOpenings openings, List<BlockBox> taken, int piece, int round) {
        List<Integer> own = new ArrayList<>();
        int lowest = Integer.MAX_VALUE;
        int highest = Integer.MIN_VALUE;
        for (int j = openings.from(piece); j < openings.to(piece); j++) {
            if (openings.facings.get(j).getAxis() == Direction.Axis.Y) continue;
            own.add(j);
            lowest = Math.min(lowest, openings.positions.get(j).getY());
            highest = Math.max(highest, openings.positions.get(j).getY());
        }
        if (highest - lowest < VERTICAL_RISE) return false;
        // the bottom exit of a pit and both exits of a level piece are where a ladder or stair leads: they have to connect
        List<Integer> low = new ArrayList<>();
        List<Integer> high = new ArrayList<>();
        for (int j : own) {
            if (openings.positions.get(j).getY() <= lowest + LOW_BAND) low.add(j);
            if (openings.positions.get(j).getY() >= lowest + VERTICAL_RISE) high.add(j);
        }
        boolean required = low.size() == 1;
        boolean sealed = true;
        boolean changed = false;
        for (int j : high) {
            if (openings.connected(j) || joined.contains(openings.positions.get(j))) continue;
            if (attachSomething(session, openings.positions.get(j), openings.facings.get(j), taken, round, all)) {
                changed = true;
            } else if (required && high.size() == 1) {
                sealed = false;
            }
        }
        for (int j : low) {
            if (!required || openings.connected(j) || joined.contains(openings.positions.get(j))) continue;
            if (attachSomething(session, openings.positions.get(j), openings.facings.get(j), taken, round, all)) {
                changed = true;
            } else {
                sealed = false;
            }
        }
        if (!sealed) {
            removeWithOrphans(session, all, piece);
            changed = true;
        }
        return changed;
    }

    // facing rock: dead end or rubble plug (even over rock), both need rock under. Cave air, bridged, vertical stay open
    // if nothing fits inside the bands the piece goes with whatever hangs off it, and its neighbours are rechecked
    private void capOpenings(MineSession session, List<StructurePiece> all, Set<BlockPos> bridged, Set<BlockPos> joined) {
        for (int round = 0; round < CAP_ROUNDS; round++) {
            MineOpenings openings = new MineOpenings(session, all);
            List<BlockBox> taken = boxesOf(all);
            List<StructurePiece> added = new ArrayList<>();
            List<StructurePiece> doomed = new ArrayList<>();
            for (int i = 0; i < openings.size(); i++) {
                Direction out = openings.facings.get(i);
                BlockPos opening = openings.positions.get(i);
                if (out.getAxis() == Direction.Axis.Y || bridged.contains(opening) || joined.contains(opening) || openings.connected(i) || !facesRock(session, opening, out, taken)) continue;
                List<StructurePiece> cap = capFor(session, opening, out, taken);
                if (cap.isEmpty() && openings.owners.get(i) > 0) doomed.add(all.get(openings.owners.get(i)));
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

    // a dead end, or else a plug
    private List<StructurePiece> capFor(MineSession session, BlockPos opening, Direction out, List<BlockBox> taken) {
        List<StructurePiece> cap = JigsawAssembler.assemble(session.context, endsPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), opening.offset(out), 1, Integer.MAX_VALUE, taken, session::capFits, session::capFits, random(session.context, opening, CAP_END_SALT));
        if (!cap.isEmpty()) return cap;
        return JigsawAssembler.assemble(session.context, plugsPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), opening.offset(out), 1, Integer.MAX_VALUE, taken,
                (box, origin, floor) -> session.inBand(box) && session.floored(box) && !overlaps(box, taken), session::capFits, random(session.context, opening, CAP_PLUG_SALT));
    }

    // mostly terrain rock in the 3 by 3 cells beyond the opening. One facing another piece stays a blocked alcove
    private static boolean facesRock(MineSession session, BlockPos opening, Direction out, List<BlockBox> taken) {
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
                for (BlockBox box : taken) {
                    if (box.contains(x, y, z)) {
                        inside = true;
                        break;
                    }
                }
                if (inside) {
                    piece++;
                } else if (session.probe.solid(x, y, z)) {
                    solid++;
                }
            }
        }
        return piece < ROCK_CELLS && solid >= ROCK_CELLS;
    }

    // bridge pieces may touch the pieces around them by a block, as assembled ones do, but not share space
    private static boolean overlapsPiece(List<StructurePiece> pieces, List<BlockBox> taken) {
        for (StructurePiece piece : pieces) {
            BlockBox box = piece.getBoundingBox();
            for (BlockBox other : taken) {
                if (box.getMinX() + 1 <= other.getMaxX() && box.getMaxX() - 1 >= other.getMinX()
                        && box.getMinY() + 1 <= other.getMaxY() && box.getMaxY() - 1 >= other.getMinY()
                        && box.getMinZ() + 1 <= other.getMaxZ() && box.getMaxZ() - 1 >= other.getMinZ()) return true;
            }
        }
        return false;
    }

    private static boolean overlaps(BlockBox box, List<BlockBox> taken) {
        for (BlockBox other : taken) {
            if (box.intersects(other)) return true;
        }
        return false;
    }

    // a growth layout from the opening, or else a single dead end
    private boolean attachSomething(MineSession session, BlockPos opening, Direction out, List<BlockBox> taken, int round, List<StructurePiece> all) {
        Optional<List<StructurePiece>> layout = layoutFrom(session, opening, out, ATTACH_WAVE_BASE + round, Integer.MAX_VALUE, taken, session::capFits);
        if (layout.isEmpty()) layout = endFrom(session, opening, out, round, taken);
        if (layout.isEmpty()) return false;
        addAll(all, taken, layout.get());
        return true;
    }

    private Optional<List<StructurePiece>> endFrom(MineSession session, BlockPos opening, Direction out, int round, List<BlockBox> taken) {
        List<StructurePiece> pieces = JigsawAssembler.assemble(session.context, endsPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), opening.offset(out), 1, Integer.MAX_VALUE, taken, session::capFits, session::capFits, random(session.context, opening, END_SALT + round));
        return pieces.isEmpty() ? Optional.empty() : Optional.of(pieces);
    }

    // drops the piece and whatever was only reachable from the start through it
    private static void removeWithOrphans(MineSession session, List<StructurePiece> all, int index) {
        List<StructurePiece> before = reachable(session, all);
        session.links.remove(all.remove(index));
        Set<StructurePiece> after = Collections.newSetFromMap(new IdentityHashMap<>());
        after.addAll(reachable(session, all));
        for (StructurePiece piece : before) {
            if (!after.contains(piece)) all.remove(piece);
        }
    }

    // pieces linked to the first one through jigsaws
    private static List<StructurePiece> reachable(MineSession session, List<StructurePiece> pieces) {
        List<StructurePiece> result = new ArrayList<>();
        if (pieces.isEmpty()) return result;
        Map<StructurePiece, Integer> indices = new IdentityHashMap<>();
        for (int i = 0; i < pieces.size(); i++) indices.put(pieces.get(i), i);
        MineOpenings openings = new MineOpenings(session, pieces);
        Map<BlockPos, Integer> at = new HashMap<>();
        for (int j = 0; j < openings.size(); j++) at.put(openings.positions.get(j), openings.owners.get(j));
        boolean[] seen = new boolean[pieces.size()];
        Deque<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        seen[0] = true;
        while (!queue.isEmpty()) {
            int index = queue.poll();
            result.add(pieces.get(index));
            // a bridge, and the mine behind it, is reached through the piece it starts from
            for (StructurePiece linked : session.links.getOrDefault(pieces.get(index), List.of())) {
                Integer other = indices.get(linked);
                if (other == null || seen[other]) continue;
                seen[other] = true;
                queue.add(other);
            }
            for (int j = openings.from(index); j < openings.to(index); j++) {
                Integer other = at.get(openings.positions.get(j).offset(openings.facings.get(j)));
                if (other == null || seen[other]) continue;
                seen[other] = true;
                queue.add(other);
            }
        }
        return result;
    }

    private Optional<List<StructurePiece>> layoutFrom(MineSession session, BlockPos opening, Direction out, int wave, int limit, List<BlockBox> taken, JigsawAssembler.Fit accept) {
        int size = WAVE_SIZE_MIN + (MistyCaves.mix(opening.getX(), opening.getZ(), WAVE_SIZE_SALT + wave) & MASK_8) % WAVE_SIZE_SPREAD;
        List<StructurePiece> pieces = JigsawAssembler.assemble(session.context, corridorPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), opening.offset(out), size, limit, taken, accept, session::capFits, random(session.context, opening, WAVE_LAYOUT_SALT + wave));
        return pieces.size() >= WAVE_MIN_PIECES ? Optional.of(pieces) : Optional.empty();
    }

    // where an opening faces a drop, a bridge to rock on the far side and a mine continuing from there
    private void continueAcrossDrops(MineSession session, List<StructurePiece> all, Set<BlockPos> bridged) {
        MineOpenings openings = new MineOpenings(session, all);
        for (int i = 0; i < openings.size() && bridged.size() < MAX_CONTINUATIONS; i++) {
            if (openings.facings.get(i).getAxis() == Direction.Axis.Y || openings.connected(i) || bridged.contains(openings.positions.get(i))) continue;
            tryBridge(session, all, openings.positions.get(i), openings.facings.get(i), bridged);
        }
    }

    // a bridge from an open end, with the mine continuing on the far side. True when one was added
    private boolean tryBridge(MineSession session, List<StructurePiece> all, BlockPos opening, Direction out, Set<BlockPos> bridged) {
        if (bridged.size() >= MAX_CONTINUATIONS) return false;
        int landing = MineBridge.landingLength(session.context.structureTemplateManager(), false);
        if (landing < 0) return false;
        Optional<List<StructurePiece>> added = continueFrom(session, opening, out, landing, boxesOf(all), bridged.size());
        if (added.isEmpty()) return false;
        for (StructurePiece piece : all) {
            if (piece.getBoundingBox().contains(opening)) {
                session.links.computeIfAbsent(piece, key -> new ArrayList<>()).addAll(added.get());
                break;
            }
        }
        all.addAll(added.get());
        bridged.add(opening);
        return true;
    }

    private Optional<List<StructurePiece>> continueFrom(MineSession session, BlockPos opening, Direction out, int landing, List<BlockBox> taken, int count) {
        Context context = session.context;
        CaveProbe probe = session.probe;
        int deckY = opening.getY() - 1;
        // the pieces stop short of the chasm, so the floor ahead of the opening is walked to its edge first
        int lead = 0;
        while (lead < MAX_LEAD) {
            BlockPos cell = opening.offset(out, lead + 1);
            if (!probe.solid(cell.getX(), deckY, cell.getZ())) break;
            lead++;
        }
        int[] chasm = measureChasm(probe, opening, out, lead, deckY);
        int gap = chasm[0];
        int dropped = chasm[1];
        if (gap < MIN_GAP || gap >= MAX_GAP || dropped < gap * DROP_SHARE) return Optional.empty();
        // the walk to the edge is a plain tunnel, the span starts at the edge
        MineTunnelPiece tunnel = lead > 0 ? tunnel(opening, out, 1, lead, deckY) : null;
        // every span that reaches the far rim without overshooting too far is tried, shortest first
        for (int span = 0; span < MineBridge.SPAN_LENGTH.length; span++) {
            int length = MineBridge.SPAN_LENGTH[span];
            int over = length - 1 - gap;
            if (over < -SPAN_SHORT_BY || over > SPAN_LONG_BY) continue;
            int hash = MistyCaves.mix(opening.getX(), opening.getZ(), BRIDGE_SALT + count);
            Optional<List<StructurePiece>> bridge = MineBridge.bridge(context.structureTemplateManager(), probe, span, (MistyCaves.mix(hash, 1, 0) & MASK_2) == 0, out,
                    opening.offset(out, lead + (length - 1) / 2).withY(deckY),
                    (MistyCaves.mix(hash, 2, 0) & MASK_2) == 0, (MistyCaves.mix(hash, 3, 0) & MASK_2) == 0, true, false, false);
            if (bridge.isEmpty()) continue;
            List<StructurePiece> built = new ArrayList<>(bridge.get());
            if (tunnel != null) built.add(tunnel);
            if (overlapsPiece(built, taken)) continue;

            // the continuation starts right behind the far landing, with a corridor end facing back at it
            BlockPos start = opening.offset(out, lead + length + landing).withY(deckY + 1);
            List<BlockBox> blocked = new ArrayList<>(taken);
            built.forEach(piece -> blocked.add(piece.getBoundingBox()));
            List<StructurePiece> pieces = JigsawAssembler.assemble(context, corridorPool, Optional.of(MINE_CORRIDOR), Optional.of(out.getOpposite()), start, CONTINUATION_SIZE, Integer.MAX_VALUE, blocked, session::fits, session::capFits, random(context, opening, CONTINUATION_SALT));
            if (pieces.size() < CONTINUATION_MIN_PIECES) continue;
            List<StructurePiece> result = new ArrayList<>(built);
            result.addAll(pieces);
            return Optional.of(result);
        }
        return Optional.empty();
    }

    // the cells past the lead that are open air at deck height, and how many of them have a drop under them
    private static int[] measureChasm(CaveProbe probe, BlockPos opening, Direction out, int lead, int deckY) {
        int gap = 0;
        int dropped = 0;
        while (gap < MAX_GAP) {
            BlockPos cell = opening.offset(out, lead + gap + 1);
            if (probe.solid(cell.getX(), deckY, cell.getZ()) || probe.solid(cell.getX(), deckY + 1, cell.getZ())) break;
            boolean drop = true;
            for (int down = 1; down <= DROP_DEPTH && drop; down++) drop = !probe.solid(cell.getX(), deckY - down, cell.getZ());
            if (drop) dropped++;
            gap++;
        }
        return new int[]{gap, dropped};
    }

    // a 3 wide, 3 tall tunnel with its floor row, from the cell this far to the cell that far out from the opening
    private static MineTunnelPiece tunnel(BlockPos opening, Direction out, int from, int to, int floorY) {
        BlockPos first = opening.offset(out, from);
        BlockPos last = opening.offset(out, to);
        int sideX = out.getOffsetZ();
        int sideZ = out.getOffsetX();
        return new MineTunnelPiece(new BlockBox(
                Math.min(first.getX(), last.getX()) - Math.abs(sideX), floorY, Math.min(first.getZ(), last.getZ()) - Math.abs(sideZ),
                Math.max(first.getX(), last.getX()) + Math.abs(sideX), floorY + TUNNEL_HEIGHT, Math.max(first.getZ(), last.getZ()) + Math.abs(sideZ)));
    }

    // two ends facing each other across a block or three of rock are joined by a tunnel, else the paths run side by side,
    // and the plug or end that would close each of them seals the wall between them
    private void joinNearbyEnds(MineSession session, List<StructurePiece> all, Set<BlockPos> joined) {
        MineOpenings openings = new MineOpenings(session, all);
        List<StructurePiece> tunnels = new ArrayList<>();
        for (int i = 0; i < openings.size(); i++) {
            Direction out = openings.facings.get(i);
            BlockPos opening = openings.positions.get(i);
            if (out.getAxis() == Direction.Axis.Y || openings.connected(i) || joined.contains(opening)) continue;
            for (int distance = 2; distance <= JOIN_MAX; distance++) {
                int other = openings.indexAt(opening.offset(out, distance));
                if (other < 0 || openings.facings.get(other) != out.getOpposite() || openings.owners.get(other).equals(openings.owners.get(i)) || joined.contains(openings.positions.get(other))) continue;
                MineTunnelPiece tunnel = tunnel(opening, out, 1, distance - 1, opening.getY() - 1);
                if (!rockUnder(session, tunnel.getBoundingBox())) break;
                StructurePiece a = all.get(openings.owners.get(i));
                StructurePiece b = all.get(openings.owners.get(other));
                session.links.computeIfAbsent(a, key -> new ArrayList<>()).add(tunnel);
                session.links.computeIfAbsent(b, key -> new ArrayList<>()).add(tunnel);
                session.links.computeIfAbsent(tunnel, key -> new ArrayList<>()).add(a);
                session.links.get(tunnel).add(b);
                tunnels.add(tunnel);
                joined.add(opening);
                joined.add(openings.positions.get(other));
                break;
            }
        }
        all.addAll(tunnels);
    }

    // rock under every cell of the tunnel's floor, so a join never bridges a hole
    private static boolean rockUnder(MineSession session, BlockBox box) {
        for (int x = box.getMinX(); x <= box.getMaxX(); x++) {
            for (int z = box.getMinZ(); z <= box.getMaxZ(); z++) {
                if (!session.probe.solid(x, box.getMinY() - 1, z)) return false;
            }
        }
        return true;
    }

    @Override
    public StructureType<?> getType() {
        return Caves.MISTY_DWARF_MINE;
    }
}
