package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.LorienNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class LothlorienBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(LorienNpcDataPool.CIVILIAN).withWeight(10),
            new WildSpawnEventData(LorienNpcDataPool.RANGER).withWeight(9),
            new WildSpawnEventData(LorienNpcDataPool.WARRIOR).withWeight(7),
            new WildSpawnEventData(LorienNpcDataPool.KNIGHT).withWeight(4),
            new WildSpawnEventData(LorienNpcDataPool.EGLADIL_SENTINEL),
            new WildSpawnEventData(LorienNpcDataPool.EGLADIL_COMMANDER)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
                new WildSpawnEventData(LorienNpcDataPool.RANGER).withWeight(4),
                new WildSpawnEventData(LorienNpcDataPool.SENTINEL).withWeight(1)
        ));
    }
}
