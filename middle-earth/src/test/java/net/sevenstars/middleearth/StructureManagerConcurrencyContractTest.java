package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class StructureManagerConcurrencyContractTest {
    private static final Path MAIN_JAVA = Path.of("src/main/java");

    @Test
    void structureManagerIndexUsesIdentitySafeSpatialCollections()
            throws IOException {
        String service = source(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "StructureManagerService.java"
        );
        String index = source(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "SpatialIdentityIndex.java"
        );

        assertTrue(service.contains("new ConcurrentHashMap<>()"));
        assertTrue(service.contains("new SpatialIdentityIndex<>()"));
        assertTrue(index.contains("static final int CELL_SIZE = 64"));
        assertTrue(index.contains("registration.owner() == owner"));
        assertTrue(index.contains(
                "registrations.remove(registration.position(), registration)"
        ));
        assertFalse(service.contains("new WeakHashMap<>()"));
        assertFalse(service.contains("new HashSet<>()"));
        assertFalse(service.contains("Iterator<BlockPos>"));
        assertFalse(service.contains("iterator.remove()"));
    }

    @Test
    void naturalSpawnClearanceNeverTouchesBlockEntities() throws IOException {
        String service = source(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "StructureManagerService.java"
        );
        int methodStart = service.indexOf("public static boolean isClose(");
        int methodEnd = service.indexOf(
                "public static StructureManagerBlockEntity getClosest(",
                methodStart
        );
        String method = service.substring(methodStart, methodEnd);

        assertTrue(method.contains(
                "pos.getX(), pos.getY(), pos.getZ(), radius"
        ));
        assertFalse(method.contains("getBlockEntity"));
    }

    @Test
    void levelAndServerShutdownClearTheRuntimeIndex() throws IOException {
        String events = source(
                "net/sevenstars/middleearth/event/NeoForgeCommonEvents.java"
        );

        assertTrue(events.contains("LevelEvent.Unload"));
        assertTrue(events.contains("StructureManagerService.clear(level)"));
        assertTrue(events.contains("ServerStoppedEvent"));
        assertTrue(events.contains("StructureManagerService.clearAll()"));
    }

    private static String source(String relativePath) throws IOException {
        return Files.readString(MAIN_JAVA.resolve(relativePath));
    }
}
