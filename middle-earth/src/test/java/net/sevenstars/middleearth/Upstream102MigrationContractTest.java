package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class Upstream102MigrationContractTest {
    private static final Path MODULE = Path.of(".");
    private static final Path JAVA = Path.of("src/main/java/net/sevenstars/middleearth");
    private static final Path RESOURCES = Path.of("src/main/resources");

    @Test
    void allEmbeddedModulesPublishTheSameBackportVersion() throws IOException {
        String version = "1.0.2-1.21.1-beta-backport.1";
        for (Path properties : List.of(
                MODULE.resolve("gradle.properties"),
                MODULE.resolve("../sevenstars-api/gradle.properties"),
                MODULE.resolve("../of-beasts-and-wild-things/gradle.properties")
        )) {
            assertTrue(Files.readString(properties).contains("mod_version=" + version), properties.toString());
        }

        assertTrue(source("MiddleEarth.java").contains("MOD_VERSION = \"" + version + "\""));
        assertTrue(Files.readString(Path.of("../sevenstars-api/src/main/java/net/sevenstars/api/SevenStarsApi.java"))
                .contains("MOD_VERSION = \"" + version + "\""));
        assertTrue(Files.readString(Path.of(
                        "../of-beasts-and-wild-things/src/main/java/net/sevenstars/of_beasts_and_wild_things/OfBeastsAndWildThings.java"))
                .contains("MOD_VERSION = \"" + version + "\""));
    }

    @Test
    void decorativeContentIsRegisteredWithoutInventingBlockEntityState() throws IOException {
        String blocks = source("block/registration/ModDecorativeBlocks.java");
        for (String id : List.of(
                "old_skull", "skeleton", "small_bronze_chandelier", "bronze_chandelier",
                "small_chandelier", "chandelier"
        )) {
            assertTrue(blocks.contains("\"" + id + "\""), id);
        }

        String entities = source("block/registration/ModBlockEntities.java");
        assertTrue(entities.contains("register(\"old_skull\""));
        String oldSkull = source("block/special/skull/OldSkullBlockEntity.java");
        assertFalse(oldSkull.contains("tick("));
        assertFalse(oldSkull.contains("saveAdditional("));
        assertFalse(oldSkull.contains("getUpdatePacket("));
        assertFalse(oldSkull.contains("getUpdateTag("));
    }

    @Test
    void wearableAndShieldCompatibilityHooksAreWired() throws IOException {
        String mixins = Files.readString(RESOURCES.resolve("middle-earth.mixins.json"));
        assertTrue(mixins.contains("BeehiveBlockMixin"));

        String beehive = source("mixin/BeehiveBlockMixin.java");
        assertTrue(beehive.contains("EquipmentItemsME.BEEKEEPER_MASK"));
        assertTrue(beehive.contains("CampfireBlock;isSmokeyPos"));

        String weapons = source("item/WeaponItemsME.java").replace("\r\n", "\n");
        assertTrue(weapons.contains(
                "registerShield(\"dol_guldur_shield\",\n"
                        + "            (settings) -> new CustomShieldItem(ShieldTypesME.MEDIUM_SHIELD"
        ));
        assertTrue(weapons.contains(
                "registerShield(\"dol_guldur_pavise\",\n"
                        + "            (settings) -> new CustomShieldItem(ShieldTypesME.HEAVY_SHIELD"
        ));
    }

    @Test
    void wildSpawnFixesStayServerBoundWithoutGlobalCapOrChunkCache() throws IOException {
        String lookup = source("resources/datas/biome_events/BiomeEventDataLookup.java");
        String data = source("resources/datas/biome_events/BiomeEventData.java");
        String util = source("registries/content/biomevents/BiomeEventRegistryUtil.java");
        String config = source("config/ModServerConfigs.java");

        assertTrue(lookup.contains("ServerLevel world"));
        assertTrue(data.contains("ServerLevel world"));
        assertTrue(data.contains("spawningData.broadcastMessage(world, pos)"));
        assertTrue(util.contains("List.copyOf(BIOME_ENTRIES)"));
        assertTrue(util.contains("CURRENT_REGISTRATIONS.remove()"));

        for (String source : List.of(lookup, data, util, config)) {
            assertFalse(source.contains("globalMobCap"));
            assertFalse(source.contains("GLOBAL_MOB_CAP"));
            assertFalse(source.contains("disabledChunkSpawningCache"));
            assertFalse(source.contains("HashMap<ChunkPos"));
        }
        assertFalse(Files.exists(JAVA.resolve("world/spawners/MiddleEarthMobCapTracker.java")));

        String shelobite = source("entity/spider/scuttler/ShelobiteScuttlerEntity.java");
        assertTrue(shelobite.contains(
                "canSpawn(EntityType<ShelobiteScuttlerEntity> type, ServerLevelAccessor"
        ));
        assertFalse(shelobite.contains("EntityType<NpcEntity> type, LevelAccessor"));

        String events = source("event/NeoForgeCommonEvents.java");
        assertTrue(events.contains("@SubscribeEvent(priority = EventPriority.LOWEST)"));
        assertTrue(events.contains("!event.getPlacementCheckResult()"));
    }

    @Test
    void npcModelInventoryIsPublishedFromABootstrapLocalSnapshot() throws IOException {
        String registry = source("registries/content/npctypes/NpcRegistry.java");
        assertTrue(registry.contains("volatile List<ResourceKey<NpcType>> allNpcTypes = List.of()"));
        assertTrue(registry.contains("List<ResourceKey<NpcType>> registeredNpcTypes = new ArrayList<>()"));
        assertTrue(registry.contains("allNpcTypes = List.copyOf(registeredNpcTypes)"));
        assertFalse(registry.contains("allNpcTypes.add("));
    }

    @Test
    void adaptedResourcesUseThe1211ModelLayout() {
        for (String model : List.of(
                "assets/middle-earth/models/item/old_skull.json",
                "assets/middle-earth/models/item/skeleton.json",
                "assets/middle-earth/blockstates/chandelier.json",
                "assets/middle-earth/blockstates/skeleton.json"
        )) {
            assertTrue(Files.isRegularFile(RESOURCES.resolve(model)), model);
        }
        assertFalse(Files.isDirectory(RESOURCES.resolve("assets/middle-earth/items")));
    }

    private static String source(String path) throws IOException {
        return Files.readString(JAVA.resolve(path));
    }
}
