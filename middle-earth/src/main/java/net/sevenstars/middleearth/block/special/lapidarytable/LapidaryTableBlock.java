package net.sevenstars.middleearth.block.special.lapidarytable;

import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.sevenstars.middleearth.gui.lapidarytable.LapidaryScreenHandler;
import net.sevenstars.middleearth.block.registration.BlockEntityRegistryME;
import net.sevenstars.middleearth.recipe.lapidary.LapidaryTools;
import org.jetbrains.annotations.Nullable;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
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
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.tick.ScheduledTickView;

/** The two-block Lapidary Table: a cutting half and a study half. */
public class LapidaryTableBlock extends BlockWithEntity {
    public static final MapCodec<LapidaryTableBlock> CODEC = createCodec(LapidaryTableBlock::new);
    public static final EnumProperty<Direction> FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty CUTTING = BooleanProperty.of("cutting");
    public static final EnumProperty<Part> PART = EnumProperty.of("part", Part.class);

    private static final VoxelShape[][] SHAPES = new VoxelShape[Part.values().length][4];
    private static final VoxelShape[] WITH_BUCKET = new VoxelShape[4], WITH_BASIN = new VoxelShape[4];

    static {
        double[][] table = {{0, 9, 0, 16, 12, 16}, {1, 0, 1, 3, 9, 4}, {1, 0, 12, 3, 9, 15}};
        double[][] study = {{0, 9, 0, 16, 12.25, 16}, {13, 0, 1, 15, 9, 4}, {13, 0, 12, 15, 9, 15}};
        for (Direction facing : Direction.Type.HORIZONTAL) {
            SHAPES[Part.TABLE.ordinal()][facing.getHorizontalQuarterTurns()] = shape(table, facing);
            SHAPES[Part.STUDY.ordinal()][facing.getHorizontalQuarterTurns()] = shape(study, facing);
            WITH_BUCKET[facing.getHorizontalQuarterTurns()] = VoxelShapes.union(shape(study, facing),
                shape(new double[][] {{2, 0, 4, 10, 7, 12}}, facing));
            WITH_BASIN[facing.getHorizontalQuarterTurns()] = VoxelShapes.union(shape(study, facing),
                shape(new double[][] {{1, 0, 2, 13, 6, 14}}, facing));
        }
    }

    private static VoxelShape shape(double[][] boxes, Direction facing) {
        VoxelShape shape = VoxelShapes.empty();
        for (double[] b : boxes) {
            double x0 = b[0], z0 = b[2], x1 = b[3], z1 = b[5];
            double[] r = switch (facing) {
                case EAST -> new double[] {16 - z1, x0, 16 - z0, x1};
                case SOUTH -> new double[] {16 - x1, 16 - z1, 16 - x0, 16 - z0};
                case WEST -> new double[] {z0, 16 - x1, z1, 16 - x0};
                default -> new double[] {x0, z0, x1, z1};
            };
            shape = VoxelShapes.union(shape, Block.createCuboidShape(r[0], b[1], r[1], r[2], b[4], r[3]));
        }
        return shape;
    }

    public LapidaryTableBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(CUTTING, false).with(PART, Part.TABLE));
    }

    public static Direction studySide(Direction facing) {
        return facing.rotateYClockwise();
    }

    public static BlockPos tablePos(BlockState state, BlockPos pos) {
        return state.get(PART) == Part.STUDY ? pos.offset(studySide(state.get(FACING)).getOpposite()) : pos;
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, CUTTING, PART);
    }

    @Override
    public @Nullable BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction facing = ctx.getHorizontalPlayerFacing().getOpposite();
        BlockPos study = ctx.getBlockPos().offset(studySide(facing));
        World world = ctx.getWorld();
        if (!world.getBlockState(study).canReplace(ctx) || !world.getWorldBorder().contains(study)) {
            return null;
        }
        return getDefaultState().with(FACING, facing);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient()) {
            world.setBlockState(pos.offset(studySide(state.get(FACING))), state.with(PART, Part.STUDY), Block.NOTIFY_ALL);
        }
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos,
                                                   Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        Direction toStudy = studySide(state.get(FACING));
        Part part = state.get(PART);
        Part other = part == Part.STUDY ? Part.TABLE : Part.STUDY;
        if (direction == (part == Part.STUDY ? toStudy.getOpposite() : toStudy)
            && !(neighborState.isOf(this) && neighborState.get(PART) == other && neighborState.get(FACING) == state.get(FACING))) {
            return Blocks.AIR.getDefaultState();
        }
        return super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient() && state.get(PART) == Part.STUDY && (player.isCreative() || !player.canHarvest(state))) {
            BlockPos table = tablePos(state, pos);
            BlockState tableState = world.getBlockState(table);
            if (tableState.isOf(this) && tableState.get(PART) == Part.TABLE) {
                world.setBlockState(table, Blocks.AIR.getDefaultState(), Block.NOTIFY_ALL | Block.SKIP_DROPS);
                world.syncWorldEvent(player, 2001, table, Block.getRawIdFromState(tableState));
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Part part = state.get(PART) == Part.STUDY ? Part.STUDY : Part.TABLE;
        int turns = state.get(FACING).getHorizontalQuarterTurns();
        if (part == Part.STUDY && world.getBlockEntity(tablePos(state, pos)) instanceof LapidaryTableBlockEntity table
            && table.pumpTier() != null) {
            return (table.pumpTier() == LapidaryTools.RELIC ? WITH_BASIN : WITH_BUCKET)[turns];
        }
        return SHAPES[part.ordinal()][turns];
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (!world.isClient() && world.getBlockEntity(tablePos(state, pos)) instanceof LapidaryTableBlockEntity table) {
            if (player.isSneaking()) {
                table.removeUpgrade(player);
                return ActionResult.SUCCESS;
            }
            net.minecraft.server.network.ServerPlayerEntity other = table.otherUser(player, state.get(PART) != Part.STUDY);
            if (other != null) {
                player.sendMessage(Text.translatable("message.middle-earth.lapidary_table.in_use", other.getDisplayName()), false);
                return ActionResult.SUCCESS;
            }
            player.openHandledScreen(createScreenHandlerFactory(state, world, pos));
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player,
                                         net.minecraft.util.Hand hand, BlockHitResult hit) {
        if (stack.isOf(net.minecraft.item.Items.WATER_BUCKET)
            && world.getBlockEntity(tablePos(state, pos)) instanceof LapidaryTableBlockEntity table && table.pumpTier() != null) {
            if (!world.isClient()) {
                table.pourWater(player, hand);
            }
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS_TO_DEFAULT_BLOCK_ACTION;
    }

    @Override
    protected @Nullable NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        BlockPos tablePos = tablePos(state, pos);
        if (!(world.getBlockEntity(tablePos) instanceof LapidaryTableBlockEntity table)) {
            return null;
        }
        boolean atWheel = state.get(PART) != Part.STUDY;
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
            new LapidaryScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, tablePos), table, atWheel),
            Text.translatable("container.middle-earth.lapidary_table"));
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return state.get(PART) == Part.TABLE ? new LapidaryTableBlockEntity(pos, state) : null;
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state,
                                                                         BlockEntityType<T> type) {
        return state.get(PART) != Part.TABLE ? null
            : validateTicker(type, BlockEntityRegistryME.LAPIDARY_TABLE,
                world.isClient() ? LapidaryTableBlockEntity::clientTick : LapidaryTableBlockEntity::serverTick);
    }

    @Override
    protected BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    public static void registerEvents() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            ItemStack stack = player.getStackInHand(hand);
            if (player.isSpectator() || !player.isSneaking() || LapidaryTools.slotOf(stack.getItem()) == null) {
                return ActionResult.PASS;
            }
            BlockState state = world.getBlockState(hit.getBlockPos());
            if (!(state.getBlock() instanceof LapidaryTableBlock)) {
                return ActionResult.PASS;
            }
            if (!world.isClient() && world.getBlockEntity(tablePos(state, hit.getBlockPos())) instanceof LapidaryTableBlockEntity table) {
                table.install(player, stack);
            }
            return ActionResult.SUCCESS;
        });
    }

    public enum Part implements StringIdentifiable {
        TABLE("table"),
        STUDY("study"),
        HANDLE("handle"),
        WHEEL("wheel"),
        WHEEL_ENHANCED("wheel_enhanced"),
        WHEEL_KHAZAD("wheel_khazad"),
        LENS("lens"),
        LENS_REFINED("lens_refined"),
        LENS_EXQUISITE("lens_exquisite"),
        LENS_GLASS_REFINED("lens_glass_refined"),
        LENS_GLASS_EXQUISITE("lens_glass_exquisite"),
        PEDAL_BASE("pedal_base"),
        PEDAL_TOP("pedal_top"),
        PEDAL_BASE_ELVEN("pedal_base_elven"),
        PEDAL_TOP_ELVEN("pedal_top_elven"),
        BUCKET("bucket"),
        BASIN("basin");

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
