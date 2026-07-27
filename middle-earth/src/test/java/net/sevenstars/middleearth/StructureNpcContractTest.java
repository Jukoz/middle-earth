package net.sevenstars.middleearth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class StructureNpcContractTest {
    @Test
    void structurePoolSelectionUsesWorldRandomAndAuthoredWeights() throws IOException {
        MethodCalls selection = readMethodCalls(
                "net/sevenstars/middleearth/resources/datas/structure_manager_datas/"
                        + "SpawnNestNodeData.class",
                "getRandomPool"
        );
        assertTrue(selection.hasCall(
                "net/sevenstars/middleearth/resources/datas/structure_manager_datas/"
                        + "StructureSpawnNestPool",
                "getWeight"
        ));
        assertTrue(selection.hasCall(
                "net/sevenstars/middleearth/resources/datas/structure_manager_datas/"
                        + "SpawnNestNodeData",
                "nextLong"
        ));
        assertFalse(selection.hasOwner("java/util/Random"));
        MethodCalls weightedRandom = readMethodCalls(
                "net/sevenstars/middleearth/resources/datas/structure_manager_datas/"
                        + "SpawnNestNodeData.class",
                "nextLong"
        );
        assertTrue(weightedRandom.hasCall(
                "net/minecraft/util/RandomSource",
                "nextLong"
        ));

        MethodCalls amount = readMethodCalls(
                "net/sevenstars/middleearth/resources/datas/structure_manager_datas/"
                        + "StructureSpawnNestPool.class",
                "getEntityAmount"
        );
        assertTrue(amount.descriptor.contains("Lnet/minecraft/util/RandomSource;"));
        assertFalse(amount.hasOwner("java/util/Random"));
    }

    @Test
    void naturalNpcPolicyAndScuttlerPolicyAreEvaluatedOnce() throws IOException {
        MethodCalls npcSelection = readMethodCalls(
                "net/sevenstars/middleearth/resources/datas/biome_events/"
                        + "BiomeEventData.class",
                "findNpcData"
        );
        assertTrue(npcSelection.hasCall(
                "net/sevenstars/middleearth/resources/datas/biome_events/data/"
                        + "WildSpawnEventData",
                "isDiscarded"
        ));

        MethodCalls scuttler = readMethodCalls(
                "net/sevenstars/middleearth/entity/spider/scuttler/"
                        + "ShelobiteScuttlerEntity.class",
                "canSpawn"
        );
        assertFalse(scuttler.hasOwner(
                "net/sevenstars/middleearth/resources/datas/biome_events/"
                        + "BiomeEventDataLookup"
        ));
    }

    @Test
    void disabledLegacySpawnerExitsBeforeWorldQueries() throws IOException {
        MethodCalls tick = readMethodCalls(
                "net/sevenstars/middleearth/world/spawners/SpawnerNPCs.class",
                "tick"
        );
        int guard = tick.indexOf(
                "net/sevenstars/middleearth/world/spawners/ModEntitySpawning",
                "hasSpawns"
        );
        int players = tick.indexOf("net/minecraft/server/level/ServerLevel", "players");
        assertTrue(guard >= 0);
        assertTrue(players < 0 || guard < players);
    }

    @Test
    void structureSpawnsUsePlacementAndCollisionChecks() throws IOException {
        MethodCalls spawn = readMethodCalls(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "StructureManagerService.class",
                "spawnEntity"
        );
        assertTrue(spawn.hasCall(
                "net/minecraft/world/entity/SpawnPlacements",
                "isSpawnPositionOk"
        ));
        assertTrue(spawn.hasCall(
                "net/minecraft/world/entity/Mob",
                "checkSpawnObstruction"
        ));
        assertTrue(spawn.hasCall(
                "net/minecraft/server/level/ServerLevel",
                "addFreshEntity"
        ));
        int bind = spawn.indexOf(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "StructureManagerService",
                "bindManagedEntity"
        );
        int add = spawn.indexOf(
                "net/minecraft/server/level/ServerLevel",
                "addFreshEntity"
        );
        assertTrue(bind >= 0 && bind < add,
                "The manager binding must exist before the entity join event fires");
    }

    @Test
    void managedEntityLifecyclePersistsAcrossChunkUnloadsWithoutResurrection()
            throws IOException {
        MethodCalls join = readMethodCalls(
                "net/sevenstars/middleearth/event/NeoForgeCommonEvents$GameBus.class",
                "trackManagedEntityJoin"
        );
        assertTrue(join.hasCall(
                "net/neoforged/neoforge/event/entity/EntityJoinLevelEvent",
                "setCanceled"
        ));
        assertFalse(join.hasCall("net/minecraft/world/entity/Entity", "discard"));

        MethodCalls wellness = readMethodCalls(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "SpawnNestManager.class",
                "doWellnessCheck"
        );
        assertTrue(wellness.hasCall(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "StructureManagedEntityData",
                "markDead"
        ));

        MethodCalls clear = readMethodCalls(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "StructureManagerService.class",
                "clearBoundManager"
        );
        assertTrue(clear.hasCall(
                "net/sevenstars/middleearth/entity/npcs/NpcEntity",
                "clearStructureManager"
        ));
    }

    @Test
    void activeManagerCannotChangeIdentityWithSubscribedNests() throws IOException {
        MethodCalls update = readMethodCalls(
                "net/sevenstars/middleearth/block/special/structureManager/"
                        + "StructureManagerBlockEntity.class",
                "updateData"
        );
        int guard = update.indexOf(
                "net/sevenstars/middleearth/block/special/structureManager/"
                        + "StructureManagerBlockEntity",
                "canChangeStructureManagerId"
        );
        int unregister = update.indexOf(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "StructureManagerService",
                "unregister"
        );
        int resolve = update.indexOf(
                "net/sevenstars/middleearth/block/special/structureManager/"
                        + "StructureManagerBlockEntity",
                "resolveManagerData"
        );
        assertTrue(guard >= 0 && guard < unregister);
        assertTrue(unregister >= 0 && unregister < resolve);

        MethodCalls identityGuard = readMethodCalls(
                "net/sevenstars/middleearth/block/special/structureManager/"
                        + "StructureManagerBlockEntity.class",
                "canChangeStructureManagerId"
        );
        assertTrue(identityGuard.hasCall(
                "net/sevenstars/middleearth/block/special/structureManager/features/"
                        + "StructureNestList",
                "getManagers"
        ));
        assertTrue(identityGuard.hasCall("java/util/List", "isEmpty"));
    }

    @Test
    void populationDistancesAreTreatedAsRadii() throws IOException {
        String source = Files.readString(Path.of(
                "src/main/java/net/sevenstars/middleearth/resources/datas/"
                        + "biome_events/data/SpawnEventDataUtil.java"
        ));
        assertTrue(source.contains("sameEntityDistance * 2.0D"));
        assertTrue(source.contains("sameNpcTypeDistance * 2.0D"));
    }

    @Test
    void biomeSpawnCategoriesFollowEntityTypeRegistrations() throws IOException {
        MethodCalls addSpawn = readMethodCalls(
                "net/sevenstars/middleearth/world/spawners/"
                        + "ModSpawnSettingsBuilder.class",
                "addSpawn"
        );
        assertTrue(addSpawn.hasCall(
                "net/minecraft/world/entity/EntityType",
                "getCategory"
        ));
        assertTrue(addSpawn.hasCall(
                "net/minecraft/world/level/biome/MobSpawnSettings$Builder",
                "addSpawn"
        ));

        for (String method : List.of(
                "addOceanAnimals",
                "addMirkwoodSpider",
                "addRareMirkwoodSpider",
                "addUncommonBats"
        )) {
            MethodCalls methodCalls = readMethodCalls(
                    "net/sevenstars/middleearth/world/spawners/"
                            + "ModSpawnSettingsBuilder.class",
                    method
            );
            assertTrue(methodCalls.hasCall(
                    "net/sevenstars/middleearth/world/spawners/"
                            + "ModSpawnSettingsBuilder",
                    "addSpawn"
            ));
        }

        MethodCalls snowTrolls = readMethodCalls(
                "net/sevenstars/middleearth/world/biomes/caves/"
                        + "ModCaveBiomeFeatures.class",
                "addSnowTrolls"
        );
        assertTrue(snowTrolls.hasCall(
                "net/sevenstars/middleearth/world/spawners/"
                        + "ModSpawnSettingsBuilder",
                "addSpawn"
        ));

        String entities = Files.readString(Path.of(
                "src/main/java/net/sevenstars/middleearth/entity/EntitiesME.java"
        ));
        assertTrue(entities.contains(
                "EntityType.Builder.of(SnowTrollEntity::new, MobCategory.MONSTER)"
        ));
    }

    private static MethodCalls readMethodCalls(String resource, String methodName)
            throws IOException {
        InputStream stream = StructureNpcContractTest.class
                .getClassLoader()
                .getResourceAsStream(resource);
        assertNotNull(stream, "Missing compiled class " + resource);
        try (stream) {
            MethodCalls result = new MethodCalls();
            new ClassReader(stream).accept(new ClassVisitor(Opcodes.ASM9) {
                @Override
                public MethodVisitor visitMethod(
                        int access,
                        String name,
                        String descriptor,
                        String signature,
                        String[] exceptions
                ) {
                    if (!name.equals(methodName)) {
                        return null;
                    }
                    result.descriptor = descriptor;
                    return new MethodVisitor(Opcodes.ASM9) {
                        @Override
                        public void visitMethodInsn(
                                int opcode,
                                String owner,
                                String name,
                                String descriptor,
                                boolean isInterface
                        ) {
                            result.calls.add(new Call(owner, name));
                        }
                    };
                }
            }, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            return result;
        }
    }

    private record Call(String owner, String name) {
    }

    private static final class MethodCalls {
        private String descriptor = "";
        private final List<Call> calls = new ArrayList<>();

        private boolean hasCall(String owner, String name) {
            return indexOf(owner, name) >= 0;
        }

        private boolean hasOwner(String owner) {
            return calls.stream().anyMatch(call -> call.owner().equals(owner));
        }

        private int indexOf(String owner, String name) {
            for (int index = 0; index < calls.size(); index++) {
                Call call = calls.get(index);
                if (call.owner().equals(owner) && call.name().equals(name)) {
                    return index;
                }
            }
            return -1;
        }
    }
}
