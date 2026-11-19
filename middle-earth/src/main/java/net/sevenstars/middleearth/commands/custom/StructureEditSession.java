package net.sevenstars.middleearth.commands.custom;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.registries.content.structurefeatures.StructureFeaturesRegistry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

// Assembled: view only, as in the world. Spaced: pieces apart, edits saved to each template
public class StructureEditSession {
    public static final int MAXIMUM_GAP = 64;
    // Always assembled in this chunk, then moved, so a seed gives the same pieces anywhere
    private static final ChunkPos ASSEMBLY_CHUNK = new ChunkPos(0, 0);

    public record Piece(Identifier template, BlockPos origin, BlockRotation rotation, BlockBox box, NbtCompound loaded) {}

    public record Saved(List<Identifier> written, List<Identifier> conflicts) {}

    private final RegistryKey<World> dimension;
    private final Identifier structure;
    private final long seed;
    private final boolean spaced;
    private final int gap;
    // Swapped whole on every change, the render thread reads it
    private volatile List<Piece> pieces;

    StructureEditSession(RegistryKey<World> dimension, Identifier structure, long seed, boolean spaced, int gap, List<Piece> pieces) {
        this.gap = gap;
        this.dimension = dimension;
        this.structure = structure;
        this.seed = seed;
        this.spaced = spaced;
        this.pieces = List.copyOf(pieces);
    }

    public static @Nullable StructureEditSession of(UUID builder) {
        return SessionStore.of(builder);
    }

    // The builder's session when it is in this dimension
    public static @Nullable StructureEditSession ofIn(UUID builder, RegistryKey<World> dimension) {
        StructureEditSession session = SessionStore.of(builder);
        return session == null || !session.dimension.equals(dimension) ? null : session;
    }

    public static @Nullable Piece pieceAt(UUID builder, RegistryKey<World> dimension, BlockPos pos) {
        StructureEditSession session = ofIn(builder, dimension);
        return session == null ? null : session.pieceContaining(pos);
    }

    // Takes over the restored session, so only for calls that change something
    public static @Nullable StructureEditSession claim(UUID builder) {
        return SessionStore.claim(builder);
    }

    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(SessionStore::restore);
        ServerPlayConnectionEvents.DISCONNECT.register((handler, server) -> endSingle(server, handler.getPlayer().getUuid()));
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> {
            SessionStore.clear();
            WorldFreeze.clear();
            TestStructures.clear();
            BedTour.forget();
            StructureEditing.clearNoClip();
        });
    }

    public static void beginSingle(MinecraftServer server, UUID builder) {
        WorldFreeze.beginSingle(server, builder);
    }

    public static boolean endSingle(MinecraftServer server, UUID builder) {
        return WorldFreeze.endSingle(server, builder);
    }

    public static void setGap(UUID builder, int gap) {
        SessionStore.setGap(builder, gap);
    }

    public int getGap() {
        return gap;
    }

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

    // Null on success, else the piece in the way
    public @Nullable Piece expand(ServerWorld world, Piece piece, Direction side, int blocks) {
        BlockBox box = piece.box();
        BlockBox grown = new BlockBox(
                box.getMinX() + Math.min(0, side.getOffsetX() * blocks), box.getMinY() + Math.min(0, side.getOffsetY() * blocks), box.getMinZ() + Math.min(0, side.getOffsetZ() * blocks),
                box.getMaxX() + Math.max(0, side.getOffsetX() * blocks), box.getMaxY() + Math.max(0, side.getOffsetY() * blocks), box.getMaxZ() + Math.max(0, side.getOffsetZ() * blocks));
        for(Piece other : pieces) {
            if(other != piece && other.box().intersects(grown)) {
                StructureEditing.log("%s not made bigger to the %s by %d: %s in %s is in the way of %s".formatted(piece.template(), side.asString(), blocks,
                        other.template(), PieceLayout.describe(other.box()), PieceLayout.describe(grown)));
                return other;
            }
        }
        for(BlockPos pos : BlockPos.iterate(grown.getMinX(), grown.getMinY(), grown.getMinZ(), grown.getMaxX(), grown.getMaxY(), grown.getMaxZ())) {
            if(!box.contains(pos) && world.isInBuildLimit(pos))
                world.setBlockState(pos, Blocks.STRUCTURE_VOID.getDefaultState(), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        }
        replace(piece, new Piece(piece.template(), originOf(grown, piece.rotation()), piece.rotation(), grown, piece.loaded()));
        SessionStore.write(world.getServer());
        StructureEditing.log("%s made bigger to the %s by %d: %s where it was %s".formatted(piece.template(), side.asString(), blocks, PieceLayout.describe(grown), PieceLayout.describe(box)));
        return null;
    }

    // Anything outside the box stays in the world, to take in again or clear by hand
    public @Nullable Piece resize(ServerWorld world, Piece piece, BlockBox resized) {
        BlockBox box = piece.box();
        for(Piece other : pieces) {
            if(other != piece && other.box().intersects(resized)) {
                StructureEditing.log("%s not given the bounds %s: %s in %s is in the way".formatted(piece.template(), PieceLayout.describe(resized), other.template(), PieceLayout.describe(other.box())));
                return other;
            }
        }
        for(BlockPos pos : BlockPos.iterate(resized.getMinX(), resized.getMinY(), resized.getMinZ(), resized.getMaxX(), resized.getMaxY(), resized.getMaxZ())) {
            if(!box.contains(pos) && world.isInBuildLimit(pos) && world.getBlockState(pos).isAir())
                world.setBlockState(pos, Blocks.STRUCTURE_VOID.getDefaultState(), Block.NOTIFY_LISTENERS | Block.FORCE_STATE);
        }
        replace(piece, new Piece(piece.template(), originOf(resized, piece.rotation()), piece.rotation(), resized, piece.loaded()));
        SessionStore.write(world.getServer());
        StructureEditing.log("%s given the bounds %s where it had %s".formatted(piece.template(), PieceLayout.describe(resized), PieceLayout.describe(box)));
        return null;
    }

    private void replace(Piece old, Piece replacement) {
        List<Piece> changed = new ArrayList<>(pieces);
        changed.set(changed.indexOf(old), replacement);
        pieces = List.copyOf(changed);
    }

    private static BlockPos originOf(BlockBox box, BlockRotation rotation) {
        return switch (rotation) {
            case NONE -> new BlockPos(box.getMinX(), box.getMinY(), box.getMinZ());
            case CLOCKWISE_90 -> new BlockPos(box.getMaxX(), box.getMinY(), box.getMinZ());
            case CLOCKWISE_180 -> new BlockPos(box.getMaxX(), box.getMinY(), box.getMaxZ());
            case COUNTERCLOCKWISE_90 -> new BlockPos(box.getMinX(), box.getMinY(), box.getMaxZ());
        };
    }

    public boolean hasChanges(ServerWorld world) {
        if(!spaced)
            return false;
        for(Piece piece : pieces) {
            StructureTemplate template = world.getStructureTemplateManager().getTemplate(piece.template()).orElse(null);
            if(template != null && !TemplateSnapshot.read(world, piece, piece.loaded()).equals(piece.loaded()))
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

    public static @Nullable StructureEditSession load(ServerWorld world, UUID builder, RegistryEntry<Structure> structure, Identifier id, long seed, boolean spaced, Function<Vec3i, BlockPos> cornerFor) {
        StructureStart start = StructureAssembly.fullest(world, structure, seed, ASSEMBLY_CHUNK).start();
        if(!start.hasChildren()) {
            StructureEditing.log("%s from the seed %d: the game put no pieces together".formatted(id, seed));
            return null;
        }

        List<Piece> assembled = templatePieces(world, start);
        if(assembled.isEmpty()) {
            StructureEditing.log("%s from the seed %d: %d pieces, none of them a template of its own".formatted(id, seed, start.getChildren().size()));
            return null;
        }

        int gap = SessionStore.gapOf(builder);
        List<Piece> placed = spaced ? PieceLayout.spread(assembled, gap) : assembled;
        BlockBox whole = PieceLayout.boundsOf(placed);
        BlockPos corner = cornerFor.apply(new Vec3i(whole.getBlockCountX(), whole.getBlockCountY(), whole.getBlockCountZ()));
        Vec3i move = new Vec3i(corner.getX() - whole.getMinX(), corner.getY() - whole.getMinY(), corner.getZ() - whole.getMinZ());
        List<Piece> pieces = new ArrayList<>();
        for(Piece piece : placed)
            pieces.add(new Piece(piece.template(), piece.origin().add(move), piece.rotation(), piece.box().offset(move.getX(), move.getY(), move.getZ()), piece.loaded()));

        close(world, builder);
        TemplateSnapshot.fill(world, PieceLayout.boundsOf(pieces), Blocks.AIR.getDefaultState());
        List<Piece> loaded = new ArrayList<>();
        for(Piece piece : pieces)
            loaded.add(TemplateSnapshot.place(world, piece, spaced));
        // A piece changes the blocks along its seam after the earlier one was read, so read them all again
        if(spaced)
            loaded.replaceAll(piece -> new Piece(piece.template(), piece.origin(), piece.rotation(), piece.box(), TemplateSnapshot.read(world, piece, null)));
        StructureEditSession session = new StructureEditSession(world.getRegistryKey(), id, seed, spaced, gap, loaded);
        SessionStore.put(builder, session);
        SessionStore.write(world.getServer());
        WorldFreeze.pause(world.getServer());
        StructureEditing.log("loaded %s from the seed %d, %s, %d pieces in %s".formatted(id, seed, spaced ? "spaced %d apart".formatted(gap) : "assembled", loaded.size(), PieceLayout.describe(PieceLayout.boundsOf(loaded))));
        for(Piece piece : loaded)
            StructureEditing.log("  piece %s turned %s in %s".formatted(piece.template(), piece.rotation().asString(), PieceLayout.describe(piece.box())));
        return session;
    }

    private static List<Piece> templatePieces(ServerWorld world, StructureStart start) {
        StructureTemplateManager manager = world.getStructureTemplateManager();
        List<Piece> found = new ArrayList<>();
        for(StructurePiece child : start.getChildren()) {
            if(!(child instanceof PoolStructurePiece piece))
                continue;
            for(Identifier template : StructureAssembly.templatesOf(piece.getPoolElement())) {
                StructureTemplate known = manager.getTemplate(template).orElse(null);
                if(known != null)
                    found.add(new Piece(template, piece.getPos(), piece.getRotation(), TemplateSnapshot.boxOf(known, piece.getPos(), piece.getRotation()), new NbtCompound()));
            }
        }
        return found;
    }

    public static boolean close(ServerWorld world, UUID builder) {
        StructureEditSession session = claim(builder);
        if(session == null)
            return false;
        ServerWorld held = session.dimension.equals(world.getRegistryKey()) ? world : world.getServer().getWorld(session.dimension);
        if(held == null)
            return false;
        SessionStore.remove(builder);
        StructureEditing.log("closed %s, %d pieces taken out of the world".formatted(session.structure, session.pieces.size()));
        for(Piece piece : session.pieces)
            TemplateSnapshot.fill(held, piece.box(), Blocks.AIR.getDefaultState());
        SessionStore.write(world.getServer());
        WorldFreeze.pause(world.getServer());
        return true;
    }

    // Neither is written if two pieces of one template were changed differently
    public Saved save(ServerWorld world) {
        StructureTemplateManager manager = world.getStructureTemplateManager();
        Map<Identifier, NbtCompound> changed = new LinkedHashMap<>();
        List<Identifier> conflicts = new ArrayList<>();
        for(Piece piece : pieces) {
            StructureTemplate template = manager.getTemplate(piece.template()).orElse(null);
            if(template == null)
                continue;
            NbtCompound now = TemplateSnapshot.read(world, piece, piece.loaded());
            if(now.equals(piece.loaded()))
                continue;
            StructureEditing.log("  changed: %s in %s, size %s where it was %s, %s".formatted(piece.template(), PieceLayout.describe(piece.box()),
                    now.getListOrEmpty(TemplateSnapshot.SIZE), piece.loaded().getListOrEmpty(TemplateSnapshot.SIZE), TemplateSnapshot.differences(piece.loaded(), now)));
            NbtCompound other = changed.put(piece.template(), now);
            if(other != null && !other.equals(now) && !conflicts.contains(piece.template()))
                conflicts.add(piece.template());
        }
        List<Identifier> written = new ArrayList<>();
        for(Map.Entry<Identifier, NbtCompound> entry : changed.entrySet()) {
            if(!conflicts.contains(entry.getKey()) && writeTemplate(world, entry.getKey(), entry.getValue()))
                written.add(entry.getKey());
        }
        // What was saved is the new baseline. Other pieces of that template are updated, or the old one would be saved over it
        List<Piece> updated = new ArrayList<>(pieces);
        for(int i = 0; i < updated.size(); i++) {
            Piece piece = updated.get(i);
            if(written.contains(piece.template()))
                updated.set(i, TemplateSnapshot.place(world, piece, true));
        }
        pieces = List.copyOf(updated);
        SessionStore.write(world.getServer());
        StructureEditing.log("saved %s: wrote %s, left unwritten for differing copies %s, %d pieces unchanged".formatted(structure, written, conflicts,
                pieces.stream().filter(piece -> !changed.containsKey(piece.template())).count()));
        return new Saved(written, conflicts);
    }

    private static boolean writeTemplate(ServerWorld world, Identifier id, NbtCompound blocks) {
        StructureTemplateManager manager = world.getStructureTemplateManager();
        StructureTemplate template = manager.getTemplateOrBlank(id);
        NbtCompound nbt = template.writeNbt(new NbtCompound());
        nbt.put(TemplateSnapshot.SIZE, blocks.getListOrEmpty(TemplateSnapshot.SIZE));
        nbt.remove(TemplateSnapshot.PALETTES);
        nbt.put(TemplateSnapshot.PALETTE, blocks.getListOrEmpty(TemplateSnapshot.PALETTE));
        nbt.put(TemplateSnapshot.BLOCKS, blocks.getListOrEmpty(TemplateSnapshot.BLOCKS));
        template.readNbt(world.getRegistryManager().getOrThrow(RegistryKeys.BLOCK), nbt);
        if(!manager.saveTemplate(id))
            return false;
        StructureFeaturesRegistry.edited(id);
        return true;
    }
}
