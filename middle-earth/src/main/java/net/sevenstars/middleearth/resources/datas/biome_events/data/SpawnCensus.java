package net.sevenstars.middleearth.resources.datas.biome_events.data;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

// Who lives where, counted once in a while. Every spawn attempt used to search hundreds of blocks around for its neighbours
public class SpawnCensus {
    // In ticks
    private static final int LIFETIME = 60;
    private static final int CELL_SHIFT = 6;

    private static final Map<World, SpawnCensus> WORLDS = new WeakHashMap<>();

    private record Resident(EntityType<?> type, @Nullable Identifier npcType, double x, double y, double z, boolean surface) {}

    private final Long2ObjectOpenHashMap<List<Resident>> cells = new Long2ObjectOpenHashMap<>();
    private boolean taken = false;
    private long takenAt = 0;

    private static SpawnCensus of(ServerWorld world) {
        SpawnCensus census = WORLDS.computeIfAbsent(world, key -> new SpawnCensus());
        if(!census.taken || world.getTime() - census.takenAt >= LIFETIME || world.getTime() < census.takenAt)
            census.take(world);
        return census;
    }

    private void take(ServerWorld world) {
        taken = true;
        takenAt = world.getTime();
        cells.clear();
        for(Entity entity : world.iterateEntities()) {
            if(entity instanceof LivingEntity living && living.isAlive())
                add(living.getType(), living instanceof NpcEntity npc ? npc.getNpcTypeIdentifier() : null, living.getX(), living.getY(), living.getZ(), SpawnEventDataUtil.isSurface(world, living.getBlockPos()));
        }
    }

    private void add(EntityType<?> type, @Nullable Identifier npcType, double x, double y, double z, boolean surface) {
        long cell = ChunkPos.toLong((int) Math.floor(x) >> CELL_SHIFT, (int) Math.floor(z) >> CELL_SHIFT);
        cells.computeIfAbsent(cell, key -> new ArrayList<>()).add(new Resident(type, npcType, x, y, z, surface));
    }

    // Counts a spawn that was just let through, so that the next attempts of the same tick see it
    public static void expect(ServerWorld world, EntityType<?> type, @Nullable Identifier npcType, BlockPos pos) {
        of(world).add(type, npcType, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, SpawnEventDataUtil.isSurface(world, pos));
    }

    public static int count(ServerWorld world, Box box, EntityType<?> type, @Nullable Identifier npcType, boolean surfaceOnly, int stopAt) {
        SpawnCensus census = of(world);
        int found = 0;
        int minimumX = (int) Math.floor(box.minX) >> CELL_SHIFT;
        int maximumX = (int) Math.floor(box.maxX) >> CELL_SHIFT;
        int minimumZ = (int) Math.floor(box.minZ) >> CELL_SHIFT;
        int maximumZ = (int) Math.floor(box.maxZ) >> CELL_SHIFT;
        for(int x = minimumX; x <= maximumX; x++) {
            for(int z = minimumZ; z <= maximumZ; z++) {
                List<Resident> residents = census.cells.get(ChunkPos.toLong(x, z));
                if(residents == null)
                    continue;
                for(Resident resident : residents) {
                    if(resident.type != type || (surfaceOnly && !resident.surface) || !box.contains(resident.x, resident.y, resident.z))
                        continue;
                    if(npcType != null && !npcType.equals(resident.npcType))
                        continue;
                    found++;
                    if(found >= stopAt)
                        return found;
                }
            }
        }
        return found;
    }
}
