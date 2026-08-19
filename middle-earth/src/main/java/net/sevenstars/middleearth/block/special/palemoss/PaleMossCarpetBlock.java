package net.sevenstars.middleearth.block.special.palemoss;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BooleanSupplier;

public final class PaleMossCarpetBlock extends Block implements BonemealableBlock {
    public static final MapCodec<PaleMossCarpetBlock> CODEC = simpleCodec(PaleMossCarpetBlock::new);
    public static final BooleanProperty BOTTOM = BlockStateProperties.BOTTOM;
    public static final EnumProperty<WallSide> NORTH = BlockStateProperties.NORTH_WALL;
    public static final EnumProperty<WallSide> EAST = BlockStateProperties.EAST_WALL;
    public static final EnumProperty<WallSide> SOUTH = BlockStateProperties.SOUTH_WALL;
    public static final EnumProperty<WallSide> WEST = BlockStateProperties.WEST_WALL;
    public static final Map<Direction, EnumProperty<WallSide>> WALL_PROPERTIES = Map.of(
            Direction.NORTH, NORTH,
            Direction.EAST, EAST,
            Direction.SOUTH, SOUTH,
            Direction.WEST, WEST);

    private static final VoxelShape BOTTOM_SHAPE = Block.box(0, 0, 0, 16, 1, 16);
    private static final Map<Direction, VoxelShape> LOW_SHAPES = Map.of(
            Direction.NORTH, Block.box(0, 0, 0, 16, 10, 1),
            Direction.EAST, Block.box(15, 0, 0, 16, 10, 16),
            Direction.SOUTH, Block.box(0, 0, 15, 16, 10, 16),
            Direction.WEST, Block.box(0, 0, 0, 1, 10, 16));
    private static final Map<Direction, VoxelShape> TALL_SHAPES = Map.of(
            Direction.NORTH, Block.box(0, 0, 0, 16, 16, 1),
            Direction.EAST, Block.box(15, 0, 0, 16, 16, 16),
            Direction.SOUTH, Block.box(0, 0, 15, 16, 16, 16),
            Direction.WEST, Block.box(0, 0, 0, 1, 16, 16));

    public PaleMossCarpetBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any()
                .setValue(BOTTOM, true)
                .setValue(NORTH, WallSide.NONE)
                .setValue(EAST, WallSide.NONE)
                .setValue(SOUTH, WallSide.NONE)
                .setValue(WEST, WallSide.NONE));
    }

    @Override
    protected MapCodec<PaleMossCarpetBlock> codec() {
        return CODEC;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        VoxelShape result = state.getValue(BOTTOM) ? BOTTOM_SHAPE : Shapes.empty();
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            WallSide wallSide = state.getValue(WALL_PROPERTIES.get(direction));
            if (wallSide == WallSide.LOW) {
                result = Shapes.or(result, LOW_SHAPES.get(direction));
            } else if (wallSide == WallSide.TALL) {
                result = Shapes.or(result, TALL_SHAPES.get(direction));
            }
        }
        return result.isEmpty() ? Shapes.block() : result;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(BOTTOM) ? BOTTOM_SHAPE : Shapes.empty();
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return Shapes.empty();
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state, BlockGetter level, BlockPos pos) {
        return true;
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState below = level.getBlockState(pos.below());
        return state.getValue(BOTTOM) ? !below.isAir() : below.is(this) && below.getValue(BOTTOM);
    }

    private static boolean canGrowOnFace(BlockGetter level, BlockPos pos, Direction direction) {
        if (direction == Direction.UP) {
            return false;
        }
        BlockPos supportPos = pos.relative(direction);
        return MultifaceBlock.canAttachTo(level, direction, supportPos, level.getBlockState(supportPos));
    }

    private BlockState updateState(BlockState state, BlockGetter level, BlockPos pos, boolean forceLow) {
        BlockState above = null;
        BlockState below = null;
        forceLow |= state.getValue(BOTTOM);

        for (Direction direction : Direction.Plane.HORIZONTAL) {
            EnumProperty<WallSide> property = WALL_PROPERTIES.get(direction);
            WallSide side = canGrowOnFace(level, pos, direction)
                    ? (forceLow ? WallSide.LOW : state.getValue(property))
                    : WallSide.NONE;
            if (side == WallSide.LOW) {
                if (above == null) {
                    above = level.getBlockState(pos.above());
                }
                if (above.is(this) && above.getValue(property) != WallSide.NONE && !above.getValue(BOTTOM)) {
                    side = WallSide.TALL;
                }
                if (!state.getValue(BOTTOM)) {
                    if (below == null) {
                        below = level.getBlockState(pos.below());
                    }
                    if (below.is(this) && below.getValue(property) == WallSide.NONE) {
                        side = WallSide.NONE;
                    }
                }
            }
            state = state.setValue(property, side);
        }
        return state;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return updateState(defaultBlockState(), context.getLevel(), context.getClickedPos(), true);
    }

    public static void placeAt(LevelAccessor level, BlockPos pos, RandomSource random, int flags) {
        PaleMossCarpetBlock carpet = (PaleMossCarpetBlock) ModNatureBlocks.PALE_MOSS_CARPET;
        BlockState bottom = carpet.updateState(carpet.defaultBlockState(), level, pos, true);
        level.setBlock(pos, bottom, flags);
        BlockState upper = carpet.createUpperState(level, pos, random::nextBoolean);
        if (!upper.isAir()) {
            level.setBlock(pos.above(), upper, flags);
            level.setBlock(pos, carpet.updateState(bottom, level, pos, true), flags);
        }
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            BlockState upper = createUpperState(level, pos, level.getRandom()::nextBoolean);
            if (!upper.isAir()) {
                level.setBlock(pos.above(), upper, Block.UPDATE_ALL);
            }
        }
    }

    private BlockState createUpperState(BlockGetter level, BlockPos pos, BooleanSupplier randomBoolean) {
        BlockPos upperPos = pos.above();
        BlockState current = level.getBlockState(upperPos);
        boolean isCarpet = current.is(this);
        if ((isCarpet && current.getValue(BOTTOM)) || (!isCarpet && !current.canBeReplaced())) {
            return Blocks.AIR.defaultBlockState();
        }

        BlockState upper = updateState(defaultBlockState().setValue(BOTTOM, false), level, upperPos, true);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            EnumProperty<WallSide> property = WALL_PROPERTIES.get(direction);
            if (upper.getValue(property) != WallSide.NONE && !randomBoolean.getAsBoolean()) {
                upper = upper.setValue(property, WallSide.NONE);
            }
        }
        return hasAnyShape(upper) && upper != current ? upper : Blocks.AIR.defaultBlockState();
    }

    private static boolean hasAnyShape(BlockState state) {
        if (state.getValue(BOTTOM)) {
            return true;
        }
        return WALL_PROPERTIES.values().stream().anyMatch(property -> state.getValue(property) != WallSide.NONE);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        if (!state.canSurvive(level, pos)) {
            return Blocks.AIR.defaultBlockState();
        }
        BlockState updated = updateState(state, level, pos, false);
        return hasAnyShape(updated) ? updated : Blocks.AIR.defaultBlockState();
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BOTTOM, NORTH, EAST, SOUTH, WEST);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return switch (rotation) {
            case CLOCKWISE_180 -> state
                    .setValue(NORTH, state.getValue(SOUTH))
                    .setValue(EAST, state.getValue(WEST))
                    .setValue(SOUTH, state.getValue(NORTH))
                    .setValue(WEST, state.getValue(EAST));
            case CLOCKWISE_90 -> state
                    .setValue(NORTH, state.getValue(WEST))
                    .setValue(EAST, state.getValue(NORTH))
                    .setValue(SOUTH, state.getValue(EAST))
                    .setValue(WEST, state.getValue(SOUTH));
            case COUNTERCLOCKWISE_90 -> state
                    .setValue(NORTH, state.getValue(EAST))
                    .setValue(EAST, state.getValue(SOUTH))
                    .setValue(SOUTH, state.getValue(WEST))
                    .setValue(WEST, state.getValue(NORTH));
            default -> state;
        };
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return switch (mirror) {
            case LEFT_RIGHT -> state
                    .setValue(NORTH, state.getValue(SOUTH))
                    .setValue(SOUTH, state.getValue(NORTH));
            case FRONT_BACK -> state
                    .setValue(EAST, state.getValue(WEST))
                    .setValue(WEST, state.getValue(EAST));
            default -> super.mirror(state, mirror);
        };
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return state.getValue(BOTTOM) && !createUpperState(level, pos, () -> true).isAir();
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockState upper = createUpperState(level, pos, () -> true);
        if (!upper.isAir()) {
            level.setBlock(pos.above(), upper, Block.UPDATE_ALL);
        }
    }
}
