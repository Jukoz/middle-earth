package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.features.npcs.classes.NpcRegistry;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.PartyData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class LothlorienBiomeEventPool {
    public final static BiomeEventData DEFAULT;
    public final static BiomeEventData SCOUTS;

    static {
        DEFAULT = new BiomeEventData(false, List.of(
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_CIVILIAN).withWeight(10).withSameNpcClass(3, 256).shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_RANGER).withWeight(4).withSameNpcClass(2, 256).shouldSpawnAbove(64).withParty(PartyData.create().add(NpcRegistry.LOTHLORIEN_RANGER, 1, 2)),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_WARRIOR).withWeight(3).withSameNpcClass(2, 256).shouldSpawnAbove(64).withParty(PartyData.create().add(NpcRegistry.LOTHLORIEN_WARRIOR, 1, 2)),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_KNIGHT).withWeight(2).withSameNpcClass(1, 256).shouldSpawnAbove(64).withParty(PartyData.create().add(NpcRegistry.LOTHLORIEN_WARRIOR, 1, 2)),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_EGLADIL_SENTINEL).withSameNpcClass(1, 256).shouldSpawnAbove(64),
            new WildSpawnEventData(NpcRegistry.LOTHLORIEN_EGLADIL_COMMANDER).withSameNpcClass(0, 256).shouldSpawnAbove(64)
        ));

        SCOUTS = new BiomeEventData(false, List.of(
                new WildSpawnEventData(NpcRegistry.LOTHLORIEN_RANGER).withWeight(4).withSameNpcClass(2, 256).shouldSpawnAbove(64).withParty(PartyData.create().add(NpcRegistry.LOTHLORIEN_RANGER, 1, 2)),
                new WildSpawnEventData(NpcRegistry.LOTHLORIEN_SENTINEL).withWeight(1).withSameNpcClass(1, 256).shouldSpawnAbove(64)
        ));
    }
}
