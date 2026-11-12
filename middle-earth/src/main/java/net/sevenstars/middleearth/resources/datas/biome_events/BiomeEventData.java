package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.SpawnEventDataUtil;
import net.sevenstars.middleearth.resources.datas.biome_events.data.SpawnCensus;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BiomeEventData {
    public static class Fields {
        public static final String SPAWN_DEFAULT_WHEN_UNMET = "SpawnDefaultWhenUnmet";
        public static final String WILD_SPAWNS = "WildSpawns";
    }

    public static final Codec<BiomeEventData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf(Fields.SPAWN_DEFAULT_WHEN_UNMET).forGetter(BiomeEventData::getSpawnDefaultWhenUnmet),
            Codec.list(WildSpawnEventData.CODEC).fieldOf(Fields.WILD_SPAWNS).forGetter(BiomeEventData::getWildSpawnEventDatas)
    ).apply(instance, BiomeEventData::new));

    private Boolean shouldSpawnDefaultWhenUnmet;
    private List<WildSpawnEventData> wildSpawnEventDatas;

    private static HashMap<ChunkPos, Long> disabledChunkSpawningCache = new HashMap<>();

    // In ticks
    private static final int PENDING_SPAWN_LIFETIME = 40;
    private static final int MAXIMUM_PENDING_SPAWNS = 256;
    private static final Long2ObjectOpenHashMap<PendingSpawn> PENDING_SPAWNS = new Long2ObjectOpenHashMap<>();

    private record PendingSpawn(WildSpawnEventData data, long expires) {}

    public BiomeEventData(boolean shouldSpawnDefaultWhenUnmet, List<WildSpawnEventData> wildNpcs){
        this(wildNpcs);
        this.shouldSpawnDefaultWhenUnmet = shouldSpawnDefaultWhenUnmet;
    }

    public BiomeEventData withMoreWildSpawns(List<WildSpawnEventData> wildNpcs){

        List<WildSpawnEventData> newWildSpawns = new ArrayList<>();
        if(wildSpawnEventDatas != null)
            newWildSpawns.addAll(wildSpawnEventDatas);
        newWildSpawns.addAll(wildNpcs);
        this.wildSpawnEventDatas = newWildSpawns;
        return this;
    }



    private List<WildSpawnEventData> getWildSpawnEventDatas() {
        return this.wildSpawnEventDatas;
    }

    public BiomeEventData(List<WildSpawnEventData> wildNpcs){
        this.wildSpawnEventDatas = wildNpcs;
        this.shouldSpawnDefaultWhenUnmet = false;
    }

    public Boolean getSpawnDefaultWhenUnmet(){
        return shouldSpawnDefaultWhenUnmet;
    }

    public ContextualizedBiomeData findNpcData(World world, NpcEntity entity) {
        DynamicRegistryManager manager = world.getRegistryManager();
        // What the spawn check picked for this spot is what gets spawned, instead of rolling again and maybe coming up empty
        PendingSpawn pending = PENDING_SPAWNS.remove(entity.getBlockPos().asLong());
        if(pending != null && world.getTime() <= pending.expires() && wildSpawnEventDatas.contains(pending.data())) {
            Identifier pendingId = pending.data().getNpcType(null);
            NpcType pendingType = (pendingId != null) ? manager.getOrThrow(DynamicRegistriesME.NPC_TYPE).get(pendingId) : null;
            return new ContextualizedBiomeData(pendingType, pending.data().getParty());
        }

        List<WildSpawnEventData> weightedData = new ArrayList<>();

        for(WildSpawnEventData data : getWildSpawnEventDatas()){
            if(SpawnEventDataUtil.isConsideredForSpawning(data, Registries.ENTITY_TYPE.getId(EntitiesME.NPC), world, entity.getBlockPos()))
            {
                int weightAmount = data.getWeight(1);
                for(int i = 0; i < weightAmount; i ++){
                    weightedData.add(data);
                }
            }
        }
        if(weightedData.isEmpty())
            return null;

        WildSpawnEventData spawningData = weightedData.get(Random.create().nextInt(weightedData.size()));
        Registry<NpcType> npcDataRegistry = manager.getOrThrow(DynamicRegistriesME.NPC_TYPE);
        Identifier npcId = spawningData.getNpcType(null);
        NpcType foundNpcType = (npcId != null) ? npcDataRegistry.get(npcId) : null;

        return new ContextualizedBiomeData(foundNpcType, spawningData.getParty());
    }

    public boolean canSpawn(EntityType<?> type, ServerWorld world, BlockPos pos, Random random) {
        ChunkPos chunkPos = world.getChunk(pos).getPos();

        Long expiredTick = disabledChunkSpawningCache.get(chunkPos);

        if (expiredTick != null) {
            if(world.getTime() >= expiredTick)
                disabledChunkSpawningCache.remove(chunkPos);
            else
                return false;
        }

        List<WildSpawnEventData> weightedData = new ArrayList<>();
        boolean containEntityType = false;
        for(WildSpawnEventData data : getWildSpawnEventDatas()){
            Identifier entityId = Registries.ENTITY_TYPE.getId(type);
            if(!containEntityType && IdentifierUtil.compareId(data.getEntityType(), entityId))
                containEntityType = true;
            if(SpawnEventDataUtil.isConsideredForSpawning(data, entityId, world, pos))
            {
                int weightAmount = data.getWeight(1);
                for(int i = 0; i < weightAmount; i ++){
                    weightedData.add(data);
                }
            }
        }
        if(!containEntityType)
            return true;
        if(weightedData.isEmpty()){
            disableChunk(world, chunkPos);
            return false;
        }
        WildSpawnEventData spawningData = weightedData.get(Random.create().nextInt(weightedData.size()));
        if(spawningData.isDiscarded(random)){
            disableChunk(world, chunkPos);
            return false;
        }
        spawningData.broadcastMessage(world, pos);
        if(type == EntitiesME.NPC) {
            if(PENDING_SPAWNS.size() >= MAXIMUM_PENDING_SPAWNS)
                PENDING_SPAWNS.clear();
            PENDING_SPAWNS.put(pos.asLong(), new PendingSpawn(spawningData, world.getTime() + PENDING_SPAWN_LIFETIME));
        }
        SpawnCensus.expect(world, type, type == EntitiesME.NPC ? spawningData.getNpcType(null) : null, pos);
        return true;
    }

    private void disableChunk(ServerWorld world, ChunkPos chunkPos) {
        if(disabledChunkSpawningCache == null)
            disabledChunkSpawningCache = new HashMap<>();
        disabledChunkSpawningCache.remove(chunkPos);
        disabledChunkSpawningCache.put(chunkPos, world.getTime() + 20 * 5);
    }


    public record ContextualizedBiomeData(NpcType npcType, @Nullable PartyData party){

    }
}
