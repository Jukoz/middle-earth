package net.sevenstars.middleearth.block.special.wood_pile;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class WoodPileBlock  extends BlockWithEntity implements BlockEntityProvider {
    public static final IntProperty STAGE = IntProperty.of("stage", 0, 3);
    public static final DirectionProperty HORIZONTAL_FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape STAGE_0, STAGE_1, STAGE_2, STAGE_3;
    public static final MapCodec<WoodPileBlock> CODEC = FurnaceBlock.createCodec(WoodPileBlock::new);

    public WoodPileBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(HORIZONTAL_FACING, Direction.NORTH).with(STAGE, 0));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, STAGE);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(STAGE, 0);
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if(state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if(blockEntity instanceof WoodPileBlockEntity woodPileBlockEntity) {
                ItemScatterer.spawn(world, pos, woodPileBlockEntity);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }  else {
            if (player.isInCreativeMode() && player.isSneaking()) {
                world.setBlockState(pos, state.cycle(STAGE));
            } else {
                if (addStackRightClick(world, pos, player, player.getActiveHand())) {
                    NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                    if (screenHandlerFactory != null) {
                        player.openHandledScreen(screenHandlerFactory);
                    }
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    public boolean addStackRightClick(World world, BlockPos pos, PlayerEntity player, Hand hand){
        Inventory blockEntity = (Inventory) world.getBlockEntity(pos);

        if (!player.getStackInHand(hand).isEmpty() && player.getStackInHand(hand).isIn(ItemTags.LOGS)) {
            for(int i = 0;i <= 8; i++){
                if (blockEntity.getStack(i).isEmpty()) {
                    blockEntity.setStack(i, player.getStackInHand(hand).copy());
                    player.getStackInHand(hand).setCount(0);
                }
            }
        } else {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new WoodPileBlockEntity(pos,state);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(STAGE)) {
            case 3 -> STAGE_3;
            case 2 -> STAGE_2;
            case 1 -> STAGE_1;
            default -> STAGE_0;
        };
    }

    static {
        STAGE_0 = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 4, 16));

        STAGE_1 = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 7, 16));

        STAGE_2 = VoxelShapes.union(
                Block.createCuboidShape(0, 0, 0, 16, 9, 16));

        STAGE_3 = Block.createCuboidShape(0, 0, 0, 16, 16, 16);
    }
}