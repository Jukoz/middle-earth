package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.sevenstars.middleearth.MiddleEarth;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/// StructureManagerData is used by StructureManagers. It's a data entity that stores the different properties necessary for
/// Spawn Nests.
public class StructureManagerData {
    public static final Codec<StructureManagerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(StructureManagerData::getIdString),
            Codec.list(SpawnNestNodeData.CODEC).fieldOf("spawn_nest").forGetter(StructureManagerData::getNpcSpawnNest)
    ).apply(instance, StructureManagerData::new));

    private final ResourceLocation id;
    private final List<SpawnNestNodeData> spawnNestNodeData;
    private final Map<ResourceLocation, SpawnNestNodeData> spawnNestNodesById;

    public StructureManagerData(String id, List<SpawnNestNodeData> nests) {
        this(MiddleEarth.fetchId(id), nests);
    }

    public StructureManagerData(ResourceLocation id, List<SpawnNestNodeData> nests) {
        this.id = id;
        this.spawnNestNodeData = List.copyOf(nests);
        this.spawnNestNodesById = new LinkedHashMap<>();
        for (SpawnNestNodeData nest : this.spawnNestNodeData) {
            this.spawnNestNodesById.putIfAbsent(nest.getId(), nest);
        }
    }

    private String getIdString() {
        return this.id.toString();
    }

    public ResourceLocation getId() {
        return this.id;
    }

    public List<SpawnNestNodeData> getNpcSpawnNest() {
        if(this.spawnNestNodeData.isEmpty())
            return List.of();
        return spawnNestNodeData;
    }

    @Nullable
    public SpawnNestNodeData getNpcSpawnNest(ResourceLocation idToCompare) {
        return spawnNestNodesById.get(idToCompare);
    }
}
