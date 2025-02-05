package net.sevenstars.middleearth.block.special;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class CandleHeapBlock extends Block {
    public static final BooleanProperty LIT = Properties.LIT;

    private final ImmutableList<Vec3d> particles = ImmutableList.of(
            new Vec3d(0.875, 0.34375, 0.875),
            new Vec3d(0.3125, 0.25, 0.875),
            new Vec3d(0.125, 0.34375, 0.875),
            new Vec3d(0.8125, 0.25, 0.5625),
            new Vec3d(0.625, 0.53125, 0.625),
            new Vec3d(0.375, 0.46875, 0.6875),
            new Vec3d(0.1875, 0.28125, 0.5625),
            new Vec3d(0.5, 0.46875, 0.375),
            new Vec3d(0.1875, 0.25, 0.3125),
            new Vec3d(0.8125, 0.34375, 0.125),
            new Vec3d(0.5625, 0.25, 0.1875),
            new Vec3d(0.3125, 0.34375, 0.125)
    );

    public CandleHeapBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(LIT, false));
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CandleHeapBlock.createCodec(CandleHeapBlock::new);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            if(player.isInCreativeMode()){
                world.setBlockState(pos, state.cycle(LIT));
            } else {
                ItemStack itemStack = player.getStackInHand(hand);
                if (state.get(LIT) && player.getMainHandStack().isEmpty()) {
                    extinguish((Entity)null, world, pos, state);
                } else if (!state.get(LIT) && itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.TORCH)) {
                    setLit(world, state, pos, true);
                }
            }
        }
        return ActionResult.SUCCESS;
    }

    protected static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlockState(pos, state.with(LIT, lit), 2 | 3);
        if(lit){
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.5F, 1.0F);
        }
    }

    public static void extinguish(@Nullable Entity entity, WorldAccess world, BlockPos pos, BlockState state) {
        if (world.isClient()) {
            for(int i = 0; i < 20; ++i) {
                spawnSmokeParticle((World)world, pos, true, true);
            }
        }

        world.emitGameEvent(entity, GameEvent.BLOCK_CHANGE, pos);
    }

    public static void spawnSmokeParticle(World world, BlockPos pos, boolean isSignal, boolean lotsOfSmoke) {
        Random random = world.getRandom();
        SimpleParticleType simpleParticleType = isSignal ? ParticleTypes.CAMPFIRE_SIGNAL_SMOKE : ParticleTypes.CAMPFIRE_COSY_SMOKE;
        world.addImportantParticle(simpleParticleType, true, (double)pos.getX() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + random.nextDouble() + random.nextDouble(), (double)pos.getZ() + 0.5 + random.nextDouble() / 3.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.07, 0.0);
        if (lotsOfSmoke) {
            world.addParticle(ParticleTypes.SMOKE, (double)pos.getX() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), (double)pos.getY() + 0.4, (double)pos.getZ() + 0.5 + random.nextDouble() / 4.0 * (double)(random.nextBoolean() ? 1 : -1), 0.0, 0.005, 0.0);
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT);
    }

    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0);
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if ((Boolean)state.get(LIT)) {
            this.particles.forEach((offset) -> {
                spawnCandleParticles(world, offset.add((double)pos.getX(), (double)pos.getY(), (double)pos.getZ()), random);
            });
        }
    }

    private static void spawnCandleParticles(World world, Vec3d vec3d, Random random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                world.playSound(vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }

        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
    }
}
