package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.features.npcs.classes.NpcRegistry;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class DaleBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData LAKE;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.DALE_PEASANT).withWeight(7).withSameNpcClass(4, 256).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.DALE_PEASANT).withWeight(3).withSameNpcClass(4, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcJobs.DROVER).addAnimals(EntityType.DONKEY, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_MILITIA).withWeight(3).withSameNpcClass(5, 256).withParty(PartyData.create().add(NpcRegistry.DALE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_SOLDIER).withWeight(3).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.DALE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_ELITE_ARCHER).withWeight(2).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.DALE_ARCHER, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_KNIGHT).withWeight(1).withSameNpcClass(0, 256),
            new WildSpawnEventData(NpcRegistry.EREBOR_PEASANT).withJob(NpcJobs.TRAVELLER).withWeight(1).withSameNpcClass(1, 512).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.DALE_SERGEANT).withWeight(0).withSameNpcClass(0, 512)
        ));

        LAKE = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.DALE_PEASANT).withJob(NpcJobs.FISHER).withWeight(10).withSameNpcClass(4, 256).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.DALE_PEASANT).withWeight(4).withSameNpcClass(2, 256).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.DALE_MILITIA).withWeight(2).withSameNpcClass(3, 256).withParty(PartyData.create().add(NpcRegistry.DALE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_SOLDIER).withWeight(1).withSameNpcClass(2, 256).withParty(PartyData.create().add(NpcRegistry.DALE_MILITIA, 1, 2))
        ));
    }
}
