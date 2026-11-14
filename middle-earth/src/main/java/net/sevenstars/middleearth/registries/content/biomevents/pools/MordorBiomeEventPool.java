package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcclasses.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
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
            new WildSpawnEventData(NpcRegistry.MORDOR_SNAGA).withWeight(5).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(5).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(5).withSameNpcClass(2, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(5).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.MORDOR_SNAGA, 2, 3)),
            new WildSpawnEventData(NpcRegistry.MORDOR_VETERAN).withWeight(2).withSameNpcClass(0, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_CAPTAIN).withSameNpcClass(0, 512),
            new WildSpawnEventData(EntitiesME.CAVE_TROLL).withSameEntity(1, 256).withEntitySurfaceOnly()
        ));

        NURN = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SNAGA).withWeight(5).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(5).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(5).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(5).withSameNpcClass(1, 256)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(4).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.MORDOR_SCOUT, 1, 2)),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(3).withSameNpcClass(2, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(2).withSameNpcClass(0, 256)
        ));

        ITHILIEN = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(4).withSameNpcClass(4, 256).requireNight(),
            new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(3).withSameNpcClass(1, 256).requireNight(),
            new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(2).withSameNpcClass(2, 256).requireNight(),

            new WildSpawnEventData(NpcRegistry.GONDOR_ITHILIEN_RANGER).withWeight(5).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.GONDOR_ITHILIEN_RANGER, 1, 2)),
            new WildSpawnEventData(NpcRegistry.GONDOR_SOLDIER).withWeight(4).withSameNpcClass(2, 256),
            new WildSpawnEventData(NpcRegistry.GONDOR_KNIGHT).withWeight(1).withSameNpcClass(0, 256)
        ));

        DOL_GULDUR = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(4).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.MORDOR_SNAGA).withWeight(4).withSameNpcClass(2, 256),
            new WildSpawnEventData(NpcRegistry.DOL_GULDUR_SCOUT).withWeight(5).withSameNpcClass(4, 256),
            new WildSpawnEventData(NpcRegistry.DOL_GULDUR_WARRIOR).withWeight(2).withSameNpcClass(1, 256)
        ));
    }
}
