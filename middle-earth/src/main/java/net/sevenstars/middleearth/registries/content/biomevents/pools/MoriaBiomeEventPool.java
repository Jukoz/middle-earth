package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcclasses.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class MoriaBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData EREGION;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORIA_GOBLIN).withWeight(7).withSameNpcClass(4, 256).requireUnderground(),
            new WildSpawnEventData(NpcRegistry.MORIA_WARRIOR).withWeight(6).withSameNpcClass(2, 256).requireUnderground(),
            new WildSpawnEventData(NpcRegistry.MORIA_MILITIA).withWeight(5).withSameNpcClass(2, 256),
            new WildSpawnEventData(NpcRegistry.MORIA_SCOUT).withWeight(4).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.MORIA_RIDER).withWeight(4).withSameNpcClass(0, 256).withoutSkyRequirement(),
            new WildSpawnEventData(NpcRegistry.MORIA_VETERAN).withWeight(3).withSameNpcClass(0, 256).requireNight(),
            new WildSpawnEventData(NpcRegistry.MORIA_CHIEF).withSameNpcClass(0, 512).requireNight()
        ));

        EREGION = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.BRIGAND_THIEF).withSameNpcClass(3, 256).withWeight(2),
            new WildSpawnEventData(NpcRegistry.BRIGAND_THUG).withSameNpcClass(2, 256).withWeight(1),
            new WildSpawnEventData(NpcRegistry.MORIA_SCOUT).withSameNpcClass(3, 256).withWeight(3),
            new WildSpawnEventData(NpcRegistry.MORIA_RIDER).withSameNpcClass(0, 512)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);
    }
}
