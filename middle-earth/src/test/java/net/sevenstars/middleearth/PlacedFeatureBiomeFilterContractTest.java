package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class PlacedFeatureBiomeFilterContractTest {
    @Test
    void wildWheatFieldRestrictsPlacementToOwningBiomes() throws IOException {
        String registration = registrationStatement(
                "src/main/java/net/sevenstars/middleearth/world/features/vegetation/"
                        + "ModVegetationPlacedFeatures.java",
                "PlacementUtils.register(featureRegisterable, FIELD_WILD_WHEAT"
        );

        assertTrue(registration.endsWith("BiomeFilter.biome());"));
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
