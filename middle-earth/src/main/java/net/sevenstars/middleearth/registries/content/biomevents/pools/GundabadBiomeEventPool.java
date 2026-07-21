package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class GundabadBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.GUNDABAD_GOBLIN).withWeight(6).withSameNpcType(4, 128),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(7).withSameNpcType(3, 128),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(6).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(5).withSameNpcType(1, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_RIDER).withWeight(2).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_VETERAN).withWeight(2).withSameNpcType(0, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_LEADER).withSameNpcType(0, 512)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);


        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(4).withSameNpcType(1, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(3).withSameNpcType(0, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(2).withSameNpcType(0, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_RIDER).withSameNpcType(0, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);
    }
}
