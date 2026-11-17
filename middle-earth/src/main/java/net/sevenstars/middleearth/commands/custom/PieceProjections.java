package net.sevenstars.middleearth.commands.custom;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.registry.Registry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

// Each pool that offers a piece decides how it is placed on the ground: as it was built, or with every column of it
// projected onto the ground below. The same template can use one in one pool and the other in the next
public class PieceProjections {
    private static final String ELEMENTS = "elements";
    private static final String ELEMENT = "element";
    private static final String LOCATION = "location";
    private static final String PROJECTION = "projection";
    private static final String POOL_FOLDER = "worldgen/template_pool";
    private static final String JSON = ".json";

    public static Map<Identifier, Set<StructurePool.Projection>> of(Registry<StructurePool> pools) {
        Map<Identifier, Set<StructurePool.Projection>> projections = new HashMap<>();
        for(StructurePool pool : pools) {
            for(var weighted : pool.getElementWeights()) {
                StructurePoolElement element = weighted.getFirst();
                for(Identifier template : StructureEditSession.templatesOf(element))
                    projections.computeIfAbsent(template, key -> EnumSet.noneOf(StructurePool.Projection.class)).add(element.getProjection());
            }
        }
        return projections;
    }

    // Changes the pools the game has loaded, for what is generated from now on. Returns the names of the pools that were changed
    public static List<Identifier> set(Registry<StructurePool> pools, Identifier template, StructurePool.Projection projection, @Nullable Identifier only) {
        List<Identifier> changed = new ArrayList<>();
        for(Identifier id : pools.getIds()) {
            StructurePool pool = pools.get(id);
            if(pool == null || only != null && !only.equals(id))
                continue;
            boolean any = false;
            for(var weighted : pool.getElementWeights()) {
                StructurePoolElement element = weighted.getFirst();
                if(StructureEditSession.templatesOf(element).contains(template) && element.getProjection() != projection) {
                    element.setProjection(projection);
                    any = true;
                }
            }
            if(any)
                changed.add(id);
        }
        return changed;
    }

    // Changes the pool files in the mod's own sources, for every world opened after. Returns the files that were written
    public static List<Path> write(Path resources, Identifier template, StructurePool.Projection projection, @Nullable Identifier only) throws IOException {
        Path data = resources.resolve("data");
        List<Path> written = new ArrayList<>();
        if(!Files.isDirectory(data))
            return written;
        List<Path> files;
        try(Stream<Path> found = Files.walk(data)) {
            files = found.filter(path -> path.toString().endsWith(JSON) && data.relativize(path).toString().replace('\\', '/').contains("/" + POOL_FOLDER + "/")).sorted().toList();
        }
        for(Path file : files) {
            String relative = data.relativize(file).toString().replace('\\', '/');
            String namespace = relative.substring(0, relative.indexOf('/'));
            String path = relative.substring(relative.indexOf(POOL_FOLDER) + POOL_FOLDER.length() + 1, relative.length() - JSON.length());
            if(only != null && !(only.getNamespace().equals(namespace) && only.getPath().equals(path)))
                continue;
            JsonObject pool;
            try {
                JsonElement parsed = PiecePools.GSON.fromJson(Files.readString(file, StandardCharsets.UTF_8), JsonElement.class);
                if(parsed == null || !parsed.isJsonObject())
                    continue;
                pool = parsed.getAsJsonObject();
            } catch (JsonParseException e) {
                continue;
            }
            JsonArray elements = pool.has(ELEMENTS) && pool.get(ELEMENTS).isJsonArray() ? pool.getAsJsonArray(ELEMENTS) : new JsonArray();
            boolean any = false;
            for(JsonElement entry : elements) {
                if(entry.isJsonObject() && entry.getAsJsonObject().has(ELEMENT) && entry.getAsJsonObject().get(ELEMENT).isJsonObject())
                    any |= set(entry.getAsJsonObject().getAsJsonObject(ELEMENT), template.toString(), projection.getId());
            }
            if(any) {
                Files.writeString(file, PiecePools.GSON.toJson(pool), StandardCharsets.UTF_8);
                written.add(file);
            }
        }
        return written;
    }

    // A list element has a projection of its own, and each element inside it has one too
    private static boolean set(JsonObject element, String template, String projection) {
        boolean any = false;
        if(element.has(ELEMENTS) && element.get(ELEMENTS).isJsonArray()) {
            for(JsonElement inner : element.getAsJsonArray(ELEMENTS)) {
                if(inner.isJsonObject())
                    any |= set(inner.getAsJsonObject(), template, projection);
            }
            if(any)
                element.addProperty(PROJECTION, projection);
            return any;
        }
        if(!element.has(LOCATION) || !element.get(LOCATION).isJsonPrimitive() || !template.equals(element.get(LOCATION).getAsString()))
            return false;
        if(element.has(PROJECTION) && projection.equals(element.get(PROJECTION).getAsString()))
            return false;
        element.addProperty(PROJECTION, projection);
        return true;
    }
}
