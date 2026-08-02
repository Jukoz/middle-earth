package net.sevenstars.middleearth;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RecipeViewerCompatibilityContractTest {
    private static final Path MAIN_JAVA = Path.of("src/main/java/net/sevenstars/middleearth");
    private static final Path GENERATED_RECIPES =
            Path.of("src/main/generated/data/middle-earth/recipe");
    private static final Path GENERATED_VANILLA_RECIPES =
            Path.of("src/main/generated/data/minecraft/recipe");
    private static final Path RESOURCE_RECIPES =
            Path.of("src/main/resources/data/middle-earth/recipe");
    private static final Path PROCESSED_RECIPES =
            Path.of("build/resources/main/data/middle-earth/recipe");
    private static final Path PROCESSED_VANILLA_RECIPES =
            Path.of("build/resources/main/data/minecraft/recipe");

    @Test
    void jeiAndEmiExposeEveryActiveCustomRecipeType() throws IOException {
        String jei = source("compat/jei/MiddleEarthJeiPlugin.java");
        String emi = source("compat/emi/MiddleEarthEmiPlugin.java").replace("\r\n", "\n");

        Map<String, Integer> expected = Map.of(
                "middle-earth:artisan_table", 854,
                "middle-earth:inscription_table", 102,
                "middle-earth:anvil_shaping", 11,
                "middle-earth:alloying", 63
        );
        assertEquals(expected, activeRecipeCounts());

        for (String field : List.of("ARTISAN_TABLE", "INSCRIPTION_TABLE", "ANVIL_SHAPING", "FORGE")) {
            assertTrue(jei.contains("registration.addRecipes(" + field));
            assertTrue(jei.contains("registration.addRecipeCatalysts(" + field));
            assertTrue(emi.contains("registry.addCategory(" + field));
            assertTrue(emi.contains("registry.addWorkstation(" + field));
            assertTrue(emi.contains("getAllRecipesFor(RecipesME." + field));
        }

        assertFalse(jei.contains("RecipesME.CROCKPOT"));
        assertFalse(emi.contains("RecipesME.CROCKPOT"));
        assertFalse(jei.contains("registerRecipeTransferHandlers"));
        assertFalse(emi.contains("/artisan_table"));
        assertFalse(emi.contains("/shaping_anvil"));
        assertFalse(emi.contains("/forge"));
    }

    @Test
    void dynamicCustomCraftingRecipesUseNativeCraftingViewerExtensions() throws IOException {
        Map<String, String> expected = Map.ofEntries(
                Map.entry("middle-earth:custom_armor_back_attachment", "BackAttachmentRecipe.class"),
                Map.entry("middle-earth:custom_armor_back_attachment_removal", "BackAttachmentRemovalRecipe.class"),
                Map.entry("middle-earth:custom_armor_helmet_attachment", "HelmetAttachmentRecipe.class"),
                Map.entry("middle-earth:custom_armor_helmet_attachment_removal", "HelmetAttachmentRemovalRecipe.class"),
                Map.entry("middle-earth:custom_mount_armor_addon_removal", "MountArmorAddonRemovalRecipe.class"),
                Map.entry("middle-earth:custom_mount_armor_side_skull_addon", "MountArmorSideSkullAddonRecipe.class"),
                Map.entry("middle-earth:custom_mount_armor_top_skull_addon", "MountArmorTopSkullAddonRecipe.class"),
                Map.entry("middle-earth:custom_item_decoration", "CustomItemDecorationRecipe.class")
        );

        Set<String> generatedTypes = new HashSet<>();
        try (Stream<Path> files = Files.list(GENERATED_VANILLA_RECIPES)) {
            for (Path path : files
                    .filter(Files::isRegularFile)
                    .filter(file -> file.getFileName().toString().startsWith("custom_"))
                    .toList()) {
                JsonObject recipe = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
                generatedTypes.add(recipe.get("type").getAsString());
            }
        }
        assertEquals(expected.keySet(), generatedTypes);

        String jei = source("compat/jei/MiddleEarthJeiPlugin.java");
        String emi = source("compat/emi/MiddleEarthEmiPlugin.java").replace("\r\n", "\n");
        String display = source("compat/recipeviewer/DynamicCraftingRecipeDisplay.java");
        assertTrue(jei.contains("registerVanillaCategoryExtensions"));
        assertTrue(jei.contains("craftingGridHelper.createAndSetInputs"));
        assertTrue(jei.contains("craftingGridHelper.createAndSetOutputs"));
        assertTrue(emi.contains("VanillaEmiRecipeCategories.CRAFTING"));
        assertTrue(emi.contains("new DynamicCraftingEmiRecipe(holder)"));
        assertTrue(emi.contains("getBackingRecipe()"));
        assertTrue(emi.contains("super(VanillaEmiRecipeCategories.CRAFTING, holder.id()"));
        assertTrue(emi.contains("boolean importDynamicCraftingFromJei = ModList.get().isLoaded(\"jei\")"));
        assertTrue(emi.contains("if (!importDynamicCraftingFromJei)"));
        assertEquals(1, countOccurrences(emi, "registry.addRecipe(new DynamicCraftingEmiRecipe(holder))"));
        assertEquals(3, countOccurrences(emi, "public boolean supportsRecipeTree()"));
        assertEquals(3, countOccurrences(emi, "public boolean supportsRecipeTree() {\n            return false;"));

        for (String recipeClass : expected.values()) {
            assertTrue(jei.contains("crafting.addExtension(" + recipeClass));
            assertTrue(display.contains("instanceof " + recipeClass.replace(".class", "")));
        }

        assertFalse(jei.contains("ShapedRecipe.class"));
        assertFalse(jei.contains("ShapelessRecipe.class"));
        assertFalse(emi.contains("instanceof ShapedRecipe"));
        assertFalse(emi.contains("instanceof ShapelessRecipe"));
        assertTrue(display.contains("setBackAttachmentWithColor"));
        assertTrue(display.contains("setHelmetAttachmentWithcolor"));
        assertTrue(display.contains("new MountArmorAddonComponent(top, side)"));
        assertTrue(display.contains("DataComponents.BANNER_PATTERNS"));
        assertTrue(display.contains("List<ItemStack> cleanArmors = decoratedArmors.stream()"));
        assertFalse(display.contains("List<ItemStack> cleanArmors = armors.stream()"));
        assertFalse(display.contains("static final Map<"));
        assertTrue(jei.contains("private DynamicCraftingRecipeDisplay.Display display;"));
    }

    @Test
    void processedResourcesPreserveVanillaRecipeReplacementsIndependentlyOfViewers() throws IOException {
        assertEquals(activeRecipeCounts(), activeRecipeCounts(PROCESSED_RECIPES, PROCESSED_VANILLA_RECIPES));

        Set<String> expectedOverrides = Set.of(
                "bow", "crossbow", "golden_axe", "golden_hoe", "golden_shovel", "golden_sword",
                "iron_axe", "iron_hoe", "iron_shovel", "iron_sword"
        );
        Set<String> processedOverrides = new HashSet<>();
        for (String name : expectedOverrides) {
            Path recipe = PROCESSED_VANILLA_RECIPES.resolve(name + ".json");
            assertTrue(Files.isRegularFile(recipe), recipe.toString());
            JsonObject json = JsonParser.parseString(Files.readString(recipe)).getAsJsonObject();
            assertEquals("middle-earth:artisan_table", json.get("type").getAsString(), recipe.toString());
            processedOverrides.add(name);
        }
        assertEquals(expectedOverrides, processedOverrides);
    }

    @Test
    void forgeViewerLayoutAndMetalQuantityMatchRuntimeSemantics() throws IOException {
        String helper = source("compat/recipeviewer/RecipeViewerHelper.java");
        String jei = source("compat/jei/MiddleEarthJeiPlugin.java");
        String emi = source("compat/emi/MiddleEarthEmiPlugin.java");

        assertTrue(helper.contains("new ItemStack(metal.getIngot(), getIngotEquivalent(recipe))"));
        assertTrue(jei.contains("gui.createDrawableItemLike(ModDecorativeBlocks.FORGE), 138, 64"));
        assertTrue(jei.contains("builder.addOutputSlot(117, 7)"));
        assertTrue(jei.contains("this.arrow.draw(graphics, 80, 7)"));
        assertTrue(emi.contains("super(FORGE, holder, 138, 64)"));
        assertTrue(emi.contains("EmiTexture.EMPTY_ARROW, 80, 7"));
        assertTrue(emi.contains("widgets.addSlot(this.outputs.getFirst(), 116, 6)"));
    }

    @Test
    void dynamicCraftingCoreRejectsDuplicateAndLossyInputs() throws IOException {
        String backAttachment = source("recipe/BackAttachmentRecipe.java");
        String helmetAttachment = source("recipe/HelmetAttachmentRecipe.java");
        String backRemoval = source("recipe/BackAttachmentRemovalRecipe.java");
        String helmetRemoval = source("recipe/HelmetAttachmentRemovalRecipe.java");
        String mountRemoval = source("recipe/MountArmorAddonRemovalRecipe.java");
        String mountSide = source("recipe/MountArmorSideSkullAddonRecipe.java").replace("\r\n", "\n");
        String mountTop = source("recipe/MountArmorTopSkullAddonRecipe.java").replace("\r\n", "\n");
        String display = source("compat/recipeviewer/DynamicCraftingRecipeDisplay.java");

        assertTrue(backAttachment.contains("|| !itemStackBackAttachment.isEmpty()"));
        assertTrue(backAttachment.contains("|| !backAttachment.isEmpty()"));
        assertTrue(helmetAttachment.contains("|| !itemStackHood.isEmpty()"));
        assertTrue(helmetAttachment.contains("|| !hood.isEmpty()"));

        for (String removal : List.of(backRemoval, helmetRemoval, mountRemoval)) {
            assertEquals(2, countOccurrences(removal, "|| !itemStackShears.isEmpty()"));
            assertTrue(removal.contains("!itemStack.isEmpty() && !itemStackShears.isEmpty()"));
        }

        assertTrue(mountSide.contains("data.sideArmorAddon()"));
        assertTrue(mountTop.contains("data.topArmorAddon()"));
        assertTrue(mountSide.contains("} else {\n                    return false;"));
        assertTrue(mountTop.contains("} else {\n                    return false;"));
        assertTrue(display.contains(".filter(stack -> !currentSideAddon(stack))"));
        assertTrue(display.contains(".filter(stack -> !currentTopAddon(stack))"));
        assertTrue(display.contains("output.remove(DataComponents.BANNER_PATTERNS)"));
    }

    @Test
    void viewerRegistrationLogsAreSingleStageAndJeiDrawsUsePrecomputedText() throws IOException {
        String jei = source("compat/jei/MiddleEarthJeiPlugin.java");
        String emi = source("compat/emi/MiddleEarthEmiPlugin.java");

        assertTrue(jei.contains("JEI recipe viewer registered: artisan=%d, inscription=%d, anvil=%d, forge=%d, dynamic_crafting=%d"));
        assertTrue(emi.contains("EMI recipe viewer registered: artisan=%d, inscription=%d, anvil=%d, forge=%d, dynamic_crafting=%d"));
        assertEquals(1, countOccurrences(jei, "JEI recipe viewer registered:"));
        assertEquals(1, countOccurrences(emi, "EMI recipe viewer registered:"));

        assertTrue(jei.contains("DRAW_TEXT.clear()"));
        assertTrue(jei.contains("cacheDrawText(artisanRecipes, forgeRecipes, anvilRecipes, inscriptionRecipes)"));
        List<String> drawBodies = methodBodies(jei, "public void draw(");
        assertEquals(4, drawBodies.size());
        for (String drawBody : drawBodies) {
            assertFalse(drawBody.contains("Component."));
            assertFalse(drawBody.contains("String.join"));
            assertFalse(drawBody.contains(".stream()"));
            assertFalse(drawBody.contains("for ("));
            assertTrue(drawBody.contains("DRAW_TEXT.get(holder.id())"));
        }
    }

    @Test
    void disabledCrockpotResidueIsNotAdvertisedAsAUsableWorkstation() throws IOException {
        String blocks = source("block/registration/ModDecorativeBlocks.java")
                .replaceAll("(?s)/\\*.*?\\*/", "")
                .replaceAll("(?m)//.*$", "");
        String blockEntities = source("block/registration/ModBlockEntities.java")
                .replaceAll("(?s)/\\*.*?\\*/", "")
                .replaceAll("(?m)//.*$", "");
        String items = source("item/DecorativeItemsME.java")
                .replaceAll("(?s)/\\*.*?\\*/", "")
                .replaceAll("(?m)//.*$", "");

        assertFalse(blocks.contains("CROCKPOT"));
        assertFalse(blockEntities.contains("CROCKPOT"));
        assertFalse(items.contains("CROCKPOT"));
        assertEquals(3, countRecipes(RESOURCE_RECIPES, "middle-earth:crockpot"));
    }

    @Test
    void everyInscriptionRecipeHasAtLeastOneRealCatalyst() throws IOException {
        String wordBank = source("recipe/inscription/InscriptionWordBank.java");
        Pattern entry = Pattern.compile("wordBank\\.put\\(([^,]+), \\\"([^\\\"]+)\\\"\\)");
        Matcher matcher = entry.matcher(wordBank);
        Map<String, Set<String>> wordsByCatalyst = new HashMap<>();
        while (matcher.find()) {
            wordsByCatalyst
                    .computeIfAbsent(matcher.group(1).trim(), ignored -> new HashSet<>())
                    .add(matcher.group(2));
        }

        Set<String> common = wordsByCatalyst.getOrDefault("null", Set.of());
        List<String> catalysts = List.of(
                "Items.LAPIS_LAZULI",
                "Items.EMERALD",
                "ResourceItemsME.RUBY",
                "ResourceItemsME.SAPPHIRE",
                "ResourceItemsME.ADAMANT"
        );
        int checked = 0;
        try (Stream<Path> files = Files.list(GENERATED_RECIPES)) {
            for (Path path : files.filter(Files::isRegularFile).toList()) {
                JsonObject recipe = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
                if (!"middle-earth:inscription_table".equals(recipe.get("type").getAsString())) {
                    continue;
                }
                JsonArray jsonWords = recipe.getAsJsonArray("words");
                List<String> required = new ArrayList<>();
                jsonWords.forEach(word -> required.add(word.getAsString()));
                boolean reachable = catalysts.stream().anyMatch(catalyst -> {
                    Set<String> available = new HashSet<>(common);
                    available.addAll(wordsByCatalyst.getOrDefault(catalyst, Set.of()));
                    return available.containsAll(required);
                });
                assertTrue(reachable, path.getFileName() + " has no valid inscription catalyst");
                checked++;
            }
        }
        assertEquals(102, checked);
        assertTrue(common.contains("spirit"));
    }

    @Test
    void everyArtisanRecipeFitsItsPhysicalInputShape() throws IOException {
        Map<String, Integer> capacities = Map.ofEntries(
                Map.entry("any", 9), Map.entry("sword", 3), Map.entry("axe", 3),
                Map.entry("spear", 3), Map.entry("bow", 6), Map.entry("crossbow", 7),
                Map.entry("pickaxe", 3), Map.entry("shovel", 3), Map.entry("hoe", 3),
                Map.entry("chisel", 3), Map.entry("helmet", 5), Map.entry("chestplate", 8),
                Map.entry("leggings", 7), Map.entry("boots", 4), Map.entry("hat", 5),
                Map.entry("back_attachment", 9), Map.entry("helmet_attachment", 6),
                Map.entry("pipe", 6), Map.entry("light_shield", 5),
                Map.entry("medium_shield", 7), Map.entry("heavy_shield", 9),
                Map.entry("mount_armor", 7)
        );

        int checked = 0;
        for (Path recipeDirectory : List.of(GENERATED_RECIPES, GENERATED_VANILLA_RECIPES)) {
            try (Stream<Path> files = Files.list(recipeDirectory)) {
                for (Path path : files.filter(Files::isRegularFile).toList()) {
                    JsonObject recipe = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
                    if (!"middle-earth:artisan_table".equals(recipe.get("type").getAsString())) {
                        continue;
                    }
                    String category = recipe.get("category").getAsString();
                    assertTrue(capacities.containsKey(category), path + " uses an unknown artisan shape");
                    assertTrue(recipe.getAsJsonArray("ingredients").size() <= capacities.get(category),
                            path.getFileName() + " has more ingredients than the " + category + " shape");
                    checked++;
                }
            }
        }
        assertEquals(854, checked);
    }

    @Test
    void alloyingSearchOutputsCoverAllRecipesWithoutChangingLiquidSemantics() throws IOException {
        String metals = source("block/special/forge/MetalTypes.java");
        Set<String> metalNames = new HashSet<>();
        Matcher names = Pattern.compile("[A-Z_]+\\([^,]+, \\\"([^\\\"]+)\\\"").matcher(metals);
        while (names.find()) {
            metalNames.add(names.group(1));
        }

        String helper = source("compat/recipeviewer/RecipeViewerHelper.java");
        String jei = source("compat/jei/MiddleEarthJeiPlugin.java");
        String emi = source("compat/emi/MiddleEarthEmiPlugin.java");
        assertTrue(helper.contains("return new ItemStack(metal.getIngot(), getIngotEquivalent(recipe));"));
        assertTrue(helper.contains("METAL_UNITS_PER_INGOT = 144"));
        assertTrue(jei.contains("getIngotEquivalent"));
        assertTrue(emi.contains("getIngotEquivalent"));
        assertFalse(jei.contains("contains(\"nugget\")"));
        assertFalse(emi.contains("contains(\"nugget\")"));

        int checked = 0;
        try (Stream<Path> files = Files.list(GENERATED_RECIPES)) {
            for (Path path : files.filter(Files::isRegularFile).toList()) {
                JsonObject recipe = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
                if (!"middle-earth:alloying".equals(recipe.get("type").getAsString())) {
                    continue;
                }
                assertTrue(metalNames.contains(recipe.get("output").getAsString()), path.toString());
                int amount = recipe.get("amount").getAsInt();
                assertEquals(0, amount % 144, path.toString());
                assertTrue(amount >= 144 && amount <= 576, path.toString());
                checked++;
            }
        }
        assertEquals(63, checked);
    }

    @Test
    void viewerDependenciesStayOptionalAndClientOnly() throws IOException {
        String build = Files.readString(Path.of("build.gradle"));
        String metadata = Files.readString(Path.of("src/main/resources/META-INF/neoforge.mods.toml"));
        String rootProperties = Files.readString(Path.of("../gradle.properties"));

        assertTrue(build.contains("compileOnly \"dev.emi:emi-neoforge:${emi_version}:api\""));
        assertTrue(build.contains("compileOnly \"mezz.jei:jei-${minecraft_version}-neoforge-api:${jei_version}\""));
        assertFalse(build.contains("jarJar \"dev.emi"));
        assertFalse(build.contains("jarJar \"mezz.jei"));
        assertTrue(rootProperties.contains("emi_version=1.1.24+1.21.1"));
        assertTrue(rootProperties.contains("jei_version=19.43.0.390"));
        assertTrue(build.contains("providers.gradleProperty('viewer').orElse('both')"));
        assertTrue(build.contains("['none', 'jei', 'emi', 'both']"));
        assertTrue(build.contains("viewerRuntime.equalsIgnoreCase('emi') || viewerRuntime.equalsIgnoreCase('both')"));
        assertTrue(build.contains("viewerRuntime.equalsIgnoreCase('jei') || viewerRuntime.equalsIgnoreCase('both')"));

        for (String modId : List.of("emi", "jei")) {
            String stanza = dependencyStanza(metadata, modId);
            assertTrue(stanza.contains("type=\"optional\""));
            assertTrue(stanza.contains("side=\"CLIENT\""));
            assertTrue(stanza.contains("ordering=\"AFTER\""));
        }
        assertTrue(dependencyStanza(metadata, "neoforge").contains("ordering=\"AFTER\""));
        for (String modId : List.of("platform", "vanillabackport")) {
            String stanza = dependencyStanza(metadata, modId);
            assertTrue(stanza.contains("type=\"optional\""));
            assertTrue(stanza.contains("ordering=\"AFTER\""));
            assertTrue(stanza.contains("side=\"BOTH\""));
        }
    }

    private static Map<String, Integer> activeRecipeCounts() throws IOException {
        return activeRecipeCounts(GENERATED_RECIPES, GENERATED_VANILLA_RECIPES);
    }

    private static Map<String, Integer> activeRecipeCounts(Path recipes, Path vanillaRecipes) throws IOException {
        Map<String, Integer> counts = new HashMap<>();
        for (Path recipeDirectory : List.of(recipes, vanillaRecipes)) {
            try (Stream<Path> files = Files.list(recipeDirectory)) {
                for (Path path : files.filter(Files::isRegularFile).toList()) {
                    JsonObject recipe = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
                    String type = recipe.get("type").getAsString();
                    if (type.equals("middle-earth:artisan_table")
                            || type.equals("middle-earth:inscription_table")
                            || type.equals("middle-earth:anvil_shaping")
                            || type.equals("middle-earth:alloying")) {
                        counts.merge(type, 1, Integer::sum);
                    }
                }
            }
        }
        return counts;
    }

    private static String dependencyStanza(String metadata, String modId) {
        int dependency = metadata.indexOf("modId=\"" + modId + "\"");
        assertTrue(dependency >= 0, "Missing dependency stanza for " + modId);
        int next = metadata.indexOf("[[dependencies.", dependency);
        return metadata.substring(dependency, next >= 0 ? next : metadata.length());
    }

    private static int countRecipes(Path directory, String type) throws IOException {
        int count = 0;
        try (Stream<Path> files = Files.list(directory)) {
            for (Path path : files.filter(Files::isRegularFile).toList()) {
                JsonObject recipe = JsonParser.parseString(Files.readString(path)).getAsJsonObject();
                if (type.equals(recipe.get("type").getAsString())) {
                    count++;
                }
            }
        }
        return count;
    }

    private static String source(String relativePath) throws IOException {
        return Files.readString(MAIN_JAVA.resolve(relativePath));
    }

    private static int countOccurrences(String source, String value) {
        int count = 0;
        int index = 0;
        while ((index = source.indexOf(value, index)) >= 0) {
            count++;
            index += value.length();
        }
        return count;
    }

    private static List<String> methodBodies(String source, String signature) {
        List<String> bodies = new ArrayList<>();
        int searchFrom = 0;
        while ((searchFrom = source.indexOf(signature, searchFrom)) >= 0) {
            int openingBrace = source.indexOf('{', searchFrom);
            int depth = 0;
            for (int index = openingBrace; index < source.length(); index++) {
                char character = source.charAt(index);
                if (character == '{') {
                    depth++;
                } else if (character == '}' && --depth == 0) {
                    bodies.add(source.substring(openingBrace, index + 1));
                    searchFrom = index + 1;
                    break;
                }
            }
        }
        return bodies;
    }
}
