package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class ShireBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(12).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.SHIRE_MILITIA).withWeight(3).withSameNpcType(1, 256),
            new WildSpawnEventData(NpcRegistry.SHIRE_SHIRRIFF).withSameNpcType(0, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.wildBrigands_easy);
    }
}
