package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

public class StructureEditing {
    // In blocks, from the builder to the manager, nest, marker or bed being set up
    public static final int REACH = 8;

    // Asked again when the request arrives: a screen can be faked, and the block it was opened on can be far away by then
    public static boolean mayEdit(PlayerEntity player, BlockPos pos) {
        return player.isCreative() && player.isCreativeLevelTwoOp() && isWithinReach(player, pos);
    }

    public static boolean isWithinReach(PlayerEntity player, BlockPos pos) {
        return player.getBlockPos().isWithinDistance(pos, REACH) && player.getWorld().isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
    }
}
