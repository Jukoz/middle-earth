package net.sevenstars.middleearth;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PackagedDataDeterminismContractTest {
    private static final Path BUILD_LIBS = Path.of("build/libs");
    private static final List<String> NPC_RANK_ORDER = List.of(
            "CIVILIAN", "MILITIA", "SOLDIER", "KNIGHT", "VETERAN", "LEADER"
    );

    @Test
    void factionNpcRanksUseCanonicalSerializationOrder() throws IOException {
        int checkedFactions = 0;
        try (ZipFile playerJar = new ZipFile(playerJar().toFile())) {
            Enumeration<? extends ZipEntry> entries = playerJar.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String path = entry.getName();
                if (entry.isDirectory()
                        || !path.startsWith("data/middle-earth/middle-earth/faction/")
                        || !path.endsWith(".json")) {
                    continue;
                }

                JsonObject faction = readJson(playerJar, entry);
                if (!faction.has("npcs")) {
                    continue;
                }
                checkedFactions++;
                JsonArray ranks = faction.getAsJsonObject("npcs").getAsJsonArray("ranks");
                int previousIndex = -1;
                for (int index = 0; index < ranks.size(); index++) {
                    String rank = ranks.get(index).getAsJsonObject().get("rank").getAsString();
                    int canonicalIndex = NPC_RANK_ORDER.indexOf(rank);
                    assertTrue(canonicalIndex >= 0, path + " contains unknown rank " + rank);
                    assertTrue(canonicalIndex > previousIndex, path + " has noncanonical rank order");
                    previousIndex = canonicalIndex;
                }
            }
        }
        assertTrue(checkedFactions > 0, "Expected packaged faction NPC pools");
    }

    @Test
    void packagedHorseArmorTexturesUseTheMinecraft1211Paths() throws IOException {
        try (ZipFile playerJar = new ZipFile(playerJar().toFile())) {
            for (String armor : List.of(
                    "gondorian_horse_armor",
                    "rohirric_horse_armor",
                    "dalish_horse_armor",
                    "lorien_horse_armor"
            )) {
                String expected = "assets/middle-earth/textures/entity/horse/armor/horse_armor_"
                        + armor + ".png";
                String incompatible = "assets/middle-earth/textures/entity/equipment/horse_body/"
                        + armor + ".png";
                assertNotNull(playerJar.getEntry(expected), "Missing " + expected);
                assertNull(playerJar.getEntry(incompatible), "Found incompatible " + incompatible);
            }
        }
    }

    private static JsonObject readJson(ZipFile playerJar, ZipEntry entry) throws IOException {
        try (InputStreamReader reader = new InputStreamReader(
                playerJar.getInputStream(entry), StandardCharsets.UTF_8
        )) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        }
    }

    private static Path playerJar() throws IOException {
        try (var files = Files.list(BUILD_LIBS)) {
            List<Path> playerJars = files
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().startsWith("Middle-earth-"))
                    .filter(path -> path.getFileName().toString().endsWith(".jar"))
                    .filter(path -> !path.getFileName().toString().endsWith("-sources.jar"))
                    .toList();
            assertEquals(1, playerJars.size(), "Expected one packaged Middle-earth player JAR");
            return playerJars.getFirst();
        }
    }
}
