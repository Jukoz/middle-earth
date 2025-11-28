package net.sevenstars.middleearth.resources.datas.biome_events;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.entity.npcs.NpcEntity;
import net.sevenstars.middleearth.registries.DynamicRegistriesME;
import net.sevenstars.middleearth.registries.content.biomevents.BiomeEventRegistry;

public class BiomeEventDataLookup {
    public static BiomeEventData.FoundNpcReturn findNpcDataForBiome(World world, RegistryEntry<Biome> biome, NpcEntity entity) {
        Identifier biomeEventId = Identifier.of(biome.getIdAsString());
        BiomeEventData eventData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.BIOME_EVENT).get(biomeEventId);
        if(eventData != null){
            var foundNpcData = eventData.findNpcData(world, entity);
            if(foundNpcData == null && eventData.getSpawnDefaultWhenUnmet()){
                eventData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.BIOME_EVENT).get(BiomeEventRegistry.DEFAULT);
                foundNpcData = eventData.findNpcData(world, entity);
            }
            return foundNpcData;
        }
        else {
            eventData = world.getRegistryManager().getOrThrow(DynamicRegistriesME.BIOME_EVENT).get(BiomeEventRegistry.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world, entity);
        }
        return null;
    }
}
