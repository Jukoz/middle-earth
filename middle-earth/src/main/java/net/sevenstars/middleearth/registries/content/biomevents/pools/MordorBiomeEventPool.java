package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.MordorNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class MordorBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData NURN;
    public final static BiomeEventData SCOUTS;
    public final static BiomeEventData ITHILIEN;
    public final static BiomeEventData CAVE;
    public final static BiomeEventData DOL_GULDUR;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(MordorNpcDataPool.SNAGA).withWeight(5),
            new WildSpawnEventData(MordorNpcDataPool.SCOUT).withWeight(5),
            new WildSpawnEventData(MordorNpcDataPool.MILITIA).withWeight(5),
            new WildSpawnEventData(MordorNpcDataPool.WARRIOR).withWeight(5),
            new WildSpawnEventData(MordorNpcDataPool.VETERAN).withWeight(2),
            new WildSpawnEventData(MordorNpcDataPool.CAPTAIN)
        ));

        NURN = new BiomeEventData(false, List.of(
            new WildSpawnEventData(MordorNpcDataPool.SNAGA).withWeight(5),
            new WildSpawnEventData(MordorNpcDataPool.SCOUT).withWeight(5),
            new WildSpawnEventData(MordorNpcDataPool.MILITIA).withWeight(5),
            new WildSpawnEventData(MordorNpcDataPool.WARRIOR).withWeight(5)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(MordorNpcDataPool.SCOUT).withWeight(4),
            new WildSpawnEventData(MordorNpcDataPool.MILITIA).withWeight(3),
            new WildSpawnEventData(MordorNpcDataPool.WARRIOR).withWeight(2)
        ));


        ITHILIEN = new BiomeEventData(false, List.of(
            new WildSpawnEventData(MordorNpcDataPool.SCOUT).withWeight(4),
            new WildSpawnEventData(MordorNpcDataPool.MILITIA).withWeight(3),
            new WildSpawnEventData(MordorNpcDataPool.WARRIOR).withWeight(2),

            new WildSpawnEventData(GondorianNpcDataPool.SOLDIER).withWeight(4),
            new WildSpawnEventData(GondorianNpcDataPool.KNIGHT).withWeight(1)
        ));

        CAVE = new BiomeEventData(false, List.of(
            new WildSpawnEventData(MordorNpcDataPool.SNAGA).withWeight(3),
            new WildSpawnEventData(MordorNpcDataPool.SCOUT).withWeight(3),
            new WildSpawnEventData(MordorNpcDataPool.WARRIOR).withWeight(2)
        ));

        DOL_GULDUR = new BiomeEventData(false, List.of(
            new WildSpawnEventData(MordorNpcDataPool.DOL_GULDUR_SCOUT).withWeight(5),
            new WildSpawnEventData(MordorNpcDataPool.DOL_GULDUR_WARRIOR).withWeight(2)
        ));
    }
}
