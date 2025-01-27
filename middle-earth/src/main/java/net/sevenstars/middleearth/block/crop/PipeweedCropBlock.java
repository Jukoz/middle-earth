package net.sevenstars.middleearth.block.crop;

import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class PipeweedCropBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = Properties.AGE_3;
    //protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 30.0, 14.0);

    private static final VoxelShape[] AGE_TO_SHAPE = new VoxelShape[]{
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 6.0, 14.0),  // Shape for age 0
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 12.0, 14.0), // Shape for age 1
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 22.0, 14.0), // Shape for age 2
            Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 30.0, 14.0)  // Shape for age 3
    };
    public PipeweedCropBlock(Settings settings) {
        super(settings);
    }
    @Override
    protected ItemConvertible getSeedsItem() {
        return ModResourceItems.PIPEWEED_SEEDS;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return AGE_TO_SHAPE[this.getAge(state)];
    }
    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
    //actually poorly named method, this is called when the crop is bonemealed- only used for bonemealing item if u take a look :)
        return super.canGrow(world, random, pos, state);
    }
    @Override
    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        //LoggerUtil.logDebugMsg("PipeweedCropBlock randomTick");
        int currentAge = this.getAge(state);
        if (world.getBlockState(pos.up()).isAir()) {
            super.randomTick(state, world, pos, random);
        } else {
            if (currentAge < 1) {
                world.setBlockState(pos, this.withAge(currentAge + 1), 2);
            }
        }
    }
}
