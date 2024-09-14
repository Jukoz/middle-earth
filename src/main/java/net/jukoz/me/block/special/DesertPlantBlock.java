package net.jukoz.me.block.special;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class DesertPlantBlock extends CustomPlantBlock {
    public static final MapCodec<DesertPlantBlock> CODEC = DesertPlantBlock.createCodec(DesertPlantBlock::new);

    public DesertPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SAND) || floor.isOf(Blocks.FARMLAND);
    }
}