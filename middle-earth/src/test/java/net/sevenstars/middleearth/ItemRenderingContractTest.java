package net.sevenstars.middleearth;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemRenderingContractTest {
    private static final Path MAIN_JAVA = Path.of("src/main/java");
    private static final Path MAIN_RESOURCES = Path.of("src/main/resources/assets/middle-earth/models");
    private static final Path MAIN_GENERATED = Path.of("src/main/generated/assets/middle-earth/models");
    private static final Path MAIN_RESOURCE_TEXTURES = Path.of(
            "src/main/resources/assets/middle-earth/textures"
    );
    private static final Path MAIN_GENERATED_TEXTURES = Path.of(
            "src/main/generated/assets/middle-earth/textures"
    );
    private static final Pattern STANDALONE_STATE_MODEL = Pattern.compile(
            ".*(?:_inventory|_blocking|_broken|_glowing|_strike|_pulling_[012]|_hot)$"
    );

    @Test
    void itemModelSelectionUsesReloadSafeConstantTimeCaches() throws IOException {
        String helper = source("net/sevenstars/middleearth/client/ItemModelRenderStateClient.java");
        String mixin = source("net/sevenstars/middleearth/mixin/client/ItemRendererMixin.java");
        String plateEvents = source("net/sevenstars/middleearth/block/special/plate/PlateModelClientEvents.java");
        String plateRenderer = source("net/sevenstars/middleearth/block/special/plate/PlateEntityRenderer.java");

        assertTrue(helper.contains("ModelEvent.RegisterAdditional"));
        assertTrue(helper.contains("ModelEvent.BakingCompleted"));
        assertTrue(helper.contains("IdentityHashMap<Item, ModelDescriptor>"));
        assertTrue(helper.contains("IdentityHashMap<Item, BakedModels>"));
        assertTrue(helper.contains("Collections.unmodifiableMap"));
        assertTrue(helper.contains("BAKED_MODELS.get(stack.getItem())"));
        assertTrue(helper.contains("model != null && model != missingModel"));
        assertTrue(helper.contains("shouldBeGlowing(level, entity)"));
        assertTrue(helper.contains("CustomDaggerWeaponItem.canSneakAttack(stack)"));
        assertTrue(helper.contains("pull >= 0.65F"));
        assertTrue(helper.contains("pull >= 0.9F"));
        assertFalse(helper.contains("original == models.get("));
        assertFalse(helper.contains("glowingLongsword.glowing"));
        assertFalse(helper.contains("glowingDagger.glowing"));

        String resolve = between(
                helper,
                "public static BakedModel resolve(",
                "private static BakedModel resolveBigBow"
        );
        for (String forbidden : new String[]{
                "Minecraft.getInstance", "getModelManager", ".stream(",
                "ThreadLocal", "printStackTrace", "new ", "throw "
        }) {
            assertFalse(resolve.contains(forbidden), "Hot path contains " + forbidden);
        }

        assertTrue(mixin.contains("@ModifyExpressionValue"));
        assertTrue(mixin.contains("method = \"renderStatic("));
        assertTrue(mixin.contains("ItemRenderer;getModel("));
        assertTrue(mixin.contains(
                "ItemModelRenderStateClient.resolve(original, stack, displayContext, level, entity)"
        ));

        assertTrue(plateEvents.contains("ModelEvent.BakingCompleted"));
        assertTrue(plateEvents.contains("Map.copyOf(models)"));
        assertTrue(plateEvents.contains("model != null && model != missingModel"));
        assertTrue(plateRenderer.contains("PlateModelClientEvents.getBakedModel(modelId)"));
        assertTrue(plateRenderer.contains("if (model != null)"));
        assertFalse(plateRenderer.contains("getModelManager()"));
    }

    @Test
    void generatedModelsCoverFlatActiveBrokenAndGlowingStates() throws IOException {
        String provider = source(
                "net/sevenstars/middleearth/datageneration/providers/models/ItemModelProvider.java"
        );
        String client = source("net/sevenstars/middleearth/MiddleEarthClient.java");

        assertTrue(provider.contains("instanceof CustomLongswordWeaponItem"));
        assertTrue(provider.contains("\"_blocking\""));
        assertTrue(provider.contains("\"_broken_blocking\""));
        assertTrue(provider.contains("\"_glowing_blocking\""));
        assertTrue(client.contains(" / 20.0F"));

        String dualArtefact = between(provider, "if (dualModel) {", "} else {");
        int glowing = dualArtefact.indexOf("overrides.add(override(glowing,");
        int broken = dualArtefact.indexOf("overrides.add(override(broken,");
        int blocking = dualArtefact.indexOf("overrides.add(override(blocking,");
        int glowingBlocking = dualArtefact.indexOf("overrides.add(override(glowingBlocking,");
        int brokenBlocking = dualArtefact.indexOf("overrides.add(override(brokenBlocking,");
        assertTrue(glowing >= 0 && glowing < broken);
        assertTrue(broken < blocking);
        assertTrue(blocking < glowingBlocking);
        assertTrue(glowingBlocking < brokenBlocking);

        for (String model : new String[]{
                "item/gondorian_longsword_blocking.json",
                "item/glamdring_blocking.json",
                "item/glamdring_broken_blocking.json",
                "item/glamdring_glowing_blocking.json",
                "item/gondorian_longbow_pulling_2_inventory.json",
                "item/gondorian_spear_inventory.json",
                "item/smoking_pipe.json",
                "item/troll_mace.json",
                "item/troll_mace_inventory.json",
                "item/candle_holder.json",
                "block/candle_holder.json",
                "item/watering_can.json",
                "block/watering_can.json",
                "block/watering_can_sprinkling.json"
        }) {
            assertTrue(modelExists(model), "Missing rendered state model " + model);
        }
    }

    @Test
    void everyStandaloneInventoryAndStateModelHasACompleteAssetGraph() throws IOException {
        Map<String, Path> models = effectiveModels();
        int checkedStates = 0;
        int checkedInventory = 0;

        for (Map.Entry<String, Path> entry : models.entrySet()) {
            String modelId = entry.getKey();
            String fileName = modelId.substring(modelId.lastIndexOf('/') + 1);
            if (!modelId.startsWith("item/")
                    || !STANDALONE_STATE_MODEL.matcher(modelId).matches()
                    && !fileName.startsWith("smoking_")) {
                continue;
            }

            checkedStates++;
            JsonObject model = JsonParser.parseString(Files.readString(entry.getValue()))
                    .getAsJsonObject();
            if (model.has("parent")) {
                assertModelReferenceExists(models, model.get("parent").getAsString(), modelId);
            }
            if (model.has("textures")) {
                for (Map.Entry<String, JsonElement> texture
                        : model.getAsJsonObject("textures").entrySet()) {
                    assertTextureReferenceExists(texture.getValue().getAsString(), modelId);
                }
            }
            if (model.has("overrides")) {
                for (JsonElement override : model.getAsJsonArray("overrides")) {
                    JsonObject overrideObject = override.getAsJsonObject();
                    if (overrideObject.has("model")) {
                        assertModelReferenceExists(
                                models,
                                overrideObject.get("model").getAsString(),
                                modelId
                        );
                    }
                }
            }

            if (modelId.startsWith("item/") && modelId.endsWith("_inventory")) {
                checkedInventory++;
                assertEquals("minecraft:item/generated", model.get("parent").getAsString(), modelId);
                String expectedTexture = modelId.equals("item/troll_mace_inventory")
                        ? "middle-earth:item/troll_mace"
                        : "middle-earth:" + modelId;
                assertEquals(
                        expectedTexture,
                        model.getAsJsonObject("textures").get("layer0").getAsString(),
                        modelId
                );
            }
        }

        assertEquals(610, checkedStates, "Unexpected standalone state model coverage");
        assertEquals(260, checkedInventory, "Unexpected standalone inventory model coverage");
    }

    @Test
    void testsGenerateRequiredModelsInCleanCheckouts() throws IOException {
        String build = Files.readString(Path.of("build.gradle"));
        String testTask = between(build, "tasks.named('test')", "tasks.named('jar')");
        assertTrue(testTask.contains("dependsOn tasks.named('runData')"));
    }

    @Test
    void itemTintsMatchTheUpstreamDefinitions() throws IOException {
        String colors = source("net/sevenstars/middleearth/client/ItemColorsME.java");
        String grassItems = between(colors, "GRASS_TINT_ITEMS = {", "    };");
        Matcher matcher = Pattern.compile("Mod(?:Nature)?Blocks\\.[A-Z0-9_]+").matcher(grassItems);
        long count = matcher.results().count();

        assertEquals(36, count);
        assertTrue(colors.contains("GrassColor.get(0.5D, 1.0D)"));
        assertTrue(colors.contains("SimpleDyeableItemModel.items"));
        assertTrue(colors.contains("DyedItemColor.getOrDefault(stack, -6265536)"));
        assertTrue(colors.contains("WoodBlockSets.BEECH_SET.leaves"));
        assertTrue(colors.contains("0xFF48B518"));
        assertTrue(colors.contains("ResourceItemsME.COLORED_BUNDLES"));
    }

    @Test
    void forgedComponentsKeepEveryMaterialModelAndHotState() throws IOException {
        String provider = source(
                "net/sevenstars/middleearth/datageneration/providers/models/ItemModelProvider.java"
        );
        String build = Files.readString(Path.of("build.gradle"));
        String[] components = {
                "rod", "large_rod", "pickaxe_head", "axe_head", "shovel_head", "hoe_head",
                "blade", "short_blade", "long_blade", "sword_hilt", "mail_ring", "mail",
                "scale", "scale_mail", "armor_plate", "helmet_plate", "shield_border",
                "shield_plate"
        };
        String[] materials = {
                "jade", "tin", "lead", "silver", "bronze", "steel", "crude",
                "burzum_steel", "edhel_steel", "khazad_steel", "mithril", "quartz",
                "iron", "netherite", "redstone", "copper", "gold", "emerald",
                "diamond", "lapis", "amethyst", "resin"
        };

        assertTrue(provider.contains("generatedBaseModels.add(baseModel)"));
        assertTrue(build.contains(
                "sourceSets.main.resources.setSrcDirs(['src/main/generated', 'src/main/resources'])"
        ));
        for (String component : components) {
            Path base = MAIN_GENERATED.resolve("item/" + component + ".json");
            assertTrue(Files.isRegularFile(base), "Missing generated component model " + component);
            String model = Files.readString(base);
            int lastTrim = -1;
            for (String material : materials) {
                String child = "middle-earth:item/" + component + "_" + material + "_trim";
                int index = model.indexOf(child);
                assertTrue(index > lastTrim, "Missing or unordered material model " + child);
                lastTrim = index;
            }
            int hot = model.indexOf("middle-earth:item/" + component + "_hot");
            assertTrue(hot > lastTrim, "Hot state must override material state for " + component);
        }
    }

    private static String source(String relative) throws IOException {
        return Files.readString(MAIN_JAVA.resolve(relative));
    }

    private static String between(String source, String startMarker, String endMarker) {
        int start = source.indexOf(startMarker);
        assertTrue(start >= 0, "Missing marker " + startMarker);
        int end = source.indexOf(endMarker, start + startMarker.length());
        assertTrue(end > start, "Missing marker " + endMarker);
        return source.substring(start, end);
    }

    private static boolean modelExists(String relative) {
        return Files.isRegularFile(MAIN_RESOURCES.resolve(relative))
                || Files.isRegularFile(MAIN_GENERATED.resolve(relative));
    }

    private static Map<String, Path> effectiveModels() throws IOException {
        Map<String, Path> models = new LinkedHashMap<>();
        addModels(models, MAIN_GENERATED);
        addModels(models, MAIN_RESOURCES);
        return models;
    }

    private static void addModels(Map<String, Path> models, Path root) throws IOException {
        if (!Files.isDirectory(root)) {
            return;
        }
        try (Stream<Path> paths = Files.walk(root)) {
            paths.filter(path -> path.getFileName().toString().endsWith(".json"))
                    .forEach(path -> {
                        String relative = root.relativize(path).toString().replace('\\', '/');
                        models.putIfAbsent(relative.substring(0, relative.length() - 5), path);
                    });
        }
    }

    private static void assertModelReferenceExists(
            Map<String, Path> models,
            String reference,
            String owner
    ) {
        if (!reference.startsWith("middle-earth:")) {
            return;
        }
        String path = reference.substring("middle-earth:".length());
        assertTrue(models.containsKey(path), owner + " references missing model " + reference);
    }

    private static void assertTextureReferenceExists(String reference, String owner) {
        if (reference.startsWith("#") || !reference.startsWith("middle-earth:")) {
            return;
        }
        String relative = reference.substring("middle-earth:".length()) + ".png";
        assertTrue(
                Files.isRegularFile(MAIN_GENERATED_TEXTURES.resolve(relative))
                        || Files.isRegularFile(MAIN_RESOURCE_TEXTURES.resolve(relative)),
                owner + " references missing texture " + reference
        );
    }
}
