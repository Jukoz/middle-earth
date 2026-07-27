package net.sevenstars.middleearth.block.special.pointedBlocks;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.MapCodec;
import net.sevenstars.middleearth.block.registration.ModBlocks;
import net.sevenstars.middleearth.block.registration.StoneBlockSets;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class PointedIzherabanBlock extends Block implements Fallable, SimpleWaterloggedBlock {
    public static final MapCodec<PointedIzherabanBlock> CODEC = simpleCodec(PointedIzherabanBlock::new);
    public static final EnumProperty<Direction> VERTICAL_DIRECTION;
    public static final EnumProperty<DripstoneThickness> THICKNESS;
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

    public MapCodec<PointedIzherabanBlock> codec() {
        return CODEC;
    }

    public PointedIzherabanBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.registerDefaultState((BlockState)((BlockState)((BlockState)((BlockState)this.stateDefinition.any()).setValue(VERTICAL_DIRECTION, Direction.UP)).setValue(THICKNESS, DripstoneThickness.TIP)).setValue(WATERLOGGED, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{VERTICAL_DIRECTION, THICKNESS, WATERLOGGED});
    }

    protected boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        return canPlaceAtWithDirection(world, pos, (Direction)state.getValue(VERTICAL_DIRECTION));
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean)state.getValue(WATERLOGGED)) {
            world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        } else {
            Direction direction2 = (Direction)state.getValue(VERTICAL_DIRECTION);
            if (direction2 == Direction.DOWN && world.getBlockTicks().hasScheduledTick(pos, this)) {
                return state;
            } else if (direction == direction2.getOpposite() && !this.canSurvive(state, world, pos)) {
                if (direction2 == Direction.DOWN) {
                    world.scheduleTick(pos, this, 2);
                } else {
                    world.scheduleTick(pos, this, 1);
                }

                return state;
            } else {
                boolean bl = state.getValue(THICKNESS) == DripstoneThickness.TIP_MERGE;
                DripstoneThickness thickness = getThickness(world, pos, direction2, bl);
                return (BlockState)state.setValue(THICKNESS, thickness);
            }
        }
    }

    protected void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!world.isClientSide) {
            BlockPos blockPos = hit.getBlockPos();
            if (world instanceof ServerLevel) {
                ServerLevel serverWorld = (ServerLevel)world;
                if (projectile.mayInteract(serverWorld, blockPos) && projectile.mayBreak(serverWorld) && projectile instanceof ThrownTrident && projectile.getDeltaMovement().length() > 0.6) {
                    world.destroyBlock(blockPos, true);
                }
            }

        }
    }

    public void fallOn(Level world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.getValue(VERTICAL_DIRECTION) == Direction.UP && state.getValue(THICKNESS) == DripstoneThickness.TIP) {
            entity.causeFallDamage(fallDistance + 2.5F, 2.0F, world.damageSources().stalagmite());
        } else {
            super.fallOn(world, state, pos, entity, fallDistance);
        }

    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (canDrip(state)) {
            float f = random.nextFloat();
            if (!(f > 0.12F)) {
                getFluid(world, pos, state).filter((fluid) -> {
                    return f < 0.02F || isFluidLiquid(fluid.fluid);
                }).ifPresent((fluid) -> {
                    createParticle(world, pos, state, fluid.fluid);
                });
            }
        }
    }

    protected void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (isPointingUp(state) && !this.canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        } else {
            spawnFallingBlock(state, world, pos);
        }

    }

    protected void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        dripTick(state, world, pos, random.nextFloat());
        if (random.nextFloat() < 0.011377778F && isHeldByPointedDripstone(state, world, pos)) {
            tryGrow(state, world, pos, random);
        }

    }

    @VisibleForTesting
    public static void dripTick(BlockState state, ServerLevel world, BlockPos pos, float dripChance) {
        if (!(dripChance > 0.17578125F) || !(dripChance > 0.05859375F)) {
            if (isHeldByPointedDripstone(state, world, pos)) {
                Optional<PointedIzherabanBlock.DrippingFluid> optional = getFluid(world, pos, state);
                if (!optional.isEmpty()) {
                    Fluid fluid = ((PointedIzherabanBlock.DrippingFluid)optional.get()).fluid;
                    float f;
                    if (fluid == Fluids.WATER) {
                        f = 0.17578125F;
                    } else {
                        if (fluid != Fluids.LAVA) {
                            return;
                        }

                        f = 0.05859375F;
                    }

                    if (!(dripChance >= f)) {
                        BlockPos blockPos = getTipPos(state, world, pos, 11, false);
                        if (blockPos != null) {
                            if (((PointedIzherabanBlock.DrippingFluid)optional.get()).sourceState.is(Blocks.MUD) && fluid == Fluids.WATER) {
                                BlockState blockState = Blocks.CLAY.defaultBlockState();
                                world.setBlockAndUpdate(((PointedIzherabanBlock.DrippingFluid)optional.get()).pos, blockState);
                                Block.pushEntitiesUp(((PointedIzherabanBlock.DrippingFluid)optional.get()).sourceState, blockState, world, ((PointedIzherabanBlock.DrippingFluid)optional.get()).pos);
                                world.gameEvent(GameEvent.BLOCK_CHANGE, ((PointedIzherabanBlock.DrippingFluid)optional.get()).pos, GameEvent.Context.of(blockState));
                                world.levelEvent(1504, blockPos, 0);
                            } else {
                                BlockPos blockPos2 = getCauldronPos(world, blockPos, fluid);
                                if (blockPos2 != null) {
                                    world.levelEvent(1504, blockPos, 0);
                                    int i = blockPos.getY() - blockPos2.getY();
                                    int j = 50 + i;
                                    BlockState blockState2 = world.getBlockState(blockPos2);
                                    world.scheduleTick(blockPos2, blockState2.getBlock(), j);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        LevelAccessor worldAccess = ctx.getLevel();
        BlockPos blockPos = ctx.getClickedPos();
        Direction direction = ctx.getNearestLookingVerticalDirection().getOpposite();
        Direction direction2 = getDirectionToPlaceAt(worldAccess, blockPos, direction);
        if (direction2 == null) {
            return null;
        } else {
            boolean bl = !ctx.isSecondaryUseActive();
            DripstoneThickness thickness = getThickness(worldAccess, blockPos, direction2, bl);
            return thickness == null ? null : (BlockState)((BlockState)((BlockState)this.defaultBlockState().setValue(VERTICAL_DIRECTION, direction2)).setValue(THICKNESS, thickness)).setValue(WATERLOGGED, worldAccess.getFluidState(blockPos).getType() == Fluids.WATER);
        }
    }

    protected FluidState getFluidState(BlockState state) {
        return (Boolean)state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state, BlockGetter world, BlockPos pos) {
        return Shapes.empty();
    }

    protected VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        VoxelShape var10000;
        switch ((DripstoneThickness)state.getValue(THICKNESS)) {
            case TIP_MERGE -> var10000 = TIP_MERGE_SHAPE;
            case TIP -> var10000 = state.getValue(VERTICAL_DIRECTION) == Direction.DOWN ? DOWN_TIP_SHAPE : UP_TIP_SHAPE;
            case FRUSTUM -> var10000 = BASE_SHAPE;
            case MIDDLE -> var10000 = FRUSTUM_SHAPE;
            case BASE -> var10000 = MIDDLE_SHAPE;
            default -> throw new MatchException((String)null, (Throwable)null);
        }

        VoxelShape voxelShape = var10000;
        Vec3 offset = state.getOffset(world, pos);
        return voxelShape.move(offset.x, 0.0, offset.z);
    }

    protected boolean isCollisionShapeFullBlock(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }

    protected float getMaxHorizontalOffset() {
        return MAX_HORIZONTAL_MODEL_OFFSET;
    }

    public void onBrokenAfterFall(Level world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.levelEvent(1045, pos, 0);
        }

    }

    public DamageSource getFallDamageSource(Entity attacker) {
        return attacker.damageSources().fallingStalactite(attacker);
    }

    private static void spawnFallingBlock(BlockState state, ServerLevel world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();

        for(BlockState blockState = state; isPointingDown(blockState); blockState = world.getBlockState(mutable)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.fall(world, mutable, blockState);
            if (isTip(blockState, true)) {
                int i = Math.max(1 + pos.getY() - mutable.getY(), 6);
                float f = 1.0F * (float)i;
                fallingBlockEntity.setHurtsEntities(f, 40);
                break;
            }

            mutable.move(Direction.DOWN);
        }

    }

    @VisibleForTesting
    public static void tryGrow(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        BlockState blockState = world.getBlockState(pos.above(1));
        BlockState blockState2 = world.getBlockState(pos.above(2));
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

    private static void tryGrowStalagmite(ServerLevel world, BlockPos pos) {
        BlockPos.MutableBlockPos mutable = pos.mutable();

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

            if (canPlaceAtWithDirection(world, mutable, Direction.UP) && !world.isWaterAt(mutable.below())) {
                tryGrow(world, mutable.below(), Direction.UP);
                return;
            }

            if (!canDripThrough(world, mutable, blockState)) {
                return;
            }
        }

    }

    private static void tryGrow(ServerLevel world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (isTip(blockState, direction.getOpposite())) {
            growMerged(blockState, world, blockPos);
        } else if (blockState.isAir() || blockState.is(Blocks.WATER)) {
            place(world, blockPos, direction, DripstoneThickness.TIP);
        }

    }

    private static void place(LevelAccessor world, BlockPos pos, Direction direction, DripstoneThickness thickness) {
        BlockState blockState = (BlockState)((BlockState)((BlockState)ModBlocks.POINTED_IZHERABAN.defaultBlockState().setValue(VERTICAL_DIRECTION, direction)).setValue(THICKNESS, thickness)).setValue(WATERLOGGED, world.getFluidState(pos).getType() == Fluids.WATER);
        world.setBlock(pos, blockState, 3);
    }

    private static void growMerged(BlockState state, LevelAccessor world, BlockPos pos) {
        BlockPos blockPos2;
        BlockPos blockPos;
        if (state.getValue(VERTICAL_DIRECTION) == Direction.UP) {
            blockPos = pos;
            blockPos2 = pos.above();
        } else {
            blockPos2 = pos;
            blockPos = pos.below();
        }

        place(world, blockPos2, Direction.DOWN, DripstoneThickness.TIP_MERGE);
        place(world, blockPos, Direction.UP, DripstoneThickness.TIP_MERGE);
    }

    public static void createParticle(Level world, BlockPos pos, BlockState state) {
        getFluid(world, pos, state).ifPresent((fluid) -> {
            createParticle(world, pos, state, fluid.fluid);
        });
    }

    private static void createParticle(Level world, BlockPos pos, BlockState state, Fluid fluid) {
        Vec3 vec3d = state.getOffset(world, pos);
        double d = 0.0625;
        double e = (double)pos.getX() + 0.5 + vec3d.x;
        double f = (double)pos.getY() + DOWN_TIP_Y - 0.0625;
        double g = (double)pos.getZ() + 0.5 + vec3d.z;
        Fluid fluid2 = getDripFluid(world, fluid);
        ParticleOptions particleEffect = fluid2.is(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        world.addParticle(particleEffect, e, f, g, 0.0, 0.0, 0.0);
    }

    @Nullable
    private static BlockPos getTipPos(BlockState state, LevelAccessor world, BlockPos pos, int range, boolean allowMerged) {
        if (isTip(state, allowMerged)) {
            return pos;
        } else {
            Direction direction = (Direction)state.getValue(VERTICAL_DIRECTION);
            BiPredicate<BlockPos, BlockState> biPredicate = (posx, statex) -> {
                return statex.is(ModBlocks.POINTED_IZHERABAN) && statex.getValue(VERTICAL_DIRECTION) == direction;
            };
            return (BlockPos)searchInDirection(world, pos, direction.getAxisDirection(), biPredicate, (statex) -> {
                return isTip(statex, allowMerged);
            }, range).orElse(null);
        }
    }

    @Nullable
    private static Direction getDirectionToPlaceAt(LevelReader world, BlockPos pos, Direction direction) {
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

    private static DripstoneThickness getThickness(LevelReader world, BlockPos pos, Direction direction, boolean tryMerge) {
        Direction direction2 = direction.getOpposite();
        BlockState blockState = world.getBlockState(pos.relative(direction));
        if (isPointedDripstoneFacingDirection(blockState, direction2)) {
            return !tryMerge && blockState.getValue(THICKNESS) != DripstoneThickness.TIP_MERGE ? DripstoneThickness.TIP : DripstoneThickness.TIP_MERGE;
        } else if (!isPointedDripstoneFacingDirection(blockState, direction)) {
            return DripstoneThickness.TIP;
        } else {
            DripstoneThickness thickness = (DripstoneThickness)blockState.getValue(THICKNESS);
            if (thickness != DripstoneThickness.TIP && thickness != DripstoneThickness.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.relative(direction2));
                return !isPointedDripstoneFacingDirection(blockState2, direction) ? DripstoneThickness.BASE : DripstoneThickness.MIDDLE;
            } else {
                return DripstoneThickness.FRUSTUM;
            }
        }
    }

    public static boolean canDrip(BlockState state) {
        return isPointingDown(state) && state.getValue(THICKNESS) == DripstoneThickness.TIP && !(Boolean)state.getValue(WATERLOGGED);
    }

    private static boolean canGrow(BlockState state, ServerLevel world, BlockPos pos) {
        Direction direction = (Direction)state.getValue(VERTICAL_DIRECTION);
        BlockPos blockPos = pos.relative(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockState.isAir() ? true : isTip(blockState, direction.getOpposite());
        }
    }

    private static Optional<BlockPos> getSupportingPos(Level world, BlockPos pos, BlockState state, int range) {
        Direction direction = (Direction)state.getValue(VERTICAL_DIRECTION);
        BiPredicate<BlockPos, BlockState> biPredicate = (posx, statex) -> {
            return statex.is(ModBlocks.POINTED_IZHERABAN) && statex.getValue(VERTICAL_DIRECTION) == direction;
        };
        return searchInDirection(world, pos, direction.getOpposite().getAxisDirection(), biPredicate, (statex) -> {
            return !statex.is(ModBlocks.POINTED_IZHERABAN);
        }, range);
    }

    private static boolean canPlaceAtWithDirection(LevelReader world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.relative(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isFaceSturdy(world, blockPos, direction) || isPointedDripstoneFacingDirection(blockState, direction);
    }

    private static boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.is(ModBlocks.POINTED_IZHERABAN)) {
            return false;
        } else {
            DripstoneThickness thickness = (DripstoneThickness)state.getValue(THICKNESS);
            return thickness == DripstoneThickness.TIP || allowMerged && thickness == DripstoneThickness.TIP_MERGE;
        }
    }

    private static boolean isTip(BlockState state, Direction direction) {
        return isTip(state, false) && state.getValue(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isPointingDown(BlockState state) {
        return isPointedDripstoneFacingDirection(state, Direction.DOWN);
    }

    private static boolean isPointingUp(BlockState state) {
        return isPointedDripstoneFacingDirection(state, Direction.UP);
    }

    private static boolean isHeldByPointedDripstone(BlockState state, LevelReader world, BlockPos pos) {
        return isPointingDown(state) && !world.getBlockState(pos.above()).is(ModBlocks.POINTED_IZHERABAN);
    }

    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    private static boolean isPointedDripstoneFacingDirection(BlockState state, Direction direction) {
        return state.is(ModBlocks.POINTED_IZHERABAN) && state.getValue(VERTICAL_DIRECTION) == direction;
    }

    @Nullable
    private static BlockPos getCauldronPos(Level world, BlockPos pos, Fluid fluid) {
        Predicate<BlockState> predicate = (state) -> {
            return state.getBlock() instanceof AbstractCauldronBlock && ((AbstractCauldronBlock)state.getBlock()).canReceiveStalactiteDrip(fluid);
        };
        BiPredicate<BlockPos, BlockState> biPredicate = (posx, state) -> {
            return canDripThrough(world, posx, state);
        };
        return (BlockPos)searchInDirection(world, pos, Direction.DOWN.getAxisDirection(), biPredicate, predicate, 11).orElse(null);
    }

    @Nullable
    public static BlockPos getDripPos(Level world, BlockPos pos) {
        BiPredicate<BlockPos, BlockState> biPredicate = (posx, state) -> {
            return canDripThrough(world, posx, state);
        };
        return (BlockPos)searchInDirection(world, pos, Direction.UP.getAxisDirection(), biPredicate, PointedIzherabanBlock::canDrip, 11).orElse(null);
    }

    public static Fluid getDripFluid(ServerLevel world, BlockPos pos) {
        return (Fluid)getFluid(world, pos, world.getBlockState(pos)).map((fluid) -> {
            return fluid.fluid;
        }).filter(PointedIzherabanBlock::isFluidLiquid).orElse(Fluids.EMPTY);
    }

    private static Optional<PointedIzherabanBlock.DrippingFluid> getFluid(Level world, BlockPos pos, BlockState state) {
        return !isPointingDown(state) ? Optional.empty() : getSupportingPos(world, pos, state, 11).map((posx) -> {
            BlockPos blockPos = posx.above();
            BlockState blockState = world.getBlockState(blockPos);
            Object fluid;
            if (blockState.is(Blocks.MUD) && !world.dimensionType().ultraWarm()) {
                fluid = Fluids.WATER;
            } else {
                fluid = world.getFluidState(blockPos).getType();
            }

            return new PointedIzherabanBlock.DrippingFluid(blockPos, (Fluid)fluid, blockState);
        });
    }

    private static boolean isFluidLiquid(Fluid fluid) {
        return fluid == Fluids.LAVA || fluid == Fluids.WATER;
    }

    private static boolean canGrow(BlockState dripstoneBlockState, BlockState waterState) {
        return dripstoneBlockState.is(StoneBlockSets.IZHERABAN_SET.baseBlocks.base()) && waterState.is(Blocks.WATER) && waterState.getFluidState().isSource();
    }

    private static Fluid getDripFluid(Level world, Fluid fluid) {
        if (fluid.isSame(Fluids.EMPTY)) {
            return world.dimensionType().ultraWarm() ? Fluids.LAVA : Fluids.WATER;
        } else {
            return fluid;
        }
    }

    private static Optional<BlockPos> searchInDirection(LevelAccessor world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
        Direction direction2 = Direction.get(direction, Direction.Axis.Y);
        BlockPos.MutableBlockPos mutable = pos.mutable();

        for(int i = 1; i < range; ++i) {
            mutable.move(direction2);
            BlockState blockState = world.getBlockState(mutable);
            if (stopPredicate.test(blockState)) {
                return Optional.of(mutable.immutable());
            }

            if (world.isOutsideBuildHeight(mutable.getY()) || !continuePredicate.test(mutable, blockState)) {
                return Optional.empty();
            }
        }

        return Optional.empty();
    }

    private static boolean canDripThrough(BlockGetter world, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        } else if (state.isSolidRender(world, pos)) {
            return false;
        } else if (!state.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape voxelShape = state.getCollisionShape(world, pos);
            return !Shapes.joinIsNotEmpty(DRIP_COLLISION_SHAPE, voxelShape, BooleanOp.AND);
        }
    }

    static {
        VERTICAL_DIRECTION = BlockStateProperties.VERTICAL_DIRECTION;
        THICKNESS = BlockStateProperties.DRIPSTONE_THICKNESS;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        TIP_MERGE_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
        UP_TIP_SHAPE = Block.box(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
        DOWN_TIP_SHAPE = Block.box(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
        BASE_SHAPE = Block.box(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
        FRUSTUM_SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
        MIDDLE_SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
        DOWN_TIP_Y = DOWN_TIP_SHAPE.min(Direction.Axis.Y);
        MAX_HORIZONTAL_MODEL_OFFSET = (float)MIDDLE_SHAPE.min(Direction.Axis.X);
        DRIP_COLLISION_SHAPE = Block.box(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    }

    static record DrippingFluid(BlockPos pos, Fluid fluid, BlockState sourceState) {

        DrippingFluid(BlockPos pos, Fluid fluid, BlockState sourceState) {
            this.pos = pos;
            this.fluid = fluid;
            this.sourceState = sourceState;
        }

        public BlockPos pos() {
            return this.pos;
        }

        public Fluid fluid() {
            return this.fluid;
        }

        public BlockState sourceState() {
            return this.sourceState;
        }
    }
}
