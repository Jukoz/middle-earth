package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;

public class SpikesBlock extends Block {
    public static final MapCodec<SpikesBlock> CODEC = simpleCodec(SpikesBlock::new);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;

    public SpikesBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.LOWER));
    }

    public MapCodec<SpikesBlock> codec() {
        return CODEC;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isFaceSturdy(world, blockPos, Direction.UP) || blockState.is(this);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        BlockState downState = world.getBlockState(pos.below());

        if(!downState.is(this) && !downState.isFaceSturdy(world, pos, Direction.UP)){
            return Blocks.AIR.defaultBlockState();
        } else if (doubleBlockHalf == DoubleBlockHalf.LOWER && world.getBlockState(pos.above()).isAir()){
            return state.setValue(HALF, DoubleBlockHalf.UPPER);
        } else {
            return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockState downState = world.getBlockState(pos.below());
        if (downState.is(this)){
            world.setBlockAndUpdate(pos.below(), downState.setValue(HALF, DoubleBlockHalf.LOWER));
            world.setBlock(pos, (BlockState)state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
        } else {
            world.setBlock(pos, (BlockState)state.setValue(HALF, DoubleBlockHalf.UPPER), 3);
        }
    }

    protected void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!world.isClientSide) {
            BlockPos blockPos = hit.getBlockPos();
            if (world instanceof ServerLevel) {
                ServerLevel serverWorld = (ServerLevel)world;
                if (projectile.mayInteract(serverWorld, blockPos) && projectile.mayBreak(serverWorld) && projectile instanceof ThrownTrident && projectile.getDeltaMovement().length() > 0.6) {
                    world.destroyBlock(blockPos, true);
                }
            }
        }
    }

    @Override
    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        entity.causeFallDamage(fallDistance + 2.5F, 2.0F, world.damageSources().fall());
    }
}
