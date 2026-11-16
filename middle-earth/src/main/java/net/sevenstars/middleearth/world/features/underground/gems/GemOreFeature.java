package net.sevenstars.middleearth.world.features.underground.gems;

import net.sevenstars.middleearth.block.special.gems.GemKind;
import net.sevenstars.middleearth.block.special.gems.GemOreBlock;
import net.sevenstars.middleearth.block.special.gems.GemOreStone;
import net.sevenstars.middleearth.block.registration.NatureBlockRegistryME;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

/** Hides gems beside host ore clusters, a set number per chunk. */
public class GemOreFeature extends Feature<GemOreFeatureConfig> {
    public GemOreFeature() {
        super(GemOreFeatureConfig.CODEC);
    }

    @Override
    public boolean generate(FeatureContext<GemOreFeatureConfig> context) {
        GemOreFeatureConfig config = context.getConfig();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();
        ChunkPos chunk = new ChunkPos(context.getOrigin());

        if (config.region().isPresent() && !surfaceInRegion(world, chunk, config)) {
            return false;
        }

        int minY = Math.max(config.minY(), world.getBottomY());
        int maxY = Math.min(config.maxY(), world.getTopYInclusive());
        if (minY > maxY) {
            return false;
        }

        Set<Block> targets = new HashSet<>(config.targets());
        Set<Long> hosts = new HashSet<>();
        BlockPos.Mutable pos = new BlockPos.Mutable();
        for (int x = chunk.getStartX(); x <= chunk.getEndX(); x++) {
            for (int z = chunk.getStartZ(); z <= chunk.getEndZ(); z++) {
                for (int y = minY; y <= maxY; y++) {
                    pos.set(x, y, z);
                    if (targets.contains(world.getBlockState(pos).getBlock())) {
                        hosts.add(pos.asLong());
                    }
                }
            }
        }

        int wanted = config.minPerChunk() + random.nextInt(Math.max(1, config.maxPerChunk() - config.minPerChunk() + 1));
        if (wanted <= 0 || hosts.isEmpty()) {
            return false;
        }
        List<List<Long>> rocks = new ArrayList<>();
        for (List<Long> cluster : clusters(hosts)) {
            List<Long> rock = rockAround(world, cluster, hosts, chunk);
            if (!rock.isEmpty()) {
                rocks.add(rock);
            }
        }
        Util.shuffle(rocks, random);
        Set<Long> taken = new HashSet<>();
        int placed = 0;
        while (placed < wanted && !rocks.isEmpty()) {
            for (int i = 0; i < rocks.size() && placed < wanted; i++) {
                if (placeOne(world, rocks.get(i), taken, config, random)) {
                    placed++;
                }
            }
            rocks.removeIf(List::isEmpty);
        }
        return placed > 0;
    }

    private static boolean placeOne(StructureWorldAccess world, List<Long> rock, Set<Long> taken,
                                    GemOreFeatureConfig config, Random random) {
        while (!rock.isEmpty()) {
            long key = rock.remove(random.nextInt(rock.size()));
            if (!taken.add(key)) {
                continue;
            }
            BlockPos gemPos = BlockPos.fromLong(key);
            GemOreStone stone = GemOreStone.of(world.getBlockState(gemPos).getBlock());
            if (stone == null) {
                continue;
            }
            world.setBlockState(gemPos, NatureBlockRegistryME.GEM_ORE.getDefaultState()
                .with(GemOreBlock.GEM, config.gem())
                .with(GemOreBlock.STONE, stone), Block.NOTIFY_LISTENERS);
            return true;
        }
        return false;
    }

    private static boolean surfaceInRegion(StructureWorldAccess world, ChunkPos chunk, GemOreFeatureConfig config) {
        int x = chunk.getCenterX();
        int z = chunk.getCenterZ();
        int surfaceY = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z) - 1;
        return world.getBiome(new BlockPos(x, surfaceY, z)).isIn(config.region().get());
    }

    /** Groups touching host ore blocks into clusters. */
    private static List<List<Long>> clusters(Set<Long> hosts) {
        List<List<Long>> clusters = new ArrayList<>();
        Set<Long> seen = new HashSet<>();
        for (Long start : hosts) {
            if (!seen.add(start)) {
                continue;
            }
            List<Long> cluster = new ArrayList<>();
            ArrayDeque<Long> queue = new ArrayDeque<>();
            queue.add(start);
            while (!queue.isEmpty()) {
                long current = queue.poll();
                cluster.add(current);
                BlockPos p = BlockPos.fromLong(current);
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dz = -1; dz <= 1; dz++) {
                            long next = BlockPos.asLong(p.getX() + dx, p.getY() + dy, p.getZ() + dz);
                            if (hosts.contains(next) && seen.add(next)) {
                                queue.add(next);
                            }
                        }
                    }
                }
            }
            clusters.add(cluster);
        }
        return clusters;
    }

    /** Stone blocks touching a cluster, inside the chunk, that a gem can replace. */
    private static List<Long> rockAround(StructureWorldAccess world, List<Long> cluster, Set<Long> hosts, ChunkPos chunk) {
        Set<Long> rock = new LinkedHashSet<>();
        BlockPos.Mutable next = new BlockPos.Mutable();
        for (long member : cluster) {
            BlockPos p = BlockPos.fromLong(member);
            for (Direction side : Direction.values()) {
                next.set(p, side);
                long key = next.asLong();
                if (hosts.contains(key) || next.getX() >> 4 != chunk.x || next.getZ() >> 4 != chunk.z
                    || world.isOutOfHeightLimit(next) || GemOreStone.of(world.getBlockState(next).getBlock()) == null) {
                    continue;
                }
                rock.add(key);
            }
        }
        return new ArrayList<>(rock);
    }
}
