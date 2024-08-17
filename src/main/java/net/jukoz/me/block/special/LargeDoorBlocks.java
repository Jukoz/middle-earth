package net.jukoz.me.block.special;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class LargeDoorBlocks extends Block {

    public static final IntProperty PART = IntProperty.of("part", 0, 127);
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty OPEN = Properties.OPEN;;
    public static int doorHeight = 2;
    public static int doorWidth = 2;

    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);


    public LargeDoorBlocks(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH)).with(PART, 1)).with(OPEN, false))));
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int part = state.get(PART);

        if(neighborState.getBlock() instanceof LargeDoorBlocks){
            if(neighborState.get(OPEN)){
                state = state.with(OPEN, true);
            }
            if(!neighborState.get(OPEN)){
                state = state.with(OPEN, false);
            }
        }

        return state;
    }

    /*if(state.get(PART) == 1){
            if(!state.get(OPEN)){
                state = (BlockState)state.cycle(OPEN);
                world.setBlockState(pos, state, 10);
                world.setBlockState(pos.up(), state.with(PART, 3), 10);

                BlockPos blockPos = pos.offset((Direction)state.get(FACING).rotateYClockwise());
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
                world.setBlockState(blockPos.up(), Blocks.AIR.getDefaultState(), 35);
                blockPos = pos.offset((Direction)state.get(FACING));
                world.setBlockState(blockPos, (BlockState)state.with(PART, 2), 3);
                world.setBlockState(blockPos.up(), (BlockState)state.with(PART, 4), 3);
            } else {
                state = (BlockState)state.cycle(OPEN);
                world.setBlockState(pos, state, 10);
                world.setBlockState(pos.up(), state.with(PART, 3), 10);

                BlockPos blockPos = pos.offset((Direction)state.get(FACING));
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 3);
                world.setBlockState(blockPos.up(), Blocks.AIR.getDefaultState(), 3);

                blockPos = pos.offset((Direction)state.get(FACING).rotateYClockwise());
                world.setBlockState(blockPos, (BlockState)state.with(PART, 2), 35);
                world.setBlockState(blockPos.up(), (BlockState)state.with(PART, 4), 35);
            }
            this.playOpenCloseSound(player, world, pos, (Boolean)state.get(OPEN));

            world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        }*/

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{PART, FACING, OPEN});
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(direction.rotateYClockwise());
        World world = ctx.getWorld();
        return world.getBlockState(blockPos2).canReplace(ctx) && world.getWorldBorder().contains(blockPos2) ? (BlockState)this.getDefaultState().with(FACING, direction).with(PART, 1) : null;
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (!world.isClient){
            BlockPos blockPos = pos.offset((Direction)state.get(FACING).rotateYClockwise());
            world.setBlockState(blockPos, (BlockState)state.with(PART, 2), 3);
            world.setBlockState(pos.up(), (BlockState)state.with(PART, 3), 3);
            world.setBlockState(blockPos.up(), (BlockState)state.with(PART, 4), 3);
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            BlockPos blockPos = pos.offset((Direction)state.get(FACING).rotateYClockwise());
            world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
            world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 35);
            world.setBlockState(blockPos.up(), Blocks.AIR.getDefaultState(), 35);
        }
        return super.onBreak(world, pos, state, player);
    }

    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        state = (BlockState)state.cycle(OPEN);
        world.setBlockState(pos, state, 10);
        this.playOpenCloseSound(player, world, pos, (Boolean)state.get(OPEN));
        world.emitGameEvent(player, this.isOpen(state) ? GameEvent.BLOCK_OPEN : GameEvent.BLOCK_CLOSE, pos);
        return ActionResult.success(world.isClient);
    }

    private void playOpenCloseSound(@Nullable Entity entity, World world, BlockPos pos, boolean open) {
        world.playSound(entity, pos, open ? SoundEvents.BLOCK_WOODEN_DOOR_OPEN : SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, world.getRandom().nextFloat() * 0.1F + 0.9F);
    }

    public boolean isOpen(BlockState state) {
        return (Boolean)state.get(OPEN);
    }


    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return (BlockState)state.with(FACING, rotation.rotate((Direction)state.get(FACING)));
    }

    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return mirror == BlockMirror.NONE ? state : (BlockState)state.rotate(mirror.getRotation((Direction)state.get(FACING)));
    }

    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        boolean var10000;
        switch (type) {
            case LAND:
            case AIR:
                var10000 = (Boolean)state.get(OPEN);
                break;
            case WATER:
                var10000 = false;
                break;
            default:
                throw new MatchException((String)null, (Throwable)null);
        }

        return var10000;
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Direction direction = (Direction)state.get(FACING);
        VoxelShape var10000 = null;
        if(!state.get(OPEN)){
            return switch (direction) {
                case WEST -> EAST_SHAPE;
                case EAST -> WEST_SHAPE;
                case SOUTH -> NORTH_SHAPE;
                default -> SOUTH_SHAPE;
            };
        } else {
            return switch (direction) {
                case WEST -> SOUTH_SHAPE;
                case EAST -> NORTH_SHAPE;
                case SOUTH -> EAST_SHAPE;
                default -> WEST_SHAPE;
            };
        }
    }
}
