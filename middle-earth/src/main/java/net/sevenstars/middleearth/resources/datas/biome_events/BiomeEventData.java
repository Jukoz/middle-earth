package net.sevenstars.middleearth.resources.datas.biome_events;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.resources.datas.npc_types.NpcType;

import java.util.ArrayList;
import java.util.List;

public class BiomeEventData {
    public static final Codec<BiomeEventData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.BOOL.fieldOf("spawn_default_when_unmet").forGetter(BiomeEventData::getSpawnDefaultWhenUnmet),
            Codec.list(BiomeNpcSpawningData.CODEC).fieldOf("wild_npcs").forGetter(BiomeEventData::getWildNpcs)
    ).apply(instance, BiomeEventData::new));

    private Boolean shouldSpawnDefaultWhenUnmet;
    private List<BiomeNpcSpawningData> wildNpcs;

    public BiomeEventData(boolean shouldSpawnDefaultWhenUnmet, List<BiomeNpcSpawningData> wildNpcs){
        this(wildNpcs);
        this.shouldSpawnDefaultWhenUnmet = shouldSpawnDefaultWhenUnmet;
    }

    public BiomeEventData(List<BiomeNpcSpawningData> wildNpcs){
        this.wildNpcs = wildNpcs;
        this.shouldSpawnDefaultWhenUnmet = false;
    }

    public Boolean getSpawnDefaultWhenUnmet(){
        return shouldSpawnDefaultWhenUnmet;
    }

    public List<BiomeNpcSpawningData> getWildNpcs(){
        return wildNpcs;
    }

    public ContextualizedBiomeData findNpcData(World world, NpcEntity entity) {
        List<BiomeNpcSpawningData> weightedData = new ArrayList<BiomeNpcSpawningData>();
        DynamicRegistryManager manager = world.getRegistryManager();

        for(BiomeNpcSpawningData data : getWildNpcs()){
            int weightAmount = data.getWeight();
            if(data.conditionsAreMet(world, entity.getBlockPos()))
                for(int i = 0; i < weightAmount; i ++){
                    weightedData.add(data);
                }
        }
        if(weightedData.isEmpty())
            return null;

        BiomeNpcSpawningData spawningData = weightedData.get(Random.create().nextInt(weightedData.size()));
        Registry<NpcType> npcDataRegistry = manager.getOrThrow(DynamicRegistriesME.NPC_TYPE);
        NpcType foundNpcType = npcDataRegistry.get(spawningData.getNpcDataIdentifier());

        EntityType entityType = null;
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
        return new ContextualizedBiomeData(foundNpcType, entityType, mountArmorItemStack);
    }

    public record ContextualizedBiomeData(NpcType npcType, EntityType hasMount, ItemStack mountArmor){

    }
}
