package net.jesteur.me.block.special.toggeable_lights;

import net.minecraft.block.*;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.*;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.ToIntFunction;

public class SkullLanternBlock extends Block {
    public static final BooleanProperty LIT;
    public static final IntProperty LEVEL_15;
    public static final BooleanProperty WATERLOGGED;
    public static final BooleanProperty HANGING;
    private static final VoxelShape STANDING_SHAPE;
    private static final VoxelShape HANGING_SHAPE;
    // Custom
    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    public static int DEFAULT_LUMINANCE;

    public SkullLanternBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(LIT, false).with(HANGING, false).with(WATERLOGGED, false).with(LEVEL_15, 15));
    }
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{LEVEL_15, LIT, HANGING, WATERLOGGED});
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        Direction[] var3 = ctx.getPlacementDirections();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.getDefaultState().with(HANGING, direction == Direction.UP);
                if (blockState.canPlaceAt(ctx.getWorld(), ctx.getBlockPos())) {
                    return blockState.with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
                }
            }
        }

        return null;
    }

    public void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient && projectile.isOnFire() && !state.get(LIT)) {
            world.setBlockState(hit.getBlockPos(), state.with(LIT, true), STATE_TO_LUMINANCE.applyAsInt(state));
        }
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            if(state.get(LIT) && player.getStackInHand(hand).isIn(ItemTags.SHOVELS)){
                extinguish(null, state, world, pos);
            } else if(!state.get(LIT) && player.getStackInHand(hand).isOf(Items.FLINT_AND_STEEL) || player.getStackInHand(hand).isOf(Items.TORCH)){
                setLit(world, state, pos, true);
            }
        }
        return ActionResult.SUCCESS;
    }

    private static void setLit(WorldAccess world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlockState(pos, state.with(LIT, lit).cycle(LEVEL_15), 2 | 3);
    }

    public static void extinguish(@Nullable PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos) {
        setLit(world, state, pos, false);

        world.playSound((PlayerEntity)null, pos, SoundEvents.BLOCK_CANDLE_EXTINGUISH, SoundCategory.BLOCKS, 2.0F, 1.0F);
        world.emitGameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return state.get(HANGING) ? HANGING_SHAPE : STANDING_SHAPE;
    }

    protected Iterable<Vec3d> getParticleOffsets(BlockState state) {
        return null;
    }

    @Override
    public BlockState getAppearance(BlockState state, BlockRenderView renderView, BlockPos pos, Direction side, @Nullable BlockState sourceState, @Nullable BlockPos sourcePos) {
        return super.getAppearance(state, renderView, pos, side, sourceState, sourcePos);
    }

    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return addNbtForLevel(super.getPickStack(world, pos, state), (Integer)state.get(LEVEL_15));
    }

    public static ItemStack addNbtForLevel(ItemStack stack, int level) {
        if (level != 15) {
            NbtCompound nbtCompound = new NbtCompound();
            nbtCompound.putString(LEVEL_15.getName(), String.valueOf(level));
            stack.setSubNbt("BlockStateTag", nbtCompound);
        }

        return stack;
    }


    @Override
    public boolean isEnabled(FeatureSet enabledFeatures) {
        return super.isEnabled(enabledFeatures);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        Direction direction = attachedDirection(state).getOpposite();
        return Block.sideCoversSmallSquare(world, pos.offset(direction), direction.getOpposite());
    }
    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            extinguish(null, state, world, pos);
            //state.with(LIT, false);
            //System.out.print(state.get(LIT));
        }

        return attachedDirection(state).getOpposite() == direction && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }
    protected static Direction attachedDirection(BlockState state) {
        return state.get(HANGING) ? Direction.DOWN : Direction.UP;
    }
    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }
    @Override
    public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) { return false; }
    public static boolean emitsLight(BlockState state){
        return state.get(LIT);
    }

    static {
        DEFAULT_LUMINANCE = 15;
        STANDING_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 7.0, 11.0),
                Block.createCuboidShape(6.0, 7.0, 6.0, 10.0, 9.0, 10.0));
        HANGING_SHAPE = VoxelShapes.union(
                Block.createCuboidShape(5.0, 1.0, 5.0, 11.0, 8.0, 11.0),
                Block.createCuboidShape(6.0, 8.0, 6.0, 10.0, 10.0, 10.0));

        LIT = Properties.LIT;
        LEVEL_15 = Properties.LEVEL_15;
        STATE_TO_LUMINANCE = (state) -> { return state.get(LIT) ? state.get(LEVEL_15) : 0; };
        WATERLOGGED = Properties.WATERLOGGED;
        HANGING = Properties.HANGING;
    }
}
