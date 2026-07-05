package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.ShireNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class ShireBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(ShireNpcDataPool.PEASANT).withWeight(12),
            new BiomeNpcSpawningData(ShireNpcDataPool.MILITIA).withWeight(3),
            new BiomeNpcSpawningData(ShireNpcDataPool.SHIRRIFF)
        ));
    }
}
