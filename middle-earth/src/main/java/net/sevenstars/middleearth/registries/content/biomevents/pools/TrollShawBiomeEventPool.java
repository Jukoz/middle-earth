package net.sevenstars.middleearth.registries.content.biomevents.pools;

import net.sevenstars.middleearth.entity.EntitiesME;
import net.sevenstars.middleearth.resources.datas.biome_events.BiomeEventData;
import net.sevenstars.middleearth.resources.datas.biome_events.data.WildSpawnEventData;

import java.util.List;

public class TrollShawBiomeEventPool {
    public final static BiomeEventData TROLLSHAWS;

    static {
        TROLLSHAWS = new BiomeEventData(false, List.of(
                new WildSpawnEventData(EntitiesME.STONE_TROLL).withSameEntity(3, 256).requireNight().requireSky().withEntitySurfaceOnly(),
                new WildSpawnEventData(EntitiesME.GREAT_HORN).withSameEntity(3, 256).requireSky()
        ));
    }
}
