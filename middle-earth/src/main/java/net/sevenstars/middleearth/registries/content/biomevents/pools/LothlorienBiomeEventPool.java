package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class LothlorienBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_CIVILIAN).withWeight(10),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_RANGER).withWeight(9),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_WARRIOR).withWeight(7),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_KNIGHT).withWeight(4),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_EGLADIL_SENTINEL),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_EGLADIL_COMMANDER)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.LOTHLORIEN_RANGER).withWeight(4),
                new WildSpawnEventData(NpcRegistry.LOTHLORIEN_SENTINEL).withWeight(1)
        ));
    }
}
