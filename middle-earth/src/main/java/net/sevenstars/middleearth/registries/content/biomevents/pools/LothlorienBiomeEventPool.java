package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.LorienNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class LothlorienBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(LorienNpcDataPool.CIVILIAN).withWeight(10),
            new BiomeNpcSpawningData(LorienNpcDataPool.RANGER).withWeight(9),
            new BiomeNpcSpawningData(LorienNpcDataPool.WARRIOR).withWeight(7),
            new BiomeNpcSpawningData(LorienNpcDataPool.KNIGHT).withWeight(4),
            new BiomeNpcSpawningData(LorienNpcDataPool.EGLADIL_SENTINEL),
            new BiomeNpcSpawningData(LorienNpcDataPool.EGLADIL_COMMANDER)
        ));

        SCOUTS = new BiomeEventData(List.of(
                new BiomeNpcSpawningData(LorienNpcDataPool.RANGER).withWeight(4),
                new BiomeNpcSpawningData(LorienNpcDataPool.SENTINEL).withWeight(1)
        ));
    }
}
