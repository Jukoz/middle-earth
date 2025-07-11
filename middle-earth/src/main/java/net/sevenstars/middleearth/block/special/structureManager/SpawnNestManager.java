package net.sevenstars.middleearth.block.special.structureManager;

import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
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
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(SpawnNestManager::getRespawnTickDelay)
    ).apply(instance, SpawnNestManager::new));

    private static final String ID = "spawn_nest_data";

    private Identifier id;
    private ArrayList<UUID> entities;
    private long respawnEventTriggerTick;
    private int respawnTickDelay;

    public SpawnNestManager(Identifier dataId, List<UUID> dataEntities, long dataRespawnEventTriggerTick, int dataRespawnTickDelay) {
        this.id = dataId;
        this.entities =  Lists.newArrayList();
        this.entities.addAll(dataEntities);
        this.respawnEventTriggerTick = dataRespawnEventTriggerTick;
        this.respawnTickDelay = dataRespawnTickDelay;
        MiddleEarth.LOGGER.logDebugMsg("SMND :: Created new Structure Manager Nest Data, %s with %s entities".formatted(id, entities.size()));
    }

    public SpawnNestManager(SpawnNestNodeData spawnNestNodeData) {
        this.entities = new ArrayList<UUID>();
        this.id = spawnNestNodeData.getId();
        this.respawnTickDelay = spawnNestNodeData.getRespawnTickDelay();
        this.respawnEventTriggerTick = 0;
        MiddleEarth.LOGGER.logDebugMsg("SMND :: Created a new Structure Manager based on a nest, %s with %s entities".formatted(id, entities.size()));
    }

    public Identifier getId() {
        return this.id;
    }

    public ArrayList<UUID> getEntityUuids() {
        MiddleEarth.LOGGER.logDebugMsg("SMND :: Getting all entity uuids : %s".formatted(entities.size()));
        return entities;
    }

    private long getRespawnEventTriggerTick() {
        return this.respawnEventTriggerTick;
    }

    public int getRespawnTickDelay() {
        return this.respawnTickDelay;
    }

    public void addEntity(LivingEntity entity){
        if(entity.getWorld().isClient)
            return;

        UUID uuid = entity.getUuid();
        MiddleEarth.LOGGER.logDebugMsg("SMND :: Added one entity : %s".formatted(uuid.toString()));
        if(this.entities == null)
            this.entities = new ArrayList<UUID>();

        this.entities.add(uuid);
    }

    public boolean removeEntity(LivingEntity entity){
        if(entity.getWorld().isClient || !this.entities.contains(entity.getUuid()))
            return false;
        this.entities.remove(entity.getUuid());
        MiddleEarth.LOGGER.logDebugMsg("SMND :: Removed one entity : %s. Now has %s entities.".formatted(entity.getUuid(), entities.size()));
        if(this.entities.isEmpty()){
            beginRespawnSequence(entity.getWorld());
        }
        return true;
    }

    private void beginRespawnSequence(World world) {
        // Trigger smthing
        this.respawnEventTriggerTick = world.getTickOrder();
    }

    public boolean canRespawn(World world){
        return (entities.isEmpty() && world.getTickOrder() > respawnEventTriggerTick + respawnTickDelay);
    }

    public void tick(StructureManagerData structureManagerData, long currentTick, World world, BlockPos sourcePos) {
        if(entities.isEmpty() && currentTick % 1 == 0){
            triggerRespawnAlert(structureManagerData, world, sourcePos);
        }
    }

    private void triggerRespawnAlert(StructureManagerData structureManagerData, World world, BlockPos sourcePos) {
        MiddleEarth.LOGGER.logDebugMsg("%s :: Respawn :: Respawning all entities for %s".formatted(ID, id));
        SpawnNestNodeData data = structureManagerData.getNpcSpawnNest(id);

        if(data != null){
            StructureSpawnNestPool pool = data.getRandomPool();
            int entityAmountToSpawn = pool.getEntityAmount();
            for(int i = 0; i < entityAmountToSpawn; i ++){
                NpcEntity entityToAdd = StructureManagerService.SpawnEntity(world, pool.getNpcIdentifier(), pool.getFactionIdentifier(), sourcePos.add(data.getBlockPosOffset()), data.getBedRadius());
                entityToAdd.setStructureManagerHost(sourcePos);
                addEntity(entityToAdd);
            }
        }
    }

    public boolean computeDeath(LivingEntity entity) {
        if (removeEntity(entity)) {
            MiddleEarth.LOGGER.logDebugMsg("%s :: Nest contains entity and will be removed.".formatted(ID));
            return true;
        }
        return false;
    }
}

