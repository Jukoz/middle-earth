package net.sevenstars.middleearth.block.special.crockpot;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import net.sevenstars.middleearth.block.registration.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlock;
import org.jetbrains.annotations.Nullable;

public class CrockpotBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty HANGING = BooleanProperty.create("hanging");

    public CrockpotBlock(Properties settings) {
        super(settings);
        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(HANGING, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CrockpotBlock.simpleCodec(CrockpotBlock::new);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        BlockEntity blockEntity = player.level().getBlockEntity(pos);

        if(blockEntity instanceof CrockpotBlockEntity crockpotBlockEntity) {
            boolean filled = crockpotBlockEntity.fill(stack);
            if(filled) {
                ItemStack remainder = stack.getCraftingRemainingItem();
                player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, remainder).copy());
                player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
                world.playSound(null, pos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.1F);
                world.gameEvent(null, GameEvent.FLUID_PLACE, pos);
                return ItemInteractionResult.SUCCESS;
            } else {
                if(stack.isEmpty()) {
                    MenuProvider screenHandlerFactory = state.getMenuProvider(world, pos);
                    if(screenHandlerFactory != null) {
                        if (screenHandlerFactory instanceof net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME extendedProvider) {
                        net.sevenstars.middleearth.block.utils.ExtendedMenuProviderME.open(player, extendedProvider);
                    } else {
                        player.openMenu(screenHandlerFactory);
                    }
                    }
                    return ItemInteractionResult.CONSUME;
                } else {
                    ItemStack stackResult = crockpotBlockEntity.fillBowl(stack.getItem());
                    if(!stackResult.isEmpty()) {
                        stack.shrink(1);
                        world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 0.9F);

                        if(world.isClientSide) {
                            if (stack.isEmpty()) {
                                player.setItemInHand(hand, stackResult);
                            } else if (!player.getInventory().add(stackResult)) {
                                player.drop(stackResult, false);
                            }
                        }

                        world.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
                        return ItemInteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CrockpotBlockEntity crockpot) {
                for (int slot = 0; slot < CrockpotBlockEntity.OUTPUT_SLOT; slot++) {
                    Containers.dropItemStack(world, pos.getX(), pos.getY(), pos.getZ(), crockpot.getItem(slot));
                }
            }
        }
        super.onRemove(state, world, pos, newState, moved);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(4.0, 0.0, 4.0, 12, 7.0, 12.0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, HANGING);
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        BlockGetter blockView = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        BlockPos bottomBlockPos = blockPos.below();
        BlockState blockState = blockView.getBlockState(bottomBlockPos);
        if(blockState.is(BlockTags.CAMPFIRES) || blockState.is(ModDecorativeBlocks.BONFIRE)) {
            return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite()).setValue(HANGING, true);
        }
        return this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CrockpotBlockEntity(pos, state, 0.375f);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        return null; //validateTicker(world, type, ModBlockEntities.CROCKPOT);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> givenType, BlockEntityType<CrockpotBlockEntity> expectedType) {
        return world.isClientSide
                ? BellowsBlock.createTickerHelper(givenType, expectedType, CrockpotBlockEntity::clientTick)
                : BellowsBlock.createTickerHelper(givenType, expectedType, CrockpotBlockEntity::serverTick);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        super.animateTick(state, world, pos, random);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof CrockpotBlockEntity crockpotBlockEntity) {
            if(crockpotBlockEntity.isCooking()) {

            }
        }

    }
}
