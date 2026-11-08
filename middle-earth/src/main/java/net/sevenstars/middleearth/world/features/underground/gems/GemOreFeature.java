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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

/**
 * Hides gems in the rock beside host ore clusters. Runs once per chunk after the ores (underground decoration
 * step): finds the host ore blocks in the chunk's y band, groups touching blocks into clusters, and gives each
 * cluster a 1-in-N chance to have one of the stone blocks touching it (face to face) turned into a gem ore
 * disguised as that very stone (or several, see {@link GemOreFeatureConfig#perCluster()}). The ore itself is
 * never replaced, so a gem is found by mining around the vein.
 */
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

        boolean placed = false;
        for (List<Long> cluster : clusters(hosts)) {
            if (random.nextInt(config.oneIn()) != 0) {
                continue;
            }
            // Turn up to perCluster distinct stone blocks touching this cluster into gems.
            List<Long> rock = rockAround(world, cluster, hosts, chunk);
            for (int i = 0; i < config.perCluster() && !rock.isEmpty(); i++) {
                BlockPos gemPos = BlockPos.fromLong(rock.remove(random.nextInt(rock.size())));
                BlockState gemOre = NatureBlockRegistryME.GEM_ORE.getDefaultState()
                    .with(GemOreBlock.GEM, config.gem())
                    .with(GemOreBlock.STONE, GemOreStone.of(world.getBlockState(gemPos).getBlock()));
                world.setBlockState(gemPos, gemOre, Block.NOTIFY_LISTENERS);
                placed = true;
            }
        }
        return placed;
    }

    /** Region gems: check the surface biome in the middle of the chunk (underground is cave biomes). */
    private static boolean surfaceInRegion(StructureWorldAccess world, ChunkPos chunk, GemOreFeatureConfig config) {
        int x = chunk.getCenterX();
        int z = chunk.getCenterZ();
        int surfaceY = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, x, z) - 1;
        return world.getBiome(new BlockPos(x, surfaceY, z)).isIn(config.region().get());
    }

    /** Touching host blocks (including diagonals) form one cluster. */
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

    /**
     * The stone blocks touching a cluster face to face, inside this chunk: only stones a gem ore can disguise
     * itself as (see {@link GemOreStone}), so the gem looks exactly like the block it took the place of.
     */
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
