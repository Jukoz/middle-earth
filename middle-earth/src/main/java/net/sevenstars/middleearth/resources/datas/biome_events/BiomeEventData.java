package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.sevenstars.middleearth.MiddleEarth;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.biome_events.data.SpawnEventDataUtil;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.ArrayList;
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

    public ContextualizedBiomeData findNpcData(Level world, NpcEntity entity) {
        RegistryAccess manager = world.registryAccess();
        ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(EntitiesME.NPC);
        WildSpawnEventData spawningData = null;
        long totalWeight = 0L;

        for(WildSpawnEventData data : getWildSpawnEventDatas()){
            if (SpawnEventDataUtil.isConsideredForSpawning(data, entityId, world, entity.blockPosition())) {
                long weight = Math.max(0, data.getWeight(1));
                if (weight == 0L) {
                    continue;
                }
                totalWeight += weight;
                if (nextLong(world.random, totalWeight) < weight) {
                    spawningData = data;
                }
            }
        }
        if(spawningData == null)
            return null;

        Registry<NpcType> npcDataRegistry = manager.registryOrThrow(DynamicRegistriesME.NPC_TYPE);
        ResourceLocation npcId = spawningData.getNpcType(null);
        NpcType foundNpcType = (npcId != null) ? npcDataRegistry.get(npcId) : null;

        return new ContextualizedBiomeData(foundNpcType);
    }

    public boolean canSpawn(EntityType<?> type, Level world, BlockPos pos, RandomSource random) {
        boolean containEntityType = false;
        ResourceLocation entityId = BuiltInRegistries.ENTITY_TYPE.getKey(type);
        WildSpawnEventData spawningData = null;
        long totalWeight = 0L;
        for(WildSpawnEventData data : getWildSpawnEventDatas()){
            if(!containEntityType && MiddleEarth.compareId(data.getEntityType(), entityId))
                containEntityType = true;
            if (SpawnEventDataUtil.isConsideredForSpawning(data, entityId, world, pos)) {
                long weight = Math.max(0, data.getWeight(1));
                if (weight == 0L) {
                    continue;
                }
                totalWeight += weight;
                if (nextLong(random, totalWeight) < weight) {
                    spawningData = data;
                }
            }
        }
        if(!containEntityType)
            return true;
        if(spawningData == null)
            return false;

        return !spawningData.isDiscarded(random);
    }

    private static long nextLong(RandomSource random, long bound) {
        long value = random.nextLong() >>> 1;
        long result = value % bound;
        while (value - result + (bound - 1L) < 0L) {
            value = random.nextLong() >>> 1;
            result = value % bound;
        }
        return result;
    }


    public record ContextualizedBiomeData(NpcType npcType){

    }
}
