package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.features.npcs.classes.NpcRegistry;
import net.sevenstars.middleearth.features.npcs.jobs.NpcJobs;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class ShireBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData EDGE;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(8).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(3).withSameNpcClass(3, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcJobs.SWINEHERD).addAnimals(EntityType.PIG, 2, 3)),
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(2).withSameNpcClass(3, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcJobs.FARMER).addAnimals(EntityType.WOLF, 1, 1)),
            new WildSpawnEventData(NpcRegistry.SHIRE_MILITIA).withWeight(2).withSameNpcClass(1, 256).withParty(PartyData.create().add(NpcRegistry.SHIRE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.SHIRE_SHIRRIFF).withSameNpcClass(0, 256)
        ));

        EDGE = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(6).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(2).withSameNpcClass(3, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcJobs.SWINEHERD).addAnimals(EntityType.PIG, 2, 3)),
            new WildSpawnEventData(NpcRegistry.SHIRE_BOUNDER).withWeight(5).withSameNpcClass(2, 256).withParty(PartyData.create().add(NpcRegistry.SHIRE_BOUNDER, 1, 1)),
            new WildSpawnEventData(NpcRegistry.SHIRE_MILITIA).withWeight(1).withSameNpcClass(1, 256).withParty(PartyData.create().add(NpcRegistry.SHIRE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.EREBOR_PEASANT).withJob(NpcJobs.TRAVELLER).withWeight(1).withSameNpcClass(1, 512).lightShouldBeAtLeast(8).withParty(PartyData.create().add(NpcRegistry.EREBOR_PEASANT, NpcJobs.TRAVELLER, 1, 2))
        ));
    }
}
