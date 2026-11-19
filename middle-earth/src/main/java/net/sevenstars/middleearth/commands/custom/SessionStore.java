package net.sevenstars.middleearth.commands.custom;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

// Sessions by builder, kept in the world's save so they come back with it
class SessionStore {
    static final int DEFAULT_GAP = 4;
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

    private static final Map<UUID, Integer> GAPS = new HashMap<>();
    private static final Set<UUID> RESTORED = ConcurrentHashMap.newKeySet();
    private static final Map<UUID, StructureEditSession> SESSIONS = new ConcurrentHashMap<>();
    private static final AtomicReference<Pending> pending = new AtomicReference<>();
    private static final ExecutorService WRITER = Executors.newSingleThreadExecutor(task -> {
        Thread thread = new Thread(task, "middle-earth-structure-editing-writer");
        thread.setDaemon(true);
        return thread;
    });

    // Falls back to the unclaimed restored session. A workspace game gives its player a new id every run
    static @Nullable StructureEditSession of(UUID builder) {
        StructureEditSession own = SESSIONS.get(builder);
        if(own != null || !FabricLoader.getInstance().isDevelopmentEnvironment() || RESTORED.size() != 1)
            return own;
        return SESSIONS.get(RESTORED.iterator().next());
    }

    // Takes over the restored session, so only for calls that change something
    static @Nullable StructureEditSession claim(UUID builder) {
        if(SESSIONS.containsKey(builder)) {
            RESTORED.remove(builder);
            return SESSIONS.get(builder);
        }
        if(!FabricLoader.getInstance().isDevelopmentEnvironment() || RESTORED.size() != 1)
            return null;
        StructureEditSession restored = SESSIONS.remove(RESTORED.iterator().next());
        RESTORED.clear();
        if(restored != null) {
            SESSIONS.put(builder, restored);
            GAPS.put(builder, restored.getGap());
        }
        return restored;
    }

    static void put(UUID builder, StructureEditSession session) {
        SESSIONS.put(builder, session);
    }

    static void remove(UUID builder) {
        SESSIONS.remove(builder);
    }

    static boolean anySpaced() {
        return SESSIONS.values().stream().anyMatch(StructureEditSession::isSpaced);
    }

    static int gapOf(UUID builder) {
        return GAPS.getOrDefault(builder, DEFAULT_GAP);
    }

    static void setGap(UUID builder, int gap) {
        GAPS.put(builder, Math.clamp(gap, 0, StructureEditSession.MAXIMUM_GAP));
    }

    static void clear() {
        SESSIONS.clear();
        RESTORED.clear();
        GAPS.clear();
    }

    static void write(MinecraftServer server) {
        NbtList sessions = new NbtList();
        for(Map.Entry<UUID, StructureEditSession> entry : SESSIONS.entrySet()) {
            StructureEditSession session = entry.getValue();
            NbtCompound stored = new NbtCompound();
            stored.putString(BUILDER, entry.getKey().toString());
            stored.putString(DIMENSION, session.getDimension().getValue().toString());
            stored.putString(STRUCTURE, session.getStructure().toString());
            stored.putLong(SEED, session.getSeed());
            stored.putBoolean(SPACED, session.isSpaced());
            stored.putInt(GAP, session.getGap());
            NbtList pieces = new NbtList();
            for(StructureEditSession.Piece piece : session.getPieces())
                pieces.add(storedPiece(piece));
            stored.put(PIECES, pieces);
            sessions.add(stored);
        }
        NbtCompound root = new NbtCompound();
        root.put(SESSIONS_KEY, sessions);
        // Pieces' loaded snapshots are replaced whole, never edited, so sharing them with the writer is safe
        pending.set(new Pending(server.getSavePath(WorldSavePath.ROOT).resolve(FILE), root));
        WRITER.execute(SessionStore::writePending);
    }

    private record Pending(Path file, NbtCompound root) {}

    // Only the latest snapshot is written, the ones queued behind it find nothing left
    private static void writePending() {
        Pending latest = pending.getAndSet(null);
        if(latest == null)
            return;
        try {
            Files.createDirectories(latest.file().getParent());
            NbtIo.writeCompressed(latest.root(), latest.file());
        } catch (IOException e) {
            MiddleEarth.LOGGER.logError("StructureEditSession :: could not keep the loaded structures with the world", e);
        }
    }

    static void flush() {
        try {
            WRITER.submit(() -> {}).get(30, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            MiddleEarth.LOGGER.logError("StructureEditSession :: the loaded structures were not all written when the server stopped", e);
        }
    }

    private static NbtCompound storedPiece(StructureEditSession.Piece piece) {
        NbtCompound storedPiece = new NbtCompound();
        storedPiece.putString(TEMPLATE, piece.template().toString());
        storedPiece.putString(ROTATION, piece.rotation().name());
        storedPiece.putIntArray(ORIGIN, new int[]{piece.origin().getX(), piece.origin().getY(), piece.origin().getZ()});
        BlockBox box = piece.box();
        storedPiece.putIntArray(BOX, new int[]{box.getMinX(), box.getMinY(), box.getMinZ(), box.getMaxX(), box.getMaxY(), box.getMaxZ()});
        storedPiece.put(LOADED, piece.loaded());
        return storedPiece;
    }

    static void restore(MinecraftServer server) {
        flush();
        SESSIONS.clear();
        RESTORED.clear();
        Path file = server.getSavePath(WorldSavePath.ROOT).resolve(FILE);
        if(!Files.isRegularFile(file))
            return;
        try {
            NbtCompound root = NbtIo.readCompressed(file, NbtSizeTracker.ofUnlimitedBytes());
            for(NbtElement element : root.getListOrEmpty(SESSIONS_KEY)) {
                try {
                    if(element instanceof NbtCompound stored)
                        restoreSession(stored);
                } catch (IllegalArgumentException e) {
                    MiddleEarth.LOGGER.logError("StructureEditSession :: could not read a structure that was loaded in this world", e);
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            MiddleEarth.LOGGER.logError("StructureEditSession :: could not read the structures that were loaded in this world", e);
        }
        WorldFreeze.pause(server);
    }

    private static void restoreSession(NbtCompound stored) {
        Identifier dimension = Identifier.tryParse(stored.getString(DIMENSION, ""));
        Identifier structure = Identifier.tryParse(stored.getString(STRUCTURE, ""));
        if(dimension == null || structure == null)
            return;
        List<StructureEditSession.Piece> pieces = new ArrayList<>();
        for(NbtElement pieceElement : stored.getListOrEmpty(PIECES)) {
            if(!(pieceElement instanceof NbtCompound storedPiece))
                continue;
            Identifier template = Identifier.tryParse(storedPiece.getString(TEMPLATE, ""));
            int[] origin = storedPiece.getIntArray(ORIGIN).orElse(new int[0]);
            int[] box = storedPiece.getIntArray(BOX).orElse(new int[0]);
            if(template == null || origin.length != 3 || box.length != 6)
                continue;
            pieces.add(new StructureEditSession.Piece(template, new BlockPos(origin[0], origin[1], origin[2]), BlockRotation.valueOf(storedPiece.getString(ROTATION, BlockRotation.NONE.name())),
                    new BlockBox(box[0], box[1], box[2], box[3], box[4], box[5]), TemplateSnapshot.withoutSeeds(storedPiece.getCompoundOrEmpty(LOADED))));
        }
        UUID builder = UUID.fromString(stored.getString(BUILDER, ""));
        SESSIONS.put(builder, new StructureEditSession(RegistryKey.of(RegistryKeys.WORLD, dimension), structure, stored.getLong(SEED, 0),
                stored.getBoolean(SPACED, true), stored.getInt(GAP, DEFAULT_GAP), pieces));
        RESTORED.add(builder);
        StructureEditing.log("%s came back with the world: %d pieces, %s".formatted(structure, pieces.size(), stored.getBoolean(SPACED, true) ? "spaced" : "assembled"));
    }
}
