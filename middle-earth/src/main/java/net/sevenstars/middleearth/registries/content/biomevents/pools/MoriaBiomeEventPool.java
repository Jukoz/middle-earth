package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.GundabadNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.MoriaNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class MoriaBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(MoriaNpcDataPool.GOBLIN).withWeight(5).withUndegroundRequired(),
            new BiomeNpcSpawningData(MoriaNpcDataPool.SCOUT).withWeight(5),
            new BiomeNpcSpawningData(MoriaNpcDataPool.WARRIOR).withWeight(5).withNightRequired(),
            new BiomeNpcSpawningData(MoriaNpcDataPool.RIDER).withWeight(3).withMount(EntitiesME.WARG),
            new BiomeNpcSpawningData(MoriaNpcDataPool.RIDER).withMount(EntitiesME.CAVE_TROLL).withUndegroundRequired(),
            new BiomeNpcSpawningData(MoriaNpcDataPool.VETERAN).withWeight(2).withNightRequired()
        ));
    }
}
