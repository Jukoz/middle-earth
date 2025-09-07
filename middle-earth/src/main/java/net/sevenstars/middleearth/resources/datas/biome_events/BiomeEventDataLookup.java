package net.sevenstars.middleearth.resources.datas.biome_events;

import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.sevenstars.middleearth.resources.BiomeEventsME;
import net.sevenstars.middleearth.resources.NpcME;
import net.sevenstars.middleearth.resources.datas.npcs.NpcData;

public class BiomeEventDataLookup {
    public static NpcData findNpcDataForBiome(World world, RegistryEntry<Biome> biome) {
        Identifier biomeEventId = Identifier.of(biome.getIdAsString() + "_events");
        BiomeEventData eventData = world.getRegistryManager().getOrThrow(BiomeEventsME.KEY).get(biomeEventId);
        Registry<NpcData> registry = world.getRegistryManager().getOrThrow(NpcME.KEY);
        if(eventData != null){
            return registry.get(eventData.getNpcDataIdTemp());
        }
        return registry.get(0);
    }
}
