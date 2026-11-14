package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.registries.content.npcclasses.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class GundabadBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.GUNDABAD_GOBLIN).withWeight(6).withSameNpcClass(4, 128),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(7).withSameNpcClass(3, 128),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(6).withSameNpcClass(2, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(5).withSameNpcClass(1, 256).withParty(PartyData.create().add(NpcRegistry.GUNDABAD_GOBLIN, 2, 3).addAnimals(EntitiesME.WARG, 0, 1)),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_RIDER).withWeight(2).withSameNpcClass(2, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_VETERAN).withWeight(2).withSameNpcClass(0, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_LEADER).withSameNpcClass(0, 512)
        ));


        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(4).withSameNpcClass(1, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(3).withSameNpcClass(0, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(2).withSameNpcClass(0, 256),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_RIDER).withSameNpcClass(0, 256)
        ));
    }
}
