package net.sevenstars.middleearth.block.special.beds;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.sevenstars.middleearth.block.special.structureManager.features.NestLookup;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;
import net.minecraft.util.Identifier;
import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataPools;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Optional;
import java.util.TreeSet;

// Offsets from the bed head as if it pointed north, so they turn with the bed
// Empty job = the kind's default
// The manager offset is unused, kept so old templates load
public record BedTag(String nestKind, Optional<Identifier> job, Optional<Identifier> npcClass, List<BlockPos> links, boolean manual, Optional<BlockPos> manager,
                     Optional<Identifier> place, Optional<BlockPos> store) {
    public static final Codec<BedTag> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("type").forGetter(BedTag::nestKind),
            Identifier.CODEC.xmap(NpcJobs::current, job -> job).optionalFieldOf("role").forGetter(BedTag::job),
            Identifier.CODEC.optionalFieldOf("npc_class").forGetter(BedTag::npcClass),
            BlockPos.CODEC.listOf().optionalFieldOf("links", List.of()).forGetter(BedTag::links),
            Codec.BOOL.optionalFieldOf("manual", false).forGetter(BedTag::manual),
            BlockPos.CODEC.optionalFieldOf("manager").forGetter(BedTag::manager),
            Identifier.CODEC.optionalFieldOf("place").forGetter(BedTag::place),
            BlockPos.CODEC.optionalFieldOf("store").forGetter(BedTag::store)
    ).apply(instance, BedTag::new));

    private static List<String> nestKinds = null;
    private static Map<Identifier, Identifier> peopleNests = null;

    public enum Folk {
        COMMON,
        FIGHTERS,
        PRISONERS,
        MIXED
    }

    public static BlockPos placed(BlockPos bed, Direction facing, BlockPos offset) {
        return bed.add(offset.rotate(fromNorth(facing)));
    }

    public static BlockPos offsetTo(BlockPos bed, Direction facing, BlockPos target) {
        return target.subtract(bed).rotate(toNorth(facing));
    }

    public static Direction facingAt(BlockView world, BlockPos bed) {
        BlockState state = world.getBlockState(bed);
        return state.contains(BedBlock.FACING) ? state.get(BedBlock.FACING) : Direction.NORTH;
    }

    private static BlockRotation toNorth(Direction facing) {
        return switch (facing) {
            case EAST -> BlockRotation.COUNTERCLOCKWISE_90;
            case SOUTH -> BlockRotation.CLOCKWISE_180;
            case WEST -> BlockRotation.CLOCKWISE_90;
            default -> BlockRotation.NONE;
        };
    }

    private static BlockRotation fromNorth(Direction facing) {
        return switch (facing) {
            case EAST -> BlockRotation.CLOCKWISE_90;
            case SOUTH -> BlockRotation.CLOCKWISE_180;
            case WEST -> BlockRotation.COUNTERCLOCKWISE_90;
            default -> BlockRotation.NONE;
        };
    }

    public static Folk folkOf(DynamicRegistryManager registries, String nestKind) {
        if(NpcJobs.forNestKind(nestKind).filter(NpcJobs.PRISONER.getValue()::equals).isPresent())
            return Folk.PRISONERS;
        Registry<NpcClass> classes = registries.getOptional(DynamicRegistriesME.NPC_CLASS).orElse(null);
        boolean common = false;
        boolean fighters = false;
        for(StructureManagerData manager : NestLookup.places(registries)) {
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

    // The no-argument versions read the static pools (datagen, the report, the client)
    public static synchronized List<String> nestKinds() {
        if(nestKinds == null)
            nestKinds = nestKindsOf(StructureManagerDataPools.all());
        return nestKinds;
    }

    public static List<String> nestKinds(DynamicRegistryManager registries) {
        return nestKindsOf(NestLookup.places(registries));
    }

    public static synchronized Map<Identifier, Identifier> peopleNests() {
        if(peopleNests == null)
            peopleNests = peopleNestsOf(StructureManagerDataPools.all());
        return peopleNests;
    }

    public static Map<Identifier, Identifier> peopleNests(DynamicRegistryManager registries) {
        return peopleNestsOf(NestLookup.places(registries));
    }

    private static List<String> nestKindsOf(Iterable<StructureManagerData> places) {
        TreeSet<String> found = new TreeSet<>();
        for(StructureManagerData manager : places) {
            for(SpawnNestNodeData nest : manager.getNpcSpawnNest()) {
                if(nest.isPeople())
                    found.add(nestKindOf(nest.getId()));
            }
        }
        return List.copyOf(found);
    }

    private static Map<Identifier, Identifier> peopleNestsOf(Iterable<StructureManagerData> places) {
        Map<Identifier, Identifier> found = new TreeMap<>();
        for(StructureManagerData place : places) {
            for(SpawnNestNodeData nest : place.getNpcSpawnNest()) {
                if(nest.isPeople())
                    found.put(nest.getId(), place.getId());
            }
        }
        return Collections.unmodifiableMap(found);
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
