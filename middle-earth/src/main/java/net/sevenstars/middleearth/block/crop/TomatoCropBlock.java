package net.sevenstars.middleearth.block.crop;

import net.sevenstars.middleearth.item.ModResourceItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TomatoCropBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = Properties.AGE_3;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 30.0, 14.0);

    public TomatoCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return ModResourceItems.TOMATO_SEEDS;
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
        if(super.getAge(state) > 1){
            return SHAPE;
        }
        return super.getOutlineShape(state, world, pos, context);
    }
    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        if(super.getAge(state) > 1) {
            return world.getBlockState(pos).canPlaceAt(world, pos.add(0,1,0));
            // todo : make it so whenever the tomato wants to grow, it checks the origin above to see if you can place anything
        }
        return super.canGrow(world, random, pos, state);
    }
}
