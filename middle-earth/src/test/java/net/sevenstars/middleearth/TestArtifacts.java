package net.sevenstars.middleearth;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class TestArtifacts {
    private TestArtifacts() {
    }

    static Path playerJar() {
        String configuredJar = System.getProperty("middleearth.playerJar");
        assertNotNull(configuredJar, "Run tests with the module's Gradle test task");
        Path jar = Path.of(configuredJar);
        assertTrue(Files.isRegularFile(jar), "Missing current build artifact " + jar);
        return jar;
    }
}
