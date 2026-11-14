package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.registries.content.npcjobs.NpcJobs;
import net.sevenstars.middleearth.registries.content.npcclasses.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class LongbeardsBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData GREY_PLAINS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.EREBOR_PEASANT).withWeight(6).withSameNpcClass(3, 256).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.EREBOR_PEASANT).withJob(NpcJobs.MINER).withWeight(5).withSameNpcClass(2, 256).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.EREBOR_PEASANT).withJob(NpcJobs.TRAVELLER).withWeight(2).withSameNpcClass(1, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().add(NpcRegistry.EREBOR_PEASANT, NpcJobs.TRAVELLER, 1, 3).addAnimals(EntityType.DONKEY, 1, 1)),
            new WildSpawnEventData(NpcRegistry.EREBOR_MILITIA).withWeight(3).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.EREBOR_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.EREBOR_SOLDIER).withWeight(5).withSameNpcClass(2, 256).withParty(PartyData.create().add(NpcRegistry.EREBOR_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.EREBOR_VETERAN).withWeight(2).withSameNpcClass(0, 256),
            new WildSpawnEventData(NpcRegistry.EREBOR_ELITE).withWeight(1).withSameNpcClass(0, 256)
        ));

        GREY_PLAINS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.EREBOR_MILITIA).withWeight(4).withSameNpcClass(2, 256).lightShouldBeAtLeast(5),
            new WildSpawnEventData(NpcRegistry.EREBOR_SOLDIER).withWeight(3).withSameNpcClass(1, 256).lightShouldBeAtLeast(5),
            new WildSpawnEventData(NpcRegistry.EREBOR_VETERAN).withWeight(1).withSameNpcClass(0, 256).lightShouldBeAtLeast(5),

            new WildSpawnEventData(NpcRegistry.GUNDABAD_SCOUT).withWeight(5).withSameNpcClass(2, 256).requireNight().lightShouldBeBetween(0, 7),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_MILITIA).withWeight(4).withSameNpcClass(2, 256).requireNight().lightShouldBeBetween(0, 7),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_WARRIOR).withWeight(3).withSameNpcClass(1, 256).requireNight().lightShouldBeBetween(0, 7),
            new WildSpawnEventData(NpcRegistry.GUNDABAD_RIDER).withWeight(2).withSameNpcClass(0, 256)
        ));
    }
}
