package net.sevenstars.middleearth.world.features.columns;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;

public class WillowVinesFeature extends Feature<DefaultFeatureConfig> {
    public WillowVinesFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess structureWorldAccess = context.getWorld();
        BlockPos blockPos = context.getOrigin();
        Random random = context.getRandom();
        if (!structureWorldAccess.isAir(blockPos)) {
            return false;
        } else {
            BlockState blockState = structureWorldAccess.getBlockState(blockPos.up());
            if (!blockState.isOf(WoodBlockSets.WILLOW_SET.leaves) && !blockState.isSolidBlock(context.getWorld(), blockPos.up())) {
                return false;
            } else {
                this.generateVinesInArea(structureWorldAccess, random, blockPos);
                return true;
            }
        }
    }

    private void generateVinesInArea(WorldAccess world, Random random, BlockPos pos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int i = 0; i < 32; ++i) {
            mutable.set(pos, random.nextInt(8) - random.nextInt(8), random.nextInt(2) - random.nextInt(7), random.nextInt(8) - random.nextInt(8));

            if (world.isAir(mutable) && validateRoot(world, mutable)) {
                int length = MathHelper.nextInt(random, 1, 8);

                if (random.nextInt(6) == 0) { // very long vine
                    length *= 2;
                }

                if (random.nextInt(5) == 0) { // short vine
                    length = 1;
                }

                generateVineColumn(world, mutable, length);
            }
        }
    }

    public static void generateVineColumn(WorldAccess world, BlockPos.Mutable pos, int length) {
        for(int i = 0; i <= length; ++i) {
            if (world.isAir(pos)) {
                BlockState blockStateAbove = world.getBlockState(pos.up());

                if(blockStateAbove.isAir())
                    break;

                if(blockStateAbove.isOf(WoodBlockSets.WILLOW_SET.leaves)){
                    world.setBlockState(pos.up(), WoodBlockSets.WILLOW_SET.leaves.getDefaultState().with(LeavesBlock.PERSISTENT, false), 2);
                }

                if (i == length || !world.getBlockState(pos.down()).isAir()) {
                    world.setBlockState(pos, ModNatureBlocks.WILLOW_VINES.getDefaultState().with(Properties.TIP, true), 2);
                    break;
                }

                world.setBlockState(pos, ModNatureBlocks.WILLOW_VINES.getDefaultState().with(Properties.TIP, false), 2);
            }

            pos.move(Direction.DOWN);
        }
    }

    private static boolean validateRoot(WorldAccess world, BlockPos.Mutable mutable) {
        BlockState blockState = world.getBlockState(mutable.up());
        return (blockState.isOf(WoodBlockSets.WILLOW_SET.logBlocks.log()) || blockState.isOf(WoodBlockSets.WILLOW_SET.leaves));
    }
}
