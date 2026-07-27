package net.sevenstars.middleearth.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;

public class SpawnUtil {
    public static boolean canCreatureSpawn(EntityType<?> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        if (spawnReason == MobSpawnType.NATURAL && serverWorldAccess instanceof Level world) {
            Holder<Biome> biome = world.getBiome(blockPos);
            return BiomeEventDataLookup.canEntitySpawn(world, biome, blockPos, type, random);
        }
        return true;
    }

    public static boolean canSpawn(BlockPos blockPos, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason) {
        BlockPos below = blockPos.below();
        boolean isOnSolidGround = serverWorldAccess.getBlockState(below).isRedstoneConductor(serverWorldAccess, below);
        boolean isNotOnTopOfLogs = !serverWorldAccess.getBlockState(below).is(BlockTags.LOGS);
        return isOnSolidGround && isNotOnTopOfLogs;
    }
}
