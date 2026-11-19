package net.sevenstars.middleearth.commands.custom;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureStart;
import net.minecraft.structure.pool.ListPoolElement;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.gen.structure.Structure;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

// Has the game put a structure's pieces together, the way world generation does
class StructureAssembly {
    static final String START_HEIGHT_KEY = "start_height";
    static final String PROJECT_TO_HEIGHTMAP_KEY = "project_start_to_heightmap";
    static final String ABSOLUTE_KEY = "absolute";
    static final String START_POOL_KEY = "start_pool";
    static final String START_JIGSAW_NAME_KEY = "start_jigsaw_name";
    static final String SIZE_KEY = "size";
    static final int MAXIMUM_DEPTH = 7;
    // Tallest possible world, or the game drops pieces that reach out of it
    static final HeightLimitView ASSEMBLY_HEIGHT = HeightLimitView.create(-2032, 4064);

    record Assembled(StructureStart start, boolean freeStanding) {}

    static StructureStart assemble(ServerWorld world, RegistryEntry<Structure> entry, Structure structure, long seed, HeightLimitView height, ChunkPos chunk) {
        return structure.createStructureStart(entry, world.getRegistryKey(), world.getRegistryManager(),
                world.getChunkManager().getChunkGenerator(), world.getChunkManager().getChunkGenerator().getBiomeSource(), world.getChunkManager().getNoiseConfig(),
                world.getStructureTemplateManager(), seed, chunk, 0, height, biome -> true);
    }

    // Underground parts get cut short where the ground is thin, so use whichever version has more pieces
    static Assembled fullest(ServerWorld world, RegistryEntry<Structure> entry, long seed, ChunkPos chunk) {
        StructureStart start = assemble(world, entry, entry.value(), seed, world, chunk);
        Structure free = freeStanding(world, entry.value());
        StructureStart freeStart = free == null ? null : assemble(world, entry, free, seed, ASSEMBLY_HEIGHT, chunk);
        if(freeStart != null && freeStart.getChildren().size() > start.getChildren().size())
            return new Assembled(freeStart, true);
        return new Assembled(start, false);
    }

    // Starts at a fixed height mid-world with room above and below. Only for structures whose data can be written back
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

    static List<Identifier> templatesOf(StructurePoolElement element) {
        List<Identifier> templates = new ArrayList<>();
        if(element instanceof SinglePoolElement single)
            single.location.left().ifPresent(templates::add);
        else if(element instanceof ListPoolElement list)
            list.getElements().forEach(inner -> templates.addAll(templatesOf(inner)));
        return templates;
    }
}
