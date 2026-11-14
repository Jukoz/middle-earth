package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.registries.content.npcroles.NpcRoles;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class DaleBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData LAKE;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.DALE_PEASANT).withWeight(7).withSameNpcType(4, 256).lightShouldBeAtLeast(8),
            // Dale feeds the Mountain
            new WildSpawnEventData(NpcRegistry.DALE_PEASANT).withWeight(3).withSameNpcType(4, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().ledBy(NpcRoles.DROVER).addAnimals(EntityType.DONKEY, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_MILITIA).withWeight(3).withSameNpcType(5, 256).withParty(PartyData.create().add(NpcRegistry.DALE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_SOLDIER).withWeight(3).withSameNpcType(3, 256).withParty(PartyData.create().add(NpcRegistry.DALE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_ELITE_ARCHER).withWeight(2).withSameNpcType(3, 256).withParty(PartyData.create().add(NpcRegistry.DALE_ARCHER, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_KNIGHT).withWeight(1).withSameNpcType(0, 256),
            new WildSpawnEventData(NpcRegistry.EREBOR_TRAVELLER).withWeight(1).withSameNpcType(1, 512).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.DALE_SERGEANT).withWeight(0).withSameNpcType(0, 512)
        ));

        LAKE = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.DALE_FISHERMAN).withWeight(10).withSameNpcType(4, 256).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.DALE_PEASANT).withWeight(4).withSameNpcType(2, 256).lightShouldBeAtLeast(8),
            new WildSpawnEventData(NpcRegistry.DALE_MILITIA).withWeight(2).withSameNpcType(3, 256).withParty(PartyData.create().add(NpcRegistry.DALE_MILITIA, 1, 2)),
            new WildSpawnEventData(NpcRegistry.DALE_SOLDIER).withWeight(1).withSameNpcType(2, 256).withParty(PartyData.create().add(NpcRegistry.DALE_MILITIA, 1, 2))
        ));
    }
}
