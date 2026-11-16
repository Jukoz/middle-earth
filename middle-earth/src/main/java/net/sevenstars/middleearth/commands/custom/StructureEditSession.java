package net.sevenstars.middleearth.commands.custom;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SpawnerBlock;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.block.TrialSpawnerBlock;
import net.minecraft.block.VaultBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.minecraft.util.WorldSavePath;
import net.minecraft.server.MinecraftServer;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtInt;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.pool.ListPoolElement;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.registry.RegistryOps;
import net.minecraft.structure.StructurePiecesList;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.world.chunk.Chunk;
import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.entity.npcs.routine.RoutineSites;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.StructureLiquidSettings;
import net.sevenstars.middleearth.registries.content.structurefeatures.StructureFeaturesRegistry;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// A whole structure laid out for its builder, as the game would assemble it from its jigsaw pieces. Assembled, it
// stands as it would in the world and is only for looking at. Spaced, the pieces are set apart, each whole in its
// own box, and changes to one are saved back to the template of that piece
public class StructureEditSession {
    // In blocks. The gap left between spaced pieces unless the builder asks for another
    public static final int DEFAULT_GAP = 4;
    public static final int MAXIMUM_GAP = 64;
    private static final double FIRST_SPREAD = 1.5;
    private static final double SPREAD_STEP = 0.25;
    private static final double MAXIMUM_SPREAD = 6;
    // The structure is always assembled in this chunk and then moved, so the same seed gives the same pieces
    // wherever the builder stands
    private static final ChunkPos ASSEMBLY_CHUNK = new ChunkPos(0, 0);
    // The game drops pieces that reach out of the world, so use the tallest possible world to make sure none do
    private static final HeightLimitView ASSEMBLY_HEIGHT = HeightLimitView.create(-2032, 4064);
    private static final String START_HEIGHT_KEY = "start_height";
    private static final String PROJECT_TO_HEIGHTMAP_KEY = "project_start_to_heightmap";
    private static final String ABSOLUTE_KEY = "absolute";
    private static final String START_POOL_KEY = "start_pool";
    private static final String START_JIGSAW_NAME_KEY = "start_jigsaw_name";
    private static final String SIZE_KEY = "size";
    // How many pieces deep a jigsaw structure may be assembled
    private static final int MAXIMUM_DEPTH = 7;
    private static final String PALETTE = "palette";
    private static final String PALETTES = "palettes";
    private static final String BLOCKS = "blocks";
    private static final String POS = "pos";
    private static final String STATE = "state";
    private static final String NBT = "nbt";
    private static final String SIZE = "size";
    private static final String LOOT_TABLE_SEED = "LootTableSeed";
    private static final int DIFFERENCES_LOGGED = 6;
    private static final int DIFFERENCE_LENGTH = 90;

    private static final Map<UUID, Integer> GAPS = new HashMap<>();
    // Sessions read back with the world that no builder has claimed yet
    private static final Set<UUID> RESTORED = new HashSet<>();
    private static final Set<UUID> SINGLES = new HashSet<>();
    private static final String FILE = "data/middle-earth_structure_editing.nbt";
    private static final String SESSIONS_KEY = "sessions";
    private static final String BUILDER = "builder";
    private static final String DIMENSION = "dimension";
    private static final String STRUCTURE = "structure";
    private static final String SEED = "seed";
    private static final String SPACED = "spaced";
    private static final String GAP = "gap";
    private static final String PIECES = "pieces";
    private static final String TEMPLATE = "template";
    private static final String ROTATION = "rotation";
    private static final String ORIGIN = "origin";
    private static final String BOX = "box";
    private static final String LOADED = "loaded";

    private static final Map<UUID, StructureEditSession> SESSIONS = new HashMap<>();

    // The corner the template was placed from, and the blocks it had when it was loaded, used to tell what has changed since
    public record Piece(Identifier template, BlockPos origin, BlockRotation rotation, BlockBox box, NbtCompound loaded) {}

    public record Saved(List<Identifier> written, List<Identifier> conflicts) {}

    private final RegistryKey<World> dimension;
    private final Identifier structure;
    private final long seed;
    private final boolean spaced;
    private final int gap;
    private final List<Piece> pieces;

    private StructureEditSession(RegistryKey<World> dimension, Identifier structure, long seed, boolean spaced, int gap, List<Piece> pieces) {
        this.gap = gap;
        this.dimension = dimension;
        this.structure = structure;
        this.seed = seed;
        this.spaced = spaced;
        this.pieces = pieces;
    }

    // The builder's own session, or else the one restored with the world that nobody has claimed yet. A game run from the
    // workspace gives its player a different name, and so a different id, every time
    public static @Nullable StructureEditSession of(UUID builder) {
        StructureEditSession own = SESSIONS.get(builder);
        if(own != null || RESTORED.size() != 1)
            return own;
        return SESSIONS.get(RESTORED.iterator().next());
    }

    // Makes the session restored with the world the builder's own, so the commands can work on it
    public static @Nullable StructureEditSession claim(UUID builder) {
        if(SESSIONS.containsKey(builder)) {
            RESTORED.remove(builder);
            return SESSIONS.get(builder);
        }
        if(RESTORED.size() != 1)
            return null;
        StructureEditSession restored = SESSIONS.remove(RESTORED.iterator().next());
        RESTORED.clear();
        if(restored != null) {
            SESSIONS.put(builder, restored);
            GAPS.put(builder, restored.gap);
        }
        return restored;
    }

    // The pieces belong to the world that was open and mean nothing in the next one
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(StructureEditSession::restore);
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            SESSIONS.clear();
            RESTORED.clear();
            SINGLES.clear();
            TESTS.clear();
            StructureEditing.clearNoClip();
        });
    }

    // Saved with the world. The pieces stay in it as blocks, and without this nothing would recognise them as pieces again
    private static void store(MinecraftServer server) {
        NbtList sessions = new NbtList();
        for(Map.Entry<UUID, StructureEditSession> entry : SESSIONS.entrySet()) {
            StructureEditSession session = entry.getValue();
            NbtCompound stored = new NbtCompound();
            stored.putString(BUILDER, entry.getKey().toString());
            stored.putString(DIMENSION, session.dimension.getValue().toString());
            stored.putString(STRUCTURE, session.structure.toString());
            stored.putLong(SEED, session.seed);
            stored.putBoolean(SPACED, session.spaced);
            stored.putInt(GAP, session.gap);
            NbtList pieces = new NbtList();
            for(Piece piece : session.pieces) {
                NbtCompound storedPiece = new NbtCompound();
                storedPiece.putString(TEMPLATE, piece.template().toString());
                storedPiece.putString(ROTATION, piece.rotation().name());
                storedPiece.putIntArray(ORIGIN, new int[]{piece.origin().getX(), piece.origin().getY(), piece.origin().getZ()});
                BlockBox box = piece.box();
                storedPiece.putIntArray(BOX, new int[]{box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ()});
                storedPiece.put(LOADED, piece.loaded());
                pieces.add(storedPiece);
            }
            stored.put(PIECES, pieces);
            sessions.add(stored);
        }
        NbtCompound root = new NbtCompound();
        root.put(SESSIONS_KEY, sessions);
        try {
            Path file = server.getSavePath(WorldSavePath.ROOT).resolve(FILE);
            Files.createDirectories(file.getParent());
            NbtIo.writeCompressed(root, file);
        } catch (IOException e) {
            MiddleEarth.LOGGER.logError("StructureEditSession :: could not keep the loaded structures with the world", e);
        }
    }

    private static void restore(MinecraftServer server) {
        SESSIONS.clear();
        RESTORED.clear();
        Path file = server.getSavePath(WorldSavePath.ROOT).resolve(FILE);
        if(!Files.isRegularFile(file))
            return;
        try {
            NbtCompound root = NbtIo.readCompressed(file, NbtSizeTracker.ofUnlimitedBytes());
            for(NbtElement element : root.getListOrEmpty(SESSIONS_KEY)) {
                if(!(element instanceof NbtCompound stored))
                    continue;
                Identifier dimension = Identifier.tryParse(stored.getString(DIMENSION, ""));
                Identifier structure = Identifier.tryParse(stored.getString(STRUCTURE, ""));
                if(dimension == null || structure == null)
                    continue;
                List<Piece> pieces = new ArrayList<>();
                for(NbtElement pieceElement : stored.getListOrEmpty(PIECES)) {
                    if(!(pieceElement instanceof NbtCompound storedPiece))
                        continue;
                    Identifier template = Identifier.tryParse(storedPiece.getString(TEMPLATE, ""));
                    int[] origin = storedPiece.getIntArray(ORIGIN).orElse(new int[0]);
                    int[] box = storedPiece.getIntArray(BOX).orElse(new int[0]);
                    if(template == null || origin.length != 3 || box.length != 6)
                        continue;
                    pieces.add(new Piece(template, new BlockPos(origin[0], origin[1], origin[2]), BlockRotation.valueOf(storedPiece.getString(ROTATION, BlockRotation.NONE.name())),
                            new BlockBox(box[0], box[1], box[2], box[3], box[4], box[5]), withoutSeeds(storedPiece.getCompoundOrEmpty(LOADED))));
                }
                UUID builder = UUID.fromString(stored.getString(BUILDER, ""));
                SESSIONS.put(builder, new StructureEditSession(RegistryKey.of(RegistryKeys.WORLD, dimension), structure, stored.getLong(SEED, 0),
                        stored.getBoolean(SPACED, true), stored.getInt(GAP, DEFAULT_GAP), pieces));
                RESTORED.add(builder);
                StructureEditing.log("%s came back with the world: %d pieces, %s".formatted(structure, pieces.size(), stored.getBoolean(SPACED, true) ? "spaced" : "assembled"));
            }
        } catch (IOException | IllegalArgumentException e) {
            MiddleEarth.LOGGER.logError("StructureEditSession :: could not read the structures that were loaded in this world", e);
        }
        pause(server);
    }

    // A template loaded on its own is edited too, though it isn't a session
    public static void beginSingle(MinecraftServer server, UUID builder) {
        SINGLES.add(builder);
        pause(server);
    }

    public static boolean endSingle(MinecraftServer server, UUID builder) {
        boolean ended = SINGLES.remove(builder);
        pause(server);
        return ended;
    }

    // While pieces are in the world to be edited, the world is frozen: nothing grows, flows, burns or wakes up in them, so
    // what is saved is what the builder made. Builders can still move and build. A frozen game isn't saved with the world,
    // so this is applied again when the server starts
    private static void pause(MinecraftServer server) {
        boolean editing = !SINGLES.isEmpty() || SESSIONS.values().stream().anyMatch(session -> session.spaced);
        if(server.getTickManager().isFrozen() == editing)
            return;
        server.getTickManager().setFrozen(editing);
        StructureEditing.log(editing ? "the world stands still while pieces are edited" : "the world runs again");
    }

    public static int gapOf(UUID builder) {
        return GAPS.getOrDefault(builder, DEFAULT_GAP);
    }

    public static void setGap(UUID builder, int gap) {
        GAPS.put(builder, Math.clamp(gap, 0, MAXIMUM_GAP));
    }

    public int getGap() {
        return gap;
    }

    // The piece the builder is in, or else the nearest one
    public @Nullable Piece pieceAt(BlockPos pos) {
        Piece nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        for(Piece piece : pieces) {
            if(piece.box().contains(pos))
                return piece;
            double distance = piece.box().getCenter().getSquaredDistance(pos);
            if(distance < nearestDistance) {
                nearestDistance = distance;
                nearest = piece;
            }
        }
        return nearest;
    }

    public @Nullable Piece pieceContaining(BlockPos pos) {
        return pieces.stream().filter(piece -> piece.box().contains(pos)).findFirst().orElse(null);
    }

    // Expands a piece on one side, to be built into and saved as part of its template. Returns null on success, or else the
    // piece that is in the way
    public @Nullable Piece expand(ServerWorld world, Piece piece, Direction side, int blocks) {
        BlockBox box = piece.box();
        BlockBox grown = new BlockBox(
                box.getMinX() + Math.min(0, side.getOffsetX() * blocks), box.getMinY() + Math.min(0, side.getOffsetY() * blocks), box.getMinZ() + Math.min(0, side.getOffsetZ() * blocks),
                box.getMaxX() + Math.max(0, side.getOffsetX() * blocks), box.getMaxY() + Math.max(0, side.getOffsetY() * blocks), box.getMaxZ() + Math.max(0, side.getOffsetZ() * blocks));
        for(Piece other : pieces) {
            if(other != piece && other.box().intersects(grown)) {
                StructureEditing.log("%s not made bigger to the %s by %d: %s in %s is in the way of %s".formatted(piece.template(), side.asString(), blocks,
                        other.template(), describe(other.box()), describe(grown)));
                return other;
            }
        }
        // The new room is open, like what a template leaves out, until something is built in it
        for(BlockPos pos : BlockPos.iterate(grown.getMinX(), grown.getMinY(), grown.getMinZ(), grown.getMaxX(), grown.getMaxY(), grown.getMaxZ())) {
            if(!box.contains(pos) && world.isInBuildLimit(pos))
                world.setBlockState(pos, Blocks.STRUCTURE_VOID.getDefaultState(), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        }
        pieces.set(pieces.indexOf(piece), new Piece(piece.template(), originOf(grown, piece.rotation()), piece.rotation(), grown, piece.loaded()));
        store(world.getServer());
        StructureEditing.log("%s made bigger to the %s by %d: %s where it was %s".formatted(piece.template(), side.asString(), blocks, describe(grown), describe(box)));
        return null;
    }

    // The box a piece is saved with, set as a whole. Anything it leaves out stays in the world, to be taken in again or cleared by hand
    public @Nullable Piece resize(ServerWorld world, Piece piece, BlockBox resized) {
        BlockBox box = piece.box();
        for(Piece other : pieces) {
            if(other != piece && other.box().intersects(resized)) {
                StructureEditing.log("%s not given the bounds %s: %s in %s is in the way".formatted(piece.template(), describe(resized), other.template(), describe(other.box())));
                return other;
            }
        }
        // New empty room is open, like what a template leaves out. Blocks already built there are taken in as they are
        for(BlockPos pos : BlockPos.iterate(resized.getMinX(), resized.getMinY(), resized.getMinZ(), resized.getMaxX(), resized.getMaxY(), resized.getMaxZ())) {
            if(!box.contains(pos) && world.isInBuildLimit(pos) && world.getBlockState(pos).isAir())
                world.setBlockState(pos, Blocks.STRUCTURE_VOID.getDefaultState(), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        }
        pieces.set(pieces.indexOf(piece), new Piece(piece.template(), originOf(resized, piece.rotation()), piece.rotation(), resized, piece.loaded()));
        store(world.getServer());
        StructureEditing.log("%s given the bounds %s where it had %s".formatted(piece.template(), describe(resized), describe(box)));
        return null;
    }

    // The corner of its box that a rotated template is placed from
    private static BlockPos originOf(BlockBox box, BlockRotation rotation) {
        return switch (rotation) {
            case NONE -> new BlockPos(box.getMinX(), box.getMinY(), box.getMinZ());
            case CLOCKWISE_90 -> new BlockPos(box.getMaxX(), box.getMinY(), box.getMinZ());
            case CLOCKWISE_180 -> new BlockPos(box.getMaxX(), box.getMinY(), box.getMaxZ());
            case COUNTERCLOCKWISE_90 -> new BlockPos(box.getMinX(), box.getMinY(), box.getMaxZ());
        };
    }

    // The size of the template as it lies unrotated
    private static Vec3i sizeOf(BlockBox box, BlockRotation rotation) {
        boolean turned = rotation == BlockRotation.CLOCKWISE_90 || rotation == BlockRotation.COUNTERCLOCKWISE_90;
        return new Vec3i(turned ? box.getBlockCountZ() : box.getBlockCountX(), box.getBlockCountY(), turned ? box.getBlockCountX() : box.getBlockCountZ());
    }

    public boolean hasChanges(ServerWorld world) {
        if(!spaced)
            return false;
        for(Piece piece : pieces) {
            StructureTemplate template = world.getStructureTemplateManager().getTemplate(piece.template()).orElse(null);
            if(template != null && !read(world, piece, piece.loaded()).equals(piece.loaded()))
                return true;
        }
        return false;
    }

    public RegistryKey<World> getDimension() {
        return dimension;
    }

    public Identifier getStructure() {
        return structure;
    }

    public long getSeed() {
        return seed;
    }

    public boolean isSpaced() {
        return spaced;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    // Null when the structure can't be put together from that seed, or none of its pieces comes from a template
    public static @Nullable StructureEditSession load(ServerWorld world, UUID builder, RegistryEntry<Structure> structure, Identifier id, long seed, boolean spaced, BlockPos corner) {
        StructureStart start = assemble(world, structure, structure.value(), seed, world, ASSEMBLY_CHUNK);
        // A structure that goes underground is cut short where the ground is thin, as in a flat world. Use whichever version
        // gives more pieces to edit
        Structure free = freeStanding(world, structure.value());
        StructureStart freeStart = free == null ? null : assemble(world, structure, free, seed, ASSEMBLY_HEIGHT, ASSEMBLY_CHUNK);
        if(freeStart != null && freeStart.getChildren().size() > start.getChildren().size())
            start = freeStart;
        if(!start.hasChildren()) {
            StructureEditing.log("%s from the seed %d: the game put no pieces together".formatted(id, seed));
            return null;
        }

        StructureTemplateManager manager = world.getStructureTemplateManager();
        List<Piece> assembled = new ArrayList<>();
        for(StructurePiece child : start.getChildren()) {
            if(!(child instanceof PoolStructurePiece piece))
                continue;
            for(Identifier template : templatesOf(piece.getPoolElement())) {
                StructureTemplate found = manager.getTemplate(template).orElse(null);
                if(found != null)
                    assembled.add(new Piece(template, piece.getPos(), piece.getRotation(), boxOf(found, piece.getPos(), piece.getRotation()), new NbtCompound()));
            }
        }
        if(assembled.isEmpty()) {
            StructureEditing.log("%s from the seed %d: %d pieces, none of them a template of its own".formatted(id, seed, start.getChildren().size()));
            return null;
        }

        int gap = gapOf(builder);
        List<Piece> placed = spaced ? spread(assembled, gap) : assembled;
        BlockBox whole = boundsOf(placed);
        Vec3i move = new Vec3i(corner.getX() - whole.getMinX(), corner.getY() - whole.getMinY(), corner.getZ() - whole.getMinZ());
        List<Piece> pieces = new ArrayList<>();
        for(Piece piece : placed)
            pieces.add(new Piece(piece.template(), piece.origin().add(move), piece.rotation(), piece.box().offset(move.getX(), move.getY(), move.getZ()), piece.loaded()));

        close(world, builder);
        fill(world, boundsOf(pieces), Blocks.AIR.getDefaultState());
        List<Piece> loaded = new ArrayList<>();
        for(Piece piece : pieces)
            loaded.add(place(world, piece, spaced));
        // A piece placed against an earlier one changes the blocks along their seam after the earlier one was read,
        // so all the pieces are read again
        if(spaced)
            loaded.replaceAll(piece -> new Piece(piece.template(), piece.origin(), piece.rotation(), piece.box(), read(world, piece, null)));
        StructureEditSession session = new StructureEditSession(world.getRegistryKey(), id, seed, spaced, gap, loaded);
        SESSIONS.put(builder, session);
        store(world.getServer());
        pause(world.getServer());
        StructureEditing.log("loaded %s from the seed %d, %s, %d pieces in %s".formatted(id, seed, spaced ? "spaced %d apart".formatted(gap) : "assembled", loaded.size(), describe(boundsOf(loaded))));
        for(Piece piece : loaded)
            StructureEditing.log("  piece %s turned %s in %s".formatted(piece.template(), piece.rotation().asString(), describe(piece.box())));
        return session;
    }

    private static StructureStart assemble(ServerWorld world, RegistryEntry<Structure> entry, Structure structure, long seed, HeightLimitView height, ChunkPos chunk) {
        return structure.createStructureStart(entry, world.getRegistryKey(), world.getRegistryManager(),
                world.getChunkManager().getChunkGenerator(), world.getChunkManager().getChunkGenerator().getBiomeSource(), world.getChunkManager().getNoiseConfig(),
                world.getStructureTemplateManager(), seed, chunk, 0, height, biome -> true);
    }

    public record Generated(BlockBox box, int pieces) {}

    private record Test(RegistryKey<World> dimension, Structure structure, ChunkPos chunk) {}

    private static final Map<UUID, List<Test>> TESTS = new HashMap<>();

    // The structure as the game would generate it, at the builder, and registered with the world as a structure: its
    // managers wake up, its people are born and its markers are gone. Placing it with a command would only set
    // blocks. A structure the ground would cut short is placed whole at the builder's height instead of on the ground
    public static @Nullable Generated generate(ServerWorld world, UUID builder, RegistryEntry<Structure> structure, long seed, BlockPos at) {
        ChunkPos chunk = new ChunkPos(at);
        StructureStart start = assemble(world, structure, structure.value(), seed, world, chunk);
        Structure free = freeStanding(world, structure.value());
        StructureStart freeStart = free == null ? null : assemble(world, structure, free, seed, ASSEMBLY_HEIGHT, chunk);
        if(freeStart != null && freeStart.getChildren().size() > start.getChildren().size()) {
            List<StructurePiece> pieces = freeStart.getChildren();
            BlockBox whole = BlockBox.encompass(pieces.stream().map(StructurePiece::getBoundingBox).toList()).orElseThrow();
            int rise = at.getY() - pieces.getFirst().getBoundingBox().getMinY();
            rise += Math.max(0, world.getBottomY() + 1 - (whole.getMinY() + rise));
            rise -= Math.max(0, whole.getMaxY() + rise - (world.getTopYInclusive() - 1));
            if(whole.getMinY() + rise <= world.getBottomY()) {
                StructureEditing.log("%s from the seed %d not generated: %d blocks tall, more than the world".formatted(structure.getIdAsString(), seed, whole.getBlockCountY()));
                return null;
            }
            for(StructurePiece piece : pieces)
                piece.translate(0, rise, 0);
            // A start remembers the bounds it had before its pieces were moved
            start = new StructureStart(structure.value(), chunk, 0, new StructurePiecesList(pieces));
        }
        if(!start.hasChildren()) {
            StructureEditing.log("%s from the seed %d: the game put no pieces together".formatted(structure.getIdAsString(), seed));
            return null;
        }
        BlockBox box = start.getBoundingBox();
        register(world, structure.value(), start, chunk);
        TESTS.computeIfAbsent(builder, key -> new ArrayList<>()).add(new Test(world.getRegistryKey(), structure.value(), chunk));
        StructureEditing.log("generated %s from the seed %d: %d pieces in %s".formatted(structure.getIdAsString(), seed, start.getChildren().size(), describe(box)));
        return new Generated(box, start.getChildren().size());
    }

    // One piece on its own, placed at the builder as a structure the world knows: its people are born and its markers are
    // gone, with no pool and no structure of its own. The world stores it under the name of an existing structure, which is
    // only a name to store it by
    public static @Nullable Generated generatePiece(ServerWorld world, UUID builder, Identifier template, RegistryEntry<Structure> carrier, BlockPos at) {
        StructureTemplateManager manager = world.getStructureTemplateManager();
        if(manager.getTemplate(template).isEmpty())
            return null;
        Generated joined = generateFrom(world, builder, template, carrier, at);
        if(joined != null)
            return joined;
        StructurePoolElement element = StructurePoolElement.ofSingle(template.toString()).apply(StructurePool.Projection.RIGID);
        BlockBox box = element.getBoundingBox(manager, at, BlockRotation.NONE);
        ChunkPos chunk = new ChunkPos(at);
        StructureStart start = new StructureStart(carrier.value(), chunk, 0, new StructurePiecesList(List.of(
                new PoolStructurePiece(manager, element, at, 0, BlockRotation.NONE, box, StructureLiquidSettings.IGNORE_WATERLOGGING))));
        register(world, carrier.value(), start, chunk);
        TESTS.computeIfAbsent(builder, key -> new ArrayList<>()).add(new Test(world.getRegistryKey(), carrier.value(), chunk));
        StructureEditing.log("generated the piece %s by itself in %s, kept as %s".formatted(template, describe(box), carrier.getIdAsString()));
        return new Generated(box, 1);
    }

    // The piece with everything its plugs ask for, as the game would assemble them from the pools it has read. It uses the
    // structure the piece is stored as, started from a pool of this piece alone. Null if that structure isn't made of jigsaw
    // pieces, or nothing came of it
    private static @Nullable Generated generateFrom(ServerWorld world, UUID builder, Identifier template, RegistryEntry<Structure> carrier, BlockPos at) {
        RegistryOps<JsonElement> ops = world.getRegistryManager().getOps(JsonOps.INSTANCE);
        JsonElement json = Structure.STRUCTURE_CODEC.encodeStart(ops, carrier.value()).result().orElse(null);
        if(json == null || !json.isJsonObject() || !json.getAsJsonObject().has(START_POOL_KEY))
            return null;
        JsonObject element = new JsonObject();
        element.addProperty("element_type", "minecraft:single_pool_element");
        element.addProperty("location", template.toString());
        element.addProperty("projection", StructurePool.Projection.RIGID.getId());
        element.addProperty("processors", "minecraft:empty");
        JsonObject weighted = new JsonObject();
        weighted.addProperty("weight", 1);
        weighted.add("element", element);
        JsonArray elements = new JsonArray();
        elements.add(weighted);
        JsonObject pool = new JsonObject();
        pool.addProperty("fallback", "minecraft:empty");
        pool.add("elements", elements);
        JsonObject height = new JsonObject();
        height.addProperty(ABSOLUTE_KEY, at.getY());
        JsonObject data = json.getAsJsonObject();
        data.add(START_POOL_KEY, pool);
        data.add(START_HEIGHT_KEY, height);
        data.addProperty(SIZE_KEY, MAXIMUM_DEPTH);
        data.remove(PROJECT_TO_HEIGHTMAP_KEY);
        data.remove(START_JIGSAW_NAME_KEY);
        Structure whole = Structure.STRUCTURE_CODEC.parse(ops, data).result().orElse(null);
        if(whole == null)
            return null;
        ChunkPos chunk = new ChunkPos(at);
        List<StructurePiece> pieces = assemble(world, carrier, whole, world.getRandom().nextLong(), world, chunk).getChildren();
        if(pieces.isEmpty())
            return null;
        // Work out the offset before moving anything, because translating a piece also moves its bounding box
        BlockBox first = pieces.getFirst().getBoundingBox();
        Vec3i move = new Vec3i(at.getX() - first.getMinX(), at.getY() - first.getMinY(), at.getZ() - first.getMinZ());
        for(StructurePiece piece : pieces)
            piece.translate(move.getX(), move.getY(), move.getZ());
        StructureStart start = new StructureStart(carrier.value(), chunk, 0, new StructurePiecesList(pieces));
        register(world, carrier.value(), start, chunk);
        TESTS.computeIfAbsent(builder, key -> new ArrayList<>()).add(new Test(world.getRegistryKey(), carrier.value(), chunk));
        StructureEditing.log("generated the piece %s with what its plugs ask for, %d pieces in %s, kept as %s".formatted(template, pieces.size(), describe(start.getBoundingBox()), carrier.getIdAsString()));
        // The box of the start is wider than its pieces where the ground is shaped to it
        return new Generated(BlockBox.encompass(pieces.stream().map(StructurePiece::getBoundingBox).toList()).orElse(start.getBoundingBox()), pieces.size());
    }

    private static void register(ServerWorld world, Structure structure, StructureStart start, ChunkPos chunk) {
        BlockBox box = start.getBoundingBox();
        ChunkPos.stream(new ChunkPos(box.getMinX() >> 4, box.getMinZ() >> 4), new ChunkPos(box.getMaxX() >> 4, box.getMaxZ() >> 4)).forEach(in -> {
            start.place(world, world.getStructureAccessor(), world.getChunkManager().getChunkGenerator(), world.getRandom(),
                    new BlockBox(in.getStartX(), world.getBottomY(), in.getStartZ(), in.getEndX(), world.getTopYInclusive() + 1, in.getEndZ()), in);
            world.getStructureAccessor().addStructureReference(ChunkSectionPos.from(in, 0), structure, chunk.toLong(), world.getChunk(in.x, in.z));
        });
        world.getStructureAccessor().setStructureStart(ChunkSectionPos.from(chunk, 0), structure, start, world.getChunk(chunk.x, chunk.z));
        RoutineSites.forget(world);
    }

    // Removes what the builder generated to try out in this dimension. That covers its blocks, its residents and its registration
    // as a structure. The ground it was placed on isn't restored
    public static int clearTests(ServerWorld world, UUID builder) {
        List<Test> tests = TESTS.getOrDefault(builder, List.of());
        int cleared = 0;
        for(Test test : List.copyOf(tests)) {
            if(!test.dimension().equals(world.getRegistryKey()))
                continue;
            tests.remove(test);
            if(clear(world, test.structure(), test.chunk()))
                cleared++;
        }
        return cleared;
    }

    // The same for the structure the builder is standing in, however it got there. One generated before the game was last
    // started isn't remembered as the builder's
    public static boolean clearTestAt(ServerWorld world, BlockPos pos) {
        for(StructureStart start : world.getStructureAccessor().getStructureStarts(new ChunkPos(pos), structure -> true)) {
            if(start.hasChildren() && start.getBoundingBox().contains(pos))
                return clear(world, start.getStructure(), start.getPos());
        }
        return false;
    }

    private static boolean clear(ServerWorld world, Structure structure, ChunkPos chunk) {
        StructureStart start = world.getStructureAccessor().getStructureStart(ChunkSectionPos.from(chunk, 0), structure, world.getChunk(chunk.x, chunk.z));
        if(start == null || !start.hasChildren())
            return false;
        BlockBox box = start.getBoundingBox();
        Set<UUID> residents = new HashSet<>();
        ChunkPos.stream(new ChunkPos(box.getMinX() >> 4, box.getMinZ() >> 4), new ChunkPos(box.getMaxX() >> 4, box.getMaxZ() >> 4)).forEach(in -> {
            for(BlockEntity blockEntity : world.getChunk(in.x, in.z).getBlockEntities().values()) {
                if(blockEntity instanceof NestHost host && host.getNest() != null && box.contains(blockEntity.getPos()))
                    residents.addAll(host.getNest().getEntityUuids());
            }
        });
        for(UUID resident : residents) {
            Entity entity = world.getEntity(resident);
            if(entity != null)
                entity.discard();
        }
        // And whatever was left lying or standing in it: loot, animals from its markers, anyone its managers lost count of
        for(Entity entity : world.getOtherEntities(null, Box.from(box), entity -> !(entity instanceof PlayerEntity)))
            entity.discard();
        for(StructurePiece piece : start.getChildren())
            fill(world, piece.getBoundingBox(), Blocks.AIR.getDefaultState());
        ChunkPos.stream(new ChunkPos(box.getMinX() >> 4, box.getMinZ() >> 4), new ChunkPos(box.getMaxX() >> 4, box.getMaxZ() >> 4)).forEach(in -> {
            Chunk within = world.getChunk(in.x, in.z);
            within.getStructureReferences(structure).remove(chunk.toLong());
            within.markNeedsSaving();
        });
        world.getStructureAccessor().setStructureStart(ChunkSectionPos.from(chunk, 0), structure, StructureStart.DEFAULT, world.getChunk(chunk.x, chunk.z));
        RoutineSites.forget(world);
        StructureEditing.log("took away the generated structure in %s, %d pieces and %d who lived there".formatted(describe(box), start.getChildren().size(), residents.size()));
        return true;
    }

    // The structure as it is, but starting at a fixed height in the middle of a world taller than any, with room for all of it
    // above and below. Only works for a structure whose data can be read and written back, as a jigsaw structure's can
    private static @Nullable Structure freeStanding(ServerWorld world, Structure structure) {
        RegistryOps<JsonElement> ops = world.getRegistryManager().getOps(JsonOps.INSTANCE);
        JsonElement json = Structure.STRUCTURE_CODEC.encodeStart(ops, structure).result().orElse(null);
        if(json == null || !json.isJsonObject() || !json.getAsJsonObject().has(START_HEIGHT_KEY))
            return null;
        JsonObject data = json.getAsJsonObject();
        data.remove(PROJECT_TO_HEIGHTMAP_KEY);
        JsonObject height = new JsonObject();
        height.addProperty(ABSOLUTE_KEY, 0);
        data.add(START_HEIGHT_KEY, height);
        return Structure.STRUCTURE_CODEC.parse(ops, data).result().orElse(null);
    }

    private static Piece place(ServerWorld world, Piece piece, boolean spaced) {
        StructureTemplate template = world.getStructureTemplateManager().getTemplate(piece.template()).orElseThrow();
        // Its box is the template's box as it is now, which a save may have enlarged
        piece = new Piece(piece.template(), piece.origin(), piece.rotation(), boxOf(template, piece.origin(), piece.rotation()), piece.loaded());
        // What a template leaves open is kept open: it is filled with structure void, which is left out again when saving
        if(spaced)
            fill(world, piece.box(), Blocks.STRUCTURE_VOID.getDefaultState());
        // Placed as they were saved. If they were left to settle, a door at the edge of a piece would fall because the next
        // piece's floor isn't there, and fences would reach for neighbours that don't exist
        StructurePlacementData placement = new StructurePlacementData().setRotation(piece.rotation()).setIgnoreEntities(true).setUpdateNeighbors(true);
        template.place(world, piece.origin(), piece.origin(), placement, world.getRandom(), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        return new Piece(piece.template(), piece.origin(), piece.rotation(), piece.box(), spaced ? read(world, piece, null) : piece.loaded());
    }

    // Removes the pieces of the builder's structure from the world again
    public static boolean close(ServerWorld world, UUID builder) {
        StructureEditSession session = claim(builder);
        if(session == null || !session.dimension.equals(world.getRegistryKey()))
            return false;
        SESSIONS.remove(builder);
        StructureEditing.log("closed %s, %d pieces taken out of the world".formatted(session.structure, session.pieces.size()));
        for(Piece piece : session.pieces)
            fill(world, piece.box(), Blocks.AIR.getDefaultState());
        store(world.getServer());
        pause(world.getServer());
        return true;
    }

    // Writes the template of every piece that was changed. If two pieces of one template were changed differently, neither
    // is written
    public Saved save(ServerWorld world) {
        StructureTemplateManager manager = world.getStructureTemplateManager();
        Map<Identifier, NbtCompound> changed = new LinkedHashMap<>();
        List<Identifier> conflicts = new ArrayList<>();
        for(Piece piece : pieces) {
            StructureTemplate template = manager.getTemplate(piece.template()).orElse(null);
            if(template == null)
                continue;
            NbtCompound now = read(world, piece, piece.loaded());
            if(now.equals(piece.loaded()))
                continue;
            StructureEditing.log("  changed: %s in %s, size %s where it was %s, %s".formatted(piece.template(), describe(piece.box()),
                    now.getListOrEmpty(SIZE), piece.loaded().getListOrEmpty(SIZE), differences(piece.loaded(), now)));
            NbtCompound other = changed.put(piece.template(), now);
            if(other != null && !other.equals(now) && !conflicts.contains(piece.template()))
                conflicts.add(piece.template());
        }
        List<Identifier> written = new ArrayList<>();
        for(Map.Entry<Identifier, NbtCompound> entry : changed.entrySet()) {
            if(conflicts.contains(entry.getKey()))
                continue;
            StructureTemplate template = manager.getTemplateOrBlank(entry.getKey());
            // Entities are left as they were
            NbtCompound nbt = template.writeNbt(new NbtCompound());
            nbt.put(SIZE, entry.getValue().getListOrEmpty(SIZE));
            nbt.remove(PALETTES);
            nbt.put(PALETTE, entry.getValue().getListOrEmpty(PALETTE));
            nbt.put(BLOCKS, entry.getValue().getListOrEmpty(BLOCKS));
            template.readNbt(world.getRegistryManager().getOrThrow(RegistryKeys.BLOCK), nbt);
            if(manager.saveTemplate(entry.getKey())) {
                written.add(entry.getKey());
                StructureFeaturesRegistry.edited(entry.getKey());
            }
        }
        // What was saved is the baseline for later changes. The other pieces of a template that was written are updated to
        // match it, or the old one would be saved over it next time
        for(int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if(written.contains(piece.template()))
                pieces.set(i, place(world, piece, true));
        }
        store(world.getServer());
        StructureEditing.log("saved %s: wrote %s, left unwritten for differing copies %s, %d pieces unchanged".formatted(structure, written, conflicts,
                pieces.stream().filter(piece -> !changed.containsKey(piece.template())).count()));
        return new Saved(written, conflicts);
    }

    static List<Identifier> templatesOf(StructurePoolElement element) {
        List<Identifier> templates = new ArrayList<>();
        if(element instanceof SinglePoolElement single)
            single.location.left().ifPresent(templates::add);
        else if(element instanceof ListPoolElement list)
            list.getElements().forEach(inner -> templates.addAll(templatesOf(inner)));
        return templates;
    }

    private static BlockBox boxOf(StructureTemplate template, BlockPos origin, BlockRotation rotation) {
        return template.calculateBoundingBox(origin, rotation, BlockPos.ORIGIN, BlockMirror.NONE);
    }

    private static String describe(BlockBox box) {
        return "%d,%d,%d to %d,%d,%d".formatted(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ());
    }

    private static BlockBox boundsOf(List<Piece> pieces) {
        return BlockBox.encompass(pieces.stream().map(Piece::box).toList()).orElseThrow();
    }

    // Moves groups apart from the middle of the structure until no two are within the gap of each other, so they keep their
    // places beside one another. A piece that stands on or under another, like a cellar under its house, stays over or under
    // it and is lifted off it by the gap. Pieces that run through one another are put side by side instead
    private static List<Piece> spread(List<Piece> assembled, int gap) {
        List<List<Piece>> groups = attached(assembled);
        List<BlockBox> bounds = groups.stream().map(StructureEditSession::boundsOf).toList();
        BlockPos middle = boundsOf(assembled).getCenter();
        int[] movesX = new int[groups.size()];
        int[] movesZ = new int[groups.size()];
        List<BlockBox> moved = new ArrayList<>(bounds);
        boolean apart = tooClose(moved, gap) < 0;
        for(double factor = FIRST_SPREAD; factor <= MAXIMUM_SPREAD && !apart; factor += SPREAD_STEP) {
            for(int i = 0; i < bounds.size(); i++) {
                BlockPos center = bounds.get(i).getCenter();
                movesX[i] = (int) Math.round((center.getX() - middle.getX()) * (factor - 1));
                movesZ[i] = (int) Math.round((center.getZ() - middle.getZ()) * (factor - 1));
                moved.set(i, bounds.get(i).offset(movesX[i], 0, movesZ[i]));
            }
            apart = tooClose(moved, gap) < 0;
        }
        if(!apart) {
            List<BlockBox> settled = new ArrayList<>();
            for(int i = 0; i < moved.size(); i++) {
                settled.add(moved.get(i));
                int other;
                while((other = tooClose(settled, gap)) >= 0 && other < i) {
                    int shift = settled.get(other).getMaxX() + gap + 1 - settled.get(i).getMinX();
                    movesX[i] += shift;
                    settled.set(i, settled.get(i).offset(shift, 0, 0));
                }
            }
        }
        Map<Piece, Integer> groupOf = new IdentityHashMap<>();
        for(int i = 0; i < groups.size(); i++) {
            for(Piece piece : groups.get(i))
                groupOf.put(piece, i);
        }
        // Lift each piece above whatever it stands on by the gap, lowest first
        Map<Piece, Integer> lifts = new IdentityHashMap<>();
        for(List<Piece> group : groups) {
            List<Piece> rising = new ArrayList<>(group);
            rising.sort(Comparator.comparingInt(piece -> piece.box().getMinY()));
            for(int i = 0; i < rising.size(); i++) {
                BlockBox box = rising.get(i).box();
                int lift = 0;
                for(int j = 0; j < i; j++) {
                    BlockBox under = rising.get(j).box();
                    boolean over = box.getMinX() <= under.getMaxX() && box.getMaxX() >= under.getMinX() && box.getMinZ() <= under.getMaxZ() && box.getMaxZ() >= under.getMinZ();
                    if(over && under.getMaxY() < box.getMinY())
                        lift = Math.max(lift, lifts.get(rising.get(j)) + gap);
                }
                lifts.put(rising.get(i), lift);
            }
        }
        List<Piece> spread = new ArrayList<>();
        for(Piece piece : assembled) {
            int i = groupOf.get(piece);
            int lift = lifts.get(piece);
            spread.add(new Piece(piece.template(), piece.origin().add(movesX[i], lift, movesZ[i]), piece.rotation(), piece.box().offset(movesX[i], lift, movesZ[i]), piece.loaded()));
        }
        return spread;
    }

    // Pieces stacked over one another without running through each other form one group and move together
    private static List<List<Piece>> attached(List<Piece> pieces) {
        int[] group = new int[pieces.size()];
        for(int i = 0; i < group.length; i++)
            group[i] = i;
        for(int i = 0; i < pieces.size(); i++) {
            BlockBox box = pieces.get(i).box();
            for(int j = i + 1; j < pieces.size(); j++) {
                BlockBox other = pieces.get(j).box();
                boolean over = box.getMinX() <= other.getMaxX() && box.getMaxX() >= other.getMinX() && box.getMinZ() <= other.getMaxZ() && box.getMaxZ() >= other.getMinZ();
                if(over && !box.intersects(other)) {
                    int from = group[j];
                    int to = group[i];
                    for(int k = 0; k < group.length; k++) {
                        if(group[k] == from)
                            group[k] = to;
                    }
                }
            }
        }
        Map<Integer, List<Piece>> groups = new LinkedHashMap<>();
        for(int i = 0; i < pieces.size(); i++)
            groups.computeIfAbsent(group[i], key -> new ArrayList<>()).add(pieces.get(i));
        return new ArrayList<>(groups.values());
    }

    // The earlier of the first two boxes that are within the gap of each other across the ground, or -1
    private static int tooClose(List<BlockBox> boxes, int gap) {
        for(int i = 0; i < boxes.size(); i++) {
            BlockBox box = boxes.get(i);
            for(int j = i + 1; j < boxes.size(); j++) {
                BlockBox other = boxes.get(j);
                if(box.getMinX() - gap <= other.getMaxX() && box.getMaxX() + gap >= other.getMinX()
                        && box.getMinZ() - gap <= other.getMaxZ() && box.getMaxZ() + gap >= other.getMinZ())
                    return i;
            }
        }
        return -1;
    }

    private static void fill(ServerWorld world, BlockBox box, BlockState state) {
        for(BlockPos pos : BlockPos.iterate(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ())) {
            if(!world.isInBuildLimit(pos))
                continue;
            // A chest that is removed spills what it holds, and one that was never opened rolls its loot to spill it. Without its
            // data it has nothing to drop
            if(world.getBlockState(pos).hasBlockEntity())
                world.removeBlockEntity(pos);
            world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.FORCE_STATE | Block.SKIP_DROPS);
        }
    }

    // For a session stored back when loot seeds were still read along with the rest
    private static NbtCompound withoutSeeds(NbtCompound template) {
        NbtList blocks = template.getListOrEmpty(BLOCKS);
        for(int i = 0; i < blocks.size(); i++)
            blocks.getCompoundOrEmpty(i).getCompound(NBT).ifPresent(data -> data.remove(LOOT_TABLE_SEED));
        return template;
    }

    private record Held(BlockState state, @Nullable NbtCompound data) {}

    private static Map<BlockPos, Held> heldIn(ServerWorld world, NbtCompound template) {
        Map<BlockPos, Held> held = new HashMap<>();
        NbtList palette = template.getListOrEmpty(PALETTE);
        NbtList blocks = template.getListOrEmpty(BLOCKS);
        for(int i = 0; i < blocks.size(); i++) {
            NbtCompound block = blocks.getCompoundOrEmpty(i);
            NbtList at = block.getListOrEmpty(POS);
            held.put(new BlockPos(at.getInt(0, 0), at.getInt(1, 0), at.getInt(2, 0)), new Held(
                    NbtHelper.toBlockState(world.getRegistryManager().getOrThrow(RegistryKeys.BLOCK), palette.getCompoundOrEmpty(block.getInt(STATE, 0))),
                    block.getCompound(NBT).orElse(null)));
        }
        return held;
    }

    // What the world does to a build on its own while it is being edited: a spawner or vault wakes up near the builder,
    // grass dies under a roof and spreads over bare earth. None of it is the builder's work
    private static boolean isWorldsDoing(BlockState held, BlockState now) {
        Block before = held.getBlock();
        Block after = now.getBlock();
        if(before == after)
            return before instanceof TrialSpawnerBlock || before instanceof VaultBlock || before instanceof SpawnerBlock;
        return isSoil(before) && isSoil(after);
    }

    private static boolean isSoil(Block block) {
        return block == Blocks.DIRT || block instanceof SpreadableBlock;
    }

    // What was changed, for the log: how many blocks, and the first few
    private static String differences(NbtCompound before, NbtCompound now) {
        Map<BlockPos, String> old = described(before);
        Map<BlockPos, String> current = described(now);
        Set<BlockPos> all = new HashSet<>(old.keySet());
        all.addAll(current.keySet());
        List<BlockPos> differing = new ArrayList<>(all.stream().filter(pos -> !String.valueOf(old.get(pos)).equals(String.valueOf(current.get(pos)))).toList());
        differing.sort(Comparator.naturalOrder());
        StringBuilder out = new StringBuilder("%d blocks differ".formatted(differing.size()));
        for(int i = 0; i < Math.min(DIFFERENCES_LOGGED, differing.size()); i++) {
            BlockPos pos = differing.get(i);
            out.append(i == 0 ? ": " : "; ").append(pos.toShortString()).append(" ").append(shortened(old.get(pos))).append(" -> ").append(shortened(current.get(pos)));
        }
        return out.toString();
    }

    private static Map<BlockPos, String> described(NbtCompound template) {
        Map<BlockPos, String> described = new HashMap<>();
        NbtList palette = template.getListOrEmpty(PALETTE);
        NbtList blocks = template.getListOrEmpty(BLOCKS);
        for(int i = 0; i < blocks.size(); i++) {
            NbtCompound block = blocks.getCompoundOrEmpty(i);
            NbtList at = block.getListOrEmpty(POS);
            described.put(new BlockPos(at.getInt(0, 0), at.getInt(1, 0), at.getInt(2, 0)),
                    palette.getCompoundOrEmpty(block.getInt(STATE, 0)) + block.getCompound(NBT).map(NbtCompound::toString).orElse(""));
        }
        return described;
    }

    private static String shortened(@Nullable String text) {
        return text == null ? "nothing" : text.length() > DIFFERENCE_LENGTH ? text.substring(0, DIFFERENCE_LENGTH) + "..." : text;
    }

    // The blocks of a piece as its template holds them: rotated back to how the template is saved, in the order the game
    // saves them, full blocks first and those with data last. Given what the piece held when it was loaded, anything the
    // world has since done to it on its own is read as it was
    private static NbtCompound read(ServerWorld world, Piece piece, @Nullable NbtCompound loaded) {
        Map<BlockPos, Held> held = loaded == null ? Map.of() : heldIn(world, loaded);
        Vec3i size = sizeOf(piece.box(), piece.rotation());
        BlockRotation back = switch (piece.rotation()) {
            case CLOCKWISE_90 -> BlockRotation.COUNTERCLOCKWISE_90;
            case COUNTERCLOCKWISE_90 -> BlockRotation.CLOCKWISE_90;
            default -> piece.rotation();
        };
        record Found(BlockPos pos, BlockState state, @Nullable NbtCompound data, int order) {}
        List<Found> found = new ArrayList<>();
        BlockBox box = piece.box();
        for(BlockPos pos : BlockPos.iterate(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ())) {
            BlockState state = world.getBlockState(pos);
            if(state.isOf(Blocks.STRUCTURE_VOID))
                continue;
            BlockPos local = StructureTemplate.transformAround(pos.subtract(piece.origin()), BlockMirror.NONE, back, BlockPos.ORIGIN);
            if(local.getX() < 0 || local.getY() < 0 || local.getZ() < 0 || local.getX() >= size.getX() || local.getY() >= size.getY() || local.getZ() >= size.getZ())
                continue;
            BlockEntity blockEntity = world.getBlockEntity(pos);
            NbtCompound data = blockEntity == null ? null : blockEntity.createNbtWithIdentifyingData(world.getRegistryManager());
            if(data != null) {
                data.remove("x");
                data.remove("y");
                data.remove("z");
                // A new seed is drawn for every chest each time a template is placed
                data.remove(LOOT_TABLE_SEED);
            }
            BlockState turned = state.rotate(back);
            Held before = held.get(local);
            if(before != null && isWorldsDoing(before.state(), turned)) {
                turned = before.state();
                data = before.data();
            }
            int order = data != null ? 2 : !turned.getBlock().hasDynamicBounds() && turned.isFullCube(world, pos) ? 0 : 1;
            found.add(new Found(local.toImmutable(), turned, data, order));
        }
        found.sort(Comparator.<Found>comparingInt(Found::order).thenComparingInt(block -> block.pos().getY())
                .thenComparingInt(block -> block.pos().getX()).thenComparingInt(block -> block.pos().getZ()));

        List<BlockState> palette = new ArrayList<>();
        NbtList blocks = new NbtList();
        for(Found block : found) {
            int index = palette.indexOf(block.state());
            if(index < 0) {
                index = palette.size();
                palette.add(block.state());
            }
            NbtList pos = new NbtList();
            pos.add(NbtInt.of(block.pos().getX()));
            pos.add(NbtInt.of(block.pos().getY()));
            pos.add(NbtInt.of(block.pos().getZ()));
            NbtCompound entry = new NbtCompound();
            entry.put(POS, pos);
            entry.putInt(STATE, index);
            if(block.data() != null)
                entry.put(NBT, block.data());
            blocks.add(entry);
        }
        NbtList states = new NbtList();
        for(BlockState state : palette)
            states.add(NbtHelper.fromBlockState(state));
        NbtList sizes = new NbtList();
        sizes.add(NbtInt.of(size.getX()));
        sizes.add(NbtInt.of(size.getY()));
        sizes.add(NbtInt.of(size.getZ()));
        NbtCompound read = new NbtCompound();
        read.put(SIZE, sizes);
        read.put(PALETTE, states);
        read.put(BLOCKS, blocks);
        return read;
    }
}
