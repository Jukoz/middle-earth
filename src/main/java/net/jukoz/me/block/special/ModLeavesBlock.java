package net.jukoz.me.block.special;

import net.jukoz.me.particles.ModParticleTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.OptionalInt;

public class ModLeavesBlock extends LeavesBlock {
    final protected boolean castShadow;
    private final ParticleEffect particleType;
    public ModLeavesBlock(Settings settings, boolean castShadow, ParticleEffect particleType) {
        super(settings);
        this.castShadow = castShadow;
        this.particleType = particleType;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), Block.NOTIFY_ALL);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        int i;
        if (state.get(WATERLOGGED).booleanValue()) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        if ((i = getDistanceFromLog(neighborState) + 1) != 1 || state.get(DISTANCE) != i) {
            world.scheduleBlockTick(pos, this, 1);
        }
        return state;
    }

    private static BlockState updateDistanceFromLogs(BlockState state, WorldAccess world, BlockPos pos) {
        int i = 7;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for(int x = -1; x <= 1; x++) {
            for(int y = -1; y <= 1; y++) {
                for(int z = -1; z <= 1; z++) {
                    if(x == 0 && y == 0 && z == 0) continue;
                    mutable.set(pos, x, y, z);
                    i = Math.min(i, getDistanceFromLog(world.getBlockState(mutable)) + 1);
                    if (i == 1) break;
                }
            }
        }

        return state.with(DISTANCE, i);
    }

    private static int getDistanceFromLog(BlockState state) {
        return LeavesBlock.getOptionalDistanceFromLog(state).orElse(7);
    }

    @Override
    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        if(castShadow) return super.getOpacity(state, world, pos);
        return 0;
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        BlockState blockState = (this.getDefaultState().with(PERSISTENT, true)).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
        return updateDistanceFromLogs(blockState, ctx.getWorld(), ctx.getBlockPos());
    }

    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        super.randomDisplayTick(state, world, pos, random);
        if(this.particleType != null){
            if (random.nextInt(10) == 0) {
                BlockPos blockPos = pos.down();
                BlockState blockState = world.getBlockState(blockPos);
                if (!isFaceFullSquare(blockState.getCollisionShape(world, blockPos), Direction.UP)) {
                    ParticleUtil.spawnParticle(world, pos, random, this.particleType);
                }
            }
        }

    }
}
