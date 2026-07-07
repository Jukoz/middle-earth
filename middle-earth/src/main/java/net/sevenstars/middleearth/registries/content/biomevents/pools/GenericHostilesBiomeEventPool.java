package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcs.pools.BrigandNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.GoblinTownNpcDataPool;
import net.sevenstars.middleearth.registries.content.npcs.pools.WildGoblinNpcDataPool;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeNpcSpawningData;

import java.util.List;

public class GenericHostilesBiomeEventPool {
    public final static BiomeEventData CAVE;
    public final static BiomeEventData BRIGANDS;
    public final static BiomeEventData ANDUIN;
    public final static BiomeEventData EMPTY;

    static {
        ANDUIN = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(BrigandNpcDataPool.CHIEFTAIN).withMount(EntityType.HORSE),
            new BiomeNpcSpawningData(BrigandNpcDataPool.CHIEFTAIN),
            new BiomeNpcSpawningData(BrigandNpcDataPool.MERCENARY).withWeight(4),
            new BiomeNpcSpawningData(BrigandNpcDataPool.THIEF).withWeight(7).withNightRequired().withLightLevelMax(5),
            new BiomeNpcSpawningData(BrigandNpcDataPool.THUG).withWeight(8),

            new BiomeNpcSpawningData(GoblinTownNpcDataPool.GOBLIN).withWeight(5),
            new BiomeNpcSpawningData(GoblinTownNpcDataPool.SCOUT).withWeight(4),
            new BiomeNpcSpawningData(GoblinTownNpcDataPool.WARRIOR).withWeight(3).withNightRequired().withLightLevelMax(5),
            new BiomeNpcSpawningData(GoblinTownNpcDataPool.RIDER).withMount(EntitiesME.WARG).withWeight(2),
            new BiomeNpcSpawningData(GoblinTownNpcDataPool.VETERAN).withNightRequired().withLightLevelMax(5)
        ));

        BRIGANDS = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(BrigandNpcDataPool.CHIEFTAIN).withMount(EntityType.HORSE),
            new BiomeNpcSpawningData(BrigandNpcDataPool.CHIEFTAIN),
            new BiomeNpcSpawningData(BrigandNpcDataPool.MERCENARY).withWeight(3),
            new BiomeNpcSpawningData(BrigandNpcDataPool.THIEF).withWeight(5),
            new BiomeNpcSpawningData(BrigandNpcDataPool.THUG).withWeight(5)
        ));

        CAVE = new BiomeEventData(List.of(
            new BiomeNpcSpawningData(WildGoblinNpcDataPool.GATHERER).withLightLevelMax(5),
            new BiomeNpcSpawningData(WildGoblinNpcDataPool.SCOUT).withLightLevelMax(5),
            new BiomeNpcSpawningData(WildGoblinNpcDataPool.WARRIOR).withLightLevelMax(3),
            new BiomeNpcSpawningData(WildGoblinNpcDataPool.RIDER).withMount(EntitiesME.CAVE_TROLL).withLightLevelMax(3),
            new BiomeNpcSpawningData(WildGoblinNpcDataPool.BRUTE).withLightLevelMax(1)
        ));

        EMPTY = new BiomeEventData(List.of(
        ));
    }
}
