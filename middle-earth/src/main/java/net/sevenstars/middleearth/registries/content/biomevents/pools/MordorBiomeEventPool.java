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
    public final static BiomeEventData DOL_GULDUR;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SNAGA).withWeight(5).withSameNpcType(3, 64),
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(5).withSameNpcType(3, 64),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(5).withSameNpcType(2, 64),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(5).withSameNpcType(3, 64),
            new WildSpawnEventData(NpcRegistry.MORDOR_VETERAN).withWeight(2).withSameNpcType(0, 64),
            new WildSpawnEventData(NpcRegistry.MORDOR_CAPTAIN).withSameNpcType(0, 512)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);

        NURN = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SNAGA).withWeight(5).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(5).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(5).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(5).withSameNpcType(1, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);

        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(4).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(3).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(2).withSameNpcType(0, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);


        ITHILIEN = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(4).withSameNpcType(4, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(3).withSameNpcType(1, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(2).withSameNpcType(2, 256),

            new WildSpawnEventData(NpcRegistry.GONDOR_SOLDIER).withWeight(4).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.GONDOR_KNIGHT).withWeight(1).withSameNpcType(0, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);

        DOL_GULDUR = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(4).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_SNAGA).withWeight(4).withSameNpcType(2, 256),
            new WildSpawnEventData(NpcRegistry.DOL_GULDUR_SCOUT).withWeight(5).withSameNpcType(4, 256),
            new WildSpawnEventData(NpcRegistry.DOL_GULDUR_WARRIOR).withWeight(2).withSameNpcType(1, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);
    }
}
