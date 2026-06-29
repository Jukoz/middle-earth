package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.IsengardNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.IsengardNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class IsengardBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(IsengardNpcDataPool.SNAGA).withWeight(6),
            new BiomeNpcSpawningData(IsengardNpcDataPool.WARRIOR).withWeight(5),
            new BiomeNpcSpawningData(IsengardNpcDataPool.WARRIOR).withWeight(4).withMount(EntitiesME.WARG),
            new BiomeNpcSpawningData(IsengardNpcDataPool.URUK_HAI_SCOUT).withWeight(8),
            new BiomeNpcSpawningData(IsengardNpcDataPool.URUK_HAI_SOLDIER).withWeight(7),
            new BiomeNpcSpawningData(IsengardNpcDataPool.URUK_HAI_VETERAN).withWeight(4),
            new BiomeNpcSpawningData(IsengardNpcDataPool.URUK_HAI_BERSERKER).withWeight(2),
            new BiomeNpcSpawningData(IsengardNpcDataPool.URUK_HAI_LEADER)
        ));

        SCOUTS = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(IsengardNpcDataPool.URUK_HAI_SCOUT).withWeight(3),
            new BiomeNpcSpawningData(IsengardNpcDataPool.WARRIOR).withWeight(2),
            new BiomeNpcSpawningData(IsengardNpcDataPool.WARRIOR).withWeight(2).withMount(EntitiesME.WARG)
        ));
    }
}
