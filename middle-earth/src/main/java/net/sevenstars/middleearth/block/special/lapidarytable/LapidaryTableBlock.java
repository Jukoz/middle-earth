package net.sevenstars.middleearth.block.special.lapidarytable;

import net.sevenstars.middleearth.gui.lapidarytable.LapidaryScreenHandler;

import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;

import org.jetbrains.annotations.Nullable;

import com.mojang.serialization.MapCodec;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

/**
 * The Lapidary Table (gem rework, step 5): where raw gems are studied and cut, tier by tier.
 * <p>
 * Plain vanilla rendering, no animation library. Idle, the whole table is one block model. While a gem is
 * ground ({@link #CUTTING}, only while a facet is ground in the minigame) the block model leaves out the handle and the wheel, and
 * {@code LapidaryTableRenderer} draws those two turning. It draws them through {@link #PART} states of this
 * same block, which are never placed in the world (blockstates/lapidary_table.json maps them to the
 * handle/wheel models), so no extra model loading is needed.
 */
public class LapidaryTableBlock extends BlockWithEntity {
    public static final MapCodec<LapidaryTableBlock> CODEC = createCodec(LapidaryTableBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty CUTTING = BooleanProperty.of("cutting");
    public static final EnumProperty<Part> PART = EnumProperty.of("part", Part.class);

    /**
     * Four legs and the tabletop; the crank, wheel and magnifier stay out of the hitbox. The legs are 2 px
     * wide and 3 px deep in the model (facing north/south), so they swap when the table faces east/west.
     */
    private static final VoxelShape SHAPE_NORTH_SOUTH = shape(2, 3);
    private static final VoxelShape SHAPE_EAST_WEST = shape(3, 2);

    private static VoxelShape shape(int legX, int legZ) {
        return VoxelShapes.union(
            Block.createCuboidShape(0, 9, 0, 16, 12, 16),
            Block.createCuboidShape(1, 0, 1, 1 + legX, 9, 1 + legZ),
            Block.createCuboidShape(15 - legX, 0, 1, 15, 9, 1 + legZ),
            Block.createCuboidShape(1, 0, 15 - legZ, 1 + legX, 9, 15),
            Block.createCuboidShape(15 - legX, 0, 15 - legZ, 15, 9, 15));
    }

    public LapidaryTableBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(CUTTING, false).with(PART, Part.TABLE));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CUTTING, PART);
    }

    /** The front of the table faces the player who placed it. */
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(FACING).getAxis() == Direction.Axis.X ? SHAPE_EAST_WEST : SHAPE_NORTH_SOUTH;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient()) {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected @Nullable NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        if (!(world.getBlockEntity(pos) instanceof LapidaryTableBlockEntity table)) {
            return null;
        }
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
            new LapidaryScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos), table),
            Text.translatable("container.middle-earth.lapidary_table"));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new LapidaryTableBlockEntity(pos, state);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
                                                                         BlockEntityType<T> type) {
        return world.isClient() ? null
            : validateTicker(type, BlockEntityRegistryME.LAPIDARY_TABLE, LapidaryTableBlockEntity::serverTick);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    /** TABLE is the placed block; HANDLE and WHEEL exist only so the renderer can draw those models. */
    public enum Part implements StringIdentifiable {
        TABLE("table"),
        HANDLE("handle"),
        WHEEL("wheel");

        private final String name;

        Part(String name) {
            this.name = name;
        }

        @Override
        public String asString() {
            return name;
        }
    }
}
