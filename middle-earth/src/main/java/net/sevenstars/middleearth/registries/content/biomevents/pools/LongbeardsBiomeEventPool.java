package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.EreborNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.GundabadNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class LongbeardsBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData GREY_PLAINS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(EreborNpcDataPool.MILITIA).withWeight(6),
            new WildSpawnEventData(EreborNpcDataPool.SOLDIER).withWeight(5),
            new WildSpawnEventData(EreborNpcDataPool.VETERAN).withWeight(2),
            new WildSpawnEventData(EreborNpcDataPool.VETERAN).withWeight(1),
            new WildSpawnEventData(EreborNpcDataPool.ELITE).withWeight(1)
        ));

        GREY_PLAINS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(EreborNpcDataPool.MILITIA).withWeight(4),
            new WildSpawnEventData(EreborNpcDataPool.SOLDIER).withWeight(3),
            new WildSpawnEventData(EreborNpcDataPool.VETERAN).withWeight(1),

            new WildSpawnEventData(GundabadNpcDataPool.SCOUT).withWeight(5),
            new WildSpawnEventData(GundabadNpcDataPool.MILITIA).withWeight(4),
            new WildSpawnEventData(GundabadNpcDataPool.WARRIOR).withWeight(3),
            new WildSpawnEventData(GundabadNpcDataPool.MILITIA).withWeight(2)
        ));
    }
}
