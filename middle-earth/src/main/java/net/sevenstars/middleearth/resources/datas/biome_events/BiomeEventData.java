package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sevenstars.api.utils.IdentifierUtil;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.SpawnEventDataUtil;
import net.sevenstars.middleearth.resources.datas.biome_events.data.SpawnCensus;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;
import net.sevenstars.middleearth.resources.datas.npc_classes.NpcClass;

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

    // In ticks
    private static final int PENDING_SPAWN_LIFETIME = 40;
    private static final int CHUNK_DISABLE_TICKS = 20 * 5;

    private static final int MAXIMUM_PENDING_SPAWNS = 256;
    private static final int DEFAULT_WEIGHT = 1;
    private static final Long2ObjectOpenHashMap<PendingSpawn> PENDING_SPAWNS = new Long2ObjectOpenHashMap<>();
    // Per kind, so npcs waiting for night don't stop sheep spawning
    private static final HashMap<DisabledSpawn, Long> DISABLED_SPAWNS = new HashMap<>();
    private static final int MAXIMUM_DISABLED_SPAWNS = 4096;

    private record DisabledSpawn(RegistryKey<World> world, ChunkPos chunk, EntityType<?> type) {}

    private record PendingSpawn(WildSpawnEventData data, long expires) {}

    private boolean shouldSpawnDefaultWhenUnmet;
    private List<WildSpawnEventData> wildSpawnEventDatas;

    public BiomeEventData(boolean shouldSpawnDefaultWhenUnmet, List<WildSpawnEventData> wildNpcs){
        this(wildNpcs);
        this.shouldSpawnDefaultWhenUnmet = shouldSpawnDefaultWhenUnmet;
    }

    public BiomeEventData(List<WildSpawnEventData> wildNpcs){
        this.wildSpawnEventDatas = wildNpcs;
        this.shouldSpawnDefaultWhenUnmet = false;
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

    public boolean getSpawnDefaultWhenUnmet(){
        return shouldSpawnDefaultWhenUnmet;
    }

    public ContextualizedBiomeData findNpcData(World world, NpcEntity entity) {
        // Use what the spawn check picked here. Only the event that made a pick can take it
        long spot = entity.getBlockPos().asLong();
        PendingSpawn pending = PENDING_SPAWNS.get(spot);
        WildSpawnEventData spawningData = null;
        if(pending != null && wildSpawnEventDatas.contains(pending.data())) {
            PENDING_SPAWNS.remove(spot);
            if(world.getTime() <= pending.expires())
                spawningData = pending.data();
        }
        if(spawningData == null)
            spawningData = pickWeighted(Registries.ENTITY_TYPE.getId(EntitiesME.NPC), world, entity.getBlockPos(), world.getRandom());
        if(spawningData == null)
            return null;

        Identifier npcClassId = spawningData.getNpcClass(null);
        NpcClass npcClass = npcClassId == null ? null : world.getRegistryManager().getOrThrow(DynamicRegistriesME.NPC_CLASS).get(npcClassId);
        return new ContextualizedBiomeData(npcClass, spawningData.getParty(), spawningData.getJob());
    }

    private @Nullable WildSpawnEventData pickWeighted(Identifier entityId, World world, BlockPos pos, Random random) {
        List<WildSpawnEventData> considered = new ArrayList<>();
        int total = 0;
        for(WildSpawnEventData data : wildSpawnEventDatas) {
            if(SpawnEventDataUtil.isConsideredForSpawning(data, entityId, world, pos)) {
                considered.add(data);
                total += Math.max(0, data.getWeight(DEFAULT_WEIGHT));
            }
        }
        if(total <= 0)
            return null;
        int roll = random.nextInt(total);
        for(WildSpawnEventData data : considered) {
            roll -= Math.max(0, data.getWeight(DEFAULT_WEIGHT));
            if(roll < 0)
                return data;
        }
        return null;
    }

    private boolean hasEntityType(Identifier entityId) {
        for(WildSpawnEventData data : wildSpawnEventDatas) {
            if(IdentifierUtil.compareId(data.getEntityType(), entityId))
                return true;
        }
        return false;
    }

    public boolean canSpawn(EntityType<?> type, ServerWorld world, BlockPos pos, Random random) {
        Identifier entityId = Registries.ENTITY_TYPE.getId(type);
        if(!hasEntityType(entityId))
            return true;
        DisabledSpawn spawn = new DisabledSpawn(world.getRegistryKey(), new ChunkPos(pos), type);
        Long expiredTick = DISABLED_SPAWNS.get(spawn);
        if (expiredTick != null) {
            if(world.getTime() < expiredTick)
                return false;
            DISABLED_SPAWNS.remove(spawn);
        }

        WildSpawnEventData spawningData = pickWeighted(entityId, world, pos, random);
        if(spawningData == null || spawningData.isDiscarded(random)){
            // Only revisited where something tries to spawn, so abandoned chunks' entries pile up
            if(DISABLED_SPAWNS.size() >= MAXIMUM_DISABLED_SPAWNS)
                DISABLED_SPAWNS.values().removeIf(until -> until <= world.getTime());
            DISABLED_SPAWNS.put(spawn, world.getTime() + CHUNK_DISABLE_TICKS);
            return false;
        }
        spawningData.broadcastMessage(world, pos);
        if(type == EntitiesME.NPC) {
            if(PENDING_SPAWNS.size() >= MAXIMUM_PENDING_SPAWNS)
                PENDING_SPAWNS.clear();
            PENDING_SPAWNS.put(pos.asLong(), new PendingSpawn(spawningData, world.getTime() + PENDING_SPAWN_LIFETIME));
        }
        SpawnCensus.expect(world, type, type == EntitiesME.NPC ? spawningData.getNpcClass(null) : null, pos);
        return true;
    }

    // World times, which a different save starts over
    public static void clearSpawns() {
        PENDING_SPAWNS.clear();
        DISABLED_SPAWNS.clear();
    }

    public record ContextualizedBiomeData(NpcClass npcClass, @Nullable PartyData party, @Nullable Identifier job) {}
}
