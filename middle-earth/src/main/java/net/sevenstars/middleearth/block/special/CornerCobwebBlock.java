package net.sevenstars.middleearth.block.special;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import org.jetbrains.annotations.Nullable;

public class CornerCobwebBlock extends WebBlock {
    public static final BooleanProperty HANGING;
    private static final EnumProperty<Direction> FACING;

    public CornerCobwebBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(HANGING, false).setValue(FACING, Direction.EAST));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add (HANGING).add(FACING);
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getClickedFace();
        BlockPos blockPos = ctx.getClickedPos();
        return this.defaultBlockState()
                .setValue(FACING, ctx.getHorizontalDirection())
                .setValue(HANGING, !(direction != Direction.DOWN && (direction == Direction.UP || !(ctx.getClickLocation().y - (double)blockPos.getY() > 0.5))));
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos sidePos = pos.relative(state.getValue(FACING));
        BlockPos verticalPos = pos.relative(state.getValue(HANGING) ? Direction.UP : Direction.DOWN);

        return world.getBlockState(verticalPos).isRedstoneConductor(world, verticalPos) ||
                world.getBlockState(sidePos).isRedstoneConductor(world, sidePos);
    }

    @Override
    protected void neighborChanged(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        if (!world.isClientSide) {
            for (Direction direction : Direction.values()) {
                if (world.getFluidState(pos.relative(direction)).is(net.minecraft.tags.FluidTags.WATER)) {
                    world.destroyBlock(pos, true);
                    return;
                }
            }
        }
        super.neighborChanged(state, world, pos, sourceBlock, sourcePos, notify);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        Direction direction = state.getValue(FACING);
        switch (mirror) {
            case LEFT_RIGHT -> {
                if (direction.getAxis() != Direction.Axis.Z) break;
                return state.rotate(Rotation.CLOCKWISE_180);
            }
            case FRONT_BACK -> {
                if (direction.getAxis() != Direction.Axis.X) break;
                return state.rotate(Rotation.CLOCKWISE_180);
            }
        }
        return super.mirror(state, mirror);
    }

    static {
        HANGING = BlockStateProperties.HANGING;
        FACING = BlockStateProperties.HORIZONTAL_FACING;
    }
}
