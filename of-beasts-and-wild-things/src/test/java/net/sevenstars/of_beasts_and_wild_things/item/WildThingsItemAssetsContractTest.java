package net.sevenstars.of_beasts_and_wild_things.item;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WildThingsItemAssetsContractTest {
    private static final String NAMESPACE = "wild-things";
    private static final Path RESOURCES = Path.of("src/main/resources/assets", NAMESPACE);
    private static final Path GENERATED = Path.of("src/main/generated/assets", NAMESPACE);
    private static final List<String> ITEM_IDS = List.of(
            "bird_nest",
            "raw_venison",
            "cooked_venison",
            "raw_poultry",
            "cooked_poultry",
            "raw_swan",
            "cooked_swan",
            "swan_feather",
            "swan_egg",
            "deer_spawn_egg",
            "swan_spawn_egg",
            "pheasant_spawn_egg",
            "snail_spawn_egg"
    );

    @Test
    void everyRegisteredCreativeItemHasACompleteLegacyAssetGraph() throws IOException {
        for (String itemId : ITEM_IDS) {
            Path modelPath = asset("models/item/" + itemId + ".json");
            assertTrue(Files.isRegularFile(modelPath), "Missing item model for " + itemId);

            JsonObject model = JsonParser.parseString(Files.readString(modelPath)).getAsJsonObject();
            assertEquals("minecraft:item/generated", model.get("parent").getAsString(), itemId);
            assertEquals(
                    NAMESPACE + ":item/" + itemId,
                    model.getAsJsonObject("textures").get("layer0").getAsString(),
                    itemId
            );

            Path texturePath = asset("textures/item/" + itemId + ".png");
            assertTrue(Files.isRegularFile(texturePath), "Missing item texture for " + itemId);
            BufferedImage texture = ImageIO.read(texturePath.toFile());
            assertNotNull(texture, "Invalid PNG for " + itemId);
            assertEquals(16, texture.getWidth(), itemId);
            assertEquals(16, texture.getHeight(), itemId);
        }
    }

    @Test
    void cleanBuildsGenerateDatagenAssetsBeforeTestingAndPackaging() throws IOException {
        String build = Files.readString(Path.of("build.gradle"));
        assertTaskDependsOnRunData(build, "tasks.named('test')");
        assertTaskDependsOnRunData(build, "tasks.named('jar')");
    }

    private static Path asset(String relativePath) {
        Path resource = RESOURCES.resolve(relativePath);
        return Files.isRegularFile(resource) ? resource : GENERATED.resolve(relativePath);
    }

    private static void assertTaskDependsOnRunData(String build, String taskDeclaration) {
        int taskStart = build.indexOf(taskDeclaration);
        assertTrue(taskStart >= 0, "Missing " + taskDeclaration);
        int taskEnd = build.indexOf("\n}", taskStart);
        assertTrue(taskEnd > taskStart, "Unterminated " + taskDeclaration);
        String task = build.substring(taskStart, taskEnd);
        assertTrue(task.contains("dependsOn tasks.named('runData')"), taskDeclaration);
    }
}
