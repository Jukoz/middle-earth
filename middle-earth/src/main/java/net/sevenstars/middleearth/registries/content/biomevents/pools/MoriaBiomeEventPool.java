package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.BrigandNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.MoriaNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class MoriaBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData EREGION;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(MoriaNpcDataPool.GOBLIN).withWeight(7).requireUnderground(),
            new WildSpawnEventData(MoriaNpcDataPool.MILITIA).withWeight(5),
            new WildSpawnEventData(MoriaNpcDataPool.SCOUT).withWeight(4),
            new WildSpawnEventData(MoriaNpcDataPool.WARRIOR).withWeight(6).requireUnderground(),
            new WildSpawnEventData(MoriaNpcDataPool.RIDER).withWeight(4),
            new WildSpawnEventData(MoriaNpcDataPool.VETERAN).withWeight(3).requireNight(),
            new WildSpawnEventData(MoriaNpcDataPool.CHIEF).requireNight()
        ));

        EREGION = new BiomeEventData(false, List.of(
            new WildSpawnEventData(BrigandNpcDataPool.THIEF).withWeight(2),
            new WildSpawnEventData(BrigandNpcDataPool.THUG).withWeight(1),
            new WildSpawnEventData(MoriaNpcDataPool.SCOUT).withWeight(3),
            new WildSpawnEventData(MoriaNpcDataPool.RIDER)
        ));
    }
}
