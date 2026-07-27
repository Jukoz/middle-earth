package net.sevenstars.middleearth.block.special.plants;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class MistweedPlantBlock extends CustomPlantBlock implements BonemealableBlock {
    public static final MapCodec<CustomPlantBlock> CODEC = simpleCodec(MistweedPlantBlock::new);
    public static final BooleanProperty ACTIVE = BooleanProperty.create("active");

    public MistweedPlantBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(ACTIVE, true));
    }

    @Override
    protected MapCodec<CustomPlantBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE);
    }

    @Override
    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(ACTIVE) && random.nextDouble() <= 0.45) {
            double d = (double)pos.getX() + random.nextDouble() * 16.0 - 8.0;
            double e = (double)pos.getY() + random.nextDouble() * 5.0;
            double f = (double)pos.getZ() + random.nextDouble() * 16.0 - 8.0;
            world.addAlwaysVisibleParticle(ModParticleTypes.BIOME_FOG_PARTICLE, true, d, e, f, 0.0, 0.0, 0.0);
        }
        super.animateTick(state, world, pos, random);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionHand hand = player.getUsedItemHand();
        if (!world.isClientSide && player.getAbilities().mayBuild) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (!state.getValue(ACTIVE) && itemStack.is(ItemTags.AXES)) {
                activateState(null, state, world, pos);
            } else if (state.getValue(ACTIVE) && itemStack.is(Items.HONEYCOMB)) {
                deactivateState(null, state, world, pos);
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected static void activateState(@Nullable Player player, BlockState state, Level world, BlockPos pos) {
        world.setBlock(pos, state.setValue(ACTIVE, true), 11);
        world.playSound(null, pos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        ParticleUtils.spawnParticles(world, pos, 3, 3.0, 1.0, true, ParticleTypes.WAX_OFF);
    }

    protected static void deactivateState(@Nullable Player player, BlockState state, Level world, BlockPos pos) {
        world.setBlock(pos, state.setValue(ACTIVE, false), 11);
        world.playSound(null, pos, SoundEvents.HONEYCOMB_WAX_ON, SoundSource.BLOCKS, 1.0F, 1.0F);
        world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
        ParticleUtils.spawnParticles(world, pos, 3, 3.0, 1.0, true, ParticleTypes.WAX_ON);
    }

    public boolean isValidBonemealTarget(LevelReader world, BlockPos pos, BlockState state) {
        return Direction.Plane.HORIZONTAL.stream().anyMatch(direction -> {
            BlockPos targetPos = pos.relative(direction);
            return world.isEmptyBlock(targetPos) && state.canSurvive(world, targetPos);
        });
    }

    public boolean isBonemealSuccess(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        findSpreadableNeighbourPos(world, pos, state).ifPresent((blockPos) -> {
            world.setBlockAndUpdate(blockPos, this.defaultBlockState());
        });
    }

    private static Optional<BlockPos> findSpreadableNeighbourPos(ServerLevel world, BlockPos pos, BlockState state) {
        for (Direction direction : Direction.Plane.HORIZONTAL.shuffledCopy(world.random)) {
            BlockPos targetPos = pos.relative(direction);
            if (world.isEmptyBlock(targetPos) && state.canSurvive(world, targetPos)) {
                return Optional.of(targetPos);
            }
        }
        return Optional.empty();
    }
}
