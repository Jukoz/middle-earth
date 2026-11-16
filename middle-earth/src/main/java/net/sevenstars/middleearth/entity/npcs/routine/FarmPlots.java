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

// Field plots and crops: what work a plot needs and what it yields
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

    // Ground that used to be a plot and has since been trampled or grown over
    public static boolean isTillable(BlockState ground) {
        return ground.isIn(BlockTags.DIRT) && !ground.isOf(Blocks.MUD) && !ground.isOf(Blocks.MOSS_BLOCK);
    }

    // Sown with whatever grows in the nearby rows, or wheat if nothing does
    public static Block cropFor(ServerWorld world, BlockPos cell) {
        for(BlockPos near : BlockPos.iterateOutwards(cell.up(), CROP_SEARCH_RANGE, 0, CROP_SEARCH_RANGE)) {
            if(world.getBlockState(near).getBlock() instanceof CropBlock crop)
                return crop;
        }
        return Blocks.WHEAT;
    }

    // The item the crop is grown for, not its seed. The block's own item is the seed for wheat and beetroot but the crop itself for carrots
    public static Item harvestOf(ServerWorld world, BlockPos pos, BlockState crop) {
        Item seed = crop.getBlock().asItem();
        for(ItemStack drop : Block.getDroppedStacks(crop, world, pos, null)) {
            if(!drop.isEmpty() && drop.getItem() != seed)
                return drop.getItem();
        }
        return seed;
    }
}
