package net.sevenstars.middleearth.block.special.plants;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.sevenstars.middleearth.particles.ModParticleTypes;
import net.sevenstars.middleearth.utils.BlockTagsME;

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
        return (floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTagsME.FARMLANDS)) && floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }
}