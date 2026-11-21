package net.sevenstars.middleearth.commands.custom;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Pair;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.mixin.StructurePoolAccessor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// A plug asking for a pool names a target and wants every piece with a plug of that name.
// So pieces join the pools they answer to, and missing pools are created from them
public class PiecePools {
    static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    static final Identifier EMPTY = Identifier.ofVanilla("empty");
    private static final String JIGSAW = "minecraft:jigsaw";
    private static final int DEFAULT_WEIGHT = 1;

    record Plugs(Set<Identifier> names, Map<Identifier, Set<Identifier>> asked) {}

    // The game only sees new pools once the world is reopened
    public record Result(List<Identifier> live, List<Path> written, List<Identifier> created) {}

    private static Plugs plugsOf(ServerWorld world, Identifier template) {
        Set<Identifier> names = new LinkedHashSet<>();
        Map<Identifier, Set<Identifier>> asked = new LinkedHashMap<>();
        StructureTemplate found = world.getStructureTemplateManager().getTemplate(template).orElse(null);
        if(found == null)
            return new Plugs(names, asked);
        NbtCompound root = found.writeNbt(new NbtCompound());
        NbtList palette = root.getListOrEmpty("palette");
        NbtList blocks = root.getListOrEmpty("blocks");
        for(int i = 0; i < blocks.size(); i++) {
            NbtCompound block = blocks.getCompoundOrEmpty(i);
            if(!JIGSAW.equals(palette.getCompoundOrEmpty(block.getInt("state", 0)).getString("Name", "")))
                continue;
            NbtCompound data = block.getCompoundOrEmpty("nbt");
            Identifier name = Identifier.tryParse(data.getString("name", ""));
            Identifier pool = Identifier.tryParse(data.getString("pool", ""));
            Identifier target = Identifier.tryParse(data.getString("target", ""));
            if(name != null && !name.equals(EMPTY))
                names.add(name);
            if(pool != null && target != null && !pool.equals(EMPTY) && !target.equals(EMPTY))
                asked.computeIfAbsent(pool, key -> new LinkedHashSet<>()).add(target);
        }
        return new Plugs(names, asked);
    }

    public static Set<Identifier> asked(ServerWorld world, Identifier template) {
        return plugsOf(world, template).asked().keySet();
    }

    static Map<Identifier, Plugs> plugsIn(ServerWorld world, String namespace) {
        Map<Identifier, Plugs> plugs = new LinkedHashMap<>();
        for(Identifier id : world.getStructureTemplateManager().streamTemplates().filter(id -> id.getNamespace().equals(namespace)).sorted().toList())
            plugs.put(id, plugsOf(world, id));
        return plugs;
    }

    public static Result publish(ServerWorld world, Identifier template, Path resources) throws IOException {
        return publish(world, template, resources, plugsIn(world, template.getNamespace()));
    }

    static Result publish(ServerWorld world, Identifier template, Path resources, Map<Identifier, Plugs> plugs) throws IOException {
        Registry<StructurePool> pools = world.getRegistryManager().getOrThrow(RegistryKeys.TEMPLATE_POOL);
        Plugs own = plugs.computeIfAbsent(template, id -> plugsOf(world, id));

        Map<Identifier, Set<Identifier>> additions = new LinkedHashMap<>();
        for(Plugs other : plugs.values()) {
            for(Map.Entry<Identifier, Set<Identifier>> asked : other.asked().entrySet()) {
                if(asked.getValue().stream().anyMatch(own.names()::contains))
                    additions.computeIfAbsent(asked.getKey(), key -> new LinkedHashSet<>()).add(template);
            }
        }
        List<Identifier> created = new ArrayList<>();
        for(Map.Entry<Identifier, Set<Identifier>> asked : own.asked().entrySet()) {
            if(pools.containsId(asked.getKey()) || Files.isRegularFile(fileOf(resources, asked.getKey())))
                continue;
            Set<Identifier> answering = additions.computeIfAbsent(asked.getKey(), key -> new LinkedHashSet<>());
            for(Map.Entry<Identifier, Plugs> other : plugs.entrySet()) {
                if(asked.getValue().stream().anyMatch(other.getValue().names()::contains))
                    answering.add(other.getKey());
            }
            created.add(asked.getKey());
        }

        List<Identifier> live = new ArrayList<>();
        List<Path> written = new ArrayList<>();
        for(Map.Entry<Identifier, Set<Identifier>> addition : additions.entrySet()) {
            StructurePool pool = pools.get(addition.getKey());
            if(pool != null && addLive(pool, addition.getValue()))
                live.add(addition.getKey());
            if(Files.isDirectory(resources) && write(fileOf(resources, addition.getKey()), addition.getKey(), addition.getValue()))
                written.add(fileOf(resources, addition.getKey()));
        }
        return new Result(live, written, created);
    }

    public static boolean writeStart(Path resources, Identifier pool, Identifier template) throws IOException {
        return write(fileOf(resources, pool), pool, Set.of(template));
    }

    static Path fileOf(Path resources, Identifier pool) throws IOException {
        return within(resources, resources.resolve("data").resolve(pool.getNamespace()).resolve("worldgen").resolve("template_pool").resolve(pool.getPath() + ".json"));
    }

    // A path may hold "..", which must not leave the folder
    static Path within(Path resources, Path file) throws IOException {
        Path normalized = file.toAbsolutePath().normalize();
        if(!normalized.startsWith(resources.toAbsolutePath().normalize()))
            throw new IOException(file + " is outside " + resources);
        return normalized;
    }

    static JsonObject elementOf(Identifier template) {
        JsonObject element = new JsonObject();
        element.addProperty("element_type", "minecraft:single_pool_element");
        element.addProperty("location", template.toString());
        element.addProperty("projection", StructurePool.Projection.RIGID.getId());
        element.addProperty("processors", EMPTY.toString());
        JsonObject entry = new JsonObject();
        entry.addProperty("weight", DEFAULT_WEIGHT);
        entry.add("element", element);
        return entry;
    }

    private static boolean addLive(StructurePool pool, Set<Identifier> templates) {
        List<Pair<StructurePoolElement, Integer>> weights = new ArrayList<>(pool.getElementWeights());
        boolean any = false;
        for(Identifier template : templates) {
            if(weights.stream().anyMatch(weighted -> StructureAssembly.templatesOf(weighted.getFirst()).contains(template)))
                continue;
            StructurePoolElement element = StructurePoolElement.ofSingle(template.toString()).apply(StructurePool.Projection.RIGID);
            weights.add(Pair.of(element, DEFAULT_WEIGHT));
            ((StructurePoolAccessor) pool).middleEarth$getElements().add(element);
            any = true;
        }
        if(any)
            ((StructurePoolAccessor) pool).middleEarth$setElementWeights(weights);
        return any;
    }

    // Pieces already listed are kept
    private static boolean write(Path file, Identifier pool, Set<Identifier> templates) throws IOException {
        JsonObject root = null;
        if(Files.isRegularFile(file)) {
            try {
                JsonElement parsed = GSON.fromJson(Files.readString(file, StandardCharsets.UTF_8), JsonElement.class);
                root = parsed != null && parsed.isJsonObject() ? parsed.getAsJsonObject() : null;
            } catch (JsonParseException e) {
                return false;
            }
            if(root == null)
                return false;
        } else {
            root = new JsonObject();
            root.addProperty("name", pool.toString());
            root.addProperty("fallback", EMPTY.toString());
        }
        if(!root.has("elements") || !root.get("elements").isJsonArray())
            root.add("elements", new JsonArray());
        JsonArray elements = root.getAsJsonArray("elements");
        Set<String> listed = new LinkedHashSet<>();
        for(JsonElement entry : elements) {
            if(entry.isJsonObject() && entry.getAsJsonObject().has("element") && entry.getAsJsonObject().get("element").isJsonObject()
                    && entry.getAsJsonObject().getAsJsonObject("element").has("location"))
                listed.add(entry.getAsJsonObject().getAsJsonObject("element").get("location").getAsString());
        }
        boolean any = false;
        for(Identifier template : templates) {
            if(listed.contains(template.toString()))
                continue;
            elements.add(elementOf(template));
            any = true;
        }
        if(!any)
            return false;
        Files.createDirectories(file.getParent());
        Files.writeString(file, GSON.toJson(root), StandardCharsets.UTF_8);
        return true;
    }
}
