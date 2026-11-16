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
import net.sevenstars.middleearth.commands.custom.StructureEditSession;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.npc_jobs.NpcJob;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

// One choice for who lives in a bed, as offered to the builder: a villager, a baker, a soldier, a prisoner, for one place.
// Behind it are the place's nest that spawns that kind of person and the job they take, which the builder doesn't need
// to know about. The classes are who can spawn, shown before saving. An empty job means the class's own routine
public record BedOccupant(Group group, Identifier place, String nestKind, String job, List<Identifier> classes) {
    public enum Group {
        COMMON,
        FIGHTERS,
        OTHER
    }

    private static final int MAXIMUM_LENGTH = 128;
    private static final int MAXIMUM_CLASSES = 64;
    public static final int MAXIMUM_OCCUPANTS = 2048;
    // In blocks and in chunks, around a bed outside any structure being edited, to find what its neighbours are tagged with
    private static final int NEIGHBOUR_RANGE = 64;
    private static final int NEIGHBOUR_CHUNKS = 4;

    public static final PacketCodec<RegistryByteBuf, BedOccupant> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT.xmap(index -> Group.values()[Math.clamp(index, 0, Group.values().length - 1)], Group::ordinal), BedOccupant::group,
            Identifier.PACKET_CODEC, BedOccupant::place,
            PacketCodecs.string(MAXIMUM_LENGTH), BedOccupant::nestKind,
            PacketCodecs.string(MAXIMUM_LENGTH), BedOccupant::job,
            Identifier.PACKET_CODEC.collect(PacketCodecs.toList(MAXIMUM_CLASSES)), BedOccupant::classes,
            BedOccupant::new
    );

    // Everyone each place can house: the kinds it has a nest for, plus each trade its common folk can take
    public static List<BedOccupant> all(ServerWorld world) {
        Registry<NpcClass> classes = world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS);
        List<BedOccupant> occupants = new ArrayList<>();
        for(StructureManagerData place : world.getRegistryManager().getOrThrow(DynamicRegistriesME.STRUCTURE_MANAGER_DATA)) {
            Map<String, BedOccupant> found = new LinkedHashMap<>();
            BedOccupant villager = null;
            for(SpawnNestNodeData nest : place.getNpcSpawnNest()) {
                if(!nest.isPeople())
                    continue;
                List<Identifier> born = new ArrayList<>();
                // A village's common folk nest includes its militia too, and is still where its bakers and farmers come from
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
                BedOccupant occupant = new BedOccupant(group, place.getId(), nestKind, job == null ? "" : job.toString(), born);
                // One entry per job, taken from the first nest that gives it. A small cell and a large one both hold a
                // prisoner
                found.putIfAbsent(occupant.job().isEmpty() ? nestKind : occupant.job(), occupant);
                if(villager == null && group == Group.COMMON && job == null)
                    villager = occupant;
            }
            if(villager != null) {
                Set<String> offered = new LinkedHashSet<>();
                found.values().forEach(occupant -> offered.add(occupant.job()));
                for(var key : NpcJobs.all()) {
                    NpcJob job = NpcJobs.get(key.getValue());
                    // A trade with its own routine. Herders and travellers get theirs from the party they walk with
                    if(job == null || !job.replacesName() || job.routine().isEmpty() || job.routine().get().getEntries().isEmpty() || offered.contains(key.getValue().toString()))
                        continue;
                    found.put(key.getValue().toString(), new BedOccupant(Group.COMMON, place.getId(), villager.nestKind(), key.getValue().toString(), villager.classes()));
                }
            }
            occupants.addAll(found.values());
        }
        return occupants.size() > MAXIMUM_OCCUPANTS ? occupants.subList(0, MAXIMUM_OCCUPANTS) : occupants;
    }

    // The most likely place for a bed that doesn't have one yet: the place most of its neighbours have. The neighbours are
    // the beds of the structure the builder has loaded, or else the beds near it
    public static Optional<Identifier> likelyPlace(ServerWorld world, BlockPos bed, UUID builder) {
        StructureEditSession session = StructureEditSession.of(builder);
        boolean inSession = session != null && session.getDimension().equals(world.getRegistryKey())
                && session.getPieces().stream().anyMatch(piece -> piece.box().contains(bed));
        ChunkPos center = new ChunkPos(bed);
        int fromX = center.x - NEIGHBOUR_CHUNKS, toX = center.x + NEIGHBOUR_CHUNKS, fromZ = center.z - NEIGHBOUR_CHUNKS, toZ = center.z + NEIGHBOUR_CHUNKS;
        if(inSession) {
            for(StructureEditSession.Piece piece : session.getPieces()) {
                fromX = Math.min(fromX, piece.box().getMinX() >> 4);
                toX = Math.max(toX, piece.box().getMaxX() >> 4);
                fromZ = Math.min(fromZ, piece.box().getMinZ() >> 4);
                toZ = Math.max(toZ, piece.box().getMaxZ() >> 4);
            }
        }
        Map<Identifier, Integer> counts = new LinkedHashMap<>();
        for(int x = fromX; x <= toX; x++) {
            for(int z = fromZ; z <= toZ; z++) {
                if(!world.isChunkLoaded(x, z))
                    continue;
                for(BlockEntity blockEntity : world.getChunk(x, z).getBlockEntities().values()) {
                    BlockPos at = blockEntity.getPos();
                    boolean belongs = inSession ? session.getPieces().stream().anyMatch(piece -> piece.box().contains(at)) : at.isWithinDistance(bed, NEIGHBOUR_RANGE);
                    if(!belongs)
                        continue;
                    if(blockEntity instanceof BedTagHolder holder && holder.getBedTag() != null)
                        holder.getBedTag().place().ifPresent(place -> counts.merge(place, 1, Integer::sum));
                }
            }
        }
        Identifier most = null;
        for(Map.Entry<Identifier, Integer> count : counts.entrySet()) {
            if(most == null || count.getValue() > counts.get(most))
                most = count.getKey();
        }
        return Optional.ofNullable(most);
    }
}
