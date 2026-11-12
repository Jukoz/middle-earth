package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.minecraft.entity.EntityType;
import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class GondorBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData PEASANT_FIEF;
    public final static BiomeEventData OSGILIATH;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.GONDOR_PEASANT).withWeight(4).withSameNpcType(2, 256).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.GONDOR_PEASANT).withWeight(2).withSameNpcType(2, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().addAnimals(EntityType.DONKEY, 1, 1)),
                new WildSpawnEventData(NpcRegistry.GONDOR_MILITIA).withWeight(3).withSameNpcType(5, 128).withParty(PartyData.create().add(NpcRegistry.GONDOR_MILITIA, 1, 2)),
                new WildSpawnEventData(NpcRegistry.GONDOR_SOLDIER).withWeight(2).withSameNpcType(3, 128).withParty(PartyData.create().add(NpcRegistry.GONDOR_MILITIA, 1, 2)),
                new WildSpawnEventData(NpcRegistry.GONDOR_KNIGHT).withWeight(2).withSameNpcType(1, 256).withParty(PartyData.create().add(NpcRegistry.GONDOR_SOLDIER, 2, 3)),
                new WildSpawnEventData(NpcRegistry.GONDOR_VETERAN).withWeight(2).withSameNpcType(0, 256),
                new WildSpawnEventData(NpcRegistry.GONDOR_LEADER).withSameNpcType(0, 512)
        ));

        PEASANT_FIEF = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.GONDOR_PEASANT).withWeight(7).withSameNpcType(4, 256).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.GONDOR_PEASANT).withWeight(3).withSameNpcType(4, 256).lightShouldBeAtLeast(8).withParty(PartyData.create().addAnimals(EntityType.COW, 2, 3)),
                new WildSpawnEventData(NpcRegistry.GONDOR_MILITIA).withWeight(3).withSameNpcType(4, 256).withParty(PartyData.create().add(NpcRegistry.GONDOR_MILITIA, 1, 2)),
                new WildSpawnEventData(NpcRegistry.GONDOR_SOLDIER).withWeight(2).withSameNpcType(2, 256).withParty(PartyData.create().add(NpcRegistry.GONDOR_MILITIA, 1, 2)),
                new WildSpawnEventData(NpcRegistry.GONDOR_KNIGHT).withWeight(1).withSameNpcType(0, 256)
        ));


        OSGILIATH = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.MORDOR_SCOUT).withWeight(4).withSameNpcType(3, 256),
                new WildSpawnEventData(NpcRegistry.MORDOR_MILITIA).withWeight(3).withSameNpcType(4, 256),
                new WildSpawnEventData(NpcRegistry.MORDOR_WARRIOR).withWeight(2).withSameNpcType(1, 256),

                new WildSpawnEventData(NpcRegistry.GONDOR_SOLDIER).withWeight(7).withSameNpcType(1, 256),
                new WildSpawnEventData(NpcRegistry.GONDOR_KNIGHT).withWeight(2).withSameNpcType(2, 256),
                new WildSpawnEventData(NpcRegistry.GONDOR_VETERAN).withWeight(2).withSameNpcType(0, 256)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);
    }
}
