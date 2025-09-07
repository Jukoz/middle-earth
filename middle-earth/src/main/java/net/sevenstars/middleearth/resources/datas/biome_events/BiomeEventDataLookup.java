package net.sevenstars.middleearth.resources.datas.biome_events;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.resources.BiomeEventsME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

public class BiomeEventDataLookup {
    public static NpcData findNpcDataForBiome(World world, RegistryEntry<Biome> biome) {
        Identifier biomeEventId = Identifier.of(biome.getIdAsString());
        BiomeEventData eventData = world.getRegistryManager().getOrThrow(BiomeEventsME.KEY).get(biomeEventId);
        if(eventData != null){
            return eventData.findNpcData(world);
        }
        else {
            eventData = world.getRegistryManager().getOrThrow(BiomeEventsME.KEY).get(BiomeEventsME.DEFAULT);
            if(eventData != null)
                return eventData.findNpcData(world);
        }
        return null;
    }
}
