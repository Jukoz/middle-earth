package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class PlacedFeatureBiomeFilterContractTest {
    @Test
    void wildWheatUsesSeparateBiomeAndJigsawPlacements() throws IOException {
        String biomeRegistration = registrationStatement(
                "src/main/java/net/sevenstars/middleearth/world/features/vegetation/"
                        + "ModVegetationPlacedFeatures.java",
                "PlacementUtils.register(featureRegisterable, FIELD_WILD_WHEAT"
        );
        String structureRegistration = registrationStatement(
                "src/main/java/net/sevenstars/middleearth/world/features/vegetation/"
                        + "ModVegetationPlacedFeatures.java",
                "PlacementUtils.register(featureRegisterable, FIELD_WILD_WHEAT_STRUCTURE"
        );

        assertTrue(biomeRegistration.contains("BiomeFilter.biome()"));
        assertFalse(structureRegistration.contains("BiomeFilter.biome()"));

        String pool = Files.readString(Path.of(
                "src/main/resources/data/middle-earth/worldgen/template_pool/wild_wheat.json"
        ));
        String alternatePool = Files.readString(Path.of(
                "src/main/resources/data/middle-earth/worldgen/template_pool/wild_wheat_1.json"
        ));
        assertTrue(pool.contains("middle-earth:field_wild_wheat_structure"));
        assertTrue(alternatePool.contains("middle-earth:field_wild_wheat_structure"));
    }

    @Test
    void directlyReferencedFallenBirchRestrictsPlacementToOwningBiome() throws IOException {
        String registration = registrationStatement(
                "src/main/java/net/sevenstars/middleearth/world/features/tree/"
                        + "ModTreePlacedFeatures.java",
                "register(context, FALLEN_BIRCH_PLACED_TREE_KEY"
        );

        assertTrue(registration.endsWith("BiomeFilter.biome()));"));
    }

    private static String registrationStatement(String sourcePath, String startMarker)
            throws IOException {
        String source = Files.readString(Path.of(sourcePath));
        int start = source.indexOf(startMarker);
        assertTrue(start >= 0, "Missing registration marker " + startMarker);
        int end = source.indexOf(';', start);
        assertTrue(end >= 0, "Missing registration terminator for " + startMarker);
        return source.substring(start, end + 1).trim();
    }
}
