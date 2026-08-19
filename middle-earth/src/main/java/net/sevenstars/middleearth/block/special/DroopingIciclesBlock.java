package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
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
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DroopingIciclesBlock extends Block {
    public static final MapCodec<DroopingIciclesBlock> CODEC = simpleCodec(DroopingIciclesBlock::new);
    protected static final VoxelShape SHAPE = Block.box(2.0, 10.0, 2.0, 14.0, 16.0, 14.0);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;


    public DroopingIciclesBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(HALF, DoubleBlockHalf.UPPER));
    }

    public MapCodec<DroopingIciclesBlock> codec() {
        return CODEC;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF);
    }

    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.above();
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isFaceSturdy(world, blockPos, Direction.DOWN) || blockState.is(this);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        DoubleBlockHalf doubleBlockHalf = state.getValue(HALF);
        BlockState upState = world.getBlockState(pos.above());

        if(!upState.is(this) && !upState.isFaceSturdy(world, pos, Direction.DOWN)){
            return Blocks.AIR.defaultBlockState();
        } else if (doubleBlockHalf == DoubleBlockHalf.UPPER && world.getBlockState(pos.below()).isAir()){
            return state.setValue(HALF, DoubleBlockHalf.LOWER);
        } else {
            return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        BlockState upState = world.getBlockState(pos.above());
        if (upState.is(this)){
            world.setBlockAndUpdate(pos.above(), upState.setValue(HALF, DoubleBlockHalf.UPPER));
            world.setBlock(pos, (BlockState)state.setValue(HALF, DoubleBlockHalf.LOWER), 3);
        } else {
            world.setBlock(pos, (BlockState)state.setValue(HALF, DoubleBlockHalf.LOWER), 3);
        }
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if (state.getValue(HALF) == DoubleBlockHalf.UPPER){
            return super.getShape(state, world, pos, context);
        } else  {
            return SHAPE;
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

}
