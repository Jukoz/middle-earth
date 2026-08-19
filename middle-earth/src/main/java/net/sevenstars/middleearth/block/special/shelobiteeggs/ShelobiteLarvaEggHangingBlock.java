package net.sevenstars.middleearth.block.special.shelobiteeggs;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
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
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.entity.EntitiesME;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class ShelobiteLarvaEggHangingBlock extends AbstractShelobiteLarvaEgg {
    public static final EnumProperty<Half> BLOCK_HALF = BlockStateProperties.HALF;

    public ShelobiteLarvaEggHangingBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(BLOCK_HALF, Half.TOP));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return simpleCodec(ShelobiteLarvaEggHangingBlock::new);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.UP && !this.canSurvive(state, world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        if (Objects.requireNonNull(state.getValue(BLOCK_HALF)) == Half.BOTTOM) {
            return !world.isWaterAt(pos) && world.getBlockState(pos.below()).isAir();
        }
        return Block.canSupportCenter(world, pos.above(), Direction.DOWN) && !world.isWaterAt(pos) && world.getBlockState(pos.below()).isAir();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BLOCK_HALF);
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        if (!world.isClientSide) {
            BlockPos blockPos = pos.below();
            world.setBlock(blockPos, (BlockState)state.setValue(BLOCK_HALF, Half.BOTTOM), 3);
            world.updateNeighborsAt(pos, Blocks.AIR);
            state.updateNeighbourShapes(world, pos, 3);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        if (!world.isClientSide) {
            if (state.getValue(BLOCK_HALF) == Half.BOTTOM){
                world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, pos.above(), Block.getId(state));
            } else{
                world.setBlock(pos.below(), Blocks.AIR.defaultBlockState(), 35);
                world.levelEvent(player, 2001, pos.below(), Block.getId(state));
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(BLOCK_HALF)) {
            case BOTTOM -> Block.box(4, 4, 4, 12, 16, 12);
            case TOP -> {
                if (world.getBlockState(pos.below()).is(this)){
                    yield Shapes.join(Block.box(0, 14, 0, 16, 16, 16), Block.box(5, 0, 5, 11, 14, 11), BooleanOp.OR);
                } else {
                    yield Block.box(0, 0, 0, 0, 0, 0);
                }
            }
        };
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        if (entity.getType() != EntitiesME.SHELOBITE_SCUTTLER && state.getValue(BLOCK_HALF) == Half.BOTTOM){
            breakEgg(world, pos, state);
        }
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(BLOCK_HALF)) {
            case BOTTOM -> Block.box(4, 4, 4, 12, 16, 12);
            case TOP -> Shapes.join(Block.box(0, 14, 0, 16, 16, 16), Block.box(5, 0, 5, 11, 14, 11), BooleanOp.OR);
        };
    }
}
