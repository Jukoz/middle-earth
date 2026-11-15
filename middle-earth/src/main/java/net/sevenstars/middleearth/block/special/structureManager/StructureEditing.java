package net.sevenstars.middleearth.block.special.structureManager;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;

public class StructureEditing {
    // In blocks, from the builder to the manager, nest, marker or bed being set up
    public static final int REACH = 8;

    // What builders do to a build goes in the log, to follow a build being worked on and to tell why a click did nothing
    public static void log(String what) {
        MiddleEarth.LOGGER.logInfoMsg("StructEdit :: " + what);
    }

    // Asked again when the request arrives: a screen can be faked, and the block it was opened on can be far away by then
    public static boolean mayEdit(PlayerEntity player, BlockPos pos) {
        boolean allowed = player.isCreative() && player.isCreativeLevelTwoOp() && isWithinReach(player, pos);
        if(!allowed)
            log("%s may not edit %s: creative %s, operator %s, within %d blocks and loaded %s".formatted(player.getName().getString(), pos.toShortString(),
                    player.isCreative(), player.isCreativeLevelTwoOp(), REACH, isWithinReach(player, pos)));
        return allowed;
    }

    public static boolean isWithinReach(PlayerEntity player, BlockPos pos) {
        return player.getBlockPos().isWithinDistance(pos, REACH) && player.getWorld().isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
    }
}
