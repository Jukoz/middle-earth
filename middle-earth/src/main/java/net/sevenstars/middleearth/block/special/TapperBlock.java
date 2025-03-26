package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.sevenstars.middleearth.block.WoodBlockSets;
import net.sevenstars.middleearth.item.ModFoodItems;

public class TapperBlock extends HorizontalFacingBlock {
    public static final int FULL_TAP_LEVEL = 5;
    public static final IntProperty TAP_LEVEL = IntProperty.of("tap_level", 0, FULL_TAP_LEVEL);
    public static final int RANDOM_TICK_CHANCE = 1;

    public TapperBlock(Settings settings) {
        super(settings.ticksRandomly());

        this.setDefaultState(((this.stateManager.getDefaultState())
                .with(TAP_LEVEL, 0))
                .with(FACING, Direction.NORTH));
    }

    protected int getComparatorOutput(BlockState state, World world, BlockPos pos) {
        return state.get(TAP_LEVEL);
    }

    @Override
    protected MapCodec<? extends HorizontalFacingBlock> getCodec() {
        return null;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState behindBlock = world.getBlockState(pos.offset(state.get(FACING).getOpposite()));
        return behindBlock.isIn(BlockTags.LOGS);
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case SOUTH:
                return Block.createCuboidShape(4.0, 0.0, 0.0, 12.0, 10.0, 8.0);
            default:
                return Block.createCuboidShape(4.0, 0.0, 8.0, 12.0, 10.0, 16.0);
            case WEST:
                return Block.createCuboidShape(8.0, 0.0, 4.0, 16.0, 10.0, 12.0);
            case EAST:
                return Block.createCuboidShape(0.0, 0.0, 4.0, 8.0, 10.0, 12.0);
        }
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        int i = state.get(TAP_LEVEL);
        boolean bl = false;
        if (i >= FULL_TAP_LEVEL) {
            Item item = stack.getItem();

            BlockState behindBlock = world.getBlockState(pos.offset(state.get(FACING).getOpposite()));

            Item result = Items.RESIN_CLUMP;
            if(behindBlock.getBlock() == WoodBlockSets.MAPLE.log() || behindBlock.getBlock() == WoodBlockSets.SILVER_MAPLE.log()) {
                result = ModFoodItems.MAPLE_SYRUP;
            } else if (behindBlock.getBlock() == Blocks.BIRCH_LOG) {
                result = ModFoodItems.BIRCH_WATER;
            }

            ItemStack stackResult = new ItemStack(result);
            if(result.equals(Items.RESIN_CLUMP)) {
                if (!player.getInventory().insertStack(stackResult)) {
                    player.dropItem(stackResult, false);
                }
                world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                emptyBucket(state, world, pos);
            }
            else if (stack.isOf(Items.GLASS_BOTTLE)) {
                if(result != Items.RESIN_CLUMP) stack.decrement(1);
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1.0F, 1.1F);

                if (stack.isEmpty()) {
                    player.setStackInHand(hand, stackResult);
                } else if (!player.getInventory().insertStack(stackResult)) {
                    player.dropItem(stackResult, false);
                }

                bl = true;
                world.emitGameEvent(player, GameEvent.FLUID_PICKUP, pos);
                emptyBucket(state, world, pos);
            }

            if (!world.isClient() && bl) {
                player.incrementStat(Stats.USED.getOrCreateStat(item));
            }
        }

        if (bl) {
            return ActionResult.SUCCESS;
        } else {
            return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
        }
    }

    private void emptyBucket(BlockState state, World world, BlockPos pos) {
        if(world instanceof ServerWorld serverWorld) {
            serverWorld.setBlockState(pos, state.with(TAP_LEVEL, 0), 2);
        }
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        int rand = world.random.nextInt(RANDOM_TICK_CHANCE);
        if (rand == 0) {
            int i = state.get(TAP_LEVEL);
            if (i < FULL_TAP_LEVEL) {
                world.setBlockState(pos, state.with(TAP_LEVEL, i + 1), 2);
            }
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TAP_LEVEL, FACING);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
