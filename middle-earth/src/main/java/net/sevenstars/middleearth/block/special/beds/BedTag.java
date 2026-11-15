package net.sevenstars.middleearth.block.special.beds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataPools;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

// Who is born in a bed: the kind of person, as the last word of the nest of its structure that spawns them, and the job they
// take, or none for the routine of the kind. The class is named where one of the nest's own will not do, as for who is
// held in a cell. Links are what they work at, as offsets from the bed head so that a template
// stays valid when it is turned. They are used as they are only when manual, otherwise the nearest target is the link.
// The manager is the one the bed was tied to by hand, an offset as the links are. Without one the bed is born by the nearest
// manager of its structure that has a nest of its kind
public record BedTag(String nestKind, Optional<Identifier> job, Optional<Identifier> npcClass, List<BlockPos> links, boolean manual, Optional<BlockPos> manager) {
    public static final Codec<BedTag> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("type").forGetter(BedTag::nestKind),
            Identifier.CODEC.optionalFieldOf("role").forGetter(BedTag::job),
            Identifier.CODEC.optionalFieldOf("npc_class").forGetter(BedTag::npcClass),
            BlockPos.CODEC.listOf().optionalFieldOf("links", List.of()).forGetter(BedTag::links),
            Codec.BOOL.optionalFieldOf("manual", false).forGetter(BedTag::manual),
            BlockPos.CODEC.optionalFieldOf("manager").forGetter(BedTag::manager)
    ).apply(instance, BedTag::new));

    private static List<String> nestKinds = null;

    // Who a kind of nest houses, by the classes of its pools in every structure that has one
    public enum Folk {
        COMMON,
        FIGHTERS,
        PRISONERS,
        MIXED
    }

    public static Folk folkOf(DynamicRegistryManager registries, String nestKind) {
        if(NpcJobs.forNestKind(nestKind).filter(NpcJobs.PRISONER.getValue()::equals).isPresent())
            return Folk.PRISONERS;
        Registry<NpcClass> classes = registries.getOptional(DynamicRegistriesME.NPC_CLASS).orElse(null);
        boolean common = false;
        boolean fighters = false;
        for(StructureManagerData manager : StructureManagerDataPools.all()) {
            SpawnNestNodeData nest = manager.findPeopleNest(nestKind);
            if(nest == null || classes == null)
                continue;
            for(StructureSpawnNestPool pool : nest.getNpcSpawnNestPool()) {
                NpcClass npcClass = pool.getNpcIdentifier().map(classes::get).orElse(null);
                if(npcClass == null)
                    continue;
                common |= npcClass.isCivilian();
                fighters |= !npcClass.isCivilian();
            }
        }
        return common == fighters ? Folk.MIXED : common ? Folk.COMMON : Folk.FIGHTERS;
    }

    public BedTag(String nestKind, Optional<Identifier> job, Optional<Identifier> npcClass) {
        this(nestKind, job, npcClass, List.of(), false, Optional.empty());
    }

    public BedTag withLinks(List<BlockPos> links, boolean manual) {
        return new BedTag(nestKind, job, npcClass, List.copyOf(links), manual, manager);
    }

    public BedTag withManager(Optional<BlockPos> manager) {
        return new BedTag(nestKind, job, npcClass, links, manual, manager);
    }

    public BedTag withoutLinks() {
        return withLinks(List.of(), false);
    }

    // Every kind of person any structure has a nest for
    public static synchronized List<String> nestKinds() {
        if(nestKinds == null) {
            TreeSet<String> found = new TreeSet<>();
            for(StructureManagerData manager : StructureManagerDataPools.all()) {
                for(SpawnNestNodeData nest : manager.getNpcSpawnNest()) {
                    if(nest.isPeople())
                        found.add(nestKindOf(nest.getId()));
                }
            }
            nestKinds = List.copyOf(found);
        }
        return nestKinds;
    }

    public static String nestKindOf(Identifier nestId) {
        String path = nestId.getPath();
        return path.substring(path.lastIndexOf('.') + 1);
    }
}
