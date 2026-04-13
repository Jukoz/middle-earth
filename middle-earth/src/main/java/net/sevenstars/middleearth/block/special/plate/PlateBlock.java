package net.sevenstars.middleearth.block.special.plate;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponent;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.UseRemainderComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class PlateBlock extends BlockWithEntity {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty UTENSILS = BooleanProperty.of("utensils");

    public PlateBlock(Settings settings) {
        super(settings);

        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(UTENSILS, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return PlateBlock.createCodec(PlateBlock::new);
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.down();
        return hasTopRim(world, blockPos) || sideCoversSmallSquare(world, blockPos, Direction.UP);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        ActionResult result = onEat(world, pos, player);
        if(result == ActionResult.PASS || result == ActionResult.FAIL) {
            if(world instanceof ServerWorld serverWorld) {
                boolean hasUtensils = state.get(UTENSILS);
                serverWorld.setBlockState(pos, state.with(UTENSILS, !hasUtensils), 2);
            }
        }

        return result;
    }

    protected static ActionResult onEat(World world, BlockPos pos, PlayerEntity player) {
        if (world.isClient) {
            if (tryEat(world, pos, player).isAccepted()) {
                return ActionResult.SUCCESS;
            }
            if (player.getStackInHand(Hand.MAIN_HAND).isEmpty()) {
                return ActionResult.CONSUME;
            }
        }
        return tryEat(world, pos, player);
    }

    protected static ActionResult tryEat(World world, BlockPos pos, PlayerEntity player) {
        if (!player.canConsume(false)) {
            return ActionResult.PASS;
        }

        PlateBlockEntity plateBlockEntity = (PlateBlockEntity) world.getBlockEntity(pos);
        if(plateBlockEntity == null) return ActionResult.FAIL;

        ItemStack food = plateBlockEntity.getStack();
        if(food == null || food.isEmpty()) {
            return ActionResult.PASS;
        }

        FoodComponent foodComponent = food.get(DataComponentTypes.FOOD);
        ConsumableComponent consumableComponent = food.get(DataComponentTypes.CONSUMABLE);
        if(foodComponent != null && consumableComponent != null) {
            if(!world.isClient) {
                player.getHungerManager().add((foodComponent.nutrition() / 2), foodComponent.saturation());
            }
            foodComponent.onConsume(player.getWorld(), player, food, consumableComponent);
            world.emitGameEvent(player, GameEvent.EAT, pos);

            ItemStack remainderItem = ItemStack.EMPTY;
            UseRemainderComponent useRemainderComponent = food.get(DataComponentTypes.USE_REMAINDER);
            if(useRemainderComponent != null) remainderItem = useRemainderComponent.convertInto();
            plateBlockEntity.setStack(remainderItem);
        }

        return ActionResult.SUCCESS;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if(stack.getItem() instanceof BlockItem blockItem) {
            if(blockItem.getBlock() instanceof PlateBlock) {
                return ActionResult.PASS;
            }
        }

        if(stack.contains(DataComponentTypes.FOOD)) {
            BlockEntity blockEntity = player.getWorld().getBlockEntity(pos);
            if(blockEntity instanceof PlateBlockEntity plateBlockEntity) {
                ItemStack plateStack = plateBlockEntity.getStack();
                if(plateStack == null || plateStack.isEmpty()) {
                    ItemStack insertedStack = stack.copy();
                    insertedStack.setCount(1);
                    plateBlockEntity.setStack(insertedStack);
                    stack.decrementUnlessCreative(1, player);
                    return ActionResult.SUCCESS;
                } else if (world.isClient) {
                    return onEat(world, pos, player);
                }
            } else {
                return ActionResult.FAIL;
            }
        }

        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 1.0, 13.0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, UTENSILS);
        super.appendProperties(builder);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(UTENSILS, false);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PlateBlockEntity(pos, state);
    }
}
