package net.jukoz.me.world.features.pillar;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LargeDripstoneFeature;
import net.minecraft.world.gen.feature.util.CaveSurface;
import net.minecraft.world.gen.feature.util.DripstoneHelper;
import net.minecraft.world.gen.feature.util.FeatureContext;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class PillarFeature extends Feature<PillarFeatureConfig> {
    public PillarFeature(Codec<PillarFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<PillarFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        PillarFeatureConfig largeDripstoneFeatureConfig = context.getConfig();
        Random random = context.getRandom();
        if (!canGenerate(structureWorldAccess, blockPos)) {
            return false;
        }
        Optional<CaveSurface> optional = CaveSurface.create(structureWorldAccess, blockPos, largeDripstoneFeatureConfig.floorToCeilingSearchRange, DripstoneHelper::canGenerate, DripstoneHelper::canReplaceOrLava);
        if (!optional.isPresent() || !(optional.get() instanceof CaveSurface.Bounded)) {
            return false;
        }
        CaveSurface.Bounded bounded = (CaveSurface.Bounded)optional.get();
        if (bounded.getHeight() < 4) {
            return false;
        }
        int i = (int)((float)bounded.getHeight() * largeDripstoneFeatureConfig.maxColumnRadiusToCaveHeightRatio);
        int j = MathHelper.clamp(i, largeDripstoneFeatureConfig.columnRadius.getMin(), largeDripstoneFeatureConfig.columnRadius.getMax());
        int k = MathHelper.nextBetween(random, largeDripstoneFeatureConfig.columnRadius.getMin(), j);
        DripstoneGenerator dripstoneGenerator = createGenerator(blockPos.withY(bounded.getCeiling() - 1), false, random, k, largeDripstoneFeatureConfig.stalactiteBluntness, largeDripstoneFeatureConfig.heightScale);
        DripstoneGenerator dripstoneGenerator2 = createGenerator(blockPos.withY(bounded.getFloor() + 1), true, random, k, largeDripstoneFeatureConfig.stalagmiteBluntness, largeDripstoneFeatureConfig.heightScale);
        WindModifier windModifier = dripstoneGenerator.generateWind(largeDripstoneFeatureConfig) && dripstoneGenerator2.generateWind(largeDripstoneFeatureConfig) ? new WindModifier(blockPos.getY(), random, largeDripstoneFeatureConfig.windSpeed) : WindModifier.create();
        boolean bl = dripstoneGenerator.canGenerate(structureWorldAccess, windModifier);
        boolean bl2 = dripstoneGenerator2.canGenerate(structureWorldAccess, windModifier);
        if (bl) {
            dripstoneGenerator.generate(structureWorldAccess, random, windModifier, largeDripstoneFeatureConfig.blockState);
        }
        if (bl2) {
            dripstoneGenerator2.generate(structureWorldAccess, random, windModifier, largeDripstoneFeatureConfig.blockState);
        }
        return true;
    }

    private static DripstoneGenerator createGenerator(BlockPos pos, boolean isStalagmite, Random random, int scale, FloatProvider bluntness, FloatProvider heightScale) {
        return new DripstoneGenerator(pos, isStalagmite, scale, bluntness.get(random), heightScale.get(random));
    }

    static final class DripstoneGenerator {
        private BlockPos pos;
        private final boolean isStalagmite;
        private int scale;
        private final double bluntness;
        private final double heightScale;

        DripstoneGenerator(BlockPos pos, boolean isStalagmite, int scale, double bluntness, double heightScale) {
            this.pos = pos;
            this.isStalagmite = isStalagmite;
            this.scale = scale;
            this.bluntness = bluntness;
            this.heightScale = heightScale;
        }

        private int getBaseScale() {
            return this.scale(0.0f);
        }

        private int getBottomY() {
            if (this.isStalagmite) {
                return this.pos.getY();
            }
            return this.pos.getY() - this.getBaseScale();
        }

        private int getTopY() {
            if (!this.isStalagmite) {
                return this.pos.getY();
            }
            return this.pos.getY() + this.getBaseScale();
        }

        boolean canGenerate(StructureWorldAccess world, WindModifier wind) {
            while (this.scale > 1) {
                BlockPos.Mutable mutable = this.pos.mutableCopy();
                int i = Math.min(10, this.getBaseScale());
                for (int j = 0; j < i; ++j) {
                    if (world.getBlockState(mutable).isOf(Blocks.LAVA)) {
                        return false;
                    }
                    if (canGenerateBase(world, wind.modify(mutable), this.scale)) {
                        this.pos = mutable;
                        return true;
                    }
                    mutable.move(this.isStalagmite ? Direction.DOWN : Direction.UP);
                }
                this.scale /= 2;
            }
            return false;
        }

        private int scale(float height) {
            return (int)scaleHeightFromRadius(height, this.scale, this.heightScale, this.bluntness);
        }

        void generate(StructureWorldAccess world, Random random, WindModifier wind, BlockState blockState) {
            for (int i = -this.scale; i <= this.scale; ++i) {
                block1: for (int j = -this.scale; j <= this.scale; ++j) {
                    int k;
                    float f = MathHelper.sqrt(i * i + j * j);
                    if (f > (float)this.scale || (k = this.scale(f)) <= 0) continue;
                    if ((double)random.nextFloat() < 0.2) {
                        k = (int)((float)k * MathHelper.nextBetween(random, 0.8f, 1.0f));
                    }
                    BlockPos.Mutable mutable = this.pos.add(i, 0, j).mutableCopy();
                    boolean bl = false;
                    int l = this.isStalagmite ? world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, mutable.getX(), mutable.getZ()) : Integer.MAX_VALUE;
                    for (int m = 0; m < k && mutable.getY() < l; ++m) {
                        BlockPos blockPos = wind.modify(mutable);
                        if (canGenerateOrLava(world, blockPos)) {
                            bl = true;
                            world.setBlockState(blockPos, blockState, Block.NOTIFY_LISTENERS);
                        } else if (bl && world.getBlockState(blockPos).isIn(BlockTags.BASE_STONE_OVERWORLD)) continue block1;
                        mutable.move(this.isStalagmite ? Direction.UP : Direction.DOWN);
                    }
                }
            }
        }

        boolean generateWind(PillarFeatureConfig config) {
            return this.scale >= config.minRadiusForWind && this.bluntness >= (double)config.minBluntnessForWind;
        }
    }

    protected static boolean canGenerate(WorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, DripstoneHelper::canGenerate);
    }
    protected static boolean canGenerateBase(StructureWorldAccess world, BlockPos pos, int height) {
        if (canGenerateOrLava(world, pos)) {
            return false;
        }
        float f = 6.0f;
        float g = 6.0f / (float)height;
        for (float h = 0.0f; h < (float)Math.PI * 2; h += g) {
            int j;
            int i = (int)(MathHelper.cos(h) * (float)height);
            if (!canGenerateOrLava(world, pos.add(i, 0, j = (int)(MathHelper.sin(h) * (float)height)))) continue;
            return false;
        }
        return true;
    }

    protected static double scaleHeightFromRadius(double radius, double scale, double heightScale, double bluntness) {
        if (radius < bluntness) {
            radius = bluntness;
        }
        double d = 0.384;
        double e = radius / scale * 0.384;
        double f = 0.75 * Math.pow(e, 1.3333333333333333);
        double g = Math.pow(e, 0.6666666666666666);
        double h = 0.3333333333333333 * Math.log(e);
        double i = heightScale * (f - g - h);
        i = Math.max(i, 0.0);
        return i / 0.384 * scale;
    }

    protected static boolean canGenerateOrLava(WorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, DripstoneHelper::canGenerateOrLava);
    }

    static final class WindModifier {
        private final int y;
        @Nullable
        private final Vec3d wind;

        WindModifier(int y, Random random, FloatProvider wind) {
            this.y = y;
            float f = wind.get(random);
            float g = MathHelper.nextBetween(random, 0.0f, (float)Math.PI);
            this.wind = new Vec3d(MathHelper.cos(g) * f, 0.0, MathHelper.sin(g) * f);
        }

        private WindModifier() {
            this.y = 0;
            this.wind = null;
        }

        static WindModifier create() {
            return new WindModifier();
        }

        BlockPos modify(BlockPos pos) {
            if (this.wind == null) {
                return pos;
            }
            int i = this.y - pos.getY();
            Vec3d vec3d = this.wind.multiply(i);
            return pos.add(MathHelper.floor(vec3d.x), 0, MathHelper.floor(vec3d.z));
        }
    }
}

