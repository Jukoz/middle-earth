package net.sevenstars.middleearth.world.features.columns;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.levelgen.Column;
import net.minecraft.world.level.levelgen.feature.DripstoneUtils;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.sevenstars.middleearth.block.special.pointedBlocks.PointedDolomiteBlock;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;

public class ClusterFeature extends Feature<ClusterFeatureConfig> {
    public ClusterFeature(Codec<ClusterFeatureConfig> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<ClusterFeatureConfig> context) {
        WorldGenLevel structureWorldAccess = context.level();
        BlockPos blockPos = context.origin();
        ClusterFeatureConfig ClusterFeatureConfig = context.config();
        RandomSource random = context.random();
        if (!canGenerate(structureWorldAccess, blockPos)) {
            return false;
        } else {
            int i = ClusterFeatureConfig.height.sample(random);
            float f = ClusterFeatureConfig.wetness.sample(random);
            float g = ClusterFeatureConfig.density.sample(random);
            int j = ClusterFeatureConfig.radius.sample(random);
            int k = ClusterFeatureConfig.radius.sample(random);

            for(int l = -j; l <= j; ++l) {
                for(int m = -k; m <= k; ++m) {
                    double d = this.dripstoneChance(j, k, l, m, ClusterFeatureConfig);
                    BlockPos blockPos2 = blockPos.offset(l, 0, m);
                    this.generate(structureWorldAccess, random, blockPos2, l, m, f, d, i, g, ClusterFeatureConfig);
                }
            }

            return true;
        }
    }

    private void generate(WorldGenLevel world, RandomSource random, BlockPos pos, int localX, int localZ, float wetness,
                          double dripstoneChance, int height, float density, ClusterFeatureConfig config) {
        Optional<Column> optional = Column.scan(world, pos, config.floorToCeilingSearchRange, DripstoneUtils::isEmptyOrWater, DripstoneUtils::isNeitherEmptyNorWater);
        if (!optional.isEmpty()) {
            OptionalInt optionalInt = ((Column)optional.get()).getCeiling();
            OptionalInt optionalInt2 = ((Column)optional.get()).getFloor();
            if (!optionalInt.isEmpty() || !optionalInt2.isEmpty()) {
                boolean bl = random.nextFloat() < wetness;
                Column caveSurface;
                if (bl && optionalInt2.isPresent() && this.canWaterSpawn(world, pos.atY(optionalInt2.getAsInt()), config.blockState, config.pointedBlockState)) {
                    int i = optionalInt2.getAsInt();
                    caveSurface = ((Column)optional.get()).withFloor(OptionalInt.of(i - 1));
                    world.setBlock(pos.atY(i), Blocks.WATER.defaultBlockState(), 2);
                } else {
                    caveSurface = (Column)optional.get();
                }

                OptionalInt optionalInt3 = caveSurface.getFloor();
                boolean bl2 = random.nextDouble() < dripstoneChance;
                int l;
                int j;
                if (optionalInt.isPresent() && bl2 && !this.isLava(world, pos.atY(optionalInt.getAsInt()))) {
                    j = config.dripstoneBlockLayerThickness.sample(random);
                    this.placeDripstoneBlocks(world, pos.atY(optionalInt.getAsInt()), j, Direction.UP, config.blockState);
                    int k;
                    if (optionalInt3.isPresent()) {
                        k = Math.min(height, optionalInt.getAsInt() - optionalInt3.getAsInt());
                    } else {
                        k = height;
                    }

                    l = this.getHeight(random, localX, localZ, density, k, config);
                } else {
                    l = 0;
                }

                boolean bl3 = random.nextDouble() < dripstoneChance;
                int stalagmiteHeight;
                if (optionalInt3.isPresent() && bl3 && !this.isLava(world, pos.atY(optionalInt3.getAsInt()))) {
                    stalagmiteHeight = config.dripstoneBlockLayerThickness.sample(random);
                    this.placeDripstoneBlocks(world, pos.atY(optionalInt3.getAsInt()), stalagmiteHeight, Direction.DOWN, config.blockState);
                    if (optionalInt.isPresent()) {
                        j = Math.max(0, l + Mth.randomBetweenInclusive(random, -config.maxStalagmiteStalactiteHeightDiff, config.maxStalagmiteStalactiteHeightDiff));
                    } else {
                        j = this.getHeight(random, localX, localZ, density, height, config);
                    }
                } else {
                    j = 0;
                }

                int stalactiteHeight;
                if (optionalInt.isPresent() && optionalInt3.isPresent() && optionalInt.getAsInt() - l <= optionalInt3.getAsInt() + j) {
                    int n = optionalInt3.getAsInt();
                    int o = optionalInt.getAsInt();
                    int p = Math.max(o - l, n + 1);
                    int q = Math.min(n + j, o - 1);
                    int r = Mth.randomBetweenInclusive(random, p, q + 1);
                    int s = r - 1;
                    stalagmiteHeight = o - r;
                    stalactiteHeight = s - n;
                } else {
                    stalagmiteHeight = l;
                    stalactiteHeight = j;
                }

                boolean bl4 = random.nextBoolean() && stalagmiteHeight > 0 && stalactiteHeight > 0 && caveSurface.getHeight().isPresent() && stalagmiteHeight + stalactiteHeight == caveSurface.getHeight().getAsInt();
                if (optionalInt.isPresent()) {
                    generatePointedBlock(world, pos.atY(optionalInt.getAsInt() - 1), Direction.DOWN, stalagmiteHeight, bl4, config.pointedBlockState);
                }

                if (optionalInt3.isPresent()) {
                    generatePointedBlock(world, pos.atY(optionalInt3.getAsInt() + 1), Direction.UP, stalactiteHeight, bl4, config.pointedBlockState);
                }
            }
        }
    }

    public static boolean generateBlock(LevelAccessor world, BlockPos pos, BlockState stoneBlockState) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.is(BlockTags.DRIPSTONE_REPLACEABLE)) {
            world.setBlock(pos, stoneBlockState, 2);
            return true;
        } else {
            return false;
        }
    }

    protected static void generatePointedBlock(LevelAccessor world, BlockPos pos, Direction direction, int height, boolean merge, BlockState blockState) {
        if (canReplace(world.getBlockState(pos.relative(direction.getOpposite())))) {
            BlockPos.MutableBlockPos mutable = pos.mutable();
            getDripstoneThickness(direction, height, merge, state -> {
                if (state.is(blockState.getBlock())) {
                    state = state.setValue(PointedDripstoneBlock.WATERLOGGED, world.isWaterAt(mutable));
                }

                world.setBlock(mutable, state, Block.UPDATE_CLIENTS);
                mutable.move(direction);
            }, blockState);
        }
    }

    public static void getDripstoneThickness(Direction direction, int height, boolean merge, Consumer<BlockState> callback, BlockState blockState) {
        if (height >= 3) {
            callback.accept(getState(direction, DripstoneThickness.BASE, blockState));
            for(int i = 0; i < height - 3; ++i) {
                callback.accept(getState(direction, DripstoneThickness.MIDDLE, blockState));
            }
        }
        if (height >= 2) {
            callback.accept(getState(direction, DripstoneThickness.FRUSTUM, blockState));
        }
        if (height >= 1) {
            callback.accept(getState(direction, merge ? DripstoneThickness.TIP_MERGE : DripstoneThickness.TIP, blockState));
        }
    }

    public static boolean canGenerate(BlockState state) {
        return state.isAir() || state.is(Blocks.WATER);
    }

    public static boolean canGenerate(LevelAccessor world, BlockPos pos) {
        return world.isStateAtPosition(pos, ClusterFeature::canGenerate);
    }

    public static boolean canGenerateOrLava(LevelAccessor world, BlockPos pos) {
        return world.isStateAtPosition(pos, DripstoneUtils::isEmptyOrWaterOrLava);
    }

    public static boolean canReplace(BlockState state) {
        return state.is(BlockTags.BASE_STONE_OVERWORLD);
    }

    private static BlockState getState(Direction direction, DripstoneThickness thickness, BlockState blockState) {
        return (blockState.setValue(PointedDolomiteBlock.VERTICAL_DIRECTION, direction)).setValue(PointedDolomiteBlock.THICKNESS, thickness);
    }

    private boolean isLava(LevelReader world, BlockPos pos) {
        return world.getBlockState(pos).is(Blocks.LAVA);
    }

    private int getHeight(RandomSource random, int localX, int localZ, float density, int height, ClusterFeatureConfig config) {
        if (random.nextFloat() > density) {
            return 0;
        } else {
            int i = Math.abs(localX) + Math.abs(localZ);
            float f = (float)Mth.clampedMap((double)i, 0.0, (double)config.maxDistanceFromCenterAffectingHeightBias, height / 2.0, 0.0);
            return (int)clampedGaussian(random, 0.0F, height, f, config.heightDeviation);
        }
    }

    private boolean canWaterSpawn(WorldGenLevel world, BlockPos pos, BlockState stoneBlockState, BlockState pointedBlockState) {
        BlockState blockState = world.getBlockState(pos);
        if (!blockState.is(Blocks.WATER) && !blockState.is(stoneBlockState.getBlock()) && !blockState.is(pointedBlockState.getBlock())) {
            if (world.getBlockState(pos.above()).getFluidState().is(FluidTags.WATER)) {
                return false;
            } else {
                Iterator var4 = Direction.Plane.HORIZONTAL.iterator();

                Direction direction;
                do {
                    if (!var4.hasNext()) {
                        return this.isStoneOrWater(world, pos.below());
                    }

                    direction = (Direction)var4.next();
                } while(this.isStoneOrWater(world, pos.relative(direction)));

                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isStoneOrWater(LevelAccessor world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.is(BlockTags.BASE_STONE_OVERWORLD) || blockState.getFluidState().is(FluidTags.WATER);
    }

    private void placeDripstoneBlocks(WorldGenLevel world, BlockPos pos, int height, Direction direction, BlockState blockState) {
        BlockPos.MutableBlockPos mutable = pos.mutable();

        for(int i = 0; i < height; ++i) {
            if (!generateBlock(world, mutable, blockState)) {
                return;
            }

            mutable.move(direction);
        }

    }

    private double dripstoneChance(int radiusX, int radiusZ, int localX, int localZ, ClusterFeatureConfig config) {
        int i = radiusX - Math.abs(localX);
        int j = radiusZ - Math.abs(localZ);
        int k = Math.min(i, j);
        return (double)Mth.clampedMap((float)k, 0.0F, (float)config.maxDistanceFromCenterAffectingChanceOfDripstoneColumn, config.chanceOfDripstoneColumnAtMaxDistanceFromCenter, 1.0F);
    }

    private static float clampedGaussian(RandomSource random, float min, float max, float mean, float deviation) {
        return ClampedNormalFloat.sample(random, mean, deviation, min, max);
    }
}
