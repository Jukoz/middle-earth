package net.jukoz.me.block.special;

import com.google.common.annotations.VisibleForTesting;
import com.mojang.serialization.MapCodec;
import net.jukoz.me.block.ModBlocks;
import net.jukoz.me.block.StoneBlockSets;
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

public class PointedDolomiteBlock extends Block implements LandingBlock, Waterloggable {

    public static final MapCodec<PointedDolomiteBlock> CODEC = createCodec(PointedDolomiteBlock::new);
    public static final DirectionProperty VERTICAL_DIRECTION;
    public static final EnumProperty<Thickness> THICKNESS;
    public static final BooleanProperty WATERLOGGED;
    private static final int field_31205 = 11;
    private static final int field_31207 = 2;
    private static final float field_31208 = 0.02F;
    private static final float field_31209 = 0.12F;
    private static final int field_31210 = 11;
    private static final float WATER_DRIP_CHANCE = 0.17578125F;
    private static final float LAVA_DRIP_CHANCE = 0.05859375F;
    private static final double field_31213 = 0.6;
    private static final float field_31214 = 1.0F;
    private static final int field_31215 = 40;
    private static final int field_31200 = 6;
    private static final float field_31201 = 2.0F;
    private static final int field_31202 = 2;
    private static final float field_33566 = 5.0F;
    private static final float field_33567 = 0.011377778F;
    private static final int MAX_STALACTITE_GROWTH = 7;
    private static final int STALACTITE_FLOOR_SEARCH_RANGE = 10;
    private static final float field_31203 = 0.6875F;
    private static final VoxelShape TIP_MERGE_SHAPE;
    private static final VoxelShape UP_TIP_SHAPE;
    private static final VoxelShape DOWN_TIP_SHAPE;
    private static final VoxelShape BASE_SHAPE;
    private static final VoxelShape FRUSTUM_SHAPE;
    private static final VoxelShape MIDDLE_SHAPE;
    private static final float field_31204 = 0.125F;
    private static final VoxelShape DRIP_COLLISION_SHAPE;

    public MapCodec<PointedDolomiteBlock> getCodec() {
        return CODEC;
    }

    public PointedDolomiteBlock(Settings settings) {
        super(settings);
        this.setDefaultState((BlockState) ((BlockState) ((BlockState) ((BlockState) this.stateManager.getDefaultState()).with(VERTICAL_DIRECTION, Direction.UP)).with(THICKNESS, Thickness.TIP)).with(WATERLOGGED, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{VERTICAL_DIRECTION, THICKNESS, WATERLOGGED});
    }

    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return canPlaceAtWithDirection(world, pos, (Direction) state.get(VERTICAL_DIRECTION));
    }

    protected BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if ((Boolean) state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (direction != Direction.UP && direction != Direction.DOWN) {
            return state;
        } else {
            Direction direction2 = (Direction) state.get(VERTICAL_DIRECTION);
            if (direction2 == Direction.DOWN && world.getBlockTickScheduler().isQueued(pos, this)) {
                return state;
            } else if (direction == direction2.getOpposite() && !this.canPlaceAt(state, world, pos)) {
                if (direction2 == Direction.DOWN) {
                    world.scheduleBlockTick(pos, this, 2);
                } else {
                    world.scheduleBlockTick(pos, this, 1);
                }

                return state;
            } else {
                boolean bl = state.get(THICKNESS) == Thickness.TIP_MERGE;
                Thickness thickness = getThickness(world, pos, direction2, bl);
                return (BlockState) state.with(THICKNESS, thickness);
            }
        }
    }

    protected void onProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile) {
        if (!world.isClient) {
            BlockPos blockPos = hit.getBlockPos();
            if (projectile.canModifyAt(world, blockPos) && projectile.canBreakBlocks(world) && projectile instanceof TridentEntity && projectile.getVelocity().length() > 0.6) {
                world.breakBlock(blockPos, true);
            }

        }
    }

    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (state.get(VERTICAL_DIRECTION) == Direction.UP && state.get(THICKNESS) == Thickness.TIP) {
            entity.handleFallDamage(fallDistance + 2.0F, 2.0F, world.getDamageSources().stalagmite());
        } else {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        }

    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
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

    @VisibleForTesting
    public static void dripTick(BlockState state, ServerWorld world, BlockPos pos, float dripChance) {
        if (!(dripChance > 0.17578125F) || !(dripChance > 0.05859375F)) {
            if (isHeldByPointedDripstone(state, world, pos)) {
                Optional<PointedDolomiteBlock.DrippingFluid> optional = getFluid(world, pos, state);
                if (!optional.isEmpty()) {
                    Fluid fluid = ((PointedDolomiteBlock.DrippingFluid) optional.get()).fluid;
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
                            if (((PointedDolomiteBlock.DrippingFluid) optional.get()).blockState.isOf(Blocks.MUD) && fluid == Fluids.WATER) {
                                BlockState blockState = Blocks.CLAY.getDefaultState();
                                world.setBlockState(((DrippingFluid) optional.get()).blockPos, blockState);
                                Block.pushEntitiesUpBeforeBlockChange(((DrippingFluid) optional.get()).blockState, blockState, world, ((DrippingFluid) optional.get()).blockPos);
                                world.emitGameEvent(GameEvent.BLOCK_CHANGE, ((DrippingFluid) optional.get()).blockPos, GameEvent.Emitter.of(blockState));
                                world.syncWorldEvent(1504, blockPos, 0);
                            } else {
                                BlockPos blockPos2 = getCauldronPos(world, blockPos, fluid);
                                if (blockPos2 != null) {
                                    world.syncWorldEvent(1504, blockPos, 0);
                                    int i = blockPos.getY() - blockPos2.getY();
                                    int j = 50 + i;
                                    BlockState blockState2 = world.getBlockState(blockPos2);
                                    world.scheduleBlockTick(blockPos2, blockState2.getBlock(), j);
                                }
                            }
                        }
                    }
                }
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
            return thickness == null ? null : (BlockState) ((BlockState) ((BlockState) this.getDefaultState().with(VERTICAL_DIRECTION, direction2)).with(THICKNESS, thickness)).with(WATERLOGGED, worldAccess.getFluidState(blockPos).getFluid() == Fluids.WATER);
        }
    }

    protected FluidState getFluidState(BlockState state) {
        return (Boolean) state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected VoxelShape getCullingShape(BlockState state, BlockView world, BlockPos pos) {
        return VoxelShapes.empty();
    }

    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        Thickness thickness = (Thickness) state.get(THICKNESS);
        VoxelShape voxelShape;
        if (thickness == Thickness.TIP_MERGE) {
            voxelShape = TIP_MERGE_SHAPE;
        } else if (thickness == Thickness.TIP) {
            if (state.get(VERTICAL_DIRECTION) == Direction.DOWN) {
                voxelShape = DOWN_TIP_SHAPE;
            } else {
                voxelShape = UP_TIP_SHAPE;
            }
        } else if (thickness == Thickness.FRUSTUM) {
            voxelShape = BASE_SHAPE;
        } else if (thickness == Thickness.MIDDLE) {
            voxelShape = FRUSTUM_SHAPE;
        } else {
            voxelShape = MIDDLE_SHAPE;
        }

        Vec3d vec3d = state.getModelOffset(world, pos);
        return voxelShape.offset(vec3d.x, 0.0, vec3d.z);
    }

    protected boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    protected float getMaxHorizontalModelOffset() {
        return 0.125F;
    }

    public void onDestroyedOnLanding(World world, BlockPos pos, FallingBlockEntity fallingBlockEntity) {
        if (!fallingBlockEntity.isSilent()) {
            world.syncWorldEvent(1045, pos, 0);
        }

    }

    public DamageSource getDamageSource(Entity attacker) {
        return attacker.getDamageSources().fallingStalactite(attacker);
    }

    private static void spawnFallingBlock(BlockState state, ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (BlockState blockState = state; isPointingDown(blockState); blockState = world.getBlockState(mutable)) {
            FallingBlockEntity fallingBlockEntity = FallingBlockEntity.spawnFromBlock(world, mutable, blockState);
            if (isTip(blockState, true)) {
                int i = Math.max(1 + pos.getY() - mutable.getY(), 6);
                float f = 1.0F * (float) i;
                fallingBlockEntity.setHurtEntities(f, 40);
                break;
            }

            mutable.move(Direction.DOWN);
        }

    }

    @VisibleForTesting
    public static void tryGrow(BlockState state, ServerWorld world, BlockPos pos, Random random) {
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

    private static void tryGrowStalagmite(ServerWorld world, BlockPos pos) {
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 0; i < 10; ++i) {
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

    private static void tryGrow(ServerWorld world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (isTip(blockState, direction.getOpposite())) {
            growMerged(blockState, world, blockPos);
        } else if (blockState.isAir() || blockState.isOf(Blocks.WATER)) {
            place(world, blockPos, direction, Thickness.TIP);
        }

    }

    private static void place(WorldAccess world, BlockPos pos, Direction direction, Thickness thickness) {
        BlockState blockState = (BlockState) ((BlockState) ((BlockState) ModBlocks.POINTED_DOLOMITE.getDefaultState().with(VERTICAL_DIRECTION, direction)).with(THICKNESS, thickness)).with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER);
        world.setBlockState(pos, blockState, 3);
    }

    private static void growMerged(BlockState state, WorldAccess world, BlockPos pos) {
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

    public static void createParticle(World world, BlockPos pos, BlockState state) {
        getFluid(world, pos, state).ifPresent((fluid) -> {
            createParticle(world, pos, state, fluid.fluid);
        });
    }

    private static void createParticle(World world, BlockPos pos, BlockState state, Fluid fluid) {
        Vec3d vec3d = state.getModelOffset(world, pos);
        double d = 0.0625;
        double e = (double) pos.getX() + 0.5 + vec3d.x;
        double f = (double) ((float) (pos.getY() + 1) - 0.6875F) - 0.0625;
        double g = (double) pos.getZ() + 0.5 + vec3d.z;
        Fluid fluid2 = getDripFluid(world, fluid);
        ParticleEffect particleEffect = fluid2.isIn(FluidTags.LAVA) ? ParticleTypes.DRIPPING_DRIPSTONE_LAVA : ParticleTypes.DRIPPING_DRIPSTONE_WATER;
        world.addParticle(particleEffect, e, f, g, 0.0, 0.0, 0.0);
    }

    @Nullable
    private static BlockPos getTipPos(BlockState state, WorldAccess world, BlockPos pos, int range, boolean allowMerged) {
        if (isTip(state, allowMerged)) {
            return pos;
        } else {
            Direction direction = (Direction) state.get(VERTICAL_DIRECTION);
            BiPredicate<BlockPos, BlockState> biPredicate = (posx, statex) -> {
                return statex.isOf(ModBlocks.POINTED_DOLOMITE) && statex.get(VERTICAL_DIRECTION) == direction;
            };
            return (BlockPos) searchInDirection(world, pos, direction.getDirection(), biPredicate, (statex) -> {
                return isTip(statex, allowMerged);
            }, range).orElse((BlockPos) null);
        }
    }

    @Nullable
    private static Direction getDirectionToPlaceAt(WorldView world, BlockPos pos, Direction direction) {
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

    private static Thickness getThickness(WorldView world, BlockPos pos, Direction direction, boolean tryMerge) {
        Direction direction2 = direction.getOpposite();
        BlockState blockState = world.getBlockState(pos.offset(direction));
        if (isPointedDripstoneFacingDirection(blockState, direction2)) {
            return !tryMerge && blockState.get(THICKNESS) != Thickness.TIP_MERGE ? Thickness.TIP : Thickness.TIP_MERGE;
        } else if (!isPointedDripstoneFacingDirection(blockState, direction)) {
            return Thickness.TIP;
        } else {
            Thickness thickness = (Thickness) blockState.get(THICKNESS);
            if (thickness != Thickness.TIP && thickness != Thickness.TIP_MERGE) {
                BlockState blockState2 = world.getBlockState(pos.offset(direction2));
                return !isPointedDripstoneFacingDirection(blockState2, direction) ? Thickness.BASE : Thickness.MIDDLE;
            } else {
                return Thickness.FRUSTUM;
            }
        }
    }

    public static boolean canDrip(BlockState state) {
        return isPointingDown(state) && state.get(THICKNESS) == Thickness.TIP && !(Boolean) state.get(WATERLOGGED);
    }

    private static boolean canGrow(BlockState state, ServerWorld world, BlockPos pos) {
        Direction direction = (Direction) state.get(VERTICAL_DIRECTION);
        BlockPos blockPos = pos.offset(direction);
        BlockState blockState = world.getBlockState(blockPos);
        if (!blockState.getFluidState().isEmpty()) {
            return false;
        } else {
            return blockState.isAir() ? true : isTip(blockState, direction.getOpposite());
        }
    }

    private static Optional<BlockPos> getSupportingPos(World world, BlockPos pos, BlockState state, int range) {
        Direction direction = (Direction) state.get(VERTICAL_DIRECTION);
        BiPredicate<BlockPos, BlockState> biPredicate = (posx, statex) -> {
            return statex.isOf(ModBlocks.POINTED_DOLOMITE) && statex.get(VERTICAL_DIRECTION) == direction;
        };
        return searchInDirection(world, pos, direction.getOpposite().getDirection(), biPredicate, (statex) -> {
            return !statex.isOf(ModBlocks.POINTED_DOLOMITE);
        }, range);
    }

    private static boolean canPlaceAtWithDirection(WorldView world, BlockPos pos, Direction direction) {
        BlockPos blockPos = pos.offset(direction.getOpposite());
        BlockState blockState = world.getBlockState(blockPos);
        return blockState.isSideSolidFullSquare(world, blockPos, direction) || isPointedDripstoneFacingDirection(blockState, direction);
    }

    private static boolean isTip(BlockState state, boolean allowMerged) {
        if (!state.isOf(ModBlocks.POINTED_DOLOMITE)) {
            return false;
        } else {
            Thickness thickness = (Thickness) state.get(THICKNESS);
            return thickness == Thickness.TIP || allowMerged && thickness == Thickness.TIP_MERGE;
        }
    }

    private static boolean isTip(BlockState state, Direction direction) {
        return isTip(state, false) && state.get(VERTICAL_DIRECTION) == direction;
    }

    private static boolean isPointingDown(BlockState state) {
        return isPointedDripstoneFacingDirection(state, Direction.DOWN);
    }

    private static boolean isPointingUp(BlockState state) {
        return isPointedDripstoneFacingDirection(state, Direction.UP);
    }

    private static boolean isHeldByPointedDripstone(BlockState state, WorldView world, BlockPos pos) {
        return isPointingDown(state) && !world.getBlockState(pos.up()).isOf(ModBlocks.POINTED_DOLOMITE);
    }

    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return false;
    }

    private static boolean isPointedDripstoneFacingDirection(BlockState state, Direction direction) {
        return state.isOf(ModBlocks.POINTED_DOLOMITE) && state.get(VERTICAL_DIRECTION) == direction;
    }

    @Nullable
    private static BlockPos getCauldronPos(World world, BlockPos pos, Fluid fluid) {
        Predicate<BlockState> predicate = (state) -> {
            return state.getBlock() instanceof AbstractCauldronBlock && ((AbstractCauldronBlock) state.getBlock()).canBeFilledByDripstone(fluid);
        };
        BiPredicate<BlockPos, BlockState> biPredicate = (posx, state) -> {
            return canDripThrough(world, posx, state);
        };
        return (BlockPos) searchInDirection(world, pos, Direction.DOWN.getDirection(), biPredicate, predicate, 11).orElse((BlockPos) null);
    }

    @Nullable
    public static BlockPos getDripPos(World world, BlockPos pos) {
        BiPredicate<BlockPos, BlockState> biPredicate = (posx, state) -> {
            return canDripThrough(world, posx, state);
        };
        return (BlockPos) searchInDirection(world, pos, Direction.UP.getDirection(), biPredicate, PointedDolomiteBlock::canDrip, 11).orElse((BlockPos) null);
    }

    public static Fluid getDripFluid(ServerWorld world, BlockPos pos) {
        return (Fluid) getFluid(world, pos, world.getBlockState(pos)).map((fluid) -> {
            return fluid.fluid;
        }).filter(PointedDolomiteBlock::isFluidLiquid).orElse(Fluids.EMPTY);
    }

    private static Optional<PointedDolomiteBlock.DrippingFluid> getFluid(World world, BlockPos pos, BlockState state) {
        return !isPointingDown(state) ? Optional.empty() : getSupportingPos(world, pos, state, 11).map((posx) -> {
            BlockPos blockPos = posx.up();
            BlockState blockState = world.getBlockState(blockPos);
            Object fluid;
            if (blockState.isOf(Blocks.MUD) && !world.getDimension().ultrawarm()) {
                fluid = Fluids.WATER;
            } else {
                fluid = world.getFluidState(blockPos).getFluid();
            }

            return new PointedDolomiteBlock.DrippingFluid(blockPos, (Fluid) fluid, blockState);
        });
    }

    private static boolean isFluidLiquid(Fluid fluid) {
        return fluid == Fluids.LAVA || fluid == Fluids.WATER;
    }

    private static boolean canGrow(BlockState dripstoneBlockState, BlockState waterState) {
        return dripstoneBlockState.isOf(StoneBlockSets.DOLOMITE.base()) && waterState.isOf(Blocks.WATER) && waterState.getFluidState().isStill();
    }

    private static Fluid getDripFluid(World world, Fluid fluid) {
        if (fluid.matchesType(Fluids.EMPTY)) {
            return world.getDimension().ultrawarm() ? Fluids.LAVA : Fluids.WATER;
        } else {
            return fluid;
        }
    }

    private static Optional<BlockPos> searchInDirection(WorldAccess world, BlockPos pos, Direction.AxisDirection direction, BiPredicate<BlockPos, BlockState> continuePredicate, Predicate<BlockState> stopPredicate, int range) {
        Direction direction2 = Direction.get(direction, Direction.Axis.Y);
        BlockPos.Mutable mutable = pos.mutableCopy();

        for (int i = 1; i < range; ++i) {
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

    private static boolean canDripThrough(BlockView world, BlockPos pos, BlockState state) {
        if (state.isAir()) {
            return true;
        } else if (state.isOpaqueFullCube(world, pos)) {
            return false;
        } else if (!state.getFluidState().isEmpty()) {
            return false;
        } else {
            VoxelShape voxelShape = state.getCollisionShape(world, pos);
            return !VoxelShapes.matchesAnywhere(DRIP_COLLISION_SHAPE, voxelShape, BooleanBiFunction.AND);
        }
    }

    static {
        VERTICAL_DIRECTION = Properties.VERTICAL_DIRECTION;
        THICKNESS = Properties.THICKNESS;
        WATERLOGGED = Properties.WATERLOGGED;
        TIP_MERGE_SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 16.0, 11.0);
        UP_TIP_SHAPE = Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 11.0, 11.0);
        DOWN_TIP_SHAPE = Block.createCuboidShape(5.0, 5.0, 5.0, 11.0, 16.0, 11.0);
        BASE_SHAPE = Block.createCuboidShape(4.0, 0.0, 4.0, 12.0, 16.0, 12.0);
        FRUSTUM_SHAPE = Block.createCuboidShape(3.0, 0.0, 3.0, 13.0, 16.0, 13.0);
        MIDDLE_SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 16.0, 14.0);
        DRIP_COLLISION_SHAPE = Block.createCuboidShape(6.0, 0.0, 6.0, 10.0, 16.0, 10.0);
    }

    static record DrippingFluid(BlockPos blockPos, Fluid fluid, BlockState blockState) {

        DrippingFluid(BlockPos blockPos, Fluid fluid, BlockState blockState) {
            this.blockPos = blockPos;
            this.fluid = fluid;
            this.blockState = blockState;
        }

        public BlockPos pos() {
            return this.blockPos;
        }

        public Fluid fluid() {
            return this.fluid;
        }

        public BlockState sourceState() {
            return this.blockState;
        }
    }
}
