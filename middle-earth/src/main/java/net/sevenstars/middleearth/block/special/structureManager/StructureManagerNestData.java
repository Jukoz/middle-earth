package net.sevenstars.middleearth.block.special.structureManager;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.sevenstars.middleearth.resources.datas.structure_manager_datas.StructureSpawnNest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class StructureManagerNestData {
    public static final Codec<StructureManagerNestData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Identifier.CODEC.fieldOf("id").forGetter(StructureManagerNestData::getId),
            Codec.list(Codec.STRING, 0, 10).fieldOf("entity_uuid").forGetter(StructureManagerNestData::getEntityUuidStrings),
            Codec.LONG.fieldOf("respawn_event_trigger_tick").forGetter(StructureManagerNestData::getRespawnEventTriggerTick),
            Codec.INT.fieldOf("respawn_tick_delay").forGetter(StructureManagerNestData::getRespawnTickDelay)
    ).apply(instance, StructureManagerNestData::new));



    private Identifier id;
    private List<UUID> entityUuids;
    private long respawnEventTriggerTick;
    private int respawnTickDelay;

    private StructureManagerNestData(Identifier id, List<String> entityUuids, long respawnEventTriggerTick, int respawnTickDelay) {
        this.id = id;
        this.respawnEventTriggerTick = respawnEventTriggerTick;
        this.respawnTickDelay = respawnTickDelay;
        this.entityUuids = new ArrayList<>();
        for(var entityUuid : entityUuids){
            this.entityUuids.add(UUID.fromString(entityUuid));
        }
    }

    public Identifier getId() {
        return this.id;
    }
    public List<String> getEntityUuidStrings() {
        List<String> entityUuidStrings = new ArrayList<>();
        for(var entityUuid : this.entityUuids){
            entityUuidStrings.add(entityUuid.toString());
        }
        return entityUuidStrings;
    }
    private long getRespawnEventTriggerTick() {
        return this.respawnEventTriggerTick;
    }

    public int getRespawnTickDelay() {
        return this.respawnTickDelay;
    }

    public StructureManagerNestData(StructureSpawnNest structureSpawnNest) {
        this.entityUuids = new ArrayList<>();
        this.id = structureSpawnNest.getId();
        this.respawnTickDelay = structureSpawnNest.getRespawnTickDelay();
        this.respawnEventTriggerTick = 0;
    }

    public boolean addEntity(LivingEntity entity){
        boolean isSuccess = this.entityUuids.add(entity.getUuid());
        return isSuccess;
    }
    public boolean removeEntity(LivingEntity entity){
        boolean isSuccess = this.entityUuids.remove(entity.getUuid());
        if(isSuccess && this.entityUuids.isEmpty()){
            // Trigger smthing
            this.respawnEventTriggerTick = entity.getWorld().getTickOrder();
        }
        return isSuccess;
    }

    public boolean canRespawn(World world){
        return (entityUuids.isEmpty() && world.getTickOrder() > respawnEventTriggerTick + respawnTickDelay);
    }

    public List<LivingEntity> getAllEntities(World world){
        List<LivingEntity> livingEntities = new ArrayList<>();
        for(var entityUuid : this.entityUuids){
            if(world.getEntity(entityUuid) instanceof LivingEntity entity)
                livingEntities.add(entity);
        }
        return livingEntities;
    }
}

