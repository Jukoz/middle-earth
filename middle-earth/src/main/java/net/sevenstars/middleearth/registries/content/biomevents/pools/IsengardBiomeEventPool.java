package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.IsengardNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class IsengardBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(IsengardNpcDataPool.SNAGA).withWeight(6),
            new WildSpawnEventData(IsengardNpcDataPool.WARRIOR).withWeight(5),
            new WildSpawnEventData(IsengardNpcDataPool.URUK_HAI_SCOUT).withWeight(8),
            new WildSpawnEventData(IsengardNpcDataPool.URUK_HAI_SOLDIER).withWeight(7),
            new WildSpawnEventData(IsengardNpcDataPool.URUK_HAI_VETERAN).withWeight(4),
            new WildSpawnEventData(IsengardNpcDataPool.URUK_HAI_BERSERKER).withWeight(2),
            new WildSpawnEventData(IsengardNpcDataPool.URUK_HAI_LEADER)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(IsengardNpcDataPool.URUK_HAI_SCOUT).withWeight(3),
            new WildSpawnEventData(IsengardNpcDataPool.WARRIOR).withWeight(2)
        ));
    }
}
