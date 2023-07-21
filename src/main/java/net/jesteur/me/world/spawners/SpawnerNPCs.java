package net.jesteur.me.world.spawners;

import net.jesteur.me.entity.hobbits.HobbitEntity;
import net.jesteur.me.world.spawners.EntitySpawningSettings;
import net.jesteur.me.world.spawners.ModEntitySpawning;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.spawner.Spawner;

import java.util.List;

public class SpawnerNPCs implements Spawner {
    private static final int SPAWN_DISTANCE = 24;
    private static final int SPAWN_RAND = 8;
    private static final int BASE_COOLDOWN = 8;
    private static final int COOLDOWN_RANGE = 7;
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

            float randomAngle = random.nextInt(360);
            int distance = SPAWN_DISTANCE + random.nextInt(SPAWN_RAND);

            int x = targetBlockPos.getX() + (int)(distance * Math.cos(randomAngle));
            int z = targetBlockPos.getZ() + (int)(distance * Math.sin(randomAngle));
            targetBlockPos = new BlockPos(x, 1 + getHighestYAtXZ(world, x, z), z);

            blockState = world.getBlockState(new BlockPos(x, targetBlockPos.getY() - 1, z));
            if(blockState.isOf(Blocks.WATER) || blockState.isOf(Blocks.LAVA)) continue;

            if(world.getBiome(targetBlockPos).getKey().isEmpty()) continue;
            RegistryKey<Biome> biomeRegistryKey = world.getBiome(targetBlockPos).getKey().get();

            EntityData entityData = null;
            List<EntitySpawningSettings> spawningSettings = ModEntitySpawning.getSpawnsAt(biomeRegistryKey);
            if(spawningSettings == null || spawningSettings.size() == 0) continue;
            EntitySpawningSettings entitySpawningSettings = spawningSettings.get(random.nextInt(spawningSettings.size()));

            int randomCount = random.nextInt(1 + entitySpawningSettings.getMaxCount() - entitySpawningSettings.getMinCount()); // We add +1 because we want inclusive bound.
            int entityCount = entitySpawningSettings.getMinCount() + randomCount;

            for (int m = 0; m < entityCount; ++m) {
                PathAwareEntity entity = (PathAwareEntity) entitySpawningSettings.getEntity().create(world);
                if (entity == null) continue;
                entity.refreshPositionAndAngles(targetBlockPos, 0.0f, 0.0f);
                entityData = entity.initialize(world, localDifficulty, SpawnReason.NATURAL, entityData, null);
                world.spawnEntityAndPassengers(entity);
                ++i;
            }
        }
        return i;
    }

    public int getHighestYAtXZ(World world, int x, int z) {
        return world.getChunk(new BlockPos(x, 0, z)).sampleHeightmap(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, x, z);
    }
}

