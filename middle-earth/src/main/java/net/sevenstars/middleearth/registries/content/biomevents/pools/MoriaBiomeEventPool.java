package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class MoriaBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData EREGION;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORIA_GOBLIN).withWeight(7).requireUnderground(),
            new WildSpawnEventData(NpcRegistry.MORIA_MILITIA).withWeight(5),
            new WildSpawnEventData(NpcRegistry.MORIA_SCOUT).withWeight(4),
            new WildSpawnEventData(NpcRegistry.MORIA_WARRIOR).withWeight(6).requireUnderground(),
            new WildSpawnEventData(NpcRegistry.MORIA_RIDER).withWeight(4),
            new WildSpawnEventData(NpcRegistry.MORIA_VETERAN).withWeight(3).requireNight(),
            new WildSpawnEventData(NpcRegistry.MORIA_CHIEF).requireNight()
        ));

        EREGION = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.BRIGAND_THIEF).withWeight(2),
            new WildSpawnEventData(NpcRegistry.BRIGAND_THUG).withWeight(1),
            new WildSpawnEventData(NpcRegistry.MORIA_SCOUT).withWeight(3),
            new WildSpawnEventData(NpcRegistry.MORIA_RIDER)
        ));
    }
}
