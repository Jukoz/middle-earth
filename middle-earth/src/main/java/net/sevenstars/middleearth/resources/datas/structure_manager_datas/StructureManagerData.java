package net.sevenstars.middleearth.resources.datas.structure_manager_datas;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.sevenstars.middleearth.utils.IdentifierUtil;

import java.util.List;

/// StructureManagerData is used by StructureManagers. It's a data entity that stores the different properties necessary for
/// Spawn Nests.
public class StructureManagerData {
    public static final Codec<StructureManagerData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("id").forGetter(StructureManagerData::getIdString),
            Codec.list(StructureSpawnNest.CODEC).fieldOf("spawn_nest").forGetter(StructureManagerData::getNpcSpawnNest)
    ).apply(instance, StructureManagerData::new));

    private final Identifier id;
    private final List<StructureSpawnNest> structureSpawnNests;

    public StructureManagerData(String id, List<StructureSpawnNest> nests) {
        this.id = IdentifierUtil.getIdentifierFromString(id);
        this.structureSpawnNests = nests;
    }

    public StructureManagerData(Identifier id, List<StructureSpawnNest> nests) {
        this.id = id;
        this.structureSpawnNests = nests;
    }

    private String getIdString() {
        return this.id.toString();
    }
    public Identifier getId() {
        return this.id;
    }

    private List<StructureSpawnNest> getNpcSpawnNest() {
        return structureSpawnNests;
    }
}
