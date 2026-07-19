package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class WoodlandRealmBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;
    public final static BiomeEventData HALL;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_HUNTER).withWeight(7),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_RANGER).withWeight(5),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_LANCER).withWeight(3),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARRIOR).withWeight(2),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_RANGER).withWeight(4),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_SENTINEL).withWeight(3),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_NIGHTSHADE).withWeight(1).requireNight()
        ));

        HALL = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_ARTISAN).withWeight(10),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_HUNTER).withWeight(6),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_RANGER).withWeight(4),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARRIOR).withWeight(2),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE)
        ));
    }
}
