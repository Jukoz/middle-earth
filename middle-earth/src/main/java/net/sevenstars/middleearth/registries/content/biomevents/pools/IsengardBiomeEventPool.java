package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.features.npcs.classes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class IsengardBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.ISENGARD_ORC_SNAGA).withWeight(6).withSameNpcClass(6, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_ORC_WARRIOR).withWeight(5).withSameNpcClass(4, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_SCOUT).withWeight(8).withSameNpcClass(5, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_SOLDIER).withWeight(7).withSameNpcClass(3, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_VETERAN).withWeight(4).withSameNpcClass(1, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_BERSERKER).withWeight(2).withSameNpcClass(0, 256),
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_LEADER).withSameNpcClass(0, 256)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.ISENGARD_URUK_HAI_SCOUT).withWeight(3).withSameNpcClass(2, 256).withParty(PartyData.create().add(NpcRegistry.ISENGARD_URUK_HAI_SCOUT, 1, 2).addAnimals(EntitiesME.WARG, 1, 2)),
            new WildSpawnEventData(NpcRegistry.ISENGARD_ORC_WARRIOR).withWeight(2).withSameNpcClass(1, 256)
        ));
    }
}
