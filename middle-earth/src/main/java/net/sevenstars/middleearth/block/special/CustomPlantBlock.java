package net.sevenstars.middleearth.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CustomPlantBlock extends PlantBlock {
    public static final MapCodec<CustomPlantBlock> CODEC = CustomPlantBlock.createCodec(CustomPlantBlock::new);
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public CustomPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected MapCodec<CustomPlantBlock> getCodec() {
        return CODEC;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return (floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND)) && floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}