package net.sevenstars.middleearth.block.special.fireBlocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractToggleableFireBlock extends BaseEntityBlock {

    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public AbstractToggleableFireBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.stateDefinition.any().setValue(LIT, true));
    }

    protected abstract MapCodec<? extends AbstractToggleableFireBlock> codec();

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit) {
        InteractionHand hand = player.getUsedItemHand();
        if (!world.isClientSide && player.getAbilities().mayBuild) {
            if(player.hasInfiniteMaterials()){
                world.setBlockAndUpdate(pos, state.cycle(LIT));
            } else {
                ItemStack itemStack = player.getItemInHand(hand);
                if (state.getValue(LIT) && itemStack.is(ItemTags.SHOVELS)) {
                    extinguish((Entity)null, world, pos, state);
                } else if (!state.getValue(LIT)
                        && (itemStack.is(Items.FLINT_AND_STEEL) || itemStack.is(Items.TORCH))) {
                    setLit(world, state, pos, true);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    protected static void setLit(LevelAccessor world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlock(pos, state.setValue(LIT, lit), 2 | 3);
        if(lit){
            world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.5F, 1.0F);
        }
    }

    public static void extinguish(@Nullable Entity entity, LevelAccessor world, BlockPos pos, BlockState state) {
        setLit(world, state, pos, false);
        if (world.isClientSide()) {
            for(int i = 0; i < 20; ++i) {
                spawnSmokeParticle((Level)world, pos, true, true);
            }
        }

        world.gameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
    }

    public static void spawnSmokeParticle(Level world, BlockPos pos, boolean isSignal, boolean lotsOfSmoke) {
        RandomSource random = world.getRandom();
        SimpleParticleType simpleParticleType = isSignal ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        world.addAlwaysVisibleParticle(simpleParticleType, true, (double)pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
        if (lotsOfSmoke) {
            world.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.005, 0.0);
        }
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LIT});
    }

    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (state.getValue(LIT) && entity instanceof LivingEntity) {
            if (!world.isClientSide) {
                if (isEntityAbove(pos, entity)) {
                    entity.hurt(world.damageSources().campfire(), 1.0F);
                }
            }
        }

        super.entityInside(state, world, pos, entity);
    }

    protected void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        BlockPos blockPos = hit.getBlockPos();
        if (!world.isClientSide && projectile.isOnFire()) {
            ServerLevel serverWorld = (ServerLevel)world;
            if (projectile.mayInteract(serverWorld, blockPos) && !(Boolean)state.getValue(LIT)){
                world.setBlock(blockPos, (BlockState)state.setValue(BlockStateProperties.LIT, true), 11);
            }
        }
    }

    public static boolean isLitFireBlock(BlockState state) {
        return state.hasProperty(LIT) && (Boolean)state.getValue(LIT) && state.getBlock() instanceof AbstractToggleableFireBlock;
    }

    private static boolean isEntityAbove(BlockPos pos, Entity entity) {
        return entity.onGround() && entity.position().y > (double)((float)pos.getY() + 0.51F);
    }
}
