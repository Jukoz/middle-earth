package net.jukoz.me.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class DroopingIciclesBlock extends Block {
    public static final MapCodec<DroopingIciclesBlock> CODEC = createCodec(DroopingIciclesBlock::new);
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 10.0, 2.0, 14.0, 16.0, 14.0);
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;


    public DroopingIciclesBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HALF, DoubleBlockHalf.UPPER));
    }

    public MapCodec<DroopingIciclesBlock> getCodec() {
        return CODEC;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.DOWN) || blockState.isOf(this);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        BlockState upState = world.getBlockState(pos.up());

        if(!upState.isOf(this) && !upState.isSideSolidFullSquare(world, pos, Direction.DOWN)){
            return Blocks.AIR.getDefaultState();
        } else if (doubleBlockHalf == DoubleBlockHalf.UPPER && world.getBlockState(pos.down()).isAir()){
            return state.with(HALF, DoubleBlockHalf.LOWER);
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockState upState = world.getBlockState(pos.up());
        if (upState.isOf(this)){
            world.setBlockState(pos.up(), upState.with(HALF, DoubleBlockHalf.UPPER));
            world.setBlockState(pos, (BlockState)state.with(HALF, DoubleBlockHalf.LOWER), 3);
        } else {
            world.setBlockState(pos, (BlockState)state.with(HALF, DoubleBlockHalf.LOWER), 3);
        }
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER){
            return super.getOutlineShape(state, world, pos, context);
        } else  {
            return SHAPE;
        }
    }

    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            if (projectile.canModifyAt(world, blockPos) && projectile.canBreakBlocks(world) && projectile instanceof TridentEntity && projectile.getVelocity().length() > 0.6) {
                world.breakBlock(blockPos, true);
            }
        }
    }

}
