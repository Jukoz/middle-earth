package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class Upstream102DataGenerationContractTest {
    private static final Path MAIN_JAVA = Path.of("src/main/java");
    private static final Path MAIN_RESOURCES = Path.of("src/main/resources");
    private static final Path GENERATED_RESOURCES = Path.of("src/main/generated");

    @Test
    void newDecorativeBlocksHaveModelsLootAndRecipes() throws IOException {
        String drops = source("datageneration/content/loot_tables/BlockDrops.java");
        String models = source("datageneration/content/models/SimpleItemModel.java");
        String recipes = source("datageneration/providers/recipes/RecipeProvider.java");

        for (String block : List.of(
                "OLD_SKULL",
                "SKELETON",
                "SMALL_BRONZE_CHANDELIER",
                "BRONZE_CHANDELIER",
                "SMALL_CHANDELIER",
                "CHANDELIER"
        )) {
            assertTrue(drops.contains("ModDecorativeBlocks." + block), block);
            assertTrue(recipes.contains("ModDecorativeBlocks." + block), block);
        }

        for (String chandelier : List.of(
                "SMALL_BRONZE_CHANDELIER",
                "BRONZE_CHANDELIER",
                "SMALL_CHANDELIER",
                "CHANDELIER"
        )) {
            assertTrue(models.contains("ModDecorativeBlocks." + chandelier + ".asItem()"), chandelier);
            assertEquals(
                    1,
                    count(recipes, "RecipeCategory.BUILDING_BLOCKS, ModDecorativeBlocks." + chandelier + ")"),
                    chandelier
            );
        }
    }

    @Test
    void newWearablesAndShieldsHaveSingleArtisanRecipes() throws IOException {
        String armor = source("datageneration/providers/recipes/ArtisanTableGenericArmorRecipeProvider.java");
        String handheld = source("datageneration/providers/recipes/ArtisanTableHandheldRecipeProvider.java");

        for (String item : List.of(
                "BAGGY_HAT",
                "CHAPERON",
                "GLASSES",
                "DWARVEN_MONOCLE",
                "BEEKEEPER_MASK",
                "BLACKSMITH_APRON"
        )) {
            assertEquals(1, count(armor, "EquipmentItemsME." + item + ".getDefaultInstance()"), item);
        }

        for (String item : List.of(
                "DOL_GULDUR_ARMRUST_SHIELD",
                "DOL_GULDUR_HEAVY_SHIELD",
                "DOL_GULDUR_HEAVY_SKULL_SHIELD"
        )) {
            assertEquals(1, count(handheld, "WeaponItemsME." + item + ".getDefaultInstance()"), item);
            assertEquals(1, count(handheld, "WeaponItemsME." + item + ").getPath() + \"_artisan\""), item);
        }
    }

    @Test
    void newWearablesAndShieldsFlowThroughModelTagAndLanguageCollectors() throws IOException {
        String equipment = source("item/EquipmentItemsME.java");
        String weapons = source("item/WeaponItemsME.java");
        String itemModels = source("datageneration/providers/models/ItemModelProvider.java");

        for (String registration : List.of(
                "registerCustomModelArmorPiece(\"baggy_hat\"",
                "registerCustomModelArmorPiece(\"chaperon\"",
                "registerCustomModelArmorPiece(\"glasses\"",
                "registerCustomModelArmorPiece(\"dwarven_monocle\"",
                "registerDyeableArmorPiece(\"beekeeper_mask\"",
                "registerArmorPiece(\"blacksmith_apron\""
        )) {
            assertTrue(equipment.contains(registration), registration);
        }
        assertTrue(equipment.contains("SimpleItemModel.items.add(item)"));
        assertTrue(equipment.contains("SimpleDyeableItemModel.items.add(item)"));
        assertTrue(equipment.contains("TranslationEntries.itemEntries.add(item)"));
        assertTrue(equipment.contains("ArmorTags.armors.add(armorItem)"));

        for (String shield : List.of(
                "dol_guldur_ancient_flanged_shield",
                "rusted_dol_guldur_ancient_flanged_shield",
                "dol_guldur_armrust_shield",
                "dol_guldur_heavy_shield",
                "dol_guldur_heavy_skull_shield"
        )) {
            assertTrue(weapons.contains("registerShield(\"" + shield + "\""), shield);
        }
        assertTrue(weapons.contains("shields.add(item)"));
        assertTrue(weapons.contains("TranslationEntries.itemEntries.add(item)"));
        assertTrue(weapons.contains("WeaponEnchants.weapons.add(item)"));
        assertTrue(itemModels.contains("WeaponItemsME.shields.forEach(this::registerShield)"));
    }

    @Test
    void upstreamMenuMusicTranslationIsGenerated() throws IOException {
        String translations = source("datageneration/content/TranslationEntries.java");
        assertTrue(translations.contains(".music.menu.swept_away\", \"Swept Away\""));
    }

    @Test
    void officialTagInscriptionLocalizationIsComplete() throws IOException {
        String translations = source("datageneration/content/TranslationEntries.java");
        String languageProvider = source("datageneration/providers/LanguageProvider.java");
        String screen = source("gui/inscriptiontable/InscriptionTableScreen.java");
        String wordBank = source("recipe/inscription/InscriptionWordBank.java");

        assertTrue(translations.contains("inscriptionEntries = new ArrayList"));
        assertTrue(translations.contains(".linking_dash\", \"-\""));
        assertTrue(translations.contains(".level\", \"%d Level\""));
        assertTrue(translations.contains(".levels\", \"%d Levels\""));
        assertTrue(languageProvider.contains("TranslationEntries.inscriptionEntries.forEach"));
        assertTrue(wordBank.contains("TranslationEntries.inscriptionEntries.addAll(wordBank.values())"));
        assertTrue(screen.contains("String levelKey = k == 1 ? \".level\" : \".levels\""));
        assertTrue(screen.contains(".linking_dash\").getString()"));

        JsonObject chinese = JsonParser.parseString(Files.readString(MAIN_RESOURCES.resolve(
                "assets/middle-earth/lang/zh_cn.json"
        ))).getAsJsonObject();
        assertEquals("", chinese.get("inscription.middle-earth.linking_dash").getAsString());
        assertEquals("%d 级", chinese.get("inscription.middle-earth.level").getAsString());
        assertEquals("%d 级", chinese.get("inscription.middle-earth.levels").getAsString());
    }

    @Test
    void upstreamForgeAndLayerLootSemanticsAreGenerated() throws IOException {
        JsonObject forge = generatedJson("data/middle-earth/loot_table/blocks/forge.json");
        JsonArray forgePools = forge.getAsJsonArray("pools");
        assertEquals(1, forgePools.size());
        JsonObject forgePool = forgePools.get(0).getAsJsonObject();
        assertEquals("top", forgePool.getAsJsonArray("conditions").get(0).getAsJsonObject()
                .getAsJsonObject("properties").get("part").getAsString());
        JsonObject forgeEntry = forgePool.getAsJsonArray("entries").get(0).getAsJsonObject();
        assertEquals("middle-earth:forge", forgeEntry.get("name").getAsString());
        assertEquals("minecraft:survives_explosion", forgeEntry.getAsJsonArray("conditions")
                .get(0).getAsJsonObject().get("condition").getAsString());

        for (String block : List.of("skeletal_pile_layer", "waste_pile_layer")) {
            JsonArray pools = generatedJson(
                    "data/middle-earth/loot_table/blocks/" + block + ".json"
            ).getAsJsonArray("pools");
            assertEquals(8, pools.size(), block);
            for (int layers = 1; layers <= 8; layers++) {
                JsonObject pool = pools.get(layers - 1).getAsJsonObject();
                assertEquals(Integer.toString(layers), pool.getAsJsonArray("conditions")
                        .get(0).getAsJsonObject().getAsJsonObject("properties")
                        .get("layers").getAsString(), block);
                JsonObject entry = pool.getAsJsonArray("entries").get(0).getAsJsonObject();
                assertEquals("middle-earth:" + block, entry.get("name").getAsString(), block);
                JsonArray functions = entry.getAsJsonArray("functions");
                assertEquals(layers, functions.get(0).getAsJsonObject().get("count").getAsInt(), block);
                assertEquals("minecraft:explosion_decay", functions.get(1).getAsJsonObject()
                        .get("function").getAsString(), block);
            }
        }
    }

    @Test
    void saddleCraftabilityIsPreservedForMinecraft1211() throws IOException {
        String recipes = source("datageneration/providers/recipes/RecipeProvider.java");
        assertTrue(recipes.contains("ShapedRecipeBuilder.shaped(RecipeCategory.MISC, Items.SADDLE)"));
        assertTrue(recipes.contains("MiddleEarth.MOD_ID, \"saddle\""));

        JsonObject saddle = generatedJson("data/middle-earth/recipe/saddle.json");
        assertEquals("minecraft:crafting_shaped", saddle.get("type").getAsString());
        JsonArray pattern = saddle.getAsJsonArray("pattern");
        assertEquals("LLL", pattern.get(0).getAsString());
        assertEquals("I I", pattern.get(1).getAsString());
        JsonObject key = saddle.getAsJsonObject("key");
        assertEquals("minecraft:leather", key.getAsJsonObject("L").get("item").getAsString());
        assertEquals("minecraft:iron_ingot", key.getAsJsonObject("I").get("item").getAsString());
        JsonObject result = saddle.getAsJsonObject("result");
        assertEquals("minecraft:saddle", result.get("id").getAsString());
        assertEquals(1, result.get("count").getAsInt());
    }

    @Test
    void secondaryBreaksUseTheRealToolAndVanillaDestroySemantics() throws IOException {
        String events = source("event/ModEvents.java");

        assertTrue(events.contains("CommonHooks.fireBlockBreak("));
        assertTrue(events.contains("BlockEntity blockEntity"));
        assertTrue(events.contains("ItemStack dropTool = stack.copy()"));
        assertTrue(events.contains("stack.mineBlock(world, destroyedState, blockpos, serverPlayer)"));
        assertTrue(events.contains("block.playerDestroy(world, serverPlayer, blockpos, destroyedState, blockEntity, dropTool)"));
        assertFalse(events.contains("world.destroyBlock(blockpos, true, player)"));
        assertFalse(events.contains("Block.dropResources("));
    }

    private static String source(String relativePath) throws IOException {
        return Files.readString(MAIN_JAVA.resolve("net/sevenstars/middleearth").resolve(relativePath));
    }

    private static JsonObject generatedJson(String relativePath) throws IOException {
        return JsonParser.parseString(Files.readString(GENERATED_RESOURCES.resolve(relativePath)))
                .getAsJsonObject();
    }

    private static int count(String value, String needle) {
        int matches = 0;
        int offset = 0;
        while ((offset = value.indexOf(needle, offset)) >= 0) {
            matches++;
            offset += needle.length();
        }
        return matches;
    }
}
