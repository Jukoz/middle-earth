package net.sevenstars.middleearth.world.features.boulder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;


public class BigBoulderFeature extends Feature<BigBoulderFeatureConfig> {
    private static final ImmutableList<Block> CANNOT_PLACE_ON_BLOCKS;
    private static final ImmutableList<Block> CANNOT_REPLACE_BLOCKS;

    public BigBoulderFeature(Codec<BigBoulderFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean place(FeaturePlaceContext<BigBoulderFeatureConfig> context) {
        BlockPos blockPos = context.origin();
        WorldGenLevel structureWorldAccess = context.level();
        RandomSource random = context.random();

        BlockState underBlock = structureWorldAccess.getBlockState(blockPos.below());
        if(underBlock.is(BlockTags.LEAVES) || CANNOT_PLACE_ON_BLOCKS.contains(underBlock.getBlock())) {
            return false;
        }

        var config = context.config();
        float length = (float) Math.ceil(config.length) + (random.nextFloat() * config.randomSize);
        float width = (float) Math.ceil(config.width) + (random.nextFloat() * config.randomSize);
        float baseHeight = context.config().height + (random.nextFloat() * config.randomSize);

        float forSize = Math.max(length, width) * 2f;
        float angle = random.nextFloat() * 180;

        for(int x = (int) -forSize; x <= forSize; ++x) {
            for(int z = (int) -forSize; z <= forSize; ++z) {
                for(int y = (int) -baseHeight-3; y <= baseHeight+3; ++y) {
                    BlockPos offsetBlockPos = blockPos.mutable().offset(x, y, z);
                    BlockState mutableBlockState = structureWorldAccess.getBlockState(offsetBlockPos);
                    if(!mutableBlockState.isAir() && !mutableBlockState.isRedstoneConductor(structureWorldAccess, offsetBlockPos.mutable().offset(0, 1, 0)) ||
                            mutableBlockState.is(BlockTags.LOGS) || CANNOT_REPLACE_BLOCKS.contains(mutableBlockState.getBlock())) {
                        continue;
                    } else if (this.isPointInside(x, y, z, length, width, baseHeight, angle, config.randomness, random)) {
                        BlockState blockState = config.blockStates.get(random.nextIntBetweenInclusive(0, config.blockStates.size() - 1));
                        this.setBlock(structureWorldAccess, blockPos.mutable().offset(x, y, z), blockState);
                    }
                }
            }
        }

        return true;
    }

    private boolean isPointInside(int x, int y, int z, float length, float width, float baseHeight, float angle, float randomNoise, RandomSource random) {
        float randomness = -randomNoise + (random.nextFloat() * randomNoise * 2);

        float squareLength = (length + randomness) * (length + randomness);
        float squareWidth = (width + randomness) * (width + randomness);
        float squareHeight = (baseHeight + randomness) * (baseHeight + randomness);

        float radians = (float) Math.toRadians(angle);

        float rotatedX = (float) (x * Math.cos(radians) - z * Math.sin(radians));
        float rotatedZ = (float) (x * Math.sin(radians) + z * Math.cos(radians));

        float deltaX = (float)(rotatedX*rotatedX) / squareLength;
        float deltaY = (float)(y*y) / squareHeight;
        float deltaZ = (float)(rotatedZ*rotatedZ) / squareWidth;

        return (deltaX + deltaY + deltaZ <= 1);
    }

    static {
        CANNOT_PLACE_ON_BLOCKS = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.WATER, StoneBlockSets.NURGON_SET.cobblestoneBlocks.base(),
                StoneBlockSets.MEDGON_SET.smoothBlocks.base(), Blocks.CHEST, Blocks.SPAWNER);
        CANNOT_REPLACE_BLOCKS = ImmutableList.of(Blocks.BEDROCK, StoneBlockSets.NURGON_SET.cobblestoneBlocks.base(),
                StoneBlockSets.MEDGON_SET.smoothBlocks.base(), Blocks.CHEST, Blocks.BARREL, Blocks.SPAWNER, ModDecorativeBlocks.STRUCTURE_MANAGER,
                ModDecorativeBlocks.ORC_STRUCTURE_MANAGER, ModDecorativeBlocks.STRUCTURE_NEST, ModDecorativeBlocks.THIN_BARREL, ModDecorativeBlocks.SMALL_CRATE);
    }
}
