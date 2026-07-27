package net.sevenstars.middleearth.block.special.curtains;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.utils.BlockTagsME;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;

public class CurtainsBlock extends MultifaceBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<CurtainsBlock> CODEC = simpleCodec(CurtainsBlock::new);
    public static final BooleanProperty TIP = BooleanProperty.create("tip");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final Map<BlockState, VoxelShape> shapes;
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public CurtainsBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(TIP, false).setValue(WATERLOGGED, false));
        this.shapes = makeShapes();
    }

    @Override
    public MapCodec<? extends MultifaceBlock> codec() {
        return CODEC;
    }

    @Override
    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Level world = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        BlockState blockState = world.getBlockState(blockPos);
        BlockState returnState = (BlockState) Arrays.stream(ctx.getNearestLookingDirections()).map((direction) -> {
            return this.getStateForPlacement(blockState, world, blockPos, direction);
        }).filter(Objects::nonNull).findFirst().orElse((BlockState) null);
        if(returnState != null) returnState = returnState.setValue(TIP,
                !world.getBlockState(blockPos.below()).is(BlockTagsME.CURTAINS));
        return returnState;
    }

    @Override
    protected boolean isFaceSupported(Direction direction) {
        return direction != Direction.UP && direction != Direction.DOWN;
    }

    @Override
    public boolean isValidStateForPlacement(BlockGetter world, BlockState state, BlockPos pos, Direction direction) {
        return isFaceSupported(direction);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
        boolean sneaking = false;
        if(context.getPlayer() != null) sneaking = context.getPlayer().isShiftKeyDown();
        boolean placeVertically = false;
        if(context.getClickedFace() == Direction.UP) {
            if(!context.getItemInHand().is(this.asItem())) {
                placeVertically = true;
            } else {
                BlockState neighbor = context.getLevel().getBlockState(context.getClickedPos().above());
                if(context.getItemInHand().is(neighbor.getBlock().asItem()) || neighbor.isAir()) {
                    placeVertically = true;
                }
            }
        } else if(context.getClickedFace() == Direction.DOWN) {
            if(!context.getItemInHand().is(this.asItem())) {
                placeVertically = true;
            } else {
                BlockState neighbor = context.getLevel().getBlockState(context.getClickedPos().below());
                if(context.getItemInHand().is(neighbor.getBlock().asItem()) || neighbor.isAir()) {
                    placeVertically = true;
                }
            }
        }
        if(sneaking) placeVertically = !placeVertically;
        return hasAnyVacantFace(state) && !placeVertically && context.getItemInHand().is(this.asItem());
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }
        return state.setValue(TIP, !world.getBlockState(pos.below()).is(BlockTagsME.CURTAINS));
    }

    private static boolean hasAnyVacantFace(BlockState state) {
        Direction[] var1 = DIRECTIONS;
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Direction direction = var1[var3];
            if (!hasFace(state, direction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return this.shapes.get(state);
    }

    private Map<BlockState, VoxelShape> makeShapes() {
        Map<Direction, VoxelShape> map = new EnumMap<>(Direction.class);
        map.put(Direction.NORTH, Block.box(0, 0, 0, 16, 16, 3));
        map.put(Direction.SOUTH, Block.box(0, 0, 13, 16, 16, 16));
        map.put(Direction.WEST, Block.box(0, 0, 0, 3, 16, 16));
        map.put(Direction.EAST, Block.box(13, 0, 0, 16, 16, 16));
        return this.getShapeForEachState((state) -> {
            VoxelShape voxelShape = Shapes.empty();
            Direction[] var3 = DIRECTIONS;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                Direction direction = var3[var5];
                if (hasFace(state, direction)) {
                    voxelShape = Shapes.or(voxelShape, (VoxelShape)map.get(direction));
                }
            }

            return voxelShape.isEmpty() ? Shapes.block() : voxelShape;
        });
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TIP, WATERLOGGED);
        super.createBlockStateDefinition(builder);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public MultifaceSpreader getSpreader() {
        return this.spreader;
    }
}
