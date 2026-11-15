package net.sevenstars.middleearth.commands.custom;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// A whole structure laid out for its builder, as the game would put it together from its jigsaw pieces. Assembled, it stands
// as it would in the world, to be looked at. Spaced, its pieces stand apart, each whole in a box of its own, and what is
// changed in one is saved back to the template of that piece
public class StructureEditSession {
    // In blocks, left between the pieces when they are spaced
    private static final int PIECE_GAP = 4;
    private static final double FIRST_SPREAD = 1.5;
    private static final double SPREAD_STEP = 0.25;
    private static final double MAXIMUM_SPREAD = 6;
    // The same pieces come of the same seed wherever the builder stands: the structure is put together here, and moved
    private static final ChunkPos ASSEMBLY_CHUNK = new ChunkPos(0, 0);
    private static final String PALETTE = "palette";
    private static final String PALETTES = "palettes";
    private static final String BLOCKS = "blocks";
    private static final String POS = "pos";
    private static final String STATE = "state";
    private static final String NBT = "nbt";

    private static final Map<UUID, StructureEditSession> SESSIONS = new HashMap<>();

    // The corner the template was placed from, and the blocks it had when it was loaded, to tell what was changed since
    public record Piece(Identifier template, BlockPos origin, BlockRotation rotation, BlockBox box, NbtCompound loaded) {}

    public record Saved(List<Identifier> written, List<Identifier> conflicts) {}

    private final RegistryKey<World> dimension;
    private final Identifier structure;
    private final long seed;
    private final boolean spaced;
    private final List<Piece> pieces;

    private StructureEditSession(RegistryKey<World> dimension, Identifier structure, long seed, boolean spaced, List<Piece> pieces) {
        this.dimension = dimension;
        this.structure = structure;
        this.seed = seed;
        this.spaced = spaced;
        this.pieces = pieces;
    }

    public static @Nullable StructureEditSession of(UUID builder) {
        return SESSIONS.get(builder);
    }

    // The pieces are of the world that was open, and mean nothing in the next one
    public static void register() {
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> SESSIONS.clear());
    }

    public boolean hasChanges(ServerWorld world) {
        if(!spaced)
            return false;
        for(Piece piece : pieces) {
            StructureTemplate template = world.getStructureTemplateManager().getTemplate(piece.template()).orElse(null);
            if(template != null && !read(world, piece, template.getSize()).equals(piece.loaded()))
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

    // Null when the structure cannot be put together from that seed, or has no pieces of its own templates
    public static @Nullable StructureEditSession load(ServerWorld world, UUID builder, RegistryEntry<Structure> structure, Identifier id, long seed, boolean spaced, BlockPos corner) {
        StructureStart start = structure.value().createStructureStart(structure, world.getRegistryKey(), world.getRegistryManager(),
                world.getChunkManager().getChunkGenerator(), world.getChunkManager().getChunkGenerator().getBiomeSource(), world.getChunkManager().getNoiseConfig(),
                world.getStructureTemplateManager(), seed, ASSEMBLY_CHUNK, 0, world, biome -> true);
        if(!start.hasChildren())
            return null;

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
        if(assembled.isEmpty())
            return null;

        List<Piece> placed = spaced ? spread(assembled) : assembled;
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
        StructureEditSession session = new StructureEditSession(world.getRegistryKey(), id, seed, spaced, loaded);
        SESSIONS.put(builder, session);
        return session;
    }

    private static Piece place(ServerWorld world, Piece piece, boolean spaced) {
        // What a template leaves open is kept open: a block of void stands for it, and is left out again when saving
        if(spaced)
            fill(world, piece.box(), Blocks.STRUCTURE_VOID.getDefaultState());
        StructureTemplate template = world.getStructureTemplateManager().getTemplate(piece.template()).orElseThrow();
        // Placed as they were saved: left to settle, a door at the edge of a piece falls for want of the floor of the next
        // one, and fences reach for neighbours that are not there
        StructurePlacementData placement = new StructurePlacementData().setRotation(piece.rotation()).setIgnoreEntities(true).setUpdateNeighbors(true);
        template.place(world, piece.origin(), piece.origin(), placement, world.getRandom(), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        return new Piece(piece.template(), piece.origin(), piece.rotation(), piece.box(), spaced ? read(world, piece, template.getSize()) : piece.loaded());
    }

    // Takes the pieces of the builder's structure out of the world again
    public static boolean close(ServerWorld world, UUID builder) {
        StructureEditSession session = SESSIONS.remove(builder);
        if(session == null || !session.dimension.equals(world.getRegistryKey()))
            return false;
        for(Piece piece : session.pieces)
            fill(world, piece.box(), Blocks.AIR.getDefaultState());
        return true;
    }

    // Writes the template of every piece that was changed. Where two pieces of one template were changed in different ways,
    // neither is written
    public Saved save(ServerWorld world) {
        StructureTemplateManager manager = world.getStructureTemplateManager();
        Map<Identifier, NbtCompound> changed = new LinkedHashMap<>();
        List<Identifier> conflicts = new ArrayList<>();
        for(Piece piece : pieces) {
            StructureTemplate template = manager.getTemplate(piece.template()).orElse(null);
            if(template == null)
                continue;
            NbtCompound now = read(world, piece, template.getSize());
            if(now.equals(piece.loaded()))
                continue;
            NbtCompound other = changed.put(piece.template(), now);
            if(other != null && !other.equals(now) && !conflicts.contains(piece.template()))
                conflicts.add(piece.template());
        }
        List<Identifier> written = new ArrayList<>();
        for(Map.Entry<Identifier, NbtCompound> entry : changed.entrySet()) {
            if(conflicts.contains(entry.getKey()))
                continue;
            StructureTemplate template = manager.getTemplateOrBlank(entry.getKey());
            // Its size and its entities stay as they were
            NbtCompound nbt = template.writeNbt(new NbtCompound());
            nbt.remove(PALETTES);
            nbt.put(PALETTE, entry.getValue().getListOrEmpty(PALETTE));
            nbt.put(BLOCKS, entry.getValue().getListOrEmpty(BLOCKS));
            template.readNbt(world.getRegistryManager().getOrThrow(RegistryKeys.BLOCK), nbt);
            if(manager.saveTemplate(entry.getKey()))
                written.add(entry.getKey());
        }
        // What was saved is what later changes are told from. The other pieces of a template that was written are made
        // like it, or the old one would be saved over it next time
        for(int i = 0; i < pieces.size(); i++) {
            Piece piece = pieces.get(i);
            if(written.contains(piece.template()))
                pieces.set(i, place(world, piece, true));
        }
        return new Saved(written, conflicts);
    }

    private static List<Identifier> templatesOf(StructurePoolElement element) {
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

    private static BlockBox boundsOf(List<Piece> pieces) {
        return BlockBox.encompass(pieces.stream().map(Piece::box).toList()).orElseThrow();
    }

    // Moved apart from the middle of the structure until no two boxes are within the gap of each other, so that the pieces
    // keep their places beside one another. Pieces that stand inside or over one another are put side by side instead
    private static List<Piece> spread(List<Piece> assembled) {
        BlockPos middle = boundsOf(assembled).getCenter();
        List<Piece> spread = assembled;
        for(double factor = FIRST_SPREAD; factor <= MAXIMUM_SPREAD; factor += SPREAD_STEP) {
            spread = new ArrayList<>();
            for(Piece piece : assembled) {
                BlockPos center = piece.box().getCenter();
                int moveX = (int) Math.round((center.getX() - middle.getX()) * (factor - 1));
                int moveZ = (int) Math.round((center.getZ() - middle.getZ()) * (factor - 1));
                spread.add(new Piece(piece.template(), piece.origin().add(moveX, 0, moveZ), piece.rotation(), piece.box().offset(moveX, 0, moveZ), piece.loaded()));
            }
            if(firstTooClose(spread, spread.size()) < 0)
                return spread;
        }
        List<Piece> apart = new ArrayList<>();
        for(Piece piece : spread) {
            Piece moved = piece;
            apart.add(moved);
            int other;
            while((other = firstTooClose(apart, apart.size())) >= 0 && other < apart.size() - 1) {
                int moveX = apart.get(other).box().getMaxX() + PIECE_GAP + 1 - moved.box().getMinX();
                moved = new Piece(moved.template(), moved.origin().add(moveX, 0, 0), moved.rotation(), moved.box().offset(moveX, 0, 0), moved.loaded());
                apart.set(apart.size() - 1, moved);
            }
        }
        return apart;
    }

    // The earlier of the first two pieces that stand within the gap of each other across the ground, or -1
    private static int firstTooClose(List<Piece> pieces, int count) {
        for(int i = 0; i < count; i++) {
            BlockBox box = pieces.get(i).box();
            for(int j = i + 1; j < count; j++) {
                BlockBox other = pieces.get(j).box();
                if(box.getMinX() - PIECE_GAP <= other.getMaxX() && box.getMaxX() + PIECE_GAP >= other.getMinX()
                        && box.getMinZ() - PIECE_GAP <= other.getMaxZ() && box.getMaxZ() + PIECE_GAP >= other.getMinZ())
                    return i;
            }
        }
        return -1;
    }

    private static void fill(ServerWorld world, BlockBox box, BlockState state) {
        for(BlockPos pos : BlockPos.iterate(box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ())) {
            if(world.isInBuildLimit(pos))
                world.setBlockState(pos, state, Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        }
    }

    // The blocks of a piece as its template holds them: turned back to how the template lies, in the order the game saves
    // them in, whole blocks first and those with data last
    private static NbtCompound read(ServerWorld world, Piece piece, Vec3i size) {
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
            }
            int order = data != null ? 2 : !state.getBlock().hasDynamicBounds() && state.isFullCube(world, pos) ? 0 : 1;
            found.add(new Found(local, state.rotate(back), data, order));
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
        NbtCompound read = new NbtCompound();
        read.put(PALETTE, states);
        read.put(BLOCKS, blocks);
        return read;
    }
}
