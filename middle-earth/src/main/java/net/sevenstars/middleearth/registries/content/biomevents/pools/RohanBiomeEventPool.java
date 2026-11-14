package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.npcroles.NpcRoles;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class RohanBiomeEventPool {
    public final static BiomeEventData DEFAULT;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.ROHAN_PEASANT).withWeight(7).withSameNpcType(3, 256).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.ROHAN_PEASANT).withWeight(2).withSameNpcType(3, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcRoles.HERDSMAN).addAnimals(EntityType.COW, 2, 4)),
            new WildSpawnEventData(NpcRegistry.ROHAN_PEASANT).withWeight(4).withSameNpcType(3, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcRoles.HERDSMAN).addAnimals(EntityType.SHEEP, 3, 5)),
            new WildSpawnEventData(NpcRegistry.ROHAN_PEASANT).withWeight(2).withSameNpcType(3, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcRoles.HERDSMAN).addAnimals(EntityType.HORSE, 2, 4)),
            new WildSpawnEventData(NpcRegistry.ROHAN_MILITIA).withWeight(3).withSameNpcType(2, 256).withParty(PartyData.create().add(NpcRegistry.ROHAN_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.ROHAN_SOLDIER).withWeight(2).withSameNpcType(2, 256).withParty(PartyData.create().add(NpcRegistry.ROHAN_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.ROHAN_KNIGHT).withWeight(2).withSameNpcType(1, 256).withParty(PartyData.create().add(NpcRegistry.ROHAN_SOLDIER, 2, 3)),
            new WildSpawnEventData(NpcRegistry.ROHAN_EORLING_MARSHAL).withWeight(1).withSameNpcType(0, 512)
        ));
    }
}
