package net.sevenstars.middleearth.entity.npcs.routine;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

// Field plots and crops: what needs doing on one, and what it yields
public final class FarmPlots {
    private static final int CROP_SEARCH_RANGE = 2;

    private FarmPlots() {}

    public static boolean needsWork(ServerWorld world, BlockPos cell) {
        BlockState ground = world.getBlockState(cell);
        BlockState above = world.getBlockState(cell.up());
        if(ground.getBlock() instanceof FarmlandBlock)
            return above.isAir() || (above.getBlock() instanceof CropBlock crop && crop.isMature(above));
        return isTillable(ground) && (above.isAir() || above.isReplaceable());
    }

    // Ground that was a plot and has been trodden or grown over
    public static boolean isTillable(BlockState ground) {
        return ground.isIn(BlockTags.DIRT) && !ground.isOf(Blocks.MUD) && !ground.isOf(Blocks.MOSS_BLOCK);
    }

    // Sown with what grows in the next rows, or wheat where nothing does
    public static Block cropFor(ServerWorld world, BlockPos cell) {
        for(BlockPos near : BlockPos.iterateOutwards(cell.up(), CROP_SEARCH_RANGE, 0, CROP_SEARCH_RANGE)) {
            if(world.getBlockState(near).getBlock() instanceof CropBlock crop)
                return crop;
        }
        return Blocks.WHEAT;
    }

    // What the crop is grown for, not its seed: the block's own item is the seed of wheat and beetroot, the crop itself for carrots
    public static Item harvestOf(ServerWorld world, BlockPos pos, BlockState crop) {
        Item seed = crop.getBlock().asItem();
        for(ItemStack drop : Block.getDroppedStacks(crop, world, pos, null)) {
            if(!drop.isEmpty() && drop.getItem() != seed)
                return drop.getItem();
        }
        return seed;
    }
}
