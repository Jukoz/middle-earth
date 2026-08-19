package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CandleBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class LayeredCakeBlock extends Block {
    public static final MapCodec<LayeredCakeBlock> CODEC = simpleCodec(LayeredCakeBlock::new);
    public static final int MAX_BITES = 8;
    public static final IntegerProperty BITES;
    public static final int DEFAULT_COMPARATOR_OUTPUT;
    private static final VoxelShape[] SHAPES_BY_BITES;

    public MapCodec<LayeredCakeBlock> codec() {
        return CODEC;
    }

    public LayeredCakeBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState((this.stateDefinition.any()));
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_BITES[state.getValue(BITES)];
    }

    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        Item item = stack.getItem();
        if (stack.is(ItemTags.CANDLES) && (Integer)state.getValue(BITES) == 0) {
            Block var10 = Block.byItem(item);
            if (var10 instanceof CandleBlock) {
                CandleBlock candleBlock = (CandleBlock)var10;
                stack.consume(1, player);
                world.playSound(null, pos, SoundEvents.CAKE_ADD_CANDLE, SoundSource.BLOCKS, 1.0F, 1.0F);
                world.setBlockAndUpdate(pos, CandleLayeredCakeBlock.getCandleCakeFromCandle(candleBlock));
                world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
                player.awardStat(Stats.ITEM_USED.get(item));
                return ItemInteractionResult.SUCCESS;
            }
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        if (world.isClientSide) {
            if (tryEat(world, pos, state, player).consumesAction()) {
                return InteractionResult.SUCCESS;
            }

            if (player.getItemInHand(InteractionHand.MAIN_HAND).isEmpty()) {
                return InteractionResult.CONSUME;
            }
        }

        return tryEat(world, pos, state, player);
    }

    protected static InteractionResult tryEat(LevelAccessor world, BlockPos pos, BlockState state, Player player) {
        if (!player.canEat(false)) {
            return InteractionResult.PASS;
        } else {
            player.awardStat(Stats.EAT_CAKE_SLICE);
            player.getFoodData().eat(3, 0.15F);
            int i = state.getValue(BITES);
            world.gameEvent(player, GameEvent.EAT, pos);
            if (i < MAX_BITES) {
                world.setBlock(pos, state.setValue(BITES, i + 1), 3);
            } else {
                world.removeBlock(pos, false);
                world.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
            }

            return InteractionResult.SUCCESS;
        }
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return world.getBlockState(pos.below()).isSolid();
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(BITES);
    }

    protected int getAnalogOutputSignal(BlockState state, Level world, BlockPos pos) {
        return getComparatorOutput(state.getValue(BITES));
    }

    public static int getComparatorOutput(int bites) {
        return (MAX_BITES - bites) * 2;
    }

    protected boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }

    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    static {
        BITES = IntegerProperty.create("bites", 0, MAX_BITES);;
        DEFAULT_COMPARATOR_OUTPUT = getComparatorOutput(0);
        SHAPES_BY_BITES = new VoxelShape[MAX_BITES + 1];
        for (int bites = 0; bites <= MAX_BITES; bites++) {
            SHAPES_BY_BITES[bites] = Shapes.or(
                    Block.box(0, 0.0, 0, 16.0, 8.0, 16.0 - (Math.clamp(bites - 4, 0, 4) * 4) + Math.clamp(bites - 4, 0, 1) * 2),
                    Block.box(2, 8, 2, 14, 16, 14 - (Math.clamp(bites, 0, 4) * 3))
            );
        }
    }
}
