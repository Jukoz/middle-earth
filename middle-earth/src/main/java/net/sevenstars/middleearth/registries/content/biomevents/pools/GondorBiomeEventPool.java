package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.GondorianNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.MordorNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class GondorBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData PEASANT_FIEF;
    public final static BiomeEventData OSGILIATH;

    static {
        DEFAULT = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(GondorianNpcDataPool.MILITIA).withWeight(5),
            new BiomeNpcSpawningData(GondorianNpcDataPool.SOLDIER).withWeight(4),
            new BiomeNpcSpawningData(GondorianNpcDataPool.KNIGHT).withWeight(2),
            new BiomeNpcSpawningData(GondorianNpcDataPool.KNIGHT).withWeight(1).withMount(EntityType.HORSE),
            new BiomeNpcSpawningData(GondorianNpcDataPool.VETERAN).withWeight(2),
            new BiomeNpcSpawningData(GondorianNpcDataPool.LEADER)
        ));

        PEASANT_FIEF = new BiomeEventData(List.of(
                new BiomeNpcSpawningData(GondorianNpcDataPool.MILITIA).withWeight(6),
                new BiomeNpcSpawningData(GondorianNpcDataPool.SOLDIER).withWeight(3),
                new BiomeNpcSpawningData(GondorianNpcDataPool.SOLDIER).withWeight(1).withMount(EntityType.HORSE),
                new BiomeNpcSpawningData(GondorianNpcDataPool.KNIGHT).withWeight(1),
                new BiomeNpcSpawningData(GondorianNpcDataPool.KNIGHT).withWeight(1).withMount(EntityType.HORSE)
        ));

        OSGILIATH = new BiomeEventData(List.of(
                new BiomeNpcSpawningData(MordorNpcDataPool.SCOUT).withWeight(4),
                new BiomeNpcSpawningData(MordorNpcDataPool.MILITIA).withWeight(3),
                new BiomeNpcSpawningData(MordorNpcDataPool.WARRIOR).withWeight(2),

                new BiomeNpcSpawningData(GondorianNpcDataPool.SOLDIER).withWeight(7),
                new BiomeNpcSpawningData(GondorianNpcDataPool.KNIGHT).withWeight(2),
                new BiomeNpcSpawningData(GondorianNpcDataPool.KNIGHT).withWeight(2).withMount(EntityType.HORSE),
                new BiomeNpcSpawningData(GondorianNpcDataPool.VETERAN).withWeight(2)
        ));
    }
}
