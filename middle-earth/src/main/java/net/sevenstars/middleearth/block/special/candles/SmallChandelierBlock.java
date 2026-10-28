package net.sevenstars.middleearth.block.special.candles;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.ScheduledTickView;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class SmallChandelierBlock extends Block {
    private static final int MAX_VARIANT = 2;
    public static final IntProperty VARIANT = IntProperty.of("variant", 1, MAX_VARIANT);
    public static final BooleanProperty LIT;

    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    private static final VoxelShape SHAPE;

    public SmallChandelierBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIT).add(VARIANT);
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = this.getDefaultState().with(VARIANT, 1);
        if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
            return blockState;
        }
        return null;
    }

    @Override
    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        return !state.canPlaceAt(world, pos) ?
                Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, world, tickView, pos, direction, neighborPos, neighborState, random);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit) {
        Hand hand = player.getActiveHand();
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            ItemStack itemStack = player.getStackInHand(hand);
            if(player.isInCreativeMode() && itemStack == ItemStack.EMPTY){
                world.setBlockState(pos, state.cycle(VARIANT));
                return ActionResult.SUCCESS;
            }

            if (state.get(LIT) && itemStack.isIn(ItemTags.SHOVELS)) {
                extinguish(null, state, world, pos);
            } else if (!state.get(LIT) && itemStack.isOf(Items.FLINT_AND_STEEL) || itemStack.isOf(Items.TORCH)) {
                setLit(world, state, pos, true);
            }
        }
        return ActionResult.SUCCESS;
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(LIT)) {
            if(state.get(VARIANT) == 1) {
                spawnCandleParticles(world, pos.toCenterPos().add(0.0625, 12.5 * 0.0625, 0.0625), random);
                spawnCandleParticles(world, pos.toCenterPos().add(0.0625, 12.5 * 0.0625, 0.9375), random);
                spawnCandleParticles(world, pos.toCenterPos().add(0.9375, 12.5 * 0.0625, 0.0625), random);
                spawnCandleParticles(world, pos.toCenterPos().add(0.9375, 12.5 * 0.0625, 0.9375), random);
            } else if(state.get(VARIANT) == 2) {
                spawnCandleParticles(world, pos.toCenterPos().add(0.125, 12.5 * 0.0625, 0.5), random);
                spawnCandleParticles(world, pos.toCenterPos().add(0.5, 12.5 * 0.0625, 0.875), random);
                spawnCandleParticles(world, pos.toCenterPos().add(0.5, 12.5 * 0.0625, 0.125), random);
                spawnCandleParticles(world, pos.toCenterPos().add(0.875, 12.5 * 0.0625, 0.5), random);
            }
        }
    }

    protected static void spawnCandleParticles(World world, Vec3d vec3d, Random random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            world.addParticleClient(ParticleTypes.SMOKE, vec3d.x - 0.5f, vec3d.y - 0.5f, vec3d.z - 0.5f, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                world.playSoundClient(vec3d.x, vec3d.y, vec3d.z, SoundEvents.BLOCK_CANDLE_AMBIENT, SoundCategory.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }
        world.addParticleClient(ParticleTypes.SMALL_FLAME, vec3d.x - 0.5f, vec3d.y - 0.5f, vec3d.z - 0.5f, 0.0, 0.0, 0.0);
    }

    protected static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlockState(pos, state.with(LIT, lit), 2 | 3);
        if(lit){
            world.playSound(null, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.BLOCKS, 1.5F, 1.0F);
        }
    }

    protected static void extinguish(@Nullable PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos) {
        setLit(world, state, pos, false);

        world.playSound(null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 1.5F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return Block.sideCoversSmallSquare(world, pos.up(), Direction.DOWN);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.EMPTY.getDefaultState();
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire() && !state.get(LIT)) {
            world.setBlockState(hit.getBlockPos(), state.with(LIT, true), STATE_TO_LUMINANCE.applyAsInt(state));
        }
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    static {
        LIT = Properties.LIT;
        STATE_TO_LUMINANCE = (state) -> state.get(LIT) ? 15 : 0;
        SHAPE = Block.createCuboidShape(0, 2, 0, 16, 16.0, 16);
    }
}
