package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void middleEarthWorldgenCompatibilityUsesScheduledFeatureNeighborhoods() throws IOException {
        String mixins = Files.readString(MAIN_RESOURCES.resolve("middle-earth.mixins.json"));
        assertTrue(mixins.contains("\"ChunkGeneratorMixin\""));
        assertTrue(mixins.contains("\"ChunkStepBuilderMixin\""));
        assertFalse(mixins.contains("\"WorldGenRegionMixin\""));
        assertFalse(mixins.contains("ChunkGenerationStepMixin"));
        assertFalse(Files.exists(MAIN_JAVA.resolve(
                "net/sevenstars/middleearth/mixin/WorldGenRegionMixin.java"
        )));

        String generatorMixin = source(
                "net/sevenstars/middleearth/mixin/ChunkGeneratorMixin.java"
        );
        assertTrue(generatorMixin.contains(
                "(Object) this instanceof MiddleEarthChunkGenerator"
        ));
        assertTrue(generatorMixin.contains("MIDDLE_EARTH_BIOME_FEATURE_RADIUS = 4"));

        String chunkStepMixin = source(
                "net/sevenstars/middleearth/mixin/ChunkStepBuilderMixin.java"
        );
        assertTrue(chunkStepMixin.contains("@Mixin(ChunkStep.Builder.class)"));
        assertTrue(chunkStepMixin.contains("this.status != ChunkStatus.FEATURES"));
        assertTrue(chunkStepMixin.contains("this.blockStateWriteRadius != 1"));
        assertTrue(chunkStepMixin.contains(
                ".addRequirement(ChunkStatus.CARVERS, MIDDLE_EARTH_FEATURE_WRITE_RADIUS)"
        ));
        assertTrue(chunkStepMixin.contains(
                ".blockStateWriteRadius(MIDDLE_EARTH_FEATURE_WRITE_RADIUS)"
        ));

        String sableMixins = Files.readString(
                MAIN_RESOURCES.resolve("middle-earth-sable.mixins.json")
        );
        assertTrue(sableMixins.contains("\"required\": false"));
        assertTrue(sableMixins.contains(
                "net.sevenstars.middleearth.mixin.compat.sable.OptionalSableMixinPlugin"
        ));
        String sablePlugin = source(
                "net/sevenstars/middleearth/mixin/compat/sable/OptionalSableMixinPlugin.java"
        );
        assertTrue(sablePlugin.contains("SABLE_EVENTS_CLASS"));
        assertTrue(sablePlugin.contains("getBytecodeProvider().getClassNode"));
        assertTrue(sablePlugin.contains("return List.of(\"SableCommonEventsMixin\")"));
        String sableMixin = source(
                "net/sevenstars/middleearth/mixin/compat/sable/SableCommonEventsMixin.java"
        );
        assertTrue(sableMixin.contains("@Pseudo"));
        assertTrue(sableMixin.contains("level.dimension().equals(ModDimensions.ME_WORLD_KEY)"));
        assertTrue(sableMixin.contains("!level.getServer().isSameThread()"));
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

        String decorativeBlocks = source(
                "net/sevenstars/middleearth/block/registration/ModDecorativeBlocks.java"
        );
        int plateRegistrationsStart = decorativeBlocks.indexOf(
                "public static final Block CERAMIC_PLATE"
        );
        int plateRegistrationsEnd = decorativeBlocks.indexOf(
                "public static final Block TAPPER",
                plateRegistrationsStart
        );
        String plateRegistrations = decorativeBlocks.substring(
                plateRegistrationsStart,
                plateRegistrationsEnd
        );
        assertEquals(
                3,
                plateRegistrations.split(
                        "PlateBlock::new, BlockBehaviour\\.Properties\\.of\\(\\)",
                        -1
                ).length - 1
        );
        assertFalse(plateRegistrations.contains("Properties.ofFullCopy"));
        assertFalse(plateRegistrations.contains("requiresCorrectToolForDrops"));

        String blockEntity = source(
                "net/sevenstars/middleearth/block/special/plate/PlateBlockEntity.java"
        );
        assertFalse(blockEntity.contains("static void tick("));
        assertTrue(blockEntity.contains("public void onLoad()"));
        assertTrue(blockEntity.contains("DataComponents.CONTAINER_LOOT"));
        assertTrue(blockEntity.contains("components.set(DataComponents.CONTAINER_LOOT, null)"));
        assertTrue(blockEntity.contains("serverLevel.scheduleTick"));
        assertTrue(blockEntity.contains("hasPendingLoot()"));
    }

    @Test
    void npcRenderingHasNoNormalWhiteBasePass() throws IOException {
        String renderer = source(
                "net/sevenstars/middleearth/entity/npcs/renderer/NpcEntityRenderer.java"
        );
        assertTrue(renderer.contains("if (bodyVisible || !glowing)"));
        assertTrue(renderer.contains("return RenderType.outline(EMPTY_TEXTURE)"));

        String layers = source(
                "net/sevenstars/middleearth/client/ModTexturedRenderLayers.java"
        );
        assertTrue(layers.contains("RenderType.entityCutoutNoCull(CHARACTER_ATLAS_TEXTURES)"));
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
        assertTrue(goal.contains("isInsideDoorway()"));
        assertTrue(goal.contains("this.doorwayBounds = this.createDoorwayBounds()"));
        assertTrue(goal.contains("npc.beginDoorTraversal()"));
        assertTrue(goal.contains("npc.endDoorTraversal()"));
        assertFalse(goal.contains("this.mob.getNavigation().stop()"));

        String npc = source("net/sevenstars/middleearth/entity/npcs/NpcEntity.java");
        assertTrue(npc.contains(
                "this.goalSelector.addGoal(3, new NpcDoorInteractGoal(this))"
        ));
        assertTrue(npc.contains("groundNavigation.setCanPassDoors(true)"));
        assertTrue(npc.contains("MAX_DOOR_TRAVERSAL_WIDTH = 0.60F"));
        assertTrue(npc.contains("MAX_DOOR_TRAVERSAL_WIDTH / scaledWidth"));
        assertTrue(npc.contains("noBlockCollision"));
        assertTrue(npc.contains("DOOR_TRAVERSAL_DIMENSIONS"));
        assertTrue(npc.contains("onSyncedDataUpdated"));
        assertTrue(npc.contains("KeyStrings.DOOR_TRAVERSAL"));
        assertFalse(npc.contains(
                "this.targetSelector.addGoal(4, new NpcDoorInteractGoal"
        ));
    }

    @Test
    void npcAttackGoalsAreMutuallyExclusive() throws IOException {
        String npc = source("net/sevenstars/middleearth/entity/npcs/NpcEntity.java");
        assertTrue(npc.contains("this.goalSelector.removeGoal(this.crossBowAttackGoal)"));
        assertTrue(npc.contains(
                "} else if (itemStack.is(Items.CROSSBOW) || itemStack.is(ItemTagsME.CROSSBOW)) {"
        ));
    }

    @Test
    void starlightPhialReturnPointPersistsAndUsesServerCapturedPosition() throws IOException {
        String playerData = source(
                "net/sevenstars/middleearth/resources/persistent_datas/PlayerData.java"
        );
        assertTrue(playerData.contains("middle_earth_return_pos"));
        assertTrue(playerData.contains("assignMiddleEarthReturnPos"));
        assertTrue(playerData.contains("nbt.putIntArray"));
        assertTrue(playerData.contains("new BlockPos("));

        String handler = source(
                "net/sevenstars/middleearth/network/handlers/OnboardingServerHandler.java"
        );
        assertTrue(handler.contains(
                "PlayerDataService.setMiddleEarthReturnPos(player, session.origin())"
        ));
        assertTrue(handler.contains("ModDimensions.teleportPlayerToMeReturnPoint"));

        String dimensions = source(
                "net/sevenstars/middleearth/world/dimension/ModDimensions.java"
        );
        assertTrue(dimensions.contains(
                "completeMiddleEarthTeleport("
        ));
        assertTrue(dimensions.contains("safeTarget.get(),\n                        true,"));
        assertTrue(dimensions.contains("level.getWorldBorder().isWithinBounds(standingBounds)"));
        assertTrue(dimensions.contains("level.noBlockCollision(player, standingBounds)"));
        assertTrue(dimensions.contains("findSafeOverworldReturnTarget"));
    }

    @Test
    void willowVinesKeepAuthoredPlacementAndStableNaturalSupport() throws IOException {
        String biomes = source(
                "net/sevenstars/middleearth/world/biomes/surface/ModBiomes.java"
        );
        int methodStart = biomes.indexOf("public static void addPondVegetation");
        int methodEnd = biomes.indexOf("public static void addRivendellVegetation", methodStart);
        String pondVegetation = biomes.substring(methodStart, methodEnd);
        assertTrue(pondVegetation.contains("ModBiomeFeatures.addWillowVines(vegetation)"));
        assertTrue(pondVegetation.contains(
                "GenerationStep.Decoration.TOP_LAYER_MODIFICATION, "
                        + "ModVegetationPlacedFeatures.WILLOW_VINES"
        ));

        String feature = source(
                "net/sevenstars/middleearth/world/features/columns/WillowVinesFeature.java"
        );
        assertFalse(feature.contains("setValue(LeavesBlock.PERSISTENT"));
        assertFalse(feature.contains("world.setBlock(pos.above()"));
    }

    @Test
    void structureCorpusIsCompleteForExternalNbtAudit() throws IOException {
        Path structures = MAIN_RESOURCES.resolve("data/middle-earth/structure");
        try (Stream<Path> paths = Files.walk(structures)) {
            assertEquals(356, paths.filter(file -> file.toString().endsWith(".nbt")).count());
        }
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
