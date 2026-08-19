package net.sevenstars.middleearth.block.special.structureManager.features;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

public class SpawnNestManager {
    public static final Codec<SpawnNestManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            ResourceLocation.CODEC.fieldOf("id").forGetter(SpawnNestManager::getId),
            Codec.list(UUIDUtil.AUTHLIB_CODEC).fieldOf("entity_uuid").forGetter(SpawnNestManager::getEntityUuids),
            Codec.LONG.fieldOf("respawn_event_trigger_tick").forGetter(SpawnNestManager::getRespawnEventTriggerTick),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestManager::getRespawnTickDelay),
            BlockPos.CODEC.fieldOf("origin_pos").forGetter(SpawnNestManager::getOriginPos),
            Codec.INT.fieldOf("spawn_radius").forGetter(SpawnNestManager::getSpawnRadius),
            Codec.list(TrackedEntity.CODEC).optionalFieldOf("last_known_positions", List.of())
                    .forGetter(SpawnNestManager::getTrackedEntities)
    ).apply(instance, SpawnNestManager::new));

    private static final String ID = "spawn_nest_data";

    private ResourceLocation id;
    private ArrayList<UUID> entities;
    private long respawnEventTriggerTick;
    private int respawnTickDelay;
    private BlockPos originPos;
    private int spawnRadius;
    private final Map<UUID, BlockPos> lastKnownPositions;

    private List<BedBlock> beds = new ArrayList<>();

    public SpawnNestManager(
            ResourceLocation dataId,
            List<UUID> dataEntities,
            long dataRespawnEventTriggerTick,
            int dataRespawnTickDelay,
            BlockPos position,
            int spawnRadius,
            List<TrackedEntity> trackedEntities
    ) {
        this.id = dataId;
        this.entities = Lists.newArrayList();
        this.entities.addAll(dataEntities);
        this.respawnEventTriggerTick = dataRespawnEventTriggerTick;
        this.respawnTickDelay = dataRespawnTickDelay;
        this.originPos = position;
        this.spawnRadius = spawnRadius;
        this.lastKnownPositions = new HashMap<>();
        for (TrackedEntity trackedEntity : trackedEntities) {
            if (this.entities.contains(trackedEntity.uuid())) {
                this.lastKnownPositions.put(
                        trackedEntity.uuid(), trackedEntity.pos().immutable());
            }
        }
    }

    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, BlockPos position, int spawnRadius) {
        this.entities = new ArrayList<UUID>();
        this.id = spawnNestNodeData.getId();
        this.respawnTickDelay = spawnNestNodeData.getRespawnTickDelay();
        this.respawnEventTriggerTick = -((long) this.respawnTickDelay) - 1L;
        this.originPos = position;
        this.spawnRadius = spawnRadius;
        this.lastKnownPositions = new HashMap<>();
    }

    public ResourceLocation getId() {
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

    private List<TrackedEntity> getTrackedEntities() {
        ArrayList<TrackedEntity> trackedEntities = new ArrayList<>(lastKnownPositions.size());
        for (Map.Entry<UUID, BlockPos> entry : lastKnownPositions.entrySet()) {
            trackedEntities.add(new TrackedEntity(entry.getKey(), entry.getValue()));
        }
        return trackedEntities;
    }

    public void addEntity(LivingEntity entity){
        if(entity.level().isClientSide)
            return;

        UUID uuid = entity.getUUID();
        if(this.entities == null)
            this.entities = new ArrayList<UUID>();

        if (!this.entities.contains(uuid)) {
            this.entities.add(uuid);
        }
        this.lastKnownPositions.put(uuid, entity.blockPosition().immutable());
    }

    public boolean removeEntity(LivingEntity entity){
        if(entity.level().isClientSide || !this.entities.contains(entity.getUUID()))
            return false;
        this.entities.remove(entity.getUUID());
        this.lastKnownPositions.remove(entity.getUUID());
        if(this.entities.isEmpty()){
            beginRespawnSequence(entity.level());
        }
        return true;
    }
    public boolean removeEntity(Level world, UUID uuid){
        return removeEntity(world, uuid, true);
    }

    private boolean removeEntity(Level world, UUID uuid, boolean forgetTracking){
        if(world.isClientSide || !this.entities.contains(uuid))
            return false;
        this.entities.remove(uuid);
        this.lastKnownPositions.remove(uuid);
        if (forgetTracking && world instanceof ServerLevel serverLevel) {
            StructureManagerService.forgetManagedEntity(serverLevel, uuid);
        }
        if(this.entities.isEmpty()){
            beginRespawnSequence(world);
        }
        return true;
    }

    private void beginRespawnSequence(Level world) {
        this.respawnEventTriggerTick = world.getGameTime();
    }

    public boolean canRespawn(long time){
        return (entities.isEmpty() && time > respawnEventTriggerTick + respawnTickDelay);
    }

    public void tick(StructureManagerData structureManagerData, long currentTick, ServerLevel world, BlockPos sourcePos) {
        if(canRespawn(currentTick)){
            respawnAll(structureManagerData, world, sourcePos);
        }
    }

    public boolean doWellnessCheck(StructureManagerData structureManagerData, ServerLevel world, BlockPos sourcePos) {
        boolean changed = false;
        if(entities != null && !entities.isEmpty()){
            List<UUID> toRemove = new ArrayList<>();
            List<UUID> legacyUnknown = new ArrayList<>();
            for (UUID uuid : entities){ // Wellness check
                StructureManagedEntityData.Entry tracked =
                        StructureManagedEntityData.get(world).get(uuid);
                if (tracked != null && tracked.dead()) {
                    toRemove.add(uuid);
                    continue;
                }
                var entity = world.getEntity(uuid);
                if (entity instanceof LivingEntity livingEntity && livingEntity.isAlive()) {
                    BlockPos currentPos = livingEntity.blockPosition().immutable();
                    if (!currentPos.equals(lastKnownPositions.put(uuid, currentPos))) {
                        changed = true;
                    }
                    StructureManagedEntityData.get(world).track(
                            uuid, sourcePos, currentPos);
                } else if (entity != null) {
                    toRemove.add(uuid);
                } else {
                    BlockPos lastKnownPos = tracked != null
                            ? tracked.lastPos()
                            : lastKnownPositions.get(uuid);
                    if (lastKnownPos == null) {
                        StructureManagedEntityData.get(world).markDead(
                                uuid, sourcePos, originPos);
                        legacyUnknown.add(uuid);
                    } else if (world.areEntitiesLoaded(ChunkPos.asLong(lastKnownPos))) {
                        toRemove.add(uuid);
                    }
                }
            }
            for (UUID uuid : toRemove) {
                changed |= removeEntity(world, uuid);
            }
            for (UUID uuid : legacyUnknown) {
                changed |= removeEntity(world, uuid, false);
            }
        }
        return changed;
    }


    private void respawnAll(StructureManagerData structureManagerData, ServerLevel world, BlockPos structureManagerPos) {
        if(structureManagerData == null)
            return;
        SpawnNestNodeData data = structureManagerData.getNpcSpawnNest(id);

        if(data != null){
            StructureSpawnNestPool pool = data.getRandomPool(world.getRandom());
            if (pool == null) {
                this.respawnEventTriggerTick = world.getGameTime();
                return;
            }
            int entityAmountToSpawn = pool.getEntityAmount(world.getRandom());
            for(int i = 0; i < entityAmountToSpawn; i ++){
                LivingEntity entityToAdd = StructureManagerService.spawnEntity(
                        world, pool, originPos, spawnRadius, structureManagerPos);
                if(entityToAdd == null) {
                    continue;
                }
                addEntity(entityToAdd);
            }
            world.blockEntityChanged(structureManagerPos);
        }
        this.respawnEventTriggerTick = entities.isEmpty() ? world.getGameTime() : -1L;
    }

    public boolean computeDeath(LivingEntity entity) {
        if (removeEntity(entity)) {
            return true;
        }
        return false;
    }

    public void forceRespawn(StructureManagerData structureManagerData, ServerLevel world, BlockPos structureManagerPos) {
        for(var uuid : List.copyOf(getEntityUuids())){
            if(world.getEntity(uuid) instanceof LivingEntity livingEntity){
                livingEntity.setRemoved(Entity.RemovalReason.DISCARDED);
                MiddleEarth.LOGGER.logDebugMsg("Removed %s".formatted(uuid));
            } else {
                BlockPos lastKnownPos = lastKnownPositions.getOrDefault(uuid, originPos);
                StructureManagedEntityData.get(world).markDead(
                        uuid, structureManagerPos, lastKnownPos);
            }
        }
        entities = new ArrayList<UUID>();
        lastKnownPositions.clear();

        refreshBeds(structureManagerData, world);
        respawnAll(structureManagerData, world, structureManagerPos);
    }

    public void refreshBeds(StructureManagerData structureManagerData, Level world){
        // TODO : Connect with the @StructureManagerBlockEntity.fetchBeds() / Redistribute

        BlockPos origin = getOriginPos();
        int bedRadius = 10;
        List<BlockPos> bedBlockPositions = new ArrayList<>();
        BlockPos.findClosestMatch(origin, bedRadius, 5, new Predicate<BlockPos>() {
            @Override
            public boolean test(BlockPos blockPos) {
                var blockState = world.getBlockState(blockPos);
                if(blockState.getBlock() instanceof BedBlock bedBlock){
                    if(BedBlock.getBlockType(blockState) == DoubleBlockCombiner.BlockType.FIRST){
                        bedBlockPositions.add(blockPos);
                    }
                }
                return false;
            }
        });
    }

    private record TrackedEntity(UUID uuid, BlockPos pos) {
        private static final Codec<TrackedEntity> CODEC = RecordCodecBuilder.create(instance ->
                instance.group(
                        UUIDUtil.AUTHLIB_CODEC.fieldOf("uuid").forGetter(TrackedEntity::uuid),
                        BlockPos.CODEC.fieldOf("pos").forGetter(TrackedEntity::pos)
                ).apply(instance, TrackedEntity::new));
    }
}

