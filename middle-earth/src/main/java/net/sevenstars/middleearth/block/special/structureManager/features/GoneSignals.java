package net.sevenstars.middleearth.block.special.structureManager.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// People who left while their host's chunk was unloaded, until the host next ticks
public class GoneSignals extends PersistentState {
    private record Entry(BlockPos host, List<UUID> uuids) {
        static final Codec<Entry> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                BlockPos.CODEC.fieldOf("host").forGetter(Entry::host),
                Codec.list(Uuids.CODEC).fieldOf("uuids").forGetter(Entry::uuids)
        ).apply(instance, Entry::new));
    }

    private static final Codec<GoneSignals> CODEC = Entry.CODEC.listOf().xmap(GoneSignals::new, GoneSignals::entries);
    private static final PersistentStateType<GoneSignals> TYPE = new PersistentStateType<>("middle_earth_gone_signals", GoneSignals::new, CODEC, null);

    private final Map<BlockPos, Set<UUID>> pending = new HashMap<>();

    public GoneSignals() {}

    private GoneSignals(List<Entry> entries) {
        for(Entry entry : entries)
            pending.computeIfAbsent(entry.host(), key -> new HashSet<>()).addAll(entry.uuids());
    }

    private List<Entry> entries() {
        List<Entry> entries = new ArrayList<>();
        pending.forEach((host, uuids) -> entries.add(new Entry(host, new ArrayList<>(uuids))));
        return entries;
    }

    private static GoneSignals of(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE);
    }

    public static void queue(ServerWorld world, BlockPos host, UUID uuid) {
        GoneSignals state = of(world);
        state.pending.computeIfAbsent(host.toImmutable(), key -> new HashSet<>()).add(uuid);
        state.markDirty();
    }

    public static Set<UUID> take(ServerWorld world, BlockPos host) {
        GoneSignals state = of(world);
        if(state.pending.isEmpty())
            return Set.of();
        Set<UUID> taken = state.pending.remove(host);
        if(taken == null)
            return Set.of();
        state.markDirty();
        return taken;
    }
}
