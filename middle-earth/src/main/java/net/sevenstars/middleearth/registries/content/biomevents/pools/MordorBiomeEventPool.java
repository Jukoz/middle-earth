package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.MordorNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class MordorBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData NURN;
    public final static BiomeEventData SCOUTS;
    public final static BiomeEventData ITHILIEN;
    public final static BiomeEventData CAVE;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(MordorNpcDataPool.SNAGA).withWeight(5),
            new BiomeNpcSpawningData(MordorNpcDataPool.SCOUT).withWeight(5),
            new BiomeNpcSpawningData(MordorNpcDataPool.MILITIA).withWeight(5),
            new BiomeNpcSpawningData(MordorNpcDataPool.WARRIOR).withWeight(5),
            new BiomeNpcSpawningData(MordorNpcDataPool.MILITIA).withWeight(3).withMount(EntitiesME.WARG),
            new BiomeNpcSpawningData(MordorNpcDataPool.MILITIA).withMount(EntitiesME.CAVE_TROLL),
            new BiomeNpcSpawningData(MordorNpcDataPool.VETERAN).withWeight(2),
            new BiomeNpcSpawningData(MordorNpcDataPool.CAPTAIN)
        ));

        NURN = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(MordorNpcDataPool.SNAGA).withWeight(5),
            new BiomeNpcSpawningData(MordorNpcDataPool.SCOUT).withWeight(5),
            new BiomeNpcSpawningData(MordorNpcDataPool.MILITIA).withWeight(5),
            new BiomeNpcSpawningData(MordorNpcDataPool.WARRIOR).withWeight(5),
            new BiomeNpcSpawningData(MordorNpcDataPool.MILITIA).withWeight(3).withMount(EntitiesME.WARG)
        ));

        SCOUTS = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(MordorNpcDataPool.SCOUT).withWeight(4),
            new BiomeNpcSpawningData(MordorNpcDataPool.MILITIA).withWeight(3),
            new BiomeNpcSpawningData(MordorNpcDataPool.WARRIOR).withWeight(2),
            new BiomeNpcSpawningData(MordorNpcDataPool.SCOUT).withWeight(2).withMount(EntitiesME.WARG)
        ));

        ITHILIEN = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(MordorNpcDataPool.SCOUT).withWeight(4),
            new BiomeNpcSpawningData(MordorNpcDataPool.MILITIA).withWeight(3),
            new BiomeNpcSpawningData(MordorNpcDataPool.WARRIOR).withWeight(2),

            new BiomeNpcSpawningData(GondorianNpcDataPool.SOLDIER).withWeight(4),
            new BiomeNpcSpawningData(GondorianNpcDataPool.KNIGHT).withWeight(1)
        ));

        CAVE = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(MordorNpcDataPool.SNAGA).withWeight(3),
            new BiomeNpcSpawningData(MordorNpcDataPool.SCOUT).withWeight(3),
            new BiomeNpcSpawningData(MordorNpcDataPool.WARRIOR).withWeight(2),
            new BiomeNpcSpawningData(MordorNpcDataPool.SCOUT).withMount(EntitiesME.CAVE_TROLL)
        ));
    }
}
