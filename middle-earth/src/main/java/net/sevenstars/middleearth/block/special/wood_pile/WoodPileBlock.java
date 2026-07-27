package net.sevenstars.middleearth.block.special.wood_pile;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class WoodPileBlock  extends BaseEntityBlock implements EntityBlock {
    public static final IntegerProperty STAGE = IntegerProperty.create("stage", 0, 3);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape STAGE_0, STAGE_1, STAGE_2, STAGE_3;
    public static final MapCodec<WoodPileBlock> CODEC = FurnaceBlock.simpleCodec(WoodPileBlock::new);

    public WoodPileBlock(Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()).setValue(HORIZONTAL_FACING, Direction.NORTH).setValue(STAGE, 0));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, STAGE);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite()).setValue(STAGE, 0);
    }

    @Override
    protected void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        Containers.dropContentsOnDestroy(state, newState, world, pos);
        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }  else {
            if (player.hasInfiniteMaterials() && player.isShiftKeyDown()) {
                world.setBlockAndUpdate(pos, state.cycle(STAGE));
            } else {
                if (addStackRightClick(world, pos, player, player.getUsedItemHand())) {
                    MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
                    if (screenHandlerFactory != null) {
                        player.openMenu(screenHandlerFactory);
                    }
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    public boolean addStackRightClick(Level world, BlockPos pos, Player player, InteractionHand hand){
        Container blockEntity = (Container) world.getBlockEntity(pos);

        if (!player.getItemInHand(hand).isEmpty() && player.getItemInHand(hand).is(ItemTags.LOGS)) {
            for(int i = 0;i <= 8; i++){
                if (blockEntity.getItem(i).isEmpty()) {
                    blockEntity.setItem(i, player.getItemInHand(hand).copy());
                    player.getItemInHand(hand).setCount(0);
                }
            }
        } else {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WoodPileBlockEntity(pos,state);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(STAGE)) {
            case 3 -> STAGE_3;
            case 2 -> STAGE_2;
            case 1 -> STAGE_1;
            default -> STAGE_0;
        };
    }

    static {
        STAGE_0 = Shapes.or(
                Block.box(0, 0, 0, 16, 4, 16));

        STAGE_1 = Shapes.or(
                Block.box(0, 0, 0, 16, 7, 16));

        STAGE_2 = Shapes.or(
                Block.box(0, 0, 0, 16, 9, 16));

        STAGE_3 = Block.box(0, 0, 0, 16, 16, 16);
    }
}
