package net.sevenstars.middleearth.utils;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventDataLookup;

public class SpawnUtil {
    public static boolean canCreatureSpawn(EntityType<?> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        BlockPos below = blockPos.down();
        boolean isOnSolidGround = serverWorldAccess.getBlockState(below).isSolidBlock(serverWorldAccess, below);
        boolean isNotOnTopOfLogs = !serverWorldAccess.getBlockState(below).isIn(BlockTags.LOGS);

        if (spawnReason == SpawnReason.NATURAL && serverWorldAccess instanceof World world) {
            RegistryEntry<Biome> biome = world.getBiome(blockPos);
            boolean canSpawn = BiomeEventDataLookup.canEntitySpawn(world, biome, blockPos, type, random);
            return isOnSolidGround && isNotOnTopOfLogs && canSpawn;
        }

        return isOnSolidGround && isNotOnTopOfLogs;
    }
}
