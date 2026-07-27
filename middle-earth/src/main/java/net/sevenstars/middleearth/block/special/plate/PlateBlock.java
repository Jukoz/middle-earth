package net.sevenstars.middleearth.block.special.plate;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.*;
import net.minecraft.world.item.component.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
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
import net.minecraft.world.ticks.TickPriority;
import net.sevenstars.middleearth.block.registration.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PlateBlock extends BaseEntityBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty UTENSILS = BooleanProperty.create("utensils");

    public PlateBlock(Properties settings) {
        super(settings);

        registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH).setValue(UTENSILS, false));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return PlateBlock.simpleCodec(PlateBlock::new);
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.setPlacedBy(world, pos, state, placer, itemStack);
        PlateBlockEntity plateBlockEntity = (PlateBlockEntity) world.getBlockEntity(pos);
        if(plateBlockEntity != null && world instanceof ServerLevel serverWorld) {
            SeededContainerLoot containerLootComponent = itemStack.get(DataComponents.CONTAINER_LOOT);
            if(containerLootComponent != null) {
                plateBlockEntity.setLootTable(containerLootComponent.lootTable(), containerLootComponent.seed());
            }
        }
    }

    @Override
    protected void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify) {
        super.onPlace(state, world, pos, oldState, notify);
        PlateBlockEntity plateBlockEntity = (PlateBlockEntity) world.getBlockEntity(pos);
        if(plateBlockEntity != null && world instanceof ServerLevel serverWorld) {
            serverWorld.scheduleTick(pos, this, 1, TickPriority.NORMAL);
        }
    }

    @Override
    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        super.tick(state, world, pos, random);
        PlateBlockEntity plateBlockEntity = (PlateBlockEntity) world.getBlockEntity(pos);
        if(plateBlockEntity != null && plateBlockEntity.isBlockPlaced()) {
            plateBlockEntity.generateItem(world);
        }
        plateBlockEntity.setBlockPlaced();
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        PlateBlockEntity plateBlockEntity = (PlateBlockEntity) world.getBlockEntity(pos);
        if(plateBlockEntity != null && plateBlockEntity.isBlockPlaced()) {
            plateBlockEntity.generateItem(world);
        }
        super.randomTick(state, world, pos, random);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level world, BlockState state, BlockEntityType<T> type) {
        if (!world.isClientSide) {
            return PlateBlock.validateTicker(world, type, ModBlockEntities.PLATE);
        }
        return super.getTicker(world, state, type);
    }

    private static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(Level world, BlockEntityType<T> type, BlockEntityType<PlateBlockEntity> plate) {
        return PlateBlock.createTickerHelper(type, plate, PlateBlockEntity::tick);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return Block.canSupportCenter(world, pos.below(), Direction.UP);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(world, pos) ?
                Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionResult result = onEat(world, pos, player);
        if(result == InteractionResult.PASS || result == InteractionResult.FAIL) {
            if(world instanceof ServerLevel serverWorld) {
                boolean hasUtensils = state.getValue(UTENSILS);
                serverWorld.setBlock(pos, state.setValue(UTENSILS, !hasUtensils), 2);
            }
        }

        return result;
    }

    protected static InteractionResult onEat(Level world, BlockPos pos, Player player) {
        if (world.isClientSide) {
            if (tryEat(world, pos, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }
            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }
        return tryEat(world, pos, player);
    }

    protected static InteractionResult tryEat(Level world, BlockPos pos, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        }

        PlateBlockEntity plateBlockEntity = (PlateBlockEntity) world.getBlockEntity(pos);
        if(plateBlockEntity == null) return InteractionResult.FAIL;

        ItemStack food = plateBlockEntity.getTheItem();
        if(food == null || food.isEmpty()) {
            return InteractionResult.PASS;
        }

        FoodProperties foodComponent = food.get(DataComponents.FOOD);
        if(foodComponent != null) {
            if(!world.isClientSide) {
                player.getFoodData().eat((foodComponent.nutrition() / 2), foodComponent.saturation());
                for (FoodProperties.PossibleEffect possibleEffect : foodComponent.effects()) {
                    if (world.random.nextFloat() < possibleEffect.probability()) {
                        player.addEffect(possibleEffect.effect());
                    }
                }
            }
            world.gameEvent(player, GameEvent.EAT, pos);

            ItemStack remainderItem = foodComponent.usingConvertsTo()
                    .map(ItemStack::copy)
                    .orElse(ItemStack.EMPTY);
            plateBlockEntity.setTheItem(remainderItem);
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(stack.getItem() instanceof BlockItem blockItem) {
            if(blockItem.getBlock() instanceof PlateBlock) {
                return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
            }
        }

        if(stack.has(DataComponents.FOOD)) {
            BlockEntity blockEntity = player.level().getBlockEntity(pos);
            if(blockEntity instanceof PlateBlockEntity plateBlockEntity) {
                ItemStack plateStack = plateBlockEntity.getTheItem();
                if(plateStack == null || plateStack.isEmpty()) {
                    ItemStack insertedStack = stack.copy();
                    insertedStack.setCount(1);
                    plateBlockEntity.setTheItem(insertedStack);
                    stack.consume(1, player);
                    return ItemInteractionResult.SUCCESS;
                } else if (world.isClientSide) {
                    InteractionResult result = onEat(world, pos, player);
                    if (result == InteractionResult.FAIL) {
                        return ItemInteractionResult.FAIL;
                    }
                    if (result == InteractionResult.CONSUME || result == InteractionResult.CONSUME_PARTIAL) {
                        return ItemInteractionResult.CONSUME;
                    }
                    return result.consumesAction()
                            ? ItemInteractionResult.SUCCESS
                            : ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
                }
            } else {
                return ItemInteractionResult.FAIL;
            }
        }

        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Block.box(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, UTENSILS);
        super.createBlockStateDefinition(builder);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction[] var3 = ctx.getNearestLookingDirections();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite()).setValue(UTENSILS, false);
                if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
                    return blockState;
                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new PlateBlockEntity(pos, state);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
}
