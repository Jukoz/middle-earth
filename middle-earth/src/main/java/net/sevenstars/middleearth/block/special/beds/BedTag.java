package net.sevenstars.middleearth.block.special.beds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataPools;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

// Who is born in a bed: the kind of person, as the last word of the nest of its structure that spawns them, and the job they
// take, or none for the routine of the kind. Links are what they work at, as offsets from the bed head so that a template
// stays valid when it is turned. They are used as they are only when manual, otherwise the nearest target is the link
public record BedTag(String type, Optional<Identifier> job, List<BlockPos> links, boolean manual) {
    public static final Codec<BedTag> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("type").forGetter(BedTag::type),
            Identifier.CODEC.optionalFieldOf("role").forGetter(BedTag::job),
            BlockPos.CODEC.listOf().optionalFieldOf("links", List.of()).forGetter(BedTag::links),
            Codec.BOOL.optionalFieldOf("manual", false).forGetter(BedTag::manual)
    ).apply(instance, BedTag::new));

    private static List<String> types = null;

    public BedTag(String type, Optional<Identifier> job) {
        this(type, job, List.of(), false);
    }

    public BedTag withLinks(List<BlockPos> links, boolean manual) {
        return new BedTag(type, job, List.copyOf(links), manual);
    }

    public BedTag withoutLinks() {
        return withLinks(List.of(), false);
    }

    // Every kind of person any structure has a nest for
    public static synchronized List<String> types() {
        if(types == null) {
            TreeSet<String> found = new TreeSet<>();
            for(StructureManagerData manager : StructureManagerDataPools.all()) {
                for(SpawnNestNodeData nest : manager.getNpcSpawnNest()) {
                    if(nest.isPeople())
                        found.add(typeOf(nest.getId()));
                }
            }
            types = List.copyOf(found);
        }
        return types;
    }

    public static String typeOf(Identifier nestId) {
        String path = nestId.getPath();
        return path.substring(path.lastIndexOf('.') + 1);
    }
}
