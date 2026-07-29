package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class NpcRenderCompatibilityContractTest {
    private static final Path MAIN_JAVA = Path.of("src/main/java");

    @Test
    void acceleratedRenderingUsesItsEntityBlacklistWithoutAHardDependency() throws IOException {
        String compatibility = source(
                "net/sevenstars/middleearth/entity/npcs/renderer/NpcRenderCompatibility.java"
        );

        assertTrue(compatibility.contains("InterModEnqueueEvent"));
        assertTrue(compatibility.contains("value = Dist.CLIENT"));
        assertTrue(compatibility.contains("bus = EventBusSubscriber.Bus.MOD"));
        assertTrue(compatibility.contains("ModList.get().isLoaded"));
        assertTrue(compatibility.contains("\"acceleratedrendering\""));
        assertTrue(compatibility.contains("\"entity_type_blacklist\""));
        assertTrue(compatibility.contains("() -> EntitiesME.NPC"));
        assertFalse(compatibility.contains("com.github.argon4w"));
    }

    @Test
    void modeledNpcFeaturesMatchTheUpstreamEffectiveArmorLod() throws IOException {
        String configs = source(
                "net/sevenstars/middleearth/config/ModClientConfigs.java"
        );
        assertTrue(configs.contains(
                "LOD_NPC_FEATURES_DISTANCE = LOD_NPC_ARMOR_DISTANCE;"
        ));

        for (String feature : new String[] {
                "ear/EarFeatureRenderer.java",
                "feet/FeetFeatureRenderer.java",
                "hair/HairFeatureRenderer.java",
                "nose/NoseFeatureRenderer.java"
        }) {
            String renderer = source(
                    "net/sevenstars/middleearth/entity/npcs/renderer/features/" + feature
            );
            assertTrue(renderer.contains(
                    "getLOD(entity.position()) > ModClientConfigs.LOD_NPC_FEATURES_DISTANCE"
            ));
        }
    }

    private static String source(String relativePath) throws IOException {
        return Files.readString(MAIN_JAVA.resolve(relativePath));
    }
}
