package net.sevenstars.middleearth.world.features.deltas;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.Iterator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;

public class ModDeltaFeatures extends Feature<DeltaFeatureConfiguration> {
    private static final ImmutableList<Block> CANNOT_REPLACE_BLOCKS;
    private static final Direction[] DIRECTIONS;
    private static final double field_31501 = 0.9;

    public ModDeltaFeatures(Codec<DeltaFeatureConfiguration> codec) {
        super(codec);
    }

    public boolean place(FeaturePlaceContext<DeltaFeatureConfiguration> context) {
        boolean bl = false;
        RandomSource random = context.random();
        WorldGenLevel structureWorldAccess = context.level();
        DeltaFeatureConfiguration deltaFeatureConfig = (DeltaFeatureConfiguration)context.config();
        BlockPos blockPos = context.origin();
        boolean bl2 = random.nextDouble() < 0.9;
        int i = bl2 ? deltaFeatureConfig.rimSize().sample(random) : 0;
        int j = bl2 ? deltaFeatureConfig.rimSize().sample(random) : 0;
        boolean bl3 = bl2 && i != 0 && j != 0;
        int k = deltaFeatureConfig.size().sample(random);
        int l = deltaFeatureConfig.size().sample(random);
        int m = Math.max(k, l);
        Iterator var14 = BlockPos.withinManhattan(blockPos, k, 0, l).iterator();

        while(var14.hasNext()) {
            BlockPos blockPos2 = (BlockPos)var14.next();
            if (blockPos2.distManhattan(blockPos) > m) {
                break;
            }

            if (canPlace(structureWorldAccess, blockPos2, deltaFeatureConfig)) {
                if (bl3) {
                    bl = true;
                    this.setBlock(structureWorldAccess, blockPos2, deltaFeatureConfig.rim());
                }

                BlockPos blockPos3 = blockPos2.offset(i, 0, j);
                if (canPlace(structureWorldAccess, blockPos3, deltaFeatureConfig)) {
                    bl = true;
                    this.setBlock(structureWorldAccess, blockPos3, deltaFeatureConfig.contents());
                }
            }
        }

        return bl;
    }

    private static boolean canPlace(LevelAccessor world, BlockPos pos, DeltaFeatureConfiguration config) {
        //pos = pos.offset(Direction.Axis.Y, -1);
        BlockState blockState = world.getBlockState(pos);
        if (blockState.is(config.contents().getBlock())) {
            return false;
        } else if (CANNOT_REPLACE_BLOCKS.contains(blockState.getBlock())) {
            return false;
        } else {
            for (Direction currentDirection : DIRECTIONS) {
                BlockState directionBlockState = world.getBlockState(pos.relative(currentDirection));
                boolean isAir = directionBlockState.isAir();
                boolean isSolidBlock = directionBlockState.isRedstoneConductor(world, pos.relative(currentDirection));

                if (currentDirection == Direction.UP) {
                    if(!isAir)
                        return false;
                } else if (currentDirection == Direction.DOWN) {
                    if(!isSolidBlock)
                        return false;
                } else if (!isSolidBlock && !directionBlockState.is(Blocks.WATER)) {
                    return false;
                }
            }

            return true;
        }
    }

    static {
        CANNOT_REPLACE_BLOCKS = ImmutableList.of(Blocks.BEDROCK, Blocks.NETHER_BRICKS, Blocks.NETHER_BRICK_FENCE, Blocks.NETHER_BRICK_STAIRS, Blocks.NETHER_WART, Blocks.CHEST, Blocks.SPAWNER);
        DIRECTIONS = Direction.values();
    }
}
