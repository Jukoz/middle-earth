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

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Optional;
import java.util.TreeSet;

// Who spawns in a bed. The nest kind is the last word of the nest's id. An empty job means that kind's default routine.
// The class is only set where the nest's own classes won't do, such as who is held in a cell. Links and store are
// offsets from the bed head, so a rotated template stays valid; links count only when manual, otherwise the nearest
// target is used. A bed with no place spawns nobody. The manager offset is unused and kept only so old templates load
public record BedTag(String nestKind, Optional<Identifier> job, Optional<Identifier> npcClass, List<BlockPos> links, boolean manual, Optional<BlockPos> manager,
                     Optional<Identifier> place, Optional<BlockPos> store) {
    public static final Codec<BedTag> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("type").forGetter(BedTag::nestKind),
            Identifier.CODEC.optionalFieldOf("role").forGetter(BedTag::job),
            Identifier.CODEC.optionalFieldOf("npc_class").forGetter(BedTag::npcClass),
            BlockPos.CODEC.listOf().optionalFieldOf("links", List.of()).forGetter(BedTag::links),
            Codec.BOOL.optionalFieldOf("manual", false).forGetter(BedTag::manual),
            BlockPos.CODEC.optionalFieldOf("manager").forGetter(BedTag::manager),
            Identifier.CODEC.optionalFieldOf("place").forGetter(BedTag::place),
            BlockPos.CODEC.optionalFieldOf("store").forGetter(BedTag::store)
    ).apply(instance, BedTag::new));

    private static List<String> nestKinds = null;
    private static Map<Identifier, Identifier> peopleNests = null;

    // Who a kind of nest houses, judged by the classes of its pools in every structure that has one
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

    public BedTag(String nestKind, Optional<Identifier> job, Optional<Identifier> npcClass, Optional<Identifier> place) {
        this(nestKind, job, npcClass, List.of(), false, Optional.empty(), place, Optional.empty());
    }

    public BedTag withLinks(List<BlockPos> links, boolean manual) {
        return new BedTag(nestKind, job, npcClass, List.copyOf(links), manual, manager, place, store);
    }

    public BedTag withManager(Optional<BlockPos> manager) {
        return new BedTag(nestKind, job, npcClass, links, manual, manager, place, store);
    }

    public BedTag withStore(Optional<BlockPos> store) {
        return new BedTag(nestKind, job, npcClass, links, manual, manager, place, store);
    }

    public BedTag withoutLinks() {
        return withLinks(List.of(), false);
    }

    // Every kind of person that any structure has a nest for
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

    // The place of each people nest, keyed by nest. A spawn marker names its people by such a nest
    public static synchronized Map<Identifier, Identifier> peopleNests() {
        if(peopleNests == null) {
            Map<Identifier, Identifier> found = new TreeMap<>();
            for(StructureManagerData place : StructureManagerDataPools.all()) {
                for(SpawnNestNodeData nest : place.getNpcSpawnNest()) {
                    if(nest.isPeople())
                        found.put(nest.getId(), place.getId());
                }
            }
            peopleNests = Collections.unmodifiableMap(found);
        }
        return peopleNests;
    }

    public static boolean placeHas(Identifier place, String nestKind) {
        for(Map.Entry<Identifier, Identifier> nest : peopleNests().entrySet()) {
            if(nest.getValue().equals(place) && nestKindOf(nest.getKey()).equals(nestKind))
                return true;
        }
        return false;
    }

    public static String nestKindOf(Identifier nestId) {
        String path = nestId.getPath();
        return path.substring(path.lastIndexOf('.') + 1);
    }
}
