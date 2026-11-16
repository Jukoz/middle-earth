package net.sevenstars.middleearth.block.special.structureManager;

import java.util.Set;
import java.util.function.Consumer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.MiddleEarth;

public class StructureEditing {
    // In blocks, from the builder to the manager, nest, marker or bed being set up
    public static final int REACH = 8;
    // In blocks along one side of what is saved as one template. The structure block's own screen cuts a box down to 48
    // when its Done is pressed, so a bigger one is only to be set with the tool or the command
    public static final int MAXIMUM_SIZE = 128;
    private static final int OPERATOR_LEVEL = 2;
    // Set by the game that draws: opens the screen of the section whose structure block was clicked with the tool
    public static Consumer<BlockPos> sectionScreen = pos -> {};

    // What builders do to a build goes in the log, to follow a build being worked on and to tell why a click did nothing
    public static void log(String what) {
        MiddleEarth.LOGGER.logInfoMsg("StructEdit :: " + what);
    }

    public static boolean isBuildMode(PlayerEntity player) {
        return player.isCreative();
    }

    // Builders who pass through blocks, to go over a build from inside its walls. Kept here for the game and the server
    // it runs alike: one that joins a server apart from it is not told, and is pulled back by it
    private static final Set<UUID> NO_CLIP = ConcurrentHashMap.newKeySet();

    public static boolean isNoClip(PlayerEntity player) {
        return !NO_CLIP.isEmpty() && NO_CLIP.contains(player.getUuid()) && player.isCreative();
    }

    public static boolean toggleNoClip(PlayerEntity player) {
        if(NO_CLIP.remove(player.getUuid()))
            return false;
        NO_CLIP.add(player.getUuid());
        return true;
    }

    public static void clearNoClip() {
        NO_CLIP.clear();
    }

    public static boolean isBuilder(PlayerEntity player) {
        return isBuildMode(player) && player.hasPermissionLevel(OPERATOR_LEVEL);
    }

    // Asked again when the request arrives: a screen can be faked, and the block it was opened on can be far away by then
    public static boolean mayEdit(PlayerEntity player, BlockPos pos) {
        boolean allowed = isBuilder(player) && isWithinReach(player, pos);
        if(!allowed)
            log("%s may not edit %s: creative %s, operator %s, within %d blocks and loaded %s".formatted(player.getName().getString(), pos.toShortString(),
                    isBuildMode(player), player.hasPermissionLevel(OPERATOR_LEVEL), REACH, isWithinReach(player, pos)));
        return allowed;
    }

    public static boolean isWithinReach(PlayerEntity player, BlockPos pos) {
        return player.getBlockPos().isWithinDistance(pos, REACH) && player.getWorld().isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
    }
}
