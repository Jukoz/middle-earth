package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

/// NpcSpawnNest is a specific area where entities can spawn with the different parameters.
public class SpawnNestNodeData {
    public static final Codec<SpawnNestNodeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(SpawnNestNodeData::getId),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestNodeData::getRespawnTickDelay),
            Codec.INT.fieldOf("bed_radius").forGetter(SpawnNestNodeData::getBedRadius),
            Codec.list(StructureSpawnNestPool.CODEC).fieldOf("npc_pool").forGetter(SpawnNestNodeData::getNpcSpawnNestPool)
    ).apply(instance, SpawnNestNodeData::new));

    private final ResourceLocation id;
    private final int respawnTickDelay;
    private int bedRadius;
    private final List<StructureSpawnNestPool> structureSpawnNestPools;

    public SpawnNestNodeData(ResourceLocation id, int respawnTickDelay, int bedRadius, List<StructureSpawnNestPool> npcPools) {
        this.id = id;
        this.respawnTickDelay = respawnTickDelay;
        this.bedRadius = bedRadius;
        this.structureSpawnNestPools = npcPools;
    }

    public SpawnNestNodeData(ResourceLocation id, int respawnTickDelay, List<StructureSpawnNestPool> npcPools) {
        this.id = id;
        this.respawnTickDelay = respawnTickDelay;
        this.bedRadius = 15;
        this.structureSpawnNestPools = npcPools;
    }

    public SpawnNestNodeData WithBedRadius(int radius){
        this.bedRadius = radius;
        return this;
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public int getRespawnTickDelay() {
        return this.respawnTickDelay;
    }
    public int getBedRadius() {
        return this.bedRadius;
    }
    public List<StructureSpawnNestPool> getNpcSpawnNestPool() {
        return structureSpawnNestPools;
    }

    @Nullable
    public StructureSpawnNestPool getRandomPool(RandomSource random) {
        StructureSpawnNestPool selected = null;
        long totalWeight = 0L;
        for (StructureSpawnNestPool pool : structureSpawnNestPools) {
            int weight = Math.max(0, pool.getWeight());
            if (weight == 0) {
                continue;
            }
            totalWeight += weight;
            if (nextLong(random, totalWeight) < weight) {
                selected = pool;
            }
        }
        return selected;
    }

    private static long nextLong(RandomSource random, long bound) {
        long value = random.nextLong() >>> 1;
        long result = value % bound;
        while (value - result + (bound - 1L) < 0L) {
            value = random.nextLong() >>> 1;
            result = value % bound;
        }
        return result;
    }
}
