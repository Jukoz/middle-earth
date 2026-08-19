package net.sevenstars.middleearth.block.special.verticalSlabs;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TransparentVerticalSlab extends VerticalSlabBlock{
    protected MapCodec<? extends TransparentVerticalSlab> codec() {
        return simpleCodec(TransparentVerticalSlab::new);
    }

    public TransparentVerticalSlab(Properties settings) {
        super(settings);
    }

    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    protected boolean skipRendering(BlockState state, BlockState stateFrom, Direction direction) {
        return stateFrom.is(this) ? true : super.skipRendering(state, stateFrom, direction);
    }

    protected VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return Shapes.empty();
    }

    protected float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
        return 1.0F;
    }
}
