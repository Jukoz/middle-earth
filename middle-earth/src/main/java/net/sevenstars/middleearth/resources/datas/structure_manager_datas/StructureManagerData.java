package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import org.jetbrains.annotations.Nullable;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.block.special.beds.BedTag;

import java.util.List;

public class StructureManagerData {
    public static final Codec<StructureManagerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(StructureManagerData::getIdString),
            Codec.list(SpawnNestNodeData.CODEC).fieldOf("spawn_nest").forGetter(StructureManagerData::getNpcSpawnNest)
    ).apply(instance, StructureManagerData::new));

    private final Identifier id;
    private final List<SpawnNestNodeData> spawnNestNodeData;

    public StructureManagerData(String id, List<SpawnNestNodeData> nests) {
        this.id = MiddleEarth.ofId(id);
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

    public @Nullable SpawnNestNodeData findSpawnNest(Identifier id) {
        for(SpawnNestNodeData nest : spawnNestNodeData) {
            if(nest.getId().equals(id))
                return nest;
        }
        return null;
    }

    // Animal nests and other managers' nests can share the last word
    public @Nullable SpawnNestNodeData findPeopleNest(String nestKind) {
        for(SpawnNestNodeData nest : spawnNestNodeData) {
            if(nest.isPeople() && BedTag.nestKindOf(nest.getId()).equals(nestKind))
                return nest;
        }
        return null;
    }
}
