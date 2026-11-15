package net.sevenstars.middleearth.block.special.beds;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.sevenstars.middleearth.block.special.structureManager.StructureManagerBlockEntity;
import net.sevenstars.middleearth.block.special.structureManager.features.StructureManagerService;
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.registries.content.structuremanagerdatas.StructureManagerDataPools;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.npc_jobs.NpcJob;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

// One answer to who lives in a bed, as a builder is asked it: a villager, a baker, a soldier, a prisoner. Behind it are the
// nest of the place that spawns such a person and the job they take, which the builder does not have to know about.
// The classes are who may be born, to show before saving. An empty job is the day the class has by itself
public record BedOccupant(Group group, String nestKind, String job, List<Identifier> classes) {
    public enum Group {
        COMMON,
        FIGHTERS,
        OTHER
    }

    private static final int MAXIMUM_LENGTH = 128;
    private static final int MAXIMUM_CLASSES = 64;
    public static final int MAXIMUM_OCCUPANTS = 256;
    // In blocks and in chunks, around a bed that is in no structure being edited, to find the managers it could belong to
    private static final int MANAGER_RANGE = 64;
    private static final int MANAGER_CHUNKS = 4;

    public static final PacketCodec<RegistryByteBuf, BedOccupant> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT.xmap(index -> Group.values()[Math.clamp(index, 0, Group.values().length - 1)], Group::ordinal), BedOccupant::group,
            PacketCodecs.string(MAXIMUM_LENGTH), BedOccupant::nestKind,
            PacketCodecs.string(MAXIMUM_LENGTH), BedOccupant::job,
            Identifier.PACKET_CODEC.collect(PacketCodecs.toList(MAXIMUM_CLASSES)), BedOccupant::classes,
            BedOccupant::new
    );

    // Everybody the place this bed is in can house: those its managers have a nest for, and of its common folk each trade
    // they can take. The place is the structure the builder has loaded, or else what the managers standing near the bed
    // spawn, or with none of those every nest there is
    public static List<BedOccupant> forBed(ServerWorld world, BlockPos bed, UUID builder) {
        Registry<NpcClass> classes = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS);
        Map<String, BedOccupant> found = new LinkedHashMap<>();
        BedOccupant villager = null;
        for(StructureManagerData manager : managersFor(world, bed, builder)) {
            for(SpawnNestNodeData nest : manager.getNpcSpawnNest()) {
                if(!nest.isPeople())
                    continue;
                List<Identifier> born = new ArrayList<>();
                // A village's nest of common folk has its militia in it too, and is still where its bakers and farmers come from
                boolean common = false;
                for(StructureSpawnNestPool pool : nest.getNpcSpawnNestPool()) {
                    Identifier id = pool.getNpcIdentifier().orElse(null);
                    NpcClass npcClass = id == null ? null : classes.get(id);
                    if(npcClass == null || born.contains(id))
                        continue;
                    born.add(id);
                    common |= npcClass.isCivilian();
                }
                String nestKind = BedTag.nestKindOf(nest.getId());
                Identifier job = nest.getJob().orElse(null);
                Group group = NpcJobs.PRISONER.getValue().equals(job) ? Group.OTHER : common ? Group.COMMON : Group.FIGHTERS;
                BedOccupant occupant = new BedOccupant(group, nestKind, job == null ? "" : job.toString(), born);
                // One answer for each job, whichever of the nests that give it comes first: a small cell and a large one both
                // hold a prisoner
                found.putIfAbsent(occupant.job().isEmpty() ? nestKind : occupant.job(), occupant);
                if(villager == null && group == Group.COMMON && job == null)
                    villager = occupant;
            }
        }
        if(villager != null) {
            Set<String> offered = new LinkedHashSet<>();
            found.values().forEach(occupant -> offered.add(occupant.job()));
            for(var key : NpcJobs.all()) {
                NpcJob job = NpcJobs.get(key.getValue());
                // A trade with a day of its own. Herders and travellers get theirs from the party they walk with
                if(job == null || !job.replacesName() || job.routine().isEmpty() || job.routine().get().getEntries().isEmpty() || offered.contains(key.getValue().toString()))
                    continue;
                found.put(key.getValue().toString(), new BedOccupant(Group.COMMON, villager.nestKind(), key.getValue().toString(), villager.classes()));
            }
        }
        List<BedOccupant> occupants = new ArrayList<>(found.values());
        return occupants.size() > MAXIMUM_OCCUPANTS ? occupants.subList(0, MAXIMUM_OCCUPANTS) : occupants;
    }

    private static List<StructureManagerData> managersFor(ServerWorld world, BlockPos bed, UUID builder) {
        Set<Identifier> ids = new LinkedHashSet<>();
        StructureEditSession session = StructureEditSession.of(builder);
        boolean inSession = session != null && session.getDimension().equals(world.getRegistryKey())
                && session.getPieces().stream().anyMatch(piece -> piece.box().contains(bed));
        if(inSession) {
            Registry<StructureFeatureData> features = world.getRegistryManager().getOptional(DynamicRegistriesME.STRUCTURE_FEATURES).orElse(null);
            for(StructureEditSession.Piece piece : session.getPieces()) {
                StructureFeatureData data = features == null ? null : features.get(piece.template());
                if(data != null)
                    data.managers().forEach(manager -> ids.add(manager.data()));
            }
        }
        // Also the managers that stand in the world, which the data does not know of until it is generated again: those in the
        // pieces of the structure, or with no structure loaded those near the bed
        ChunkPos center = new ChunkPos(bed);
        int fromX = center.x - MANAGER_CHUNKS, toX = center.x + MANAGER_CHUNKS, fromZ = center.z - MANAGER_CHUNKS, toZ = center.z + MANAGER_CHUNKS;
        if(inSession) {
            for(StructureEditSession.Piece piece : session.getPieces()) {
                fromX = Math.min(fromX, piece.box().getMinX() >> 4);
                toX = Math.max(toX, piece.box().getMaxX() >> 4);
                fromZ = Math.min(fromZ, piece.box().getMinZ() >> 4);
                toZ = Math.max(toZ, piece.box().getMaxZ() >> 4);
            }
        }
        for(int x = fromX; x <= toX; x++) {
            for(int z = fromZ; z <= toZ; z++) {
                if(!world.isChunkLoaded(x, z))
                    continue;
                for(BlockEntity blockEntity : world.getChunk(x, z).getBlockEntities().values()) {
                    if(!(blockEntity instanceof StructureManagerBlockEntity manager) || manager.getStructureManagerId() == null)
                        continue;
                    BlockPos at = manager.getPos();
                    boolean belongs = inSession ? session.getPieces().stream().anyMatch(piece -> piece.box().contains(at)) : at.isWithinDistance(bed, MANAGER_RANGE);
                    if(belongs)
                        ids.add(manager.getStructureManagerId());
                }
            }
        }
        List<StructureManagerData> managers = new ArrayList<>();
        for(Identifier id : ids) {
            StructureManagerData data = StructureManagerService.getStructureManagerData(world, id);
            if(data != null)
                managers.add(data);
        }
        return managers.isEmpty() ? StructureManagerDataPools.all() : managers;
    }
}
