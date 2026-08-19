package net.sevenstars.middleearth.block.special.candles;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.Util;
import net.minecraft.world.level.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.ToIntFunction;

public class SkullCandleBlock extends Block {
    public static final EnumProperty<Direction> HORIZONTAL_FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty CANDLES = IntegerProperty.create("candles", 1, 3);
    public static final BooleanProperty LIT;
    private static final Int2ObjectMap CANDLES_TO_PARTICLE_OFFSETS;

    public static final ToIntFunction<BlockState> STATE_TO_LUMINANCE;
    private static final VoxelShape SHAPE;

    public SkullCandleBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(this.defaultBlockState().setValue(LIT, false).setValue(CANDLES, 1)
                .setValue(HORIZONTAL_FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIT, CANDLES, HORIZONTAL_FACING);
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        Direction[] var3 = ctx.getNearestLookingDirections();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Direction direction = var3[var5];
            if (direction.getAxis() == Direction.Axis.Y) {
                BlockState blockState = this.defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalDirection().getOpposite());
                if (blockState.canSurvive(ctx.getLevel(), ctx.getClickedPos())) {
                    return blockState;
                }
            }
        }
        return null;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return !state.canSurvive(world, pos) ?
                Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
    }

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
                    extinguish(null, state, world, pos);
                } else if (!state.getValue(LIT) && itemStack.is(Items.FLINT_AND_STEEL) || itemStack.is(Items.TORCH)) {
                    setLit(world, state, pos, true);
                }
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(itemStack.is(ItemTags.CANDLES) && state.getValue(CANDLES) < 3) {
            stack.consume(1, player);
            world.setBlockAndUpdate(pos, state.cycle(CANDLES));
            return ItemInteractionResult.CONSUME;
        }
        return super.useItemOn(stack, state, world, pos, player, hand, hit);
    }

    public void animateTick(BlockState state, Level world, BlockPos pos, RandomSource random) {
        if (state.getValue(LIT)) {
            this.getParticleOffsets(state).forEach((offset) -> {
                int quarter = state.getValue(HORIZONTAL_FACING).get2DDataValue();
                Vec3 rotatedOffset = offset.add(-0.5, -0.5, -0.5).yRot(quarter * -90 * ((float)Math.PI / 180)).add(0.5, 0.5, 0.5);
                spawnCandleParticles(world, rotatedOffset.add(pos.getX(), pos.getY(), pos.getZ()), random);
            });
        }
    }

    private static void spawnCandleParticles(Level world, Vec3 vec3d, RandomSource random) {
        float f = random.nextFloat();
        if (f < 0.3F) {
            world.addParticle(ParticleTypes.SMOKE, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
            if (f < 0.17F) {
                world.playLocalSound(vec3d.x + 0.5, vec3d.y + 0.5, vec3d.z + 0.5, SoundEvents.CANDLE_AMBIENT, SoundSource.BLOCKS, 1.0F + random.nextFloat(), random.nextFloat() * 0.7F + 0.3F, false);
            }
        }

        world.addParticle(ParticleTypes.SMALL_FLAME, vec3d.x, vec3d.y, vec3d.z, 0.0, 0.0, 0.0);
    }

    protected static void setLit(LevelAccessor world, BlockState state, BlockPos pos, boolean lit) {
        world.setBlock(pos, state.setValue(LIT, lit), 2 | 3);
        if(lit){
            world.playSound(null, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.5F, 1.0F);
        }
    }

    protected static void extinguish(@Nullable Player player, BlockState state, LevelAccessor world, BlockPos pos) {
        setLit(world, state, pos, false);

        world.playSound(null, pos, SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, 1.5F, 1.0F);
        world.gameEvent(player, GameEvent.BLOCK_CHANGE, pos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return Fluids.EMPTY.defaultFluidState();
    }

    public void onProjectileHit(Level world, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (!world.isClientSide && projectile.isOnFire() && !state.getValue(LIT)) {
            world.setBlock(hit.getBlockPos(), state.setValue(LIT, true), STATE_TO_LUMINANCE.applyAsInt(state));
        }
    }

    protected Iterable<Vec3> getParticleOffsets(BlockState state) {
        return (Iterable)CANDLES_TO_PARTICLE_OFFSETS.get(state.getValue(CANDLES));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(HORIZONTAL_FACING, rotation.rotate(state.getValue(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        Direction direction = state.getValue(HORIZONTAL_FACING);
        switch (mirror) {
            case LEFT_RIGHT -> {
                if (direction.getAxis() != Direction.Axis.Z) break;
                return state.rotate(Rotation.CLOCKWISE_180);
            }
            case FRONT_BACK -> {
                if (direction.getAxis() != Direction.Axis.X) break;
                return state.rotate(Rotation.CLOCKWISE_180);
            }
        }
        return super.mirror(state, mirror);
    }

    static {
        LIT = BlockStateProperties.LIT;
        STATE_TO_LUMINANCE = (state) -> state.getValue(LIT) ? (int)(state.getValue(CANDLES) * 3.5f) : 0;
        CANDLES_TO_PARTICLE_OFFSETS = Util.make(new Int2ObjectOpenHashMap(4), (int2ObjectOpenHashMap) -> {
            int2ObjectOpenHashMap.put(1, List.of((new Vec3(8.0, 16.0, 8.0)).scale(0.0625)));
            int2ObjectOpenHashMap.put(2, List.of((new Vec3(7.0, 16.0, 8.0)).scale(0.0625), (new Vec3(10, 14.0, 9)).scale(0.0625)));
            int2ObjectOpenHashMap.put(3, List.of((new Vec3(7.0, 16.0, 9.0)).scale(0.0625), (new Vec3(8.0, 15.0, 6.0)).scale(0.0625),
                    (new Vec3(10, 14.0, 10.0)).scale(0.0625), (new Vec3(2.0, 8.0, 4.0)).scale(0.0625), (new Vec3(13.0, 6.0, 13.0)).scale(0.0625)));
        });
        SHAPE = Block.box(4, 0, 4, 12, 8, 12);
    }
}
