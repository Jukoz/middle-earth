package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.WoodlandRealmNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class WoodlandRealmBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;
    public final static BiomeEventData HALL;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.HUNTER).withWeight(7),
            new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.RANGER).withWeight(5),
            new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.LANCER).withWeight(3).withMount(EntitiesME.GREAT_HORN),
            new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.WARRIOR).withWeight(2),
            new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.WARDEN_OF_THE_GLADE)
        ));

        SCOUTS = new BiomeEventData(List.of(
                new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.RANGER).withWeight(4),
                new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.SENTINEL).withWeight(3),
                new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.NIGHTSHADE).withWeight(1).withNightRequired()
        ));

        HALL = new BiomeEventData(List.of(
                new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.ARTISAN).withWeight(10),
                new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.HUNTER).withWeight(6),
                new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.RANGER).withWeight(4),
                new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.WARRIOR).withWeight(2),
                new BiomeNpcSpawningData(WoodlandRealmNpcDataPool.WARDEN_OF_THE_GLADE)
        ));
    }
}
