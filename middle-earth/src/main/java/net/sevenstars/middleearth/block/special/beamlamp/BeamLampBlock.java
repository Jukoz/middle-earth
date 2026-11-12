package net.sevenstars.middleearth.block.special.beamlamp;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BlockFace;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import org.jetbrains.annotations.Nullable;

/**
 * A focused lamp set down as a block: standing, hanging or on a wall like Middle-earth's lanterns, its lens
 * pointing the way the player faced (on a wall, straight out from it). Lit, it glows a little itself and throws
 * a level beam that lights the spot where it lands. Right-click to light it or shade it.
 * <p>
 * Models face north (lens on the north side; a wall lamp's wall is to the south) and turn with {@link #FACING}.
 */
public class BeamLampBlock extends BlockWithEntity implements Waterloggable {
    public static final EnumProperty<BlockFace> FACE = Properties.BLOCK_FACE;
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty LIT = Properties.LIT;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private final BeamLampKind kind;
    private final MapCodec<BeamLampBlock> codec;

    public BeamLampBlock(BeamLampKind kind, Settings settings) {
        super(settings);
        this.kind = kind;
        this.codec = createCodec(s -> new BeamLampBlock(kind, s));
        setDefaultState(getDefaultState().with(FACE, BlockFace.FLOOR).with(FACING, Direction.NORTH)
            .with(LIT, true).with(WATERLOGGED, false));
    }

    public BeamLampKind kind() {
        return kind;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return codec;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING, LIT, WATERLOGGED);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    /** The side the lamp hangs from or stands on. */
    private static Direction attached(BlockState state) {
        return switch (state.get(FACE)) {
            case FLOOR -> Direction.DOWN;
            case CEILING -> Direction.UP;
            case WALL -> state.get(FACING).getOpposite();
        };
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        boolean water = ctx.getWorld().getFluidState(ctx.getBlockPos()).getFluid() == Fluids.WATER;
        for (Direction direction : ctx.getPlacementDirections()) {
            BlockState state;
            if (direction == Direction.UP) {
                state = getDefaultState().with(FACE, BlockFace.CEILING).with(FACING, ctx.getHorizontalPlayerFacing());
            } else if (direction == Direction.DOWN) {
                state = getDefaultState().with(FACE, BlockFace.FLOOR).with(FACING, ctx.getHorizontalPlayerFacing());
            } else {
                state = getDefaultState().with(FACE, BlockFace.WALL).with(FACING, direction.getOpposite());
            }
            if (state.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                return state.with(WATERLOGGED, water);
            }
        }
        return null;
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction toSupport = attached(state);
        return Block.sideCoversSmallSquare(world, pos.offset(toSupport), toSupport.getOpposite());
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos,
                                                   Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if (direction == attached(state) && !state.canPlaceAt(world, pos)) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient) {
            boolean lit = !state.get(LIT);
            world.setBlockState(pos, state.with(LIT, lit), Block.NOTIFY_ALL);
            world.playSound(null, pos, lit ? SoundEvents.ITEM_FLINTANDSTEEL_USE : SoundEvents.BLOCK_CANDLE_EXTINGUISH,
                SoundCategory.BLOCKS, 0.8F, 1.2F);
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACE)) {
            case FLOOR -> Block.createCuboidShape(5, 0, 5, 11, 12, 11);
            case CEILING -> Block.createCuboidShape(5, 0, 5, 11, 16, 11);
            case WALL -> switch (state.get(FACING)) {
                case SOUTH -> Block.createCuboidShape(5, 0, 0, 11, 14, 14);
                case EAST -> Block.createCuboidShape(0, 0, 5, 14, 14, 11);
                case WEST -> Block.createCuboidShape(2, 0, 5, 16, 14, 11);
                default -> Block.createCuboidShape(5, 0, 2, 11, 14, 16);
            };
        };
    }

    /** Where the light leaves the lens, in world space: the flame's height, at the front of the housing. */
    public static Vec3d lens(BlockPos pos, BlockState state) {
        Direction facing = state.get(FACING);
        BeamLampKind kind = state.getBlock() instanceof BeamLampBlock lamp ? lamp.kind() : BeamLampKind.BULLSEYE;
        boolean wall = state.get(FACE) == BlockFace.WALL;
        double out = (wall ? kind.lensOutWall() : kind.lensOut()) / 16;
        double height = (wall ? kind.lensHeightWall() : kind.lensHeight()) / 16;
        return new Vec3d(pos.getX() + 0.5 + facing.getOffsetX() * out, pos.getY() + height,
            pos.getZ() + 0.5 + facing.getOffsetZ() * out);
    }

    /** The beam's direction: level, out of the lens. */
    public static Vec3d direction(BlockState state) {
        Direction facing = state.get(FACING);
        return new Vec3d(facing.getOffsetX(), 0, facing.getOffsetZ());
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BeamLampBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? null : validateTicker(type, BlockEntityRegistryME.BEAM_LAMP, BeamLampBlockEntity::serverTick);
    }
}
