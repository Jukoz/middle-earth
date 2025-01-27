package net.sevenstars.middleearth.world.spawners;

import net.sevenstars.middleearth.entity.NpcEntity;
import net.sevenstars.middleearth.world.dimension.ModDimensions;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.spawner.SpecialSpawner;

import java.util.ArrayList;
import java.util.List;

public class SpawnerNPCs implements SpecialSpawner {
    private static final int SPAWN_COUNT_CAP = 32;
    private static final int SPAWN_DISTANCE = 32;
    private static final int SPAWN_RAND = 8;
    private static final int MAX_SPAWN_RAD = SPAWN_DISTANCE + SPAWN_RAND + 8;
    private static final int BASE_COOLDOWN = 30;
    private static final int COOLDOWN_RANGE = 8;
    private int cooldown = BASE_COOLDOWN + COOLDOWN_RANGE;

    @Override
    public int spawn(ServerWorld world, boolean spawnMonsters, boolean spawnAnimals) {
        Random random = world.random;
        --this.cooldown;
        if (this.cooldown > 0) {
            return 0;
        }

        this.cooldown += (BASE_COOLDOWN + random.nextInt(COOLDOWN_RANGE)) * 20;

        int i = 0;
        for (PlayerEntity playerEntity : world.getPlayers()) {
            BlockState blockState;
            if (playerEntity.isSpectator()) continue;
            BlockPos blockPos = playerEntity.getBlockPos();
            BlockPos targetBlockPos = new BlockPos(blockPos);
            LocalDifficulty localDifficulty = world.getLocalDifficulty(blockPos);

            Vec3d offset = new Vec3d(MAX_SPAWN_RAD, 0, MAX_SPAWN_RAD);
            Vec3d pos1 = blockPos.toCenterPos().add(offset).add(0, 321 - playerEntity.getPos().y, 0);
            Vec3d pos2 = blockPos.toCenterPos().subtract(offset).add(0, -63 - playerEntity.getPos().y, 0);
            int size = world.getEntitiesByClass(NpcEntity.class, new Box(pos1, pos2), (entity) -> true).size();
            if(size <= SPAWN_COUNT_CAP) {
                float randomAngle = random.nextInt(360);
                int distance = SPAWN_DISTANCE + random.nextInt(SPAWN_RAND);

                int x = targetBlockPos.getX() + (int)(distance * Math.cos(randomAngle));
                int z = targetBlockPos.getZ() + (int)(distance * Math.sin(randomAngle));
                targetBlockPos = new BlockPos(x, ModDimensions.getDimensionHeight(x, z).y, z);

                if(world.getBiome(targetBlockPos).getKey().isEmpty()) continue;
                RegistryKey<Biome> biomeRegistryKey = world.getBiome(targetBlockPos).getKey().get();

                EntityData entityData = null;
                List<EntitySpawningSettings> biomeSpawnSettings = ModEntitySpawning.getSpawnsAt(biomeRegistryKey);
                if(biomeSpawnSettings == null) continue;
                ArrayList<EntitySpawningSettings> spawningSettings = new ArrayList<>(biomeSpawnSettings);
                if(world.isDay()) {
                    spawningSettings.removeIf(EntitySpawningSettings::isNightOnly);
                }
                if(spawningSettings.size() == 0) continue;
                int totalWeight = 0;
                for(EntitySpawningSettings settings : spawningSettings) {
                    totalWeight += settings.getWeight();
                }
                int nextWeight = random.nextInt(totalWeight) + 1;
                totalWeight = 0;
                EntitySpawningSettings entitySpawningSettings = spawningSettings.get(0);
                for(EntitySpawningSettings settings : spawningSettings) {
                    if(totalWeight + settings.getWeight() >= nextWeight) {
                        entitySpawningSettings = settings;
                        break;
                    }
                    totalWeight += settings.getWeight();
                }
                int randomCount = random.nextInt(1 + entitySpawningSettings.getMaxCount() - entitySpawningSettings.getMinCount());
                int entityCount = entitySpawningSettings.getMinCount() + randomCount;

                blockState = world.getBlockState(new BlockPos(x, targetBlockPos.getY() - 1, z));
                if(!canSpawnAt(world.getChunkAsView(targetBlockPos.getX() / 16, targetBlockPos.getZ() / 16),
                        targetBlockPos.subtract(new Vec3i(0, 1, 0)), entitySpawningSettings.getEntity(), blockState)) continue;

                for (int m = 0; m < entityCount; ++m) {
                    PathAwareEntity entity = (PathAwareEntity) entitySpawningSettings.getEntity().create(world);
                    if (entity == null) continue;
                    entity.refreshPositionAndAngles(targetBlockPos, 0.0f, 0.0f);
                    entityData = entity.initialize(world, localDifficulty, SpawnReason.NATURAL, entityData);
                    world.spawnEntityAndPassengers(entity);
                    ++i;
                }
            }
        }
        return i;
    }

    public static int getHighestYAtXZ(World world, int x, int z) {
        return world.getChunk(new BlockPos(x, 0, z)).sampleHeightmap(Heightmap.Type.WORLD_SURFACE_WG, x, z);
    }

    private static boolean canSpawnAt(BlockView world, BlockPos pos, EntityType type, BlockState blockState) {
        if(!blockState.allowsSpawning(world, pos, type)) return false;
        return (!blockState.isOf(Blocks.WATER) && !blockState.isOf(Blocks.LAVA) && !blockState.isIn(BlockTags.LOGS));
    }
}

