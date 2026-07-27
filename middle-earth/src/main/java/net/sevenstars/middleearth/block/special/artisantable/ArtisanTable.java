package net.sevenstars.middleearth.block.special.artisantable;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME;
import net.sevenstars.middleearth.gui.artisantable.ArtisanTableScreenHandler;
import net.sevenstars.middleearth.resources.datas.common.DispositionType;
import net.sevenstars.middleearth.resources.persistent_datas.PlayerDataService;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class ArtisanTable extends HorizontalDirectionalBlock {
    public static final EnumProperty<ArtisanTablePart> PART = EnumProperty.create("part", ArtisanTablePart.class);
    private static final Component TITLE = Component.translatable("container.%s.artisan_table".formatted(MiddleEarth.MOD_ID));


    public ArtisanTable(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, ArtisanTablePart.LEFT).setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return simpleCodec(ArtisanTable::new);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if(!world.isClientSide) {
            ExtendedMenuProviderME.open(player, new ExtendedMenuProviderME() {
                @Override
                public void writeOpeningData(RegistryFriendlyByteBuf buffer) {
                    ServerPlayer serverPlayer = (ServerPlayer) player;
                    DispositionType dispositionType = PlayerDataService.getPlayerDisposition(player, world);
                    if (dispositionType == null){
                        dispositionType = DispositionType.NEUTRAL;
                    }
                    buffer.writeUtf(dispositionType + "/" + serverPlayer.isCreative());
                }

                @Override
                public Component getDisplayName() {
                    return TITLE;
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory, Player player) {
                    DispositionType dispositionType = PlayerDataService.getPlayerDisposition(player, world);
                    if (dispositionType == null){
                        dispositionType = DispositionType.NEUTRAL;
                    }
                    return new ArtisanTableScreenHandler(
                            syncId,
                            playerInventory,
                            ContainerLevelAccess.create(world, pos),
                            dispositionType + "/" + player.isCreative()
                    );
                }
            });
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (direction == getDirectionTowardsOtherPart((ArtisanTablePart)state.getValue(PART), (Direction)state.getValue(FACING).getClockWise())) {
            return neighborState.is(this) && neighborState.getValue(PART) != state.getValue(PART) ? (BlockState)state : Blocks.AIR.defaultBlockState();
        } else {
            return super.updateShape(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        ArtisanTablePart tablePart = (ArtisanTablePart)state.getValue(PART);
        ArtisanTablePart tablePartOpposite = (ArtisanTablePart)state.getValue(PART).getOpposite(state.getValue(PART));

        if (!world.isClientSide && (player.isCreative() || !player.hasCorrectToolForDrops(state))) {
            if (tablePart == ArtisanTablePart.RIGHT) {
                BlockPos blockPos = pos.relative(state.getValue(FACING).getCounterClockWise());
                BlockState blockState = world.getBlockState(blockPos);
                if (blockState.is(state.getBlock()) && blockState.getValue(PART) == ArtisanTablePart.LEFT) {
                    world.destroyBlock(blockPos, false);
                    world.levelEvent(player, 2001, blockPos, Block.getId(blockState));
                }
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }

    private static Direction getDirectionTowardsOtherPart(ArtisanTablePart part, Direction direction) {
        return part == ArtisanTablePart.LEFT ? direction : direction.getOpposite();
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection();
        BlockPos blockPos = ctx.getClickedPos();
        BlockPos blockPos2 = blockPos.relative(direction.getClockWise());
        Level world = ctx.getLevel();
        return world.getBlockState(blockPos2).canBeReplaced(ctx) && world.getWorldBorder().isWithinBounds(blockPos2) ? (BlockState)this.defaultBlockState().setValue(FACING, direction).setValue(PART, ArtisanTablePart.LEFT) : null;
    }

    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        if (!world.isClientSide) {
            BlockPos blockPos = pos.relative((Direction)state.getValue(FACING).getClockWise());
            world.setBlock(blockPos, (BlockState)state.setValue(PART, ArtisanTablePart.RIGHT), 3);
            world.updateNeighborsAt(pos, Blocks.AIR);
            state.updateNeighbourShapes(world, pos, 3);
        }
    }

    public long getSeed(BlockState state, BlockPos pos) {
        BlockPos blockPos = pos.relative((Direction)state.getValue(FACING), state.getValue(PART) == ArtisanTablePart.RIGHT ? 0 : 1);
        return Mth.getSeed(blockPos.getX(), pos.getY(), blockPos.getZ());
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING, PART);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(PART)){
            case LEFT ->
                    switch (state.getValue(FACING)){
                        case DOWN, UP -> null;
                        case NORTH -> Stream.of(
                                Block.box(1, 0, 1, 4, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(4, 4, 7, 16, 8, 9)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                        case SOUTH -> Stream.of(
                                Block.box(12, 0, 1, 15, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(0, 4, 7, 12, 8, 9)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                        case WEST -> Stream.of(
                                Block.box(1, 0, 12, 15, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(7, 4, 0, 9, 8, 12)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                        case EAST -> Stream.of(
                                Block.box(1, 0, 1, 15, 12, 4),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(7, 4, 4, 9, 8, 16)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                    };
            case RIGHT ->
                    switch (state.getValue(FACING)) {
                        case DOWN -> null;
                        case UP -> null;
                        case NORTH -> Stream.of(
                                Block.box(12, 0, 1, 15, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(0, 4, 7, 12, 8, 9)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                        case SOUTH -> Stream.of(
                                Block.box(1, 0, 1, 4, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(4, 4, 7, 16, 8, 9)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                        case WEST -> Stream.of(
                                Block.box(1, 0, 1, 15, 12, 4),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(7, 4, 4, 9, 8, 16)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                        case EAST -> Stream.of(
                                Block.box(1, 0, 12, 15, 12, 15),
                                Block.box(0, 12, 0, 16, 16, 16),
                                Block.box(7, 4, 0, 9, 8, 12)
                        ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();
                    };
        };
    }
}
