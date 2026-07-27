package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.sevenstars.middleearth.block.registration.WoodBlockSets;
import net.sevenstars.middleearth.block.registration.ModNatureBlocks;
import net.sevenstars.middleearth.item.FoodItemsME;

public class TapperBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<TapperBlock> CODEC = simpleCodec(TapperBlock::new);
    public static final int FULL_TAP_LEVEL = 5;
    public static final IntegerProperty TAP_LEVEL = IntegerProperty.create("tap_level", 0, FULL_TAP_LEVEL);
    public static final int RANDOM_TICK_CHANCE = 7;
    private static final Item RESIN_CLUMP = ModNatureBlocks.RESIN_CLUMP.asItem();

    public TapperBlock(Properties settings) {
        super(settings.randomTicks());

        this.registerDefaultState(((this.stateDefinition.any())
                .setValue(TAP_LEVEL, 0))
                .setValue(FACING, Direction.NORTH));
    }

    protected int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return state.getValue(TAP_LEVEL);
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState behindBlock = world.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));
        return behindBlock.is(BlockTags.LOGS);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case SOUTH -> Block.box(4.0, 0.0, 0.0, 12.0, 10.0, 8.0);
            default -> Block.box(4.0, 0.0, 8.0, 12.0, 10.0, 16.0);
            case WEST -> Block.box(8.0, 0.0, 4.0, 16.0, 10.0, 12.0);
            case EAST -> Block.box(0.0, 0.0, 4.0, 8.0, 10.0, 12.0);
        };
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        int i = state.getValue(TAP_LEVEL);
        boolean bl = false;
        if (i >= FULL_TAP_LEVEL) {
            Item item = stack.getItem();

            BlockState behindBlock = world.getBlockState(pos.relative(state.getValue(FACING).getOpposite()));

            Item result = RESIN_CLUMP;
            if(behindBlock.getBlock() == WoodBlockSets.MAPLE_SET.logBlocks.log() || behindBlock.getBlock() == WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log()) {
                result = FoodItemsME.MAPLE_SYRUP;
            } else if (behindBlock.getBlock() == Blocks.BIRCH_LOG) {
                result = FoodItemsME.BIRCH_WATER;
            }

            ItemStack stackResult = new ItemStack(result);
            if(result.equals(RESIN_CLUMP)) {
                if (!player.getInventory().add(stackResult)) {
                    player.drop(stackResult, false);
                }
                world.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
                emptyBucket(state, world, pos);
            }
            else if (stack.is(Items.GLASS_BOTTLE)) {
                if(result != RESIN_CLUMP) stack.shrink(1);
                world.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL, SoundSource.BLOCKS, 1.0F, 1.1F);

                if (stack.isEmpty()) {
                    player.setItemInHand(hand, stackResult);
                } else if (!player.getInventory().add(stackResult)) {
                    player.drop(stackResult, false);
                }

                bl = true;
                world.gameEvent(player, GameEvent.FLUID_PICKUP, pos);
                emptyBucket(state, world, pos);
            }

            if (!world.isClientSide() && bl) {
                player.awardStat(Stats.ITEM_USED.get(item));
            }
        }

        if (bl) {
            return ItemInteractionResult.SUCCESS;
        } else {
            return super.useItemOn(stack, state, world, pos, player, hand, hit);
        }
    }

    private void emptyBucket(BlockState state, Level world, BlockPos pos) {
        if(world instanceof ServerLevel serverWorld) {
            serverWorld.setBlock(pos, state.setValue(TAP_LEVEL, 0), 2);
        }
    }

    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int rand = world.random.nextInt(RANDOM_TICK_CHANCE);
        if (rand == 0) {
            int i = state.getValue(TAP_LEVEL);
            if (i < FULL_TAP_LEVEL) {
                world.setBlock(pos, state.setValue(TAP_LEVEL, i + 1), 2);
            }
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(TAP_LEVEL, FACING);
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
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
