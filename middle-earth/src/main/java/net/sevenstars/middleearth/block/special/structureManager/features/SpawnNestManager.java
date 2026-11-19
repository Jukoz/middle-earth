package net.sevenstars.middleearth.block.special.structureManager.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BedBlock;
import org.jetbrains.annotations.Nullable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.sevenstars.middleearth.block.special.beds.BedTag;
import net.sevenstars.middleearth.features.npcs.NpcEntity;
import net.sevenstars.middleearth.block.special.structureManager.marker.MarkerKind;
import net.sevenstars.middleearth.features.npcs.routine.PrisonCell;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSite;
import net.sevenstars.middleearth.features.npcs.routine.RoutineSites;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.features.npcs.classes.NpcClass;
import net.sevenstars.middleearth.resources.datas.structure_features.StructureFeatureData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SpawnNestManager {
    public static final Codec<SpawnNestManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(SpawnNestManager::getId),
            Codec.list(Uuids.CODEC).fieldOf("entity_uuid").forGetter(SpawnNestManager::getEntityUuids),
            Codec.LONG.fieldOf("respawn_event_trigger_tick").forGetter(SpawnNestManager::getRespawnEventTriggerTick),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestManager::getRespawnTickDelay),
            BlockPos.CODEC.fieldOf("origin_pos").forGetter(SpawnNestManager::getOriginPos),
            Codec.INT.fieldOf("spawn_radius").forGetter(SpawnNestManager::getSpawnRadius),
            BlockPos.CODEC.optionalFieldOf("bed").forGetter(manager -> Optional.ofNullable(manager.bed)),
            Identifier.CODEC.optionalFieldOf("bed_role").forGetter(manager -> manager.bedJob),
            Identifier.CODEC.optionalFieldOf("bed_class").forGetter(manager -> manager.bedClass),
            BlockPos.CODEC.optionalFieldOf("store").forGetter(manager -> Optional.ofNullable(manager.store)),
            BlockPos.CODEC.listOf().optionalFieldOf("links", List.of()).forGetter(manager -> manager.links),
            Codec.INT.optionalFieldOf("capacity", 0).forGetter(manager -> manager.capacity)
    ).apply(instance, SpawnNestManager::new));

    private static final int BED_SPAWN_RADIUS = 2;
    private static final int GUARD_COUNT_MARGIN = 48;

    private Identifier id;
    private ArrayList<UUID> entities;
    private long respawnEventTriggerTick;
    private int respawnTickDelay;
    private BlockPos originPos;
    private int spawnRadius;

    // Single-person nest: the bed (the nest's origin), its job and its store
    private @Nullable BlockPos bed = null;
    private Optional<Identifier> bedJob = Optional.empty();
    private Optional<Identifier> bedClass = Optional.empty();
    private @Nullable BlockPos store = null;
    // Places the builder tied the bed to. Empty means the npc finds its own work
    private List<BlockPos> links = List.of();
    // Above 0 for a nest with a set number of people: one per tagged bed, or a spawn_npc marker's count
    private int capacity = 0;

    public SpawnNestManager(Identifier dataId, List<UUID> dataEntities, long dataRespawnEventTriggerTick, int dataRespawnTickDelay, BlockPos position, int spawnRadius, Optional<BlockPos> bed, Optional<Identifier> bedJob, Optional<Identifier> bedClass, Optional<BlockPos> store, List<BlockPos> links, int capacity) {
        this.bedClass = bedClass;
        this.capacity = capacity;
        this.bed = bed.orElse(null);
        this.bedJob = bedJob;
        this.store = store.orElse(null);
        this.links = List.copyOf(links);
        this.id = dataId;
        this.entities =  Lists.newArrayList();
        this.entities.addAll(dataEntities);
        this.respawnEventTriggerTick = dataRespawnEventTriggerTick;
        this.respawnTickDelay = dataRespawnTickDelay;
        this.originPos = position;
        this.spawnRadius = spawnRadius;
    }

    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, BlockPos position, int spawnRadius, int capacity) {
        this(spawnNestNodeData, position, spawnRadius);
        this.capacity = capacity;
    }

    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, StructureFeatureData.Bed tagged) {
        this(spawnNestNodeData, tagged.pos(), BED_SPAWN_RADIUS, 1);
        this.bed = tagged.pos();
        this.bedJob = tagged.tag().flatMap(BedTag::job);
        this.bedClass = tagged.tag().flatMap(BedTag::npcClass);
        this.store = tagged.store().orElse(null);
        this.links = tagged.manual() ? tagged.links() : List.of();
    }

    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, BlockPos position, int spawnRadius) {
        this.entities = new ArrayList<>();
        this.id = spawnNestNodeData.getId();
        this.respawnTickDelay = spawnNestNodeData.getRespawnTickDelay();
        // The delay only applies to respawns. A new settlement fills at once
        this.respawnEventTriggerTick = -spawnNestNodeData.getRespawnTickDelay();
        this.originPos = position;
        this.spawnRadius = spawnRadius;
    }

    public Identifier getId() {
        return this.id;
    }

    public ArrayList<UUID> getEntityUuids() {
        return entities;
    }

    private long getRespawnEventTriggerTick() {
        return this.respawnEventTriggerTick;
    }

    public int getRespawnTickDelay() {
        return this.respawnTickDelay;
    }
    public BlockPos getOriginPos() {
        return this.originPos;
    }
    public int getSpawnRadius() {
        return this.spawnRadius;
    }

    public boolean isMarked() {
        return capacity > 0;
    }

    public Optional<Identifier> getBedJob() {
        return bedJob;
    }

    public void addEntity(LivingEntity entity){
        if(entity.getEntityWorld().isClient())
            return;

        this.entities.add(entity.getUuid());
    }

    public boolean removeEntity(LivingEntity entity){
        if(entity.getEntityWorld().isClient())
            return false;
        return removeUuid(entity.getUuid(), entity.getEntityWorld());
    }

    private boolean removeUuid(UUID uuid, World world){
        if(!this.entities.remove(uuid))
            return false;
        if(this.entities.isEmpty()){
            beginRespawnSequence(world);
        }
        return true;
    }

    // A previous nest's people still live here
    public void adopt(List<UUID> uuids){
        for(UUID uuid : uuids){
            if(!entities.contains(uuid))
                entities.add(uuid);
        }
    }
    private void beginRespawnSequence(World world) {
        this.respawnEventTriggerTick = world.getTime();
    }

    public boolean canRespawn(long time){
        return (entities.isEmpty() && time > respawnEventTriggerTick + respawnTickDelay);
    }

    public void tick(long currentTick, ServerWorld world, BlockPos sourcePos) {
        boolean left = false;
        for(UUID uuid : GoneSignals.take(world, sourcePos))
            left |= removeUuid(uuid, world);
        if(left)
            world.markDirty(sourcePos);
        if(canRespawn(currentTick)){
            respawnAll(world, sourcePos);
        }
    }

    private void respawnAll(ServerWorld world, BlockPos structureManagerPos) {
        SpawnNestNodeData data = StructureManagerService.findNest(world, id);

        if(data != null){
            StructureSpawnNestPool pool = data.getRandomPool();
            BlockPos bedside = null;
            if(bed != null) {
                if(!world.isChunkLoaded(bed.getX() >> 4, bed.getZ() >> 4))
                    return;
                if(!(world.getBlockState(bed).getBlock() instanceof BedBlock)) {
                    // A broken bed houses nobody. Check again after the usual wait
                    this.respawnEventTriggerTick = world.getTime();
                    return;
                }
                bedside = NestSurvey.standFor(world, bed);
            }
            RoutineSite site = RoutineSites.forManager(world, structureManagerPos);
            int entityAmountToSpawn = isMarked() ? capacity : pool.getEntityAmount();
            for(int i = 0; i < entityAmountToSpawn; i ++){
                LivingEntity entityToAdd = bedClass.isPresent() ? StructureManagerService.spawnNpcAt(world, bedClass.get(), bedside != null ? bedside : originPos)
                        : bedside != null ? StructureManagerService.spawnEntityAt(world, pool, bedside)
                        : StructureManagerService.spawnEntity(world, pool, originPos, spawnRadius);
                if(entityToAdd instanceof NpcEntity npcEntity)
                    settle(npcEntity, data, world, structureManagerPos, site, bedside);
                if(entityToAdd != null)
                    addEntity(entityToAdd);
            }
            world.markDirty(structureManagerPos);
        }
        if(entities.isEmpty())
            this.respawnEventTriggerTick = world.getTime();
    }

    private void settle(NpcEntity npc, SpawnNestNodeData data, ServerWorld world, BlockPos structureManagerPos, @Nullable RoutineSite site, @Nullable BlockPos bedside) {
        Optional<Identifier> job = bed != null && bedJob.isPresent() ? bedJob : data.getJob();
        if(job.isEmpty() && (NpcJobs.isGuardNest(BedTag.nestKindOf(id)) || mansPosts(site, npc)))
            job = guardDuty(world, structureManagerPos, site);
        job.ifPresent(npc::takeJob);
        npc.assignHost(structureManagerPos);
        if(npc.isPrisoner()) {
            PrisonCell cell = site == null ? null : site.findCell(npc.getBlockPos());
            npc.setPrisonCell(cell != null ? cell : PrisonCell.around(world, npc.getBlockPos()));
        }
        if(bed != null)
            npc.setHousehold(bed, bedside, store, links);
        else if(isMarked())
            npc.setHousehold(null, null, null, List.of());
    }

    private Optional<Identifier> guardDuty(ServerWorld world, BlockPos structureManagerPos, @Nullable RoutineSite site) {
        if(site == null)
            return Optional.of(NpcJobs.PATROL.getValue());
        int reach = site.getExtent() + GUARD_COUNT_MARGIN;
        Box area = new Box(site.getCenterX() - reach, world.getBottomY(), site.getCenterZ() - reach, site.getCenterX() + reach, world.getTopYInclusive(), site.getCenterZ() + reach);
        // Guards can spawn before their lord, who is counted anyway
        boolean lordExpected = site.getBeds().stream().anyMatch(found -> NpcJobs.isLord(found.tag().flatMap(BedTag::job).orElse(null)));
        return Optional.of(NpcJobs.guardDuty(site, world.getEntitiesByClass(NpcEntity.class, area, NpcEntity::isAlive), lordExpected));
    }

    // A fighter stands guard where the build marks posts or lookouts, like a watch tower. Elsewhere it keeps its kind's routine
    private boolean mansPosts(@Nullable RoutineSite site, NpcEntity npc) {
        NpcClass npcClass = npc.getNpcClass();
        if(npcClass == null || npcClass.isCivilian())
            return false;
        return site != null && (!site.getMarkers(MarkerKind.ENTRANCE).isEmpty() || !site.getMarkers(MarkerKind.GUARD_POST).isEmpty()
                || !site.getMarkers(MarkerKind.LOOKOUT).isEmpty());
    }
}
