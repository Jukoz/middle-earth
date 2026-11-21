package net.sevenstars.middleearth.commands.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureLiquidSettings;
import net.minecraft.structure.StructurePiece;
import net.minecraft.structure.StructurePiecesList;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.structure.Structure;
import net.sevenstars.middleearth.block.special.structureManager.StructureEditing;
import net.sevenstars.middleearth.block.special.structureManager.features.NestHost;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSites;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// Structures set down for a builder to see their people born, and taken away again
class TestStructures {
    record Generated(BlockBox box, int pieces) {}

    private record Test(RegistryKey<World> dimension, Structure structure, ChunkPos chunk) {}

    private static final Map<UUID, List<Test>> TESTS = new HashMap<>();

    static void clear() {
        TESTS.clear();
    }

    private static void remember(UUID builder, ServerWorld world, Structure structure, ChunkPos chunk) {
        TESTS.computeIfAbsent(builder, key -> new ArrayList<>()).add(new Test(world.getRegistryKey(), structure, chunk));
    }

    // Registered as a real structure: managers wake, people are born, markers go. A command only sets blocks
    // One the ground would cut short is placed whole at the builder's height
    static @Nullable Generated generate(ServerWorld world, UUID builder, RegistryEntry<Structure> structure, long seed, BlockPos at) {
        ChunkPos chunk = new ChunkPos(at);
        StructureAssembly.Assembled assembled = StructureAssembly.fullest(world, structure, seed, chunk);
        StructureStart start = assembled.start();
        if(assembled.freeStanding()) {
            List<StructurePiece> pieces = start.getChildren();
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
            // A start remembers its bounds from before the pieces moved
            start = new StructureStart(structure.value(), chunk, 0, new StructurePiecesList(pieces));
        }
        if(!start.hasChildren()) {
            StructureEditing.log("%s from the seed %d: the game put no pieces together".formatted(structure.getIdAsString(), seed));
            return null;
        }
        BlockBox box = start.getBoundingBox();
        placeAsStructure(world, structure.value(), start, chunk);
        remember(builder, world, structure.value(), chunk);
        StructureEditing.log("generated %s from the seed %d: %d pieces in %s".formatted(structure.getIdAsString(), seed, start.getChildren().size(), PieceLayout.describe(box)));
        return new Generated(box, start.getChildren().size());
    }

    // A single piece placed as a structure with no pool, stored under an existing structure's name (just a key)
    static @Nullable Generated generatePiece(ServerWorld world, UUID builder, Identifier template, RegistryEntry<Structure> carrier, BlockPos at) {
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
        placeAsStructure(world, carrier.value(), start, chunk);
        remember(builder, world, carrier.value(), chunk);
        StructureEditing.log("generated the piece %s by itself in %s, kept as %s".formatted(template, PieceLayout.describe(box), carrier.getIdAsString()));
        return new Generated(box, 1);
    }

    private static @Nullable Generated generateFrom(ServerWorld world, UUID builder, Identifier template, RegistryEntry<Structure> carrier, BlockPos at) {
        RegistryOps<JsonElement> ops = world.getRegistryManager().getOps(JsonOps.INSTANCE);
        JsonElement json = Structure.STRUCTURE_CODEC.encodeStart(ops, carrier.value()).result().orElse(null);
        if(json == null || !json.isJsonObject() || !json.getAsJsonObject().has(StructureAssembly.START_POOL_KEY))
            return null;
        JsonArray elements = new JsonArray();
        elements.add(PiecePools.elementOf(template));
        JsonObject pool = new JsonObject();
        pool.addProperty("fallback", PiecePools.EMPTY.toString());
        pool.add("elements", elements);
        JsonObject height = new JsonObject();
        height.addProperty(StructureAssembly.ABSOLUTE_KEY, at.getY());
        JsonObject data = json.getAsJsonObject();
        data.add(StructureAssembly.START_POOL_KEY, pool);
        data.add(StructureAssembly.START_HEIGHT_KEY, height);
        data.addProperty(StructureAssembly.SIZE_KEY, StructureAssembly.MAXIMUM_DEPTH);
        data.remove(StructureAssembly.PROJECT_TO_HEIGHTMAP_KEY);
        data.remove(StructureAssembly.START_JIGSAW_NAME_KEY);
        Structure whole = Structure.STRUCTURE_CODEC.parse(ops, data).result().orElse(null);
        if(whole == null)
            return null;
        ChunkPos chunk = new ChunkPos(at);
        List<StructurePiece> pieces = StructureAssembly.assemble(world, carrier, whole, world.getRandom().nextLong(), world, chunk).getChildren();
        if(pieces.isEmpty())
            return null;
        // Work out the offset first, translating a piece moves its box too
        BlockBox first = pieces.getFirst().getBoundingBox();
        Vec3i move = new Vec3i(at.getX() - first.getMinX(), at.getY() - first.getMinY(), at.getZ() - first.getMinZ());
        for(StructurePiece piece : pieces)
            piece.translate(move.getX(), move.getY(), move.getZ());
        StructureStart start = new StructureStart(carrier.value(), chunk, 0, new StructurePiecesList(pieces));
        placeAsStructure(world, carrier.value(), start, chunk);
        remember(builder, world, carrier.value(), chunk);
        StructureEditing.log("generated the piece %s with what its plugs ask for, %d pieces in %s, kept as %s".formatted(template, pieces.size(), PieceLayout.describe(start.getBoundingBox()), carrier.getIdAsString()));
        return new Generated(BlockBox.encompass(pieces.stream().map(StructurePiece::getBoundingBox).toList()).orElse(start.getBoundingBox()), pieces.size());
    }

    private static void placeAsStructure(ServerWorld world, Structure structure, StructureStart start, ChunkPos chunk) {
        BlockBox box = start.getBoundingBox();
        PieceLayout.chunksOf(box).forEach(in -> {
            start.place(world, world.getStructureAccessor(), world.getChunkManager().getChunkGenerator(), world.getRandom(),
                    new BlockBox(in.getStartX(), world.getBottomY(), in.getStartZ(), in.getEndX(), world.getTopYInclusive() + 1, in.getEndZ()), in);
            world.getStructureAccessor().addStructureReference(ChunkSectionPos.from(in, 0), structure, chunk.toLong(), world.getChunk(in.x, in.z));
        });
        world.getStructureAccessor().setStructureStart(ChunkSectionPos.from(chunk, 0), structure, start, world.getChunk(chunk.x, chunk.z));
        RoutineSites.forget(world);
    }

    // The ground it was placed on isn't restored
    static int clearTests(ServerWorld world, UUID builder) {
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

    static boolean clearTestAt(ServerWorld world, BlockPos pos) {
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
        PieceLayout.chunksOf(box).forEach(in -> {
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
        for(Entity entity : world.getOtherEntities(null, Box.from(box), entity -> !(entity instanceof PlayerEntity)))
            entity.discard();
        for(StructurePiece piece : start.getChildren())
            TemplateSnapshot.fill(world, piece.getBoundingBox(), Blocks.AIR.getDefaultState());
        PieceLayout.chunksOf(box).forEach(in -> {
            Chunk within = world.getChunk(in.x, in.z);
            within.getStructureReferences(structure).remove(chunk.toLong());
            within.markNeedsSaving();
        });
        world.getStructureAccessor().setStructureStart(ChunkSectionPos.from(chunk, 0), structure, StructureStart.DEFAULT, world.getChunk(chunk.x, chunk.z));
        RoutineSites.forget(world);
        StructureEditing.log("took away the generated structure in %s, %d pieces and %d who lived there".formatted(PieceLayout.describe(box), start.getChildren().size(), residents.size()));
        return true;
    }
}
