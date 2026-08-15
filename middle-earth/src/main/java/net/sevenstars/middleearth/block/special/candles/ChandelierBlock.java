package net.sevenstars.middleearth.block.special.candles;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class ChandelierBlock extends AbstractChandelierBlock {
    public static final MapCodec<ChandelierBlock> CODEC = simpleCodec(ChandelierBlock::new);
    private static final double CANDLE_HEIGHT = 12.5 / 16.0;
    private static final List<Vec3> VARIANT_ONE_PARTICLES = List.of(
            new Vec3(0.5, CANDLE_HEIGHT, -0.375),
            new Vec3(0.5, CANDLE_HEIGHT, 1.375),
            new Vec3(0.375, CANDLE_HEIGHT, 0.5),
            new Vec3(1.375, CANDLE_HEIGHT, 0.5),
            new Vec3(-0.125, CANDLE_HEIGHT, -0.125),
            new Vec3(-0.125, CANDLE_HEIGHT, 1.125),
            new Vec3(1.125, CANDLE_HEIGHT, -0.125),
            new Vec3(1.125, CANDLE_HEIGHT, 1.125));
    private static final List<Vec3> VARIANT_TWO_PARTICLES = List.of(
            new Vec3(-0.25, CANDLE_HEIGHT, -0.25),
            new Vec3(-0.25, CANDLE_HEIGHT, 1.25),
            new Vec3(1.25, CANDLE_HEIGHT, -0.25),
            new Vec3(1.25, CANDLE_HEIGHT, 1.25),
            new Vec3(-0.25, CANDLE_HEIGHT, 0.5),
            new Vec3(0.5, CANDLE_HEIGHT, 1.25),
            new Vec3(0.5, CANDLE_HEIGHT, -0.25),
            new Vec3(1.25, CANDLE_HEIGHT, 0.5));
    private static final VoxelShape SHAPE = Block.box(0.0, 2.0, 0.0, 16.0, 16.0, 16.0);

    public ChandelierBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends Block> codec() {
        return CODEC;
    }

    @Override
    protected Iterable<Vec3> particleOffsets(BlockState state) {
        return state.getValue(VARIANT) == 1 ? VARIANT_ONE_PARTICLES : VARIANT_TWO_PARTICLES;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
}
