package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.Optional;
import net.minecraft.registry.RegistryKey;
import net.sevenstars.middleearth.registries.content.npcroles.NpcRoles;
import net.sevenstars.middleearth.resources.datas.npc_roles.NpcRole;
import java.util.Random;

/// NpcSpawnNest is a specific area where entities can spawn with the different parameters.
public class SpawnNestNodeData {
    public static final Codec<SpawnNestNodeData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(SpawnNestNodeData::getId),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestNodeData::getRespawnTickDelay),
            Codec.INT.fieldOf("bed_radius").forGetter(SpawnNestNodeData::getBedRadius),
            Codec.list(StructureSpawnNestPool.CODEC).fieldOf("npc_pool").forGetter(SpawnNestNodeData::getNpcSpawnNestPool),
            Identifier.CODEC.optionalFieldOf("role").forGetter(SpawnNestNodeData::getRole)
    ).apply(instance, SpawnNestNodeData::new));

    private final Identifier id;
    private final int respawnTickDelay;
    private int bedRadius;
    private Optional<Identifier> role;
    private final List<StructureSpawnNestPool> structureSpawnNestPools;

    public SpawnNestNodeData(Identifier id, int respawnTickDelay, int bedRadius, List<StructureSpawnNestPool> npcPools, Optional<Identifier> role) {
        this.role = role;
        this.id = id;
        this.respawnTickDelay = respawnTickDelay;
        this.bedRadius = bedRadius;
        this.structureSpawnNestPools = npcPools;
    }

    public SpawnNestNodeData(Identifier id, int respawnTickDelay, int bedRadius, List<StructureSpawnNestPool> npcPools) {
        this(id, respawnTickDelay, bedRadius, npcPools, NpcRoles.forNest(id));
    }

    public SpawnNestNodeData(Identifier id, int respawnTickDelay, List<StructureSpawnNestPool> npcPools) {
        // A baker's nest houses a baker, unless told otherwise
        this.role = NpcRoles.forNest(id);
        this.id = id;
        this.respawnTickDelay = respawnTickDelay;
        this.bedRadius = 15;
        this.structureSpawnNestPools = npcPools;
    }

    public SpawnNestNodeData withRole(RegistryKey<NpcRole> role) {
        this.role = Optional.of(role.getValue());
        return this;
    }

    public Optional<Identifier> getRole() {
        return role;
    }

    public SpawnNestNodeData WithBedRadius(int radius){
        this.bedRadius = radius;
        return this;
    }

    public Identifier getId() {
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

    public StructureSpawnNestPool getRandomPool() {
        Random random = new Random();
        return structureSpawnNestPools.get(random.nextInt(0, structureSpawnNestPools.size()));
    }
}
