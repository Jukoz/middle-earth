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
            new WildSpawnEventData(NpcRegistry.GUNDABAD_GOBLIN).withWeight(6),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(7),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(6),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(5),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_VETERAN).withWeight(2),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_LEADER)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(4),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(3),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(2)
        ));
    }
}
