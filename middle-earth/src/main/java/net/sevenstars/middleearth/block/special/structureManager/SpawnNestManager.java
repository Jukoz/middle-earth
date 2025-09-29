package net.sevenstars.middleearth.block.special.structureManager;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Uuids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.SpawnNestNodeData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureManagerData;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNestPool;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SpawnNestManager {
    public static final Codec<SpawnNestManager> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(SpawnNestManager::getId),
            Codec.list(Uuids.CODEC).fieldOf("entity_uuid").forGetter(SpawnNestManager::getEntityUuids),
            Codec.LONG.fieldOf("respawn_event_trigger_tick").forGetter(SpawnNestManager::getRespawnEventTriggerTick),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestManager::getRespawnTickDelay),
            BlockPos.CODEC.fieldOf("origin_pos").forGetter(SpawnNestManager::getOriginPos),
            Codec.INT.fieldOf("spawn_radius").forGetter(SpawnNestManager::getSpawnRadius)

    ).apply(instance, SpawnNestManager::new));

    private static final String ID = "spawn_nest_data";

    private Identifier id;
    private ArrayList<UUID> entities;
    private long respawnEventTriggerTick;
    private int respawnTickDelay;
    private BlockPos originPos;
    private int spawnRadius;

    public SpawnNestManager(Identifier dataId, List<UUID> dataEntities, long dataRespawnEventTriggerTick, int dataRespawnTickDelay, BlockPos position, int spawnRadius) {
        this.id = dataId;
        this.entities =  Lists.newArrayList();
        this.entities.addAll(dataEntities);
        this.respawnEventTriggerTick = dataRespawnEventTriggerTick;
        this.respawnTickDelay = dataRespawnTickDelay;
        this.originPos = position;
        this.spawnRadius = spawnRadius;
    }

    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData, BlockPos position, int spawnRadius) {
        this.entities = new ArrayList<UUID>();
        this.id = spawnNestNodeData.getId();
        this.respawnTickDelay = spawnNestNodeData.getRespawnTickDelay();
        this.respawnEventTriggerTick = 0;
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

    public void addEntity(LivingEntity entity){
        if(entity.getWorld().isClient)
            return;

        UUID uuid = entity.getUuid();
        if(this.entities == null)
            this.entities = new ArrayList<UUID>();

        this.entities.add(uuid);
    }

    public boolean removeEntity(LivingEntity entity){
        if(entity.getWorld().isClient || !this.entities.contains(entity.getUuid()))
            return false;
        this.entities.remove(entity.getUuid());
        if(this.entities.isEmpty()){
            beginRespawnSequence(entity.getWorld());
        }
        return true;
    }
    public boolean removeEntity(World world, UUID uuid){
        if(world.isClient || !this.entities.contains(uuid))
            return false;
        this.entities.remove(uuid);
        if(this.entities.isEmpty()){
            beginRespawnSequence(world);
        }
        return true;
    }

    private void beginRespawnSequence(World world) {
        this.respawnEventTriggerTick = world.getTime();
        MiddleEarth.LOGGER.logDebugMsg("Respawn Sequence begun::%s".formatted(this.respawnEventTriggerTick));
    }

    public boolean canRespawn(long time){
        return (entities.isEmpty() && time > respawnEventTriggerTick + respawnTickDelay);
    }

    public void tick(StructureManagerData structureManagerData, long currentTick, World world, BlockPos sourcePos) {
        if(canRespawn(currentTick)){
            MiddleEarth.LOGGER.logDebugMsg("Respawn All begun::[World]%s [RespawnDelay]%s [Trigger]%s [TimeOfDay]%s".formatted(currentTick, this.respawnTickDelay, this.respawnEventTriggerTick, world.getTimeOfDay() % 24000));
            respawnAll(structureManagerData, world, sourcePos);
        }
    }

    public void doWellnessCheck(StructureManagerData structureManagerData, World world, BlockPos sourcePos) {
        if(entities != null && !entities.isEmpty()){
            List<UUID> toRemove = new ArrayList<>();
            for (UUID uuid : entities){ // Wellness check
                var entity = world.getEntity(uuid);
                if(entity == null || !entity.isAlive())
                    toRemove.add(uuid);
            }
            for (UUID uuid : toRemove)
                removeEntity(world, uuid);
        }
    }


    private void respawnAll(StructureManagerData structureManagerData, World world, BlockPos sourcePos) {
        if(structureManagerData == null)
            return;

        SpawnNestNodeData data = structureManagerData.getNpcSpawnNest(id);

        if(data != null){
            StructureSpawnNestPool pool = data.getRandomPool();
            int entityAmountToSpawn = pool.getEntityAmount();
            for(int i = 0; i < entityAmountToSpawn; i ++){
                LivingEntity entityToAdd = StructureManagerService.SpawnEntity(world, pool, originPos, spawnRadius);
                if(entityToAdd instanceof NpcEntity npcEntity)
                    npcEntity.assignStructureManager((StructureManagerBlockEntity) world.getBlockEntity(sourcePos));
                if(entityToAdd != null)
                    addEntity(entityToAdd);
            }
            world.markDirty(sourcePos);
        }
        this.respawnEventTriggerTick = -1;
    }

    public boolean computeDeath(LivingEntity entity) {
        if (removeEntity(entity)) {
            return true;
        }
        return false;
    }

    public void forceRespawn(StructureManagerData structureManagerData, World world, BlockPos pos) {
        for(var uuid : getEntityUuids()){
            if(world.getEntity(uuid) instanceof LivingEntity livingEntity){
                livingEntity.setRemoved(Entity.RemovalReason.DISCARDED);
                MiddleEarth.LOGGER.logDebugMsg("Removed %s".formatted(uuid));
            }
        }
        entities = new ArrayList<UUID>();
        respawnAll(structureManagerData, world, pos);
    }
}

