package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.WoodlandRealmNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class WoodlandRealmBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;
    public final static BiomeEventData HALL;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(WoodlandRealmNpcDataPool.HUNTER).withWeight(7),
            new WildSpawnEventData(WoodlandRealmNpcDataPool.RANGER).withWeight(5),
            new WildSpawnEventData(WoodlandRealmNpcDataPool.LANCER).withWeight(3),
            new WildSpawnEventData(WoodlandRealmNpcDataPool.WARRIOR).withWeight(2),
            new WildSpawnEventData(WoodlandRealmNpcDataPool.WARDEN_OF_THE_GLADE)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
                new WildSpawnEventData(WoodlandRealmNpcDataPool.RANGER).withWeight(4),
                new WildSpawnEventData(WoodlandRealmNpcDataPool.SENTINEL).withWeight(3),
                new WildSpawnEventData(WoodlandRealmNpcDataPool.NIGHTSHADE).withWeight(1).requireNight()
        ));

        HALL = new BiomeEventData(false, List.of(
                new WildSpawnEventData(WoodlandRealmNpcDataPool.ARTISAN).withWeight(10),
                new WildSpawnEventData(WoodlandRealmNpcDataPool.HUNTER).withWeight(6),
                new WildSpawnEventData(WoodlandRealmNpcDataPool.RANGER).withWeight(4),
                new WildSpawnEventData(WoodlandRealmNpcDataPool.WARRIOR).withWeight(2),
                new WildSpawnEventData(WoodlandRealmNpcDataPool.WARDEN_OF_THE_GLADE)
        ));
    }
}
