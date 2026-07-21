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
            new WildSpawnEventData(NpcRegistry.GUNDABAD_GOBLIN).withWeight(6).withSameNpc(4, 128),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(7).withSameNpc(3, 128),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(6).withSameNpc(2, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(5).withSameNpc(1, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_VETERAN).withWeight(2).withSameNpc(0, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_LEADER).withSameNpc(0, 512)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.wildBrigands_easy);


        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(4).withSameNpc(1, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(3).withSameNpc(0, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(2).withSameNpc(0, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.wildBrigands_easy);
    }
}
