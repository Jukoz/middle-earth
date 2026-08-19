package net.sevenstars.middleearth.block.special.crop;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.item.ResourceItemsME;

public class PipeweedCropBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    //protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 30.0, 14.0);

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.box(2.0, 0.0, 2.0, 14.0, 6.0, 14.0),  // Shape for age 0
            Block.box(2.0, 0.0, 2.0, 14.0, 12.0, 14.0), // Shape for age 1
            Block.box(2.0, 0.0, 2.0, 14.0, 22.0, 14.0), // Shape for age 2
            Block.box(2.0, 0.0, 2.0, 14.0, 30.0, 14.0)  // Shape for age 3
    };
    public PipeweedCropBlock(Properties settings) {
        super(settings);
    }
    @Override
    protected ItemLike getBaseSeedId() {
        return ResourceItemsME.PIPEWEED_SEEDS;
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return AGE_TO_SHAPE[this.getAge(state)];
    }
    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
    //actually poorly named method, this is called when the crop is bonemealed- only used for bonemealing item if u take a look :)
        return super.isBonemealSuccess(world, random, pos, state);
    }
    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        //MiddleEarth.LOGGER.logDebugMsg("PipeweedCropBlock randomTick");
        int currentAge = this.getAge(state);
        if (world.getBlockState(pos.above()).isAir()) {
            super.randomTick(state, world, pos, random);
        } else {
            if (currentAge < 1) {
                world.setBlock(pos, this.getStateForAge(currentAge + 1), 2);
            }
        }
    }
}
