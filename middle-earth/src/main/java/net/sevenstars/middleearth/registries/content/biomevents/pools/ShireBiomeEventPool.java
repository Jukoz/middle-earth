package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.npcroles.NpcRoles;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class ShireBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData EDGE;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(8).withSameNpcType(3, 256),
            // Pigs driven to market, and a farmer out with the dog
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(3).withSameNpcType(3, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcRoles.SWINEHERD).addAnimals(EntityType.PIG, 2, 3)),
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(2).withSameNpcType(3, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcRoles.FARMER).addAnimals(EntityType.WOLF, 1, 1)),
            new WildSpawnEventData(NpcRegistry.SHIRE_MILITIA).withWeight(2).withSameNpcType(1, 256).withParty(PartyData.create().add(NpcRegistry.SHIRE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.SHIRE_SHIRRIFF).withSameNpcType(0, 256)
        ));

        EDGE = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(6).withSameNpcType(3, 256),
            new WildSpawnEventData(NpcRegistry.SHIRE_PEASANT).withWeight(2).withSameNpcType(3, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcRoles.SWINEHERD).addAnimals(EntityType.PIG, 2, 3)),
            new WildSpawnEventData(NpcRegistry.SHIRE_BOUNDER).withWeight(5).withSameNpcType(2, 256).withParty(PartyData.create().add(NpcRegistry.SHIRE_BOUNDER, 1, 1)),
            new WildSpawnEventData(NpcRegistry.SHIRE_MILITIA).withWeight(1).withSameNpcType(1, 256).withParty(PartyData.create().add(NpcRegistry.SHIRE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.EREBOR_PEASANT).withRole(NpcRoles.TRAVELLER).withWeight(1).withSameNpcType(1, 512).lightShouldBeAtLeast(8).withParty(PartyData.create().add(NpcRegistry.EREBOR_PEASANT, NpcRoles.TRAVELLER, 1, 2))
        ));
    }
}
