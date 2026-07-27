package net.sevenstars.middleearth.block.special;

import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class PaperSheetBlock extends Block {
    private static final int MAX_PAPERS = 6;
    public static final IntegerProperty PAPERS = IntegerProperty.create("papers", 1, MAX_PAPERS);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape[] SHAPES_BY_PAPERS;

    public PaperSheetBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(PAPERS, 1));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, PAPERS);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_PAPERS[state.getValue(PAPERS) - 1];
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }  else {
            if (player.hasInfiniteMaterials()) {
                world.setBlockAndUpdate(pos, state.cycle(PAPERS));
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(itemStack.getItem() == this.asItem() && state.getValue(PAPERS) < MAX_PAPERS) {
            stack.consume(1, player);
            world.setBlockAndUpdate(pos, state.cycle(PAPERS));
            return ItemInteractionResult.CONSUME;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        boolean bl1 = !context.isSecondaryUseActive();
        boolean bl2 = context.getItemInHand().getItem() == this.asItem();
        boolean bl3 = state.getValue(PAPERS) <= MAX_PAPERS;
        return bl1 && bl2 && bl3 || super.canBeReplaced(state, context);
    }

    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (blockState.is(this)) {
            return blockState.cycle(PAPERS).setValue(HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
        } else {
            return Objects.requireNonNull(super.getStateForPlacement(ctx)).setValue(HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
        }
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() :
                super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    static {
        SHAPES_BY_PAPERS = new VoxelShape[]{
                Block.box(4, 0, 4, 12, 1, 12),
                Block.box(2, 0, 2, 14, 1, 14),
                Block.box(1, 0, 1, 15, 1, 15),
                Block.box(0, 0, 0, 16, 3, 16),
                Block.box(0, 0, 0, 16, 8, 16),
                Block.box(0, 0, 0, 16, 12, 16),
        };
    }
}
