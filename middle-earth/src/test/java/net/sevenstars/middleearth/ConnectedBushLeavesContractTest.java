package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ConnectedBushLeavesContractTest {
    private static final Path TREE_FEATURES = Path.of(
            "src/main/java/net/sevenstars/middleearth/world/features/tree/"
                    + "ModTreeConfiguredFeatures.java"
    );

    @Test
    void everyCustomOvalOrBushTreeRemovesDisconnectedGeneratedLeaves()
            throws IOException {
        String source = Files.readString(TREE_FEATURES);
        String[] keys = {
                "MALLORN_BUSH_KEY",
                "MALLORN_FLOWERING_BUSH_KEY",
                "OAK_BUSH_TREE_KEY",
                "DRY_PINE_BUSH_TREE_KEY",
                "SPRUCE_BUSH_TREE_KEY",
                "WHITE_SPRUCE_BUSH_TREE_KEY"
        };

        for (String key : keys) {
            int start = source.indexOf("register(context, " + key);
            assertTrue(start >= 0, "Missing configured feature " + key);
            int end = source.indexOf(".build());", start);
            assertTrue(end >= 0, "Missing builder terminator for " + key);
            String registration = source.substring(start, end);
            assertTrue(registration.contains(
                    ".decorators(connectedLeaves("
            ), "Missing connected-leaves decorator for " + key);
        }

        assertEquals(
                keys.length,
                source.split("new BushFoliagePlacer", -1).length - 1,
                "New bush configurations must opt into connectivity cleanup"
        );
        assertEquals(
                25,
                source.split("new OvalFoliagePlacer", -1).length - 1,
                "Unexpected number of custom oval foliage placers"
        );
        assertEquals(
                8,
                source.split("\\n\\s*mapleFoliage,", -1).length - 1,
                "Every maple variant must use the shared oval foliage placer"
        );
        assertEquals(
                38,
                source.split("\\.decorators\\(connectedLeaves\\(", -1).length - 1,
                "Every custom oval or bush tree must prune unsupported leaves"
        );
    }

    @Test
    void decoratorUsesVanillaLeafDistanceAgainstActualWorldState()
            throws IOException {
        String source = Files.readString(Path.of(
                "src/main/java/net/sevenstars/middleearth/world/features/tree/"
                        + "decorators/ConnectedLeavesTreeDecorator.java"
        ));

        assertTrue(source.contains("if (context.leaves().isEmpty())"));
        assertTrue(source.contains("MAX_SUPPORTED_DISTANCE = 6"));
        assertTrue(source.contains("collectNearbyLeaves("));
        assertTrue(source.contains("findSupportedLeaves("));
        assertTrue(source.contains(
                "LeavesBlock.getOptionalDistanceAt(state).isPresent()"
        ));
        assertTrue(source.contains("state.is(BlockTags.LOGS)"));
        assertTrue(source.contains(
                "distance < MAX_SUPPORTED_DISTANCE - 1"
        ));
        assertTrue(source.contains(
                "distance < MAX_SUPPORTED_DISTANCE"
        ));
        assertTrue(source.contains(
                "&& !supportedLeaves.contains(leaf)"
        ));
        assertTrue(source.contains(
                "context.setBlock(leaf, Blocks.AIR.defaultBlockState())"
        ));
        assertTrue(source.contains("leaves.remove()"));
    }
}
