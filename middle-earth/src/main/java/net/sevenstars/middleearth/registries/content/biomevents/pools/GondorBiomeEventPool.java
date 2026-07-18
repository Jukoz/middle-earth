package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.MordorNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class GondorBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData PEASANT_FIEF;
    public final static BiomeEventData OSGILIATH;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(GondorianNpcDataPool.MILITIA).withWeight(5),
            new WildSpawnEventData(GondorianNpcDataPool.SOLDIER).withWeight(4),
            new WildSpawnEventData(GondorianNpcDataPool.KNIGHT).withWeight(2),
            new WildSpawnEventData(GondorianNpcDataPool.VETERAN).withWeight(2),
            new WildSpawnEventData(GondorianNpcDataPool.LEADER)
        ));

        PEASANT_FIEF = new BiomeEventData(false, List.of(
                new WildSpawnEventData(GondorianNpcDataPool.MILITIA).withWeight(6),
                new WildSpawnEventData(GondorianNpcDataPool.SOLDIER).withWeight(3),
                new WildSpawnEventData(GondorianNpcDataPool.KNIGHT).withWeight(1)
        ));

        OSGILIATH = new BiomeEventData(false, List.of(
                new WildSpawnEventData(MordorNpcDataPool.SCOUT).withWeight(4),
                new WildSpawnEventData(MordorNpcDataPool.MILITIA).withWeight(3),
                new WildSpawnEventData(MordorNpcDataPool.WARRIOR).withWeight(2),

                new WildSpawnEventData(GondorianNpcDataPool.SOLDIER).withWeight(7),
                new WildSpawnEventData(GondorianNpcDataPool.KNIGHT).withWeight(2),
                new WildSpawnEventData(GondorianNpcDataPool.VETERAN).withWeight(2)
        ));
    }
}
