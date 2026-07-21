package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
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

    public ContextualizedBiomeData findNpcData(World world, NpcEntity entity) {
        List<WildSpawnEventData> weightedData = new ArrayList<>();
        DynamicRegistryManager manager = world.getRegistryManager();

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
        EntityType entityType = null;
        /*
                ItemStack mountArmorItemStack = null;
        if( spawningData.getMount().isPresent()){
            entityType = manager.getOrThrow(RegistryKeys.ENTITY_TYPE).get(spawningData.getMount().get());
            if(spawningData.getMountArmor().isPresent()){
                mountArmorItemStack = new ItemStack(manager.getOrThrow(RegistryKeys.ITEM).get(spawningData.getMountArmor().get()));
                if(spawningData.getMountArmorColor().isPresent()){
                    mountArmorItemStack.set(DataComponentTypes.DYED_COLOR, new DyedColorComponent(spawningData.getMountArmorColor().get()));
                }
            }
        }

         */
        return new ContextualizedBiomeData(foundNpcType, entityType, ItemStack.EMPTY);
    }

    public boolean canSpawn(EntityType<?> type, World world, BlockPos pos, Random random) {
        List<WildSpawnEventData> weightedData = new ArrayList<>();
        boolean containEntityType = false;
        for(WildSpawnEventData data : getWildSpawnEventDatas()){
            Identifier entityId = Registries.ENTITY_TYPE.getId(type);
            if(!containEntityType && MiddleEarth.compareId(data.getEntityType(), entityId))
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
        if(weightedData.isEmpty())
            return false;

        WildSpawnEventData spawningData = weightedData.get(Random.create().nextInt(weightedData.size()));
        return !spawningData.isDiscarded(random);
    }


    public record ContextualizedBiomeData(NpcType npcType, EntityType hasMount, ItemStack mountArmor){

    }
}
