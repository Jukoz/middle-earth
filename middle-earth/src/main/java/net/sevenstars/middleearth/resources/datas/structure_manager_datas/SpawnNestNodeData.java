package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.npc_jobs.NpcJob;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class SpawnNestNodeData {
    public static final Codec<SpawnNestNodeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(SpawnNestNodeData::getId),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestNodeData::getRespawnTickDelay),
            Codec.list(StructureSpawnNestPool.CODEC).fieldOf("npc_pool").forGetter(SpawnNestNodeData::getNpcSpawnNestPool),
            Identifier.CODEC.optionalFieldOf("role").forGetter(SpawnNestNodeData::getJob)
    ).apply(instance, SpawnNestNodeData::new));

    private final Identifier id;
    private final int respawnTickDelay;
    private Optional<Identifier> job;
    private final List<StructureSpawnNestPool> structureSpawnNestPools;

    public SpawnNestNodeData(Identifier id, int respawnTickDelay, List<StructureSpawnNestPool> npcPools, Optional<Identifier> job) {
        this.job = job;
        this.id = id;
        this.respawnTickDelay = respawnTickDelay;
        this.structureSpawnNestPools = npcPools;
    }

    public SpawnNestNodeData(Identifier id, int respawnTickDelay, List<StructureSpawnNestPool> npcPools) {
        // Job comes from the nest name unless another is given
        this(id, respawnTickDelay, npcPools, NpcJobs.forNest(id));
    }

    public SpawnNestNodeData withJob(RegistryKey<NpcJob> job) {
        this.job = Optional.of(job.getValue());
        return this;
    }

    public Optional<Identifier> getJob() {
        return job;
    }

    public Identifier getId() {
        return this.id;
    }

    public int getRespawnTickDelay() {
        return this.respawnTickDelay;
    }
    public List<StructureSpawnNestPool> getNpcSpawnNestPool() {
        return structureSpawnNestPools;
    }

    public boolean isPeople() {
        return !structureSpawnNestPools.isEmpty() && structureSpawnNestPools.stream().allMatch(pool -> pool.getEntityType() == EntitiesME.NPC);
    }

    public StructureSpawnNestPool getRandomPool() {
        int total = 0;
        for(StructureSpawnNestPool pool : structureSpawnNestPools)
            total += Math.max(1, pool.getWeight());
        int roll = new Random().nextInt(total);
        for(StructureSpawnNestPool pool : structureSpawnNestPools) {
            roll -= Math.max(1, pool.getWeight());
            if(roll < 0)
                return pool;
        }
        return structureSpawnNestPools.getLast();
    }
}
