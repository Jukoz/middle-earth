package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.DalishNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class DaleBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(DalishNpcDataPool.MILITIA).withWeight(7),
            new WildSpawnEventData(DalishNpcDataPool.SOLDIER).withWeight(3),
            new WildSpawnEventData(DalishNpcDataPool.ELITE_ARCHER).withWeight(3),
            new WildSpawnEventData(DalishNpcDataPool.KNIGHT).withWeight(1),
            new WildSpawnEventData(DalishNpcDataPool.SERGEANT).withWeight(1)
        ));
    }
}
