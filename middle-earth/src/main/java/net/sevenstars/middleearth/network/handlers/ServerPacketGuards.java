package net.sevenstars.middleearth.network.handlers;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

public final class ServerPacketGuards {
    private static final double MAX_INTERACTION_DISTANCE_SQUARED = 64.0D;

    // C2S payloads are registered on the main thread. Weak keys release state after logout.
    private static final Map<ServerPlayer, Map<ResourceLocation, Long>> LAST_ACTION_TICKS = new WeakHashMap<>();

    private ServerPacketGuards() {
    }

    public static boolean isLoadedAndNearby(ServerPlayer player, BlockPos pos) {
        if (player == null || pos == null || !Level.isInSpawnableBounds(pos)) {
            return false;
        }
        Level level = player.level();
        return level.hasChunkAt(pos)
                && player.distanceToSqr(Vec3.atCenterOf(pos)) <= MAX_INTERACTION_DISTANCE_SQUARED;
    }

    public static BlockPos exactBlockPos(double x, double y, double z) {
        if (!Double.isFinite(x) || !Double.isFinite(y) || !Double.isFinite(z)
                || x != Math.rint(x) || y != Math.rint(y) || z != Math.rint(z)) {
            return null;
        }
        BlockPos pos = BlockPos.containing(x, y, z);
        return Level.isInSpawnableBounds(pos) ? pos : null;
    }

    public static boolean tryAcquire(ServerPlayer player, ResourceLocation action, int minimumIntervalTicks) {
        if (player == null || action == null) {
            return false;
        }
        long currentTick = player.level().getGameTime();
        Map<ResourceLocation, Long> actions = LAST_ACTION_TICKS.computeIfAbsent(player, ignored -> new HashMap<>());
        Long previousTick = actions.get(action);
        if (previousTick != null
                && currentTick >= previousTick
                && currentTick - previousTick < Math.max(1, minimumIntervalTicks)) {
            return false;
        }
        actions.put(action, currentTick);
        return true;
    }
}
