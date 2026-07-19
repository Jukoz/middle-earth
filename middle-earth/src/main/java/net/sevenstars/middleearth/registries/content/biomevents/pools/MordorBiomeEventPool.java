package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class MordorBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData NURN;
    public final static BiomeEventData SCOUTS;
    public final static BiomeEventData ITHILIEN;
    public final static BiomeEventData CAVE;
    public final static BiomeEventData DOL_GULDUR;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SNAGA).withWeight(5),
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(5),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(5),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(5),
            new WildSpawnEventData(NpcRegistry.MORDOR_VETERAN).withWeight(2),
            new WildSpawnEventData(NpcRegistry.MORDOR_CAPTAIN)
        ));

        NURN = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SNAGA).withWeight(5),
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(5),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(5),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(5)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(4),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(3),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(2)
        ));


        ITHILIEN = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(4),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(3),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(2),

            new WildSpawnEventData(NpcRegistry.GONDOR_SOLDIER).withWeight(4),
            new WildSpawnEventData(NpcRegistry.GONDOR_KNIGHT).withWeight(1)
        ));

        CAVE = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SNAGA).withWeight(3),
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(3),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(2)
        ));

        DOL_GULDUR = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.DOL_GULDUR_SCOUT).withWeight(5),
            new WildSpawnEventData(NpcRegistry.DOL_GULDUR_WARRIOR).withWeight(2)
        ));
    }
}
