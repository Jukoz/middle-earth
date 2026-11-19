package net.sevenstars.middleearth.block.special.structureManager.features;

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
import java.util.Collections;
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
            BlockPos.CODEC.optionalFieldOf("bed").forGetter(manager -> manager.household.map(Household::bed)),
            Identifier.CODEC.optionalFieldOf("bed_role").forGetter(manager -> manager.household.flatMap(Household::job)),
            Identifier.CODEC.optionalFieldOf("bed_class").forGetter(manager -> manager.household.flatMap(Household::npcClass)),
            BlockPos.CODEC.optionalFieldOf("store").forGetter(manager -> manager.household.flatMap(Household::store)),
            BlockPos.CODEC.listOf().optionalFieldOf("links", List.of()).forGetter(manager -> manager.household.map(Household::links).orElse(List.of())),
            Codec.INT.optionalFieldOf("capacity", 0).forGetter(manager -> manager.capacity)
    ).apply(instance, SpawnNestManager::load));

    private static final int BED_SPAWN_RADIUS = 2;
    private static final int GUARD_COUNT_MARGIN = 48;

    // Single-person nest: the bed (the nest's origin), its job and its store. Links are the places
    // the builder tied the bed to; empty means the npc finds its own work
    public record Household(BlockPos bed, Optional<Identifier> job, Optional<Identifier> npcClass, Optional<BlockPos> store, List<BlockPos> links) {
        public Household {
            links = List.copyOf(links);
        }
    }

    private final Identifier id;
    private final List<UUID> entities = new ArrayList<>();
    private long respawnEventTriggerTick;
    private final int respawnTickDelay;
    private final BlockPos originPos;
    private final int spawnRadius;
    private final Optional<Household> household;
    // Above 0 for a nest with a set number of people: one per tagged bed, or a spawn_npc marker's count.
    // 0 means the pool picks the number
    private final int capacity;

    private SpawnNestManager(Identifier id, List<UUID> entities, long respawnEventTriggerTick, int respawnTickDelay, BlockPos originPos, int spawnRadius, Optional<Household> household, int capacity) {
        this.id = id;
        this.entities.addAll(entities);
        this.respawnEventTriggerTick = respawnEventTriggerTick;
        this.respawnTickDelay = respawnTickDelay;
        this.originPos = originPos;
        this.spawnRadius = spawnRadius;
        this.household = household;
        this.capacity = capacity;
    }

    private static SpawnNestManager load(Identifier id, List<UUID> entities, long respawnEventTriggerTick, int respawnTickDelay, BlockPos originPos, int spawnRadius, Optional<BlockPos> bed, Optional<Identifier> bedJob, Optional<Identifier> bedClass, Optional<BlockPos> store, List<BlockPos> links, int capacity) {
        Optional<Household> household = bed.map(pos -> new Household(pos, bedJob, bedClass, store, links));
        return new SpawnNestManager(id, entities, respawnEventTriggerTick, respawnTickDelay, originPos, spawnRadius, household, capacity);
    }

    // The delay only applies to respawns. A new settlement fills at once
    private static long freshTriggerTick(SpawnNestNodeData data) {
        return -data.getRespawnTickDelay();
    }

    // A marker's nest
    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, BlockPos position, int spawnRadius, int capacity) {
        this(spawnNestNodeData.getId(), List.of(), freshTriggerTick(spawnNestNodeData), spawnNestNodeData.getRespawnTickDelay(), position, spawnRadius, Optional.empty(), capacity);
    }

    // A bed's nest
    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, StructureFeatureData.Bed tagged) {
        this(spawnNestNodeData.getId(), List.of(), freshTriggerTick(spawnNestNodeData), spawnNestNodeData.getRespawnTickDelay(), tagged.pos(), BED_SPAWN_RADIUS,
                Optional.of(new Household(tagged.pos(), tagged.tag().flatMap(BedTag::job), tagged.tag().flatMap(BedTag::npcClass), tagged.store(),
                        tagged.manual() ? tagged.links() : List.of())), 1);
    }

    public Identifier getId() {
        return this.id;
    }

    public List<UUID> getEntityUuids() {
        return Collections.unmodifiableList(entities);
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

    public boolean hasFixedCapacity() {
        return capacity > 0;
    }

    public Optional<Identifier> getHouseholdJob() {
        return household.flatMap(Household::job);
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
        SpawnNestNodeData data = NestLookup.findNest(world, id);

        if(data != null){
            StructureSpawnNestPool pool = data.getRandomPool(world.getRandom());
            BlockPos bedside = null;
            if(household.isPresent()) {
                BlockPos bed = household.get().bed();
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
            int entityAmountToSpawn = hasFixedCapacity() ? capacity : pool.getEntityAmount(world.getRandom());
            for(int i = 0; i < entityAmountToSpawn; i ++){
                LivingEntity entityToAdd = spawnResident(world, pool, bedside);
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

    private @Nullable LivingEntity spawnResident(ServerWorld world, StructureSpawnNestPool pool, @Nullable BlockPos bedside) {
        Optional<Identifier> bedClass = household.flatMap(Household::npcClass);
        if(bedClass.isPresent())
            return NestSpawner.spawnNpcAt(world, bedClass.get(), bedside != null ? bedside : originPos);
        if(bedside != null)
            return NestSpawner.spawnEntityAt(world, pool, bedside);
        return NestSpawner.spawnEntity(world, pool, originPos, spawnRadius);
    }

    private void settle(NpcEntity npc, SpawnNestNodeData data, ServerWorld world, BlockPos structureManagerPos, @Nullable RoutineSite site, @Nullable BlockPos bedside) {
        Optional<Identifier> bedJob = getHouseholdJob();
        Optional<Identifier> job = bedJob.isPresent() ? bedJob : data.getJob();
        if(job.isEmpty() && (NpcJobs.isGuardNest(BedTag.nestKindOf(id)) || mansPosts(site, npc)))
            job = guardDuty(world, structureManagerPos, site);
        job.ifPresent(npc::takeJob);
        npc.assignHost(structureManagerPos);
        if(npc.isPrisoner()) {
            PrisonCell cell = site == null ? null : site.findCell(npc.getBlockPos());
            npc.setPrisonCell(cell != null ? cell : PrisonCell.around(world, npc.getBlockPos()));
        }
        if(household.isPresent())
            npc.setHousehold(household.get().bed(), bedside, household.get().store().orElse(null), household.get().links());
        else if(hasFixedCapacity())
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
