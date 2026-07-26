package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class LongbeardsBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData GREY_PLAINS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.EREBOR_MILITIA).withWeight(6).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.EREBOR_SOLDIER).withWeight(5).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.EREBOR_VETERAN).withWeight(2).withSameNpcType(0, 256),
            new WildSpawnEventData(NpcRegistry.EREBOR_ELITE).withWeight(1).withSameNpcType(0, 256)
        ));

        GREY_PLAINS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.EREBOR_MILITIA).withWeight(4).withSameNpcType(2, 256).lightShouldBeAtLeast(5),
            new WildSpawnEventData(NpcRegistry.EREBOR_SOLDIER).withWeight(3).withSameNpcType(1, 256).lightShouldBeAtLeast(5),
            new WildSpawnEventData(NpcRegistry.EREBOR_VETERAN).withWeight(1).withSameNpcType(0, 256).lightShouldBeAtLeast(5),

            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(5).withSameNpcType(2, 256).requireNight().lightShouldBeBetween(0, 7),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(4).withSameNpcType(2, 256).requireNight().lightShouldBeBetween(0, 7),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(3).withSameNpcType(1, 256).requireNight().lightShouldBeBetween(0, 7),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_RIDER).withWeight(2).withSameNpcType(0, 256)
        ));
    }
}
