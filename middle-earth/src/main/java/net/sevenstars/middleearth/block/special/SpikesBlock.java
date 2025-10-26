package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.block.enums.Thickness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
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
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

public class SpikesBlock extends Block {
    public static final MapCodec<SpikesBlock> CODEC = createCodec(SpikesBlock::new);
    public static final EnumProperty<DoubleBlockHalf> HALF = Properties.DOUBLE_BLOCK_HALF;

    public SpikesBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HALF, DoubleBlockHalf.LOWER));
    }

    public MapCodec<SpikesBlock> getCodec() {
        return CODEC;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, Direction.UP) || blockState.isOf(this);
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        DoubleBlockHalf doubleBlockHalf = state.get(HALF);
        BlockState downState = world.getBlockState(pos.down());

        if(!downState.isOf(this) && !downState.isSideSolidFullSquare(world, pos, Direction.UP)){
            return Blocks.AIR.getDefaultState();
        } else if (doubleBlockHalf == DoubleBlockHalf.LOWER && world.getBlockState(pos.up()).isAir()){
            return state.with(HALF, DoubleBlockHalf.UPPER);
        } else {
            return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
        }
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockState downState = world.getBlockState(pos.down());
        if (downState.isOf(this)){
            world.setBlockState(pos.down(), downState.with(HALF, DoubleBlockHalf.LOWER));
            world.setBlockState(pos, (BlockState)state.with(HALF, DoubleBlockHalf.UPPER), 3);
        } else {
            world.setBlockState(pos, (BlockState)state.with(HALF, DoubleBlockHalf.UPPER), 3);
        }
    }

    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld)world;
                if (projectile.canModifyAt(serverWorld, blockPos) && projectile.canBreakBlocks(serverWorld) && projectile instanceof TridentEntity && projectile.getVelocity().length() > 0.6) {
                    world.breakBlock(blockPos, true);
                }
            }
        }
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        entity.handleFallDamage(fallDistance + 2.5, 2.0F, world.getDamageSources().stalagmite());
    }
}
