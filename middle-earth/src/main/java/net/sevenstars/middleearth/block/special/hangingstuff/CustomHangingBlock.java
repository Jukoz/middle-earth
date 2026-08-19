package net.sevenstars.middleearth.block.special.hangingstuff;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CustomHangingBlock extends Block implements BonemealableBlock {
    public static final MapCodec<CustomHangingBlock> CODEC = simpleCodec(CustomHangingBlock::new);
    public static final BooleanProperty TIP = BooleanProperty.create("tip");
    private static final VoxelShape BODY_SHAPE = Block.box(1.0, 0.0, 1.0, 15.0, 16.0, 15.0);
    private static final VoxelShape TIP_SHAPE = Block.box(1.0, 2.0, 1.0, 15.0, 16.0, 15.0);

    public CustomHangingBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(TIP, true));
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return state.getValue(TIP) ? TIP_SHAPE : BODY_SHAPE;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return this.canStayAtPosition(world, pos);
    }

    private boolean canStayAtPosition(BlockGetter world, BlockPos pos) {
        BlockPos blockPos = pos.relative(Direction.UP);
        BlockState blockState = world.getBlockState(blockPos);
        return MultifaceBlock.canAttachTo(world, Direction.UP, blockPos, blockState) || blockState.is(this);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (!this.canStayAtPosition(world, pos)) {
            world.scheduleTick(pos, this, 1);
        }

        return state.setValue(TIP, !world.getBlockState(pos.below()).is(this));
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (!this.canStayAtPosition(world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return world.getBlockState(findTipPos(world, pos).below()).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos growthPos = findTipPos(world, pos).below();
        if (world.getBlockState(growthPos).isAir()) {
            world.setBlock(growthPos, state.setValue(TIP, true), Block.UPDATE_ALL);
        }
    }

    private BlockPos findTipPos(BlockGetter world, BlockPos pos) {
        BlockPos.MutableBlockPos cursor = pos.mutable().move(Direction.DOWN);
        while (world.getBlockState(cursor).is(this)) {
            cursor.move(Direction.DOWN);
        }
        return cursor.move(Direction.UP).immutable();
    }
}
