package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class RohanBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.ROHAN_MILITIA).withWeight(7).withSameNpc(2, 256),
            new WildSpawnEventData(NpcRegistry.ROHAN_SOLDIER).withWeight(3).withSameNpc(2, 256),
            new WildSpawnEventData(NpcRegistry.ROHAN_KNIGHT).withWeight(2).withSameNpc(1, 256),
            new WildSpawnEventData(NpcRegistry.ROHAN_EORLING_MARSHAL).withWeight(1).withSameNpc(0, 512)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.wildBrigands_easy);
    }
}
