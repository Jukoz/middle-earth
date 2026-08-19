package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class UpstreamBaselineContractTest {
    private static final Path MAIN_JAVA = Path.of("src/main/java");
    private static final Path MAIN_RESOURCES = Path.of("src/main/resources");

    @Test
    void naturalSpawnPolicyUsesTheLevelDimensionKey() throws IOException {
        String events = source("net/sevenstars/middleearth/event/NeoForgeCommonEvents.java");
        assertTrue(events.contains("ModDimensions.isInMiddleEarth(level)"));
        assertFalse(events.contains("level.dimension().equals(ModDimensions.ME_DIMENSION_KEY)"));
    }

    @Test
    void baseHeightNeverReportsLandBelowSeaLevel() throws IOException {
        String generator = source(
                "net/sevenstars/middleearth/world/chunkgen/MiddleEarthChunkGenerator.java"
        );
        assertTrue(generator.contains("return Math.max(WATER_HEIGHT, (int) worldHeight);"));
    }

    @Test
    void coldSmithingDoesNotRequireOrDereferenceTemperature() throws IOException {
        String recipe = source("net/sevenstars/middleearth/recipe/AnvilShapingRecipe.java");
        assertFalse(recipe.contains("TEMPERATURE_DATA"));

        String anvil = source(
                "net/sevenstars/middleearth/block/special/shapingAnvil/ShapingAnvilBlockEntity.java"
        );
        assertTrue(anvil.contains("temperatureData == null ? 0 : temperatureData.temperature()"));
        assertTrue(anvil.contains("if (temperatureData != null)"));
        assertTrue(anvil.contains("ShapingProgress.roll"));
    }

    @Test
    void auleBlessingDefinitionsAndInscriptionsAgree() throws IOException {
        JsonObject aule = json("data/middle-earth/enchantment/aule_blessing.json");
        assertEquals(3, aule.get("max_level").getAsInt());
        assertEquals(
                "#middle-earth:enchantable/smithing_hammers",
                aule.get("supported_items").getAsString()
        );

        String inscriptions = source(
                "net/sevenstars/middleearth/datageneration/providers/recipes/InscriptionRecipeProvider.java"
        );
        int regionStart = inscriptions.indexOf("//region AULE BLESSING");
        int regionEnd = inscriptions.indexOf("//region BANE OF GIANTS", regionStart);
        assertTrue(regionStart >= 0 && regionEnd > regionStart);
        String auleRegion = inscriptions.substring(regionStart, regionEnd);
        assertEquals(3, count(auleRegion, "getEnchantment(EnchantmentsME.AULE_BLESSING)"));
        assertFalse(auleRegion.contains("EnchantmentsME.AILMENT_PROTECTION"));
    }

    @Test
    void celerityAndHammerTagsUseTheirDedicatedEnchantableSets() throws IOException {
        JsonObject celerity = json("data/middle-earth/enchantment/celerity.json");
        assertEquals(
                "#middle-earth:enchantable/celerity",
                celerity.get("supported_items").getAsString()
        );
        assertTrue(Files.isRegularFile(MAIN_RESOURCES.resolve(
                "data/middle-earth/tags/item/enchantable/smithing_hammers.json"
        )));
        assertTrue(Files.isRegularFile(MAIN_RESOURCES.resolve(
                "data/middle-earth/tags/enchantment/exclusive_set/smithing_hammer.json"
        )));
    }

    @Test
    void vanillaStoneOutputsKeepNamespacedSmeltingRecipes() throws IOException {
        String recipes = source(
                "net/sevenstars/middleearth/datageneration/providers/recipes/RecipeProvider.java"
        );
        assertTrue(recipes.contains("createCobbledBaseSmeltingRecipe(recipeOutput"));
        assertTrue(recipes.contains("inputPath + \"_to_\" + outputPath"));
    }

    @Test
    void crossbowsArePublishedToNeoForgeCommonToolTags() throws IOException {
        String tags = source(
                "net/sevenstars/middleearth/datageneration/providers/tags/ItemTagProvider.java"
        );
        assertTrue(tags.contains("\"c\", \"tools/crossbow\""));
        int rangedStart = tags.indexOf("\"c\", \"tools/ranged_weapon\"");
        int rangedEnd = tags.indexOf("\"c\", \"tools/bow\"", rangedStart);
        assertTrue(rangedStart >= 0 && rangedEnd > rangedStart);
        assertTrue(tags.substring(rangedStart, rangedEnd).contains("Crossbows.crossbows"));
    }

    @Test
    void broadhoofGoatsKeepTheLatestStablePowderedSnowImmunity() throws IOException {
        String broadhoof = source(
                "net/sevenstars/middleearth/entity/beasts/broadhoof/BroadhoofGoatEntity.java"
        );
        assertTrue(broadhoof.contains(
                ".add(EntityAttributesME.POWDERED_SNOW_IMMUNITY, 1.0f)"
        ));
    }

    @Test
    void modMenuTranslationKeysFollowTheLatestLocalizationOverlay() throws IOException {
        String translations = source(
                "net/sevenstars/middleearth/datageneration/content/TranslationEntries.java"
        );
        assertTrue(translations.contains(
                "put(\"modmenu.nameTranslation.\"+ MiddleEarth.MOD_ID"
        ));
        assertFalse(translations.contains("modmenu.nameTranslation.me"));
    }

    @Test
    void biomeBootstrapSerializesAndResetsSharedFeatureLists() throws IOException {
        String biomes = source(
                "net/sevenstars/middleearth/world/biomes/surface/ModBiomes.java"
        );

        assertTrue(biomes.contains(
                "public static synchronized void bootstrap(BootstrapContext<Biome> context)"
        ));
        int bootstrap = biomes.indexOf("public static synchronized void bootstrap(");
        int firstBiome = biomes.indexOf("createAnduinBiome(", bootstrap);
        int initialReset = biomes.indexOf("resetFeatureLists();", bootstrap);
        assertTrue(initialReset > bootstrap && initialReset < firstBiome);
        assertEquals(4, count(biomes, "resetFeatureLists()"));

        int delegatingRegister = biomes.indexOf("public static void registerBiome(");
        int plainRegister = biomes.indexOf(
                "float temperature, boolean precipitation, boolean... removeDefaultOres)",
                delegatingRegister
        );
        int particleRegister = biomes.indexOf(
                "AmbientParticleSettings particleConfig, float temperature",
                plainRegister
        );
        int resetHelper = biomes.indexOf("private static void resetFeatureLists()", particleRegister);
        assertTrue(delegatingRegister >= 0 && plainRegister > delegatingRegister);
        assertTrue(particleRegister > plainRegister && resetHelper > particleRegister);
        assertTrue(biomes.substring(plainRegister, particleRegister).contains("resetFeatureLists();"));
        assertTrue(biomes.substring(particleRegister, resetHelper).contains("resetFeatureLists();"));
    }

    private static String source(String relativePath) throws IOException {
        return Files.readString(MAIN_JAVA.resolve(relativePath));
    }

    private static JsonObject json(String relativePath) throws IOException {
        return JsonParser.parseString(Files.readString(MAIN_RESOURCES.resolve(relativePath)))
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
