package net.sevenstars.middleearth.block.special.structureManager;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class StructureManagerBlock extends BaseEntityBlock implements EntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;

    public StructureManagerBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(((this.stateDefinition.any()).setValue(FACING, Direction.NORTH)));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(StructureManagerBlock::new);
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalDirection().getOpposite();

        return this.defaultBlockState().setValue(FACING, direction);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)){
            case DOWN, UP -> null;
            case NORTH -> Block.box(0, 0, 2, 16, 16, 16);
            case WEST -> Block.box(2, 0, 0, 16, 16, 16);
            case SOUTH -> Block.box(0, 0, 0, 16, 16, 14);
            case EAST -> Block.box(0, 0, 0, 14, 16, 16);
        };
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        } else if(player.isCreative() && player.canUseGameMasterBlocks()) {
            MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
            if(screenHandlerFactory != null) {
                if (screenHandlerFactory instanceof net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME extendedProvider) {
                        net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME.open(player, extendedProvider);
                    } else {
                        player.openMenu(screenHandlerFactory);
                    }
            }
            return InteractionResult.CONSUME;
        }
        return InteractionResult.FAIL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StructureManagerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type, ModBlockEntities.STRUCTURE_MANAGER);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<StructureManagerBlockEntity> expectedType) {
        BlockEntityTicker ticker;
        if (world.isClientSide) {
            return null;
        }

        ticker = createTickerHelper(givenType, expectedType, (worldx, pos, state, blockEntity) -> {
            StructureManagerBlockEntity.tickEvent(world, pos, state, blockEntity);
        });

        return ticker;
    }
}
