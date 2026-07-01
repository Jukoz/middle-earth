package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.EreborNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.GundabadNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class LongbeardsBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData GREY_PLAINS;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(EreborNpcDataPool.MILITIA).withWeight(6),
            new BiomeNpcSpawningData(EreborNpcDataPool.SOLDIER).withWeight(5),
            new BiomeNpcSpawningData(EreborNpcDataPool.VETERAN).withWeight(2),
            new BiomeNpcSpawningData(EreborNpcDataPool.VETERAN).withWeight(1).withMount(EntitiesME.BROADHOOF_GOAT),
            new BiomeNpcSpawningData(EreborNpcDataPool.ELITE).withWeight(1)
        ));

        GREY_PLAINS = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(EreborNpcDataPool.MILITIA).withWeight(4),
            new BiomeNpcSpawningData(EreborNpcDataPool.SOLDIER).withWeight(3),
            new BiomeNpcSpawningData(EreborNpcDataPool.VETERAN).withWeight(1),

            new BiomeNpcSpawningData(GundabadNpcDataPool.SCOUT).withWeight(5),
            new BiomeNpcSpawningData(GundabadNpcDataPool.MILITIA).withWeight(4),
            new BiomeNpcSpawningData(GundabadNpcDataPool.WARRIOR).withWeight(3),
            new BiomeNpcSpawningData(GundabadNpcDataPool.SCOUT).withWeight(2).withMount(EntitiesME.WARG)
        ));
    }
}
