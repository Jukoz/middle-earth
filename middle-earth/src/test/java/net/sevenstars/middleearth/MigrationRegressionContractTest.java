package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class MigrationRegressionContractTest {
    private static final Path MAIN_JAVA = Path.of("src/main/java");
    private static final Path MAIN_RESOURCES = Path.of("src/main/resources");

    @Test
    void middleEarthWorldgenCompatibilityIsDimensionScoped() throws IOException {
        String mixins = Files.readString(MAIN_RESOURCES.resolve("middle-earth.mixins.json"));
        assertTrue(mixins.contains("\"ChunkGeneratorMixin\""));
        assertTrue(mixins.contains("\"WorldGenRegionMixin\""));
        assertFalse(mixins.contains("ChunkGenerationStepMixin"));

        String regionMixin = source(
                "net/sevenstars/middleearth/mixin/WorldGenRegionMixin.java"
        );
        assertTrue(regionMixin.contains("level.dimension().equals(ModDimensions.ME_WORLD_KEY)"));
        assertTrue(regionMixin.contains("step.targetStatus() != ChunkStatus.FEATURES"));
        assertTrue(regionMixin.contains("MIDDLE_EARTH_FEATURE_WRITE_RADIUS = 2"));

        String generatorMixin = source(
                "net/sevenstars/middleearth/mixin/ChunkGeneratorMixin.java"
        );
        assertTrue(generatorMixin.contains(
                "(Object) this instanceof MiddleEarthChunkGenerator"
        ));
        assertTrue(generatorMixin.contains("MIDDLE_EARTH_BIOME_FEATURE_RADIUS = 4"));
    }

    @Test
    void plateInteractionUsesServerAuthorityAndSupportsRetrieval() throws IOException {
        String plate = source(
                "net/sevenstars/middleearth/block/special/plate/PlateBlock.java"
        );
        assertTrue(plate.contains("copyWithCount(1).finishUsingItem(world, player)"));
        assertTrue(plate.contains("private static InteractionResult takeFood"));
        assertTrue(plate.contains("if (!world.isClientSide)"));
        assertTrue(plate.contains("player.addItem(removed)"));
        assertTrue(plate.contains("ItemInteractionResult.sidedSuccess(world.isClientSide)"));

        String blockEntity = source(
                "net/sevenstars/middleearth/block/special/plate/PlateBlockEntity.java"
        );
        assertFalse(blockEntity.contains("static void tick("));
    }

    @Test
    void npcRenderingHasNoNormalWhiteBasePass() throws IOException {
        String renderer = source(
                "net/sevenstars/middleearth/entity/npcs/renderer/NpcEntityRenderer.java"
        );
        assertTrue(renderer.contains("if (!glowing)"));
        assertTrue(renderer.contains("return RenderType.outline(EMPTY_TEXTURE)"));

        String layers = source(
                "net/sevenstars/middleearth/client/ModTexturedRenderLayers.java"
        );
        assertTrue(layers.contains("RenderType.entityCutout(CHARACTER_ATLAS_TEXTURES)"));
        assertFalse(layers.contains("entityCutoutNoCull(CHARACTER_ATLAS_TEXTURES)"));
    }

    @Test
    void npcDoorGoalWaitsForPassageAndHasBoundedRetry() throws IOException {
        String goal = source(
                "net/sevenstars/middleearth/entity/goals/NpcDoorInteractGoal.java"
        );
        assertTrue(goal.contains("extends DoorInteractGoal"));
        assertTrue(goal.contains("super.canContinueToUse()"));
        assertTrue(goal.contains("MAX_OPEN_TICKS"));
        assertTrue(goal.contains("RETRY_COOLDOWN_TICKS"));
        assertTrue(goal.contains("this.mob.getNavigation().stop()"));

        String npc = source("net/sevenstars/middleearth/entity/npcs/NpcEntity.java");
        assertTrue(npc.contains(
                "this.goalSelector.addGoal(3, new NpcDoorInteractGoal(this))"
        ));
        assertFalse(npc.contains(
                "this.targetSelector.addGoal(4, new NpcDoorInteractGoal"
        ));
    }

    @Test
    void gundabadPlateLootExistsAndResourcePathsAreLegal() throws IOException {
        Path loot = MAIN_RESOURCES.resolve(
                "data/middle-earth/loot_table/structures/gundabad/plated_food.json"
        );
        JsonParser.parseString(Files.readString(loot)).getAsJsonObject();

        Path textures = MAIN_RESOURCES.resolve("assets/middle-earth/textures");
        try (Stream<Path> paths = Files.walk(textures)) {
            assertFalse(paths
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .anyMatch(name -> name.chars().anyMatch(Character::isWhitespace)));
        }
    }

    private static String source(String relativePath) throws IOException {
        return Files.readString(MAIN_JAVA.resolve(relativePath));
    }
}
