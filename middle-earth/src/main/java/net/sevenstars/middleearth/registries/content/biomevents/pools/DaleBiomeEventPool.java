package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class DaleBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.DALE_MILITIA).withWeight(7).withSameNpc(5, 256),
            new WildSpawnEventData(NpcRegistry.DALE_SOLDIER).withWeight(3).withSameNpc(3, 256),
            new WildSpawnEventData(NpcRegistry.DALE_ELITE_ARCHER).withWeight(3).withSameNpc(3, 256),
            new WildSpawnEventData(NpcRegistry.DALE_KNIGHT).withWeight(1).withSameNpc(0, 256),
            new WildSpawnEventData(NpcRegistry.DALE_SERGEANT).withWeight(0).withSameNpc(0, 512)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.wildBrigands_easy);
    }
}
