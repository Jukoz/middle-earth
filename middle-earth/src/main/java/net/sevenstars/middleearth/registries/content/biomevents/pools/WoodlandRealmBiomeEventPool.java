package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.registries.content.npctypes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class WoodlandRealmBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;
    public final static BiomeEventData HALL;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_HUNTER).withWeight(7).withSameNpcType(1, 256).withoutSkyRequirement().shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_RANGER).withWeight(5).withSameNpcType(3, 256).withoutSkyRequirement().shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_LANCER).withWeight(3).withSameNpcType(0, 256).withoutSkyRequirement().shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARRIOR).withWeight(2).withSameNpcType(3, 256).withoutSkyRequirement().shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE).withSameNpcType(0, 512)
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);

        SCOUTS = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_RANGER).withWeight(4).withSameNpcType(3, 256).withoutSkyRequirement().shouldSpawnAbove(64),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_SENTINEL).withWeight(3).withSameNpcType(0, 256).withoutSkyRequirement().shouldSpawnAbove(64),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_NIGHTSHADE).withWeight(1).withSameNpcType(0, 256).withoutSkyRequirement().shouldSpawnAbove(64).requireNight()
        )).withMoreWildSpawns(GenericHostilesBiomeEventPool.WILD_BRIGANDS_EASY);

        HALL = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_ARTISAN).withWeight(10).withSameNpcType(5, 256).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_HUNTER).withWeight(6).withSameNpcType(3, 256).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_RANGER).withWeight(4).withSameNpcType(2, 256).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARRIOR).withWeight(2).withSameNpcType(2, 256).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8),
                new WildSpawnEventData(NpcRegistry.WOODLAND_REALM_WARDEN_OF_THE_GLADE).withSameNpcType(0, 512).withoutSkyRequirement().structureManagerRadiusAvoidance(0).lightShouldBeAtLeast(8)
        ));
    }
}
