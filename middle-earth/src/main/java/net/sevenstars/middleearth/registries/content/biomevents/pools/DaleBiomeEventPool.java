package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.registries.content.npcs.pools.DalishNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.RohirricNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class DaleBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(DalishNpcDataPool.MILITIA).withWeight(7),
            new BiomeNpcSpawningData(DalishNpcDataPool.SOLDIER).withWeight(3),
            new BiomeNpcSpawningData(DalishNpcDataPool.ELITE_ARCHER).withWeight(3),
            new BiomeNpcSpawningData(DalishNpcDataPool.KNIGHT).withWeight(1),
            new BiomeNpcSpawningData(DalishNpcDataPool.KNIGHT).withWeight(2).withMount(EntityType.HORSE),
            new BiomeNpcSpawningData(DalishNpcDataPool.SERGEANT).withWeight(1)
        ));
    }
}
