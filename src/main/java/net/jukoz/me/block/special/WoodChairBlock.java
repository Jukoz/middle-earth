package net.jukoz.me.block.special;

import net.minecraft.block.*;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class WoodChairBlock extends SeatBlock implements Waterloggable {

    public WoodChairBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)){
            default -> Block.createCuboidShape(2, 0, 4, 14, 9, 16);
            case SOUTH -> Block.createCuboidShape(2, 0, 0, 14, 9, 12);
            case EAST -> Block.createCuboidShape(0, 0, 2, 12, 9, 14);
            case WEST -> Block.createCuboidShape(4, 0, 2, 16, 9, 14);
        };
    }
}
