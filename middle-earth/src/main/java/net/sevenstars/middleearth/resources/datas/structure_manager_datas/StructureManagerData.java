package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.List;

/// StructureManagerData is used by StructureManagers. It's a data entity that stores the different properties necessary for
/// Spawn Nests.
public class StructureManagerData {
    public static final Codec<StructureManagerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(StructureManagerData::getIdString),
            Codec.list(SpawnNestNodeData.CODEC).fieldOf("spawn_nest").forGetter(StructureManagerData::getNpcSpawnNest)
    ).apply(instance, StructureManagerData::new));

    private final Identifier id;
    private final List<SpawnNestNodeData> spawnNestNodeData;

    public StructureManagerData(String id, List<SpawnNestNodeData> nests) {
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.spawnNestNodeData = nests;
    }

    public StructureManagerData(Identifier id, List<SpawnNestNodeData> nests) {
        this.id = id;
        this.spawnNestNodeData = nests;
    }

    private String getIdString() {
        return this.id.toString();
    }

    public Identifier getId() {
        return this.id;
    }

    public List<SpawnNestNodeData> getNpcSpawnNest() {
        if(this.spawnNestNodeData.isEmpty())
            return List.of();
        return spawnNestNodeData;
    }

    public SpawnNestNodeData getNpcSpawnNest(Identifier idToCompare) {
        for (var nest : spawnNestNodeData){
            if(nest.getId().equals(idToCompare))
                return nest;
        }
        MiddleEarth.LOGGER.logDebugMsg("StructureManagerData :: Couldn't find nest id : %s".formatted(idToCompare));
        return spawnNestNodeData.getFirst();
    }
}
