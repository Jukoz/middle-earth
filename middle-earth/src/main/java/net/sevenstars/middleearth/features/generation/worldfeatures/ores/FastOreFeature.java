package net.sevenstars.middleearth.features.generation.worldfeatures.ores;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.BlockStateMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ChunkSectionCache;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.ChunkSection;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Arrays;
import java.util.List;

// Vanilla's OreFeature with the same random calls in the same order and the same blocks placed. Only the bookkeeping differs:
// the verdicts of the target rules are remembered per block state, the visited set is a reused array, and the section lookups
// and the air check skip the allocations and repeated lookups.
public class FastOreFeature extends Feature<OreFeatureConfig> {
    private static final Direction[] DIRECTIONS = Direction.values();
    private static final int MAX_CACHED_TARGETS = 64;
    private static final int CACHE_SIZE = 8;

    private static final class Scratch {
        long[] visited = new long[256];
        int visitedWords; // words that may be set
        final BlockState[] cachedStates = new BlockState[CACHE_SIZE];
        final long[] cachedMasks = new long[CACHE_SIZE];
        int cachedCount;
        int cachedNext;
        final BlockPos.Mutable neighbour = new BlockPos.Mutable();

        void clearVisited() {
            Arrays.fill(visited, 0, Math.min(visitedWords, visited.length), 0L);
            visitedWords = 0;
        }

        boolean visit(int index) {
            int word = index >> 6;
            if (word >= visited.length) visited = Arrays.copyOf(visited, Math.max(word + 1, visited.length * 2));
            long bit = 1L << index;
            if ((visited[word] & bit) != 0) return false;
            visited[word] |= bit;
            if (word >= visitedWords) visitedWords = word + 1;
            return true;
        }
    }

    private static final ThreadLocal<Scratch> SCRATCH = ThreadLocal.withInitial(Scratch::new);

    public FastOreFeature(Codec<OreFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<OreFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        OreFeatureConfig oreFeatureConfig = context.getConfig();
        float f = random.nextFloat() * (float) Math.PI;
        float g = oreFeatureConfig.size / 8.0F;
        int i = MathHelper.ceil((oreFeatureConfig.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d = blockPos.getX() + Math.sin(f) * g;
        double e = blockPos.getX() - Math.sin(f) * g;
        double h = blockPos.getZ() + Math.cos(f) * g;
        double j = blockPos.getZ() - Math.cos(f) * g;
        double l = blockPos.getY() + random.nextInt(3) - 2;
        double m = blockPos.getY() + random.nextInt(3) - 2;
        int n = blockPos.getX() - MathHelper.ceil(g) - i;
        int o = blockPos.getY() - 2 - i;
        int p = blockPos.getZ() - MathHelper.ceil(g) - i;
        int q = 2 * (MathHelper.ceil(g) + i);
        int r = 2 * (2 + i);

        for (int s = n; s <= n + q; s++) {
            for (int t = p; t <= p + q; t++) {
                if (o <= structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, s, t)) {
                    return generateVeinPart(structureWorldAccess, random, oreFeatureConfig, d, e, h, j, l, m, n, o, p, q, r);
                }
            }
        }

        return false;
    }

    private static boolean deterministic(RuleTest test) {
        Class<?> type = test.getClass();
        return type == TagMatchRuleTest.class || type == BlockMatchRuleTest.class
                || type == BlockStateMatchRuleTest.class || type == AlwaysTrueRuleTest.class;
    }

    private static boolean generateVeinPart(StructureWorldAccess world, Random random, OreFeatureConfig config, double startX, double endX,
                                            double startZ, double endZ, double startY, double endY, int x, int y, int z, int horizontalSize, int verticalSize) {
        int placed = 0;
        Scratch scratch = SCRATCH.get();
        scratch.clearVisited();
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int j = config.size;
        double[] ds = new double[j * 4];

        for (int k = 0; k < j; k++) {
            float f = (float) k / j;
            double d = MathHelper.lerp(f, startX, endX);
            double e = MathHelper.lerp(f, startY, endY);
            double g = MathHelper.lerp(f, startZ, endZ);
            double h = random.nextDouble() * j / 16.0;
            double l = ((MathHelper.sin((float) Math.PI * f) + 1.0F) * h + 1.0) / 2.0;
            ds[k * 4 + 0] = d;
            ds[k * 4 + 1] = e;
            ds[k * 4 + 2] = g;
            ds[k * 4 + 3] = l;
        }

        for (int k = 0; k < j - 1; k++) {
            if (!(ds[k * 4 + 3] <= 0.0)) {
                for (int m = k + 1; m < j; m++) {
                    if (!(ds[m * 4 + 3] <= 0.0)) {
                        double d = ds[k * 4 + 0] - ds[m * 4 + 0];
                        double e = ds[k * 4 + 1] - ds[m * 4 + 1];
                        double g = ds[k * 4 + 2] - ds[m * 4 + 2];
                        double h = ds[k * 4 + 3] - ds[m * 4 + 3];
                        if (h * h > d * d + e * e + g * g) {
                            if (h > 0.0) {
                                ds[m * 4 + 3] = -1.0;
                            } else {
                                ds[k * 4 + 3] = -1.0;
                            }
                        }
                    }
                }
            }
        }

        List<OreFeatureConfig.Target> targets = config.targets;
        int targetCount = targets.size();
        // A rule's verdict can be remembered per block state only when it reads nothing but the state; a rule that draws
        // from the random (the Random* tests) is asked every time, exactly as before.
        boolean cached = targetCount <= MAX_CACHED_TARGETS;
        for (int k = 0; k < targetCount && cached; k++) cached = deterministic(targets.get(k).target);
        scratch.cachedCount = 0;
        scratch.cachedNext = 0;
        float discardChance = config.discardOnAirChance;
        int verticalCells = horizontalSize * verticalSize;

        try (ChunkSectionCache chunkSectionCache = new ChunkSectionCache(world)) {
            int sectionX = 0;
            int sectionY = 0;
            int sectionZ = 0;
            ChunkSection section = null;
            // false while the current section holds no state any target accepts, so its blocks need no look
            boolean sectionMayMatch = true;
            for (int m = 0; m < j; m++) {
                double d = ds[m * 4 + 3];
                if (!(d < 0.0)) {
                    double e = ds[m * 4 + 0];
                    double g = ds[m * 4 + 1];
                    double h = ds[m * 4 + 2];
                    int n = Math.max(MathHelper.floor(e - d), x);
                    int o = Math.max(MathHelper.floor(g - d), y);
                    int p = Math.max(MathHelper.floor(h - d), z);
                    int q = Math.max(MathHelper.floor(e + d), n);
                    int r = Math.max(MathHelper.floor(g + d), o);
                    int s = Math.max(MathHelper.floor(h + d), p);

                    for (int t = n; t <= q; t++) {
                        double u = (t + 0.5 - e) / d;
                        double uu = u * u;
                        if (uu < 1.0) {
                            for (int v = o; v <= r; v++) {
                                double w = (v + 0.5 - g) / d;
                                double uw = uu + w * w;
                                if (uw < 1.0 && !world.isOutOfHeightLimit(v)) {
                                    for (int aa = p; aa <= s; aa++) {
                                        double ab = (aa + 0.5 - h) / d;
                                        if (uw + ab * ab < 1.0) {
                                            // the index collides for some positions, and so it did in vanilla
                                            int index = t - x + (v - y) * horizontalSize + (aa - z) * verticalCells;
                                            if (scratch.visit(index)) {
                                                mutable.set(t, v, aa);
                                                if (world.isValidForSetBlock(mutable)) {
                                                    int sx = t >> 4;
                                                    int sy = v >> 4;
                                                    int sz = aa >> 4;
                                                    if (section == null || sx != sectionX || sy != sectionY || sz != sectionZ) {
                                                        section = chunkSectionCache.getSection(mutable);
                                                        sectionX = sx;
                                                        sectionY = sy;
                                                        sectionZ = sz;
                                                        sectionMayMatch = !cached || section == null
                                                                || section.hasAny(state -> passingTargets(scratch, targets, targetCount, state, random) != 0L);
                                                    }
                                                    if (section != null && sectionMayMatch) {
                                                        int ad = ChunkSectionPos.getLocalCoord(t);
                                                        int ae = ChunkSectionPos.getLocalCoord(v);
                                                        int af = ChunkSectionPos.getLocalCoord(aa);
                                                        BlockState blockState = section.getBlockState(ad, ae, af);
                                                        long mask = cached ? passingTargets(scratch, targets, targetCount, blockState, random) : -1L;
                                                        if (mask == 0L) continue;
                                                        for (int k = 0; k < targetCount; k++) {
                                                            OreFeatureConfig.Target target = targets.get(k);
                                                            boolean passes = cached ? (mask & (1L << k)) != 0 : target.target.test(blockState, random);
                                                            if (!passes) continue;
                                                            if (shouldNotDiscard(random, discardChance) || !exposedToAir(chunkSectionCache, scratch.neighbour, t, v, aa)) {
                                                                section.setBlockState(ad, ae, af, target.state, false);
                                                                placed++;
                                                                // the new block may be one a target accepts
                                                                sectionMayMatch = true;
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return placed > 0;
    }

    // bit k set when target k's rule accepts the state
    private static long passingTargets(Scratch scratch, List<OreFeatureConfig.Target> targets, int targetCount, BlockState state, Random random) {
        BlockState[] states = scratch.cachedStates;
        for (int k = 0; k < scratch.cachedCount; k++) {
            if (states[k] == state) return scratch.cachedMasks[k];
        }
        long mask = 0L;
        for (int k = 0; k < targetCount; k++) {
            if (targets.get(k).target.test(state, random)) mask |= 1L << k;
        }
        int slot = scratch.cachedCount < CACHE_SIZE ? scratch.cachedCount++ : scratch.cachedNext++ % CACHE_SIZE;
        states[slot] = state;
        scratch.cachedMasks[slot] = mask;
        return mask;
    }

    private static boolean shouldNotDiscard(Random random, float chance) {
        if (chance <= 0.0F) {
            return true;
        } else {
            return chance >= 1.0F ? false : random.nextFloat() >= chance;
        }
    }

    // Feature.isExposedToAir, looking at the neighbours in the same order and stopping at the first air
    private static boolean exposedToAir(ChunkSectionCache cache, BlockPos.Mutable neighbour, int x, int y, int z) {
        for (Direction direction : DIRECTIONS) {
            neighbour.set(x + direction.getOffsetX(), y + direction.getOffsetY(), z + direction.getOffsetZ());
            if (cache.getBlockState(neighbour).isAir()) return true;
        }
        return false;
    }
}
