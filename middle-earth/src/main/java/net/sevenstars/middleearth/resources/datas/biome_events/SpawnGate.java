package net.sevenstars.middleearth.resources.datas.biome_events;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

// Spawn state that outlives one check: picks waiting for their npc, and kinds that are shut out of a chunk for a while
public class SpawnGate {
    // In ticks
    private static final int PENDING_LIFETIME = 40;
    private static final int MAXIMUM_PENDING = 256;
    private static final int MAXIMUM_DISABLED = 4096;

    private static final Map<World, SpawnGate> WORLDS = new WeakHashMap<>();

    // Per kind, so npcs waiting for night don't stop sheep spawning
    private record DisabledSpawn(ChunkPos chunk, EntityType<?> type) {}

    private record PendingSpawn(WildSpawnEventData data, long expires) {}

    private final Long2ObjectOpenHashMap<PendingSpawn> pending = new Long2ObjectOpenHashMap<>();
    private final HashMap<DisabledSpawn, Long> disabled = new HashMap<>();

    public static void register() {
        // World times, which a different save starts over
        ServerLifecycleEvents.SERVER_STOPPED.register(server -> WORLDS.clear());
    }

    private static SpawnGate of(World world) {
        return WORLDS.computeIfAbsent(world, key -> new SpawnGate());
    }

    public static boolean isDisabled(ServerWorld world, BlockPos pos, EntityType<?> type) {
        SpawnGate gate = of(world);
        DisabledSpawn spawn = new DisabledSpawn(new ChunkPos(pos), type);
        Long expiredTick = gate.disabled.get(spawn);
        if(expiredTick == null)
            return false;
        if(world.getTime() < expiredTick)
            return true;
        gate.disabled.remove(spawn);
        return false;
    }

    public static void disable(ServerWorld world, BlockPos pos, EntityType<?> type, int ticks) {
        SpawnGate gate = of(world);
        // Only revisited where something tries to spawn, so abandoned chunks' entries pile up
        if(gate.disabled.size() >= MAXIMUM_DISABLED)
            gate.disabled.values().removeIf(until -> until <= world.getTime());
        gate.disabled.put(new DisabledSpawn(new ChunkPos(pos), type), world.getTime() + ticks);
    }

    public static void hold(ServerWorld world, BlockPos pos, WildSpawnEventData data) {
        SpawnGate gate = of(world);
        if(gate.pending.size() >= MAXIMUM_PENDING)
            gate.pending.clear();
        gate.pending.put(pos.asLong(), new PendingSpawn(data, world.getTime() + PENDING_LIFETIME));
    }

    // Only the event that made a pick can take it
    public static @Nullable WildSpawnEventData takeHeld(World world, BlockPos pos, List<WildSpawnEventData> events) {
        SpawnGate gate = of(world);
        PendingSpawn held = gate.pending.get(pos.asLong());
        if(held == null || !events.contains(held.data()))
            return null;
        gate.pending.remove(pos.asLong());
        return world.getTime() <= held.expires() ? held.data() : null;
    }
}
