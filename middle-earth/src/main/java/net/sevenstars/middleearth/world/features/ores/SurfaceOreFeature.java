package net.sevenstars.middleearth.world.features.ores;

import com.mojang.serialization.Codec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
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

import java.util.BitSet;
import java.util.function.Function;
import java.util.function.Predicate;

public class SurfaceOreFeature extends Feature<OreFeatureConfig> {
    public SurfaceOreFeature(Codec<OreFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<OreFeatureConfig> context) {
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        OreFeatureConfig oreFeatureConfig = context.getConfig();
        float f = random.nextFloat() * (float)Math.PI;
        float g = (float)oreFeatureConfig.size / 8.0f;
        int i = MathHelper.ceil(((float)oreFeatureConfig.size / 16.0f * 2.0f + 1.0f) / 2.0f);
        double d = (double)blockPos.getX() + Math.sin(f) * (double)g;
        double e = (double)blockPos.getX() - Math.sin(f) * (double)g;
        double h = (double)blockPos.getZ() + Math.cos(f) * (double)g;
        double j = (double)blockPos.getZ() - Math.cos(f) * (double)g;
        int k = 2;
        double l = blockPos.getY() + random.nextInt(3) - 2;
        double m = blockPos.getY() + random.nextInt(3) - 2;
        int n = blockPos.getX() - MathHelper.ceil(g) - i;
        int o = blockPos.getY() - 2 - i;
        int p = blockPos.getZ() - MathHelper.ceil(g) - i;
        int q = 2 * (MathHelper.ceil(g) + i);
        int r = 2 * (2 + i);
        for (int s = n; s <= n + q; ++s) {
            for (int t = p; t <= p + q; ++t) {
                if (o > structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, s, t)) continue;
                return this.generateVeinPart(structureWorldAccess, random, oreFeatureConfig, d, e, h, j, l, m, n, o, p, q, r);
            }
        }
        return false;
    }

    protected boolean generateVeinPart(StructureWorldAccess world, Random random, OreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int horizontalSize, int verticalSize) {
        double h;
        double g;
        double e;
        double d;
        int k;
        int i = 0;
        BitSet bitSet = new BitSet(horizontalSize * verticalSize * horizontalSize);
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int j = config.size;
        double[] ds = new double[j * 4];
        for (k = 0; k < j; ++k) {
            float f = (float)k / (float)j;
            d = MathHelper.lerp((double)f, startX, endX);
            e = MathHelper.lerp((double)f, startY, endY);
            g = MathHelper.lerp((double)f, startZ, endZ);
            h = random.nextDouble() * (double)j / 16.0;
            double l = ((double)(MathHelper.sin((float)Math.PI * f) + 1.0f) * h + 1.0) / 2.0;
            ds[k * 4 + 0] = d;
            ds[k * 4 + 1] = e;
            ds[k * 4 + 2] = g;
            ds[k * 4 + 3] = l;
        }
        for (k = 0; k < j - 1; ++k) {
            if (ds[k * 4 + 3] <= 0.0) continue;
            for (int m = k + 1; m < j; ++m) {
                if (ds[m * 4 + 3] <= 0.0 || !((h = ds[k * 4 + 3] - ds[m * 4 + 3]) * h > (d = ds[k * 4 + 0] - ds[m * 4 + 0]) * d + (e = ds[k * 4 + 1] - ds[m * 4 + 1]) * e + (g = ds[k * 4 + 2] - ds[m * 4 + 2]) * g)) continue;
                if (h > 0.0) {
                    ds[m * 4 + 3] = -1.0;
                    continue;
                }
                ds[k * 4 + 3] = -1.0;
            }
        }
        try (ChunkSectionCache chunkSectionCache = new ChunkSectionCache(world);){
            for (int m = 0; m < j; ++m) {
                d = ds[m * 4 + 3];
                if (d < 0.0) continue;
                e = ds[m * 4 + 0];
                g = ds[m * 4 + 1];
                h = ds[m * 4 + 2];
                int n = Math.max(MathHelper.floor(e - d), x);
                int o = Math.max(MathHelper.floor(g - d), y);
                int p = Math.max(MathHelper.floor(h - d), z);
                int q = Math.max(MathHelper.floor(e + d), n);
                int r = Math.max(MathHelper.floor(g + d), o);
                int s = Math.max(MathHelper.floor(h + d), p);
                for (int t = n; t <= q; ++t) {
                    double u = ((double)t + 0.5 - e) / d;
                    if (!(u * u < 1.0)) continue;
                    for (int v = o; v <= r; ++v) {
                        double w = ((double)v + 0.5 - g) / d;
                        if (!(u * u + w * w < 1.0)) continue;
                        block11: for (int aa = p; aa <= s; ++aa) {
                            ChunkSection chunkSection;
                            int ac;
                            double ab = ((double)aa + 0.5 - h) / d;
                            if (!(u * u + w * w + ab * ab < 1.0) || world.isOutOfHeightLimit(v) || bitSet.get(ac = t - x + (v - y) * horizontalSize + (aa - z) * horizontalSize * verticalSize)) continue;
                            bitSet.set(ac);
                            mutable.set(t, v, aa);
                            if (!world.isValidForSetBlock(mutable) || (chunkSection = chunkSectionCache.getSection(mutable)) == null) continue;
                            int ad = ChunkSectionPos.getLocalCoord(t);
                            int ae = ChunkSectionPos.getLocalCoord(v);
                            int af = ChunkSectionPos.getLocalCoord(aa);
                            BlockState blockState = chunkSection.getBlockState(ad, ae, af);
                            for (OreFeatureConfig.Target target : config.targets) {
                                if (!shouldPlace(blockState, chunkSectionCache::getBlockState, random, config, target, mutable)) continue;
                                chunkSection.setBlockState(ad, ae, af, target.state, false);
                                ++i;
                                continue block11;
                            }
                        }
                    }
                }
            }
        }
        return i > 0;
    }

    public static boolean shouldPlace(BlockState state, Function<BlockPos, BlockState> posToState, Random random, OreFeatureConfig config, OreFeatureConfig.Target target, BlockPos.Mutable pos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Predicate<BlockState> predicate = AbstractBlock.AbstractBlockState::isAir;
        mutable.set(pos, Direction.UP);
        if (!predicate.test(posToState.apply(mutable))) return false;

        if (!target.target.test(state, random)) {
            return false;
        }

        return shouldNotDiscard(random, config.discardOnAirChance);
    }

    protected static boolean shouldNotDiscard(Random random, float chance) {
        if (chance <= 0.0f) {
            return true;
        }
        if (chance >= 1.0f) {
            return false;
        }
        return random.nextFloat() >= chance;
    }
}
