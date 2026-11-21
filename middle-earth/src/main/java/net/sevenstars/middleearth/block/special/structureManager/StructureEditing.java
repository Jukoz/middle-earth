package net.sevenstars.middleearth.block.special.structureManager;

import java.util.Set;
import java.util.function.Consumer;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;

public class StructureEditing {
    // In blocks
    public static final int REACH = 8;
    // Per side, in blocks. The structure block screen clamps to 48, so more needs the tool or command
    public static final int MAXIMUM_SIZE = 128;
    public static Consumer<BlockPos> sectionScreen = pos -> {};

    public static void log(String what) {
        MiddleEarth.LOGGER.logInfoMsg("StructEdit :: " + what);
    }

    public static boolean isBuildMode(PlayerEntity player) {
        return player.isCreative();
    }

    // Shared by client and server; a client on a separate server isn't told, and gets pulled back
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
        return isBuildMode(player) && CommandManager.GAMEMASTERS_CHECK.allows(player.getPermissions());
    }

    // Checked again on arrival: a screen can be faked and the block may be far away
    public static boolean mayEdit(PlayerEntity player, BlockPos pos) {
        boolean allowed = isBuilder(player) && isWithinReach(player, pos);
        if(!allowed)
            log("%s may not edit %s: creative %s, operator %s, within %d blocks and loaded %s".formatted(player.getName().getString(), pos.toShortString(),
                    isBuildMode(player), CommandManager.GAMEMASTERS_CHECK.allows(player.getPermissions()), REACH, isWithinReach(player, pos)));
        return allowed;
    }

    // For positions a packet names besides the one checked: loaded, and within a build's size of it
    public static boolean isNear(World world, BlockPos checked, BlockPos pos) {
        return Math.abs(pos.getX() - checked.getX()) <= MAXIMUM_SIZE && Math.abs(pos.getY() - checked.getY()) <= MAXIMUM_SIZE && Math.abs(pos.getZ() - checked.getZ()) <= MAXIMUM_SIZE
                && world.isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
    }

    public static boolean isWithinReach(PlayerEntity player, BlockPos pos) {
        return player.getBlockPos().isWithinDistance(pos, REACH) && player.getEntityWorld().isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4);
    }
}
