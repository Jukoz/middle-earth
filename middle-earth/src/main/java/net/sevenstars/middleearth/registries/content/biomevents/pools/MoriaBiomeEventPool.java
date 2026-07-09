package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.BrigandNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.MoriaNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class MoriaBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData EREGION;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(MoriaNpcDataPool.GOBLIN).withWeight(7).withUndergroundRequired(),
            new BiomeNpcSpawningData(MoriaNpcDataPool.MILITIA).withWeight(5),
            new BiomeNpcSpawningData(MoriaNpcDataPool.SCOUT).withWeight(4),
            new BiomeNpcSpawningData(MoriaNpcDataPool.WARRIOR).withWeight(6).withNightRequired(),
            new BiomeNpcSpawningData(MoriaNpcDataPool.RIDER).withWeight(4).withMount(EntitiesME.WARG),
            new BiomeNpcSpawningData(MoriaNpcDataPool.VETERAN).withWeight(3).withNightRequired(),
            new BiomeNpcSpawningData(MoriaNpcDataPool.CHIEF).withNightRequired()
        ));

        EREGION = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(BrigandNpcDataPool.THIEF).withWeight(2),
            new BiomeNpcSpawningData(BrigandNpcDataPool.THUG).withWeight(1),
            new BiomeNpcSpawningData(MoriaNpcDataPool.SCOUT).withWeight(3),
            new BiomeNpcSpawningData(MoriaNpcDataPool.RIDER).withMount(EntitiesME.WARG)
        ));
    }
}
