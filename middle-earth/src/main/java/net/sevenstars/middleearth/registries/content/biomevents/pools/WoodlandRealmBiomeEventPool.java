package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npcclasses.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class WoodlandRealmBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;
    public final static BiomeEventData HALL;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_HUNTER).withWeight(4).withSameNpcClass(1, 256),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_HUNTER).withWeight(3).withSameNpcClass(2, 256).withParty(PartyData.create().add(NpcRegistry.WOODLAND_REALM_HUNTER, 1, 2)),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_RANGER).withWeight(3).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.WOODLAND_REALM_RANGER, 1, 2)),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_LANCER).withWeight(3).withSameNpcClass(0, 256),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARRIOR).withWeight(1).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.WOODLAND_REALM_WARRIOR, 1, 2)),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE).withSameNpcClass(0, 512)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_RANGER).withWeight(4).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.WOODLAND_REALM_RANGER, 1, 2)),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_SENTINEL).withWeight(3).withSameNpcClass(0, 256),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_NIGHTSHADE).withSameNpcClass(0, 512).requireNight().lightShouldBeBetween(0,7)
        ));

        HALL = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_ARTISAN).withWeight(10).withSameNpcClass(5, 256).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_HUNTER).withWeight(6).withSameNpcClass(3, 256).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_RANGER).withWeight(4).withSameNpcClass(2, 256).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARRIOR).withWeight(2).withSameNpcClass(2, 256).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE).withSameNpcClass(0, 512).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8)
        ));
    }
}
