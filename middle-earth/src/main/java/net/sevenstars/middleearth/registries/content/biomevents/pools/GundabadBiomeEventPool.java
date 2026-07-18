package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcs.pools.GundabadNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class GundabadBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(GundabadNpcDataPool.GOBLIN).withWeight(6),
            new WildSpawnEventData(GundabadNpcDataPool.SCOUT).withWeight(7),
            new WildSpawnEventData(GundabadNpcDataPool.MILITIA).withWeight(6),
            new WildSpawnEventData(GundabadNpcDataPool.WARRIOR).withWeight(5),
            new WildSpawnEventData(GundabadNpcDataPool.VETERAN).withWeight(2),
            new WildSpawnEventData(GundabadNpcDataPool.LEADER)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(GundabadNpcDataPool.SCOUT).withWeight(4),
            new WildSpawnEventData(GundabadNpcDataPool.MILITIA).withWeight(3),
            new WildSpawnEventData(GundabadNpcDataPool.WARRIOR).withWeight(2)
        ));
    }
}
