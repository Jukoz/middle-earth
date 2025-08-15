package net.sevenstars.middleearth.block.special;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.TallFlowerBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RotatableTallFlowerBlock extends TallFlowerBlock {
    public static final EnumProperty<Direction> HORIZONTAL_FACING = Properties.HORIZONTAL_FACING;

    public RotatableTallFlowerBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder.add(HORIZONTAL_FACING));
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        World world = ctx.getWorld();
        if(blockPos.getY() < world.getTopYInclusive() - 1 && world.getBlockState(blockPos.up()).canReplace(ctx)){
            return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(HALF, DoubleBlockHalf.LOWER);
        } else {
            return null;
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), (BlockState)state.with(HALF, DoubleBlockHalf.UPPER), 3);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch (mirror) {
            case LEFT_RIGHT -> {
                if (direction.getAxis() != Direction.Axis.Z) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
            case FRONT_BACK -> {
                if (direction.getAxis() != Direction.Axis.X) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
        }
        return super.mirror(state, mirror);
    }
}
