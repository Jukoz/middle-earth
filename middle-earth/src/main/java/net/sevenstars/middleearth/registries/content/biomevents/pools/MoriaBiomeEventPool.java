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
            new WildSpawnEventData(NpcRegistry.MORIA_GOBLIN).withWeight(7).withSameNpcType(4, 256).requireUnderground(),
            new WildSpawnEventData(NpcRegistry.MORIA_WARRIOR).withWeight(6).withSameNpcType(2, 256).requireUnderground(),
            new WildSpawnEventData(NpcRegistry.MORIA_MILITIA).withWeight(5).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.MORIA_SCOUT).withWeight(4).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.MORIA_RIDER).withWeight(4).withSameNpcType(0, 256).withoutSkyRequirement(),
            new WildSpawnEventData(NpcRegistry.MORIA_VETERAN).withWeight(3).withSameNpcType(0, 256).requireNight(),
            new WildSpawnEventData(NpcRegistry.MORIA_CHIEF).withSameNpcType(0, 512).requireNight()
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);

        EREGION = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.BRIGAND_THIEF).withSameNpcType(3, 256).withWeight(2),
            new WildSpawnEventData(NpcRegistry.BRIGAND_THUG).withSameNpcType(2, 256).withWeight(1),
            new WildSpawnEventData(NpcRegistry.MORIA_SCOUT).withSameNpcType(3, 256).withWeight(3),
            new WildSpawnEventData(NpcRegistry.MORIA_RIDER).withSameNpcType(0, 512)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);
    }
}
