package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class IsengardBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.ISENGARD_ORC_SNAGA).withWeight(6).withSameNpcType(6, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_ORC_WARRIOR).withWeight(5).withSameNpcType(4, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_SCOUT).withWeight(8).withSameNpcType(5, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_SOLDIER).withWeight(7).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_VETERAN).withWeight(4).withSameNpcType(1, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_BERSERKER).withWeight(2).withSameNpcType(0, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_LEADER).withSameNpcType(0, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.wildBrigands_easy);

        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_SCOUT).withWeight(3).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_ORC_WARRIOR).withWeight(2).withSameNpcType(1, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.wildBrigands_easy);
    }
}
