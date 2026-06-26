package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.registries.content.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.MordorNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.RohirricNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class RohanBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(RohirricNpcDataPool.MILITIA).withWeight(7),
            new BiomeNpcSpawningData(RohirricNpcDataPool.SOLDIER).withWeight(5),
            new BiomeNpcSpawningData(RohirricNpcDataPool.KNIGHT).withWeight(2),
            new BiomeNpcSpawningData(RohirricNpcDataPool.KNIGHT).withWeight(1).withMount(EntityType.HORSE),
            new BiomeNpcSpawningData(RohirricNpcDataPool.EORLING_MARSHAL).withWeight(1)
        ));
    }
}
