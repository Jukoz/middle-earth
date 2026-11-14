package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.entity.EntitiesME;

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
            Codec.list(StructureSpawnNestPool.CODEC).fieldOf("npc_pool").forGetter(SpawnNestNodeData::getNpcSpawnNestPool),
            Identifier.CODEC.optionalFieldOf("role").forGetter(SpawnNestNodeData::getRole)
    ).apply(instance, SpawnNestNodeData::new));

    private final Identifier id;
    private final int respawnTickDelay;
    private Optional<Identifier> role;
    private final List<StructureSpawnNestPool> structureSpawnNestPools;

    public SpawnNestNodeData(Identifier id, int respawnTickDelay, List<StructureSpawnNestPool> npcPools, Optional<Identifier> role) {
        this.role = role;
        this.id = id;
        this.respawnTickDelay = respawnTickDelay;
        this.structureSpawnNestPools = npcPools;
    }

    public SpawnNestNodeData(Identifier id, int respawnTickDelay, List<StructureSpawnNestPool> npcPools) {
        // A baker's nest houses a baker, unless told otherwise
        this(id, respawnTickDelay, npcPools, NpcRoles.forNest(id));
    }

    public SpawnNestNodeData withRole(RegistryKey<NpcRole> role) {
        this.role = Optional.of(role.getValue());
        return this;
    }

    public Optional<Identifier> getRole() {
        return role;
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

    // A nest of people, which is born in beds, as opposed to a nest of beasts
    public boolean isPeople() {
        return !structureSpawnNestPools.isEmpty() && structureSpawnNestPools.stream().allMatch(pool -> pool.getEntityType() == EntitiesME.NPC);
    }

    public StructureSpawnNestPool getRandomPool() {
        Random random = new Random();
        return structureSpawnNestPools.get(random.nextInt(0, structureSpawnNestPools.size()));
    }
}
