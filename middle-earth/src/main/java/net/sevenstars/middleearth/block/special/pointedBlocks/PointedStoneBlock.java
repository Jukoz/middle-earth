package net.sevenstars.middleearth.block.special.pointedBlocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.Registries;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.tick.ScheduledTickView;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.entity.Entity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.*;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class PointedStoneBlock extends Block implements Falling, Waterloggable {
    public static final MapCodec<PointedStoneBlock> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Registries.BLOCK.getCodec().fieldOf("base").forGetter(block -> block.base), createSettingsCodec()).apply(instance, PointedStoneBlock::new));
    // the stone it grows from, as dripstone grows from dripstone blocks
    private final Block base;
    public static final EnumProperty<Direction> VERTICAL_DIRECTION;
    public static final EnumProperty<Thickness> THICKNESS;
    public static final BooleanProperty WATERLOGGED;
    private static final VoxelShape TIP_MERGE_SHAPE;
    private static final VoxelShape UP_TIP_SHAPE;
    private static final VoxelShape DOWN_TIP_SHAPE;
    private static final VoxelShape BASE_SHAPE;
    private static final VoxelShape FRUSTUM_SHAPE;
    private static final VoxelShape MIDDLE_SHAPE;
    private static final double DOWN_TIP_Y;
    private static final float MAX_HORIZONTAL_MODEL_OFFSET;
    private static final VoxelShape DRIP_COLLISION_SHAPE;

    public MapCodec<PointedStoneBlock> getCodec() {
        return CODEC;
    }

    public PointedStoneBlock(Block base, AbstractBlock.Settings settings) {
        super(settings);
        this.base = base;
        setDefaultState(stateManager.getDefaultState().with(VERTICAL_DIRECTION, Direction.UP).with(THICKNESS, Thickness.TIP).with(WATERLOGGED, false));
    }

    // one block of a run of `run` blocks, `fromTip` back from its point, shaped as dripstone grows
    public BlockState pointing(Direction pointing, int fromTip, int run) {
        Thickness thickness = fromTip == 0 ? Thickness.TIP : fromTip == 1 ? Thickness.FRUSTUM : fromTip == run - 1 ? Thickness.BASE : Thickness.MIDDLE;
        return getDefaultState().with(VERTICAL_DIRECTION, pointing).with(THICKNESS, thickness);
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(VERTICAL_DIRECTION, THICKNESS, WATERLOGGED);
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAtWithDirection(world, pos, state.get(VERTICAL_DIRECTION));
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, WorldView world, ScheduledTickView tickView, BlockPos pos, Direction direction, BlockPos neighborPos, BlockState neighborState, Random random) {
        if (state.get(WATERLOGGED)) {
            tickView.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        } else {
            Direction direction2 = state.get(VERTICAL_DIRECTION);
            if (direction2 == Direction.DOWN && tickView.getBlockTickScheduler().isQueued(pos, this)) {
                return state;
            } else if (direction == direction2.getOpposite() && !this.canPlaceAt(state, world, pos)) {
                tickView.scheduleBlockTick(pos, this, direction2 == Direction.DOWN ? 2 : 1);
                return state;
            } else {
                boolean bl = state.get(THICKNESS) == Thickness.TIP_MERGE;
                Thickness thickness = getThickness(world, pos, direction2, bl);
                return state.with(THICKNESS, thickness);
            }
        }
    }

    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (world instanceof ServerWorld serverWorld) {
            BlockPos blockPos = hit.getBlockPos();
            if (projectile.canModifyAt(serverWorld, blockPos) && projectile.canBreakBlocks(serverWorld) && projectile instanceof TridentEntity && projectile.getVelocity().length() > 0.6) {
                world.breakBlock(blockPos, true);
            }
        }
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance) {
        if (state.get(VERTICAL_DIRECTION) == Direction.UP && state.get(THICKNESS) == Thickness.TIP) {
            entity.handleFallDamage(fallDistance + 2.5, 2.0F, world.getDamageSources().stalagmite());
        } else {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        }
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.12F)) {
                getFluid(world, pos, state)
                        .filter(fluid -> f < 0.02F || isFluidLiquid(fluid.fluid))
                        .ifPresent(fluid -> createParticle(world, pos, state, fluid.fluid));
            }
        }
    }

    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (isPointingUp(state) && !this.canPlaceAt(state, world, pos)) {
            world.breakBlock(pos, true);
        } else {
            spawnFallingBlock(state, world, pos);
        }
    }

    protected void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        dripTick(state, world, pos, random.nextFloat());
        if (random.nextFloat() < 0.011377778F && isHeldByPointedDripstone(state, world, pos)) {
            tryGrow(state, world, pos, random);
        }
    }

    public void dripTick(BlockState state, ServerWorld world, BlockPos pos, float dripChance) {
        if (dripChance > 0.17578125F && dripChance > 0.05859375F) return;
        if (!isHeldByPointedDripstone(state, world, pos)) return;
        Optional<DrippingFluid> optional = getFluid(world, pos, state);
        if (optional.isEmpty()) return;
        DrippingFluid dripping = optional.get();
        Fluid fluid = dripping.fluid;
        float f;
        if (fluid == Fluids.WATER) {
            f = 0.17578125F;
        } else if (fluid == Fluids.LAVA) {
            f = 0.05859375F;
        } else {
            return;
        }
        if (dripChance >= f) return;
        BlockPos tipPos = getTipPos(state, world, pos, 11, false);
        if (tipPos == null) return;
        if (dripping.sourceState.isOf(Blocks.MUD) && fluid == Fluids.WATER) {
            BlockState clay = Blocks.CLAY.getDefaultState();
            world.setBlockState(dripping.pos, clay);
            Block.pushEntitiesUpBeforeBlockChange(dripping.sourceState, clay, world, dripping.pos);
            world.emitGameEvent(GameEvent.BLOCK_CHANGE, dripping.pos, GameEvent.Emitter.of(clay));
            world.syncWorldEvent(1504, tipPos, 0);
        } else {
            BlockPos cauldronPos = getCauldronPos(world, tipPos, fluid);
            if (cauldronPos != null) {
                world.syncWorldEvent(1504, tipPos, 0);
                int delay = 50 + tipPos.getY() - cauldronPos.getY();
                world.scheduleBlockTick(cauldronPos, world.getBlockState(cauldronPos).getBlock(), delay);
            }
        }
    }

    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        WorldAccess worldAccess = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        Direction direction = ctx.getVerticalPlayerLookDirection().getOpposite();
        Direction direction2 = getDirectionToPlaceAt(worldAccess, blockPos, direction);
        if (direction2 == null) {
            return null;
        } else {
            boolean bl = !ctx.shouldCancelInteraction();
            Thickness thickness = getThickness(worldAccess, blockPos, direction2, bl);
            return thickness == null ? null : getDefaultState().with(VERTICAL_DIRECTION, direction2).with(THICKNESS, thickness).with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER);
        }
    }

    protected FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected VoxelShape getCullingShape(BlockState state) {
        return VoxelShapes.empty();
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = switch (state.get(THICKNESS)) {
            case TIP_MERGE -> TIP_MERGE_SHAPE;
            case TIP -> state.get(VERTICAL_DIRECTION) == Direction.DOWN ? DOWN_TIP_SHAPE : UP_TIP_SHAPE;
            case FRUSTUM -> BASE_SHAPE;
            case MIDDLE -> FRUSTUM_SHAPE;
            case BASE -> MIDDLE_SHAPE;
        };
        return shape.offset(state.getModelOffset(pos));
    }

    protected boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    protected float getMaxHorizontalModelOffset() {
        return MAX_HORIZONTAL_MODEL_OFFSET;
    }

    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.syncWorldEvent(1045, pos, 0);
        }
    }

    public DamageSource getDamageSource(Entity attacker) {
        return attacker.getDamageSources().fallingStalactite(attacker);
    }

    private void spawnFallingBlock(BlockState state, ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        for(BlockState blockState = state; isPointingDown(blockState); blockState = world.getBlockState(mutable)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, mutable, blockState);
            if (isTip(blockState, true)) {
                int i = Math.max(1 + pos.getY() - mutable.getY(), 6);
                fallingBlockEntity.setHurtEntities((float) i, 40);
                break;
            }

            mutable.move(Direction.DOWN);
        }
    }

    public void tryGrow(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = world.getBlockState(pos.up(1));
        BlockState blockState2 = world.getBlockState(pos.up(2));
        if (canGrow(blockState, blockState2)) {
            BlockPos blockPos = getTipPos(state, world, pos, 7, false);
            if (blockPos != null) {
                BlockState blockState3 = world.getBlockState(blockPos);
                if (canDrip(blockState3) && canGrow(blockState3, world, blockPos)) {
                    if (random.nextBoolean()) {
                        tryGrow(world, blockPos, Direction.DOWN);
                    } else {
                        tryGrowStalagmite(world, blockPos);
                    }
                }
            }
        }
    }

    private void tryGrowStalagmite(ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        for(int i = 0; i < 10; ++i) {
            mutable.move(Direction.DOWN);
            BlockState blockState = world.getBlockState(mutable);
            if (!blockState.getFluidState().isEmpty()) {
                return;
            }

            if (isTip(blockState, Direction.UP) && canGrow(blockState, world, mutable)) {
                tryGrow(world, mutable, Direction.UP);
                return;
            }

            if (canPlaceAtWithDirection(world, mutable, Direction.UP) && !world.isWater(mutable.down())) {
                tryGrow(world, mutable.down(), Direction.UP);
                return;
            }

            if (!canDripThrough(world, mutable, blockState)) {
                return;
            }
        }
    }

    private void tryGrow(ServerWorld world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (isTip(blockState, direction.getOpposite())) {
            growMerged(blockState, world, blockPos);
        } else if (blockState.isAir() || blockState.isOf(Blocks.WATER)) {
            place(world, blockPos, direction, Thickness.TIP);
        }
    }

    private void place(WorldAccess world, BlockPos pos, Direction direction, Thickness thickness) {
        BlockState blockState = getDefaultState().with(VERTICAL_DIRECTION, direction).with(THICKNESS, thickness).with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
        world.setBlockState(pos, blockState, 3);
    }

    private void growMerged(BlockState state, WorldAccess world, BlockPos pos) {
        BlockPos blockPos2;
        BlockPos blockPos;
        if (state.get(VERTICAL_DIRECTION) == Direction.UP) {
            blockPos = pos;
            blockPos2 = pos.up();
        } else {
            blockPos2 = pos;
            blockPos = pos.down();
        }

        place(world, blockPos2, Direction.DOWN, Thickness.TIP_MERGE);
        place(world, blockPos, Direction.UP, Thickness.TIP_MERGE);
    }

    private void createParticle(World world, BlockPos pos, BlockState state, Fluid fluid) {
        Vec3d vec3d = state.getModelOffset(pos);
        double e = pos.getX() + 0.5 + vec3d.x;
        double f = pos.getY() + DOWN_TIP_Y - 0.0625;
        double g = pos.getZ() + 0.5 + vec3d.z;
        ParticleEffect particleEffect = getParticleEffect(world, fluid, pos);
        world.addParticleClient(particleEffect, e, f, g, 0.0, 0.0, 0.0);
    }

    private ParticleEffect getParticleEffect(World world, Fluid fluid, BlockPos pos) {
        if (fluid.matchesType(Fluids.EMPTY)) {
            return world.getEnvironmentAttributes().getAttributeValue(EnvironmentAttributes.DEFAULT_DRIPSTONE_PARTICLE_VISUAL, pos);
        } else {
            return fluid.isIn(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        }
    }

    @Nullable
    private BlockPos getTipPos(BlockState state, WorldAccess world, BlockPos pos, int range, boolean allowMerged) {
        if (isTip(state, allowMerged)) {
            return pos;
        } else {
            Direction direction = state.get(VERTICAL_DIRECTION);
            return searchInDirection(world, pos, direction.getDirection(), continuingAlong(direction), s -> isTip(s, allowMerged), range).orElse(null);
        }
    }

    @Nullable
    private Direction getDirectionToPlaceAt(WorldView world, BlockPos pos, Direction direction) {
        Direction direction2;
        if (canPlaceAtWithDirection(world, pos, direction)) {
            direction2 = direction;
        } else {
            if (!canPlaceAtWithDirection(world, pos, direction.getOpposite())) {
                return null;
            }

            direction2 = direction.getOpposite();
        }

        return direction2;
    }

    private Thickness getThickness(WorldView world, BlockPos pos, Direction direction, boolean tryMerge) {
        Direction direction2 = direction.getOpposite();
        BlockState blockState = world.getBlockState(pos.offset(direction));
        if (isPointedDripstoneFacingDirection(blockState, direction2)) {
            return !tryMerge && blockState.get(THICKNESS) != Thickness.TIP_MERGE ? Thickness.TIP : Thickness.TIP_MERGE;
        } else if (!isPointedDripstoneFacingDirection(blockState, direction)) {
            return Thickness.TIP;
        } else {
            Thickness thickness = blockState.get(THICKNESS);
            if (thickness != Thickness.TIP && thickness != Thickness.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.offset(direction2));
                return !isPointedDripstoneFacingDirection(blockState2, direction) ? Thickness.BASE : Thickness.MIDDLE;
            } else {
                return Thickness.FRUSTUM;
            }
        }
    }

    public boolean canDrip(BlockState state) {
        return isPointingDown(state) && state.get(THICKNESS) == Thickness.TIP && !state.get(WATERLOGGED);
    }

    private boolean canGrow(BlockState state, ServerWorld world, BlockPos pos) {
        Direction direction = state.get(VERTICAL_DIRECTION);
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockState.isAir() || isTip(blockState, direction.getOpposite());
        }
    }

    private BiPredicate<BlockPos, BlockState> continuingAlong(Direction direction) {
        return (p, s) -> s.isOf(this) && s.get(VERTICAL_DIRECTION) == direction;
    }

    private Optional<BlockPos> getSupportingPos(World world, BlockPos pos, BlockState state, int range) {
        Direction direction = state.get(VERTICAL_DIRECTION);
        return searchInDirection(world, pos, direction.getOpposite().getDirection(), continuingAlong(direction), s -> !s.isOf(this), range);
    }

    private boolean canPlaceAtWithDirection(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, direction) || isPointedDripstoneFacingDirection(blockState, direction);
    }

    private boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.isOf(this)) {
            return false;
        } else {
            Thickness thickness = state.get(THICKNESS);
            return thickness == Thickness.TIP || allowMerged && thickness == Thickness.TIP_MERGE;
        }
    }

    private boolean isTip(BlockState state, Direction direction) {
        return isTip(state, false) && state.get(VERTICAL_DIRECTION) == direction;
    }

    private boolean isPointingDown(BlockState state) {
        return isPointedDripstoneFacingDirection(state, Direction.DOWN);
    }

    private boolean isPointingUp(BlockState state) {
        return isPointedDripstoneFacingDirection(state, Direction.UP);
    }

    private boolean isHeldByPointedDripstone(BlockState state, WorldView world, BlockPos pos) {
        return isPointingDown(state) && !world.getBlockState(pos.up()).isOf(this);
    }

    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    private boolean isPointedDripstoneFacingDirection(BlockState state, Direction direction) {
        return state.isOf(this) && state.get(VERTICAL_DIRECTION) == direction;
    }

    @Nullable
    private BlockPos getCauldronPos(World world, BlockPos pos, Fluid fluid) {
        Predicate<BlockState> predicate = state -> state.getBlock() instanceof AbstractCauldronBlock cauldron && cauldron.canBeFilledByDripstone(fluid);
        return searchInDirection(world, pos, Direction.DOWN.getDirection(), (p, state) -> canDripThrough(world, p, state), predicate, 11).orElse(null);
    }

    @Nullable
    public BlockPos getDripPos(World world, BlockPos pos) {
        return searchInDirection(world, pos, Direction.UP.getDirection(), (p, state) -> canDripThrough(world, p, state), this::canDrip, 11).orElse(null);
    }

    public Fluid getDripFluid(ServerWorld world, BlockPos pos) {
        return getFluid(world, pos, world.getBlockState(pos)).map(DrippingFluid::fluid).filter(this::isFluidLiquid).orElse(Fluids.EMPTY);
    }

    private Optional<DrippingFluid> getFluid(World world, BlockPos pos, BlockState state) {
        return !isPointingDown(state) ? Optional.empty() : getSupportingPos(world, pos, state, 11).map(supportPos -> {
            BlockPos blockPos = supportPos.up();
            BlockState blockState = world.getBlockState(blockPos);
            Fluid fluid;
            if (blockState.isOf(Blocks.MUD) && !world.getEnvironmentAttributes().getAttributeValue(EnvironmentAttributes.WATER_EVAPORATES_GAMEPLAY, blockPos)) {
                fluid = Fluids.WATER;
            } else {
                fluid = world.getFluidState(blockPos).getFluid();
            }
            return new DrippingFluid(blockPos, fluid, blockState);
        });
    }

    private boolean isFluidLiquid(Fluid fluid) {
        return fluid == Fluids.LAVA || fluid == Fluids.WATER;
    }

    private boolean canGrow(BlockState dripstoneBlockState, BlockState waterState) {
        return dripstoneBlockState.isOf(base) && waterState.isOf(Blocks.WATER) && waterState.getFluidState().isStill();
    }

    private Optional<BlockPos> searchInDirection(WorldAccess world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
        Direction direction2 = Direction.get(direction, Direction.Axis.Y);
        BlockPos.Mutable mutable = pos.mutableCopy();

        for(int i = 1; i < range; ++i) {
            mutable.move(direction2);
            BlockState blockState = world.getBlockState(mutable);
            if (stopPredicate.test(blockState)) {
                return Optional.of(mutable.toImmutable());
            }

            if (world.isOutOfHeightLimit(mutable.getY()) || !continuePredicate.test(mutable, blockState)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    private boolean canDripThrough(BlockView world, BlockPos pos, BlockState state) {
        if (state.isAir()) return true;
        if (state.isOpaqueFullCube() || !state.getFluidState().isEmpty()) return false;
        return !VoxelShapes.matchesAnywhere(DRIP_COLLISION_SHAPE, state.getCollisionShape(world, pos), BooleanBiFunction.AND);
    }

    static {
        VERTICAL_DIRECTION = Properties.VERTICAL_DIRECTION;
        THICKNESS = Properties.THICKNESS;
        WATERLOGGED = Properties.WATERLOGGED;
        TIP_MERGE_SHAPE = Block.createColumnShape(6.0, 0.0, 16.0);
        UP_TIP_SHAPE = Block.createColumnShape(6.0, 0.0, 11.0);
        DOWN_TIP_SHAPE = Block.createColumnShape(6.0, 5.0, 16.0);
        BASE_SHAPE = Block.createColumnShape(8.0, 0.0, 16.0);
        FRUSTUM_SHAPE = Block.createColumnShape(10.0, 0.0, 16.0);
        MIDDLE_SHAPE = Block.createColumnShape(12.0, 0.0, 16.0);
        DOWN_TIP_Y = DOWN_TIP_SHAPE.getMin(Direction.Axis.Y);
        MAX_HORIZONTAL_MODEL_OFFSET = (float)MIDDLE_SHAPE.getMin(Direction.Axis.X);
        DRIP_COLLISION_SHAPE = Block.createColumnShape(4.0, 0.0, 16.0);
    }

    record DrippingFluid(BlockPos pos, Fluid fluid, BlockState sourceState) {
    }
}
