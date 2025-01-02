package net.jukoz.me.world.features.columns;

import com.mojang.serialization.Codec;
import net.jukoz.me.block.special.PointedDolomiteBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.enums.Thickness;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.floatprovider.ClampedNormalFloatProvider;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.CaveSurface;
import net.minecraft.world.gen.feature.util.DripstoneHelper;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.function.Consumer;

public class ClusterFeature extends Feature<ClusterFeatureConfig> {
    public ClusterFeature(Codec<ClusterFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<ClusterFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        ClusterFeatureConfig ClusterFeatureConfig = context.getConfig();
        Random random = context.getRandom();
        if (!canGenerate(structureWorldAccess, blockPos)) {
            return false;
        } else {
            int i = ClusterFeatureConfig.height.get(random);
            float f = ClusterFeatureConfig.wetness.get(random);
            float g = ClusterFeatureConfig.density.get(random);
            int j = ClusterFeatureConfig.radius.get(random);
            int k = ClusterFeatureConfig.radius.get(random);

            for(int l = -j; l <= j; ++l) {
                for(int m = -k; m <= k; ++m) {
                    double d = this.dripstoneChance(j, k, l, m, ClusterFeatureConfig);
                    BlockPos blockPos2 = blockPos.add(l, 0, m);
                    this.generate(structureWorldAccess, random, blockPos2, l, m, f, d, i, g, ClusterFeatureConfig);
                }
            }

            return true;
        }
    }

    private void generate(StructureWorldAccess world, Random random, BlockPos pos, int localX, int localZ, float wetness, 
                          double dripstoneChance, int height, float density, ClusterFeatureConfig config) {
        Optional<CaveSurface> optional = CaveSurface.create(world, pos, config.floorToCeilingSearchRange, DripstoneHelper::canGenerate, DripstoneHelper::cannotGenerate);
        if (!optional.isEmpty()) {
            OptionalInt optionalInt = ((CaveSurface)optional.get()).getCeilingHeight();
            OptionalInt optionalInt2 = ((CaveSurface)optional.get()).getFloorHeight();
            if (!optionalInt.isEmpty() || !optionalInt2.isEmpty()) {
                boolean bl = random.nextFloat() < wetness;
                CaveSurface caveSurface;
                if (bl && optionalInt2.isPresent() && this.canWaterSpawn(world, pos.withY(optionalInt2.getAsInt()), config.blockState, config.pointedBlockState)) {
                    int i = optionalInt2.getAsInt();
                    caveSurface = ((CaveSurface)optional.get()).withFloor(OptionalInt.of(i - 1));
                    world.setBlockState(pos.withY(i), Blocks.WATER.getDefaultState(), 2);
                } else {
                    caveSurface = (CaveSurface)optional.get();
                }

                OptionalInt optionalInt3 = caveSurface.getFloorHeight();
                boolean bl2 = random.nextDouble() < dripstoneChance;
                int l;
                int j;
                if (optionalInt.isPresent() && bl2 && !this.isLava(world, pos.withY(optionalInt.getAsInt()))) {
                    j = config.dripstoneBlockLayerThickness.get(random);
                    this.placeDripstoneBlocks(world, pos.withY(optionalInt.getAsInt()), j, Direction.UP, config.blockState);
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
                int m;
                if (optionalInt3.isPresent() && bl3 && !this.isLava(world, pos.withY(optionalInt3.getAsInt()))) {
                    m = config.dripstoneBlockLayerThickness.get(random);
                    this.placeDripstoneBlocks(world, pos.withY(optionalInt3.getAsInt()), m, Direction.DOWN, config.blockState);
                    if (optionalInt.isPresent()) {
                        j = Math.max(0, l + MathHelper.nextBetween(random, -config.maxStalagmiteStalactiteHeightDiff, config.maxStalagmiteStalactiteHeightDiff));
                    } else {
                        j = this.getHeight(random, localX, localZ, density, height, config);
                    }
                } else {
                    j = 0;
                }

                int t;
                if (optionalInt.isPresent() && optionalInt3.isPresent() && optionalInt.getAsInt() - l <= optionalInt3.getAsInt() + j) {
                    int n = optionalInt3.getAsInt();
                    int o = optionalInt.getAsInt();
                    int p = Math.max(o - l, n + 1);
                    int q = Math.min(n + j, o - 1);
                    int r = MathHelper.nextBetween(random, p, q + 1);
                    int s = r - 1;
                    m = o - r;
                    t = s - n;
                } else {
                    m = l;
                    t = j;
                }

                boolean bl4 = random.nextBoolean() && m > 0 && t > 0 && caveSurface.getOptionalHeight().isPresent() && m + t == caveSurface.getOptionalHeight().getAsInt();
                if (optionalInt.isPresent()) {
                    generatePointedBlock(world, pos.withY(optionalInt.getAsInt() - 1), Direction.DOWN, m, bl4, config.pointedBlockState);
                }

                if (optionalInt3.isPresent()) {
                    generatePointedBlock(world, pos.withY(optionalInt3.getAsInt() + 1), Direction.UP, t, bl4, config.pointedBlockState);
                }

            }
        }
    }

    public static boolean generateBlock(WorldAccess world, BlockPos pos, BlockState stoneBlockState) {
        BlockState blockState = world.getBlockState(pos);
        if (blockState.isIn(BlockTags.DRIPSTONE_REPLACEABLE_BLOCKS)) {
            world.setBlockState(pos, stoneBlockState, 2);
            return true;
        } else {
            return false;
        }
    }

    public static void generatePointedBlock(WorldAccess world, BlockPos pos, Direction direction, int height, boolean merge, BlockState blockState) {
        if (canReplace(world.getBlockState(pos.offset(direction.getOpposite())))) {
            BlockPos.Mutable mutable = pos.mutableCopy();
            getDripstoneThickness(direction, height, merge, (state) -> {
                if (state.isOf(blockState.getBlock())) {
                    state = state.with(PointedDolomiteBlock.WATERLOGGED, world.isWater(mutable));
                }

                world.setBlockState(mutable, state, 2);
                mutable.move(direction);
            }, blockState);
        }
    }

    public static void getDripstoneThickness(Direction direction, int height, boolean merge, Consumer<BlockState> callback, BlockState blockState) {
        if (height >= 3) {
            callback.accept(getState(direction, Thickness.BASE, blockState));

            for(int i = 0; i < height - 3; ++i) {
                callback.accept(getState(direction, Thickness.MIDDLE, blockState));
            }
        }

        if (height >= 2) {
            callback.accept(getState(direction, Thickness.FRUSTUM, blockState));
        }

        if (height >= 1) {
            callback.accept(getState(direction, merge ? Thickness.TIP_MERGE : Thickness.TIP, blockState));
        }

    }

    public static boolean canGenerate(BlockState state) {
        return state.isAir() || state.isOf(Blocks.WATER);
    }

    public static boolean canGenerate(WorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, ClusterFeature::canGenerate);
    }

    public static boolean canGenerateOrLava(WorldAccess world, BlockPos pos) {
        return world.testBlockState(pos, DripstoneHelper::canGenerateOrLava);
    }

    public static boolean canReplace(BlockState state) {
        return state.isIn(BlockTags.BASE_STONE_OVERWORLD);
    }

    private static BlockState getState(Direction direction, Thickness thickness, BlockState blockState) {
        return (blockState.with(PointedDolomiteBlock.VERTICAL_DIRECTION, direction)).with(PointedDolomiteBlock.THICKNESS, thickness);
    }

    private boolean isLava(WorldView world, BlockPos pos) {
        return world.getBlockState(pos).isOf(Blocks.LAVA);
    }

    private int getHeight(Random random, int localX, int localZ, float density, int height, ClusterFeatureConfig config) {
        if (random.nextFloat() > density) {
            return 0;
        } else {
            int i = Math.abs(localX) + Math.abs(localZ);
            float f = (float)MathHelper.clampedMap((double)i, 0.0, (double)config.maxDistanceFromCenterAffectingHeightBias, (double)height / 2.0, 0.0);
            return (int)clampedGaussian(random, 0.0F, (float)height, f, (float)config.heightDeviation);
        }
    }

    private boolean canWaterSpawn(StructureWorldAccess world, BlockPos pos, BlockState stoneBlockState, BlockState pointedBlockState) {
        BlockState blockState = world.getBlockState(pos);
        if (!blockState.isOf(Blocks.WATER) && !blockState.isOf(stoneBlockState.getBlock()) && !blockState.isOf(pointedBlockState.getBlock())) {
            if (world.getBlockState(pos.up()).getFluidState().isIn(FluidTags.WATER)) {
                return false;
            } else {
                Iterator var4 = Direction.Type.HORIZONTAL.iterator();

                Direction direction;
                do {
                    if (!var4.hasNext()) {
                        return this.isStoneOrWater(world, pos.down());
                    }

                    direction = (Direction)var4.next();
                } while(this.isStoneOrWater(world, pos.offset(direction)));

                return false;
            }
        } else {
            return false;
        }
    }

    private boolean isStoneOrWater(WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.isIn(BlockTags.BASE_STONE_OVERWORLD) || blockState.getFluidState().isIn(FluidTags.WATER);
    }

    private void placeDripstoneBlocks(StructureWorldAccess world, BlockPos pos, int height, Direction direction, BlockState blockState) {
        BlockPos.Mutable mutable = pos.mutableCopy();

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
        return (double)MathHelper.clampedMap((float)k, 0.0F, (float)config.maxDistanceFromCenterAffectingChanceOfDripstoneColumn, config.chanceOfDripstoneColumnAtMaxDistanceFromCenter, 1.0F);
    }

    private static float clampedGaussian(Random random, float min, float max, float mean, float deviation) {
        return ClampedNormalFloatProvider.get(random, mean, deviation, min, max);
    }
}
