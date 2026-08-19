package net.sevenstars.middleearth.world.features.pillar;

import com.mojang.serialization.Codec;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.phys.Vec3;

public class PillarFeature extends Feature<PillarFeatureConfig> {
    public PillarFeature(Codec<PillarFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<PillarFeatureConfig> context) {
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        PillarFeatureConfig largeDripstoneFeatureConfig = context.config();
        RandomSource random = context.random();
        if (!canGenerate(structureWorldAccess, blockPos)) {
            return false;
        }
        Optional<Column> optional = Column.scan(structureWorldAccess, blockPos, largeDripstoneFeatureConfig.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isDripstoneBaseOrLava);
        if (!optional.isPresent() || !(optional.get() instanceof Column.Range)) {
            return false;
        }
        Column.Range bounded = (Column.Range)optional.get();
        if (bounded.height() < 4) {
            return false;
        }
        int i = (int)((float)bounded.height() * largeDripstoneFeatureConfig.maxColumnRadiusToCaveHeightRatio);
        int j = Mth.clamp(i, largeDripstoneFeatureConfig.columnRadius.getMinValue(), largeDripstoneFeatureConfig.columnRadius.getMaxValue());
        int k = Mth.randomBetweenInclusive(random, largeDripstoneFeatureConfig.columnRadius.getMinValue(), j);
        DripstoneGenerator dripstoneGenerator = createGenerator(blockPos.atY(bounded.ceiling() - 1), false, random, k, largeDripstoneFeatureConfig.stalactiteBluntness, largeDripstoneFeatureConfig.heightScale);
        DripstoneGenerator dripstoneGenerator2 = createGenerator(blockPos.atY(bounded.floor() + 1), true, random, k, largeDripstoneFeatureConfig.stalagmiteBluntness, largeDripstoneFeatureConfig.heightScale);
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

    private static DripstoneGenerator createGenerator(BlockPos pos, boolean isStalagmite, RandomSource random, int scale, FloatProvider bluntness, FloatProvider heightScale) {
        return new DripstoneGenerator(pos, isStalagmite, scale, bluntness.sample(random), heightScale.sample(random));
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

        boolean canGenerate(WorldGenLevel world, WindModifier wind) {
            while (this.scale > 1) {
                BlockPos.MutableBlockPos mutable = this.pos.mutable();
                int i = Math.min(10, this.getBaseScale());
                for (int j = 0; j < i; ++j) {
                    if (world.getBlockState(mutable).is(Blocks.LAVA)) {
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

        void generate(WorldGenLevel world, RandomSource random, WindModifier wind, BlockState blockState) {
            for (int i = -this.scale; i <= this.scale; ++i) {
                block1: for (int j = -this.scale; j <= this.scale; ++j) {
                    int k;
                    float f = Mth.sqrt(i * i + j * j);
                    if (f > (float)this.scale || (k = this.scale(f)) <= 0) continue;
                    if ((double)random.nextFloat() < 0.2) {
                        k = (int)((float)k * Mth.randomBetween(random, 0.8f, 1.0f));
                    }
                    BlockPos.MutableBlockPos mutable = this.pos.offset(i, 0, j).mutable();
                    boolean bl = false;
                    int l = this.isStalagmite ? world.getHeight(Heightmap.Types.WORLD_SURFACE_WG, mutable.getX(), mutable.getZ()) : Integer.MAX_VALUE;
                    for (int m = 0; m < k && mutable.getY() < l; ++m) {
                        BlockPos blockPos = wind.modify(mutable);
                        if (canGenerateOrLava(world, blockPos)) {
                            bl = true;
                            world.setBlock(blockPos, blockState, Block.UPDATE_CLIENTS);
                        } else if (bl && world.getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) continue block1;
                        mutable.move(this.isStalagmite ? Direction.UP : Direction.DOWN);
                    }
                }
            }
        }

        boolean generateWind(PillarFeatureConfig config) {
            return this.scale >= config.minRadiusForWind && this.bluntness >= (double)config.minBluntnessForWind;
        }
    }

    protected static boolean canGenerate(LevelAccessor world, BlockPos pos) {
        return world.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWater);
    }
    protected static boolean canGenerateBase(WorldGenLevel world, BlockPos pos, int height) {
        if (canGenerateOrLava(world, pos)) {
            return false;
        }
        float f = 6.0f;
        float g = 6.0f / (float)height;
        for (float h = 0.0f; h < (float)Math.PI * 2; h += g) {
            int j;
            int i = (int)(Mth.cos(h) * (float)height);
            if (!canGenerateOrLava(world, pos.offset(i, 0, j = (int)(Mth.sin(h) * (float)height)))) continue;
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

    protected static boolean canGenerateOrLava(LevelAccessor world, BlockPos pos) {
        return world.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWaterOrLava);
    }

    static final class WindModifier {
        private final int y;
        @Nullable
        private final Vec3 wind;

        WindModifier(int y, RandomSource random, FloatProvider wind) {
            this.y = y;
            float f = wind.sample(random);
            float g = Mth.randomBetween(random, 0.0f, (float)Math.PI);
            this.wind = new Vec3(Mth.cos(g) * f, 0.0, Mth.sin(g) * f);
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
            Vec3 vec3d = this.wind.scale(i);
            return pos.offset(Mth.floor(vec3d.x), 0, Mth.floor(vec3d.z));
        }
    }
}

