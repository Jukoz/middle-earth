package net.sevenstars.middleearth.world.features.ores;

import com.mojang.serialization.Codec;
import java.util.BitSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.BulkSectionAccess;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ModOreFeature extends Feature<ModOreFeatureConfig> {
    public ModOreFeature(Codec<ModOreFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ModOreFeatureConfig> context) {
        RandomSource random = context.random();
        BlockPos blockPos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();
        ModOreFeatureConfig modOreFeatureConfig = context.config();
        float f = random.nextFloat() * 3.1415927F;
        float g = (float)modOreFeatureConfig.size / 8.0F;
        int i = Mth.ceil(((float)modOreFeatureConfig.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double d = (double)blockPos.getX() + Math.sin((double)f) * (double)g;
        double e = (double)blockPos.getX() - Math.sin((double)f) * (double)g;
        double h = (double)blockPos.getZ() + Math.cos((double)f) * (double)g;
        double j = (double)blockPos.getZ() - Math.cos((double)f) * (double)g;
        double l = (double)(blockPos.getY() + random.nextInt(3) - 2);
        double m = (double)(blockPos.getY() + random.nextInt(3) - 2);
        int n = blockPos.getX() - Mth.ceil(g) - i;
        int o = blockPos.getY() - 2 - i;
        int p = blockPos.getZ() - Mth.ceil(g) - i;
        int q = 2 * (Mth.ceil(g) + i);
        int r = 2 * (2 + i);

        for(int s = n; s <= n + q; ++s) {
            for(int t = p; t <= p + q; ++t) {
                if (o <= structureWorldAccess.getHeight(Heightmap.Types.OCEAN_FLOOR_WG, s, t)) {
                    boolean result = this.generateVeinPart(structureWorldAccess, random, modOreFeatureConfig, d, e, h, j, l, m, n, o, p, q, r);
                    structureWorldAccess.setBlock(context.origin(), context.config().blockState, 2);
                    return result;
                }
            }
        }

        return false;
    }

    protected boolean generateVeinPart(WorldGenLevel world, RandomSource random, ModOreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int horizontalSize, int verticalSize) {
        int i = 0;
        BitSet bitSet = new BitSet(horizontalSize * verticalSize * horizontalSize);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int j = config.size;
        double[] ds = new double[j * 4];

        int k;
        double d;
        double e;
        double g;
        double h;
        for(k = 0; k < j; ++k) {
            float f = (float)k / (float)j;
            d = Mth.lerp((double)f, startX, endX);
            e = Mth.lerp((double)f, startY, endY);
            g = Mth.lerp((double)f, startZ, endZ);
            h = random.nextDouble() * (double)j / 16.0;
            double l = ((double)(Mth.sin(3.1415927F * f) + 1.0F) * h + 1.0) / 2.0;
            ds[k * 4 + 0] = d;
            ds[k * 4 + 1] = e;
            ds[k * 4 + 2] = g;
            ds[k * 4 + 3] = l;
        }

        int m;
        for(k = 0; k < j - 1; ++k) {
            if (!(ds[k * 4 + 3] <= 0.0)) {
                for(m = k + 1; m < j; ++m) {
                    if (!(ds[m * 4 + 3] <= 0.0)) {
                        d = ds[k * 4 + 0] - ds[m * 4 + 0];
                        e = ds[k * 4 + 1] - ds[m * 4 + 1];
                        g = ds[k * 4 + 2] - ds[m * 4 + 2];
                        h = ds[k * 4 + 3] - ds[m * 4 + 3];
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

        BulkSectionAccess chunkSectionCache = new BulkSectionAccess(world);

        try {
            for(m = 0; m < j; ++m) {
                d = ds[m * 4 + 3];
                if (!(d < 0.0)) {
                    e = ds[m * 4 + 0];
                    g = ds[m * 4 + 1];
                    h = ds[m * 4 + 2];
                    int n = Math.max(Mth.floor(e - d), x);
                    int o = Math.max(Mth.floor(g - d), y);
                    int p = Math.max(Mth.floor(h - d), z);
                    int q = Math.max(Mth.floor(e + d), n);
                    int r = Math.max(Mth.floor(g + d), o);
                    int s = Math.max(Mth.floor(h + d), p);

                    for(int t = n; t <= q; ++t) {
                        double u = ((double)t + 0.5 - e) / d;
                        if (u * u < 1.0) {
                            for(int v = o; v <= r; ++v) {
                                double w = ((double)v + 0.5 - g) / d;
                                if (u * u + w * w < 1.0) {
                                    for(int aa = p; aa <= s; ++aa) {
                                        double ab = ((double)aa + 0.5 - h) / d;
                                        if (u * u + w * w + ab * ab < 1.0 && !world.isOutsideBuildHeight(v)) {
                                            int ac = t - x + (v - y) * horizontalSize + (aa - z) * horizontalSize * verticalSize;
                                            if (!bitSet.get(ac)) {
                                                bitSet.set(ac);
                                                mutable.set(t, v, aa);
                                                if (world.ensureCanWrite(mutable)) {
                                                    LevelChunkSection chunkSection = chunkSectionCache.getSection(mutable);
                                                    if (chunkSection != null) {
                                                        int ad = SectionPos.sectionRelative(t);
                                                        int ae = SectionPos.sectionRelative(v);
                                                        int af = SectionPos.sectionRelative(aa);
                                                        BlockState blockState = chunkSection.getBlockState(ad, ae, af);
                                                        Iterator var57 = config.targets.iterator();

                                                        while(var57.hasNext()) {
                                                            ModOreFeatureConfig.Target target = (ModOreFeatureConfig.Target)var57.next();
                                                            Objects.requireNonNull(chunkSectionCache);
                                                            if (shouldPlace(blockState, chunkSectionCache::getBlockState, random, config, target, mutable)) {
                                                                chunkSection.setBlockState(ad, ae, af, target.state, false);
                                                                ++i;
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
        } catch (Throwable var60) {
            try {
                chunkSectionCache.close();
            } catch (Throwable var59) {
                var60.addSuppressed(var59);
            }

            throw var60;
        }

        chunkSectionCache.close();
        return i > 0;
    }

    public static boolean shouldPlace(BlockState state, Function<BlockPos, BlockState> posToState, RandomSource random, ModOreFeatureConfig config, ModOreFeatureConfig.Target target, BlockPos.MutableBlockPos pos) {
        if (!target.target.test(state, random)) {
            return false;
        } else if (shouldNotDiscard(random, config.discardOnAirChance)) {
            return true;
        } else {
            return !isAdjacentToAir(posToState, pos);
        }
    }

    protected static boolean shouldNotDiscard(RandomSource random, float chance) {
        if (chance <= 0.0F) {
            return true;
        } else if (chance >= 1.0F) {
            return false;
        } else {
            return random.nextFloat() >= chance;
        }
    }
}
