package net.sevenstars.middleearth.block.special.structureManager.features;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// Where hosts are shows a place is lived in. Indexed by region so a lookup doesn't scan every host
public class HostIndex {
    // Region edge is 1 << REGION_SHIFT blocks
    private static final int REGION_SHIFT = 6;
    private static final Map<RegistryKey<World>, Long2ObjectOpenHashMap<Set<BlockPos>>> HOSTS = new HashMap<>();

    // Hosts are removed as their chunks unload, which a stopping server doesn't wait for
    public static void register() {
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> HOSTS.clear());
    }

    public static void add(World world, BlockPos pos) {
        if (world == null || world.isClient())
            return;
        HOSTS.computeIfAbsent(world.getRegistryKey(), key -> new Long2ObjectOpenHashMap<>())
                .computeIfAbsent(regionOf(pos.getX(), pos.getZ()), key -> new HashSet<>())
                .add(pos.toImmutable());
    }

    public static void remove(@Nullable World world, BlockPos pos) {
        if (world == null || world.isClient())
            return;
        Long2ObjectOpenHashMap<Set<BlockPos>> regions = HOSTS.get(world.getRegistryKey());
        if (regions == null)
            return;

        long region = regionOf(pos.getX(), pos.getZ());
        Set<BlockPos> hosts = regions.get(region);
        if (hosts == null)
            return;
        hosts.remove(pos);
        if (hosts.isEmpty())
            regions.remove(region);
        if (regions.isEmpty())
            HOSTS.remove(world.getRegistryKey());
    }

    public static boolean isClose(World world, BlockPos pos, int radius) {
        Long2ObjectOpenHashMap<Set<BlockPos>> regions = HOSTS.get(world.getRegistryKey());
        if (regions == null)
            return false;

        long radiusSquared = (long) radius * radius;
        int reach = Math.abs(radius);
        int fromX = (pos.getX() - reach) >> REGION_SHIFT, toX = (pos.getX() + reach) >> REGION_SHIFT;
        int fromZ = (pos.getZ() - reach) >> REGION_SHIFT, toZ = (pos.getZ() + reach) >> REGION_SHIFT;
        long cells = (long) (toX - fromX + 1) * (toZ - fromZ + 1);
        // A wide radius covers more regions than there are
        if (cells > regions.size()) {
            for (var entry : regions.long2ObjectEntrySet()) {
                int x = ChunkPos.getPackedX(entry.getLongKey()), z = ChunkPos.getPackedZ(entry.getLongKey());
                if (x >= fromX && x <= toX && z >= fromZ && z <= toZ && anyWithin(entry.getValue(), pos, radiusSquared))
                    return true;
            }
            return false;
        }
        for (int x = fromX; x <= toX; x++) {
            for (int z = fromZ; z <= toZ; z++) {
                Set<BlockPos> hosts = regions.get(ChunkPos.toLong(x, z));
                if (hosts != null && anyWithin(hosts, pos, radiusSquared))
                    return true;
            }
        }
        return false;
    }

    private static boolean anyWithin(Set<BlockPos> hosts, BlockPos pos, long radiusSquared) {
        for (BlockPos hostPos : hosts) {
            if (hostPos.getSquaredDistance(pos) <= radiusSquared)
                return true;
        }
        return false;
    }

    private static long regionOf(int x, int z) {
        return ChunkPos.toLong(x >> REGION_SHIFT, z >> REGION_SHIFT);
    }
}
