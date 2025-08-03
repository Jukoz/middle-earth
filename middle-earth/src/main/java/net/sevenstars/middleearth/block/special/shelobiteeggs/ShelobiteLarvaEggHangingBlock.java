package net.sevenstars.middleearth.block.special.shelobiteeggs;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.BlockHalf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCollisionHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import net.sevenstars.middleearth.entity.ModEntities;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ShelobiteLarvaEggHangingBlock extends AbstractShelobiteLarvaEgg {
    public static final EnumProperty<BlockHalf> BLOCK_HALF = Properties.BLOCK_HALF;

    public ShelobiteLarvaEggHangingBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(BLOCK_HALF, BlockHalf.TOP));
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return createCodec(ShelobiteLarvaEggHangingBlock::new);
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return direction == Direction.UP && !this.canPlaceAt(state, world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        if (Objects.requireNonNull(state.get(BLOCK_HALF)) == BlockHalf.BOTTOM) {
            return !world.isWater(pos) && world.getBlockState(pos.down()).isAir();
        }
        return Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN) && !world.isWater(pos) && world.getBlockState(pos.down()).isAir();
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_HALF);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockPos blockPos = pos.down();
            world.setBlockState(blockPos, (BlockState)state.with(BLOCK_HALF, BlockHalf.BOTTOM), 3);
            world.updateNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, 3);
        }
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient) {
            if (state.get(BLOCK_HALF) == BlockHalf.BOTTOM){
                world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 35);
                world.syncWorldEvent(player, 2001, pos.up(), Block.getRawIdFromState(state));
            } else{
                world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 35);
                world.syncWorldEvent(player, 2001, pos.down(), Block.getRawIdFromState(state));
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(BLOCK_HALF)) {
            case BOTTOM -> Block.createCuboidShape(4, 4, 4, 12, 16, 12);
            case TOP -> {
                if (world.getBlockState(pos.down()).isOf(this)){
                    yield VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 14, 0, 16, 16, 16), Block.createCuboidShape(5, 0, 5, 11, 14, 11), BooleanBiFunction.OR);
                } else {
                    yield Block.createCuboidShape(0, 0, 0, 0, 0, 0);
                }
            }
        };
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity, EntityCollisionHandler handler) {
        super.onEntityCollision(state, world, pos, entity, handler);
        if (entity.getType() != ModEntities.MIRKWOOD_SPIDER && state.get(BLOCK_HALF) == BlockHalf.BOTTOM){
            breakEgg(world, pos, state);
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(BLOCK_HALF)) {
            case BOTTOM -> Block.createCuboidShape(4, 4, 4, 12, 16, 12);
            case TOP -> VoxelShapes.combineAndSimplify(Block.createCuboidShape(0, 14, 0, 16, 16, 16), Block.createCuboidShape(5, 0, 5, 11, 14, 11), BooleanBiFunction.OR);
        };
    }
}