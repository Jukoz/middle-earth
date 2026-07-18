package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.RohirricNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class RohanBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(RohirricNpcDataPool.MILITIA).withWeight(7),
            new WildSpawnEventData(RohirricNpcDataPool.SOLDIER).withWeight(3),
            new WildSpawnEventData(RohirricNpcDataPool.KNIGHT).withWeight(2),
            new WildSpawnEventData(RohirricNpcDataPool.EORLING_MARSHAL).withWeight(1)
        ));
    }
}
