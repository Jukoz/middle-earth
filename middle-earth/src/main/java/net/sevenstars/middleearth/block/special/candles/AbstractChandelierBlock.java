package net.sevenstars.middleearth.block.special.candles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public abstract class AbstractChandelierBlock extends Block {
    public static final IntegerProperty VARIANT = IntegerProperty.create("variant", 1, 2);
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE = state -> state.getValue(LIT) ? 15 : 0;

    protected AbstractChandelierBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState().setValue(VARIANT, 1).setValue(LIT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(VARIANT, LIT);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = defaultBlockState().setValue(VARIANT, 1).setValue(LIT, false);
        return state.canSurvive(context.getLevel(), context.getClickedPos()) ? state : null;
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction direction, BlockState neighborState,
                                     LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        return state.canSurvive(level, pos)
                ? super.updateShape(state, direction, neighborState, level, pos, neighborPos)
                : Blocks.AIR.defaultBlockState();
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        return Block.canSupportCenter(level, pos.above(), Direction.DOWN);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild
                || !player.hasInfiniteMaterials()
                || !player.getMainHandItem().isEmpty()) {
            return super.useWithoutItem(state, level, pos, player, hit);
        }

        if (!level.isClientSide) {
            level.setBlock(pos, state.cycle(VARIANT), Block.UPDATE_ALL_IMMEDIATE);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hit) {
        if (!player.getAbilities().mayBuild) {
            return super.useItemOn(stack, state, level, pos, player, hand, hit);
        }

        if (state.getValue(LIT) && stack.is(ItemTags.SHOVELS)) {
            if (!level.isClientSide) {
                extinguish(player, state, level, pos);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!state.getValue(LIT) && (stack.is(Items.FLINT_AND_STEEL) || stack.is(Items.TORCH))) {
            if (!level.isClientSide) {
                setLit(level, state, pos, true);
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.useItemOn(stack, state, level, pos, player, hand, hit);
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            particleOffsets(state).forEach(offset ->
                    spawnCandleParticles(level, offset.add(pos.getX(), pos.getY(), pos.getZ()), random));
        }
    }

    protected abstract Iterable<Vec3> particleOffsets(BlockState state);

    private static void spawnCandleParticles(Level level, Vec3 position, RandomSource random) {
        float chance = random.nextFloat();
        if (chance < 0.3F) {
            level.addParticle(ParticleTypes.SMOKE, position.x, position.y, position.z, 0.0, 0.0, 0.0);
            if (chance < 0.17F) {
                level.playLocalSound(position.x + 0.5, position.y + 0.5, position.z + 0.5,
                        SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS,
                        1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        level.addParticle(ParticleTypes.SMALL_FLAME, position.x, position.y, position.z, 0.0, 0.0, 0.0);
    }

    protected static void setLit(LevelAccessor level, BlockState state, BlockPos pos, boolean lit) {
        level.setBlock(pos, state.setValue(LIT, lit), Block.UPDATE_ALL_IMMEDIATE);
        if (lit) {
            level.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.5F, 1.0F);
        }
    }

    protected static void extinguish(@Nullable Player player, BlockState state, LevelAccessor level, BlockPos pos) {
        setLit(level, state, pos, false);
        level.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.5F, 1.0F);
        level.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.EMPTY.defaultFluidState();
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!level.isClientSide && projectile.isOnFire() && !state.getValue(LIT)) {
            level.setBlock(hit.getBlockPos(), state.setValue(LIT, true), Block.UPDATE_ALL_IMMEDIATE);
        }
    }
}
