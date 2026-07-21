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
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_CIVILIAN).withWeight(10).withSameNpc(3, 256).shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_RANGER).withWeight(9).withSameNpc(2, 256).shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_WARRIOR).withWeight(7).withSameNpc(2, 256).shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_KNIGHT).withWeight(4).withSameNpc(1, 256).shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_EGLADIL_SENTINEL).withSameNpc(1, 256).shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_EGLADIL_COMMANDER).withSameNpc(0, 256).shouldSpawnAbove(64)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.wildBrigands_easy);

        SCOUTS = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.LOTHLORIEN_RANGER).withWeight(4).withSameNpc(2, 256).shouldSpawnAbove(64),
                new WildSpawnEventData(NpcRegistry.LOTHLORIEN_SENTINEL).withWeight(1).withSameNpc(1, 256).shouldSpawnAbove(64)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.wildBrigands_easy);
    }
}
