package net.sevenstars.of_beasts_and_wild_things.compat.farm;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public final class FarmAnimalVariants {
    public static final ResourceLocation TEMPERATE = ResourceLocation.withDefaultNamespace("temperate");
    private static final ResourceLocation COLD = ResourceLocation.withDefaultNamespace("cold");
    private static final ResourceLocation WARM = ResourceLocation.withDefaultNamespace("warm");
    private static final ResourceLocation COLD_BIOMES =
            ResourceLocation.withDefaultNamespace("spawns_cold_variant_farm_animals");
    private static final ResourceLocation WARM_BIOMES =
            ResourceLocation.withDefaultNamespace("spawns_warm_variant_farm_animals");

    private static volatile Snapshot snapshot = Snapshot.baseline();

    private FarmAnimalVariants() {
    }

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(FarmAnimalVariants::registerPayloads);
        NeoForge.EVENT_BUS.addListener(FarmAnimalVariants::addReloadListener);
        NeoForge.EVENT_BUS.addListener(FarmAnimalVariants::syncDatapack);
    }

    public static boolean contains(FarmAnimalKind kind, ResourceLocation id) {
        return snapshot.definitions().getOrDefault(kind, Map.of()).containsKey(id);
    }

    public static ResourceLocation select(Entity entity, ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        FarmAnimalKind kind = FarmAnimalKind.of(entity);
        if (kind == null) {
            return TEMPERATE;
        }
        Holder<Biome> biome = level.getBiome(pos);
        FarmAnimalVariantSelector.SpawnContext context =
                new FarmAnimalVariantSelector.SpawnContext(level, pos, biome);
        List<FarmAnimalVariantSelection.Candidate<ResourceLocation>> candidates = new ArrayList<>();
        for (FarmAnimalVariantDefinition definition : snapshot.definitions().get(kind).values()) {
            for (FarmAnimalVariantSelector selector : definition.selectors()) {
                candidates.add(new FarmAnimalVariantSelection.Candidate<>(
                        definition.id(),
                        selector.priority(),
                        () -> selector.test(context)
                ));
            }
        }
        List<ResourceLocation> eligible = FarmAnimalVariantSelection.eligible(candidates);
        if (eligible.isEmpty()) {
            return TEMPERATE;
        }
        return FarmAnimalVariantSelection.choose(eligible, random::nextInt);
    }

    public static List<FarmAnimalVariantDefinition.Synced> syncedDefinitions() {
        return snapshot.definitions().values().stream()
                .flatMap(values -> values.values().stream())
                .map(FarmAnimalVariantDefinition.Synced::from)
                .toList();
    }

    private static void registerPayloads(RegisterPayloadHandlersEvent event) {
        event.registrar("2").playToClient(
                FarmAnimalVariantSyncPayload.TYPE,
                FarmAnimalVariantSyncPayload.STREAM_CODEC,
                (payload, context) -> FarmAnimalVariantClientState.replace(payload.definitions())
        );
    }

    private static void addReloadListener(AddReloadListenerEvent event) {
        event.addListener(new Loader());
    }

    private static void syncDatapack(OnDatapackSyncEvent event) {
        FarmAnimalVariantSyncPayload payload = new FarmAnimalVariantSyncPayload(syncedDefinitions());
        event.getRelevantPlayers().forEach(player -> PacketDistributor.sendToPlayer(player, payload));
    }

    private static FarmAnimalVariantDefinition parse(
            FarmAnimalKind kind,
            ResourceLocation id,
            Resource resource
    ) {
        try (var reader = resource.openAsReader()) {
            JsonObject json = GsonHelper.parse(reader);
            ResourceLocation assetId = parseLocation(GsonHelper.getAsString(json, "asset_id"), "asset_id");
            FarmAnimalVariantModel model =
                    FarmAnimalVariantModel.parse(kind, GsonHelper.getAsString(json, "model", "normal"));
            JsonArray selectorJson = GsonHelper.getAsJsonArray(json, "spawn_conditions");
            List<FarmAnimalVariantSelector> selectors = new ArrayList<>(selectorJson.size());
            for (JsonElement element : selectorJson) {
                JsonObject selector = GsonHelper.convertToJsonObject(element, "spawn condition");
                int priority = GsonHelper.getAsInt(selector, "priority");
                Optional<FarmAnimalVariantSelector.Condition> condition = selector.has("condition")
                        ? Optional.of(parseCondition(GsonHelper.getAsJsonObject(selector, "condition")))
                        : Optional.empty();
                selectors.add(new FarmAnimalVariantSelector(condition, priority));
            }
            return new FarmAnimalVariantDefinition(kind, id, assetId, model, selectors, false);
        } catch (IOException | RuntimeException exception) {
            throw new JsonParseException("Failed to parse farm animal variant " + id + " from "
                    + resource.sourcePackId(), exception);
        }
    }

    private static FarmAnimalVariantSelector.Condition parseCondition(JsonObject condition) {
        ResourceLocation type = parseLocation(GsonHelper.getAsString(condition, "type"), "condition type");
        if (type.equals(ResourceLocation.withDefaultNamespace("biome"))) {
            return new FarmAnimalVariantSelector.BiomeCondition(
                    parseRegistryMatchers(condition.get("biomes"), Registries.BIOME, "biomes")
            );
        }
        if (type.equals(ResourceLocation.withDefaultNamespace("structure"))) {
            return new FarmAnimalVariantSelector.StructureCondition(
                    parseRegistryMatchers(condition.get("structures"), Registries.STRUCTURE, "structures")
            );
        }
        if (type.equals(ResourceLocation.withDefaultNamespace("moon_brightness"))) {
            JsonElement rangeElement = condition.get("range");
            if (rangeElement == null) {
                throw new JsonParseException("Missing moon brightness range");
            }
            if (rangeElement.isJsonPrimitive() && rangeElement.getAsJsonPrimitive().isNumber()) {
                double exact = rangeElement.getAsDouble();
                return new FarmAnimalVariantSelector.MoonBrightnessCondition(exact, exact);
            }
            JsonObject range = GsonHelper.convertToJsonObject(rangeElement, "range");
            return new FarmAnimalVariantSelector.MoonBrightnessCondition(
                    GsonHelper.getAsDouble(range, "min", Double.NEGATIVE_INFINITY),
                    GsonHelper.getAsDouble(range, "max", Double.POSITIVE_INFINITY)
            );
        }
        throw new JsonParseException("Unknown farm animal spawn condition type: " + type);
    }

    private static <T> List<FarmAnimalVariantSelector.RegistryEntryMatcher<T>> parseRegistryMatchers(
            JsonElement element,
            ResourceKey<? extends Registry<T>> registry,
            String fieldName
    ) {
        if (element == null) {
            throw new JsonParseException("Missing " + fieldName);
        }
        List<FarmAnimalVariantSelector.RegistryEntryMatcher<T>> matchers = new ArrayList<>();
        if (element.isJsonArray()) {
            for (JsonElement value : element.getAsJsonArray()) {
                matchers.add(parseRegistryMatcher(GsonHelper.convertToString(value, fieldName), registry));
            }
        } else {
            matchers.add(parseRegistryMatcher(GsonHelper.convertToString(element, fieldName), registry));
        }
        return matchers;
    }

    private static <T> FarmAnimalVariantSelector.RegistryEntryMatcher<T> parseRegistryMatcher(
            String value,
            ResourceKey<? extends Registry<T>> registry
    ) {
        if (value.startsWith("#")) {
            ResourceLocation id = parseLocation(value.substring(1), "tag");
            return new FarmAnimalVariantSelector.RegistryEntryMatcher.Tag<>(TagKey.create(registry, id));
        }
        ResourceLocation id = parseLocation(value, "registry entry");
        return new FarmAnimalVariantSelector.RegistryEntryMatcher.Id<>(ResourceKey.create(registry, id));
    }

    private static ResourceLocation parseLocation(String value, String fieldName) {
        ResourceLocation location = ResourceLocation.tryParse(value);
        if (location == null) {
            throw new JsonParseException("Invalid " + fieldName + ": " + value);
        }
        return location;
    }

    public record Snapshot(Map<FarmAnimalKind, Map<ResourceLocation, FarmAnimalVariantDefinition>> definitions) {
        public Snapshot {
            EnumMap<FarmAnimalKind, Map<ResourceLocation, FarmAnimalVariantDefinition>> immutable =
                    new EnumMap<>(FarmAnimalKind.class);
            definitions.forEach((kind, values) ->
                    immutable.put(kind, Collections.unmodifiableMap(new TreeMap<>(values))));
            definitions = Collections.unmodifiableMap(immutable);
        }

        private static Snapshot baseline() {
            EnumMap<FarmAnimalKind, Map<ResourceLocation, FarmAnimalVariantDefinition>> definitions =
                    new EnumMap<>(FarmAnimalKind.class);
            for (FarmAnimalKind kind : FarmAnimalKind.values()) {
                TreeMap<ResourceLocation, FarmAnimalVariantDefinition> values = new TreeMap<>();
                values.put(COLD, baselineDefinition(kind, COLD, COLD_BIOMES, 1));
                String kindName = kind.name().toLowerCase();
                values.put(TEMPERATE, new FarmAnimalVariantDefinition(
                        kind,
                        TEMPERATE,
                        ResourceLocation.withDefaultNamespace(
                                "entity/" + kindName + "/temperate_" + kindName
                        ),
                        FarmAnimalVariantModel.NORMAL,
                        List.of(new FarmAnimalVariantSelector(Optional.empty(), 0)),
                        false
                ));
                values.put(WARM, baselineDefinition(kind, WARM, WARM_BIOMES, 1));
                definitions.put(kind, values);
            }
            return new Snapshot(definitions);
        }

        private static FarmAnimalVariantDefinition baselineDefinition(
                FarmAnimalKind kind,
                ResourceLocation id,
                ResourceLocation biomeTag,
                int priority
        ) {
            String kindName = kind.name().toLowerCase();
            FarmAnimalVariantModel model = id.equals(COLD)
                    ? FarmAnimalVariantModel.COLD
                    : kind == FarmAnimalKind.COW
                    ? FarmAnimalVariantModel.WARM
                    : FarmAnimalVariantModel.NORMAL;
            return new FarmAnimalVariantDefinition(
                    kind,
                    id,
                    ResourceLocation.withDefaultNamespace(
                            "entity/" + kindName + "/" + id.getPath() + "_" + kindName
                    ),
                    model,
                    List.of(new FarmAnimalVariantSelector(
                            Optional.of(new FarmAnimalVariantSelector.BiomeCondition(List.of(
                                    new FarmAnimalVariantSelector.RegistryEntryMatcher.Tag<>(
                                            TagKey.create(Registries.BIOME, biomeTag)
                                    )
                            ))),
                            priority
                    )),
                    false
            );
        }
    }

    private static final class Loader extends SimplePreparableReloadListener<Snapshot> {
        @Override
        protected Snapshot prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
            EnumMap<FarmAnimalKind, Map<ResourceLocation, FarmAnimalVariantDefinition>> definitions =
                    new EnumMap<>(FarmAnimalKind.class);
            Snapshot baseline = Snapshot.baseline();
            for (FarmAnimalKind kind : FarmAnimalKind.values()) {
                TreeMap<ResourceLocation, FarmAnimalVariantDefinition> values =
                        new TreeMap<>(baseline.definitions().get(kind));
                Map<ResourceLocation, Resource> resources = resourceManager.listResources(
                        kind.dataDirectory(),
                        location -> location.getPath().endsWith(".json")
                );
                resources.entrySet().stream()
                        .sorted(Map.Entry.comparingByKey())
                        .forEach(entry -> {
                            ResourceLocation file = entry.getKey();
                            String prefix = kind.dataDirectory() + "/";
                            String path = file.getPath();
                            if (!path.startsWith(prefix)) {
                                return;
                            }
                            ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
                                    file.getNamespace(),
                                    path.substring(prefix.length(), path.length() - ".json".length())
                            );
                            values.put(id, parse(kind, id, entry.getValue()));
                        });
                definitions.put(kind, values);
            }
            return new Snapshot(definitions);
        }

        @Override
        protected void apply(Snapshot prepared, ResourceManager resourceManager, ProfilerFiller profiler) {
            snapshot = prepared;
        }
    }
}
