package net.sevenstars.middleearth.block.special.crockpot;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.sevenstars.middleearth.block.ModBlockEntities;
import net.sevenstars.middleearth.block.ModDecorativeBlocks;
import net.sevenstars.middleearth.block.special.bellows.BellowsBlock;
import net.sevenstars.middleearth.block.special.forge.ForgeBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CrockpotBlock extends BlockWithEntity {
    public static final EnumProperty<Direction> FACING = Properties.HORIZONTAL_FACING;
    public static final BooleanProperty HANGING = BooleanProperty.of("hanging");

    public CrockpotBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(FACING, Direction.NORTH).with(HANGING, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec() {
        return CrockpotBlock.createCodec(CrockpotBlock::new);
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        BlockEntity blockEntity = player.getWorld().getBlockEntity(pos);
        if(blockEntity instanceof CrockpotBlockEntity crockpotBlockEntity) {
            boolean filled = crockpotBlockEntity.fill(stack);
            if(filled) {
                ItemStack remainder = stack.getRecipeRemainder();
                player.setStackInHand(hand, ItemUsage.exchangeStack(stack, player, remainder).copy());
                player.incrementStat(Stats.USED.getOrCreateStat(stack.getItem()));
                world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.1F);
                world.emitGameEvent(null, GameEvent.FLUID_PLACE, pos);
                return ActionResult.SUCCESS;
            } else {
                ItemStack stackResult = crockpotBlockEntity.fillBowl(stack.getItem());
                if(!stackResult.isEmpty()) {
                    stack.decrement(1);
                    world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 0.9F);

                    if (stack.isEmpty()) {
                        player.setStackInHand(hand, stackResult);
                    } else if (!player.getInventory().insertStack(stackResult)) {
                        player.dropItem(stackResult, false);
                    }
                    world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                } else {
                    if (world.isClient) {
                        return ActionResult.SUCCESS;
                    } else {
                        NamedScreenHandlerFactory screenHandlerFactory = state.createScreenHandlerFactory(world, pos);
                        if(screenHandlerFactory != null) {
                            player.openHandledScreen(screenHandlerFactory);
                        }
                        return ActionResult.CONSUME;
                    }
                }
            }
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(4.0, 0.0, 4.0, 12, 7.0, 12.0);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, HANGING);
        super.appendProperties(builder);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockView blockView = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockPos bottomBlockPos = blockPos.down();
        BlockState blockState = blockView.getBlockState(bottomBlockPos);
        if(blockState.isIn(BlockTags.CAMPFIRES) || blockState.isOf(ModDecorativeBlocks.BONFIRE)) {
            return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(HANGING, true);
        }
        return this.getDefaultState().with(Properties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CrockpotBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return validateTicker(world, type, ModBlockEntities.CROCKPOT);
    }

    @Nullable
    protected static <T extends BlockEntity> BlockEntityTicker<T> validateTicker(World world, BlockEntityType<T> givenType, BlockEntityType<CrockpotBlockEntity> expectedType) {
        return world.isClient
                ? BellowsBlock.validateTicker(givenType, expectedType, CrockpotBlockEntity::clientTick)
                : BellowsBlock.validateTicker(givenType, expectedType, CrockpotBlockEntity::serverTick);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if(blockEntity instanceof CrockpotBlockEntity crockpotBlockEntity) {
            if(crockpotBlockEntity.isCooking()) {

            }
        }

    }
}
