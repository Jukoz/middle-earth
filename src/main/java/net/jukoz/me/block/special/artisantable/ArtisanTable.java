package net.jukoz.me.block.special.artisantable;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.jukoz.me.block.ModBlockEntities;
import net.jukoz.me.block.special.alloyfurnace.AlloyFurnaceEntity;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.BedPart;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.*;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.stream.Stream;

public class ArtisanTable extends HorizontalFacingBlock {
    public static final EnumProperty<ArtisanTablePart> PART = EnumProperty.of("part", ArtisanTablePart.class);
    private static final Text TITLE = Text.translatable("container.artisan_table");


    public ArtisanTable(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(PART, ArtisanTablePart.LEFT).with(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return createCodec(ArtisanTable::new);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        return ActionResult.CONSUME;
    }

    @Nullable
    public static Direction getDirection(BlockView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos);
        return blockState.getBlock() instanceof ArtisanTable ? (Direction)blockState.get(FACING) : null;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == getDirectionTowardsOtherPart((ArtisanTablePart)state.get(PART), (Direction)state.get(FACING).rotateYClockwise())) {
            return neighborState.isOf(this) && neighborState.get(PART) != state.get(PART) ? (BlockState)state : Blocks.AIR.getDefaultState();
        } else {
            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }


    @Override
    public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!world.isClient && player.isCreative()) {
            ArtisanTablePart tablePart = (ArtisanTablePart)state.get(PART);
            ArtisanTablePart tablePartOpposite = (ArtisanTablePart)state.get(PART).getOpposite(state.get(PART));

            BlockPos blockPos = pos.offset(getDirectionTowardsOtherPart(tablePart, (Direction)state.get(FACING).rotateYClockwise()));
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.isOf(this) && blockState.get(PART) == tablePartOpposite) {
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 35);
                world.syncWorldEvent(player, 2001, blockPos, Block.getRawIdFromState(blockState));
            }
        }
        return super.onBreak(world, pos, state, player);
    }

    private static Direction getDirectionTowardsOtherPart(ArtisanTablePart part, Direction direction) {
        return part == ArtisanTablePart.LEFT ? direction : direction.getOpposite();
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos blockPos2 = blockPos.offset(direction.rotateYClockwise());
        World world = ctx.getWorld();
        return world.getBlockState(blockPos2).canReplace(ctx) && world.getWorldBorder().contains(blockPos2) ? (BlockState)this.getDefaultState().with(FACING, direction).with(PART, ArtisanTablePart.LEFT) : null;
    }

    public static Direction getOppositePartDirection(BlockState state) {
        Direction direction = (Direction)state.get(FACING);
        return state.get(PART) == ArtisanTablePart.RIGHT ? direction.getOpposite() : direction;
    }

    public static DoubleBlockProperties.Type getTablePart(BlockState state) {
        ArtisanTablePart tablePart = (ArtisanTablePart)state.get(PART);
        return tablePart == ArtisanTablePart.RIGHT ? DoubleBlockProperties.Type.FIRST : DoubleBlockProperties.Type.SECOND;
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockPos blockPos = pos.offset((Direction)state.get(FACING).rotateYClockwise());
            world.setBlockState(blockPos, (BlockState)state.with(PART, ArtisanTablePart.RIGHT), 3);
            world.updateNeighbors(pos, Blocks.AIR);
            state.updateNeighbors(world, pos, 3);
        }
    }

    public long getRenderingSeed(BlockState state, BlockPos pos) {
        BlockPos blockPos = pos.offset((Direction)state.get(FACING), state.get(PART) == ArtisanTablePart.RIGHT ? 0 : 1);
        return MathHelper.hashCode(blockPos.getX(), pos.getY(), blockPos.getZ());
    }

    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
            player.incrementStat(Stats.INTERACT_WITH_STONECUTTER);
            return ActionResult.CONSUME;
        }
    }

    @Nullable
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, playerInventory, player) -> {
            return new ArtisanTableScreenHandler(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
        }, TITLE);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(FACING, PART);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return switch (state.get(PART)){
            case LEFT ->
                    switch (state.get(FACING)){
                        case DOWN -> null;
                        case UP -> null;
                        case NORTH -> Stream.of(
                                Block.createCuboidShape(1, 0, 1, 4, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(4, 4, 7, 16, 8, 9)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case SOUTH -> Stream.of(
                                Block.createCuboidShape(12, 0, 1, 15, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(0, 4, 7, 12, 8, 9)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case WEST -> Stream.of(
                                Block.createCuboidShape(1, 0, 12, 15, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(7, 4, 0, 9, 8, 12)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case EAST -> Stream.of(
                                Block.createCuboidShape(1, 0, 1, 15, 12, 4),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(7, 4, 4, 9, 8, 16)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                    };
            case RIGHT ->
                    switch (state.get(FACING)) {
                        case DOWN -> null;
                        case UP -> null;
                        case NORTH -> Stream.of(
                                Block.createCuboidShape(12, 0, 1, 15, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(0, 4, 7, 12, 8, 9)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case SOUTH -> Stream.of(
                                Block.createCuboidShape(1, 0, 1, 4, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(4, 4, 7, 16, 8, 9)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case WEST -> Stream.of(
                                Block.createCuboidShape(1, 0, 1, 15, 12, 4),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(7, 4, 4, 9, 8, 16)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                        case EAST -> Stream.of(
                                Block.createCuboidShape(1, 0, 12, 15, 12, 15),
                                Block.createCuboidShape(0, 12, 0, 16, 16, 16),
                                Block.createCuboidShape(7, 4, 0, 9, 8, 12)
                        ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, BooleanBiFunction.OR)).get();
                    };

        };
    }
}
