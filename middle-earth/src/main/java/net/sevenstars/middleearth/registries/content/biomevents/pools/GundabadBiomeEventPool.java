package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.GundabadNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class GundabadBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(GundabadNpcDataPool.GOBLIN).withWeight(6),
            new BiomeNpcSpawningData(GundabadNpcDataPool.SCOUT).withWeight(7),
            new BiomeNpcSpawningData(GundabadNpcDataPool.MILITIA).withWeight(6),
            new BiomeNpcSpawningData(GundabadNpcDataPool.WARRIOR).withWeight(5),
            new BiomeNpcSpawningData(GundabadNpcDataPool.MILITIA).withWeight(4).withMount(EntitiesME.WARG),
            new BiomeNpcSpawningData(GundabadNpcDataPool.MILITIA).withMount(EntitiesME.CAVE_TROLL),
            new BiomeNpcSpawningData(GundabadNpcDataPool.VETERAN).withWeight(2),
            new BiomeNpcSpawningData(GundabadNpcDataPool.LEADER)
        ));

        SCOUTS = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(GundabadNpcDataPool.SCOUT).withWeight(4),
            new BiomeNpcSpawningData(GundabadNpcDataPool.MILITIA).withWeight(3),
            new BiomeNpcSpawningData(GundabadNpcDataPool.WARRIOR).withWeight(2),
            new BiomeNpcSpawningData(GundabadNpcDataPool.MILITIA).withWeight(2).withMount(EntitiesME.WARG)
        ));
    }
}
