package net.sevenstars.middleearth.world.features.boulder;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.sevenstars.middleearth.block.StoneBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;


public class BigBoulderFeature extends Feature<BigBoulderFeatureConfig> {
    private static final ImmutableList<Block> CANNOT_PLACE_ON_BLOCKS;
    private static final ImmutableList<Block> CANNOT_REPLACE_BLOCKS;

    public BigBoulderFeature(Codec<BigBoulderFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<BigBoulderFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();

        BlockState underBlock = structureWorldAccess.getBlockState(blockPos.down());
        if(underBlock.isIn(BlockTags.LEAVES) || CANNOT_PLACE_ON_BLOCKS.contains(underBlock.getBlock())) {
            return false;
        }

        var config = context.getConfig();
        float length = (float) Math.ceil(config.length) + (random.nextFloat() * config.randomSize);
        float width = (float) Math.ceil(config.width) + (random.nextFloat() * config.randomSize);
        float baseHeight = context.getConfig().height + (random.nextFloat() * config.randomSize);

        float forSize = Math.max(length, width) * 2f;
        float angle = random.nextFloat() * 180;

        for(int x = (int) -forSize; x <= forSize; ++x) {
            for(int z = (int) -forSize; z <= forSize; ++z) {
                for(int y = (int) -baseHeight-3; y <= baseHeight+3; ++y) {
                    BlockState mutableBlockState = structureWorldAccess.getBlockState(blockPos.mutableCopy().add(x, y, z));
                    if(mutableBlockState.isIn(BlockTags.LOGS) || CANNOT_REPLACE_BLOCKS.contains(mutableBlockState.getBlock())) {
                        continue;
                    } else if (this.isPointInside(x, y, z, length, width, baseHeight, angle, config.randomness, random)) {
                        BlockState blockState = config.blockStates.get(random.nextBetween(0, config.blockStates.size() - 1));
                        this.setBlockState(structureWorldAccess, blockPos.mutableCopy().add(x, y, z), blockState);
                    }
                }
            }
        }

        return true;
    }

    private boolean isPointInside(int x, int y, int z, float length, float width, float baseHeight, float angle, float randomNoise, Random random) {
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
        CANNOT_PLACE_ON_BLOCKS = ImmutableList.of(Blocks.LAVA, Blocks.BEDROCK, Blocks.WATER, StoneBlockSets.COBBLED_NURGON.base(),
                StoneBlockSets.SMOOTH_MEDGON.base(), Blocks.CHEST, Blocks.SPAWNER);
        CANNOT_REPLACE_BLOCKS = ImmutableList.of(Blocks.BEDROCK, StoneBlockSets.COBBLED_NURGON.base(),
                StoneBlockSets.SMOOTH_MEDGON.base(), Blocks.CHEST, Blocks.SPAWNER);
    }
}
