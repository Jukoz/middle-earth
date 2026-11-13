package net.sevenstars.middleearth.block.special.beamlight;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.sevenstars.middleearth.light.BeamLights;

/**
 * The light where a lamp's beam falls (or round someone carrying a lantern): invisible, untouchable, replaced
 * by anything placed there. It checks every half second that its lamp still shines on it and goes out if not.
 */
public class BeamLightBlock extends Block {
    public static final MapCodec<BeamLightBlock> CODEC = createCodec(BeamLightBlock::new);
    public static final IntProperty LEVEL = Properties.LEVEL_15;
    public static final int CHECK_TICKS = 10;

    public BeamLightBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(LEVEL, 12));
    }

    @Override
    protected MapCodec<? extends Block> getCodec() {
        return CODEC;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }

    @Override
    protected BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return VoxelShapes.empty();
    }

    @Override
    protected void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (BeamLights.isFresh(world, pos)) {
            world.scheduleBlockTick(pos, this, CHECK_TICKS);
        } else {
            BeamLights.remove(world, pos);
        }
    }
}
